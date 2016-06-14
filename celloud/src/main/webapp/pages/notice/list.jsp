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
						<p>系统发出公告、产品消息、安全通知、故障通知等信息时，会实时推送到您的消息中心（Notification Center），您也可以在这里查看系统发送的历史信息。 您可以在 这里 修改接收消息的通知列表.</p>
					</div>
					<ul id="userOperaUl">
						<li class="${type=='message'?'active':'' }" id="userMessageTab">
							<a href="javascript:users.showUserInfo()">消息提醒</a>
						</li>
						<li class="${type=='notice'?'active':'' }" id="userNoticeTab">
							<a href="javascript:users.showChangePwd()">系统公告</a>
						</li>
					</ul>
				</div>
				<div class="y-row" style="padding: 20px 10px; background-color: #fff;" data-spm="17">
					<div class="common-normal common-slide common-normals ${type=='message'?'':'hide' }" id="userMessage">
						<table class="table">
							<thead>
								<tr>
									<th style="width: 40px;" class="text-center">
										<input type="checkbox">
									</th>
									<th width="15%">标题</th>
									<th>内容</th>
									<th style="width: 150px;">时间</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${messageList.datas }" var="message">
									<tr style="font-weight: ${message.readState==0?'bold':'normal'};">
										<td class="text-center">
											<input type="checkbox">
										</td>
										<td>${message.noticeTitle }</td>
										<td>${message.noticeContext }</td>
										<td class="text-center">
											<fmt:formatDate value="${message.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="row">
							<div class="col-xs-4" style="padding-top: 10px;">
								<input type="button" class="btn btn-default" disabled="disabled" value="已读">
								<input type="button" class="btn btn-default" disabled="disabled" value="删除">
								<input type="button" class="btn btn-default" value="全部置为已读">
							</div>
							<div class="col-xs-8">
								<jsp:include page="page.jsp">
									<jsp:param value="${messageList.page }" name="page" />
									<jsp:param value="notices.pageMessage" name="method" />
								</jsp:include>
							</div>
						</div>

					</div>
					<div class="common-normal common-slide common-normals ${type=='notice'?'':'hide' }" id="userNotice">
						<table class="table_" style="text-align: center;">
							<tr>
								<td>数据加载中。。。</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/notice.js?version=3.0"></script>
