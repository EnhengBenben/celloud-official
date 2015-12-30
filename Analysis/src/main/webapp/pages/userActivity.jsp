<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
<!--
-->
.searchKeyPadding {
	padding-left: 5px, padding-right:5px, float:left
}

.title {
	margin-left: 45px;
}

.form {
	margin-left: 65px;
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
		<li class="active">用户活跃度统计</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="col-sm-12">
	<div class="row">
		<h3 class="header smaller lighter green">
			<i class="icon-signal"></i>
			用户活跃度统计
		</h3>
		<!-- 用户文件数量大小统计 -->

		<div class="form">
			<div style="padding-left: 65px">
				<a style="font-size: 16px; cursor: pointer;">本周</a>
				&nbsp;&nbsp;
				<a style="font-size: 16px; cursor: pointer;">本月</a>
				&nbsp;&nbsp; &nbsp;&nbsp;
				<label>开始时间： </label>
				<input id="timeId" type="date" name="startDate" onchange="loadActivity()">
				<label>结束时间： </label>
				<input id="timeId2" type="date" name="endDate" onchange="loadActivity()">
				<label class="searchKedivyPadding">Top N:</label>
				&nbsp;&nbsp;
				<select id="topId" class="searchKeyPadding" style="width: 120px" onchange="loadActivity()">
					<option value="0" selected></option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select>
			</div>
		</div>
		<div class="title">
			<h3 class="header smaller lighter green">上传文件统计</h3>
		</div>
		<div id="fileNum" style="height: 450px"></div>
		<!-- 登陆次数统计 -->
		<div id="fileSize" style="height: 450px"></div>
		<!-- App统计 -->
		<div>
			<div class="title">
				<h3 class="header smaller lighter green">APP运行统计</h3>
			</div>
			<div id="appRun" style="height: 450px"></div>
		</div>
		<div class="space-6"></div>
	</div>
</div>
<script src="/Analysis/js/userActivity.js" type="text/javascript"></script>