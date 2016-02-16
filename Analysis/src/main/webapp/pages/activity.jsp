<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="col-sm-12">
	<div class="row">
		<h4 class="header smaller lighter green title">医院数据个数统计</h4>
		<div class="col-xs-12 " id="hfileNum" style="height: 350px"></div>
		<h4 class="header smaller lighter green title">医院数据大小统计</h4>
		<div id="hfileSize" style="height: 350px"></div>
		<c:if test="${dataList!=null&& fn:length(dataList) > 0}">
			<div class="col-xs-12 table-responsive" id="dataDiv">
				<table id="fileListId" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-90">医院名称</th>
							<th class="min-w-80">入驻时间</th>
							<th class="min-w-80">数据个数</th>
							<th class="min-w-80">数据大小</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dataList }" var="data">
							<tr>
								<td>${data.company_name }</td>
								<td>
									<fmt:formatDate type="both" value="${data.create_date }" pattern="yyyy-MM-dd" />
								</td>
								<td>${data.fileNum }</td>
								<td>
									<c:choose>
										<c:when test="${data.size>1073741824 }">
											<fmt:formatNumber pattern="0.00" value="${data.size/1073741824 }" />GB</c:when>
										<c:when test="${data.size>1048576 }">
											<fmt:formatNumber pattern="0.00" value="${data.size/1048576 }" />MB</c:when>
										<c:otherwise>
											<fmt:formatNumber pattern="0.00" value="${data.size/1024 }" />KB</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>

		<h4 class="header smaller lighter green">用户数据个数统计</h4>
		<div id="uFileNum" style="height: 350px"></div>
		<h4 class="header smaller lighter green">用户数据大小统计</h4>
		<div id="uFileSize" style="height: 350px"></div>
		<h4 class="header smaller lighter green">用户APP运行统计</h4>
		<div id="uAppRun" style="height: 350px"></div>
		<c:if test="${userDataList!=null && fn:length(userDataList) > 0 }">
			<div class="col-xs-12 table-responsive" id="dataDiv">
				<table id="userFileList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-90">用户名称</th>
							<th class="min-w-80">注册时间</th>
							<th class="min-w-80">数据个数</th>
							<th class="min-w-80">数据大小</th>
							<th class="min-w-80">运行次数</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userDataList }" var="data">
							<tr>
								<td>${data.user_name }</td>
								<td>
									<fmt:formatDate type="both" value="${data.create_date }" pattern="yyyy-MM-dd" />
								</td>
								<td>${data.fileNum }</td>
								<td>
									<c:choose>
										<c:when test="${data.size>1073741824 }">
											<fmt:formatNumber pattern="0.00" value="${data.size/1073741824 }" />GB</c:when>
										<c:when test="${data.size>1048576 }">
											<fmt:formatNumber pattern="0.00" value="${data.size/1048576 }" />MB</c:when>
										<c:otherwise>
											<fmt:formatNumber pattern="0.00" value="${data.size/1024 }" />KB</c:otherwise>
									</c:choose>
								</td>
								<td>${data.runNum }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		<h4 class="header smaller lighter green">运行统计</h4>
		<div class="col-xs-12 " id="appListDiv" style="height: 350px"></div>
		<c:if test="${runList!=null && fn:length(runList) > 0 }">
			<div class="col-xs-12 table-responsive" id="dataDiv">
				<table id="appList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-90">APP名称</th>
							<th class="min-w-80">上线时间</th>
							<th class="min-w-80">运行次数</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${runList }" var="data">
							<tr>
								<td>${data.app_name }</td>
								<td>
									<fmt:formatDate type="both" value="${data.create_date }" pattern="yyyy-MM-dd" />
								</td>
								<td>${data.runNum }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>
</div>
<script src="./js/activity.js" type="text/javascript"></script>