<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script src="js/drawCharts.js"></script>
<script src="js/data.js"></script>
<link rel="stylesheet" href="css/data.css">
<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-tasks"></i>
			<a href="#">数据统计</a>
		</li>
		<li class="active">
			<a onclick="getPreDataView()">数据总览</a>
		</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="col-xs-12">
		<div class="row">
			<div class="col-sm-3">
				<div class="xe-widget xe-counter-block" data-count=".num" data-from="0" data-to="${resultMap.dataNum}" data-suffix="(个)" data-duration="2">
					<div class="xe-upper">
						<div class="xe-label">
							<strong>数据个数:</strong>
							<strong class="num">${dataNum}(个)</strong>
						</div>
					</div>
					<div class="xe-lower">
						<div class="border"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-3">
				<div class="xe-widget xe-counter-block xe-counter-block-purple" data-count=".num" data-from="0" data-to="${dataSize}"
					, data-suffix="${unit }" data-duration="3">
					<div class="xe-upper">
						<div class="xe-label">
							<strong>数据大小:</strong>
							<strong class="num"> 
                                <c:if test="${empty dataSize }">0(KB)</c:if>
                                <c:if test="${not empty dataSize }">
                                    <c:choose>
                                        <c:when test="${dataSize>1099511627776 }">
                                            <fmt:formatNumber pattern="0.00" value="${(dataSize-(dataSize%1099511627776))/1099511627776 + dataSize%1099511627776/1099511627776 }" />(TB)</c:when>
                                        <c:when test="${dataSize>1073741824 }">
                                            <fmt:formatNumber pattern="0.00" value="${(dataSize-(dataSize%1073741824))/1073741824 + dataSize%1073741824/1073741824 }" />(GB)</c:when>
                                        <c:when test="${dataSize>1048576 }">
                                            <fmt:formatNumber pattern="0.00" value="${(dataSize-(dataSize%1048576))/1048576 + dataSize%1048576/1048576 }" />(MB)</c:when>
                                        <c:otherwise>
                                            <fmt:formatNumber pattern="0.00" value="${(dataSize-(dataSize%1024))/1024 + dataSize%1024/1024 }" />(KB)</c:otherwise>
                                    </c:choose>
                                </c:if>
                            </strong>
						</div>
					</div>
					<div class="xe-lower">
						<div class="border"></div>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${ userRole=='2'}">
		    <div class="col-xs-12 data">
	            <div class="row">
	                <div class="widget-box transparent">
	                    <div class="widget-header widget-header-flat">
	                       <h3 class="header smaller lighter green">大客户数据增量曲线图</h3>
	                     </div>
	                    <div class="col-xs-12" style="height: 350px;" id="bigUserFileNumView"></div>
	                </div>
	            </div>
	        </div>
		</c:if>
		<div class="col-xs-12 data">
		    <div class="row">
		        <div class="widget-box transparent">
		            <div class="widget-header widget-header-flat">
		               <h3 class="header smaller lighter green">数据个数统计</h3>
		             </div>
		            <div class="col-xs-12" style="height: 250px;" id="fileNumView"></div>
                    <div class="col-xs-12" style="height: 250px;" id="fileTotalNum"></div>
		        </div>
		    </div>
		</div>
		<div class="col-xs-12">
			<div class="table-header hide" id="_companyName"></div>
			<c:if test="${monData!=null && fn:length(monData) > 0}">
				<div class="col-xs-12">
					<div class="table-responsive" id="dataDiv">
						<table id="MonthDataList" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th>上传月份</th>
									<th>数据个数(个)</th>
									<th>数据大小</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${monData}" var="data">
									<tr>
										<td>
											<!-- <a href="javascript:getAlluserDataAMonth('${data.yearMonth }')">${data.yearMonth }</a> -->
											${data.time }
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
					<!-- PAGE CONTENT ENDS -->
					<!-- /.col -->
				</div>
			</c:if>
			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
