<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_app_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>应用市场</li>
      <li>{{nowCName}}</li>
    </ol>
    <div class="content">
		<nav class="app-classify-nav">
			<ul>
			    <div ng-repeat="classify in sclassifys">
				    <li id="app-classify-{{classify.classifyId}}" ng-class="{active: $index==0}" ng-click="getAppsByCid(classify.classifyId,classify.classifyName)">{{classify.classifyName}}</li>
			    </div>
			</ul>
		</nav>
		<div class="app-classify-list">
			<ul>
				<li ng-repeat="app in appPageInfo.datas">
					<div class="inner">
						<div class="app-logo">
							<img ng-src="app/image?file={{app.pictureName}}" />
						</div>
						<div class="inner-content">
							<h5>{{app.appName}}</h5>
							<p>{{app.intro}}</p>
							<div class="date">
								<span>{{app.createDate | date:"yyyy-MM-dd"}}上线</span>
							</div>
							<div class="buttons">
								<a class="button-gray" ng-click="getApp(app.appId)" ng-if="app.isAdd!=1">获取</a>
                                <a class="button-gray add" ng-click="getApp(app.appId)" ng-if="app.isAdd==1">已获取</a>
								<a class="button-gray detail" ng-href="#app/detail/{{app.appId}}">查看详情</a>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</div>
		<pagination page="appPageInfo.page" change="pageQuery(page,pageSize)"></pagination>
	</div>
</div>