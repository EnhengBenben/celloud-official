<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-hospital"></i>
			<a href="#">医院统计</a> 
		</li>
		<li class="active" onclick="toCompanyReportList()">
			医院报告统计
		</li>
	</ul>
</div>
<div class="page-content" >
	<div class="row col-xs-12">
		<c:if test="${appList!=null && fn:length(appList)>0 }">
			<div class="table-responsive" id="dataDiv" style="overflow:auto;">
				<table id="allUserDataList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-50">编码</th>
							<th class="min-w-200">医院名称</th>
							<c:forEach items="${appList}" var="item">
								<th class="min-w-170" style="word-wrap:break-word;">${item.appName}</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dataList}" var="item">
							<tr>
								<td>${item.companyId }</td>
								<td>${item.companyName }</td>
								<c:forEach items="${appList}" var="app">
								    <c:set var="key" value="appId${app.appId }" />
									<td>${empty item[key]?0:item[key] }</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
	</div>
</div>
<script>
	jQuery(function($) {
		$('#allUserDataList').dataTable({
			"oLanguage": {
		        "sSearch": "搜索",
		        "sLengthMenu": "每页显示 _MENU_ 条记录",
		        "sZeroRecords": "Nothing found - 没有记录",
		        "sInfo": "显示第  _START_ 条到第  _END_ 条记录,共  _TOTAL_ 条记录",
		           "sInfoEmpty": "没有数据",   
		        "sProcessing": "正在加载数据...",
		        "oPaginate": {
		           "sFirst": "首页",
		           "sPrevious": "上一页",
		           "sNext": "下一页",
		           "sLast": "尾页"
		           },
		        },
			iDisplayLength : 10,
			"aaSorting" : [ [ 2, "desc" ] ],
		});
	});
</script>