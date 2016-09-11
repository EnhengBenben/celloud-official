<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>报告管理</li>
    </ol>
    <div class="content">
      <form class="search-box-form">
      <div class="search-box">
        <ul class="search-type-list">
          <li class="search-type clearfix">
            <label>时&emsp;&emsp;间：</label>
            <div class="search-type-detail times">
              <ul class="search-info seartch-date">
	            <li><a class="active" ng-click="fullDateQuery(0)" href="javascript:void(0)">全部</a></li>
	            <li><a ng-click="fullDateQuery(1)" href="javascript:void(0)">24h</a></li>
	            <li><a ng-click="fullDateQuery(3)" href="javascript:void(0)">3d</a></li>
	            <li><a ng-click="fullDateQuery(7)" href="javascript:void(0)">7d</a></li>
	            <li><a ng-click="fullDateQuery(15)" href="javascript:void(0)">15d</a></li>
	            <li><a ng-click="fullDateQuery(30)" href="javascript:void(0)">30d</a></li>
              </ul>
              <div class="search-btns">
                <input type="text" class="Wdate input" onclick="WdatePicker()" readonly="readonly" id="begin-date">
                <span>-</span>
                <input type="text" class="Wdate input" onclick="WdatePicker()" readonly="readonly" id="end-date">
                <button class="btn btn-cancel" ng-click="chooseDate()">确定</button>
              </div>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>产品标签：</label>
            <div class="search-type-detail inline-detail {{reportMoreAppTag|chevronTypeDivFilter}}" ng-init="reportMoreAppTag=true">
              <ul class="search-info">
                <li><a class="active" href="javascript:void(0)" ng-click="tagsQuery(null)">全部</a></li>
                <li ng-repeat="tag in searchInfo.tags">
                    <a ng-click="tagsQuery(tag.tagId)" href="javascript:void(0)">{{tag.tagName}}</a>
                </li>
              </ul>
              <div class="search-btns">
                <button class="btn chevron-btn" ng-click="reportMoreAppTag=changeChevronType(reportMoreAppTag)">{{reportMoreAppTag|chevronTypeTextFilter}}<i ng-class="reportMoreAppTag|chevronTypeFaFilter" aria-hidden="true"></i></button>
              </div>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>数据标签：</label>
            <div class="search-type-detail inline-detail {{reportMoreDataTag|chevronTypeDivFilter}}" ng-init="reportMoreDataTag=true">
              <ul class="search-info">
                <li><a class="active" href="javascript:void(0)" ng-click="batchsQuery(null)">全部</a></li>
                <li ng-repeat="batch in searchInfo.batchs">
                    <a href="javascript:void(0)" ng-click="batchsQuery(batch)">{{batch}}</a>
                </li>
              </ul>
	          <div class="search-btns">
	            <button class="btn chevron-btn" ng-click="reportMoreDataTag=changeChevronType(reportMoreDataTag)">{{reportMoreDataTag|chevronTypeTextFilter}}<i ng-class="reportMoreDataTag|chevronTypeFaFilter" aria-hidden="true"></i></button>
	          </div>
            </div>
          </li>
          <li class="search-type seartch-period clearfix">
            <label>状&emsp;&emsp;态：</label>
            <div class="search-type-detail">
              <ul class="search-info">
                <li><a class="active" href="javascript:void(0)" ng-click="periodQuery(null)">全部</a></li>
                <li><a href="javascript:void(0)" ng-click="periodQuery(2)">完成</a></li>
                <li><a href="javascript:void(0)" ng-click="periodQuery(1)">分析中</a></li>
                <li><a href="javascript:void(0)" ng-click="periodQuery(0)">等待分析</a></li>
                <li><a href="javascript:void(0)" ng-click="periodQuery(3)">数据上传中</a></li>
                <li><a href="javascript:void(0)" ng-click="periodQuery(4)">异常终止</a></li>
                <li><a href="javascript:void(0)" ng-click="periodQuery(6)">实验中</a></li>
                <li><a href="javascript:void(0)" ng-click="periodQuery(5)">送样中</a></li>
              </ul>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>样本编码：</label>
            <div class="search-type-detail">
              <input type="text" placeholder="扫码或输入编号">
            </div>
          </li>
        </ul>
        </form>
      </div>
      <table class="table table-main">
        <thead>
          <tr>
            <th class="th-checkoutbox">
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="demo-checkbox1">
                <span class="info"></span>
              </label>
            </th>
            <th>文件名称</th>
            <th>样品编号</th>
            <th>产品标签</th>
            <th>数据标签</th>
            <th>更新时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="task in reportList.datas">
            <td>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="demo-checkbox1" ng-disabled="file.isRunning==1||file.tagName==null">
                <span class="info"></span>
              </label>
            </td>
            <td>{{task.fileName}}</td>
            <td>{{task.dataKey}}</td>
            <td>{{task.tagName}}</td>
            <td>{{task.batch}}</td>
            <td>{{task.updateDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td>{{task.period == 1 ? '正在运行' : '运行完成'}}</td>
            <td>
              <a ng-disabled="task.period == 1" href="#/reportdata/{{task.appName == '华木兰' ? 'rocky' : task.appName}}/{{task.appId}}/{{task.dataKey}}/{{task.projectId}}"><i class="fa fa-eye" aria-hidden="true"></i></a>
            </td>
          </tr>
        </tbody>
      </table>
      <pagination page="reportList.page" change="pageQuery(page,pageSize)"></pagination>
    </div>
</div>
