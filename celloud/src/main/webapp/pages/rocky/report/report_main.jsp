<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table table-main">
	<thead>
		<tr>
			<th width="40"></th>
			<th width="140">
				<input id="report-sample-filter" type="text" placeholder="样本编号/病历号" value="${sampleFilter }">
			</th>
			<th>
				文件名
				<c:if test="${sidx=='filename'&&sord=='asc' }">
					<a id="reportSortBtn-filename-desc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-asc"></i>
					</a>
				</c:if>
				<c:if test="${sidx=='filename'&&sord=='desc' }">
					<a id="reportSortBtn-filename-asc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-desc"></i>
					</a>
				</c:if>
				<c:if test="${sidx!='filename' }">
					<a id="reportSortBtn-filename" href="javascript:void(0);">
						<i class="fa fa-sort" aria-hidden="true"></i>
					</a>
				</c:if>
			</th>
			<th>
				标签
				<c:if test="${sidx=='batch'&&sord=='asc' }">
					<a id="reportSortBtn-batch-desc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-asc"></i>
					</a>
				</c:if>
				<c:if test="${sidx=='batch'&&sord=='desc' }">
					<a id="reportSortBtn-batch-asc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-desc"></i>
					</a>
				</c:if>
				<c:if test="${sidx!='batch' }">
					<a id="reportSortBtn-batch" href="javascript:void(0);">
						<i class="fa fa-sort" aria-hidden="true"></i>
					</a>
				</c:if>
			</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody id="data-list-tbody">
		<c:forEach items="${pageList.datas }" var="report">
			<tr id="report-${report.dataKey}-${report.projectId}-${report.appId}">
				<td>
					<label class="checkbox-lable">
						<input class="checkbox" type="checkbox" name="demo-checkbox1">
						<span class="info"></span>
					</label>
				</td>
				<td>${report.sampleName }</td>
				<td title="${report.fileName }" name="data-name-td">${report.fileName }</td>
				<td>${report.batch }</td>
				<td>
					<c:if test="${report.period==0 }">等待运行</c:if>
					<c:if test="${report.period==1 }">正在运行</c:if>
					<c:if test="${report.period==2 }">完成</c:if>
					<c:if test="${report.period==3 }">数据上传中</c:if>
					<c:if test="${report.period==4 }">异常终止</c:if>
					<c:if test="${report.period==5 }">送样中</c:if>
					<c:if test="${report.period==6 }">实验中</c:if>
					<c:if test="${empty report.period }">
						<a href="javascript:void(0)" onclick="$.report.period.error('${report.fileName }')" class="wrong">运行异常</a>
					</c:if>
				</td>
				<td>
					<c:choose>
						<c:when test="${report.period ==2 }">
							<a title="查看报告" href="javascript:$.report.detail.patient('${report.dataKey}',${report.projectId},${report.appId},${size.count},${pageList.page.currentPage })">
								<i class="fa fa-eye"></i>
							</a>
						</c:when>
						<c:otherwise>
							<a title="查看报告" class="disabled" href="javascript:$report.showReport();">
								<i class="fa fa-eye"></i>
							</a>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${report.period ==21 }">
							<a title="打印患者报告" target="_blank"
								href="<%=request.getContextPath()%>/report/printRockyReport?projectId=${report.projectId }&dataKey=${report.dataKey }&appId=${report.appId }&templateType=print_patient">
								<i class="fa fa-print"></i>
							</a>
						</c:when>
						<c:otherwise>
							<a title="打印患者报告" class="disabled bg-gray" disabled="disabled">
								<i class="fa fa-print"></i>
							</a>
						</c:otherwise>
					</c:choose>
					<a title="共享报告" href="javascript:void(0)">
						<i class="fa fa-share-square-o"></i>
					</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:if test="${pageList.datas.size()<=0 }">
	<div style="display: table; width: 100%; margin-top: -10px;">
		<div class="text-center" style="display: table-cell; height: 350px; font-size: 18px; vertical-align: middle; background-color: #fff;">
			<i class="glyphicon glyphicon-exclamation-sign" style="color: #f39c12;"></i> 您好，还没有报告哦！
		</div>
	</div>
</c:if>
<div id="rocky_report_page">
	<jsp:include page="../pagination.jsp"></jsp:include>
</div>
<jsp:include page="../statistic.jsp"></jsp:include>