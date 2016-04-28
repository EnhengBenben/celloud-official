<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报告打印</title>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/browser.js"></script>
<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.ui.draggable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/print_hbv.css?version=1.16">
</head>
<body>
	<div>
		<c:if test="${empty printContext}">
			<input type="hidden" value="1" id="isSaved"/>
		</c:if>
		<c:if test="${not empty printContext}">
			<input type="hidden" value="0" id="isSaved"/>
		</c:if>
	</div>
	<div id="printMain">
		<c:if test="${empty printContext}">
			<input type="hidden" value="${egfr.projectId }" id="_projectId">
			<div style="display: none;" id="_userId">${egfr.userId }</div>
			<div style="display: none;" id="_appId">${egfr.appId }</div>
			<div style="display: none;" id="_fileId">${report.fileId }</div>
			<div>
				<a href="javascript:void(0)" onclick="preview(this)" class="button btn-info" name="change" style="float:right;margin-top:10px;margin-right:-100px;"><i class=""></i>打印</a>
				<a href="javascript:void(0)" onclick="reset()" class="button btn-info" name="change" style="float:right;margin-top:45px;margin-right:-100px;"><i class=""></i>重置</a>
				<a href="javascript:void(0)" onclick="savePage()" class="button btn-info" name="change" style="float:right;margin-top:80px;margin-right:-100px;"><i class=""></i>保存</a>
				<h1>${egfr.companyName }<c:if test="${egfr.companyId!=57 }">${egfr.appName }</c:if>
				</h1>
			    <hr />
			    <div class="wrapper">
			        <ul class="info mt5">
			            <li>姓名：<span><input type="text"></span></li>
			            <li>ID号：<span><input type="text"></span></li>
			            <li>样本编号：<span><input type="text"></span></li>
			            <li>样本种类：<span><input type="text"></span></li>
			            <li>性别：<span id="_sex"><input type="radio" name="sex" value="男" checked="checked" onclick="radioClick(0)" id="men">男<input type="radio" name="sex" value="女" onclick="radioClick(1)" id="women">女</span></li>
			            <li>科室：<span><input type="text"></span></li>
			            <li>送检日期：<span><input type="text"></span></li>
			            <li>送检医生：<span><input type="text"></span></li>
			            <li>年龄：<span><input type="text"></span>岁</li>
			            <li>床号：<span><input type="text"></span></li>
			            <li>住院号：<span><input type="text"></span></li>
			        </ul>
			    </div>
			    <hr class="hr-bold mt5" />
			    <div class="wrapper" style="min-height:800px;" id="mainDIv">
		    	    <h2 class="mt20">已知位点：</h2>
		    	    <p>
				    	<c:if test="${egfr.position!=null && egfr.position!='' }">
				    		${egfr.position }
			    		</c:if>
			    		<c:if test="${egfr.position==null || egfr.position=='' }">
			    			未检测到突变
			    		</c:if>
			    	</p>
			    	<c:if test="${egfr.knowMutationBig!=null }">
				    	<c:forEach var="kmb" items="${egfr.knowMutationBig }">
				    		<p>
				    			<br/>
								<img src="${uploadPath}${egfr.userId }/${egfr.appId }/${egfr.dataKey }/SVG/${kmb }" style="width: 900px;height: 136px;">
							</p>
				    	</c:forEach>
					</c:if>
		    	    <c:if test="${egfr.conclusion!=null && egfr.conclusion.trim()!=''}">
			    	    <h2 class="mt20">结论：</h2>
			    	    <p>${egfr.conclusion }</p>
		    	    </c:if>
		    	    <c:if test="${egfr.seq!=null && egfr.seq.trim()!='' }">
			    	    <h2 class="mt20">原始序列：</h2>
			    	    <p style="word-break: break-all;">${egfr.seq }</p>
		    	    </c:if>
		    	    <div class="w3cbbs"></div>
		    	    <h2 class="mt20">原始峰图：</h2>
		    	    <p style="width:750px;">
			   			<c:if test="${egfr.original!=null }">
					    	<c:forEach var="original" items="${egfr.original }">
								<img src="${uploadPath}${egfr.userId }/${egfr.appId }/${egfr.dataKey }/SVG/${original }" style="width: 100%;">
								<br/><br/>
					    	</c:forEach>
						</c:if>
				     	<c:if test="${egfr.original==null }">
						    样本异常，无法检测
						</c:if>
		    	    </p>
			    </div>
			    <hr/>
			    <div class="wrapper">
			        <ul class="footer">
			            <li>检验日期：<span><input type="text"></span></li>
			            <li>报告日期：<span><input type="text"></span></li>
			            <li class="right">检验者：<span><input type="text"></span></li>
			            <li class="right">审核者：<span><input type="text"></span></li>
			        </ul>
			    </div>
			</div>
		</c:if>
		<c:if test="${not empty printContext}">
			${printContext }
		</c:if>
	</div>
</body>
<script type="text/javascript">
function preview(obj){
	var inputVal;
	$("body").find("input[type='text']").each(function(){
		inputVal = $(this).val();
		$(this).parent().html("<span name='print'>"+inputVal+"</span>");
	});
	var sex = $("input[type='radio']:checked").val();
	$("#_sex").html(sex);
	$("a[name='change']").hide();
	$("hr[name='change']").hide();
	$(".w3cbbs").css("display","");
	window.print();
	$(".w3cbbs").css("display","none");
	$("a[name='change']").show();
	$("hr[name='change']").show();
	$("body").find("span[name='print']").each(function(){
		inputVal = $(this).html();
		$(this).parent().html("<input type='text' value='"+inputVal+"'>");
	});
	$("#_sex").html("<input type='radio' name='sex' value='男'>男<input type='radio' name='sex' value='女'>女");
	$("input[type='radio'][value="+sex+"]").attr("checked",true); 
}
$(document).ready(function(){
	var browser = $.NV('name');
	var allHeight;
	if(browser=='firefox'){
		allHeight = 1900;
	}else if(browser=='chrome'){
		allHeight = 2000;
	}else if(browser=='safari'){
		allHeight = 1500;
	}else {
		allHeight = 1600;
	}
	$("#_allDiv").height(allHeight);
	$(".table").find("td").each(function(){
		$(this).css("vertical-align","middle");
		$(this).css("word-break","break-all");
		$(this).css("word-wrap","break-word");
	});
});
var url = window.location.href.split("printEGFR")[0];
function savePage(){
	$("body").find("input").each(function(){
		$(this).attr("value",$(this).val());
	});
	$.post(url+"updateContext",{"projectId":$("#_projectId").val(),"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"flag":0,"printContext":$("#printMain").html()},function(result){
		if(result==1){
			jAlert("信息保存成功！");
		}else{
			jAlert("信息保存失败！");
		}
	});
}
function reset(){
  jConfirm('确定要重置之前保存的报告吗？', '重置报告', function(result) {
		$.post(url+"updateContext",{"projectId":$("#_projectId").val(),"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"flag":0,"printContext":""},function(result){
			if(result==1){
				location=location ;
			}else{
				jAlert("信息重置失败！");
			}
		});
	});
}
function radioClick(num){
	if(num==0){
		$("#men").attr("checked","checked");
		$("#women").removeAttr("checked");
	}else{
		$("#women").attr("checked","checked");
		$("#men").removeAttr("checked");
	}
}

</script>
</html>