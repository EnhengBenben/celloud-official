<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>{{accuSeqα2.appName}}报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{accuSeqα2.appName}}
        </p>
        <p ng-repeat="data in accuSeqα2.data"> 文件名称：
            {{data.fileName}}({{data.dataKey}})
        </p>
        <div class="btn-groups">
        	<a style="padding:0 10px;" class="btn -low" target="_blank" href="<%=request.getContextPath()%>/report/printMoreAccuSeqα2Report?projectId={{accuSeqα2.projectId}}&dataKey={{accuSeqα2.dataKey}}&appId={{accuSeqα2.appId}}"><i class="fa fa-print"></i>打印科研报告</a>
			<a style="padding:0 10px;" class="btn -middle" target="_blank" href="<%=request.getContextPath()%>/report/printLessAccuSeqα2Report?projectId={{accuSeqα2.projectId}}&dataKey={{accuSeqα2.dataKey}}&appId={{accuSeqα2.appId}}"><i class="fa fa-print"></i>打印临床报告</a>
        </div>
      </div>
      <div>
        <section  class="m-box">
	        <h2><i class="i-report1"></i>数据统计</h2>
			<div class="m-boxCon" id="_table">
				<p>按照测序数据质量分析报告如下：（分析日期：<span id="accuSeqα2_RunDate">{{accuSeqα2.dataBReport.runDate}}</span>）</p>
		    	<table class="table table-main">
					<thead>
						<tr>
							<th>基本信息</th>
							<th width="50%">说明</th>
						</tr>	
					</thead>
					<tbody>
						<tr>
							<td>共获得有效片段：<span id="accuSeqα2_allFragment">{{accuSeqα2.dataBReport.allFragment}}</span></td>
							<td>10000&nbsp;条以上序列认为合格</td>
						</tr>
						<tr>
							<td>平均质量：<span id="accuSeqα2_avgQuality">{{accuSeqα2.dataBReport.avgQuality}}</span></td>
							<td>质量值30以上为可用数据</td>
						</tr>
						<tr>
							<td>平均GC含量：<span id="accuSeqα2_avgGC">{{accuSeqα2.dataBReport.avgGCContent}}</span></td>
							<td>40%~50%&nbsp;均属正常</td>
						</tr>
						<tr>
							<td>可用片段：<span id="accuSeqα2_useFragment">{{accuSeqα2.dataBReport.usableFragment}}</span></td>
							<td>高质量数据，碱基质量大于30</td>
						</tr>
						<tr>
							<td>待检基因：<span id="accuSeqα2_undetectGene">{{accuSeqα2.dataBReport.noDetectedGene}}</span></td>
							<td>待检基因数目</td>
						</tr>
						<tr>
							<td>检测基因数：<span id="accuSeqα2_detectGene">{{accuSeqα2.dataBReport.detectedGene}}</span></td>
							<td>检测到的基因数目</td>
						</tr>
						<tr>
							<td>平均测序深度：<span id="accuSeqα2_avgCoverage">{{accuSeqα2.dataBReport.avgCoverage}}</span></td>
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
	   					<td ng-if="accuSeqα2.accuSeqα2GeneResult=='' || accuSeqα2.accuSeqα2GeneResult==null || accuSeqα2.accuSeqα2GeneResult=='null'">未检测到相关突变位点</td>
		   				<td ng-if="accuSeqα2.accuSeqα2GeneResult.length >= 2 && accuSeqα2.accuSeqα2GeneResult!='' && accuSeqα2.accuSeqα2GeneResult!=null && accuSeqα2.accuSeqα2GeneResult!='null'" width="46%" valign="top" height="100%">
							<table class="table table-main" id="snp_table1">
								<thead>
									<tr>
										<th class="mwidth_Gene">基因</th>
										<th>已知突变位点数</th>
										<th>测序深度</th>
									</tr>	
								</thead>
								<tbody>
									<tr ng-repeat="gene in accuSeqα2.dataBReport.cmpGeneResult">
										<td ng-if="$index <= accuSeqα2.dataBReport.cmpGeneResult.length/2-1">
											<span ng-if="gene.sequencingDepth < 50" style='background-color:#feaa20'>{{gene.geneName}}</span>
											<span ng-if="gene.sequencingDepth >= 50">{{gene.geneName}}</span>
										</td>
										<td ng-if="$index <= accuSeqα2.dataBReport.cmpGeneResult.length/2-1">{{gene.knownMSNum}}</td>
										<td ng-if="$index <= accuSeqα2.dataBReport.cmpGeneResult.length/2-1">{{gene.sequencingDepth}}</td>
									</tr>
								</tbody>
							</table>
						</td>
						<td ng-if="accuSeqα2.dataBReport.cmpGeneResult.length >= 2 &&accuSeqα2.dataBReport.cmpGeneResult!='' && accuSeqα2.dataBReport.cmpGeneResult!=null && accuSeqα2.dataBReport.cmpGeneResult!='null'" width="46%" valign="top" height="100%">
							<table class="table table-main" id="snp_table2">
								<thead>
									<tr>
										<th class="mwidth_Gene">基因</th>
										<th>已知突变位点数</th>
										<th>测序深度</th>
									</tr>	
								</thead>
								<tbody>
									<tr ng-repeat="gene in accuSeqα2.dataBReport.cmpGeneResult">
										<td ng-if="$index >= accuSeqα2.dataBReport.cmpGeneResult.length/2">
											<span ng-if="gene.sequencingDepth < 50" style='background-color:#feaa20'>{{gene.geneName}}</span>
											<span ng-if="gene.sequencingDepth >= 50">{{gene.geneName}}</span>
										</td>
										<td ng-if="$index >= accuSeqα2.dataBReport.cmpGeneResult.length/2">{{gene.knownMSNum}}</td>
										<td ng-if="$index >= accuSeqα2.dataBReport.cmpGeneResult.length/2">{{gene.sequencingDepth}}</td>
									</tr>
								</tbody>
							</table>
						</td>
		   				<td ng-if="accuSeqα2.dataBReport.cmpGeneResult.length < 2 && accuSeqα2.dataBReport.cmpGeneResult!='' && accuSeqα2.dataBReport.cmpGeneResult!=null && accuSeqα2.dataBReport.cmpGeneResult!='null'" width="47%" valign="top" height="100%">
							<table class="table table-main" id="snp_table1">
								<thead>
									<tr>
										<th class="mwidth_Gene">基因</th>
										<th>已知突变位点数 </th>
										<th>测序深度</th>
									</tr>	
								</thead>
								<tbody>
									<tr ng-repeat="gene in accuSeqα2.dataBReport.cmpGeneResult">
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
							<td>{{accuSeqα2.dataAReport.basicStatistics1.Filename}}</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics2.Filename}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics1.Filename}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics2.Filename}}</td>
						</tr>
						<tr>
							<td>文件类型</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics1.FileType}}</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics2.FileType}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics1.FileType}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics2.FileType}}</td>
						</tr>
						<tr>
							<td>编码</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics1.Encoding}}</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics2.Encoding}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics1.Encoding}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics2.Encoding}}</td>
						</tr>
						<tr>
							<td>总序列数</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics1.TotalSeq}}</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics2.TotalSeq}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics1.TotalSeq}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics2.TotalSeq}}</td>
						</tr>
						<tr>
							<td>筛选序列</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics1.FilteredSeq}}</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics2.FilteredSeq}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics1.FilteredSeq}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics2.FilteredSeq}}</td>
						</tr>
						<tr>
							<td>序列长度</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics1.SeqLength}}</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics2.SeqLength}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics1.SeqLength}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics2.SeqLength}}</td>
						</tr>
						<tr>
							<td>平均GC含量</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics1.gc}}</td>
							<td>{{accuSeqα2.dataAReport.basicStatistics2.gc}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics1.gc}}</td>
							<td>{{accuSeqα2.dataBReport.basicStatistics2.gc}}</td>
						</tr>
					</tbody>
				</table>
				<table style="width:100%;">
			      <tr>
			    	<td style="width:50%;">
			    		<img style="width:100%;" ng-if="accuSeqα2.dataAReport.qualityPath1.indexOf('Tools')<0" src="{{uploadPath}}{{accuSeqα2.userId}}/{{accuSeqα2.appId}}/{{accuSeqα2.dataKey}}{{accuSeqα2.dataAReport.qualityPath1}}">
			    		<img style="width:100%;" ng-if="accuSeqα2.dataAReport.qualityPath1.indexOf('Tools')>-1" src="{{accuSeqα2.dataAReport.qualityPath1}}">
			    	</td>
			    	<td>
			    		<img style="width:100%;" ng-if="accuSeqα2.dataAReport.qualityPath2.indexOf('Tools')<0" src="{{uploadPath}}{{accuSeqα2.userId}}/{{accuSeqα2.appId}}/{{accuSeqα2.dataKey}}/{{accuSeqα2.dataAReport.qualityPath2}}">
			    		<img style="width:100%;"ng-if="accuSeqα2.dataAReport.qualityPath2.indexOf('Tools')>-1" src="{{accuSeqα2.dataAReport.qualityPath2}}">
			    	</td>
			      </tr>
			      <tr>
			    	<td>
			    		<img style="width:100%;" alt="" ng-if="accuSeqα2.dataAReport.seqContentPath1.indexOf('Tools')<0" src="{{uploadPath}}{{accuSeqα2.userId}}/{{accuSeqα2.appId}}/{{accuSeqα2.dataKey}}{{accuSeqα2.dataAReport.seqContentPath1}}">
			    		<img style="width:100%;" alt="" ng-if="accuSeqα2.dataAReport.seqContentPath1.indexOf('Tools')>-1" src="{{accuSeqα2.dataAReport.seqContentPath1}}">
			    	</td>
			    	<td>
			    		<img style="width:100%;" alt="" ng-if="accuSeqα2.dataAReport.seqContentPath2.indexOf('Tools')<0" src="{{uploadPath}}{{accuSeqα2.userId}}/{{accuSeqα2.appId}}/{{accuSeqα2.dataKey}}{{accuSeqα2.dataAReport.seqContentPath2}}">
			    		<img style="width:100%;" alt="" ng-if="accuSeqα2.dataAReport.seqContentPath2.indexOf('Tools')>-1" src="{{accuSeqα2.dataAReport.seqContentPath2}}">
			    	</td>
			      </tr>
			      <tr>
                    <td style="width:50%;">
                        <img style="width:100%;" ng-if="accuSeqα2.dataBReport.qualityPath1.indexOf('Tools')<0" src="{{uploadPath}}{{accuSeqα2.userId}}/{{accuSeqα2.appId}}/{{accuSeqα2.dataKey}}{{accuSeqα2.dataBReport.qualityPath1}}">
                        <img style="width:100%;" ng-if="accuSeqα2.dataBReport.qualityPath1.indexOf('Tools')>-1" src="{{accuSeqα2.dataBReport.qualityPath1}}">
                    </td>
                    <td>
                        <img style="width:100%;" ng-if="accuSeqα2.dataBReport.qualityPath2.indexOf('Tools')<0" src="{{uploadPath}}{{accuSeqα2.userId}}/{{accuSeqα2.appId}}/{{accuSeqα2.dataKey}}/{{accuSeqα2.dataBReport.qualityPath2}}">
                        <img style="width:100%;"ng-if="accuSeqα2.dataBReport.qualityPath2.indexOf('Tools')>-1" src="{{accuSeqα2.dataBReport.qualityPath2}}">
                    </td>
                  </tr>
                  <tr>
                    <td>
                        <img style="width:100%;" alt="" ng-if="accuSeqα2.dataBReport.seqContentPath1.indexOf('Tools')<0" src="{{uploadPath}}{{accuSeqα2.userId}}/{{accuSeqα2.appId}}/{{accuSeqα2.dataKey}}{{accuSeqα2.dataBReport.seqContentPath1}}">
                        <img style="width:100%;" alt="" ng-if="accuSeqα2.dataBReport.seqContentPath1.indexOf('Tools')>-1" src="{{accuSeqα2.dataBReport.seqContentPath1}}">
                    </td>
                    <td>
                        <img style="width:100%;" alt="" ng-if="accuSeqα2.dataBReport.seqContentPath2.indexOf('Tools')<0" src="{{uploadPath}}{{accuSeqα2.userId}}/{{accuSeqα2.appId}}/{{accuSeqα2.dataKey}}{{accuSeqα2.dataBReport.seqContentPath2}}">
                        <img style="width:100%;" alt="" ng-if="accuSeqα2.dataBReport.seqContentPath2.indexOf('Tools')>-1" src="{{accuSeqα2.dataBReport.seqContentPath2}}">
                    </td>
                  </tr>
			    </table>
			</div>
         </section>
	   </div>
     </div>
 </div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>