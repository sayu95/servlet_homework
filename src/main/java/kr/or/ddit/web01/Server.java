package kr.or.ddit.web01;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/01/server-now")
public class Server extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	      Date now = new Date();
	      
	      //키 "now" : value now
	      Map<String, Date> nativeDate = Map.of("now", now);
	      String json = new Gson().toJson(nativeDate);
	      String mime = "application/json;charset=UTF-8";
	      resp.setContentType(mime);
	      resp.getWriter().print(json);
	}
}
