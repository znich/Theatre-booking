
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages" var="bundle" scope="page"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="title.userProfile" bundle="${bundle}"/></title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="../static/css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="../static/js/html5shiv.js"></script>
    <![endif]-->
    <link href="../static/css/style.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row" id="user-nav">
            <%@include file="../jspf/userLogin.jspf"%>
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
            <%@include file="../jspf/mainMenu.jspf"%>
        </div>
        <div class="contentWrapper" id="userProfile">
            <ul class="nav nav-pills">
                <li class="active"><a href="./Controller?action=user_profile">Билеты</a></li>
                <li><a href="./Controller?action=user_profile">Изменить профиль</a></li>
                <li><a href="./Controller?action=logout">Logout</a></li>
            </ul>

            <div class="footer">
                <p>&copy; Java Enterprise 2013.3 Team 1 Project</p>
            </div>
        </div>
    </div>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="../static/js/bootstrap.min.js"></script>
</body>
</html>