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
					<button class="btn -low" ng-click="readNotices()" ng-disabled="!readState">
						<i class="fa fa-folder-open" aria-hidden="true"></i>已读
					</button>
					<button class="btn -low" ng-click="deleteNotice()" ng-disabled="!removeState">
						<i class="fa fa-times" aria-hidden="true"></i>删除
					</button>
					<button class="btn -low" ng-click="readAllNotices()">
						<i class="fa fa-folder-open" aria-hidden="true"></i>全部已读
					</button>
				</div>
			</div>
		</div>
		<table class="table table-main">
			<thead>
				<tr>
					<th class="th-checkoutbox">
						<label class="checkbox-lable">
							<input class="checkbox" ng-model="checkNoticeAllState" ng-checked="{{checkNoticeAllState}}" ng-change="checkNoticeAll()" type="checkbox">
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
				<tr ng-repeat="message in notices.datas">
					<td>
						<label class="checkbox-lable">
							<input class="checkbox" type="checkbox" ng-model="chk" ng-checked="checkedNotices.indexOf(message)>-1" ng-change="checkNoticeOne(message)" name="noticeIds" value="{{message.noticeId }}">
							<span class="info"></span>
						</label>
					</td>
					<td>
						<a>
							<i class="fa ng-class:{'fa-folder':message.readState==0,'fa-folder-open-o':message.readState!=0}"></i>
						</a>
					</td>
					<td style="text-align: left;">{{message.noticeTitle }}</td>
					<td style="text-align: left;">{{message.noticeContext }}</td>
					<td>{{message.createDate | date : 'yyyy-MM-dd HH:mm:ss'}}</td>
				</tr>
			</tbody>
		</table>
		<pagination page="notices.page" change="changePage(page,pageSize)"></pagination>
	</div>
</div>
