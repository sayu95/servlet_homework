package kr.or.ddit.homework07.exception;

import java.time.LocalDateTime;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ExceptionDTO {
	//여기도 Jsonfomat으로 바로 바꿀수 있는데 스프링쓰면 내가 직접 json변환기를 만들어야해서
	//String으로 변환
    private final String timestamp;
    private final String exceptionName;
    private final String message;
    private final String uri;

 // 생성자가 직접 재료를 손질하게 함
    public ExceptionDTO(Exception e, HttpServletRequest req) {
    	this.timestamp = LocalDateTime.now().toString();
        this.exceptionName = e.getClass().getSimpleName(); // 예외 이름 자동 추출
        this.message = e.getMessage();                    // 메시지 자동 추출
        this.uri = req.getRequestURI();                  // 경로 자동 추출
    }
}