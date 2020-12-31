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
<link rel="stylesheet" href="resources/assets/css/bootstrap.min.css" />
</head>
<body>

	<!-- Wrapper -->
	<div id="wrapper">
		<!-- Header -->
		<header id="header">
			<div class="container">
				<div class="row">
					<c:if test="${pageContext.request.userPrincipal.name != null }">
						<div class="col-sm-6">
								<form class="form-inline" method="get"
									action="<c:url value="/search" />">
									<input class="form-control mr-sm-2 search-input" type="search"
										placeholder="검색" name="search" style="width:50%;"> ​​​​​​​
									<button class="btn btn-search my-2 my-sm-0" type="submit">
										<i class="fa fa-search"></i>
									</button>
								</form>
						</div>						
						<div class="col-sm-6">
							<div style="float: right;">
								<form id="logout" action="<c:url value="/logout" />"
									method="post">
									<input type="hidden" name="${_csrf.parameterName }"
										value="${_csrf.token }" /> ​​​​​​​
									<button class="btn btn-lg btn-primary" type="submit" style = "height:47px;">
										<i><b>로그아웃</b></i>
									</button>
								</form>
							</div>
							<div style="float: right;margin-right: 10px;">
							<a class="btn btn-lg btn-primary" href= "<c:url value="/move/imageFileUpload?username=${pageContext.request.userPrincipal.name}" />">업로드</a>
							</div>													
							<div style="float: right;margin-right: 10px;">
							<a href="#" class="btn btn-secondary btn-lg disabled" role="button" aria-disabled="true">${pageContext.request.userPrincipal.name}</a>
							</div>	
						</div>					
					</c:if>
				</div>
			</div>
			<span class="avatar"><img src="resources/images/avatar.jpg"
				alt="" /></span>
			<h1>
				This is <strong>Visualize</strong>, a responsive site template
				designed by <a href="http://templated.co">TEMPLATED</a><br /> and
				released for free under the Creative Commons License.
			</h1>
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
			</ul>
		</header>

		<!-- Main -->
		<section id="main">
			<!-- Thumbnails -->
			<section class="thumbnails">
				<div id = "div1">
					<a href="resources/images/fulls/4848eb41-50ba-4716-b2ef-8a781fd081da1597060791.jpg"> <img
						src="resources/images/fulls/4848eb41-50ba-4716-b2ef-8a781fd081da1597060791.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet1</h3>
					</a> <a href="resources/images/fulls/02.jpg"> <img
						src="resources/images/thumbs/02.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet2</h3>
					</a>
				</div>
				<div>
					<a href="resources/images/fulls/01.jpg"> <img
						src="resources/images/thumbs/01.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet1</h3>
					</a> <a href="resources/images/fulls/02.jpg"> <img
						src="resources/images/thumbs/02.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet2</h3>
					</a>

				</div>
				<div>
					<a href="resources/images/fulls/03.jpg"> <img
						src="resources/images/thumbs/03.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet3</h3>
					</a> <a href="resources/images/fulls/04.jpg"> <img
						src="resources/images/thumbs/04.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet4</h3>
					</a> <a href="resources/images/fulls/05.jpg"> <img
						src="resources/images/thumbs/05.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet5</h3>
					</a> <a href="resources/images/fulls/06.jpg"> <img
						src="resources/images/thumbs/06.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet6</h3>
					</a> <a href="resources/images/fulls/07.jpg"> <img
						src="resources/images/thumbs/07.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet7</h3>
					</a> <a href="resources/images/fulls/01.jpg"> <img
						src="resources/images/thumbs/01.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet1</h3>
					</a> <a href="resources/images/fulls/02.jpg"> <img
						src="resources/images/thumbs/02.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet2</h3>
					</a> <a href="resources/images/fulls/03.jpg"> <img
						src="resources/images/thumbs/03.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet3</h3>
					</a>
				</div>
				<div>
					<a href="resources/images/fulls/04.jpg"> <img
						src="resources/images/thumbs/04.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet4</h3>
					</a> <a href="resources/images/fulls/05.jpg"> <img
						src="resources/images/thumbs/05.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet5</h3>
					</a> <a href="resources/images/fulls/06.jpg"> <img
						src="resources/images/thumbs/06.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet6</h3>
					</a> <a href="resources/images/fulls/07.jpg"> <img
						src="resources/images/thumbs/07.jpg" alt="" />
						<h3>Lorem ipsum dolor sit amet7</h3>
					</a>
				</div>
			</section>
		</section>
		<!-- Footer -->
		<footer id="footer">
			<p>
				&copy; Untitled. All rights reserved. Design: <a
					href="http://templated.co">TEMPLATED</a>. Demo Images: <a
					href="http://unsplash.com">Unsplash</a>.
			</p>
		</footer>

	</div>
	<!-- Scripts -->
	<script src="resources/assets/js/jquery.min.js"></script>
	<script src="resources/assets/js/jquery.poptrox.min.js"></script>
	<script src="resources/assets/js/skel.min.js"></script>
	<script src="resources/assets/js/main.js"></script>
	<script>
	window.addEventListener('DOMContentLoaded', function()
	{
		loadImage();
	});
	
	function loadImage(){
		var searchData = ${searchData};
		//var searchData = "<c:out value='${searchData}'/>";
		var param = searchData.image0;
		console.log(param);
	}
	</script>
</body>
</html>