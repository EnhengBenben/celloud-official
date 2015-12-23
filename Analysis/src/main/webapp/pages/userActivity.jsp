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
		<div class="title">
			<h3 class="header smaller lighter green">上传文件统计</h3>
		</div>
		<div class="form">
			<label>开始时间： </label>
			<input id="fileTimeId" type="date" onchange="fileOnChange()" name="startDate">
			<label>结束时间： </label>
			<input id="fileTimeId2" type="date" onchange="fileOnChange()" name="endDate">
			<select id="fileGoupId" onChange="fileOnChange()" style="margin-left: 8px">
				<option value="month">月统计</option>
				<option value="week" selected="selected">周统计</option>
			</select>
			<label style="margin-left: 8px;">选择用户:</label>
			<select id="fileSelectId" style="width: 120px" class="searchKeyPadding" onchange="fileOnChange()">
				<option value="-1">==请选择=＝</option>
				<s:if test="%{userList.size()>0}">
					<s:iterator id="item" value="userList">
						<option value="${item.userId}">${item.username}</option>
					</s:iterator>
				</s:if>
			</select>
			<label class="searchKeyPadding">Top N:</label>
			<select id="fileTopId" style="width: 120px；" onchange="fileOnChange()">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3" selected>3</option>
				<option value="4">4</option>
				<option value="5">5</option>
			</select>
			<label class="searchKeyPadding">排序方式:</label>
			<select id="orderType" style="width: 120px" class="searchKeyPadding" onchange="fileOnChange()">
				<option value="1">文件数量</option>
				<option value="2">数据大小</option>
			</select>
		</div>
		<div class="col-xs-12 table-responsive" id="userFileListDiv" style="height: 450px"></div>


		<!-- 登陆次数统计 -->
		<div>
			<h3 class="header smaller lighter green title">登陆统计</h3>
			<div class="form">
				<label>开始时间： </label>
				<input id="loginTimeId" type="date" onchange="loginOnChange()" name="startDate">
				<label>结束时间： </label>
				<input id="loginTimeId2" type="date" onchange="loginOnChange()" name="endDate">
				<select id="loginGoupId" onChange="loginOnChange()" >
					<option value="month">月统计</option>
					<option value="week" selected="selected">周统计</option>
				</select>
				<label class="float:left">选择用户:</label>
				<select id="loginSelectId" style="width: 120px;" onchange="loginOnChange()">
					<option value="-1">==请选择=＝</option>
					<s:if test="%{userList.size()>0}">
						<s:iterator id="item" value="userList">
							<option value="${item.userId}">${item.username}</option>
						</s:iterator>
					</s:if>
				</select>
				<label >Top N:</label>
				<select id="loginTopId" style="width: 120px; " onchange="loginOnChange()">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3" selected>3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select>
			</div>
		</div>
		<div class="col-xs-12 table-responsive" id="loginListDiv" style="height: 450px"></div>

		<!-- App统计 -->
		<div>
			<div class="title">
				<h3 class="header smaller lighter green">APP运行统计</h3>
			</div>
			<div class="form">
				<label>开始时间： </label>
				<input id="appTimeId" type="date" onchange="appOnChange()" name="startDate">
				<label>结束时间： </label>
				<input id="appTimeId2" type="date" onchange="appOnChange()" name="endDate">
				<select id="appGoupId" onChange="appOnChange()" style="width: 120px; float: left">
					<option value="month">月统计</option>
					<option value="week" selected="selected">周统计</option>
				</select>
				<label style="float: left">选择用户</label>
				<select id="appSelectId" style="width: 120px;" onchange="appOnChange()">
					<option value="-1">==请选择=＝</option>
					<s:if test="%{userList.size()>0}">
						<s:iterator id="item" value="userList">
							<option value="${item.userId}">${item.username}</option>
						</s:iterator>
					</s:if>
				</select>
				<label >Top N:</label>
				<select id="appTopId" style="width: 120px; " onchange="appOnChange()">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3" selected>3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select>
			</div>
		</div>
		<div class="col-xs-12 table-responsive" id="appListDiv" style="height: 450px"></div>
	</div>
</div>
<div class="space-6"></div>
<script src="/Analysis/js/userActivity.js" type="text/javascript"></script>