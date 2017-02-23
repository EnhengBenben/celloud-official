<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<%=request.getContextPath()%>/css/app.css" rel="stylesheet" type="text/css" />
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>应用市场</li>
    </ol>
	<section class="content">
		<div class="row">
		  <div class="col-xs-10">
			<div class="mainpage" id="appMain">
				<div class="pro-body">
					<!-- <input type="hidden" id="app-detail-appId" value="{{app.appId}}"> -->
					<div class="item-list">
					  <div class="item-left">
						<div class="product-info-top-wrap">
					  	  <div class="product-info-hd clearfix">
					        <div class="view">
					          <img ng-src="<%=request.getContextPath()%>/app/image?file={{app.pictureName}}">
					        </div>
					        <div class="itemInfo">
					          <h5>
					          	<span ng-bind="app.appName"></span>
					            <span ng-if="app != undefind" id="manageAppBtns" style="display:inline-block;position:relative;margin-left:20px;" data-step="2" data-intro="" data-position="bottom" data-img="changedApp.png">
					            	<a ng-if="app.runType==0" class="btn btn-celloud-success btn-flat" style="padding-top: 10px;" ng-href="{{app.address}}" target="_blank"><i class="fa fa-plus"></i>&nbsp;点击使用</a>
					                <a ng-if="app.runType==2" class="btn btn-celloud-success btn-flat" style="padding-top: 10px;" ng-href="{{app.address}}" target="_blank"><i class="fa fa-plus"></i>&nbsp;点击使用</a>
				                    <a ng-if="app.runType!=0 && app.isAdded==0" class="btn btn-celloud-success btn-flat" style="padding-top: 10px;" href="javascript:void(0);" ng-click="addApp(app.appId)" id="toAddApp"><i class="fa fa-plus"></i>&nbsp;添加</a>
				                    <a ng-if="app.runType!=0 && app.isAdded==1" class="btn btn-celloud-close btn-flat" style="padding-top: 10px;" href="javascript:void(0);" ng-click="removeApp(app.appId)" id="toAddApp"><i class="fa fa-minus"></i>&nbsp;取消添加</a>
				                    <a ng-if="app.runType!=0 && app.isAdded==null" class="btn btn-celloud-close btn-flat" style="padding-top: 10px;" href="javascript:void(0);">&nbsp;不需要添加</a>
					          </span>
					         </h5>
					          <div class="unlinedate">上线时间：<span class="date" ng-bind="app.createDate | date:'yyyy-MM-dd'"></span></div>
					          <div class="intro">
					            <ul>
					              <li>分类：<span ng-bind="app.classifyNames"></span></li>
					              <li>提供者：<span ng-bind="app.companyName==null?'上海华点云生物科技有限公司':app.companyName"></span>
					              </li>
					            </ul>
					          </div>
					        </div>
					      </div>
					      <div class="select-one">
					        <div class="price-con">
					            <span class="text" ng-if="userInfo.username == 'xiawt'">
					                <span id="app-price-label">金额</span>：
					                <em id="total-price"><c:if test="${empty app.price }">0</c:if>${app.price }</em>
					                <span style="font-size:14px;color:#f60">元</span>
					            </span>
					        </div>
					      </div>
					    </div>
					  </div>
					</div>
					<div class="item-intro">
					  <div class="link">
					    <div class="inner-link">
					      <ul id="toAppMoreDetailUl">
					        <li class="select" style="border-left:0;" id="toAppIntro" ng-click="toAppMoreDetail('toAppIntro')"><a href="javascript:void(0);">产品介绍</a></li>
					        <li style="border-right: 0;" class="" id="toAppScreeen" ng-click="toAppMoreDetail('toAppScreeen')"><a href="javascript:void(0);">报告截图</a></li>
					      </ul>
					    </div>
					  </div>
					  <div class="box-icon" id="1">
					    <h5>应用介绍</h5>
					    <p ng-bind-html="app.appDoc"></p>
					  </div>
					  <div class="box-icon" id="2">
						<h5>报告截图</h5>
						<div class="comment-list2">
						  <div class="app-detail-imgs">
						        <div ng-if="screenList.length > 0" id="appScreen" class="carousel slide" data-ride="carousel">
					                 <ol class="carousel-indicators app-detail-slider">
					                  <li ng-repeat="screen in screenList" data-target="#appScreen" data-slide-to="{{$index}}" ng-class="{active:$index==0}"></li>
					                 </ol>
					                 <div class="app-detail-imgcontainer">
					                 <div class="carousel-inner">
					                    <div ng-repeat="screen in screenList" class="item" ng-class="{active:$index==0}">
					                      <img style="height:200px;width:100%" ng-src="<%=request.getContextPath()%>/app/screen?file={{screen.screenName}}" alt="First slide">
					                    </div>
					                 </div>
					                 <a class="left carousel-control" data-target="#appScreen" data-slide="prev">
					                   <span class="fa fa-angle-left"></span>
					                 </a>
					                 <a class="right carousel-control" data-target="#appScreen" data-slide="next">
					                   <span class="fa fa-angle-right"></span>
					                 </a>
					                 </div>
				                </div>
				                <div ng-if="screenList.length <= 0">
						         	无截图
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