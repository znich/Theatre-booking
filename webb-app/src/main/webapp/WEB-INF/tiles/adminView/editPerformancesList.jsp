<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<a href="/webb-app/admin/addPerformance"   class="btn btn-primary">Добавить представление</a>
<c:forEach var="performance" items="${performanceList}">
    <div class="row-fluid">

        <div class="span3">
            <c:forEach var="property" items="${performance.properties}">
                <c:if test="${property.name=='IMAGE'}">
                    <c:forEach var="childProperty" items="${property.childProperties}">
                        <img src="/static/img/thumbnail/${childProperty.value}" class="img-polaroid">
                    </c:forEach>
                </c:if>
            </c:forEach>
        </div>
        <div class="span6 perf-text">
            <a
                    href="/webb-app/performance?performanceId=${performance.id}">
                <h3><c:forEach var="property" items="${performance.properties}">
                    <c:if test="${property.name == 'NAME'}">
                        <c:forEach var="childProperty" items="${property.childProperties}">
                            ${childProperty.value}
                        </c:forEach>
                    </c:if>
                </c:forEach></h3>
            </a> <span><c:forEach var="property" items="${performance.properties}">
            <c:if test="${property.name=='SHORT_DESCRIPTION'}">
                <c:forEach var="childProperty" items="${property.childProperties}">
                    ${childProperty.value}
                </c:forEach>
            </c:if>
        </c:forEach></span>
        </div>
        <div class="span1">
            <div class="btn-group">
                <a class="btn"
                   href="/webb-app/admin/editPerformance/${performance.id}"><i
                        class="icon-edit icon-large"></i></a> <a class="btn"
                                               href="/webb-app/admin/deletePerformance?performanceId=${performance.id}"><i
                    class="icon-trash icon-large"></i></a>
            </div>
        </div>

    </div>
</c:forEach>