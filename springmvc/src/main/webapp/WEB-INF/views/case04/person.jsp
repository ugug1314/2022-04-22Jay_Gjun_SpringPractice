<%@ page isErrorPage="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="spform"
	uri="http://www.springframework.org/tags/form"%>   
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/person.js"></script>
<meta charset="UTF-8">
<title>Person Form</title>
<style type="text/css">
	.error {
		color: #FF0000
	}
</style>
</head>
<body style="padding: 15px">
	
	<spform:form class="pure-form" 
				method="post" 
				modelAttribute="person"
				action="${ pageContext.request.contextPath }/mvc/case04/person/">
		<fieldset>
			<legend>Person Form ${ index }</legend>
			<input type="hidden" id="_method" name="_method" value="${ _method }">
	    	姓名：<spform:input path="name"/>
	    		 <spform:errors path="name" cssClass="error" /><p />
	    	年齡：<spform:input path="age"/>
	    		 <spform:errors path="age" cssClass="error" /><p />
	    	會員：<spform:radiobutton path="member" value="true" />會員
	    		 <spform:radiobutton path="member" value="false" />非會員
	    		 <spform:errors path="member" cssClass="error" /><p />
	    	生日：<spform:input path="birth" type="date"/>
	    		 <spform:errors path="birth" cssClass="error" /><p />
	        <button type="submit" class="pure-button pure-button-primary" ${ _method=='POST'?'':'disabled' }>新增</button>
	        <button type="button" class="pure-button pure-button-primary" ${ _method=='PUT'?'':'disabled' } onclick="updatePerson(${index})">修改</button>
	        <button type="button" class="pure-button pure-button-primary" ${ _method=='PUT'?'':'disabled' } onclick="deletePerson(${index})">刪除</button>
	        <p />
	        <spform:errors path="*" cssClass="error" />
	    </fieldset>
    </spform:form>
    <table class="pure-table pure-table-bordered">
    	<thead>
    		<tr>
    			<th>index</th>
    			<th>person</th>
    		</tr>
    	</thead>
    	<tbody>
    		<c:forEach varStatus="status" var="person" items="${ people }">
    		<tr>
    			<td><a href="${ pageContext.request.contextPath }/mvc/case04/person/${ status.index }">${ status.index }</a></td>
    			<td>${ person }</td>
    		</tr>
    		</c:forEach>
    	</tbody>
    </table>
    
</body>
</html>