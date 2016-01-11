<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="row">
	<div class="col-xs-12">
		<div class="col-xs-12">
			<h3 class="header smaller lighter green">数据量统计</h3>
		</div>
		<div class="col-xs-12" style="height: 450px;" id="fileNum"></div>
		<div class="col-xs-12">
			<h3 class="header smaller lighter green">数据大小统计</h3>
		</div>
		<div class="col-xs-12" style="height: 450px;" id="fileSize"></div>
		<div class="table-responsive">
			<table id="allUserDataList" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th>月份</th>
						<th class="hidden-480">数据量(个)</th>
						<th class="hidden-480">数据大小(GB)</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${dataList!=null }">
						<c:forEach items="${dataList}"  var="data">
							<tr>
								<td>${data.yearMonth }</td>
								<td>${data.fileNum }</td>
								<td>
									<fmt:formatNumber value="${data.size/(1024*1024*1024)}" pattern="#00.0#" />
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script type="text/javascript">
  
	getBigUserDataById();8080
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
			if((avg/(1024 * 1024*1024))>0.8){
                len=30;
                optSizeName = "数据大小(GB)"
            }else if((avg/(1024*1024))>0.8){
                len = 20;
                optSizeName = "数据大小(MB)"
            }else if((avg/(1024))>0.8){
                len=10;
                optSizeName = "数据大小(KB)"
            } 
	         for (var i = 0; i < yAxis.length; i++) {
	        	 if(len==10){
	        		   yAxis[i] = parseFloat((yAxis[i] / (1024)).toFixed(2));	        		 
	        	 }
	        	 else if(len==20)
	                 yAxis[i] = parseFloat((yAxis[i] / (1024 * 1024)).toFixed(2));
	        	 else if(len==30)
	                 yAxis[i] = parseFloat((yAxis[i] / (1024 * 1024*1024)).toFixed(2));
	         }
			var fileSizeOpt = makeOptionScrollUnit(xAxis, yAxis,optSizeName, barType, 0, 12);
			var fileNumOpt = makeOptionScrollUnit(xAxis, fileNum, optNumName,  barType, 0, 12);
			
			var sizeChart = echarts.init(document.getElementById(fileSizeId));
			var numChart = echarts.init(document.getElementById(fileNumId));
			
			sizeChart.setOption(fileSizeOpt);
			numChart.setOption(fileNumOpt);
		});
	}
</script>