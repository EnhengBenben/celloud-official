<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<ng-include src="'pages/partial/_partial_notice_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>消息管理</li>
      <li>消息设置</li>
    </ol>
    <div class="content">
      <div class="notice-content">
        <div class="notice-body">
	        <h5>消息提醒设置</h5>
	        <table class="table table-main notice-table">
	          <thead>
	            <tr>
	              <th></th>
	              <th>邮件</th>
	              <th>桌面提醒</th>
	              <th>微信</th>
	            </tr>
	          </thead>
	          <tbody>
	            <tr ng-repeat="message in userMessageCategoryList">
	              <td>收到[{{message.name}}]通知</td>
	              <td>
	                <div class="switch-btn-group" ng-show="message.email!='2'">
	                  <a><span class="s-btn no" ng-class="{active: message.email=='0'}" ng-click="updateMessageCategory(message.flag,message.mcId,'Email',0)">拒绝</span></a>
	                  <a><span class="s-btn" ng-class="{active: message.email=='1'}" ng-click="updateMessageCategory(message.flag,message.mcId,'Email',1)">接受</span></a>
	                </div>
	                <div class="switch-btn-group disabled" ng-show="message.email=='2'">
	                  <a><span class="s-btn no">拒绝</span></a>
	                  <a><span class="s-btn">接受</span></a>
	                </div>
	              </td>
	              <td>
	              	<div class="switch-btn-group" ng-show="message.window!='2'">
	                  <a><span class="s-btn no" ng-class="{active: message.window=='0'}" ng-click="updateMessageCategory(message.flag,message.mcId,'Window',0)">拒绝</span></a>
	                  <a><span class="s-btn" ng-class="{active: message.window=='1'}" ng-click="updateMessageCategory(message.flag,message.mcId,'Window',1)">接受</span></a>
	                </div>
	                <div class="switch-btn-group disabled" ng-show="message.window=='2'">
	                  <a><span class="s-btn no">拒绝</span></a>
	                  <a><span class="s-btn">接受</span></a>
	                </div>
	              </td>
	              <td>
	              	<div class="switch-btn-group" ng-show="message.wechat!='2'">
	                  <a><span class="s-btn no" ng-class="{active: message.wechat=='0'}" ng-click="updateMessageCategory(message.flag,message.mcId,'Wechat',0)">拒绝</span></a>
	                  <a><span class="s-btn" ng-class="{active: message.wechat=='1'}" ng-click="updateMessageCategory(message.flag,message.mcId,'Wechat',1)">接受</span></a>
	                </div>
	                <div class="switch-btn-group disabled" ng-show="message.wechat=='2'">
	                  <a><span class="s-btn no">拒绝</span></a>
	                  <a><span class="s-btn">接受</span></a>
	                </div>
	              </td>
	            </tr>
	          </tbody>
	        </table>
        </div>
      </div>
    </div>
</div>
