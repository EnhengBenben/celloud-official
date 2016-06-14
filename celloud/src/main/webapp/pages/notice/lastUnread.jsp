<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
	<i class="fa fa-bell-o"></i>
	<span class="label label-warning">${pageList.page.rowCount }</span>
</a>
<ul class="dropdown-menu">
	<li class="header">您有${pageList.page.rowCount }条新消息</li>
	<li>
		<ul class="menu">
			<c:forEach items="${pageList.datas }" var="notice">
				<li>
					<a href="javascript:;">
						<div class="pull-left">
							<i class="fa fa-sellsy fa-2x"></i>
						</div>
						<h4>
							${notice.noticeTitle } <small><i class="fa fa-clock-o"></i> 5 分钟</small>
						</h4>
						<p style="word-wrap: break-word; word-break: normal;">${notice.noticeContext }</p>
					</a>
				</li>
			</c:forEach>
		</ul>
	</li>
	<li class="footer">
		<a href="#">查看所有</a>
	</li>
</ul>