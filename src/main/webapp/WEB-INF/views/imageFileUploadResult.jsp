<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="icon" href="<c:url value="/favicon.ico"/>">
<meta charset="UTF-8">
<title>Image File Upload Result</title>
</head>
<body>
	파일업로드 완료. 파일명 : ${uploadFileNames}
</body>
</html>