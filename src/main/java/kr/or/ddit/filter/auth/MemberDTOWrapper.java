package kr.or.ddit.filter.auth;

import java.security.Principal;

import kr.or.ddit.dto.MemberDTO;
import lombok.Getter;
import lombok.ToString;

/**
 * wrapper(adapter) 패턴 : 
 * 	- 원본 객체의 상태를 변경할 수 없는 경우,
 * 	  wrapper(adapter)를 통해 상태변경을 표현(추가 인터페이스를 구현)할 수 있음.
 * 
 * 		wrapper(adapter)는 adaptee를 갖고, 기본 생성자가 없음
 */

@Getter
@ToString(callSuper = false)
public class MemberDTOWrapper implements Principal {
	
	private final MemberDTO realUser;

	public MemberDTOWrapper(MemberDTO realUser) {
		super();
		this.realUser = realUser;
	}

	@Override
	public String getName() {
		return realUser.getMemId();
	}
}
