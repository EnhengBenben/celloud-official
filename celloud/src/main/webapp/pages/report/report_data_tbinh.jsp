<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>TB-INH报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{tbinh.appName}}
        </p>
        <p> 文件名称：
            {{tbinh.fileName}}({{tbinh.dataKey}})
        </p>
      </div>
      <div>
	    <section class="m-box">
		    <h2>
				<i class="i-edit"></i>Gene Name
			</h2>
			<div class="m-boxCon result">{{tbinh.geneName}}</div>
			<input type="hidden" id="_hidName" ng-model="tbinh.simpleGeneName">
	     </section>
         <section class="m-box">
	        <h2>
				<i class="i-edit"></i>Known mutation 
				<span class="filter">
					<input type="text" value="5" id="_snum1">
					<a href="javascript:void(0)" class="btn -low" ng-click="searchTable('_snum1','r1','_sr1')">
					<i class="i-filter"></i>筛选</a>
				</span>
			</h2>
			<div class="m-boxCon result">
				<table id="_sr1">
				</table>
			</div>
         </section>
         <section class="m-box">
	         <h2>
				<i class="i-edit"></i>Unknown mutation
				<span class="filter">
					<input type="text" value="5" id="_snum2">
					<a href="javascript:void(0)" class="btn -low" ng-click="searchTable('_snum2','r2','_sr2')">
					<i class="i-filter"></i>筛选</a>
				</span>
			 </h2>
			 <div class="m-boxCon result">
			 	<table id="_sr2">
			 	</table>
			 </div>
	     </section>
	     <section class="m-box">
	     	<div id="r1" style="display: none;">{{tbinh.position}}</div>
	     </section>
	     <section class="m-box">
			<div id="r2" style="display: none;">{{tbinh.mutationPosition}}</div>
	     </section>
	     <section class="m-box">
	     	<h2>
				<i class="i-edit"></i>Samples Statistic
			</h2>
			<div class="m-boxCon" id="_showPie" style="width: 400px;height:200px;"></div>
	     </section>
	     <section class="m-box">
	     	<h2>
				<i class="i-edit"></i>原始序列
			</h2>
			<div class="m-boxCon result" id="_seq">{{tbinh.seq}}</div>
	     </section>
	     <section class="m-box">
	         <h2>
				<i class="i-dna"></i>原始峰图
			 </h2>
		 	 <div ng-if="tbinh.original == null" class="m-boxCon">样本数据异常,无法检测</div>
	 		 <div ng-if="tbinh.original != null" class="m-boxCon"ng-repeat="original in tbinh.original">
	 			<a ng-click="bigOrigin(uploadPath + tbinh.userId + '/' + tbinh.appId + '/' + tbinh.dataKey + '/SVG/' + original,'original' + ($index+1));">
	 				<img name="imgSrc" ng-src="{{uploadPath}}{{tbinh.userId}}/{{tbinh.appId}}/{{tbinh.dataKey}}/SVG/{{original}}" class="originImg" id="original{{$index+1}}"><br/>
	 			</a>
	 		 </div>
	     </section>
	   </div>
     </div>
 </div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>