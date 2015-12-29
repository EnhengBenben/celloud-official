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
		<li class="active">大客户APP统计</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span onclick="toAPPList()">大客户APP统计</span>
				<small id="secondTitle" class="hide">
					<i class="icon-double-angle-right"></i>
					<span id="_oneApp"></span>
				</small>
			</h3>
			<div class="table-header hide" id="_appName"></div>
			<div class="col-xs-11 table-responsive ">
				<table id="appList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-80">大客户编号</th>
							<th class="min-w-80">大客户名称</th>
							<th class="min-w-80">入驻时间</th>
							<th class="min-w-80">APP数量</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${appList!=null}">
							<c:forEach var="data" items="${ appList}">
								<tr>
									<td>
										<a href="javascript:getToAppList(${data.company_id },'${data.company_name }')">${data.company_id }</a>
									</td>
									<td>
										<a href="javascript:getToAppList(${data.company_id },'${data.company_name }')">${data.company_name } </a>
									</td>
									<td>
										<fmt:formatDate type="both" value="${data.create_date }" pattern="yyyy-MM-dd" />
									</td>
									<td>${data.runNum }</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<div class="col-sm-10" style="height: 450px;" id="echartView"></div>
				<div class="col-sm-12" id="appListDiv"></div>
			</div>
			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
<script type="text/javascript">
   getToAppList("3","北京嘉宝仁和医疗科技有限公司");
   function getToAppList(id, name) {
		$("#_oneApp").html(name);
		$("#secondTitle").removeClass("hide");
		loadRunNumChart(id);
		$.get("app!getOneBigUserAppList", {
			"user.user_id" : id
		}, function(responseText) {
			$("#appListDiv").html(responseText);
		});
		
	}
	function loadRunNumChart(id) {
		$.get("app!getOneBigUserAppListJson", {
			"user.user_id" : id
		}, function(res) {
			var xAxis = new Array(res.length);
			var runNum = new Array(res.length);
			for (var i = 0; i < res.length; i++) {
				xAxis[i] = res[i].app_name;
				runNum[i] = res[i].runNum;
			}
			var option = makeOptionScrollUnit(xAxis, runNum, "运行次数", barType, 0, 10, xAxis.length);
			var myChart = echarts.init(document.getElementById('echartView'));
			myChart.setOption(option);
		});
	}
</script>