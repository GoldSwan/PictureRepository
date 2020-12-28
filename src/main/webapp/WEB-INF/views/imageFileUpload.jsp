<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Image File Upload</title>
</head>
<body>
<form action="<c:url value="/uploadForm" />" method="post" enctype="multipart/form-data">
    <input type="file" name="file" placeholder="파일 선택" /><input type="submit" value="업로드">
</form>
</body>
</html>