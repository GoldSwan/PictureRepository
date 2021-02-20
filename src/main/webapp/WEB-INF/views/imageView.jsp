<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/assets/css/imageView.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/assets/css/bootstrap.min.css"/>" />
<style>
</style>
<meta charset="UTF-8">
<title>Image View</title>
</head>
<body>
	<div id="wrapper">
		<header id="header">
		</header>
        <section id="imageDataSection">
        	<img alt="" src="${pageContext.request.contextPath}/resources/images/fulls/${fileId}">
        </section>
		<footer id="footer"> 
		</footer>
	</div>
	<script>
	</script>
</body>
</html>