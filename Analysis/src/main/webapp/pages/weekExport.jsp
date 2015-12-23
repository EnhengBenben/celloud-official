<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<style>
.table {
	margin-left: 40px;
	margin-right: 20px;
	display: none;
}

.btn {
	margin-left: 5px;
	margin-top: 5px;
}

.btn-position {
	margin-left: 36px;
}
</style>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed');
		} catch (e) {
		}
	</script>

	<ul class="breadcrumb">
		<li>
			<i class="icon-user-md"></i>
			<a href="#">导出结果</a>
		</li>
		<li class="active">周统计</li>
	</ul>
</div>
<div class="page-content">
	<div style="background: pink; width: 400px" id="Timer" style="margin-left: auto"></div>
	<label>查看时间： </label>
	<input id="timeId" type="date" onchange="change()" name="startDate">  
	统计时间：<label id="lastWeek"></label>
	&nbsp; &nbsp; ~&nbsp; &nbsp; 
	<label id="endWeek"></label>
	

	<div class="row">
		<div class="col-xs-12">
			<div class="row">
				<h3 class="header smaller lighter blue">
					<span onclick="toWeekReport()"></span>
					<small id="secondTitle" class="hide"> <i class="icon-double-angle-right"></i> <span id="_oneUser"></span>
					</small>
				</h3>
				<div class="row">
					<div class="col-xs-12">
						<h3 class="header smaller lighter green">Top10统计</h3>
						<div class='btn-position'>
							<button class="btn btn-primary" onclick="tableToExcel('loginfileapptop10', '本周TOP10')">
								<i class="icon-save bigger-160"></i>
								保存表格
							</button>
						</div>
						<table class="table table-striped table-bordered table-hover" id="loginfileapptop10">
							<tr>
								<th colspan=" 2" align='center'>前10活跃度用户及登陆次数</th>
								<th colspan=" 2" align='center'>数据量前10 用户及数据量</th>
								<th colspan=" 2"  align='center'>前10App及运行次数</th>
							</tr>
							<tr>
								<th class="min-w-80"  align='center'>用户名</th>
								<th class="min-w-80"  align='center'>登陆次数</th>
								<th class="min-w-80"  align='center'>用户名</th>
								<th class="min-w-80"  align='center'>数据大小(GB)</th>
								<th class="min-w-80"  align='center'>App</th>
								<th class="min-w-80"  align='center'>运行次数</th>
								<c:forEach var="i" begin="0" end="10" step="1">
									<tr>
										<td>${logList[i].userName}</td>
										<td>${logList[i].logNum}</td>
										<td>${dataList[i].userName}</td>
										<td>
											<fmt:formatNumber pattern="0.00" value="${ dataList[i].size/1024/1024/1024}"></fmt:formatNumber>
										</td>
										<td>${softList[i].softwareName}</td>
										<td>${softList[i].runNum}</td>
									</tr>
								</c:forEach>
							</tr>
						</table>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter green">客户端使用情况统计</h3>
					<div class="col-sm-12" style="height: 450px;" id="browserCountVId"></div>
					<div class='btn-position'>
						<button class="btn btn-success" onclick="tableswitch('userBrowserCount')">
							<i class="icon-share-alt bigger-160"></i>
							显示表格
						</button>
						<button class="btn btn-info" onclick="tableToExcel('userBrowserCount', '客户端使用情况统计')">
							<i class="icon-save bigger-160"></i>
							保存表格
						</button>

					</div>
					<table class="table table-striped table-bordered table-hover" id="userBrowserCount">
						<tr>
							<th class="min-w-80">浏览器</th>
							<th class="min-w-80">使用次数</th>
							<c:forEach var="item" items="${browserList }">
								<tr>
									<td>${item.browser}</td>
									<td>${item.logNum}</td>
								</tr>
							</c:forEach>
						</tr>
					</table>
				</div>
			</div>

			<!--  -->
			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter green">新用户活跃度</h3>
					<div id="newUserChartView" class="col-xs-12"></div>
					<div class='btn-position'>
						<button class="btn btn-success" onclick="tableswitch('newUserAcitivityTable')">
							<i class="icon-share-alt bigger-160"></i>
							显示表格
						</button>
						<button class="btn btn-info" onclick="tableToExcel('newUserAcitivityTable', '新用户活跃度')">
							<i class="icon-save bigger-160"></i>
							保存表格
						</button>

					</div>
					<table class="table table-striped table-bordered table-hover" id="newUserAcitivityTable">
						<tr>
							<th>用户名</th>
							<th>登陆次数</th>
						</tr>
						<tr>
							<c:forEach var="item" items="${newUserList}">
								<tr>
									<td>${item.userName}</td>
									<td>${item.logNum}</td>
								</tr>
							</c:forEach>
						</tr>
					</table>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter green">每天登陆统计</h3>
					<div class="col-sm-12" style="height: 450px;" id="loginEachDay"></div>
					<div class='btn-position'>
						<button class="btn btn-success" onclick="tableswitch('loginCount')">
							<i class="icon-share-alt bigger-160"></i>
							显示表格
						</button>
						<button class="btn btn-primary" onclick="tableToExcel('loginCount', '每天登陆次数统计')">
							<i class="icon-save bigger-160"></i>
							保存表格
						</button>

					</div>
					<table class="table table-striped table-bordered table-hover" id="loginCount">
						<tr>
							<th class="min-w-80">日期</th>
							<th class="min-w-80">登陆次数</th>
							<c:forEach var="item" items="${totalLogList }">
								<tr>
									<td>${item.weekDate}</td>
									<td>${item.logNum}</td>
								</tr>
							</c:forEach>
						</tr>
					</table>
				</div>
			</div>

			<div class="row">
				<div class="col-xs-12">
					<h3 class="header smaller lighter green">每天App运行统计</h3>
					<div class="col-sm-12" style="height: 450px;" id="appRunEachDay"></div>
					<div class='btn-position'>
						<button class="btn btn-success" onclick="tableswitch('eachDayAppRun')">
							<i class="icon-share-alt bigger-160"></i>
							显示表格
						</button>
						<button class="btn btn-primary" onclick="tableToExcel('eachDayAppRun', '每天App运行统计')">
							<i class="icon-save bigger-160"></i>
							保存表格
						</button>
						<table class="table table-striped table-bordered table-hover" id="eachDayAppRun">
							<tr>
								<th class="min-w-80">日期</th>
								<th class="min-w-80">App运行次数</th>
								<c:forEach var="item" items="${totalSoftList }">
									<tr>
										<td>${item.weekDate}</td>
										<td>${item.runNum}</td>
									</tr>
								</c:forEach>
							</tr>
						</table>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-12">
						<h3 class="header smaller lighter green">每天上传数据统计</h3>
						<div class="col-sm-12" style="height: 450px;" id="eachDataSize"></div>
						<div class='btn-position'>
							<button class="btn btn-success" onclick="tableswitch('weekDayUploadFile')">
								<i class="icon-share-alt bigger-160"></i>
								显示表格
							</button>
							<button class="btn btn-primary" onclick="tableToExcel('weekDayUploadFile', '每天App运行统计')">
								<i class="icon-save bigger-160"></i>
								保存表格
							</button>

						</div>
						<table class="table table-striped table-bordered table-hover" id="weekDayUploadFile">
							<tr>
								<th class="min-w-80">日期</th>
								<th class="min-w-80">数据个数</th>
								<th class="min-w-80">数据大小(MB)</th>
								<c:forEach var="item" items="${eachDataList }">
									<tr>
										<td>${item.weekDate}</td>
										<td>${item.fileNum}</td>
										<td>${item.size}</td>
									</tr>
								</c:forEach>
							</tr>
						</table>
					</div>
				</div>


				<div class="row">
					<div class="col-xs-12">
						<h3 class="header smaller lighter green">用户登陆统计</h3>
						<div class="col-sm-12" style="height: 450px;" id="eachUserLoginNun"></div>
						<div class='btn-position'>
							<button class="btn btn-success" onclick="tableswitch('userLoginNum')">
								<i class="icon-share-alt bigger-160"></i>
								显示表格
							</button>
							<button class="btn btn-primary" onclick="tableToExcel('userLoginNum', '用户运行App统计')">
								<i class="icon-save bigger-160"></i>
								保存表格
							</button>
						</div>
						<table class="table table-striped table-bordered table-hover" id="userLoginNum">
							<tr>
								<th class="min-w-80">用户名称</th>
								<th class="min-w-80">登陆次数</th>
								<c:forEach var="item" items="${logList }">
									<tr>
										<td>${item.userName}</td>
										<td>${item.logNum}</td>
									</tr>
								</c:forEach>
							</tr>
						</table>
					</div>
				</div>


				<div class="row">
					<div class="col-xs-12">
						<h3 class="header smaller lighter green">用户运行App统计</h3>
						<div class="col-sm-12" style="height: 450px;" id="eachUserRunApp"></div>
						<div class='btn-position'>
							<button class="btn btn-success" onclick="tableswitch('userAppRunCount')">
								<i class="icon-share-alt bigger-160"></i>
								显示表格
							</button>
							<button class="btn btn-primary" onclick="tableToExcel('userAppRunCount', '用户运行App统计')">
								<i class="icon-save bigger-160"></i>
								保存表格
							</button>

						</div>
						<table class="table table-striped table-bordered table-hover" style="display: none" id="userAppRunCount">
							<tr>
								<th class="min-w-80">用户名称</th>
								<th class="min-w-80">运行次数</th>
								<c:forEach var="item" items="${eachAppList }">
									<tr>
										<td>${item.userName}</td>
										<td>${item.runNum}</td>
									</tr>
								</c:forEach>
							</tr>
						</table>
					</div>
				</div>

				<div class="row">
					<div class="col-xs-12">
						<h3 class="header smaller lighter green">用户上传数据统计</h3>
						<div class="col-sm-12" style="height: 450px;" id="fileNumSize"></div>
						<div class='btn-position'>
							<button class="btn btn-success" onclick="tableswitch('userUploadFileId')">
								<i class="icon-share-alt bigger-160"></i>
								显示表格
							</button>
							<button class="btn btn-primary" onclick="tableToExcel('userUploadFileId', '用户上传数据统计')">
								<i class="icon-save bigger-160"></i>
								保存表格
							</button>

						</div>
						<table class="table table-striped table-bordered table-hover" id="userUploadFileId">
							<tr>
								<th class="min-w-80">用户名称</th>
								<th class="min-w-80">数据个数</th>
								<th class="min-w-80">数据大小MB</th>
								<c:forEach var="item" items="${userDataList }">
									<tr>
										<td>${item.userName}</td>
										<td>${item.fileNum}</td>
										<td>
											<fmt:formatNumber pattern="0.00" value="${ item.size/1024/1024/1024}"></fmt:formatNumber>
										</td>
									</tr>
								</c:forEach>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- /.page-content -->
<script type="text/javascript" src="./js/weekExport.js"></script>
<script type="text/javascript" src="./js/tableExport.js"></script>