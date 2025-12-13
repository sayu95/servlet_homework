package calendar;

import java.io.IOException;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calendar")
public class YearMonthServlet extends HttpServlet {
	
	/**
	 * 1. 클라이언트는 년도를 선택해야한다. 
	 * 2. 클라이언트는 제공되는 월을 선택해야한다. 
	 * 3. 클라이언트는 제공되는 나라목록을 나라를 선택해야한다. 
	 * 4. 선택된 나라로 다양한 시간대를 제공해줘야한다.
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//클라이언트가 원하는 년도
		String year = req.getParameter("year");
		
		//클라이언트가 원하는 월
		String month = req.getParameter("month");
		
		//클라이언트가 원하는 나라
		Locale locale = Optional.ofNullable(req.getParameter("locale"))
							.filter(Predicate.not(String::isBlank))
							.map(Locale::forLanguageTag)
							.orElse(req.getLocale());
		
		//클라이언트가 원하는 시간
		String zoneId = req.getParameter("zoneId");
		
		System.out.println(year);
		System.out.println(month);
		System.out.println(locale);
		System.out.println(zoneId);
		
		// 클라이언트가 선택할 월을 제공한다.
		Map<String, Integer> months = Arrays.stream(Month.values())
				.collect(Collectors.toMap(
						m -> m.getDisplayName(TextStyle.FULL, locale),
						m -> m.maxLength(),
						(k1, k2) -> k1,
						LinkedHashMap::new
						));
			   

		// 클라이언트가 선택할 나라들을 제공한다.
		Map<String, String> locales = Arrays.stream(Locale.getAvailableLocales())
				.filter(l -> !l.getDisplayName().isBlank())
				.collect(Collectors.toMap(
						Locale::toLanguageTag, 
						l -> l.getDisplayName(l), (k1, k2) -> k1));

		// 클라이언트가 선택할 시간대를 제공한다.
		Map<String, String> timeZones = ZoneId.getAvailableZoneIds().stream()
				.map(ZoneId::of)
				.collect(Collectors.toMap(ZoneId::getId, 
						z->z.getDisplayName(TextStyle.FULL, locale), 
						(k1,k2)->k1));
		
		req.setAttribute("months", months);
		req.setAttribute("locales", locales);
		req.setAttribute("timeZones", timeZones);
		
		String view = "/WEB-INF/views/calendar.jsp";
		req.getRequestDispatcher(view).forward(req, resp);
	}
}
