<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>HBV_SNP2报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{hbv.appName}}
        </p>
        <p> 文件名称：
            {{hbv.fileName}}({{hbv.dataKey}})
        </p>
        <div class="btn-groups">
            <a ng-if="hbv.dataKey != null && hbv.dataKey != ''" class="btn -low" target="_blank" ng-href='report/printHBV?appId={{hbv.appId}}&dataKey={{hbv.dataKey}}&projectId={{hbv.projectId}}&flag=0'><i class="fa fa-print"></i>详细报告</a>
            <a ng-if="hbv.dataKey != null && hbv.dataKey != ''" class="btn -low" target="_blank" ng-href='report/printHBV?appId={{hbv.appId}}&dataKey={{hbv.dataKey}}&projectId={{hbv.projectId}}&flag=1'><i class="fa fa-print"></i>简要报告</a>
            <a ng-if="hbv.pdf != null && hbv.pdf != ''" class="btn -middle" ng-href="/report/down?path={{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/{{hbv.pdf}}"><i class="fa fa-file-pdf-o"></i>PDF下载</a>
            <a ng-if="hbv.pdf != zip && hbv.zip != ''" class="btn -high" ng-href="/report/down?path={{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/{{hbv.zip}}"><i class="fa fa-cloud-download"></i>报告下载</a>
        </div>
      </div>
      <div>
	    <section id="cfda">
		    <div class="m-box w500">
				<h2>位点突变</h2>
				<h5 id="snpType" style="margin-left: 15px;">{{hbv.type.replace('Type','基因型')}}</h5>
				<div class="m-boxCon">
					<img ng-if="hbv.known['204_png'] != null" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['204_png']}}" height="476px" width="420px" style="margin-left: 25px;"/>
					<div ng-if="hbv.known != undefined && hbv.known['204_png'] == null" class="imgmiss">
						204位点测序失败，建议重测。
					</div>
				</div>
				<div class="m-tips">
					<i class="i-tips"></i>
					<span ng-if="hbv.txt204.indexOf('LAM')>-1 || hbv.txt204.indexOf('FTC')>-1 || hbv.txt204.indexOf('LDT')>-1 || hbv.txt204.indexOf('ETV')>-1">
						检测到
						<span ng-if="hbv.txt204.indexOf('LDT')>-1">
						替比夫定 LDT
						</span>
						<span ng-if="hbv.txt204.indexOf('LAM')>-1">
						拉米夫定 LAM
						</span>
						<span ng-if="hbv.txt204.indexOf('FTC')>-1">
						恩曲他滨 FTC
						</span>
						<span ng-if="hbv.txt204.indexOf('ETV')>-1">
						恩替卡韦 ETV
						</span>
						突变
					</span>
					<span ng-if="hbv.txt204.indexOf('LAM')<0 && hbv.txt204.indexOf('FTC')<0 && hbv.txt204.indexOf('LDT')<0 && hbv.txt204.indexOf('ETV')<0">
						未检测到突变
					</span>
				</div>
			</div>
			<div class="text-center mb20 w500">
				<a href="javascript:void(0)" ng-click="change1()" class="btn -low"><i class="i-view"></i>查看详情</a>
			</div>
	     </section>
	     <section id="nomal" style="display: none;">
			<div class="m-box" ng-if="hbv.type != '' && hbv.type == 'no match&lt;br /&gt;'">
			 	<h2>数据异常</h2>
			    <div class="m-boxCon">
					没有比对结果
			    </div>
			</div>
			<!--位点突变-->
			<div ng-if="hbv.type != 'no match&lt;br /&gt;'">
				<div class="m-box">
				 	<h2>
				 		1.替诺福韦酯TDF突变检测
				 		<span style="float:right;padding-right: 30px;" title="帮助" ng-click="showModal('helpModal',0,1)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
				 	</h2>
				    <div class="m-boxCon">
				    	<span ng-if="hbv.known['194_png'] != null">
					    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['194_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['194_10_png']);" >
								<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['194_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="hbv.known['194_png'] != null && lowQcStr.indexOf('194') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                   该位点测序质量低，结果仅供参考
		                        </div>
							</a>
						</span>
						<div ng-if="hbv.known['194_png'] == null" class="imgmiss">
							194位点测序失败，建议重测。
						</div>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>2.替比夫定 LDT 突变检测
				 		<span style="float:right;padding-right: 30px;" title="帮助" ng-click="showModal('helpModal',0,2)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
				 	</h2>
				    <div class="m-boxCon">
				    	<span ng-if="hbv.known['204_png'] != null">
					    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['204_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['204_10_png']);" >
								<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['204_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('204') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    该位点测序质量低，结果仅供参考
		                        </div>
							</a>
						</span>
						<div ng-if="hbv.known['204_png'] == null" class="imgmiss">
							204位点测序失败，建议重测。
						</div>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>3.阿德福韦 ADV 突变检测
				 		<span style="float:right;padding-right: 30px;" title="帮助" ng-click="showModal('helpModal',0,3)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
				 	</h2>
				    <div class="m-boxCon">
				    	<span ng-if="hbv.known['181_png'] != null">
					    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['181_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['181_10_png']);" >
								<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['181_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('181') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    该位点测序质量低，结果仅供参考
		                        </div>
							</a>
						</span>
						<div ng-if="hbv.known['181_png'] == null" class="imgmiss">
							181位点测序失败，建议重测。
						</div>
						<span ng-if="hbv.known['236_png'] != null">
							<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['236_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['236_10_png']);" >
								<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['236_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('236') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    该位点测序质量低，结果仅供参考
		                        </div>
							</a>
						</span>
						<div ng-if="hbv.known['236_png'] == null" class="imgmiss">
							236位点测序失败，建议重测。
						</div>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>4.拉米夫定 LAM 突变检测
				 		<span style="float:right;padding-right: 30px;" title="帮助" ng-click="showModal('helpModal',0,4)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
				 	</h2>
				    <div class="m-boxCon">
					    <span ng-if="hbv.known['173_png'] != null">
							<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['173_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['173_10_png']);" >
								<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['173_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('173') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    该位点测序质量低，结果仅供参考
		                        </div>
							</a>
						</span>
						<div ng-if="hbv.known['173_png'] == null" class="imgmiss">
							173位点测序失败，建议重测。
						</div>
						<span ng-if="hbv.known['180_png'] != null">
							<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['180_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['180_10_png']);" >
								<img style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['180_png']}}" height="170px;" width="150px;">
								<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('180') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
		                                                    该位点测序质量低，结果仅供参考
		                        </div>
							</a>
						</span>
						<div ng-if="hbv.known['180_png'] == null" class="imgmiss">
							180位点测序失败，建议重测。
						</div>
						<span ng-if="hbv.known['204_png'] != null">
				    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['204_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['204_10_png']);" >
							<img style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['204_png']}}" height="170px;" width="150px;">
							<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('204') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
                                                            该位点测序质量低，结果仅供参考
                            </div>
						</a>
						</span>
						<div ng-if="hbv.known['204_png'] == null" class="imgmiss">
							204位点测序失败，建议重测。
						</div>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>5.恩曲他滨 FTC 突变检测
				 		<span style="float:right;padding-right: 30px;" title="帮助" ng-click="showModal('helpModal',0,5)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
				 	</h2>
				    <div class="m-boxCon">
				    	<span ng-if="hbv.known['173_png'] != null">
						<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['173_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['173_10_png']);" >
							<img style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['173_png']}}" height="170px;" width="150px;">
							<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('173') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                    该位点测序质量低，结果仅供参考
	                        </div>
						</a>
						</span>
						<div ng-if="hbv.known['173_png'] == null" class="imgmiss">
							173位点测序失败，建议重测。
						</div>
						<span ng-if="hbv.known['180_png'] != null">
						<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['180_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['180_10_png']);" >
							<img style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['180_png']}}" height="170px;" width="150px;">
							<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('180') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                    该位点测序质量低，结果仅供参考
	                        </div>
						</a>
						</span>
						<div ng-if="hbv.known['180_png'] == null" class="imgmiss">
							180位点测序失败，建议重测。
						</div>
						<span ng-if="hbv.known['204_png'] != null">
				    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['204_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['204_10_png']);" >
							<img style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['204_png']}}" height="170px;" width="150px;">
							<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('204') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                    该位点测序质量低，结果仅供参考
	                        </div>
						</a>
						</span>
						<div ng-if="hbv.known['204_png'] == null" class="imgmiss">
							204位点测序失败，建议重测。
						</div>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>6.恩替卡韦 ETV 突变检测
				 		<span style="float:right;padding-right: 30px;" title="帮助" ng-click="showModal('helpModal',0,6)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
				 	</h2>
				    <div class="m-boxCon">
				    	<span ng-if="hbv.known['169_png'] != null">
						<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['169_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['169_10_png']);" >
							<img class="imgtop" style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['169_png']}}" height="170px;" width="150px;">
							<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('169') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                    该位点测序质量低，结果仅供参考
	                        </div>
						</a>
						</span>
						<div ng-if="hbv.known['169_png'] == null" class="imgmiss">
							169位点测序失败，建议重测。
						</div>
						<span ng-if="hbv.known['180_png'] != null">
						<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['180_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['180_10_png']);" >
							<img class="imgtop" style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['180_png']}}" height="170px;" width="150px;">
	                        <div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('180') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                    该位点测序质量低，结果仅供参考
	                        </div>
						</a>
						</span>
						<div ng-if="hbv.known['180_png'] == null" class="imgmiss">
                            180位点测序失败，建议重测。
                        </div>
                        <span ng-if="hbv.known['184_png'] != null">
						<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['184_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['184_10_png']);" >
							<img class="imgtop" style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['184_png']}}" height="170px;" width="150px;">
							<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('184') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                    该位点测序质量低，结果仅供参考
	                        </div>
						</a>
						</span>
						<div ng-if="hbv.known['184_png'] == null" class="imgmiss">
							184位点测序失败，建议重测。
						</div>
						<span ng-if="hbv.known['202_png'] != null">
						<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['202_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['202_10_png']);" >
							<img class="imgtop" style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['202_png']}}" height="170px;" width="150px;">
							<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('202') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                    该位点测序质量低，结果仅供参考
	                        </div>
						</a>
						</span>
						<div ng-if="hbv.known['202_png'] == null" class="imgmiss">
							202位点测序失败，建议重测。
						</div>
						<span ng-if="hbv.known['204_png'] != null">
				    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['204_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['204_10_png']);" >
							<img class="imgtop" style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['204_png']}}" height="170px;" width="150px;">
							<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('204') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                    该位点测序质量低，结果仅供参考
	                        </div>
						</a>
						</span>
						<div ng-if="hbv.known['204_png'] == null" class="imgmiss">
							204位点测序失败，建议重测。
						</div>
						<span ng-if="hbv.known['250_png'] != null">
				    	<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.known['250_png'] != null" ng-click="bigFigure(uploadPath + hbv.userId + '/' + hbv.appId + '/' + hbv.dataKey + '/SVG/' + hbv.known['250_10_png']);" >
							<img class="imgtop" style="padding-left: 30px;" name="imgSrc" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{hbv.known['250_png']}}" height="170px;" width="150px;">
							<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf('250') > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                    该位点测序质量低，结果仅供参考
	                        </div>
						</a>
						</span>
						<div ng-if="hbv.known['250_png'] == null" class="imgmiss">
							250位点测序失败，建议重测。
						</div>
				    </div>
				</div>
				<div class="w3cbbs" style="display: none;"></div>
				<div class="container" style="display: none;"></div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>7.其他突变位点（该位点目前没有已发表文献支持，无明确临床意义）
				 	  	<span style="float:right;padding-right: 30px;" title="帮助" ng-click="showModal('helpModal',0,7)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
				 	</h2>
				    <div class="m-boxCon" id="otherPng">
			    		<a style="display: inline-block;vertical-align: top;color: #333333;" ng-if="hbv.imgString!=''" ng-repeat="img in hbv.imgString.split(',')" ng-click=bigFigure("{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{img.replace('png','10.png')}}")>
							<img class="imgtop" title="{{img}}" name="imgSrc" style="padding-left: 30px;" ng-src="{{uploadPath}}{{hbv.userId}}/{{hbv.appId}}/{{hbv.dataKey}}/SVG/{{img}}" height="170px;" width="150px;">
							<div style="width:120px;margin-left:30px;margin-top: 10px;" ng-if="lowQcStr.indexOf(img.split('_')[0]) > -1" data-step="2" data-position="bottom" data-intro="" data-img="hbv_tips.png">
	                                                    该位点测序质量低，结果仅供参考
	                        </div>
						</a>
				    </div>
				</div>
			</div>
			<!--结论-->
			<div class="m-box">
			 	  <h2>参考结论（根据已发表文献得出以下参考结论）
			 	  <span style="float:right;padding-right: 30px;" title="帮助" ng-click="showModal('helpModal',2)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
			 	  </h2>
			    <div ng-bind-html="hbv.reporttxt.split('Other')[0]" class="m-boxCon result" id="resultDiv">
			    </div>
			</div>
			<!--检测结果-->
			<div class="m-box">
				<h2><i class="i-edit"></i>原始序列</h2>
			    <div class="m-boxCon result" id="printDiv2">
					{{hbv.seq}}
			    </div>
			</div>
		    <div ng-bind-html="hbv.clinical" id="printDiv4" style="display: none;">
		    </div>
			<!--染色体序列点图-->
			<div class="m-box" id="printDiv3">
				<h2><i class="i-dna"></i>原始峰图
					<span style="float:right;padding-right: 30px;" title="帮助" ng-click="showModal('helpModal',1)"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><a style="line-height:38px;width: 24px;height: 24px;margin-top: 7px;display: block;" class="hbv-tips"></a></div></span>
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
		        <h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
		        <div class="m-boxCon">
		          <div class="alert alert-success" style="width:80%;margin: 0 auto;margin-bottom: 20px;">该统计结果，针对无文献支持的“其他突变位点”。<br/>统计结果根据突变次数及所占比例由大到小排序，页面展示前20个突变位点，查看其他位点请点击“显示全部”。</div>
		          <div id="tableDiv">
		            <table class="table table-main" ng-show="more" id="site_table_less" style="width: 80%;margin: 0 auto;">
				        <thead>
				          <tr>
				            <th>位点</th>
				            <th>出现次数</th>
				            <th>比例</th>
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
                            <th>位点</th>
                            <th>出现次数</th>
                            <th>比例</th>
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
                        <a href="javascript:void(0)" ng-show="more" ng-click="showMore('table');" class="btn -low"><i class="i-view"></i>显示全部</a>
                        <a href="javascript:void(0)" ng-show="less" ng-click="showLess('table');" class="btn -low"><i class="i-view"></i>收起</a>
                    </div>
			        <br/>
			        <div class="row" id="charDiv0">
                    </div>
                    <div class="text-center mb20">
		                <a href="javascript:void(0)" ng-show="more" ng-click="showMore('charts');" class="btn -low"><i class="i-view"></i>显示全部</a>
		                <a href="javascript:void(0)" ng-show="less" ng-click="showLess('charts');" class="btn -low"><i class="i-view"></i>收起</a>
		            </div>
		        	<div class="row" id="charDiv">
			        </div>
		        </div>
			</div>
			<div class="text-center mt10 mb20">
				<a href="javascript:void(0)" ng-click="change2()" class="btn -low pull-center"><i class="i-view"></i>返回</a>
			</div>
	     </section>
	   </div>
     </div>
 </div>
 
<div id="helpModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
        <h4 class="modal-title">帮助</h4>
	</div>
	<div class="modal-body form-modal">
		<div id="_showMore">
			<div class="lineheight y y1">	1. 耐药相关的位点突变(替诺福韦酯TDF突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">A194T</span>
				</div>
			</div>
			<div class="lineheight y y2">	1. 耐药相关的位点突变(替比夫定LDT突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">M204I</span>
				</div>
			</div>
			<div class="lineheight y y3">	1. 耐药相关的位点突变(阿德福韦ADV突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">A181V/T、N236T</span>
				</div>
			</div>
			<div class="lineheight y y4">	1. 耐药相关的位点突变(拉米夫定LAM突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">V173L、L180M、M204I/V</span>
				</div>
			</div>
			<div class="lineheight y y5">	1. 耐药相关的位点突变(恩曲他滨FTC突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">V173L、L180M、M204I/V</span>
				</div>
			</div>
			<div class="lineheight y y6">	1. 耐药相关的位点突变(恩替卡韦ETV突变检测规则)：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">I169T、L180M、T184A/G/S/I/L/F、S202G/I、M204V、M250V/L/I</span>
				</div>
			</div>
			<div class="lineheight y y7">	1. 耐药相关的位点突变：
				<div style="line-height:1em;padding-top:10px" class="_leftShort">
					<span style="color:#9966FF">该位点目前没有已发表文献支持，无明确临床意义</span>
				</div>
			</div>
			<div class="lineheight">
				2. 突变结果举例解释：“M204M|V {A-A|G(67|33,2.1);G-G|T(72|28,2.5)}” ：
				<div class="_leftShort" style="line-height:25px;">（1）“M”表示野生型编码氨基酸为M；“204”表示氨基酸位点为204；“M|V”表示由原来的野生型M变为V；</div>
				<div class="_leftShort" style="line-height:25px;">（2）“{A-A|G(67|33,2.1);G-G|T(72|28,2.5)} ” 表示碱基的变化，其中一个位点由原来的A变为A|G杂合，比例为67比33；另一个碱基由原来的G变为G|T杂合，比例为72比28。</div>
	    		<div class="_leftShort" style="line-height:25px;">（3）比例值说明：A-A|G(67|33,2.1)，2.1为67和33的比值，该比例并不代表样本中该位点A和G的真实数量比例，只是代表该位点是A的可能性为67%，是G的可能性为33%。如果没有“(67|33,2.1）”出现，认为该位点100%发生突变。如果是A-A|G，说明该位点为A的可能性大；如果为A-G|A，说明该位点为G的 可能性大。当野生型的碱基（即A）的可能性大于突变碱基（即G）的可能性时，如果比值小于5我们认为该位点是突变；如果突变碱基（即G）的可能性大于野生型的碱基（即A）的可能性时，不论比值多少都认为发生了突变。</div>
	        </div>
			<div class="lineheight">3. “ *Wild Type: GCT ” 表示该位点的野生型为GCT</div>
			<div class="lineheight">4. 峰图中的 “ * ” 表示该位置的碱基发生了突变</div>
			<div class="lineheight">5. “ *Low quality ” 表示该区域的碱基测序质量值太低</div>
		</div>
		<div id="_showOne">
			<div class="lineheight">峰图中的 “ * ” 表示该位置的碱基发生了突变</div>
		</div>
		<div id="_showImg">
			<img class="imgtop" src="${pageContext.request.contextPath }/app/image?file=HBV_explain.png" width="100%">
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" class="btn -low pull-right" data-dismiss="modal">关闭</button>
	</div>
  </div>
 </div>
</div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>