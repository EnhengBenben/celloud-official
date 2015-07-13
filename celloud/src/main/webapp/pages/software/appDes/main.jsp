<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>App_Info</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/pages/software/appDes/css/appDes.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.css">
</head>
<body>
	<div class="app">
		<div class="header">
			<div class="fl">
				<font size="5"><b>Apps</b></font>
				<input type="hidden" value="${param.appName}" id="appName"/>
			</div>
		</div>
		<div class="main-new">
			<div class="leftBar">
				<div class="accordion">
					<s:iterator value="list" id="soft">
						<s:if test="%{#soft.appDoc!=null&&!#soft.appDoc.equals('')}">
							<h2 onclick="jumpTo('<s:property value="#soft.softwareName"/>')" name="<s:property value="#soft.softwareName"/>">
								<span class="arrow"></span> <s:property value="#soft.softwareName"/>
							</h2>
						</s:if>
					</s:iterator>
				</div>
			</div>
			<div class="mainContent" id="main" style="overFlow-x: hidden;">
				<s:iterator value="list" id="soft">
					<s:if test="%{#soft.appDoc!=null&&!#soft.appDoc.equals('')}">
						<div id="<s:property value="#soft.softwareName"/>" style="display: none;">
							<s:property value="#soft.appDoc" escape="false"/>
						</div>
					</s:if>
				</s:iterator>
			</div>
		</div>
	</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery.ba-resize.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#main").resize(function(){
		height = $("#main").height();
		$(".leftBar").css("min-height",height);
	});
	$(window).manhuatoTop({
		showHeight : 100,
		speed : 1000
	});
	if($("#appName").val()){
		jumpTo($("#appName").val());
	}else{
		$(".accordion").children().first().addClass("active");
		$(".mainContent").children().first().css("display","");
		$(".fr").css("display","none");
	}
});
function jumpTo(app){
	$(".accordion").children().each(function(){
		$(this).removeClass("active");
	});
	$("h2[name='"+app+"']").addClass("active");
	$("#main").children().each(function(){
		$(this).css("display","none");
	});
	$("#"+app).css("display","");
}

function linkTo(flag){
	$(".accordion").children().each(function(){
		$(this).removeClass("active");
	});
	if(flag ==1){
		$("#main").load("RNA_DeNovo.jsp");
		$("#RNA_DeNovo").addClass("active");
	}else{
		$("#main").load("RNA_DN_Diff.jsp");
		$("#RNA_DN_Diff").addClass("active");
	}
}
</script>
</body>
</html>