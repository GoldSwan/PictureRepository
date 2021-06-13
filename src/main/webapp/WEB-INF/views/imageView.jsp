<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/assets/css/imageView.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/assets/css/bootstrap.min.css"/>" />
<link rel="icon" href="<c:url value="/favicon.ico"/>">
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
        	<div><img alt="" src="${pageContext.request.contextPath}/resources/images/fulls/${fileId}"></div>
        	<div><span>제목 : <c:out value = "${title}"/></span></div>
        	<div><span>내용 : <c:out value = "${content}"/></span></div>
        	<div><span>태그 : <c:out value = "${tag}"/></span></div>
        	<div><span>시간 : <c:out value = "${isrtDt}"/></span></div>
        </section>
		<footer id="footer"> 
		</footer>
	</div>
	<script>
	</script>
</body>
</html>