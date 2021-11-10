<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board_View</title>
<link rel="stylesheet" href="resources/css/table.css" />
</head>
<body>

<table>
		<caption>게시글 정보보기</caption>
		<tr>
			<th>게시글 번호</th>
			<td>${view.bnum}</td>
		</tr>
		
		<tr>
			<th>작성자</th>
			<td>${view.bwri}</td>
		</tr>
		
		<tr>
			<th>제목</th>
			<td>${view.btitle}</td>
		</tr>
		
		<tr>
			<th>내용</th>
			<td>${view.bcontent}</td>
		</tr>
		
		<tr>
			<th>최종 작성 날짜</th>
			<td>${view.bdate}</td>
		</tr>
		
		<tr>
			<th>조회수</th>
			<td>${view.bhit}</td>
		</tr>
		
		<tr>
			<th>첨부파일</th>
			<td><img src="resources/boardFile/${view.bfileName}" width="300px"/></td>
		</tr>
		
		<c:if test="${view.bwri eq sessionScope.loginId}">
		<tr>
			<td colspan="2">
			<button onclick="location.href='B_modiForm?Bnum=${view.bnum}&page=${paging.page}'">수정</button>
			<button onclick="location.href='B_delete?Bnum=${view.bnum}&page=${paging.page}'">삭제</button>
			</td>
		</tr>
		</c:if>	
			
		<tr>
			<td colspan="2">
			<button onclick="location.href='B_list?page=${paging.page}'">뒤로가기</button>
			</td>
		</tr>
		
		

		
	</table>
	
	<!-- 댓글쓰기 -->
	<c:if test="${not empty sessionScope.loginId}">
	<div>
		<textarea rows="2" cols="40" id="Ccontent"></textarea>
		<button id="cmtBtn">댓글 입력</button>
	</div>
	</c:if>
	
	
	<div id="commentArea"></div>

</body>

<script type="text/javascript" src="resources/js/jquery3.6.0.js"> </script>
<script type="text/javascript">
	
	// M_View.jsp 페이지 로딩시 댓글 목록 조회 기능
	$(document).ready(function(){
		let Cwriter = '${sessionScope.loginId}';
		let CBno = ${view.bnum};
		
		$.ajax({
			type : "POST",
			url : "C_list",
			data : { "CBno" : CBno } ,
			dataType : "json",
			success : function(result) {
				// 성공
				commentList(result);
			} ,
			error : function() {
				// 실패
				alert("댓글 리스트 불러오기 실패!");
			} 
		});
		
		// 댓글 쓰기
		$("#cmtBtn").click(function(){
			// let Cwriter = document.getElementById("Cwriter").value;
			// let Cwriter = $("#Cwriter").val();
			let Cwriter = '${sessionScope.loginId}';
			let Ccontent = $("#Ccontent").val();
			let CBno = ${view.bnum};
			
			$.ajax({
				type : "POST",
				url : "C_write",
				data : { "Cwriter" : Cwriter,
						 "Ccontent" : Ccontent,
						 "CBno" : CBno } ,
				dataType : "json",
				success : function(result) {
					// 성공 : 댓글 목록 새로고침, 값 다시 받아오기
					commentList(result);
						 $("Ccontent").val("");
				} ,
				error : function() {
					// 실패
					alert("댓글 입력 실패!");
				} 
			});
		});
	});
	
	function commentList(result){
		let output = "";
		
		output += "<table>";
		output += "<caption>댓글 목록</caption>";
		
		output += "<tr>";
		output += "<th>작성자</th>";
		output += "<th>작성 날짜</th>";
		output += "<th>내용</th>";
		output += "<th>삭제</th>";
		output += "</tr>";
		
		for(var i in result){
			output += "<tr>";
			output += "<td>" + result[i].Cwriter + "</td>";
			output += "<td>" + result[i].Cdate + "</td>";
			output += "<td>" + result[i].Ccontent + "</td>";
			output += "<td><button onclick='cmtDel(" + result[i].Cno + ")'>삭제</button></td>";
			output += "</tr>";
		}
		output += "</table>";
		
		// 위에 <div id="commentArea"></div> 가 있어야 연결됨!!!!!
		$("#commentArea").html(output);
	}
	
	function cmtDel(Cno){
		
		$.ajax({
			type : "GET",
			url : "C_delete",
			data : { "Cno" : Cno,
					 "CBno" : ${view.bnum} } ,
			dataType : "json",
			success : function(result) {
				// 성공 : 해당 댓글 삭제
				commentList(result);
			} ,
			error : function() {
				// 실패
				alert("댓글 삭제 실패!");
			} 
		});
		
	}

</script>
</html>