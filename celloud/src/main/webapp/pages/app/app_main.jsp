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
		<div class="row">
		  <div class="col-xs-10">
			<div class="mainpage" id="appMain">
			  <div class="y-row operation-serve box box-success" data-spm="16">
		  		<div class="info">
		    	  <p>应用市场是CelLoud的一个开放平台，为用户提供专业、精准的生物信息分析服务。</p>
		  		</div>
		  		<ul id="appClassifyUl" class="app-classify-ul" data-step="2" data-intro="" data-position="bottom" data-img="checkapp.png">
			  	 	<li id="classifypidLi{{pc.classifyId}}" ng-repeat="pc in pclassifys">
			  	 	<a href="javascript:void(0)" ng-click="toSclassifyApp(pc.classifyId,pc.classifyName)" ng-bind="pc.classifyName"></a></li>
		  		</ul>
			  </div>
			  <div id="sclassify" ng-if="sclassifys.length > 0">
	  	    	  <div ng-repeat="sc in sclassifys" ng-if="classifyAppMap[sc.classifyId].length > 0" class="y-row" style="padding-bottom: 20px;"  data-spm="17">
		      		<div class="common-normal common-slide common-normals">
		        	  <div class="normal-tit normal-title">
		         		<p class="link"><a class="bc-a-tit" href="javascript:void(0);" ng-click="toMoreApp(sc.classifyPid,sc.classifyId,1,1)" title="">获取更多&gt;</a></p>
		         		<h4 ng-bind="sc.classifyName"></h4>
		        	  </div>
		        	  <div class="normal-slide">
		          		<div class="J_tab">
		            	  <div class="tab-content">
		              		<div class="tab-pannel">
					    	  <ul class="slide-con guess-like-box">
				              		<li ng-repeat="app in classifyAppMap[sc.classifyId]" ng-if="$index < 5">
			                    	  <div class="detail">
			                      		<div class="picbox">
			                        	  <div class="pic">
			                          		<p><a ng-href="{{'#/app/detail/' + app.appId}}"><img ng-src="<%=request.getContextPath()%>/app/image?file={{app.pictureName}}" width="48px;" height="48px;"></a></p>
			                        	  </div>
			                      		</div>
			                      		<p class="appname" ng-bind="app.appName"></p>
			                      		<p class="company" ng-bind="app.createDate | date:'yyyy-MM-dd'"></p>
			                    	  </div>
			                    	  <div class="button" style="color: #ff6600"> <a ng-href="{{'#/app/detail/' + app.appId}}">查看详情<i class="fa fa-arrow-circle-o-right"></i></a> </div>
			                    	  <span class="app_mark"></span>
			                  		</li>
		                	  </ul>
		              		</div>
		            	  </div>
		          		</div>
		        	  </div>
		      		</div>
		    	  </div>
			  </div>
			</div>
		  </div>
		  <div class="col-xs-2 pull-right myApplist" id="myAppDiv">
		  <div class="nominate" style="display: block;">
		  <h5>已添加的APP</h5>
  			<ul>
			  <div ng-if="appList == null || appList == ''" class="col-md-12">结果为空 </div>
			  <li ng-if="appList != null" ng-repeat="app in appList">
			      <a ng-href="{{'#/app/detail/' + app.appId}}">
			        <img ng-src="<%=request.getContextPath()%>/app/image?file={{app.pictureName}}" alt="">
			      </a>
			      <div class="intro">
			        <h6 style="overflow: hidden;" title="{{app.appName}}">
			          <a class="reco-hd-link" ng-href="{{'#/app/detail/' + app.appId}}">
			          	{{app.appName}}
			          </a>
			        </h6>
			       	<p><a href="javascript:void(0);" ng-click="removeApp(app.appId)" >取消添加</a></p>
			      </div>
			   </li>
		   </ul>
		  </div>
		  </div>
		</div>
	</section>
</div>
<script src="<%=request.getContextPath()%>/js/app.js?version=1.1" type="text/javascript"></script>
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