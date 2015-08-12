<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try{ace.settings.check('breadcrumbs' , 'fixed');}catch(e){}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-home home-icon"></i>
			<a href="#">用户统计</a>
		</li>
	</ul><!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span onclick="getMonthDataList()">用户详细信息</span>
				<small id="secondTitle" class="hide">
					<i class="icon-double-angle-right"></i>
					<span id="_month"></span>用户上传数据量
				</small>
			</h3>
			<div class="table-header hide" id="_userName">
				
			</div>
			<div class="table-responsive" id="userListDiv">
				<table id="userList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>用户名</th>
							<th>Email</th>
							<th class="min-w-120">电话</th>
							<th class="min-w-120">所属医院</th>
							<th class="min-w-80">所属部门</th>
							<th class="min-w-80">数据个数</th>
							<th class="min-w-110">数据大小(GB)</th>
							<th class="min-w-80">报告个数</th>
							<th class="w160">注册时间</th>
						</tr>
					</thead>
				
					<tbody>
						<s:if test="%{userList.size()>0}">
							<s:iterator id="data" value="userList">
								<tr>
									<td><a href="javascript:void()">${data.username }</a></td>
									<td>${data.email }</td>
									<td>${data.cellphone }</td>
									<td>${data.companyName }</td>
									<td>${data.deptName }</td>
									<td>${data.fileNum }</td>
									<td><fmt:formatNumber pattern="0.00" value="${data.fileSize/ (1024 * 1024 * 1024) }"/></td>
									<td>${data.reportNum }</td>
									<td><fmt:formatDate type="both" value="${data.createDate }"/></td>
								</tr>
							</s:iterator>
						</s:if>
					</tbody>
				</table>
			</div>
		<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->
<script type="text/javascript">
	jQuery(function($) {
		var oTable1 = $('#userList').dataTable( {
		"aoColumns": [
	      null,null,null,null,null,null,null,null,null
		] } );
	})
	function getCompanyDetail(id,name,address,tel,userNum,fileNum,fileSize,reportNum,createDate){
		
	}
</script>