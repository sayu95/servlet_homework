package kr.or.ddit.homework07.mbti.mapper;

import java.util.List;

import kr.or.ddit.homework07.mbti.dto.MbtiDTO;

/**
 * dao의 역할을 대신할 Mapper
 * 
 * 어떻게 가능한것인가?
 * 
 * Mybatis는 Dao의 역할을 완벽하게 대체해준다.
 * 
 * 그렇다면 Dao의 역할은? 
 * 
 * 1. Dao는 Sqlsession으로 데이터베이스의 접근하는 객체
 * 2. Sqlssession에 정의된 메소드로 sql를 처리하고 그 결과를 반환하는 객체이다.
 * 
 * 이 두 가지 역할을 Mapper로 완벽하게 대체가 가능하다.
 * 
 * 어떻게 가능한것인가?
 * 
 * Mybatis는 Dao의 객체를 Proxy 패턴을 이용해 하나의 가짜 객체를 만든다.
 * 따라서 Dao를 직접 구현하지 않아도 가짜 객체를 이용해 위에 설명한 2가지 기능을 완벽히 대체한다.
 * 
 */

public interface MbtiMapper {
	List<MbtiDTO> mbtiSelectAll();
	MbtiDTO mbtiSelectOne(String mtType);
	int mbtiCreate(MbtiDTO dto);
	int mbtiUpdate(MbtiDTO dto);
	int mbtiDelete(String mtType);
}
