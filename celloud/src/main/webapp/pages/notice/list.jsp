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
										<tr style="font-weight: ${message.readState==0?'bold':'normal'};">
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
												<i class="fa fa-sellsy"></i> ${message.noticeTitle }
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
						<table class="table_" style="text-align: center;">
							<tr>
								<td>数据加载中。。。</td>
							</tr>
						</table>
						<div class="row">
							<div class="col-xs-4" style="padding-top: 10px;">
								<input type="button" class="btn btn-default" disabled="disabled" value="已读">
								<input type="button" class="btn btn-default" disabled="disabled" value="删除">
								<input type="button" class="btn btn-default" value="全部置为已读">
							</div>
							<div class="col-xs-8">
								<c:if test="${ noticeList!=null}">
									<jsp:include page="page.jsp">
										<jsp:param value="${noticeList.page }" name="page" />
										<jsp:param value="notices.pageNotice" name="method" />
									</jsp:include>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/notice.js?version=3.0"></script>
