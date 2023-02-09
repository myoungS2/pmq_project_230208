<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
    
<div class=" d-flex justify-content-center">
	<div>
		<%-- 기존 임시저장 발행물 제목 --%>
		<div class="mt-2">
			<input type="text" id="subjectInput" name="subject" class="form-control" placeholder="금월 발행물 제목" value="${publication.subject}">
		</div>
		<%-- 기존 임시저장 발행물 내용 --%>
		<textarea class="form-control mt-2" id="contentInput" cols="50" rows="20" placeholder="내용을 입력하세요." >${publication.content}</textarea>
		<%-- 발행 버튼 --%>
		<div class="d-flex justify-content-center mt-3">
				<a href="#" data-edition-id="${editionId}" data-user-id="${userId}" data-state="send" id="sendOutBtn" class="btn btn-dark w-100">발행</a>
		</div>
	</div>	
</div>
<script>
	$(document).ready(function(){
		
		// 임시저장 글 발행
		$('#sendOutBtn').on('click', function(e){
			// 제목
			let subject = $('#subjectInput').val();
			if (subject == ''){
				alert("금월 발행물 제목을 입력하세요.");
				return;
			}
			
			// 내용
			let content = $('#contentInput').val();
			// alert(content);
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
						alert("발행에 성공했습니다.");
						location.reload();
					}
				}
				, error: function(e){
					alert("발행에 실패했습니다.");
					location.reload();
				}
			}); // draft ajax close
			
		}); // sendOutBtn close
		
	}); // document close

</script>