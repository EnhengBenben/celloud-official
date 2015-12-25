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
	<!-- .breadcrumb -->
</div>

<div class="page-content">
	<div class="row">
		<h3 class="header smaller lighter blue">
			<span onclick="bigUserCount()">大客户信息统计</span>
		</h3>
		<div style="htight: 10px"></div>
		<div class="col-xs-11" style="margin-left: 60px; margin-top: 15px">
			<div class="table-header hide" id="_companyName"></div>
			<div class="table-responsive" id="dataDiv">
				<table id="allUserDataList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>大客户编号</th>
							<th>大客户名称</th>
							<th class="hidden-480">数据量(个)</th>
							<th class="hidden-480">数据大小(GB)</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${dataList!=null }">
							<c:forEach items="${dataList}" var="data">
								<tr>
									<td>
										<a href="javascript:bigUesrMonthData('${data.company_id }','${data.company_name}');">${data.company_id }</a>
									</td>
									<td>
										<a href="javascript:bigUesrMonthData('${data.company_id }','${data.company_name }');">${data.company_name }</a>
									</td>
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
			<!-- PAGE CONTENT ENDS -->
		</div>
		<div class="col-xs-12">
			<h3 class="header smaller lighter green">用户数据量统计</h3>
		</div>
		<div class="col-xs-12" style="height: 450px;" id="fileSize"></div>
		<div class="col-xs-12">
			<h3 class="header smaller lighter green">用户文件数量统计</h3>
		</div>
		<div class="col-xs-12" style="height: 450px;" id="fileNum"></div>
		<div class="col-xs-12" id="userDataList"></div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
<script type="text/javascript" src="./js/bigUser.js">
	
</script>

