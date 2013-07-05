<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="s" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="RU" />
<fmt:setBundle basename="messages" var="bundle" scope="page" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title><fmt:message key="title.admin" bundle="${bundle}" /></title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/daterangepicker.css" rel="stylesheet" type="text/css" />
    <link href="/static/css/datepicker.css" rel="stylesheet" type="text/css" />
    <link href="/static/css/bootstrap-timepicker.min.css" rel="stylesheet" type="text/css" />
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="../static/js/html5shiv.js"></script>
    <![endif]-->
    <link href="/static/css/style.css" rel="stylesheet">
    <script type="text/javascript" src="/static/ckeditor/ckeditor.js"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/js/moment.js"></script>
    <script type="text/javascript" src="/static/js/daterangepicker.js"></script>
    <script type="text/javascript" src="/static/js/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="/static/js/bootstrap-timepicker.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#reservation').daterangepicker();
            $('#datepicker').datepicker();

        });
    </script>
</head>
<body id="body">
<style type="text/css">
    #body {
        padding: 100px;
    }
</style>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">
            <%@include file="adminView/adminMenu.jspf"%>
        </div>
        <div class="span10">
            <div class="contentWrapper">
                <s:insertAttribute name="adminmaincontent"/>
            </div>

        </div>
    </div>
</div>


</body>
</html>