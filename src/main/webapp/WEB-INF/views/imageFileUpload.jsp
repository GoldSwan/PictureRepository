<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/assets/css/imageFileUpload.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/assets/css/bootstrap.min.css"/>" />
<meta charset="UTF-8">
<title>Image File Upload</title>
</head>
<body>
	<div id = "wrapper">
	<header id = "header">
		<h3>사진을 업로드 할 수 있습니다.</h3>
	</header>
	<section id="uploadSection">
			<form id = "uploadForm" action="<c:url value="/uploadForm/multi" />" method="post"
				enctype="multipart/form-data">
				<!-- <div class="form-group"> -->
				<div>
					<input type="file" class="form-control-file" multiple="multiple"
						name="file" accept = "image/gif, image.jpeg, image/png, image/jpg"/>
				</div>
				<!-- placeholder="파일 선택" /> -->
				<div>
					<input type="submit" class="btn btn-primary" value="다중 업로드" />
				</div>
				<div>
					<input type="text" name="username" value="${username}" />
				</div>
				<div>
					<c:if test="${not empty uploadMultiErrorMsg }">
						<span id="uploadFormMulti.errors" class="error">${uploadMultiErrorMsg }</span>
					</c:if>
				</div>
				<!-- </div> -->
			</form>
	</section>
	</div>
</body>
</html>