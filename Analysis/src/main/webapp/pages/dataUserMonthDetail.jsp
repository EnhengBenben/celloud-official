<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table id="monthDataDetail" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>数据名称</th>
			<th>数据大小(B)</th>
			<th>上传时间</th>
		</tr>
	</thead>
	<tbody>
		<s:if test="%{list.size()>0}">
			<s:iterator id="data" value="list">
				<tr>
					<td>${data.file_name }</td>
					<td class="min-w-200">${data.size }</td>
					<td class="min-w-200">${data.create_date }</td>
				</tr>
			</s:iterator>
		</s:if>
	</tbody>
</table>
<script type="text/javascript">
	jQuery(function($) {
		var oTable1 = $('#monthDataDetail').dataTable( {
		"aoColumns": [
	      null,null,null
		] } );
	})
</script>