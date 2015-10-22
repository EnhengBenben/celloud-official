<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" id="pageRecordNumHidden" value='<s:property value="pageRecordNum"/>'/>
<input type="hidden" id="currentPageRecordNum" value='<s:property value="%{privateDataPageList.datas.size()}" />'>
<table class="table table-tab table-bordered table-collapse">
	<thead>
    	<tr>
        	<th>文件名称</th>
        	<th>数据编号</th>
        	<th>文件别名</th>
        	<th>上传时间</th>
			<th>操作</th>
        </tr>
    </thead>
	<tbody>
		<s:if test="%{privateDataPageList.datas.size()>0}">
			<s:iterator value="privateDataPageList.datas" status="st" id="data">
				<tr>
					<td title="<s:property value="#data.fileName"/>">
						<s:if test="#data.fileName.length()>40">
							<s:property value="#data.fileName.substring(0,40)"/>...
						</s:if>
						<s:else>
							<s:property value="#data.fileName" />
						</s:else>
						<s:if test="%{#data.isRunning>0}">
							<img src="<%=request.getContextPath()%>/images/publicIcon/icon-running.png" title="running" style="position: absolute;margin-top: 1px;"/>
						</s:if>
					</td>
					<td>
						<s:property value="#data.dataKey"/>
					</td>
					<td class="center"><s:property value="#data.anotherName"/></td>
					<td class="center"><s:date name="#data.createDate" format="yyyy/MM/dd" /></td>
					<td class="center">
        				<a href="javascript:deleteData(<s:property value="#data.fileId"/>,'<s:property value="#data.dataKey"/><s:property value="#data.fileName.substring(#data.fileName.indexOf('.'), #data.fileName.length())" />');">删除</a>
        			</td>
				</tr>
			</s:iterator>
		</s:if>
		<s:else>
			<tr><td colspan="6">数据记录为空</td></tr>
		</s:else>
   	</tbody>
	<tfoot>
   	</tfoot>
</table>