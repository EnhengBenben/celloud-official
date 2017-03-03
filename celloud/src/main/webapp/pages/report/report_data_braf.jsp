<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>BRAF报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{braf.appName}}
        </p>
        <p> 文件名称：
            {{braf.fileName}}({{braf.dataKey}})
        </p>
      </div>
      <div>
        <section class="m-box">
	        <h2>
				<i class="i-edit"></i>突变类型
			</h2>
		    <div class="m-boxCon result">
		    	<div id="knowResult">
		    		<span ng-if="braf.position != null && braf.position != ''" ng-bind-html="braf.position"></span>
		    		<span ng-if="braf.position == null || braf.position == ''">未检测到突变</span>
		    	</div>
	    		<br/>
		    </div>
	    </section>
	    <section class="m-box">
		    <h2>
				<i class="i-edit"></i>SNP 
				<span class="filter">
					<input type="text" value="5" id="_snum">
					<a href="javascript:void(0)" class="btn -low" ng-click="searchTable()"><i class="i-filter"></i>筛选</a>
				</span>
			</h2>
			<div class="m-boxCon result">
				<table>
					<tbody>
						<tr>
							<td ng-bind-html="braf.mutationPosition" ng-if="braf.mutationPosition!=null" style="width: 30%; min-width: 265px" id="report_tb">
					    	</td>
					    	<td ng-if="braf.mutationPosition==null" style="width: 30%; min-width: 265px" id="report_tb">
					    		数据正常，未找到其他突变。
					    	</td>
							<td style="width: 40%; text-align: center;">
								<img src="${pageContext.request.contextPath }/resources/img/report/arrow1.png">
							</td>
							<td style="width: 30%; padding-right: 50px">
								<div style="width: 58px; min-width: 58px; padding: 20px 20px; border: 4px solid #B5D329; border-radius: 4px; margin-left: 30%; text-align: center" id="searchResult"></div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
	     </section>
         <section class="m-box">
	         <h2>
				<i class="i-edit"></i>原始序列
			</h2>
		    <div class="m-boxCon result" id="_result">
		    	{{braf.seq}}
		    </div>
         </section>
         <section class="m-box">
	         <h2>
	         	<i class="i-edit"></i>原始峰图
	         </h2>
			 <div class="m-boxCon result" ng-if="braf.original != null">
				<a ng-repeat="original in braf.original" ng-click="bigOrigin(uploadPath + braf.userId + '/' + braf.appId + '/' + braf.dataKey + '/SVG/' + original,'original' + ($index+1));" >
					<br/>
					<img name="imgSrc" class="originImg" ng-src="{{uploadPath}}{{braf.userId}}/{{braf.appId}}/{{braf.dataKey}}/SVG/{{original}}" id="original{{$index+1}}"><br/>
				</a>
		     </div>
		     <div class="m-boxCon result" ng-if="braf.original == null">
		    样本异常，无法检测
		     </div>
	     </section>
	   </div>
     </div>
 </div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>