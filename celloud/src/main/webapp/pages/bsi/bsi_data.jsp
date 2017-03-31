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
        <li>数据</li>
    </ol>
    <div class="content">
        <div class="page-layout page-main-content">
            <ng-include src="'pages/partial/_partial_bsi_header.jsp'"></ng-include>
            <div id="container" class="container-fluid common-container">
                <table class="table table-main">
				  <thead>
				    <tr>
				      <th>批次/标签<a id="sort-batch" href="javascript:void(0);" ng-click="sortBatch()">
				        <i ng-show="params.sort == 1" class="sort-batch-icon fa fa-sort-amount-asc"></i>
				        <i ng-show="params.sort != 1" class="fa fa-sort"></i></a>
				      </th>
				      <th>文件名<a id="sort-name" href="javascript:void(0);" ng-click="sortName()">
				        <i ng-show="params.sort == 2" class="sort-name-icon fa fa-sort-amount-asc"></i>
				        <i ng-show="params.sort != 2" class="fa fa-sort"></i></a>
				      </th>
				      <th>文件编号</th>
				      <th>文件大小</th>
				      <th>上传时间<a id="sort-date" href="javascript:void(0);" ng-click="sortDate()">
				        <i ng-show="params.sort == 0" class="sort-date-icon fa fa-sort-amount-desc"></i>
				        <i ng-show="params.sort != 0" class="fa fa-sort"></i></a>
				      </th>
				    </tr>
				  </thead>
				  <tbody id="data-list-tbody" ng-if="pageList.datas.length > 0">
				        <tr ng-repeat="data in pageList.datas">
				          <td title="{{data.batch}}">{{data.batch}}</td>
				          <td title="{{data.fileName}}" name="data-name-td" >
				              <span>{{data.fileName.length > 60 ? data.fileName.substring(0,60) + '...' : data.fileName}}</span>
				              <span ng-if="data.anotherName != null">({{data.anotherName}})</span>
				          </td>
				          <td>{{data.dataKey}}</td>
				          <td>{{data.size | fileSizeFormat}}</td>
				          <td>{{data.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
				        </tr>
				  </tbody>
				</table>
				<div class="pagination text-center" ng-if="pageList.datas.length > 0">
				    <input id="current-page-hide" value="{{pageList.page.currentPage}}" type="hidden" >
				    <input id="total-page-hide" value="{{pageList.page.totalPage}}" type="hidden" >
				    <ul id="pagination-data" class="pages pull-left">
				      <!-- 显示prev -->
				      <li><a id="prev-page-task" class="ends" ng-click="paginationBtn(pageList.page.currentPage>1?pageList.page.currentPage-1:1)">&lt;&lt;</a></li>
				      
			          <li ng-if="pageList.page.totalPage<=7" ng-repeat="step in [1,2,3,4,5,6,7]" ng-class="{active: step == pageList.page.currentPage}">
                         <a ng-if="step == pageList.page.currentPage && step <= pageList.page.totalPage">{{step}}</a>
                         <a ng-if="step != pageList.page.currentPage && step <= pageList.page.totalPage" name="pagination-task" ng-click="paginationBtn(step)">{{step}}</a>
                      </li>
                      
                      <li ng-if="pageList.page.currentPage <= 4 && pageList.page.totalPage > 7" ng-repeat="step in [1,2,3,4,5]" ng-class="{active: step == pageList.page.currentPage}">
                         <a ng-if="step == pageList.page.currentPage">{{step}}</a>
                         <a ng-if="step != pageList.page.currentPage" name="pagination-task" ng-click="paginationBtn(step)">{{step}}</a>
                      </li>
                      <li ng-if="pageList.page.currentPage <= 4 && pageList.page.totalPage > 7"><a>…</a></li>
                      <li ng-if="pageList.page.currentPage <= 4 && pageList.page.totalPage > 7"><a name="pagination-task" ng-click="paginationBtn(pageList.page.totalPage)">{{pageList.page.totalPage}}</a></li>
				        
				      <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a name="pagination-task" ng-click="paginationBtn(step)">1</a></li>
                      <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a>…</a></li>
                      <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a name="pagination-task" ng-click="paginationBtn(pageList.page.currentPage - 1)">{{pageList.page.currentPage - 1}}</a></li>
                      <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3" class="active"><a>{{pageList.page.currentPage}}</a></li>
                      <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a name="pagination-task" ng-click="paginationBtn(pageList.page.currentPage + 1)">{{pageList.page.currentPage + 1}}</a></li>
                      <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a>…</a></li>
                      <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3"><a name="pagination-task" ng-click="paginationBtn(pageList.page.totalPage)">{{pageList.page.totalPage}}</a></li>

                      <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage >= pageList.page.totalPage-3"><a name="pagination-task" ng-click="paginationBtn(1)">1</a></li>
                      <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage >= pageList.page.totalPage-3"><a>…</a></li> 
                      <li ng-if="pageList.page.totalPage > 7 && pageList.page.currentPage>=pageList.page.totalPage-3" ng-repeat="step in [1,2,3,4,5]" ng-class="{active: pageList.page.totalPage - 4 + $index == pageList.page.currentPage}">
                         <a ng-if="dataList.page.currentPage == pageList.page.totalPage - 4 + $index">{{pageList.page.totalPage - 4 + $index}}</a>
                         <a ng-if="dataList.page.currentPage != pageList.page.totalPage - 4 + $index" name="pagination-task" ng-click="paginationBtn(pageList.page.totalPage - 4 + $index)">{{pageList.page.totalPage - 4 + $index}}</a>
                      </li>				          
				        
				      <li><a id="next-page-task" class="ends" ng-click="paginationBtn(pageList.page.currentPage < pageList.page.totalPage?pageList.page.currentPage+1:pageList.page.totalPage)">&gt;&gt;</a></li>
				    </ul>
				    <ul class="pagination-data pull-right">
				      <li><span>共{{pageList.page.rowCount}}条</span></li>
				      <li>每页
				        <select id="page-size-sel" ng-model="dataPageSize" ng-change="pageSizeQuery()">
				          <option value="10" ng-selected="pageList.page.pageSize == 10">10</option>
				          <option value="20" ng-selected="pageList.page.pageSize == 20">20</option>
				          <option value="30" ng-selected="pageList.page.pageSize == 30">30</option>
				          <option value="50" ng-selected="pageList.page.pageSize == 50">50</option>
				          <option value="100" ng-selected="pageList.page.pageSize == 100">100</option>
				        </select>条
				      </li>
				    </ul>
				</div>
            </div>
        </div>
    </div>
</div>
