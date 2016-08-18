<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<ng-include src="'pages/partial/_partial_notice_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>消息管理</li>
      <li>系统公告</li>
    </ol>
    <div class="content">
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
            <th>内容</th>
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
