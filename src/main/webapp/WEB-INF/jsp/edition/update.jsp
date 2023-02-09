<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="d-flex justify-content-center">
	<div class="mt-2">
		<%-- 썸네일 --%>
		<div>
		  	<%-- input file을 d-none으로 숨겨둠 --%>
			<input type="file" id="file" class="d-none"  accept=".jpg,.jpeg,.png,.gif">
			<a href="#" id="thumbnailUploadBtn">
				<img id="fileCheck" src="${editionInfo.thumbnailPath}" alt="이미지파일업데이트" width="350" height="250">
			</a>
		</div>
		<%-- 제목 --%>
		<div class="mt-2">
			<input type="text" id="subjectInput" name="subject" class="form-control" value="${editionInfo.subject}">
		</div>
		<%-- 카테고리 --%>
		<%-- edition info 중 선택 된 category가 선택되어있게 하는 방법 --%>
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
			<span>발행일자 매월</span><input type="text" id="publishDateInput" name="date" class="form-control col-5 ml-4" value="${editionInfo.publishingDate}" placeholder="              일">
		</div>
		<%-- 발행물소개 --%>
		<%-- 클릭시 지워지게 --%>
		<textarea class="form-control mt-2" id="editionIntroduceInput" cols="20" rows="15">${editionInfo.content}</textarea>
		<%-- 발행물 수정 버튼 --%>
		<div class="d-flex justify-content-center mt-3">
				<a id="updateBtn" class="btn btn-dark w-100" data-edition-id="${editionInfo.id}">수정하기</a>
		</div>
		<%-- 취소 버튼 -> 다시 선택하고 들어온 edition으로 돌아가기 --%>
		<div class="d-flex justify-content-center mt-3">
				<a href="/edition/detail_view?editionId=${editionInfo.id}&userId=${publisherInfo.id}" id="cancelBtn" class="btn btn-dark w-100">취소하기</a>
		</div>
	</div>	
</div>
<script>
	$(document).ready(function(){
		// 기존 카테고리가 선택되어 있게하기
		// 서버에서 내려오는 값 =  name일치 -> checked
		let category = '${editionInfo.category}';
		$('select[name=editionType]').val(category).prop("selected", true);
		
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
	
		// subjectInput 클릭 시 값 비워주기
		$('#subjectInput').on('click', function(e){
			e.preventDefault();
			if($('#subjectInput').val().trim() != null){
				$('#subjectInput').val('');
			}
		}); // subjectInput close
		
		// publishDateInput 클릭 시 값 비워주기
		$('#publishDateInput').on('click', function(e){
			e.preventDefault();
			if($('#publishDateInput').val().trim() != null){
				$('#publishDateInput').val('');
			}
		}); // publishDateInput close
		
		// editionIntroduceInput 클릭 시 값 비워주기
	 	$('#editionIntroduceInput').on('click', function(e){
	 		e.preventDefault();
	 		// alert("textarea click");
	 		
			// 따라서 id값을 지용해서 값을 지워주기
			document.getElementById('editionIntroduceInput').value='';
			
			/*  textarea에 기록한 것을 ajax를 통해 전송시, 
				textarea에 타이핑했던 내용이 지워지지 않고 그대로 남아있다. 
				페이지가 전환되지 않기 때문. 
			if($('#editionIntroduceInput').val().trim() != null){
				$('#editionIntroduceInput').val('');
			} */
			
		}); // editionIntroduceInput close
		
		// 글 수정
		$('#updateBtn').on('click', function(){
			
			// 썸네일 입력 체크
			let newFile = $('#file').val();
			let fileCheck = $('#fileCheck').src;
			if (newFile == '' && fileCheck == ''){
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
			
			// 어떤 edition에 대한 수정인지도 함께
			let editionId = $(this).data('edition-id'); 
			console.log("editionId:" + editionId);
			
			// 서버에 요청
			// 폼태그 만들기
			let formData = new FormData();
			formData.append("editionId",editionId);
			formData.append("file", $('#file')[0].files[0]);
			formData.append("subject", subject);
			formData.append("category", editionType);
			formData.append("publishingDate", date);
			formData.append("content", editionIntroduce);
			
			// ajax 보내는 부분 다시볼 것!
			$.ajax({
				url:'/edition/update'
				, method: 'POST'
				, data: formData
				, enctype: 'multipart/form-data' // 파일 업로드를 위한 필수 설정
				, processData: false // 파일 업로드를 위한 필수 설정
				, contentType: false // 파일 업로드를 위한 필수 설정
				, success: function(data){
					if (data.result == 'success'){
						alert("발행물 수정이 완료되었습니다.");
						location.reload(true); // 새로고침
					}
				}
				, error: function(e){
				
				}
			});	// edition update ajax close
			
		}); // updateBtn close
		
	}); // document close

</script>