<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javas
	cript">
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
		<li class="active"><a onclick="toAPPList()">APP详细信息</a></li>
		<li  id = "app_name" style="display:none" ></li>
	</ul>
	<!-- .breadcrumb -->
	<span id = "_app_id" class = "hide"></span>
</div>
<div class="page-content">
	<div class="row" id = "app_content">
		<div class="col-xs-12">
			<div class="title">
				<h3 class="header smaller lighter green">APP运行统计</h3>
			</div>
			<div class="col-sm-10" style="height: 350px;" id="echartView"></div>
			<div class="table-header hide" id="_appName"></div>
			<c:if test="${appList!=null &&fn:length(appList) > 0}">
				<div class="col-xs-11" style="margin-left: 60px; margin-top: 15px" id="appListDiv">
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
							<c:forEach var="data" items="${appList}">
								<tr>
									<td>
										<a href="javascript:getAppDetail(${data.app_id },'${data.app_name }')">${data.app_name }</a>
									</td>
									<td>${data.company_name }</td>
									<td>
										  <fmt:formatDate type="both" value="${data.create_date }" pattern="yyyy-MM-dd" />
									</td>
									<td>${data.runNum }</td>
									<td>${data.description }</td>
								</tr>
							</c:forEach>

						</tbody>
					</table>
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
	$.get("app!getAppListByBigUserJson", {}, function(res) {
		var xAxis = new Array(res.length);
		var runNum = new Array(res.length);
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].app_name;
			runNum[i] = res[i].runNum;
			
		}
		var option = makeOptionScrollUnit(xAxis, runNum, "运行次数", barType, 0, 16);
		var myChart = echarts.init(document.getElementById('echartView'));
		myChart.setOption(option);
	});
	jQuery(function($) {
		var oTable1 = $('#appList').dataTable({
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, null, {
				"bSortable" : false
			} ],
			"aaSorting" : [ [ 3, "desc" ] ],
			iDisplayLength : 10
		
		});
	})
	function getAppDetail(id, name) {
		$("#app_name").html(name);
		$("#app_name").css("display","inline");
		$("#_app_id").html(id);
		
		$("#secondTitle").removeClass("hide");
		$.get("app!getAppById", {
			"app.app_id" : id
		}, function(responseText) {
			$("#app_content").html(responseText);
		});
	}
</script>