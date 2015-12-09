<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="stylesheet" href="<%=request.getContextPath() %>/content/css/style.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/content/css/feedback.css" />
<title>Celloud-用户报告</title>
</head>
<body style="overflow: auto;">
	<input type="hidden" id="proIdHidden" value="<s:property value="projectId"/>">
	<input type="hidden" id="reportHidden" value="<s:property value="reportResult"/>">
	<div class="themeWrapper clearfix">
	<div class="tabbable user-info"> <!-- tabs -->
        <ul class="nav nav-tabs">
            <li id="liPro" class="tab1" onclick="javascript:showProReport();"><a data-toggle="tab" style="text-decoration: none;cursor: pointer;">项目报告</a></li>
            <li id="liData" class="tab2 active" onclick="javascript:showDataReport();"><a data-toggle="tab" style="text-decoration: none;cursor: pointer;">数据报告</a></li>
        </ul>
        <div class="tab-content" style="height: 650px;"> 
            <div class="tab-pane" id="tab1" style="margin-left: 0px;width: 900px;">  
            	
            </div>
            <div class="tab-pane active" id="tab2" style="margin-left: 0">  
            	
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jquery-1.11.3.min.js"></script>
<!-- amcharts -->
<script src="<%=request.getContextPath() %>/plugins/amcharts/amcharts.js"></script>
<script src="<%=request.getContextPath() %>/js/reportForEmail.js"></script>
<script type="text/javascript">
$.ajaxSetup ({
    cache: false //关闭AJAX相应的缓存
});
$(document).ready(function() {	
	showReport();
});
</script>
</body>
</html>