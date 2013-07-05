<%@ taglib prefix="s" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages" var="bundle" scope="page"/>
<div class="row">
    <div class="span12">
        <p class="text-error"><c:out value="${message}"/></p>
        <s:form class="form-signin" modelAttribute="user" method="POST">
            <h2 class="form-signin-heading"><fmt:message key="lbl.signin" bundle="${bundle}"/></h2>
            <s:input path="email" class="input-block-level" />
            <s:input path="password" class="input-block-level" />
            <button class="btn btn-large btn-primary" type="submit"><fmt:message key="btn.signin" bundle="${bundle}"/></button>
        </s:form>
    </div>

    <hr>
</div>