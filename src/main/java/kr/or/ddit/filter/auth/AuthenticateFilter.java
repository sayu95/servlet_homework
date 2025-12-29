package kr.or.ddit.filter.auth;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticateFilter extends HttpFilter {
	@Override
	protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// 1. 현재 요청이 로그인이 필요한 '보호 자원'인지 확인 (예: /address)
		String uri = req.getRequestURI();
		
		boolean isSecured = uri.contains("/address");
		
		// 2. Principal 확인
		HttpSession session = req.getSession();
		MemberDTOWrapper principal = (MemberDTOWrapper) session.getAttribute("authMember");
		HttpServletRequest wrappedReq = new PrincipalRequestWrapper(req, principal);
		boolean isAuthenticate = principal != null;
		
		if (isSecured) {
	        if (isAuthenticate) {
	            //로그인 상태라면 그대로 진행
	            chain.doFilter(wrappedReq, res);
	        } else {
	            //로그인 안 된 상태라면 로그인 페이지로 보냄
	            res.sendRedirect(req.getContextPath() + "/");
	        }
	    } else {
	        // 보호 자원이 아니면(로그인 페이지 등) 그냥 통과
	        chain.doFilter(req, res);
	    }
	}
}
