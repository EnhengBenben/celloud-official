<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<ng-include src="'pages/partial/_partial_notice_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>消息管理</li>
      <li>消息提醒</li>
    </ol>
    <div class="content">
      <div class="table-opera">
        <div class="table-opera-content">
          <div class="only-btn">
            <button class="btn -low"><i class="fa fa-folder-open" aria-hidden="true"></i>已读</button>
            <button class="btn btn-cancel -low"><i class="fa fa-times" aria-hidden="true"></i>删除</button>
            <button class="btn btn-cancel -low"><i class="fa fa-folder-open" aria-hidden="true"></i>全部已读</button>
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
            <th>状态</th>
            <th>标题</th>
            <th>类型</th>
            <th>时间</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="demo-checkbox1" ng-disabled="file.isRunning==1||file.tagName==null">
                <span class="info"></span>
              </label>
            </td>
            <td><a><i class="fa fa-folder" aria-hidden="true"></i></a></td>
            <td></td>
            <td></td>
            <td>2016-05-05 12:12:12</td>
          </tr>
          <tr>
            <td>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="demo-checkbox1" ng-disabled="file.isRunning==1||file.tagName==null">
                <span class="info"></span>
              </label>
            </td>
            <td><a><i class="fa fa-folder-open" aria-hidden="true"></i></a></td>
            <td></td>
            <td></td>
            <td>2016-05-05 12:12:12</td>
          </tr>
        </tbody>
      </table>
      <ng-include src="'pages/partial/_partial_pagination_common.jsp'" ></ng-include>
    </div>
</div>
