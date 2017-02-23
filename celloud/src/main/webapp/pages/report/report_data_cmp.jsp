<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>{{cmp.appName}}报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{cmp.appName}}
        </p>
        <p ng-repeat="data in cmp.data"> 文件名称：
            {{data.fileName}}({{data.dataKey}})
        </p>
        <div class="btn-groups">
        	<a style="padding:0 10px;" class="btn -low" target="_blank" ng-href="<%=request.getContextPath()%>/report/printMoreCMPReport?projectId={{cmp.projectId}}&dataKey={{cmp.dataKey}}&appId={{cmp.appId}}"><i class="fa fa-print"></i>打印科研报告</a>
			<a style="padding:0 10px;" class="btn -middle" target="_blank" ng-href="<%=request.getContextPath()%>/report/printLessCMPReport?projectId={{cmp.projectId}}&dataKey={{cmp.dataKey}}&appId={{cmp.appId}}"><i class="fa fa-print"></i>打印临床报告</a>
        </div>
      </div>
      <div>
        <section  class="m-box">
	        <h2><i class="i-report1"></i>数据统计</h2>
			<div class="m-boxCon" id="_table">
				<p>按照测序数据质量分析报告如下：（分析日期：<span id="cmp_RunDate">{{cmp.runDate}}</span>）</p>
		    	<table class="table table-main">
					<thead>
						<tr>
							<th>基本信息</th>
							<th width="50%">说明</th>
						</tr>	
					</thead>
					<tbody>
						<tr>
							<td>共获得有效片段：<span id="cmp_allFragment">{{cmp.allFragment}}</span></td>
							<td>10000&nbsp;条以上序列认为合格</td>
						</tr>
						<tr>
							<td>平均质量：<span id="cmp_avgQuality">{{cmp.avgQuality}}</span></td>
							<td>质量值30以上为可用数据</td>
						</tr>
						<tr>
							<td>平均GC含量：<span id="cmp_avgGC">{{cmp.avgGCContent}}</span></td>
							<td>40%~50%&nbsp;均属正常</td>
						</tr>
						<tr>
							<td>可用片段：<span id="cmp_useFragment">{{cmp.usableFragment}}</span></td>
							<td>高质量数据，碱基质量大于30</td>
						</tr>
						<tr>
							<td>待检基因：<span id="cmp_undetectGene">{{cmp.noDetectedGene}}</span></td>
							<td>待检基因数目</td>
						</tr>
						<tr>
							<td>检测基因数：<span id="cmp_detectGene">{{cmp.detectedGene}}</span></td>
							<td>检测到的基因数目</td>
						</tr>
						<tr>
							<td>平均测序深度：<span id="cmp_avgCoverage">{{cmp.avgCoverage}}</span></td>
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
	   					<td ng-if="cmp.cmpGeneResult=='' || cmp.cmpGeneResult==null || cmp.cmpGeneResult=='null'">未检测到相关突变位点</td>
		   				<td ng-if="cmp.cmpGeneResult.length >= 2 && cmp.cmpGeneResult!='' && cmp.cmpGeneResult!=null && cmp.cmpGeneResult!='null'" width="46%" valign="top" height="100%">
							<table class="table table-main" id="snp_table1">
								<thead>
									<tr>
										<th class="mwidth_Gene">基因</th>
										<th>已知突变位点数</th>
										<th>测序深度</th>
									</tr>	
								</thead>
								<tbody>
									<tr ng-repeat="gene in cmp.cmpGeneResult">
										<td ng-if="$index <= cmp.cmpGeneResult.length/2-1">
											<span ng-if="gene.sequencingDepth < 50" style='background-color:#feaa20'>{{gene.geneName}}</span>
											<span ng-if="gene.sequencingDepth >= 50">{{gene.geneName}}</span>
										</td>
										<td ng-if="$index <= cmp.cmpGeneResult.length/2-1">{{gene.knownMSNum}}</td>
										<td ng-if="$index <= cmp.cmpGeneResult.length/2-1">{{gene.sequencingDepth}}</td>
									</tr>
								</tbody>
							</table>
						</td>
						<td ng-if="cmp.cmpGeneResult.length >= 2 && cmp.cmpGeneResult!='' && cmp.cmpGeneResult!=null && cmp.cmpGeneResult!='null'" width="46%" valign="top" height="100%">
							<table class="table table-main" id="snp_table2">
								<thead>
									<tr>
										<th class="mwidth_Gene">基因</th>
										<th>已知突变位点数</th>
										<th>测序深度</th>
									</tr>	
								</thead>
								<tbody>
									<tr ng-repeat="gene in cmp.cmpGeneResult">
										<td ng-if="$index >= cmp.cmpGeneResult.length/2">
											<span ng-if="gene.sequencingDepth < 50" style='background-color:#feaa20'>{{gene.geneName}}</span>
											<span ng-if="gene.sequencingDepth >= 50">{{gene.geneName}}</span>
										</td>
										<td ng-if="$index >= cmp.cmpGeneResult.length/2">{{gene.knownMSNum}}</td>
										<td ng-if="$index >= cmp.cmpGeneResult.length/2">{{gene.sequencingDepth}}</td>
									</tr>
								</tbody>
							</table>
						</td>
		   				<td ng-if="cmp.cmpGeneResult.length < 2 && cmp.cmpGeneResult!='' && cmp.cmpGeneResult!=null && cmp.cmpGeneResult!='null'" width="47%" valign="top" height="100%">
							<table class="table table-main" id="snp_table1">
								<thead>
									<tr>
										<th class="mwidth_Gene">基因</th>
										<th>已知突变位点数 </th>
										<th>测序深度</th>
									</tr>	
								</thead>
								<tbody>
									<tr ng-repeat="gene in cmp.cmpGeneResult">
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
				<div class="h2">Basic Statistics</div>
				<table class="table table-main">
					<thead>
						<tr>
							<th>#Measure</th>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Filename</td>
							<td>{{cmp.basicStatistics1.Filename}}</td>
						</tr>
						<tr>
							<td>File type</td>
							<td>{{cmp.basicStatistics1.FileType}}</td>
						</tr>
						<tr>
							<td>Encoding</td>
							<td>{{cmp.basicStatistics1.Encoding}}</td>
						</tr>
						<tr>
							<td>Total Sequences</td>
							<td>{{cmp.basicStatistics1.TotalSeq}}</td>
						</tr>
						<tr>
							<td>Filtered Sequences</td>
							<td>{{cmp.basicStatistics1.FilteredSeq}}</td>
						</tr>
						<tr>
							<td>Sequence length</td>
							<td>{{cmp.basicStatistics1.SeqLength}}</td>
						</tr>
						<tr>
							<td>%GC</td>
							<td>{{cmp.basicStatistics1.gc}}</td>
						</tr>
					</tbody>
				</table>
				<div class="h2">Basic Statistics</div>
				<table class="table table-main">
					<thead>
						<tr>
							<th>#Measure</th>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Filename</td>
							<td>{{cmp.basicStatistics2.Filename}}</td>
						</tr>
						<tr>
							<td>File type</td>
							<td>{{cmp.basicStatistics2.FileType}}</td>
						</tr>
						<tr>
							<td>Encoding</td>
							<td>{{cmp.basicStatistics2.Encoding}}</td>
						</tr>
						<tr>
							<td>Total Sequences</td>
							<td>{{cmp.basicStatistics2.TotalSeq}}</td>
						</tr>
						<tr>
							<td>Filtered Sequences</td>
							<td>{{cmp.basicStatistics2.FilteredSeq}}</td>
						</tr>
						<tr>
							<td>Sequence length</td>
							<td>{{cmp.basicStatistics2.SeqLength}}</td>
						</tr>
						<tr>
							<td>%GC</td>
							<td>{{cmp.basicStatistics2.gc}}</td>
						</tr>
					</tbody>
				</table>
				<table style="width:100%;">
			      <tr>
			    	<td style="width:50%;">
			    		<img style="width:100%;" ng-if="cmp.qualityPath1.indexOf('Tools')<0" src="{{uploadPath}}{{cmp.userId}}/{{cmp.appId}}/{{cmp.dataKey}}{{cmp.qualityPath1}}">
			    		<img style="width:100%;" ng-if="cmp.qualityPath1.indexOf('Tools')>-1" src="{{cmp.qualityPath1}}">
			    	</td>
			    	<td>
			    		<img style="width:100%;" ng-if="cmp.qualityPath2.indexOf('Tools')<0" src="{{uploadPath}}{{cmp.userId}}/{{cmp.appId}}/{{cmp.dataKey}}/{{cmp.qualityPath2}}">
			    		<img style="width:100%;"ng-if="cmp.qualityPath2.indexOf('Tools')>-1" src="{{cmp.qualityPath2}}">
			    	</td>
			      </tr>
			      <tr>
			    	<td>
			    		<img style="width:100%;" alt="" ng-if="cmp.seqContentPath1.indexOf('Tools')<0" src="{{uploadPath}}{{cmp.userId}}/{{cmp.appId}}/{{cmp.dataKey}}{{cmp.seqContentPath1}}">
			    		<img style="width:100%;" alt="" ng-if="cmp.seqContentPath1.indexOf('Tools')>-1" src="{{cmp.seqContentPath1}}">
			    	</td>
			    	<td>
			    		<img style="width:100%;" alt="" ng-if="cmp.seqContentPath2.indexOf('Tools')<0" src="{{uploadPath}}{{cmp.userId}}/{{cmp.appId}}/{{cmp.dataKey}}{{cmp.seqContentPath2}}">
			    		<img style="width:100%;" alt="" ng-if="cmp.seqContentPath2.indexOf('Tools')>-1" src="{{cmp.seqContentPath2}}">
			    	</td>
			      </tr>
			    </table>
			</div>
         </section>
	   </div>
     </div>
 </div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>