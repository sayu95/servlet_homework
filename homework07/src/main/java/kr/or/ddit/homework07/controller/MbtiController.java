package kr.or.ddit.homework07.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.homework07.exception.ExceptionDTO;
import kr.or.ddit.homework07.exception.MbtiAlreadyExistsException;
import kr.or.ddit.homework07.exception.MbtiInvalidTypeException;
import kr.or.ddit.homework07.exception.MbtiNotFoundException;
import kr.or.ddit.homework07.mbti.dto.MbtiDTO;
import kr.or.ddit.homework07.mbti.mapper.MbtiMapper;
import kr.or.ddit.homework07.mbti.service.MbtiService;
import kr.or.ddit.homework07.mbti.service.MbtiServiceImpl;
import kr.or.ddit.homework07.mybatis.CustomSqlSessionFactoryBuilber;

/**
 * MbtiController 구현
 */

@WebServlet("/mbti/*")
public class MbtiController extends HttpServlet {

	// 스프링을 써야하는 이유1
	// 컨트롤에서 팩토리를 생성하고있음
	// 컨트롤은 데이터만 전달하는 역할을 해야하는데 이를 해결할 수가 없음
	// 왜냐? 스프링 없이는이를 해결할 수 없으니까 앞으로 트랜잭션 처리를 위해 컨트롤에서 팩토리와 세션을 생성해야함.
	// 당연히 팩토리와 세션은 service에서만 필요하지만 Service에서 생성할 경우 극심한 장애를 유발할 수 있음.
	// 만약 세션(SqlSession)을 각 서비스 내부에서 만든다면, 회원정보 저장은 성공하고 포인트 지급은 실패했을 때
	// 이미 저장된 회원정보를 취소(Rollback)할 방법이 없음.
	// 두 트랜잭션을 하나로 묶어 하나가 실패하면 모두 실패해야하기 때문 따로 해버리면 회원가입이 성공해도 포인트 지급이 안될 수 있음

	// init()에서 직접 factory와 gson을 챙기는 과정
	// 스프링은 "Bean(빈)"이라는 개념으로 완전히 자동화함.
	// 스프링은 컨테이너가 미리 SqlSessionFactory를 빈(Bean)으로 등록해 둡니다.
	// init은 결국 스프링의 빈의 역할이네? >> 서블릿의 init()은 스프링 프레임워크가 탄생하게 된 거대한 뿌리이자,
	// '빈(Bean)의 초기화 단계' 그 자체
	private SqlSessionFactory factory;
	private Gson gson;

	// @Autowired나 @RequiredArgsConstructor(Lombok의 생성자 주입)으로 선언만 하면 스프링이 알아서 꽂아준다.
	// 역시나 당연히 컨트롤에서 하지않고 MbtiMapper를 Mapper에서 주입한다.
	@Override
	public void init() throws ServletException {
		this.factory = CustomSqlSessionFactoryBuilber.getSqlsessionfactory();
		this.gson = new Gson();
	}

	// @GetMapping
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (PrintWriter out = resp.getWriter()) {

			// Session은 절대 전역으로 선언해서는 안됨
			// 하나의 세션을 공유하는 상황이 일어남
			// 세션은 항상 닫아줘야하니까 try / catch문
			try (SqlSession session = factory.openSession()) {

				// 스프링을 써야하는 이유 2
				// 컨트롤에서 데이터만 전달하는 역할을 해야하는데 역시 마찬가지로 트랜잭션 처리를 위해
				// Controller에서 MapperProxy를 생성함
				// 그리고 결국 세션이 있어야 mapper객체가 만들어지기 때문에 컨트롤에서 mapper를 받아 넘김

				MbtiMapper mapper = session.getMapper(MbtiMapper.class);
				MbtiService service = new MbtiServiceImpl(mapper);
				
				// 스프링을 써야하는 이유3
				// 경로를 분석해야함 이후 스프링의 DispatcherServlet로 경로 Depth를 해결함
				String validPath = Optional.ofNullable(req.getPathInfo())
						.filter(Predicate.not(String::isBlank))
						.orElse("/");

				// 값이 있냐 없냐에 따라 실행할 로직을 분리
				if (validPath.equals("/")) {
					List<MbtiDTO> list = service.retrieveMbtiList();
					out.print(gson.toJson(list));
				} else {
					// 그렇지 않다면 MBTI 개별조회
					String mbtiType = validPath.substring(1);
					MbtiDTO mbti = service.retrieveMbti(mbtiType);
					if (mbti == null) {
						throw new MbtiNotFoundException(mbtiType);
					}
					out.print(gson.toJson(mbti));
				}
				// 커스텀예외에 대한 주석은 doPost에 정의
			} catch (MbtiNotFoundException e) {
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				out.print(gson.toJson(new ExceptionDTO(e, req)));
			} catch (Exception e) {
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				out.print(gson.toJson(new ExceptionDTO(e, req)));
			}
		}
	}

	// @PostMapping
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (PrintWriter out = resp.getWriter()) {
			try (SqlSession session = factory.openSession()) {
				MbtiMapper mapper = session.getMapper(MbtiMapper.class);

				MbtiDTO dto = gson.fromJson(req.getReader(), MbtiDTO.class);

				MbtiService service = new MbtiServiceImpl(mapper);
				int result = service.createMbti(dto);
				
				//컨트롤러가 mapper를 가지고 있기때문에 컨트롤러에서 commit
				session.commit();
				
				// JSON 직렬화
				out.print(gson.toJson(result));

				// 커스텀 인셉션
			} catch (MbtiNotFoundException e) {
				// 상태코드 [404]
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				// 스프링을 써야하는 이유 4
				// 결국 ExceptionDTO의 오류를 클라이언트에게 보내기 위해서 Json화 해야하는데
				// 스프링을 쓰면 JackSon 라이브러리로 바로 직렬화 해주는데
				// 순수 서블릿을 쓰기 때문에 Gson 라이브러리로 내가 직렬화를 직접 구현해야함
				// 스프링이 몰래 해주던 Jackson의 일을 내가 Gson으로 직접해야함
				resp.getWriter().print(gson.toJson(new ExceptionDTO(e, req)));

				// 커스텀 인셉션
			} catch (MbtiAlreadyExistsException e) {
				// 상태코드 400
				resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				resp.getWriter().print(gson.toJson(new ExceptionDTO(e, req)));
			} catch (Exception e) {
				resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				resp.getWriter().print(gson.toJson(new ExceptionDTO(e, req)));
			}
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try (PrintWriter out = resp.getWriter()) {
			try (SqlSession session = factory.openSession()) {
				MbtiMapper mapper = session.getMapper(MbtiMapper.class);
				MbtiService service = new MbtiServiceImpl(mapper);

				String mbtiType = Optional.ofNullable(req.getPathInfo()).map(path -> path.substring(1))
						.filter(Predicate.not(String::isBlank))
						.orElseThrow(() -> new MbtiInvalidTypeException("수정할 MBTI 항목을 먼저 선택해야 합니다."));
				
				MbtiDTO dto = gson.fromJson(req.getReader(), MbtiDTO.class);
				dto.setMtType(mbtiType);
				int result = service.modifyMbti(dto);
				
				//컨트롤러가 mapper를 가지고 있기때문에 컨트롤러에서 commit
				session.commit();
				
				out.print(gson.toJson(result));
				
			} catch (MbtiNotFoundException e) {
				resp.setStatus(404);
				out.print(gson.toJson(new ExceptionDTO(e, req)));
			} catch (Exception e) {
				resp.setStatus(500);
				out.print(gson.toJson(new ExceptionDTO(e, req)));
			}
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    try (PrintWriter out = resp.getWriter()) {
	        try (SqlSession session = factory.openSession()) {
	            MbtiMapper mapper = session.getMapper(MbtiMapper.class);
	            MbtiService service = new MbtiServiceImpl(mapper);

	            String mbtiType = Optional.ofNullable(req.getPathInfo())
	                    .map(path -> path.substring(1))
	                    .filter(Predicate.not(String::isBlank))
	                    .orElseThrow(() -> new MbtiInvalidTypeException("삭제할 대상을 선택해 주세요."));
	            
	            int result = service.removeMbti(mbtiType);
	            
	            //컨트롤러가 mapper를 가지고 있기때문에 컨트롤러에서 commit
	            session.commit();
	            
	            out.print(gson.toJson(result));
	            
	        } catch (MbtiNotFoundException e) {
	            resp.setStatus(404);
	            out.print(gson.toJson(new ExceptionDTO(e, req)));
	        } catch (Exception e) {
	            resp.setStatus(500);
	            out.print(gson.toJson(new ExceptionDTO(e, req)));
	        }
	    }
	}
}
