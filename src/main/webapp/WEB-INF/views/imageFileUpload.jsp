<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/assets/css/imageFileUpload.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/assets/css/bootstrap.min.css"/>" />
<link rel="icon" href="<c:url value="/favicon.ico"/>">
<style>
.items a:before{
	content: "#"
}
</style>
<meta charset="UTF-8">
<title>Image File Upload</title>
</head>
<body>
	<div id="wrapper">
		<header id="header">
			<h3>사진을 업로드 할 수 있습니다.</h3>
		</header>
		<form id="uploadForm" action="<c:url value="/uploadForm/multi" />"
			method="post" enctype="multipart/form-data">
			<!-- <div class="form-group"> -->
			<section id="uploadSection">
				<div>
					<div>
						<span>파일 용량/갯수 : </span><span id = "fileStatusSpan"></span>
					</div>
					<div id=fileDiv>
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
					<input type="text" name="tag" placeholder="태그" value="${tag}" />
				</div>
				<div>
				<span>#</span><span>샘플태그</span><span>#</span><span>샘플태그</span><span class = "items"><a href="${pageContext.request.contextPath}/" rel="tag">샘플태그</a></span>
				</div>
				
				<div class = "inputText">
					<input type="hidden" name="username" value="${username}" />
				</div>
				<div class = "radio">
					<input type="radio" name="publicRange" value="A"><span>전체 공개</span> 
					<input type="radio" name="publicRange" value="C"><span>비공개</span>
				</div>
				<div class = "inputText">
					<input type="submit" class="btn btn-primary" value="다중 업로드" />
				</div>
			</section>
		</form>
		<footer id="footer"> </footer>
	</div>
	<script>	
		function changeFileStatus(file){
			let totalFileSize = 0;
			let browser=navigator.appName;
			let FileFilter = '/\.(jpeg|gif|png|jpg)$/i';
			 
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
	        totalFileSize = Math.round(totalFileSize / 1024 / 1024 * 100) / 100 + 'MB';//소수점 2째 자리까지 나오도록 바이트(Byte) -> 메가바이트(MB) 변환 처리
	        document.getElementById('fileStatusSpan').innerHTML = totalFileSize + ' / ' + file.files.length+'개';
		}
	</script>
</body>
</html>