package kr.or.ddit.homework07.mybatis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *  CustomSqlSessionFactoryBuilberTest
 *  제공한 Configuration 파일의 설정대로 factory가 생성되고,
 *  factory가 sqlsession을 정말 잘 생성하는지 테스트
 * 
 * Junit과 AssertJ 라이브러리를 쓰면 확장성 좋은 테스트가 가능해짐
 * 메서드 체이닝 방식으로 검증을 하기 때문에 가독성이 좋음.
 * 
 * 테스트에는 GWT 패턴을 지키는것이 좋음
 * Given (준비) : "이런 상황이 주어졌을 때..."
 * When (실행) : "이것을 실행하면..."
 * Then (검증) : "이런 결과가 나와야 한다!"
 */
class CustomSqlSessionFactoryBuilberTest {
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
    @DisplayName("MyBatis 설정 및 DB 연결 확인 테스트")
    void testGetSqlSessionFactory() {
        // [Given] factory 객체가 null이 아닌지 먼저 확인
        assertThat(factory)
            .as("MyBatis 설정 파일을 읽어 생성된 SqlSessionFactory 객체가 존재해야 합니다.")
            .isNotNull();

        // [When] 실제로 DB 세션을 오픈해 봄
        try (SqlSession session = factory.openSession()) {
            
            // [Then] 세션 및 커넥션 유효성 검증
            assertThat(session)
                .as("factory에서 오픈한 SqlSession 객체는 null이 아니어야 합니다.")
                .isNotNull();

            assertThat(session.getConnection())
                .as("SqlSession을 통한 DB 커넥션이 유효해야 합니다.")
                .isNotNull();
                
            System.out.println("DB 연결 및 세션 생성 성공!");

        } catch (Exception e) {
            fail("DB 연결 시도 중 예상치 못한 예외가 발생했습니다: " + e.getMessage());
        }
    }
}
