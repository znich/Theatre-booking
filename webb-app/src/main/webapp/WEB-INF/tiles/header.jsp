<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="messages" var="bundle" scope="page"/>
<div class="row" id="user-nav">
		   <%@include file="userLogin.jspf"%>
           <%@include file="lang.jspf"%>
		  <div class="span4">
			<form class="form-search">
			  <div class="input-append">
				<input type="text" class="span2 search-query"> 
				<button type="submit" class="btn"><fmt:message key="btn.search" bundle="${bundle}"/></button>
			  </div>
			</form>
		  </div>
	  </div>
	  <div class="navbar">
		  <%@include file="mainMenu.jspf"%>
	  </div>