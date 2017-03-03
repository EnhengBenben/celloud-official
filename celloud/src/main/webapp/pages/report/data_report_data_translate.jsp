<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport" ng-controller="commonReport">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>translate_simplified报告</li>
    </ol>
    <div class="content">
        <div class="content-left col-xs-10">
	      <div class="content-header">
	        <p> 项目名称：
	            {{project.projectName}}
	        </p>
	        <p> 应用名称：
	        	{{translate.appName}}
	        </p>
	        <p> 文件名称：
	            {{translate.fileName}}({{translate.dataKey}})
	        </p>
	        <div class="btn-group">
		        <a class="btn -middle" ng-href="/report/down?path={{translate.userId}}/{{translate.appId}}/{{translate.dataKey}}/result.txt"><i class="fa fa-file-pdf-o"></i>结果下载</a>
	        </div>
	      </div>
	      <div>
		    	<section class="m-box" ng-if="translate.result == null">
					<div class="m-boxCon result">
						对不起，程序运行尚未生成可展示的结果，请稍后查询
					</div>
		    	</section>
		    	<section class="m-box" ng-if="translate.result != null">
		    		<div class="m-box">
						<h2><i class="i-report1"></i>输入序列</h2>
						<div class="m-boxCon result">
							{{translate.source}}
						</div>
					</div>
					<div class="m-box m-box-yc">
						<h2><i class="i-edit"></i>输出序列</h2>
						<div class="m-boxCon result">
							{{translate.result}}
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