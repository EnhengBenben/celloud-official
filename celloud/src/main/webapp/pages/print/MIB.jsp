<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印MIB报告</title>
<link href="<%=request.getContextPath() %>/css/print_gdd.css?version=1.0" rel="stylesheet">
</head>
<body>
<a href="javascript:void(0)" onclick="preview(this)" class="btn btn-default" id="change" style="float:right;margin-top:10px;margin-right:-80px;"><i class="i-print"></i>打印</a>
<section class="section2 border1 w3cbbs">
    <div class="header">
		<h1 style="font-size:32px;padding:0 0 5 0px;">MIB检测分析报告</h1>
	</div>
    <h4>1.&nbsp;&nbsp; 检测文件（${mib.dataKey}）</h4>
    <div class="info">
		<c:forEach items="${mib.data}" var="data">
			${data.fileName}&nbsp;&nbsp;
		</c:forEach>
	</div>
	<h4>2.&nbsp;&nbsp;数据统计</h4>
    <div class="info">
    	<table class="table table-bordered table-condensed">
			<thead>
				<tr><th>序列总数</th><th>平均质量</th><th>平均GC含量</th></tr>
		    </thead>
			<tbody>
				<tr>
					<td>${mib.totalReads }</td>
					<td>${mib.avgQuality }</td>
					<td>${mib.avgGCContent }</td>
				</tr>
			</tbody>
		</table>
    </div>
    <h4>3.&nbsp;&nbsp;Reads Distribution</h4>
    <div class="info">
        <table style="width:90%;">
	      <tr>
	    	<td style="width:49%;"><img src="${mib.readsDistribution }" style="width:100%;"></td>
	    	<td><img src="${mib.familyDistribution }" style="width:100%;"></td>
	      </tr>
	    </table>
    </div>
    <h4>4.&nbsp;&nbsp;Genus Distribution</h4>
    <div class="info">
        <img src="${mib.genusDistribution }" style="width:90%;">
    </div>
    <h4>5.&nbsp;&nbsp;报告</h4>
    <div class="info">
    	<table class="table table-bordered table-condensed">
			<thead>
		       <tr>
		         <th>Species</th>
		         <th style="min-width:88px">Genus</th>
		         <th style="min-width:54px">GI</th>
		         <th style="min-width:60px">%Coverage</th>
		         <th style="min-width:56px">Reads_hit</th>
		         <th style="min-width:66px">Reads_num</th>
		         <th style="min-width:59px">depths</th>
		       </tr>
		     </thead>
		     <tbody>
		       <c:choose>
		       	 <c:when test="${fn:length(mib.summaryTable)>1}">
		       	   	<c:forEach items="${mib.summaryTable }" var="summary" varStatus="s" begin="1">
					   <tr>
					     <td>${summary.Species }</td>
					     <td>${summary.Genus }</td>
					     <td>${summary.GI }</td>
					     <td>${summary.Coverage }</td>
					     <td>${summary.Reads_hit }</td>
					     <td>${summary.Reads_num }</td>
					     <td>${summary.avgCoverage }</td>
					   </tr>
			        </c:forEach>
		       	 </c:when>
		       	 <c:otherwise>
		       	 	<tr><td colspan="7">无结果</td></tr>
		       	 </c:otherwise>
		       </c:choose>
		     </tbody>
		</table>
    </div>
    <h4>6.&nbsp;&nbsp;图</h4>
    <div class="info">
      <c:if test="${mib.pngPath.top1png!=null }">
		<img src="${mib.pngPath.top1png }" style="width:90%;">
	  </c:if>
	  <c:if test="${mib.pngPath.top2png!=null }">
		<img src="${mib.pngPath.top2png }" style="width:90%;">
	  </c:if>
	  <c:if test="${mib.pngPath.top3png!=null }">
		<img src="${mib.pngPath.top3png }" style="width:90%;">
	  </c:if>
	  <c:if test="${mib.pngPath.top4png!=null }">
		<img src="${mib.pngPath.top4png }" style="width:90%;">
	  </c:if>
	  <c:if test="${mib.pngPath.top5png!=null }">
		<img src="${mib.pngPath.top5png }" style="width:90%;">
	  </c:if>
	  <c:if test="${mib.pngPath.top6png!=null }">
		<img src="${mib.pngPath.top6png }" style="width:90%;">
	  </c:if>
	  <c:if test="${mib.pngPath.top7png!=null }">
		<img src="${mib.pngPath.top7png }" style="width:90%;">
	  </c:if>
	  <c:if test="${mib.pngPath.top8png!=null }">
		<img src="${mib.pngPath.top8png }" style="width:90%;">
	  </c:if>
	  <c:if test="${mib.pngPath.top9png!=null }">
		<img src="${mib.pngPath.top9png }" style="width:90%;">
	  </c:if>
	  <c:if test="${mib.pngPath.top10png!=null }">
		<img src="${mib.pngPath.top10png }" style="width:90%;">
	  </c:if>
    </div>
</section>
<section class="section8 border1 w3cbbs">
	<h3>序列质量分析（见QC结果）</h3>
	<div class="h2">Basic Statistics</div>
	<table class="table table-green table-striped-blue table-text-center">
		<thead>
			<tr>
				<th>#Measure</th>
				<th>Value</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Filename</td>
				<td>${mib.basicStatistics1.Filename }</td>
			</tr>
			<tr>
				<td>File type</td>
				<td>${mib.basicStatistics1.FileType }</td>
			</tr>
			<tr>
				<td>Encoding</td>
				<td>${mib.basicStatistics1.Encoding }</td>
			</tr>
			<tr>
				<td>Total Sequences</td>
				<td>${mib.basicStatistics1.TotalSeq }</td>
			</tr>
			<tr>
				<td>Filtered Sequences</td>
				<td>${mib.basicStatistics1.FilteredSeq }</td>
			</tr>
			<tr>
				<td>Sequence length</td>
				<td>${mib.basicStatistics1.SeqLength }</td>
			</tr>
			<tr>
				<td>%GC</td>
				<td>${mib.basicStatistics1.gc }</td>
			</tr>
		</tbody>
	</table>
	<div class="h2">Basic Statistics</div>
	<table class="table table-green table-striped-orange table-text-center">
		<thead>
			<tr>
				<th>#Measure</th>
				<th>Value</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>Filename</td>
				<td>${mib.basicStatistics2.Filename }</td>
			</tr>
			<tr>
				<td>File type</td>
				<td>${mib.basicStatistics2.FileType }</td>
			</tr>
			<tr>
				<td>Encoding</td>
				<td>${mib.basicStatistics2.Encoding }</td>
			</tr>
			<tr>
				<td>Total Sequences</td>
				<td>${mib.basicStatistics2.TotalSeq }</td>
			</tr>
			<tr>
				<td>Filtered Sequences</td>
				<td>${mib.basicStatistics2.FilteredSeq }</td>
			</tr>
			<tr>
				<td>Sequence length</td>
				<td>${mib.basicStatistics2.SeqLength }</td>
			</tr>
			<tr>
				<td>%GC</td>
				<td>${mib.basicStatistics2.gc }</td>
			</tr>
		</tbody>
	</table>
	<table style="width:100%;">
     <tr>
    	<td style="width:50%;"><img src="${mib.qualityPath1 }" style="width:100%;"></td>
    	<td><img src="${mib.qualityPath2 }" style="width:100%;"></td>
      </tr>
      <tr>
    	<td><img alt="" src="${mib.seqContentPath1 }" style="width:100%;"></td>
    	<td><img alt="" src="${mib.seqContentPath2 }" style="width:100%;"></td>
      </tr>
    </table>
</section>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript">
function preview(obj){
	var inputVal;
	var textareaVal;
	var classname;
	var cmpDrug = "";
	$("body").find("section").each(function(){
		$(this).removeClass("border1");
	});
	window.print();
	$("#change").show();
	$("body").find("section").each(function(){
		$(this).addClass("border1");
	});
}
</script>
</body>
</html>