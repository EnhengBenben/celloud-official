<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pro-body qa-container">
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
		<div class="qa-content" ng-show="feedbacks.page.rowCount>0">
			<header>
				<button class="btn -low" ng-click="solve(current.id)" ng-disabled="current.solved">问题已解决</button>
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
						<img ng-click="showAttach(attachment)" alt="Feedback Attachment" class="img-thumbnail" ng-src="feedback/attach?file={{attachment.filePath}}">
					</li>
					<li ng-class="{hide:current.solved||current.attachments.length>=5}">
						<a href="javascript:void(0)" id="attachmentUploadBtn">
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
				<textarea ng-model="replyContent" ng-init="replyContent=''" ng-keyup="replyKeyUp($event)" ng-trim="true" rows="4"></textarea>
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
					<form class="form-horizontal info-form" name="feedbackCreateForm" ng-submit="saveFeedback(feedbackCreateForm.$valid)">
						<div class="form-group">
							<div class="control-label form-label col-xs-2">标题：</div>
							<div class="col-xs-7">
								<input type="text" ng-trim="true" ng-model="unsavedFeedback.title" name="title" required ng-minlength="4" ng-maxlength="30" />
								<span class="input-alert break-line" ng-show="feedbackCreateForm.title.$dirty && feedbackCreateForm.title.$error.required">请输入问题的标题！</span>
								<!-- 长度4-30-->
								<span class="input-alert break-line" ng-show="feedbackCreateForm.title.$dirty && (feedbackCreateForm.title.$error.minlength||feedbackCreateForm.title.$error.maxlength)">请输入4-30字的描述！</span>
							</div>
							<div class="col-xs-3 form-group-content">
								<span>常见问题</span>
							</div>
						</div>
						<div class="form-group">
							<div class="control-label form-label col-xs-2">描述：</div>
							<!-- 长度10-100 -->
							<div class="col-xs-10 form-group-content">
								<textarea rows="4" ng-trim="true" ng-model="unsavedFeedback.content" name="content" required ng-minlength="10" ng-maxlength="100"></textarea>
								<span class="input-alert break-line" ng-show="feedbackCreateForm.content.$dirty && feedbackCreateForm.content.$error.required">请输入问题的描述</span>
								<span class="input-alert break-line" ng-show="feedbackCreateForm.content.$dirty && (feedbackCreateForm.content.$error.minlength||feedbackCreateForm.content.$error.maxlength)">请输入10-1000字的描述！</span>
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
									<li ng-repeat="unsavedAttach in unsavedAttachments">
										<img alt="Feedback Attachment" ng-src="<%=request.getContextPath()%>/feedback/attach/temp?file={{unsavedAttach}}">
									</li>
									<li ng-show="unsavedAttachments.length<5" ng-init="unsavedAttachments=[]">
										<a href="javascript:void(0)" id="unsavedAttachmentUploadBtn">
											<i class="fa fa-plus" aria-hidden="true"></i>
										</a>
									</li>
									<div class="clearfix"></div>
								</ul>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
			        <div class="text-center">
                        <button type="reset" class="btn btn-cancel -low" ng-click="resetCreateForm()">重置</button>
                        <button type="submit" class="btn -low" ng-click="saveFeedback(feedbackCreateForm.$valid)" ng-disabled="feedbackCreateForm.$invalid">提交</button>
                    </div>
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
				<h4 class="modal-title">工单附件</h4>
			</div>
			<div class="modal-body">
				<a ng-href="<%=request.getContextPath()%>/feedback/attach?file={{currentAttach.filePath}}" target="_blank">
					<img alt="" class="img-responsive img-thumbnail" ng-src="<%=request.getContextPath()%>/feedback/attach?file={{currentAttach.filePath}}">
				</a>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn" ng-click="deleteAttach(currentAttach.id)">删除</button>
				<button type="button" class="btn btn-cancel" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>