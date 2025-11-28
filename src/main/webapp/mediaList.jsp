<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 
네임스페이스를 이용하여 ContextPath를 넘기는 방식 가장 권장하는 방식이라 하여 채용해봄 
반드시 JSP 내부에 존재해야 한다고 함 -->
<script>
	window.App = window.App || {};

	window.App.CONFIG = {
		contextPath : '${pageContext.request.contextPath}'
	};

	document.addEventListener('DOMContentLoaded', function() {
		const CONTEXT_PATH = App.CONFIG.contextPath;
		const SCRIPT_URL = CONTEXT_PATH + '/resources/js/app/media.js';
		const scriptElement = document.createElement('script');

		scriptElement.src = SCRIPT_URL;
		scriptElement.type = 'text/javascript';
		document.head.appendChild(scriptElement);
	});
</script>

</head>
<body>
	<select name="media">
	</select>
	<select name="data">
	</select>
	<button id="view-button">보기</button>
	<div id="content"></div>
</body>
</html>