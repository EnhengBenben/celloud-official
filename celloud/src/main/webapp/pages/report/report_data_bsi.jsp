<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="${pageContext.request.contextPath }/resources/css/bsi_main.css?v=3.3.12" rel="stylesheet">
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>百菌探报告</li>
    </ol>
    <div class="content">
		<div class="row rdata-content">
		  <div class="col-sm-10">
			  <ul id="myTabs" class="nav nav-tabs" role="tablist">
			    <li role="presentation" ng-class="{active:tab == 'patient'}"><a href="javascript:void(0);" ng-click="tab = 'patient'" id="patient-tab" aria-controls="patient-report" role="tab" data-toggle="tab">患者报告</a></li>
			    <li role="presentation" ng-class="{active:tab == 'analy'}"><a href="javascript:void(0);" ng-click="tab = 'analy'" id="analy-tab" aria-controls="analy-report" role="tab" data-toggle="tab">分析报告</a></li>
			  </ul>
			  <div id="myTabContent" class="tab-content">
			    <div role="tabpanel" class="tab-pane" ng-class="{active : tab == 'patient'}" id="patient-report" aria-labelledby="patient-tab">
			      <h4>检测结果：</h4>
			      <div class="test-info">
			        <span ng-repeat="species20 in bsi.species_20">
		              <span ng-if="species20.species_zh == '未知' || species20.species_zh == null">{{species20.species}}<br></span>
		              <span ng-if="species20.species_zh != '未知' && species20.species_zh != null">{{species20.species_zh}}<br></span>
		            </span>
		            <span ng-if="bsi.species_20 == null">未检测到病原菌</span>
			      </div>
			      <h4>检测范围(20种)：</h4>
			      <table class="table table-main">
			        <tbody>
			        <tr>
		              <td>屎肠球菌</td>
			          <td>粪肠球菌</td>
			          <td>肺炎链球菌</td>
			          <td>艰难梭状芽胞杆菌</td>
			        </tr>
			        <tr>
			          <td>人葡萄球菌</td>
			          <td>表皮葡萄球菌</td>
			          <td>溶血性葡萄球菌</td>
			          <td>金黄色葡萄球菌</td>
			        </tr>
			        <tr>
			          <td>结核分枝杆菌</td>
			          <td>大肠埃希菌</td>
			          <td>鲍曼不动杆菌</td>
			          <td>肺炎克雷伯氏菌</td>
			        </tr>
			        <tr>
			          <td>阴沟肠杆菌</td>
			          <td>粘质沙雷氏菌</td>
			          <td>奇异变形杆菌</td>
			          <td>洋葱伯克霍尔德菌</td>
			          
			        </tr>
			        <tr>
			          <td>产气肠杆菌</td>
			          <td>铜绿假单胞菌</td>
			          <td>肠炎沙门氏菌</td>
			          <td>嗜麦芽窄食单胞菌</td>
			         </tr>
			         </tbody>
			       </table>
			      <p>*此检测结果为阴性仅代表未检测到上述20种细菌，不代表样本中不存在本检测范围之外的其他细菌。</p>
			      <h4>检测方法：<span>基于16S rDNA高通量测序方法</span></h4>
			    </div>
			    <div role="tabpanel" class="tab-pane analy-tab"  ng-class="{active : tab == 'analy'}" id="analy-report" aria-labelledby="analy-tab">
			      <h4>1. 检测结果：</h4>
			      <div class="test-info">
                    <span ng-repeat="species20 in bsi.species_20">
                      <span ng-if="species20.species_zh == '未知' || species20.species_zh == null">{{species20.species}}<br></span>
                      <span ng-if="species20.species_zh != '未知' && species20.species_zh != null">{{species20.species_zh}}<br></span>
                    </span>
                    <span ng-if="bsi.species_20 == null">未检测到病原菌</span>
                  </div>
			      <table class="table table-20species">
			        <tbody>
			         <tr>
			           <td class="title" rowspan="3">革兰氏阳性</td>
		               <td ng-if="havestrain.indexOf('屎肠球菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		                              屎肠球菌
                       </td>
		               <td ng-if="havestrain.indexOf('屎肠球菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                               屎肠球菌
		               </td>
		               <td ng-if="havestrain.indexOf('粪肠球菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		                                  粪肠球菌
                       </td>
		               <td ng-if="havestrain.indexOf('粪肠球菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
			                          粪肠球菌
			           </td>
		               <td ng-if="havestrain.indexOf('肺炎链球菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               肺炎链球菌
                       </td>
		               <td ng-if="havestrain.indexOf('肺炎链球菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                                     肺炎链球菌
		               </td>
		               <td ng-if="havestrain.indexOf('艰难梭状芽胞杆菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               艰难梭状芽胞杆菌
                       </td>
		               <td ng-if="havestrain.indexOf('艰难梭状芽胞杆菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                               艰难梭状芽胞杆菌
		               </td>
			         </tr>
			         <tr>
		               <td ng-if="havestrain.indexOf('人葡萄球菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               人葡萄球菌
                       </td>
		               <td ng-if="havestrain.indexOf('人葡萄球菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                              人葡萄球菌
		               </td>
		               <td ng-if="havestrain.indexOf('表皮葡萄球菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               表皮葡萄球菌
                       </td>
		               <td ng-if="havestrain.indexOf('表皮葡萄球菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
			                           表皮葡萄球菌
			           </td>
		               <td ng-if="havestrain.indexOf('溶血性葡萄球菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               溶血性葡萄球菌
                       </td>
		               <td ng-if="havestrain.indexOf('溶血性葡萄球菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
			                          溶血性葡萄球菌
			           </td>
		               <td ng-if="havestrain.indexOf('金黄色葡萄球菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               金黄色葡萄球菌
                       </td>
		               <td ng-if="havestrain.indexOf('金黄色葡萄球菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                              金黄色葡萄球菌
		               </td>
			         </tr>
			         <tr>
		               <td ng-if="havestrain.indexOf('结核分枝杆菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               结核分枝杆菌
                       </td>
		               <td ng-if="havestrain.indexOf('结核分枝杆菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                             结核分枝杆菌
		               </td>
		               <td></td>
		               <td></td>
		               <td></td>
			         </tr>
			         <tr>
			           <td class="title" rowspan="3">革兰氏阴性</td>
		               <td ng-if="havestrain.indexOf('大肠埃希菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
                                       大肠埃希菌
                       </td>
		               <td ng-if="havestrain.indexOf('大肠埃希菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                               大肠埃希菌
		               </td>
		               <td ng-if="havestrain.indexOf('鲍曼不动杆菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               鲍曼不动杆菌
                       </td>
		               <td ng-if="havestrain.indexOf('鲍曼不动杆菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
			                          鲍曼不动杆菌
			           </td>
		               <td ng-if="havestrain.indexOf('肺炎克雷伯氏菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               肺炎克雷伯氏菌
                       </td>
		               <td ng-if="havestrain.indexOf('肺炎克雷伯氏菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
			                           肺炎克雷伯氏菌
			           </td>
		               <td ng-if="havestrain.indexOf('嗜麦芽窄食单胞菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               嗜麦芽窄食单胞菌
                       </td>
		               <td ng-if="havestrain.indexOf('嗜麦芽窄食单胞菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                              嗜麦芽窄食单胞菌
		               </td>
			         </tr>
			         <tr>
		               <td ng-if="havestrain.indexOf('阴沟肠杆菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               阴沟肠杆菌
                       </td>
		               <td ng-if="havestrain.indexOf('阴沟肠杆菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                              阴沟肠杆菌
		               </td>
		               <td ng-if="havestrain.indexOf('粘质沙雷氏菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		                粘质沙雷氏菌
                       </td>
		               <td ng-if="havestrain.indexOf('粘质沙雷氏菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
			                           粘质沙雷氏菌
			           </td>
		               <td ng-if="havestrain.indexOf('奇异变形杆菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               奇异变形杆菌
                       </td>
		               <td ng-if="havestrain.indexOf('奇异变形杆菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                               奇异变形杆菌
		               </td>
		               <td ng-if="havestrain.indexOf('洋葱伯克霍尔德菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               洋葱伯克霍尔德菌
                       </td>
		               <td ng-if="havestrain.indexOf('洋葱伯克霍尔德菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                               洋葱伯克霍尔德菌
		               </td>
			         </tr>
			         <tr>
		               <td ng-if="havestrain.indexOf('产气肠杆菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               产气肠杆菌
                       </td>
		               <td ng-if="havestrain.indexOf('产气肠杆菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
		                              产气肠杆菌
		               </td>
		               <td ng-if="havestrain.indexOf('铜绿假单胞菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               铜绿假单胞菌
                       </td>
		               <td ng-if="havestrain.indexOf('铜绿假单胞菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
			                           铜绿假单胞菌
			           </td>
		               <td ng-if="havestrain.indexOf('肠炎沙门氏菌') > -1" class="hasit"><i class="fa fa-circle" aria-hidden="true"></i>
		               肠炎沙门氏菌
                       </td>
		               <td ng-if="havestrain.indexOf('肠炎沙门氏菌') < 0"><i class="fa fa-circle-o" aria-hidden="true"></i>
			                           肠炎沙门氏菌
			           </td>
			           <td></td>
			         </tr>
			        </tbody>
			      </table>
			      <table id="test-seq-table" class="table seq-table" ng-if="bsi.species_20 != null">
			        <thead>
			          <tr>
			            <th style="max-width: 119px;">菌名</th>
			            <th width="85">序列比重</th>
			            <th width="85">检出区域</th>
			            <th width="380">序列 (5'-3')</th>
			          </tr>
			        </thead>
			        <tbody ng-repeat="species20 in bsi.species_20">
		               <tr ng-init="srowspan = 1">
		                 <td class="title" rowspan="{{species20.seq2 | getBsiRowSpan:species20.seq3:species20.seq4}}">
		                 	<span ng-if="species20.species_zh == '未知' || species20.species_zh == null">{{species20.species}}<br></span>
	              			<span ng-if="species20.species_zh != '未知' && species20.species_zh != null">{{species20.species_zh}}<br></span>
		                   <br>
		                   <svg width="200" height="20">
		                     <g>
		                       <rect x="0" y="0" width="50" height="20" fill="{{species20.site1 | getResultByCompare:'0,0':'#8c8c8c':'#fff'}}"
			                     style="stroke:#8c8c8c;fill-opacity:0.4;stroke-opacity:0.9"></rect>
			                   <text x="8" y="15">V1-V2</text>
			                   <rect x="50" y="0" width="50" height="20" fill="{{species20.site2 | getResultByCompare:'0,0':'#dcdcdc':'#fff'}}"
		                         style="stroke:#8c8c8c;fill-opacity:0.4;stroke-opacity:0.9"></rect>
		                       <text x="58" y="15">V3-V4</text>
		                       <rect x="100" y="0" width="50" height="20" fill="{{species20.site3 | getResultByCompare:'0,0':'#323232':'#fff'}}"
		                         style="stroke:#8c8c8c;fill-opacity:0.4;stroke-opacity:0.9"></rect>
			                   <text x="108" y="15">V5-V6</text>
			                   <rect x="150" y="0" width="50" height="20" fill="{{species20.site4 | getResultByCompare:'0,0':'#c8c8c8':'#fff'}}"
		                         style="stroke:#8c8c8c;fill-opacity:0.4;stroke-opacity:0.9"></rect>
			                   <text x="158" y="15">V7-V8</text>
		                     </g>
		                   </svg>
		                 </td>
		                 <td class="sub-title" rowspan="{{species20.seq2 | getBsiRowSpan:species20.seq3:species20.seq4}}">{{species20.seq_proportion}}</td>
		                 <td>{{species20.seq1_name}}</td>
		                 <td>
		                   <div class="seq">
		                   {{species20.seq1_no}}<br>
		                   {{species20.seq1}}
		                   </div>
		                 </td>
		               </tr>
	                   <tr ng-if="species20.seq2 != null && species20.seq2 != ''">
	                     <td>{{species20.seq2_name}}</td>
	                     <td>
	                       <div class="seq">{{species20.seq2_no}}<br>{{species20.seq2}}</div>
	                     </td>
	                   </tr>
	                   <tr ng-if="species20.seq3 != null && species20.seq3 != ''">
	                     <td>{{species20.seq3_name}}</td>
	                     <td>
	                       <div class="seq">{{species20.seq3_no}}<br>{{species20.seq3}}</div>
	                     </td>
	                   </tr>
	                   <tr ng-if="species20.seq4 != null && species20.seq4 != ''">
	                     <td>{{species20.seq4_name}}</td>
	                     <td>
	                       <div class="seq">{{species20.seq4_no}}<br>{{species20.seq4}}</div>
	                     </td>
	                   </tr>
			        </tbody>
			      </table>
			      <p>序列比重：唯一识别序列数>10条时为检出<br>
			                        快速序列验证及比对(NCBI Blast): <br>
			        <a target="_blank" href="http://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastn&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome">http://blast.ncbi.nlm.nih.gov/Blast.cgi?PROGRAM=blastn&PAGE_TYPE=BlastSearch&LINK_LOC=blasthome</a>
			      </p>
			      <h4>2. 统计信息</h4>
			      <table class="table table-count">
			        <tr>
			          <td>序列总数</td>
			          <td>平均质量</td>
			          <td>平均GC含量</td>
			          <td>human序列总数</td>
			          <td>16S序列总数</td>
			        </tr>
			        <tr>
			          <td>{{bsi.totalReads}}</td>
			          <td>{{bsi.avgQuality}}</td>
			          <td>{{bsi.avgGCContent}}</td>
			          <td>{{bsi.human_reads}}</td>
			          <td>{{bsi.reads_16s}}</td>
			        </tr>
			      </table>
			    </div>
			  </div>
			  <div class="data-content-footer">
			    <ul>
			      <li><h4>更多信息： </h4></li>
			      <li>
			        <label>标签：</label>
			        <span id="data-batch">{{data.batch}}</span>
			      </li>
			      <li>
			        <label>生成时间：</label>
			        <span>
		              {{bsi.createDate | date:'yyyyMMdd'}}
		            </span>
			      </li>
			      <li>
			        <label>原始数据：</label>
		            <span style="width:150px; max-width: 150px;">
			            <p ng-repeat="data in bsi.data">{{data.fileName}}&nbsp;&nbsp;</p>
			        </span>
			        <a href="javascript:void(0)">（查看原始数据信息）</a>
			      </li>
			    </ul>
			  </div>
		  </div>
		  <div class="report-opera col-sm-2">
		    <%-- <div class="nav-pub">
		      <div >
		        <c:if test="{{pageList.datas.size()>0}">
		          <input id="total-page-hide" value="{{pageList.page.totalPage}}" type="hidden" >
		          <a class="prev-btn" ng-href="javascript:$.report.detail.prev({{pageList.page.currentPage}})"><i class="fa fa-chevron-circle-left"></i>上一份</a>
		          <a class="next-btn" ng-href="javascript:$.report.detail.next({{pageList.page.currentPage}})">下一份<i class="fa fa-chevron-circle-right"></i></a>
		        </c:if>
		      </div>
		    </div>
		    <div id="report-pagination" class="report-pagination"></div> --%>
		    <div class="report-btn-group pull-right">
		       <a class="btn" ng-class="{hide : tab == 'analy'}" id="print-patient-a" ng-href="<%=request.getContextPath()%>/report/printBSIReport?projectId={{bsi.projectId }}&dataKey={{bsi.dataKey }}&appId={{bsi.appId }}&templateType=print_patient" target="_blank"><i class="fa fa-print"></i>打印</a>
		       <a class="btn" ng-class="{hide : tab == 'patient'}" id="print-analy-a" ng-href="<%=request.getContextPath()%>/report/printBSIReport?projectId={{bsi.projectId }}&dataKey={{bsi.dataKey }}&appId={{bsi.appId }}&templateType=print_analy" target="_blank"><i class="fa fa-print"></i>打印</a>
		       <a class="btn" href="javascript:void(0)"><i class="fa fa-cloud-download"></i>导出</a>
			   <a class="btn" href="javascript:void(0)"><i class="fa fa-group"></i>发布</a>
			   <a class="btn" href="javascript:void(0)"><i class="fa fa-share-square-o"></i>分享</a>
			   <a class="btn" href="javascript:void(0)"><i class="fa fa-inbox"></i>存档</a>
		    </div>
		  </div>
		  <input id="appid-hide" type="hidden" value="{{bsi.appId}}">
		  <input id="datakey-hide" type="hidden" value="{{bsi.dataKey}}">
		</div>
	</div>
</div>
<%-- <script src="<%=request.getContextPath()%>/js/bsi_report.js?version=1.2"></script> --%>