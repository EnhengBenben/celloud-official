<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<title></title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/style.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/software.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/database.css">
	<link href="<%=request.getContextPath() %>/css/buttons.css?version=20150730" rel="stylesheet">
	</head>
	<body>
		<div class="container app">
			<div class="header">
				<div class="fl"><img src="<%=request.getContextPath()%>/images/software/APP_logo.png" /></div>
	    		<div class="fr" style="margin-right: 40px;">
	    			<span id="num"></span>
					<input type="text" id="search_softName" placeholder="搜索软件" /><a href="javascript:void(0);" class="search-new" id="search" style="">search</a>
				</div>
		    </div>
		    <div class="main-new">
		    	<div class="leftBar" id="leftBarDiv" style="height: 500px;">
          			<div class="accordion" id="showList"> 
          				
          			</div>
          		</div>
          		<div class="mainContext" style="width: 635px;margin-left: 200px;margin-top: -557px;display: none;" id="softDetailDiv">
				
				</div>
				<div class="main" style="width: 500px;margin-left: 400px;padding:0;margin-top: -557px" id="softListDiv">
					<ul class="content" id="showDiv" style="margin-left:60px;margin-top:60px">
					</ul>
				</div>
		   	</div>
		   	
		</div>
	<script src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath() %>/plugins/bootstrap.js" type="text/javascript" ></script>
<%-- 	<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/baidu.js"></script> --%>
	<!-- jquery_alert_dialogs begin -->
	<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.ui.draggable.js" type="text/javascript"></script>
	<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
	<link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<!-- jquery_alert_dialogs end -->
	<script type="text/javascript">
		var sessionUserRole = <%=session.getAttribute("userRole")%>;
		$(document).ready(function(){
			initSoftware();
		});
	</script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/js/software.js"></script>
	</body>
</html>