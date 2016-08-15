<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<aside class="pro-sidebar">
  <section class="s-bar">
    <ul class="pro-sidebar-menu">
      <li class="header">账户管理</li>
      <li>
        <a href="javascript:void(0)"><span>基本信息</span></a>
      </li>
      <li>
        <a href="javascript:void(0)"><span>修改密码</span></a>
      </li>
      <li class="active">
        <a href="javascript:void(0)"><span>操作日志</span></a>
      </li>
      <li>
        <a href="javascript:void(0)"><span>报告设置</span></a>
      </li>
    </ul>
  </section>
</aside>
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
	      <tr>
	        <td>&nbsp;</td>
	        <td></td>
	        <td></td>
	        <td></td>
	        <td></td>
	        <td></td>
	      </tr>
	      <tr>
            <td>&nbsp;</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
          </tr>
	    </tbody>
	  </table>
	  <ng-include src="'pages/partial/_partial_pagination_common.jsp'"></ng-include>
    </div>
</div>