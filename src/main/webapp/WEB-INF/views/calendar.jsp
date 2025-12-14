<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Calendar View</title>
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
	margin-bottom: 20px; 
}

.calendarHeaderControls {
    display: flex;
    justify-content: center; 
    align-items: center;
    gap: 10px; 
    margin-bottom: 20px; 
}

.calendarHeaderControls h1 {
    margin: 0; 
    font-size: 1.8em;
}

.calendarHeaderControls a {
    text-decoration: none;
    color: inherit; 
}

.calendarHeaderControls button {
    cursor: pointer;
    font-size: 1.5em;
    border: none;
    background: none;
    padding: 5px 10px;
}

.calendar-grid {
	display: grid;
	grid-template-columns: repeat(7, 1fr);
	width: 100%;
	max-width: 800px;
	border: 1px solid #ccc;
	margin: 0 auto;
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

.day-cell.today {
	background-color: green;
}
</style>
</head>
<body>

	<c:set var="data" value="${calendarData}" />
<c:set var="offset" value="${data.startDayOfWeek - 1}" />
<c:set var="targetMonthValue" value="${data.monthValue}" />
<c:set var="targetYear" value="${data.year}" />

<c:set var="prevMonth" value="${targetMonthValue - 1}" />
<c:set var="prevYear" value="${targetYear}" />
<c:if test="${targetMonthValue == 1}">
    <c:set var="prevMonth" value="12" />
    <c:set var="prevYear" value="${targetYear - 1}" />
</c:if>

<c:set var="nextMonth" value="${targetMonthValue + 1}" />
<c:set var="nextYear" value="${targetYear}" />
<c:if test="${targetMonthValue == 12}">
    <c:set var="nextMonth" value="1" />
    <c:set var="nextYear" value="${targetYear + 1}" />
</c:if>

	<div class="calendarHeaderControls">
		<a
			href="?year=${prevYear}&month=${prevMonth}&locale=${selectedLocale.toLanguageTag()}&timeZone=${calendarData.zoneId.id}">
			<button type="button">◀</button>
		</a>
        
        <h1>${data.headerYearMonth}</h1> 
        
		<a
			href="?year=${nextYear}&month=${nextMonth}&locale=${selectedLocale.toLanguageTag()}&timeZone=${calendarData.zoneId.id}">
			<button type="button">▶</button>
		</a>
	</div>
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
	<c:set var="targetMonthValue" value="${data.monthValue}" />
	<c:set var="targetYear" value="${data.year}" />

	<div class="calendar-grid">

		<c:forEach var="name" items="${data.dayNames}">
			<div class="day-header">${name}</div>
		</c:forEach>

		<c:forEach begin="1" end="${offset}" var="i">
			<div class="day-cell day-other-month"></div>
		</c:forEach>

		<c:forEach begin="1" end="${data.daysInMonth}" var="day">
			<c:set var="isToday"
				value="${day eq todayDayInZone && targetMonthValue eq todayMonthInZone && targetYear eq todayYearInZone}" />

			<div class="day-cell ${isToday ? 'today' : ''}">
				<span class="day-number">${day}</span>
			</div>
		</c:forEach>

	</div>

	<script type="text/javascript"
		src='<c:url value="/resources/js/app/calender.js"></c:url>'></script>
</body>
</html>