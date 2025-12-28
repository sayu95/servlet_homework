package kr.or.ddit.mapper;

import java.util.List;

import kr.or.ddit.dto.AddressDTO;

public interface AddressMapper {
	/**
	 * 해당 회원의 주소록 등록
	 * @param AddressDTO
	 * @return 삽입된 행의 수
	 */
	int insertAddress(AddressDTO addressDto);
	
	/** 
	 * 해당 회원의 특정 주소록 조회
	 * @param AddressDTO
	 * @return List로 addressName에 해당되는 특정 주소록
	 */
	List<AddressDTO> selectAddress(AddressDTO addressDto);
	
	/** 
	 * 해당 회원의 모든 주소록 조회
	 * @return 모든 주소록
	 */
	List<AddressDTO> selectAllAddress(String username);
	
	/** 
	 * 해당 회원의 특정 주소록 수정
	 * @param AddressDTO
	 * @return 수정된 행의수
	 */
	int updateAddress(AddressDTO addressDto);
	
	/**
	 * 해당 회원의 주소록 삭제
	 * @param AddressDTO
	 * @return 삭제된 행의수
	 */
	int deleteAddress(AddressDTO addressDto);
}
