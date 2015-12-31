<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed');
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-hospital"></i>
			<a href="#">医院统计</a>
		</li>
		<li class="active">医院详细信息</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<!--  <h3 class="header smaller lighter blue">
			<span onclick="toHospitalList()">医院详细信息</span>
			<small id="secondTitle" class="hide">
				<i class="icon-double-angle-right"></i>
				<span id="_oneHospital"></span>
			</small>
		</h3>
		-->
		<div class="table-header hide" id="_companyName"></div>
		<div class="title">
			<h3 class="header smaller lighter green">APP运行统计</h3>
		</div>
		<div class="col-xs-12" style="height: 450px;" id="runNumView"></div>
		<div class="title">
			<h3 class="header smaller lighter green">数据量统计</h3>
		</div>
		<div class="col-xs-12" style="height: 450px;" id="fileNumView"></div>
		<div class="title">
			<h3 class="header smaller lighter green">数据大小统计</h3>
		</div>
		<div class="col-xs-12" style="height: 450px;" id="fileSizeView"></div>
		<div class="col-xs-11 table-responsive" id="dataDiv">
			<table id="hospitalList" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="min-w-90">医院名称</th>
						<th class="min-w-80">入驻时间</th>
						<th>地址</th>
						<th class="min-w-60">电话</th>
						<th class="min-w-80">用户数量</th>
						<th class="min-w-80">数据个数</th>
						<th class="min-w-80">数据大小(GB)</th>
						<th class="min-w-80">报告个数</th>
					</tr>
				</thead>

				<tbody>
					<c:if test="${complist!=null}">

						<c:forEach items="${complist }" var="data">
							<tr>
								<td>
									<a href="javascript:getCompanyDetail(${data.company_id },'${data.company_name }')">${data.company_name }</a>
								</td>
								<td>
									<fmt:formatDate type="both" value="${data.create_date }" pattern="yyyy-MM-dd" />
								</td>
								<td>${data.address }</td>
								<td>${data.tel }</td>
								<td>${data.userNum }</td>
								<td>${data.fileNum }</td>
								<td>
									<fmt:formatNumber pattern="0.00" value="${data.size/1024/1024/1024 }" />
								</td>
								<td>${data.runNum }</td>

							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>

		</div>

		<!-- PAGE CONTENT ENDS -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
<script type="text/javascript">
	var runNumViewID = "runNumView";
	var fileNumViewID = "fileNumView";
	var fileSizeViewID = "fileSizeView";
	
	var getOneCompany = "getOneCompany";
	var Company_DetailURL = "company!getCompanyDetailJson";
	$.get(Company_DetailURL, {
		"orderby" : "runNum"
	}, function(res) {
		console.log(res);
		var xAxis = new Array(res.length);
		var yAxis = new Array(res.length);
		
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].company_name;
			yAxis[i] = res[i].runNum;
			
		}
		var myChart = echarts.init(document.getElementById(runNumViewID));
		var opt = makeOptionScrollUnit(xAxis, yAxis, "运行次数", "bar", 0, 10);
		myChart.setOption(opt);
	});
	
	$.get(Company_DetailURL, {
		"orderby" : "size"
	}, function(res) {
		console.log(res);
		var xAxis = new Array(res.length);
		var yAxis = new Array(res.length);
		
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].company_name;
			yAxis[i] = (parseFloat(res[i].size / (1024 * 1024 * 1024)).toFixed(2));
		}
		var myChart = echarts.init(document.getElementById(fileSizeViewID));
		var opt = makeOptionScrollUnit(xAxis, yAxis, "数据大小(GB)", "bar", 0, 10);
		myChart.setOption(opt);
	});
	$.get(Company_DetailURL, {
		"orderby" : "fileNum"
	}, function(res) {
		console.log(res);
		var xAxis = new Array(res.length);
		var yAxis = new Array(res.length);
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].company_name;
			yAxis[i] = res[i].fileNum;
		}
		var myChart = echarts.init(document.getElementById(fileNumViewID));
		var opt = makeOptionScrollUnit(xAxis, yAxis, "数据量", "bar", 0, 10);
		myChart.setOption(opt);
	});
	
	jQuery(function($) {
		var oTable1 = $('#hospitalList').dataTable({
			"aoColumns" : [ null, null, null, null, null, null, null, null ],
			iDisplayLength : 100
		});
	})

	function getCompanyDetail(id, name) {
		$("#_oneHospital").html(name);
		$("#secondTitle").removeClass("hide");
		$.get("company!" + getOneCompany, {
			"company.company_id" : id
		}, function(responseText) {
			$("#dataDiv").html(responseText);
		})
	}
</script>