<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_sample_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>样本管理</li>
      <li>样本追踪</li>
    </ol>
    <div class="content sample">
        <div class="table-opera">
          <div class="table-opera-content clearfix">
	          <div class="info-btn-group pull-right">
	            <input class="field" type="text" placeholder="搜索样本编号" ng-model="sampleName" ng-keypress="doOnKeyPress($event)"/>
	            <a class="action" ng-click="sampleNameQuery()">搜索</a>
	          </div>
	        </div>
        </div>
         <table class="table table-main">
           <thead>
               <tr>
                   <th>订单号</th>
                   <th>样本编号</th>
                   <th>姓名</th>
                   <th>性别</th>
                   <th>年龄</th>
                   <th>实验样本编号</th>
                   <th>检测项目</th>
                   <th>样本类型</th>
                   <th>更新时间</th>
                   <th>状态</th>
               </tr>
           </thead>
           <tbody>
               <tr ng-repeat="sample in sampleList.datas">
                   <td>{{sample.orderNo }}</td>
                   <td>{{sample.sampleName }}</td>
                   <td>{{sample.name }}</td>
                   <td>{{sample.gender == 0 ? '女' : '男' }}</td>
                   <td>{{sample.age }}</td>
                   <td>{{sample.experSampleName }}</td>
                   <td>{{sample.tagName }}</td>
                   <td>{{sample.type }}</td>
                   <td class="createDate">{{sample.updateDate | date : 'yyyy-MM-dd HH:mm:ss' }}</td>
                   <td>{{sample.experState | experStateFilter}}</td>
               </tr>
           </tbody>
         </table>
         <pagination page="sampleList.page" change="pageQuery(page,pageSize)"></pagination>
    </div>
</div>