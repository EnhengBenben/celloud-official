<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="project-report-opera">
 	<div class="report-pagination" id="fileListUl" style="width: 100%;margin-top: 0px;">
 	  <table class="table table-main">
          <thead>
            <tr>
              <th style="padding:0px;text-align: center;">相同标签报告</th>
            </tr>
          </thead>
          <tbody id="data-list-tbody" ng-if="taskPageList.datas.length > 0">
            <tr id="reportbatch{{task.dataKey}}" ng-class="{active: task.dataKey == obj.dataKey }" ng-repeat="task in taskPageList.datas">
              <td title="{{task.fileName}}" name="data-name-td" style="padding: 0px;text-align: center;" >
                 <a ng-href="${pageContext.request.contextPath }/index#/reportdata/{{appName}}/{{task.appId}}/{{task.dataKey}}/{{task.projectId}}" ng-if="task.period == 2">
                   {{task.fileName.length > 17 ? task.fileName.substring(0, 17) + '...' : task.fileName}}{{task.anotherName == null? '' : task.anotherName}}
                 </a>
                 <a href="javascript:void(0)" ng-if="task.period != 2">
                   {{task.fileName.length > 17 ? task.fileName.substring(0, 17) + '...' : task.fileName}}{{task.anotherName == null? '' : task.anotherName}}
                 </a>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="pagination text-center" ng-if="taskPageList.datas.length > 0" style="height: 21px;">
            <input id="batch-current-page-hide" value="{{taskPageList.page.currentPage}}" type="hidden" >
            <input id="batch-total-page-hide" value="{{taskPageList.page.totalPage}}" type="hidden" >
            <ul id="pagination-data-report" class="pages">
                <li><a id="prev-page-task" class="ends pull-left" ng-click="dataInBatch(taskPageList.page.currentPage>1?taskPageList.page.currentPage-1:1, obj)">&lt;&lt;</a></li>
                <!-- 显示第一页 -->
                <li ng-if="taskPageList.page.totalPage <= 3 && step <= taskPageList.page.totalPage" ng-class="{active: step == taskPageList.page.currentPage}" ng-repeat="step in [1,2,3]">
                 <a ng-if="step == taskPageList.page.currentPage" name="pagination-task" >{{step}}</a>
                 <a ng-if="step != taskPageList.page.currentPage" name="pagination-task" ng-click="dataInBatch(step, obj)">{{step}}</a>
                </li>
                
                <li ng-if="taskPageList.page.totalPage > 3 && taskPageList.page.currentPage == 1" class="active"><a name="pagination-task">1</a></li>
                <li ng-if="taskPageList.page.totalPage > 3 && taskPageList.page.currentPage == 1"><a name="pagination-task" ng-click="dataInBatch(2, obj)">2</a></li>
                <li ng-if="taskPageList.page.totalPage > 3 && taskPageList.page.currentPage == 1"><a name="pagination-task" ng-click="dataInBatch(3, obj)">3</a></li>
                    
                <li ng-if="taskPageList.page.totalPage > 3 && taskPageList.page.currentPage == taskPageList.page.totalPage"><a name="pagination-task" ng-click="dataInBatch(taskPageList.page.currentPage-2, obj)">{{taskPageList.page.currentPage-2}}</a></li>
                <li ng-if="taskPageList.page.totalPage > 3 && taskPageList.page.currentPage == taskPageList.page.totalPage"><a name="pagination-task" ng-click="dataInBatch(taskPageList.page.currentPage-1, obj)">{{taskPageList.page.currentPage-1}}</a></li>
                <li ng-if="taskPageList.page.totalPage > 3 && taskPageList.page.currentPage == taskPageList.page.totalPage" class="active"><a name="pagination-task">{{taskPageList.page.currentPage}}</a></li>
                    
                <li ng-if="taskPageList.page.totalPage > 3 && taskPageList.page.currentPage > 1 && taskPageList.page.currentPage < taskPageList.page.totalPage"><a name="pagination-task" ng-click="dataInBatch(taskPageList.page.currentPage-1, obj)">{{taskPageList.page.currentPage-1}}</a></li>
                <li ng-if="taskPageList.page.totalPage > 3 && taskPageList.page.currentPage > 1 && taskPageList.page.currentPage < taskPageList.page.totalPage" class="active"><a name="pagination-task">{{taskPageList.page.currentPage}}</a></li>
                <li ng-if="taskPageList.page.totalPage > 3 && taskPageList.page.currentPage > 1 && taskPageList.page.currentPage < taskPageList.page.totalPage"><a name="pagination-task" ng-click="dataInBatch(taskPageList.page.currentPage+1, obj)">{{taskPageList.page.currentPage+1}}</a></li>
              
                <li><a id="next-page-task" class="ends pull-right" ng-click="dataInBatch(taskPageList.page.currentPage < taskPageList.page.totalPage ? taskPageList.page.currentPage + 1 : taskPageList.page.totalPage, obj)">&gt;&gt;</a></li>
            </ul>
          </div>
    </div>
 </div>