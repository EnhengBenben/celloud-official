<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport" ng-controller="commonReport">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">{{'REPORT.MANAGEMENT' | translate}}</a></li>
      <li>HBV_SNP2{{'REPORT.REPORT' | translate}}</li>
    </ol>
    <div class="content">
        <div class="content-left col-xs-10">
	      <div class="content-header">
	        <p> {{'REPORT.PROJECT_NAME' | translate}}：
	            {{project.projectName}}
	        </p>
	        <p> {{'REPORT.APP_NAME' | translate}}：
	        	{{hbv.appName}}
	        </p>
	        <p> {{'DATA.FILE_NAME' | translate}}：
	            {{hbv.fileName}}({{hbv.dataKey}})
	        </p>
	        <div class="btn-groups">
	            <a ng-if="hbv.dataKey != null && hbv.dataKey != ''" class="btn -low" target="_blank" ng-href='report/printHBV?appId={{hbv.appId}}&dataKey={{hbv.dataKey}}&projectId={{hbv.projectId}}&flag=0'><i class="fa fa-print"></i>{{'HBV.DETAIL_REPORT' | translate}}</a>
	            <a ng-if="hbv.dataKey != null && hbv.dataKey != ''" class="btn -low" target="_blank" ng-href='report/printHBV?appId={{hbv.appId}}&dataKey={{hbv.dataKey}}&projectId={{hbv.projectId}}&flag=1'><i class="fa fa-print"></i>{{'HBV.BRIEF_REPORT' | translate}}</a>
	            <a ng-if="hbv.pdf != null && hbv.pdf != ''" class="btn -middle" ng-href="/report/down?path={{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/{{hbv.pdf}}"><i class="fa fa-file-pdf-o"></i>{{'REPORT.PDF_DOWNLOAD' | translate}}</a>
	            <a ng-if="hbv.pdf != zip && hbv.zip != ''" class="btn -high" ng-href="/report/down?path={{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/{{hbv.zip}}"><i class="fa fa-cloud-download"></i>{{'HBV.REPORT_DOWNLOAD' | translate}}</a>
	        </div>
	      </div>
	      <div>
		    <section id="cfda">
			    <div class="m-box w500">
					<h2>{{'HBV.SITE_MUTATION' | translate}}</h2>
					<h5 id="snpType" style="margin-left: 15px;">{{hbv.type.replace('Type','HBV.GENOTYPE' | translate)}}</h5>
					<div class="m-boxCon">
						<img ng-if="hbv.known['204_png'] != null" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['204_png']}}" height="476px" width="420px" style="margin-left: 25px;"/>
						<div ng-if="hbv.known != undefined && hbv.known['204_png'] == null" class="imgmiss">
							{{'HBV.SITE_TEST_FAIL' | translate: {"site":204}}}
						</div>
					</div>
					<div class="m-tips" style="height: auto;padding-right: 19px;">
						<i class="i-tips"></i>
						<span ng-if="hbv.txt204.indexOf('LAM')>-1 || hbv.txt204.indexOf('FTC')>-1 || hbv.txt204.indexOf('LDT')>-1 || hbv.txt204.indexOf('ETV')>-1">
							{{'HBV.RESISTANS_STRAINS' | translate}}
							<span ng-if="hbv.txt204.indexOf('LDT')>-1">
							{{'HBV.LDT' | translate}}
							</span>
							<span ng-if="hbv.txt204.indexOf('LAM')>-1">
							{{'HBV.LAM' | translate}}
							</span>
							<span ng-if="hbv.txt204.indexOf('FTC')>-1">
							{{'HBV.FTC' | translate}}
							</span>
							<span ng-if="hbv.txt204.indexOf('ETV')>-1">
							{{'HBV.ETV' | translate}}
							</span>
							{{'HBV.MUTATION_DETECTED' | translate}}
						</span>
						<span ng-if="hbv.txt204.indexOf('LAM')<0 && hbv.txt204.indexOf('FTC')<0 && hbv.txt204.indexOf('LDT')<0 && hbv.txt204.indexOf('ETV')<0">
							{{'HBV.NO_MUTATION' | translate}}
						</span>
					</div>
				</div>
				<div class="text-center mb20 w500">
					<a href="javascript:void(0)" ng-click="change1()" class="btn -low"><i class="i-view"></i>{{'HBV.VIEW_DETAIL' | translate}}</a>
				</div>
		     </section>
		     <section id="nomal" style="display: none;">
				<div class="m-box" ng-if="hbv.type != '' && hbv.type == 'no match&lt;br /&gt;'">
				 	<h2>{{'HBV.ABNORMAL_DATA' | translate}}</h2>
				    <div class="m-boxCon">
						{{'HBV.CONTRAST_FAILED' | translate}}
				    </div>
				</div>
				<!--位点突变-->
				<div ng-if="hbv.type != 'no match&lt;br /&gt;'">
					<div class="m-box">
					 	<h2>
					 		1.{{'HBV.TDF' | translate}}{{'HBV.MUTATION_DETECTION' | translate}}
					 		<span style="float:right;padding-right: 30px;" ng-title="{{'HBV.HELP' | translate}}" ng-click="showModal('helpModal',0,1)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
					 	</h2>
					    <div class="m-boxCon">
					    	<span ng-if="hbv.known['194_png'] != null">
						    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['194_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['194_10_png']);" >
									<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['194_png']}}" height="170px;" width="150px;">
									<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="hbv.known['194_png'] != null && lowQcStr.indexOf('194') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
			                                                   {{'HBV.LOW_QC' | translate}}
			                        </div>
								</a>
							</span>
							<div ng-if="hbv.known['194_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":194}}}
							</div>
					    </div>
					</div>
					<!--位点突变-->
					<div class="m-box">
					 	<h2>2.{{'HBV.LDT' | translate}}{{'HBV.MUTATION_DETECTION' | translate}}
					 		<span style="float:right;padding-right: 30px;" ng-title="{{'HBV.HELP' | translate}}" ng-click="showModal('helpModal',0,2)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
					 	</h2>
					    <div class="m-boxCon">
					    	<span ng-if="hbv.known['204_png'] != null">
						    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['204_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['204_10_png']);" >
									<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['204_png']}}" height="170px;" width="150px;">
									<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('204') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
			                                                    {{'HBV.LOW_QC' | translate}}
			                        </div>
								</a>
							</span>
							<div ng-if="hbv.known['204_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":204}}}
							</div>
					    </div>
					</div>
					<!--位点突变-->
					<div class="m-box">
					 	<h2>3.{{'HBV.ADV' | translate}}{{'HBV.MUTATION_DETECTION' | translate}}
					 		<span style="float:right;padding-right: 30px;" ng-title="{{'HBV.HELP' | translate}}" ng-click="showModal('helpModal',0,3)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
					 	</h2>
					    <div class="m-boxCon">
					    	<span ng-if="hbv.known['181_png'] != null">
						    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['181_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['181_10_png']);" >
									<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['181_png']}}" height="170px;" width="150px;">
									<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('181') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
			                                                    {{'HBV.LOW_QC' | translate}}
			                        </div>
								</a>
							</span>
							<div ng-if="hbv.known['181_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":181}}}
							</div>
							<span ng-if="hbv.known['236_png'] != null">
								<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['236_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['236_10_png']);" >
									<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['236_png']}}" height="170px;" width="150px;">
									<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('236') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
			                                                    {{'HBV.LOW_QC' | translate}}
			                        </div>
								</a>
							</span>
							<div ng-if="hbv.known['236_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":236}}}
							</div>
					    </div>
					</div>
					<!--位点突变-->
					<div class="m-box">
					 	<h2>4.{{'HBV.LAM' | translate}}{{'HBV.MUTATION_DETECTION' | translate}}
					 		<span style="float:right;padding-right: 30px;" ng-title="{{'HBV.HELP' | translate}}" ng-click="showModal('helpModal',0,4)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
					 	</h2>
					    <div class="m-boxCon">
						    <span ng-if="hbv.known['173_png'] != null">
								<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['173_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['173_10_png']);" >
									<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['173_png']}}" height="170px;" width="150px;">
									<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('173') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
			                                                    {{'HBV.LOW_QC' | translate}}
			                        </div>
								</a>
							</span>
							<div ng-if="hbv.known['173_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":173}}}
							</div>
							<span ng-if="hbv.known['180_png'] != null">
								<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['180_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['180_10_png']);" >
									<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['180_png']}}" height="170px;" width="150px;">
									<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('180') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
			                                                    {{'HBV.LOW_QC' | translate}}
			                        </div>
								</a>
							</span>
							<div ng-if="hbv.known['180_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":180}}}
							</div>
							<span ng-if="hbv.known['204_png'] != null">
					    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['204_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['204_10_png']);" >
								<img style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['204_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('204') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                            {{'HBV.LOW_QC' | translate}}
	                            </div>
							</a>
							</span>
							<div ng-if="hbv.known['204_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":204}}}
							</div>
					    </div>
					</div>
					<!--位点突变-->
					<div class="m-box">
					 	<h2>5.{{'HBV.FTC' | translate}}{{'HBV.MUTATION_DETECTION' | translate}}
					 		<span style="float:right;padding-right: 30px;" ng-title="{{'HBV.HELP' | translate}}" ng-click="showModal('helpModal',0,5)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
					 	</h2>
					    <div class="m-boxCon">
					    	<span ng-if="hbv.known['173_png'] != null">
							<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['173_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['173_10_png']);" >
								<img style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['173_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('173') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    {{'HBV.LOW_QC' | translate}}
		                        </div>
							</a>
							</span>
							<div ng-if="hbv.known['173_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":173}}}
							</div>
							<span ng-if="hbv.known['180_png'] != null">
							<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['180_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['180_10_png']);" >
								<img style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['180_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('180') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    {{'HBV.LOW_QC' | translate}}
		                        </div>
							</a>
							</span>
							<div ng-if="hbv.known['180_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":180}}}
							</div>
							<span ng-if="hbv.known['204_png'] != null">
					    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['204_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['204_10_png']);" >
								<img style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['204_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('204') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    {{'HBV.LOW_QC' | translate}}
		                        </div>
							</a>
							</span>
							<div ng-if="hbv.known['204_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":204}}}
							</div>
					    </div>
					</div>
					<!--位点突变-->
					<div class="m-box">
					 	<h2>6.{{'HBV.ETV' | translate}}{{'HBV.MUTATION_DETECTION' | translate}}
					 		<span style="float:right;padding-right: 30px;" ng-title="{{'HBV.HELP' | translate}}" ng-click="showModal('helpModal',0,6)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
					 	</h2>
					    <div class="m-boxCon">
					    	<span ng-if="hbv.known['169_png'] != null">
							<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['169_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['169_10_png']);" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['169_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('169') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    {{'HBV.LOW_QC' | translate}}
		                        </div>
							</a>
							</span>
							<div ng-if="hbv.known['169_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":169}}}
							</div>
							<span ng-if="hbv.known['180_png'] != null">
							<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['180_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['180_10_png']);" >
								<img class="imgtop" style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['180_png']}}" height="170px;" width="150px;">
		                        <div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('180') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    {{'HBV.LOW_QC' | translate}}
		                        </div>
							</a>
							</span>
							<div ng-if="hbv.known['180_png'] == null" class="imgmiss">
	                            {{'HBV.SITE_TEST_FAIL' | translate: {"site":180}}}
	                        </div>
	                        <span ng-if="hbv.known['184_png'] != null">
							<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['184_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['184_10_png']);" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['184_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('184') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    {{'HBV.LOW_QC' | translate}}
		                        </div>
							</a>
							</span>
							<div ng-if="hbv.known['184_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":184}}}
							</div>
							<span ng-if="hbv.known['202_png'] != null">
							<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['202_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['202_10_png']);" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['202_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('202') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    {{'HBV.LOW_QC' | translate}}
		                        </div>
							</a>
							</span>
							<div ng-if="hbv.known['202_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":202}}}
							</div>
							<span ng-if="hbv.known['204_png'] != null">
					    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['204_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['204_10_png']);" >
								<img class="imgtop" style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['204_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('204') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    {{'HBV.LOW_QC' | translate}}
		                        </div>
							</a>
							</span>
							<div ng-if="hbv.known['204_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":204}}}
							</div>
							<span ng-if="hbv.known['250_png'] != null">
					    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['250_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['250_10_png']);" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['250_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('250') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    {{'HBV.LOW_QC' | translate}}
		                        </div>
							</a>
							</span>
							<div ng-if="hbv.known['250_png'] == null" class="imgmiss">
								{{'HBV.SITE_TEST_FAIL' | translate: {"site":250}}}
							</div>
					    </div>
					</div>
					<div class="w3cbbs" style="display: none;"></div>
					<div class="container" style="display: none;"></div>
					<!--位点突变-->
					<div class="m-box">
					 	<h2>7.{{'HBV.OTHER_MUTATION' | translate}}({{'HBV.NO_LITERATURE_SUPPORT' | translate}})
					 	  	<span style="float:right;padding-right: 30px;" ng-title="{{'HBV.HELP' | translate}}" ng-click="showModal('helpModal',0,7)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
					 	</h2>
					    <div class="m-boxCon" id="otherPng">
				    		<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.imgString!=''" ng-repeat="img in hbv.imgString.split(',')" ng-click=bigFigure("{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{img.replace('png','10.png')}}")>
								<img class="imgtop" title="{{img}}" name="imgSrc" style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{img}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf(img.split('_')[0]) > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                            {{'HBV.LOW_QC' | translate}}
		                        </div>
							</a>
					    </div>
					</div>
				</div>
				<!--结论-->
				<div class="m-box">
				 	  <h2>{{'HBV.REFERENCE_CONCLUSIONS' | translate}}
				 	  <span style="float:right;padding-right: 30px;" ng-title="{{'HBV.HELP' | translate}}" ng-click="showModal('helpModal',2)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
				 	  </h2>
				    <div ng-bind-html="hbv.reporttxt.split('Other')[0]" class="m-boxCon result" id="resultDiv">
				    </div>
				</div>
				<!--检测结果-->
				<div class="m-box">
					<h2><i class="i-edit"></i>{{'HBV.ORIGINAL_SEQUENCE' | translate}}</h2>
				    <div class="m-boxCon result" id="printDiv2">
						{{hbv.seq}}
				    </div>
				</div>
			    <div ng-bind-html="hbv.clinical" id="printDiv4" style="display: none;">
			    </div>
				<!--染色体序列点图-->
				<div class="m-box" id="printDiv3">
					<h2><i class="i-dna"></i>{{'HBV.ORIGINAL_PEAK_MAP' | translate}}
						<span style="float:right;padding-right: 30px;" ng-title="{{'HBV.HELP' | translate}}" ng-click="showModal('helpModal',1)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
					</h2>
				    <div ng-if="hbv.original['1_all_png'] != null" class="m-boxCon result">
						<a ng-click=bigFigure("{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['1_all_png']}}","listAll1Img")>
							<img class="imgtop originImg" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['1_all_png']}}" id="listAll1Img">
						</a>
				    </div>
				    <div ng-if="hbv.original['2_all_png'] != null" class="m-boxCon result">
						<a ng-click=bigFigure("{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['2_all_png']}}","listAll2Img")>
							<img class="imgtop originImg" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['2_all_png']}}" id="listAll2Img">
						</a>
				    </div>
				    <div ng-if="hbv.original['3_all_png'] != null" class="m-boxCon result">
						<a ng-click=bigFigure("{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['3_all_png']}}","listAll3Img")>
							<img class="imgtop originImg" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['3_all_png']}}" id="listAll3Img">
						</a>
				    </div>
				     <div ng-if="hbv.original['4_all_png'] != null" class="m-boxCon result">
						<a ng-click=bigFigure("{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['4_all_png']}}","listAll4Img")>
							<img class="imgtop originImg" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['4_all_png']}}" id="listAll4Img">
						</a>
				    </div>
				    <div ng-if="hbv.original['5_all_png'] != null" class="m-boxCon result">
	<!-- 					<a ng-click="bigFigure({{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['5_all_png']}},'listAll5Img');" > -->
						<a ng-click=bigFigure("{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['5_all_png']}}","listAll5Img")>
							<img class="imgtop originImg" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.original['5_all_png']}}" id="listAll5Img">
						</a>
				    </div>
				</div>
				<!--Celloud数据参数同比分析-->
			    <div class="m-box">
			        <h2><i class="i-celloud"></i>{{'HBV.ANALYSIS_CELLOUD_PARAMETERS' | translate}}</h2>
			        <div class="m-boxCon">
			          <div class="alert alert-success" style="width:80%;margin: 0 auto;margin-bottom: 20px;">{{'HBV.WITHOUT_LITERATURE_SUPPORT' | translate}}<br/>{{'HBV.PLEASE_CLICK_SHOW_ALL' | translate }}</div>
			          <div id="tableDiv">
			            <table class="table table-main" ng-show="more" id="site_table_less" style="width: 80%;margin: 0 auto;">
					        <thead>
					          <tr>
					            <th>{{'HBV.SITE' | translate}}</th>
					            <th>{{'HBV.NUMBER_OCCURRENCES' | translate}}</th>
					            <th>{{'HBV.PROPORTION' | translate}}</th>
					          </tr>
					        </thead>
					        <tbody>
					          <tr ng-repeat="hbvOtherSite in hbvOtherSiteList" ng-if="$index < 20">
					            <td>{{siteKeys[$index]}}</td>
					            <td>{{hbvOtherSite[siteKeys[$index]]['count']}}</td>
					            <td>{{hbvOtherSite[siteKeys[$index]]['percent']}}</td>
					          </tr>
					        </tbody>
				        </table>
				        <table class="table table-main" ng-show="less" id="site_table_more" style="width: 80%;margin: 0 auto;">
	                        <thead>
	                          <tr>
	                            <th>{{'HBV.SITE' | translate}}</th>
                                <th>{{'HBV.NUMBER_OCCURRENCES' | translate}}</th>
                                <th>{{'HBV.PROPORTION' | translate}}</th>
	                          </tr>
	                        </thead>
	                        <tbody>
	                          <tr ng-repeat="hbvOtherSite in hbvOtherSiteList">
	                            <td>{{siteKeys[$index]}}</td>
	                            <td>{{hbvOtherSite[siteKeys[$index]]['count']}}</td>
	                            <td>{{hbvOtherSite[siteKeys[$index]]['percent']}}</td>
	                          </tr>
	                        </tbody>
	                     </table>
	                    </div>
				        <div class="text-center mb20 mt20">
	                        <a href="javascript:void(0)" ng-show="more" ng-click="showMore('table');" class="btn -low"><i class="i-view"></i>{{'HBV.SHOW_ALL' | translate}}</a>
	                        <a href="javascript:void(0)" ng-show="less" ng-click="showLess('table');" class="btn -low"><i class="i-view"></i>{{'REPORT.PACK_UP' | translate}}</a>
	                    </div>
				        <br/>
				        <div class="row" id="charDiv0">
	                    </div>
	                    <div class="text-center mb20">
			                <a href="javascript:void(0)" ng-show="more" ng-click="showMore('charts');" class="btn -low"><i class="i-view"></i>{{'HBV.SHOW_ALL' | translate}}</a>
			                <a href="javascript:void(0)" ng-show="less" ng-click="showLess('charts');" class="btn -low"><i class="i-view"></i>{{'HBV.PACK_UP' | translate}}</a>
			            </div>
			        	<div class="row" id="charDiv">
				        </div>
			        </div>
				</div>
				<div class="text-center mt10 mb20">
					<a href="javascript:void(0)" ng-click="change2()" class="btn -low pull-center"><i class="i-view"></i>{{'HBV.RETURN' | translate}}</a>
				</div>
		     </section>
		   </div>
		 </div>
		 <div class="content-right col-xs-2">
	        <ng-include src="'pages/partial/_partial_datareportoperate.jsp'"></ng-include>
	      </div>
     </div>
 </div>
 
<div id="helpModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
        <h4 class="modal-title">{{'HBV.HELP' | translate}}</h4>
	</div>
	<div class="modal-body form-modal">
		<div id="_showMore">
			<div class="lineheight y y1">	1. {{'HBV.RESISTANCE_ASSOCIATED' | translate}}({{'HBV.TDF_DETECTION_RULE' | translate}})：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">A194T</span>
				</div>
			</div>
			<div class="lineheight y y2">	1. {{'HBV.RESISTANCE_ASSOCIATED' | translate}}({{'HBV.LDT_DETECTION_RULE' | translate}})：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">M204I</span>
				</div>
			</div>
			<div class="lineheight y y3">	1. {{'HBV.RESISTANCE_ASSOCIATED' | translate}}({{'HBV.ADV_DETECTION_RULE' | translate}})：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">A181V/T、N236T</span>
				</div>
			</div>
			<div class="lineheight y y4">	1. {{'HBV.RESISTANCE_ASSOCIATED' | translate}}({{'HBV.LAM_DETECTION_RULE' | translate}})：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">V173L、L180M、M204I/V</span>
				</div>
			</div>
			<div class="lineheight y y5">	1. {{'HBV.RESISTANCE_ASSOCIATED' | translate}}({{'HBV.FTC_DETECTION_RULE' | translate}})：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">V173L、L180M、M204I/V</span>
				</div>
			</div>
			<div class="lineheight y y6">	1. {{'HBV.RESISTANCE_ASSOCIATED' | translate}}({{'HBV.ETV_DETECTION_RULE' | translate}})：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">I169T、L180M、T184A/G/S/I/L/F、S202G/I、M204V、M250V/L/I</span>
				</div>
			</div>
			<div class="lineheight y y7">	1. {{'HBV.RESISTANCE_ASSOCIATED' | translate}}：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">{{'NO_LITERATURE_SUPPORT' | translate}}</span>
				</div>
			</div>
			<div class="lineheight">
				2. {{'HBV.EXAMPLES_MUTATION' | translate}}：“M204M|V {A-A|G(67|33,2.1);G-G|T(72|28,2.5)}” ：
				<div class="_leftShort" style="line-height:25px;">（1）{{'HBV.M_EXPLANATION' | translate}}</div>
				<div class="_leftShort" style="line-height:25px;">（2）{{'HBV.BASE_CHANGES' | translate}}</div>
	    		<div class="_leftShort" style="line-height:25px;">（3）{{'HBV.DESCRIPTION_OF_SCAL_VALUE' | translate}}</div>
	        </div>
			<div class="lineheight">3. {{'HBV.WILD_TYPE_GCT' | translate}}</div>
			<div class="lineheight">4. {{'HBV.STAR_IN_PEAK' | translate}}</div>
			<div class="lineheight">5. {{'HBV.LOW_QUALITY' | translate}}</div>
		</div>
		<div id="_showOne">
			<div class="lineheight">{{'HBV.STAR_IN_PEAK' | translate}}</div>
		</div>
		<div id="_showImg">
			<img class="imgtop" src="${pageContext.request.contextPath }/app/image?file=HBV_explain.png" width="100%">
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn -low pull-right" data-dismiss="modal">{{'BUTTON.CLOSE' | translate}}</button>
	</div>
  </div>
 </div>
</div>