<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
input {
	width: 300px; height : 150px;
	font-size: 2em;
	height: 150px;
}
</style>
</head>
<body>
<!-- form 태그를 쓰지 않고 해보기 -->
<h1> form 태그없이 하기 </h1>
	<div>
		<input id="left" type="text" placeholder="숫자를 입력하세요"/>
		<input id="operator" type="text" placeholder="연산자를 입력하세요"/> 
		<input id="right" type="text" placeholder="숫자를 입력하세요"/>
		<button id="button" onclick="send()">계산하기</button>
	</div>
	
<!-- form 태그로 해보기 -->
<h1> form 태그로 하기 </h1>

	<form action="${pageContext.request.contextPath}/calc/calculate" method="GET">
		<input name="left2" type="text" placeholder="숫자를 입력하세요"/>
		<input name="operator2" type="text" placeholder="연산자를 입력하세요"/> 
		<input name="right2" type="text" placeholder="숫자를 입력하세요"/>
		<button type="submit">계산하기</button>
	</form>
	<div id="content"></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/calculate.js"></script>
</body>
</html>