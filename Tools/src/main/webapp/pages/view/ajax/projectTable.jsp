<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="%{resultMap.projectTable.equals('')}">
	<div>
		对不起，程序运行尚未生成可展示的结果，请稍后查询
	</div>
</s:if>
<s:else>
	<s:property value="resultMap.projectTable" escape="false"/>
</s:else>
<style>
font.pass {
	color: #007500;
}
font.now {
	color: #58B100;
}
font.will {
	color: #888;
}
.process span.order {
	display: inline-block;
	padding: 0 5px;
	height: 16px;
	margin: 0;
	-webkit-border-radius: 8px;
	-moz-border-radius: 8px;
	border-radius: 8px;
	color: #fff;
	font-size: 12px;
	line-height: 16px;
}
#tab4 td a> span.pass {
	background-color: #007500;
}
#tab4 td a> span.now {
	background-color: #58B100;
}
#tab4 td a> span.will {
	background-color: #888;
}
#tab4 td a, #tab5 td a{
	color: blue;
}
</style>
<s:if test="resultMap.appId==81">
	<script type="text/javascript">
		$('#pgsTable').sortTable({
			onCol : 1,
			keepRelationships : true
		});
	</script>
</s:if>