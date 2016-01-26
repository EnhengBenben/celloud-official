<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>
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
						<c:if test="${cmpList!=null }">
							<c:forEach items="${cmpList}" var="item">
								<tr>
									<td>${item.company_id }</td>
									<td>${item.company_name }</td>
									<td>
										<fmt:formatDate type="both" value="${item.create_date }" pattern="yyyy-MM-dd" />
									</td>
									<td>${item.companyNum}</td>
									<td>${item.userNum}</td>
									<td>${item.fileNum }</td>
									<td>
										<c:choose>
											<c:when test="${item.size>1073741824 }">
												<fmt:formatNumber pattern="0.00" value="${item.size/1073741824 }" />GB</c:when>
											<c:when test="${item.size>1048576 }">
												<fmt:formatNumber pattern="0.00" value="${item.size/1048576 }" />MB</c:when>
											<c:otherwise>
												<fmt:formatNumber pattern="0.00" value="${item.size/1024 }" />KB</c:otherwise>
										</c:choose>
									</td>
									<td>${item.runNum}</td>
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
<!-- /.page-content -->
<script type="text/javascript">
	var GetCompanyNumURL = "home!toCompanyBigUserJson";
	jQuery(function($) {
		getCompanyBigUesr();
		var oTable1 = $('#allUserDataList').dataTable({
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, null, null, null, {
				"sType" : "filesize"
			}, null ],
			iDisplayLength : 10,
			"aaSorting" : [ [ 3, "desc" ] ],
		});
		
	});
	function getCompanyBigUesr() {
		$.get(GetCompanyNumURL, {}, function(data) {
			console.log(data);
			var xAxis = new Array(data.length);
			var yAxis = new Array(data.length);
			for (var i = 0; i < data.length; i++) {
				xAxis[i] = data[i].company_name;
				yAxis[i] = {
					'value' : data[i].companyNum,
					'name' : data[i].company_name
				}
			}
			var option = makePieOption('', xAxis, '医院数量统计', '70%', '45%', '45%', yAxis, {x:'left',y:'center',orient : 'vertical'})
			var myChart = echarts.init(document.getElementById('companyNum'), theme);
			myChart.setOption(option);
		});
		
	}
</script>