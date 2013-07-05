<%@page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="startTime" class="java.util.Date" scope="page" />
<jsp:useBean id="endTime" class="java.util.Date" scope="page" />
 <fmt:setBundle basename="messages" var="bundle" scope="page" />


<ul class="nav nav-tabs">
	<li class="dropdown"><a class="dropdown-toggle"
		data-toggle="dropdown" href="#"> <fmt:message key="lbl.category"
				bundle="${bundle}" /> <b class="caret"></b>
	</a>
		<ul class="dropdown-menu">
			<c:if test="${not empty categories}">
				<c:forEach var="category" items="${categories}">
					<c:forEach var="childCategory" items="${category.childCategories}">
						<li><a tabindex="-1"
							href="/webb-app/admin/showEvents?categoryId=${childCategory.parent.id}">${childCategory.name}</a></li>
					</c:forEach>
				</c:forEach>
			</c:if>
			<li class="divider"></li>
			<li><a tabindex="-1"
				href="/webb-app/admin/showEvents?categoryId=0"> <fmt:message
						key="lbl.category.all" bundle="${bundle}" /></a></li>
		</ul></li>
	<li>
		<form class="form-horizontal">
			<fieldset>
				<div class="control-group">
					<label class="control-label" for="reservation"><fmt:message
							key="lbl.date" bundle="${bundle}" />:</label>
					<div class="controls">
						<div class="input-prepend">
							<span class="add-on"><i class="icon-calendar"></i></span> <input
								type="hidden" id="action" name="action"
								value="admin_show_events" /> <input class="input-medium"
								type="text" name="dateInteval" id="reservation"
								value="${dateInterval}" />
						</div>
						<button class="btn btn-small" type="submit">Показать</button>
					</div>
				</div>
			</fieldset>
		</form>
	</li>
	<li><a href="/webb-app/admin/showAddEvent" class="btn btn-primary">Добавить
			новое событие</a></li>
</ul>

<table class="table table-bordered table-hover">
	<thead>
		<tr>
			<th><fmt:message key="lbl.date" bundle="${bundle}" /></th>
			<th><fmt:message key="lbl.time" bundle="${bundle}" /></th>
			<th><fmt:message key="lbl.title" bundle="${bundle}" /></th>
			<th><fmt:message key="lbl.tickets" bundle="${bundle}" /></th>
			<th><fmt:message key="lbl.cost" bundle="${bundle}" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="event" items="${eventList}">
			<jsp:setProperty name="startTime" property="time"
				value="${event.startTime}" />
			<jsp:setProperty name="endTime" property="time"
				value="${event.endTime}" />
			<tr class="rowlink">
				<td><fmt:formatDate value="${startTime}" pattern="MM/dd/yyyy" /></td>
				<td><fmt:formatDate value="${startTime}" pattern="HH:mm" /> -
					<fmt:formatDate value="${endTime}" pattern="HH:mm" /></td>
				<td><strong> <a
						href="/webb-app/performance?performanceId=${performance.id}">
							<c:forEach var="property" items="${event.performance.properties}">
								<c:if test="${property.name == 'NAME'}">
									<c:forEach var="childProperty"
										items="${property.childProperties}">
                                ${childProperty.value}
                            </c:forEach>
								</c:if>
							</c:forEach>
					</a>
				</strong>
					<p>
						<c:forEach var="property" items="${event.performance.properties}">
							<c:if test="${property.name=='SHORT_DESCRIPTION'}">
								<c:forEach var="childProperty"
									items="${property.childProperties}">
                                ${childProperty.value}
                            </c:forEach>
							</c:if>
						</c:forEach>
					</p></td>
				<td><c:if test="${event.freeTicketsCount<=0}">
						<fmt:message key="lbl.noticket" bundle="${bundle}" />
					</c:if> <c:if test="${event.freeTicketsCount>0}">
						<a href="/webb-app/#?eventId=${event.id}"><fmt:message
								key="btn.buyTicket" bundle="${bundle}" /></a>
					</c:if></td>
				<td>${event.minTicketPrice} - ${event.maxTicketPrice} <fmt:message
						key="lbl.rubles" bundle="${bundle}" /></br> ${event.freeTicketsCount}
					<fmt:message key="lbl.ticketsAvailable" bundle="${bundle}" />
					<div class="dropdown">

						<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
							<li><a tabindex="-1"
								href="/webb-app/performance?performance=${event.performance.id}">Просмотреть</a></li>
							<li><a tabindex="-1"
								href="/webb-app/admin/showEditEvent?eventId=${event.id}">Редактировать</a></li>
							<li><a tabindex="-1"
								href="/webb-app/admin/deleteEvent?eventId=${event.id}">Удалить</a></li>
							<li class="divider"></li>
							<li><a tabindex="-1" href="#">Отмена</a></li>
						</ul>
					</div>
				</td>
		</c:forEach>
</table>
<script>
	$(function() {
		$(this).find('.dropdown-toggle').dropdown();
		$('.rowlink').on('click', function(e) {
			$(this).find('.dropdown').toggleClass('open');
			e.stopPropagation();
		});
	});
</script>