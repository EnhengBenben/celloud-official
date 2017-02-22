<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="pro-body mreport col-xs-10">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>GDD报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{gdd.appName}}
        </p>
        <p ng-repeat="data in gdd.data"> 文件名称：
            {{data.fileName}}({{data.dataKey}})
        </p>
        <div class="btn-group">
	        <a style="padding:0 10px;" class="btn -low" target="_blank" ng-href="<%=request.getContextPath()%>/report/printGDDReport?projectId={{gdd.projectId}}&dataKey={{gdd.dataKey}}&appId={{gdd.appId}}"><i class="fa fa-print"></i>打印GDD报告</a>
        </div>
      </div>
      <div>
        <section class="m-box">
	        <h2><i class="i-report1"></i>数据统计</h2>
			<div class="m-boxCon" id="_table">
				<p>按照测序数据质量分析报告如下：（分析日期：<span id="cmp_RunDate">{{gdd.runDate}}</span>）</p>
		    	<table class="table table-main">
					<thead>
						<tr>
							<th>基本信息</th>
							<th width="50%">说明</th>
						</tr>	
					</thead>
					<tbody>
						<tr>
							<td>共获得有效片段：<span id="cmp_allFragment">{{gdd.allFragment}}</span></td>
							<td>10000&nbsp;条以上序列认为合格</td>
						</tr>
						<tr>
							<td>平均质量：<span id="cmp_avgQuality">{{gdd.avgQuality}}</span></td>
							<td>质量值30以上为可用数据</td>
						</tr>
						<tr>
							<td>平均GC含量：<span id="cmp_avgGC">{{gdd.avgGCContent}}</span></td>
							<td>40%~50%&nbsp;均属正常</td>
						</tr>
						<tr>
							<td>可用片段：<span id="cmp_useFragment">{{gdd.usableFragment}}</span></td>
							<td>高质量数据，碱基质量大于30</td>
						</tr>
						<tr>
							<td>待检基因：<span id="cmp_undetectGene">{{gdd.noDetectedGene}}</span></td>
							<td>待检基因数目</td>
						</tr>
						<tr>
							<td>检测基因数：<span id="cmp_detectGene">{{gdd.detectedGene}}</span></td>
							<td>检测到的基因数目</td>
						</tr>
						<tr>
							<td>平均测序深度：<span id="cmp_avgCoverage">{{gdd.avgCoverage}}</span></td>
							<td>100&nbsp;倍以上数据</td>
						</tr>
					</tbody>
				</table>
			</div>
	    </section>
	    <section class="m-box">
	        <h2><i class="i-edit"></i>报告</h2>
			<div class="m-boxCon" id="_report" style="display: inline-block;width: 100%">
				<table class="table table-main" style="width:95%;height:100px;">
			   		<tr>
	   					<td ng-if="gdd.cmpGeneResult=='' || gdd.cmpGeneResult==null || gdd.cmpGeneResult=='null'">未检测到相关突变位点</td>
		   				<td ng-if="gdd.cmpGeneResult.length >= 2 && gdd.cmpGeneResult!='' && gdd.cmpGeneResult!=null && gdd.cmpGeneResult!='null'" width="46%" valign="top" height="100%">
							<table class="table table-main" id="snp_table1">
								<thead>
									<tr>
										<th class="mwidth_Gene">基因</th>
										<th>已知突变位点数</th>
										<th>测序深度</th>
									</tr>	
								</thead>
								<tbody>
									<tr ng-repeat="gene in gdd.cmpGeneResult">
										<td ng-if="$index <= gdd.cmpGeneResult.length/2-1">
											<span ng-if="gene.sequencingDepth < 50" style='background-color:#feaa20'>{{gene.geneName}}</span>
											<span ng-if="gene.sequencingDepth >= 50">{{gene.geneName}}</span>
										</td>
										<td ng-if="$index <= gdd.cmpGeneResult.length/2-1">{{gene.knownMSNum}}</td>
										<td ng-if="$index <= gdd.cmpGeneResult.length/2-1">{{gene.sequencingDepth}}</td>
									</tr>
								</tbody>
							</table>
						</td>
						<td ng-if="gdd.cmpGeneResult.length >= 2 && gdd.cmpGeneResult!='' && gdd.cmpGeneResult!=null && gdd.cmpGeneResult!='null'" width="46%" valign="top" height="100%">
							<table class="table table-main" id="snp_table2">
								<thead>
									<tr>
										<th class="mwidth_Gene">基因</th>
										<th>已知突变位点数</th>
										<th>测序深度</th>
									</tr>	
								</thead>
								<tbody>
									<tr ng-repeat="gene in gdd.cmpGeneResult">
										<td ng-if="$index >= gdd.cmpGeneResult.length/2-1">
											<span ng-if="gene.sequencingDepth < 50" style='background-color:#feaa20'>{{gene.geneName}}</span>
											<span ng-if="gene.sequencingDepth >= 50">{{gene.geneName}}</span>
										</td>
										<td ng-if="$index >= gdd.cmpGeneResult.length/2-1">{{gene.knownMSNum}}</td>
										<td ng-if="$index >= gdd.cmpGeneResult.length/2-1">{{gene.sequencingDepth}}</td>
									</tr>
								</tbody>
							</table>
						</td>
		   				<td ng-if="gdd.cmpGeneResult.length < 2 && gdd.cmpGeneResult!='' && gdd.cmpGeneResult!=null && gdd.cmpGeneResult!='null'" width="47%" valign="top" height="100%">
							<table class="table table-main" id="snp_table1">
								<thead>
									<tr>
										<th class="mwidth_Gene">基因</th>
										<th>已知突变位点数 </th>
										<th>测序深度</th>
									</tr>	
								</thead>
								<tbody>
									<tr ng-repeat="gene in gdd.cmpGeneResult">
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
         <section class="m-box">
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
							<td>{{gdd.basicStatistics1.Filename}}</td>
						</tr>
						<tr>
							<td>File type</td>
							<td>{{gdd.basicStatistics1.FileType}}</td>
						</tr>
						<tr>
							<td>Encoding</td>
							<td>{{gdd.basicStatistics1.Encoding}}</td>
						</tr>
						<tr>
							<td>Total Sequences</td>
							<td>{{gdd.basicStatistics1.TotalSeq}}</td>
						</tr>
						<tr>
							<td>Filtered Sequences</td>
							<td>{{gdd.basicStatistics1.FilteredSeq}}</td>
						</tr>
						<tr>
							<td>Sequence length</td>
							<td>{{gdd.basicStatistics1.SeqLength}}</td>
						</tr>
						<tr>
							<td>%GC</td>
							<td>{{gdd.basicStatistics1.gc}}</td>
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
							<td>{{gdd.basicStatistics2.Filename}}</td>
						</tr>
						<tr>
							<td>File type</td>
							<td>{{gdd.basicStatistics2.FileType}}</td>
						</tr>
						<tr>
							<td>Encoding</td>
							<td>{{gdd.basicStatistics2.Encoding}}</td>
						</tr>
						<tr>
							<td>Total Sequences</td>
							<td>{{gdd.basicStatistics2.TotalSeq}}</td>
						</tr>
						<tr>
							<td>Filtered Sequences</td>
							<td>{{gdd.basicStatistics2.FilteredSeq}}</td>
						</tr>
						<tr>
							<td>Sequence length</td>
							<td>{{gdd.basicStatistics2.SeqLength}}</td>
						</tr>
						<tr>
							<td>%GC</td>
							<td>{{gdd.basicStatistics2.gc}}</td>
						</tr>
					</tbody>
				</table>
				<table style="width:100%;">
			      <tr>
			    	<td style="width:50%;">
			    		<img style="width:100%;" ng-if="gdd.qualityPath1.indexOf('Tools')<0" src="{{uploadPath}}{{gdd.userId}}/{{gdd.appId}}/{{gdd.dataKey}}{{gdd.qualityPath1}}">
			    		<img style="width:100%;" ng-if="gdd.qualityPath1.indexOf('Tools')>-1" src="{{gdd.qualityPath1}}">
			    	</td>
			    	<td>
			    		<img style="width:100%;" ng-if="gdd.qualityPath2.indexOf('Tools')<0" src="{{uploadPath}}{{gdd.userId}}/{{gdd.appId}}/{{gdd.dataKey}}/{{gdd.qualityPath2}}">
			    		<img style="width:100%;"ng-if="gdd.qualityPath2.indexOf('Tools')>-1" src="{{gdd.qualityPath2}}">
			    	</td>
			      </tr>
			      <tr>
			    	<td>
			    		<img style="width:100%;" alt="" ng-if="gdd.seqContentPath1.indexOf('Tools')<0" src="{{uploadPath}}{{gdd.userId}}/{{gdd.appId}}/{{gdd.dataKey}}{{gdd.seqContentPath1}}">
			    		<img style="width:100%;" alt="" ng-if="gdd.seqContentPath1.indexOf('Tools')>-1" src="{{gdd.seqContentPath1}}">
			    	</td>
			    	<td>
			    		<img style="width:100%;" alt="" ng-if="gdd.seqContentPath2.indexOf('Tools')<0" src="{{uploadPath}}{{gdd.userId}}/{{gdd.appId}}/{{gdd.dataKey}}{{gdd.seqContentPath2}}">
			    		<img style="width:100%;" alt="" ng-if="gdd.seqContentPath2.indexOf('Tools')>-1" src="{{gdd.seqContentPath2}}">
			    	</td>
			      </tr>
			    </table>
			</div>
         </section>
	   </div>
     </div>
 </div>
 <ng-include src="'pages/partial/_partial_reportoperate.jsp'"></ng-include>