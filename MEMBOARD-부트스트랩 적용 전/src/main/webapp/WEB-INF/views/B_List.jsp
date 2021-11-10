<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board_List</title>
<link rel="stylesheet" href="resources/css/table.css" />
</head>
<body>
	<table>
		<caption>게시글 목록</caption>
		<tr>
			<th>글 번호</th>
			<th>작성자</th>
			<th>제목</th>
			<th>최종 작성일</th>
			<th>조회수</th>
			<th>첨부파일</th>
			<c:if test="${list.bwri eq 'admin'}">
			<tr>
				<th><button>삭제</button></th>
			</tr>
			</c:if>
		</tr>
		
		<c:forEach var="list" items="${boardList}">
		<tr>
			<td>${list.bnum}</td>
			<td>${list.bwri}</td>
			<td><a href="B_view?Bnum=${list.bnum}&page=${paging.page}">${list.btitle}</a></td>
			<td>${list.bdate}</td>
			<td>${list.bhit}</td>
			<td><img src="resources/boardFile/${list.bfileName}" width="50px"></td>
		</tr>
		</c:forEach>
	</table>




<!-- 페이징 처리 -->
	<c:if test="${paging.page <= 1}">[이전]</c:if>
	<c:if test="${paging.page > 1}"><a href="B_list?page=${paging.page-1}">[이전]</a></c:if>
	
	<!-- 현재 페이지를 숫자로 나타내기 -->
	<c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}" step="1">
		<c:choose>
			<c:when test="${i eq paging.page}">${i}</c:when>
			<c:otherwise><a href="B_list?page=${i}">${i}</a></c:otherwise>
		</c:choose>
	</c:forEach>
	
	<c:if test="${paging.page >= paging.maxPage}">[다음]</c:if>
	<c:if test="${paging.page < paging.maxPage}"><a href="B_list?page=${paging.page+1}">[다음]</a></c:if>

</body>
</html>