<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token }" />
<meta name="_csrf_header" content="${_csrf.headerName }" />
<link rel="stylesheet"
	href="<c:url value="/resources/assets/css/login.css"/>">
<title>Login</title>
</head>
<body>
	<div class="layer">
		<div class="layer_inner">
			<div class="content">
				<form action="<c:url value="/login" />" method="post" name="login">
					<input type="hidden" name="${_csrf.parameterName }"
						value="${_csrf.token }" />
					<table>
						<tr>
							<td></td>
						</tr>
						<tr>
							<td><h3>
									<span class="intro_span">짤 저장소에 오신 것을 환영합니다!</span>
								</h3></td>
						</tr>
						<tr>
							<td><h3>
									<span class="intro_span">언제 어디서든 자신이 원하는 짤을 접해보세요!!</span>
								</h3></td>
						</tr>
						<tr>
							<td><input class="input_login" placeholder="아이디"
								maxLength="255" type="text" id="username" name="username"
								value="${username }" tabindex="1"></td>
						</tr>
						<tr>
							<td><input class="input_login" placeholder="비밀번호"
								maxLength="255" type="password" id="password" name="password"
								value="${password }" tabindex="2"></td>
						</tr>
						<tr>
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
						</tr>
						<tr>
							<td><input class="Button-LOGIN" type="submit" id="login"
								name="login" value="로그인" tabindex="3" /></td>
						</tr>
						<tr>
						</tr>
					</table>
				</form>
				<form action="<c:url value="/join" />" method="POST" name="join">
					<input type="hidden" name="${_csrf.parameterName }"
						value="${_csrf.token }" />
					<table>
						<tr>
							<td><button class="Button-JOIN" type="submit" id="join"
									name="join" tabindex="4">회원가입</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>