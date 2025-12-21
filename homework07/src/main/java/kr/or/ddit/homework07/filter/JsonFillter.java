package kr.or.ddit.homework07.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebFilter으로 web.xml에 필터등록을 생략할 수 있다.
//스프링 시큐리티(Security) 같은 기능을 쓸 때, 
//수많은 필터들을 순서대로 배치해서 보안 검사를 하거나 로그를 남기는 작업을 어노테이션 몇 개로 처리한다.
//나중에 스프링의 인터셉터(Interceptor)나 AOP(관점 지향 프로그래밍) 개념을 이해할 수 있다.
@WebFilter("/*")
public class JsonFillter implements Filter  {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // 메서드 허용
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); // 헤더 허용
        
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            resp.setStatus(HttpServletResponse.SC_OK);
            return; // OPTIONS 요청은 여기서 끝내고 실제 서블릿으로 보내지 않음
        }

        chain.doFilter(request, response);
	}
}
