<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div>
	<div class="m-file">
		文件名称：
		<span class="file-name">
			<span id="translateDataKey"></span>(<s:property value="resultMap.fileName"/>)
		</span>
	</div>
	<s:if test="%{resultMap.result.equals('')}">
		<div class="m-box">
			<div class="m-boxCon result">
				对不起，程序运行尚未生成可展示的结果，请稍后查询
			</div>
		</div>
	</s:if>
	<s:else>
		<div class="m-box">
			<h2><i class="i-report1"></i>输入序列</h2>
			<div class="m-boxCon">
				<s:property value="resultMap.source" escape="false"/>
			</div>
		</div>
		<div class="m-box m-box-yc">
			<h2><i class="i-edit"></i>输出序列</h2>
			<div class="m-boxCon result">
				<s:property value="resultMap.result" escape="false"/>            
			</div>
		</div>
		<br/>
	</s:else>
</div>