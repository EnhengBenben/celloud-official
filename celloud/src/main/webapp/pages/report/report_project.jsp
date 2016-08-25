<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>报告管理</li>
    </ol>
    <div class="content">
      <form class="search-box-form">
      <div class="search-box">
        <ul class="search-type-list">
          <li class="search-type clearfix">
            <label>所&emsp;&emsp;属：</label>
            <div class="search-type-detail times">
              <ul class="search-info">
	            <li><a class="active" href="javascript:void(0)">全部</a></li>
	            <li><a href="javascript:void(0)">我的</a></li>
	            <li><a href="javascript:void(0)">共享来的</a></li>
              </ul>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>时&emsp;&emsp;间：</label>
            <div class="search-type-detail times">
              <ul class="search-info">
	            <li><a class="active" href="javascript:void(0)">全部</a></li>
	            <li><a href="javascript:void(0)">24h</a></li>
	            <li><a href="javascript:void(0)">3d</a></li>
	            <li><a href="javascript:void(0)">7d</a></li>
	            <li><a href="javascript:void(0)">15d</a></li>
	            <li><a href="javascript:void(0)">30d</a></li>
              </ul>
              <div class="search-btns">
                <input type="text">
                <span>-</span>
                <input type="text">
                <button class="btn btn-cancel">确定</button>
              </div>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>产品标签：</label>
            <div class="search-type-detail inline-detail {{reportMoreAppTag|chevronTypeDivFilter}}" ng-init="reportMoreAppTag=true">
              <ul class="search-info">
                <li><a class="active" href="javascript:void(0)">全部</a></li>
                <li ng-repeat="app in ranAppList.datas"><a href="javascript:void(0)">{{app.app_name}}</a></li>
              </ul>
              <div class="search-btns">
                <button class="btn chevron-btn" ng-click="reportMoreAppTag=changeChevronType(reportMoreAppTag)">{{reportMoreAppTag|chevronTypeTextFilter}}<i ng-class="reportMoreAppTag|chevronTypeFaFilter" aria-hidden="true"></i></button>
              </div>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>样本编码：</label>
            <div class="search-type-detail">
              <input type="text" placeholder="扫码或输入编号">
            </div>
          </li>
        </ul>
        </form>
      </div>
      <table class="table table-main" ng-init="pageType='reportpro'">
        <tbody id="_show">
          <tr ng-repeat="report in dataList.datas" load-over>
            <td>
            	<div>
               		项目名称：
               		<span id="showPname{{report.project_id }}">
	                    <span id="pnameSpan{{report.project_id }}">
	                    	{{report.project_name | contextLengthFilter:'13'}}
	                    </span>
                    	<a ng-if="report.userName=='no_one'" ng-click="toChangePname(report.project_id)" href="javascript:void()">Edit</a>
                   	</span>
                   	<span ng-if="report.userName=='no_one'" id="changePname{{report.project_id}}" class="hide">
	                    <input type="text" value="{{report.project_name}}" ng-blur="changePname(report.project_id)" id="updatePname{{report.project_id }}" class="changeInput"/>
	                    <img src="<%=request.getContextPath() %>/images/report/ok_blue.png" id="okUpdateImg{{report.project_id }}" class="okImg" ng-click="changePname(report.project_id)" />
                   	</span>
               </div>
               <div>
                   App名称：{{report.app_name }}
               </div>
               <div>
               	文件数量：
                <span id="rdataNum{{report.project_id }}">
                	{{report.data_num }}
                </span>
               </div>
               <div>
               	数据大小：{{report.data_size | fileSizeFormat}}
               </div>
               <div>
               	启动时间：{{report.create_date | date:'yyyy-MM-dd HH:mm:ss'}}
               </div>
               <div>
               	结束时间：{{report.end_date | date:'yyyy-MM-dd HH:mm:ss'}}
               </div>
               <div class="operate" ng-if="report.userName!='no_one'">
                   <a class="sharefrom" title="共享" href="javascript:void()"></a><span class="shareU">{{report.userName }}</span>
                   <a class="delete" title="删除" ng-click="cancelProjectShare(report.project_id,report.user_id)" href="javascript:void(0)"></a>
               </div>
               <div class="operate" ng-if="report.userName=='no_one'">
                   <a ng-if="loginUserInSession.companyId == 6" class="projectreport" title="项目报告" target="_blank" href="{{pageContext.request.contextPath }}/report/printPgsProject?projectId={{report.project_id}}"><i class="fa fa-file-text-o" aria-hidden="true"></i></a>
	               <a class="pdfdown" title="PDF下载" ng-click="downPDF(report.user_id,report.app_id,report.project_id)" href="javascript:void(0)" ng-if="report.app_id>84&&report.app_id!=89&&report.app_id!=90&&report.app_id!=105&&report.app_id!=106&&report.app_id!=107&&report.app_id!=108&&report.app_id!=109&&report.app_id!=110&&report.app_id!=111&&report.app_id!=112&&report.app_id!=113&&report.app_id!=114&&report.app_id!=117"></a>
	               <a ng-if="report.share_num==0" class="share" title="共享" ng-click="toShareModal(report.project_id,report.project_name,report.data_num)" href="javascript:void(0);"></a>
	               <a ng-if="report.share_num!=0" class="shared" title="已共享" ng-click="shareModal(report.project_id,report.user_id,report.project_name,report.data_num)" href="javascript:void(0);"></a>
                   <a class="delete" title="删除" ng-click="removePro(report.project_id)" href="javascript:void(0)"></a>
               </div>
            </td>
            <td class="hide">
			    {{report.app_id}},{{report.app_name}},{{report.project_id}},{{report.user_id}}
			</td>
            <td ng-bind-html="report.context | trustHtml" class="projectContext"></td>
          </tr>
        </tbody>
      </table>
      <ng-include src="'pages/partial/_partial_pagination_common.jsp'" ></ng-include>
</div>
