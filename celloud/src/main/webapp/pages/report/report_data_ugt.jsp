<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>UGT报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{ugt.appName}}
        </p>
        <p> 文件名称：
            {{ugt.fileName}}({{ugt.dataKey}})
        </p>
      </div>
      <div>
        <section class="m-box">
	        <h2><i class="i-edit"></i>突变类型</h2>
		    <div class="m-boxCon result">
	    		<span ng-if="ugt.position!=null && ugt.position!=''" ng-bind-html="ugt.position"></span>
	    		<span ng-if="ugt.position==null || ugt.position==''">未检测到突变</span>
		    </div>
	    </section>
	    <section class="m-box">
			<h2>
				<i class="i-edit"></i>SNP
			</h2>
		    <div class="m-boxCon result" id="report_tb">
		    	<span ng-if="ugt.mutationPosition != null" ng-bind-html="ugt.mutationPosition"></span>
		    	<span ng-if="ugt.mutationPosition == null">数据正常，未找到其他突变。</span>
		    </div>
	     </section>
	     <section class="m-box">
	     	<h2><i class="i-edit"></i>原始序列</h2>
		    <div class="m-boxCon result" id="_seq">
				{{ugt.seq}}
		    </div>
	     </section>
         <section class="m-box">
	         <h2><i class="i-dna"></i>原始峰图</h2>
			 <div ng-if="ugt.original != null" ng-repeat="original in ugt.original" class="m-boxCon result">
				<a ng-click="bigOrigin(uploadPath + ugt.userId + '/' + ugt.appId + '/' + ugt.dataKey + '/SVG/' + original,'original' + ($index+1));" >
					<img ng-src="{{uploadPath}}{{ugt.userId}}/{{ugt.appId}}/{{ugt.dataKey}}/SVG/{{original}}" class="originImg" id="original{{$index+1}}">
				</a>
		     </div>
		     <div ng-if="ugt.original == null" class="m-boxCon result">
		    样本异常，无法检测
		     </div>
         </section>
	   </div>
     </div>
 </div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>