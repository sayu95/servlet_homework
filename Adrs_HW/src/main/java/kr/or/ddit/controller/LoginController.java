package kr.or.ddit.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.dto.MemberDTO;
import kr.or.ddit.filter.auth.MemberDTOWrapper;
import kr.or.ddit.service.authenticate.AuthenticateService;
import kr.or.ddit.service.authenticate.AuthenticateServiceImpl;
import kr.or.ddit.service.authenticate.BadCrendentialException;
import kr.or.ddit.service.exception.UserNotFoundException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    
    // 이전에 만든 서비스 클래스 주입
    private AuthenticateService service = new AuthenticateServiceImpl();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String memId = req.getParameter("memId");
        String memPass = req.getParameter("memPass");
        
        try {
        	
            MemberDTO authenticatedMember = service.authenticate(memId, memPass);
            MemberDTOWrapper principal = new MemberDTOWrapper(authenticatedMember);
            req.getSession().setAttribute("authMember", principal);
            resp.sendRedirect(req.getContextPath() + "/address");

        } catch (Exception e) {
        	
            System.out.println(">>> 인증 실패 사유: " + e.getMessage());
            req.setAttribute("message", e.getMessage());
            req.getRequestDispatcher("/").forward(req, resp);
            
        }
    }
}