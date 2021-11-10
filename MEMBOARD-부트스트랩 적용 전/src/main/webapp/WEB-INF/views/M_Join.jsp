<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MEM_Join</title>
</head>
<body>
	<form action="M_join" method="POST" enctype="multipart/form-data" id="joinForm">
		<table>
		<caption>회원가입</caption>
			<tr>
				<th>아이디</th>
				<td><input type="text" name="Mid" id="Mid" onkeyup="idOverlap()">
				</td>
			</tr>
			<tr>
				<td colspan="2">
				 <span id="confirmId"></span>
				</td>
			</tr>
			<tr>
				<th>비밀번호</th>
				<td>
				<input type="password" name="Mpw" id="Mpw" onkeyup="pwRegExp()"/>
				<br/>
				<span id="pwRegExp"></span>
				</td>
			</tr>
			<tr>
				<th>패스워드 확인</th>
				<td>
				<input type="password" id="checkPw" onkeyup="pwOverlap()">
				<br/>
				<span id="confirmPw"></span>
				</td>
			</tr>
			<tr>
				<th>이름</th>
				<td><input type="text" name="Mname"></td>
			</tr>
			<tr>
				<th>연락처</th>
				<td><input type="text" name="Mphone"></td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td><input type="date" name="Mbirth"></td>
			</tr>
			<tr>
				<th>이메일</th>
				<td>
				<input type="email" name="Memail" id="Memail">
				<span id="emailConfirmText">
				<input type="button" value="인증" onclick="confirmEmail()">
				</span>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>
					<input type="text" id="sample6_postcode" placeholder="우편번호" name="addr1">
					<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
					<input type="text" id="sample6_address" placeholder="주소" name="addr2"><br>
					<input type="text" id="sample6_detailAddress" placeholder="상세주소" name="addr3">
				</td>
			</tr>
			<tr>
				<th>프로필 사진</th>
				<td><input type="file" name="Mprofile" /></td>
			</tr>
			
			<tr>
				<td colspan="2">
					<input type="button" value="가입" onclick="mJoin()">
					<input type="reset" value="다시작성">
				</td>
			</tr>
		</table>
	</form>
</body>

<script type="text/javascript" src="resources/js/jquery3.6.0.js"> </script>
<script type="text/javascript">
	// 아이디 중복체크
	function idOverlap(){
		let idCheck = document.getElementById("Mid").value;
		let confirmId = document.getElementById("confirmId");
		// 회원가입 버튼용
		let idPass = false;
		
		$.ajax({
			type : "POST",
			url : "A_idoverlap",
			data : { "Mid" : idCheck } ,
			async : false,
			dataType : "text",
			success : function(data) {
				// 성공
				if(data=="OK"){
					confirmId.style.color="#0000ff";
					confirmId.innerHTML = "사용가능한 아이디 입니다.";
					// 회원가입 버튼용
					idPass = true;
				} else{
					confirmId.style.color="#ff0000";
					confirmId.innerHTML = "사용 불가능한 아이디 입니다.";
					// 회원가입 버튼용
					idPass = false;
				}
			} ,
			error : function() {
				// 실패
				alert("idOverlap 함수 통신 실패!");
			} 
		
		});
		// 회원가입 버튼용
		return idPass;	
	}
	
	// 비밀번호 정규식
	function pwRegExp(){
		let Mpw = document.getElementById("Mpw").value;
		let pwRegExp = document.getElementById("pwRegExp");
		let pwPass = false;
		
		let num = Mpw.search(/[0-9]/g);
		let eng = Mpw.search(/[a-z]/ig);
		let spe = Mpw.search(/[`~!@#$%^&*|\\\'\";:\/?]/gi);
		
		if(Mpw.length < 8 || Mpw.length > 15) {
			pwRegExp.style.color = "#ff0000";
			pwRegExp.innerHTML = "비밀번호는 8자~15자 이하로 입력해주세요.";
		} else if(Mpw.search(/\s/) != -1){
			pwRegExp.style.color = "#ff0000";
			pwRegExp.innerHTML = "비밀번호에는 공백을 사용할 수 없습니다.";
		} else if(num<0 || eng<0 || spe<0){
			pwRegExp.style.color = "#ff0000";
			pwRegExp.innerHTML = "영문, 숫자, 특수문자를 모두 혼합하여 입력해주세요.";
		} else{
			pwRegExp.style.color = "#0000ff";
			pwRegExp.innerHTML = "사용가능한 비밀번호 입니다.";
			pwPass = true;
		}
		return pwPass;
	}
	
	// 비밀번호 재확인
	function pwOverlap(){
		let Mpw = document.getElementById("Mpw").value;
		let pwRegExp = document.getElementById("pwRegExp");
		
		let checkPw = document.getElementById("checkPw").value;
		let confirmPw = document.getElementById("confirmPw");
		
		let pwPass = false;
		
		pwRegExp.innerHTML = "";
		
		if(Mpw == checkPw){
			confirmPw.style.color = "#0000ff";
			confirmPw.innerHTML = "비밀번호가 일치합니다.";
			pwPass = true;
		} else {
			confirmPw.style.color = "#ff0000";
			confirmPw.innerHTML = "비밀번호가 불일치합니다.";
		}
		return pwPass;
	}	
	
	// 이메일 인증 
	function confirmEmail(){
		let Memail = document.getElementById("Memail").value;
		let emailConfirmText = document.getElementById("emailConfirmText");
		
		let emailPass = false;
	
		$.ajax({
			type : "GET",
			url : "A_emailConfirm",
			data : { "Memail" : Memail } ,
			dataType : "text",
			success : function(data) {
				// 성공
				alert("입력하신 이메일로 인증번호가 발송 되었습니다.");
				emailConfirmText.innerHTML = "<input type='hidden' id='check2' value='" + data + "'>"
												+ "<br/><input type='text' placeholder='인증번호 입력' id='emailKey'>"
												+ " <input type='button' value='확인' onclick='keyCheck()'>"
												+ "<br/><span id='check1'></span>";
			} ,
			error : function() {
				// 실패
				alert("인증번호 발송에 실패했습니다.");
			} 
		});
	}
	
	// 인증번호 확인하기
	function keyCheck(data){
		let emailKey = document.getElementById("emailKey").value;
		
		let check1 = document.getElementById("check1");
		let check2 = document.getElementById("check2").value;
		
		
		if(emailKey == check2){
			check1.style.color = "#0000ff";
			check1.innerHTML = "인증번호가 일치합니다.";
			emailPass = true;
		} else{
			check1.style.color = "#ff0000";
			check1.innerHTML = "인증번호가 일치하지 않습니다.";
		}
		return emailPass;
	}
	
</script>


<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
    
    // 회원 가입 버튼
    function mJoin(){
    	console.log("아이디 체크:" + idOverlap());
    	console.log("비밀번호 정규식 체크:" + pwRegExp());
    	console.log("비밀번호 확인 체크:" + pwOverlap());
    	console.log("이메일 인증코드 확인 체크:" +keyCheck());
    	
    	if(!idOverlap()){
    		alert("아이디 중복을 확인해주세요!");
    	} else if(!pwRegExp()){
    		alert("비밀번호를 확인해주세요!");
    	} else if(!pwOverlap()){
    		alert("비밀번호를 확인해주세요!(불일치)");
    	} else if(!keyCheck()){
    		alert("이메일 인증코드를 확인해주세요!");
    	} else{
    		joinForm.submit();
    	}	
    }
    
</script>
</html>