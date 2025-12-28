<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주소록 목록</title>
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

table, th, td {
	border: 1px solid black;
}

th, td {
	padding: 8px;
	text-align: center;
}

.btn-group a {
	margin: 0 5px;
	text-decoration: none;
}

.update-link { color: blue; }
.delete-link { color: red; }
</style>
</head>
<body>
	<h1>주소록 페이지</h1>
	<c:choose>
		<c:when test="${not empty addressList}">
			<table>
				<thead>
					<tr>
						<th>번호</th>
						<th>이름</th>
						<th>전화번호</th>
						<th>주소</th>
						<th>이메일</th>
						<th>관리</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${addressList}" var="addr" varStatus="vs">
						<tr>
							<td>${vs.count}</td>
							<td>${addr.adrsName}</td>
							<td>${addr.adrsTel}</td>
							<td>${addr.adrsAdd}</td>
							<td>${addr.adrsMail}</td>
							<td class="btn-group">
								<a href="${pageContext.request.contextPath}/address/update.do?adrsNo=${addr.adrsNo}" class="update-link">수정</a>
								
								<a href="${pageContext.request.contextPath}/address/delete.do?adrsNo=${addr.adrsNo}" class="delete-link">삭제</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<div class="msg">
				<p>${message}</p>
			</div>
		</c:otherwise>
	</c:choose>
	<br>
	<a href="${pageContext.request.contextPath}/address/insert.do">[새 주소 등록]</a>
</body>
</html>