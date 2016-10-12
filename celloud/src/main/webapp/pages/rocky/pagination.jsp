<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="pagination text-center" ng-show="dataList.datas.length > 0">
	<ul class="checkboxul pull-left">
		<li class="checkbox-li">
			<label class="checkbox-lable"> <input class="checkbox" type="checkbox" name="demo-checkbox1"> <span class="info" style="top:12%;"></span>
			</label>
		</li>
		<li>全选</li>
		<li>
			<select>
				<option value="0">批量下载</option>
				<option value="1">批量归档</option>
				<option value="2">批量分发</option>
			</select>
		</li>
	</ul>
	<ul class="datanumul pull-right">
		<li>
			<span>共&nbsp;&nbsp;{{dataList.page.rowCount}}&nbsp;&nbsp;条</span>
		</li>
		<li>
			每页 <select id="page-size-sel" ng-model="rockyPageSize" ng-change="changePageSize()">
				<option value="10" ng-selected="dataList.page.pageSize == 10">10</option>
				<option value="20" ng-selected="dataList.page.pageSize == 20">20</option>
				<option value="30" ng-selected="dataList.page.pageSize == 30">30</option>
				<option value="50" ng-selected="dataList.page.pageSize == 50">50</option>
				<option value="100" ng-selected="dataList.page.pageSize == 100">100</option>
			</select>条
		</li>
	</ul>
	<ul id="pagination-ul" class="pages pull-right">
		<!-- 显示prev -->
		<li>
			<a id="prev-page" class="ends" data-click="pagination-btn" ng-click="paginationBtn(dataList.page.currentPage>1?dataList.page.currentPage-1:1)" data-page="{{dataList.page.currentPage>1?dataList.page.currentPage-1:1}}" href="javascript:void(0);">&lt;&lt;</a>
		</li>
			<li ng-if="dataList.page.totalPage <= 7" ng-repeat="step in [1,2,3,4,5,6,7]" ng-class="{active: step == dataList.page.currentPage}">
	            <a ng-if="step == dataList.page.currentPage && step <= dataList.page.totalPage" href="javascript:void(0);">{{step}}</a>
	            <a ng-if="step != dataList.page.currentPage && step <= dataList.page.totalPage" name="pagination-info" data-click="pagination-btn" ng-click="paginationBtn(step)" data-page="{{step}}" href="javascript:void(0)">{{step}}</a>
	        </li>
				
			<li ng-if="dataList.page.currentPage <= 4 && dataList.page.totalPage > 7" ng-repeat="step in [1,2,3,4,5]" ng-class="{active: step == dataList.page.currentPage}">
                <a ng-if="step == dataList.page.currentPage" href="javascript:void(0);">{{step}}</a>
                <a ng-if="step != dataList.page.currentPage" name="pagination-info" data-click="pagination-btn" ng-click="paginationBtn(step)" data-page="{{step}}" href="javascript:void(0)">{{step}}</a>
            </li>
			<li ng-if="dataList.page.currentPage <= 4 && dataList.page.totalPage > 7">
                <a href="javascript:void(0)">…</a>
            </li>
            <li ng-if="dataList.page.currentPage <= 4 && dataList.page.totalPage > 7">
                <a name="pagination-info" data-click="pagination-btn" ng-click="paginationBtn(dataList.page.totalPage)" data-page="{{dataList.page.totalPage}}" href="javascript:void(0)">{{dataList.page.totalPage}}</a>
            </li>
				
			<li ng-if="dataList.page.currentPage > 4 && dataList.page.currentPage < dataList.page.totalPage - 3 && dataList.page.totalPage > 7">
                <a name="pagination-info"  data-click="pagination-btn" ng-click="paginationBtn(1)" data-page="1" href="javascript:void(0)">1</a>
            </li>
            <li ng-if="dataList.page.currentPage > 4 && dataList.page.currentPage < dataList.page.totalPage - 3 && dataList.page.totalPage > 7">
                <a href="javascript:void(0)">…</a>
            </li>
            <li ng-if="dataList.page.currentPage > 4 && dataList.page.currentPage < dataList.page.totalPage - 3 && dataList.page.totalPage > 7">
                <a name="pagination-info"  data-click="pagination-btn" ng-click="paginationBtn(dataList.page.currentPage - 1)" data-page="{{dataList.page.currentPage-1}}" href="javascript:void(0)">{{dataList.page.currentPage - 1}}</a>
            </li>
            <li class="active" ng-if="dataList.page.currentPage > 4 && dataList.page.currentPage < dataList.page.totalPage - 3 && dataList.page.totalPage > 7">
                <a href="javascript:void(0);">{{dataList.page.currentPage}}</a>
            </li>
            <li ng-if="dataList.page.currentPage > 4 && dataList.page.currentPage < dataList.page.totalPage - 3 && dataList.page.totalPage > 7">
                <a name="pagination-info" data-click="pagination-btn" ng-click="paginationBtn(dataList.page.currentPage + 1)" data-page="{{dataList.page.currentPage + 1}}" href="javascript:void(0)">{{dataList.page.currentPage + 1}}</a>
            </li>
            <li ng-if="dataList.page.currentPage > 4 && dataList.page.currentPage < dataList.page.totalPage - 3 && dataList.page.totalPage > 7">
                <a href="javascript:void(0)">…</a>
            </li>
            <li ng-if="dataList.page.currentPage > 4 && dataList.page.currentPage < dataList.page.totalPage - 3 && dataList.page.totalPage > 7">
                <a name="pagination-info" data-click="pagination-btn" ng-click="paginationBtn(dataList.page.totalPage)" data-page="{{dataList.page.totalPage}}" href="javascript:void(0)">{{dataList.page.totalPage}}</a>
            </li>
				
			<li ng-if="dataList.page.currentPage >= dataList.page.totalPage - 3 && dataList.page.totalPage > 7">
                <a name="pagination-info" data-click="pagination-btn" ng-click="paginationBtn(1)" data-page="1"  href="javascript:void(0)">1</a>
            </li>
            <li ng-if="dataList.page.currentPage >= dataList.page.totalPage - 3 && dataList.page.totalPage > 7">
                <a href="javascript:void(0)">…</a>
            </li>
			<li ng-if="dataList.page.currentPage >= dataList.page.totalPage - 3 && dataList.page.totalPage > 7" ng-repeat="step in [1,2,3,4,5]" ng-class="{active: step == dataList.page.currentPage}">
                <a ng-if="dataList.page.totalPage - 5 + $index == dataList.page.currentPage" href="javascript:void(0);">{{dataList.page.totalPage - 5 + $index}}</a>
                <a ng-if="dataList.page.totalPage - 5 + $index != dataList.page.currentPage" name="pagination-info" data-click="pagination-btn"  ng-click="paginationBtn(dataList.page.totalPage - 5 + $index)" data-page="{{dataList.page.totalPage - 5 + $index}}" href="javascript:void(0)">{{dataList.page.totalPage - 5 + $index}}</a>
            </li>
		<li>
			<a id="next-page" class="ends" data-click="pagination-btn" ng-click="paginationBtn(dataList.page.currentPage < dataList.page.totalPage?dataList.page.currentPage+1:dataList.page.totalPage)" data-page="{{dataList.page.currentPage < dataList.page.totalPage?dataList.page.currentPage+1:dataList.page.totalPage}}" href="javascript:void(0)">&gt;&gt;</a>
		</li>
	</ul>
</div>