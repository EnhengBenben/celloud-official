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
            <i class="icon-tasks"></i>
            <a href="#">数据统计</a>
        </li>
        <li class="active">大客户信息汇总</li>
    </ul>
</div>

<div class="page-content">
<div class="col-sm-8">
	<div class="row">
		<div class="col-xs-12">
			<div class="col-xs-12">
				<h3 class="header smaller lighter green">数据量统计</h3>
			</div>
			<div class="col-xs-12" style="height: 450px;" id="fileSize"></div>
			<div class="col-xs-12">
				<h3 class="header smaller lighter green">数大小统计</h3>
			</div>
			<div class="col-xs-12" style="height: 450px;" id="fileNum"></div>
			<div class="table-responsive" id="dataDiv">
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
							<c:forEach items="${dataList}" var="data">
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
</div>
</div><!-- end page-content -->
<script type="text/javascript">
bigUesrMonthData(3, "中山大学达安基因股份有限公司");

function bigUesrMonthData(company_id, company_name) {
    getBigUserDataById(company_id);
    $("#_oneApp").html(company_name);
}
function getBigUserDataById(company_id) {
    var fileSizeId = "fileSize";
    var fileNumId = "fileNum";
    var bigUesrURL = "data!getBigUserMonth";
    var bigUesrTableURL = "data!getBigUserMonthTable";
    var param = {
        "companyId" : company_id
    };
    var type = 'line';
    var optNumName = "'数据大小'";
    var optSizeName = '数据量';
    
    $.get(bigUesrURL, param, function(data) {
        data = data == null ? [] : data;
        var xAxis = new Array(data.length);
        var yAxis = new Array(data.length);
        var fileNum = new Array(data.length);
        for (var i = 0; i < data.length; i++) {
            xAxis[i] = data[i].yearMonth;
            fileNum[i] = data[i].fileNum;
            yAxis[i] = parseFloat(data[i].size / (1024 * 1024 * 1024)).toFixed(2);
        }
        var fileSizeOpt = makeOptionScrollUnit(xAxis, yAxis, optNumName, lineType, 0, 12);
        var fileNumOpt = makeOptionScrollUnit(xAxis, fileNum, optSizeName, barType, 0, 12);
        
        var sizeChart = echarts.init(document.getElementById(fileSizeId));
        var numChart = echarts.init(document.getElementById(fileNumId));
        
        sizeChart.setOption(fileSizeOpt);
        numChart.setOption(fileNumOpt);
    });
}
//    $.get(bigUesrTableURL, param, function(responseText) {
//      $("#userDataList").html(responseText);
 //  });
</script>