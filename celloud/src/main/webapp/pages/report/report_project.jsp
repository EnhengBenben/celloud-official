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
	            <li><a ng-click="changeBelongs(1)" id="belongs1" href="javascript:void(0)" ng-class="{active: projectOptions.belongs == 1}" class="belongs">全部</a></li>
	            <li><a ng-click="changeBelongs(2)" id="belongs2" href="javascript:void(0)" ng-class="{active: projectOptions.belongs == 2}" class="belongs">我的</a></li>
	            <li><a ng-click="changeBelongs(3)" id="belongs3" href="javascript:void(0)" ng-class="{active: projectOptions.belongs == 3}" class="belongs">共享来的</a></li>
              </ul>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>时&emsp;&emsp;间：</label>
            <div class="search-type-detail times">
              <ul class="search-info seartch-date">
	            <li><a ng-click="changeDate(0)" ng-class="{active: projectOptions.changeDate == 0}" id="changeDate0" href="javascript:void(0)" class="changeDate">全部</a></li>
	            <li><a ng-click="changeDate(1)" ng-class="{active: projectOptions.changeDate == 1}" id="changeDate1" href="javascript:void(0)" class="changeDate">24h</a></li>
	            <li><a ng-click="changeDate(3)" ng-class="{active: projectOptions.changeDate == 3}" id="changeDate3" href="javascript:void(0)" class="changeDate">3d</a></li>
	            <li><a ng-click="changeDate(7)" ng-class="{active: projectOptions.changeDate == 7}" id="changeDate7" href="javascript:void(0)" class="changeDate">7d</a></li>
	            <li><a ng-click="changeDate(15)" ng-class="{active: projectOptions.changeDate == 15}" id="changeDate15" href="javascript:void(0)" class="changeDate">15d</a></li>
	            <li><a ng-click="changeDate(30)" ng-class="{active: projectOptions.changeDate == 30}" id="changeDate30" href="javascript:void(0)" class="changeDate">30d</a></li>
              </ul>
              <div class="search-btns">
                <input type="text" id="_searchDate" class="Wdate input" onclick="WdatePicker()" ng-if="projectOptions.changeDate == -1" ng-model="projectOptions.start" readonly="readonly" style="cursor: pointer;">
                <input type="text" id="_searchDate" class="Wdate input" onclick="WdatePicker()" ng-if="projectOptions.changeDate != -1" readonly="readonly" style="cursor: pointer;">
                <span>-</span>
                <input type="text" id="_endDate" class="Wdate input" onclick="WdatePicker()" ng-if="projectOptions.changeDate == -1" ng-model="projectOptions.end" readonly="readonly" style="cursor: pointer;">
                <input type="text" id="_endDate" class="Wdate input" onclick="WdatePicker()" ng-if="projectOptions.changeDate != -1" readonly="readonly" style="cursor: pointer;">
                <button class="btn data-operate" ng-click="chooseDate()">确定</button>
              </div>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>产品标签：</label>
            <div class="search-type-detail inline-detail {{reportMoreAppTag|chevronTypeDivFilter}}" ng-init="reportMoreAppTag=true">
              <ul class="search-info">
                <li><a class="changeApp" ng-class="{active: projectOptions.app == 0}" ng-click="changeApp(0)" id="changeApp0" href="javascript:void(0)">全部</a></li>
                <li ng-repeat="app in ranAppList.datas">
                	<a class="changeApp" ng-class="{active: projectOptions.app == app.app_id}" ng-click="changeApp(app.app_id)" id="changeApp{{app.app_id}}" href="javascript:void(0)">{{app.app_name}}</a>
                </li>
              </ul>
              <div class="search-btns">
                <button class="btn chevron-btn" ng-click="reportMoreAppTag=changeChevronType(reportMoreAppTag)">{{reportMoreAppTag|chevronTypeTextFilter}}<i ng-class="reportMoreAppTag|chevronTypeFaFilter" aria-hidden="true"></i></button>
              </div>
            </div>
          </li>
          <li class="search-type clearfix">
            <label>数&emsp;&emsp;据：</label>
            <div class="search-type-detail">
              <input type="text" placeholder="检索文件名/别名/数据编号" ng-change="changeCondition()" ng-model="projectOptions.condition" onkeydown="if(event.keyCode==13){event.keyCode=0;return false}">
            </div>
          </li>
        </ul>
        </form>
      </div>
      <table class="table table-main info-table pro-table no-hover" ng-init="pageType='reportpro'">
        <tbody id="_show">
          <tr ng-if="dataList.datas.length==0"><td style="text-align: center;">没有可以展示的报告</td></tr>
          <tr ng-repeat="report in dataList.datas" load-over>
            <td>
            	<div>
               		项目名称：
               		<span id="showPname{{report.project_id }}">
	                    <span id="pnameSpan{{report.project_id }}">
	                    	{{report.project_name | contextLengthFilter:'13'}}
	                    </span>
                    	<a title="编辑" ng-if="report.userName=='no_one'" ng-click="toChangePname(report.project_id)"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
                   	</span>
                   	<span ng-if="report.userName=='no_one'" id="changePname{{report.project_id}}" class="hide">
	                    <input style="width: 120px;color: black;" type="text" value="{{report.project_name}}" ng-blur="changePname(report.project_id)" id="updatePname{{report.project_id }}" class="changeInput"/>
                    	<a style="float: right;" title="确定" ng-if="report.userName=='no_one'" ng-click="changePname(report.project_id)"><i class="fa fa-check" aria-hidden="true"></i></a>
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
                   <a style="padding-right: 20px;" title="共享" class="sharefrom" href="javascript:void()">共享自{{report.userName }}</a>
                   <a style="padding-right: 20px;" title="删除" class="delete" ng-click="cancelProjectShare(report.project_id)" href="javascript:void(0)"><i class="fa fa-times" aria-hidden="true"></i></a>
               </div>
               <div class="operate" ng-if="report.userName=='no_one'">
                   <a style="padding-right: 20px;" title="项目报告" ng-if="report.context!=null && report.context!='' && (companyId == 6 || companyId == 24)" class="projectreport" target="_blank" href="report/printPgsProject?projectId={{report.project_id}}"><i class="fa fa-file-text-o" aria-hidden="true"></i></a>
	               <a style="padding-right: 20px;" title="PDF下载" ng-if="report.context!=null && report.context!='' && ((report.app_id>84&&report.app_id<89)||(report.app_id>90&&report.app_id<96)||(report.app_id>118&&report.app_id<131&&report.app_id!=126&&report.app_id!=127&&report.app_id!=128&&report.app_id!=123)||report.app_id==104||report.app_id==116)" class="pdfdown" ng-click="downPDF(report.user_id,report.app_id,report.project_id)" href="javascript:void(0)"><i class="fa fa-file-pdf-o" aria-hidden="true"></i></a>
	               <a style="padding-right: 20px;" title="报告共享" ng-if="report.share_num==0" data-toggle="modal" data-target="#project-share-modal" class="share" ng-click="toShareModal(report.project_id,report.project_name,report.data_num)"><i class="fa fa-share" aria-hidden="true"></i></a>
	               <a style="padding-right: 20px;" title="已共享" ng-if="report.share_num!=0" data-toggle="modal" data-target="#project-share-modal" class="shared" ng-click="shareModal(report.project_id,report.project_name,report.data_num)"><i class="fa fa-share-square-o" aria-hidden="true"></i></a>
                   <a style="padding-right: 20px;" title="删除" class="delete" ng-click="removePro(report.project_id)" href="javascript:void(0)"><i class="fa fa-times" aria-hidden="true"></i></a>
               </div>
            </td>
            <td class="hide">
			    {{report.app_id}},{{report.code}},{{report.project_id}},{{report.user_id}}
			</td>
            <td ng-if="report.context!=null&&report.context!=''" ng-bind-html="report.context | trustHtml" class="projectContext"></td>
            <td ng-if="report.context==null||report.context==''" class="projectContext" style="text-align: center;">
            	<img src="<%=request.getContextPath() %>/images/report/running.png" title="正在运行..."/>
            </td>
          </tr>
        </tbody>
      </table>
      <pagination page="dataList.page" change="pageQuery(page,pageSize)"></pagination>
</div>
<div id="project-share-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">项目共享</h4>
	      <span style="float: right;margin-right: 150px;margin-top: -14px;">项目名称：<span ng-bind="shareProjectName"></span></span>
	    </div>
	    <div class="modal-body form-modal">
	      <form class="form-horizontal info-form" name="editDataForm" id="editDataForm">
	          <div class="form-group">
	            <div class="col-xs-12">
	            	共计<span ng-bind="dataNum"></span>个数据文件
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="col-xs-12 select-content">
	                <select style="width: 400px;" multiple="" tabindex="-1" aria-hidden="true" id="shareProjectSelect"></select>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="text-center">
	                <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
	                <button type="submit" class="btn" ng-click="saveShareProject()">提交</button>
	            </div>
	            <div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="updateState">
	              <button type="button" class="close" ng-click="updateState=false"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	              <span>{{updateMessage}}</span>
	            </div>
	          </div>
	      </form>
	    </div>
	  </div>
	</div>
</div>
