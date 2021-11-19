<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<!-- Wrapper -->
	<div id="wrapper">
		<!-- Main -->
		<section id="main">
			<!-- Thumbnails -->
			<section id= "imageSection" class="thumbnails">
					<c:if test="${not empty noData}">
						<div style = "width: 100%;"><h3 id = "noData">${noData}</h3></div>
					</c:if>
			</section>
		</section>
		<!-- nav -->
		<nav id = "nav" aria-label="Page navigation example">
 			<ul id = "nav_ul" class="pagination">
			</ul>
		</nav>
		<!-- Footer -->
 		<footer id="footer">
 			<p>Picture Repository</p>
		</footer>

	</div>
	<!-- Scripts -->
	<script>
	var jsonParam = '${searchDataMap}';
	/* var jsonParam = '${searchDataMap}'; */
	/* var jsonParam = '<c:out value='${searchDataMap}' escapeXml = "false"/>'; */
	var imageLikeMap = new Map();//좋아요 클릭시 해당 버튼의 이미지 KEY 값을 찾기 위한 Map 생성
/* 	const burger = document.querySelector(".burger");
	const nav = document.querySelector(".nav-links");
	const navlinks = document.querySelectorAll(".nav-links li");
	const navAnimation = () => {
		  navlinks.forEach((link, index) => {
		    if (link.style.animation) {
		      link.style.animation = "";
		    } else {
		      link.style.animation = `navLinkFade 0.5s ease forwards ${
		        index / 7 + 0.5
		      }s`;
		    }
		  });
		};
		const handleNav = () => {
		  nav.classList.toggle("nav-active");
		  navAnimation();
		  burger.classList.toggle("toggle");
		};
		const navSlide = () => {
		  burger.addEventListener("click", handleNav);
		};

		const setNavTransition = (width) => {
		  if (width > 768) {
		    nav.style.transition = "";
		  } else {
		    nav.style.transition = "transform 0.5s ease-in";
		  }
		};

		const handleResize = () => {
		  const width = event.target.innerWidth;
		  setNavTransition(width);
		};

		const init = () => {
		  window.addEventListener("resize", handleResize);
		  navSlide();
		};

		init(); */

	window.addEventListener('DOMContentLoaded', function(){
		if(jsonParam=='')
			return;

		//jsonParam = jsonParam.replace(/&#034;/g,'"');
		if(jsonParam!=''){
			jsonParam = JSON.parse(jsonParam);
		}

		loadImage();
		loadPageLink();
	});

	function loadImage(){
		var searchData = jsonParam.imageData;

		if(searchData==null || searchData=='' || searchData=='undefined' || searchData.length == 0)
			return;

		for(var i = 0 ; i < 4 ; i++){
			var dynamic_div = document.createElement('div');
			dynamic_div.setAttribute('id', 'div_'+i);
			document.getElementById('imageSection').appendChild(dynamic_div);
		}
		for(var i = 0 ; i < searchData.length ; i++){
			var divIndex = i%4;
			var dynamic_div_parent = document.createElement('div');
			var dynamic_div_child = document.createElement('div');
			var dynamic_a = document.createElement('a');
			var dynamic_img = document.createElement('img');
			var dynamic_btn = document.createElement('button');
			var dynamic_i = document.createElement('i');
			//var imageViewURL = '${pageContext.request.contextPath}/move/imageView/?fileId='+searchData[i].image+'&username=${pageContext.request.userPrincipal.name}';
			//var imageViewURL = '${pageContext.request.contextPath}/move/imageView/?bulletinId='+searchData[i].bulletinId+'&username=${pageContext.request.userPrincipal.name}';
			var imageViewURL = '${pageContext.request.contextPath}/bulletinboards/'+searchData[i].bulletinId+'/?username=${pageContext.request.userPrincipal.name}';
			//console.log("imageViewURL:"+imageViewURL);
			dynamic_i.style.color = (searchData[i].like == 'Y') ? 'red' : 'black';
			dynamic_i.style.fontSize = '30px';
			dynamic_div_parent.setAttribute('id', 'div_parent_'+i);
			dynamic_div_child.setAttribute('id', 'div_child_'+i);
			dynamic_a.setAttribute('id', 'a_'+i);
			dynamic_img.setAttribute('id', 'img_'+i);
			dynamic_btn.setAttribute('id', 'btn_like_'+i);
			dynamic_i.setAttribute('id', 'i_like_'+i);
			//dynamic_a.setAttribute('href', 'resources/images/fulls/'+searchData[i].image);
			//dynamic_a.setAttribute('href', imageViewURL);//Image View 이동 추가
			dynamic_img.setAttribute('src', '${pageContext.request.contextPath}/resources/images/thumbs/'+searchData[i].image);
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
			document.getElementById('a_'+i).href = imageViewURL;
			document.getElementById('a_'+i).target = "_blank";
			document.getElementById('a_'+i).rel = "noopener";
			imageLikeMap.set('btn_like_'+i, searchData[i].image);//좋아요 클릭시 해당 버튼의 이미지 KEY 값을 찾기 위한 Map 생성
		}
	}

	function loadPageLink(){
		var startPage = jsonParam.startPage;
		var endPage = jsonParam.endPage;
		var isPreviousPage = jsonParam.isPreviousPage;
		var isNextPage = jsonParam.isNextPage;
		var search = document.getElementById('search').value;

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
		var previousUrlValue = (isPreviousPage == false) ? '#none' : '${pageContext.request.contextPath}/bulletinboards?search='+search + '&page=' + (startPage-1);
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
			var urlValue = '${pageContext.request.contextPath}/bulletinboards?'+sendParam;
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
		var nextUrlValue = (isNextPage == false) ? '#none' : '${pageContext.request.contextPath}/bulletinboards?search='+search + '&page=' + (endPage+1);
		document.getElementById('nav_end_a').setAttribute('href',nextUrlValue);
		document.getElementById('nav_end_a').innerHTML = '>';
	}

	function onClickAsyncLike(){
		var xhttp = new XMLHttpRequest();
		var username = '${pageContext.request.userPrincipal.name}';
		var fileId = imageLikeMap.get(this.id);
		var strArray = (this.id).split('_');
		var likeHeart = document.getElementById('i_'+strArray[1]+'_'+strArray[2]);

		xhttp.open('POST','${pageContext.request.contextPath}/async-like.do', true);
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == xhttp.DONE) {
				if (xhttp.status == 200 || xhttp.status == 201) {
					var param = JSON.parse(xhttp.responseText);
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
		/* var sendParam = "username=" + username + "&fileId=" + fileId; */
		sendParam = "fileId=" + fileId;
		xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xhttp.send(sendParam);
	}

	</script>