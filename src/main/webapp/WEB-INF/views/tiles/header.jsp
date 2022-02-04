<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

		<!-- Header -->
		<header id="header">
			<div class="container">
					<c:if test="${pageContext.request.userPrincipal.name != null }">
									<nav id = "headerNav">
										<ul class="nav-links">
											<li><a href= "<c:url value="/bulletinboards/newbulletinboard?username=${pageContext.request.userPrincipal.name}"/>">사진 올리기</a></li>
											<li><a href= "#none">${pageContext.request.userPrincipal.name}</a></li>
											<li><a href= "javascript:document.getElementById('logout').submit()">로그아웃</a></li>
										</ul>
										    <div class="burger">
        										<div class="line1"></div>
        										<div class="line2"></div>
        										<div class="line3"></div>
      										</div>
									 </nav>
								<div style = "text-align:center;display:inline-block;width:100%;">
								<form class="" method="get"
									action="<c:url value="/bulletinboards" />">
									        <select name="searchtype" style = "display:inline-block;width:18%;height:38px;background:white;color:black;font-weight:bold;">
											   <option value="title" <c:if test="${searchtype eq 'title'}"> selected </c:if>>제목</option>
											   <option value="content" <c:if test="${searchtype eq 'content'}"> selected </c:if>>내용</option>
											   <option value="tag" <c:if test="${searchtype eq 'tag'}"> selected </c:if>>태그</option>
										    </select>
											<input id = "search" class="form-control" type="search" placeholder="검색" value="${search}" name="search" style = "display:inline-block;width:62%;font-weight:bold;">
											<input type="hidden" name="page" value='1'>	​​​​​​​
											<button class="btn btn-search my-2" type="submit" style = "background-color:white;margin-bottom:.8rem!important">
												<i class="fa fa-search"></i>
											</button>
								</form>
								</div>
								<form id="logout" action="<c:url value="/logout" />"
										method="post" style = "margin:0 0 0 0;height:0">
										<input type="hidden" name="${_csrf.parameterName }"
											value="${_csrf.token }" /> ​​​​​​​
								</form>
					</c:if>
			</div>
			<!--
 			<ul class="icons">
				<li><a href="#" class="icon style2 fa-twitter"><span
						class="label">Twitter</span></a></li>
				<li><a href="#" class="icon style2 fa-facebook"><span
						class="label">Facebook</span></a></li>
				<li><a href="#" class="icon style2 fa-instagram"><span
						class="label">Instagram</span></a></li>
				<li><a href="#" class="icon style2 fa-500px"><span
						class="label">500px</span></a></li>
				<li><a href="#" class="icon style2 fa-envelope-o"><span
						class="label">Email</span></a></li>
			</ul> -->
		</header>