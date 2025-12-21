package kr.or.ddit.homework07.mbti.service;

import java.util.List;

import kr.or.ddit.homework07.mbti.dto.MbtiDTO;

/**
 * Service Inferface
 * 사용할수 있는 비지니스 로직을 정의한다.
 * 인터페이스이기 떄문에 B.L을 담을 추상메서드만 정의
 */
public interface MbtiService {
	public List<MbtiDTO> retrieveMbtiList();
    public MbtiDTO retrieveMbti(String mtType);
    public int createMbti(MbtiDTO dto);
    public int modifyMbti(MbtiDTO dto);
    public int removeMbti(String mtType);
}
