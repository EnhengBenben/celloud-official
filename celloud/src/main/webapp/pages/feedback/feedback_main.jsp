<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pro-body qa-container" ng-controller="feedbackController">
	<ol class="breadcrumb">
		<li>用户中心</li>
		<li>问题反馈</li>
	</ol>
	<div class="content">
		<div class="qa-list">
			<ul>
				<li class="qa-header">
					<button class="btn -low" data-toggle="modal" data-target="#new-qa-modal">创建工单</button>
					<p>合计{{feedbacks.page.rowCount}}条</p>
				</li>
				<li ng-repeat="feedback in feedbacks.datas" ng-class="{active:feedback.id==current.id}" ng-click="change(feedback.id)" style="cursor: pointer;">
					<h5>{{feedback.title}}</h5>
					<p class="feedback-date">提问时间：{{feedback.createDate | date : 'yyyy-MM-dd HH:mm:ss'}}</p>
					<p class="feedback-content">{{feedback.content}}</p>
				</li>
			</ul>
		</div>
		<div class="qa-content">
			<header>
				<button class="btn -low ng-class:{hide:current.solved}" ng-click="solve(current.id)">问题已解决</button>
				<button class="btn btn-cancel -low ng-class:{hide:!current.solved}">问题已解决</button>
			</header>
			<div class="qa-detail">
				<h5>{{current.title}}</h5>
				<p class="feedback-date">提问时间：{{current.createDate | date : 'yyyy-MM-dd HH:mm:ss'}}</p>
				<p class="feedback-content">{{current.content}}</p>
			</div>
			<div class="qa-attachment">
				<h5>
					附件
					<span>附件支持格式：txt/jpg/jpeg/png，最大2M，每个工单最多可上传5个附件</span>
				</h5>
				<ul class="attachment-list">
					<li ng-repeat="attachment in current.attachments">
						<img alt="Feedback Attachment" ng-src="feedback/attach?file={{attachment.filePath}}">
					</li>
					<li ng-class={hide:current.solved}>
						<a href="javascript:void(0)">
							<i class="fa fa-plus" aria-hidden="true"></i>
						</a>
					</li>
					<div class="clearfix"></div>
				</ul>
			</div>
			<div class="qa-chatbox">
				<div ng-repeat="reply in current.replies" class="chat-content ng-class:{'answer-chat':reply.userId!=userInfo.userId,'questioner-chat':reply.userId==userInfo.userId}">
					<div class="qa-avatar">
						<img class="img-circle" alt="头像" ng-src="{{reply.userId==userInfo.userId?userInfo.avatar:'images/avatar/05.png'}}">
					</div>
					<div class="answer-text">
						<h5>{{reply.content}}</h5>
						<p class="feedback-date">回复时间：{{reply.createTime | date : 'yyyy-MM-dd HH:mm:ss'}}</p>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="qa-insert ng-class:{hide:current.solved}">
				<textarea ng-model="replyContent" ng-keyup="replyKeyUp($event)" ng-trim="true" rows="4"></textarea>
				<button class="btn -low" ng-disabled="replyContent.length<=0" ng-click="reply(current.id,replyContent)">回&nbsp;复</button>
			</div>
		</div>
	</div>


	<div id="new-qa-modal" class="modal qa-modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">
							<i class="fa fa-times-circle"></i>
						</span>
					</button>
					<h4 class="modal-title">创建工单</h4>
				</div>
				<div class="modal-body form-modal">
					<form class="form-horizontal info-form">
						<div class="form-group">
							<div class="control-label form-label col-xs-2">标题：</div>
							<div class="col-xs-7">
								<input type="text" ng-model="money" name="money" id="money" />
								<span class="input-alert break-line">标题不能为空</span>
							</div>
							<div class="col-xs-3 form-group-content">
								<span>常见问题</span>
							</div>
						</div>
						<div class="form-group">
							<div class="control-label form-label col-xs-2">描述：</div>
							<div class="col-xs-10 form-group-content">
								<textarea rows="4"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="control-label form-label col-xs-12 no-input">
								附件：
								<p>附件支持格式：txt/jpg/jpeg/png，最大2M，每个工单最多可上传5个附件</p>
							</div>
						</div>
						<div class="form-group">
							<div class="control-label form-label">
								<ul class="attachment-list">
									<li>
										<img alt="" src="<%=request.getContextPath()%>/images/screenshot/CMP1.png">
									</li>
									<li>
										<a href="javascript:void(0)">
											<i class="fa fa-plus" aria-hidden="true"></i>
										</a>
									</li>
									<div class="clearfix"></div>
								</ul>
							</div>
						</div>
						<div class="form-group form-btns">
							<div class="text-center">
								<button type="reset" class="btn btn-cancel">重置</button>
								<button type="submit" class="btn">提交</button>
							</div>
							<div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="state">
								<button type="button" class="close" ng-click="state=false">
									<span aria-hidden="true">
										<i class="fa fa-times-circle"></i>
									</span>
								</button>
								<span>{{message}}</span>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="attachmentModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">
						<i class="fa fa-times-circle"></i>
					</span>
				</button>
				<h4 class="modal-title">创建工单</h4>
			</div>
			<div class="modal-body">
				<img alt="" class="img-responsive img-thumbnail" src="<%=request.getContextPath()%>/images/screenshot/CMP1.png">
			</div>
		</div>
	</div>
</div>