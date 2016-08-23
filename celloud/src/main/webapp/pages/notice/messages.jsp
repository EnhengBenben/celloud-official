<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<ng-include src="'pages/partial/_partial_notice_sidebar.jsp'"></ng-include>
<div class="pro-body">
	<ol class="breadcrumb">
		<li>CelLoud</li>
		<li>消息管理</li>
		<li>系统公告</li>
	</ol>
	<div class="content" ng-controller="noticeController">
		<div class="table-opera">
			<div class="table-opera-content">
				<div class="only-btn">
					<button class="btn -low">
						<i class="fa fa-folder-open" aria-hidden="true"></i>已读
					</button>
					<button class="btn btn-cancel -low">
						<i class="fa fa-times" aria-hidden="true"></i>删除
					</button>
					<button class="btn btn-cancel -low">
						<i class="fa fa-folder-open" aria-hidden="true"></i>全部已读
					</button>
				</div>
			</div>
		</div>
		<div id="messageListTable">
			<table class="table table-main">
				<thead>
					<tr>
						<th style="width: 30px;">
							<label class="checkbox-lable">
								<input class="checkbox" type="checkbox" name="demo-checkbox1">
								<span class="info"></span>
							</label>
						</th>
						<th style="width: 30px;"></th>
						<th style="width: 15%; text-align: left;">标题</th>
						<th style="text-align: left;">内容</th>
						<th style="width: 170px;">时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${notices.datas }" var="message">
						<tr>
							<td>
								<label class="checkbox-lable">
									<input class="checkbox" type="checkbox" name="demo-checkbox1" ng-disabled="file.isRunning==1||file.tagName==null">
									<span class="info"></span>
								</label>
							</td>
							<td>
								<a>
									<c:choose>
										<c:when test="${message.readState==0 }">
											<i class="fa fa-folder"></i>
										</c:when>
										<c:otherwise>
											<i class="fa fa-folder-open-o"></i>
										</c:otherwise>
									</c:choose>
								</a>
							</td>
							<td style="text-align: left;">
								<a>
									<i class="${message.icon }"></i>
								</a>
								${message.noticeTitle }
							</td>
							<td style="text-align: left;">${message.noticeContext }</td>
							<td>
								<fmt:formatDate value="${message.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<ng-include src="'pages/partial/_partial_pagination_common.jsp'"></ng-include>
	</div>
</div>