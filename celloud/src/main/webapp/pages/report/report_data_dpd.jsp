<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>DPD报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{dpd.appName}}
        </p>
        <p> 文件名称：
            {{dpd.fileName}}({{dpd.dataKey}})
        </p>
      </div>
      <div>
        <section class="m-box">
	        <h2><i class="i-edit"></i>突变类型</h2>
		    <div class="m-boxCon result">
	    		<span ng-if="dpd.position != null && dpd.position != ''" ng-bind-html="dpd.position"></span>
	    		<span ng-if="dpd.position == null || dpd.position == ''">未检测到突变</span>
		    </div>
	    </section>
	    <section class="m-box">
			<h2>
				<i class="i-edit"></i>SNP
				<span class="filter">
					<input type="text" value="5" id="_snum">
					<a href="javascript:void(0)" class="btn -low" ng-click="searchTable()"><i class="i-filter"></i>筛选</a>
				</span>
			</h2>
		    <div class="m-boxCon result" id="report_tb">
				<table id="_sr">
				</table>
		    </div>
	     </section>
	     <section class="m-box">
	     	<div id="egfrTable" style="display: none;">
		    	<span ng-if="dpd.mutationPosition != null">{{dpd.mutationPosition}}</span>
		    	<span ng-if="dpd.mutationPosition == null">数据正常，未找到其他突变。</span>
			</div>
	     </section>
         <section class="m-box">
	         <h2><i class="i-edit"></i>原始序列</h2>
		     <div class="m-boxCon result" id="_seq">
				{{dpd.seq}}
		     </div>
         </section>
         <section class="m-box">
	         <h2><i class="i-dna"></i>原始峰图</h2>
	 		 <div ng-if="dpd.original!=null" ng-repeat="original in dpd.original" class="m-boxCon result">
 				<a ng-click="bigOrigin(uploadPath + dpd.userId + '/' + dpd.appId + '/' + dpd.dataKey + '/SVG/' + original,'original' + ($index+1));" >
 					<img name="imgSrc" class="originImg" ng-src="{{uploadPath}}{{dpd.userId}}/{{dpd.appId}}/{{dpd.dataKey}}/SVG/{{original}}" id="original{{$index+1}}">
 				</a>
	 	     </div>
		     <div ng-if="dpd.original==null" class="m-boxCon result">
		     样本异常，无法检测
		     </div>
	     </section>
	   </div>
     </div>
 </div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>