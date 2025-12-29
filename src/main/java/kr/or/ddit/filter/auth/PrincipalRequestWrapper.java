package kr.or.ddit.filter.auth;

import java.security.Principal;
import java.util.Optional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class PrincipalRequestWrapper extends HttpServletRequestWrapper {
	
	private final MemberDTOWrapper principal;

	public PrincipalRequestWrapper(HttpServletRequest request, MemberDTOWrapper principal) {
		super(request);
		this.principal = principal;
	}
	
	@Override
	public Principal getUserPrincipal() {
		return this.principal;
	}
}
