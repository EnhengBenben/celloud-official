<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>报告管理</li>
    </ol>
    <div class="content">
      <form class="search-box-form">
      <div class="search-box">
        <ul class="search-type-list">
          <li class="search-type clearfix">
            <label>时&emsp;&emsp;间：</label>
            <div class="search-type-detail times">
              <ul class="search-info">
	            <li><a class="active" href="javascript:void(0)">全部</a></li>
	            <li><a href="javascript:void(0)">24h</a></li>
	            <li><a href="javascript:void(0)">3d</a></li>
	            <li><a href="javascript:void(0)">7d</a></li>
	            <li><a href="javascript:void(0)">15d</a></li>
	            <li><a href="javascript:void(0)">30d</a></li>
              </ul>
              <div class="search-btns">
                <input type="text">
                <span>-</span>
                <input type="text">
                <button class="btn btn-cancel">确定</button>
              </div>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>产品标签：</label>
            <div class="search-type-detail inline-detail {{reportMoreAppTag|chevronTypeDivFilter}}" ng-init="reportMoreAppTag=true">
              <ul class="search-info">
                <li><a class="active" href="javascript:void(0)">全部</a></li>
                <li><a href="javascript:void(0)">HBV</a></li>
                <li><a href="javascript:void(0)">HCV</a></li>
                <li><a href="javascript:void(0)">NIPT</a></li>
                <li><a href="javascript:void(0)">HBV</a></li>
                <li><a href="javascript:void(0)">HCV</a></li>
                <li><a href="javascript:void(0)">HBV</a></li>
                <li><a href="javascript:void(0)">HCV</a></li>
                <li><a href="javascript:void(0)">NIPT</a></li>
                <li><a href="javascript:void(0)">HBV</a></li>
                <li><a href="javascript:void(0)">HCV</a></li>
                <li><a href="javascript:void(0)">HBV</a></li>
                <li><a href="javascript:void(0)">HCV</a></li>
                <li><a href="javascript:void(0)">NIPT</a></li>
                <li><a href="javascript:void(0)">HBV</a></li>
                <li><a href="javascript:void(0)">HCV</a></li>
              </ul>
              <div class="search-btns">
                <button class="btn chevron-btn" ng-click="reportMoreAppTag=changeChevronType(reportMoreAppTag)">{{reportMoreAppTag|chevronTypeTextFilter}}<i ng-class="reportMoreAppTag|chevronTypeFaFilter" aria-hidden="true"></i></button>
              </div>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>数据标签：</label>
            <div class="search-type-detail inline-detail {{reportMoreDataTag|chevronTypeDivFilter}}" ng-init="reportMoreDataTag=true">
              <ul class="search-info">
                <li><a class="active" href="javascript:void(0)">全部</a></li>
                <li><a href="javascript:void(0)">aaaa</a></li>
              </ul>
	          <div class="search-btns">
	            <button class="btn chevron-btn" ng-click="reportMoreDataTag=changeChevronType(reportMoreDataTag)">{{reportMoreDataTag|chevronTypeTextFilter}}<i ng-class="reportMoreDataTag|chevronTypeFaFilter" aria-hidden="true"></i></button>
	          </div>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>状&emsp;&emsp;态：</label>
            <div class="search-type-detail">
              <ul class="search-info">
                <li><a class="active" href="javascript:void(0)">全部</a></li>
                <li><a href="javascript:void(0)">完成</a></li>
                <li><a href="javascript:void(0)">分析中</a></li>
                <li><a href="javascript:void(0)">等待分析</a></li>
                <li><a href="javascript:void(0)">数据上传中</a></li>
                <li><a href="javascript:void(0)">异常终止</a></li>
                <li><a href="javascript:void(0)">实验中</a></li>
                <li><a href="javascript:void(0)">送样中</a></li>
              </ul>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>样本编码：</label>
            <div class="search-type-detail">
              <input type="text" placeholder="扫码或输入编号">
            </div>
          </li>
        </ul>
        </form>
      </div>
      <table class="table table-main">
        <thead>
          <tr>
            <th>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="demo-checkbox1">
                <span class="info"></span>
              </label>
            </th>
            <th>文件名称</th>
            <th>样品编号</th>
            <th>产品标签</th>
            <th>数据标签</th>
            <th>生成时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="file in dataList.datas">
            <td>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="demo-checkbox1" ng-disabled="file.isRunning==1||file.tagName==null">
                <span class="info"></span>
              </label>
            </td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td>2016-05-05 12:12:12</td>
            <td>
              <a href="javascript:void(0)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
            </td>
          </tr>
        </tbody>
      </table>
      <ng-include src="'pages/partial/_partial_pagination_common.jsp'" ></ng-include>
    </div>
</div>
