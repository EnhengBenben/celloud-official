<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
		<li class="active">App活跃度统计</li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="col-sm-12">
	<div class="row">
		<h3 class="header smaller lighter green">
			<i class="icon-signal"></i>
			App活跃度统计
		</h3>

		<!-- App统计 -->
		<div style="margin: 8px">
			<div class="form">
				<div style="padding-left: 65px">
					<a style="font-size: 16px; cursor: pointer;">本周</a>
					&nbsp;&nbsp;
					<a style="font-size: 16px; cursor: pointer;">本月</a>
					&nbsp;&nbsp; &nbsp;&nbsp;
					<label>开始时间： </label>
					<input id="timeId" type="date" name="startDate" onchange="onChange()">
					<label>结束时间： </label>
					<input id="timeId2" type="date" name="endDate" onchange="onChange()">
					<label class="searchKedivyPadding">Top N:</label>
					&nbsp;&nbsp;
					<select id="topId" class="searchKeyPadding" style="width: 120px" onchange="onChange()">
						<option value="0" selected></option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
				</div>
			</div>
		</div>
		<div class="col-xs-12 table-responsive" id="appListDiv" style="height: 450px"></div>
	</div>
	<div class="space-6"></div>
</div>
<script src="./js/appActivity.js"></script>
