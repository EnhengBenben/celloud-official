<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed');
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-cloud"></i>
			<a href="#">APP统计</a>
		</li>
		<li class="active">APP详细信息</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span onclick="toAPPList()">APP详细信息</span>
				<small id="secondTitle" class="hide">
					<i class="icon-double-angle-right"></i>
					<span id="_oneApp"></span>
					<span id="_app_id"></span>
				</small>
			</h3>
			<div class="col-sm-10" style="height: 350px;" id="echartView"></div>
			<div class="table-header hide" id="_appName"></div>
			<div class="col-xs-11 table-responsive " style="margin-left: 60px; margin-top: 15px" id="appListDiv">
				<table id="appList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>APP名称</th>
							<th class="min-w-80">研发机构</th>
							<th class="w160">上线时间</th>
							<th class="min-w-80">运行次数</th>
							<th class="min-w-110">描述</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${appList!=null}">
							<c:forEach var="data" items="${appList}">
								<tr>
									<td>
										<a href="javascript:getAppDetail(${data.app_id },'${data.app_name }')">${data.app_name }</a>
									</td>
									<td>${data.company_name }</td>
									<td>
										<fmt:formatDate type="both" value="${data.create_date }"  pattern="yyyy-MM-dd"/>
									</td>
									<td>${data.runNum }</td>
									<td>${data.description }</td>
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
<script type="text/javascript">
	$.get("app!getAppListByBigUserJson", {}, function(res) {
		var xAxis = new Array(res.length);
		var runNum = new Array(res.length);
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].app_name;
			runNum[i] = res[i].runNum;
	
		}
		var option = makeOptionScrollUnit(xAxis, runNum, "运行次数", barType, 0  , 16, xAxis.length);
		var myChart = echarts.init(document.getElementById('echartView'));
		myChart.setOption(option);
	});
	jQuery(function($) {
		var oTable1 = $('#appList').dataTable({
			// 			bFilter:false,
			"aoColumns" : [ {
				"bSearchable" : true,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			}, {
				"bSearchable" : false,
				"aTargets" : [ 0 ]
			} ],
			iDisplayLength : 100
		
		});
	})
	function getAppDetail(id, name) {
		$("#_oneApp").html(name);
	     $("#_app_id").html(id);
		
		$("#secondTitle").removeClass("hide");
		$.get("app!getAppById", {
			"app.app_id" : id
		}, function(responseText) {
			$("#appListDiv").html(responseText);
		});
	}

	
</script>