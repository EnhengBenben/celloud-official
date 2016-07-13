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
			<span id="subtitle">消息设置</span>
		</li>
	</ol>
</section>

<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="mainpage" id="appMain">
				<div class="y-row operation-serve box box-success" data-spm="16">
					<div class="info">
						<p>系统发出公告、产品消息、安全通知、故障通知等信息时，会实时推送到您的消息中心（Notification Center），您可以在这里设置接收消息的方式。</p>
					</div>
					<ul id="userOperaUl">
						<li id="userMessageTab">
							<a href="javascript:showMessage()">系统消息</a>
						</li>
						<li id="userNoticeTab">
							<a href="javascript:showNotice()">站内消息</a>
						</li>
						<li class="active" id="userSettingTab">
                            <a href="javascript:showUserSetting()">消息设置</a>
                        </li>
					</ul>
				</div>
				<div class="y-row" style="padding: 20px 10px; background-color: #fff;" data-spm="17">
					<div class="common-normal common-slide common-normals" id="userSetting">
						<form id="userSettingForm" action="">
						    <input type="hidden" value="${flag }" id="flag">
							<table class="table">
								<thead>
									<tr>
										<th width="25%">消息提醒设置</th>
										<th>邮件</th>
										<th>桌面提醒</th>
										<th>微信</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${messageCategoryList }" var="message">
											<tr>
												<td>收到【${message.name }】通知</td>
												<td>
													<c:choose>
														<c:when test="${message.email=='2'}">
														    <div><input name="email" class="create-switch" type="checkbox" disabled="disabled" /></div>
														</c:when>
														<c:otherwise>
														    <div><input name="email" class="create-switch" type="checkbox" ${message.email=='0'?'':'checked' } /></div>
														</c:otherwise>
													</c:choose>
											    </td>
												<td>
													<c:choose>
														<c:when test="${message.window=='2'}">
														    <div><input name="window" class="create-switch" type="checkbox" disabled="disabled"/></div>
														</c:when>
														<c:otherwise>
														    <div><input name="window" class="create-switch" type="checkbox" ${message.window=='0'?'':'checked' } /></div>
														</c:otherwise>
													</c:choose>
												</td>
	                                            <td>
	                                                <div><input name="wechat" class="create-switch" type="checkbox" ${message.wechat=='0'?'':'checked' } /></div>
	                                            </td>
	                                            <td><input type="hidden" name="messageCategoryId" value="${message.id }" /></td>											
											</tr>
									</c:forEach>
								</tbody>
							</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/notice.js?version=3.2.1.1"></script>
