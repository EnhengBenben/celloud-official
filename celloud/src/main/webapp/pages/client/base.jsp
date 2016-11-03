<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="base">
  <h2>基本信息<span>（<strong>*</strong>必填项）</span></h2>
  <form class="info-form" name="userForm" novalidate="novalidate" ng-submit="updateUserInfo()">
    <div class="info-form-group">
      <label>地址:</label>
      <div>
          <select id="s_province" name="s_province" ng-model="province"></select>  
          <select id="s_city" name="s_city" ng-model="city"></select>  
          <select id="s_county" name="s_county" ng-model="district"></select>
      </div>
    </div>
    <div class="info-form-group">
      <label>&nbsp;<strong class="pull-right">*</strong></label>
      <div>
          <input name="address" type="text" ng-model="user.address" placeholder="详细地址" required="true"/>
          <span class="input-alert" ng-show="userForm.address.$dirty && userForm.address.$invalid">
             详细地址不能为空
          </span>
      </div>
    </div>
    <div class="info-form-group">
      <label>邮编:</label>
      <div>
          <input name="zipCode" type="text" ng-model="user.zipCode" placeholder="邮编" />
      </div>
    </div>
    <div class="info-form-group">
      <label>姓名:<strong class="pull-right">*</strong></label>
      <div>
          <input name="truename" type="text" id="input-phone" ng-model="user.truename" placeholder="姓名" required="true"/>
          <span class="input-alert" ng-show="userForm.truename.$dirty && userForm.truename.$invalid">
              姓名不能为空
          </span>
      </div>
    </div>
    <div class="info-form-group">
      <label>年龄:<strong class="pull-right">*</strong></label>
      <div>
          <input name="age" type="number" ng-model="user.age" placeholder="年龄" required="true" min="0" max="100" />
          <span class="input-alert" ng-show="userForm.age.$dirty && userForm.age.$invalid">
              请输入正确的年龄
          </span>
      </div>
    </div>
    <div class="info-form-group">
      <label>性别:<strong class="pull-right">*</strong></label>
      <div>
          <label class="radio-lable">
            <input class="radio" type="radio" ng-checked="{{user.sex== 0}}" name="sex" value="0" checked>
            <span class="info"></span>
          </label>
               男
          <label class="radio-lable">
            <input class="radio" type="radio" ng-checked="{{user.sex== 1}}" name="sex" value="1">
            <span class="info"></span>
          </label>
               女
      </div>
    </div>
    <div class="info-form-group">
      <label>手机:<strong class="pull-right">*</strong></label>
      <div>
          <input name="cellphone" type="text" id="input-phone" ng-model="user.cellphone" ng-pattern="/^((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))\d{8}$/" required="true" />
          <span class="input-alert" ng-show="userForm.cellphone.$dirty && userForm.cellphone.$invalid">
             手机号码格式不正确
          </span>
      </div>
    </div>
    <div class="info-form-group">
      <label></label>
      <div>
          <button type="submit" class="btn btn-cancel" ng-disabled="userForm.$invalid">保存</button>
          <button type="button" class="btn btn-black" ng-disabled="userForm.$invalid" ng-click="updateToPay()">保存并付费</button>
      </div>
    </div>
  </form>
</div>