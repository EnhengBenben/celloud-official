<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>oncogene报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 文件名称：
            {{oncogene.fileName}}({{oncogene.dataKey}})
        </p>
      </div>
      <div>
        <section class="m-box">
	        <h2>
				<i class="i-edit"></i>一、突变点位
			</h2>
		    <div class="m-boxCon result">
		    	<div id="knowResult">
			    	<span ng-if="oncogene.wz1 != null && oncogene.wz1 != ''" ng-bind-html="oncogene.wz1 | trustHtml">
			    	</span>
			    	<span ng-if="oncogene.wz1 == null && oncogene.wz1 == ''">
		    			未检测到突变
			    	</span>
		    	</div>
		    	<br/>
	    		<div ng-if="oncogene.knowMutation!=null" ng-repeat="img in oncogene.knowMutation">
			    	<img name="know" ng-src="{{uploadPath}}/{{oncogene.userId}}/{{oncogene.appId}}/{{oncogene.dataKey}}/SVG/{{img}}" style="width: 100%;">
	    		</div>
		    </div>
	    </section>
	    <section class="m-box">
	        <h2>
				<i class="i-edit"></i>二、未突变点位
			</h2>
		    <div class="m-boxCon result">
				<img ng-if="oncogene.out != null" ng-repeat="img in oncogene.out" class="imgtop" title="{{img}}" name="imgSrc" ng-src="{{uploadPath}}/{{oncogene.userId}}/{{oncogene.appId}}/{{oncogene.dataKey}}/SVG/{{img}}" style="width: 100%;">
		    </div>
	     </section>
         <section class="m-box">
	         <h2>
				<i class="i-edit"></i>三、 参考结论
			 </h2>
		     <div class="m-boxCon result" id="_result">
		    	{{oncogene.conclusion}}
		     </div>
         </section>
         <section class="m-box">
	         <h2><i class="i-edit"></i>四、 测序序列结果</h2>
		     <div class="m-boxCon result" id="_seq">
				{{oncogene.seq}}
		     </div>
	     </section>
         <section class="m-box">
	         <h2><i class="i-dna"></i>五、 测序峰图结果</h2>
		     <div ng-if="oncogene.original['1_all_png'] != null" class="m-boxCon result">
			 	<a ng-click="bigOrigin(uploadPath + '/' + oncogene.userId + '/' + oncogene.appId + '/' + oncogene.dataKey + '/SVG/' + oncogene.original['1_all_png'],'listAll1Img');" >
			 		<img name="imgSrc" ng-src="{{uploadPath}}/{{oncogene.userId}}/{{oncogene.appId}}/{{oncogene.dataKey}}/SVG/{{oncogene.original['1_all_png']}}" class="originImg" id="listAll1Img">
			 	</a>
		     </div>
		     <div ng-if="oncogene.original['2_all_png'] != null" class="m-boxCon result">
		 		<a ng-click="bigOrigin(uploadPath + '/' + oncogene.userId + '/' + oncogene.appId + '/' + oncogene.dataKey + '/SVG/' + oncogene.original['2_all_png'],'listAll2Img');" >
		 			<img name="imgSrc" ng-src="{{uploadPath}}/{{oncogene.userId}}/{{oncogene.appId}}/{{oncogene.dataKey}}/SVG/{{oncogene.original['2_all_png']}}" class="originImg" id="listAll2Img">
		 		</a>
		     </div>
			 <div ng-if="oncogene.original['3_all_png'] != null" class="m-boxCon result">
			 	<a ng-click="bigOrigin(uploadPath + '/' + oncogene.userId + '/' + oncogene.appId + '/' + oncogene.dataKey + '/SVG/' + oncogene.original['3_all_png'],'listAll3Img');" >
			 		<img name="imgSrc" ng-src="{{uploadPath}}/{{oncogene.userId}}/{{oncogene.appId}}/{{oncogene.dataKey}}/SVG/{{oncogene.original['3_all_png']}}" class="originImg" id="listAll3Img">
			 	</a>
			 </div>
		     <div ng-if="oncogene.original['4_all_png'] != null" class="m-boxCon result">
		 		<a ng-click="bigOrigin(uploadPath + '/' + oncogene.userId + '/' + oncogene.appId + '/' + oncogene.dataKey + '/SVG/' + oncogene.original['4_all_png'],'listAll4Img');" >
		 			<img name="imgSrc" ng-src="{{uploadPath}}/{{oncogene.userId}}/{{oncogene.appId}}/{{oncogene.dataKey}}/SVG/{{oncogene.original['4_all_png']}}" class="originImg" id="listAll4Img">
		 		</a>
		     </div>
		     <div ng-if="oncogene.original['5_all_png'] != null" class="m-boxCon result">
		 		<a ng-click="bigOrigin(uploadPath + '/' + oncogene.userId + '/' + oncogene.appId + '/' + oncogene.dataKey + '/SVG/' + oncogene.original['5_all_png'],'listAll5Img');" >
		 			<img name="imgSrc" ng-src="{{uploadPath}}/{{oncogene.userId}}/{{oncogene.appId}}/{{oncogene.dataKey}}/SVG/{{oncogene.original['5_all_png']}}" class="originImg" id="listAll5Img">
		 		</a>
		     </div>
	     </section>
	   </div>
     </div>
 </div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>