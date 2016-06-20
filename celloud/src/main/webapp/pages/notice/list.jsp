<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<section class="content-header">
	<h1>
		<small> </small>
	</h1>
	<ol class="breadcrumb">
		<li>
			<a href="javascript:void(0)">
				<i class="fa fa-user"></i> 消息中心
			</a>
		</li>
		<li class="active">
			<span id="subtitle">${type=='message'?'消息提醒':'系统公告' }</span>
		</li>
	</ol>
</section>

<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="mainpage" id="appMain">
				<div class="y-row operation-serve box box-success" data-spm="16">
					<div class="info">
						<p>系统发出公告、产品消息、安全通知、故障通知等信息时，会实时推送到您的消息中心（Notification Center），您也可以在这里查看系统发送的历史信息。</p>
					</div>
					<ul id="userOperaUl">
						<li class="${type=='message'?'active':'' }" id="userMessageTab">
							<a href="javascript:notices.showUserMessage()">消息提醒</a>
						</li>
						<li class="${type=='notice'?'active':'' }" id="userNoticeTab">
							<a href="javascript:notices.showUserNotice()">系统公告</a>
						</li>
					</ul>
				</div>
				<div class="y-row" style="padding: 20px 10px; background-color: #fff;" data-spm="17">
					<div class="common-normal common-slide common-normals ${type=='message'?'':'hide' }" id="userMessage">
						<form id="messageListForm" action="">
							<table class="table">
								<thead>
									<tr>
										<th style="width: 30px;" class="text-center">
											<input type="checkbox" id="messageListCheckAll">
										</th>
										<th style="width: 30px;"></th>
										<th width="15%">标题</th>
										<th>内容</th>
										<th style="width: 150px;">时间</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${messageList.datas }" var="message">
										<tr style="font-weight: ${message.readState==0?'bold':'normal'};" read-state="${message.readState }">
											<td class="text-center">
												<input type="checkbox" name="noticeIds" value="${message.noticeId }">
											</td>
											<td>
												<c:choose>
													<c:when test="${message.readState==0 }">
														<i class="fa fa-folder"></i>
													</c:when>
													<c:otherwise>
														<i class="fa fa-folder-open-o"></i>
													</c:otherwise>
												</c:choose>
											</td>
											<td>
												<i class="${message.icon }"></i> ${message.noticeTitle }
											</td>
											<td>${message.noticeContext }</td>
											<td class="text-center">
												<fmt:formatDate value="${message.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</form>
						<div class="row">
							<div class="col-xs-4" style="padding-top: 10px;">
								<input type="button" id="messageReadBtn" onclick="notices.readMessage()" class="btn btn-default"
									disabled="disabled" value="已读">
								<input type="button" id="messageDeleteBtn" onclick="notices.deleteMessage()" class="btn btn-default"
									disabled="disabled" value="删除">
								<input type="button" class="btn btn-default" onclick="notices.readAllMessage()" value="全部置为已读">
							</div>
							<div class="col-xs-8">
								<c:if test="${ messageList!=null}">
									<jsp:include page="page.jsp">
										<jsp:param value="${messageList.page }" name="page" />
										<jsp:param value="notices.pageMessage" name="method" />
									</jsp:include>
								</c:if>
							</div>
						</div>
					</div>
					<div class="common-normal common-slide common-normals ${type=='notice'?'':'hide' }" id="userNotice">
						<div class="row">
							<div class="col-xs-4" style="padding-right: 0px;">
								<div class="list-group">
									<div class="list-group-item">
										<button onclick="feedbacks.showForm()" class="btn btn-success">
											<span aria-hidden="true" class="glyphicon glyphicon-inbox"></span>
											全部置为已读
										</button>
										<div class="btn-group pull-right">
											<button class="btn btn-success" onclick="feedbacks.page('${page.prevPage}')" style="margin-right: 5px;">&lt;</button>
											<button class="btn btn-success" onclick="feedbacks.page(${page.nextPage})" style="margin-right: 5px;">&gt;</button>
										</div>
										<div class="pull-right" style="margin-right: 5px; line-height: 34px;">页数&nbsp;1/15</div>
									</div>
									<button class="list-group-item bg-gray" style="font-weight: bold;">
										<span class="badge bg-danger fa fa-folder" aria-hidden="true"> </span>
										<p>
											<i class="fa fa-users text-aqua"></i> 新功能上线了~~~~
										</p>
										<p>&nbsp;</p>
										<p class="feedback-date">发布时间：2016-06-17 14:37:22</p>
									</button>
									<button class="list-group-item" style="font-weight: bold;">
										<span class="badge bg-danger fa fa-folder" aria-hidden="true"> </span>
										<p>
											<i class="fa fa-warning text-yellow"></i> 您的余额坚持不了多久了哦~
										</p>
										<p>&nbsp;</p>
										<p class="feedback-date">发布时间：2016-06-17 14:37:22</p>
									</button>
									<button class="list-group-item" style="font-weight: bold;">
										<span class="badge bg-danger fa fa-folder" aria-hidden="true"> </span>
										<p>
											<i class="fa fa-users text-red"></i> 发票已邮寄成功，请关注
										</p>
										<p>&nbsp;</p>
										<p class="feedback-date">发布时间：2016-06-17 14:37:22</p>
									</button>
									<button class="list-group-item">
										<p>
											<i class="fa fa-users text-aqua"></i> 新功能上线了~~~~
										</p>
										<p>&nbsp;</p>
										<p class="feedback-date">发布时间：2016-06-17 14:37:22</p>
									</button>
									<button class="list-group-item">
										<p>
											<i class="fa fa-users text-aqua"></i> 新功能上线了~~~~
										</p>
										<p>&nbsp;</p>
										<p class="feedback-date">发布时间：2016-06-17 14:37:22</p>
									</button>
								</div>
							</div>
							<div class="col-xs-8 pull-right" style="padding-left: 0px;">
								<div class="box box-success">
									<div class="box-header with-border" style="">
										<div class="row">
											<div class="col-xs-10">
												<h4>
													<i class="fa fa-users text-aqua"></i> 新功能上线了~~~~
												</h4>
												<p>&nbsp;</p>
												<p>发布时间：2016-06-17 14:37:22</p>
											</div>
											<div class="col-xs-2">
												<button class="btn btn-close pull-right">X</button>
											</div>
										</div>
									</div>
									<div class="box-body" style="min-height: 150px;">
										<pre>新功能上线了~~~新功能上线了~~~新功能上线了~~~新功能上线了~~~新功能上线了~~~
新功能上线了~~~新功能上线了~~~新功能上线了~~~新功能上线了~~~新功能上线了~~~</pre>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</section>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/notice.js?version=3.0"></script>
