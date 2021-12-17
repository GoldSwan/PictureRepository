<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<div id="wrapper">
		<div id="header">
			<h3>사진 올리기</h3>
		</div>
		<form id="uploadForm" action="<c:url value="/bulletinboards/newbulletinboard" />"
			method="post" enctype="multipart/form-data">
			<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }" />
			<section id="uploadSection">
				<div>
					<div>
						<span>파일 용량/갯수 : </span><span id = "fileStatusSpan"></span>
					</div>
					<div id=fileDiv>
						<input id = "inputMultipleImage" type="file" class="form-control-file" multiple="multiple"
							name="file" accept="image/gif, image.jpeg, image/png, image/jpg" onchange="changeFileStatus(this); previewImage(this)"  value = "put"/>
					</div>
					<div>
						<c:if test="${not empty uploadMultiErrorMsg }">
							<span id="uploadFormMulti.errors" class="error">${uploadMultiErrorMsg }</span>
						</c:if>
					</div>
				</div>
			</section>
			<section id="multipleContainerSection" class = "thumbnails">
				<div id="column">
				</div>
			</section>
			<section id="inputSection">
				<div class = "inputText">
					<input type="text" name="title" placeholder="제목" value="${title}" />
				</div>
				<div class = "inputText">
					<textarea placeholder="내용" name="content">${content}</textarea>
				</div>
				<div class = "inputText">
					<input hidden="hidden"/>
					<input id="tagId" type="text" name="post_tag" class="form-control" placeholder="태그"
					onkeyup="if(window.event.keyCode==13||window.event.keyCode==32){(enterTag())}" style = "display:inline"/>
					<div id="tag" class="bootstrap-tagsinput" style = "padding-left:10%;padding-right:10%;"></div>
				</div>
				<div class = "inputText">
					<input type="hidden" name="username" value="${username}" />
				</div>
				<div class = "radio">
					<input type="radio" name="publicRange" value="A" checked = "checked"><span>전체 공개</span>
					<input type="radio" name="publicRange" value="C"><span>비공개</span>
				</div>
				<div class = "inputText">
					<input type="submit" class="btn btn-primary" onclick="updateCheck()" value = "저장">
				</div>
			</section>
		</form>

	</div>
	<script>
	function updateCheck() {
		 var  bulletinId = '${bulletinId}';
		 if (bulletinId == null) return;

		 document.getElementById("uploadForm").action = "${pageContext.request.contextPath}/bulletinboards/newbulletinboard/"+bulletinId;
	}
	
	window.addEventListener('DOMContentLoaded', function(){
  		var jsonParam = '${searchDataMap}';
  			
  		if(jsonParam=='')
  			return;

  		if(jsonParam!=''){
  			jsonParam = JSON.parse(jsonParam);
  		}
 
  		loadSavedImage(jsonParam);
  		
  	});
	
	function loadSavedImage(jsonParam){
		
		const searchData = jsonParam.imageData;
		const column = document.getElementById("column");
		const $colDiv = document.getElementById("column");
        
		for(var i = 0 ; i < searchData.length ; i++){
            const $imgDiv = document.createElement("div");   
            const $img = document.createElement("img");
            
            $img.classList.add("previmage");
            $imgDiv.appendChild($img);
			
            $img.src = '${pageContext.request.contextPath}/resources/images/thumbs/'+searchData[i].image;        
            $imgDiv.style.width = "117.02px";
            $imgDiv.style.height = "160px";
            $img.style.width = "117.02px";
            $img.style.height = "160px";
            
            $colDiv.appendChild($imgDiv);	      
            searchData[i].image
		}
	}
	
	</script>
