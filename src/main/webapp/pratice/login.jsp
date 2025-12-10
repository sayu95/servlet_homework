<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="<c:url value='/login'/>" method="get">
		아이디 : <input type="text" name="id"> 
		비밀번호 : <input type="password" name="password">
		<button id="btn">전송</button>
	</form>
	<c:if test="${id eq 'admin'}">
	${id }
	</c:if>
	<c:if test="${id ne 'admin'}">
	${message }
	</c:if>
	<div id="context"></div>
</body>
</html>