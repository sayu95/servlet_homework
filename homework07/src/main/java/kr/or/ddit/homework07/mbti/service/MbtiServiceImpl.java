package kr.or.ddit.homework07.mbti.service;

import java.util.List;

import kr.or.ddit.homework07.exception.MbtiAlreadyExistsException;
import kr.or.ddit.homework07.exception.MbtiNotFoundException;
import kr.or.ddit.homework07.mbti.dto.MbtiDTO;
import kr.or.ddit.homework07.mbti.mapper.MbtiMapper;


//지금은 단순한 CRUD이기 때문에 서비스영역이 할일이 없다.
public class MbtiServiceImpl implements MbtiService {
	
	private final MbtiMapper mapper;

	public MbtiServiceImpl(MbtiMapper mapper) {
        this.mapper = mapper;
    }

	@Override
	public List<MbtiDTO> retrieveMbtiList() {
		return mapper.mbtiSelectAll();
	}

	@Override
	public MbtiDTO retrieveMbti(String mtType) {
		//비지니스 로직 시작
		return mapper.mbtiSelectOne(mtType);
	}

	//해당 예외처리는 DB을 갔다와야 해당값이 있음을 알기때문에 비지니스 모델에서 예외를 처리한다.
	@Override
	public int createMbti(MbtiDTO dto) {
		//비지니스 로직 시작
		MbtiDTO existing = mapper.mbtiSelectOne(dto.getMtType());
		if (existing != null) {
	        // 이미 있으면 커스텀 예외를 던짐
			throw new MbtiAlreadyExistsException(dto.getMtType() + "은 이미 존재하는 유형입니다.");
	    }
		return mapper.mbtiCreate(dto);
	}

	@Override
	public int modifyMbti(MbtiDTO dto) {
		//비지니스 로직 시작
		MbtiDTO existing = mapper.mbtiSelectOne(dto.getMtType());
	    if (existing == null) {
	        // 수정할 대상이 없으면 예외 발생!
	        throw new MbtiNotFoundException(dto.getMtType() + "은 존재하지 않는 유형이라 수정할 수 없습니다.");
	    }
	    return mapper.mbtiUpdate(dto);
	}

	@Override
	public int removeMbti(String mtType) {
		//비지니스 로직 시작
		return mapper.mbtiDelete(mtType);
	}

}
