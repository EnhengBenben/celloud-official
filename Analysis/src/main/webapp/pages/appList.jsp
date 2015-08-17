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
			<a href="#">APP统计</a>
		</li>
	</ul><!-- .breadcrumb -->
</div>
<div class="page-content">
	<div class="row">
		<div class="col-xs-12">
			<h3 class="header smaller lighter blue">
				<span onclick="toAPPList()">APP详细信息</span>
				<small id="secondTitle" class="hide">
					<i class="icon-double-angle-right"></i>
					<span id="_oneApp"></span>
				</small>
			</h3>
			<div class="table-header hide" id="_appName">
				
			</div>
			<div class="table-responsive" id="appListDiv">
				<table id="appList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>APP名称</th>
							<th class="min-w-80">运行次数</th>
							<th class="min-w-80">人气指数</th>
							<th class="min-w-80">软件类型</th>
							<th class="min-w-110">最小数据个数</th>
							<th class="min-w-80">数据格式</th>
							<th class="w160">上线时间</th>
							<th class="min-w-110">描述</th>
						</tr>
					</thead>
				
					<tbody>
						<s:if test="%{appList.size()>0}">
							<s:iterator id="data" value="appList">
								<tr>
									<td><a href="javascript:getAppDetail(${data.softwareId },'${data.softwareName }')">${data.softwareName }</a></td>
									<td>${data.runNum }</td>
									<td>${data.bhri }</td>
									<td>${data.type }</td>
									<td>${data.dataNum }</td>
									<td>${data.dataType }</td>
									<td><fmt:formatDate type="both" value="${data.createDate }"/></td>
									<td>${data.description }</td>
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
		var oTable1 = $('#appList').dataTable( {
		"aoColumns": [
	      null,null,null,null,null,null,null,null
		] } );
	})
	function getAppDetail(id,name){
		$("#_oneApp").html(name);
		$("#secondTitle").removeClass("hide");
		$.get("app!getAppById",{"app.softwareId":id},function(responseText){
			$("#appListDiv").html(responseText);
		});
	}
</script>