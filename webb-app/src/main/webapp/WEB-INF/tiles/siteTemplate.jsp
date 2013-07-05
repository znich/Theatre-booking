<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="s" %>
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
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="/static/js/html5shiv.js"></script>
    <![endif]-->
    <link href="/static/css/style.css" rel="stylesheet">
</head>
<body id="body">
<div class="container">
    <s:insertAttribute name="header"/>
    <div class="contentWrapper">
        <s:insertAttribute name="maincontent"/>
    </div>

    <hr>

    <div class="footer">
        <p>&copy; Java Enterprise 2013.3 Team 1 Project</p>
    </div>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>