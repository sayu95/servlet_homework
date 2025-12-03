package kr.or.ddit.web01;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/world-now")
public class ServerNowServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String acceptLanguageHeader = req.getHeader("Accept-Language");
		System.out.println(acceptLanguageHeader);
		
		Locale[] allLocalesArray = Locale.getAvailableLocales();
		List<Locale> localeList = Arrays.stream(allLocalesArray)
				.filter(locale -> !locale.getDisplayCountry().isEmpty())
				.collect(Collectors.toList());

		Set<String> allZoneIds = ZoneId.getAvailableZoneIds();
		List<String> zoneIdList = allZoneIds.stream()
				.collect(Collectors.toList());

		req.setAttribute("localeList", localeList);
		req.setAttribute("zoneIdList", zoneIdList);

		String viewPath = "/WEB-INF/views/contentNegotiating.jsp";
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("수신완료");
	}
}