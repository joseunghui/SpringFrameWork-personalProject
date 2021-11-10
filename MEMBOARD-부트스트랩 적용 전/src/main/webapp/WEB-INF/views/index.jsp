<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEMBOARD_index</title>
</head>
<body>
	<c:choose>
	<c:when test="${sessionScope.loginId eq 'admin'}">
	<img src="resources/memProfile/${sessionScope.profile}" width="200px" alt="프로필사진" />
	<h1>관리자 모드로 접속했습니다.</h1>
	<hr/>
	<button onclick="location.href='B_list'">게시글 목록</button>
	<br/>
	<hr/>
	<br/>
	<button onclick="location.href='M_list'">회원 목록</button>
	<button onclick="location.href='M_logout'">로그아웃</button>
	</c:when>
	
	<c:when test="${not empty sessionScope.loginId}">
	<img src="resources/memProfile/${sessionScope.profile}" width="200px" alt="프로필사진" />
	<h1>${sessionScope.loginId} 님 환영합니다. </h1>
	<hr>
	<button onclick="location.href='B_writeForm?Mid=${sessionScope.loginId}'">게시글 작성</button>
	<button onclick="location.href='B_list'">게시글 목록</button>
	<hr>
	<button onclick="location.href='M_view?Mid=${sessionScope.loginId}'">내 정보보기</button>
	<button onclick="location.href='M_logout'">로그아웃</button>
	</c:when>
	
	<c:otherwise>
	<br/><br/>
	<button onclick="location.href='M_joinForm'">회원가입</button>
	<button onclick="location.href='M_loginForm'">로그인</button>
	<br/><br/>
	<hr/>
	<br/>
	<button onclick="location.href='B_list'">게시글 목록</button>
	</c:otherwise>
	</c:choose>
</body>
</html>