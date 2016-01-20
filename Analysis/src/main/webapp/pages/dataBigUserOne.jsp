<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="row">
    <div class="col-xs-12">

	<h3 class="header smaller lighter green">数据统计</h3>
	<div style="height: 300px;" id="fileNum"></div>
	<!-- 
	 <div style="height: 300px;" id="fileSize"></div>
	 -->
	</div>
	<div class="col-xs-12">
		<c:if test="${fn:length(dataList) > 0}">
			<div class="table-responsive">
				<table id="allUserDataList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>月份</th>
							<th class="hidden-480">数据量(个)</th>
							<th class="hidden-480">数据大小</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dataList}" var="data">
							<tr>
								<td>${data.yearMonth }</td>
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

	</div>
</div>
<script type="text/javascript">
	getBigUserDataById();
	8080
	function getBigUserDataById() {
		var fileSizeId = "fileSize";
		var fileNumId = "fileNum";
		var bigUesrURL = "data!getBigUserMonth";
		var bigUesrTableURL = "data!getBigUserMonthTable";
		var cmpId = $("#_cmpId").html();
		var param = {
			"companyId" : cmpId
		};
		var optNumName = '数据量';
		var optSizeName = "数据大小(GB)";
		$.get(bigUesrURL, param, function(data) {
			var xAxis = new Array(data.length);
			var yAxis = new Array(data.length);
			var fileNum = new Array(data.length);
			var len = 0;
			for (var i = 0; i < data.length; i++) {
				xAxis[i] = data[i].yearMonth;
				fileNum[i] = data[i].fileNum;
				yAxis[i] = parseInt(data[i].size);
			}
			var avg = ListAvg(yAxis);
			if ((avg / (1024 * 1024 * 1024)) > 0.8) {
				len = 30;
				optSizeName = "数据大小(GB)"
			} else if ((avg / (1024 * 1024)) > 0.8) {
				len = 20;
				optSizeName = "数据大小(MB)"
			} else if ((avg / (1024)) > 0.8) {
				len = 10;
				optSizeName = "数据大小(KB)"
			}
			for (var i = 0; i < yAxis.length; i++) {
				if (len == 10) {
					yAxis[i] = parseFloat((yAxis[i] / (1024)).toFixed(2));
				} else if (len == 20)
					yAxis[i] = parseFloat((yAxis[i] / (1024 * 1024)).toFixed(2));
				else if (len == 30)
					yAxis[i] = parseFloat((yAxis[i] / (1024 * 1024 * 1024)).toFixed(2));
			}
		//	var fileSizeOpt = makeOptionScrollUnit(xAxis, yAxis, optSizeName, lineType, 100, 12);
			var fileNumOpt = makeOptionScrollUnit(xAxis, fileNum, optNumName, lineType, 100, xAxis.length);
			
	//		var sizeChart = echarts.init(document.getElementById(fileSizeId));
			var numChart = echarts.init(document.getElementById(fileNumId));
			
	//		sizeChart.setOption(fileSizeOpt);
			numChart.setOption(fileNumOpt);
		});
	}
	var oTable1 = $('#allUserDataList').dataTable({
		"aoColumns" : [ null, null, { "sType": "filesize" }  ],
		iDisplayLength : 12,
		"aaSorting" : [ [ 0, "desc" ] ],
	});
</script>