package kr.or.ddit.service.exception;

public class UserNotFoundException extends AuthenticateException {
	private final String username;

	public UserNotFoundException(String username) {
		super("%s 아이디의 사용자가 없음".formatted(username));
		this.username = username;
	}
}
