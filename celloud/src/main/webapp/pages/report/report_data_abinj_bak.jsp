<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div>
	<!--文件名称-->
	<div class="m-file">
	    <dl class="dl-horizontal datareport-title">
          <dt>项目名称：</dt>
          <dd>${project.projectName}</dd>
          <dt>应用名称：</dt>
          <dd>${abinj.appName}</dd>
          <dt>文件名称：</dt>
          <dd class="force-break">${abinj.fileName}(${abinj.dataKey})</dd>
        </dl>
	</div>
	<div class="m-box">
	 	<h2>Neighbor-Joining 进化距离图</h2>
	    <div class="m-boxCon">
			<img style="padding-left: 70px;" src="${uploadPath}${abinj.userId }/${abinj.appId }/${abinj.projectId }/${abinj.resultPng }" height="500px;" width="500px;">
	    </div>
	</div>
</div>
