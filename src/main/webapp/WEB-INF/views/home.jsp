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
										placeholder="검색" name="search" style="width:50%;">
									<input type="hidden" name="page" value='1'>	​​​​​​​
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
			<section id= "imageSection" class="thumbnails">
					<c:if test="${not empty noData}">
						<div style = "width: 100%;"><h3 id = "noData">${noData}</h3></div>
					</c:if>
<!-- 				<div>
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
				</div> -->
			</section>
		</section>
		<!-- nav -->
		<nav id = "nav" aria-label="Page navigation example">
			<ul class="pagination">
				<li class="page-item"><a class="page-link" href="#">&lt;</a></li>
				<li class="page-item"><a class="page-link" href="<c:url value="/search?search=&page=1"/>">1</a></li>
				<li class="page-item"><a class="page-link" href="<c:url value="/search?search=&page=2"/>">2</a></li>
				<li class="page-item"><a class="page-link" href="<c:url value="/search?search=&page=3"/>">3</a></li>
				<li class="page-item"><a class="page-link" href="#">&gt;</a></li>
			</ul>
		</nav>
		<!-- Footer -->
		<footer id="footer">
			<button class="btn btn-lg btn-primary" type="button" style = "height:47px;" onclick="changeImage()">
				<i><b>전환테스트</b></i>
			</button>
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
		//var param = searchData[0].image;
		//console.log(param);		
		if(searchData==null || searchData=='' || searchData=='undefined' || searchData.length == 0)
			return;

		for(var i = 0 ; i < 4 ; i++){
			var dynamic_div = document.createElement('div');
			dynamic_div.setAttribute('id', 'div_'+i);
			document.getElementById('imageSection').appendChild(dynamic_div);
		}
		for(var i = 0 ; i < searchData.length ; i++){
			console.log(searchData[i].image);
			var divIndex = i%4;			
			var dynamic_a = document.createElement('a');
			var dynamic_img = document.createElement('img');
			var dynamic_h3 = document.createElement('h3');
			dynamic_a.setAttribute('id', 'a_'+i);	
			dynamic_img.setAttribute('id', 'img_'+i);	
			dynamic_h3.setAttribute('id', 'h3_'+i);			
			dynamic_a.setAttribute('href', 'resources/images/fulls/'+searchData[i].image);
			dynamic_img.setAttribute('src', 'resources/images/thumbs/'+searchData[i].image);
			dynamic_h3.innerHTML = '동적사진'+(i+1)+' 추가테스트';
			
			document.getElementById('div_'+divIndex).appendChild(dynamic_a);
			document.getElementById('a_'+i).appendChild(dynamic_img);
			document.getElementById('a_'+i).appendChild(dynamic_h3);
		}
		
/* 		for(var i = 0 ; i < 4 ; i++){
			var DynamicDiv = document.createElement('div');
			DynamicDiv.setAttribute('id', 'div_'+i);
			document.getElementById('imageSection').appendChild(DynamicDiv);
		} */
		
/* 		//var div_1 = document.createElement('div');
		var a_1 = document.createElement('a');
		var img_1 = document.createElement('img');
		var h3_1 = document.createElement('h3');
		
		//div_1.setAttribute('id', 'div_1');		
		a_1.setAttribute('id', 'a_1');	
		img_1.setAttribute('id', 'img_1');	
		h3_1.setAttribute('id', 'h3_1');	
		
		a_1.setAttribute('href', 'resources/images/fulls/'+searchData.image0);
		img_1.setAttribute('src', 'resources/images/thumbs/'+searchData.image0);
		h3_1.innerHTML = '동적사진1 추가테스트';
		
		//document.getElementById('imageSection').appendChild(div_1);
		document.getElementById('div_1').appendChild(a_1);
		document.getElementById('a_1').appendChild(img_1);
		document.getElementById('a_1').appendChild(h3_1); */
	}
	
	function changeImage(){
		var img_0= document.getElementById('img_0');
		var searchData = ${searchData};
		img_0.setAttribute('src', 'resources/images/thumbs/'+searchData[7].image);
		var imageSection = document.getElementById('imageSection');
		var a_7= document.getElementById('a_7');
		var img_7= document.getElementById('img_7');
		var h3_7= document.getElementById('h3_7');
		a_7.remove();
		img_7.remove();
		h3_7.remove();
	}
	
	function loadPageLink(){
		
	}
	</script>
</body>
</html>