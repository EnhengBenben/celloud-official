<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row-fluid">
	<div class="span12">
		<h4>按照月份统计</h4>
		<table class="table table-bordered table-striped" style="margin-bottom: 1px">
			<thead>
				<tr>
					<th>
						月份
					</th>
					<th>
						数据个数
					</th>
				</tr>
			</thead>
			<tbody>
				<s:if test="%{list.size()>0}">
					<s:iterator id="map" value="list" status="st">
						<tr>
							<td><s:property value="#map.mounth" /></td>
							<td><s:property value="#map.num" /></td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr><td colspan="4">对不起，没有数据</td></tr>
				</s:else>
			</tbody>
		</table>
	</div>
</div>