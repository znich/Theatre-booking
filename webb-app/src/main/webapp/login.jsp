<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages" var="bundle" scope="page"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 	<title><fmt:message key="lbl.signin" bundle="${bundle}"/> - <fmt:message key="title.index" bundle="${bundle}"/></title>
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
        <jsp:include page="jspf/head.jspf"></jsp:include>

		<div class="row">
			<div class="span12">
                <p class="text-error"><c:out value="${message}"/></p>
				<form class="form-signin" action="Controller" method="POST">
					<h2 class="form-signin-heading"><fmt:message key="lbl.signin" bundle="${bundle}"/></h2>
                    <input type="hidden" name="action" value="login">
					<input type="text" name="inputEmail" class="input-block-level" placeholder="Email">
					<input type="password" name="inputPassword" class="input-block-level" placeholder="<fmt:message key="lbl.password" bundle="${bundle}"/>">
					<button class="btn btn-large btn-primary" type="submit"><fmt:message key="btn.signin" bundle="${bundle}"/></button>
				  </form>
			</div>
		
		 <hr>
	</div>
	</div>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="../static/js/bootstrap.min.js"></script>

</body>
</html>