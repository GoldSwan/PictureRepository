<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.badge:before{
	content: "#"
}

.badge {
	display: inline-block;
	min-width: 10px;
	padding: 4px 7px;
	font-size: 12px;
	font-weight: 550;
	line-height: 1;
	color: #2f2f2f;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	background-color: #ececec;
	border-radius: 5px;
	margin-right: 8px;
}
.bootstrap-tagsinput{	
	width: 100%;
	height: 34px;
	padding: 6px 12px;
	
}

.xClass{
 	font-size: 10px;
	color: #777;
	margin-left: 4px;
	cursor: pointer;
	padding: 3px;
}
</style>

	<div id="wrapper">
		<div id="header">
			<h3>사진 올리기</h3>
		</div>
		<form id="uploadForm" action="<c:url value="/uploadForm/multi" />"
			method="post" enctype="multipart/form-data">
<%-- 		<form id="uploadForm" action="<c:url value="/uploadForm/multi" />"
			method="post" enctype="multipart/form-data" onsubmit="return false"> --%>
			<!-- <div class="form-group"> -->
			<section id="uploadSection">
				<div>
					<div>
						<span>파일 용량/갯수 : </span><span id = "fileStatusSpan"></span>
					</div>
					<div id=fileDiv>
						<input hidden="hidden" />
						<input type="file" class="form-control-file" multiple="multiple"
							name="file" accept="image/gif, image.jpeg, image/png, image/jpg" onchange="changeFileStatus(this)"/>
					</div>
					<div>
						<c:if test="${not empty uploadMultiErrorMsg }">
							<span id="uploadFormMulti.errors" class="error">${uploadMultiErrorMsg }</span>
						</c:if>
					</div>
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
					<input id="tagId" type="text" name="post_tag" class="form-control" value placeholder="태그"
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
					<input type="submit" class="btn btn-primary" value="저장" />
				</div>
			</section>
		</form>
		<footer id="footer"> </footer>
	</div>
	<script>	
		function changeFileStatus(file){
			var totalFileSize = 0;
			var browser=navigator.appName;
			
	    	for(let iFile of file.files){
	    		if(/\.(jpeg|gif|png|jpg)$/i.test(iFile.name) == false){
	    			alert('jpeg, gif, png, jpg 이미지 확장자 파일만 업로드 가능합니다.');
		    		file.value = '';
		    		document.getElementById('fileStatusSpan').innerHTML = '';    			
	    			return;
	    		}
	    		totalFileSize += iFile.size;
	    	}
	    	
	    	if(totalFileSize>50000000){//50MB까지 제한
	    		alert('최대 업로드 용량을 초과했습니다.');
	    		file.value = '';
	    		document.getElementById('fileStatusSpan').innerHTML = '';
	    		return;
	    	}
	    	//소수점 2째 자리까지 나오도록 바이트(Byte) -> 메가바이트(MB) 변환 처리
	        totalFileSize = Math.round(totalFileSize / 1024 / 1024 * 100) / 100 + 'MB';
	        document.getElementById('fileStatusSpan').innerHTML = totalFileSize + ' / ' + file.files.length+'개';
		}
		
		function enterTag(){ 			
  			var tagSpan = document.createElement('span');
  			var x = document.createElement('span');	
  			var xMark = 'x';
  			var result = document.getElementById('tag');
  			var input = document.getElementById('tagId');
  			tagSpan.className='badge';
  			x.setAttribute( 'onclick', 'removeTag()' );
  			x.className='xClass';
  					
  			var content =  document.getElementById('tagId');
  			var string = content.value;
  			var string2 = string.trim();
  			var string3 = string2.replace("," , "");		
  			
  			if(string3 !== ""){
  				tagSpan.append(string3);  
  	  			x.append(xMark);
  	  			tagSpan.append(x);
  	  			result.append(tagSpan);  			
  	  			input.value = null;		
  			}else if(string3 == string){}
 
  		}
  		
  		function removeTag(){
  			var listSpan = document.getElementById("tag");
  			listSpan.removeChild(listSpan.childNodes[0]);
  		}
	</script>