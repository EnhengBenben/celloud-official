<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>数据管理</li>
    </ol>
    <div class="content">
      <div class="table-opera">
        <div class="table-opera-content">
          <div class="opera-info">
          	<shiro:hasPermission name="runWithProject:button">
	            <button class="btn data-operate btn-cancel" disabled="disabled" ng-click="runWithProject()"><i class="fa fa-play" aria-hidden="true"></i> 运行</button>
			</shiro:hasPermission>
          	<shiro:hasPermission name="runWithData:button">
	            <button class="btn data-operate btn-cancel" disabled="disabled"><i class="fa fa-play" aria-hidden="true"></i>runWithData:button运行</button>
			</shiro:hasPermission>
            <button class="btn data-operate btn-cancel" disabled="disabled" ng-click="deleteData()"><i class="fa fa-play" aria-hidden="true"></i> 归档</button>
          </div>
          <div class="info-btn-group pull-right">
            <input class="field" type="text" placeholder="搜索文件名/数据标签/文件别名" ng-model="dataCondition"/>
            <a class="action" ng-click="conditionList()">搜索</a>
          </div>
        </div>
      </div>
      <div class="table-opera">
        <span class="tips">
             提醒：没有产品标签的数据和正在运行的数据无法勾选及运行。
        </span>
      </div>
      <table class="table table-main">
        <thead>
          <tr>
            <th>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="demo-checkbox1" onclick="$.dataManager.checkAll($(this), 'data-checkone')">
                <span class="info"></span>
              </label>
            </th>
            <th>文件名称</th>
            <th>文件别名</th>
            <th>产品标签</th>
            <th>数据标签</th>
            <th>数据大小</th>
            <th>上传时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="file in dataList.datas">
            <td>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="data-checkone" value="{{file.fileId}}" onclick="$.dataManager.checkOneData($(this))" ng-disabled="file.isRunning==1||file.tagName==null">
                <span class="info"></span>
              </label>
            </td>
            <td title="{{file.fileName}}">{{file.fileName}}<i class="fa fa-truck" aria-hidden="true" ng-show="file.isRunning==1"></i></td>
            <td>{{file.anotherName}}</td>
            <td>{{file.tagName}}</td>
            <td>{{file.batch}}</td>
            <td>{{file.size}}</td>
            <td>{{file.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a href="javascript:void(0)" data-toggle="modal" data-target="#data-detail-modal" ng-click="toEditData(file.fileId)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a></td>
          </tr>
        </tbody>
      </table>
      <ng-include src="'pages/partial/_partial_pagination_common.jsp'" ></ng-include>
    </div>
  <div id="data-detail-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">数据修改</h4>
	    </div>
	    <div class="modal-body form-modal">
	      <form class="form-horizontal info-form" id="editDataForm">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">文件名称：</div>
	            <div class="col-xs-9">
	                <input type="text" ng-model="dataFile.fileName" ng-readonly="true">
	                <input type="hidden" ng-model="dataFile.fileId" ng-readonly="true">
<!-- 	                <span class="input-alert break-line">文件名不能为空</span> -->
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">文件别名：</div>
	            <div class="col-xs-9">
	                <input type="text" ng-model="dataFile.anotherName">
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">产品标签：</div>
	            <div class="col-xs-9 form-group-content">
	              <div class="checkbox-group" ng-repeat="app in appList">
	                <label class="radio-lable">
	                  <input class="radio" type="radio" name="dataTagName" value="{{app.appName}}" ng-checked="app.appName==dataFile.tagName">
	                  <span class="info"></span>
	                </label>
	              	{{app.appName}}
	              </div>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">数据标签：</div>
	            <div class="col-xs-9">
	                <input type="text" name="batch" maxlength="45" ng-model="dataFile.batch"/><span class="invoice-modal-error"></span>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="text-center">
	                <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
	                <button type="submit" class="btn" ng-click="submitEditData()">提交</button>
	            </div>
	            <div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="state">
	              <button type="button" class="close" ng-click="state=false"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	              <span>{{message}}</span>
	            </div>
	          </div>
	      </form>
	    </div>
	  </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
</div>
