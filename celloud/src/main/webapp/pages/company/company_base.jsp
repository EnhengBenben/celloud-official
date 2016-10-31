<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_company_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>医院管理</li>
      <li>医院基本信息</li>
    </ol>
    <div class="content">
      <!-- <div class="content-header">
        <p>完善基本信息，修改手机。</p>
      </div> -->
      <div class="content-body">
        <form class="info-form" name="baseForm" novalidate="novalidate" ng-submit="updateCompanyBase()">
          <div class="info-form-group">
            <label>医院名称:</label>
            <div>
                <input name="companyName" type="text" id="input-name" ng-model="company.companyName" required="true" />
                <span class="input-alert" ng-show="baseForm.companyName.$dirty && baseForm.companyName.$invalid">
                    医院名称不能为空
                </span>
            </div>
          </div>
          <div class="info-form-group">
            <label>英文名称:</label>
            <div>
                <input name="englishName" type="text" id="input-english-name" ng-model="company.englishName" required="true" />
                <span class="input-alert" ng-show="baseForm.englishName.$dirty && baseForm.englishName.$invalid">
                    医院英文名称不能为空
                </span>
            </div>
          </div>
          <div class="info-form-group">
            <label>医院地址:</label>
		    <select id="s_province" name="s_province" ng-model="province"></select>  
		    <select id="s_city" name="s_city" ng-model="city"></select>  
		    <select id="s_county" name="s_county" ng-model="county"></select>
		</div>
          <div class="info-form-group">
            <label>联系电话:</label>
            <div>
                <input name="tel" type="text" id="input-tel" ng-model="company.tel" ng-pattern="/^((((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))\d{8})|((0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?))$/" />
                <span class="input-alert" ng-show="baseForm.tel.$dirty && baseForm.tel.$invalid">
					请输入正确的联系电话
				</span>
            </div>
          </div>
          <div class="info-form-group">
            <label>邮政编码:</label>
            <div>
                <input name="zipCode" type="text" id="input-zipCode" ng-model="company.zipCode" />
            </div>
          </div>
          <!-- <div class="info-form-group">
            <label>医院LOGO:</label>
            <div>
                <input name="cellphone" type="text" id="input-phone" ng-model="user.cellphone" ng-pattern="/^((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))\d{8}$/" />
                <span style="font-size: 14px;">{{user.username}}</span>
            </div>
          </div> -->
          <div class="info-form-group">
            <label></label>
            <div>
                <button type="button" ng-click="reset()" class="btn btn-cancel">重置</button>
                <button type="submit" class="btn"
                	ng-disabled="baseForm.$invalid">提交</button>
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