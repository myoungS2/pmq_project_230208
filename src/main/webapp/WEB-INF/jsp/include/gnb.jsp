<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="d-flex justify-content-between align-items-center">
		<%-- 로고 --%>
		<div class="mt-3">
			<a id="gnbLogo" href="/timeline/view">pmq</a><br>
		</div>
		<%-- sign in / user 정보 --%>
			<%-- 로그인 시 --%>
		<div class="mt-3 mb-3">	
			<c:if test="${not empty userId}">
				<div>
					<strong><span>Hello, </span><a id="userProfileMove" href="/user/profile_view?userId=${userId}">${userNickname}</strong></a>
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