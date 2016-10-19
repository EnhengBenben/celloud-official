<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="modal-header">
  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
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
                    <button onclick="feedback.solve('${feedback.id}')" class="btn btn-success pull-right">
                        <span aria-hidden="true" class="glyphicon glyphicon-remove"></span>
                        问题已解决
                    </button>
                </c:if>
            </div>
        </div>
    </div>
</div>
<div class="modal-body box box-success">
	<div class="box-body" style="min-height: 150px;">
		${feedback.content }
	</div>
	<div class="box-footer feedback-attachment-box">
		<h5>附件：</h5>
		<c:choose>
			<c:when test="${not empty feedback.hasAttachment && feedback.hasAttachment!=0 }">
				<c:forEach items="${attachments }" var="attachment">
					<img class="img-thumbnail feedback-attachment" onclick="feedback.showAttachment('${attachment.filePath}')"
						src="<%=request.getContextPath() %>/feedback/attach?file=${attachment.filePath}&userId=${feedback.userId}" alt="Feedback Attachment" />
				</c:forEach>
			</c:when>
			<c:otherwise>
				<span>无</span>
			</c:otherwise>
		</c:choose>
	</div>
	<div id="feedbackReplyList">
		<div class="box-footer feedback-reply">
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
					<button id="feedbackReplyBtn" class="btn btn-success btn-block" disabled="disabled" onclick="feedback.reply('${feedback.id }')">
						<span aria-hidden="true" class="glyphicon glyphicon-send"></span>
						回复
					</button>
				</div>
			</div>
		</div>
	</c:if>
</div>
