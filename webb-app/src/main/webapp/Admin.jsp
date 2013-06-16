<%@page language="java" contentType="text/html; UTF-8"
        pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <fmt:setLocale value="RU"/>
    <fmt:setBundle basename="messages" var="bundle" scope="page"/>
    <title><fmt:message key="title.admin" bundle="${bundle}"/></title>
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="../static/css/bootstrap.min.css" rel="stylesheet">
    <link href="../static/css/daterangepicker.css" rel="stylesheet" type="text/css"/>
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="../static/js/html5shiv.js"></script>
    <![endif]-->
    <link href="../static/css/style.css" rel="stylesheet">
    <script src="../static/ckeditor/ckeditor.js"></script>
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="../static/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../static/js/moment.js"></script>
    <script type="text/javascript" src="../static/js/daterangepicker.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('#reservation').daterangepicker();
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
            <%@include file="adminView/adminMenu.jspf" %>
        </div>
        <div class="span10">
            <div class="contentWrapper">
                <c:choose>
                    <c:when test="${answer==\"adminPage\"}">
                        <%@include file="adminView/adminInfo.jspf" %>
                    </c:when>
                    <c:when test="${answer==\"adminUsers\"}">

                    </c:when>
                    <c:when test="${answer==\"editPerformanceList\"}">
                        <%@include file="adminView/editPerformancesList.jspf" %>
                    </c:when>
                    <c:when test="${answer==\"editPerformance\"}">
                        <%@include file="adminView/editPerformance.jspf" %>
                    </c:when>
                    <c:when test="${answer==\"editEventsList\"}">
						<%@include file="adminView/editEventsList.jspf" %>
                    </c:when>
                    <c:otherwise>
                        <%@include file="adminView/adminInfo.jspf" %>
                    </c:otherwise>
                </c:choose>


            </div>

        </div>
    </div>
</div>


</body>
</html>