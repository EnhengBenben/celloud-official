<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row-fluid">
	<div class="span12">
		<h4></h4>
		<h4 style="float:left">某个用户的使用情况统计</h4>
		<a style="float:right" href="javascript:void(0)" onclick="toBack(0)" class="btn btn-primary">返回</a>
		<table class="table table-bordered table-striped" style="margin-bottom: 1px">
			<thead>
				<tr>
					<th>
						用户
					</th>
					<th>
						所在医院
					</th>
					<th>
						月份
					</th>
					<th>
						数量
					</th>
				</tr>
			</thead>
			<tbody>
				<s:if test="%{list.size()>0}">
					<s:iterator id="map" value="list" status="st">
						<tr>
							<td>
								<a href="javascript:void(0)" onclick="javascript:getUserMounthData(<s:property value="#map.userId" />,'<s:property value="#map.mounth" />')"><s:property value="#map.username" /></a>
							</td>
							<td><s:property value="#map.company" /></td>
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