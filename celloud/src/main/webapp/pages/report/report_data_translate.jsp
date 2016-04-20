<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<div class="m-file">
		<dl class="dl-horizontal datareport-title">
          <dt>项目名称：</dt>
          <dd>${project.projectName}</dd>
          <dt>应用名称：</dt>
          <dd>${translate.appName}</dd>
          <dt>文件名称：</dt>
          <dd class="force-break">${translate.fileName}(${translate.dataKey})</dd>
        </dl>
        <div class="toolbar">
        	<a class="btn btn-warning btn-flat" href="${toolsPath }Procedure!miRNADownload?userId=${translate.userId }/${translate.appId }/${translate.dataKey }/result.txt" class="btn btn-default"><i class="fa fa-file-pdf-o"></i>结果下载</a>
	    </div>
	</div>
	<c:if test="${empty translate.result }">
		<div class="m-box">
			<div class="m-boxCon result">
				对不起，程序运行尚未生成可展示的结果，请稍后查询
			</div>
		</div>
	</c:if>
	<c:if test="${not empty translate.result }">
		<div class="m-box">
			<h2><i class="i-report1"></i>输入序列</h2>
			<div class="m-boxCon result">
				${translate.source }
			</div>
		</div>
		<div class="m-box m-box-yc">
			<h2><i class="i-edit"></i>输出序列</h2>
			<div class="m-boxCon result">
				${translate.result }
			</div>
		</div>
	</c:if>
</div>
