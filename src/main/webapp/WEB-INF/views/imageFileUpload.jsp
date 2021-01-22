<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href = "https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<meta charset="UTF-8">
<title>Image File Upload</title>
</head>
<body>
<form action="<c:url value="/uploadForm" />" method="post" enctype="multipart/form-data">
    <input type="file" name="file" placeholder="파일 선택"/>
    <input type="submit" value="업로드"/>
    <input type="text" name="username" value = "${username}"/>
</form>
<form action="<c:url value="/uploadForm/multi" />" method="post" enctype="multipart/form-data">
    <input multiple="multiple" type="file" name="file" placeholder="파일 선택"/>
    <input type="submit" value="다중 업로드"/>
    <input type="text" name="username" value = "${username}"/>
</form>
</body>
</html>