<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form class="form form-horizontal app-detail-form">
	<div class="app-detail-toolbar" style="padding: 10px 30px 0">
		<div class="app-detail-abstract">
			<div class="app-detail-logo">
				<img src="<%=request.getContextPath()%>/images/app/${app.pictureName}">
			</div>
			<div class="app-detail-abs">
				<h3 class="app-detail-name">${app.softwareName }</h3>
				<p class="app-detail-author">
					提供者&nbsp;:&nbsp;<span class="time">${app.companyName }</span>&nbsp;
					上线 &nbsp;:&nbsp;<span class="time">${app.createDate }</span>&nbsp; 	分类&nbsp;:&nbsp;
					<span class="time">${app.classifyNames}</span>
				</p>
			</div>
		</div>
		<div class="app-detail-button">
		  <c:choose>
		  	<c:when test="${app.isAdded==0 }">
			  <a class="btn btn-celloud-success btn-flat" href="javascript:void()" onclick="addApp(${app.softwareId })" id="toAddApp"><i class="fa fa-plus"></i>&nbsp;添加</a>
		  	</c:when>
		  	<c:otherwise>
		  	  <a class="btn btn-celloud-close btn-flat" href="javascript:void()" onclick="removeApp(${app.softwareId })" id="toAddApp"><i class="fa fa-minus"></i>&nbsp;取消添加</a>
		  	</c:otherwise>
		  </c:choose>
		</div>
	</div>
	<ul class="page-tabs app-detail-tabs">
	  <li class="app-detail-tab selected" data-tab="Intro"><a style="width: 150px; text-align: center; font-weight: bold">简介</a></li>
	  <li class="app-detail-tab" data-tab="Services" style="display: none"><a style="width: 150px; text-align: center; font-weight: bold; color: #57b382">服务及价格</a></li>
	</ul>
	<div class="tab-content">
		<div class="app-detail-info">
		  <div class="app-detail-imgs">
			  <c:choose>
			    <c:when test="${screenList.size()>0 }">
			      <div id="appScreen" class="carousel slide" data-ride="carousel">
                   <ol class="carousel-indicators app-detail-slider">
                     <c:forEach items="${screenList }" var="screen" varStatus="size">
	                   <li data-target="#appScreen" data-slide-to="${size.index}" class="<c:if test='${size.index==0}'>active</c:if>"></li>
                     </c:forEach>
                   </ol>
                   <div class="app-detail-imgcontainer">
                   <div class="carousel-inner">
                     <c:forEach items="${screenList }" var="screen" varStatus="size">
                      <div class="item <c:if test='${size.index==0}'>active</c:if>">
                        <img style="height:200px;width:100%	" src="<%=request.getContextPath()%>/images/screenshot/${screen.screenName}" alt="First slide">
                      </div>
                     </c:forEach>
                   </div>
                   <a class="left carousel-control" href="#appScreen" data-slide="prev">
                     <span class="fa fa-angle-left"></span>
                   </a>
                   <a class="right carousel-control" href="#appScreen" data-slide="next">
                     <span class="fa fa-angle-right"></span>
                   </a>
                   </div>
                  </div>
			    </c:when>
			    <c:otherwise>
			       无截图
			    </c:otherwise>
			  </c:choose>
	      </div>
		  <div class="app-detail-misc">
		    <div class="app-detail-desp">
				<p class="app-detail-desp-long">
					<pre>【简介】
					${app.intro }</pre>
				</p>
				<p class="app-detail-desp-long">
					【详细】	
				${app.appDoc }
				</p>
			</div>
		  </div>
		</div>
	</div>
</form>
