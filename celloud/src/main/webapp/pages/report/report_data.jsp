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
	            <li><a id="fullDateQuery0" ng-class="{active: dataOptions.fullDate == 0 }" class="fullDateQuery" ng-click="fullDateQuery(0)" href="javascript:void(0)">全部</a></li>
	            <li><a id="fullDateQuery1" ng-class="{active: dataOptions.fullDate == 1 }" class="fullDateQuery" ng-click="fullDateQuery(1)" href="javascript:void(0)">24h</a></li>
	            <li><a id="fullDateQuery3" ng-class="{active: dataOptions.fullDate == 3 }" class="fullDateQuery" ng-click="fullDateQuery(3)" href="javascript:void(0)">3d</a></li>
	            <li><a id="fullDateQuery7" ng-class="{active: dataOptions.fullDate == 7 }" class="fullDateQuery" ng-click="fullDateQuery(7)" href="javascript:void(0)">7d</a></li>
	            <li><a id="fullDateQuery15" ng-class="{active: dataOptions.fullDate == 15 }" class="fullDateQuery" ng-click="fullDateQuery(15)" href="javascript:void(0)">15d</a></li>
	            <li><a id="fullDateQuery30" ng-class="{active: dataOptions.fullDate == 30 }" class="fullDateQuery" ng-click="fullDateQuery(30)" href="javascript:void(0)">30d</a></li>
              </ul>
              <div class="search-btns">
                <input type="text" ng-if="dataOptions.fullDate == -1" ng-model="dataOptions.beginDate" class="Wdate input" onclick="WdatePicker()" readonly="readonly" id="begin-date">
                <input type="text" ng-if="dataOptions.fullDate != -1" class="Wdate input" onclick="WdatePicker()" readonly="readonly" id="begin-date">
                <span>-</span>
                <input type="text" ng-if="dataOptions.fullDate == -1" ng-model="dataOptions.endDate" class="Wdate input" onclick="WdatePicker()" readonly="readonly" id="end-date">
                <input type="text" ng-if="dataOptions.fullDate != -1" class="Wdate input" onclick="WdatePicker()" readonly="readonly" id="end-date">
                <button class="btn btn-cancel" ng-click="chooseDate()">确定</button>
              </div>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>产品标签：</label>
            <div class="search-type-detail inline-detail {{reportMoreAppTag|chevronTypeDivFilter}}" ng-init="reportMoreAppTag=true">
              <ul class="search-info">
                <li><a id="tagsQuerynull" ng-class="{active: dataOptions.tagId == 'all'}" class="tagsQuery" href="javascript:void(0)" ng-click="tagsQuery('all')">全部</a></li>
                <li ng-repeat="tag in searchInfo.tags">
                    <a id="tagsQuery{{tag.tagId}}" ng-class="{active: dataOptions.tagId == tag.tagId}" class="tagsQuery" ng-click="tagsQuery(tag.tagId)" href="javascript:void(0)">{{tag.tagName}}</a>
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
                <li><a id="batchsQuerynull" ng-class="{active: dataOptions.batch == 'all'}" class="batchsQuery" href="javascript:void(0)" ng-click="batchsQuery('all')">全部</a></li>
                <li ng-repeat="batch in searchInfo.batchs">
                    <a id="batchsQuery{{batch}}" ng-class="{active: dataOptions.batch == batch}" class="batchsQuery" href="javascript:void(0)" ng-click="batchsQuery(batch)">{{batch}}</a>
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
                <li><a id="periodQuerynull" ng-class="{active: dataOptions.period == 'all'}" class="periodQuery" href="javascript:void(0)" ng-click="periodQuery('all')">全部</a></li>
                <li><a id="periodQuery2" ng-class="{active: dataOptions.period == '2'}" class="periodQuery" href="javascript:void(0)" ng-click="periodQuery(2)">完成</a></li>
                <li><a id="periodQuery1" ng-class="{active: dataOptions.period == '1'}" class="periodQuery" href="javascript:void(0)" ng-click="periodQuery(1)">分析中</a></li>
                <li><a id="periodQuery0" ng-class="{active: dataOptions.period == '0'}" class="periodQuery" href="javascript:void(0)" ng-click="periodQuery(0)">等待分析</a></li>
                <li><a id="periodQuery3" ng-class="{active: dataOptions.period == '3'}" class="periodQuery" href="javascript:void(0)" ng-click="periodQuery(3)">数据不完整</a></li>
                <li><a id="periodQuery4" ng-class="{active: dataOptions.period == '4'}" class="periodQuery" href="javascript:void(0)" ng-click="periodQuery(4)">异常终止</a></li>
<!--                 <li><a id="periodQuery6" class="periodQuery" href="javascript:void(0)" ng-click="periodQuery(6)">实验中</a></li> -->
<!--                 <li><a id="periodQuery5" class="periodQuery" href="javascript:void(0)" ng-click="periodQuery(5)">送样中</a></li> -->
              </ul>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>样本编码：</label>
            <div class="search-type-detail">
              <input ng-change="conditionQuery()" ng-model="dataOptions.condition" type="text" placeholder="扫码或输入编号">
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
          <tr ng-repeat="task in reportList.datas" class="{{task.read | reportReadFilter}} {{task.dataKey==thisReport | thisReportFilter}}">
            <td>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="demo-checkbox1" ng-disabled="file.isRunning==1||file.tagName==null">
                <span class="info"></span>
              </label>
            </td>
            <td>{{task.fileName}}</td>
            <td>{{task.sampleName}}</td>
            <td>{{task.tagName}}</td>
            <td>{{task.batch}}</td>
            <td>{{task.updateDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td>{{task.period | taskPeriodFilter}}</td>
            <td>
              <a ng-if="task.period == 1" ng-class="{disabled:task.period == 1}"><i class="fa fa-eye" aria-hidden="true"></i></a>
              <a ng-if="task.period == 2" href="#/reportdata/{{task.englishName == '华木兰' ? 'rocky' : task.englishName}}/{{task.appId}}/{{task.dataKey}}/{{task.projectId}}"><i class="fa fa-eye" aria-hidden="true"></i></a>
            </td>
          </tr>
        </tbody>
      </table>
      <pagination page="reportList.page" change="pageQuery(page,pageSize)"></pagination>
    </div>
</div>
