<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
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
			    	<td style="width:49%;"><img src="<c:if test="${!mib.readsDistribution.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.readsDistribution }" style="width:100%;"></td>
			    	<td><img src="<c:if test="${!mib.familyDistribution.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.familyDistribution }" style="width:100%;"></td>
			      </tr>
			    </table>
			</div>
		</div>
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-edit"></i>Genus Distribution</h2>
			<div class="m-boxCon">
				<img src="<c:if test="${!mib.genusDistribution.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.genusDistribution }" style="width:90%;">
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
			         <th style="min-width:110px;">Genus</th>
			         <th style="min-width:75px;">GI</th>
			         <th style="min-width:76px;">%Coverage</th>
			         <th style="min-width:65px;">Reads_hit</th>
			         <th style="min-width:78px;">Reads_num</th>
			         <th style="min-width:180px;">Average depth of coverage</th>
			       </tr>
			     </thead>
			     <tbody>
			       <c:if test="${fn:length(mib.summaryTable)>0}">
			         <c:forEach items="${mib.summaryTable }" var="summary" varStatus="s">
					   <tr>
					     <td style="text-align: left;line-height:1em">${summary.Species }</td>
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
				<img src="<c:if test="${!mib.pngPath.top1png.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top1png }" style="width:90%;">
			  </c:if>
			  <c:if test="${mib.pngPath.top2png!=null }">
				<img src="<c:if test="${!mib.pngPath.top2png.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top2png }" style="width:90%;">
			  </c:if>
			  <c:if test="${mib.pngPath.top3png!=null }">
				<img src="<c:if test="${!mib.pngPath.top3png.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top3png }" style="width:90%;">
			  </c:if>
			  <c:if test="${mib.pngPath.top4png!=null }">
				<img src="<c:if test="${!mib.pngPath.top4png.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top4png }" style="width:90%;">
			  </c:if>
			  <c:if test="${mib.pngPath.top5png!=null }">
				<img src="<c:if test="${!mib.pngPath.top5png.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top5png }" style="width:90%;">
			  </c:if>
			  <c:if test="${mib.pngPath.top6png!=null }">
				<img src="<c:if test="${!mib.pngPath.top6png.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top6png }" style="width:90%;">
			  </c:if>
			  <c:if test="${mib.pngPath.top7png!=null }">
				<img src="<c:if test="${!mib.pngPath.top7png.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top7png }" style="width:90%;">
			  </c:if>
			  <c:if test="${mib.pngPath.top8png!=null }">
				<img src="<c:if test="${!mib.pngPath.top8png.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top8png }" style="width:90%;">
			  </c:if>
			  <c:if test="${mib.pngPath.top9png!=null }">
				<img src="<c:if test="${!mib.pngPath.top9png.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top9png }" style="width:90%;">
			  </c:if>
			  <c:if test="${mib.pngPath.top10png!=null }">
				<img src="<c:if test="${!mib.pngPath.top10png.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.pngPath.top10png }" style="width:90%;">
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
			    	<td style="width:50%;"><img style="max-width:500px;" src="<c:if test="${!mib.qualityPath1.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.qualityPath1 }"></td>
			    	<td><img style="max-width:500px;" src="<c:if test="${!mib.qualityPath2.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }/</c:if>${mib.qualityPath2 }"></td>
			      </tr>
			      <tr>
			    	<td><img style="max-width:500px;" alt="" src="<c:if test="${!mib.seqContentPath1.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.seqContentPath1 }"></td>
			    	<td><img style="max-width:500px;" alt="" src="<c:if test="${!mib.seqContentPath2.contains('Tools') }">${uploadPath}${mib.userId }/${mib.appId }/${mib.dataKey }</c:if>${mib.seqContentPath2 }"></td>
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