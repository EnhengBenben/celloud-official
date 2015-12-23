<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>

	<ul class="breadcrumb">
		<li><i class="icon-tasks"></i> <a href="#">数据统计</a></li>
		<li class="active">总用户数据量</li>
	</ul>
	<!-- .breadcrumb -->
</div>

<div class="page-content">
	<div class="row">
		<h3 class="header smaller lighter blue">
			<span onclick="getUserDataList()">总用户数据量</span> <input type="hidden"
				id="hideUserId"> <small id="secondTitle" class="hide">
				<i class="icon-double-angle-right"></i> <a
				href="javascript:getUserMonthData()"><span id="_username"></span>每月数据量</a>
			</small> <small id="thirdTitle" class="hide"> <i
				class="icon-double-angle-right"></i> <span id="_month"></span>明细
			</small>
		</h3>
		<div class="col-xs-12" style="height: 450px;" id="userFileSize"></div>
		<div class="col-xs-12" style="height: 450px;" id="userFileNum"></div>
		<div style="htight: 10px"></div>
		<div class="col-xs-11"  style=" margin-left:60px;margin-top:15px">
			<div class="table-header hide" id="_companyName"></div>
			<div class="table-responsive" id="dataDiv">
				<table id="allUserDataList"
					class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>用户名</th>
							<th>所在医院</th>
							<th class="hidden-480">数据量(个)</th>
							<th class="hidden-480">数据大小(GB)</th>
						</tr>
					</thead>

					<tbody>
						<s:if test="%{list.size()>0}">
							<s:iterator id="data" value="list">
								<tr>
									<td><a
										href="javascript:userMonthDataList('${data.user_id }','${data.username }','${data.company_name }');">${data.username }</a></td>
									<td>${data.company_name }</td>
									<td>${data.num }</td>
									<td><fmt:formatNumber
											value="${data.size/(1024*1024*1024)}" pattern="#00.0#" /></td>
								</tr>
							</s:iterator>
						</s:if>
					</tbody>
				</table>
			</div>
			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
<script type="text/javascript" src="./js/datalist.js"> </script>

