<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<%=request.getContextPath()%>/css/app.css" rel="stylesheet" type="text/css" />
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>应用市场</li>
      <li>首页</li>
    </ol>
	<section class="content">
		<input type="hidden" id="defaultPid" value="{{pclassifys[0].classifyId}}">
		<input type="hidden" id="defaultPname" value="{{pclassifys[0].classifyName}}">
		<div class="row">
		  <div class="col-xs-10">
			<div class="mainpage" id="appMain">
			  <div class="y-row operation-serve box box-success" data-spm="16">
		  		<div class="info">
		    	  <p>应用市场是CelLoud的一个开放平台，为用户提供专业、精准的生物信息分析服务。</p>
		  		</div>
		  		<ul id="appClassifyUl" class="app-classify-ul" data-step="2" data-intro="" data-position="bottom" data-img="checkapp.png">
			  	 	<li id="classifypidLi{{pc.classifyId}}" ng-repeat="pc in pclassifys">
			  	 	<a href="javascript:void(0)" ng-click="toSclassifyApp(pc.classifyId,'pc.classifyName')">{{pc.classifyName}}</a></li>
		  		</ul>
			  </div>
			  <div id="sclassify">
			    
			  </div>
			</div>
		  </div>
		  <div class="col-xs-2 pull-right myApplist" id="myAppDiv">
		  	
		  </div>
		</div>
	</section>
</div>
<script src="<%=request.getContextPath()%>/js/app.js?version=1.0" type="text/javascript"></script>
<script type="text/javascript">
	var session_userId = <%=session.getAttribute("userId")%>;
	var sessionUserName = "<%=session.getAttribute("userName")%>";
	var pageAppNum = 10;
	var currentPage = 1;
	var sortFiled = "";
	var sortType = "desc";
	var classifyPid;
	var classifyId;
	var classifyfloor;
</script>