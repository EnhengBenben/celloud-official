<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pagination text-center">
	<ul class="pages pull-left">
		<li class="first-page">
			<a id="prev-page" data-click="pagination-btn" ng-click="change({page:1,pageSize:page.pageSize})">首页{{dataPage}}</a>
		</li>
	</ul>
	<ul id="pagination-ul" class="pages pull-left">
		<!-- 显示prev -->
		<li>
			<a id="prev-page" class="ends" ng-click="change({page:page.currentPage>1?page.currentPage-1:1,pageSize:page.pageSize})">&lt;&lt;</a>
		</li>
		<li ng-class="{active: current==page.currentPage}" ng-if="page.totalPage<=7" ng-repeat="current in pageArray(page.totalPage)">
			<a ng-click="change({page:current,pageSize:page.pageSize})">{{current}}</a>
		</li>
		<li ng-class="{active: current==page.currentPage}" ng-if="page.totalPage>7 && page.currentPage<=4" ng-repeat="current in [1,2,3,4,5]">
			<a ng-click="change({page:current,pageSize:page.pageSize})">{{current}}</a>
		</li>
		<li ng-if="page.totalPage>7 && page.currentPage<=4">
			<a ng-href="javascript:void(0)">…</a>
		</li>
		<li ng-if="page.totalPage>7 && page.currentPage<=4">
			<a ng-click="change({page:page.totalPage,pageSize:page.pageSize})">{{page.totalPage}}</a>
		</li>
		<li ng-if="page.totalPage>7 && page.currentPage>4 && page.currentPage<page.totalPage-3">
			<a ng-click="change({page:1,pageSize:page.pageSize})">1</a>
		</li>
		<li ng-if="page.totalPage>7 && page.currentPage>4 && page.currentPage<page.totalPage-3">
			<a ng-href="javascript:void(0)">…</a>
		</li>
		<li ng-if="page.totalPage>7 && page.currentPage>4 && page.currentPage<page.totalPage-3">
			<a ng-click="change({page:page.currentPage-1,pageSize:page.pageSize})">{{page.currentPage-1}}</a>
		</li>
		<li class="active" ng-if="page.totalPage>7 && page.currentPage>4 && page.currentPage<page.totalPage-3">
			<a ng-click="change({page:page.currentPage,pageSize:page.pageSize})">{{page.currentPage}}</a>
		</li>
		<li ng-if="page.totalPage>7 && page.currentPage>4 && page.currentPage<page.totalPage-3">
			<a ng-click="change({page:page.currentPage+1,pageSize:page.pageSize})">{{page.currentPage+1}}</a>
		</li>
		<li ng-if="page.totalPage>7 && page.currentPage>4 && page.currentPage<page.totalPage-3">
			<a ng-href="javascript:void(0)">…</a>
		</li>
		<li ng-if="page.totalPage>7 && page.currentPage>4 && page.currentPage<page.totalPage-3">
			<a ng-click="change({page:page.totalPage,pageSize:page.pageSize})">{{page.totalPage}}</a>
		</li>
		<li ng-if="page.totalPage>7 && page.currentPage>=page.totalPage-3">
			<a ng-click="change({page:1,pageSize:page.pageSize})">1</a>
		</li>
		<li ng-if="page.totalPage>7 && page.currentPage>=page.totalPage-3">
			<a ng-href="javascript:void(0)">…</a>
		</li>
		<li ng-class="{active: current==page.currentPage}" ng-if="page.totalPage>7 && page.currentPage>=page.totalPage-3"
			ng-repeat="current in [page.totalPage-4,page.totalPage-3,page.totalPage-2,page.totalPage-1,page.totalPage]">
			<a ng-click="change({page:current,pageSize:page.pageSize})">{{current}}</a>
		</li>
		<li>
			<a class="ends" ng-click="change({page:page.currentPage<page.totalPage?(page.currentPage+1):page.totalPage,pageSize:page.pageSize})">&gt;&gt;</a>
		</li>
	</ul>
	<ul class="pages pull-left">
		<li class="last-page">
			<a ng-click="change({page:page.totalPage,pageSize:page.pageSize})">尾页</a>
		</li>
	</ul>
	<ul class="datanumul pull-right">
		<li>
			<span>共&nbsp;&nbsp;{{page.rowCount}}&nbsp;&nbsp;条</span>
		</li>
		<li>
			每页 <select class="form-control" id="page-size-sel" ng-model="pageSize" ng-change="change({page:1,pageSize:pageSize})">
				<option value="10" ng-selected="page.pageSize == 10">10</option>
				<option value="20" ng-selected="page.pageSize == 20">20</option>
				<option value="30" ng-selected="page.pageSize == 30">30</option>
				<option value="50" ng-selected="page.pageSize == 50">50</option>
				<option value="100" ng-selected="page.pageSize == 100">100</option>
			</select> 条
		</li>
	</ul>
</div>
