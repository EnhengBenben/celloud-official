<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<ng-include src="'pages/partial/_partial_rocky_sidebar.jsp'"></ng-include>
<div class="pro-body rocky">
    <ol class="breadcrumb">
        <li>CelLoud</li>
        <li>我的产品</li>
        <li>华木兰</li>
        <li>数据</li>
    </ol>
	<div class="search-form" style="float: right;z-index: 99">
	    <input style="border-radius: 20px;margin-bottom: 5px;margin-right: 21px;" id="data-condition-input" class="field" ng-keypress="conditionQuery($event)" ng-model="condition" type="text" placeholder="搜索" />
	    <a id="data-condition-find" class="action">
	        <i class="fa fa-search"></i>
	    </a>
	</div>
    <div id="common-container" class="common-container" style="width:98%">
		<div id="rocky-data-list">
	        <table class="table table-main">
	            <thead>
			        <tr>
			            <th class="th-checkoutbox"></th>
			            <th width="140">
			                <input id="data-sample-filter" type="text" placeholder="样本编号/病历号" ng-model="sample" ng-keypress="sampleQuery($event)" >
			            </th>
			            <th>
			                文件名
		                    <a id="dataSortBtn-filename-desc" href="javascript:void(0);" ng-click="sortQuery('filename')">
		                        <i ng-show="params.sortField=='filename'&&params.sortType=='asc'" class="fa fa-sort-amount-asc"></i>
		                        <i ng-show="params.sortField=='filename'&&params.sortType=='desc'" class="fa fa-sort-amount-desc"></i>
		                        <i ng-show="params.sortField!='filename'" class="fa fa-sort" aria-hidden="true"></i>
		                    </a>
			            </th>
			            <th>
			                标签
		                    <a id="dataSortBtn-batch-desc" href="javascript:void(0);" ng-click="sortQuery('batch')">
		                        <i ng-show="params.sortField=='batch'&&params.sortType=='asc'" class="fa fa-sort-amount-asc"></i>
		                        <i ng-show="params.sortField=='batch'&&params.sortType=='desc'" class="fa fa-sort-amount-desc"></i>
		                        <i ng-show="params.sortField!='batch'" class="fa fa-sort" aria-hidden="true"></i>
		                    </a>
			            </th>
			            <th>
			                文件大小
                            <a id="dataSortBtn-batch-desc" href="javascript:void(0);" ng-click="sortQuery('filesize')">
                                <i ng-show="params.sortField=='filesize'&&params.sortType=='asc'" class="fa fa-sort-amount-asc"></i>
                                <i ng-show="params.sortField=='filesize'&&params.sortType=='desc'" class="fa fa-sort-amount-desc"></i>
                                <i ng-show="params.sortField!='filesize'" class="fa fa-sort" aria-hidden="true"></i>
                            </a>
			            </th>
			            <th>
			                上传时间
		                    <a id="dataSortBtn-createDate-desc" href="javascript:void(0);" ng-click="sortQuery('createDate')">
		                        <i ng-show="params.sortField=='createDate'&&params.sortType=='asc'" class="fa fa-sort-amount-asc"></i>
		                        <i ng-show="params.sortField=='createDate'&&params.sortType=='desc'" class="fa fa-sort-amount-desc"></i>
		                        <i ng-show="params.sortField!='createDate'" class="fa fa-sort" aria-hidden="true"></i>
		                    </a>
			            </th>
			        </tr>
			    </thead>
	            <tbody id="data-list-tbody">
	               <tr id="dataKey_${data.dataKey }" ng-repeat="data in dataList.datas">
		                <td>
		                    <label class="checkbox-lable">
		                        <input class="checkbox" type="checkbox" name="demo-checkbox1">
		                        <span class="info"></span>
		                    </label>
		                </td>
		                <td>{{data.sampleName}}</td>
		                <td style="text-align: left;" title="{{data.fileName}}" name="data-name-td">{{data.fileName}}</td>
		                <td>{{data.batch}}</td>
		                <td>{{data.size | fileSizeFormat}}</td>
		                <td>{{data.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
		            </tr>
	            </tbody>
	        </table>
	        
	        
	        
	        
            <div style="display: table; width: 100%; margin-top: -10px;" ng-show="dataList.datas.length == 0">
                <div class="text-center" style="display: table-cell; height: 350px; font-size: 18px; vertical-align: middle; background-color: #fff;">
                    <i class="glyphicon glyphicon-exclamation-sign" style="color: #f39c12;"></i> 未检索到数据！
                </div>
            </div>
            
            
            
	        <div id="rocky_report_page">
	            <ng-include src="'pages/rocky/pagination.jsp'"></ng-include>
	        </div>
	        <ng-include src="'pages/rocky/statistic.jsp'"></ng-include>
	    </div>
    </div>
</div>