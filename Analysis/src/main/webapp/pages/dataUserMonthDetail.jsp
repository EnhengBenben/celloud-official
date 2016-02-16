<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%><table id="monthDataDetail" class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th>数据名称</th>
			<th>数据大小(B)</th>
			<th>上传时间</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${list!=null && fn:length(list) > 0 }">
			 <c:forEach items="${list }" var="data">
				<tr>
					<td>${data.file_name }</td>
					<td class="min-w-200">${data.size }</td>
					<td class="min-w-200">${data.create_date }</td>
				</tr>
			</c:forEach>
		</c:if>
	</tbody>
</table>
<script type="text/javascript">
	jQuery(function($) {
		var oTable1 = $('#monthDataDetail').dataTable( {
		"aoColumns": [
	      null,null,null
		],
		iDisplayLength: 100
		} );
	})
</script>