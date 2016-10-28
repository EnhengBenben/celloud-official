<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<ng-include src="'pages/partial/_partial_company_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>医院管理</li>
      <li>医院账号管理</li>
    </ol>
    <div class="content">
      <div class="table-opera">
        <div class="table-opera-content">
          <div class="opera-info">
            <shiro:hasPermission name="run:button">
              <button class="btn data-operate btn-cancel" disabled="disabled" ng-click="runWithProject()"><i class="fa fa-play" aria-hidden="true"></i> 运行</button>
            </shiro:hasPermission>
            <button class="btn data-operate btn-cancel" disabled="disabled" ng-click="deleteData()"><i class="fa fa-play" aria-hidden="true"></i> 归档</button>
          </div>
          <div class="info-btn-group pull-right">
            <a class="action" data-toggle="modal" data-target="#company-addUser-modal" ng-click="showAddUserForm()">新增</a>
          </div>
        </div>
      </div>
      <div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="state">
      	<button type="button" class="close" ng-click="state=false"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
      	<span>{{message}}</span>
      </div>
      <table class="table table-main" ng-init="pageType='data'" id="_data_table">
        <thead>
          <tr>
            <th>账户名</th>
            <th>真实姓名</th>
            <th>邮箱</th>
            <th>手机号码</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="user in userList.datas">
            <td title="{{user.username}}"><i class="fa fa-truck" aria-hidden="true" ng-show="file.isRunning==1"></i>{{user.username}}</td>
            <td title="{{user.truename}}">{{user.truename}}</td>
            <td>{{user.email}}</td>
            <td>{{user.cellphone}}</td>
            <td>{{user.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a class="btn-link" data-toggle="modal" ng-click="updateUserState(user.userId,user.state)"><span ng-if="user.state == 1">启用</span><span ng-if="user.state == 0">禁用</span></a></td>
          </tr>
          <tr ng-show="user.datas.length == 0">
          	<td colspan="9">暂无数据</td>
          </tr>
        </tbody>
      </table>
      <pagination page="userList.page" change="pageQuery(page,pageSize)"></pagination>
    </div>
  <div id="company-addUser-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">增加账号--发送邮件</h4>
	    </div>
	    <div class="modal-body form-modal">
	      <form class="form-horizontal info-form" name="userAddForm" id="userAddForm">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">邮箱地址：</div>
	            <div class="col-xs-9">
	                <input type="text" ng-change="clearState()" name="email" placeholder="邮箱地址" ng-model="email" required="true" ng-pattern="/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/">
	                <span class="input-alert" ng-show="userAddForm.email.$dirty && userAddForm.email.$invalid">邮箱格式不正确!</span>
	                <span class="input-alert" ng-show="emailError != null">{{emailError}}</span>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">验证码：</div>
	            <div class="col-xs-6">
                    <input type="text" ng-change="clearState()" name="kaptcha" placeholder="验证码" ng-model="kaptcha" required="true" />
	                <span class="input-alert" ng-show="kaptchaError != null">{{kaptchaError}}</span>
	            </div>
	            <div class="col-xs-2">
	               <div style="position: absolute;top: 0px;right: 0px;">
                       <img title="看不清，换一张" src="<%=request.getContextPath()%>/kaptcha" id="kaptchaImage" alt="验证码"
                            class="validateCode" style="cursor: pointer;" />
                    </div>
	            </div>
	          </div>
	      </form>
	    </div>
	    <div class="modal-footer">
	      <div class="text-center">
              <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
              <button type="submit" class="btn" ng-disabled="userAddForm.$invalid" ng-click="sendEmail()">提交</button>
          </div>
	    </div>
	  </div>
	</div>
  </div>
</div>
