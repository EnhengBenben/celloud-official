<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed');
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-tasks"></i>
			<a href="#">数据统计</a>
		</li>
		<li class="active"><a onclick="getMonthDataList()" >数据个数月统计</a></li>
	</ul>
	<!-- .breadcrumb -->
</div>

<div class="page-content">
	<div class="row">
		<div class="table-header hide" id="_companyName"></div>
		<h3 class="header smaller lighter green">每月数据大小</h3>
		<div class="col-xs-12" style="height: 450px;" id="fileSizeView"></div>
		<h3 class="header smaller lighter green">每月数据个数</h3>
		<div class="col-xs-12" style="height: 450px;" id="fileNumView"></div>
		<div class="title">
			<h3 class="header smaller lighter green">每月数据详细信息列表</h3>
		</div>
		<c:if test="${dataList!=null && fn:length(dataList) > 0}">
			<div class="col-xs-12">
				<div class="table-responsive" id="dataDiv">
					<table id="MonthDataList" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>上传月份</th>
								<th>数据个数(个)</th>
								<th>数据大小(GB)</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${dataList}" var="data">
								<tr>
									<td>
										<!-- <a href="javascript:getAlluserDataAMonth('${data.yearMonth }')">${data.yearMonth }</a> -->
										${data.yearMonth }
									</td>
									<td>${data.fileNum }</td>
									<td>
                                        <c:choose><c:when test="${data.size>1073741824 }"><fmt:formatNumber pattern="0.00" value="${data.size/1073741824 }"/>GB</c:when>
                                        <c:when test="${data.size>1048576 }"><fmt:formatNumber pattern="0.00" value="${data.size/1048576 }"/>MB</c:when>
                                        <c:otherwise><fmt:formatNumber pattern="0.00" value="${data.size/1024 }"/>KB</c:otherwise></c:choose>
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
		<!-- /.row -->
	</div>
	<!-- /.page-content -->
</div>
<script type="text/javascript">
	var getUsersMonthDataURL = "data!getUsersMonthData";
	$.get(getUsersMonthDataURL, {}, function(data) {
		console.log(data);
		var xAxis = new Array(data.length);
		var yAxis = new Array(data.length);
		var yAxisSize = new Array(data.length);
		for (var i = 0; i < data.length; i++) {
			xAxis[i] = data[i].yearMonth;
			yAxis[i] = data[i].fileNum;
			yAxisSize[i] = parseFloat((data[i].size / (1024 * 1024*1024 )).toFixed(2));
		}
		var fileSizeOpt = makeOptionScrollUnit(xAxis, yAxisSize, '数据大小(GB)', barType, 100, 12);
		var fileNumOpt = makeOptionScrollUnit( xAxis, yAxis, '数据个数', barType,100, 12);
		
		var fileSizeChart = echarts.init(document.getElementById('fileSizeView'));
		var fileNumChart = echarts.init(document.getElementById('fileNumView'));
		
		console.log("test");
		fileSizeChart.setOption(fileSizeOpt);
		fileNumChart.setOption(fileNumOpt);
		
	});
	
	jQuery(function($) {
		var oTable1 = $('#MonthDataList').dataTable({
			"aoColumns" : [ null, null, null ],
			iDisplayLength : 10,
			"aaSorting":[[0,"desc"]],
		});
		
		$('table th input:checkbox').on('click', function() {
			var that = this;
			$(this).closest('table').find('tr > td:first-child input:checkbox').each(function() {
				this.checked = that.checked;
				$(this).closest('tr').toggleClass('selected');
			});		
		});
		
		$('[data-rel="tooltip"]').tooltip({
			placement : tooltip_placement
		});
		function tooltip_placement(context, source) {
			var $source = $(source);
			var $parent = $source.closest('table')
			var off1 = $parent.offset();
			var w1 = $parent.width();
			
			var off2 = $source.offset();
			var w2 = $source.width();
			
			if (parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2))
				return 'right';
			return 'left';
		}
		
	})
	function getAlluserDataAMonth(month) {
		$("#secondTitle").removeClass("hide");
		$("#_month").html(month);
		$.get("data!getUserDataInMonth", {
			"month" : month
		}, function(responseText) {
			$("#dataDiv").html(responseText);
		});
	}
</script>
