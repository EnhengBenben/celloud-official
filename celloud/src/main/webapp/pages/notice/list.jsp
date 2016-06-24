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
			<span id="subtitle">${type=='message'?'系统消息':'站内消息' }</span>
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
							<a href="javascript:notices.showUserMessage()">系统消息</a>
						</li>
						<li class="${type=='notice'?'active':'' }" id="userNoticeTab">
							<a href="javascript:notices.showUserNotice()">站内消息</a>
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
										<th style="width: 155px;">时间</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${messageList.datas }" var="message">
										<tr class="${message.readState==0?'unread':''}" read-state="${message.readState }">
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
									<c:if test="${ messageList.datas.size() == 0  }">
										<tr height="250px">
											<td colspan="5" class="text-center" style="vertical-align: middle; font-size: 18px;">
												<i class="glyphicon glyphicon-exclamation-sign text-yellow"></i> 您好，还没有消息哦！
											</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</form>
						<div class="row">
							<div class="col-xs-4" style="padding-top: 10px;">
								<input type="button" id="messageReadBtn" onclick="notices.readMessage()" class="btn btn-default"
									disabled="disabled" value="已读">
								<input type="button" id="messageDeleteBtn" onclick="notices.deleteMessage()" class="btn btn-default"
									disabled="disabled" value="删除">
								<input type="button" class="btn btn-default" onclick="notices.readAllMessage()"
									${messageList.datas.size() == 0?'disabled':''  } value="全部置为已读">
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
							<c:if test="${noticeList!=null && noticeList.datas.size() > 0  }">
								<div class="col-xs-4" style="padding-right: 0px;">
									<div class="list-group">
										<div class="list-group-item">
											<button class="btn btn-success">
												<i class="fa fa-folder-open-o"></i> 全部置为已读
											</button>
											<div class="btn-group pull-right">
												<button class="btn btn-success" ${noticeList.page.isHasPrev()?'':'disabled' }
													onclick="notices.pageNotice('${noticeList.page.prevPage}')" style="margin-right: 5px;">&lt;</button>
												<button class="btn btn-success" ${noticeList.page.isHasNext()?'':'disabled' }
													onclick="notices.pageNotice(${noticeList.page.nextPage})" style="margin-right: 5px;">&gt;</button>
											</div>
											<div class="pull-right" style="margin-right: 5px; line-height: 34px;">页数&nbsp;${noticeList.page.currentPage}/${noticeList.page.totalPage }</div>
										</div>
										<c:forEach items="${noticeList.datas }" var="notice">
											<button class="list-group-item ${notice.readState==0?'unread':'' }" _data_notice_id="${notice.noticeId }"
												onclick="notices.showNotice('${notice.noticeId}')">
												<span class="badge fa ${notice.readState==0?'fa-folder-o':'fa-folder-open-o bg-gray' }" aria-hidden="true">
												</span>
												<p>
													<i class="${notice.icon }"></i> ${notice.noticeTitle }
												</p>
												<p>&nbsp;</p>
												<p class="feedback-date">
													发布时间：
													<fmt:formatDate value="${notice.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
												</p>
											</button>
										</c:forEach>
									</div>
								</div>
								<div class="col-xs-8" style="padding-left: 0px;" id="noticeDetail"></div>
							</c:if>
							<c:if test="${noticeList==null || noticeList.datas.size() == 0  }">
								<div class="col-xs-12">
									<table class="table">
										<thead>
											<tr>
												<th>&nbsp;</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="text-center" style="height: 250px; font-size: 18px; vertical-align: middle;">
													<i class="glyphicon glyphicon-exclamation-sign text-yellow"></i> 您好，还没有消息哦！
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/notice.js?version=3.0"></script>
<c:if test="${type=='notice'}">
	<script type="text/javascript">
    notices.showNotice();
    </script>
</c:if>
