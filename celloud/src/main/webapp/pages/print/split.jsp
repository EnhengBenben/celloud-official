<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印split报告</title>
<link href="<%=request.getContextPath() %>/css/print_gdd.css?version=1.0" rel="stylesheet">
</head>
<body>
<a href="javascript:void(0)" onclick="preview(this)" class="btn btn-default" id="change" style="float:right;margin-top:10px;margin-right:-80px;"><i class="i-print"></i>打印</a>
<section class="section2 border1 w3cbbs">
    <div class="header">
		<h1 style="font-size:32px;padding:0 0 5 0px;">split检测分析报告</h1>
	</div>
	<h4>1.&nbsp;&nbsp; 基本信息</h3>
	<div class="section1">
    <ul style="margin-bottom: 10px;">
    	<li>编号：<span><input type="text" name="" value=""></span></li>
        <li>样本类型：<span><input type="text" name="" value=""></span></li>
    	<li>姓名：<span><input type="text" name="" value=""></span></li>
        <li>申请日期：<span><input type="text" name=""></span></li>
        <li>性别： <span id="_sex"><input type="radio" name="sex" value="男">男<input type="radio" name="sex" value="女" >女</span></li>
        <li>接收日期：<span><input type="text" name=""></span></li>
        <li>年龄：<span><input type="text" id="patientAge" name="" value=""></span>岁</li>
        <li>样本状态：<span><input type="text" name=""></span></li>
    </ul>
    </div>
    <h4>2.&nbsp;&nbsp; 检测文件（${split.dataKey}）</h4>
    <div class="info">
		<c:forEach items="${split.data}" var="data">
			${data.fileName}&nbsp;&nbsp;
		</c:forEach>
	</div>
	<h4>3.&nbsp;&nbsp;源数据统计</h4>
    <div class="info">
    	<table class="table table-bordered table-condensed">
			<thead>
				<tr><th>平均质量</th><th>平均GC含量</th><th>序列总数</th><th>有效序列</th><th>未知序列</th></tr>
		    </thead>
			<tbody>
				<tr>
					<td>${split.avgQuality }</td>
					<td>${split.avgGCContent }</td>
					<td>${split.totalReads }</td>
					<td>${split.usefulReads }</td>
					<td>${split.unknownReads }</td>
				</tr>
			</tbody>
		</table>
    </div>
    <h4>4.&nbsp;&nbsp;结果样本统计</h4>
    <div class="info">
    	<table class="table table-bordered table-condensed">
			<tbody>
			  <tr>
			    <td style="width:200px">样本数量</td>
			    <td>${split.sampleNum }</td>
			  </tr>
			  <tr>
			    <td>&lt;5000条序列的样本数量</td>
			    <td>${split.less5000 }</td>
			  </tr>
			  <tr>
			    <td>&gt;20000条序列的样本数量</td>
			    <td>${split.more2000 }</td>
			 </tr>
			  <tr>
			    <td>样本序列数平均值</td>
			    <td>${split.avgSampleSeq }</td>
			  </tr>
			  <tr>
			    <td>样本序列数最小值</td>
			    <td>${split.minSampleSeq }</td>
			  </tr>
			  <tr>
			    <td>样本序列数最大值</td>
			    <td>${split.maxSampleSeq }</td>
			  </tr>
			  <tr>
			    <td>方差</td>
			    <td>${split.variance }</td>
			  </tr>
			  <tr>
			    <td>标准差</td>
			    <td>${split.stdev }</td>
			  </tr>
			</tbody>
		</table>
    </div>
    <h4>5.&nbsp;&nbsp;结果样本详细</h4>
    <div class="info">
      <table class="table table-bordered table-condensed">
		<thead>
		  <tr>
			<th>数据名称</th>	
			<th>序列数量</th>
			<th>平均质量</th>
			<th>平均GC含量</th>
		  </tr>	
		</thead>
		<tbody>
		  <c:choose>
		  	<c:when test="${split.resultList==null}"><tr><td colspan="4">未分析出结果</td></tr></c:when>
		  	<c:otherwise>
			  <c:forEach items="${split.resultList}" var="data">
				  <c:if test="${!(data.name=='total' ||data.name=='useful'||data.name=='unknown')}">
					<tr>
					  <td>${data.name }</td>
					  <td>${data.number }</td>
					  <td>${data.avgQuality }</td>
					  <td>${data.avgGCContent }</td>
					</tr>
				  </c:if>
			  </c:forEach>
		  	</c:otherwise>
		  </c:choose>
		</tbody>
	  </table>
    </div>
</section>
<section class="section8 border1 w3cbbs">
	<h3>序列质量分析（见QC结果）</h3>
	<div class="h2">Basic Statistics</div>
	<table class="table table-green table-striped-blue table-text-center">
		<thead>
			<tr>
				<th>#Measure</th>
				<th colspan="2">Value</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Filename</td>
				<td>${split.basicStatistics1.Filename }</td>
				<td>${split.basicStatistics2.Filename }</td>
			</tr>
			<tr>
				<td>File type</td>
				<td>${split.basicStatistics1.FileType }</td>
				<td>${split.basicStatistics2.FileType }</td>
			</tr>
			<tr>
				<td>Encoding</td>
				<td>${split.basicStatistics1.Encoding }</td>
				<td>${split.basicStatistics2.Encoding }</td>
			</tr>
			<tr>
				<td>Total Sequences</td>
				<td>${split.basicStatistics1.TotalSeq }</td>
				<td>${split.basicStatistics2.TotalSeq }</td>
			</tr>
			<tr>
				<td>Filtered Sequences</td>
				<td>${split.basicStatistics1.FilteredSeq }</td>
				<td>${split.basicStatistics2.FilteredSeq }</td>
			</tr>
			<tr>
				<td>Sequence length</td>
				<td>${split.basicStatistics1.SeqLength }</td>
				<td>${split.basicStatistics2.SeqLength }</td>
			</tr>
			<tr>
				<td>%GC</td>
				<td>${split.basicStatistics1.gc }</td>
				<td>${split.basicStatistics2.gc }</td>
			</tr>
		</tbody>
	</table>
	<table style="width:100%;">
      <tr>
    	<td style="width:50%;"><img style="width: 100%;" src="<c:if test="${!split.qualityPath1.contains('Tools') }">${outPath }/${split.userId }/${split.appId }/${split.dataKey }</c:if>${split.qualityPath1 }"></td>
    	<td><img style="width: 100%;" src="<c:if test="${!split.qualityPath2.contains('Tools') }">${outPath }/${split.userId }/${split.appId }/${split.dataKey }/</c:if>${split.qualityPath2 }"></td>
      </tr>
      <tr>
    	<td><img style="width: 100%;" alt="" src="<c:if test="${!split.seqContentPath1.contains('Tools') }">${outPath }/${split.userId }/${split.appId }/${split.dataKey }</c:if>${split.seqContentPath1 }"></td>
    	<td><img style="width: 100%;" alt="" src="<c:if test="${!split.seqContentPath2.contains('Tools') }">${outPath }/${split.userId }/${split.appId }/${split.dataKey }</c:if>${split.seqContentPath2 }"></td>
      </tr>
    </table>
</section>
<script language="javascript" src="<%=request.getContextPath()%>/plugins/jQuery/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
function preview(obj){
	var inputVal;
	var textareaVal;
	var classname;
	var cmpDrug = "";
	$("body").find("section").each(function(){
		$(this).removeClass("border1");
	});
	$("body").find("input[type='text']").each(function(){
		inputVal = $(this).val();
		classname = $(this).attr("class");
		$(this).parent().html("<input type='hidden' value='"+classname+"'><span name='print'>"+inputVal+"</span>");
	});
	var sex = $("input[type='radio']:checked").val();
	$("#_sex").html(sex);
	$("#change").hide();
	window.print();
	$("#change").show();
	$("body").find("section").each(function(){
		$(this).addClass("border1");
	});
	$("body").find("span[name='print']").each(function(){
		inputVal = $(this).html();
		classname = $(this).prev().val();
		$(this).parent().html("<input type='text' class='"+classname+"' value='"+inputVal+"'>");
	});
	$("#_sex").html("<input type='radio' name='sex' value='男'>男<input type='radio' name='sex' value='女'>女");
	$("input[type='radio'][value="+sex+"]").prop("checked",true); 
}
</script>
</body>
</html>