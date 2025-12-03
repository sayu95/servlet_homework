<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
</head>
<body>
	<h4>
		서버의 현재 시간 : <span id="server-area"></span>
	</h4>
	<label><input type="radio" name="accept" checked
		value="text/html" data-fn-name="renderHtml" />HTML</label>
	<label><input type="radio" name="accept"
		value="application/json" data-fn-name="renderJson" />JSON</label>
	<select>
		<option value="">🌎 언어/지역 선택 (본인 언어로) --</option>

		<c:forEach var="locale" items="${requestScope.localeList}">
			<c:set var="country" value="${locale.getDisplayCountry(locale)}" />

			<option value="${locale.toLanguageTag()}">
				<c:out value="${country}" />
			</option>
		</c:forEach>
	</select>
	<select id="timeZoneSelector" name="timeZone">
		<option value="">⏱️ 타임존 선택 --</option>
		<c:forEach var="zoneId" items="${requestScope.zoneIdList}">
			<option value="${zoneId}">
				<c:out value="${zoneId}" />
			</option>
		</c:forEach>
	</select>
	<button>서버 시간 가져오기</button>

	<script
		src="${pageContext.request.contextPath}/resources/js/app/contentNegotiating.js"></script>
</body>
</html>