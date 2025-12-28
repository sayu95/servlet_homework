package kr.or.ddit.mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;

class CustomSqlSessionFactoryBuilberTest {
	
	CustomSqlSessionFactoryBuilber factory = new CustomSqlSessionFactoryBuilber();
	
	@Test
	void testGetSqlsessionfactory() {
		
		//given
		SqlSessionFactory sessionFactory = factory.getSqlsessionfactory();
		
		//when
		assertThat(sessionFactory)
			.as("세션 팩토리는 null이 아니어야 함")
			.isNotNull();
		
		//then
		try (SqlSession session = sessionFactory.openSession()) {
            assertThat(session).isNotNull();
            System.out.println("DB 연결 세션 오픈 성공!");
        }
	}
}
