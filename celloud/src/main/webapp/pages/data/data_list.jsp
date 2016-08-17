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
            <td>{{file.createDate| date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a href="javascript:void(0)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a></td>
          </tr>
        </tbody>
      </table>
      <ng-include src="'pages/partial/_partial_pagination_common.jsp'" ></ng-include>
    </div>
</div>
