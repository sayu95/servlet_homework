<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/login" method="post" enctype="application/x-www-form-urlencoded">
	아이디 : <input type="text" name="memId">
	비밀번호 : <input type="password" name="memPass">
	<button type="submit">로그인</button>
	</form>
</body>
</html>