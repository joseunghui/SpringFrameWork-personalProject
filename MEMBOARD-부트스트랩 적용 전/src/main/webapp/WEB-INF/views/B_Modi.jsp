<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board_Modi</title>
<link rel="stylesheet" href="resources/css/table.css">
</head>
<body>
	<form action="B_modi" method="post" enctype="multipart/form-data">
	<table>
		<caption>게시글 작성하기</caption>
		<tr>
			<th>글 번호</th>
			<td>${modi.bnum}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${modi.bwri}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="Btitle" /></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea rows="20" cols="40" name="Bcontent"></textarea></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td><input type="file" name="Bfile" /></td>
		</tr>
		<tr>
			<td colspan="2">
			<input type="hidden" name="Bnum" value="${modi.bnum}" >
			<input type="hidden" name="Bwri" value="${modi.bwri}" >
			<input type="hidden" name="Bdate" value="${modi.bdate}" >
			<input type="submit" value="등록">
			</td>
		</tr>
	</table>
	</form>
</body>
</html>