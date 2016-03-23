<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<input type="hidden" id="splitIdHidden" value="${split.id}">
<div>
    <div class="m-file">
        <dl class="dl-horizontal datareport-title">
          <dt>项目名称：</dt>
          <dd>${project.projectName}</dd>
          <dt>应用名称：</dt>
          <dd>${split.appName}</dd>
          <dt>文件名称：</dt>
          <c:forEach items="${split.data}" var="data">
            <dd class="force-break">${data.fileName}(${data.dataKey})</dd>
          </c:forEach>
        </dl>
        <div class="toolbar">
            <a class="btn btn-celloud-success btn-flat" href="javascript:printSplit(${split.projectId },${split.dataKey },${split.appId })"><i class="fa fa-print"></i>打印报告</a>
            <a class="btn btn-warning btn-flat" href="${toolsPath }Procedure!miRNADownload?userId=${split.userId }/${split.appId }/${split.dataKey }/result/split_reads.tar.gz"><i class="fa fa-cloud-download"></i> 下载全部</a>
        </div>
    </div>
    <div>
      <div class="m-box">
        <h2><i class="i-report1"></i>数据统计</h2>
        <div class="m-boxCon">
          <div style="display:flex;">
              <div class="quota-left">
                <div class="quota-inner">
                  <h5 class="quota-title">源数据统计</h5>
                  <dl class="quota-usage">
                    <dt>平均质量</dt>
                    <dd><span class="usage">${split.avgQuality }</span></dd>
                    <dt>平均GC含量</dt>
                    <dd><span class="usage">${split.avgGCContent }</span></dd>
                    <dt>序列总数</dt>
                    <dd><span class="usage">${split.totalReads }</span></dd>
                    <dt>有效序列</dt>
                    <dd><span class="usage">${split.usefulReads }</span></dd>
                    <dt>未知序列</dt>
                    <dd><span class="usage">${split.unknownReads }</span></dd>
                  </dl>
                </div>
              </div>
              <div class="quota-right">
                <div class="quota-inner">
                  <h5 class="quota-title">结果样本统计</h5>
                  <dl class="quota-usage">
                    <dt>样本数量</dt>
                    <dd><span class="usage">${split.sampleNum }</span></dd>
                    <dt>&lt;5000条序列的样本数量</dt>
                    <dd><span class="usage">${split.less5000 }</span></dd>
                    <dt>&gt;20000条序列的样本数量</dt>
                    <dd><span class="usage">${split.more2000 }</span></dd>
                    <dt>样本序列数平均值</dt>
                    <dd><span class="usage">${split.avgSampleSeq }</span></dd>
                    <dt>样本序列数最小值</dt>
                    <dd><span class="usage">${split.minSampleSeq }</span></dd>
                    <dt>样本序列数最大值</dt>
                    <dd><span class="usage">${split.maxSampleSeq }</span></dd>
                    <dt>方差</dt>
                    <dd><span class="usage">${split.variance }</span></dd>
                    <dt>标准差</dt>
                    <dd><span class="usage">${split.stdev }</span></dd>
                  </dl>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--检测结果-->
        <div class="m-box">
            <h2><i class="i-edit"></i>结果样本详细</h2>
            <div class="m-boxCon">
                <table class="table table-striped-green table-text-center table-padding0">
                    <thead>
                        <tr>
                            <th>数据名称</th>   
                            <th>序列数量</th>
                            <th>平均质量</th>
                            <th>平均GC含量</th>
                        </tr>   
                    </thead>
                    <tbody>
                      <c:choose>
                        <c:when test="${split.resultList==null}"><tr><td colspan="4">未分析出结果</td></tr></c:when>
                        <c:otherwise>
                          <c:forEach items="${split.resultList}" var="data">
                              <c:if test="${!(data.name=='total' ||data.name=='useful'||data.name=='unknown')}">
                                <tr>
                                  <td>
                                        <a class="link" href="${toolsPath }Procedure!miRNADownload?userId=${split.userId }/${split.appId }/${split.dataKey }/result/split/${data.name }.tar.gz">${data.name }</a>  
                                  </td>
                                  <td>${data.number }</td>
                                  <td>${data.avgQuality }</td>
                                  <td>${data.avgGCContent }</td>
                                </tr>
                              </c:if>
                          </c:forEach>
                        </c:otherwise>
                      </c:choose>
                    </tbody>
                </table>
            </div>
            <div class="m-tips">
                <i class="i-tips"></i>注：点击文件名即可下载；已保存的数据可到数据管理页面查看
            </div>
        </div>
        <!--检测结果-->
        <div class="m-box">
            <h2><i class="i-edit"></i>序列质量分析（见QC结果）</h2>
            <div class="m-boxCon" id="_report" style="display: inline-block;width: 90%">
              <c:choose>
                <c:when test="${empty split.basicStatistics1 }">
                    无质量分析结果
                </c:when>
                <c:otherwise>
                  <div class="h2">Basic Statistics</div>
                  <table class="table table-bordered table-condensed">
                    <thead>
                        <tr>
                            <th>#Measure</th>
                            <th colspan="2">Value</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Filename</td>
                            <td>${split.basicStatistics1.Filename }</td>
                            <td>${split.basicStatistics2.Filename }</td>
                        </tr>
                        <tr>
                            <td>File type</td>
                            <td>${split.basicStatistics1.FileType }</td>
                            <td>${split.basicStatistics2.FileType }</td>
                        </tr>
                        <tr>
                            <td>Encoding</td>
                            <td>${split.basicStatistics1.Encoding }</td>
                            <td>${split.basicStatistics2.Encoding }</td>
                        </tr>
                        <tr>
                            <td>Total Sequences</td>
                            <td>${split.basicStatistics1.TotalSeq }</td>
                            <td>${split.basicStatistics2.TotalSeq }</td>
                        </tr>
                        <tr>
                            <td>Filtered Sequences</td>
                            <td>${split.basicStatistics1.FilteredSeq }</td>
                            <td>${split.basicStatistics2.FilteredSeq }</td>
                        </tr>
                        <tr>
                            <td>Sequence length</td>
                            <td>${split.basicStatistics1.SeqLength }</td>
                            <td>${split.basicStatistics2.SeqLength }</td>
                        </tr>
                        <tr>
                            <td>%GC</td>
                            <td>${split.basicStatistics1.gc }</td>
                            <td>${split.basicStatistics2.gc }</td>
                        </tr>
                    </tbody>
                  </table>
                  <table style="width:100%;">
                    <tr>
                      <td style="width:50%;"><img style="width:100%;" src="${uploadPath }/${split.userId }/${split.appId }/${split.dataKey }${split.qualityPath1 }"></td>
                      <td><img style="width:100%;" src="${uploadPath }/${split.userId }/${split.appId }/${split.dataKey }${split.qualityPath2 }"></td>
                    </tr>
                    <tr>
                      <td><img style="width:100%;" alt="" src="${uploadPath }/${split.userId }/${split.appId }/${split.dataKey }${split.seqContentPath1 }"></td>
                      <td><img style="width:100%;" alt="" src="${uploadPath }/${split.userId }/${split.appId }/${split.dataKey }${split.seqContentPath2 }"></td>
                    </tr>
                  </table>
                </c:otherwise>
              </c:choose>
            </div>
        </div>
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
    </div>
</div>
<script>
$(function() {
    $(window).manhuatoTop({
        showHeight : 100,
        speed : 1000
    });
    $.get("count/splitCompare",{"id":$("#splitIdHidden").val()},function(data){
        var totalSource = JSON.stringify(data.totalSource);
        var totalSample = JSON.stringify(data.totalSample);
        var thisSource = JSON.stringify(data.thisSource);
        var thisSample = JSON.stringify(data.thisSample);
        drawScatter("sourceCharDiv",eval(totalSource),eval(thisSource),'Split源数据同比图','平均质量','序列总数');
        drawScatter("sampleCharDiv",eval(totalSample),eval(thisSample),'Split分离结果数据同比图','平均质量','序列总数');
    });
});
</script>