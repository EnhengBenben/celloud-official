<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<ng-include src="'pages/partial/_partial_notice_sidebar.jsp'"></ng-include>
<div class="pro-body">
	<ol class="breadcrumb">
		<li>CelLoud</li>
		<li>消息管理</li>
		<li>系统公告</li>
	</ol>
	<div class="content" ng-controller="messageController">
		<div class="table-opera">
			<div class="table-opera-content">
				<div class="only-btn">
					<button class="btn -low ng-class:{'btn-cancel' : !readState}" ng-click="read()" ng-disabled="!readState">
						<i class="fa fa-folder-open" aria-hidden="true"></i>已读
					</button>
					<button class="btn -low ng-class:{'btn-cancel' : !removeState}" ng-click="remove()" ng-disabled="!removeState">
						<i class="fa fa-times" aria-hidden="true"></i>删除
					</button>
					<button class="btn -low" ng-click="readAll()">
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
								<input class="checkbox" type="checkbox" ng-model="checkAllState" ng-change="checkAll(checkAllState)">
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
					<tr ng-repeat="message in messages.datas">
						<td>
							<label class="checkbox-lable">
								<input class="checkbox" type="checkbox" ng-model="chk" ng-checked="chkall" ng-change="checkOne(message,chk)" name="noticeIds" value="{{message.noticeId }}">
								<span class="info"></span>
							</label>
						</td>
						<td>
							<a>
								<i class="fa ng-class:{'fa-folder':message.readState==0,'fa-folder-open-o':message.readState!=0}"></i>
							</a>
						</td>
						<td style="text-align: left;">
							<a>
								<i class="{{message.icon }}"></i>
							</a>
							{{message.noticeTitle }}
						</td>
						<td style="text-align: left;">{{message.noticeContext }}</td>
						<td>{{message.createDate | date : 'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<ng-include src="'pages/partial/_partial_pagination_common.jsp'"></ng-include>
	</div>
</div>