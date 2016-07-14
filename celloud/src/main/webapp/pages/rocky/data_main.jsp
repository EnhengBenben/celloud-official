<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table table-main">
	<thead>
		<tr>
			<th width="40"></th>
			<th width="140">
				<input id="sample-selector" type="text" placeholder="样本编号/病历号">
			</th>
			<th>文件名</th>
			<th>标签</th>
			<th>文件大小</th>
			<th>
				状态
				<a href="javascript:void(0);">
					<i class="fa fa-sort" aria-hidden="true"></i>
				</a>
			</th>
			<th>
				上传时间
				<a id="sort-date" href="javascript:void(0);">
					<i id="sort-date-icon" class="fa fa-sort-amount-asc"></i>
				</a>
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
				<td>${data.dataKey }</td>
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
	<jsp:include page="pagination.jsp"></jsp:include>
</div>
<jsp:include page="statistic.jsp"></jsp:include>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rocky_data.js"></script>