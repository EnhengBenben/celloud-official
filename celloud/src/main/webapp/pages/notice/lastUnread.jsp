<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.celloud.model.mysql.Notice,com.celloud.utils.DateUtil"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose>
	<c:when test="${type=='message' }">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
			<i class="fa fa-bell-o"></i>
			<span class="label label-warning">${pageList.page.rowCount==0?'':pageList.page.rowCount }</span>
		</a>
		<ul class="dropdown-menu">
			<li class="header">您有${pageList.page.rowCount }条新消息</li>
			<li>
				<ul class="menu">
					<c:forEach items="${pageList.datas }" var="notice">
						<c:set scope="request" var="notice" value="${notice }"></c:set>
						<li>
							<a href="javascript:;">
								<div class="pull-left">
									<i class="${notice.icon } fa-2x" ></i>
								</div>
								<h4>
									${notice.noticeTitle } <small><i class="fa fa-clock-o"></i> <%=DateUtil.formatPastTime(((Notice) request.getAttribute("notice")).getCreateDate())%></small>
								</h4>
								<p style="word-wrap: break-word; word-break: normal;">${notice.noticeContext }</p>
							</a>
						</li>
					</c:forEach>
				</ul>
			</li>
			<li class="footer">
				<a href="javascript:showMessage()">查看所有</a>
			</li>
		</ul>
	</c:when>
	<c:when test="${type=='notice' }">
		<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
			<i class="fa fa-envelope-o"></i>
			<span class="label label-success"></span>
		</a>
		<ul class="dropdown-menu">
			<li class="header">站内消息提醒</li>
			<li>
				<!-- inner menu: contains the actual data -->
				<ul class="menu">
					<li>
						<a href="#">
							<i class="fa fa-users text-aqua"></i> 新功能上线啦~~~~
						</a>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-warning text-yellow"></i> 您的余额坚持不了多久了哦~
						</a>
					</li>
					<li>
						<a href="#">
							<i class="fa fa-users text-red"></i> 发票已邮寄成功，请关注
						</a>
					</li>
				</ul>
			</li>
			<li class="footer">
				<a href="javascript:showNotice()">查看所有</a>
			</li>
		</ul>
	</c:when>
	<c:otherwise></c:otherwise>
</c:choose>
