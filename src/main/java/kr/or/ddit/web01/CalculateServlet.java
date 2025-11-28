package kr.or.ddit.web01;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calc/calculate")
public class CalculateServlet extends HttpServlet {

	// 연산자 enum
	//메서드나 클래스
	private enum OperatorList {

		ADD("+") {
			@Override
			public int calc(int num1, int num2) {
				return num1 + num2;
			}

		},

		SUB("-") {
			@Override
			public int calc(int num1, int num2) {
				return num1 - num2;
			}
		},

		MUL("*") {
			@Override
			public int calc(int num1, int num2) {
				return num1 * num2;
			}
		},

		DIV("/") {
			@Override
			public int calc(int num1, int num2) {
				return num1 / num2;
			}
		},

		MOD("%") {
			@Override
			public int calc(int a, int b) {
				// 0으로 나눴을때의 에외처리
				if (b == 0)
					throw new ArithmeticException("0으로 나눌 수 없습니다.");
				return a / b;
			}
		};

		public abstract int calc(int a, int b);

		private static final Map<String, OperatorList> operatorMap = new HashMap<>();

		static {
			for (OperatorList op : OperatorList.values()) {
				operatorMap.put(op.getOperator(), op);
			}
		}

		// 반환할 operator를 담는 변수
		private final String operator;

		OperatorList(String symbol) {
			this.operator = symbol;
		}

		public String getOperator() {
			return this.operator;
		}

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상태코드 기본값 200
		int status = resp.SC_OK;
		// 수신확인
		System.out.println("수신완료");

		// getParameter를 이용해 쿼리스트링의 left, right, operator 파라미터값을 getParameter함수를 이용해
		// 가져온다.
		String left = req.getParameter("left");
		String right = req.getParameter("right");
		String operator = req.getParameter("operator");

		// 가져온 String left, String right는 피연산자로 연산이 되야함으로 int로 치환
		int num1 = Integer.parseInt(left);
		int num2 = Integer.parseInt(right);

		OperatorList realOperator = null;

		System.out.println(realOperator);

		if (realOperator != null) {
			int result = realOperator.calc(num1, num2);
			resp.setContentType("text/html; charset=UTF-8");
			try (PrintWriter out = resp.getWriter()) {
				out.print(result);
			}
		} else {
			status = resp.SC_BAD_REQUEST;
			resp.sendError(status, "요청 구문 오류: operator 파라미터를 확인하세요.");
		}

		// 프론트단에서 해당 응답이 내가 보내는 데이터는 text이면서 HTML이고, UTF-8로 인코딩되어 있다고 알려줌
//		resp.setContentType("text/html; charset=UTF-8");

		// 향상된 try문으로 out을 다 쓰면 자동으로 반환
		/*
		 * try (PrintWriter out = resp.getWriter()) { //switch문으로 가져온 operator의 값으로 연산을
		 * 함 switch (operator) { case "+": out.print(num1 + num2); break;
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
	}
}
