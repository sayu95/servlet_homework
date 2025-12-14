package calendar;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
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
public class CalenderServlet extends HttpServlet {

	/**
	 * 1. 클라이언트는 년도를 선택해야한다. 2. 클라이언트는 제공되는 월을 선택해야한다. 3. 클라이언트는 제공되는 나라목록을 나라를
	 * 선택해야한다. 4. 선택된 나라로 다양한 시간대를 제공해줘야한다.
	 */

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Controller : 데이터를 받고 데이터를 검증하고 검증된 데이터를 전달한다.
		// 클라이언트가 원하는 시간
		ZoneId zoneId = Optional.ofNullable(req.getParameter("timeZone")).filter(Predicate.not(String::isBlank))
				.map(ZoneId::of).orElse(ZoneId.systemDefault());
		
		LocalDate todayInZone = LocalDate.now(zoneId); 
		int todayDayInZone = todayInZone.getDayOfMonth(); // 1~31
		int todayMonthInZone = todayInZone.getMonthValue(); // 1~12
		int todayYearInZone = todayInZone.getYear();       // 2025

		req.setAttribute("todayDayInZone", todayDayInZone);
		req.setAttribute("todayMonthInZone", todayMonthInZone);
		req.setAttribute("todayYearInZone", todayYearInZone);

		// 클라이언트가 원하는 년도
		int year = Optional.ofNullable(req.getParameter("year"))
				// 필터에 정규식을 사용해 데이터를 검증한다.
				.filter(y -> y.matches("^\\d{4}$")).map(Integer::parseInt).orElse(Year.now(zoneId).getValue());

		// 클라이언트가 원하는 월
		YearMonth month = Optional.ofNullable(req.getParameter("month")).filter(mp -> mp.matches("^[1-9]|1[0-2]$"))
				.map(Integer::parseInt).map(m -> YearMonth.of(year, m)).orElse(YearMonth.now(zoneId));

		// 클라이언트가 원하는 나라
		Locale locale = Optional.ofNullable(req.getParameter("locale")).filter(Predicate.not(String::isBlank))
				.map(Locale::forLanguageTag).orElse(req.getLocale());

		req.setAttribute("selectedLocale", locale);

		// 클라이언트가 선택할 월을 제공한다.
		Map<String, Integer> months = Arrays.stream(Month.values()).collect(Collectors.toMap(
				m -> m.getDisplayName(TextStyle.FULL, locale), m -> m.getValue(), (k1, k2) -> k1, LinkedHashMap::new));

		// 클라이언트가 선택할 나라들을 제공한다.
		Map<String, String> locales = Arrays.stream(Locale.getAvailableLocales())
				.filter(l -> !l.getDisplayName().isBlank())
				.collect(Collectors.toMap(Locale::toLanguageTag, l -> l.getDisplayName(l), (k1, k2) -> k1));

		// 클라이언트가 선택할 시간대를 제공한다.
		Map<String, String> timeZones = ZoneId.getAvailableZoneIds().stream().map(ZoneId::of).collect(
				Collectors.toMap(ZoneId::getId, z -> z.getDisplayName(TextStyle.FULL, locale), (k1, k2) -> k1));

		// dto로 값들을 객체화 dto로 묶어 req.setAttribute

		CalenderDTO cdto = new CalenderDTO(locale, zoneId, month);

		int firstDayOfWeekValue = month.atDay(1).getDayOfWeek().getValue();
		cdto.setStartDayOfWeek(firstDayOfWeekValue);

		cdto.setDaysInMonth(month.lengthOfMonth());

		List<String> dayNames = Arrays.stream(java.time.DayOfWeek.values())
				.map(day -> day.getDisplayName(java.time.format.TextStyle.SHORT, locale)).collect(Collectors.toList());

		cdto.setDayNames(dayNames);
		
		DateTimeFormatter formatter = DateTimeFormatter
		        .ofPattern("yyyy MMMM", locale); 
		        
		String formattedHeader = month.format(formatter);
		cdto.setHeaderYearMonth(formattedHeader);

		// ------------------------------------

		req.setAttribute("calendarData", cdto);

		req.setAttribute("ctdo", cdto);
		req.setAttribute("months", months);
		req.setAttribute("locales", locales);
		req.setAttribute("timeZones", timeZones);

		System.out.println(zoneId);
		System.out.println(year);
		System.out.println(month);
		System.out.println(locale);

		String view = "/WEB-INF/views/calendar.jsp";
		req.getRequestDispatcher(view).forward(req, resp);
	}
}
