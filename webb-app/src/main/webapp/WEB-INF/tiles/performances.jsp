<?xml version="1.0" encoding="UTF-8" ?>
<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false" %>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <fmt:setLocale value="${lang}"/>
 <fmt:setBundle basename="messages" var="bundle" scope="page"/>
	<title><fmt:message key="title.repertoire" bundle="${bundle}"/></title>
	<meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap -->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../static/js/html5shiv.js"></script>
    <![endif]-->
	<link href="/static/css/style.css" rel="stylesheet">
  </head>
  <body id="body">
    <div class="container">
       <%@include file="head.jspf"%>
	  <div class="contentWrapper" id="performanceList">
	   <%@include file="../../jspf/perfCategories.jspf"%>
	
	   <%@include file="../../jspf/performancesList.jspf"%>
	  
		
		
		<div class="pagination pagination-centered">
		  <ul>
			<li class="disabled"><a href="#">«</a></li>
			<li class="active"><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">5</a></li>
			<li><a href="#">»</a></li>
		  </ul>
		</div>
		 <hr>
      <div class="footer">
        <p>&copy; Java Enterprise 2013.3 Team 1 Project</p>
      </div>
	</div>
	</div>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
  </body>
</html>