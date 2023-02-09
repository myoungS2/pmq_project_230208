<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>     
<div>
	<%-- user info --%>
	<div class="d-flex justify-content-around">
		<%-- user profile img --%>
		<div>
			<%-- 프로필 사진이 없을 때 --%>
			<c:if test="${empty userInfo.profileImgPath}">
				<div><img src="/static/images/profileIcon.png" alt="프로필아이콘" width="80" height="80"></div>
			</c:if>
			<%-- 프로필 사진이 있을 때 --%>
			<c:if test="${not empty userInfo.profileImgPath}">
				<div><img id="profileImg" src="${userInfo.profileImgPath}" alt="userProfileImg" width="80" height="80"></div>
			</c:if>
		</div>
		<div>
			<%-- user nickname --%>
			<div class="ml-3">
				<strong>${userInfo.nickname}</strong>
			</div>
			<%-- user website --%>
			<div class="ml-3">
				<a href="${userInfo.website}">${userInfo.website}</a>
			</div>
			<%-- user introduction --%>
			<div class="ml-3">
				${userInfo.introduce}
			</div>
			<%-- update profile btn(화면이동) --%>
			<div>
				<a href="/user/profile_update_view" id="updateProfileBtn" class="btn"><img src="/static/images/editprofilebtn.png"></a>
			</div>
		</div>
	</div>	
	<%-- subscribe list --%>
	<div>
		<small><strong>구독 리스트</strong></small>
	</div>
	<c:if test="${not empty interestViewList}">
		<div class="d-flex">
			<c:forEach var="interest" items="${interestViewList}">
				<div class="mr-3">
					<a><img src="${interest.edition.thumbnailPath}" alt="구독썸네일" width="150" height="200"></a>
				</div>
			</c:forEach>
		</div>
	</c:if>
	<%-- like list(관심리스트가 있는 경우에만)--%>
	<div>
		<small><strong>관심 리스트</strong></small>
	</div>
	<c:if test="${not empty thinkViewList}">
		<div class="d-flex">
			<c:forEach var="think" items="${thinkViewList}">
				<div class="mr-3">
					<a><img src="${think.edition.thumbnailPath}" alt="관심썸네일" width="150" height="200"></a>
				</div>
			</c:forEach>
		</div>
	</c:if>
</div>