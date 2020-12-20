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
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/assets/css/join.css"/>">
<title>Join</title>
</head>
<body>
	<div class="layer">
		<div class="layer_inner">
			<div class="content">
				 <sf:form action="${pageContext.request.contextPath}/join.do" method="get" name="join" modelAttribute="user">
 					<input type="hidden" name="${_csrf.parameterName }"
						value="${_csrf.token }" />
					<table>
						<tr>
							<td id = td_username><sf:input class="input_join" placeholder="아이디"
								maxLength="20" type="text" id="username" name="username"
								path = "username" value="${username }" onchange="onchangeUsernameField(this)" tabindex="1"/><br>
								<sf:errors path="username" class="error"/></td>
						</tr>
						<tr>
							<td id = td_email><sf:input class="input_join" placeholder="이메일"
								maxLength="100" type="text" id="email" name="email"
								path = "email" value="${email }" onchange="onchangeEmailField(this)" tabindex="2"/><br>
								<sf:errors path="email" class="error"/></td>
						</tr>						
						<tr>
							<td id = td_password><sf:input class="input_join" placeholder="비밀번호"
								maxLength="20" type="password" id="password" name="password"
								path = "password" value="${password }" onchange="onchangePasswordField(this)" tabindex="3"/><br>
								<sf:errors path="password" class="error"/></td>
						</tr>
						 
 						<tr>
							<td id = td_confirmPassword><input class="input_join" placeholder="비밀번호 확인"
								maxLength="20" type="password" id="confirmPassword" name="confirmPassword" value="${confirmPassword }" onchange="onchangeConfirmPasswordField(this)" tabindex="4"/><br>
								<c:if test="${not empty errorMsg }">
									<span id = "confirmPassword.errors" class="error">${errorMsg }</span>
						</c:if>
							</td>
						</tr> 
						<tr>
							<td><input class="Button-JOIN" type="submit" id="join"
								name="join" value="회원가입" tabindex="5" /></td>							
						</tr>
						<tr>
						</tr>
					</table>
				</sf:form>
			</div>
		</div>
	</div>
</body>
<script>
	function onchangeUsernameField(obj) {
		var fieldName = 'username';
		checkAsyncJoinVaild(fieldName);
	}

	function onchangeEmailField(obj) {
		var fieldName = 'email';
		checkAsyncJoinVaild(fieldName);
	}
	
	function onchangePasswordField(obj) {
		var fieldName = 'password';
		checkAsyncJoinVaild(fieldName);
	}
	
	function onchangeConfirmPasswordField(obj) {
		var fieldName = 'confirmPassword';
		checkAsyncJoinVaild(fieldName);
	}
	
	function checkAsyncJoinVaild(fieldName) {
		var xhttp = new XMLHttpRequest();
		var username = document.getElementById('username').value;	
		var email = document.getElementById('email').value;
		var password =  document.getElementById('password').value;
		var confirmPassword =  document.getElementById('confirmPassword').value;
		
		//xhttp.open('POST','http://localhost:8106/picturerepository/async-valid.do',true);
		xhttp.open('POST','${pageContext.request.contextPath}/async-valid.do',true);
		//onreadystatechange : XMLHttpRequest 객체의 readyState 프로퍼티 값이 변할 때 자동으로 호출되는 함수.
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == xhttp.DONE) {
				if (xhttp.status == 200 || xhttp.status == 201) {
					var param = JSON.parse(xhttp.responseText);
					//console.log(xhttp.responseText);
					if (document.getElementById(fieldName + '.errors')) {
						updateErrorSpanMessage(fieldName, param.validErrorMessage)//이미 에러메세지가 존재한다면 메세지 업데이트
					} else {
						createErrorSpanMessage(fieldName, param.validErrorMessage);//에러메세지가 존재하지 않는 경우 동적 삽입
					}
				} else {
					console.error(xhttp.responseText);
				}
			}
		}
		var sendParam = "modelMemberVar=" + fieldName + "&username=" + username + "&email=" + email+"&password="+password+"&confirmPassword="+confirmPassword;
		xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xhttp.send(sendParam);
	}
	
	function updateErrorSpanMessage(fieldName, validErrorMessage) {
		document.getElementById(fieldName + '.errors').innerHTML = validErrorMessage;
	}

	function createErrorSpanMessage(fieldName, validErrorMessage) {
		var errorSpan = document.createElement('span');
		errorSpan.setAttribute('id', fieldName + '.errors');
		errorSpan.innerHTML = validErrorMessage;
		document.getElementById('td_' + fieldName).appendChild(errorSpan);
		document.getElementById(fieldName + '.errors').setAttribute('class','error');
	}
</script>
</html>