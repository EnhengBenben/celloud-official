<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="box box-success">
	<div class="box-header with-border" style="">
		<div class="row">
			<div class="col-xs-10">
				<h4>${feedback.title }</h4>
				<p>
					提问时间：
					<fmt:formatDate value="${feedback.createDate }" pattern="yyyy-MM-dd HH:mm" />
				</p>
			</div>
			<div class="col-xs-2">
				<c:if test="${feedback.isSolved() }">
					<button class="btn btn-close pull-right" disabled="disabled">已解决</button>
				</c:if>
				<c:if test="${!feedback.isSolved() }">
					<button onclick="feedbacks.solve('${feedback.id}')" class="btn btn-success pull-right">问题已解决</button>
				</c:if>
			</div>
		</div>
	</div>
	<div class="box-body" style="min-height: 150px;">
		<p>${feedback.content }</p>
	</div>
	<div class="box-footer" style="min-height: 120px;">
		<h5>附件：</h5>
		<c:choose>
			<c:when test="${feedback.hasAttachment() }">
				<c:forEach items="${attachments }" var="attachment">
					<img class="img-thumbnail" style="height: 60px; margin-right: 10px;"
						src="<%=request.getContextPath() %>/feedback/attach?file=${attachment.filePath}" alt="User Image" />
				</c:forEach>
			</c:when>
			<c:otherwise>
				<span>无</span>
			</c:otherwise>
		</c:choose>
	</div>
	<div id="feedbackReplyList">
		<div class="box-footer" style="min-height: 120px;">
			<h5>回复加载中。。。</h5>
		</div>
	</div>
	<c:if test="${!feedback.isSolved() }">
		<div class="box-footer">
			<div class="row">
				<div class="col-xs-10">
					<form action="<%=request.getContextPath() %>/feedback/reply/${feedback.id }" method="post" id="feedbackReplyForm">
						<input type="hidden" name="_method" value="put">
						<textarea id="feedbackReplyContent" name="content" rows="3" style="width: 100%"></textarea>
					</form>
				</div>
				<div class="col-xs-2">
					<button id="feedbackReplyBtn" class="btn btn-success btn-block" disabled="disabled" onclick="feedbacks.reply()"
						style="height: 100%">回复</button>
				</div>
			</div>
		</div>
	</c:if>
</div>
