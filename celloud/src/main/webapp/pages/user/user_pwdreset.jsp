<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_user_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>账户管理</li>
      <li>修改密码</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p>完善基本信息，统计邮箱及手机绑定。</p>
      </div>
      <div class="content-body">
        <form class="info-form" name="pwdForm" novalidate="novalidate" ng-submit="updatePassword()">
          <div class="info-form-group">
            <label>原密码:</label>
            <div>
                <input name="oldPwd" type="password" ng-model="oldPwd" required />
               	<span class="input-alert" ng-show="pwdForm.oldPwd.$invalid">原始密码不能为空!</span>
               	<span class="input-alert" ng-show="code==203">{{message}}</span>
            </div>
          </div>
          <div class="info-form-group">
            <label>新密码:</label>
            <div>
                <input name="newPwd" type="password" ng-model="newPwd" ng-pattern="/^[\d\w_]{6,16}$/" required />
               	<span class="input-alert" ng-show="pwdForm.newPwd.$invalid">密码为6-16位的字母、数字及下划线组合!</span>
            </div>
          </div>
          <div class="info-form-group">
            <label>确认密码:</label>
            <div>
                <input name="confirmPwd" type="password" ng-model="confirmPwd" />
               	<span class="input-alert" ng-show="newPwd!=confirmPwd">确认密码与新密码不一致!</span>
            </div>
          </div>
          <div class="info-form-group">
            <label></label>
            <div>
                <button class="btn btn-cancel" ng-click="reset()">重置</button>
                <button type="submit" class="btn" ng-disabled="newPwd!=confirmPwd||pwdForm.$invalid">提交</button>
            </div>
          </div>
        </form>
      </div>
    </div>
</div>