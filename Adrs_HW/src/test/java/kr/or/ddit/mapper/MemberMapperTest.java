package kr.or.ddit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import kr.or.ddit.dto.MemberDTO;
import kr.or.ddit.mybatis.MapperProxyFactory;

class MemberMapperTest {
	
	MemberMapper mapper = MapperProxyFactory.generataProxy(MemberMapper.class);

	@Test
	void testSelectMemberForAuth() {
		//given
		String testId = "a001";
		
		//when
		MemberDTO member = mapper.selectMemberForAuth(testId);
		
		//then
		assertThat(member)
        .satisfies(m -> {
            assertThat(m.getMemId()).isEqualTo(testId);
            assertThat(m.getMemPass()).isNotBlank();
        });

	        // 결과 출력 (콘솔 확인용)
	        System.out.println(">>> 조회된 회원 정보: " + member);
	}

}
