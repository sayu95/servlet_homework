package practice;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
@MultipartConfig
public class LoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		
		String view = "/pratice/login.jsp";
		
		if(id.equals("admin") && password.equals("1234")) {
			req.setAttribute("id", id);
			req.getRequestDispatcher(view).forward(req, resp);
		} else {
			req.setAttribute("id", id);
			req.setAttribute("message", "로그인 실패!");
			req.getRequestDispatcher(view).forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Post 수신완료");
		
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		
		String view = "/pratice/success.jsp";
		
		if(id.equals("admin") && password.equals("1234")) {
			req.setAttribute("id", id);
			req.getRequestDispatcher(view).forward(req, resp);
		}
	}
}
