<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
</head>
<body>
<div class="row" id="user-nav">
	<div class="span8">
		<ul>
			<li><a href="./Controller?action=show_login_form">Войти</a> </li>
			<li><a href="./Controller?action=show_reg_form">Регистрация</a></li>
			<li><a href="#">Мой аккаунт</a></li>
		</ul>
	</div>	
	<div class="span4">
			<form class="form-search">
			  <div class="input-append">
				<input type="text" class="span2 search-query">
				<button type="submit" class="btn">Поиск</button>
			  </div>
			</form>
		  </div>
	  </div>
	  <div class="navbar">
		  <nav class="navbar-inner">
			  <a class="brand">Minsk Opera House</a>
			  <ul class="nav">
				<li class="divider-vertical"></li>
				<li><a href="./Controller">Главная</a></li>
				<li><a href="./Controller?action=show_perf_list">Репертуар</a></li>
				<li><a href="./userView/events.jsp">Афиша и Билеты</a></li>
				<li><a href="#">Контакты</a></li>
			  </ul>
		  </nav>
	  </div>
</body>
</html>