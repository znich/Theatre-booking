<%@page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<fmt:setLocale value="EN" />
	<fmt:setBundle basename="messages" var="bundle" scope="page" />
	<title><fmt:message key="title.admin" bundle="${bundle}" /></title>
	<meta name="description" content="">
		<meta name="author" content="">
			<link href="css/bootstrap.min.css" rel="stylesheet">
			<link href="css/daterangepicker.css" rel="stylesheet" type="text/css" />
				<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
				<!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
    <![endif]-->
				<link href="css/style.css" rel="stylesheet">
				<script src="ckeditor/ckeditor.js"></script>
				<script src="http://code.jquery.com/jquery-latest.js"></script>
    			<script src="js/bootstrap.min.js"></script>
				<script type="text/javascript" src="js/moment.js"></script>
				<script type="text/javascript" src="js/daterangepicker.js"></script>
				<script type="text/javascript">
				$(document).ready(function() {
				$('#reservation').daterangepicker();
				});
				</script>
</head>
<body id="body">
<style type="text/css">
  #body { padding: 100px; }
</style>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span2">
				<%@include file="adminView/adminMenu.jspf"%>
			</div>
			<div class="span10">
				<div class="contentWrapper">
					<c:choose>
						<c:when test="${answer==\"adminPage\"}">
							<%@include file="adminView/adminInfo.jspf"%>
						</c:when>
						<c:when test="${answer==\"adminUsers\"}">
							
						</c:when>
						<c:when test="${answer==\"editPerformanceList\"}">
							<%@include file="adminView/editPerformancesList.jspf"%>
						</c:when>
						<c:when test="${answer==\"editPerformance\"}">
							<%@include file="adminView/editPerformance.jspf"%>
						</c:when>
						<c:when test="${answer==\"editEvents\"}">
							
						</c:when>
						<c:otherwise>
							<%@include file="adminView/adminInfo.jspf"%>
						</c:otherwise>
					</c:choose>
					
					
					<!--  
					<form action="Controller" method="post">
					 <fieldset>
				 <legend>Редактирование представления</legend>
					<input type="hidden" name="action" value="test_command" />
					
						
						<div class="control-group">
						<div class="row-fluid">
						<div class="span4">
						
                     <label class="control-label">Название</label>
                     <div class="controls">
                         <div class="input-prepend">
                             <span class="add-on"><i class="icon-file"></i></span>
                             <input type="text" class="input-large" name="inputName" id="inputName" value="${performance.name}">
                         </div>
                     </div>
                     
                     <label class="control-label" for="inputCategory">Категория</label>
					 <div class="controls">
					  <div class="input-prepend">
						  <span class="add-on"><i class="icon-heart"></i></span>
						<select class="input-large" size=1 name="inputCategoryId" > 
						<optgroup label="Категория">						
						<option value="1" >быллет</option>
						<option value="2" selected>драма</option>
						<option value="3" >театр</option>						
						</optgroup>
						</select>
						</div>
                     </div>
                     
                     <label class="control-label" for="reservation">Выберите дату:</label>
						<div class="controls">
					      <div class="input-prepend">
						  	<span class="add-on"><i class="icon-calendar"></i></span>						 				
								<input class="input-large" type="text" name="inputDateInteval" id="reservation" value="06/11/2013 - 08/01/2013" />
						 </div> 
						</div>
                 </div>
                 
                 <div class="span8">
                 <div class="row-fluid">
                 
                  <div class="span6">
                <label class="control-label" for="inputLanguage">Язык:</label>
					<div class="controls">
					  <div class="input-prepend">
						  <span class="add-on"><i class="icon-comment"></i></span>
						  <select size=1 name="inputLangId" > 
						<optgroup label="Язык">
						<option value="1" <c:if test="${langId == 1}">selected</c:if>>русский</option>
						<option value="2" <c:if test="${langId == 2}">selected</c:if>>english</option>
						</optgroup>
						</select>
						</div>
					</div>
                 </div>
                 
                 <div class="span6">                
					<label class="control-label" for="inputImage">Изображение</label>
					<div class="controls">
					<img src="img/thumbnail/1.jpg" class="img-rounded">
					  <div class="input-prepend">
						  <span class="add-on"><i class="icon-home"></i></span>						  
						  <input type="text" class="input-large" name="inputImage" id="inputImage">						  
					  </div>
					</div>				  
				  </div>
				  
				  </div>
				 
				  </div>    
				  
				  </div>        
					
				  <textarea name="editor1">&lt;p&gt;Initial value.&lt;/p&gt;
					<p><span style="font-size:22px"><span style="font-family:tahoma,geneva,sans-serif">ссылка</span></span></p>
					</textarea>
						<script>
   						 CKEDITOR.replace( 'editor1',
   								 {
   							 height : 100,   
   							 
   								 });
						</script>
						
						<input type="submit"/>
						</form> 
						</fieldset> -->
				</div>
			
			</div>
		</div>
	</div>




</body>
</html>