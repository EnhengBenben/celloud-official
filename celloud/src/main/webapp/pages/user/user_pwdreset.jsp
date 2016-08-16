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
        <form class="info-form" name="pwdForm" novalidate="novalidate">
          <div class="info-form-group">
            <label>原密码:</label>
            <div>
                <input name="oldPwd" type="text" ng-model="oldPwd" ng-pattern="/^\d{3}$/" required />
                <span class="input-alert" ng-show="pwdForm.oldPwd.$dirty && pwdForm.oldPwd.$invalid">用户名不能为空</span>
            </div>
          </div>
          <div class="info-form-group">
            <label>新密码:</label>
            <div>
                <input name="newPwd" type="text" ng-model="newPwd" />
				<span class="input-alert" ng-show="userForm.cellphone.$dirty && userForm.cellphone.$invalid">用户名不能为空</span>
            </div>
          </div>
          <div class="info-form-group">
            <label>确认密码:</label>
            <div>
                <input name="confirmPwd" type="text" ng-model="confirmPwd" />
                <span style="color:red">
					请输入正确的手机号
				</span>
				<span class="input-alert" ng-show="userForm.cellphone.$dirty && userForm.cellphone.$invalid">用户名不能为空</span>
            </div>
          </div>
          <div class="info-form-group">
            <label></label>
            <div>
                <button type="reset" class="btn btn-cancel">重置</button>
                <button type="submit" class="btn">提交</button>
            </div>
          </div>
        </form>
      </div>
    </div>
</div>