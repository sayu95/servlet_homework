package kr.or.ddit.web01;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/gugudan")
public class GugudanServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("수신완료");
		
		String mindanStr = req.getParameter("mindan");
		String maxdanStr = req.getParameter("maxdan");
		
		int mindan = 0;
		int maxdan = 0;
		int tmp;
		
		if ((mindanStr == null || mindanStr.isEmpty()) && (maxdanStr == null || maxdanStr.isEmpty())) {
			mindan = 2; // 기본값
			maxdan = 9; // 기본값
	    } else {
	    	mindan = Integer.parseInt(mindanStr);
	    	maxdan = Integer.parseInt(maxdanStr);
	    }
		
		if(mindan > maxdan) {
			tmp = mindan;
			mindan = maxdan;
			maxdan = tmp;
		}
		
		System.out.println(mindan);
		System.out.println(maxdan);
		req.setAttribute("mindan", mindan);
		req.setAttribute("maxdan", maxdan);
		
		String view = "WEB-INF/views/gugudan.jsp";
		req.getRequestDispatcher(view).forward(req, resp);
	}
}