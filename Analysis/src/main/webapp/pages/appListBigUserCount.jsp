<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try {
			ace.settings.check('breadcrumbs', 'fixed');
		} catch (e) {
		}
	</script>
	<ul class="breadcrumb" >
		<li>
			<i class="icon-cloud"></i>
			<a href="#">APP统计</a>
		</li>
		<li class="active"><a href="javascript:bigUserAppList()">大客户统计</a></li>
		<li class="active"  id="cmpName" style="display:none" ></li>
	</ul>
	<!-- .breadcrumb -->
</div>
<div class="page-content"> 
	<div class="row">
		<div class="col-xs-12" id="cmpId">
			<div class="table-header hide" ></div>
			<div class="col-xs-11 table-responsive ">
				<table id="appList" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="min-w-80">大客户编号</th>
							<th class="min-w-80">大客户名称</th>
							<th class="min-w-80">入驻时间</th>
							<th class="min-w-80">APP数量</th>
						</tr>
					</thead>

					<tbody>
						<c:if test="${appList!=null}">
							<c:forEach var="data" items="${ appList}">
								<tr>
									<td>
										<a href="javascript:getToAppList(${data.company_id },'${data.company_name }')">${data.company_id }</a>
									</td>
									<td>
										<a href="javascript:getToAppList(${data.company_id },'${data.company_name }')">${data.company_name } </a>
									</td>
									<td>
										<fmt:formatDate type="both" value="${data.create_date }" pattern="yyyy-MM-dd" />
									</td>
									<td>${data.runNum }</td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
				<div class="col-sm-12" id="appListDiv"></div>
			</div>
			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->
<script type="text/javascript">
   function getToAppList(id, name) {
		$("#cmpId").val(id);
		$("#cmpName").html(name);
		$("#cmpName").css('display','inline'); 
		$.get("app!getOneBigUserAppList", {
			"user.user_id" : id
		}, function(responseText) {
			$("#cmpId").html(responseText);
		});
	}
   var oTable1 = $('#appList').dataTable({
       "aoColumns" : [{
           "bSortable" : false
       }, {
           "bSortable" : false
       }, null, null ],
       iDisplayLength : 12,
       "aaSorting":[[3,"desc"]],
   });
</script>