<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
	<div id="wrapper">
        <section id="imageSection">
        	<div><a class="btn btn-primary" href= "javascript:document.getElementById('delete').submit()">삭제</a></div>    	
        	<form id="delete" action="<c:url value="/bulletinboards/newbulletinboard/${bulletinId}/delete"/>"
					method="post" style = "margin:0 0 0 0;height:0">
<!-- 					<input type="hidden" name = "_method" value = "put"/> -->
 					<input type="hidden" name="${_csrf.parameterName }"
										 value="${_csrf.token }" />​​​​​​​
			</form>
        	<div><span>제목 : <c:out value = "${title}"/></span></div>
        	<div><span>작성시간 : <c:out value = "${isrtDt}"/></span></div>
        	<div><span>작성자 : <c:out value = "${username}"/></span></div>
        	<div><span>내용 : <c:out value = "${content}"/></span></div>
        	<div><span>좋아요 횟수 : <c:out value = "${likeCnt}"/></span></div>       	
        	<div><span>좋아요 여부 : <c:out value = "${likeFlag}"/></span></div>
        </section>
	</div>
	<script>
	/* var jsonParam = '<c:out value='${searchDataMap}' escapeXml = "false"/>'; */
	window.addEventListener('DOMContentLoaded', function(){		
		var jsonParam = '${searchDataMap}';
		if(jsonParam=='')
			return;
		
		//jsonParam = jsonParam.replace(/&#034;/g,'"');
		console.log(jsonParam);
		if(jsonParam!=''){
			jsonParam = JSON.parse(jsonParam);
		}
	
		loadImage(jsonParam);
	});
	
	function loadImage(jsonParam){
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
			//var dynamic_btn = document.createElement('button');
			//var dynamic_i = document.createElement('i');
			var imageViewURL = '${pageContext.request.contextPath}/resources/images/originals/'+searchData[i].image;
			dynamic_div_child.style.height = '30px';
			//dynamic_i.style.color = 'red';
			//dynamic_i.style.fontSize = '30px';
			dynamic_div_parent.setAttribute('id', 'div_parent_'+i);
			dynamic_div_child.setAttribute('id', 'div_child_'+i);
			dynamic_a.setAttribute('id', 'a_'+i);	
			dynamic_img.setAttribute('id', 'img_'+i);
			//dynamic_btn.setAttribute('id', 'btn_like_'+i);
			//dynamic_i.setAttribute('id', 'i_like_'+i);		
			dynamic_img.setAttribute('src', '${pageContext.request.contextPath}/resources/images/fulls/'+searchData[i].image);
			//dynamic_btn.setAttribute('type', 'button');		
			document.getElementById('div_'+divIndex).appendChild(dynamic_div_parent);
			document.getElementById('div_parent_'+i).appendChild(dynamic_a);
			document.getElementById('a_'+i).appendChild(dynamic_img);
			document.getElementById('div_parent_'+i).appendChild(dynamic_div_child);
			//document.getElementById('div_child_'+i).appendChild(dynamic_btn);
			//document.getElementById('btn_like_'+i).appendChild(dynamic_i);
			//document.getElementById('i_like_'+i).setAttribute('class', 'fa fa-heart');
			//document.getElementById('i_like_'+i).setAttribute('aria-hidden', 'false');	
			document.getElementById('a_'+i).href = imageViewURL;
			document.getElementById('a_'+i).target = "_blank";
			document.getElementById('a_'+i).rel = "noopener";
			//imageLikeMap.set('btn_like_'+i, searchData[i].image);//좋아요 클릭시 해당 버튼의 이미지 KEY 값을 찾기 위한 Map 생성
			
		}
	}
	</script>