<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>{{'DATA_MANAGEMENT' | translate}}</li>
    </ol>
    <div class="content">
      <div class="table-opera">
        <div class="table-opera-content">
          <div class="opera-info">
            <shiro:hasPermission name="data:run">
	          <button class="btn data-operate btn-cancel" disabled="disabled" ng-click="runWithProject()"><i class="fa fa-play" aria-hidden="true"></i> {{'RUN' | translate}}</button>
            </shiro:hasPermission>
            <button class="btn data-operate btn-cancel" disabled="disabled" ng-click="deleteData()"><i class="fa fa-play" aria-hidden="true"></i> {{'ARCHIVE' | translate}}</button>
          </div>
          <div class="info-btn-group pull-right">
            <input class="field" type="text" ng-placeholder="{{'SEARCH_FOR_DATA' | translate}}" ng-model="dataCondition" ng-keypress="doSearch($event)"/>
            <a class="action" ng-click="conditionList()">{{'SEARCH' | translate}}</a>
          </div>
        </div>
      </div>
      <div class="table-opera">
        <span class="tips">
             {{'DATA_REMINDER' | translate}}
        </span>
      </div>
      <div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="state">
      	<button type="button" class="close" ng-click="state=false"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
      	<span>{{message}}</span>
      </div>
      <table class="table table-main" ng-init="pageType='data'" id="_data_table">
        <thead>
          <tr>
            <th class="th-checkoutbox">
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="demo-checkbox1" id="data-checkall" onclick="$.dataManager.checkAll(this, 'data-checkone')">
                <span class="info"></span>
              </label>
            </th>
            <th>{{'FILE_NAME' | translate}}</th>
            <th>
               {{'FILE_ALIAS' | translate}}
              <a ng-show="column=='anotherName' && order=='asc'" ng-click="sortQuery('anotherName','asc')" href="javascript:void(0);">
                  <i class="fa fa-sort-amount-asc"></i>
              </a>
              <a ng-show="column=='anotherName' && order=='desc'" ng-click="sortQuery('anotherName','desc')" href="javascript:void(0);">
                  <i class="fa fa-sort-amount-desc"></i>
              </a>
              <a ng-show="column!='anotherName'" ng-click="sortQuery('anotherName','desc')" href="javascript:void(0);">
                  <i class="fa fa-sort" aria-hidden="true"></i>
              </a>
            </th>
            <th>{{'PRODUCT_LABEL' | translate}}</th>
            <th>{{'DATA_LABEL' | translate}}</th>
            <th>{{'FILE_SIZE' | translate}}</th>
            <th>{{'UPLOAD_TIME' | translate}}</th>
            <th>
               {{'RUN_OR_NOT' | translate}}
              <a ng-show="column=='run' && order=='asc'" ng-click="sortQuery('run','asc')" href="javascript:void(0);">
                  <i class="fa fa-sort-amount-asc"></i>
              </a>
              <a ng-show="column=='run' && order=='desc'" ng-click="sortQuery('run','desc')" href="javascript:void(0);">
                  <i class="fa fa-sort-amount-desc"></i>
              </a>
              <a ng-show="column!='run'" ng-click="sortQuery('run','desc')" href="javascript:void(0);">
                  <i class="fa fa-sort" aria-hidden="true"></i>
              </a>
            </th>
            <th>{{'OPERAT' | translate}}</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="file in dataList.datas">
            <td>
              <label class="checkbox-lable">
                <input class="checkbox" is_run="{{file.isRunning==1}}" is_tag="{{file.tagName==null}}" is_bsi="{{file.tagName=='百菌探'}}" is_rocky="{{file.tagName=='华木兰'}}" is_pair="{{file.tagName=='CMP'||file.tagName=='CMP_199'||file.tagName=='GDD'||file.tagName=='split'||file.tagName=='AccuSeqα'||file.tagName=='AccuSeqα199'||file.tagName=='AccuSeqΩ'||file.tagName=='AccuSeqα2'||file.tagName=='AccuSeqΩ-exon'}}" type="checkbox" name="data-checkone" value="{{file.fileId}}" onclick="$.dataManager.checkOneData(this)">
                <span class="info"></span>
              </label>
            </td>
            <td title="{{file.fileName}}"><i class="fa fa-truck" aria-hidden="true" ng-show="file.isRunning==1 || file.isRun == 1"></i> {{file.fileName.length > 23 ? file.fileName.substring(0,23) + '...' : file.fileName}}</td>
            <td title="{{file.anotherName}}">{{file.anotherName.length > 23 ? file.anotherName.substring(0,23) + '...' : file.anotherName}}</td>
            <td>{{file.tagName}}</td>
            <td title="{{file.batch}}">{{file.batch}}</td>
            <td>{{file.size | fileSizeFormat}}</td>
            <td>{{file.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td ng-if="file.reportNum < 1" style="font-weight: bold;">{{'NOT_RUN' | translate}}</td>
            <td ng-if="file.reportNum > 0">{{'RUNNING' | translate}}</td>
            <td><a href="javascript:void(0)" ng-click="toEditData(file.fileId)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a></td>
          </tr>
          <tr ng-show="dataList.datas.length == 0">
          	<td colspan="9">{{'NO_DATA' | translate}}</td>
          </tr>
        </tbody>
      </table>
      <pagination page="dataList.page" change="pageQuery(page,pageSize)"></pagination>
    </div>
  <div id="data-detail-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-md">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">{{'DATA_MODIFICATION' | translate}}</h4>
	    </div>
	    <div class="modal-body form-modal">
	      <form class="form-horizontal info-form" name="editDataForm" id="editDataForm">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">{{'FILE_NAME' | translate}}：</div>
	            <div class="col-xs-9">
	                <input type="text" ng-model="dataFile.fileName" ng-readonly="true">
	                <input type="hidden" ng-model="dataFile.fileId" ng-readonly="true">
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">{{'FILE_ALIAS' | translate}}：</div>
	            <div class="col-xs-9">
	                <input type="text" ng-placeholder="{{'PLEASE_INPUT' | translate}}{{'FILE_ALIAS' | translate}}" ng-model="dataFile.anotherName" maxlength="50">
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">{{'PRODUCT_LABEL' | translate}}：</div>
	            <div class="col-xs-9 form-group-content">
	              <select class="checkbox-group" ng-model="appSelected" ng-options="app.tagName for app in appList"></select>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">{{'DATA_LABEL' | translate}}：</div>
	            <div class="col-xs-9">
	                <input type="text"  ng-placeholder="{{'PLEASE_INPUT' | translate}}{{'DATA_LABEL' | translate}}" name="batch" maxlength="30" ng-model="dataFile.batch" required=""/><span class="invoice-modal-error"></span>
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
              <button type="reset" class="btn btn-cancel" data-dismiss="modal">{{'CANCLE' | translate}}</button>
              <button type="submit" class="btn" ng-disabled="editDataForm.$invalid" ng-click="submitEditData()">{{'SUBMIT' | translate}}</button>
          </div>
	    </div>
	  </div>
	</div>
  </div>
</div>
