<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages" var="bundle" scope="page"/>
<div class="row">
    <div class="span12">


        <script type="text/javascript">
            $(document).ready(function() {
                $('#reservation').daterangepicker();
            });
        </script>
        <ul class="nav nav-tabs">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <fmt:message key="lbl.category" bundle="${bundle}"/> <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <c:if test="${not empty categories}">
                        <c:forEach var="category" items="${categories}">
                            <c:forEach var="childCategory" items="${category.childCategories}">
                                <li><a tabindex="-1" href="/webb-app/events?categoryId=${childCategory.parent.id}">${childCategory.name}</a></li>
                            </c:forEach>
                        </c:forEach>
                    </c:if>
                    <li class="divider"></li>
                    <li><a tabindex="-1" href="/webb-app/events?categoryId=0">
                        <fmt:message key="lbl.category.all" bundle="${bundle}"/></a></li>
                </ul>
            </li>
            <li>
                <form class="form-horizontal">
                    <fieldset>
                        <div class="control-group">
                            <label class="control-label" for="reservation"><fmt:message key="lbl.date" bundle="${bundle}"/>:</label>
                            <div class="controls">
                                <div class="input-prepend">
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                    <input type="hidden" id="action" name="action" value="show_event_list"/>
                                    <input class="input-medium" type="text" name="dateInterval" id="reservation" value="${dateInterval}" />
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </form>
            </li>
        </ul>
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th><fmt:message key="lbl.date" bundle="${bundle}"/></th>
                <th><fmt:message key="lbl.time" bundle="${bundle}"/></th>
                <th><fmt:message key="lbl.title" bundle="${bundle}"/></th>
                <th><fmt:message key="lbl.tickets" bundle="${bundle}"/></th>
                <th><fmt:message key="lbl.cost" bundle="${bundle}"/></th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="today" class="java.util.Date" scope="page"/>
            <jsp:useBean id="startTime" class="java.util.Date" scope="page"/>
            <jsp:useBean id="endTime" class="java.util.Date" scope="page"/>

            <c:forEach var="event" items="${eventList}">
                <jsp:setProperty name="startTime" property="time" value="${event.startTime}" />
                <jsp:setProperty name="endTime" property="time" value="${event.endTime}" />
                <tr class=<c:if test="${startTime < today}">"warning"</c:if><c:if
                        test="${startTime >= today}">"success"</c:if> onclick="location.href='Controller?action=PERFORMANCE_SHOW&eventId=${event.id}'">
                    <td>
                        <fmt:formatDate value="${startTime}" pattern="MM/dd/yyyy"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${startTime}" pattern="HH:mm"/> - <fmt:formatDate
                            value="${endTime}" pattern="HH:mm"/>
                    </td>
                    <td>

                        <c:forEach var="property" items="${event.performance.properties}">
                            <c:if test="${property.name == 'NAME'}">
                                <c:forEach var="childProperty" items="${property.childProperties}">
                                    <strong>${childProperty.value}</strong>
                                </c:forEach>
                            </c:if>
                        </c:forEach>


                        <c:forEach var="property" items="${event.performance.properties}">
                            <c:if test="${property.name == 'SHORT_DESCRIPTION'}">
                                <c:forEach var="childProperty" items="${property.childProperties}">
                                    <p>${childProperty.value}</p>
                                </c:forEach>
                            </c:if>
                        </c:forEach>

                    </td>
                    <td>
                        <c:if test="${event.freeTicketsCount<=0}">
                            <fmt:message key="lbl.noticket" bundle="${bundle}"/>
                        </c:if>
                        <c:if test="${event.freeTicketsCount>0}">
                            <a href="/webb-app/Controller?action=BOOKING_TICKETS_SHOW&eventId=${event.id}"><fmt:message
                                    key="btn.buyTicket" bundle="${bundle}"/></a>
                        </c:if>
                    </td>
                    <td>
                        ${event.minTicketPrice} - ${event.maxTicketPrice} <fmt:message key="lbl.rubles" bundle="${bundle}"/></br>
                        ${event.freeTicketsCount} <fmt:message key="lbl.ticketsAvailable" bundle="${bundle}"/>
                    </td>

            </c:forEach>
            </tbody>
        </table>
    </div>
</div>