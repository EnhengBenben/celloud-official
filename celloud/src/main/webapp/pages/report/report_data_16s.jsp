<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>报告管理</li>
      <li>s16报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{s16.appName}}
        </p>
        <p> 文件名称：
            {{s16.fileName}}({{s16.dataKey}})
        </p>
      </div>
      <div class="content-body">
        <section>
	        <h2><i class="i-report1"></i>数据统计</h2>
			<div ng-bind-html="s16.resultTable" class="m-boxCon" id="16sResult">
			</div>
	    </section>
	   </div>
     </div>
 </div>