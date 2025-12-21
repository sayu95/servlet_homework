package kr.or.ddit.homework07.mbti.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kr.or.ddit.homework07.mbti.dto.MbtiDTO;
/**
 *  MBTI CRUD TEST
 *  
 *  MbtiMapper 인터페이스 안의 정의된 메서드들이 제대로 수행되고 있는지 테스트
 * 
 * Junit과 AssertJ 라이브러리를 쓰면 확장성 좋은 테스트가 가능해짐
 * 메서드 체이닝 방식으로 검증을 하기 때문에 가독성이 좋음.
 * 
 * 테스트에는 GWT 패턴을 지키는것이 좋음
 * Given (준비) : "이런 상황이 주어졌을 때..."
 * When (실행) : "이것을 실행하면..."
 * Then (검증) : "이런 결과가 나와야 한다!"
 */
class MbtiMapperTest {
	
	private static SqlSessionFactory factory;
	
	@BeforeAll
    static void setUp() {
        // [Given] 테스트 환경 준비: 설정 파일을 읽어 factory를 빌드함
        try {
            String resource = "kr/or/ddit/mybatis/Configuration.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            factory = new SqlSessionFactoryBuilder().build(reader);
        } catch (Exception e) {
            e.printStackTrace();
            // 초기화 실패 시 이후 모든 테스트가 의미 없으므로 여기서 중단됨
        }
    }

	@Test
	@DisplayName("MBTI SelectAll : 데이터가 존재하고 정확한지 확인")
	void testmbtiSelectAll() {
	    // [Given] 준비: 세션을 열고 매퍼 인터페이스를 가져옵니다.
	    try (SqlSession session = factory.openSession()) {
	    	
	    	//정말 중요한개념
	    	//Mapper는 Mapper.class를 받아 가짜 프록시 객체를 생성한다
	    	//따라서 해당 mapper 객체가 모든 DAO의 일을 대신 위임해 해당 일을 처리
	    	//XML에 정의된 SQL(mbtiSelectOne)을 대신 실행(위임)함
	    	//1. Dao는 Sqlsession으로 데이터베이스의 접근하는 객체
	    	//2. Sqlssession에 정의된 메소드로 sql를 처리하고 그 결과를 반환하는 객체이다.
	        MbtiMapper mapper = session.getMapper(MbtiMapper.class);

	        // [When] 실행: 전체 목록 조회 메서드를 호출합니다.
	        List<MbtiDTO> list = mapper.mbtiSelectAll();

	        // [Then] 검증: AssertJ를 사용하여 결과를 확인합니다.
	        assertThat(list)
	            .as("MBTI는 null이 아니어야 합니다.")
	            .isNotNull()
	            .as("DB에 최소한 1개 이상의 MBTI 데이터가 있어야 합니다.")
	            .isNotEmpty()
	            .as("리스트 내의 모든 항목이 유효한 MBTI 타입을 가지고 있어야 합니다.")
	            .allSatisfy(dto -> {
	                assertThat(dto.getMtType()).isNotBlank();
	                assertThat(dto.getMtTitle()).isNotBlank();
	            });
	        System.out.println("조회된 데이터 개수: " + list.size());
	    }
	}

	@Test
	@DisplayName("MBTI SelectOne : 데이터가 존재하고 정확한지 확인")
	void testmbtiSelectOne() {
		//[Given] 준비
		try (SqlSession session = factory.openSession()) {
			MbtiMapper mapper = session.getMapper(MbtiMapper.class);
			
			//[When] 실행
			MbtiDTO mbti = mapper.mbtiSelectOne("isfp");
			
			//[Then] 검증
			assertThat(mbti)
				.as("해당 mbti는 존재해야합니다.")
				.isNotNull()
				.as("해당 mbti는 mtTitle 데이터가 있어야합니다.")
				.hasFieldOrProperty("mtTitle")
				.as("해당 mbti는 mtContent 데이터가 있어야합니다.")
				.hasFieldOrProperty("mtContent");
			
			System.out.println("해당 mbit의 tilte : " + mbti.getMtTitle());
			System.out.println("해당 mbit의 Content : " + mbti.getMtContent());
		} 
	}
	
	@Test
	@DisplayName("MBTI Create : 요청된 MBTI가 DB에 제대로 들어가는지 확인")
	void testmbtiCreate() {
		//[Given] 준비
		try (SqlSession session = factory.openSession()) {
			MbtiMapper mapper = session.getMapper(MbtiMapper.class);
			MbtiDTO dto = new MbtiDTO();
			
			String text = """
				    정신산만함, 생각 많음, 고집 셈.
				    일머리 있음.
				    흥미있고 관심있는건 열정적 그러나 관심 없는건 별로 알고 싶어하지도 않음.
				    매번 나서서 무얼 하진 않는데 아무도 안나서면 답답해서 나서는 스타일.
				    낯을 좀 가리는데 풀리면 금방 친해지고 말도 많아짐.
				    얘기하는 거 좋아함.
				    무언가에 쉽게 몰두했다 쉽게 그만둠.
				    남 얘기에 리액션을 잘 해줘서 고민상담 하는 애들 많음.
				    거짓말 잘 못해서 입에 발린 말 잘 못함.
				    하루에 행복한 일 하나씩 정해놓고 설레함.
				    친구들이랑 얘기하는거 좋아함.
				    새롭게 친구 사귀는 거 좋아함.
				    감정기복 심함.
				    감정 얼굴에 다 드러나는 편.
				    무계획, 즉흥적인 편.
				    내가 하고싶은거 꼭 해야함.
				    저금 잘 안함.
				    """;
			
			dto.setMtType("enfp");
			dto.setMtTitle("ENFP 스파크형");
			dto.setMtContent(text);
			
			//[When] 실행
			int result = mapper.mbtiCreate(dto);
			
			//[Then] 검증
			assertThat(result)
				.as("해당 mbti는 Insert가 되어야합니다.")
				.isEqualTo(1);
			
			System.out.println(result);
		}
	}
	
	@Test
	@DisplayName("MBTI Update : 요청된 MBTI가 새로운 값으로 DB에 제대로 들어가는지 확인")
	void testmbtiUpdate() {
		//[Given] 준비
		try (SqlSession session = factory.openSession()) {
			MbtiMapper mapper = session.getMapper(MbtiMapper.class);
			
			MbtiDTO dto = new MbtiDTO();
			
			String text = """
				    정신산만함, 생각 많음, 고집 셈.
				    일머리 있음.
				    흥미있고 관심있는건 열정적 그러나 관심 없는건 별로 알고 싶어하지도 않음.
				    매번 나서서 무얼 하진 않는데 아무도 안나서면 답답해서 나서는 스타일.
				    낯을 좀 가리는데 풀리면 금방 친해지고 말도 많아짐.
				    얘기하는 거 좋아함.
				    무언가에 쉽게 몰두했다 쉽게 그만둠.
				    남 얘기에 리액션을 잘 해줘서 고민상담 하는 애들 많음.
				    거짓말 잘 못해서 입에 발린 말 잘 못함.
				    하루에 행복한 일 하나씩 정해놓고 설레함.
				    친구들이랑 얘기하는거 좋아함.
				    새롭게 친구 사귀는 거 좋아함.
				    감정기복 심함.
				    감정 얼굴에 다 드러나는 편.
				    무계획, 즉흥적인 편.
				    내가 하고싶은거 꼭 해야함.
				    저금 잘 안함.
				    """;
			
			dto.setMtType("isfp");
			dto.setMtTitle("ENFP 스파크형");
			dto.setMtContent(text);
			
			//[When] 실행
			int result = mapper.mbtiUpdate(dto);
			
			//[Then] 검증
			assertThat(result)
			 	.as("해당 MBTI는 null이 아니어야 합니다.")
	            .isNotNull()
				.as("해당 mbti는 Update가 되어야합니다.")
				.isEqualTo(1);
			
			System.out.println(result);
		}
	}
	
	@Test
	@DisplayName("MBTI Delete : 요청된 MBTI가 DB에서 제대로 지워지는지 확인")
	void testmbtiDelete() {
		//[Given] 준비
		try (SqlSession session = factory.openSession()) {
			
			MbtiMapper mapper = session.getMapper(MbtiMapper.class);
			
			//[When] 실행
			int result = mapper.mbtiDelete("isfp");
			
			//[Then] 검증
			assertThat(result)
			 	.as("해당 MBTI는 null이 아니어야 합니다.")
	            .isNotNull()
				.as("해당 mbti는 Delete가 되어야합니다.")
				.isEqualTo(1);
			
			System.out.println(result);
		}
	}
	
}
