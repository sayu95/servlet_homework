package kr.or.ddit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import kr.or.ddit.dto.AddressDTO;
import kr.or.ddit.mybatis.MapperProxyFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class AddressMapperTest {
	AddressMapper mapper = MapperProxyFactory.generataProxy(AddressMapper.class);

	@Test
	@Disabled
	void testInsertAddress() {
		// given, //when
		int result = mapper.insertAddress(AddressDTO.builder()
				.memId("a001")
				.adrsName("자취")
				.adrsTel("041-000-0000")
				.adrsAdd("대전광역시 월평동")
				.adrsMail("우편주소").build());
		// when
		assertThat(result);

		// then
		assertThat(result).as("데이터 삽입 성공 시 결과값은 1이어야 합니다.").isEqualTo(1);
	}
 
	@Test
	@Disabled
	void testSelectAddress() {

		// given
		String adrsName  = "자취";
		String memId = "a001";
		
		// when
		List<AddressDTO> list = mapper.selectAddress(AddressDTO.builder()
	            .adrsName(adrsName)
	            .memId(memId)
	            .build());

		// then
		assertThat(list)
        .isNotNull()
        .allSatisfy(address -> {
            // 모든 결과가 검색한 사용자의 데이터인지 확인
            assertThat(address.getMemId()).isEqualTo(memId);
            // 이름에 검색어가 포함되어 있는지 확인
            assertThat(address.getAdrsName()).contains(adrsName);
        });

		log.info("AddressMapperTest 조회 결과 확인");
		log.info("조회된 총 데이터 개수: {}건", list.size());
		log.info("조회된 총 데이터 개수: {}", list);
	}

	@Test
	@Disabled
	void testSelectAllAddress() {
		// given
		String memId = "a001";
		
		// when
		List<AddressDTO> addresses = mapper.selectAllAddress(memId);

		// then
		assertThat(addresses).allSatisfy(address -> {
	        assertThat(address.getMemId()).isEqualTo(memId);
	    });

	    log.info("selectAllAddress 실행 완료. 조회된 개수: {}", addresses.size());
	}

	@Test
	@Disabled
	void testUpdateAddress() {
		int targetNo = 2;
		
		// given, when
		int result = mapper.updateAddress(AddressDTO.builder()
				.adrsNo(targetNo)
				.memId("a001")
				.adrsName("자취방")
				.adrsTel("010-9999-8888") 
				.adrsAdd("대전광역시 월평동") 
				.adrsMail("1111") 
				.build());

		// then
		assertThat(result).isGreaterThanOrEqualTo(1);
		log.info("수정 완료된 행 개수: {}", result);
	}

	@Test
	@Disabled
	void testDeleteAddress() {
		//given //when
		int result = mapper.deleteAddress(AddressDTO.builder()
				.adrsNo(13)
				.memId("a001")
				.build());
		
		// then
		assertThat(result).isGreaterThanOrEqualTo(1);
		log.info("삭제 완료된 행 개수: {}", result);
	}

}
