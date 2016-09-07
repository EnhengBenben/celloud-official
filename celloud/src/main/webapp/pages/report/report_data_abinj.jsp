<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>报告管理</li>
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
      <div class="content-body">
        <section>
	        <h2>Neighbor-Joining 进化距离图</h2>
		    <div class="m-boxCon">
				<img style="padding-left: 70px;" src="{{uploadPath}}{{abinj.userId}}/{{abinj.appId}}/{{abinj.projectId}}/{{abinj.resultPng}}" height="500px;" width="500px;">
		    </div>
	    </section>
	   </div>
     </div>
 </div>