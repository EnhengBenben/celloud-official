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
        <form class="info-form company" id="baseForm" name="baseForm" novalidate="novalidate" ng-submit="updateCompanyInfo()">
          <div class="info-form-group">
            <label>医院名称:</label>
            <div>
                <input title="{{company.companyName}}" name="companyName" type="text" id="input-name" ng-model="company.companyName" required="true" />
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
            <div>
			    <select id="s_province" name="s_province" ng-model="province"></select>  
			    <select id="s_city" name="s_city" ng-model="city"></select>  
    		    <select id="s_county" name="s_county" ng-model="district"></select>
            </div>
            <span class="input-alert" ng-show="province == '省份' || city == '地级市' || district == '市、县级市'">
                    医院地址不能为空
            </span>
		  </div>
		  <div class="info-form-group">
            <label>详细地址:</label>
            <div>
                <input name="address" type="text" id="input-address" ng-model="company.address" required="true" />
                <span class="input-alert" ng-show="baseForm.address.$dirty && baseForm.address.$invalid">
                    医院详细地址不能为空
                </span>
            </div>
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
          <div class="info-form-group">
            <label style="vertical-align: top;">医院LOGO:</label>
            <div class="bottom-btn">
		        <div id="clipArea" class="clipArea"></div>
				<br/>
				<button type="button" class="btn" ng-click="reset()">重置</button>
                <button type="submit" class="btn" ng-disabled="baseForm.$invalid || province == '省份' || city == '地级市' || district == '市、县级市'">提交</button>
                <div style="float:left;position: relative;">
    				<button type="button" class="btn -middle" id="chooseFile">选择图片</button>
    				<input type="file" id="file" class="file">
                </div>
				<button type="button" class="btn -middle" id="clipBtn">截取</button>
				<div id="view" class="view"></div>
            </div>
          </div>
        </form>
      </div>
    </div>
</div>