package kr.or.ddit.service.authenticate;

import kr.or.ddit.service.exception.AuthenticateException;

public class BadCrendentialException extends AuthenticateException {

	public BadCrendentialException() {
		this("비밀번호 오류");
	}

	public BadCrendentialException(String message) {
		super(message);
	}
	
}
