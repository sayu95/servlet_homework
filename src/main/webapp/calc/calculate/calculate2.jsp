<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.ipt {
	width: 300px;
	height: 150px;
	font-size: 2em;
	height: 150px;
}
</style>
</head>
<body>

	<h4>수신 희망 컨텐츠 형식 선택</h4>
	<label><input type="radio" name="accept" value="html" checked />HTML</label>
	<label><input type="radio" name="accept" value="json" />JSON</label>
	
	<form action="${pageContext.request.contextPath}/calc/calculate2"
		method="GET">
		<input class="ipt" name="left" type="text" placeholder="숫자를 입력하세요" />
		<input class="ipt" name="operator" type="text" placeholder="연산자를 입력하세요" /> 
		<input class="ipt" name="right" type="text" placeholder="숫자를 입력하세요" />
		<button type="submit">계산하기</button>
	</form>
	<div id="content"></div>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/app/calculate2.js"></script>
</body>
</html>