<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<c:set var = "contextpath" value = "<%=request.getContextPath()%>"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="_csrf" content="${_csrf.token }" />
<meta name="_csrf_header" content="${_csrf.headerName }" />
<tiles:insertAttribute name="css" />
<link rel="stylesheet" href="${contextpath}<tiles:getAsString name = "dynamic_css"/>"/>
<title><tiles:getAsString name = "title"/></title>
</head>
<body>
    <tiles:insertAttribute name="header" />
    <div id="content">
    	<tiles:insertAttribute name="body" />
    </div>
    <tiles:insertAttribute name="footer" />
</body>
<tiles:insertAttribute name="script" />
<script src="${contextpath}<tiles:getAsString name = "dynamic_js"/>"/></script>
</html>