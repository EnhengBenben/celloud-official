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
		<li class="active"><a onclick = "hospitalBigUserCount()" >大客户统计</a></li>
	</ul>
</div>
<div class="page-content">
	<div class="row">
		<!-- 
		<h3 class="header smaller lighter blue">
			<span onclick="bigUserCount()">大客户信息统计</span>
		</h3>
		 -->
		<div style="htight: 10px"></div>
		<div class="col-xs-11" style="margin-left: 60px; margin-top: 15px">
			<div class="table-header hide" id="_companyName"></div>
			<div class="table-responsive" id="dataDiv">
				<table id="allUserDataList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>大客户编号</th>
							<th class="min-w-80">大客户名称</th>
							<th class="hidden-480">入驻时间</th>
							<th class="hidden-480">数据量(个)</th>
							<th class="hidden-480">数据大小(GB)</th>
							<th class="hidden-480">APP运行次数</th>
							<th class="min-w-80">用户数量</th>
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
									<td>${item.fileNum }</td>
									<td>
										<fmt:formatNumber value="${item.size/(1024*1024*1024)}" pattern="#00.0#" />
									</td>
									<td>${item.runNum}</td>
									<td>${item.userNum}</td>
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
<script type="text/javascript" >
var oTable1 = $('#allUserDataList').dataTable({
    "aoColumns" : [  {
        "bSortable" : false
    }, {
        "bSortable" : false
    }, null, null,null,null,null ],
    iDisplayLength : 10,
    "aaSorting":[[3,"desc"]],
});
</script>