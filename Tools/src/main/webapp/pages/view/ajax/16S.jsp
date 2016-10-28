<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!--报告图示一-->
<div class="row">
	<div class="m-file">
		文件名称：
		<span class="file-name">
			<s:property value="resultMap.datakey"/>(<s:property value="resultMap.fileName"/>)
		</span>
	</div>
	<div class="m-box">
		<h2><i class="i-report1"></i>数据统计</h2>
		<div class="m-boxCon">
			<s:property value="resultMap.table" escape="false"/>
		</div>
	</div>
</div>