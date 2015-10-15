<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="m-file">
		数据编号：<span class="file-name">${ split.dataKey}</span><br>
		文件名称：
		<div class="file-name">
		<c:forEach items="${split.data}" var="data">
			${split.fileName}<br>
		</c:forEach>
		</div>
		<div class="toolbar">
			<a href="${path.replace('upload','') }Procedure!miRNADownload?userId=${split.userId }/${split.appId }/${split.dataKey }/result/split_reads.tar.gz" class="btn btn-default"><i class="i-download"></i>下载全部</a>
		</div>
	</div>
	<div id="printCMPContext">
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-report1"></i>数据统计</h2>
			<div class="m-boxCon" id="_table">
		    	<table class="table table-bordered table-condensed">
					<thead>
						<tr><th>序列总数</th></tr>
						<tr><th>平均质量</th></tr>
						<tr><th>平均GC含量</th></tr>
				    </thead>
					<tbody>
						<tr>
							<td>${split.totalReads }</td>
						</tr>
						<tr>
							<td>${split.avgQuality }</td>
						</tr>
						<tr>
							<td>${split.avgGCContent }</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!--检测结果-->
		<div class="m-box m-box-yc">
			<h2><i class="i-edit"></i>报告</h2>
			<div class="m-boxCon" id="_report" style="display: inline-block;width: 100%">
				<table class="table table-striped-green table-text-center table-padding0" id="snp_table1">
					<thead>
						<tr>
							<th>数据名称</th>
							<th>序列数量</th>
						</tr>	
					</thead>
					<tbody>
					  <c:choose>
					  	<c:when test="${split.resultList==null}"><tr><td colspan="3">未分析出结果</td></tr></c:when>
					  	<c:otherwise>
							<c:forEach items="${split.resultList}" var="split">
								<tr>
									<td><a href="${path.replace('upload','') }Procedure!miRNADownload?userId=${pgs.userId }/${pgs.appId }/${pgs.dataKey }/result/split/${split.name }.tar.gz">${split.name }</a></td>
									<td>${split.number }</td>
								</tr>
							</c:forEach>
					  	</c:otherwise>
					  </c:choose>
					</tbody>
				</table>
			</div>
			<div class="m-tips">
				<i class="i-tips"></i>注：点击文件名即可下载
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