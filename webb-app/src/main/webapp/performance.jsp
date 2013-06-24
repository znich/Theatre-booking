<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages" var="bundle" scope="page"/>
<html>
<head>
    <c:forEach var="property" items="${performanceList.properties}">
        <c:if test="${property.name == 'NAME'}">
            <c:forEach var="childProperty" items="${property.childProperties}">
                <title>${childProperty.value} - <fmt:message key="title.index" bundle="${bundle}"/></title>
            </c:forEach>
        </c:if>
    </c:forEach>
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
    <%@include file="jspf/head.jspf"%>
    <div class="contentWrapper">
        <div class="row">
            <div class="span12">
                <div  class="well well-small">
                    <div class="row">
                        <div class="span8">
                            <c:forEach var="property" items="${performanceList.properties}">
                                <c:if test="${property.name == 'NAME'}">
                                    <c:forEach var="childProperty" items="${property.childProperties}">
                                        <h3>${childProperty.value}</h3>
                                    </c:forEach>
                                </c:if>
                            </c:forEach>

                            <c:forEach var="property" items="${performance.properties}">
                            <c:if test="${property.name=='SHORT_DESCRIPTION'}">
                                <c:forEach var="childProperty" items="${property.childProperties}">
                                    <span>${childProperty.value}</span></a>
                                </c:forEach>
                            </c:if>
                            </c:forEach>

                            <p>
                                <a href="#" class="btn btn-mini">Купить билет</a>
                            </p>
                        </div>
                        <div class="span3">
                            <c:forEach var="property" items="${performanceList.properties}">
                                <c:if test="${property.name=='IMAGE'}">
                                    <c:forEach var="childProperty" items="${property.childProperties}">
                                        <img src="../static/img/thumbnail/${childProperty.value}" class="img-polaroid">
                                    </c:forEach>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <c:forEach var="property" items="${performanceList.properties}">
                    <c:if test="${property.name=='DESCRIPTION'}">
                        <c:forEach var="childProperty" items="${property.childProperties}">
                            ${childProperty.value}
                        </c:forEach>
                    </c:if>
                </c:forEach>
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