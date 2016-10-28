<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row">
	<div class="m-file">
		文件名称：
		<span class="file-name">
			<span id="treeDataKey"></span>(<span id="treeFileName"></span>)
		</span>
	</div>
	<div class="m-box">
	  <h2><i class="i-edit"></i>Neighbor-Joining 进化距离图</h2>
	  <div class="m-boxCon">
		<img style="padding-left: 70px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.imgName"/>" height="500px;" width="500px;">
	  </div>
	</div>
</div>
