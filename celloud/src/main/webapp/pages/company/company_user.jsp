<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
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
          <div class="info-btn-group pull-right">
            <input class="field" type="text" placeholder="搜索文件名/数据标签/文件别名" ng-model="dataCondition" ng-keypress="doSearch($event)"/>
            <a class="action" ng-click="conditionList()">搜索</a>
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
          </tr>
          <tr ng-show="user.datas.length == 0">
          	<td colspan="9">暂无数据</td>
          </tr>
        </tbody>
      </table>
      <pagination page="userList.page" change="pageQuery(page,pageSize)"></pagination>
    </div>
  <!-- <div id="data-detail-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">数据修改</h4>
	    </div>
	    <div class="modal-body form-modal">
	      <form class="form-horizontal info-form" name="editDataForm" id="editDataForm">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">文件名称：</div>
	            <div class="col-xs-9">
	                <input type="text" ng-model="dataFile.fileName" ng-readonly="true">
	                <input type="hidden" ng-model="dataFile.fileId" ng-readonly="true">
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">文件别名：</div>
	            <div class="col-xs-9">
	                <input type="text" placeholder="请输入文件别名" ng-model="dataFile.anotherName" maxlength="50">
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">产品标签：</div>
	            <div class="col-xs-9 form-group-content">
	              <select class="checkbox-group" ng-model="appSelected" ng-options="app.tagName for app in appList"></select>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">数据标签：</div>
	            <div class="col-xs-9">
	                <input type="text" placeholder="请输入数据标签" name="batch" maxlength="50" ng-model="dataFile.batch" required=""/><span class="invoice-modal-error"></span>
	            </div>
	          </div>
            <div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="updateState">
              <button type="button" class="close" ng-click="updateState=false"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
              <span>{{updateMessage}}</span>
            </div>
	      </form>
	    </div>
	    <div class="modal-footer">
	      <div class="text-center">
              <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
              <button type="submit" class="btn" ng-disabled="editDataForm.$invalid" ng-click="submitEditData()">提交</button>
          </div>
	    </div>
	  </div>
	</div>
  </div> -->
</div>
