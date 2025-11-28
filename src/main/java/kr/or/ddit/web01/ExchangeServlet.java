package kr.or.ddit.web01;

import java.io.BufferedReader;
import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/currency/exchange")
public class ExchangeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String viewPath = "/WEB-INF/exchange.jsp";
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.setCharacterEncoding("UTF-8");
        
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        
        String amountStr = sb.toString().trim(); 
        
        try {
            
            int krwAmount = Integer.parseInt(amountStr);
            
            double exchangeRate = 1466;
            int exchangedAmount = (int)(krwAmount / exchangeRate);
            
            resp.setContentType("text/plain;charset=UTF-8");
            resp.getWriter().write(
                String.format("%d 원은 %d USD로 환전됩니다.", krwAmount, exchangedAmount)
            );
            
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효한 숫자가 아닙니다: " + amountStr);
        } catch (Exception e) {
             resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "데이터 처리 중 서버 오류가 발생했습니다.");
        }
	}
}
