<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEM_View</title>
<link rel="stylesheet" href="resources/css/table.css">
</head>
<body>
<table>
		<caption>회원정보보기</caption>
		<tr>
			<th>아이디</th>
			<td>${view.mid}</td>
		</tr>
		
		<tr>
			<th>이름</th>
			<td>${view.mname}</td>
		</tr>
		
		<tr>
			<th>전화번호</th>
			<td>${view.mphone}</td>
		</tr>
		
		<tr>
			<th>생년월일</th>
			<td>${view.mbirth}</td>
		</tr>
		
		<tr>
			<th>이메일</th>
			<td>${view.memail}</td>
		</tr>
		
		<tr>
			<th>주소</th>
			<td>${view.maddr}</td>
		</tr>
		
		<tr>
			<th>프로필</th>
			<td><img src="resources/memProfile/${view.mprofileName}" width="300px"/></td>
		</tr>
		
		<tr>
			<td colspan="2">
			<button onclick="location.href='M_modiForm?Mid=${view.mid}'">수정</button>
			<button onclick="location.href='M_delete?Mid=${view.mid}'">삭제</button>
			</td>
		</tr>
	
	</table>
</body>
</html>