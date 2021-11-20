<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<div class="layer">
		<div class="layer_inner">
			<div class="content">
				<form action="<c:url value="/login" />" method="post" name="login">
					<input type="hidden" name="${_csrf.parameterName }"
						value="${_csrf.token }" />
					<table class="login_table">
						<tr>
							<td></td>
						</tr>
						<tr>
							<td><span class="intro_span">Picture Repository</span></td>
						</tr>
						<tr>
							<td><span class="intro_span2">언제 어디서든 사진을 불러올 수 있는 사진 저장소 플랫폼입니다.</span></td>
						</tr>						
						<tr></tr>
						<tr>
							<td><input class="input_login" placeholder="아이디"
								maxLength="20" type="text" id="username" name="username"
								value="${username }" tabindex="1"></td>
						</tr>
						<tr>
							<td><input class="input_login" placeholder="비밀번호"
								maxLength="20" type="password" id="password" name="password"
								value="${password }" tabindex="2"></td>
						</tr>
						<c:if test="${not empty errorMsg }">
							<tr>
								<td class="error">${errorMsg }</td>
							</tr>
						</c:if>

						<c:if test="${not empty logoutMsg }">
							<tr>
								<td class="logout">${logoutMsg }</td>
							</tr>
						</c:if>
						<tr>
							<td><input class="Button-LOGIN" type="submit" id="login"
								name="login" value="로그인" tabindex="3" /></td>
						</tr>
						<tr>
							<td><a class="a-JOIN" href= "<c:url value="/join" />">회원가입</a></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>