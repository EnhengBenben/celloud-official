<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/drawCharts.js"></script>
<script src="js/data_company.js"></script>
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-hospital"></i>
			<a href="#">数据统计</a>
		</li>
		<li class="active" onclick="toHospitalList()">医院数据量统计</li>
	</ul>
</div>
<div class="page-content">
<div class="col-xs-12" >
	<div class="row">
		<div class="table-header hide" id="_companyName"></div>
		<h3 class="header smaller lighter green">医院数据个数统计</h3>
		<div class="col-xs-12" style="height: 350px;" id="fileNumView"></div>
		<c:if test="${companyDataCount!=null&&fn:length(companyDataCount) > 0}">
			<div class="col-xs-11 table-responsive table-div">
				<table id="hospitalList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-90">医院名称</th>
							<th class="min-w-90">医院地址</th>
							<th class="min-w-90">医院电话</th>
							<th class="min-w-80">入驻时间</th>
							<th class="min-w-80">数据个数</th>
							<th class="min-w-80">数据大小</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${companyDataCount }" var="data">
							<tr>
								<td>
									${data.companyName }
								</td>
								<td>${data.address }</td>
								<td>${data.tel }</td>
								<td>
									<fmt:formatDate type="both" value="${data.createDate }" pattern="yyyy-MM-dd" />
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
					</tbody>
				</table>
			</div>
		</c:if>
		<!-- PAGE CONTENT ENDS -->
	</div>
	<!-- /.row -->
	</div>
</div>
<!-- /.page-content -->