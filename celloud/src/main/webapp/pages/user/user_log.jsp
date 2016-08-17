<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_user_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>账户管理</li>
      <li>操作日志</li>
    </ol>
    <div class="content">
      <table class="table table-main">
	    <thead>
	      <tr>
	        <th>操作</th>
	        <th>时间</th>
	        <th>IP地址</th>
	        <th>登录地点</th>
	        <th>浏览器</th>
	        <th>操作系统</th>
	      </tr>
	    </thead>
	    <tbody>
	      <tr ng-repeat="log in dataList.datas">
            <td>{{log.operate}}</td>
            <td>{{log.logDate}}</td>
            <td>{{log.ip}}</td>
            <td>{{log.address}}</td>
            <td>{{log.browser}}</td>
            <td>{{log.os}}</td>
          </tr>
	    </tbody>
	  </table>
	  <ng-include src="'pages/partial/_partial_pagination_common.jsp'"></ng-include>
    </div>
</div>