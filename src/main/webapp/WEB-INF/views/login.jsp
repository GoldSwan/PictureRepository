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
<%-- <link rel="icon" href="<c:url value="/favicon.ico"/>">	 --%>
<link rel="icon" href="resources/images/favicon.ico"> 
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
									<span class="intro_span">나의 사진 저장소에 오신것을 환영합니다!</span>
								</h3></td>
						</tr>
						<tr>
							<td><h3>
									<span class="intro_span">언제 어디서든 나만의 사진을 접해보세요!!</span>
								</h3></td>
						</tr>
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
</body>
</html>