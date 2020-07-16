<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token }" />
<meta name="_csrf_header" content="${_csrf.headerName }" />
<link rel="stylesheet"
	href="<c:url value="/resources/assets/css/join.css"/>">
<title>Join</title>
</head>
<body>
	<div class="layer">
		<div class="layer_inner">
			<div class="content">
				 <sf:form action="${pageContext.request.contextPath}/join.do" method="post" name="join" modelAttribute="user">
 					<input type="hidden" name="${_csrf.parameterName }"
						value="${_csrf.token }" />
					<table>
						<tr>
							<td><sf:input class="input_join" placeholder="아이디"
								maxLength="20" type="text" id="username" name="username"
								path = "username" value="${username }" tabindex="1"/><br>
								<sf:errors path="username" class="error"/></td>
						</tr>
						<tr>
							<td><sf:input class="input_join" placeholder="이메일"
								maxLength="100" type="text" id="email" name="email"
								path = "email" value="${email }" tabindex="1"/><br>
								<sf:errors path="email" class="error"/></td>
						</tr>						
						<tr>
							<td><sf:input class="input_join" placeholder="비밀번호"
								maxLength="20" type="password" id="password" name="password"
								path = "password" value="${password }" tabindex="2"/><br>
								<sf:errors path="password" class="error"/></td>
						</tr>
						 
 						<tr>
							<td><input class="input_join" placeholder="비밀번호 확인"
								maxLength="20" type="password" id="passwordConfirm" name="passwordConfirm" value="${passwordConfirm }" tabindex="2"/>
							</td>
						</tr> 
						<c:if test="${not empty errorMsg }">
							<tr>
								<td class="error">${errorMsg }</td>
							</tr>
						</c:if>
						<tr>
						</tr>
						<tr>
							<td><input class="Button-JOIN" type="submit" id="join"
								name="join" value="회원가입" tabindex="3" /></td>
						</tr>
						<tr>
						</tr>
					</table>
				</sf:form>
			</div>
		</div>
	</div>
</body>
</html>