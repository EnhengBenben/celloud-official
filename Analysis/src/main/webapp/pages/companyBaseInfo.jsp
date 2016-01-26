<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed')
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb">
		<li>
			<i class="icon-hospital"></i>
			<a href="#">医院统计</a>
		</li>
		<li class="active" onclick="toCompanyReportList()">医院基本信息</li>
	</ul>
</div>
<div class="page-content">
	<div class="row">
		<c:if test="${cmpList!=null && fn:length(cmpList)>0 }">
			<div class="table-responsive" id="dataDiv">
				<table id="allUserDataList " class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-50">医院编码</th>
							<th class="min-w-200 ">医院名称</th>
							<th class="min-w-100 ">入驻时间</th>
							<th class="min-w-200 ">所属大客户</th>
							<th class="min-w-200 ">医院地址</th>
							<th class="min-w-50 ">账号数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cmpList}" var="item">
							<tr>
								<td>${item.company_id }</td>
								<td>${item.company_name }</td>
								<td>${item.create_date }</td>
								<td>${item.belowCompany }</td>
								<td>${item.address }</td>
								<td>${item.userNum }</td>
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
		var cells = document.getElementById("allUserDataList").rows.item(0).cells.length;
		var arrLen = new Array(cells);
		$.each(arrLen, function(index, value) {
			arrLen[index] = null;
		})
		arrLen[0] = {
			"bSortable" : false
		};
		arrLen[1] = {
			"bSortable" : false
		};
		var oTable1 = $('#allUserDataList').dataTable({
			"aoColumns" : arrLen,
			iDisplayLength : 10,
			"aaSorting" : [ [ 2, "desc" ] ],
		});
	});
</script>