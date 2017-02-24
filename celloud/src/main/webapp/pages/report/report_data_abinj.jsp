<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>ABI_NJ报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{abinj.appName}}
        </p>
        <p> 文件名称：
            {{abinj.fileName}}({{abinj.dataKey}})
        </p>
      </div>
      <div>
        <section class="m-box">
	        <h2>Neighbor-Joining 进化距离图</h2>
		    <div class="m-boxCon">
				<img style="padding-left: 70px;" ng-src="{{uploadPath}}{{abinj.userId}}/{{abinj.appId}}/{{abinj.projectId}}/{{abinj.resultPng}}" height="500px;" width="500px;">
		    </div>
	    </section>
	   </div>
     </div>
 </div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>