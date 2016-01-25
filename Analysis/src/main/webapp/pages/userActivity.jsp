<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style>
<!--
-->

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
		<!-- 用户文件数量大小统计 -->
		<div class="form">
			<div>
				<a style="font-size: 16px; cursor: pointer;" href="javascript:localWeek()" >本周</a>
				&nbsp;&nbsp;
				<a style="font-size: 16px; cursor: pointer;" href="javascript:localMonth()">本月</a>
				&nbsp;&nbsp; &nbsp;&nbsp;
				<label>开始时间： </label>
				<input id="timeId" type="date" name="startDate" onchange="loadActivity()">
				<label>结束时间： </label>
				<input id="timeId2" type="date" name="endDate" onchange="loadActivity()">
				<label class="searchKedivyPadding">Top N:</label>
				&nbsp;&nbsp;
				<select id="topId" style="width: 120px" onchange="loadActivity()">
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
			<h3 class="header smaller lighter green">数据个数统计</h3>
		</div>
		<div id="fileNum" style="height: 450px"></div>
		<!-- 数据大小数统计 -->
		<div class="title">
            <h3 class="header smaller lighter green">数据大小统计</h3>
        </div>
		<div id="fileSize" style="height: 450px"></div>
		<!-- AppAPP运行统计 -->
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