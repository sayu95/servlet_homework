package kr.or.ddit.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.dto.AddressDTO;
import kr.or.ddit.service.address.AddressService;
import kr.or.ddit.service.address.AddressServiceImpl;
import kr.or.ddit.validate.ValidateUtils;
import kr.or.ddit.validate.groups.InsertGroup;
import kr.or.ddit.validate.groups.UpdateGroup;

@WebServlet("/address")
public class AddressController extends HttpServlet {
    private AddressService service = new AddressServiceImpl();

    // 1. 조회 (목록 및 이름 검색)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession();
        
        // 세션에서 꺼내기
        Map<String, String> errors = (Map) session.getAttribute("errors");
        AddressDTO address = (AddressDTO) session.getAttribute("address");
        String message = (String) session.getAttribute("message"); // 추가
        
        if (errors != null) {
        	req.setAttribute("errors", errors);
        	req.setAttribute("address", address);
            
            // 중요: 한 번 사용한 데이터는 세션에서 지워줘야 함 (Flash Attribute)
            session.removeAttribute("errors");
            session.removeAttribute("address");
        }
        
        if (message != null) { // 추가
        	req.setAttribute("message", message);
            session.removeAttribute("message"); // 꺼낸 후 삭제 (Flash Attribute)
        }
        
        String username = getUserName(req, resp);
        if (username == null) return;

        // 이름 검색 파라미터 확인 (Optional)
        String searchName = req.getParameter("searchName");
        
        String logicalViewName = "address";

        try {
            List<AddressDTO> addressList;
            if (searchName != null && !searchName.trim().isEmpty()) {
            	
            	AddressDTO dto = AddressDTO.builder()
                        .memId(username)
                        .adrsName(searchName) 
                        .build();
            	
                addressList = service.readAddress(dto);
            } else {
                addressList = service.readAddressList(username);
            }

            if (addressList == null || addressList.isEmpty()) {
            	if (req.getAttribute("message") == null) {
                    req.setAttribute("message", "조회된 주소록 정보가 없습니다.");
                }
            } else {
                req.setAttribute("addressList", addressList);
            }
            
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        if(logicalViewName.startsWith("redirect:")) {
			String location = logicalViewName.replace("redirect:", req.getContextPath());
			resp.sendRedirect(location);
		} else {
			String prefix = "/WEB-INF/views/";
			String suffix = ".jsp";
			String view = prefix + logicalViewName + suffix;
			req.getRequestDispatcher(view).forward(req, resp);
		}
        
    }

    // 2. 생성 (등록)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	// 1. 사용자 인증 확인
        String username = getUserName(req, resp);
        
        // 2. Command Object 생성
        AddressDTO dto = AddressDTO.builder()
                .memId(username)
                .adrsName(req.getParameter("adrsName"))
                .adrsTel(req.getParameter("adrsTel"))
                .adrsAdd(req.getParameter("adrsAdd"))
                .adrsMail(req.getParameter("adrsMail"))
                .build();
        
        // 3. 검증시작
        Map<String, String> errors = ValidateUtils.validate(dto, InsertGroup.class);
        
        String logicalViewName = "";
        
        if(errors.isEmpty()) {
        	// 검증 통과: 서비스 호출
        	try {
        	boolean success = service.createAddress(dto); // 서비스 구현 필요
        	if(success) {
        		logicalViewName = "redirect:/address";
        	} else {
                req.setAttribute("message", "서버 오류로 등록에 실패했습니다.");
                logicalViewName = "redirect:/address"; // 다시 폼으로
            }
        	
        } catch (Exception e) {
			// service 예외 처리
        	resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
		}
        } else {
        	//검증 실패 : 에러메세지, 기존 입력 데이터를 넘겨야함
        	HttpSession session = req.getSession();
            session.setAttribute("errors", errors);
            session.setAttribute("address", dto);
            logicalViewName = "redirect:/address";
        }
        if(logicalViewName.startsWith("redirect:")) {
			String location = logicalViewName.replace("redirect:", req.getContextPath());
			resp.sendRedirect(location);
		} else {
			String prefix = "/WEB-INF/views/";
			String suffix = ".jsp";
			String view = prefix + logicalViewName + suffix;
			req.getRequestDispatcher(view).forward(req, resp);
		}
    }

    // 3. 수정 (Update)
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 사용자 인증 확인
        String username = getUserName(req, resp);
        if (username == null) return;

        // 2. Command Object 생성
        AddressDTO dto = AddressDTO.builder()
                .adrsNo(Integer.parseInt(req.getParameter("adrsNo")))
                .memId(username)
                .adrsName(req.getParameter("adrsName"))
                .adrsTel(req.getParameter("adrsTel"))
                .adrsAdd(req.getParameter("adrsAdd"))
                .adrsMail(req.getParameter("adrsMail"))
                .build();

        // 3. 검증 시작
        Map<String, String> errors = ValidateUtils.validate(dto, UpdateGroup.class);

        // 4. 검증 결과에 따른 처리
        if (!errors.isEmpty()) {
        	//검증 실패 : 에러메세지, 기존 입력 데이터를 넘겨야함
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setCharacterEncoding("UTF-8");
            String firstError = errors.values().iterator().next();
            resp.getWriter().write(firstError); 
            return;
        }

        // 5. 검증 통과: 수정 서비스 호출
        try {
        	
            boolean success = service.modifyAddress(dto);
            
            if (success) {
                resp.setStatus(HttpServletResponse.SC_OK); // 200 성공
                resp.getWriter().write("수정되었습니다.");
            } else {
                // 본인 주소가 아니거나 데이터가 없을 때
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "수정 권한이 없거나 대상을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
        }
    }

    // 4. 삭제 (Delete)
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String adrsNo = req.getParameter("adrsNo");
        String username = getUserName(req, resp);
        
        AddressDTO dto = AddressDTO.builder()
                .adrsNo(Integer.parseInt(adrsNo))
                .memId(username) // 본인 확인용
                .build();
        
        boolean success = service.removeAddress(dto);
        
        if(success) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 성공
        } else {
            resp.sendError(500);
        }
    }

    // 인증 공통 로직 추출
    private String getUserName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Principal user = req.getUserPrincipal();
        if (user == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 정보 만료");
            return null;
        }
        return user.getName();
    }
}