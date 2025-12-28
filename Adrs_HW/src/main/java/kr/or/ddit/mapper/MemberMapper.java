package kr.or.ddit.mapper;

import kr.or.ddit.dto.MemberDTO;

public interface MemberMapper {
	MemberDTO selectMemberForAuth(String username);
}
