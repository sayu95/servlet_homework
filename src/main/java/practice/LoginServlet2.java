package practice;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login2")
public class LoginServlet2 extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><meta charset=\"UTF-8\"><title>로그인 결과</title></head>");
        out.println("<body>");
        
        boolean loginSuccess = "admin".equals(id) && "1234".equals(password);
        String message = "";
        
        out.println("<form action=\"" + req.getContextPath() + "/login2" + "\" method=\"get\">");
        out.println("아이디 : <input type=\"text\" name=\"id\" value=\"" + (id != null ? id : "") + "\">");
        out.println("비밀번호 : <input type=\"password\" name=\"password\">");
        out.println("<button>전송</button>");
        out.println("</form>");
        
        if (loginSuccess) {
            message = id;
            
        } else {
            if (id != null) {
                message = "로그인 실패!";
            }
        }
        
        if (!message.isEmpty()) {
            out.println(message);
        }
        
        out.println("</body>");
        out.println("</html>");
        
        out.close();
    }
}