<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="js/drawCharts.js"></script>
<script src="js/company_bigCustomer.js"></script>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-hospital"></i>
			<a href="#">医院统计</a>
		</li>
		<li class="active">
			<a onclick="hospitalBigUserCount()">大客户统计</a>
		</li>
	</ul>
</div>
<div class="page-content">
	<div class="col-xs-12">
		<div class="row">
			<h3 class="header smaller lighter green">医院数量统计</h3>
			<div class="col-xs-12" style="height: 350px;" id="companyNum"></div>
			<div class="col-xs-12">
				<div class="table-header hide" id="_companyName"></div>
				<div class="table-responsive" id="dataDiv">
					<table id="allUserDataList" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>大客户编号</th>
								<th class="min-w-80">大客户名称</th>
								<th class="min-w-80">入驻时间</th>
								<th class="min-w-80">医院数量</th>
								<th class="min-w-80">用户数量</th>
								<th class="min-w-80">数据个数(个)</th>
								<th class="min-w-80">数据大小</th>
								<th class="min-w-80">运行次数</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${dataList!=null }">
								<c:forEach items="${dataList}" var="item">
									<tr>
										<td>${item.companyId }</td>
										<td>${item.companyName }</td>
										<td>
											<fmt:formatDate type="both" value="${item.createDate }" pattern="yyyy-MM-dd" />
										</td>
										<td>${not empty item.companyNum?item.companyNum:0}</td>
										<td>${not empty item.userNum?item.userNum:0}</td>
										<td>${not empty item.dataNum?item.dataNum:0 }</td>
										<td>
	                                        <c:choose>
	                                            <c:when test="${item.dataSize>1099511627776 }">
	                                                <fmt:formatNumber pattern="0.00" value="${(item.dataSize-item.dataSize%1099511627776)/1099511627776 + item.dataSize%1099511627776/1099511627776 }" />TB</c:when>
	                                            <c:when test="${item.dataSize>1073741824 }">
	                                                <fmt:formatNumber pattern="0.00" value="${(item.dataSize-item.dataSize%1073741824)/1073741824 + item.dataSize%1073741824/1073741824 }" />GB</c:when>
	                                            <c:when test="${item.dataSize>1048576 }">
	                                                <fmt:formatNumber pattern="0.00" value="${(item.dataSize-item.dataSize%1048576)/1048576 + item.dataSize%1048576/1048576 }" />MB</c:when>
	                                            <c:otherwise>
	                                                <fmt:formatNumber pattern="0.00" value="${(item.dataSize-item.dataSize%1024)/1024 + item.dataSize%1024/1024 }" />KB</c:otherwise>
	                                        </c:choose>
	                                    </td>
										<td>${not empty item.runNum?item.runNum:0}</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>
				<!-- PAGE CONTENT ENDS -->
			</div>
		</div>
		<!-- /.row -->
	</div>
</div>
