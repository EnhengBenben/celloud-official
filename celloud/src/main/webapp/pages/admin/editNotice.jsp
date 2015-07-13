<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form class="form-horizontal" id="editForm">
	<div class="control-group">
		<label class="control-label">
			公告标题
		</label>
		<div class="controls">
			<input type="hidden" name="notice.noticeId" value="<s:property value='notice.noticeId' />" />
			<input type="text" name="notice.noticeTitle" value="<s:property value='notice.noticeTitle' />" />
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">
			公告内容
		</label>
		<div class="controls">
			<textarea rows="3" name="notice.noticeContext" id="editNotice"><s:property value="notice.noticeContext" /></textarea>
		</div>
	</div>
</form>