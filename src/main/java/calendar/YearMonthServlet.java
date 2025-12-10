package calendar;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calendar")
public class YearMonthServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("수신완료");
		
		Map<String, Integer> monthMap = new LinkedHashMap<>();
		
		String[] month = {
	            "January", "February", "March", "April", "May", "June",
	            "July", "August", "September", "October", "November", "December"
	        };
		
		for(int i=0; i<month.length; i++) {
			monthMap.put(month[i], i+1);
		}
		
		req.setAttribute("monthMap", monthMap);
		
		String view = "/WEB-INF/views/calendar.jsp";
		req.getRequestDispatcher(view).forward(req, resp);
	}
}
