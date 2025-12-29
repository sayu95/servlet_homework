<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주소록</title>
<style>
    table, th, td { border: 1px solid black; border-collapse: collapse; }
    th, td { padding: 5px; }
    .error { color: red; font-size: 0.9em; }
    .edit-mode { display: none; }
</style>
</head>
<body data-bpath="${path}">

    <h2>주소록 목록</h2>

    <c:if test="${not empty message}">
        <p style="color: blue; font-weight: bold;">알림: ${message}</p>
    </c:if>

    <form action="/address" method="get">
        <input type="text" name="searchName" placeholder="이름 검색" value="${param.searchName}">
        <button type="submit">검색</button>
        <a href="/address">초기화</a>
    </form>

    <br>

    <table>
        <thead>
            <tr>
                <th>번호</th><th>이름</th><th>전화번호</th><th>주소</th><th>이메일</th><th>관리</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty addressList}">
                    <c:forEach items="${addressList}" var="addr">
                        <tr data-adrs-no="${addr.adrsNo}">
                            <td>${addr.adrsNo}</td>
                            <td>
                                <span class="view-mode">${addr.adrsName}</span>
                                <input type="text" class="edit-mode in-name" value="${addr.adrsName}">
                            </td>
                            <td>
                                <span class="view-mode">${addr.adrsTel}</span>
                                <input type="text" class="edit-mode in-tel" value="${addr.adrsTel}">
                            </td>
                            <td>
                                <span class="view-mode">${addr.adrsAdd}</span>
                                <input type="text" class="edit-mode in-add" value="${addr.adrsAdd}">
                            </td>
                            <td>
                                <span class="view-mode">${addr.adrsMail}</span>
                                <input type="text" class="edit-mode in-mail" value="${addr.adrsMail}">
                            </td>
                            <td>
                                <button type="button" class="view-mode" onclick="toggleEdit(this)">수정</button>
                                <button type="button" class="edit-mode" onclick="saveEdit(this)">저장</button>
                                <button type="button" class="edit-mode" onclick="cancelEdit(this)">취소</button>
                                <button type="button" onclick="deleteAddress(this)">삭제</button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="6">데이터가 없습니다.</td></tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <hr>

    <h3>새 주소 등록</h3>
    <form action="/address" method="post">
        <div>
            이름: <input type="text" name="adrsName" value="${address.adrsName}">
            <span class="error">${errors.adrsName}</span>
        </div>
        <div>
            전화: <input type="text" name="adrsTel" value="${address.adrsTel}">
            <span class="error">${errors.adrsTel}</span>
        </div>
        <div>
            주소: <input type="text" name="adrsAdd" value="${address.adrsAdd}">
        </div>
        <div>
            메일: <input type="text" name="adrsMail" value="${address.adrsMail}">
            <span class="error">${errors.adrsMail}</span>
        </div>
        <button type="submit">등록</button>
    </form>

  <script src="<c:url value='/resources/js/address.js'/>"></script>
</body>
</html>