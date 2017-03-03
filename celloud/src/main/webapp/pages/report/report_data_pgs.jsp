<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>{{pgs.appName}}报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{pgs.appName}}
        </p>
        <p> 文件名称：
            {{pgs.anotherName != null ? pgs.anotherName : pgs.fileName}}({{pgs.dataKey}})
        </p>
        <div class="btn-groups">
        	<a class="btn -low" target="_blank" ng-href="report/printPGS?appId={{pgs.appId}}&projectId={{pgs.projectId}}&dataKey={{pgs.dataKey}}&flag=0"><i class="fa fa-print"></i>打印报告1</a>
            <a ng-if="pgs.splitPng != null && pgs.companyId != 949" class="btn -low" target="_blank" ng-href="report/printPGS?appId={{pgs.appId}}&projectId={{pgs.projectId}}&dataKey={{pgs.dataKey}}&flag=1"><i class="fa fa-print"></i>点图报告1</a>                  
        	<a ng-if="pgs.reportMiniPng != null" class="btn -low" target="_blank" ng-href="report/printPGS?appId={{pgs.appId}}&projectId={{pgs.projectId}}&dataKey={{pgs.dataKey}}&flag=2"><i class="fa fa-print"></i>打印报告2</a>
            <a ng-if="pgs.reportMiniPng != null && pgs.companyId != 949" class="btn -low" target="_blank" ng-href="report/printPGS?appId={{pgs.appId}}&projectId={{pgs.projectId}}&dataKey={{pgs.dataKey}}&flag=3"><i class="fa fa-print"></i>点图报告2</a>                  
            <br/>
            <div class="mt3">
	        	<a ng-if="experiment != null && experiment.qualified == null" class="btn -low" href="javascript:void(0)" ng-click="showConclusion()"><i class="fa fa-edit"></i>结果判定</a>
	        	<a ng-if="experiment != null && (experiment.qualified==0 || experiment.qualified==1)" class="btn -low" href="javascript:void(0)">
		        	<span ng-if="experiment.qualified == 1">无效</span>
		        	<span ng-if="experiment.qualified == 0">有效</span>
	        	</a>
	        	<a ng-if="experiment != null && (experiment.qualified==0 || experiment.qualified==1)" class="btn -low" href="javascript:void(0)" ng-click="editShowConclusion()"><i class="fa fa-edit"></i>结果修改</a>
            </div>
        </div>
      </div>
      <div>
        <section class="m-box" ng-if="pgs.noEnoughReads == 'false'">
	        <h2>
				<i class="i-report1"></i>数据统计
				<div ng-if="pgs.appId != 85" style="line-height:38px;float:right;padding-right: 30px" title="帮助" ng-click="showModal('countModal')"><i class="i-tips"></i></div>
			</h2>
			<div class="m-boxCon" id="_table">
				<table class="table table-bordered table-condensed">
				  <thead>
					<tr>
						<th ng-if="pgs.totalReads != null">Total_Reads</th>
						<th ng-if="pgs.mapReads != null">Map_Reads</th>
						<th ng-if="pgs.mtRatio != null">MT_ratio(%)</th>
						<th ng-if="pgs.mapRatio != null">Map_Ratio(%)</th>
						<th ng-if="pgs.duplicate != null">Duplicate(%)</th>
						<th ng-if="pgs.gcCount != null">GC_Count(%)</th>
						<th ng-if="pgs.sd != null">*SD</th>
						<th ng-if="pgs.winSize != null">Win_size(kb)</th>
					</tr>
				  </thead>
				  <tbody>
					<tr>
						<td ng-if="pgs.totalReads != null">{{pgs.totalReads}}</td>
						<td ng-if="pgs.mapReads != null">{{pgs.mapReads}}</td>
						<td ng-if="pgs.mtRatio != null">{{pgs.mtRatio}}</td>
						<td ng-if="pgs.mapRatio != null">{{pgs.mapRatio}}</td>
						<td ng-if="pgs.duplicate != null">{{pgs.duplicate}}</td>
						<td ng-if="pgs.gcCount != null">{{pgs.gcCount}}</td>
						<td ng-if="pgs.sd != null">{{pgs.sd}}</td>
						<td ng-if="pgs.winSize != null">{{pgs.winSize}}</td>
					</tr>
				  </tbody>
				</table>
			</div>
			<div ng-if="pgs.note != null && pgs.note != '' && pgs.appId == 85" class="m-tips">
				<i class="i-tips"></i>{{pgs.note}}
			</div>
	    </section>
	    <section class="m-box" ng-if="pgs.noEnoughReads == 'false'">
	        <h2><i class="i-edit"></i>报告
				<div ng-if="pgs.appId != 85 && pgs.detail != null && pgs.detail.length > 0" style="line-height:38px;float:right;padding-right: 30px" title="帮助" ng-click="showModal('reportModal')"><i class="i-tips"></i></div>
			</h2>
			<div class="m-boxCon result" id="reportDiv">
				<div ng-if="pgs.appId == 85">
					{{pgs.report}}
				</div>
				<div ng-if="pgs.appId != 85">
					<div ng-if="pgs.detail == null || pgs.detail.length == 0">
						{{pgs.report}}
					</div>
					<div ng-if="pgs.detail != null && pgs.detail.length > 0">
						<table class='table table-main' id="reportTable">
							<tr ng-repeat="info in pgs.detail" pgs-over>
							    <td ng-if="info.length == 1 || info.length == 2 || info.length == 3 || info.length == 4">{{info[0]}}</td>
							    <td ng-if="info.length == 2 || info.length == 3 || info.length == 4">{{info[1]}}</td>
							    <td ng-if="info.length == 3 || info.length == 4">{{info[2].length > 50 ? info[2].substr(0,50) + '...' : info[2]}}</td>
							    <td ng-if="info.length == 4">{{info[3].length > 55 ? info[3].substr(0,55) + '...' : info[3]}}</td>
						    	<td ng-if="info.length == 1"></td>
						    	<td ng-if="info.length == 1"></td>
						    	<td ng-if="info.length == 1"></td>
						    	<td ng-if="info.length == 2"></td>
						    	<td ng-if="info.length == 2"></td>
						    	<td ng-if="info.length == 3"></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div ng-if="pgs.mosaic != null && pgs.mosaic != ''" class="m-boxCon result">
				{{pgs.mosaic}}
			</div>
	     </section>
         <section class="m-box" ng-if="pgs.noEnoughReads == 'false'">
	         <h2><i class="i-dna"></i>染色体</h2>
             <div class="m-boxCon">
				<img ng-if="pgs.miniPng != null && pgs.miniPng != ''" ng-src="{{uploadPath}}{{pgs.userId}}/{{pgs.appId}}/{{pgs.dataKey}}/{{pgs.miniPng}}" style="width: 100%;" id="miniPngImg">
				<span ng-if="pgs.miniPng == null || pgs.miniPng == ''" style="color: red;">运行异常，未产生图片！</span>
             </div>
         </section>
         <section class="m-box" ng-if="pgs.noEnoughReads == 'false'">
	         <h2><i class="i-dna"></i>染色体点图</h2>
             <div class="m-boxCon">
				<a ng-if="pgs.testPng != null && pgs.testPng != ''" ng-click="bigOrigin(uploadPath + pgs.userId + '/' + pgs.appId + '/' + pgs.dataKey + '/' + pgs.testPng,'testPngImg');" >
					<img ng-src="{{uploadPath}}{{pgs.userId}}/{{pgs.appId}}/{{pgs.dataKey}}/{{pgs.testPng}}" style="width: 100%;" id="testPngImg">
				</a>
				<span ng-if="pgs.testPng == null || pgs.testPng == ''" style="color: red;">运行异常，未产生图片！</span>
				<a ng-if="pgs.hrpng != null && pgs.hrpng != ''" ng-click="bigOrigin(uploadPath + pgs.userId + '/' + pgs.appId + '/' + pgs.dataKey + '/' + pgs.hrpng,'HRPngImg');" >
					<img ng-src="{{uploadPath}}{{pgs.userId}}/{{pgs.appId}}/{{pgs.dataKey}}/{{pgs.hrpng}}" style="width: 100%;" id="HRPngImg">
				</a>
             </div>
	     </section>
         <section class="m-box" ng-if="pgs.noEnoughReads == 'false'">
	         <h2><i class="i-dna"></i>染色体位置图</h2>
             <div class="m-boxCon">
				<img ng-if="pgs.finalPng != null && pgs.finalPng != ''" ng-src="{{uploadPath}}{{pgs.userId}}/{{pgs.appId}}/{{pgs.dataKey}}/{{pgs.finalPng}}" style="width: 100%;" id="finalPngImg">
				<span ng-if="pgs.finalPng == null || pgs.finalPng == ''" style="color: red;">运行异常，未产生图片！</span>
             </div>
	     </section>
	     <section ng-if="pgs.noEnoughReads == 'false' && pgs.appId != 104 && pgs.appId != 116" class="m-box">
	         <h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
	         <div class="m-boxCon">
	        	<div class="row" id="charDiv">
	        	</div>
	         </div>
	         <div class="m-tips">
	        	<i class="i-tips"></i>
	        	<span id="charResult"></span>
	         </div>
	     </section>
	     <section class="m-box" ng-if="pgs != undefined && pgs.noEnoughReads != 'false'">
	     	<h3>测序量不足，无法分析，建议重测。</h3>
			<p>{{pgs.noEnoughReads}}</p>
	     </section>
	   </div>
     </div>
 </div>

<div class="modal modal-green-header in" id="countModal">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
        <h4 class="modal-title">注释</h4>
	</div>
	<div class="modal-body form-modal">
		<div class="lineheight">Total_Reads：样本测序序列总数据量，分析要求数据量大于15万。</div>
		<div class="lineheight">Map_Reads：样本序列比对到人类基因组的数据量。</div>
		<div class="lineheight">Map_Ratio：样本序列比对到人类基因组的数据量比例。</div>
		<div class="lineheight">Duplicate：样本测序过程中冗余序列比例，冗余序列产生于同一序列片段，为消除冗余的对分析染色体拷贝数的影响，分析过程中去除冗余序列。</div>
		<div class="lineheight">GC_Count：样本测序序列的GC含量，围绕人类基因组GC平均含量41%波动。</div>
		<div class="lineheight">SD：样本拷贝数分析中染色体的平均偏差，SD越小假阳率越低，SD小于3.5可检测4Mb以上染色体异常。</div>
		<c:if test="{{pgs.appId==81||pgs.appId==88||pgs.appId==91||pgs.appId==93}}">
			<div class="lineheight">MT_Ratio：样本测序序列中线粒体序列百分比。数据统计表明染色体拷贝数异常胚胎线粒体比例高。</div>
			<div>
				<img alt="" src="<%=request.getContextPath()%>/images/report/pgs.png" width="100%">
			</div>
		</c:if>
	</div>
	<div class="modal-footer">
		<a class="btn -low pull-right" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
	</div>
  </div>
 </div>
</div>
<div class="modal modal-green-header in" id="reportModal">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
        <h4 class="modal-title">报告说明</h4>
	</div>
	<div class="modal-body">
		<div class="lineheight">Aneuploidy：染色体异倍性及异常区域。</div>
		<div class="lineheight">Position：染色体异常位置。</div>
		<div class="lineheight">Average：染色体拷贝数分析中每个染色体的平均值。该值为染色体拷贝数据的参考值。数据统计表明大部分正常染色体平均值为15-23，染色体三体的平均值大于25，染色体单体的平均值小于13。</div>
	</div>
	<div class="modal-footer">
		<a class="btn -low pull-right" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
	</div>
  </div>
 </div>
</div>
<div id="reportConclusion" class="modal fade">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">结果判定</h4>
      </div>
      <div class="modal-body row">
        <form id="reportConclusionForm" class="form-horizontal form-cel">
          <div class="form-group">
         	<div class="control-label form-label col-xs-3" style="margin-top: -9px;">是否合格</div>
         	<div class="col-xs-9">
         		<input type="radio" name="qualified" value="0" ng-checked="experiment.qualified == 0" /> 有效
         		<input type="radio" name="qualified" value="1" ng-checked="experiment.qualified == 1" /> 无效
	         	<input type="hidden" name="id" value="{{experiment.id}}"/>
	         	<!-- 状态修改为正常关闭 -->
	         	<input type="hidden" name="state" value="2"/>
         	</div>
          </div>
          <div class="form-group">
         	<div class="control-label form-label col-xs-3">备注</div>
         	<div class="col-xs-9">
         		<textarea rows="3" cols="40" name="remarks"></textarea>
         	</div>
          </div>
        </form>
      </div>
      <div id="conclusion-error" class="alert alert-warning-cel alert-dismissable hide">
		<h5 style="text-align: center;" id="conclusionErrorInfo"></h5>
	  </div>
      <div class="modal-footer">
        <button type="button" class="btn -low pull-left" data-dismiss="modal">关闭</button>
        <button ng-click="saveConclusion()" type="button" class="btn -low pull-right">保存</button>
      </div>
    </div>
  </div>
</div>
<div id="editReportConclusion" class="modal modal-green-header">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">结果修改</h4>
      </div>
      <div class="modal-body row">
        <form id="editReportConclusionForm" class="form-horizontal form-cel">
          <div class="form-group">
         	<div class="control-label form-label col-xs-3" style="margin-top: -9px;">是否合格</div>
         	<div class="col-xs-9">
         		<input type="radio" name="qualified" value="0" ng-checked="experiment.qualified == 0" /> 有效
         		<input type="radio" name="qualified" value="1" ng-checked="experiment.qualified == 1" /> 无效
	         	<input type="hidden" name="id" value="{{experiment.id}}"/>
	         	<!-- 状态修改为正常关闭 -->
	         	<input type="hidden" name="state" value="2"/>
         	</div>
          </div>
          <div class="form-group">
         	<div class="control-label form-label col-xs-3">备注</div>
         	<div class="col-xs-9">
         		<textarea rows="3" cols="40" name="remarks">{{experiment.remarks}}</textarea>
         	</div>
          </div>
        </form>
      </div>
      <div id="edit-conclusion-error" class="alert alert-warning-cel alert-dismissable hide">
		<h5 style="text-align: center;" id="edit-conclusionErrorInfo"></h5>
	  </div>
      <div class="modal-footer">
        <button type="button" class="btn -low pull-left" data-dismiss="modal">关闭</button>
        <button ng-click="editSaveConclusion()" type="button" class="btn -low pull-right">保存</button>
      </div>
    </div>
  </div>
</div>
<ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>
