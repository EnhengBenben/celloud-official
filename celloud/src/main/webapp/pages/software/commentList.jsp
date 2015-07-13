<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:iterator value="commentList" id="comment" status="st">
	<div class="comment">
	<!-- 评论内容 -->
	<p>
		<a href="javascript:void(0);"><s:property value="#comment.commentUserName" /></a>：<s:property value="#comment.comment" />
		<a href="javascript:replyComment('<s:property value="#comment.id" />');">回复</a>
		<s:if test="#comment.commentUserName==#session.userName">
         			<a href="javascript:deleteComment('<s:property value="soft.softwareId"/>','<s:property value="#comment.id" />',0);">删除</a>
         		</s:if>
		<span><s:date name="#comment.commentDate" format="yyyy-MM-dd HH:mm:ss" /></span>
	</p>
	<!-- 回复内容 -->
	<s:iterator value="#comment.replyList" id="reply" status="st">
		<p>
			<a href="javascript:void(0);"><s:property value="#reply.replyUserName" /></a>回复
			<a href="javascript:void(0);"><s:property value="#comment.commentUserName" /></a>：
			<s:property value="#reply.comment" />
			<s:if test="#reply.replyUserName==#session.userName">
        			<a href="javascript:deleteComment('<s:property value="soft.softwareId"/>','<s:property value="#reply.id" />',1);">删除</a>
        		</s:if>
			<span><s:date name="#reply.commentDate" format="yyyy-MM-dd HH:mm:ss" /></span>
		</p>
     		</s:iterator>
     		<!-- 回复输入框 -->
     		<p id='<s:property value="#comment.id" />Textarea' style="display: none;">回复：<textarea></textarea></p>
          <p>
          	<a id='<s:property value="#comment.id" />Asubmit' href="javascript:submitReply('<s:property value="#comment.id" />','<s:property value="soft.softwareId"/>');" class="btn btn-blue" style="display: none;">确认回复</a>
          	<a id='<s:property value="#comment.id" />Acancel' href="javascript:cancelReply('<s:property value="#comment.id" />');" class="btn" style="display: none;">取消回复</a>
          	<span id="<s:property value="#comment.id" />SpanInfo"></span>
          </p>
	</div>
</s:iterator>