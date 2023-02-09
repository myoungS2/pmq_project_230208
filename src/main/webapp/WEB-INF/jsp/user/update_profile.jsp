<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div>
	<%-- 프로필 사진 --%>
	<div class="d-flex justify-content-center">
		<div class="w-50">
			<%-- input file을 d-none으로 숨겨둠 --%>
			<input type="file" id="file" class="d-none" accept=".jpg,.jpeg,.png,.gif"> 
				<a href="#" id="fileUploadBtn">
					<img src="/static/images/profileIcon.png"alt="프로필아이콘">
				</a>
		</div>
	</div>
	<%-- 닉네임 / 중복확인 --%>
	<div class="d-flex">
		<input type="text" id="nicknameInput" name="nickname" class="form-control mt-3 col-8" value="${userInfo.nickname}"> 
		<a id="nicknameCheckHref" data-user-id="${userInfo.id}" type="button" class="btn mt-3 ml-3 col-4"><small>중복확인</small></a>
	</div>
	<%-- 중복확인 메세지 --%>
	<div>
		<span id="nicknameCheckDuplicated" class="small text-danger d-none">사용중인 닉네임입니다.</span> 
		<span id="nicknameCheckOk" class="small text-success d-none">사용가능한 닉네임입니다.</span>
	</div>
	<%-- 이메일 --%>
	<div class="d-flex">
		<input type="text" id="emailInput" name="email" class="form-control mt-3" value="${userInfo.email}">
	</div>
	<%-- 주소 --%>
	<div class="d-flex">
		<input type="text" id="addressInput" name="address" class="form-control mt-3" value="${userInfo.address}">
	</div>
	<%-- 웹사이트 --%>
	<div class="d-flex">
		<input type="text" id="websiteInput" name="website" class="form-control mt-3" value="${userInfo.website}">
	</div>
	<%-- 자기소개 --%>
	<div class="d-flex">
		<input type="text" id="introduceInput" name="introduce" class="form-control mt-3" value="${userInfo.introduce}">
	</div>

	<%-- alter profile btn --%>
	<div class="d-flex justify-content-center mt-3">
		<a id="updateProfileBtn" class="btn"><img src="/static/images/editprofilebtn2.png" alt="프로필수정btn"></a>
	</div>

	<%-- sign out btn --%>
	<div class="d-flex justify-content-center mt-3">
		<a href="/user/sign_out_view" id="signOutBtn" class="btn"><img src="/static/images/signoutbtn.png" alt="로그아웃btn"></a>
	</div>
</div>

<script>
	$(document).ready(function(){
		// 프로필 이미지
		// 파일 업로드 버튼 -> 파일 선택 창
		$('#fileUploadBtn').on('click', function(e){
			e.preventDefault();
			// 숨겨둔 input file이 동작
			$('#file').click(); // 사용자가 input file을 클릭한 것과 같은 효과
			
		}); // fileUploadBtn close
		
		// 사용자가 파일을 선택 했을 때
		$('#file').on('change', function(e){
			let fileName = e.target.files[0].name; //  사용자가 선택한 파일 -> fileName변수에 저장
			console.log("fileName:" + fileName); // 값이 잘 가져와지는지 체크
			
			// 파일 형식 체크
			let fileNameArr = fileName.split('.');
			if (fileNameArr[fileNameArr.length - 1] != 'png'
				&& 	fileNameArr[fileNameArr.length - 1] != 'gif'
				&& 	fileNameArr[fileNameArr.length - 1] != 'jpg'
				&& 	fileNameArr[fileNameArr.length - 1] != 'jepg') {
				// if문이 참이면 -> 잘못 된 파일 형식
				alert("잘못된 파일형식입니다.");
				$(this).val('') // 잘못 된 파일을 비워준다.
				return;
			}
		}); // file close
		
		// 닉네임 중복확인
		$('#nicknameCheckHref').on('click', function(e){
			
			// nicknameInput값 가져오기
			let nickname = $('#nicknameInput').val().trim();
			// alert(nickname); 값 잘 가져와짐!
			// userId 정보 같이 보내기
			let userId = $(this).data('user-id');
			
			// nicknameCheckDuplicated, nicknameCheckOk 상태값
			// 닉네임 중복여부 -> ajax 서버 호출 
			$.ajax({
				// request info
				type: 'get'
				, url: '/user/is_duplicated_update_nickname'
				, data: {"nickname": nickname, "userId": userId}
				// 응답값
				, success: function(data) {
					// alert(data.result); // true or flase 결과 잘 가져와짐!
					// if문 -> false면 중복x 사용가능, true면 중복o 사용불가능
					if (data.result){
						// true
						$('#nicknameCheckDuplicated').removeClass('d-none'); // 문구노출
						$('#nicknameCheckOk').addClass('d-none');
						alert("기존 닉네임을 사용하시겠습니까?");
					} else {
						// false
						$('#nicknameCheckDuplicated').addClass('d-none'); 
						$('#nicknameCheckOk').removeClass('d-none'); // 문구노출
					}
				}
			}); // 닉네임 중복확인 ajax close
		}); // nicknameCheckHref close
		
		// 프로필 수정 버튼
		$('#updateProfileBtn').on('click', function(e){
			e.preventDefault();	
			
			// profileImgPath (nullable)
			let file = $('#file').val();
			
			if (file != ''){
				// 1.  파일명을 .을 기준으로 split -> 배열에 저장
				// 확인 : console.log(file.split('.'));
				// 2. pop : 마지막값(확장자) 빼내기 <> push
				let ext = file.split('.').pop().toLowerCase() // 3. toLowerCase : 빼낸 값을 몽땅 소문자로 변경해서 값 비교하기/ 4. ext 변수에 담기
				// 5. if문
				// 파일 형식 체크 (방법1)
				if ($.inArray(ext,['jpg', 'jpeg', 'png', 'gif']) == -1) {
					// 배열(허용하고자하는 확장자가 들어있음)에 들어있냐 -> 참이면, 잘못들어 온 확장자 
					alert("잘못된 파일형식입니다.");
					// 잘못 된 파일 형식 -> 비워주기
					$('#file').val('');
					return;
				}
			}
			
			// nickname
			let nickname = $('#nicknameInput').val().trim();
			if (nickname == ''){
				alert("닉네임을 입력하세요.");
				return;
			}
			// email
			let email = $('#emailInput').val().trim();
			if (email == ''){
				alert("이메일을 입력하세요.");
				return;
			}
			// address
			let address = $('#addressInput').val().trim();
			if (address == ''){
				alert("주소를 입력하세요.");
				return;
			}
			// website (nullable)
			let website = $('#websiteInput').val().trim();
			
			// introduce (nullable)
			let introduce = $('#introduceInput').val().trim();
			
			// 서버에 요청
			// 폼태그 만들기
			let formData = new FormData();
			formData.append("file", $('#file')[0].files[0]); // $('#file')[0]는 첫번째 input file 태그를 의미, files[0]는 업로드된 첫번째 파일을 의미
			formData.append("nickname", nickname);
			formData.append("email", email);
			formData.append("address", address);
			formData.append("website", website);
			formData.append("introduce", introduce);
			
			$.ajax({
				url: '/user/profile_update'
				, method: 'POST'
				, data: formData
				, enctype: 'multipart/form-data' // 파일 업로드를 위한 필수 설정
				, processData: false // 파일 업로드를 위한 필수 설정
				, contentType: false // 파일 업로드를 위한 필수 설정
				, success: function(data){
					if (data.result == 'success'){
						alert("프로필수정이 완료되었습니다.");
						location.reload(true); // 새로고침
					}
				}
				, error: function(e){
					alert("프로필수정에 실패했습니다.");
				}
			}); // 프로필수정 ajax close
			
		}); // updateProfileBtn close
		
		
		
		
	}); // document close

</script>
