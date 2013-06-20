<?xml version="1.0" encoding="UTF-8" ?>
<%@page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <fmt:setLocale value="${locale}"/>
 <fmt:setBundle basename="messages" var="bundle" scope="page"/>
	<title><fmt:message key="title.repertoire" bundle="${bundle}"/></title>
	<meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap -->
    <link href="../static/css/bootstrap.min.css" rel="stylesheet">
	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../static/js/html5shiv.js"></script>
    <![endif]-->
	<link href="../static/css/style.css" rel="stylesheet">
  </head>
  <body id="body">
    <div class="container">
       <%@include file="jspf/head.jspf"%>
	  <div class="contentWrapper" id="performanceList">
	   <%@include file="jspf/perfCategories.jspf"%>
	  <div class="row">
			<div class="span12">
				<div class="span3">
					<img src="../static/img/thumbnail/1.jpg" class="img-polaroid">
				</div>
				<div class="span8 perf-text">
					<a href="http://localhost:8080/userView/performance.jsp"><h3>Аида</h3></a>
					<span>Джузеппе Верди</span>
					<p>
					<small>
						опера в 4-х действиях с участием народного артиста Грузии Теймураза Гугушвили<br />
						<b>Либретто</b> – Антонио Гисланцони, Камилл дю Локль<br />
						по сценарию Франсуа Огюста Фердинанда Мариетта<br />
						<b>Дирижер-постановщик</b> – Вячеслав Волич<br />
						<b>Режиссер-постановщик</b> – Михаил Панджавидзе<br />
					</small>
					</p>
				</div>
			</div>
	  </div>
	 
	
	   <%@include file="jspf/performancesList.jspf"%>
	  
		
		
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
    <script src="../static/js/bootstrap.min.js"></script>
  </body>
</html>