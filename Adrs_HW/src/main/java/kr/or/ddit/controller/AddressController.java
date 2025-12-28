package kr.or.ddit.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.or.ddit.dto.AddressDTO;
import kr.or.ddit.service.address.AddressService;
import kr.or.ddit.service.address.AddressServiceImpl;

@WebServlet("/address")
public class AddressController extends HttpServlet {
	
	private AddressService service= new AddressServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 인증 객체 확인
		Principal user = req.getUserPrincipal();
		
		// 2. 인증 객체 검증
		boolean result = Optional.ofNullable(user)
							.isEmpty();
		if(result) resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증 정보가 만료되었습니다. 다시 로그인해주세요.");
		
		// 3. 서비스로 보낼 username 추출
		String username = user.getName();
		
		try {
			List<AddressDTO> addressList = service.readAddressList(username);
			
			Optional.ofNullable(addressList)
			    .filter(list -> !list.isEmpty())
			    .ifPresentOrElse(
			        list -> req.setAttribute("addressList", list), // 값이 있을 때
			        () -> req.setAttribute("message", "조회된 주소록 정보가 없습니다.") // 값이 없을 때
			    );
			req.setAttribute("addressList", addressList);
			req.getRequestDispatcher("/WEB-INF/views/address.jsp").forward(req, resp);
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터 조회 중 문제가 발생했습니다.");
		}
	}
}
