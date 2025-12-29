package kr.or.ddit.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class CustomSqlSessionFactoryBuilber {
	private final static SqlSessionFactory SQLSESSIONFACTORY;
	
	static {
		String resource = "kr/or/ddit/mybatis/Configuration.xml";
		try (Reader reader = Resources.getResourceAsReader(resource)) {

			SQLSESSIONFACTORY = new SqlSessionFactoryBuilder().build(reader);

		} catch (IOException e) {
			throw new PersistenceException(e);
		}
	}
	
	public static SqlSessionFactory getSqlsessionfactory() {
		return SQLSESSIONFACTORY;
	}
}
