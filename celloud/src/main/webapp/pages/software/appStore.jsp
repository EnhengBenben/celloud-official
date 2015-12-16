<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<%=request.getContextPath() %>/dist/css/productDetail.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/dist/css/productList.css" rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath() %>/dist/css/getNoResult.css" rel="stylesheet" type="text/css" />
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:showAppStore()"><i class="fa fa-cubes"></i> 应用市场</a></li>
    <li class="active"><a href="javascript:void()" id="rootClassifyName">首页</a></li>
    <li class="hide"><a href="javascript:void()" id="secondClassifyName">二级分类</a></li>
    <li class="hide"><a href="javascript:void()" id="appNameTitle">应用名称</a></li>
  </ol>
</section>
<section class="content">
<input type="hidden" id="defaultPid" value="${pclassifys.get(0).classifyId }">
<input type="hidden" id="defaultPname" value="${pclassifys.get(0).classifyName }">
<div class="row">
  <div class="col-xs-10">
	<div class="mainpage" id="appMain">
	  <div class="y-row operation-serve"  data-spm="16">
  		<div class="info">
    	  <p>应用市场是CelLoud的一个开放平台，为用户提供科学精致的服务。</p>
  		</div>
  		<ul>
  		  <c:if test="${pclassifys.size()>0}">
	  	 	<c:forEach items="${pclassifys}" var="pc" varStatus="status">
		      <li <c:if test="${status.index==1}"> data-step="1" data-intro="" data-position="bottom" data-img="checkapp.png" </c:if>><a href="javascript:toSclassifyApp(${pc.classifyId },'${pc.classifyName }')">${pc.classifyName }</a></li>
	  	 	</c:forEach>
  		  </c:if>
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
	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	$(document).ready(function(){
		initApp();
	});
</script>