<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_app_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>应用市场</li>
    </ol>
    <div class="content">
		<div class="app-header">
			<div class="view">
				<div class="swiper-container" id="app-show-swipper">
					<div class="swiper-wrapper">
						<div class="swiper-slide" ng-repeat="app in recommendApps">
							<a style="cursor: auto;"><img ng-src="app/recommendMax?file={{app.pictureName}}" /></a>
						</div>
					</div>
				</div>
			</div>
			<div class="preview">
				<div class="swiper-container" id="app-nav-swipper">
				    <i class="fa fa-caret-up" aria-hidden="true" ng-click="appSwiperPre()"></i>
					<div class="swiper-wrapper">
						<div class="swiper-slide" ng-repeat="app in recommendApps">
							<a><img ng-src="app/recommendMin?file={{app.pictureName}}" /><span>{{app.appName}}</span></a>
						</div>
					</div>
					<i class="fa fa-caret-down" aria-hidden="true" ng-click="appSwiperNext()"></i>
				</div>
			</div>
		</div>
		<div class="app-types">
			<div class="type-head">
				<h5>精选产品</h5>
				<a class="show-all" ng-href="#/app/list/{{classifys[0].classifyId}}">显示全部</a>
			</div>
			<div class="type-content app-list">
				<ul class="row">
					<li class="col-sm-3" ng-repeat="app in classicApps">
						<div class="inner">
							<a class="app-logo" ng-href="#app/detail/{{app.appId}}">
								<img ng-src="app/image?file={{app.pictureName}}" />
							</a>
							<div class="inner-content">
								<h5>{{app.appName}}</h5>
								<p>{{app.classifyName}}</p>
								<div class="score">
								    <div class="score-icons">
					                    <span class="score-icon {{app.avgScore|scoreFilter:1}}"></span>
					                    <span class="score-icon {{app.avgScore|scoreFilter:2}}"></span>
					                    <span class="score-icon {{app.avgScore|scoreFilter:3}}"></span>
					                    <span class="score-icon {{app.avgScore|scoreFilter:4}}"></span>
										<span class="score-icon {{app.avgScore|scoreFilter:5}}"></span>
									</div>
									<div class="score-text">
										<span class="score-count">{{app.scoreCount}}</span>
										<span>份测评</span>
									</div>
								</div>
								<div class="footer">
									<button class="button-gray" ng-click="getApp(app.appId,app.isAdd)" ng-if="app.isAdd!=1">获取</button>
									<button class="button-gray add" ng-click="getApp(app.appId,app.isAdd)" ng-if="app.isAdd==1">已获取</button>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div ng-repeat="classifysApp in classifysApps">
				<div class="type-head">
					<h5>{{classifysApp.classifyName}}</h5>
					<a class="show-all" ng-href="#/app/list/{{classifysApp.classifyId}}">显示全部</a>
				</div>
				<div class="type-content app-list">
				<ul class="row">
					<li class="col-sm-3" ng-repeat="app in classifysApp.appList">
						<div class="inner">
							<a class="app-logo" ng-href="#app/list/{{classifysApp.classifyId}}/detail/{{app.appId}}">
								<img ng-src="app/image?file={{app.pictureName}}" />
							</a>
							<div class="inner-content">
								<h5>{{app.appName}}</h5>
								<p>{{classifysApp.classifyName}}</p>
								<div class="score">
									<div class="score-icons">
                                        <span class="score-icon {{app.avgScore|scoreFilter:1}}"></span>
                                        <span class="score-icon {{app.avgScore|scoreFilter:2}}"></span>
                                        <span class="score-icon {{app.avgScore|scoreFilter:3}}"></span>
                                        <span class="score-icon {{app.avgScore|scoreFilter:4}}"></span>
                                        <span class="score-icon {{app.avgScore|scoreFilter:5}}"></span>
                                    </div>
                                    <div class="score-text">
                                        <span class="score-count">{{app.scoreCount}}</span>
                                        <span>份测评</span>
                                    </div>
								</div>
								<div class="footer">
									<button class="button-gray" ng-click="getApp(app.appId)" ng-if="app.isAdd!=1">获取</button>
                                    <button class="button-gray add" ng-click="getApp(app.appId)" ng-if="app.isAdd==1">已获取</button>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
		    </div>
		</div>
	</div>
</div>	