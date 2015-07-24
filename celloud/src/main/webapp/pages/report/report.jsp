<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>报告</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="<%=request.getContextPath() %>/css/report.css?version=20150526" rel="stylesheet">
		<link href="<%=request.getContextPath() %>/plugins/select/select2.css" rel="stylesheet"/>
		<link href="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.css" rel="stylesheet">
		<link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	</head>
	<body id="reportBody">
		<div class="clearfix" id="refreshTotalDiv">
		
        </div>
	<script src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/bootstrap-tab.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/bootstrap-modal.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/select/select2.min.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/select/select2_locale_zh-CN.js"></script>
	<!-- spin:loading效果 begin-->
	<script src="<%=request.getContextPath()%>/plugins/spin.min.js" type="text/javascript"></script>
	<!-- spin:loading效果 end-->
	<script src="<%=request.getContextPath() %>/plugins/jquery.ba-resize.js"></script> 
	<script src="<%=request.getContextPath() %>/plugins/calendar/WdatePicker.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/highcharts/highcharts.js"></script>
<%-- 	<script src="<%=request.getContextPath() %>/plugins/highcharts/highcharts-theme.js"></script> --%>
	<script src="<%=request.getContextPath() %>/plugins/highcharts/exporting.js"></script>
	<script src="<%=request.getContextPath() %>/plugins/highcharts/char.js?version=20150526"></script>
	<script src="<%=request.getContextPath() %>/js/report.js?version=20150723"></script>
	<script src="<%=request.getContextPath() %>/js/dataformat.js"></script>
	<script src="<%=request.getContextPath() %>/js/codon.js"></script>
	<script type="text/javascript">
		var session_userId = <%=session.getAttribute("userId")%>;
		var sessionUserName = "<%=session.getAttribute("userName")%>";
		$.ajaxSetup ({
			cache: false //关闭AJAX相应的缓存
		});
		$(document).ready(function(){
			initReport();
		});
	</script>
	</body>
</html>