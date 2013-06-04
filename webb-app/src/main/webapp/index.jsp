<?xml version="1.0" encoding="UTF-8" ?>
<%@page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<fmt:setLocale value="EN"/>
 <fmt:setBundle basename="messages" var="bundle" scope="page"/>
<title><fmt:message key="title.index" bundle="${bundle}"/></title>
	<meta name="description" content="">
    <meta name="author" content="">
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->
	<link href="css/style.css" rel="stylesheet">
</head>
<body id="body">
 <div class="container">
     <%@include file="jspf/head.jspf"%>
	  <div class="contentWrapper">
			<div class="carousel slide" id="mySlider">
				<ol class="carousel-indicators">
					<li class="active" data-target="#mySlider" data-slide-to="0"></li>
					<li data-target="#mySlider" data-slide-to="1"></li>
					<li data-target="#mySlider" data-slide-to="2"></li>
				</ol>
				<div class="carousel-inner">
					<div class="item active">
						<img src="img/slider/1.jpg" />
						<div class="carousel-caption">
							<h4>Заголовок слайда 1</h4>
							<p>Текст описания слайда</p>
						</div>
					</div>
					<div class="item">
						<img src="img/slider/2.jpg" />
						<div class="carousel-caption">
							<h4>Заголовок слайда 2</h4>
							<p>Текст описания слайда</p>
						</div>
					</div>
					<div class="item">
						<img src="img/slider/3.jpg" />
						<div class="carousel-caption">
							<h4>Заголовок слайда 3</h4>
							<p>Текст описания слайда</p>
						</div>
					</div>
				</div>
				<a class="left carousel-control" data-slide="prev" href="#mySlider">&lsaquo;</a>
				<a class="right carousel-control" data-slide="next" href="#mySlider">&rsaquo;</a>
			</div>

      <hr>

      <!-- Example row of columns -->
      <div class="row-fluid">
        <div class="span4">
			<div class="thumbnail">
				<img src="img/thumbnail/1.jpg" alt=""><p></p>
				<div class="caption">
					<h2>АИДА</h2>
					<p>Опера в 4-х действиях с участием народного артиста Грузии Теймураза Гугушвили</p>
					<p><a href="#" class="btn btn-primary">Подробнее &raquo;</a></p>
					<p></p>
				</div>
			</div>
        </div>
        <div class="span4">
			<div class="thumbnail">
				<img src="img/thumbnail/2.jpg" alt=""><p></p>
				<div class="caption">
					<h2>БОГЕМА</h2>
					<p>Опера в 4-х картинах; либретто Дж. Джакозы и Л. Иллике по роману А. Мюрже "Сцены из жизни богемы" и по его же драме "Жизнь богемы".</p>
					<p><a href="#" class="btn btn-primary">Подробнее &raquo;</a></p>
					<p></p>
				</div>
			</div>
        </div>
        <div class="span4">
			<div class="thumbnail">
				<img src="img/thumbnail/3.jpg" alt=""><p></p>
				<div class="caption">
					<h2>БОЛЕРО</h2>
					<p>Балет в 1-м действии. Либретто, хореография и постановка – народный артист СССР и Беларуси, лауреат Государственной премии Республики Беларусь Валентин Елизарьев</p>
					<p><a href="#" class="btn btn-primary">Подробнее &raquo;</a></p>
					<p></p>
				</div>
			</div>
        </div>
      </div>

      <hr>

      <div class="footer">
        <p>&copy; Java Enterprise 2013.3 Team 1 Project</p>
      </div>
	</div>
	</div>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>