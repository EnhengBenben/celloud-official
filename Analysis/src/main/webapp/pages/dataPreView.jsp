<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="./css/xenon-components.css" />
<link rel="stylesheet" href="./css/linecons.css" />
<link rel="stylesheet" href="./css/xenon-core.css" />

<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>


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
						<div class="xe-icon"></div>
						<div class="xe-label">
							<strong>数据量:</strong>
							<strong class="num">${resultMap.dataNum}(个)</strong>
						</div>
					</div>
					<div class="xe-lower">
						<div class="border"></div>
					<!--
						<span>所有用户的数据量</span>
					<strong>78% Increase</strong>
				 -->
					</div>
				</div>
				</div>
				<div class="col-sm-3">

					<div class="xe-widget xe-counter-block xe-counter-block-purple" data-count=".num" data-from="0" data-to="${resultMap.dataSize/(1024*1024*1024)}"
						data-duration="3">
						<div class="xe-upper">
							<div class="xe-icon"></div>
							<div class="xe-label">
								<strong>数据大小:</strong>
								<strong class="num">
									<c:choose>
										<c:when test="${resultMap.dataSize>1073741824 }">
											<fmt:formatNumber pattern="0.00" value="${resultMap.dataSize/1073741824 }" />GB</c:when>
										<c:when test="${resultMap.dataSize>1048576 }">
											<fmt:formatNumber pattern="0.00" value="${resultMap.dataSize/1048576 }" />MB</c:when>
										<c:otherwise>
											<fmt:formatNumber pattern="0.00" value="${resultMap.dataSize/1024 }" />KB</c:otherwise>
									</c:choose>
								</strong>
							</div>
						</div>
						<div class="xe-lower">
							<div class="border"></div>
						</div>
					</div>
				</div>
			</div>
			<h3 class="header smaller lighter green">数据量统计</h3>
			<div class="col-xs-12" style="height: 450px;" id="fileNumView"></div>
			<div class="col-xs-11" style="margin-left: 60px; margin-top: 15px">
				<div class="table-header hide" id="_companyName"></div>
				<c:if test="${resultMap.dataList!=null && fn:length(resultMap.dataList) > 0}">
					<div class="col-xs-12">
						<div class="table-responsive" id="dataDiv">
							<table id="MonthDataList" class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th>上传月份</th>
										<th>数据量(个)</th>
										<th>数据大小(GB)</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${resultMap.dataList}" var="data">
										<tr>
											<td>
												<!-- <a href="javascript:getAlluserDataAMonth('${data.yearMonth }')">${data.yearMonth }</a> -->
												${data.yearMonth }
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
	<script type="text/javascript">
		var getUsersMonthDataURL = "data!getUsersMonthData";
		$.get(getUsersMonthDataURL, {}, function(data) {
			console.log(data);
			var xAxis = new Array(data.length);
			var yAxis = new Array(data.length);
			var yAxisAdd = new Array(data.length);
			var yAxisCount = new Array(data.length);
			
			for (var i = 0; i < data.length; i++) {
				xAxis[i] = data[i].yearMonth;
				yAxis[i] = data[i].fileNum;
			}
			yAxisCount[0] = yAxis[0];
			yAxisAdd[0] = 0;
			var count = yAxis[0];
			for (var i = 1; i < yAxis.length; i++) {
				yAxisAdd[i] = yAxis[i] - yAxis[i - 1];
				count = count + yAxis[i];
				yAxisCount[i] = count;
			}
			
			var fileNumOpt = makeOptionScrollUnit(xAxis, yAxis, '数据量', barType, 100, 12);
			fileNumOpt = makeOptionAdd(fileNumOpt, yAxisAdd, "数据量变化曲线图", lineType, "阴影");
			fileNumOpt = makeOptionAdd(fileNumOpt, yAxisCount, "数据量累计", lineType);
			
			var fileNumChart = echarts.init(document.getElementById('fileNumView'));
			fileNumChart.setOption(fileNumOpt);
		});
		
		jQuery(function($) {
			var oTable1 = $('#MonthDataList').dataTable({
				"aoColumns" : [ null, null, { "sType": "filesize" }],
				iDisplayLength : 10,
				"aaSorting" : [ [ 0, "desc" ] ],
			});
		});
	</script>
	<!-- 
	 <script type="text/javascript" src="./js/xenon-widgets.js"></script>
	 -->
