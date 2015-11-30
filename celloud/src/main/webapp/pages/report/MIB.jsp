<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="row">
	<div class="m-file">
		数据编号：<span class="file-name">${ mib.dataKey}</span><br>
		文件名称：
		<span class="file-name">
		<c:forEach items="${mib.data}" var="data">
			${data.fileName}(${data.dataKey})&nbsp;&nbsp;&nbsp;
		</c:forEach>
		</span>
		<div class="toolbar">
			<a href="javascript:printMIB(${mib.projectId },${mib.dataKey },${mib.userId },${mib.appId })" class="btn btn-default"><i class="i-print"></i>打印报告</a>
		</div>
	</div>
	<div>
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-report1"></i>数据统计</h2>
			<div class="m-boxCon">
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
		</div>
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-edit"></i>Reads Distribution</h2>
			<div class="m-boxCon">
				<table style="width:90%;">
			      <tr>
			    	<td style="width:50%;"><img src="${mib.readsDistribution }" style="max-width:500px;"></td>
			    	<td><img src="${mib.familyDistribution }" style="max-width:500px;"></td>
			      </tr>
			    </table>
			</div>
		</div>
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-edit"></i>Genus Distribution</h2>
			<div class="m-boxCon">
				<img src="${mib.genusDistribution }" style="width:90%;">
			</div>
		</div>
		<!--检测结果-->
		<div class="m-box">
			<h2><i class="i-edit"></i>报告</h2>
			<div class="m-boxCon">
			  <table class="table table-striped-green table-text-center table-padding0" id="snp_table1">
			     <thead>
			       <tr>
			         <th>Species</th>
			         <th>Genus</th>
			         <th>GI</th>
			         <th>%Coverage</th>
			         <th>Reads_hit</th>
			         <th>Reads_num</th>
			         <th>Average depth of coverage</th>
			       </tr>
			     </thead>
			     <tbody>
			       <c:if test="${fn:length(mib.summaryTable)>0}">
			         <c:forEach items="${mib.summaryTable }" var="summary" varStatus="s">
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
				   </c:if>
			     </tbody>
			  </table>
			</div>
		</div>
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-edit"></i>报告</h2>
			<div class="m-boxCon">
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
		</div>
		<!--检测结果-->
		<div class="m-box">
			<h2><i class="i-edit"></i>序列质量分析（见QC结果）</h2>
			<div class="m-boxCon" id="_report" style="display: inline-block;width: 90%">
				<div class="h2">Basic Statistics</div>
				<table class="table table-bordered table-condensed">
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
				<table class="table table-bordered table-condensed">
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
				<table style="width:90%;">
			      <tr>
			    	<td style="width:50%;"><img src="${mib.qualityPath1 }" style="max-width:500px;"></td>
			    	<td><img src="${mib.qualityPath2 }" style="max-width:500px;"></td>
			      </tr>
			      <tr>
			    	<td><img alt="" src="${mib.seqContentPath1 }" style="max-width:500px;"></td>
			    	<td><img alt="" src="${mib.seqContentPath2 }" style="max-width:500px;"></td>
			      </tr>
			    </table>
			</div>
		</div>
	</div>
</div>
<script>
$(function() {
	$(window).manhuatoTop({
		showHeight : 100,
		speed : 1000
	});
});
</script>