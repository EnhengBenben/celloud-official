<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);">报告管理</a></li>
      <li>{{fsocg.appName}}报告</li>
    </ol>
    <div class="content">
	    <div class="content-header">
	        <p> 应用名称：
	        	{{fsocg.appName}}
	        </p>
	        <p> 文件名称：
	            <span ng-repeat="data in fsocg.data">
	            	<span ng-if="$index == 0">
			            {{data.fileName}}({{data.dataKey}})<br/>
	            	</span>
	            	<span ng-if="$index != 0" style="padding-left: 75px">
		            	{{data.fileName}}({{data.dataKey}})<br/>
	            	</span>
	            </span>
	        </p>
	        <div class="btn-groups">
	        	<a class="btn -low" target="_blank" href="report/printFSocg?appId={{fsocg.appId}}&projectId={{fsocg.projectId}}&dataKey={{fsocg.dataKey}}"><i class="fa fa-print"></i>打印报告</a>
	        </div>
	      </div>
    	<section class="m-box"">
	        <h2>
	        	<i class="i-edit"></i>
	        	DNA
			</h2>
			<div class="m-boxCon result">
				<div ng-if="fsocg.dna.length > 1">
					<table class='table table-main'>
						<tr ng-repeat="info in fsocg.dna">
						    <td style="padding-left: 0px;">{{info[0]}}</td>
						    <td style="padding-left: 0px;">{{info[1]}}</td>
						    <td style="padding-left: 0px;">{{info[2]}}</td>
						    <td style="padding-left: 0px;">{{info[3]}}</td>
						    <td style="padding-left: 0px;">{{info[4]}}</td>
						    <td style="padding-left: 0px;">{{info[5]}}</td>
						    <td style="padding-left: 0px;">{{info[6]}}</td>
						    <td style="padding-left: 0px;">{{info[8]}}</td>
						    <td style="padding-left: 0px;">{{info[10]}}</td>
						</tr>
					</table>
				</div>
				<div ng-if="fsocg.dna.length == 1">
					<td>没有符合条件的结果</td>
				</div>
			</div>
	     </section>
    	<section class="m-box"">
	        <h2>
	        	<i class="i-edit"></i>
	        	RNA
			</h2>
			<div class="m-boxCon result">
				<div ng-if="fsocg.rna.length > 1">
					<table class='table table-main'>
						<tr ng-repeat="info in fsocg.rna">
						    <td>{{info}}</td>
						</tr>
					</table>
				</div>
				<div ng-if="fsocg.rna.length == 1">
					<td>没有符合条件的结果</td>
				</div>
			</div>
	     </section>
     </div>
 </div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>
