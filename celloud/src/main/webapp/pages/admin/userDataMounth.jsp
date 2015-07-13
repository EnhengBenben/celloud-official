<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row-fluid">
	<div class="span12">
		<h4 style="float:left">用户真实上传数据信息</h4>
		<a style="float:right" href="javascript:void(0)" onclick="toBack(1)" class="btn btn-primary">返回</a>
		<table class="table table-bordered table-striped" style="margin-bottom: 1px">
			<thead>
				<tr>
					<th>
						数据id
					</th>
					<th>
						名称
					</th>
					<th>
						大小（B）
					</th>
					<th>
						上传时间
					</th>
				</tr>
			</thead>
			<tbody>
				<s:if test="%{list.size()>0}">
					<s:iterator id="map" value="list" status="st">
						<tr>
							<td><s:property value="#map.fileId" /></td>
							<td><s:property value="#map.fileName" /></td>
							<td><s:property value="#map.totalSize" /></td>
							<td><s:property value="#map.create_date" /></td>
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