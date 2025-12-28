package kr.or.ddit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import kr.or.ddit.dto.AddressDTO;
import kr.or.ddit.service.address.AddressService;
import kr.or.ddit.service.address.AddressServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class AddressServiceImplTest {
	
	AddressService service = new AddressServiceImpl();
 	
	@Test
	@Disabled
	void testCreateAddress() {
		
		//given
		AddressDTO addressDto = AddressDTO.builder()
	            .memId("a001")
	            .adrsName("우리집")
	            .adrsTel("000-0000-0000")
	            .adrsAdd("충청남도 아산시")
	            .adrsMail("우편주소")
	            .build();
		//when
		boolean isSuccess = service.createAddress(addressDto);
		
		//then
		assertTrue(isSuccess);
		log.info("등록 성공 여부: {}", isSuccess);	    
	}

	@Test
	@Disabled
	void testReadAddress() {
		
		//given
		AddressDTO addressDto = AddressDTO.builder()
	            .memId("a001")
	            .adrsName("우리집")
	            .build();
		 
		//when
		List<AddressDTO> address = service.readAddress(addressDto);
		
		//then
		assertThat(address)
	   		.isNotNull()                // 리스트 객체 자체가 null이 아닌지 확인
	   		.isNotEmpty()               // 검색 결과가 최소 하나는 있는지 확인
	   		.hasSizeGreaterThan(0)      // 위와 같은 의미 (크기가 0보다 큰지)
	   		.extracting("adrsName")     // 리스트 안의 DTO들에서 adrsName 필드만 추출
	   		.contains("우리집");		// 추출한 이름들 중에 검색어 "우리집"가 포함되어 있는지 확인
		
		log.info("조회된 주소록 개수: {}개", address.size());
		log.info("조회된 주소록 리스트: {}", address);
	}

	@Test
	@Disabled
	void testReadAddressList() {
		//given
		String memId = "a001";
				
		//when
		List<AddressDTO> address = service.readAddressList(memId);
		
		//than
		assertThat(address)
	   		.isNotNull()               
	   		.isNotEmpty();               
		
		log.info("조회된 주소록 개수: {}개", address.size());
		log.info("조회된 주소록 리스트: {}", address);
	}

	@Test
	@Disabled
	void testModifyAddress() {
		//given
		AddressDTO addressDto = AddressDTO.builder()
				.adrsNo(15)
	            .memId("b001")
	            .adrsName("남의집")
	            .adrsTel("000-0000-0000")
	            .adrsAdd("서울특별시 동작구")
	            .adrsMail("우편주소")
	            .build();
		
		//when
		boolean result = service.modifyAddress(addressDto);
		
		//then
		assertThat(result)
			.as("해당된 값이 true를 반환해야한다.")
			.isTrue();
		
		log.info("행이 정말 수정됐는지 여부 {}", result);
	}

	@Test
	@Disabled
	void testRemoveAddress() {
		//given
		AddressDTO addressDto = AddressDTO.builder()
				.adrsNo(14)
	            .memId("a001")
	            .build();
		
		//when
		boolean result = service.removeAddress(addressDto);
		
		//then
		assertThat(result)
			.as("해당된 값이 true를 반환해야한다.")
			.isTrue();
		
		log.info("행이 정말 삭제됐는지 여부 {}", result);
	}
}
