<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEM_회원목록</title>
<link rel="stylesheet" href="resources/css/table.css" />
</head>
<body>
<table>
<caption>회원 목록</caption>
	<tr>
		<th>아이디</th>
		<th>이름</th>
		<th>프로필 사진</th>
	</tr>
	
	<c:forEach var="list" items="${memberList}">
	<tr>
		<td><a href="M_view?Mid=${list.mid}&page=${paging.page}">${list.mid}</a></td>
		<td>${list.mname}</td>
		<td><img src="resources/memProfile/${list.mprofileName}" width="50px" /></td>
	</tr>
	</c:forEach>
</table>

	<!-- 페이징 처리 -->
	<c:if test="${paging.page <= 1}">[이전]</c:if>
	<c:if test="${paging.page > 1}"><a href="M_list?page=${paging.page-1}">[이전]</a></c:if>
	
	<!-- 현재 페이지를 숫자로 나타내기 -->
	<c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}" step="1">
		<c:choose>
			<c:when test="${i eq paging.page}">${i}</c:when>
			<c:otherwise><a href="M_list?page=${i}">${i}</a></c:otherwise>
		</c:choose>
	</c:forEach>
	
	<c:if test="${paging.page >= paging.maxPage}">[다음]</c:if>
	<c:if test="${paging.page < paging.maxPage}"><a href="M_list?page=${paging.page+1}">[다음]</a></c:if>

</body>
</html>