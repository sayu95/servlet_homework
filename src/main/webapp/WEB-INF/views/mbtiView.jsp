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
	<form action="<c:url value='/mbti/detail'/>" method="get">
		<select name="mbti">
			<c:forEach var="entry" items="${mbtiMap }">
				<option value="${entry.key }">${entry.key.toUpperCase() }</option>
			</c:forEach>
		</select>
		<button type="submit" id="transfer">전송</button>
	</form>
	<form method="post" enctype="multipart/form-data"
	    data-context-path="<c:url value='/mbti'/>">
		<input type="text" name="mtType" placeholder="entj" />
		<input type="file" name="mtFile" placeholder="entj.html"/>
		<button type="submit" id="upload">업로드</button>
	</form>
	<div id="content"></div>
	<script type="text/javascript" src='<c:url value="/resources/js/app/mbti.js"/>'></script>
</body>
</html>