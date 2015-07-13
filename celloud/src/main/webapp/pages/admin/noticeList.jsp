<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="table table-bordered">
	<thead>
		<tr>
			<th>
				公告标题
			</th>
			<th>
				公告内容
			</th>
			<th>
				发布时间
			</th>
			<th>
				操作
			</th>
		</tr>
	</thead>
	<tbody>
		<s:if test="%{list.size()>0}">
			<s:iterator id="notice" value="list" status="st">
				<tr>
					<td><s:property value="#notice.noticeTitle" /></td>
					<td>
						<s:if test="%{#notice.noticeContext.length()>45}">
							<s:property value="#notice.noticeContext.substring(0, 45)" />...							
						</s:if>
						<s:else>
							<s:property value="#notice.noticeContext" />
						</s:else>
					</td>
					<td><s:date name="#notice.createDate" format="yyyy-MM-dd hh:mm:ss" /></td>
					<td><a href="javascript:void(0)" onclick="javascript:editNotice(${ requestScope.notice.noticeId })">编辑</a></td>
				</tr>
			</s:iterator>
		</s:if>
		<s:else>
			<tr><td colspan="4">对不起，没有数据</td></tr>
		</s:else>
	</tbody>
</table>