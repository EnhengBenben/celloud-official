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
				<label>开始时间： </label>
				<input id="appTimeId" type="date" onchange="onChange()" name="startDate">
				<label>结束时间： </label>
				<input id="appTimeId2" type="date" onchange="onChange()" name="endDate">
				<select id="groupTypeApp" onchange="onChange()">
					<option value="month">月统计</option>
					<option value="week" selected="selected">周统计</option>
				</select>
				<label>选择App</label>
				<select id="softList" style="width: 120px" onchange="onChange()">
					<option value="-1">==请选择=＝</option>
					<s:if test="%{appList.size()>0}">
						<s:iterator id="item" value="appList">
							<option value="${item.softwareId}">${item.softwareName}</option>
						</s:iterator>
					</s:if>
				</select>
				<label>Top N:</label>
				<select id="topId" style="width: 120px;" onchange="onChange()">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3" selected>3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select>
			</div>
		</div>
	</div>
	<div class="col-xs-12 table-responsive" id="appListDiv" style="height: 450px"></div>
</div>
<div class="space-6"></div>
<script src="./js/appActivity.js"></script>
