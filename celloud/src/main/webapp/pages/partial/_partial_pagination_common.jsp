<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="pagination text-center">
	<ul class="pages pull-left">
      <li class="first-page">
        <a id="prev-page" data-click="pagination-btn" ng-href="#/{{pageType}}/1">首页</a>
      </li>
	</ul>
	<ul id="pagination-ul" class="pages pull-left">
      <!-- 显示prev -->
      <li>
        <a id="prev-page" class="ends" ng-href="#/{{pageType}}/{{dataList.page.currentPage>1?dataList.page.currentPage-1:1}}">&lt;&lt;</a>
      </li>
      <li ng-class="{active: page==dataList.page.currentPage}" ng-if="dataList.page.totalPage<=7" ng-repeat="page in pageArray(dataList.page.totalPage)">
        <a ng-href="#/{{pageType}}/{{page}}">{{page}}</a>
      </li>
      <li ng-class="{active: page==dataList.page.currentPage}" ng-if="dataList.page.totalPage>7 && dataList.page.currentPage<=4" ng-repeat="page in [1,2,3,4,5]">
        <a ng-href="#/{{pageType}}/{{page}}">{{page}}</a>
      </li>
      <li ng-if="dataList.page.totalPage>7 && dataList.page.currentPage<=4">
        <a ng-href="javascript:void(0)">…</a>
      </li>
      <li ng-if="dataList.page.totalPage>7 && dataList.page.currentPage<=4">
        <a ng-href="#/{{pageType}}/{{dataList.page.totalPage}}">{{dataList.page.totalPage}}</a>
      </li>
      <li ng-if="dataList.page.totalPage>7 && dataList.page.currentPage>4 && dataList.page.currentPage<dataList.page.totalPage-3">
        <a ng-href="#/{{pageType}}/1">1</a>
      </li>
      <li ng-if="dataList.page.totalPage>7 && dataList.page.currentPage>4 && dataList.page.currentPage<dataList.page.totalPage-3">
        <a ng-href="javascript:void(0)">…</a>
      </li>
      <li ng-if="dataList.page.totalPage>7 && dataList.page.currentPage>4 && dataList.page.currentPage<dataList.page.totalPage-3">
        <a ng-href="#/{{pageType}}/{{dataList.page.currentPage-1}}">{{dataList.page.currentPage-1}}</a>
      </li>
      <li class="active" ng-if="dataList.page.totalPage>7 && dataList.page.currentPage>4 && dataList.page.currentPage<dataList.page.totalPage-3">
        <a ng-href="#/{{pageType}}/{{dataList.page.currentPage}}">{{dataList.page.currentPage}}</a>
      </li>
      <li ng-if="dataList.page.totalPage>7 && dataList.page.currentPage>4 && dataList.page.currentPage<dataList.page.totalPage-3">
        <a ng-href="#/{{pageType}}/{{dataList.page.currentPage+1}}">{{dataList.page.currentPage+1}}</a>
      </li>
      <li ng-if="dataList.page.totalPage>7 && dataList.page.currentPage>4 && dataList.page.currentPage<dataList.page.totalPage-3">
        <a ng-href="javascript:void(0)">…</a>
      </li>
      <li ng-if="dataList.page.totalPage>7 && dataList.page.currentPage>4 && dataList.page.currentPage<dataList.page.totalPage-3">
        <a ng-href="#/{{pageType}}/{{dataList.page.totalPage}}">{{dataList.page.totalPage}}</a>
      </li>
      <li ng-if="dataList.page.totalPage>7 && dataList.page.currentPage>=dataList.page.totalPage-3">
        <a ng-href="#/{{pageType}}/1">1</a>
      </li>
      <li ng-if="dataList.page.totalPage>7 && dataList.page.currentPage>=dataList.page.totalPage-3">
        <a ng-href="javascript:void(0)">…</a>
      </li>
      <li ng-class="{active: page==dataList.page.currentPage}" ng-if="dataList.page.totalPage>7 && dataList.page.currentPage>=dataList.page.totalPage-3" ng-repeat="page in [dataList.page.totalPage-4,dataList.page.totalPage-3,dataList.page.totalPage-2,dataList.page.totalPage-1,dataList.page.totalPage]">
        <a ng-href="#/{{pageType}}/{{page}}">{{page}}</a>
      </li>
      <li>
        <a class="ends" ng-href="#/{{pageType}}/{{dataList.page.currentPage<dataList.page.totalPage ? dataList.page.currentPage+1:dataList.page.totalPage}}">&gt;&gt;</a>
      </li>
    </ul>
    <ul class="pages pull-left">
      <li class="last-page">
        <a ng-href="#/{{pageType}}/{{dataList.page.totalPage}}">尾页</a>
      </li>
    </ul>
	<ul class="datanumul pull-right">
      <li>
		<span>共&nbsp;&nbsp;{{dataList.page.rowCount}}&nbsp;&nbsp;条</span>
	  </li>
	  <li>
		每页<select id="page-size-sel" ng-model="pageSize" ng-change="pageList(pageSize)">
			<option value="10" ng-selected="dataList.page.pageSize == 10">10</option>
			<option value="20" ng-selected="dataList.page.pageSize == 20">20</option>
			<option value="30" ng-selected="dataList.page.pageSize == 30">30</option>
			<option value="50" ng-selected="dataList.page.pageSize == 50">50</option>
			<option value="100" ng-selected="dataList.page.pageSize == 100">100</option>
		</select>条
	  </li>
	</ul>
</div>