<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h4>서버의 현재 시간 : <span id="server-area"></span></h4>
<label><input type="radio" name="accept" checked value="text/html" data-fn-name="renderHtml" />HTML</label>
<label><input type="radio" name="accept" value="application/json" data-fn-name="renderJson" />JSON</label>
    <select id="localeSelector" name="selectedLocaleCode">
    <option value="">언어/지역 선택 (국가 포함) --</option>
    <c:forEach var="locale" items="${localeDataList}">
        <option value="${locale.code}"> 
            <c:out value="${locale.name}"/>
        </option>
    </c:forEach>
</select>
<select id="" name="timeZone"></select>
<button>서버 시간 가져오기</button>

<script src="${pageContext.request.contextPath}/resources/js/app/contentNegotiating.js"></script>
</body>
</html>