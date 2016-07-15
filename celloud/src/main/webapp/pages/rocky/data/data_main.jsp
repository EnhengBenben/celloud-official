<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table table-main">
	<thead>
		<tr>
			<th width="40"></th>
			<th width="140">
				<input id="data-sample-filter" type="text" placeholder="样本编号/病历号" value="${sampleFilter}">
			</th>
			<th>
				文件名
				<c:if test="${sidx=='filename'&&sord=='asc' }">
					<a id="sortBtn-filename-desc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-asc"></i>
					</a>
				</c:if>
				<c:if test="${sidx=='filename'&&sord=='desc' }">
					<a id="sortBtn-filename-asc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-desc"></i>
					</a>
				</c:if>
				<c:if test="${sidx!='filename' }">
					<a id="sortBtn-filename" href="javascript:void(0);">
						<i class="fa fa-sort" aria-hidden="true"></i>
					</a>
				</c:if>
			</th>
			<th>
				标签
				<c:if test="${sidx=='batch'&&sord=='asc' }">
					<a id="sortBtn-batch-desc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-asc"></i>
					</a>
				</c:if>
				<c:if test="${sidx=='batch'&&sord=='desc' }">
					<a id="sortBtn-batch-asc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-desc"></i>
					</a>
				</c:if>
				<c:if test="${sidx!='batch' }">
					<a id="sortBtn-batch" href="javascript:void(0);">
						<i class="fa fa-sort" aria-hidden="true"></i>
					</a>
				</c:if>
			</th>
			<th>
				文件大小
				<c:if test="${sidx=='filesize'&&sord=='asc' }">
					<a id="sortBtn-filesize-desc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-asc"></i>
					</a>
				</c:if>
				<c:if test="${sidx=='filesize'&&sord=='desc' }">
					<a id="sortBtn-filesize-asc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-desc"></i>
					</a>
				</c:if>
				<c:if test="${sidx!='filesize' }">
					<a id="sortBtn-filesize" href="javascript:void(0);">
						<i class="fa fa-sort" aria-hidden="true"></i>
					</a>
				</c:if>
			</th>
			<th>状态</th>
			<th>
				上传时间
				<c:if test="${sidx=='createDate'&&sord=='asc' }">
					<a id="sortBtn-createDate-desc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-asc"></i>
					</a>
				</c:if>
				<c:if test="${sidx=='createDate'&&sord=='desc' }">
					<a id="sortBtn-createDate-asc" href="javascript:void(0);">
						<i class="fa fa-sort-amount-desc"></i>
					</a>
				</c:if>
				<c:if test="${sidx!='createDate' }">
					<a id="sortBtn-createDate" href="javascript:void(0);">
						<i class="fa fa-sort" aria-hidden="true"></i>
					</a>
				</c:if>
			</th>
			<c:if test="1=2">
				<th>操作</th>
			</c:if>
		</tr>
	</thead>
	<tbody id="data-list-tbody">
		<c:forEach items="${pageList.datas }" var="data">
			<tr>
				<td>
					<label class="checkbox-lable">
						<input class="checkbox" type="checkbox" name="demo-checkbox1">
						<span class="info"></span>
					</label>
				</td>
				<td>${data.sample }</td>
				<td title="${data.fileName }" name="data-name-td">${data.fileName }</td>
				<td>${data.batch }</td>
				<td>
					<c:choose>
						<c:when test="${data.size>1048576 }">
							<fmt:formatNumber pattern="0.00" value="${data.size/1048576 }" />MB</c:when>
						<c:otherwise>
							<fmt:formatNumber pattern="0.00" value="${data.size/1024 }" />KB</c:otherwise>
					</c:choose>
				</td>
				<td>${data.isRunning==0?'完成':'分析中' }</td>
				<td>
					<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${data.createDate }" />
				</td>
				<c:if test="1=2">
					<td>
						<c:choose>
							<c:when test="${task.period ==2 }">
								<a title="查看报告" href="javascript:$.report.detail.patient('${task.dataKey}',${task.projectId},${task.appId},${size.count},${pageList.page.currentPage })">
									<i class="fa fa-eye"></i>
								</a>
							</c:when>
							<c:otherwise>
								<a title="查看报告" class="disabled" disabled="disabled">
									<i class="fa fa-eye"></i>
								</a>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${task.period ==2 }">
								<a title="打印患者报告" target="_blank"
									href="<%=request.getContextPath()%>/report/printBSIReport?projectId=${task.projectId }&dataKey=${task.dataKey }&appId=${task.appId }&templateType=print_patient">
									<i class="fa fa-print"></i>
								</a>
							</c:when>
							<c:otherwise>
								<a title="打印患者报告" class="disabled" disabled="disabled">
									<i class="fa fa-print"></i>
								</a>
							</c:otherwise>
						</c:choose>
						<a title="共享报告" href="javascript:void(0)">
							<i class="fa fa-share-square-o"></i>
						</a>
					</td>
				</c:if>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div id="rocky_data_page">
	<jsp:include page="../pagination.jsp"></jsp:include>
</div>
<jsp:include page="../statistic.jsp"></jsp:include>