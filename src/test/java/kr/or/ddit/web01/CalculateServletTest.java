package kr.or.ddit.web01;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class CalculateServletTest {

	// 덧셈 테스트코드
	@Test
	void PLUS() throws ServletException, IOException {
		// 가짜 요청과 가짜응답을 만드는 Mockito로 테스트를 진행한다고 한다.
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

		// 응답 출력
		// 서블릿이 출력하는 모든 문자열을 메모리에 임시로 저장하는 버퍼 역할
		StringWriter stringWriter = new StringWriter();

		// PrintWriter는 생성자 인수로 전달된 stringWriter에 연결
		PrintWriter writer = new PrintWriter(stringWriter);

		// 우리가 만든 가짜 writer 객체를 반환
		when(response.getWriter()).thenReturn(writer);

		// left 파라미터 설정
		when(request.getParameter("left")).thenReturn("10");

		// right 파라미터 설정
		when(request.getParameter("right")).thenReturn("5");

		// operator 파라미터 설정
		when(request.getParameter("operator")).thenReturn("+");

		// 서블릿 객체 생성
		CalculateServlet servlet = new CalculateServlet();

		// 서블릿으로 doget함수 호출
		servlet.doGet(request, response);

		writer.flush(); // 출력 내용 확정

		// 응답값 output에 담기
		String output = stringWriter.toString();

		// 검증
		assertTrue(output.contains("15.00"), "덧셈 결과(15.00)가 응답 본문에 포함되어야 합니다.");

		// Mock 객체인 response가 setStatus라는 메서드를 호출했는지,
		// 그리고 호출했다면 그 인수로 HttpServletResponse.SC_OK (HTTP 200 상태 코드)를 사용했는지 확인
		// 검증
		Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
	}

	// 뺄셈 테스트코드
	@Test
	void MINUS() throws ServletException, IOException {
		// 가짜 요청과 가짜응답을 만드는 Mockito로 테스트를 진행한다고 한다.
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

		// 응답 출력
		// 서블릿이 출력하는 모든 문자열을 메모리에 임시로 저장하는 버퍼 역할
		StringWriter stringWriter = new StringWriter();

		// PrintWriter는 생성자 인수로 전달된 stringWriter에 연결
		PrintWriter writer = new PrintWriter(stringWriter);

		// 우리가 만든 가짜 writer 객체를 반환
		when(response.getWriter()).thenReturn(writer);

		// left 파라미터 설정
		when(request.getParameter("left")).thenReturn("10");

		// right 파라미터 설정
		when(request.getParameter("right")).thenReturn("5");

		// operator 파라미터 설정
		when(request.getParameter("operator")).thenReturn("-");

		// 서블릿 객체 생성
		CalculateServlet servlet = new CalculateServlet();

		// 서블릿으로 doget함수 호출
		servlet.doGet(request, response);

		writer.flush(); // 출력 내용 확정

		// 응답값 output에 담기
		String output = stringWriter.toString();

		// 검증
		assertTrue(output.contains("5.00"), "뺄셈 결과(5.00)가 응답 본문에 포함되어야 합니다.");

		// Mock 객체인 response가 setStatus라는 메서드를 호출했는지,
		// 그리고 호출했다면 그 인수로 HttpServletResponse.SC_OK (HTTP 200 상태 코드)를 사용했는지 확인
		// 검증
		Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
	}

	// 곱셈 테스트코드
	@Test
	void MULTIPLY() throws ServletException, IOException {
		// 가짜 요청과 가짜응답을 만드는 Mockito로 테스트를 진행한다고 한다.
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

		// 응답 출력
		// 서블릿이 출력하는 모든 문자열을 메모리에 임시로 저장하는 버퍼 역할
		StringWriter stringWriter = new StringWriter();

		// PrintWriter는 생성자 인수로 전달된 stringWriter에 연결
		PrintWriter writer = new PrintWriter(stringWriter);

		// 우리가 만든 가짜 writer 객체를 반환
		when(response.getWriter()).thenReturn(writer);

		// left 파라미터 설정
		when(request.getParameter("left")).thenReturn("10");

		// right 파라미터 설정
		when(request.getParameter("right")).thenReturn("5");

		// operator 파라미터 설정
		when(request.getParameter("operator")).thenReturn("*");

		// 서블릿 객체 생성
		CalculateServlet servlet = new CalculateServlet();

		// 서블릿으로 doget함수 호출
		servlet.doGet(request, response);

		writer.flush(); // 출력 내용 확정

		// 응답값 output에 담기
		String output = stringWriter.toString();

		// 검증
		assertTrue(output.contains("50.00"), "곱셈 결과(50.00)가 응답 본문에 포함되어야 합니다.");

		// Mock 객체인 response가 setStatus라는 메서드를 호출했는지,
		// 그리고 호출했다면 그 인수로 HttpServletResponse.SC_OK (HTTP 200 상태 코드)를 사용했는지 확인
		// 검증
		Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
	}

	// 나누기 테스트코드
	@Test
	void DIVIDE() throws ServletException, IOException {
		// 가짜 요청과 가짜응답을 만드는 Mockito로 테스트를 진행한다고 한다.
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

		// 응답 출력
		// 서블릿이 출력하는 모든 문자열을 메모리에 임시로 저장하는 버퍼 역할
		StringWriter stringWriter = new StringWriter();

		// PrintWriter는 생성자 인수로 전달된 stringWriter에 연결
		PrintWriter writer = new PrintWriter(stringWriter);

		// 우리가 만든 가짜 writer 객체를 반환
		when(response.getWriter()).thenReturn(writer);

		// left 파라미터 설정
		when(request.getParameter("left")).thenReturn("10");

		// right 파라미터 설정
		when(request.getParameter("right")).thenReturn("5");

		// operator 파라미터 설정
		when(request.getParameter("operator")).thenReturn("/");

		// 서블릿 객체 생성
		CalculateServlet servlet = new CalculateServlet();

		// 서블릿으로 doget함수 호출
		servlet.doGet(request, response);

		writer.flush(); // 출력 내용 확정

		// 응답값 output에 담기
		String output = stringWriter.toString();

		// 검증
		assertTrue(output.contains("2.00"), "나누기 결과(2.00)가 응답 본문에 포함되어야 합니다.");

		// Mock 객체인 response가 setStatus라는 메서드를 호출했는지,
		// 그리고 호출했다면 그 인수로 HttpServletResponse.SC_OK (HTTP 200 상태 코드)를 사용했는지 확인
		// 검증
		Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
	}

	// 나누기 테스트코드
		@Test
		void MOD() throws ServletException, IOException {
			// 가짜 요청과 가짜응답을 만드는 Mockito로 테스트를 진행한다고 한다.
			HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse response = Mockito.mock(HttpServletResponse.class);

			// 응답 출력
			// 서블릿이 출력하는 모든 문자열을 메모리에 임시로 저장하는 버퍼 역할
			StringWriter stringWriter = new StringWriter();

			// PrintWriter는 생성자 인수로 전달된 stringWriter에 연결
			PrintWriter writer = new PrintWriter(stringWriter);

			// 우리가 만든 가짜 writer 객체를 반환
			when(response.getWriter()).thenReturn(writer);

			// left 파라미터 설정
			when(request.getParameter("left")).thenReturn("10");

			// right 파라미터 설정
			when(request.getParameter("right")).thenReturn("5");

			// operator 파라미터 설정
			when(request.getParameter("operator")).thenReturn("%");

			// 서블릿 객체 생성
			CalculateServlet servlet = new CalculateServlet();

			// 서블릿으로 doget함수 호출
			servlet.doGet(request, response);

			writer.flush(); // 출력 내용 확정

			// 응답값 output에 담기
			String output = stringWriter.toString();

			// 검증
			assertTrue(output.contains("0.00"), "나머지 결과(0.00)가 응답 본문에 포함되어야 합니다.");

			// Mock 객체인 response가 setStatus라는 메서드를 호출했는지,
			// 그리고 호출했다면 그 인수로 HttpServletResponse.SC_OK (HTTP 200 상태 코드)를 사용했는지 확인
			// 검증
			Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);
		}

		@Test
		void DivideByZero() throws ServletException, IOException {
		    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		    StringWriter stringWriter = new StringWriter();
		    PrintWriter writer = new PrintWriter(stringWriter);

		    when(response.getWriter()).thenReturn(writer);

		    when(request.getParameter("left")).thenReturn("10");
		    when(request.getParameter("right")).thenReturn("0");
		    when(request.getParameter("operator")).thenReturn("/"); // 또는 "%"

		    CalculateServlet servlet = new CalculateServlet();
		    servlet.doGet(request, response);

		    writer.flush();
		    String output = stringWriter.toString();

		    // 검증 1: 0 나누기 오류 메시지가 포함되었는지 확인
		    assertTrue(output.contains("0으로 나눌 수 없습니다."),
		               "응답 본문에 0 나누기 오류 메시지가 포함되어야 합니다.");

		    // 검증 2: HTTP 상태 코드가 400 BAD REQUEST인지 확인
		    // NOTE: servlet 코드에서 resp.setStatus(status);를 호출했으므로 setStatus를 검증
		    Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		@Test
		void InvalidOperator() throws ServletException, IOException {
		    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		    StringWriter stringWriter = new StringWriter();
		    PrintWriter writer = new PrintWriter(stringWriter);

		    when(response.getWriter()).thenReturn(writer);

		    when(request.getParameter("left")).thenReturn("10");
		    when(request.getParameter("right")).thenReturn("5");
		    when(request.getParameter("operator")).thenReturn("#"); // ⬅️ 유효하지 않은 연산자 입력

		    CalculateServlet servlet = new CalculateServlet();
		    servlet.doGet(request, response);

		    writer.flush();
		    String output = stringWriter.toString();

		    // 검증 1: 연산자 오류 메시지 확인
		    // (서블릿 코드에 따라 메시지를 "올바른 연산자를 입력해주세요."로 맞추세요)
		    assertTrue(output.contains("올바른 연산자를 입력해주세요."),
		               "응답 본문에 올바른 연산자 요청 오류 메시지가 포함되어야 합니다.");

		    // 검증 2: HTTP 상태 코드가 400 BAD REQUEST인지 확인
		    Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		@Test
		void InvalidNumberFormat() throws ServletException, IOException {
		    HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		    HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		    StringWriter stringWriter = new StringWriter();
		    PrintWriter writer = new PrintWriter(stringWriter);

		    when(response.getWriter()).thenReturn(writer);

		    // ⬅️ left 파라미터에 숫자가 아닌 문자열 입력
		    when(request.getParameter("left")).thenReturn("abc");
		    when(request.getParameter("right")).thenReturn("5");
		    when(request.getParameter("operator")).thenReturn("+");

		    CalculateServlet servlet = new CalculateServlet();
		    servlet.doGet(request, response);

		    writer.flush();
		    String output = stringWriter.toString();

		    // 검증 1: 숫자 형식 오류 메시지 확인
		    assertTrue(output.contains("숫자를 올바르게 입력해주세요."),
		               "응답 본문에 숫자 형식 오류 메시지가 포함되어야 합니다.");

		    // 검증 2: HTTP 상태 코드가 400 BAD REQUEST인지 확인
		    Mockito.verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
}
