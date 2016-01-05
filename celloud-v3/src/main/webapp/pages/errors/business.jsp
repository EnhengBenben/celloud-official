<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Business</title>
</head>
<body>
	<%
	    Exception ex = (Exception) request.getAttribute("exception");
	    String name = ex.getClass().getSimpleName();
	%>
	<h2>
		未知异常：<%=ex.getClass().getSimpleName()%></h2>
	<hr>
	<h2>
		异常信息：<%=ex.getMessage()%></h2>
	<hr>
	<h2>异常描述</h2>
	<%
	    ex.printStackTrace(new java.io.PrintWriter(out));
	%>
</body>
</html>