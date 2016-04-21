<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
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
			<input type="hidden" value="${hcv.projectId }" id="_projectId">
			<div style="display: none;" id="_userId">${hcv.userId }</div>
			<div style="display: none;" id="_appId">${hcv.appId }</div>
			<div style="display: none;" id="_fileId">${report.fileId }</div>
			<div>
				<a href="javascript:void(0)" onclick="preview(this)" class="button btn-info" name="change" style="float:right;margin-top:10px;margin-right:-100px;"><i class=""></i>打印</a>
				<a href="javascript:void(0)" onclick="reset()" class="button btn-info" name="change" style="float:right;margin-top:45px;margin-right:-100px;"><i class=""></i>重置</a>
				<a href="javascript:void(0)" onclick="savePage()" class="button btn-info" name="change" style="float:right;margin-top:80px;margin-right:-100px;"><i class=""></i>保存</a>
				<h1>${hcv.companyName }<c:if test="${hcv.companyId!=57 }">${hcv.appName }</c:if>
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
		    	   <div>
					   	<h2 class="mt20">检测结果：</h2>
				        <div class="r_seq">
					        <table class="table table-bordered table-condensed" id="hcvTable" style="width: 800px;">
								<thead>
									<tr>
										<th>File Name<br>(文件名)</th>
										<th style="min-width: 70px;">Subtype<br>(亚型)</th>
										<th style="min-width: 100px;">Subject Name<br>(参考序列名)</th>
										<th style="min-width: 80px;">Identity<br>(相似度)</th>
										<th style="min-width: 200px;">Overlap/total<br>(比对上的长度/比对的总长度)</th>
										<th style="min-width: 70px;">E_value<br>(期望值)</th>
										<th style="min-width: 50px;">Score<br>(比分)</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>${hcv.fileName }</td>
										<td>${hcv.subtype }</td>
										<td>${hcv.subjectName }</td>
										<td>${hcv.identity }</td>
										<td>${hcv.total }</td>
										<td>${hcv.evalue }</td>
										<td>${hcv.score }</td>
									</tr>
								</tbody>
							</table>
				        </div>
				        <br/>
				        <h2 class="mt10">原始序列：</h2>
						<div style="word-break: break-all;">${hcv.seq }</div>
						<br/>
						<c:if test="${hcv.original.size()>0}">
				    	    <h2 class="mt20">原始峰图：</h2>
				    	    <div id="_allDiv">
					   			<c:if test="${hcv.original.containsKey('1_all_png') }">
									<img src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['1_all_png'] }" style="width:100%">
									<br/>
									<br/>
							    </c:if>
							    <c:if test="${hcv.original.containsKey('2_all_png') }">
									<img src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['2_all_png'] }" style="width:100%">
									<br/>
									<br/>
								</c:if>
							    <c:if test="${hcv.original.containsKey('3_all_png') }">
									<img src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['3_all_png'] }" style="width:100%">
									<br/>
									<br/>
							    </c:if>
							    <c:if test="${hcv.original.containsKey('4_all_png') }">
									<img src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['4_all_png'] }" style="width:100%">
									<br/>
									<br/>
							    </c:if>
							    <c:if test="${hcv.original.containsKey('5_all_png') }">
									<img src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['5_all_png'] }" style="width:100%">
									<br/>
									<br/>
								</c:if>
							    <c:if test="${hcv.original.containsKey('6_all_png') }">
									<img src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['6_all_png'] }" style="width:100%">
									<br/>
									<br/>
								</c:if>
				    	    </div>
			    	    </c:if>
		    	   </div>
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
	window.print();
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
var url = window.location.href.split("printHCV")[0];
function savePage(){
	$("body").find("input").each(function(){
		$(this).attr("value",$(this).val());
	});
	$.post(url+"updateContext",{"projectId":$("#_projectId").val(),"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"printContext":$("#printMain").html()},function(result){
		if(result==1){
			alert("信息保存成功！");
		}else{
			alert("信息保存失败！");
		}
	});
}
function reset(){
	if(confirm("确定要重置之前保存的报告吗？")){
		$.post(url+"updateContext",{"projectId":$("#_projectId").val(),"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"printContext":""},function(result){
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
</script>
</html>