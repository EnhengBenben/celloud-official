<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<input type="hidden" id="splitId" value="${split.id}">
<div class="row">
	<div class="m-file">
		数据编号：<span class="file-name">${ split.dataKey}</span><br>
		文件名称：
		<span class="file-name">
		<c:forEach items="${split.data}" var="data">
			${data.fileName}&nbsp;&nbsp;&nbsp;
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
			<div class="m-boxCon" id="_table">
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
			<div class="m-boxCon" id="_report" style="display: inline-block;width: 100%">
				<table class="table table-striped-green table-text-center table-padding0" id="snp_table1">
					<thead>
						<tr>
							<th>数据名称
							  <span id="toSaveDataA${ split.dataKey}">
								  <c:if test="${fn:length(split.resultList)>3}">
								  	<c:choose>
									  	<c:when test="${split.upload==2}">
									  		<a class="link" href="javascript:toRunData('${split.splitDataIds}')">（已上传到数据管理）</a>
									  	</c:when>
									  	<c:otherwise>
										  <c:choose>
										  	<c:when test="${split.upload==1}">（正在上传到数据管理...）</c:when>
										  	<c:otherwise>
												<a class="link" href="javascript:void()" onclick="saveReportData('${split.userId }/${split.appId }/${split.dataKey }/result/split/',${fn:length(split.resultList)-3 },'${ split.dataKey}')">保存数据</a>
										  	</c:otherwise>
										  </c:choose>
									  	</c:otherwise>
									  </c:choose>
								  </c:if>
							  </span>
							</th>	
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
										  <c:when test="${data.name=='total' ||data.name=='useful'||data.name=='unknow'}">
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
	</div>
</div>
<!--split上传结果数据-->
<div class="modal hide fade" id="uploadModal" >
	<div class="modal-header">
		<input type="hidden" id="proIdHidden"/>
		<input type="hidden" id="proOwnerHidden"/>
		<a class="close" data-dismiss="modal">×</a>
        <span id="proNameSpan">项目名称：</span>
    	<h3><img src="<%=request.getContextPath()%>/images/publicIcon/icon-modal-03.png"/>上传结果数据</h3>
  	</div>
  	<div class="modal-body">
		<p>共计<span id="dataCount"></span>个数据文件</p>
        <span id="shareProPrompt"></span>
  	</div>
  	<div class="modal-footer">
    	<a href="javascript:saveShareProject();" class="btn">确 定</a>
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