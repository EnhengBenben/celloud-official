<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set value="${feedbackList.page }" var="page" />
<c:set value="${feedbackList.datas }" var="feedbacks" />
<div class="row">
	<div class="col-xs-4" style="padding-right: 0px;">
		<div class="list-group">
			<div class="list-group-item">
				<c:if test="${page.rowCount == 0 }">
					<div>问题反馈是您与 CelLoud 最直接有效的交流平台，您可以通过问题反馈来咨询任何问题，我们会第一时间为您解决。同时我们也欢迎您提交建议与意见。</div>
				</c:if>
				<button onclick="feedbacks.showForm()" class="btn btn-success">新问题</button>
				<c:if test="${page.totalPage>1 }">
					<div class="btn-group pull-right">
						<c:if test="${page.hasPrev }">
							<button class="btn btn-success" onclick="feedbacks.page('${page.prevPage}')" style="margin-right: 5px;">&lt;</button>
						</c:if>
						<c:if test="${!page.hasPrev }">
							<button class="btn btn-success" disabled="disabled" style="margin-right: 5px;">&lt;</button>
						</c:if>
						<c:if test="${page.hasNext }">
							<button class="btn btn-success" onclick="feedbacks.page(${page.nextPage})" style="margin-right: 5px;">&gt;</button>
						</c:if>
						<c:if test="${!page.hasNext }">
							<button class="btn btn-success" disabled="disabled" style="margin-right: 5px;">&gt;</button>
						</c:if>
					</div>
					<div class="pull-right" style="margin-right: 5px; line-height: 34px;">页数&nbsp;${page.currentPage }/${page.totalPage }</div>
				</c:if>
			</div>
			<c:forEach items="${feedbacks }" var="feedback">
				<button class="list-group-item" feedback_id="${feedback.id }" onclick="feedbacks.detail('${feedback.id}')">
					<c:if test="${feedback.isSolved() }">
						<span class="badge">已解决</span>
					</c:if>
					<c:if test="${feedback.hasAttachment() }">
						<span class="badge glyphicon glyphicon-paperclip" aria-hidden="true"> </span>
					</c:if>
					<p>${feedback.title }</p>
					<p style="font-size: 12px; font-color: #8A8A8A; text-align: left">
						提问时间：
						<fmt:formatDate value="${feedback.createDate }" pattern="yyyy-MM-dd HH:mm" />
					</p>
					<p style="font-size: 12px; font-color: #8A8A8A; text-align: left">${feedback.content }</p>
				</button>
			</c:forEach>
		</div>
	</div>
	<div class="col-xs-8 pull-right" style="padding-left: 0px;" id="feedbackDetail"></div>
</div>
