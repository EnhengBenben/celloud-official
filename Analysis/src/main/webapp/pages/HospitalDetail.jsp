<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><div class="breadcrumbs" id="breadcrumbs">
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
		<li class="active">医院详细信息</li>
	</ul>
	<!-- .breadcrumb -->
</div>

<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span onclick="getMonthDataList()">医院详细信息</span>
				<small id="secondTitle" class="hide">
					<i class="icon-double-angle-right"></i>
					<span id="_month"></span>
					信息
				</small>
			</h3>
			<!-- 
				<div class="table-header hide" id="_companyName"></div>
			 -->
			<c:if test="${list!=null && fn:length(list) > 0 }">
				<div class="table-responsive" id="dataDiv">
					<table id="allUserDataList" class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th>用户名</th>
								<th>所在医院</th>
								<th class="hidden-480">数据个数(个)</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${list }" var="data">
								<tr>
									<td>
										<a href="javascript:userMonthDataList(${data.userId },'${data.userName }','${data.companyName }');">${data.userName }</a>
									</td>
									<td>${data.companyName }</td>
									<td>${data.num }</td>
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