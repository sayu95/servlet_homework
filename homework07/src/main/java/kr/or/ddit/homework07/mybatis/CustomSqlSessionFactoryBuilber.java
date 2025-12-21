package kr.or.ddit.homework07.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * CustomSqlSessionFactoryBuilber
 * 
 * *** 싱글톤 패턴 ***
 * 
 * SqlSessionFactory는 애플리케이션 실행 내내 존재
 * 어디서든 접근이 가능하게 Static으로 선언
 * sql세션을 static 블록으로 하여 해당 클래스가 메소드영역에서 호출될때 딱 한번 생성되도록 설계
 * 연결할 DB의 정보를 가진 Configuration에서 자원을 가져와 빌더한다.
 */

public class CustomSqlSessionFactoryBuilber {
	private final static SqlSessionFactory sqlSessionFactory;
	
	static {
		String resource = "kr/or/ddit/mybatis/Configuration.xml";
		try (Reader reader = Resources.getResourceAsReader(resource)) {

			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

		} catch (IOException e) {
			throw new PersistenceException(e);
		}
	}
	
	public static SqlSessionFactory getSqlsessionfactory() {
		return sqlSessionFactory;
	}
}
