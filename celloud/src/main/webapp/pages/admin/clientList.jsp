<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="table table-bordered">
	<thead>
		<tr>
			<th>
				ID
			</th>
			<th>
				版本
			</th>
			<th>
				名称
			</th>
			<th>
				时间
			</th>
		</tr>
	</thead>
	<tbody>
		<s:if test="%{list.size()>0}">
			<s:iterator id="notice" value="list" status="st">
				<tr>
					<td><s:property value="#notice.id" /></td>
					<td><s:property value="#notice.version" /></td>
					<td><s:property value="#notice.name" /></td>
					<td><s:property value="#notice.createDate" /></td>
				</tr>
			</s:iterator>
		</s:if>
		<s:else>
			<tr><td colspan="4">对不起，没有数据</td></tr>
		</s:else>
	</tbody>
</table>