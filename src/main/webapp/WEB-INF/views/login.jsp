<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="_csrf" content="${_csrf.token }" />
<meta name="_csrf_header" content="${_csrf.headerName }" />
<link rel="stylesheet" href="<c:url value="/resources/assets/css/login.css"/>">
<title>Login</title>
</head>
<body>
<div class="bg" style="margin-bottom: 0px">
	<form action="<c:url value="/login" />" method="post" name="login">
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
		<table border="0" bgcolor=#ffffff style="margin: auto; border-radius: 25px;">
			<tr>
				<td align=center>
					<table border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td width=500 align=center>
								<table border="0" cellSpacing=0 cellPadding=0
									bgcolor=#ffffff class="logintable">

									<tr>
										<td width="100%" height="30px" colspan="5" />
									</tr>

									<tr>
										<td width="10%"></td>
										<td class="label-login"
											style="padding-left: 30px; padding-right: 0px; border-bottom: 1px solid #808080; color: #808080;">ID</td>
										<td align="left" colspan="2"
											style="border-bottom: 1px solid #808080;">
												<input
												size="16" maxLength="10" type="text" id="username" name="username"
												onFocus="this.value=''" class="input_login" tabindex="2"></td>
										<td width="10%"></td>
									</tr>
									<tr>
										<td height="20px" colspan="5" />
									</tr>

									<tr>
										<td width="10%"></td>
										<td class="label-login"
											style="padding-left: 30px; padding-right: 0px; border-bottom: 1px solid #808080; color: #808080;">PASSWORD</td>
										<td align="left" colspan="2"
											style="border-bottom: 1px solid #808080;">
												<input
												size="16" maxLength="255" type="password" id="password"
												name="password" onFocus="this.select()" class="input_login"
												tabindex="3"></td>
										<td width="10%"></td>
									</tr>
									<tr>
										<td height="20px" colspan="5"></td>
									</tr>

									<c:if test="${not empty errorMsg }">
										<tr>
											<td width="10%"></td>
											<td class="error" colspan="3" align="center">${errorMsg }</td>
											<td width="10%"></td>
										</tr>
									</c:if>

									<c:if test="${not empty logoutMsg }">
										<tr>
											<td width="10%"></td>
											<td class="logout" colspan="3" align="center">${logoutMsg }</td>
											<td width="10%"></td>
										</tr>
									</c:if>

									<tr>
										<td height="20px" colspan="5"></td>
									</tr>
									<tr>
										<td width="10%"></td>
										<td align=center colspan="3">
											<input class="Button-LOGIN"
												type="submit" id="login" name="login" value="Login"
												tabindex="4" /></td>
										<td width="10%"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>