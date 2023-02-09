<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 로그인 --%>

<%-- input --%>
<form id="signinForm" action="/user/sign_in" method="post">
	<div>
		<input type="text" id="loginIdInput" name="loginId"
			class="form-control mt-3 w-100" placeholder="ID"> <input
			type="password" id="passwordInput" name="password"
			class="form-control mt-3 w-100" placeholder="PASSWORD">
	</div>
	
	<%-- sign in btn --%>
	<div class="d-flex justify-content-center mt-3">
		<button type="submit" id="signinBtn" class="btn"><img src="/static/images/signinbtn.png" alt="로그인btn"></button>
	</div>
	
	<%-- sign up link --%>
	<div class="d-flex justify-content-center mt-3">
		<a id="signupLink" type="button" href="/user/sign_up_view">sign up</a>
	</div>
</form>
<script>
	$(document).ready(function(){
		// signinForm submit 될 때
		$('#signinForm').submit(function(e){
			e.preventDefault(); // submit 자동 수행 중단
			
			// 유효성 검사
			let loginId = $('#loginIdInput').val().trim();
			if (loginId == ''){
				alert("아이디를 입력하세요.");
				return false;
			}
			
			let password = $('#passwordInput').val().trim();
			if (password == ''){
				alert("비밀번호를 입력하세요.");
				return false;
			}
			
			let url = $(this).attr('action');
			let data = $(this).serialize();
			console.log("url :" + url);
			console.log("data :" + data);
			
			$.post(url, data)
			.done(function(data){
				if (data.result == 'success'){
					location.href= '/timeline/view';
				} else {
					alert("로그인에 실패했습니다. 다시 시도해주세요.");
				}
			}); // post close
			
			
		}); // signinForm close
		
		
	}); // document close

</script>