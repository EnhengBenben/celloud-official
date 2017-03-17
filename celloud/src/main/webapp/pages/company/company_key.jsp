<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ng-include src="'pages/partial/_partial_company_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>医院管理</li>
      <li>AccessKey</li>
    </ol>
    <div class="content">
      <div class="table-opera">
        <div class="table-opera-content">
          <div class="opera-info">
            <button class="btn data-operate" data-toggle="modal" ng-click="add()">新建</button>
          </div>
        </div>
      </div>
      <div class="table-opera">
        <span class="tips">
             Access Key ID和Access Key Secret是您访问CelLoudAPI的密钥，请您妥善保管。
        </span>
      </div>
      <div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="state">
      	<button type="button" class="close" ng-click="state=false"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
      	<span>{{message}}</span>
      </div>
      <table class="table table-main">
        <thead>
          <tr>
            <th>Access Key ID</th>
            <th>Access Key Secret</th>
            <th>状态</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="key in keyList.datas">
            <td title="{{key.keyId}}">{{key.keyId}}</td>
            <td title="{{key.keySecret}}">
                {{secretJson[key.id]}}
                <button ng-if="secretJson[key.id] == null" class="btn-link" ng-click="showSecret(key.id)">显示</button>
                <button ng-if="secretJson[key.id] != null" class="btn-link" ng-click="hideSecret(key.id)">隐藏</button>
            </td>
            <td>{{key.state == 0 ? '启用' : '禁用'}}</td>
            <td>{{key.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a class="btn-link" ng-click="update(key.id, key.state)">{{key.state == 0 ? '禁用' : '启用'}}</a><a class="btn-link" ng-click="remove(key.id)">删除</a></td>
          </tr>
          <tr ng-show="key.datas.length == 0">
          	<td colspan="9">暂无数据</td>
          </tr>
        </tbody>
      </table>
      <pagination page="keyList.page" change="pageQuery(page,pageSize)"></pagination>
    </div>
  <div id="company-showKey" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg" style="width: 500px;">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
          <h4 class="modal-title">安全校验</h4>
        </div>
        <div class="modal-body form-modal">
          <form class="form-horizontal info-form" name="userAuthenticationForm" id="userAuthenticationForm">
              <div class="form-group">
                <div class="control-label form-label col-xs-3">手机号码：</div>
                <div class="col-xs-9">
                    {{cellphone}}
	                <button id="captchaButton" type="button" class="btn" ng-disabled="cellphone == ''" ng-click="sendCaptcha()">获取验证码</button>
	                <a ng-click="hideModal()" class="btn" ng-disabled="cellphone != ''">完善手机号</a>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-3">验证码：</div>
                <div class="col-xs-9">
                    <input type="text" name="captcha" placeholder="验证码" ng-model="captcha" required="true">
                </div>
              </div>
              <div ng-show="errorInfo != ''" class="form-group" style="padding-top: 5px;margin-bottom: -5px;color: #ff587d">
                <div class="col-xs-3"></div>
                <div class="col-xs-9">{{errorInfo}}</div>
              </div>
          </form>
        </div>
        <div class="modal-footer">
          <div class="text-center">
              <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
              <button type="button" class="btn" ng-disabled="userAuthenticationForm.$invalid" ng-click="authenticationCellphone()">发送</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
