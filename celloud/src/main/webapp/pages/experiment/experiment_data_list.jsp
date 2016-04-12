<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table_" style="text-align: center;">
	<thead>
		<tr>
			<th style="min-width: 60px">请选择</th>
			<th>文件名</th>
			<th style="min-width: 80px">上传时间</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list }" var="data">
			<tr>
				<td>
					<input type="radio" name="expFile" value="${data.fileId }">
					<input type="hidden" value="${data.dataKey }">
				</td>
				<td class="cutline" title="${data.fileName }">${data.fileName }</td>
				<td><fmt:formatDate value="${data.createDate }" pattern="yyyyMMdd" /></td>
			</tr>
		</c:forEach>
		<c:if test="${list.size()==0 }">
			<tr>
				<td colspan="3">未检索到与本实验相匹配的数据</td>
			</tr>
		</c:if>
	</tbody>
</table>
