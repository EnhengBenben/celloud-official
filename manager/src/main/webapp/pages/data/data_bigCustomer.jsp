<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script src="js/drawCharts.js"></script>
<script src="js/data_bigCustomer.js"></script>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-tasks"></i>
			<a href="#">数据统计</a>
		</li>
		<li class="active">
			<a onclick="javascript:dataFile.toBigCustomerDataCount()">大客户统计</a>
		</li>
		<li class="active" id="cmpName" style="display: none"></li>
	</ul>
	<!-- .breadcrumb -->
	<span id="_cmpId" class="hide"></span>
</div>
<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<div class="table-header hide" id="_companyName"></div>
			<h3 class="header smaller lighter green">大客户数据统计</h3>
			<div>
				<div class="col-sm-12" style="height:350px" id="fileNumPie"></div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="table-responsive" id="dataDiv">
				<table id="bigUserTb" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>大客户编号</th>
							<th>大客户名称</th>
							<th class="hidden-480">数据个数(个)</th>
							<th class="hidden-480">数据大小</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${bigCustomerDataCount!=null }">
							<c:forEach items="${bigCustomerDataCount}" var="data">
								<tr>
									<td>
										<a href="javascript:dataFile.bigCustomerMonthData('${data.companyId }','${data.companyName}');">${data.companyId }</a>
									</td>
									<td>
										<a href="javascript:dataFile.bigCustomerMonthData('${data.companyId }','${data.companyName }');">${data.companyName }</a>
									</td>
									<td>${data.dataNum }</td>
									<td>
										<c:choose>
                                            <c:when test="${data.dataSize>1099511627776 }">
                                                <fmt:formatNumber pattern="0.00" value="${(data.dataSize-data.dataSize%1099511627776)/1099511627776 + data.dataSize%1099511627776/1099511627776 }" />TB</c:when>
                                            <c:when test="${data.dataSize>1073741824 }">
                                                <fmt:formatNumber pattern="0.00" value="${(data.dataSize-data.dataSize%1073741824)/1073741824 + data.dataSize%1073741824/1073741824 }" />GB</c:when>
                                            <c:when test="${data.dataSize>1048576 }">
                                                <fmt:formatNumber pattern="0.00" value="${(data.dataSize-data.dataSize%1048576)/1048576 + data.dataSize%1048576/1048576 }" />MB</c:when>
                                            <c:otherwise>
                                                <fmt:formatNumber pattern="0.00" value="${(data.dataSize-data.dataSize%1024)/1024 + data.dataSize%1024/1024 }" />KB</c:otherwise>
                                        </c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
<!-- /.row -->
</div>
<!-- /.page-content -->
