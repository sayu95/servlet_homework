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
	<form action="<c:url value='/login'/>" method="post">
		아이디 : <input type="text" name="id"> 
		비밀번호 : <input type="password" name="password">
		<button id="btn">전송</button>
	</form>
	<div id="content"></div>
	<script type="text/javascript" src="<c:url value='/resources/js/practice/login.js'/>"></script>
</body>
</html>