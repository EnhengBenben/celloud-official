<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_app_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>应用市场</li>
      <li>{{appInfos.classifys}}</li>
      <li>{{appInfos.app.appName}}</li>
    </ol>
    <div class="content">
		<div class="app-infos">
			<div class="app-left">
				<div class="app-logo">
					<img ng-src="app/image?file={{appInfos.app.pictureName}}" />
				</div>
				<a class="button-gray" ng-click="getApp(appInfos.app.appId)" ng-if="appInfos.isAdd!=1">获取</a>
                <a class="button-gray add" ng-click="getApp(appInfos.app.appId)" ng-if="appInfos.isAdd==1">已获取</a>
			</div>
			<div class="inner-content">
				<h5>{{appInfos.app.appName}}</h5>
				<ul class="row">
					<li class="col-sm-4">应用分类：{{appInfos.classifys}}</li>
					<li class="col-sm-3">版本：1.0.1</li>
				</ul>
				<ul class="row">
					<li class="col-sm-4">更新时间：{{appInfos.app.createDate | date:"yyyy-MM-dd HH:mm:ss"}}</li>
					<li class="col-sm-2">价格：{{appInfos.price}}¥</li>
					<li class="col-sm-6">提供者： {{appInfos.company}}</li>
				</ul>
				<p>应用介绍：{{appInfos.app.intro}}
				</p>
			</div>
		</div>
		<div class="app-screen">
			<div class="zoom">
				<img src="app/screen?file={{appInfos.screens[0].screenName}}" />
			</div>
			<div class="screen-list">
				<ul>
					<li ng-repeat="screen in appInfos.screens">
						<a><img ng-src="app/screen?file={{screen.screenName}}" /><i></i></a>
					</li>
				</ul>
			</div>
		</div>
		<div class="app-score">
			<h5>评分与评论</h5>
			<div class="score">
				<div class="score-icons">
					<span class="score-icon {{appInfos.avgScore|scoreFilter:5}}"></span>
					<span class="score-icon {{appInfos.avgScore|scoreFilter:4}}"></span>
					<span class="score-icon {{appInfos.avgScore|scoreFilter:3}}"></span>
					<span class="score-icon {{appInfos.avgScore|scoreFilter:2}}"></span>
					<span class="score-icon {{appInfos.avgScore|scoreFilter:1}}"></span>
				</div>
				<div class="score-text">
					<span class="score-count">{{appInfos.totalCount}}</span>
					<span>份评分</span>
				</div>
			</div>
			<div class="score progress-group">
				<div class="score-icons">
					<span class="score-icon score-gray"></span>
					<span class="score-icon score-gray"></span>
					<span class="score-icon score-gray"></span>
					<span class="score-icon score-gray"></span>
					<span class="score-icon score-gray"></span>
				</div>
				<div class="score-progress">
					<div class="progress">
					    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" ng-style="scoreStype5">
					      <span class="sr-only">60% Complete</span>
					    </div>
					</div>
					<span>{{countScore5}}</span>
				</div>
			</div>
			<div class="score progress-group">
				<div class="score-icons">
					<span class="score-icon score-gray"></span>
                    <span class="score-icon score-gray"></span>
                    <span class="score-icon score-gray"></span>
                    <span class="score-icon score-gray"></span>
				</div>
				<div class="score-progress">
					<div class="progress">
					    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" ng-style="scoreStype4">
					      <span class="sr-only">60% Complete</span>
					    </div>
					</div>
					<span>{{countScore4}}</span>
				</div>
			</div>
			<div class="score progress-group">
				<div class="score-icons">
					<span class="score-icon score-gray"></span>
                    <span class="score-icon score-gray"></span>
                    <span class="score-icon score-gray"></span>
				</div>
				<div class="score-progress">
					<div class="progress">
					    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" ng-style="scoreStype3">
					      <span class="sr-only">60% Complete</span>
					    </div>
					</div>
					<span>{{countScore3}}</span>
				</div>
			</div>
			<div class="score progress-group">
				<div class="score-icons">
					<span class="score-icon score-gray"></span>
                    <span class="score-icon score-gray"></span>
				</div>
				<div class="score-progress">
					<div class="progress">
					    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" ng-style="scoreStype2">
					      <span class="sr-only">60% Complete</span>
					    </div>
					</div>
					<span>{{countScore2}}</span>
				</div>
			</div>
			<div class="score progress-group">
				<div class="score-icons">
					<span class="score-icon score-gray"></span>
				</div>
				<div class="score-progress">
					<div class="progress">
					    <div class="progress-bar" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" ng-style="scoreStype1">
					      <span class="sr-only">60% Complete</span>
					    </div>
					</div>
					<span>{{countScore1}}</span>
				</div>
			</div>
			<div class="app-comment">
				<div class="comment-left">
					点星评论：
				</div>
				<div class="comment-right">
					<div class="score-icons comment-score">
						<span class="score-icon score-gray-null" id="score-1" ng-click="updateScore(1)"></span>
						<span class="score-icon score-gray-null" id="score-2" ng-click="updateScore(2)"></span>
						<span class="score-icon score-gray-null" id="score-3" ng-click="updateScore(3)"></span>
						<span class="score-icon score-gray-null" id="score-4" ng-click="updateScore(4)"></span>
						<span class="score-icon score-gray-null" id="score-5" ng-click="updateScore(5)"></span>
					</div>
				</div>
			</div>
			<div class="app-comment">
				<div class="comment-left">
					输入评论：
				</div>
				<div class="comment-right">
					<div class="comment-commit">
						<textarea ng-model="userComment" placeholder="至少输入5个字符">{{appInfo.userComment.comment}}</textarea>
						<button class="button-black" ng-click="updateComment()">回复</button>
					</div>
				</div>
			</div>
			<div class="app-comment">
				<div class="comment-left">
					用户评价：
				</div>
				<div class="comment-right">
					<ul class="comment-list">
						<li ng-repeat="comment in appComments.datas">
							<div class="score-icons">
								<span class="score-icon {{comment.score|scoreFilter:1}}"></span>
			                    <span class="score-icon {{comment.score|scoreFilter:2}}"></span>
			                    <span class="score-icon {{comment.score|scoreFilter:3}}"></span>
			                    <span class="score-icon {{comment.score|scoreFilter:4}}"></span>
			                    <span class="score-icon {{comment.score|scoreFilter:5}}"></span>
							</div>
							<p>{{comment.comment}}</p>
							<p class="score-user">评论人：{{comment.username}}&emsp;&emsp;&emsp;{{comment.updateDate|date:"yyyy-MM-dd HH:mm:ss"}}</p>
						</li>
					</ul>
					<pagination page="appComments.page" change="pageQuery(page,pageSize)"></pagination>
				</div>
			</div>
		</div>
	</div>
</div>
