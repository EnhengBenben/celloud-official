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
		<li class="active" onclick="toCompanyReportList()">
			医院报告统计
		</li>
	</ul>
</div>
<div class="page-content">
	<div class="row">
		<c:if test="${appList!=null && fn:length(appList)>0 }">
			<div class="table-responsive" id="dataDiv">
				<table id="allUserDataList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-50">编码</th>
							<th class="min-w-200 ">医院名称</th>
							<c:forEach items="${appList}" var="item">
								<th class="min-w-50">${item.app_name}</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cmpList}" var="item">
							<tr>
								<td>${item.company_id }</td>
								<td>${item.company_name }</td>
								<c:forEach items="${appList}" var="item2">
									<c:set var="runNumber" scope="session" value="0" />
									<c:forEach items="${mapList}" var="item3">
										<c:if test="${ item3.app_id == item2.app_id}">
											<c:if test="${ item3.company_id == item.company_id }">
												<c:set var="runNumber" scope="session" value="${item3.runNum}" />
											</c:if>
										</c:if>
									</c:forEach>
									<td>${runNumber }</td>
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