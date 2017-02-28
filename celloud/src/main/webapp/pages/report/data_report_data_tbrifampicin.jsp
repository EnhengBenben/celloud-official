<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport" ng-controller="commonReport">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>TBRifampicin报告</li>
    </ol>
    <div class="content">
        <div class="content-left col-xs-10">
	     <div class="content-header">
	       <p> 项目名称：
	           {{project.projectName}}
	       </p>
	       <p> 应用名称：
	       	{{tbrifampicin.appName}}
	       </p>
	       <p> 文件名称：
	           {{tbrifampicin.fileName}}({{tbrifampicin.dataKey}})
	       </p>
	       <div class="btn-groups">
	        <a class="btn -low" target="_blank" ng-href="${pageContext.request.contextPath }/report/printTBRifampicin?projectId={{tbrifampicin.projectId }}&dataKey={{tbrifampicin.dataKey }}&appId={{tbrifampicin.appId }}">打印报告</a>
	        <a class="btn -middle" ng-if="tbrifampicin.pdf != null && tbrifampicin.pdf != ''" ng-href="${pageContext.request.contextPath }/report/down?path={{tbrifampicin.userId}}/{{tbrifampicin.appId}}/{{tbrifampicin.dataKey}}/{{tbrifampicin.pdf}}">PDF下载</a>
	       </div>
	     </div>
	      <div>
		    <section class="m-box">
			    <h2>
					<i class="i-edit"></i>报告 
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
		     	<div id="egfrTable" style="display: none;">{{tbrifampicin.report}}</div>
		     </section>
	         <section class="m-box">
		         <h2>
					<i class="i-edit"></i>原始序列
				</h2>
			    <div class="m-boxCon result">
					<div ng-if="tbrifampicin != undefined && (tbrifampicin.seq == null || tbrifampicin.seq == '')" style="color: red;">程序分析异常，没有得到任何结果</div>
					<div ng-if="tbrifampicin.seq != null">
				    	{{tbrifampicin.seq}}
					</div>
				</div>
	         </section>
	         <section class="m-box">
		         <h2>
					<i class="i-dna"></i>原始峰图
				 </h2>
			 	 <div ng-if="tbrifampicin != null && tbrifampicin.original == null" class="m-boxCon">
				 	<div style="color: red;">程序分析异常，没有得到任何结果</div>
			 	 </div>
		 		 <div ng-if="tbrifampicin.original != null" ng-repeat="original in tbrifampicin.original" class="m-boxCon">
		 			<a ng-click="bigOrigin(uploadPath + tbrifampicin.userId + '/' + tbrifampicin.appId + '/' + tbrifampicin.dataKey + '/SVG/' + original,'original' + ($index+1));">
		 				<img name="imgSrc" ng-src="{{uploadPath}}{{tbrifampicin.userId}}/{{tbrifampicin.appId}}/{{tbrifampicin.dataKey}}/SVG/{{original}}" class="originImg" id="original{{$index+1}}">
		 		 	</a>
		 		 </div>
		     </section>
		     <section class="m-box">
		         <h2>
					<i class="i-celloud"></i>Celloud数据参数同比分析
				 </h2>
				 <div class="m-boxCon">
				 	<div class="row" id="charDiv"></div>
				 </div>
		     </section>
		   </div>
     </div>
	   <div class="content-right col-xs-2">
        <ng-include src="'pages/partial/_partial_datareportoperate.jsp'"></ng-include>
      </div>
 </div>