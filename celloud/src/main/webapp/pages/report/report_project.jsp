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
	            <button class="btn"><i class="fa fa-play" aria-hidden="true"></i>runWithProject:button运行</button>
			</shiro:hasPermission>
          	<shiro:hasPermission name="runWithData:button">
	            <button class="btn"><i class="fa fa-play" aria-hidden="true"></i>runWithData:button运行</button>
			</shiro:hasPermission>
          </div>
          <div class="info-btn-group pull-right">
            <input class="field" type="text" placeholder="搜索" />
            <a class="action">搜索</a>
          </div>
        </div>
      </div>
      <table class="table table-main">
        <thead>
          <tr>
            <th>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="demo-checkbox1">
                <span class="info"></span>
              </label>
            </th>
            <th>文件名称</th>
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
                <input class="checkbox" type="checkbox" name="demo-checkbox1" ng-disabled="file.isRunning==1||file.tagName==null">
                <span class="info"></span>
              </label>
            </td>
            <td>{{file.fileName}}<i class="fa fa-truck" aria-hidden="true" ng-show="file.isRunning==1"></i></td>
            	<!-- TODO show more -->
            <td>{{file.appId}}--{{file.tagName}}--{{file.isRunning}}--{{file.fileId}}</td>
            <td>{{file.batch}}</td>
            <td>{{file.size}}</td>
            <td>{{file.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a href="javascript:void(0)" data-toggle="modal" data-target="#data-detail-modal"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a></td>
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
          <h4 class="modal-title">申请发票</h4>
        </div>
        <div class="modal-body form-modal">
          <form class="form-horizontal info-form">
              <div class="form-group">
                <div class="control-label form-label col-xs-3">文件名称：</div>
                <div class="col-xs-9">
                    <input type="text" name="money" id="money" readonly>
                    <span class="input-alert break-line">文件名不能为空</span>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-3">产品标签：</div>
                <div class="col-xs-9 form-group-content">
                  <div class="checkbox-group">
                    <label class="checkbox-lable">
                      <input class="checkbox" type="checkbox" name="demo-checkbox1" checked>
                      <span class="info"></span>
                    </label>
	                 HBV
                  </div>
                  <div class="checkbox-group">
                    <label class="checkbox-lable">
                      <input class="checkbox" type="checkbox" name="demo-checkbox1" checked>
                      <span class="info"></span>
                    </label>
                    HCV
                  </div>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-3">数据标签：</div>
                <div class="col-xs-9">
                    <input type="text" id="address" name="address" maxlength="45"/><span class="invoice-modal-error"></span>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-3">数据状态：</div>
                <div class="col-xs-9 form-group-content">
                  <div class="checkbox-group">
                    <label class="checkbox-lable">
                      <input class="checkbox" type="checkbox" name="demo-checkbox1" checked>
                      <span class="info"></span>
                    </label>
                     未归档
                  </div>
                  <div class="checkbox-group">
                    <label class="checkbox-lable">
                      <input class="checkbox" type="checkbox" name="demo-checkbox1" checked>
                      <span class="info"></span>
                    </label>
                    归档
                  </div>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-3">上传时间：</div>
                <div class="col-xs-9 form-group-content">
                  2016-05-21 13:49:00
                </div>
              </div>
              <div class="form-group">
                <div class="text-center">
                    <button type="reset" class="btn btn-cancel">取消</button>
                    <button type="submit" class="btn" >提交</button>
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
