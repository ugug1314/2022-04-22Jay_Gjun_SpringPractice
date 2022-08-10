<%@ page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Index</title>
</head>
<body>
	Index
	<%=new Date()%>
	<ol>
		<li><a href="./mvc/case02/lotto/">樂透選號程式</a></li>
		<li><a href="./mvc/case03/exam/">sp:form</a></li>
		<li><a href="./mvc/case04/person/">sp:form+驗證</a></li>
		<li><a href="./mvc/case04/stock/">Stock買賣+自訂驗證</a></li>
		<li><a href="./mvc/lab/fund/">查詢所有基金(fund)</a></li>
		<li><a href="./mvc/lab/fundstock/">查詢所有股票(fundstock)</a></li>
		<li><a href="./html/fund.html">2022/2/27_fund(使用SPA Ajax處理)</a></li>
	</ol>
</body>
<!--  -->
</html>