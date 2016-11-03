<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="base">
  <h2>基本信息<span>（<strong>*</strong>必填项）</span></h2>
  <form class="info-form" name="userForm" novalidate="novalidate" ng-submit="updateUserInfo()">
    <div class="info-form-group">
      <label>地址:</label>
      <div>
          <select id="s_province" name="s_province" ng-model="province"></select>  
          <select id="s_city" name="s_city" ng-model="city"></select>  
          <select id="s_county" name="s_county" ng-model="county"></select>
      </div>
    </div>
    <div class="info-form-group">
      <label>&nbsp;<strong class="pull-right">*</strong></label>
      <div>
          <input name="cellphone" type="text" id="input-phone" ng-model="user.cellphone" placeholder="详细地址" />
          <span class="input-alert" ng-show="userForm.cellphone.$dirty && userForm.cellphone.$invalid">
             详细地址不能为空
          </span>
      </div>
    </div>
    <div class="info-form-group">
      <label>邮编:</label>
      <div>
          <input name="cellphone" type="text" id="input-phone" ng-model="user.cellphone" placeholder="邮编" />
      </div>
    </div>
    <div class="info-form-group">
      <label>姓名:<strong class="pull-right">*</strong></label>
      <div>
          <input name="cellphone" type="text" id="input-phone" ng-model="user.cellphone" placeholder="姓名" />
          <span class="input-alert" ng-show="userForm.cellphone.$dirty && userForm.cellphone.$invalid">
              姓名不能为空
          </span>
      </div>
    </div>
    <div class="info-form-group">
      <label>年龄:<strong class="pull-right">*</strong></label>
      <div>
          <input name="cellphone" type="text" id="input-phone" ng-model="user.cellphone" placeholder="年龄" />
          <span class="input-alert" ng-show="userForm.cellphone.$dirty && userForm.cellphone.$invalid">
              年龄不能为空
          </span>
      </div>
    </div>
    <div class="info-form-group">
      <label>性别:<strong class="pull-right">*</strong></label>
      <div>
          <label class="radio-lable">
            <input class="radio" type="radio" name="pay_type" ng-click="pay_type = 'online'" checked>
            <span class="info"></span>
          </label>
               男
          <label class="radio-lable">
            <input class="radio" type="radio" name="pay_type" ng-click="pay_type = 'transfer'">
            <span class="info"></span>
          </label>
               女
          <span class="input-alert" ng-show="userForm.cellphone.$dirty && userForm.cellphone.$invalid">
              性别不能为空
          </span>
      </div>
    </div>
    <div class="info-form-group">
      <label>手机:<strong class="pull-right">*</strong></label>
      <div>
          <input name="cellphone" type="text" id="input-phone" ng-model="user.cellphone" ng-pattern="/^((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))\d{8}$/" />
          <span class="input-alert" ng-show="userForm.cellphone.$dirty && userForm.cellphone.$invalid">
             手机号码不能为空
          </span>
      </div>
    </div>
    <div class="info-form-group">
      <label></label>
      <div>
          <button type="submit" class="btn btn-cancel" ng-disabled="userForm.$invalid">保存</button>
          <button type="submit" class="btn btn-black" ng-disabled="userForm.$invalid">保存并付费</button>
      </div>
    </div>
  </form>
</div>