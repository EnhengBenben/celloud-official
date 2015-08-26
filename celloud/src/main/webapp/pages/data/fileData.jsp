<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<title>数据管理</title>
<%-- <link href="<%=request.getContextPath() %>/css/report.css?version=20150526" rel="stylesheet"> --%>
<link href="<%=request.getContextPath() %>/css/modal.css?v=20140416" rel="stylesheet">
<link href="<%=request.getContextPath() %>/plugins/select/select2.css" rel="stylesheet"/>
<link href="<%=request.getContextPath() %>/css/data.css?v=20150527" rel="stylesheet">
<!-- 数据报告视图table hidden -->
<link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body id="fileDataBody">
<input type="hidden" id="fileDataSessionUserIdHidden" value='<s:property value="#session.userId"/>'>
<div class="clearfix">
    <div class="tabbable"> <!-- tabs -->
    	<ul class="nav-tabs">
		    <li class="active"><a href="javascript:void(0)" data-toggle="tab"><i class="i-reportList"></i>数据列表</a></li>
		</ul>
		<div class="tab-content">
	        <input type="hidden" id="userRole">
	        <div> 
	            <!-- 数据管理 -->
	            <div class="tab-pane" id="tab1">
	            	<div class="m-fileList">
	<!--             		<h4>我的数据</h4> -->
<!-- 						<div class="m-fileList"> -->
							<div class="searcharea"><input type="text" class="input" placeholder="搜索文件名/数据标签/文件别名" id="dataTagSearch" onkeyup="javascript:searchData(1);" onkeydown="javascript:keyDownToSearch();" onfocus="javascript:hideSearchInputInfo();" onblur="javascript:showSearchInputInfo();"/></div>
							<a href="javascript:searchData(1);"><img src="<%=request.getContextPath()%>/images/publicIcon/search.png"/></a>
							<div class="pull-right">
								<div class="btn-group" style="float: left;margin-right:15px;" id="selectAppDiv">
									<button class="btn btn-blue" id="appTextBtn" onclick="javascript:runMultiDataNew();">选择App</button>
									<button class="btn btn-blue dropdown-toggle" id="dataSelectAppBtn" data-toggle="dropdown" onclick="javascript:showAppsForData();"><span class="caret"></span></button>
								     <ul class="dropdown-menu" id="appsForDataUl">
								     </ul>
								</div>
								<button onclick="javascript:deleteDatas();" id="delDataBtn" class="btn disabled" disabled="disabled">删除</button>
							  	<button onclick="javascript:tobatchManageModel();" id="batchManage" class="btn disabled" disabled="disabled">批量管理</button>
							</div>
<!-- 						</div> -->
					</div>  
	                <div class="clearfix tablebox" id="selfDataDiv" style="display: none;">
	                </div>
	<!--                 <div class="clearfix tablebox" id="dataSharedToMeDiv" style="display: none;"> -->
	                	
	<!--                 </div> -->
	            </div>
	        </div>
	    </div>
    </div>
</div>
<script src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap-tab.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap-modal.js"></script>
<script src="<%=request.getContextPath() %>/plugins/select/select2.min.js"></script>
<script src="<%=request.getContextPath() %>/plugins/select/select2_locale_zh-CN.js"></script>
<script src="<%=request.getContextPath() %>/plugins/table_sort.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/baidu.js"></script>
<!-- jquery_alert_dialogs begin -->
<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.ui.draggable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
<!-- jquery_alert_dialogs end -->
<!-- spin:loading效果 begin-->
<script src="<%=request.getContextPath()%>/plugins/spin.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/data.js?version=201507025" type="text/javascript"></script>
<!-- spin:loading效果 end-->
<script type="text/javascript">
	var session_userId = <%=session.getAttribute("userId")%>;
	var sessionUserName = "<%=session.getAttribute("userName")%>";
	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	$(document).ready(function(){
		initData();
	});
</script>
</body>
</html>