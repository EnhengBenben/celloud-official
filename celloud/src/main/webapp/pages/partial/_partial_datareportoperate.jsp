<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="project-report-opera col-xs-2" style="margin-top: 48px;">
 	<div class="report-pagination" id="fileListUl">
 	  <table class="table table-main">
          <thead>
            <tr>
              <th style="padding:0px;text-align: center;">相同标签报告</th>
            </tr>
          </thead>
          <tbody id="data-list-tbody" ng-if="batchPageList.datas.length > 0">
            <tr id="reportbatch{{task.dataKey}}" ng-class="{active: task.dataKey == bsi.dataKey }" ng-repeat="task in batchPageList.datas">
              <td title="{{task.fileName}}" name="data-name-td" style="padding: 0px;text-align: center;" >
                 <a ng-href="${pageContext.request.contextPath }/index#/product/bactive/rdata/{{task.dataKey}}/{{task.projectId}}/{{task.appId}}/null/0" ng-if="task.period == 2">
                   {{task.fileName.length > 60 ? task.fileName.substring(0, 60) + '...' : task.fileName}}{{task.anotherName == null? '' : task.anotherName}}
                 </a>
                 <a href="javascript:void(0)" ng-if="task.period != 2">
                   {{task.fileName.length > 60 ? task.fileName.substring(0, 60) + '...' : task.fileName}}{{task.anotherName == null? '' : task.anotherName}}
                 </a>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="pagination text-center" ng-if="batchPageList.datas.length > 0" style="height: 21px;">
            <input id="batch-current-page-hide" value="{{batchPageList.page.currentPage}}" type="hidden" >
            <input id="batch-total-page-hide" value="{{batchPageList.page.totalPage}}" type="hidden" >
            <ul id="pagination-data-report" class="pages">
                <li><a id="prev-page-task" class="ends pull-left" ng-click="batchPageQueryBtn(batchPageList.page.currentPage>1?batchPageList.page.currentPage-1:1)">&lt;&lt;</a></li>
                <!-- 显示第一页 -->
                <li ng-if="batchPageList.page.totalPage <= 3 && step <= batchPageList.page.totalPage" ng-class="{active: step == batchPageList.page.currentPage}" ng-repeat="step in [1,2,3]">
                 <a ng-if="step == batchPageList.page.currentPage" name="pagination-task" >{{step}}</a>
                 <a ng-if="step != batchPageList.page.currentPage" name="pagination-task" ng-click="batchPageQueryBtn(step)">{{step}}</a>
                </li>
                
                <li ng-if="batchPageList.page.totalPage > 3 && batchPageList.page.currentPage == 1" class="active"><a name="pagination-task">1</a></li>
                <li ng-if="batchPageList.page.totalPage > 3 && batchPageList.page.currentPage == 1"><a name="pagination-task" ng-click="batchPageQueryBtn(2)">2</a></li>
                <li ng-if="batchPageList.page.totalPage > 3 && batchPageList.page.currentPage == 1"><a name="pagination-task" ng-click="batchPageQueryBtn(3)">3</a></li>
                    
                <li ng-if="batchPageList.page.totalPage > 3 && batchPageList.page.currentPage == batchPageList.page.totalPage"><a name="pagination-task" ng-click="batchPageQueryBtn(batchPageList.page.currentPage-2)">{{batchPageList.page.currentPage-2}}</a></li>
                <li ng-if="batchPageList.page.totalPage > 3 && batchPageList.page.currentPage == batchPageList.page.totalPage"><a name="pagination-task" ng-click="batchPageQueryBtn(batchPageList.page.currentPage-1)">{{batchPageList.page.currentPage-1}}</a></li>
                <li ng-if="batchPageList.page.totalPage > 3 && batchPageList.page.currentPage == batchPageList.page.totalPage" class="active"><a name="pagination-task">{{batchPageList.page.currentPage}}</a></li>
                    
                <li ng-if="batchPageList.page.totalPage > 3 && batchPageList.page.currentPage > 1 && batchPageList.page.currentPage < pageList.page.totalPage"><a name="pagination-task" ng-click="batchPageQueryBtn(batchPageList.page.currentPage-1)">{{batchPageList.page.currentPage-1}}</a></li>
                <li ng-if="batchPageList.page.totalPage > 3 && batchPageList.page.currentPage > 1 && batchPageList.page.currentPage < pageList.page.totalPage" class="active"><a name="pagination-task">{{batchPageList.page.currentPage}}</a></li>
                <li ng-if="batchPageList.page.totalPage > 3 && batchPageList.page.currentPage > 1 && batchPageList.page.currentPage < pageList.page.totalPage"><a name="pagination-task" ng-click="batchPageQueryBtn(batchPageList.page.currentPage+1)">{{batchPageList.page.currentPage+1}}</a></li>
              
                <li><a id="next-page-task" class="ends pull-right" ng-click="batchPageQueryBtn(batchPageList.page.currentPage < batchPageList.page.totalPage ? batchPageList.page.currentPage + 1 : batchPageList.page.totalPage)">&gt;&gt;</a></li>
            </ul>
          </div>
    </div>
 </div>