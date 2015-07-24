<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报告打印</title>
<link rel="stylesheet" href="/celloud/css/style_print.css?version=1.1">
<style type="text/css">
textarea {
	width: 800px;	
}
</style>
</head>
<body>
	<div id="printMain">
		<c:if test="${empty pageTxt}">
			<div style="display: none;" id="_userId">${userId }</div>
			<div style="display: none;" id="_appId">${appId }</div>
			<div style="display: none;" id="_fileId">${fileId }</div>
			<div class="container">
				<a href="javascript:void(0)" onclick="preview(this)" class="button btn-info" name="change" style="float:right;margin-top:20px;margin-right:30px;"><i class=""></i>打印</a>
				<a href="javascript:void(0)" onclick="reset()" class="button btn-info" name="change" style="float:right;margin-top:20px;"><i class=""></i>重置</a>
				<a href="javascript:void(0)" onclick="savePage()" class="button btn-info" name="change" style="float:right;margin-top:20px;"><i class=""></i>保存</a>
				<h1>${txt }报告单</h1>
			    <hr />
			    <div class="wrapper">
			        <ul class="info mt20">
			            <li>姓名：<span><input type="text"></span></li>
			            <li>性别：<span id="_sex"><input type="radio" name="sex" value="男" checked="checked" onclick="radioClick(0)" id="men">男<input type="radio" name="sex" value="女" onclick="radioClick(1)" id="women">女</span></li>
			            <li>年龄：<span><input type="text"></span>岁</li>
			            <li>样本编号：<span><input type="text"></span></li>
			            <li>ID号：<span><input type="text"></span></li>
			            <li>科室：<span><input type="text"></span></li>
			            <li>住院号：<span><input type="text"></span></li>
			            <li>床号：<span><input type="text"></span></li>
			            <li>样本种类：<span><input type="text"></span></li>
			            <li>送检医生：<span><input type="text"></span></li>
			            <li>送检日期：<span><input type="text"></span></li>
			        </ul>
			        <c:if test="${appId==82 }">
						<p class=" gray mt20">送检目的：HBV基因分型，拉米夫定LAM，阿德福韦ADV，恩替卡韦ETV，替比夫定LDT，替诺福韦酯TDF，恩曲他滨FTC</p>
				        <p class="gray">相关位点：rt169，rt173，rt180，rt181，rt184，rt194，rt202，rt204，rt215，rt233，rt236，rt250</p>
					</c:if>
			    </div>
			    <hr class="hr-bold mt20" />
			    <div class="wrapper" style="min-height:1000px;" id="mainDIv">
			    	<c:choose>
			    	   <c:when test="${appId==84||appId==89 }">
			    	    <h2 class="mt20">报告：</h2>
			    	    <p>${context }</p>
			    	    <br>
			    	    <h2 class="mt20">原始峰图</h2>
			    	    <p style="width:750px;">
					       	<c:if test="${imgList.size()>0}">
					   			<c:forEach items="${imgList}" var="imgHtml">
					   				<img src="${imgHtml }" style="width:100%"/>
					   			</c:forEach>
				   			</c:if>
			    	    </p>
					   </c:when>
			    	   <c:when test="${appId==90 }">
			    	    <h2 class="mt20">报告：</h2>
			    	    <p>${context }</p>
			    	    <br>
			    	    <h2 class="mt20">原始峰图</h2>
			    	    <p style="width:750px;">
					       	<c:if test="${imgList.size()>0}">
					   			<c:forEach items="${imgList}" var="imgHtml">
					   				<img src="${imgHtml }" style="width:100%"/>
					   			</c:forEach>
				   			</c:if>
			    	    </p>
					   </c:when>
			    	   <c:when test="${appId==80 }">
			    	   <div>
						   	<h2 class="mt20">检测结果：</h2>
					        <div class="r_seq">
					        ${context }
					        </div> 
			    	   </div>
					   </c:when>
					   <c:when test="${appId==82 }">
					   	<h2 class="mt20">耐药位点突变检测结果：</h2>
				        <ul class="result mt20">
				        	<c:if test="${imgList.size()>0}">
					   			<c:forEach items="${imgList}" var="imgHtml">
					   				<li onmouseover="showDelete(this)" onmouseout="hideDelete(this)">
						   				<img src="${imgHtml }"/>
						   				<em onclick="deleteLi(this)"></em>
					   				</li>
					   			</c:forEach>
				   			</c:if>
				        </ul>
				        <h2 class="mt20">基因分型结果：</h2>
				        <p>${snpType }</p>
				        <h2 class="mt20">耐药结论：</h2>
				        <div id="des">
<textarea rows="3">
耐药：${context }
敏感：${sensitive }
</textarea>
				        </div>
				        <p>&nbsp; </p>
				        <h2 class="mt20">备注：</h2>
				        <p>拉米夫定LAM突变检测：V173L、L180M、M204I/V</p>
				        <p>阿德福韦ADV突变检测：A181V/T、N236T</p>
				        <p>恩替卡韦ETV突变检测：I169T、L180M、T184A/G/S/I/L/F、S202G/I、M204V、M250V/L/I</p>
				        <p>替比夫定LDT突变检测：M204I</p>
				        <p>替诺福韦酯TDF突变检测：A194T</p>
				        <p>恩曲他滨FTC突变检测：V173L、L180M、M204I/V</p>
				        <p>&nbsp; </p>
					   </c:when>
					</c:choose>	
			    </div>
			    <hr class="mt20" />
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
		<c:if test="${not empty pageTxt}">
			${pageTxt }
		</c:if>
	</div>
</body>
<script type="text/javascript" src="/celloud/plugins/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/celloud/js/browser.js"></script>
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
	inputVal = $("#des").children().val().replace(/\n/g,"<br>");
	$("#des").html(inputVal);
	window.print();
	$("a[name='change']").show();
	$("body").find("span[name='print']").each(function(){
		inputVal = $(this).html();
		$(this).parent().html("<input type='text' value='"+inputVal+"'>");
	});
	$("#_sex").html("<input type='radio' name='sex' value='男'>男<input type='radio' name='sex' value='女'>女");
	$("input[type='radio'][value="+sex+"]").attr("checked",true); 
	inputVal = $("#des").html().replace(/<br>/g,"\n");
	$("#des").html("<textarea rows=\"3\">"+inputVal+"</textarea>");
}
function hideDelete(obj){
	$(obj).find("em").removeClass("new");
}
function showDelete(obj){
	$(obj).find("em").addClass("new");
}
function deleteLi(obj){
	$(obj).parent().remove();
}
$(document).ready(function(){
	var browser = $.NV('name');
	var height;
	if(browser=='firefox'){
		height = 1480;
	}else if(browser=='chrome'){
		height = 2500;
	}
	if($("#mainDIv").height()>1000){
		$("#mainDIv").css("min-height",height+"px");
	}
});
function savePage(){
	$("body").find("input").each(function(){
		$(this).attr("value",$(this).val());
	});
	$("#des").children().html($("#des").children().val());
	var url = "http://localhost:8080/celloud/";
	$.post(url+"updateContext",{"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"flag":0,"context":$("#printMain").html()},function(result){
		if(result==1){
			alert("信息保存成功！");
		}else{
			alert("信息保存失败！");
		}
	});
}
function reset(){
	if(confirm("确定要重置之前保存的报告吗？")){
		var url = "http://localhost:8080/celloud/";
		$.post(url+"updateContext",{"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"flag":0,"context":""},function(result){
			if(result==1){
				alert("请重新打开页面");
				window.close();
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