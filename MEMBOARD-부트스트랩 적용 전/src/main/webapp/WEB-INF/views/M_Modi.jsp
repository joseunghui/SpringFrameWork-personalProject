<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="M_modify" method="POST" enctype="multipart/form-data">
	<table>
		<caption>회원수정</caption>
		<tr>
			<th>아이디</th>
			<td>${modi.mid}
			<input type="hidden" name="Mid" value="${modi.mid}"></td>
		</tr>
		
		<tr>
			<th>패스워드</th>
			<td><input type="password" name="Mpw"></td>
		</tr>
		
		<tr>
			<th>이름</th>
			<td><input type="text" name="Mname" placeholder="${modi.mname}"></td>
		</tr>
		
		<tr>
			<th>연락처</th>
			<td><input type="text" name="Mphone" placeholder="${modi.mphone}"></td>
		</tr>
		
		<tr>
			<th>생년월일</th>
			<td><input type="date" name="Mbirth"  placeholder="${modi.mbirth}"></td>
		</tr>
		
		<tr>
			<th>이메일</th>
			<td><input type="email" name="Memail" placeholder="${modi.memail}" ></td>
		</tr>
		
		<tr>
			<th>주소</th>
			<td><input type="text" name="Maddr" placeholder="${modi.maddr}" ></td>
		</tr>
		
		<tr>
			<th>프로필 사진</th>
			<td><input type="file" name="Mprofile" ></td>
		</tr>
		
		<tr>
			<td colspan="2">
				<input type="submit" value="회원수정">
				<input type="reset" value="다시작성">
			</td>
		</tr>
	
	</table>
</form>
</body>
</html>