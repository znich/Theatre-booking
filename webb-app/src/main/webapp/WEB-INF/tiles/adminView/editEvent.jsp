<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="sTime" class="java.util.Date" scope="page"/>
<jsp:useBean id="eTime" class="java.util.Date" scope="page"/>


<p class="text-error">
    <c:out value="${message}" />
</p>

<f:form class="form-horizontal" commandName="editEvent" method="POST" modelAttribute="event">
    <jsp:setProperty name="sTime" property="time" value="${event.startTime}" />
    <jsp:setProperty name="eTime" property="time" value="${event.endTime}" />
    <fmt:formatDate value="${sTime}" type="date" pattern="yyyy-MM-dd" var="theFormattedDate" />
    <fmt:formatDate value="${sTime}" type="date" pattern="HH:mm" var="theFormattedStartTime" />

    <fieldset>
        <legend>${legend}</legend>


        <div class="control-group">
            <div class="row-fluid">
                <div class="span10">

                    <div class="control-group">
                        <div class="row">

                            <label class="control-label">Представление</label>

                            <div class="controls">
                                <div class="input-prepend">
                                    <span class="add-on"><i class="icon-file"></i></span> <select
                                        size=1 name="performanceId">
                                    <optgroup label="Выберите представление">
                                        <c:forEach var="performance" items="${performanceList}">
                                            <option value="${performance.id}"
                                                    <c:if test="${performence.id == event.performance.id}">selected</c:if>>
                                                <c:forEach var="property" items="${performance.properties}">
                                                    <c:if test="${property.name == 'NAME'}">
                                                        <c:forEach var="childProperty" items="${property.childProperties}">
                                                            ${childProperty.value}
                                                        </c:forEach>
                                                    </c:if>
                                                </c:forEach>
                                            </option>
                                        </c:forEach>
                                    </optgroup>
                                </select>
                                </div>
                            </div>

                        </div>
                    </div>

                    <div class="control-group">
                        <div class="row">

                            <label class="control-label" for="datepicker">Дата события:</label>
                            <div class="controls">
                                <div class="input-prepend">
                                    <span class="add-on"><i class="icon-calendar"></i></span> <input
                                        class="input-large" type="text" name="inputDate"
                                        id="datepicker" data-date-format="mm/dd/yy"
                                        value="<fmt:formatDate value="${sTime}" pattern="MM/dd/yy" />" />

                                </div>
                            </div>

                        </div>
                    </div>

                    <div class="control-group">
                        <div class="row">

                            <label class="control-label" for="timepicker1">Начало:</label>
                            <div class="controls">
                                <div class="input-append bootstrap-timepicker">
                                    <input id="timepicker1" type="text" class="input-large"
                                           name="inputStartTime">
                                    <span class="add-on"> <i
                                        class="icon-time"></i>
									</span>
                                </div>
                            </div>

                        </div>
                    </div>

                    <div class="control-group">
                        <div class="row">

                            <label class="control-label" for="timepicker2">Окончание:</label>
                            <div class="controls">
                                <div class="input-append bootstrap-timepicker">
                                    <input id="timepicker2" type="text" class="input-large"
                                           name="inputEndTime"> <span class="add-on"> <i
                                        class="icon-time"></i>
									</span>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="control-group">
            <div class="controls">
                <button type="submit" class="btn btn-large btn-primary">Сохранить</button>
            </div>
        </div>
    </fieldset>
</f:form>
<script type="text/javascript">
    $('#timepicker1').timepicker({
        minuteStep : 5,
        showSeconds : false,
        showMeridian : false,
        defaultTime : '<fmt:formatDate value="${sTime}" pattern="HH:mm" />'
    });
</script>
<script type="text/javascript">
    $('#timepicker2').timepicker({
        minuteStep : 5,
        showSeconds : false,
        showMeridian : false,
        defaultTime : '<fmt:formatDate value="${eTime}" pattern="HH:mm" />'
    });
</script>