<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<input type="hidden" id="splitId" value="${split.id}">
<div>
	<div class="m-file">
		数据编号：<span class="file-name">${ split.dataKey}</span><br>
		文件名称：
		<span class="file-name">
		<c:forEach items="${split.data}" var="data">
			${data.fileName}(${data.dataKey})&nbsp;&nbsp;&nbsp;
		</c:forEach>
		</span>
		<div class="toolbar">
			<a href="${path.replace('upload','') }Procedure!miRNADownload?userId=${split.userId }/${split.appId }/${split.dataKey }/result/split_reads.tar.gz" class="btn btn-default"><i class="i-download"></i>下载全部</a>
		</div>
	</div>
	<div id="printCMPContext">
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
							<td>${split.totalReads }</td>
							<td>${split.avgQuality }</td>
							<td>${split.avgGCContent }</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!--检测结果-->
		<div class="m-box m-box-yc">
			<h2><i class="i-edit"></i>报告</h2>
			<div class="m-boxCon">
				<table class="table table-striped-green table-text-center table-padding0" id="snp_table1">
					<thead>
						<tr>
							<th>数据名称</th>	
							<th>序列数量</th>
						</tr>	
					</thead>
					<tbody>
					  <c:choose>
					  	<c:when test="${split.resultList==null}"><tr><td colspan="2">未分析出结果</td></tr></c:when>
					  	<c:otherwise>
							<c:forEach items="${split.resultList}" var="data">
								<tr>
									<td>
										<c:choose>
										  <c:when test="${data.name=='total' ||data.name=='useful'||data.name=='unknown'}">
										    ${data.name }
										  </c:when>
										  <c:otherwise>
											<a class="link" href="${path.replace('upload','') }Procedure!miRNADownload?userId=${split.userId }/${split.appId }/${split.dataKey }/result/split/${data.name }.tar.gz">${data.name }</a>  
										  </c:otherwise>
										</c:choose>
									</td>
									<td>${data.number }</td>
								</tr>
							</c:forEach>
					  	</c:otherwise>
					  </c:choose>
					</tbody>
				</table>
			</div>
			<div class="m-tips">
				<i class="i-tips"></i>注：点击文件名即可下载；已保存的数据可到数据管理页面查看
			</div>
		</div>
		<!--检测结果-->
		<div class="m-box">
			<h2><i class="i-edit"></i>序列质量分析（见QC结果）</h2>
			<div class="m-boxCon" id="_report" style="display: inline-block;width: 90%">
			  <c:choose>
				<c:when test="${empty split.basicStatistics1 }">
					无质量分析结果
				</c:when>
				<c:otherwise>
				  <div class="h2">Basic Statistics</div>
				  <table class="table table-bordered table-condensed">
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
				  <table style="width:90%;">
			        <tr>
			    	  <td style="width:50%;"><img style="max-width:500px;" src="${outPath }/${split.userId }/${split.appId }/${split.dataKey }${split.qualityPath1 }"></td>
			    	  <td><img style="max-width:500px;" src="${outPath }/${split.userId }/${split.appId }/${split.dataKey }${split.qualityPath2 }"></td>
			        </tr>
			        <tr>
			    	  <td><img style="max-width:500px;" alt="" src="${outPath }/${split.userId }/${split.appId }/${split.dataKey }${split.seqContentPath1 }"></td>
			    	  <td><img style="max-width:500px;" alt="" src="${outPath }/${split.userId }/${split.appId }/${split.dataKey }${split.seqContentPath2 }"></td>
			        </tr>
			      </table>
				</c:otherwise>
			  </c:choose>
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