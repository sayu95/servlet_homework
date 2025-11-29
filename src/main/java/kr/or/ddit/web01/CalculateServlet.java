package kr.or.ddit.web01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calc/calculate")
public class CalculateServlet extends HttpServlet {

	// FunctionalInterface를 정의하여 람다에서 사용
	// enum에서 함수를 람다로 표현
	@FunctionalInterface
	public interface Calculation {
		double cal(double num1, double num2);
	}

	private enum Operator {
		PLUS("+", (num1, num2) -> num1 + num2),
		MINUS("-", (num1, num2) -> num1 - num2),
		MULTIPLY("*", (num1, num2) -> num1 * num2), 
		DIVIDE("/", (num1, num2) -> {
			if (num2 == 0) {
				throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
			}
			return num1 / num2;
		}),
		MOD("%", (num1, num2) -> {
			if (num2 == 0) {
				throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
			}
			return num1 % num2;
		});

		private final String symbol;
		private final Calculation calculation; // 정의한 함수형 인터페이스, 즉 익명함수 (num1, num2) ->

		// 생성자
		Operator(String symbol, Calculation calculation) {
			this.symbol = symbol;
			this.calculation = calculation;
		}

		// 실제 계산을 수행하는 메서드
		// 익명함수 구현
		public double calculate(double num1, double num2) {
			return calculation.cal(num1, num2);
		}

		// 연산자 기호로 Enum 상수를 찾는 정적 메서드
		public static Optional<Operator> fromSymbol(String symbol) {
			return Arrays.stream(values()).filter(op -> op.symbol.equals(symbol)).findFirst();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("수신완료");

	    // 상태코드 기본값 200 (성공 시)
	    int status = HttpServletResponse.SC_OK;
	    
	    //미리 요청 헤더의 컨탠츠 타입을 확인하고 응답에 대한 Content-Type을 정한다.
        //System.out.println(req.getHeader("Content-Type"));
	    
	    //응답 컨탠츠 타입은 요청 헤더의 컨탠츠 타입을 확인하고 PrintWriter를 열기 전에 미리 한다.
	    resp.setContentType("text/html;charset=UTF-8");

	    // PrintWriter를 가장 바깥쪽에서 열어 모든 로직에서 out을 사용 가능하게 합니다.
	    // why? 예외처리을 뷰에서 보여주기 위해 out이 필요함
	    try (PrintWriter out = resp.getWriter()) { 
	        
	        // getParameter는 예외를 던지지 않으므로 try문에 있을 필요가 없음
	        String left = req.getParameter("left");
	        String right = req.getParameter("right");
	        String operator = req.getParameter("operator");

	        try {
	            //파라미터 변환 및 계산 로직 (예외 발생 가능 지점)
	            double num1 = Double.parseDouble(left); // NumberFormatException 발생 가능
	            double num2 = Double.parseDouble(right); // NumberFormatException 발생 가능
	            
	            // 가져온 Operator를 Enum에서 찾기
	            Optional<Operator> operatorOpt = Operator.fromSymbol(operator);
	            
	            // Optional 컨테이너 안에 실제 값이 존재하는지 여부를 확인하여 boolean 값으로 반환하는 함수
	            if (operatorOpt.isPresent()) {
	                Operator op = operatorOpt.get();
	                
	                // 0 나누기 예외는 Enum에서 던져 아래 catch(IllegalArgumentException)로 이동
	                double result = op.calculate(num1, num2); 

	                //성공 결과 출력
	                out.printf("<p>계산식: %.2f %s %.2f</p>", num1, operator, num2);
	                out.printf("<p>결과: %.2f</p>", result);

	            } else {
	                // 연산자 미발견 처리 (Optional.empty() 일 때)
	                status = HttpServletResponse.SC_BAD_REQUEST; 
	                out.println("<p style='color:red;'> 오류 : 올바른 연산자를 입력해주세요.</p>");
	            }

	        } catch (NumberFormatException e) {
	            // 숫자 형식 오류 처리 (out 사용 가능) 뷰로 보낸다.
	            status = HttpServletResponse.SC_BAD_REQUEST;
	            out.println("<p style='color:red;'> 오류 : 숫자를 올바르게 입력해주세요.</p>"); 
	            
	        } catch (IllegalArgumentException e) {
	            // 0으로 나누기 예외 처리 (out 사용 가능) 뷰로 보낸다.
	            status = HttpServletResponse.SC_BAD_REQUEST;
	            // sendError 대신 out.println을 사용하여 테스트 용이성을 확보합니다.
	            // 에러페이지가 따로 있다면 sendError
	            out.println("<p style='color:red;'> 오류 : " + e.getMessage() + "</p>"); 
	        }
	        
	        // 3. 최종 상태 코드 설정
	        resp.setStatus(status);
	    }
	}
}

/*
 ************************************************ 기본 연산 ******************************************************
 * 
 * 프론트단에서 해당 응답이 내가 보내는 데이터는 text이면서 HTML이고, UTF-8로 인코딩되어 있다고 알려줌
 * resp.setContentType("text/html; charset=UTF-8");
 * 
 * 향상된 try문으로 out을 다 쓰면 자동으로 반환
 * 
 * try (PrintWriter out = resp.getWriter()) { //switch문으로 가져온 operator의 값으로 연산을
 * 함
 * 
 * switch (operator) { case "+": out.print(num1 + num2); break;
 * 
 * case "-": out.print(num1 - num2); break;
 * 
 * case "*": out.print(num1 * num2); break;
 * 
 * case "/": out.print(num1 / num2); break;
 * 
 * case "%": out.print(num1 % num2); break;
 * 
 * default: status = resp.SC_BAD_REQUEST; resp.sendError(status,
 * "요청 구문 오류: operator 파라미터를 확인하세요.");
 */
