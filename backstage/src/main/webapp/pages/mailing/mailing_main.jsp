<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Basic Setup -->
<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">邮件群发</h3>
	</div>
	<div class="panel-body">
		<form role="form" class="form-horizontal" id="mailingForm">
			<div class="form-group">
				<a onclick="mailing.checkAll(1)" class="btn col-md-offset-3 text-green" href="javascript:void(0)">全选</a> 
				<a onclick="mailing.checkAll(0)" class="btn text-green" href="javascript:void(0)">全不选</a> 
				<a onclick="mailing.checkAll(2)" class="btn text-green" href="javascript:void(0)">反选</a>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">用户列表<font color="red">*</font></label>

				<div class="col-sm-10">
					<select name="emails" id="userList" style="width: 100%" multiple="multiple">
					</select>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">文档标题<font color="red">*</font></label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="emailTitle"
						name="emailTitle" data-rule-required="true" />
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">文档介绍<font color="red">*</font></label>

				<div class="col-sm-10">
					<textarea class="form-control" cols="5" id="emailContent"
						name="emailContent" data-rule-required="true"></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12 text-center">
					<button type="submit" class="btn btn-success">发送</button>
				</div>
			</div>
		</form>

	</div>
</div>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/resources/plugins/select2/js/select2.full.min.js"></script>
<link
	href="${pageContext.request.contextPath }/resources/plugins/select2/css/select2.min.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
	$(function() {
		// 请求用户数据, 添加到select2标签中
		var url = "${pageContext.request.contextPath}/mailing/getUser";
		$.ajax({
			url : url,
			type : 'post',
			dataType : 'text',
			success : function(data) {
				// 从后台获取json数据
				var json = eval('(' + data + ')');
				$("#userList").select2({
					data : json,
					placeholder : '请选择用户',
					allowClear : true
				})
			},
			error : function(data) {

			},
		});
		/* --------------------------------- */
		CKEDITOR.replace('emailContent');

		$("#mailingForm").validate(
				{
					errorPlacement : function(error, element) {
						error.appendTo($(element).closest("div.col-sm-10"));
					},
					submitHandler : function(form) {
						$("#emailContent").val(
								CKEDITOR.instances.emailContent.getData());
						$(form).ajaxSubmit({
							url : "mailing/send",
							type : "POST",
							success : function(responseText) {
								if (responseText > 0) {
									alert("发送成功");
								}
							}
						});
					}
				});
		CKEDITOR.instances["emailContent"].on("instanceReady", function() {
			//set keyup event  
			this.document.on("keyup", updateTextArea);
			//and paste event  
			this.document.on("paste", updateTextArea);
		});
		function updateTextArea() {
			CKEDITOR.tools.setTimeout(function() {
				$("#emailContent").val(
						CKEDITOR.instances.emailContent.getData());
				$("#emailContent").trigger('keyup');
			}, 0);
		}
	});

	function checkAll(flag) {
		
	}
</script>
