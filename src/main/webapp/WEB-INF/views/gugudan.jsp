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
	font-size: 3em;
	width: 500px;
}

button {
	font-size: 3em;
	width: 500px;
}
</style>
</head>
<body>
	<form action="<c:url value='/gugudan'/>" method="get">
		<input type="number" name="mindan" min="2" max="9" value="" placeholder="최소단을 입력하세요" /> 
		<input type="number" name="maxdan" min="2" max="9" value="" placeholder="최대단을 입력하세요" />
		<button id="btn" type="submit">=</button>
	</form>
	<div id="gugudan-result">
		<c:forEach var="i" begin="${requestScope.mindan }" end="${requestScope.maxdan}">
			<h2>${i}단</h2>
			<table>
				<c:forEach var="j" begin="1" end="9">
					<c:set var="result" value="${i * j}" />
					<tr>
						<td>${i}</td>
						<td>*</td>
						<td>${j}</td>
						<td>=</td>
						<td>${result}</td>
					</tr>
				</c:forEach>
			</table>
		</c:forEach>
	</div>
</body>
</html>