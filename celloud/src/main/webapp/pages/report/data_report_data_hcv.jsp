<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport" ng-controller="commonReport">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>HCV报告</li>
    </ol>
    <div class="content">
        <div class="content-left col-xs-10">
	      <div class="content-header">
	        <p> 项目名称：
	            {{project.projectName}}
	        </p>
	        <p> 应用名称：
	        	{{hcv.appName}}
	        </p>
	        <p> 文件名称：
	            {{hcv.fileName}}({{hcv.dataKey}})
	        </p>
	        <div class="btn-groups">
		        <a class="btn -low" target="_blank" ng-href="${pageContext.request.contextPath }/report/printHCV?projectId={{hcv.projectId }}&dataKey={{hcv.dataKey }}&appId={{hcv.appId }}">打印报告</a>
		        <a class="btn -middle" style="display: none;" href="javascript:void(0)" ng-click="change()"><i class="fa fa-folder-open-o"></i><span id="_change">显示更多</span></a>
	        </div>
	      </div>
	      <div>
	      	<section class="m-box" id="cfda">
	      	    <h2><i class="i-edit"></i>检测结果</h2>
		        <div class="m-boxCon result">
					<table class="table table-bordered table-condensed" id="hcvTable">
						<thead>
							<tr>
								<th>File Name<br>(文件名)</th>
								<th style="min-width: 70px;">Subtype<br>(亚型)</th>
								<th style="min-width: 100px;">Subject Name<br>(参考序列名)</th>
								<th style="min-width: 80px;">Identity<br>(相似度)</th>
								<th style="min-width: 200px;">Overlap/total<br>(比对上的长度/比对的总长度)</th>
								<th style="min-width: 70px;">E_value<br>(期望值)</th>
								<th style="min-width: 50px;">Score<br>(比分)</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>{{hcv.fileName}}</td>
								<td>{{hcv.subtype | hcvShowMore}}</td>
								<td>{{hcv.subjectName}}</td>
								<td>{{hcv.identity}}</td>
								<td>{{hcv.total}}</td>
								<td>{{hcv.evalue}}</td>
								<td>{{hcv.score}}</td>
							</tr>
						</tbody>
					</table>
		        </div>
		    </section>
	        <section class="m-box" id="nomal" style="display: none;">
		        <h2><i class="i-edit"></i>检测结果</h2>
		        <div class="m-boxCon result">
					<table class="table table-bordered table-condensed" id="hcvTable">
						<thead>
							<tr>
								<th>File Name<br>(文件名)</th>
								<th style="min-width: 70px;">Subtype<br>(亚型)</th>
								<th style="min-width: 100px;">Subject Name<br>(参考序列名)</th>
								<th style="min-width: 80px;">Identity<br>(相似度)</th>
								<th style="min-width: 200px;">Overlap/total<br>(比对上的长度/比对的总长度)</th>
								<th style="min-width: 70px;">E_value<br>(期望值)</th>
								<th style="min-width: 50px;">Score<br>(比分)</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>{{hcv.fileName}}</td>
								<td>{{hcv.subtype}}</td>
								<td>{{hcv.subjectName}}</td>
								<td>{{hcv.identity}}</td>
								<td>{{hcv.total}}</td>
								<td>{{hcv.evalue}}</td>
								<td>{{hcv.score}}</td>
							</tr>
						</tbody>
					</table>
		        </div>
		    </section>
		    <section class="m-box">
		    	<h2><i class="i-edit"></i>原始序列</h2>
			    <div class="m-boxCon result" id="seq" style="word-break: break-all;">
					{{hcv.seq}}
			    </div>
		    </section>
		    <section class="m-box">
		    	<h2><i class="i-dna"></i>原始峰图</h2>
			    <div class="m-boxCon result" ng-if="hcv.original['1_all_png'] != null">
					<a ng-click="bigOrigin(uploadPath + hcv.userId + '/' + hcv.appId + '/' +hcv.dataKey + '/SVG/' + hcv.original['1_all_png'],'listAll1Img');" >
						<img class="imgtop originImg" name="imgSrc" ng-src="{{uploadPath}}{{hcv.userId}}/{{hcv.appId}}/{{hcv.dataKey}}/SVG/{{hcv.original['1_all_png']}}" id="listAll1Img">
					</a>
			    </div>
			    <div class="m-boxCon result" ng-if="hcv.original['2_all_png'] != null">
					<a ng-click="bigOrigin(uploadPath + hcv.userId + '/' + hcv.appId + '/' +hcv.dataKey + '/SVG/' + hcv.original['2_all_png'],'listAll2Img');" >
						<img class="imgtop originImg" name="imgSrc" ng-src="{{uploadPath}}{{hcv.userId}}/{{hcv.appId}}/{{hcv.dataKey}}/SVG/{{hcv.original['2_all_png']}}" id="listAll2Img">
					</a>
			    </div>
			    <div class="m-boxCon result" ng-if="hcv.original['3_all_png']">
					<a ng-click="bigOrigin(uploadPath + hcv.userId + '/' + hcv.appId + '/' +hcv.dataKey + '/SVG/' + hcv.original['3_all_png'],'listAll3Img');" >
						<img class="imgtop originImg" name="imgSrc" ng-src="{{uploadPath}}{{hcv.userId}}/{{hcv.appId}}/{{hcv.dataKey}}/SVG/{{hcv.original['3_all_png']}}" id="listAll3Img">
					</a>
			    </div>
			    <div class="m-boxCon result" ng-if="hcv.original['4_all_png']">
					<a ng-click="bigOrigin(uploadPath + hcv.userId + '/' + hcv.appId + '/' +hcv.dataKey + '/SVG/' + hcv.original['4_all_png'],'listAll4Img');" >
						<img class="imgtop originImg" name="imgSrc" ng-src="{{uploadPath}}{{hcv.userId}}/{{hcv.appId}}/{{hcv.dataKey}}/SVG/{{hcv.original['4_all_png']}}" id="listAll4Img">
					</a>
			    </div>
			    <div class="m-boxCon result" ng-if="hcv.original['5_all_png']">
					<a ng-click="bigOrigin(uploadPath + hcv.userId + '/' + hcv.appId + '/' +hcv.dataKey + '/SVG/' + hcv.original['5_all_png'],'listAll5Img');" >
						<img class="imgtop originImg" name="imgSrc" ng-src="{{uploadPath}}{{hcv.userId}}/{{hcv.appId}}/{{hcv.dataKey}}/SVG/{{hcv.original['5_all_png']}}" id="listAll5Img">
					</a>
			    </div>
			    <div class="m-boxCon result" ng-if="hcv.original['6_all_png']">
					<a ng-click="bigOrigin(uploadPath + hcv.userId + '/' + hcv.appId + '/' +hcv.dataKey + '/SVG/' + hcv.original['6_all_png'],'listAll6Img');" >
						<img class="imgtop originImg" name="imgSrc" ng-src="{{uploadPath}}{{hcv.userId}}/{{hcv.appId}}/{{hcv.dataKey}}/SVG/{{hcv.original['6_all_png']}}" id="listAll6Img">
					</a>
			    </div>
		    </section>
		    <section class="m-box">
		    	<h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
				<div class="m-boxCon">
					<div class="row" id="charDiv">
				    </div>
				</div>
		    </section>
		   </div>
		 </div>
		 <div class="content-right col-xs-2">
	        <ng-include src="'pages/partial/_partial_datareportoperate.jsp'"></ng-include>
	     </div>
     </div>
 </div>
