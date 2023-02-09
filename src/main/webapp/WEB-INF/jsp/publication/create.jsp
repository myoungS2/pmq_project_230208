<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%-- 발행물 임시저장 버튼 -> 이미 저장 된 글을 클릭해서 수정하러 왔다면, 값이 input에 들어있어야 함 --%>
<div class=" d-flex justify-content-center">
	<div>
		<%-- 발행물 제목 --%>
		<div class="mt-2">
			<input type="text" id="subjectInput" name="subject" class="form-control" placeholder="금월 발행물 제목">
		</div>
		<%-- 발행물 내용 --%>
		<textarea class="form-control mt-2" id="contentInput" cols="50" rows="20" placeholder="내용을 입력하세요."></textarea>
		<%-- 임시저장 버튼 --%>
		<div class="d-flex justify-content-center mt-3">
				<a href="#" data-state="draft" data-edition-id="${editionId}" data-user-id="${userId}" id="draftBtn" class="btn btn-dark w-100">임시저장</a>
		</div>
		<%-- 발행 버튼 --%>
		<div class="d-flex justify-content-center mt-3">
				<a href="#" data-state="send" data-edition-id="${editionId}" data-user-id="${userId}" id="sendOutBtn" class="btn btn-dark w-100">발행</a>
		</div>
	</div>	
</div>

<script>
	$(document).ready(function(){
		
		
		// 임시저장(insertDB -> email x)
		$('#draftBtn').on('click', function(e){
			// 제목
			let subject = $('#subjectInput').val();
			if (subject == ''){
				alert("금월 발행물 제목을 입력하세요.");
				return;
			}
			
			// 내용
			let content = $('#contentInput').val();
			if (content == ''){
				alert("발행물 내용을 입력하세요.");
				return;
			}
			
			// 상태
			let state = $(this).data('state');
			
			// editionId
			let editionId = $(this).data('edition-id');
			
			// userId
			let userId = $(this).data('user-id');
			
			
			// 서버에 요청
			
			$.ajax({
				url:'/publication/email/send'
				, method: 'POST'
				, dataType: 'json'
				, data: {"subject":subject, "content":content, "state":state, "editionId":editionId, "userId":userId}
				, success: function(data){
					if (data.result == 'success'){
						alert("임시저장에 성공했습니다.");
						location.reload();
					}
				}
				, error: function(e){
					alert("임시저장에 실패했습니다.");
					location.reload();
				}
			}); // draft ajax close
			
		}); // draftBtn close
			
		
		
		// 발행 (insertDB -> email)
		$('#sendOutBtn').on('click', function(e){
			
			// 제목
			let subject = $('#subjectInput').val();
			if (subject == ''){
				alert("금월 발행물 제목을 입력하세요.");
				return;
			}
			
			// 내용
			let content = $('#contentInput').val();
			if (content == ''){
				alert("발행물 내용을 입력하세요.");
				return;
			}
			
			// 상태
			let state = $(this).data('state');
			
			// editionId
			let editionId = $(this).data('edition-id');
			
			// userId
			let userId = $(this).data('user-id');
			
			
			$.ajax({
				url:'/publication/email/send'
				, method: 'POST'
				, dataType: 'json'
				, data: {"subject":subject, "content":content, "state":state, "editionId":editionId, "userId":userId}
				, success: function(data){
					if (data.result == 'success'){
						alert("발행에 성공했습니다.");
						location.reload();
					}
				}
				, error: function(e){
					alert("발행에 실패했습니다.");
					location.reload();
				}
			}); // send ajax close
			
		}); // sendOutBtn close
		
	}); // document close

</script>