<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报告打印</title>
<link rel="stylesheet" href="css/print_hbv.css?version=1.16">
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
			<input type="hidden" value="${report.projectId }" id="_projectId">
			<div style="display: none;" id="_userId">${report.userId }</div>
			<div style="display: none;" id="_appId">${appId }</div>
			<div style="display: none;" id="_fileId">${report.fileId }</div>
			<div style="display: none;" id="_flag">${flag }</div>
			<div>
				<a href="javascript:void(0)" onclick="preview(this)" class="button btn-info" name="change" style="float:right;margin-top:10px;margin-right:-100px;"><i class=""></i>打印</a>
				<a href="javascript:void(0)" onclick="reset()" class="button btn-info" name="change" style="float:right;margin-top:45px;margin-right:-100px;"><i class=""></i>重置</a>
				<a href="javascript:void(0)" onclick="savePage()" class="button btn-info" name="change" style="float:right;margin-top:80px;margin-right:-100px;"><i class=""></i>保存</a>
				<c:if test="${company.companyId==41 }">
					<hr name="change" style="float:right;margin-top: 115px; margin-right: -150px;width: 130px;border: solid 1px #d7d7d7;">
					<a href="javascript:void(0)" onclick="showGeneType()" class="a-green-normal" name="change" style="float:right;margin-top:125px;margin-right:-145px;"><i class=""></i>基因分型检测报告单</a>
					<a href="javascript:void(0)" onclick="showResistance()" class="a-green-normal" name="change" style="float:right;margin-top:150px;margin-right:-145px;"><i class=""></i>耐药突变检测报告单</a>
				</c:if>
				<c:if test="${flag==0 }">
					<div class="container" style="display: none;"></div>
				</c:if>
				<h1>${company.companyName }${app.appName }
					<c:if test="${company.companyId==41 }">
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
			        <c:if test="${appId==82 }">
			        	<c:choose>
			        		<c:when test="${company.companyId==41 }">
			        			<div class="gray">送检目的：<span name="geneType">HBV基因分型</span><span name="resistanceType" class="hide">拉米夫定LAM，阿德福韦ADV，恩替卡韦ETV，替比夫定LDT，替诺福韦酯TDF，恩曲他滨FTC</span></div>
					        	<div name="resistanceType" class="gray hide">相关位点：rt169，rt173，rt180，rt181，rt184，rt194，rt202，rt204，rt215，rt233，rt236，rt250</div>
			        		</c:when>
			        		<c:otherwise>
			        		<div class="gray">送检目的：HBV基因分型，拉米夫定LAM，阿德福韦ADV，恩替卡韦ETV，替比夫定LDT，替诺福韦酯TDF，恩曲他滨FTC</div>
					        <div class="gray">相关位点：rt169，rt173，rt180，rt181，rt184，rt194，rt202，rt204，rt215，rt233，rt236，rt250</div>
			        		</c:otherwise>
			        	</c:choose>
					</c:if>
			    </div>
			    <hr class="hr-bold mt5" />
			    <div class="wrapper" style="min-height:800px;" id="mainDIv">
			    	<c:choose>
			    	   <c:when test="${appId==89 || appId==84 }">
			    		<!-- KRAS || EGFR -->
				    	    <h2 class="mt20">已知位点：</h2>
				    	    <p>${table }</p>
				    	    <c:if test="${allPic!=null }">
					    	    <p><img src="${allPic }" style="width: 900px;height: 136px;"></p>
				    	    </c:if>
				    	    <c:if test="${result!=null && result.trim()!=''}">
					    	    <h2 class="mt20">结论：</h2>
					    	    <p>${result }</p>
				    	    </c:if>
				    	    <c:if test="${seq!=null && seq.trim()!='' }">
					    	    <h2 class="mt20">原始序列：</h2>
					    	    <p style="word-break: break-all;">${seq }</p>
				    	    </c:if>
				    	    <div class="w3cbbs"></div>
				    	    <h2 class="mt20">原始峰图：</h2>
				    	    <p style="width:750px;">
						       	<c:if test="${imgList.size()>0}">
						   			<c:forEach items="${imgList}" var="imgHtml">
						   				<img src="${imgHtml }" style="width:100%"/>
						   				<br/><br/>
						   			</c:forEach>
					   			</c:if>
					   			<c:if test="${imgList==null}">
					   				样本异常，无法检测
					   			</c:if>
				    	    </p>
					   </c:when>
			    	   <c:when test="${appId==90 }">
			    	    <h2 class="mt20">报告：</h2>
			    	    <p>${context }</p>
			    	    <br>
			    	    <h2 class="mt20">原始峰图：</h2>
			    	    <p style="width:750px;">
					       	<c:if test="${imgList.size()>0}">
					   			<c:forEach items="${imgList}" var="imgHtml">
					   				<img src="${imgHtml }" style="width:100%"/>
					   				<br/><br/>
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
					        <br/>
					        <h2 class="mt10">原始序列：</h2>
							<div style="word-break: break-all;">${seq }</div>
							<br/>
							<c:if test="${imgList.size()>0}">
					    	    <h2 class="mt20">原始峰图：</h2>
					    	    <div id="_allDiv">
						   			<c:forEach items="${imgList}" var="imgHtml">
						   				<img src="${imgHtml }" style="width:100%"/>
						   				<br/><br/>
						   			</c:forEach>
					    	    </div>
				    	    </c:if>
			    	   </div>
					   </c:when>
					   <c:when test="${appId==82 }">
			   			<c:choose>
			        		<c:when test="${company.companyId==41 }">
			        			<h2 name="geneType" class="mt10">基因分型结果：</h2>
			        			<div name="geneType" class="m-box_1">
			        				${hbv.type.replace('Type','基因型') }
			        			</div>
			        			<h2 name="resistanceType" class="mt10 hide">一、耐药突变位点检测结果：</h2>
			        			<div name="resistanceType" class="m-box_1 hide">
						        	${table }
							        <p class="annotation">注：深背景色<span class="_hard colorspan">&nbsp;</span>表示发生了耐药突变<br>
							        	浅背景色<span class="_light colorspan">&nbsp;</span>表示发生了突变，但是暂无文献支持其和耐药相关<br>
							       		 红色字体表示在样本中未找到该位点
							        </p>
						        </div>
						        <c:choose>
			        				<c:when test="${flag==0 }">
							          <div name="resistanceType" class="hide">
							        	<div class="w3cbbs" style="display: none;"></div>
										<div class="container" style="display: none;"></div>
									   	<h2 class="mt10">二、耐药位点突变检测结果：</h2>
								   		<div class="clearCss_3">${peakFigure }</div>
									   	<p class="annotation m-box_1">注：1. 突变结果解释：M 204 M|V {A-G}，第一个字母M，野生型编码氨基酸为Ｍ， 204为氨基酸位置，M|V：氨基酸由M变为V，{A-G}：碱基由A变为G<br>
									   		2. Wild Type: GCT;表示该位点的野生型为GCT<br>3. 峰图中的*号，表示该位置发生了突变
									   	</p>
									   	<div id="lessDiv">
											<div class="w3cbbs" style="display: none;"></div>
											<div class="container" style="display: none;"></div>
									   	</div>
								        <h2 class="mt10">三、参考结论（根据已发表文献得出以下参考结论）：</h2>
								        <div class="m-box_1" id="des">
											<textarea rows="6">${result }</textarea>
								        </div>
									   	<h2 class="mt10">四、测序序列结果：</h2>
									   	<p style="word-break: break-all;" class="m-box_1">${hbv.seq }</p>
									   	<div id="moreDiv">
											<div class="w3cbbs" style="display: none;"></div>
											<div class="container" style="display: none;"></div>
									   	</div>
										<div id="SNPEND">
										   	<h2 class="mt10">五、测序峰图结果：</h2>
										   	<div class="m-box_1 clearCss_6" id="picture_6">
											   	<p>${allPic }</p>
											   	<p class="annotation">注：峰图中的*号表示该位点发生了突变</p>
										   	</div>
										</div>
									  </div>
						        	</c:when>
						        	<c:otherwise>
						        		<h2 class="mt10">二、参考结论（根据已发表文献得出以下参考结论）：</h2>
								        <div class="m-box_1" id="des">
											<textarea rows="6">${result }</textarea>
								        </div>
						        	</c:otherwise>
						        </c:choose>
			        		</c:when>
			        		<c:otherwise>
			        			<h2 class="mt10">一、基因分型结果：<span style="font-size:12px;font-weight: normal">${hbv.type.replace('Type','基因型') }</span></h2>
			        			<h2 class="mt10">二、耐药突变位点检测结果：</h2>
			        			<div class="m-box_1">
						        	${table }
							        <p class="annotation">注：深背景色<span class="_hard colorspan">&nbsp;</span>表示发生了耐药突变<br>
							        	浅背景色<span class="_light colorspan">&nbsp;</span>表示发生了突变，但是暂无文献支持其和耐药相关<br>
							       		 红色字体表示在样本中未找到该位点
							        </p>
						        </div>
						        <c:if test="${flag==0 }">
						        	<div class="w3cbbs" style="display: none;"></div>
									<div class="container" style="display: none;"></div>
								   	<h2 class="mt10">三、耐药位点突变检测结果：</h2>
							   		<div class="clearCss_3">${peakFigure }</div>
								   	<p class="annotation m-box_1">注：1. 突变结果解释：M 204 M|V {A-G}，第一个字母M，野生型编码氨基酸为Ｍ， 204为氨基酸位置，M|V：氨基酸由M变为V，{A-G}：碱基由A变为G<br>
								   		2. Wild Type: GCT;表示该位点的野生型为GCT<br>3. 峰图中的*号，表示该位置发生了突变
								   	</p>
								   	<div id="lessDiv">
										<div class="w3cbbs" style="display: none;"></div>
										<div class="container" style="display: none;"></div>
								   	</div>
							        <h2 class="mt10">四、参考结论（根据已发表文献得出以下参考结论）：</h2>
							        <div class="m-box_1" id="des">
										<textarea rows="6">${result }</textarea>
							        </div>
								   	<h2 class="mt10">五、测序序列结果：</h2>
								   	<p style="word-break: break-all;" class="m-box_1">${hbv.seq }</p>
								   	<div id="moreDiv">
										<div class="w3cbbs" style="display: none;"></div>
										<div class="container" style="display: none;"></div>
								   	</div>
									<div id="SNPEND">
									   	<h2 class="mt10">六、测序峰图结果：</h2>
									   	<div class="m-box_1 clearCss_6" id="picture_6">
										   	<p>${allPic }</p>
										   	<p class="annotation">注：峰图中的*号表示该位点发生了突变</p>
									   	</div>
									</div>
						        </c:if>
			        		</c:otherwise>
			        	</c:choose>     
				       
					   </c:when>
					</c:choose>	
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
<script type="text/javascript" src="/celloud/js/browser.js"></script>
<script type="text/javascript">
function preview(obj){
	var inputVal;
	$("body").find("input[type='text']").each(function(){
		inputVal = $(this).val();
		var cl = $(this).attr("class"); 
		if(cl){
			$(this).parent().html("突变型:<span name='print' class='"+cl+"'>"+inputVal+"</span>");
		}else{
			$(this).parent().html("<span name='print'>"+inputVal+"</span>");
		}
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
	inputVal = $("#des").children().val();
	if(inputVal){
		inputVal = inputVal.replace(/\n/g,"<br>");
		$("#des").html(inputVal);
	}
	$(".imgmiss").addClass("imgmissprint");
	window.print();
	$(".imgmiss").removeClass("imgmissprint");
	inputVal = $("#des").html();
	if(inputVal){
		inputVal = inputVal.replace(/<br>/g,"\n");
		$("#des").html("<textarea rows=\"6\">"+inputVal+"</textarea>");
	}
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
	$("div[title='帮助']").remove();
	var num = 0;
	$("#otherPng").find("img").each(function(){
		num++;
	});
	if(num>24){
		$("#moreDiv").remove();
	}else{
		$("#lessDiv").remove();
	}
	var browser = $.NV('name');
	var height;
	var appId = $("#_appId").html();
	var _flag = $("#_flag").html();
	if(appId==80){
		$("#hcvTable").find("thead").html("<tr><th>File Name<br>(文件名)</th><th>Subtype<br>(亚型)</th><th style='min-width: 90px;'>Subject Name<br>(参考序列名)</th><th style='min-width: 50px;'>Identity<br>(相似度)</th><th style='min-width: 170px;'>Overlap/total<br>(比对上的长度/比对的总长度)</th><th style='min-width: 60px;'>E_value<br>(期望值)</th><th  style='min-width: 50px;'>Score<br>(比分)</th></tr>");
		$("#hcvTable").css('width','800px')
	}
	if(appId==82&&_flag==0){
		$("button").remove();
		$(".imgtop").each(function(){
			$(this).removeClass("imgtop");
		});
		$("span[title='帮助']").each(function(){
		  $(this).remove();
		});
	}else if(_flag==1){

	}else if($("#mainDIv").height()>1000){
	}
	$(".m-boxCon").find("a").each(function(){
		$(this).parent().append($(this).html());
		$(this).remove();
	});
	$(".table").find("td").each(function(){
		$(this).css("vertical-align","middle");
		$(this).css("word-break","break-all");
		$(this).css("word-wrap","break-word");
		var isSaved = $("#isSaved").val();
		if(isSaved==1){
			var text= $(this).text();
			if(text.indexOf('突变型:')>=0){
				var cl = $(this).attr("class");
				if(cl){
					$(this).html("突变型:<input type='text' class='"+cl+" havebefore' value='"+text.replace('突变型:','')+"'/>");
				}else{
					$(this).html("突变型:<input type='text' class='havebefore' value='"+text.replace('突变型:','')+"'/>");
				}
			}
		}
	});
	$(".snpLeft").each(function(){
		$(this).css("text-align","center");
	});
	$("#picture_6").find("img").each(function(){
		$(this).attr("style","width:600px");
	})
});
var url = window.location.href.split("index")[0]+"report/";
function savePage(){
	$("body").find("input").each(function(){
		$(this).attr("value",$(this).val());
	});
	inputVal = $("#des").children().val();
	$("#des").children().html(inputVal);
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