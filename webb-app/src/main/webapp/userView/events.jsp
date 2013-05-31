<?xml version="1.0" encoding="UTF-8" ?>
<%@page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <fmt:setLocale value="${locale}"/>
 <fmt:setBundle basename="messages" var="bundle" scope="page"/>
<title><fmt:message key="title.index" bundle="${bundle}"/></title>
	<meta charset="utf-8">
	<meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">
	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="/js/html5shiv.js"></script>
    <![endif]-->
	<link href="../css/style.css" rel="stylesheet">
	<link href="../css/daterangepicker.css" rel="stylesheet" type="text/css" />
	
	<script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="../js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../js/moment.js"></script>
	<script type="text/javascript" src="../js/daterangepicker.js"></script>
  </head>
  <body id="body">
    <div class="container">
	  <div class="row" id="user-nav">
		   <%@include file="jspf/userLogin.jspf"%>
		  <div class="span4">
			<form class="form-search">
			  <div class="input-append">
				<input type="text" class="span2 search-query"> 
				<button type="submit" class="btn"><fmt:message key="btn.search" bundle="${bundle}"/></button>
			  </div>
			</form>
		  </div>
	  </div>
	  <div class="navbar">
		  <%@include file="jspf/mainMenu.jspf"%>
	  </div>
	  <div class="contentWrapper">
		<div class="row">
				<div class="span12">

					 
					<script type="text/javascript">
					$(document).ready(function() {
						$('#reservation').daterangepicker();
					});
					</script>
					<ul class="nav nav-tabs">
						  <li class="dropdown">
							 <%@include file="jspf/categories.jspf"%>
						  </li>
						  <li>			 		
							  <form class="form-horizontal">
							  <fieldset>
								<div class="control-group">
								  <label class="control-label" for="reservation">Выберите дату:</label>
								  <div class="controls">
									<div class="input-prepend">
									  <span class="add-on"><i class="icon-calendar"></i></span><input class="input-medium" type="text" name="reservation" id="reservation" />
									</div>
								  </div>
								</div>
							  </fieldset>
							</form>
						</li>
					</ul>
					<%@include file="jspf/ShowEvents.jspf"%>
				</div>
		</div>
		
		 <hr>
      <div class="footer">
        <p>&copy; Java Enterprise 2013.3 Team 1 Project</p>
      </div>
	</div>
	</div>
  </body>
</html>