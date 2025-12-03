package kr.or.ddit.web01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class CalculateServlet2Test {
	private CalculateServlet2 servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private StringWriter stringWriter;
    private PrintWriter writer;
    private final Gson gson = new Gson();

    @BeforeEach
    void setUp() throws IOException {
        // Mock 객체 생성
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        servlet = new CalculateServlet2();

        // 응답 캡처를 위한 StringWriter 및 PrintWriter 설정
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Content-Type 헤더 기본 설정 (JSON 요청을 시뮬레이션)
        when(request.getContentType()).thenReturn("application/json");
    }

    private void mockRequestBody(String jsonBody) throws IOException {
        StringReader stringReader = new StringReader(jsonBody);
        BufferedReader bufferedReader = new BufferedReader(stringReader);
        // JSON 데이터를 읽는 req.getReader()를 Mocking
        when(request.getReader()).thenReturn(bufferedReader);
    }

    // 헬퍼 메서드: 응답 본문 JSON을 Map으로 변환
    private Map<String, String> getErrorMap(String json) {
        // GSON을 사용하여 응답 본문(JSON)을 Map으로 변환하여 오류 메시지를 쉽게 검증
        return gson.fromJson(json, Map.class);
    }

    @Test
    void PLUS() throws ServletException, IOException {
        String requestBody = "{\"left\": 10.5, \"operator\": \"+\", \"right\": 5.5}";
        mockRequestBody(requestBody);

        servlet.doPost(request, response);
        writer.flush();

        String responseJson = stringWriter.toString();
        // 응답 상태 검증 (200 OK)
        Mockito.verify(response).setStatus(HttpServletResponse.SC_OK);

        // 응답 결과값 검증
        Map<String, Object> resultDto = gson.fromJson(responseJson, Map.class);
        assertEquals(16.0, resultDto.get("result"), "덧셈 결과가 16.0이어야 합니다.");
    }

    @Test
    void MINUS() throws ServletException, IOException {
        String requestBody = "{\"left\": 20, \"operator\": \"-\", \"right\": 8.5}";
        mockRequestBody(requestBody);

        servlet.doPost(request, response);
        writer.flush();

        String responseJson = stringWriter.toString();
        Map<String, Object> resultDto = gson.fromJson(responseJson, Map.class);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(11.5, resultDto.get("result"), "뺄셈 결과가 11.5이어야 합니다.");
    }

    @Test
    void MULTIPLY() throws ServletException, IOException {
        String requestBody = "{\"left\": 7, \"operator\": \"*\", \"right\": 3}";
        mockRequestBody(requestBody);

        servlet.doPost(request, response);
        writer.flush();

        String responseJson = stringWriter.toString();
        Map<String, Object> resultDto = gson.fromJson(responseJson, Map.class);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(21.0, resultDto.get("result"), "곱셈 결과가 21.0이어야 합니다.");
    }

    @Test
    void DIVIDE() throws ServletException, IOException {
        String requestBody = "{\"left\": 10, \"operator\": \"/\", \"right\": 4}";
        mockRequestBody(requestBody);

        servlet.doPost(request, response);
        writer.flush();

        String responseJson = stringWriter.toString();
        Map<String, Object> resultDto = gson.fromJson(responseJson, Map.class);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(2.5, resultDto.get("result"), "나눗셈 결과가 2.5이어야 합니다.");
    }

    @Test
    void MOD() throws ServletException, IOException {
        String requestBody = "{\"left\": 10, \"operator\": \"%\", \"right\": 3}";
        mockRequestBody(requestBody);

        servlet.doPost(request, response);
        writer.flush();

        String responseJson = stringWriter.toString();
        Map<String, Object> resultDto = gson.fromJson(responseJson, Map.class);

        verify(response).setStatus(HttpServletResponse.SC_OK);
        assertEquals(1.0, resultDto.get("result"), "나머지 연산 결과가 1.0이어야 합니다.");
    }

    @Test
    void testNumberFormatException() throws ServletException, IOException {
        String requestBody = "{\"left\": \"ten\", \"operator\": \"+\", \"right\": 5}";
        mockRequestBody(requestBody);

        servlet.doPost(request, response);
        writer.flush();

        Map<String, String> errorMap = getErrorMap(stringWriter.toString());

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        assertEquals("숫자 형식 변환 오류", errorMap.get("error"), "오류 유형은 '숫자 형식 변환 오류'여야 합니다.");
        assertTrue(errorMap.get("message").contains("올바른 숫자를 입력하세요"), "메시지 내용이 일치해야 합니다.");
    }

    @Test
    void testInvalidOperator() throws ServletException, IOException {
        String requestBody = "{\"left\": 10, \"operator\": \"#\", \"right\": 5}";
        mockRequestBody(requestBody);

        servlet.doPost(request, response);
        writer.flush();

        Map<String, String> errorMap = getErrorMap(stringWriter.toString());

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        assertEquals("연산자 오류", errorMap.get("error"), "오류 유형은 '연산자 오류");
        assertTrue(errorMap.get("message").contains("올바른 연산자를 입력하세요"), "메시지 내용이 일치해야 합니다.");
    }

    @Test
    void testDivideByZeroError() throws ServletException, IOException {
        String requestBody = "{\"left\": 10, \"operator\": \"/\", \"right\": 0}";
        mockRequestBody(requestBody);

        servlet.doPost(request, response);
        writer.flush();

        Map<String, String> errorMap = getErrorMap(stringWriter.toString());

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        assertEquals("계산식 오류", errorMap.get("error"), "오류 유형은 '계산식 오류'여야 합니다.");
        assertTrue(errorMap.get("message").contains("0으로 나눌 수 없습니다"), "0으로 나누기 오류 메시지가 포함되어야 합니다.");
    }

    @Test
    void testModuloByZeroError() throws ServletException, IOException {
        String requestBody = "{\"left\": 10, \"operator\": \"%\", \"right\": 0}";
        mockRequestBody(requestBody);

        servlet.doPost(request, response);
        writer.flush();

        Map<String, String> errorMap = getErrorMap(stringWriter.toString());

        verify(response, atLeastOnce()).setStatus(HttpServletResponse.SC_BAD_REQUEST);

        assertEquals("계산식 오류", errorMap.get("error"), "오류 유형은 '계산식 오류'여야 합니다.");
        assertTrue(errorMap.get("message").contains("0으로 나눌 수 없습니다"), "0으로 나머지 연산 오류 메시지가 포함되어야 합니다.");
    }
}