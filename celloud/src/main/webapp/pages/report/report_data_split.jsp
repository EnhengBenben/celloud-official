<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="pro-body mreport">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li><a style="color: #a0a0a0" href="javascript:void(0);" ng-click="goBack()">报告管理</a></li>
      <li>{{split.appName}}报告</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p> 项目名称：
            {{project.projectName}}
        </p>
        <p> 应用名称：
        	{{split.appName}}
        </p>
        <p ng-repeat="data in split.data">
        	{{$index == 0 ? '文件名称：' : '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'}}
            {{data.fileName}}({{data.dataKey}})
        </p>
        <div class="btn-groups">
        	<a class="btn -low" ng-click="printSplit(split.projectId,split.dataKey,split.appId)"><i class="fa fa-print"></i>打印报告</a>
            <a class="btn -middle" ng-href="${pageContext.request.contextPath }/report/downByName?path={{split.userId}}/{{split.appId}}/{{split.dataKey}}/result/split_reads.tar.gz"><i class="fa fa-cloud-download"></i>下载全部</a>
        </div>
      </div>
      <div>
      	<section class="m-box">
	        <h2><i class="i-report1"></i>数据统计</h2>
	        <div class="m-boxCon">
	          <div style="display:flex;min-height:260px;">
	              <div class="quota-left">
	                <div class="quota-inner">
	                  <h5 class="quota-title">源数据统计</h5>
	                  <dl class="quota-usage">
	                    <dt>平均质量</dt>
	                    <dd><span class="usage">{{split.avgQuality}}</span></dd>
	                    <dt>平均GC含量</dt>
	                    <dd><span class="usage">{{split.avgGCContent}}</span></dd>
	                    <dt>序列总数</dt>
	                    <dd><span class="usage">{{split.totalReads}}</span></dd>
	                    <dt>有效序列</dt>
	                    <dd><span class="usage">{{split.usefulReads}}</span></dd>
	                    <dt>未知序列</dt>
	                    <dd><span class="usage">{{split.unknownReads}}</span></dd>
	                  </dl>
	                </div>
	              </div>
	              <div class="quota-right">
	                <div class="quota-inner">
	                  <h5 class="quota-title">结果样本统计</h5>
	                  <dl class="quota-usage">
	                    <dt>样本数量</dt>
	                    <dd><span class="usage">{{split.sampleNum}}</span></dd>
	                    <dt>&lt;5000条序列的样本数量</dt>
	                    <dd><span class="usage">{{split.less5000}}</span></dd>
	                    <dt>&gt;20000条序列的样本数量</dt>
	                    <dd><span class="usage">{{split.more2000}}</span></dd>
	                    <dt>样本序列数平均值</dt>
	                    <dd><span class="usage">{{split.avgSampleSeq}}</span></dd>
	                    <dt>样本序列数最小值</dt>
	                    <dd><span class="usage">{{split.minSampleSeq}}</span></dd>
	                    <dt>样本序列数最大值</dt>
	                    <dd><span class="usage">{{split.maxSampleSeq}}</span></dd>
	                    <dt>方差</dt>
	                    <dd><span class="usage">{{split.variance}}</span></dd>
	                    <dt>标准差</dt>
	                    <dd><span class="usage">{{split.stdev}}</span></dd>
	                  </dl>
	                </div>
	              </div>
	            </div>
	          </div>
	    </section>
	    <section class="m-box">
	        <h2><i class="i-edit"></i>结果样本详细</h2>
            <div class="m-boxCon">
                <table class="table table-main">
                    <thead>
                        <tr>
                            <th>数据名称</th>   
                            <th>序列数量</th>
                            <th>平均质量</th>
                            <th>平均GC含量</th>
                        </tr>   
                    </thead>
                    <tbody>
                        <tr ng-if="split.resultList == null"><td colspan="4">未分析出结果</td></tr>
                        <tr ng-repeat="data in split.resultList" ng-if="!(data.name=='total' ||data.name=='useful'||data.name=='unknown') && split.resultList != null">
                          <td>
                                <a class="link" ng-href="/report/down?path={{split.userId}}/{{split.appId}}/{{split.dataKey}}/result/split/{{data.name}}.tar.gz">{{data.name}}</a>  
                          </td>
                          <td>{{data.number}}</td>
                          <td>{{data.avgQuality}}</td>
                          <td>{{data.avgGCContent}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="m-tips">
                <i class="i-tips"></i>注：点击文件名即可下载；已保存的数据可到数据管理页面查看
            </div>
	     </section>
         <section class="m-box">
	         <h2><i class="i-edit"></i>序列质量分析（见QC结果）</h2>
            <div class="m-boxCon" id="_report" style="display: inline-block;width: 90%">
                <div ng-if="split.basicStatistics1 == null || split.basicStatistics1 == ''">无质量分析结果</div>
                <div ng-if="split.basicStatistics1 != null" class="h2">Basic Statistics</div>
                <table ng-if="split.basicStatistics1 != null" class="table table-main">
                  <thead>
                      <tr>
                          <th>#Measure</th>
                          <th colspan="2">Value</th>
                      </tr>
                  </thead>
                  <tbody>
                      <tr>
                          <td>Filename</td>
                          <td>{{split.basicStatistics1.Filename}}</td>
                          <td>{{split.basicStatistics2.Filename}}</td>
                      </tr>
                      <tr>
                          <td>File type</td>
                          <td>{{split.basicStatistics1.FileType}}</td>
                          <td>{{split.basicStatistics2.FileType}}</td>
                      </tr>
                      <tr>
                          <td>Encoding</td>
                          <td>{{split.basicStatistics1.Encoding}}</td>
                          <td>{{split.basicStatistics2.Encoding}}</td>
                      </tr>
                      <tr>
                          <td>Total Sequences</td>
                          <td>{{split.basicStatistics1.TotalSeq}}</td>
                          <td>{{split.basicStatistics2.TotalSeq}}</td>
                      </tr>
                      <tr>
                          <td>Filtered Sequences</td>
                          <td>{{split.basicStatistics1.FilteredSeq}}</td>
                          <td>{{split.basicStatistics2.FilteredSeq}}</td>
                      </tr>
                      <tr>
                          <td>Sequence length</td>
                          <td>{{split.basicStatistics1.SeqLength}}</td>
                          <td>{{split.basicStatistics2.SeqLength}}</td>
                      </tr>
                      <tr>
                          <td>%GC</td>
                          <td>{{split.basicStatistics1.gc}}</td>
                          <td>{{split.basicStatistics2.gc}}</td>
                      </tr>
                  </tbody>
                </table>
                <table style="width:100%;">
                  <tr>
                    <td style="width:50%;"><img style="width:100%;" ng-src="{{uploadPath}}/{{split.userId}}/{{split.appId}}/{{split.dataKey}}{{split.qualityPath1}}"></td>
                    <td><img style="width:100%;" ng-src="{{uploadPath}}/{{split.userId}}/{{split.appId}}/{{split.dataKey}}{{split.qualityPath2}}"></td>
                  </tr>
                  <tr>
                    <td><img style="width:100%;" alt="" ng-src="{{uploadPath}}/{{split.userId}}/{{split.appId}}/{{split.dataKey}}{{split.seqContentPath1}}"></td>
                    <td><img style="width:100%;" alt="" ng-src="{{uploadPath}}/{{split.userId}}/{{split.appId}}/{{split.dataKey}}{{split.seqContentPath2}}"></td>
                  </tr>
                </table>
            </div>
         </section>
         <section class="m-box">
         	<!--Celloud数据参数同比分析-->
       		<div class="bg-analysis">
	            <div class="m-box">
	                <h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
	                <div class="m-boxCon">
	                    <div class="row" id="sourceCharDiv" style="height: 400px;width:100%;">
	                    </div>
	                    <div class="row" id="sampleCharDiv" style="height: 400px;width:100%;">
	                    </div>
	                </div>
	            </div>
        	</div>
         </section>
	   </div>
     </div>
 </div>