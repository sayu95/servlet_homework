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
	<form action="<c:url value='/login2'/>" method="get">
		아이디 : <input type="text" name="id">
		비밀번호 : <input type="password" name="password">
		<button>전송</button>
	</form>
</body>
</html>