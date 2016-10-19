<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_user_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>账户管理</li>
      <li>修改邮箱</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p>完善基本信息，修改邮箱。</p>
      </div>
      <div class="content-body">
        <form class="info-form" name="emailForm" novalidate="novalidate" ng-submit="updateEmail()">
          <div class="info-form-group">
            <label>邮箱地址:</label>
            <div>
                <input name="email" class="email" type="text" ng-model="user.email" readonly="readonly" />
                <span class="input-alert" ng-show="sendSuccess">
					邮件已发送，请进入邮箱处理
				</span>
				<span class="input-alert" ng-show="sendFail">
					发送失败，请联系管理员
				</span>
            </div>
          </div>
          <div class="info-form-group">
            <label></label>
            <div>
                <button type="submit" class="btn">修改</button>
            </div>
          </div>
        </form>
      </div>
    </div>
</div>