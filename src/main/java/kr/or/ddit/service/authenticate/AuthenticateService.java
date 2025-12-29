package kr.or.ddit.service.authenticate;

import kr.or.ddit.dto.MemberDTO;

public interface AuthenticateService {
	/**
	 * 사용자 인증 여부를 판단하는 로직
	 * @param username 식별자
	 * @param password 자격증명
	 * @return 인증에 성공한 사용자의 DTO 변환, 인증 실패시에 custom exception throw.
	 * @throws AuthenticateException
	 */
	
	MemberDTO authenticate(String username, String password);
}
