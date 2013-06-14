<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages" var="bundle" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <title><fmt:message key="title.index" bundle="${bundle}"/></title>
	<meta name="description" content="">
    <meta name="author" content="">
    <link href="../static/css/bootstrap.min.css" rel="stylesheet">
	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="../static/js/html5shiv.js"></script>
    <![endif]-->
	<link href="../static/css/style.css" rel="stylesheet">
</head>
<body id="body">
 <div class="container">
     <div class="row" id="user-nav">
         <%@include file="jspf/userLogin.jspf"%>
         <div class="span4">
             <form class="form-search">
                 <div class="input-append">
                     <input type="text" class="span2 search-quyery">
                     <button type="submit" class="btn"><fmt:message key="btn.search" bundle="${bundle}"/></button>
                 </div>
             </form>
         </div>
     </div>
     <div class="navbar">
         <%@include file="jspf/mainMenu.jspf"%>
     </div>
	  <div class="contentWrapper">
			<div class="carousel slide" id="mySlider">
				<ol class="carousel-indicators">
					<li class="active" data-target="#mySlider" data-slide-to="0"></li>
					<li data-target="#mySlider" data-slide-to="1"></li>
					<li data-target="#mySlider" data-slide-to="2"></li>
				</ol>
				<div class="carousel-inner">
					<div class="item active">
						<img src="../static/img/slider/1.jpg" />
						<div class="carousel-caption">
							<h4>Заголовок слайда 1</h4>
							<p>Текст описания слайда</p>
						</div>
					</div>
					<div class="item">
						<img src="../static/img/slider/2.jpg" />
						<div class="carousel-caption">
							<h4>Заголовок слайда 2</h4>
							<p>Текст описания слайда</p>
						</div>
					</div>
					<div class="item">
						<img src="../static/img/slider/3.jpg" />
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
				<img src="../static/img/thumbnail/1.jpg" alt=""><p></p>
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
				<img src="../static/img/thumbnail/2.jpg" alt=""><p></p>
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
				<img src="../static/img/thumbnail/3.jpg" alt=""><p></p>
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
    <script src="../static/js/bootstrap.min.js"></script>
</body>
</html>