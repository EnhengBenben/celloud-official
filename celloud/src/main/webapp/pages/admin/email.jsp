<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form class="form-horizontal" id="emailForm">
	<div class="form-group">
		<label class="col-sm-2 control-label"><h3>新增软件</h3></label>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label"></label>
		<div class="col-sm-8">
			<a class="btn" onclick="selectAll();">全选</a>
			<a class="btn" onclick="selectNone();">全不选</a>
			<a class="btn" onclick="selectOthers();">反选</a>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">用户列表</label>
		<div class="col-sm-8" id="emailList">
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">文档介绍</label>
		<div class="col-sm-8">
			<textarea name="context" id="emailContext"></textarea>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
		    <a href="javascript:void(0);" class="btn btn-primary" onclick="javascript:sendEmail();">发送</a>
	    </div>
	</div>
</form>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
	initEmail();
});
editor = new UE.ui.Editor({initialFrameWidth:900,initialFrameHeight:300});
editor.render("emailContext");
</script>