<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>数据管理</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p>完善基本信息，统计邮箱及手机绑定。</p>
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
                <input class="checkbox" type="checkbox" name="demo-checkbox1">
                <span class="info"></span>
              </label>
            </td>
            <td>{{file.fileName}}</td>
            <td></td>
            <td></td>
            <td></td>
            <td>{{file.createDate}}</td>
            <td></td>
          </tr>
        </tbody>
      </table>
      <ng-include src="'pages/partial/_partial_pagination_common.jsp'" ></ng-include>
    </div>
</div>