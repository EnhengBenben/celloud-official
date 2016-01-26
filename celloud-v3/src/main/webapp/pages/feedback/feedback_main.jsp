<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="content-header">
	<h1>
		<small> </small>
	</h1>
	<ol class="breadcrumb">
		<li>
			<a href="javascript:;">
				<i class="fa fa-comments"></i>问题反馈
			</a>
		</li>
	</ol>
</section>
<section id="feedbackList" class="content"></section>
<div class="modal" id="addQa">
	<div class="modal-dialog">
		<div class="modal-content box box-success">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">新增问题</h4>
			</div>
			<div class="modal-body" style="border-bottom: 1px solid #ccc">
				<form class="form-horizontal" id="feedbackCreateForm" action="<%=request.getContextPath()%>/feedback/create"
					method="post">
					<input type="hidden" name="_method" value="put">
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">标题</label>
						<div class="col-sm-9">
							<input type="text" name="title" class="form-control" id="inputEmail3" placeholder="">
							<span class="help-block"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">描述</label>
						<div class="col-sm-9">
							<textarea name="content" class="form-control" rows="5" placeholder="Enter ..."></textarea>
							<span class="help-block"></span>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12 margin">
							<label for="attachment" class=" control-label">附件</label>&nbsp;&nbsp;附件支持格式： jpg / jpeg / png，最大2M，每个工单最多可上传5个附件
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-12">
							<img id="attachmentUploading" class="img-thumbnail hide" style="height: 60px; margin-right: 10px;"
								src="images/icon/loading.jpg">
							<a class="btn btn-default btn-lg" id="uploadAttachmentBtn">
								<i class="fa fa-plus"></i>
							</a>
							<div id="attachmentContainer"></div>
						</div>
					</div>
					<div class="form-group has-error" id="warning-group" style="color: #a94442">
						<label class="col-sm-2 control-label">WARNING：</label>
						<div class="col-sm-9">
							<p class="form-control-static">Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" onclick="feedbacks.create()">提交</button>
			</div>
		</div>
	</div>
</div>
<div class="modal" id="showAttachment">
	<div class="modal-dialog">
		<div class="modal-content box box-success">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">问题反馈附件</h4>
			</div>
			<div class="modal-body" style="border-bottom: 1px solid #ccc">
				<a href="" target="_blank">
					<img class="img-thumbnail" alt="Feedback Attachment" />
				</a>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/feedbacks.js"></script>