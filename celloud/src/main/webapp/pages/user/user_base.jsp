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
        <p>完善基本信息，统计邮箱及手机绑定。</p>
      </div>
      <div class="content-body">
        <form class="info-form" name="userForm" novalidate="novalidate">
          <div class="info-form-group avatar">
            <label for="photo">头像:</label>
            <div>
              <img src="<%=request.getContextPath()%>/images/avatar/01.png" role="button" class="img-circle" alt="头像图片" title="头像预览，保存后生效"  id="user-image"/>
            </div>
          </div>
          <div class="info-form-group">
            <label>用户名称:</label>
            <div>
                <input name="username" type="text" id="input-username" class="readonly" ng-model="user.username" />
                <span class="input-alert">用户名不能为空</span>
            </div>
          </div>
          <!-- <div class="info-form-group">
            <label>邮箱地址:</label>
            <div>
                <input type="text" class="readonly" value="" />
            </div>
          </div> -->
          <div class="info-form-group">
            <label>手机号码:</label>
            <div>
                <input name="cellphone" type="text" id="input-phone" ng-model="user.cellphone" ng-pattern="/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/" />
                <span class="input-alert" ng-show="userForm.cellphone.$dirty && userForm.cellphone.$invalid">
					请输入正确的手机号
				</span>
            </div>
          </div>
          <div class="info-form-group">
            <label></label>
            <div>
                <button type="reset" class="btn btn-cancel">重置</button>
                <button type="submit" class="btn" ng-click="updateUserInfo()"
                	ng-disabled="userForm.cellphone.$dirty && userForm.cellphone.$invalid">提交</button>
            </div>
            <span ng-show="state">{{message}}</span>
          </div>
        </form>
      </div>
    </div>
</div>