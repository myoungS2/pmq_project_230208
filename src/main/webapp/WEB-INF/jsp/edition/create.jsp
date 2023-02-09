<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="d-flex justify-content-center">
	<div class="mt-2">
		<%-- 썸네일 --%>
		<div>
		  	<%-- input file을 d-none으로 숨겨둠 --%>
			<input type="file" id="file" class="d-none"  accept=".jpg,.jpeg,.png,.gif">
			<a href="#" id="thumbnailUploadBtn"><img src="/static/images/ImgUploadIcon.png" alt="이미지파일업로드아이콘" width="350" height="250"></a>
		</div>
		<%-- 제목 --%>
		<div class="mt-2">
			<input type="text" id="subjectInput" name="subject" class="form-control" placeholder="발행물 이름">
		</div>
		<%-- 카테고리 --%>
		<div class="mt-2">
			<select name="editionType" class="form-control">
				<option value="cartoon">만화</option>
				<option value="crafts">공예</option>
				<option value="art">예술</option>
				<option value="food">푸드</option>
				<option value="music">음악</option>
				<option value="publishing">출판</option>
				<option value="tech">테크</option>
				<option value="journalism">저널리즘</option>
				<option value="game">게임</option>
				<option value="movie">영화</option>
				<option value="photo">사진</option>
				<option value="fashion">패션</option>
			</select>
		</div>	
		<%-- 발행일자 --%>
		<div class="d-flex mt-2 align-items-center">
			<span>발행일자 매월</span><input type="text" id="publishDateInput" name="date" class="form-control col-5 ml-4" placeholder="                일">
		</div>
		<%-- 발행물소개 --%>
		<textarea class="form-control mt-2" id="editionIntroduceInput" cols="20" rows="15" ></textarea>
		<%-- 발행물 등록 버튼 --%>
		<div class="d-flex justify-content-center mt-3">
				<a id="createBtn" class="btn"><img src="/static/images/enrollmentbtn.png" alt="등록btn"></a>
		</div>
	</div>	
</div>

<script>
	$(document).ready(function(){
		// 썸네일 이미지
		// fileUploadBtn 버튼 -> 파일 선택 창
		$('#thumbnailUploadBtn').on('click', function(e){
			e.preventDefault();
			// 숨겨둔 input이 동작
			$('#file').click(); // 사용자가 input file을 클릭한 것과 같은 효과
			
		}); // thumbnailUploadBtn close
		
		// 사용자가 파일을 선택 했을 때
		$('#file').on('change', function(e){
			let fileName = e.target.files[0].name; // 사용자가 선택한 파일 -> fileName변수에 저장
			console.log("fileName:" + fileName); // 파일이름 잘 가져와지는지 체크
			
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
		}); // file 선택 close
		
		// 카테고리에서 선택 된 값
		/* $('select[name=editionType]').change(function() {
			let editionType = $('select[name=editionType] option:selected').val();
			// alert(editionType); 선택값 잘 가져와지는지 체크
		}); // select[name=editionType] close */
		
		
		// createBtn 버튼 -> edition create
		$('#createBtn').on('click', function(e) {
			e.preventDefault();
			
			// alert("등록하기 버튼클릭");
			
			// 썸네일 입력 체크
			/* let fileName = $('#file').val();
			if (fileName == ''){
				alert("썸네일을 추가하세요.");
				return;
			} */
			
			let fileCheck = document.getElementById("file").value;
			if(!fileCheck){
				alert("썸네일을 첨부해주세요.");	
				return;
			}
			
			// 제목 입력 체크
			let subject = $('#subjectInput').val();
			if (subject == ''){
				alert("발행물 제목을 입력하세요.");
				return;
			}
			
			// 카테고리 가져오기
			let editionType = $('select[name=editionType] option:selected').val();
			
			// 발행일자 입력 체크
			let date = $('#publishDateInput').val().trim();
			if (date == ''){
				alert("발행일자를 입력하세요.");
				return;
			}
			
			// 발행물 소개 입력 체크
			let editionIntroduce = $('#editionIntroduceInput').val().trim();
			if (date == ''){
				alert("발행물 소개를 입력하세요.");
				return;
			}
			
			// 서버에 요청
			// 폼태그 만들기
			let formData = new FormData();
			formData.append("file", $('#file')[0].files[0]);
			formData.append("subject", subject);
			formData.append("category", editionType);
			formData.append("publishingDate", date);
			formData.append("content", editionIntroduce);
			
			$.ajax({
				url:'/edition/create'
				, method: 'POST'
				, data: formData
				, enctype: 'multipart/form-data' // 파일 업로드를 위한 필수 설정
				, processData: false // 파일 업로드를 위한 필수 설정
				, contentType: false // 파일 업로드를 위한 필수 설정
				, success: function(data){
					if (data.result == 'success'){
						alert("발행물 등록이 완료되었습니다.");
						location.href= '/timeline/view'; // 나중에는 글 디테일 화면으로 넘어가기
					}
				}
				, error: function(e){
				
				}
			}); // edition create ajax close
			
		}); // createBtn close
		
		
	}); // document close


</script>
