<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
input {
	height: 2em;
	font-weight: bold;
	text-align: center;
}

.calendarContainer {
	display: flex;
	justify-content: center;
	gap: 10px;
}

.calendar-grid {
	display: grid;
	grid-template-columns: repeat(7, 1fr);
	width: 100%;
	max-width: 800px;
	border: 1px solid #ccc;
}

.day-header {
	background: #e0e0e0;
	text-align: center;
	padding: 5px;
	font-weight: bold;
	border: 1px solid #ccc;
}

.day-cell {
	background: #fff;
	border: 1px solid #eee;
	min-height: 50px;
	padding: 5px;
	position: relative;
}

.day-other-month {
	background-color: #f9f9f9;
	color: #aaa;
}
</style>
</head>
<body>
	<form action="" method="get"
		enctype="application/x-www-form-urlencoded">
		<div class="calendarContainer">
			<input type="text" name="year" placeholder="Please select the year." />

			<select name="month">
				<c:forEach var="month" items="${months}">
					<option value="${month.value }">${month.key }</option>
				</c:forEach>
			</select> <select name="locale">
				<c:forEach var="loc" items="${locales}">
					<option value="${loc.key}"
						<c:if test="${loc.key == selectedLocale.toLanguageTag()}"> selected="selected" </c:if>>
						${loc.value}</option>
				</c:forEach>
			</select> <select name="timeZone">
				<c:forEach var="timeZone" items="${timeZones}">
					<option value="${timeZone.key }">${timeZone.value }</option>
				</c:forEach>
			</select>

		</div>
	</form>

	<c:set var="data" value="${calendarData}" />
	<c:set var="offset" value="${data.startDayOfWeek - 1}" />
	<c:set var="data" value="${calendarData}" />
	<c:set var="targetMonth" value="${data.targetMonth}" />
	
	<h1>${data.headerYearMonth}</h1>
	<div class="calendar-grid">

		<c:forEach var="name" items="${data.dayNames}">
			<div class="day-header">${name}</div>
		</c:forEach>

		<c:forEach begin="1" end="${offset}" var="i">
			<div class="day-cell day-other-month"></div>
		</c:forEach>

		<c:forEach begin="1" end="${data.daysInMonth}" var="day">
			<div class="day-cell">
				<span class="day-number">${day}</span>
			</div>
		</c:forEach>

	</div>
	<script type="text/javascript"
		src='<c:url value="/resources/js/app/calender.js"></c:url>'></script>
</body>
</html>