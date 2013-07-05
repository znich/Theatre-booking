<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages" var="bundle" scope="page"/>
<ul class="nav nav-pills">
    <li><a tabindex="-1" href="/webb-app/performances?categoryId=0">
        <fmt:message key="lbl.category.all" bundle="${bundle}" /></a></li>
    <c:if test="${not empty categories}">
        <c:forEach var="category" items="${categories}">
            <c:forEach var="childCategory" items="${category.childCategories}">
                <li><a tabindex="-1" href="/webb-app/performances?categoryId=${childCategory.parent.id}">${childCategory.name}</a></li>
            </c:forEach>
        </c:forEach>
    </c:if>
</ul>
<c:forEach var="performance" items="${performanceList}">
 <div class="row">
			<div class="span12">
				<div class="span3">
                    <c:forEach var="property" items="${performance.properties}">
                        <c:if test="${property.name=='IMAGE'}">
                            <c:forEach var="childProperty" items="${property.childProperties}">
                                <img src="/static/img/thumbnail/${childProperty.value}" class="img-polaroid">
                            </c:forEach>
                        </c:if>
                    </c:forEach>
				</div>
				<div class="span8 perf-text">
					<a href="/webb-app/performance?performanceId=${performance.id}">
                        <c:forEach var="property" items="${performance.properties}">
                            <c:if test="${property.name == 'NAME'}">
                                <c:forEach var="childProperty" items="${property.childProperties}">
                                    <h3>${childProperty.value}</h3>
                                </c:forEach>
                            </c:if>
                        </c:forEach>
                    </a>
                    <c:forEach var="property" items="${performance.properties}">
                        <c:if test="${property.name=='SHORT_DESCRIPTION'}">
                            <c:forEach var="childProperty" items="${property.childProperties}">
                                <span>${childProperty.value}</span></a>
                            </c:forEach>
                        </c:if>
                    </c:forEach>
				</div>
			</div>
	  </div>
</c:forEach>