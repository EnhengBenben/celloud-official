<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="pro-body">
    <ol class="breadcrumb">
        <li>CelLoud</li>
        <li>我的产品</li>
        <li>百菌探</li>
    </ol>
    <div class="content">
        <div class="page-layout page-main-content">
            <ng-include src="'pages/partial/_partial_bsi_header.jsp'"></ng-include>
            <div id="container" class="container-fluid common-container">
                <div class="selector">
				    <div id="batch-sl" class="selector-line">
				        <div class="sl-key">标签：</div>
				        <div id="selected-batch" class="sl-val selected-val hide">
					        <span></span>
					        <a id="clear-sl-batch" ng-click="clearSlBatch()"><i class="fa fa-times"></i></a>
				        </div>
					    <div id="to-sl-batch">
					        <div id="batch-lists" class="sl-val">
				                <div class="sl-val-content" ng-repeat="batch in batchList">
                                    <div class="celicon checkbox checkbox-un hide" id="batchId{{$index}}"></div>
                                    <a ng-click="reportBatchSearch('batchId' + $index)" href="javascript:void(0)"><span>{{batch}}</span></a>
                                </div>
						        <div class="multisl-btns hide">
						            <button id="report-multibatch-search" name="sl-confirm" class="sl-btn disabled" href="javascript:void(0)" disabled="disabled">确定</button>
						            <button data-click="reset-multiselect" class="sl-btn" href="javascript:void(0)">取消</button>
						        </div>
				            </div>
					        <div class="sl-ext">
					            <button id="batch-more" class="sl-more" href="javascript:void(0)"><span>更多</span><i class="fa fa-chevron-down" aria-hidden="true"></i></button>
					            <button id="batch-multiselect" data-click="report-select-more" class="sl-multiple" href="javascript:void(0)">多选<i class="fa fa-plus" aria-hidden="true"></i></button>
					        </div>
					    </div>
				    </div>
				    <div id="period-sl" class="selector-line">
				        <div class="sl-key">状态：</div>
				        <div id="selected-period" class="sl-val selected-val hide">
				            <span></span>
				            <a id="clear-sl-period"><i class="fa fa-times"></i></a>
				        </div>
				        <div id="to-sl-period">
				            <div id="period-lists" class="sl-val">
				                <div class="sl-val-content">
				                    <div class="celicon on_check checkbox checkbox-un hide"></div>
				                    <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="2"><span>完成</span></a>
				                </div>
				                <div class="sl-val-content">
				                    <div class="celicon on_check checkbox checkbox-un hide"></div>
				                    <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="1"><span>正在分析</span></a>
				                </div>
				                <div class="sl-val-content">
				                    <div class="celicon on_check checkbox checkbox-un hide"></div>
				                    <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="0"><span>等待分析</span></a>
					            </div>
					            <div class="sl-val-content">
						            <div class="celicon on_check checkbox checkbox-un hide"></div>
						            <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="3"><span>数据不完整</span></a>
					            </div>
					            <div class="sl-val-content">
						            <div class="celicon on_check checkbox checkbox-un hide"></div>
						            <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="4"><span>异常终止</span></a>
					            </div>
					            <div class="sl-val-content">
						            <div class="celicon on_check checkbox checkbox-un hide"></div>
						            <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="6"><span>实验中</span></a>
					            </div>
					            <div class="sl-val-content">
						            <div class="celicon on_check checkbox checkbox-un hide"></div>
						            <a data-click="report-period-search" href="javascript:void(0)"><input type="hidden" value="5"><span>送样中</span></a>
					            </div>
					            <div class="multisl-btns hide">
						            <button id="report-multiperiod-search" name="sl-confirm" class="sl-btn disabled" href="javascript:void(0)" disabled="disabled">确定</button>
						            <button data-click="reset-multiselect" class="sl-btn" href="javascript:void(0)">取消</button>
					            </div>
				            </div>
				            <div class="sl-ext">
				                <button data-click="report-select-more" class="sl-multiple" href="javascript:void(0)">多选<i class="fa fa-plus" aria-hidden="true"></i></button>
				            </div>
				        </div>
				    </div>
				    <div class="selector-line">
				        <div class="sl-key">时间：</div>
				        <div class="sl-val">
				            <input id="report-begindate-search" type="text" class="Wdate" onclick="WdatePicker()" readonly="readonly" placeholder="  年    月    日"> - 
				            <input id="report-enddate-search" type="text" class="Wdate" onclick="WdatePicker()" readonly="readonly" placeholder="  年    月    日">
				            <button data-click="report-date-search" class="sl-btn" href="javascript:void(0)">确定</button>
				        </div>
				    </div>
				    <div class="selector-line">
				        <div class="sl-key">是否分发：</div>
				        <div class="sl-val">
				            <a data-click="report-distributed-search" class="sl-judge" href="javascript:void(0)">
				                <span class="sl-judge-yes">是</span>
				                <span class="sl-judge-no hide">否</span>
				            </a>
				        </div>
				    </div>
				</div>
				<div id="report-list">
				    <table class="table table-main">
				      <thead>
				        <tr>
				          <th width="40">
				            <div data-click="report-check-all" class="celicon checkbox checkbox-un"></div>
				          </th>
				          <th width="140">
				            <input id="sample-selector" type="text" placeholder="样本编号/病历号">
				          </th>
				          <th>批次/标签<a id="sort-batch" href="javascript:void(0);"><i id="sort-batch-icon" class="fa fa-sort" aria-hidden="true"></i></a></th>
				          <th>文件名<a id="sort-name" href="javascript:void(0);"><i id="sort-name-icon" class="fa fa-sort" aria-hidden="true"></i></a></th>
				          <th>状态<a id="sort-period" href="javascript:void(0);"><i id="sort-period-icon" class="fa fa-sort" aria-hidden="true"></i></a></th>
				          <th class="date-td">更新时间<a id="sort-date" href="javascript:void(0);"><i id="sort-date-icon" class="fa fa-sort-amount-asc"></i></a></th>
				          <th>操作</th>
				        </tr>
				      </thead>
				      <tbody id="data-list-tbody" ng-if="pageList.datas.length > 0">
				          <tr id="report{{task.dataKey}}{{task.projectId}}{{task.appId}}" ng-repeat="task in pageList.datas">
				            <td>
				              <div class="celicon checkbox checkbox-un"></div>
				            </td>
				            <td>{{task.sampleName}}</td>
				            <td>{{task.batch}}</td>
				            <td title="{{task.fileName}}" name="data-name-td" >
				                <a ng-if="task.period == 2" ng-href="javascript:$.report.detail.patient('{{task.dataKey}}',{{task.projectId}},{{task.appId}},{{$index + 1}},{{pageList.page.currentPage}})">
				                  {{task.fileName.length > 60 ? task.fileName.substring(0, 60) + '...' : task.fileName}}{{task.anotherName}}
				                </a>
				                <span ng-if="task.period != 2">
				                  {{task.fileName.length > 60 ? task.fileName.substring(0, 60) + '...' : task.fileName}}{{task.anotherName}}
				                </span>
				            </td>
				            <td>
				              <a ng-if="task.period == 0" href="javascript:void(0)">等待运行</a>
				              <a ng-if="task.period == 1" data-toggle="modal" data-target="#running-modal">正在分析</a>
				              <a ng-if="task.period == 2" ng-href="javascript:$.report.detail.patient('{{task.dataKey}}',{{task.projectId}},{{task.appId}},{{$index + 1}},{{pageList.page.currentPage}})">完成</a>
				              <a ng-if="task.period == 3" data-toggle="modal" data-target="#report-uploading-modal">数据不完整</a>
				              <a ng-if="task.period == 4" data-toggle="modal" data-target="#running-error-modal">异常终止</a>
				              <a ng-if="task.period == 5" href="javascript:void(0)">送样中</a>
				              <a ng-if="task.period == 6" href="javascript:void(0)">实验中</a>
				              <a ng-if="task.period == null" href="javascript:void(0)" ng-click="$.report.period.error('{{task.fileName}}')" class="wrong">运行异常</a>
				            </td>
				            <td class="date-td">{{task.updateDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
				            <td>
				              <a title="查看报告" ng-href="javascript:$.report.detail.patient('{{task.dataKey}}',{{task.projectId}},{{task.appId}},{{$index + 1}},{{pageList.page.currentPage}})" ng-if="task.period == 2"><i class="fa fa-eye"></i></a>
				              <a title="查看报告" class="disabled"  disabled="disabled" ng-if="task.period != 2"><i class="fa fa-eye"></i></a>
				              <a title="打印患者报告" target="_blank" ng-href="${pageContext.request.contextPath }/report/printBSIReport?projectId={{task.projectId}}&dataKey={{task.dataKey}}&appId={{task.appId}}&templateType=print_patient"" ng-if="task.period == 2"><i class="fa fa-print"></i></a>
				              <a title="打印患者报告" class="disabled"  disabled="disabled" ng-href="task.period != 2"><i class="fa fa-print"></i></a>
				              <a title="共享报告" href="javascript:void(0)"><i class="fa fa-share-square-o"></i></a>
				              <a title="重新运行" ng-href="javascript:$.report.reRun({{task.dataKey}},{{task.appId}},{{task.projectId}})" ng-if="task.period==1 || task.period==2 || task.period==4 || task.period == null" ><i class="fa fa-refresh"></i></a>
				              <a title="重新运行" class="disabled"  disabled="disabled" ng-if="task.period!=1 && task.period!=2 && task.period!=4 && task.period != null" ><i class="fa fa-refresh"></i></a>
				            </td>
				          </tr>
				      </tbody>
				    </table>
				    <div class="pagination text-center" ng-if="pageList.datas.length > 0">
				        <input id="current-page-hide" value="{{pageList.page.currentPage}}" type="hidden" >
				        <input id="total-page-hide" value="{{pageList.page.totalPage}}" type="hidden" >
				        <ul class="pagination-check pull-left">
				          <li class="checkbox-li"><div data-click="report-check-all" class="celicon checkbox checkbox-un"></div></li>
				          <li>全选</li>
				          <li>
				            <select>
				              <option value="0">批量下载</option>
				              <option value="1">批量归档</option>
				              <option value="2">批量分发</option>
				            </select>
				          </li>
				        </ul>
				        <ul class="pagination-data pull-right">
				          <li><span>共&nbsp;&nbsp;{{pageList.page.rowCount}}&nbsp;&nbsp;条</span></li>
				          <li>每页
				            <select id="page-size-sel">
				              <option value="10" ng-selected="pageList.page.pageSize==10">10</option>
				              <option value="20" ng-selected="pageList.page.pageSize==20">20</option>
				              <option value="30" ng-selected="pageList.page.pageSize==30">30</option>
				              <option value="50" ng-selected="pageList.page.pageSize==50">50</option>
				              <option value="100" ng-selected="pageList.page.pageSize==100">100</option>
				            </select>条
				          </li>
				        </ul>
				        <ul id="pagination-task" class="pages pull-right">
				          <!-- 显示prev -->
				          <li><a id="prev-page-task" class="ends" href="javascript:void(0);">&lt;&lt;</a></li>
			              <li ng-if="pageList.page.totalPage<=7" ng-repeat="step in [1,2,3,4,5,6,7]" ng-class="{active: step == pageList.page.currentPage}">
			                <a ng-if="step == pageList.page.currentPage && step <= pageList.page.totalPage" ng-href="#">{{step}}</a>
			                <a ng-if="step != pageList.page.currentPage && step <= pageList.page.totalPage" name="pagination-task" ng-href="javascript:void(0)">{{step}}</a>
			              </li>
			              
			              <li ng-if="pageList.page.currentPage <= 4 && pageList.page.totalPage > 7" ng-repeat="step in [1,2,3,4,5]" ng-class="{active: step == pageList.page.currentPage}">
                            <a ng-if="step == pageList.page.currentPage" ng-href="#">{{step}}</a>
                            <a ng-if="step != pageList.page.currentPage" name="pagination-task" ng-href="javascript:void(0)">{{step}}</a>
                          </li>
		                  <li ng-if="pageList.page.currentPage <= 4 && pageList.page.totalPage > 7"><a href="javascript:void(0)">…</a></li>
		                  <li ng-if="pageList.page.currentPage <= 4 && pageList.page.totalPage > 7"><a name="pagination-task" href="javascript:void(0)">{{pageList.page.totalPage}}</a></li>
				              
			              <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a name="pagination-task" href="javascript:void(0)">1</a></li>
                          <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a href="javascript:void(0)">…</a></li>
                          <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a name="pagination-task" href="javascript:void(0)">{{pageList.page.currentPage - 1}}</a></li>
                          <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3" class="active"><a href="#">{{pageList.page.currentPage}}</a></li>
                          <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a name="pagination-task" href="javascript:void(0)">{{pageList.page.currentPage + 1}}</a></li>
                          <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a href="javascript:void(0)">…</a></li>
                          <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a name="pagination-task" href="javascript:void(0)">{{pageList.page.totalPage}}</a></li>
			              
			              <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage >= pageList.page.totalPage-3"><a name="pagination-task" href="javascript:void(0)">1</a></li>
                          <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage >= pageList.page.totalPage-3"><a href="javascript:void(0)">…</a></li> 
                          <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>=pageList.page.totalPage-3" ng-repeat="step in [1,2,3,4,5]" ng-class="{active: step == pageList.page.currentPage}">
                            <a ng-if="dataList.page.currentPage == pageList.page.totalPage - 4 + $index" ng-href="#">{{pageList.page.totalPage - 4 + $index}}</a>
                            <a ng-if="dataList.page.currentPage != pageList.page.totalPage - 4 + $index" name="pagination-task" ng-href="javascript:void(0)">{{pageList.page.totalPage - 4 + $index}}</a>
                          </li>
				          <li><a id="next-page-task" class="ends" href="javascript:void(0)">&gt;&gt;</a></li>
				        </ul>
				    </div>
				</div>
				<div class="report-statistics">
				  数据汇总：
				  <span>已出报告[{{periodMap.done}}份] </span>
				  <span>待出报告[{{periodMap.wait}}份] </span>
				  <span>已传数据[{{periodMap.uploaded}}批] </span>
				  <span>正在上传[{{periodMap.uploading}}批] </span>
				  <span>状态异常[{{periodMap.error}}份] </span>
				  <span class="pull-right">统计时间：{{nowDate | date:'yyyy-MM-dd'}}</span>
				</div>
			</div>
        </div>
    </div>
</div>