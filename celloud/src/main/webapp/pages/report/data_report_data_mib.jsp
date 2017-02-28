<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="pro-body mreport" ng-controller="commonReport">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>MIB报告</li>
    </ol>
    <div class="content">
         <div class="content-left col-xs-10">
	      <div class="content-header">
	        <p> 数据编号：
	            {{mib.dataKey}}
	        </p>
	        <p> 文件名称：
	           <span class="file-name" ng-repeat="data in mib.data">{{data.fileName}}({{data.dataKey}})&nbsp;&nbsp;</span>
	        </p>
	        <p> 检测结果：
	            {{mib.conclusion}}
	        </p>
	        <div class="btn-groups">
		        <a class="btn -low" target="_blank" ng-href="<%=request.getContextPath()%>/report/printMIBReport?projectId={{mib.projectId }}&dataKey={{mib.dataKey }}&appId={{mib.appId }}">打印报告</a>
	        </div>
	      </div>
	      <div>
	        <section class="m-box">
		        <h2><i class="fa fa-bars" aria-hidden="true"></i>数据统计</h2>
		        <table class="table table-main">
		          <thead>
		            <tr><th>序列总数</th><th>平均质量</th><th>平均GC含量</th></tr>
		          </thead>
		          <tbody>
		            <tr>
		              <td>{{mib.totalReads }}</td>
		              <td>{{mib.avgQuality }}</td>
		              <td>{{mib.avgGCContent }}</td>
		            </tr>
		          </tbody>
		        </table>
		    </section>
		    <section class="m-box">
		        <h2><i class="fa fa-pie-chart" aria-hidden="true"></i>Reads Distribution</h2>
		        <table class="table-img two-img">
		           <tr>
		             <td>
		               <img ng-if="readsDistributionInfo=='' && !mib.readsDistribution.contains('Tools') && mib.readsDistribution!=null" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.readsDistribution }}">
		               <img ng-if="readsDistributionInfo=='' && mib.readsDistribution.contains('Tools') && mib.readsDistribution!=null" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.readsDistribution }}">
		               <div ng-if="readsDistributionInfo!=''" id="reads-distribution-char" style="width:100%;height:330px;"></div>
		             </td>
		             <td>
		               <img ng-if="familyDistributionInfo=='' && !mib.familyDistribution.contains('Tools') && mib.familyDistribution!=null" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.familyDistribution }}">
		               <img ng-if="familyDistributionInfo=='' && mib.familyDistribution.contains('Tools') && mib.familyDistribution!=null" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.familyDistribution }}">
		               <div ng-if="familyDistributionInfo!=''" id="family-distribution-char" style="width:100%;height:330px;"></div>
		             </td>
		           </tr>
		         </table>
		     </section>
	         <section class="m-box">
		         <h2><i class="fa fa-bar-chart" aria-hidden="true"></i>Genus Distribution</h2>
		         <div id="genus-distribution-parent" class="m-box">
		           <img ng-if="genusDistributionInfo=='' && !mib.genusDistributionInfo.contains('Tools') && mib.genusDistribution!=null" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.genusDistribution }}">
		           <img ng-if="genusDistributionInfo=='' && mib.genusDistributionInfo.contains('Tools') && mib.genusDistribution!=null" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.genusDistribution }}">
		           <div ng-if="genusDistributionInfo!=''" id="genus-distribution-char" style="width:100%;height:330px;"></div>
		         </div>
	         </section>
	         <section class="m-box">
		         <h2><i class="fa fa-table" aria-hidden="true"></i>报告</h2>
		         <table class="table table-main" id="snp_table1">
		           <thead>
		             <tr>
		               <th style="line-height: 12px;vertical-align: middle;">种<br><span style="font-size:12px;color: #9A9999;">Species</span></th>
		               <th style="line-height: 12px;vertical-align: middle;min-width:110px;">属<br><span style="font-size:12px;color: #9A9999;">Genus</span></th>
		               <th style="line-height: 12px;vertical-align: middle;min-width:80px;">GI号<br><span style="font-size:12px;color: #9A9999;">GI</span></th>
		               <th style="line-height: 12px;vertical-align: middle;min-width:90px;">覆盖长度%<br><span style="font-size:12px;color: #9A9999;">%Coverage</span></th>
		               <th style="line-height: 12px;vertical-align: middle;min-width:130px;">种比对上的序列数<br><span style="font-size:12px;color: #9A9999;">Reads_hit</span></th>
		               <th style="line-height: 12px;vertical-align: middle;min-width:130px;">属比对上的序列数<br><span style="font-size:12px;color: #9A9999;">Reads_num</span></th>
		               <th style="line-height: 12px;vertical-align: middle;min-width:130px;">种序列百分比<br><span style="font-size:12px;color: #9A9999;">%Reads_Ratio</span></th>
		               <th style="line-height: 12px;vertical-align: middle;min-width:170px;">平均覆盖深度<br><span style="font-size:12px;color: #9A9999;">Average depth of coverage</span></th>
		             </tr>
		           </thead>
		           <tbody>
		             <tr ng-repeat="summary in mib.summaryTable">
		               <td style="text-align: left;line-height:1em">${summary.Species }</td>
		               <td>{{summary.Genus }}</td>
		               <td>{{summary.GI }}</td>
		               <td>{{summary.Coverage }}</td>
		               <td>{{summary.Reads_hit }}</td>
		               <td>{{summary.Reads_num }}</td>
		               <td>{{summary.Reads_Ratio }}</td>
		               <td>{{summary.avgCoverage }}</td>
		             </tr>
		           </tbody>
		         </table>
		     </section>
	         <section class="m-box">
		         <h2><i class="fa fa-picture-o" aria-hidden="true"></i>报告图示</h2>
		         <div class="m-boxCon">
		           <img ng-if="mib.pngPath.top1png!=null && mib.pngPath.top1png!='' && !mib.pngPath.top1png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.pngPath.top1png }}">
		           <img ng-if="mib.pngPath.top1png!=null && mib.pngPath.top1png!='' && mib.pngPath.top1png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.pngPath.top1png }}">
				   <img ng-if="mib.pngPath.top2png!=null && mib.pngPath.top2png!='' && !mib.pngPath.top2png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.pngPath.top2png }}">
		           <img ng-if="mib.pngPath.top2png!=null && mib.pngPath.top2png!='' && mib.pngPath.top2png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.pngPath.top2png }}">
		           <img ng-if="mib.pngPath.top3png!=null && mib.pngPath.top3png!='' && !mib.pngPath.top3png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.pngPath.top3png }}">
		           <img ng-if="mib.pngPath.top3png!=null && mib.pngPath.top3png!='' && mib.pngPath.top3png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.pngPath.top3png }}">
		           <img ng-if="mib.pngPath.top4png!=null && mib.pngPath.top4png!='' && !mib.pngPath.top4png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.pngPath.top4png }}">
		           <img ng-if="mib.pngPath.top4png!=null && mib.pngPath.top4png!='' && mib.pngPath.top4png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.pngPath.top4png }}">
		           <img ng-if="mib.pngPath.top5png!=null && mib.pngPath.top5png!='' && !mib.pngPath.top5png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.pngPath.top5png }}">
		           <img ng-if="mib.pngPath.top5png!=null && mib.pngPath.top5png!='' && mib.pngPath.top5png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.pngPath.top5png }}">
		           <img ng-if="mib.pngPath.top6png!=null && mib.pngPath.top6png!='' && !mib.pngPath.top6png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.pngPath.top6png }}">
		           <img ng-if="mib.pngPath.top6png!=null && mib.pngPath.top6png!='' && mib.pngPath.top6png.contains('Tools')"  ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.pngPath.top6png }}">
		           <img ng-if="mib.pngPath.top7png!=null && mib.pngPath.top7png!='' && !mib.pngPath.top7png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.pngPath.top7png }}">
		           <img ng-if="mib.pngPath.top7png!=null && mib.pngPath.top7png!='' && mib.pngPath.top7png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.pngPath.top7png }}">
		           <img ng-if="mib.pngPath.top8png!=null && mib.pngPath.top8png!='' && !mib.pngPath.top8png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.pngPath.top8png }}">
		           <img ng-if="mib.pngPath.top8png!=null && mib.pngPath.top8png!='' && mib.pngPath.top8png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.pngPath.top8png }}">
		           <img ng-if="mib.pngPath.top9png!=null && mib.pngPath.top9png!='' && !mib.pngPath.top9png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.pngPath.top9png }}">
		           <img ng-if="mib.pngPath.top9png!=null && mib.pngPath.top9png!='' && mib.pngPath.top9png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.pngPath.top9png }}">
		           <img ng-if="mib.pngPath.top10png!=null && mib.pngPath.top10png!='' && !mib.pngPath.top10png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.pngPath.top10png }}">
		           <img ng-if="mib.pngPath.top10png!=null && mib.pngPath.top10png!='' && mib.pngPath.top10png.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.pngPath.top10png }}">
		         </div>
		     </section>
	         <section class="m-box">
		         <h2><i class="fa fa-picture-o" aria-hidden="true"></i>序列质量分析（见QC结果）</h2>
		         <table class="table table-main">
		             <thead>
		                 <tr>
		                     <th>#Measure</th>
		                     <th colspan="2">Value</th>
		                 </tr>
		             </thead>
		             <tbody>
		                 <tr>
		                     <td>Filename</td>
		                     <td>{{mib.basicStatistics1.Filename }}</td>
		                     <td>{{mib.basicStatistics2.Filename }}</td>
		                 </tr>
		                 <tr>
		                     <td>File type</td>
		                     <td>{{mib.basicStatistics1.FileType }}</td>
		                     <td>{{mib.basicStatistics2.FileType }}</td>
		                 </tr>
		                 <tr>
		                     <td>Encoding</td>
		                     <td>{{mib.basicStatistics1.Encoding }}</td>
		                     <td>{{mib.basicStatistics2.Encoding }}</td>
		                 </tr>
		                 <tr>
		                     <td>Total Sequences</td>
		                     <td>{{mib.basicStatistics1.TotalSeq }}</td>
		                     <td>{{mib.basicStatistics2.TotalSeq }}</td>
		                 </tr>
		                 <tr>
		                     <td>Filtered Sequences</td>
		                     <td>{{mib.basicStatistics1.FilteredSeq }}</td>
		                     <td>{{mib.basicStatistics2.FilteredSeq }}</td>
		                 </tr>
		                 <tr>
		                     <td>Sequence length</td>
		                     <td>{{mib.basicStatistics1.SeqLength }}</td>
		                     <td>{{mib.basicStatistics2.SeqLength }}</td>
		                 </tr>
		                 <tr>
		                     <td>%GC</td>
		                     <td>{{mib.basicStatistics1.gc }}</td>
		                     <td>{{mib.basicStatistics2.gc }}</td>
		                 </tr>
		             </tbody>
		         </table>
		         <table class="table-img two-img">
		           <tr>
		             <td>
		               <img ng-if="mib.qualityPath1!=null && mib.qualityPath1!='' && !mib.qualityPath1.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.qualityPath1 }}">
		               <img ng-if="mib.qualityPath1!=null && mib.qualityPath1!='' && mib.qualityPath1.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.qualityPath1 }}">
		             </td>
		             <td>
		               <img ng-if="mib.qualityPath2!=null && mib.qualityPath2!='' && !mib.qualityPath2.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.qualityPath2 }}">
		               <img ng-if="mib.qualityPath2!=null && mib.qualityPath2!='' && mib.qualityPath2.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.qualityPath2 }}">
		             </td>
		           </tr>
		           <tr>
		             <td>
		               <img ng-if="mib.seqContentPath1!=null && mib.seqContentPath1!='' && !mib.seqContentPath1.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.seqContentPath1 }}">
		               <img ng-if="mib.seqContentPath1!=null && mib.seqContentPath1!='' && mib.seqContentPath1.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.seqContentPath1 }}">
		             </td>
		             <td>
		               <img ng-if="mib.seqContentPath2!=null && mib.seqContentPath2!='' && !mib.seqContentPath2.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{uploadPath+'/'+mib.userId+'/'+mib.appId+'/'+mib.dataKey+'/'+mib.seqContentPath2 }}">
		               <img ng-if="mib.seqContentPath2!=null && mib.seqContentPath2!='' && mib.seqContentPath2.contains('Tools')" ng-src="<%=request.getContextPath()%>/report/reportImage?file={{mib.seqContentPath2 }}">
		             </td>
		           </tr>
		         </table>
		     </section>
		   </div>
		 </div>
		 <div class="content-right col-xs-2">
            <ng-include src="'pages/partial/_partial_datareportoperate.jsp'"></ng-include>
          </div>
     </div>
 </div>