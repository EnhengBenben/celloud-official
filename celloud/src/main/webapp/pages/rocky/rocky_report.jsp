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
        <li>报告</li>
    </ol>
    <div id="common-container" class="common-container" style="width:98%">
        <div class="selector">
		    <div id="batch-sl" class="selector-line clearfix">
		        <div class="sl-key">标签：</div>
		        <div id="selected-batch" class="sl-val selected-val hide">
		            <span></span>
		            <a id="clear-sl-batch" ng-click="clearSlBatch()">
		                <i class="fa fa-times"></i>
		            </a>
		        </div>
		        <div id="to-sl-batch">
		            <div id="batch-lists" class="sl-val">
                        <div ng-repeat="batch in batchList" class="sl-val-content">
                            <div class="celicon on_check checkbox checkbox-un hide" id="batchId{{$index}}" ng-click="batchLists('batchId' + $index)"></div>
                            <a data-click="report-batch-search" ng-click="reportBatchSearch('batchId' + $index)" href="javascript:void(0)">
                                <span>{{batch}}</span>
                            </a>
                        </div>
		                <div class="multisl-btns hide">
		                    <button id="report-multibatch-search" ng-click="reportMultibatchSearch()" name="sl-confirm" class="sl-btn disabled" disabled="disabled">确定</button>
		                    <button data-click="reset-multiselect" ng-click="resetBatchMultiselect()" class="sl-btn">取消</button>
		                </div>
		            </div>
		            <div class="sl-ext">
		                <button id="batch-more" class="sl-more" ng-click="batchMore()">
		                    <span>更多</span>
		                    <i class="fa fa-chevron-down" aria-hidden="true"></i>
		                </button>
		                <button id="batch-multiselect" ng-click="batchMultiselect()" data-click="report-select-more" class="sl-multiple">
		                    多选<i class="fa fa-plus" aria-hidden="true"></i>
		                </button>
		            </div>
		        </div>
		    </div>
		    <div id="period-sl" class="selector-line clearfix">
		        <div class="sl-key">状态：</div>
		        <div id="selected-period" class="sl-val selected-val hide">
		            <span></span>
		            <a id="clear-sl-period" ng-click="clearSlPeriod()">
		                <i class="fa fa-times"></i>
		            </a>
		        </div>
		        <div id="to-sl-period">
		            <div id="period-lists" class="sl-val">
		                <div class="sl-val-content" ng-click="periodLists('finish')">
		                    <div class="celicon on_check checkbox checkbox-un hide" id="finish"></div>
		                    <a data-click="report-period-search" ng-click="reportPeriodSearch('finish')" href="javascript:void(0)">
		                        <input type="hidden" value="2">
		                        <span>完成</span>
		                    </a>
		                </div>
		                <div class="sl-val-content" ng-click="periodLists('inanalysis')">
		                    <div class="celicon on_check checkbox checkbox-un hide" id="inanalysis"></div>
		                    <a data-click="report-period-search" ng-click="reportPeriodSearch('inanalysis')" href="javascript:void(0)">
		                        <input type="hidden" value="1">
		                        <span>分析中</span>
		                    </a>
		                </div>
		                <div class="sl-val-content" ng-click="periodLists('waitanalysis')">
		                    <div class="celicon on_check checkbox checkbox-un hide" id="waitanalysis"></div>
		                    <a data-click="report-period-search" ng-click="reportPeriodSearch('waitanalysis')" href="javascript:void(0)">
		                        <input type="hidden" value="0">
		                        <span>等待分析</span>
		                    </a>
		                </div>
		                <div class="sl-val-content" ng-click="periodLists('uploading')">
		                    <div class="celicon on_check checkbox checkbox-un hide" id="uploading"></div>
		                    <a data-click="report-period-search" ng-click="reportPeriodSearch('uploading')" href="javascript:void(0)">
		                        <input type="hidden" value="3">
		                        <span>数据上传中</span>
		                    </a>
		                </div>
		                <div class="sl-val-content" ng-click="periodLists('exception')">
		                    <div class="celicon on_check checkbox checkbox-un hide" id="exception"></div>
		                    <a data-click="report-period-search" ng-click="reportPeriodSearch('exception')" href="javascript:void(0)">
		                        <input type="hidden" value="4">
		                        <span>异常终止</span>
		                    </a>
		                </div>
		                <div class="sl-val-content" ng-click="periodLists('experiment')">
		                    <div class="celicon on_check checkbox checkbox-un hide" id="experiment"></div>
		                    <a data-click="report-period-search" ng-click="reportPeriodSearch('experiment')" href="javascript:void(0)">
		                        <input type="hidden" value="6">
		                        <span>实验中</span>
		                    </a>
		                </div>
		                <div class="sl-val-content" ng-click="periodLists('sample')">
		                    <div class="celicon on_check checkbox checkbox-un hide" id="sample"></div>
		                    <a data-click="report-period-search" ng-click="reportPeriodSearch('sample')" href="javascript:void(0)">
		                        <input type="hidden" value="5">
		                        <span>送样中</span>
		                    </a>
		                </div>
		                <div class="multisl-btns hide">
		                    <button id="report-multiperiod-search" ng-click="reportMultiperiodSearch()" name="sl-confirm" class="sl-btn disabled" disabled="disabled">确定</button>
		                    <button data-click="reset-multiselect" ng-click="resetPeriodMultiSelect()" class="sl-btn">取消</button>
		                </div>
		            </div>
		            <div class="sl-ext">
		                <button data-click="report-select-more" ng-click="reportSelectMore()" class="sl-multiple">
		                    多选<i class="fa fa-plus" aria-hidden="true"></i>
		                </button>
		            </div>
		        </div>
		    </div>
		    <div class="selector-line">
		        <div class="sl-key">时间：</div>
		        <div class="sl-val">
		            <input id="report-begindate-search" type="text" class="Wdate" onclick="WdatePicker({maxDate:'#F{$dp.$D(\'report-enddate-search\')}'})" readonly="readonly" placeholder="  年    月    日">
		            -
		            <input id="report-enddate-search" type="text" class="Wdate" onclick="WdatePicker({minDate:'#F{$dp.$D(\'report-begindate-search\')}',maxDate:'%y-%M-%d'})" readonly="readonly" placeholder="  年    月    日">
		            <button data-click="report-date-search" ng-click="reportDateSearch()" class="sl-btn">确定</button>
		        </div>
		    </div>
		    <!-- div class="selector-line">
		        <div class="sl-key">搜索：</div>
		        <div class="sl-val">
		            <input id="report-sample-search" type="text" style="width:232px;" placeholder="样本编号/病历号/文件名/标签">
		            <button data-click="report-date-search" class="sl-btn">确定</button>
		        </div>
		    </div -->
		</div>
		<div id="rocky-report-list">
	        <table class="table table-main">
	            <thead>
	                <tr>
	                    <th class="th-checkoutbox"></th>
	                    <th width="140">
	                        <input id="report-sample-filter" type="text" placeholder="样本编号/病历号" ng-keypress="reportSampleFilter($event)" ng-model="params.sample" value="${sampleFilter }">
	                    </th>
	                    <th>
	                        文件名
                            <a ng-if="sidx=='filename' && sord=='asc'" ng-click="reportSortBtn('reportSortBtn-filename-desc')" id="reportSortBtn-filename-desc" href="javascript:void(0);">
                                <i class="fa fa-sort-amount-asc"></i>
                            </a>
                            <a ng-if="sidx=='filename' && sord=='desc'" ng-click="reportSortBtn('reportSortBtn-filename-asc')" id="reportSortBtn-filename-asc" href="javascript:void(0);">
                                <i class="fa fa-sort-amount-desc"></i>
                            </a>
                            <a ng-if="sidx!='filename'" ng-click="reportSortBtn('reportSortBtn-filename')" id="reportSortBtn-filename" href="javascript:void(0);">
                                <i class="fa fa-sort" aria-hidden="true"></i>
                            </a>
	                    </th>
	                    <th>
	                        标签
                            <a ng-if="sidx=='batch'&&sord=='asc'" ng-click="reportSortBtn('reportSortBtn-batch-desc')" id="reportSortBtn-batch-desc" href="javascript:void(0);">
                                <i class="fa fa-sort-amount-asc"></i>
                            </a>
                            <a ng-if="sidx=='batch'&&sord=='desc'" ng-click="reportSortBtn('reportSortBtn-batch-asc')" id="reportSortBtn-batch-asc" href="javascript:void(0);">
                                <i class="fa fa-sort-amount-desc"></i>
                            </a>
                            <a ng-if="sidx!='batch'" ng-click="reportSortBtn('reportSortBtn-batch')" id="reportSortBtn-batch" href="javascript:void(0);">
                                <i class="fa fa-sort" aria-hidden="true"></i>
                            </a>
	                    </th>
	                    <th>状态</th>
	                    <th>
	                        更新时间
                            <a ng-if="sidx=='updateDate'&&sord=='asc'" ng-click="reportSortBtn('reportSortBtn-updateDate-desc')" id="reportSortBtn-updateDate-desc" href="javascript:void(0);">
                                <i class="fa fa-sort-amount-asc"></i>
                            </a>
                            <a ng-if="sidx=='updateDate'&&sord=='desc'" ng-click="reportSortBtn('reportSortBtn-updateDate-asc')" id="reportSortBtn-updateDate-asc" href="javascript:void(0);">
                                <i class="fa fa-sort-amount-desc"></i>
                            </a>
                            <a ng-if="sidx!='updateDate'" ng-click="reportSortBtn('reportSortBtn-updateDate')" id="reportSortBtn-updateDate" href="javascript:void(0);">
                                <i class="fa fa-sort" aria-hidden="true"></i>
                            </a>
	                    </th>
	                    <th>操作</th>
	                </tr>
	            </thead>
	            <tbody id="data-list-tbody">
                    <tr ng-repeat="report in dataList.datas" id="report-{{report.dataKey}}-{{report.projectId}}-{{report.appId}}">
                        <td>
                            <label class="checkbox-lable">
                                <input class="checkbox" type="checkbox" name="demo-checkbox1">
                                <span class="info"></span>
                            </label>
                        </td>
                        <td>{{report.sampleName}}</td>
                        <td title="{{report.fileName}}" style="text-align: left;" name="data-name-td">
                            <a ng-show="report.period == 2" title="查看报告" style="color: #323232; font-size: 12px;" ng-click="showReport(report.dataKey,report.projectId,report.appId)">{{report.fileName}}</a>
                            <a style="color: #323232; font-size: 12px; cursor: text" ng-show="report.period != 2">{{report.fileName}}</a>
                        </td>
                        <td title="{{report.batch}}">{{report.batch}}</td>
                        <td>
                            <span ng-show="report.period==0">等待运行</span>
                            <span ng-show="report.period==1">正在运行</span>
                            <span ng-show="report.period==2">完成</span>
                            <span ng-show="report.period==3">数据上传中</span>
                            <span ng-show="report.period==4">异常终止</span>
                            <span ng-show="report.period==5">送样中</span>
                            <span ng-show="report.period==6">实验中</span>
                            <a ng-show="report.period == null || report.period == undefined" href="javascript:void(0)" ng-click="$.report.period.error('{{report.fileName}}')" class="wrong">运行异常</a>
                        </td>
                        <td>
                            {{report.updateDate | date:'yyyy-MM-dd HH:mm:ss'}}
                        </td>
                        <td>
                            <a ng-show="report.period == 2" title="查看报告" ng-click="showReport(report.dataKey,report.projectId,report.appId)">
                                <i class="fa fa-eye"></i>
                            </a>
                            <a ng-show="report.period == 2" title="打印患者报告" target="_blank" ng-href="${pageContext.request.contextPath }/report/printRockyReport?projectId={{report.projectId}}&dataKey={{report.dataKey}}&appId={{report.appId}}">
                                <i class="fa fa-print"></i>
                            </a>
                            <a ng-show="report.period == 2" title="共享报告" href="javascript:void(0)">
                                <i class="fa fa-share-square-o"></i>
                            </a>
                            <a ng-show="report.period != 2" title="查看报告" class="disabled bg-gray" href="javascript:;">
                                <i class="fa fa-eye"></i>
                            </a>
                            <a ng-show="report.period != 2" title="打印患者报告" class="disabled bg-gray" disabled="disabled">
                                <i class="fa fa-print"></i>
                            </a>
                            <a ng-show="report.period != 2" title="共享报告" href="javascript:void(0)">
                                <i class="fa fa-share-square-o"></i>
                            </a>
                        </td>
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