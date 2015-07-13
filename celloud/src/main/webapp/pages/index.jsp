<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>CelLoud后台管理平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/plugins/bootstrap-3.3.2-dist/css/bootstrap.min.css" />
<%-- 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap-responsive.min.css" /> --%>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/admin.css" />
		<link href="<%=request.getContextPath() %>/plugins/select/select2.css" rel="stylesheet"/>
		<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
		<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
	</head>
	<body>
	  <div class="mainContainer">
		<div id="menu">
			
		</div>
		<div class="container container_" id="main">
		
		</div>
	  </div>
	<script src="<%=request.getContextPath() %>/plugins/jquery-1.11.2.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath() %>/plugins/ajaxfileupload.js"></script> 
	<script src="<%=request.getContextPath() %>/plugins/amcharts/amcharts.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath() %>/plugins/bootstrap-3.3.2-dist/js/bootstrap.js" type="text/javascript"></script>
<%-- 	<script src="<%=request.getContextPath() %>/plugins/bootstrap.js" type="text/javascript"></script> --%>
<%-- 	<script src="<%=request.getContextPath() %>/plugins/bootstrap-modal.js"></script> --%>
	<!-- ueditor -->
	<script src="<%=request.getContextPath() %>/plugins/ueditor/ueditor.config.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/ueditor/ueditor.all.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/plugins/ueditor/themes/default/css/ueditor.css" />
	<!-- select2 -->
	<link href="<%=request.getContextPath() %>/plugins/select/select2.css" rel="stylesheet"/>
	<script src="<%=request.getContextPath() %>/plugins/select/select2.min.js"></script>
	<!-- jquery_alert_dialogs begin -->
	<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<!-- jquery_alert_dialogs end -->
	<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/md5.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/calendar/WdatePicker.js"></script>
	<script src="<%=request.getContextPath() %>/js/admin.js?version=1.0"></script>
	<script type="text/javascript">
		$.ajaxSetup ({
		    cache: false //关闭AJAX相应的缓存
		});
		$(document).ready(function() {	
			$("#menu").load("admin/menu.jsp");
		});
	</script>
	</body>
</html>