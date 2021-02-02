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
					<div id=fileDiv>
						<input type="file" class="form-control-file" multiple="multiple"
							name="file" accept="image/gif, image.jpeg, image/png, image/jpg" />
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
</body>
</html>