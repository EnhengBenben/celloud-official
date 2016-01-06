<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			<i class="icon-user-md"></i>
			<a href="#">用户统计</a>
		</li>
		<li class="active"><a onclick="toUserList()">用户详细信息</a></li>
	</ul>
</div>
<div class="page-content">
	<div class="row">
		<!-- <h3 class="header smaller lighter blue">
			<span onclick="toUserList()">用户详细信息</span>
			<small id="secondTitle" class="hide">
				<i class="icon-double-angle-right"></i>
				<span id="_oneUser"></span>
			</small>
		</h3>
		 -->
		<div class="title">
			<h3 class="header smaller lighter green">数据量统计</h3>
		</div>
		<div class="col-xs-12" style="height: 450px;" id="fileNum"></div>
		<div class="title">
			<h3 class="header smaller lighter green">数据大小统计</h3>
		</div>
		<div class="col-xs-12" style="height: 450px;" id="fileSize"></div>
		<div class="title">
			<h3 class="header smaller lighter green">APP运行统计</h3>
		</div>
		<div class="col-xs-12" style="height: 450px;" id="runNum"></div>

		<div class="table-header hide" id="_userName"></div>
		<div class="title">
			<h3 class="header smaller lighter green">用户详细信息列表</h3>
		</div>
		<c:if test="${userList!=null }">
			<div class="col-xs-11 table-responsive" id="userListDiv" style="margin-left: 60px; margin-top: 15px">
				<table id="userList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>用户名</th>
							<th class="min-w-120">所属医院</th>
							<th class="min-w-80">所属部门</th>
							<th class="min-w-80">数据个数</th>
							<th class="min-w-110">数据大小(GB)</th>
							<th class="min-w-80">运行次数</th>
							<th class="w160">注册时间</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${userList }" var="data">
							<tr>
								<td>
									${data.username }
									<!-- 
									 <a href="javascript:getUserDetail(${data.user_id },'${data.username }')">${data.username }</a>
									 -->
								</td>
								<td>${data.company_name }</td>
								<td>${data.dept_name }</td>
								<td>${data.fileNum }</td>
								<td>
									<fmt:formatNumber pattern="0.00" value="${data.size/ (1024 * 1024 * 1024) }" />
								</td>
								<td>${data.runNum }</td>
								<td>
									<fmt:formatDate type="both" value="${data.createDate }" pattern="yyyy-MM-dd" />
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
<!-- /.page-content -->
<script type="text/javascript">
	var fileNumViewId = "fileNum";
	var fileSizeViewId = "fileSize";
	var runNumViewId = "runNum";
	
	$.get("user!getUserListByBigUserJson", {
		"orderType" : "runNum"
	}, function(res) {
		var xAxis = new Array(res.length);
		var yAxis = new Array(res.length);
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].username;
			yAxis[i] = res[i].runNum;
		}
		var opt = makeOptionScrollUnit(xAxis, yAxis, '运行次数', 'bar', 0, 20);
		var myChart = echarts.init(document.getElementById(runNumViewId));
		myChart.setOption(opt);
	});
	$.get("user!getUserListByBigUserJson", {
		"orderType" : "fileNum"
	}, function(res) {
		var xAxis = new Array(res.length);
		var yAxis = new Array(res.length);
		
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].username;
			yAxis[i] = res[i].fileNum;
		}
		var opt = makeOptionScrollUnit(xAxis, yAxis, '数据量', 'bar', 0, 20);
		var myChart = echarts.init(document.getElementById(fileNumViewId));
		myChart.setOption(opt);
	});
	$.get("user!getUserListByBigUserJson", {
		"orderType" : "size"
	}, function(res) {
		var xAxis = new Array(res.length);
		var yAxis = new Array(res.length);
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].username;
			yAxis[i] = parseFloat((res[i].size / (1024 * 1024 * 1024)).toFixed(2));
		}
		var opt = makeOptionScrollUnit(xAxis, yAxis, '数据大小(GB)', 'bar', 0, 20);
		var myChart = echarts.init(document.getElementById(fileSizeViewId));
		myChart.setOption(opt);
	});
	
	jQuery(function($) {
		var oTable1 = $('#userList').dataTable({
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, null, null, null ],
			"aaSorting" : [ [ 3, "desc" ] ],
			iDisplayLength : 100
		});
	})
	function getUserDetail(id, name) {
		$("#_oneUser").html(name);
		$("#secondTitle").removeClass("hide");
		$.get("user!getUserById", {
			"user.userId" : id
		}, function(responseText) {
			$("#userListDiv").html(responseText);
		});
	}
</script>