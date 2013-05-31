<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 	<title>Авторизация - Театр Minsk Opera House</title>
	<meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
	<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->
	<link href="css/style.css" rel="stylesheet">
</head>
<body id="body">
    <div class="container">
        <jsp:include page="userView/Head.jsp"></jsp:include>

		<div class="row">
			<div class="span12">
                <p class="text-error"><c:out value="${message}"/></p>
				<form class="form-signin" action="Controller" method="POST">
					<h2 class="form-signin-heading">Авторизация</h2>
                    <input type="hidden" name="action" value="login">
					<input type="text" name="inputEmail" class="input-block-level" placeholder="Email">
					<input type="password" name="inputPassword" class="input-block-level" placeholder="Пароль">
					<label class="checkbox">
					  <input type="checkbox" value="remember-me"> Запомнить меня
					</label>
					<button class="btn btn-large btn-primary" type="submit">Войти</button>
				  </form>
			</div>
		
		 <hr>
	</div>
	</div>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="js/bootstrap.min.js"></script>

</body>
</html>