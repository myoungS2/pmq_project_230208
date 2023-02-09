<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>      
 
<div id="editionDetailDiv" class="d-flex justify-content-center">
	<div>
		<%-- edition subject --%>
		<div class="text-center ml-4 mb-3">
			<h1>${editionInfo.subject}</h1>
		</div>
		<%-- 수정/삭제 버튼 , publisher 프로필사진 + nickname, 좋아요 --%>
		<div class="d-flex justify-content-between align-items-center mr-3">
			<%-- 구독신청버튼/ 구독취소버튼(신청이 기존에 되었을 때 노출) : 로그인시에만 노출 --%>
			<%-- 현재 로그인 된 사용자(구독자) 정보 잘 내려오는 것 확인 <h1>${userInfo.id}</h1> --%>
			<c:if test="${not empty userInfo.id}">
				<div>
					<%-- 구독중이지 않을 때 -> 구독시작 버튼 --%>
					<c:if test="${existSubscribe eq false}">
						<a href="#" id="startSubscribeBtn" type="button" class="btn btn-dark" data-edition-id="${editionInfo.id}" data-user-id="${userInfo.id}">구독시작</a>
					</c:if>
					<%-- 구독중일 때-> 구독취소 버튼 --%>
					<c:if test="${existSubscribe eq true}">
						<a href="#" id="cancelSubscribeBtn" type="button" class="btn btn-dark" data-edition-id="${editionInfo.id}" data-user-id="${userInfo.id}">구독취소</a>
					</c:if>
				</div>
			</c:if>
			<%-- publisher info --%>
			<div class="text-center">
				<%-- 프로필 사진이 없을 때 --%>
				<c:if test="${empty publisherInfo.profileImgPath}">
					<div><img src="/static/images/profileIcon.png" alt="프로필아이콘" width="80" height="80"></div>
				</c:if>
				<%-- 프로필 사진이 있을 때 --%>
				<c:if test="${not empty publisherInfo.profileImgPath}">
					<div><img id="profileImg" src="${publisherInfo.profileImgPath}" alt="userProfileImg" width="80" height="80"></div>
				</c:if>
				<div><span class="font-weight-bold">by ${publisherInfo.nickname}</span></div>
			</div>
			<%-- 좋아요(토글) --%>
			<div class="text-center">
				<%-- 좋아요 수  --%>
				<div><small>${likeCount}</small></div>
				<div>
					<a href="#" class="like-btn" data-edition-id="${editionInfo.id}" data-user-id="${userInfo.id}">
					<%-- empty heart img (좋아요x) --%>
					<c:if test="${existLike eq false}">
						<img src="/static/images/emptyHeartIcon.png" alt="emptyheart" width="30" height="30">
					</c:if>
					<%-- full heart img (좋아요o) --%>
					<c:if test="${existLike eq true}">
						<img src="/static/images/fullHeartIcon.png" alt="fullheart" width="30" height="30">
					</c:if>
					</a>
				</div>
						
			</div>
		</div>
		<%-- edition thumbnail --%>
		<div class="mt-3">
			<img src="${editionInfo.thumbnailPath}" alt="editionTumbnailImg" width="390" height="400">
		</div>
		<%-- edition content --%>
		<div class="mt-3">
			${editionInfo.content}
		</div>
	</div>
</div>
<script>
	$(document).ready(function(){
		// 좋아요
		$('.like-btn').on('click', function(e){
			e.preventDefault();
			
			// 어떤 글에 좋아요를 눌렀는지..! -> $('.like-btn')으로 이벤트를 잡게되면, 클래스 이기 때문에 첫번째 것에만 계속 동작하게 됨.
			let editionId = $(this).data('edition-id');
			let userId = $(this).data('user-id');
			
			console.log(editionId);
			console.log(userId);
			
			if (userId == ''){
				alert("로그인 후에 사용할 수 있는 기능입니다.");
				return;
			}
			
			// 서버에 요청
			$.ajax({
				type:'post'
				, url: '/like/like'
				, data: {"editionId" : editionId, "userId" : userId}
				, success: function(data){
					if (data.result == 'success'){
						// alert("좋아요!");
						location.reload();
					}
				}
				, error: function(e){
					
				}
			}); // like ajax close
			
		}); // like-btn close
		
		
		// 구독시작
		$('#startSubscribeBtn').on('click', function(e){
			e.preventDefault();
			
			// 어떤 edition을 구독했는지에 대한 정보
			let editionId = $(this).data('edition-id'); 
			
			// 누가 구독을 시작했는지에 대한 정보
			let userId = $(this).data('user-id');
			
			console.log("editionId:" + editionId);
			console.log("userId:" + userId);
			
			// 서버에 요청
			$.ajax({
				type: 'post'
				,url: '/subscribe/start'
				, data: {'editionId': editionId}
				, success: function(data){
					if (data.result == 'success'){
						alert("구독이 시작되었습니다.");
						location.reload();
					}
				}
				, error: function(e){
					
				}
			}); // start subscribe ajax close
			
		}); // startSubscribeBtn close
		
		// 구독취소
		$('#cancelSubscribeBtn').on('click', function(e){
			e.preventDefault();
			
			// 어떤 edition을 취소했는지에 대한 정보 
			let editionId = $(this).data('edition-id'); 
			
			// 누가 구독을 취소했는지에 대한 정보
			let userId = $(this).data('user-id');
			
			console.log("editionId:" + editionId);
			console.log("userId:" + userId);
			
			// 서버에 요청
			$.ajax({
				type: 'post'
				,url: '/subscribe/start'
				, data: {'editionId': editionId}
				, success: function(data){
					if (data.result == 'success'){
						alert("구독이 취소되었습니다.");
						location.reload();
					}
				}
				, error: function(e){
					
				}
			}); // cancel subscribe ajax close
			
		}); // cancelSubscribeBtn close
		
	}); // document close
</script>