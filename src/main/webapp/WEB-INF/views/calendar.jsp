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
</style>
</head>
<body>
	<h1>캘린더</h1>
	<div class="calendarContainer">
		<input type="text" name="year" placeholder="년도를 입력하세요" /> 
		<select>
			<c:forEach var="month" items="${monthMap}">
				<option>${month.key }</option>
			</c:forEach>
		</select>
		<select>
			<c:forEach var="month" items="${monthMap}">
				<option>${month.key }</option>
			</c:forEach>
		</select>
		<select>
			<c:forEach var="month" items="${monthMap}">
				<option>${month.key }</option>
			</c:forEach>
		</select>
	</div>
	<script type="text/javascript" src='<c:url value="/resources/js/app/calendar.js"/>'></script>
</body>
</html>