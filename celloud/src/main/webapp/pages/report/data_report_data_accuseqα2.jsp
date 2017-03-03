<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="pro-body mreport" ng-controller="commonReport">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>{{accuSeq.appName}}报告</li>
    </ol>
    <div class="content">
        <div class="content-left col-xs-10">
	      <div class="content-header">
	        <p> 项目名称：
	            {{project.projectName}}
	        </p>
	        <p> 应用名称：
	        	{{accuSeq.appName}}
	        </p>
	        <p ng-repeat="data in accuSeq.data"> 文件名称：
	            {{data.fileName}}({{data.dataKey}})
	        </p>
	        <div class="btn-groups">
	        	<a style="padding:0 10px;" class="btn -low" target="_blank" ng-href="<%=request.getContextPath()%>/report/printMoreAccuSeqα2Report?projectId={{accuSeq.projectId}}&dataKey={{accuSeq.dataKey}}&appId={{accuSeq.appId}}"><i class="fa fa-print"></i>打印报告</a>
	        </div>
	      </div>
	      <div>
	        <section  class="m-box">
		        <h2><i class="i-report1"></i>数据统计</h2>
				<div class="m-boxCon" id="_table">
					<p>按照测序数据质量分析报告如下：（分析日期：<span id="accuSeqα2_RunDate">{{accuSeq.dataBReport.runDate}}</span>）</p>
			    	<table class="table table-main">
						<thead>
							<tr>
								<th>基本信息</th>
								<th width="50%">说明</th>
							</tr>	
						</thead>
						<tbody>
							<tr>
								<td>共获得有效片段：<span id="accuSeqα2_allFragment">{{accuSeq.dataBReport.allFragment}}</span></td>
								<td>10000&nbsp;条以上序列认为合格</td>
							</tr>
							<tr>
								<td>平均质量：<span id="accuSeqα2_avgQuality">{{accuSeq.dataBReport.avgQuality}}</span></td>
								<td>质量值30以上为可用数据</td>
							</tr>
							<tr>
								<td>平均GC含量：<span id="accuSeqα2_avgGC">{{accuSeq.dataBReport.avgGCContent}}</span></td>
								<td>40%~50%&nbsp;均属正常</td>
							</tr>
							<tr>
								<td>可用片段：<span id="accuSeqα2_useFragment">{{accuSeq.dataBReport.usableFragment}}</span></td>
								<td>高质量数据，碱基质量大于30</td>
							</tr>
							<tr>
								<td>待检基因：<span id="accuSeqα2_undetectGene">{{accuSeq.dataBReport.noDetectedGene}}</span></td>
								<td>待检基因数目</td>
							</tr>
							<tr>
								<td>检测基因数：<span id="accuSeqα2_detectGene">{{accuSeq.dataBReport.detectedGene}}</span></td>
								<td>检测到的基因数目</td>
							</tr>
							<tr>
								<td>平均测序深度：<span id="accuSeqα2_avgCoverage">{{accuSeq.dataBReport.avgCoverage}}</span></td>
								<td>100&nbsp;倍以上数据</td>
							</tr>
						</tbody>
					</table>
				</div>
		    </section>
		    <section  class="m-box">
		        <h2><i class="i-edit"></i>报告</h2>
				<div class="m-boxCon" id="_report" style="display: inline-block;width: 100%">
					<table class="table table-main no-hover" style="width:95%;height:100px;">
				   		<tr>
		   					<td ng-if="accuSeq.accuSeqα2GeneResult=='' || accuSeq.accuSeqα2GeneResult==null || accuSeq.accuSeqα2GeneResult=='null'">未检测到相关突变位点</td>
			   				<td ng-if="accuSeq.accuSeqα2GeneResult.length >= 2 && accuSeq.accuSeqα2GeneResult!='' && accuSeq.accuSeqα2GeneResult!=null && accuSeq.accuSeqα2GeneResult!='null'" width="46%" valign="top" height="100%">
								<table class="table table-main" id="snp_table1">
									<thead>
										<tr>
											<th class="mwidth_Gene">基因</th>
											<th>已知突变位点数</th>
											<th>测序深度</th>
										</tr>	
									</thead>
									<tbody>
										<tr ng-repeat="gene in accuSeq.dataBReport.cmpGeneResult">
											<td ng-if="$index <= accuSeq.dataBReport.cmpGeneResult.length/2-1">
												<span ng-if="gene.sequencingDepth < 50" style='background-color:#feaa20'>{{gene.geneName}}</span>
												<span ng-if="gene.sequencingDepth >= 50">{{gene.geneName}}</span>
											</td>
											<td ng-if="$index <= accuSeq.dataBReport.cmpGeneResult.length/2-1">{{gene.knownMSNum}}</td>
											<td ng-if="$index <= accuSeq.dataBReport.cmpGeneResult.length/2-1">{{gene.sequencingDepth}}</td>
										</tr>
									</tbody>
								</table>
							</td>
							<td ng-if="accuSeq.dataBReport.cmpGeneResult.length >= 2 &&accuSeq.dataBReport.cmpGeneResult!='' && accuSeq.dataBReport.cmpGeneResult!=null && accuSeq.dataBReport.cmpGeneResult!='null'" width="46%" valign="top" height="100%">
								<table class="table table-main" id="snp_table2">
									<thead>
										<tr>
											<th class="mwidth_Gene">基因</th>
											<th>已知突变位点数</th>
											<th>测序深度</th>
										</tr>	
									</thead>
									<tbody>
										<tr ng-repeat="gene in accuSeq.dataBReport.cmpGeneResult">
											<td ng-if="$index >= accuSeq.dataBReport.cmpGeneResult.length/2">
												<span ng-if="gene.sequencingDepth < 50" style='background-color:#feaa20'>{{gene.geneName}}</span>
												<span ng-if="gene.sequencingDepth >= 50">{{gene.geneName}}</span>
											</td>
											<td ng-if="$index >= accuSeq.dataBReport.cmpGeneResult.length/2">{{gene.knownMSNum}}</td>
											<td ng-if="$index >= accuSeq.dataBReport.cmpGeneResult.length/2">{{gene.sequencingDepth}}</td>
										</tr>
									</tbody>
								</table>
							</td>
			   				<td ng-if="accuSeq.dataBReport.cmpGeneResult.length < 2 && accuSeq.dataBReport.cmpGeneResult!='' && accuSeq.dataBReport.cmpGeneResult!=null && accuSeq.dataBReport.cmpGeneResult!='null'" width="47%" valign="top" height="100%">
								<table class="table table-main" id="snp_table1">
									<thead>
										<tr>
											<th class="mwidth_Gene">基因</th>
											<th>已知突变位点数 </th>
											<th>测序深度</th>
										</tr>	
									</thead>
									<tbody>
										<tr ng-repeat="gene in accuSeq.dataBReport.cmpGeneResult">
											<td>
												<span ng-if="gene.sequencingDepth < 50" style='background-color:#feaa20'>{{gene.geneName}}</span>
												<span ng-if="gene.sequencingDepth >= 50">{{gene.geneName}}</span>
											</td>
											<td>{{gene.knownMSNum}}</td>
											<td>{{gene.sequencingDepth}}</td>
										</tr>
									</tbody>
								</table>
							</td>
				   		</tr>
					</table>
				</div>
				<div class="m-tips">
					<i class="i-tips"></i>注释： 已知突变位点，在样本中发现且有文献支持的突变位点。
				</div>
		     </section>
	         <section  class="m-box">
		         <h2><i class="i-edit"></i>序列质量分析（见QC结果）</h2>
				<div class="m-boxCon" id="_report" style="display: inline-block;width: 90%">
					<div class="h2">基本统计</div>
					<table class="table table-main">
						<thead>
							<tr>
								<th>类型</th>
								<th>血液数据1</th>
								<th>血液数据2</th>
								<th>切片数据1</th>
								<th>切片数据2</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>文件名</td>
								<td>{{accuSeq.dataAReport.basicStatistics1.Filename}}</td>
								<td>{{accuSeq.dataAReport.basicStatistics2.Filename}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics1.Filename}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics2.Filename}}</td>
							</tr>
							<tr>
								<td>文件类型</td>
								<td>{{accuSeq.dataAReport.basicStatistics1.FileType}}</td>
								<td>{{accuSeq.dataAReport.basicStatistics2.FileType}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics1.FileType}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics2.FileType}}</td>
							</tr>
							<tr>
								<td>编码</td>
								<td>{{accuSeq.dataAReport.basicStatistics1.Encoding}}</td>
								<td>{{accuSeq.dataAReport.basicStatistics2.Encoding}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics1.Encoding}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics2.Encoding}}</td>
							</tr>
							<tr>
								<td>总序列数</td>
								<td>{{accuSeq.dataAReport.basicStatistics1.TotalSeq}}</td>
								<td>{{accuSeq.dataAReport.basicStatistics2.TotalSeq}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics1.TotalSeq}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics2.TotalSeq}}</td>
							</tr>
							<tr>
								<td>筛选序列</td>
								<td>{{accuSeq.dataAReport.basicStatistics1.FilteredSeq}}</td>
								<td>{{accuSeq.dataAReport.basicStatistics2.FilteredSeq}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics1.FilteredSeq}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics2.FilteredSeq}}</td>
							</tr>
							<tr>
								<td>序列长度</td>
								<td>{{accuSeq.dataAReport.basicStatistics1.SeqLength}}</td>
								<td>{{accuSeq.dataAReport.basicStatistics2.SeqLength}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics1.SeqLength}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics2.SeqLength}}</td>
							</tr>
							<tr>
								<td>平均GC含量</td>
								<td>{{accuSeq.dataAReport.basicStatistics1.gc}}</td>
								<td>{{accuSeq.dataAReport.basicStatistics2.gc}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics1.gc}}</td>
								<td>{{accuSeq.dataBReport.basicStatistics2.gc}}</td>
							</tr>
						</tbody>
					</table>
					<table style="width:100%;">
				      <tr>
				    	<td style="width:50%;">
				    		<img style="width:100%;" ng-src="{{uploadPath}}{{accuSeq.userId}}/{{accuSeq.appId}}/{{accuSeq.dataKey}}/A{{accuSeq.dataAReport.qualityPath1}}">
				    	</td>
				    	<td>
				    		<img style="width:100%;" ng-src="{{uploadPath}}{{accuSeq.userId}}/{{accuSeq.appId}}/{{accuSeq.dataKey}}/A{{accuSeq.dataAReport.qualityPath2}}">
				    	</td>
				      </tr>
				      <tr>
				    	<td>
				    		<img style="width:100%;" ng-src="{{uploadPath}}{{accuSeq.userId}}/{{accuSeq.appId}}/{{accuSeq.dataKey}}/A{{accuSeq.dataAReport.seqContentPath1}}">
				    	</td>
				    	<td>
				    		<img style="width:100%;" ng-src="{{uploadPath}}{{accuSeq.userId}}/{{accuSeq.appId}}/{{accuSeq.dataKey}}/A{{accuSeq.dataAReport.seqContentPath2}}">
				    	</td>
				      </tr>
				      <tr>
	                    <td style="width:50%;">
	                        <img style="width:100%;" ng-src="{{uploadPath}}{{accuSeq.userId}}/{{accuSeq.appId}}/{{accuSeq.dataKey}}/B{{accuSeq.dataBReport.qualityPath1}}">
	                    </td>
	                    <td>
	                        <img style="width:100%;" ng-src="{{uploadPath}}{{accuSeq.userId}}/{{accuSeq.appId}}/{{accuSeq.dataKey}}/B{{accuSeq.dataBReport.qualityPath2}}">
	                    </td>
	                  </tr>
	                  <tr>
	                    <td>
	                        <img style="width:100%;" ng-src="{{uploadPath}}{{accuSeq.userId}}/{{accuSeq.appId}}/{{accuSeq.dataKey}}/B{{accuSeq.dataBReport.seqContentPath1}}">
	                    </td>
	                    <td>
	                        <img style="width:100%;" ng-src="{{uploadPath}}{{accuSeq.userId}}/{{accuSeq.appId}}/{{accuSeq.dataKey}}/B{{accuSeq.dataBReport.seqContentPath2}}">
	                    </td>
	                  </tr>
				    </table>
				</div>
	         </section>
		   </div>
		 </div>
		 <div class="content-right col-xs-2">
            <ng-include src="'pages/partial/_partial_datareportoperate.jsp'"></ng-include>
          </div>
     </div>
 </div>