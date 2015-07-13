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
			<a href="#">医院统计</a>
		</li>
	</ul><!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span onclick="getMonthDataList()">医院详细信息</span>
				<small id="secondTitle" class="hide">
					<i class="icon-double-angle-right"></i>
					<span id="_month"></span>用户上传数据量
				</small>
			</h3>
			<div class="table-header hide" id="_companyName">
				
			</div>
			<div class="table-responsive" id="dataDiv">
				<table id="hospitalList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-100">医院名称</th>
							<th>地址</th>
							<th class="min-w-120">电话</th>
							<th class="min-w-100">用户数量</th>
							<th class="min-w-100">数据个数</th>
							<th class="min-w-120">数据大小(GB)</th>
							<th class="min-w-100">报告个数</th>
							<th class="w160">上传时间</th>
						</tr>
					</thead>
				
					<tbody>
						<s:if test="%{complist.size()>0}">
							<s:iterator id="data" value="complist">
								<tr>
									<td><a href="javascript:getCompanyDetail(${data.company_id },'${data.company_name }','${data.address }','${data.tel }','${data.userNum }','${data.fileNum }','${data.fileSize }','${data.reportNum }','${data.create_date }')">${data.company_name }</a></td>
									<td>${data.address }</td>
									<td>${data.tel }</td>
									<td>${data.userNum }</td>
									<td>${data.fileNum }</td>
									<td><fmt:formatNumber pattern="0.00" value="${data.fileSize }"/></td>
									<td>${data.reportNum }</td>
									<td><fmt:formatDate type="both" value="${data.create_date }"/></td>
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
		var oTable1 = $('#hospitalList').dataTable( {
		"aoColumns": [
	      null,null,null,null,null,null,null,null
		] } );
	})
	function getCompanyDetail(id,name,address,tel,userNum,fileNum,fileSize,reportNum,createDate){
		
	}
</script>