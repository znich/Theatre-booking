<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <title>Регистрация - Театр Minsk Opera House</title>
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
      <jsp:include page="Head.jsp"></jsp:include>

	  <div class="contentWrapper">
		<div class="row">
			<div class="span12">
                <p class="text-error"><c:out value="${message}"/></p>
				<form class="form-horizontal" method="POST" action = "Controller">
				 <fieldset>
				 <legend>Регистрация</legend>
                     <input type="hidden" name="action" value="registrator">
                 <div class="control-group">
                     <label class="control-label"><!--Надо добавить for="inputName"  --> Имя</label>
                     <div class="controls">
                         <div class="input-prepend">
                             <span class="add-on"><i></i></span>
                             <input type="text" class="input-large" name="inputName" id="inputName" placeholder="Имя">
                         </div>
                     </div>
                 </div>
                 <div class="control-group">
                     <label class="control-label"> <!--Надо добавить   for="inputSurname"  -->Фамилия</label>
                     <div class="controls">
                         <div class="input-prepend">
                             <span class="add-on"><i></i></span>
                             <input type="text" class="input-large" name="inputSurname" id="inputSurname" placeholder="Фамилия">
                         </div>
                     </div>
                 </div>
				  <div class="control-group">
					<label class="control-label" for="inputEmail">Email</label>
					<div class="controls">
					  <div class="input-prepend">
						  <span class="add-on"><i class="icon-envelope"></i></span>
						  <input type="text" class="input-large" name="inputEmail" id="inputEmail" placeholder="Email">
					  </div>
					</div>
				  </div>
				  <div class="control-group">
					<label class="control-label" for="inputPassword">Пароль</label>
					<div class="controls">
					  <div class="input-prepend">
						  <span class="add-on"><i class="icon-lock"></i></span>
						  <input type="password" class="input-large" name="inputPassword" id="inputPassword" placeholder="Пароль">
					  </div>
					</div>
				  </div>
				  <div class="control-group">
					<label class="control-label" for="inputAddress">Адрес</label>
					<div class="controls">
					  <div class="input-prepend">
						  <span class="add-on"><i class="icon-home"></i></span>
						  <input type="text" class="input-large" name="inputAddress" id="inputAddress" placeholder="ул. Джигурдинская 120б, кв. 80">
					  </div>
					</div>
				  </div>
				  <div class="control-group">
					<label class="control-label" for="inputPhone">Телефон</label>
					<div class="controls">
						<div class="input-prepend">
						  <span class="add-on"><i class="icon-book"></i></span>
						  <input type="text" class="input-large" name="inputAddress" id="inputPhone" placeholder="+372 (29) 555 55 55">
						</div>
					</div>
				  </div>
				  <div class="control-group">
					<div class="controls">
					  <button type="submit" class="btn btn-large btn-primary">Жмяк</button>
					</div>
				  </div>
				   </fieldset>
				</form>
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