<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row">
	<div id="addClient" >
		<h4>
			上传客户端
		</h4>
		<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label" >
					版本号<font color="red">*</font>
				</label>
				<div class="controls">
					<input type="text" name="version" id="version"/>
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" >
					文件<font color="red">*</font>
				</label>
				<div class="controls">
					<input type="file" id="file" name="file"/>
				</div>
			</div>
		</form>
		<div class="modal-footer">
			<a href="javascript:uploadClient()" class="btn btn-primary">上传</a>
		</div>
	</div>
	
	<div id="clientList">
		
	</div>
</div>
<script type="text/javascript">
	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	getAllClient();
</script>