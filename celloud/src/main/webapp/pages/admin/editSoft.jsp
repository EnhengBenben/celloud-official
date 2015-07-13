<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form class="form-horizontal" id="softEditForm">
	<input type="hidden" id="softwareIdHidden" name="soft.softwareId">
	<div class="control-group">
		<label class="control-label">软件名称</label>
		<div class="controls">
			<input type="text" name="soft.softwareName" id="softwareName" class="editSoftware" value="${soft.softwareName}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">软件入口</label>
		<div class="controls">
			<input type="text" name="soft.softwareEntry" id="softwareEntry" class="editSoftware" value="${soft.softwareEntry}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">进程名</label>
		<div class="controls">
			<input type="text" name="soft.processName" id="processName" class="editSoftware" value="${soft.processName}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">软件部署地址</label>
		<div class="controls">
			<input type="text" name="soft.host" id="host" class="editSoftware" value="${soft.host}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">软件图片名称</label>
		<div class="controls">
			<div>${soft.pictureName}</div>
			<input type="file" name="uploadImage" id="uploadSoftImg" onchange="uploadImg(this,0)"/>
			<input type="hidden" name="soft.pictureName" id="pictureName" class="editSoftware" value="${soft.pictureName}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">软件分类</label>
		<div class="controls">
			<input type="hidden" id="classifyIdHidden" name="soft.classifyId" value="${soft.classifyId}"/>
			<select name="soft.classifyId" id="classifyId" class="editSoftware">
				<option value="0">全部应用</option>
				<option value="1">引物设计</option>
				<option value="2">序列处理</option>
				<option value="3">对比和画树</option>
				<option value="4">数据统计</option>
				<option value="5">图形展示</option>
				<option value="6">热点应用</option>
				<option value="100">数据库系统</option>
			</select>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">软件描述</label>
		<div class="controls">
			<input type="text" name="soft.description" id="description" class="editSoftware" value="${soft.description}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">软件截图</label>
		<div class="controls" id="divScreenShot">
			<div class="fileName" style="display: none">${soft.screenshot}</div>
			<div>${soft.screenshot}</div>
			<input type="file" name="uploadImage" id="uploadScreenshot" onchange="uploadImg(this,1)" style="width:150px"/>
			<a style="cursor: pointer;"  onclick="addFile()"><img src="<%=request.getContextPath()%>/images/admin/add.png"></a></a>
			<input type="hidden" id="screenshot" name="soft.screenshot" class="editSoftware"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">应用类型标志位</label>
		<div class="controls">
			<input type="hidden" name="soft.type" id="softwareTypeHidden" value="${soft.type}">
			<select name="soft.type" class="editSoftware" id="softwareType">
				<option value="3">B/S软件</option>
				<option value="1">C/S软件</option>
				<option value="2">数据库</option>
			</select>
			<span class="help-inline"></span>
		</div>
	</div>
</form>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(function(){
	initEditSoft();
});
</script>