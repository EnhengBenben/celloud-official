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
			<i class="icon-hospital"></i>
			<a href="#">医院统计</a>
		</li>
		<li class="active" onclick="toHospitalList()">医院数据量统计</li>
	</ul>
</div>
<div class="page-content">
	<div class="row">
		<div class="table-header hide" id="_companyName"></div>
		<div class="title">
			<h3 class="header smaller lighter green title">数据个数统计</h3>
		</div>
		<div class="col-xs-12" style="height: 350px;" id="fileNumView"></div>
		<c:if test="${cmpList!=null&&fn:length(cmpList) > 0}">
			<div class="col-xs-11 table-responsive table-div">
				<table id="hospitalList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-90">医院名称</th>
							<th class="min-w-90">医院地址</th>
							<th class="min-w-90">医院电话</th>
							<th class="min-w-80">入驻时间</th>
							<th class="min-w-80">数据个数</th>
							<th class="min-w-80">数据大小</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cmpList }" var="data">
							<tr>
								<td>
									<!--  
									<a href="javascript:getCompanyDetail(${data.company_id },'${data.company_name }')">${data.company_name }</a>
									-->
									${data.company_name }
								</td>
								<td>${data.address }</td>
								<td>${data.tel }</td>
								<td>
									<fmt:formatDate type="both" value="${data.create_date }" pattern="yyyy-MM-dd" />
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
		</c:if>
		<!-- PAGE CONTENT ENDS -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
<script type="text/javascript">
	var runNumViewID = "runNumView";
	var fileNumViewID = "fileNumView";
	//var fileSizeViewID = "fileSizeView";
	
	var getOneCompany = "company!getOneCompany";
	var Company_DetailURL = "company!getCompanyDetailJson";
	/* 	
	$.get(Company_DetailURL, {
		"orderby" : "runNum"
	}, function(res) {
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
			yAxis[i] = parseFloat((res[i].size / (1024 * 1024 * 1024)).toFixed(2));
		}
		var myChart = echarts.init(document.getElementById(fileSizeViewID));
		var opt = makeOptionScrollUnit(xAxis, yAxis, "数据大小(GB)", "bar", 0, 10);
		myChart.setOption(opt);
	}); 

	function getCompanyDetail(id, name) {
	    $("#_oneHospital").html(name);
	    $("#secondTitle").removeClass("hide");
	    $.get(getOneCompany, {
	        "company.company_id" : id
	    }, function(responseText) {
	        $("#content").html(responseText);
	    })
	}
	 */
	$.get(Company_DetailURL, {
		"orderby" : "fileNum"
	}, function(res) {
		var xAxis = new Array(res.length);
		var yAxis = new Array(res.length);
		for (var i = 0; i < res.length; i++) {
			xAxis[i] = res[i].company_name;
			yAxis[i] = res[i].fileNum;
		}
		var myChart = echarts.init(document.getElementById(fileNumViewID));
		var opt = makeOptionScrollUnit(xAxis, yAxis, "数据个数", "bar", 0, 20);
		myChart.setOption(opt);
	});
	
	jQuery(function($) {
		var oTable1 = $('#hospitalList').dataTable({
			"aoColumns" : [ {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, {
				"bSortable" : false
			}, null, null, {
				"sType" : "filesize"
			} ],//列支持排序方式
			"aaSorting" : [ [ 4, "desc" ] ],//默认列排序
			iDisplayLength : 10
		});
	})
</script>