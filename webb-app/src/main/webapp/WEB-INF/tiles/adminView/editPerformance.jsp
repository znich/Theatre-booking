<%@page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<p class="text-error">
    <c:out value="${message}" />
</p>

<f:form class="form-horizontal" method="POST" modelAttribute="performance">
<fieldset>
<legend>${legend}</legend>

<div class="control-group">
    <div class="row-fluid">
        <div class="span6">

            <div class="control-group">
                <div class="row">

                    <label class="control-label">Название</label>

                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-file"></i></span>
                            <input type="text" class="input-large" name="inputName" id="inputName"
                                    <c:forEach var="property" items="${performance.properties}">
                                        <c:if test="${property.name == 'NAME'}">
                                            <c:forEach var="childProperty" items="${property.childProperties}">
                                                value="${childProperty.value}"
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                    />
                        </div>
                    </div>

                </div>
            </div>

            <div class="control-group">
                <div class="row">

                    <label class="control-label" for="inputCategoryId">Категория</label>

                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-sort"></i></span> <select
                                size=1 id="inputCategoryId" name="inputCategoryId">
                            <optgroup label="Категория">
                                <c:forEach var="category" items="${categories}">
                                    <c:forEach var="childCategory" items="${category.childCategories}">
                                        <option value="${childCategory.parent.id}"
                                                <c:if test="${childCategory.parent.id == performance.category.id}">selected</c:if>>${childCategory.name}
                                        </option>
                                    </c:forEach>
                                </c:forEach>
                            </optgroup>
                        </select>
                        </div>
                    </div>

                </div>
            </div>

            <div class="control-group">
                <div class="row">

                    <label class="control-label" for="reservation">Период
                        показа:</label>

                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-calendar"></i></span> <input
                                class="input-large" type="text" name="inputDateInteval"
                                id="reservation" value="${dateInterval}" />
                        </div>
                    </div>

                </div>
            </div>


            <div class="control-group">
                <div class="row">
                    <label class="control-label" for="inputImage">Изображение</label>

                    <div class="controls">
                        <img
                        <c:forEach var="property" items="${performance.properties}">
                        <c:if test="${property.name=='IMAGE'}">
                        <c:forEach var="childProperty" items="${property.childProperties}">
                                src="/static/img/thumbnail/${childProperty.value}"
                        </c:forEach>
                        </c:if>
                        </c:forEach>                                      class="img-rounded" width="300" height="200">

                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-camera"></i></span>
                            <input type="text" class="input-large" name="inputImage" id="inputImage"
                            <c:forEach var="property" items="${performance.properties}">
                            <c:if test="${property.name=='IMAGE'}">
                            <c:forEach var="childProperty" items="${property.childProperties}">
                                   value= "/static/img/thumbnail/${childProperty.value}"
                            </c:forEach>
                            </c:if>
                            </c:forEach>>
                        </div>
                    </div>
                </div>
            </div>


        </div>

        <div class="span3">
            <div class="control-group">
                <div class="row">
                    <div class="btn-group">
                        <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">Выберите язык <span class="caret"></span></button><p></p>
                        <ul class="dropdown-menu">
                            <li><a href="/webb-app/admin/editPerformance/${performance.id}?inputLangId=1">RU</a></li>
                            <li><a href="/webb-app/admin/editPerformance/${performance.id}?inputLangId=2">EN</a></li>
                        </ul></div>
                </div>
            </div>

            <div class="control-group">
                <div class="row">
                    <label class="control-label" for="inputPrice">Цена билетов:</label>

                    <div class="controls">
                        <table>
                            <tr>
                                <td>Категория:</td>
                                <td>Стоимость:</td>
                            </tr>
                            <c:forEach var="ticketsPrice" items="${ticketsPriceList}">
                                <tr>
                                    <td>${ticketsPrice.priceCategory}</td>
                                    <td><input type="text" class="input-medium"
                                               name="inputPrice${ticketsPrice.priceCategory}"
                                               id="inputPrice" value="${ticketsPrice.price}"></td>
                                </tr>
                            </c:forEach>

                        </table>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>


<div class="control-group">
    <label class="control-label">Краткое описание:</label>

    <div class="controls">
        <div class="input-prepend">

            <textarea name="inputShortDescription">
                <c:forEach var="property" items="${performance.properties}">
                    <c:if test="${property.name=='SHORT_DESCRIPTION'}">
                        <c:forEach var="childProperty" items="${property.childProperties}">
                            ${childProperty.value}
                        </c:forEach>
                    </c:if>
                </c:forEach>
            </textarea>

            <script>
                CKEDITOR.replace('inputShortDescription', {
                    height : 150
                });
            </script>
        </div>
    </div>
</div>

<div class="control-group">
    <label class="control-label" for="inputDescrition">Описание:</label>

    <div class="controls">
        <div class="input-prepend">

            <textarea name="inputDescription">
                <c:forEach var="property" items="${performanceList.properties}">
                    <c:if test="${property.name=='DESCRIPTION'}">
                        <c:forEach var="childProperty" items="${property.childProperties}">
                            ${childProperty.value}
                        </c:forEach>
                    </c:if>
                </c:forEach>
            </textarea>
            <script>
                CKEDITOR.replace('inputDescription', {
                    height : 500
                });
            </script>
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