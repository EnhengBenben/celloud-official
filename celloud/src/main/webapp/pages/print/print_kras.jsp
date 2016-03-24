<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报告打印</title>
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
			<input type="hidden" value="${kras.projectId }" id="_projectId">
			<div style="display: none;" id="_userId">${kras.userId }</div>
			<div style="display: none;" id="_appId">${kras.appId }</div>
			<div style="display: none;" id="_fileId">${report.fileId }</div>
			<div>
				<a href="javascript:void(0)" onclick="preview(this)" class="button btn-info" name="change" style="float:right;margin-top:10px;margin-right:-100px;"><i class=""></i>打印</a>
				<a href="javascript:void(0)" onclick="reset()" class="button btn-info" name="change" style="float:right;margin-top:45px;margin-right:-100px;"><i class=""></i>重置</a>
				<a href="javascript:void(0)" onclick="savePage()" class="button btn-info" name="change" style="float:right;margin-top:80px;margin-right:-100px;"><i class=""></i>保存</a>
				<c:if test="${kras.companyId==41 }">
					<hr name="change" style="float:right;margin-top: 115px; margin-right: -150px;width: 130px;border: solid 1px #d7d7d7;">
					<a href="javascript:void(0)" onclick="showGeneType()" class="a-green-normal" name="change" style="float:right;margin-top:125px;margin-right:-145px;"><i class=""></i>基因分型检测报告单</a>
					<a href="javascript:void(0)" onclick="showResistance()" class="a-green-normal" name="change" style="float:right;margin-top:150px;margin-right:-145px;"><i class=""></i>耐药突变检测报告单</a>
				</c:if>
				<c:if test="${flag==0 }">
					<div class="container" style="display: none;"></div>
				</c:if>
				<h1>${kras.companyName }${kras.appName }
					<c:if test="${kras.companyId==41 }">
						<span name="geneType">基因分型检测</span>
						<span name="resistanceType" class="hide">耐药突变检测</span><br>
					</c:if>报告单
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
			    	    <c:if test="${kras.position!=null && kras.position!='' }">
				    		${kras.position }
			    		</c:if>
			    		<c:if test="${kras.position==null || kras.position=='' }">
			    			未检测到突变
			    		</c:if>
		    	    </p>
		    	    <c:if test="${kras.conclusion!=null && kras.conclusion.trim()!=''}">
			    	    <h2 class="mt20">结论：</h2>
			    	    <p>${kras.conclusion }</p>
		    	    </c:if>
		    	    <c:if test="${kras.seq!=null && kras.seq.trim()!='' }">
			    	    <h2 class="mt20">原始序列：</h2>
			    	    <p style="word-break: break-all;">${kras.seq }</p>
		    	    </c:if>
		    	    <div class="w3cbbs"></div>
		    	    <h2 class="mt20">原始峰图：</h2>
		    	    <p style="width:750px;">
		    	    	<c:if test="${kras.original!=null }">
					    	<c:forEach var="original" items="${kras.original }">
								<img src="${uploadPath}${kras.userId }/${kras.appId }/${kras.dataKey }/SVG/${original }" style="width: 100%;">
				   				<br/><br/>
					    	</c:forEach>
						</c:if>
			   			<c:if test="${kras.original==null }">
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
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/browser.js"></script>
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
	$(".container").css("display","");
	var _flag = $("#_flag").html();
	if(_flag==1){
		$("h1").css("padding","0 0 5px 0");
	}
	window.print();
	if(_flag==1){
		$("h1").css("padding","40px 0 5px 0");
	}
	$(".w3cbbs").css("display","none");
	$(".container").css("display","none");
	$("a[name='change']").show();
	$("hr[name='change']").show();
	$("body").find("span[name='print']").each(function(){
		inputVal = $(this).html();
		var cl = $(this).attr("class");
		if(cl){
			$(this).parent().html("突变型:<input type='text' class='"+cl+"' value='"+inputVal+"'>");
		}else{
			$(this).parent().html("<input type='text' value='"+inputVal+"'>");
		}
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
});
var url = window.location.href.split("printKRAS")[0];
function savePage(){
	$("body").find("input").each(function(){
		$(this).attr("value",$(this).val());
	});
	$.post(url+"updateContext",{"projectId":$("#_projectId").val(),"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"flag":0,"printContext":$("#printMain").html()},function(result){
		if(result==1){
			alert("信息保存成功！");
		}else{
			alert("信息保存失败！");
		}
	});
}
function reset(){
	if(confirm("确定要重置之前保存的报告吗？")){
		$.post(url+"updateContext",{"projectId":$("#_projectId").val(),"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"flag":0,"printContext":""},function(result){
			if(result==1){
			  	location=location ;
			}else{
				alert("信息重置失败！");
			}
		});
	}
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

function showResistance(){
	$("div[name='resistanceType']").removeClass("hide");
	$("span[name='resistanceType']").removeClass("hide");
	$("h2[name='resistanceType']").removeClass("hide");
	$("div[name='geneType']").addClass("hide");
	$("span[name='geneType']").addClass("hide");
	$("h2[name='geneType']").addClass("hide");
}
function showGeneType(){
	$("div[name='geneType']").removeClass("hide");
	$("span[name='geneType']").removeClass("hide");
	$("h2[name='geneType']").removeClass("hide");
	$("div[name='resistanceType']").addClass("hide");
	$("span[name='resistanceType']").addClass("hide");
	$("h2[name='resistanceType']").addClass("hide");
}
</script>
</html>