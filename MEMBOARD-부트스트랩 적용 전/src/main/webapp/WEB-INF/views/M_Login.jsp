<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEM_Login</title>
</head>
<body>
	<form action="M_login" method="POST">
	<table>
		<caption>로그인</caption>
		<tr>
			<th>아이디</th>
			<td><input type="text" name="Mid" /></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="Mpw" /></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="로그인" />
			</td>
		</tr>
	</table>
	</form>
</body>
</html>