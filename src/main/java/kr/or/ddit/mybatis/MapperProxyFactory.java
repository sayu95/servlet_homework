package kr.or.ddit.mybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class MapperProxyFactory {

	private static SqlSessionFactory factory = CustomSqlSessionFactoryBuilber.getSqlsessionfactory();

	public static <T> T generataProxy(Class<T> mapperType) {
		// Mapper의 특정 메소드에 대해
		// 반복되는 보일러플레이트 코드를 해결하기 위한 템플릿을 갖고있음.
		InvocationHandler invocationHandler = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				try (SqlSession sqlSession = factory.openSession();) {
					T mapperProxy = sqlSession.getMapper(mapperType);
					// saved = mapper.selectMember(username);
					Object result = method.invoke(mapperProxy, args);
					sqlSession.commit();
					return result;
				}
			}
		};
		
		// 메퍼 인터페이스의 동적 프록시 객체 반환
		// proxy : 원본 객체의 동작을 대리할 수 있는 대리 객체
		return (T) Proxy.newProxyInstance(mapperType.getClassLoader(), new Class<?>[] { mapperType }, invocationHandler);
	}
}
