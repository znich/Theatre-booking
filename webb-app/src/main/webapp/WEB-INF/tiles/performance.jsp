<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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