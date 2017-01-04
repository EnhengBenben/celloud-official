<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_user_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>账户管理</li>
      <li>基本信息</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p>完善基本信息，修改手机。</p>
      </div>
      <div class="content-body">
        <form class="info-form" name="userForm" novalidate="novalidate" ng-submit="authenticationCellphone()">
          <div class="info-form-group">
            <label>用户邮箱:</label>
            <div>
                <span style="font-size: 14px;">{{user.email}}</span>
            </div>
          </div>
          <div class="info-form-group">
            <label>手机号码:</label>
            <div>
                <input name="cellphone" type="text" id="input-phone" ng-model="user.cellphone" ng-pattern="/^((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))\d{8}$/" required />
                <span class="input-alert" ng-show="userForm.cellphone.$dirty && userForm.cellphone.$invalid">
					请输入正确的手机号
				</span>
            </div>
          </div>
          <div class="info-form-group">
            <label>验证码:</label>
            <div>
                <input style="width:107px;" name="captcha" type="text" id="input-captcha" ng-model="captcha" required />
                <span><button id="captchaButton" style="margin-bottom: 3px;" type="button" ng-click="sendCaptcha()" class="btn" ng-disabled="userForm.cellphone.$invalid">获取验证码</button></span>
            </div>
          </div>
          <div class="info-form-group">
            <label></label>
            <div>
                <button type="button" ng-click="reset()" class="btn btn-cancel">重置</button>
                <button type="submit" class="btn"
                	ng-disabled="userForm.$invalid">提交</button>
            </div>
            <div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="state" ng-cotroller="alertController">
		      <button type="button" class="close" ng-click="state=false"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
		      <span>{{message}}</span>
		    </div>
          </div>
        </form>
      </div>
    </div>
</div>