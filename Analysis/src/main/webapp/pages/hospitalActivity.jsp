<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
<!--
-->
.searchKeyPadding {
	padding-left: 5px, padding-right:5px, float:left
}
</style>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-bar-chart"></i>
			<a href="#">活跃度统计</a>
		</li>
		<li class="active">医院活跃度统计</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter green">
						<i class="icon-signal"></i>
						月新增医院统计
					</h3>
					<div class="col-sm-12" style="height: 350px;" id="newHospitalEvyMonth"></div>
					<!-- .breadcrumb -->
				</div>
				<div class="col-sm-12">
					<div class="row">
						<!-- 用户文件数量大小统计 -->
						<h3 class="header smaller lighter green title">医院文件统计</h3>
						<div style="padding-left: 65px">
							<label>开始时间： </label>
							<input id="fileTimeId" type="date" onchange="fileOnChange()" name="startDate">
							<label>结束时间： </label>
							<input id="fileTimeId2" type="date" onchange="fileOnChange()" name="endDate">
							<select id="fileGroupId" onChange="fileOnChange()">
								<option value="month">月统计</option>
								<option value="week" selected="selected">周统计</option>
							</select>
							<label class="searchKeyPadding">选择医院:</label>
							<select id="fileHospId" class="searchKeyPadding" style="width: 120px" onchange="fileOnChange()">
								<option value="-1">==请选择=＝</option>
								<s:if test="%{complist.size()>0}">
									<s:iterator id="item" value="complist">
										<option value="${item.company_id}">${item.company_name}</option>
									</s:iterator>
								</s:if>
							</select>
							<label class="searchKedivyPadding">Top N:</label>
							<select id="fileTopId" class="searchKeyPadding" style="width: 120px" onchange="fileOnChange()">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3" selected>3</option>
								<option value="4">4</option>
								<option value="5">5</option>
							</select>
							<!-- 
							<label class="searchKeyPadding">排序方式:</label> <select
								id="fileOrderId" class="searchKeyPadding" style="width: 120px"
								onchange="fileOnChange()">
								<option value="1">文件数量</option>
								<option value="2">数据大小</option>
							</select>
							 -->
						</div>
						<div class="col-xs-12 table-responsive" id="userFileListDiv" style="height: 450px"></div>

						<!-- 登陆次数统计 -->
						<div style="margin-left: 65px; margin-top: 10px">
							<h3 class="header smaller lighter green title">医院登陆统计</h3>

							<label>开始时间： </label>
							<input id="loginTimeId" type="date" onchange="loginOnChange()" name="startDate">
							<label>结束时间： </label>
							<input id="loginTimeId2" type="date" onchange="loginOnChange()" name="endDate">
							<select id="loginGroupId" onChange="loginOnChange()" class="searchKeyPadding">
								<option value="month">月统计</option>
								<option value="week" selected="selected">周统计</option>
							</select>
							<label class="searchKeyPadding">选择医院</label>
							<select id="loginHospId" class="searchKeyPadding" style="width: 120px" onchange="loginOnChange()">
								<option value="-1">==请选择=＝</option>
								<s:if test="%{complist.size()>0}">
									<s:iterator id="item" value="complist">
										<option value="${item.company_id}">${item.company_name}</option>
									</s:iterator>
								</s:if>
							</select>
							<label class="searchKeyPadding">Top N:</label>
							<select id="loginTopId" class="searchKeyPadding" style="width: 120px" onchange="loginOnChange()">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3" selected>3</option>
								<option value="4">4</option>
								<option value="5">5</option>
							</select>
						</div>
						<div class="col-xs-12 table-responsive" id="loginListDiv" style="height: 450px"></div>

						<!-- App统计 -->
						<div style="margin-left: 65px">
							<h3 class="header smaller lighter green title">App统计</h3>

							<label>开始时间： </label>
							<input id="appTimeId" type="date" onchange="appOnChange()" name="startDate">
							<label>结束时间： </label>
							<input id="appTimeId2" type="date" onchange="appOnChange()" name="endDate">
							<select id="appGroupId" onChange="appOnChange()">
								<option value="month">月统计</option>
								<option value="week" selected="selected">周统计</option>
							</select>
							<label>选择医院</label>
							<select id="appHospId" style="width: 120px" onchange="appOnChange()">
								<option value="-1">==请选择=＝</option>
								<s:if test="%{complist.size()>0}">
									<s:iterator id="item" value="complist">
										<option value="${item.company_id}">${item.company_name}</option>
									</s:iterator>
								</s:if>
							</select>
							<label>Top N:</label>
							<select id="appTopId" style="width: 120px" onchange="appOnChange()">
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3" selected>3</option>
								<option value="4">4</option>
								<option value="5">5</option>
							</select>
						</div>
						<div class="col-xs-12 table-responsive" id="appListDiv" style="height: 450px"></div>
					</div>
					<div class="space-6"></div>
				</div>
			</div>
			<!-- /row -->
			<div class="hr hr32 hr-dotted"></div>
			<div class="row"></div>
			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
<script src="./js/hospitalActivity.js" type="text/javascript"></script>