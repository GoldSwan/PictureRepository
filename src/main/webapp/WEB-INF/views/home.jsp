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
<body>javascript:document.getElementById('logout').submit()

	<!-- Wrapper -->
	<div id="wrapper">
		<!-- Header -->
		<header id="header">
			<div class="container">
					<c:if test="${pageContext.request.userPrincipal.name != null }">
							<div style = "text-align:center">
								<form class="form-inline" method="get"
									action="<c:url value="/search" />">
									<input id = "search" class="form-control mr-sm-2 search-input" type="search"
										placeholder="검색" value="${search}" name="search" style="width:85%;">
									<input type="hidden" name="page" value='1'>	​​​​​​​
									<button class="btn btn-search my-2 my-sm-0" type="submit" style = "background-color:white">
										<i class="fa fa-search"></i>
									</button>
								</form>
								<form id="logout" action="<c:url value="/logout" />"
									method="post" style = "margin:0 0 0 0;height:0">
									<input type="hidden" name="${_csrf.parameterName }"
										value="${_csrf.token }" /> ​​​​​​​
								</form>
							</div>
							<div style = "text-align:right">
								<a class="btn btn-lg btn-primary" href= "javascript:document.getElementById('logout').submit()">로그아웃</a>
								<a class="btn btn-lg btn-primary" href= "<c:url value="/move/imageFileUpload?username=${pageContext.request.userPrincipal.name}" />">업로드</a>
								<%-- <a href="javascript:document.getElementById('btnLoginUserInfo').onclick()" class="btn btn-secondary btn-lg disabled" role="button" aria-disabled="true">${pageContext.request.userPrincipal.name}</a> --%>\
								<a class="btn btn-lg btn-primary" href="javascript:document.getElementById('btnLoginUserInfo').onclick()">${pageContext.request.userPrincipal.name}</a>	
								<button id = 'btnLoginUserInfo' onclick="openLoginUserPopup()" style = "display:none"></button>
							</div>			
					</c:if>
			</div>
			<span class="avatar"><img src="resources/images/avatar.jpg"
				alt="" /></span>
			<h1>
				<strong>원하는 사진을 검색하세요!</strong><br/>
				<strong>자신만의 개성있는 사진을 올려보세요!</strong>
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
<!-- 	 			<div>
					<div  class = "like-btn-parent">
					<a href="resources/images/fulls/01.jpg"> <img
						src="resources/images/thumbs/01.jpg" alt="" />						
					</a>					
					<div class = "like-btn-child">
						<button type = "button">
						<i class="fa fa-heart-o" aria-hidden="true" style = "color:white;background-color:black">		
						</i>
						</i></button>
					</div>
					</div>
				</div> -->
			</section>
		</section>
		<!-- nav -->
		<nav id = "nav" aria-label="Page navigation example">
 			<ul id = "nav_ul" class="pagination">
<%-- 				<li class="page-item"><a class="page-link" href="#">&lt;</a></li>
				<li class="page-item"><a class="page-link" href="<c:url value="/search?search=&page=1"/>">1</a></li>
				<li class="page-item"><a class="page-link" href="<c:url value="/search?search=&page=2"/>">2</a></li>
				<li class="page-item"><a class="page-link" href="<c:url value="/search?search=&page=3"/>">3</a></li>
				<li class="page-item"><a class="page-link" href="#">&gt;</a></li> --%>
			</ul> 
		</nav>
		<!-- Footer -->
		<footer id="footer">
<!-- 			<button class="btn btn-lg btn-primary" type="button" style = "height:47px;" onclick="changeImage()">
				<i><b>전환테스트</b></i>
			</button> -->
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
	var imageLikeMap = new Map();//좋아요 클릭시 해당 버튼의 이미지 KEY 값을 찾기 위한 Map 생성
	window.addEventListener('DOMContentLoaded', function()
	{		
		loadImage();
		loadPageLink();
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
			var dynamic_div_parent = document.createElement('div');
			var dynamic_div_child = document.createElement('div');	
			var dynamic_a = document.createElement('a');
			var dynamic_img = document.createElement('img');
			var dynamic_h3 = document.createElement('h3');
			var dynamic_btn = document.createElement('button');
			var dynamic_i = document.createElement('i');		
			dynamic_i.style.color = (searchData[i].like == 'Y') ? 'red' : 'black';
			dynamic_i.style.fontSize = '30px';
			dynamic_div_parent.setAttribute('id', 'div_parent_'+i);
			dynamic_div_child.setAttribute('id', 'div_child_'+i);
			dynamic_a.setAttribute('id', 'a_'+i);	
			dynamic_img.setAttribute('id', 'img_'+i);
			dynamic_btn.setAttribute('id', 'btn_like_'+i);
			dynamic_i.setAttribute('id', 'i_like_'+i);
			//dynamic_h3.setAttribute('id', 'h3_'+i);			
			dynamic_a.setAttribute('href', 'resources/images/fulls/'+searchData[i].image);
			dynamic_img.setAttribute('src', 'resources/images/thumbs/'+searchData[i].image);
			//dynamic_h3.innerHTML = '동적사진'+(i+1)+' 추가테스트';
			dynamic_btn.setAttribute('type', 'button');		
			document.getElementById('div_'+divIndex).appendChild(dynamic_div_parent);
			document.getElementById('div_parent_'+i).appendChild(dynamic_a);
			document.getElementById('a_'+i).appendChild(dynamic_img);
			document.getElementById('div_parent_'+i).appendChild(dynamic_div_child);
			document.getElementById('div_child_'+i).appendChild(dynamic_btn);
			document.getElementById('btn_like_'+i).appendChild(dynamic_i);
			document.getElementById('i_like_'+i).setAttribute('class', 'fa fa-heart');
			document.getElementById('i_like_'+i).setAttribute('aria-hidden', 'false');	
			document.getElementById('btn_like_'+i).addEventListener( "click", onClickAsyncLike);
			imageLikeMap.set('btn_like_'+i, searchData[i].image);//좋아요 클릭시 해당 버튼의 이미지 KEY 값을 찾기 위한 Map 생성
			//document.getElementById('a_'+i).appendChild(dynamic_h3);
		}
	}
	
/* 	function changeImage(){
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
	} */
	
	function loadPageLink(){
		var startPage = ${startPage};
		var endPage = ${endPage};
		var isPreviousPage = ${isPreviousPage};
		var isNextPage = ${isNextPage};
		var search = document.getElementById('search').value;
		
		console.log("endPage:"+endPage);
		if(endPage==null || endPage=='' || endPage=='undefined')
			return;
		var start_li = document.createElement('li');
		var start_a = document.createElement('a');
		start_li.setAttribute('id','nav_start_li');
		start_a.setAttribute('id','nav_start_a');	
		document.getElementById('nav_ul').appendChild(start_li);
		document.getElementById('nav_start_li').appendChild(start_a);
		document.getElementById('nav_start_li').setAttribute('class','page-item');
		document.getElementById('nav_start_a').setAttribute('class','page-link');
		var previousUrlValue = (isPreviousPage == false) ? '#none' : '${pageContext.request.contextPath}/search?search='+search + '&page=' + (startPage-1);
		document.getElementById('nav_start_a').setAttribute('href',previousUrlValue);
		document.getElementById('nav_start_a').innerHTML = '<';
		
		for(var i = startPage ; i <= endPage ; i++){
			var dynamic_li = document.createElement('li');
			var dynamic_a = document.createElement('a');
			dynamic_li.setAttribute('id','nav_li'+i);
			dynamic_a.setAttribute('id','nav_a'+i);
			document.getElementById('nav_ul').appendChild(dynamic_li);
			document.getElementById('nav_li'+i).appendChild(dynamic_a);
			document.getElementById('nav_li' + i).setAttribute('class','page-item');
			document.getElementById('nav_a' + i).setAttribute('class','page-link');
			var page = i;
			var sendParam = 'search=' + search + '&page=' + page;
			var urlValue = '${pageContext.request.contextPath}/search?'+sendParam;
			document.getElementById('nav_a' + i).setAttribute('href',urlValue);
			document.getElementById('nav_a' + i).innerHTML = page;
		}
		
		var end_li = document.createElement('li');
		var end_a = document.createElement('a');
		end_li.setAttribute('id','nav_end_li');
		end_a.setAttribute('id','nav_end_a');	
		document.getElementById('nav_ul').appendChild(end_li);
		document.getElementById('nav_end_li').appendChild(end_a);
		document.getElementById('nav_end_li').setAttribute('class','page-item');
		document.getElementById('nav_end_a').setAttribute('class','page-link');
		var nextUrlValue = (isNextPage == false) ? '#none' : '${pageContext.request.contextPath}/search?search='+search + '&page=' + (endPage+1);
		document.getElementById('nav_end_a').setAttribute('href',nextUrlValue);
		document.getElementById('nav_end_a').innerHTML = '>';
	}
	
	function onClickAsyncLike(){
		var xhttp = new XMLHttpRequest();
		var username = '${pageContext.request.userPrincipal.name}';
		var fileId = imageLikeMap.get(this.id);
		var strArray = (this.id).split('_');
		var likeHeart = document.getElementById('i_'+strArray[1]+'_'+strArray[2]);
		
		//btn_like_
		//i_like_
		xhttp.open('POST','${pageContext.request.contextPath}/async-like.do', true);
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == xhttp.DONE) {
				if (xhttp.status == 200 || xhttp.status == 201) {
					var param = JSON.parse(xhttp.responseText);
					//console.log(xhttp.responseText);
					if (param.like == 'Y') {
						likeHeart.style.color = 'red';
					} else {
						likeHeart.style.color = 'black';
					}
				} else {
					console.error(xhttp.responseText);
				}
			}
		}
		var sendParam = "username=" + username + "&fileId=" + fileId;
		xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xhttp.send(sendParam);
	}
	
	function openLoginUserPopup(){
		window.open('${pageContext.request.contextPath}/loginUserinfoPopup', 'popup01', 'width=300, height=400, scrollbars= 0, toolbar=0, menubar=no, status=no,  location=no');
	}
	
	</script>
</body>
</html>