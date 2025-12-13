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
	<form action="" method="get" enctype="application/x-www-form-urlencoded">
		<div class="calendarContainer">
			<input type="text" name="year" placeholder="Please select the year." /> 
			
			<select name="month">
				<c:forEach var="month" items="${months}">
					<option>${month.key }</option>
				</c:forEach>
			</select> 
			
			<select name="locale">
				<c:forEach var="locale" items="${locales}">
					<option value="${locale.key}">${locale.value }</option>
				</c:forEach>
			</select> 
			
			<select name="timeZone">
				<c:forEach var="timeZone" items="${timeZones}">
					<option>${timeZone.value }</option>
				</c:forEach>
			</select>
			
		</div>
	</form>
	<script type="text/javascript"
		src='<c:url value="/resources/js/app/calender.js"></c:url>'></script>
</body>
</html>