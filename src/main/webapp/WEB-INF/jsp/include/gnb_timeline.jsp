<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<div>
	<div class="d-flex justify-content-around align-items-center mt-3">
		<%-- 검색창 --%>
		<div class="input-group col-4">
			<input type="text" id="keywordInput" name="keyword"  class="form-control" placeholder="Please enter keyword.">
			<a href="#" id="searchBtn" type="button" class="btn input-group-btn btn-dark">search</a>
		</div>
		<%-- 로고 --%>
		<div class="col-4">
			<div class="text-center">
				<a id="timelineLogo" href="/timeline/view"><img src="/static/images/logo1.png" alt="로고"></a><br>
			</div>
		</div>
		<%-- sign in / user 정보 --%>
		<div class="col-4 d-flex justify-content-center align-items-center">
			<%-- 로그인 시 --%>
			<c:if test="${not empty userId}">
				<div>
					 <div class="ml-4">
					 	<strong><span>Hello, </span><a id="userProfileMove" href="/user/profile_view?userId=${userId}">${userNickname}</strong></a>
					 </div>
					<%-- userRole 검사 -> publiser만 보이게 --%>
					<c:if test="${userInfo.role eq 'publisher'}">
						<a href="/edition/create_view" id="editionCreateBtn" class="btn mt-1"><img src="/static/images/startpublishingbtn.png" alt="발행시작btn"></a>
					</c:if>
				</div>
			</c:if>
			<%-- 비로그인 시 --%>
			<c:if test="${empty userId}">
				<div>
					<a href="/user/sign_in_view" id="signInUpUrl">로그인/회원가입</a>
				</div>
			</c:if>
		</div>
	</div>
</div>	

<script>
	$(document).ready(function(){
		// 검색하기
		$('#searchBtn').on('click', function(e){
			e.preventDefault();
			
			// let search = document.getElementById("searchBtn");
			// alert(search.href);
			
			let keyword = $('#keywordInput').val().trim();
			// alert(keyword);
			if (keyword == ''){
				alert("검색어를 입력하세요.");
			}
			
			location.href="/timeline/view?keyword="+keyword;
			
			console.log("주소:" + location.href);
			
			// 서버에 요청 -> x
			/* $.ajax({
				type: 'GET'
				, url: 'timeline/search'
				, data: {"keyword" : keyword}
				, success: function(data){
				if(data.result == 'success'){
					location.href= '/timeline/view';
				}
				}
			, error: function(e){
				
			}
				
			}); // search ajax close */
			
		}); // searchBtn close
		
		
	}); // document close

</script>