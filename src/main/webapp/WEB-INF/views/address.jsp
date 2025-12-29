<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주소록 관리 시스템 (RESTful)</title>
<style>
    table { width: 100%; border-collapse: collapse; margin-top: 10px; }
    th, td { border: 1px solid black; padding: 5px; text-align: center; }
    .edit-mode { display: none; }
    .search-area { text-align: right; margin-bottom: 5px; }
    .register-area { margin-top: 20px; border-top: 1px solid #ccc; padding-top: 10px; }
</style>
</head>
<body data-bpath="${path}">

    <h1>주소록 관리</h1>

    <div class="search-container">
        <form action="${path}/address" method="get">
            <input type="text" name="searchName" placeholder="이름으로 검색" value="${param.searchName}">
            <button type="submit" class="btn btn-edit">검색</button>
            <a href="${path}/address" class="btn btn-cancel" style="text-decoration:none;">초기화</a>
        </form>
    </div>

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
                                <button type="button" class="btn btn-edit view-mode" onclick="toggleEdit(this)">수정</button>
                                <button type="button" class="btn btn-save edit-mode" onclick="saveEdit(this)">저장</button>
                                <button type="button" class="btn btn-cancel edit-mode" onclick="cancelEdit(this)">취소</button>
                                <button type="button" class="btn btn-del" onclick="deleteAddress(this)">삭제</button>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr><td colspan="6">${message}</td></tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>

    <div class="section-box">
        <h3>+ 새 주소 등록</h3>
        <form action="${path}/address" method="post">
            <input type="text" name="adrsName" placeholder="이름(필수)" required>
            <input type="text" name="adrsTel" placeholder="전화번호(필수)" required>
            <input type="text" name="adrsAdd" placeholder="주소">
            <input type="email" name="adrsMail" placeholder="이메일">
            <button type="submit" class="btn btn-save">신규 등록</button>
        </form>
    </div>
</body>
<script type="text/javascript" src="<c:url value='/resources/js/address.js'/>"></script>
</html>