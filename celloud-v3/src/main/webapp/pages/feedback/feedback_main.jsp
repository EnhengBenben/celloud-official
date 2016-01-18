<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="content-header">
	<h1>
		<small> </small>
	</h1>
	<ol class="breadcrumb">
		<li>
			<a href="#">
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
					<input type="hidden" name="hasAttachment" value="0">
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
							<c:forEach begin="1" end="4" var="num">
								<img style="height: 60px; margin-right: 10px; cursor: pointer;" src="images/avatar/0${num }.png"
									alt="User Image" />
							</c:forEach>
							<a class="btn btn-default btn-lg">
								<i class="fa fa-plus"></i>
							</a>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default pull-left" data-dismiss="modal">取消</button>
				<button type="button" class="btn btn-primary" onclick="feedbacks.create()">提交</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/feedbacks.js"></script>