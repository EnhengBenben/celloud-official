<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row" id="noticemain">
	<a style="float:right" href="javascript:void(0)" class="btn btn-primary" onclick="addNotice()">新增</a>
	<div id="noticeList">
		
	</div>
</div>
<div style="display: none;" id="editModal" >
	<div id="edit">
		
	</div>
	<div style="float: right;">
		<a href="javascript:void(0);" id="editSave" class="btn btn-primary">保存</a>
		<a href="javascript:void(0);" id="editCancleNotice" class="btn">取消</a>
	</div>
</div>
<div style="display: none;" id="addNotice">
		<form class="form-horizontal" id="noticeForm">
			<div class="control-group">
				<label class="control-label" for="title">
					公告标题
				</label>
				<div class="controls">
					<input type="text" id="title" name="notice.noticeTitle" placeholder="title">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="content">
					公告内容
				</label>
				<div class="controls">
					<textarea rows="3" id="content" name="notice.noticeContext" placeholder="content"></textarea>
				</div>
			</div>
		</form>
		<div style="float: right;">
		<a href="javascript:void(0);" id="save" class="btn btn-primary">保存</a>
		<a href="javascript:void(0);" id="cancleNotice" class="btn">取消</a>
		</div>
</div>
<script type="text/javascript">
editor = new UE.ui.Editor({initialFrameWidth:900,initialFrameHeight:300});
editor.render("content");
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
	initNotice();
});
</script>