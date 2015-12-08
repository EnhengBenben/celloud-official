<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
<!-- jquery_alert_dialogs begin -->
<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.ui.draggable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<title>PGS Report</title>
<style type="text/css">
body { 
  	font-family:"Times New Roman","微软雅黑";
  	font-size: 14px;
  	max-width:800px;
  	margin:auto;
} 

textarea{
	width: 100%;
}

td,th {
	min-width: 60px;
}

@media Print {
	.Noprn {
		display: none
	}
	body{
	    font-size:12px;
	}
}

.shotH3 {
    font-family: "Times New Roman","微软雅黑";
    width: 360px;
    font-size: 24px;
    display: inline-block;
    margin: 0 auto;
    margin-top: -10px;
    vertical-align: middle;
}
.secTitle{
	font-weight: lighter;
	line-height:1em;
}

.jzk {
	font-size: 14px;
	line-height: 20px;
	text-align: right;
}

.footer {
	margin: 0px auto 30px auto;
}

.footer span input {
	vertical-align: -1px;
}

hr {
	border: solid 2px #000;
	margin-top:5px;
}

.end {
	color: #3366ff; font-size：18px;
	text-align: center;
}

.notes {
	margin-top: 100px;
}

.print-blue {
	font-weight: normal;
	font-family: 微软雅黑;
	color: #fff;
	background-color: #3e94d1;
	border: 1px solid #0b65a4;
	display: inline-block;
	padding: 4px 12px;
	font-size: 14px;
	margin-bottom: 2px;
	line-height: 18px;
	text-align: center;
	text-shadow: 0 1px 1px rgba(0, 0, 0, 0.5);
	vertical-align: middle;
	border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
	-webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	-moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
	box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px
		rgba(0, 0, 0, 0.05);
}

.print-blue:hover {
	color: #fff;
	border: 1px solid #0b65a4;
	text-decoration: none;
	background-position: 0;
	-webkit-transition: background-position 0.1s linear;
	-moz-transition: background-position 0.1s linear;
	-ms-transition: background-position 0.1s linear;
	-o-transition: background-position 0.1s linear;
	transition: background-position 0.1s linear;
	background-color: #0b65a4;
}

#des{
	word-wrap: break-word;
	word-break: normal; 
}
.footer li{
  display: inline-block;
  padding-bottom: 5px;
  font-size: 14px;
}
.right{
	text-align: right;
/* 	width: 12%; */
}
.left{
/* 	width: 18%; */
}
.subtitle{
	font-size: 10px;
}
.smallTitle{
	font-size: 16px;
}
.smallh4{
	font-size: 12px;
	line-height:8px;
}
.w3cbbs { page-break-after:always;}
.miniTable_ tr td,.miniTable_ tr th{line-height:14px;}
</style>
<!--[if IE]>
<style>
@media Print {
	body{
	    font-size:12px;
	}
	.shotH3{
		font-size:20px;
	}
}
</style>
<![endif]-->
</head>
<body>
	<s:if test="context==null||context.equals('')">
		<div style="display: none;" id="_userId"><s:property value="userId"/></div>
		<div style="display: none;" id="_appId"><s:property value="appId"/></div>
		<div style="display: none;" id="_fileId"><s:property value="fileId"/></div>
		<div class="row Noprn" style="margin:20px auto;">
			<a href="javascript:void(0)" onclick="savePage()" class="print-blue">保存</a>
			<a href="javascript:void(0)" onclick="reset()" class="print-blue">重置</a>
			<a href="javascript:void(0)" onclick="preview(this)" class="print-blue">打印</a>
		</div>
		<s:if test="%{company.companyId==14||company.companyId==42}">
			<div class="row" style="margin:0px auto;font-size:12px;">
				<s:if test="%{company.companyIcon!=null&&!company.companyIcon.equals('')}">
					<div align="center" class="clearfix">
					  <s:if test="%{company.companyId==14}">
						<img src="<%=request.getContextPath() %>/images/hospitalIcon/<s:property value="company.companyIcon"/>" style="width:45px">
						<h2 class="shotH3 smallTitle" style="line-height: 20px;margin-top:0px">
							<s:property value="company.companyName" escape="false"/>
							<br />
							<span class="subtitle"><s:property value="company.address"/>，
							电话：<s:property value="company.tel"/></span>
						</h2>
					  </s:if>
					  <s:else>
					    <img src="<%=request.getContextPath() %>/images/hospitalIcon/<s:property value="company.companyIcon"/>" style="width:60px">
						<h2 class="shotH3 smallTitle" style="line-height: 20px;margin-top:0px">
							<s:property value="company.companyName" escape="false"/>
							<br />染色体拷贝数变异检测报告
							<br />
							<span class="subtitle"><s:property value="company.address"/>，
							电话：<s:property value="company.tel"/></span>
						</h2>
					  </s:else>
					</div>
					<hr style="margin:0px">
				</s:if>
				<div>
				  <s:if test="%{company.companyId==14}">
					<table class="table table-bordered table-condensed miniTable_" style="margin:0px">
						<tr>
							<th>姓名</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
							<th>门诊号</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
							<th>检验编号</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
						</tr>
						<tr>
							<th>性别</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
							<th>送检医师</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
							<th>采样日期</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
						</tr>
						<tr>
							<th>年龄</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
							<th>送检材料</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
							<th>接收日期</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
						</tr>
						<tr>
							<th>孕周</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
							<th>临床诊断</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;width:100%"/></span></td>
							<th></th>
							<td></td>
						</tr>
					 </table>
				  </s:if>
				  <s:else>
				    <table class="table table-bordered table-condensed miniTable_" style="margin:10px 0px">
					  <tr>
							<th>姓名</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></td>
							<th>性别</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></td>
							<th>年龄</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></td>
						</tr>
						<tr>
							<th>科别</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></td>
							<th>病历号</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></td>
							<th>送检材料</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></td>
						</tr>
						<tr>
							<th>采样日期</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></td>
							<th>送检医师</th>
							<td><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></td>
							<th></th>
							<td></td>
						</tr>
					  </s:else>
					</table>
				</div>
				<div>
					<h4 class="smallh4">检测方法:<span class="subtitle"> NGS </span></h4>
					<h4 class="smallh4">检测项目:<span class="subtitle"> 23对染色体拷贝数变异 </span></h4>
				</div>
				<div>
					<h4 class="smallh4">检测结果:</h4>
					<s:if test="%{miniPng!='null'}">
						<img src='<s:property value="outPath"/>/<s:property value="pagePath"/>/<s:property value="miniPng"/>' style="width:90%;max-height:245px;">
					</s:if>
					<s:if test="%{splitPng!='null'}">
						<img src='<s:property value="outPath"/>/<s:property value="pagePath"/>/<s:property value="splitPng"/>' style="width:90%;max-height:245px;">
					</s:if>
				</div>
				<br/>
				<s:if test="%{company.companyId==42}">
					<h4 class="smallh4">结果解释：</h4>
					<div id="des1">
						报告名称：<span><input type="text" value="${data.fileName }(${data.anotherName })" class="onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;width:400px"/></span><br>
						样本报告：
					    <div id="des" style="margin:0px">
							<textarea rows="3" style="margin:0px;font-size:10px;line-height:10px"><s:property value="txt.replace('@','+')"/></textarea>
						</div>
					</div>
				</s:if>
				<s:else>
				    <h4 class="smallh4">结果提示：</h4>
					<div id="des1">
						<textarea rows="3" style="margin:0px;font-size:10px;line-height:10px"></textarea>
					</div>
					<h4 class="smallh4">备注：</h4>
					<div id="des2">
						<textarea rows="3" style="margin:0px;font-size:10px;line-height:10px"></textarea>
					</div>
					<br/>
					<hr style="margin:0px">
					<div id="page2">
						<h4 class="smallh4">局限性：</h4>
						<div id="des3">
	<textarea rows="8" style="margin:0px;font-size:10px;line-height:12px">
	1. 该检测对于染色体拷贝数变异（CNVs）&lt; 4Mb 的微小畸变导致的染色体异常，可能不能检出。
	2. 本检验无法检查到由以下因素引起的染色体变异：单亲二倍体（UPD）；染色体平衡易位、倒位、环状等结构异常；多倍体如：69，XXX、92，XXXX、92，XXYY。
	3. 本检验无法检查到由以下因素引起的疾病：单基因病、多基因病；感染、药物、辐射等环境诱因。
	4. 染色体疾病中极少数是由嵌合体，即由两种或两种以上的细胞系引起的。因正常与异常细胞系比例不定，本技术对于低比例嵌合体检测存在难度。
	</textarea>
						</div>
					</div>
				</s:else>
			</div>
			<s:if test="%{company.companyId==14}">
				<ul class="footer" style="margin-bottom: 0px;font-size:12px;/* margin-top: 450px; */">
					<li class="left" style="font-size:12px;">报告者：<span><input type="text" class="input-mini" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;"/></span></li>
					<li class="left" style="font-size:12px;">审核者：<span><input type="text" class="input-mini" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;"/></span></li>
					<li class="right pull-right opinion" style="font-size:12px;">报告日期：<span id="jzkp"><input type="text" class="input-mini" id="jzkh" style="height:10px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;"/></span></li>
				</ul>
				<hr style="margin:0px">
				<h4 class="smallh4" style="font-size:12px;">临床意见：<span class="opinion"><span><input type="text" class="input-mini onlybotton" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px;border:0px;"/></span></span></h4>
			</s:if>
			<s:else>
				<ul class="footer" style="margin-bottom: 0px;font-size:12px;margin-top:150px;" id="p2footer">
					<li class="left" style="font-size:12px;">报告者：<span><input type="text" class="input-mini" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></li>
					<li class="left" style="font-size:12px;">审核者：<span><input type="text" class="input-mini" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></li>
					<li class="right pull-right opinion" style="font-size:12px;">报告日期：<span id="jzkp"><input type="text" class="input-mini" id="jzkh" style="height:12px;padding:0px;margin:0px;font-size:10px;line-height:12px"/></span></li>
				</ul>
			</s:else>
		</s:if>
		<s:else>
			<s:if test="%{company.companyId!=10}">
				<div class="jzk" style="margin:0px auto;">就诊卡号：<span id="jzkp"><input type="text" class="input-mini" id="jzkh"/></span></div>
			</s:if>
			<div class="row" style="margin:0px auto;" id="mainDiv">
				<s:if test="%{company.companyIcon!=null&&!company.companyIcon.equals('')}">
					<div align="center" class="clearfix">
						<img src="<%=request.getContextPath() %>/images/hospitalIcon/<s:property value="company.companyIcon"/>" style="width:90px">
						<h2 class="shotH3">
							<s:property value="company.companyName" escape="false"/>
							<br />
							<span class="secTitle"><s:property value="company.englishName" escape="false"/></span>
						</h2>
						<s:if test="%{company.companyId!=12&&company.companyId!=10&&company.companyId!=22}">
							<img id="_imgShow" src="<%=request.getContextPath() %>/images/deptIcon/<s:property value="dept.deptIcon"/>" style="width:95px">
						</s:if>
					</div>
					<hr>
				</s:if>
				<div align="center">
					<h3>
						<s:if test="%{company.companyId==10}">流产组织</s:if>染色体拷贝数变异检测报告
					</h3>
				</div>
				<div>
					<h4>基本信息：</h4>
					<table class="table table-bordered table-condensed">
						<s:if test="%{company.companyId!=9}">
							<tr>
								<th><s:if test="%{company.companyId==10}">实验室</s:if>编号</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>姓名</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>性别</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>年龄</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
							</tr>
							<tr>
								<th>样本类型</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>申请日期</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>接收日期</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>样本状态</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
							</tr>
						</s:if>
						<s:if test="%{company.companyId==9}">
							<tr>
								<th>胚胎编号</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>女方姓名</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>年龄</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>取卵日期</th>
								<td><span><input type="text" class="input-small onlybotton" /></span></td>
							</tr>
						</s:if>
						<s:if test="%{company.companyId==10}">
							<tr>
								<th>临床诊断</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>送检医生</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th>送检科室</th>
								<td><span><input type="text" class="input-mini onlybotton" /></span></td>
								<th></th>
								<td></td>
							</tr>
						</s:if>
					</table>
				</div>
				<div>
					<h4>检测方法:</h4>
					<div>
						NGS
					</div>
				</div>
				<div>
					<h4>检测结果:</h4>
					<img src='<s:property value="outPath"/>/<s:property value="pagePath"/>/<s:property value="miniPng"/>' width="100%">
				</div>
				<s:if test="%{company.companyId==10}">
					<br/>
					<div id="des">
						<textarea rows="3"><s:property value="txt.replace('@','+')"/></textarea>
					</div>
					<h4>结果解释：</h4>
					<div id="des2">
						<textarea rows="3"></textarea>
					</div>
				</s:if>
				<s:else>
					<h4>结果解释：</h4>
					<div id="des">
						<textarea rows="3"><s:property value="txt.replace('@','+')"/></textarea>
					</div>
				</s:else>
				<div class="notes">注：该检测对于&lt;
				<s:if test="%{appId==92||appId==93}">
				15Mb
				</s:if>
				<s:elseif test="%{appId==85}">
				12Mb
				</s:elseif>
				<s:elseif test="%{appId==87||appId==88||appId==94}">
				4Mb
				</s:elseif>
				<s:elseif test="%{appId==86||appId==91||appId==104}">
				1Mb
				</s:elseif>
				的微小畸变可能不能检出。</div>
			</div>
			<ul class="footer">
				<li class="left">检测人：<span><input type="text" class="input-mini" /></span></li>
				<s:if test="%{company.companyId!=10}">
					<li class="left">复核人：<span><input type="text" class="input-mini" /></span></li>
				</s:if>
				<s:if test="%{company.companyId!=9}">
					<li class="left">审核人：<span><input type="text" class="input-mini" /></span></li>
				</s:if>
				<li class="right pull-right">日期：<span><input type="text" class="input-small" /></span></li>
			</ul>
			<s:if test="%{company.companyIcon!=null&&!company.companyIcon.equals('')&&company.companyId!=12&&company.companyId!=10&&company.companyId!=22}">
				<div style="margin:0px auto;">
					<hr>
					<div class="end">
						地址：<s:property value="company.address"/>
						邮编：<s:property value="company.zipCode"/>
						电话：<s:property value="company.tel"/>
						<br/><s:property value="company.addressEn"/>
					</div>
				</div>
			</s:if>
		</s:else>
	</s:if>
	<s:else>
		<s:property value="context" escape="false"/>
	</s:else>
</body>
<script type="text/javascript">
	if($(".end").html()){
		$("#mainDiv").css("min-height",(960-64-38-38-20)+"px");
	}else{
		$("#mainDiv").css("min-height",(980-38-38-20)+"px");
	}
	function preview(obj){
		var inputVal;
		inputVal = $("#jzkh").val();
		if(!inputVal){
			$("#jzkp").html("<span name='print' style='padding-right:100px;'>"+inputVal+"</span>");
		}
		$("#mainDiv").find("input").each(function(){
			inputVal = $(this).val();
			$(this).parent().html("<span name='print'>"+inputVal+"</span>");
		});
		$(".opinion").find("input").each(function(){
			inputVal = $(this).val();
			$(this).parent().html("<span name='print'>"+inputVal+"</span>");
		});
		inputVal = $("#des").children().val().replace(/\n/g,"<br>");
		$("#des").html(inputVal);
		var des1 = $("#des1").children().val();
		if (des1){
			inputVal = des1.replace(/\n/g,"<br>");
			$("#des1").html(inputVal);
		}
		var des2 = $("#des2").children().val();
		if (des2){
			inputVal = des2.replace(/\n/g,"<br>");
			$("#des2").html(inputVal);
		}
		var des3 = $("#des3").children().val();
		if (des3){
			inputVal = des3.replace(/\n/g,"<br>");
			$("#des3").html(inputVal);
		}
		$("#p2footer").css("margin-top",(300-$("#page2").height())+"px");
		window.print();
		$("body").find("span[name='print']").each(function(){
			inputVal = $(this).html();
			if($(this).parent().attr("id")=="jzkp"){
				$(this).parent().html("<input type=\"text\"  class=\"input-mini\" value=\""+inputVal+"\" id=\"jzkh\" />");
			}else{
				$(this).parent().html("<input type=\"text\" class=\"input-mini\" value=\""+inputVal+"\" />");
			}
		});
		inputVal = $("#des").html().replace(/<br>/g,"\n");
		$("#des").html("<textarea rows=\"3\">"+inputVal+"</textarea>");
		if (des1){
			inputVal = $("#des1").html().replace(/<br>/g,"\n");
			$("#des1").html("<textarea rows=\"3\">"+inputVal+"</textarea>");
		}
		if (des2){
			inputVal = $("#des2").html().replace(/<br>/g,"\n");
			$("#des2").html("<textarea rows=\"3\">"+inputVal+"</textarea>");
		}
		if (des3){
			inputVal = $("#des3").html().replace(/<br>/g,"\n");
			$("#des3").html("<textarea rows=\"8\">"+inputVal+"</textarea>");
		}
	}
	function savePage(){
		$("body").find("input").each(function(){
			$(this).attr("value",$(this).val());
		});
		$("#des").children().html($("#des").children().val());
		$("#des1").children().html($("#des1").children().val());
		$("#des2").children().html($("#des2").children().val());
		var url = window.location.href.split("printPGS")[0];
		$.post(url+"updateContext",{"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"flag":0,"context":$("body").html()},function(result){
			if(result==1){
				jAlert("信息保存成功！");
			}else{
				jAlert("信息保存失败！");
			}
		});
	}
	function reset(){
		if(confirm("确定要重置之前保存的报告吗？")){
			var url = window.location.href.split("printPGS")[0];
			$.post(url+"updateContext",{"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"flag":0,"context":""},function(result){
				if(result==1){
					location=location ;
				}else{
					jAlert("信息重置失败！");
				}
			});
		}
	}
</script>
</html>