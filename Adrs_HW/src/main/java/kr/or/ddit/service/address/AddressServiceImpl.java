package kr.or.ddit.service.address;

import java.util.List;

import kr.or.ddit.dto.AddressDTO;
import kr.or.ddit.mapper.AddressMapper;
import kr.or.ddit.mybatis.MapperProxyFactory;

public class AddressServiceImpl implements AddressService {
	
	//dependency
	//마이바티스가 가짜proxy(생성)하고 mapperImpl의 역할을 대신함
	//따라서 인터페이스로 구현체가 생성되고 인터페이스의 추상메서드를 바로 쓸 수 있음
	private AddressMapper mapper = MapperProxyFactory.generataProxy(AddressMapper.class);
	
	/**
	 * 주소등록
	 * 삽입된 행의 수를 성공여부 반환
	 */
	@Override
	public boolean createAddress(AddressDTO addressDto) {
		return mapper.insertAddress(addressDto) > 0;
	}

	/**
	 * 특정 주소조회
	 * 특정 주소 모두를 반환
	 */
	@Override
	public List<AddressDTO> readAddress(AddressDTO addressDto) {
		return mapper.selectAddress(addressDto);
	}

	/**
	 * 모든 주소조회
	 * 회원이 가지고 있는 모든 주소를 반환
	 */
	@Override
	public List<AddressDTO> readAddressList(String username) {
		return mapper.selectAllAddress(username);
	}

	/**
	 * 특정 주소수정
	 * 특정 주소를 수정 후 성공여부 반환
	 */
	@Override
	public boolean modifyAddress(AddressDTO addressDto) {
		return mapper.updateAddress(addressDto) > 0;
	}

	/**
	 * 특정 주소삭제
	 * 틐정 주소를 삭제 후 성공여부 반환
	 */
	@Override
	public boolean removeAddress(AddressDTO addressDto) {
		return mapper.deleteAddress(addressDto) > 0;
	}

}
