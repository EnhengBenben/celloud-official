<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CelLoud</title>
<link rel="shortcut icon"
	href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="bookmark"
	href="<%=request.getContextPath()%>/images/favicon.ico" />
</head>
<body>
	<div style="top: 30%; left: 40%; position: absolute;">
		<font color="#ccc">页面加载中。。。</font>
	</div>
	<script type="text/javascript">
		setTimeout(function() {
			window.location.href = "index";
		}, 0);
	</script>
</body>
</html>