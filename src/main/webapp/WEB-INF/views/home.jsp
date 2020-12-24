<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
	<head>
		<title>Picture Repository</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" href="resources/assets/css/main.css" />
	</head>
	<body>

		<!-- Wrapper -->
			<div id="wrapper">		
		<!-- Header -->
					<header id="header">
							<div>
							<form method="get" action="<c:url value="/search" />">
								<input type="text" name="keyword" size = 5 />
​​​​​​​ 								<button type="submit"><b>검색</b></button>
							</form>							
							</div>
							<c:if test="${pageContext.request.userPrincipal.name != null }">
							<div style = "float:right;">
							<b>${pageContext.request.userPrincipal.name}</b>
							<a href="javascript:document.getElementById('logout').submit()"><b>로그아웃</b></a><br>
							<form id="logout" action="<c:url value="/logout" />" method="post">
								<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
							</form>								
							</div>	
							</c:if>								
						<span class="avatar"><img src="resources/images/avatar.jpg" alt="" /></span>
						<h1>This is <strong>Visualize</strong>, a responsive site template designed by <a href="http://templated.co">TEMPLATED</a><br />
						and released for free under the Creative Commons License.</h1>
						<ul class="icons">
							<li><a href="#" class="icon style2 fa-twitter"><span class="label">Twitter</span></a></li>
							<li><a href="#" class="icon style2 fa-facebook"><span class="label">Facebook</span></a></li>
							<li><a href="#" class="icon style2 fa-instagram"><span class="label">Instagram</span></a></li>
							<li><a href="#" class="icon style2 fa-500px"><span class="label">500px</span></a></li>
							<li><a href="#" class="icon style2 fa-envelope-o"><span class="label">Email</span></a></li>
						</ul>
					</header>

				<!-- Main -->
					<section id="main">
						<!-- Thumbnails -->
							<section class="thumbnails">
								<div>
									<a href="resources/images/fulls/01.jpg">
										<img src="resources/images/thumbs/01.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet1</h3>
									</a>
									<a href="resources/images/fulls/02.jpg">
										<img src="resources/images/thumbs/02.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet2</h3>
									</a>
							   </div>
								<div>
									<a href="resources/images/fulls/01.jpg">
										<img src="resources/images/thumbs/01.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet1</h3>
									</a>
									<a href="resources/images/fulls/02.jpg">
										<img src="resources/images/thumbs/02.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet2</h3>
									</a>
									
							   </div>							   
							    <div>
									<a href="resources/images/fulls/03.jpg">
										<img src="resources/images/thumbs/03.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet3</h3>
									</a>
									<a href="resources/images/fulls/04.jpg">
										<img src="resources/images/thumbs/04.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet4</h3>
									</a>
									<a href="resources/images/fulls/05.jpg">
										<img src="resources/images/thumbs/05.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet5</h3>
									</a>
									<a href="resources/images/fulls/06.jpg">
										<img src="resources/images/thumbs/06.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet6</h3>
									</a>
									<a href="resources/images/fulls/07.jpg">
										<img src="resources/images/thumbs/07.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet7</h3>
									</a>
									<a href="resources/images/fulls/01.jpg">
										<img src="resources/images/thumbs/01.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet1</h3>
									</a>
									<a href="resources/images/fulls/02.jpg">
										<img src="resources/images/thumbs/02.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet2</h3>
									</a>
									<a href="resources/images/fulls/03.jpg">
										<img src="resources/images/thumbs/03.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet3</h3>
									</a>
							   </div>
							    <div>									
									<a href="resources/images/fulls/04.jpg">
										<img src="resources/images/thumbs/04.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet4</h3>
									</a>
									<a href="resources/images/fulls/05.jpg">
										<img src="resources/images/thumbs/05.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet5</h3>
									</a>
									<a href="resources/images/fulls/06.jpg">
										<img src="resources/images/thumbs/06.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet6</h3>
									</a>
									<a href="resources/images/fulls/07.jpg">
										<img src="resources/images/thumbs/07.jpg" alt="" />
										<h3>Lorem ipsum dolor sit amet7</h3>
									</a>
								</div>								
							</section>
					</section>
				<!-- Footer -->
					<footer id="footer">
						<p>&copy; Untitled. All rights reserved. Design: <a href="http://templated.co">TEMPLATED</a>. Demo Images: <a href="http://unsplash.com">Unsplash</a>.</p>
					</footer>

			</div>
		<!-- Scripts -->
			<script src="resources/assets/js/jquery.min.js"></script>
			<script src="resources/assets/js/jquery.poptrox.min.js"></script>
			<script src="resources/assets/js/skel.min.js"></script>
			<script src="resources/assets/js/main.js"></script>

	</body>
</html>