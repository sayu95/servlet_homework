package controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/mbti/detail")
public class MbtiDetailServlet extends HttpServlet {
	private ServletContext application;
	private Map<String, String> mbtiMap;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		application = getServletContext();
		mbtiMap = (Map) application.getAttribute("mbtiMap");
	}

	/**
	 * 요청에 대한 검증 메소드
	 * 
	 * @param mbti 검증 대상 파라미터
	 * @param req
	 * @return 200: OK, 404 : 실패
	 */

	private int validate(String mbti, HttpServletRequest req) {
		int status = 200;

		if (!mbtiMap.containsKey(mbti))
			status = 404;
		return status;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mbtiURI = (String) application.getAttribute("mbtiURI");
		String mbti = Optional.ofNullable(req.getParameter("mbti"))
				.filter(Predicate.not(String::isEmpty))
				.orElse("UNKNOWN");

		int status = validate(mbti, req);

		String filename = mbtiMap.get(mbti);
		String view = mbtiURI + filename;

		if (status != 200)
			resp.sendError(status);
		else
			req.getRequestDispatcher(view).forward(req, resp);
	}
}
