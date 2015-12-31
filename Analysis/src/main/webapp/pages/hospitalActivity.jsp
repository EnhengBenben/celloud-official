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
				<div class="col-xs-12 hide">
					<h3 class="header smaller lighter green">
						<i class="icon-signal"></i>
						月新增医院统计
					</h3>
					<div class="col-sm-12" style="height: 450px;" id="newHospitalEvyMonth"></div>
					<!-- .breadcrumb -->
				</div>
				<div class="col-sm-12">
					<div class="row">
						<!-- 用户文件数量大小统计 -->
						
						<div style="padding-left: 65px">
							<a style="font-size: 16px; cursor: pointer;" onclick="javascript:localWeek()">本周</a>
							&nbsp;&nbsp;
							<a style="font-size: 16px; cursor: pointer;" onclick="javascript:localMonth()">本月</a>
							&nbsp;&nbsp; &nbsp;&nbsp;
							<label>开始时间： </label>
							<input id="timeId" type="date" name="startDate" onchange="loadActivityFile()">
							<label>结束时间： </label>
							<input id="timeId2" type="date" name="endDate" onchange="loadActivityFile()">
							<label class="searchKedivyPadding">Top N:</label>
							&nbsp;&nbsp;
							<select id="fileTopId" class="searchKeyPadding" style="width: 120px" onchange="loadActivityFile()">
								<option value="0" selected></option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
							</select>
						</div>
						<h3 class="header smaller lighter green title">医院数据量统计</h3>
						<div class="col-xs-12" id="fileNum" style="height: 450px"></div>
						<h3 class="header smaller lighter green title">数据大小统计</h3>
						<div class="col-xs-12" id="fileSize" style="height: 450px"></div>
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