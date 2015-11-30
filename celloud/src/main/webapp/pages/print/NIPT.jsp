<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/bootstrap/css/bootstrap-responsive.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!-- jquery_alert_dialogs begin -->
<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.ui.draggable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/plugins/jquery_alert_dialogs/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
<title>PGS Report</title>
<style type="text/css">
body {
	font-size: 16px;
}

textarea{
	width: 800px;
}

td,th {
	min-width: 60px;
}

@media Print {
	.Noprn {
		display: none
	}
}

.shotH3 {
	font-family:SimSun, "宋体";
	width: 530px;
	float: left;
}

.jzk {
	font-size: 18px;
	line-height: 20px;
	text-align: right;
}

.footer {
	margin: 0px auto;
	width: 800px;
	text-align: right;
	margin-top: 350px;
}

.footer .length {
	margin-left: 60px;
}

.footer .mid {
	margin-left: 60px;
	margin-right: 10px;
}

.footer .shot {
	margin-left: 10px;
	margin-right: 10px;
}

.footer span input {
	vertical-align: -1px;
}

hr {
	border: solid 2px #000;
}

.end {
	color: #3366ff; font-size：18px;
	text-align: center;
}

.Notes {
	margin-top: 30px;
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
</style>
<script type="text/javascript">
	function preview(obj){
		var inputVal;
		$("body").find("input").each(function(){
			inputVal = $(this).val();
			$(this).parent().html("<span name='print'>"+inputVal+"</span>");
		});
		window.print();
		$("body").find("span[name='print']").each(function(){
			inputVal = $(this).html();
			$(this).parent().html("<input type=\"text\" class=\"input-mini\" value=\""+inputVal+"\" />");
		});
		$(".footer").css("margin-top","350px");
	}
	function savePage(){
		$("body").find("input").each(function(){
			$(this).attr("value",$(this).val());
		});
		var url = window.location.href.split("printNIPT")[0];
		$.post(url+"updateContext",{"userId":$("#_userId").html(),"appId":$("#_appId").html(),"fileId":$("#_fileId").html(),"flag":0,"context":$("body").html()},function(result){
			if(result==1){
				jAlert("信息保存成功！");
			}else{
				jAlert("信息保存失败！");
			}
		});
	}
</script>
</head>
<body>
	<s:if test="context==null">
		<div style="display: none;" id="_userId"><s:property value="userId"/></div>
		<div style="display: none;" id="_appId"><s:property value="appId"/></div>
		<div style="display: none;" id="_fileId"><s:property value="fileId"/></div>
		<div class="row Noprn" style="margin:20px auto;width: 800px;">
			<a href="javascript:void(0)" onclick="savePage()" class="print-blue">保存</a>
			<a href="javascript:void(0)" onclick="preview(this)" class="print-blue">打印</a>
		</div>
		<div class="jzk" style="margin:0px auto;width: 800px;">就诊卡号：<span><input type="text" class="input-mini" /></span></div>
		<div class="row" style="margin:0px auto;width: 800px;">
			<div align="center">
				<h3>胎儿染色体非整倍体（T21、T18、T13）检测报告</h3>
			</div>
			<div>
				<h4>受检者信息</h4>
				<table class="table table-bordered table-condensed">
					<tr>
						<th>姓名</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
						<th>年龄</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
						<th>孕周</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
						<th>末次月经时间</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
					</tr>
					<tr>
						<th>样本编号</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
						<th>样本状态</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
						<th>样本接受日期</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
						<th>采样时间</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
					</tr>
					<tr>
						<th>送检单位</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
						<th>报告日期</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
						<th></th>
						<td></td>
						<th></th>
						<td></td>
					</tr>
				</table>
			</div>
			<div>
				<h4>检测信息</h4>
				<table class="table table-bordered table-condensed">
					<tr>
						<th>检测项目</th>
						<td>21号染色体三体  18号染色体三体  13号染色体三体</td>
					</tr>
					<tr>
						<th>检测方法</th>
						<td>DNA测序</td>
					</tr>
					<tr>
						<th>检测状态</th>
						<td><span><input type="text" class="input-mini onlybotton" /></span></td>
					</tr>
				</table>
			</div>
			<h4>检测结果：</h4>
			<div id="des">
				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th>Chr</th>
							<th>Zscore</th>
							<th>Normal_value</th>
							<th>Result</th>
						</tr>
					</thead>
					<tbody>
						<s:generator separator="@" val="txt" id="val">
							<s:iterator id="val" status="st">
								<s:if test="#st.count%4==1">
									<tr>
								</s:if>
								<td><s:property value="val"/></td>
								<s:if test="#st.count%4==0">
									</tr>
								</s:if>
							</s:iterator>
						</s:generator>
					</tbody>
				</table>
			</div>
			<div>
				<img src='<s:property value="outPath"/>/<s:property value="pagePath"/>/Result/<s:property value="miniPng"/>' width="100%">
			</div>
			<div class="footer">
				<span class="length">检测人：<span><input type="text" class="input-mini" /></span></span>
				<span class="length">复核人：<span><input type="text" class="input-mini" /></span></span>
				<span class="mid"><span><input type="text" class="input-mini" /></span>年</span>
				<span class="shot"><span><input type="text" class="input-mini" /></span>月</span>
				<span class="shot"><span><input type="text" class="input-mini" /></span>日</span>
			</div>
			<div style="padding-bottom: 50px;">
			
			</div>
		</div>
	</s:if>
	<s:else>
		<s:property value="context" escape="false"/>
	</s:else>
</body>
</html>