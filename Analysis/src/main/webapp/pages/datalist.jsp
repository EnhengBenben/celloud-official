<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<li class="active"><a onclick="getUserDataList()">总用户数据量</a></li>
	</ul>
	<!-- .breadcrumb -->
</div>

<div class="page-content">
	<div class="row">
		<!-- <h3 class="header smaller lighter blue">
			<input type="hidden" id="hideUserId">
		</h3>
		 -->
		<h3 class="header smaller lighter green">数据量统计</h3>
		<div class="col-xs-12" style="height: 450px;" id="userFileSize"></div>
		<h3 class="header smaller lighter green">文件数量统计</h3>
		<div class="col-xs-12" style="height: 450px;" id="userFileNum"></div>
		<div style="htight: 10px"></div>
		<div class="col-xs-11" style="margin-left: 60px; margin-top: 15px">
			<div class="table-header hide" id="_companyName"></div>
			<c:if test="${list!=null && fn:length(list) > 0 }">
				<div class="table-responsive" id="dataDiv">
					<table id="allUserDataList" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>用户名</th>
								<th>所在医院</th>
								<th class="hidden-480">数据量(个)</th>
								<th class="hidden-480">数据大小(GB)</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="data">
								<tr>
									<td>
										${data.username }
										<!--<a href="javascript:userMonthDataList('${data.user_id }','${data.username }','${data.company_name }');">${data.username }</a>-->
									</td>
									<td>${data.company_name }</td>
									<td>${data.fileNum }</td>
									<td>
										<fmt:formatNumber value="${data.size/(1024*1024*1024)}" pattern="#00.0#" />
									</td>
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
<script type="text/javascript" src="./js/datalist.js">
	
</script>

