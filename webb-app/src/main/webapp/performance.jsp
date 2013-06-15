<%--
  Created by IntelliJ IDEA.
  User: 1
  Date: 5/20/13
  Time: 11:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:out value="${performanceList.name}" /> - <fmt:message key="title.index" bundle="${bundle}"/></title>
    <meta charset="utf-8">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap -->
    <link href="../static//css/bootstrap.min.css" rel="stylesheet">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="../static/js/html5shiv.js"></script>
    <![endif]-->
    <link href="../static/css/style.css" rel="stylesheet">
</head>
<body id="body">
<div class="container">
    <jsp:include page="jspf/head.jspf"></jsp:include>

    <div class="contentWrapper">
        <div class="row">
            <div class="span12">
                <div  class="well well-small">
                    <div class="row">
                        <div class="span8">
                            <h1>${performanceList.name}</h1>${performanceList.shortDescription}
                            <p>
                                <a href="#" class="btn btn-mini">Купить билет</a>
                            </p>
                        </div>
                        <div class="span3">
                            <img src="../static/img/thumbnail/${performanceList.image}" class="img-rounded">
                        </div>
                    </div>
                </div>
                ${performanceList.description}
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