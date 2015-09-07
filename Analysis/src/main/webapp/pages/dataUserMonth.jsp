<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table id="userMonthDataList" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>上传月份</th>
			<th>数据量(个)</th>
		</tr>
	</thead>

	<tbody>
		<s:if test="%{list.size()>0}">
			<s:iterator id="data" value="list">
				<tr>
					<td><a href="javascript:monthDataList(${data.user_id },'${data.month }');">${data.month }</a></td>
					<td>${data.num }</td>
				</tr>
			</s:iterator>
		</s:if>
	</tbody>
</table>
<script type="text/javascript">
	jQuery(function($) {
		var oTable1 = $('#userMonthDataList').dataTable( {
		"aoColumns": [
	      null,null
		],
		iDisplayLength: 100
		} );
	})
	function monthDataList(userId,month){
		$("#thirdTitle").removeClass("hide");
		$("#_month").html(month);
		$.get("data!getUserMonthDetail",{"userId":userId,"month":month},function(responseText){
			$("#dataDiv").html(responseText);
		})
	}
</script>