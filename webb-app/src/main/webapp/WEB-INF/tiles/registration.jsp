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
 <title><fmt:message key="lbl.registration" bundle="${bundle}"/> - <fmt:message key="title.index" bundle="${bundle}"/></title>
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
        <%@include file="head.jspf"%>

	  <div class="contentWrapper">
		<div class="row">
			<div class="span12">
                <p class="text-error"><c:out value="${message}"/></p>
				<form class="form-horizontal" method="POST" action = "Controller">
				 <fieldset>
				 <legend><fmt:message key="lbl.registration" bundle="${bundle}"/></legend>
                     <input type="hidden" name="action" value="registrator">
                 <div class="control-group">
                     <label class="control-label" for="firstName"><fmt:message key="lbl.firstName" bundle="${bundle}"/></label>
                     <div class="controls">
                         <div class="input-prepend">
                             <span class="add-on"><i></i></span>
                             <input type="text" class="input-large" name="firstName" id="firstName" placeholder="<fmt:message key="lbl.firstName" bundle="${bundle}"/>">
                         </div>
                     </div>
                 </div>
                 <div class="control-group">
                     <label class="control-label" for="secondName"><fmt:message key="lbl.surname" bundle="${bundle}"/></label>
                     <div class="controls">
                         <div class="input-prepend">
                             <span class="add-on"><i></i></span>
                             <input type="text" class="input-large" name="secondName" id="secondName" placeholder="<fmt:message key="lbl.surname" bundle="${bundle}"/>">
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
					<label class="control-label" for="inputPassword"><fmt:message key="lbl.password" bundle="${bundle}"/></label>
					<div class="controls">
					  <div class="input-prepend">
						  <span class="add-on"><i class="icon-lock"></i></span>
						  <input type="password" class="input-large" name="inputPassword" id="inputPassword" placeholder="<fmt:message key="lbl.password" bundle="${bundle}"/>">
					  </div>
					</div>
				  </div>
				  <div class="control-group">
					<label class="control-label" for="inputAddress"><fmt:message key="lbl.address" bundle="${bundle}"/></label>
					<div class="controls">
					  <div class="input-prepend">
						  <span class="add-on"><i class="icon-home"></i></span>
						  <input type="text" class="input-large" name="inputAddress" id="inputAddress" placeholder="<fmt:message key="lbl.address" bundle="${bundle}"/>">
					  </div>
					</div>
				  </div>
				  <div class="control-group">
					<label class="control-label" for="inputPhone"><fmt:message key="lbl.phone" bundle="${bundle}"/></label>
					<div class="controls">
						<div class="input-prepend">
						  <span class="add-on"><i class="icon-book"></i></span>
						  <input type="text" class="input-large" name="inputPhone" id="inputPhone" placeholder="+372 (29) 555 55 55">
						</div>
					</div>
				  </div>
				  <div class="control-group">
					<div class="controls">
					  <button type="submit" class="btn btn-large btn-primary"><fmt:message key="btn.submit" bundle="${bundle}"/></button>
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