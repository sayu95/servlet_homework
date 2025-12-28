package kr.or.ddit.service.address;

import java.util.List;

import kr.or.ddit.dto.AddressDTO;

public interface AddressService {
	/**
	 * 해당 회원의 주소록 등록서비스
	 * @param address
	 * @return 삽입된 행의 성공여부
	 */
	boolean createAddress(AddressDTO addressDto);
	
	/**
	 * 해당 회원의 특정 주소록 조회서비스
	 * @param addressName
	 * @return 중복이 가능함으로 List로 addressName에 해당되는 특정 address
	 */
	List<AddressDTO> readAddress(AddressDTO addressDto);
	
	/** 해당 회원의 모든 주소록 조회서비스
	 * @return 모든 주소록
	 */
	List<AddressDTO> readAddressList(String username);
	
	/**
	 * 해당 회원의 특정 주소록 수정서비스
	 * @param address
	 * @return 수정된 행의 성공여부
	 */
	boolean modifyAddress(AddressDTO addressDto);
	
	/**
	 * 해당 회원의 특정 주소록 삭제서비스
	 * @param address
	 * @return 삭제된 행의 성공여부
	 */
	boolean removeAddress(AddressDTO addressDto);
}
