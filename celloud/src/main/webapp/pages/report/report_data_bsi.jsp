<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div>
    <div class="m-file">
        数据编号：<span class="file-name">${ bsi.dataKey}</span><br>
        文件名称：
        <span class="file-name force-break">
        <c:forEach items="${bsi.data}" var="data">
            ${data.fileName}(${data.dataKey})&nbsp;&nbsp;&nbsp;
        </c:forEach>
        </span><br>
        检测结果： ${bsi.conclusion }
        <div class="toolbar">
            <a class="btn btn-celloud-success btn-flat" target="_blank" href="<%=request.getContextPath()%>/report/printBSIReport?projectId=${bsi.projectId }&dataKey=${bsi.dataKey }&appId=${bsi.appId }&templateType=print_patient"><i class="fa fa-print"></i>打印患者报告</a>
            <a class="btn btn-celloud-success btn-flat" target="_blank" href="<%=request.getContextPath()%>/report/printBSIReport?projectId=${bsi.projectId }&dataKey=${bsi.dataKey }&appId=${bsi.appId }&templateType=print_analy"><i class="fa fa-print"></i>打印分析报告</a>
        </div>
    </div>
    <div>
        <!--报告图示一-->
        <div class="m-box">
            <h2><i class="i-report1"></i>数据统计</h2>
            <div class="m-boxCon">
                <table class="table table-bordered table-condensed">
                    <thead>
                        <tr><th>序列总数</th><th>平均质量</th><th>平均GC含量</th></tr>
                    </thead>
                    <tbody style="font-size:12px">
                        <tr>
                            <td>${bsi.totalReads }</td>
                            <td>${bsi.avgQuality }</td>
                            <td>${bsi.avgGCContent }</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <!--报告图示一-->
        <div class="m-box">
            <h2><i class="i-edit"></i>Reads Distribution</h2>
            <div class="m-boxCon">
                <table style="width:90%;">
                  <tr>
                    <td id="reads-distribution-parent" style="width:49%;">
                      <c:choose>
                        <c:when test="${empty bsi.readsDistributionInfo}">
                          <c:if test="${not empty bsi.readsDistribution}">
		                    <img src="<c:if test="${!bsi.readsDistribution.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.readsDistribution }" style="width:100%;">
		                  </c:if>
                        </c:when>
                        <c:otherwise>
                          <div id="reads-distribution-char" style="width:100%;height:330px;">${bsiCharList.readsDistributionInfo }</div>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td id="family-distribution-parent">
                      <c:choose>
                        <c:when test="${empty bsi.familyDistributionInfo}">
		                  <c:if test="${not empty bsi.familyDistribution}">
		                    <img src="<c:if test="${!bsi.familyDistribution.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.familyDistribution }" style="width:100%;">
		                  </c:if>
                        </c:when>
                        <c:otherwise>
                          <div id="family-distribution-char" style="width:100%;height:330px;">${bsiCharList.familyDistributionInfo }</div>
                        </c:otherwise>
                      </c:choose>
                    </td>
                  </tr>
                </table>
            </div>
        </div>
        <!--报告图示一-->
        <div id="genus-distribution-parent" class="m-box">
            <h2><i class="i-edit"></i>Genus Distribution</h2>
            <div class="m-boxCon">
              <c:choose>
                <c:when test="${empty bsi.genusDistributionInfo}">
		            <c:if test="${not empty bsi.genusDistribution}">
		              <img src="<c:if test="${!bsi.genusDistribution.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.genusDistribution }" style="width:90%;">
		            </c:if>
                </c:when>
                <c:otherwise>
                  <div id="genus-distribution-char" style="width:100%;height:330px;">${bsiCharList.genusDistributionInfo }</div>
                </c:otherwise>
              </c:choose>
            </div>
        </div>
        <!--检测结果-->
        <div class="m-box">
            <h2><i class="i-edit"></i>报告</h2>
            <div class="m-boxCon">
              <table class="table table-striped-green table-text-center table-padding0" id="snp_table1">
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
                 <tbody style="font-size:12px">
                   <c:if test="${fn:length(bsi.summaryTable)>0}">
                     <c:forEach items="${bsi.summaryTable }" var="summary" varStatus="s">
                       <tr>
                         <td style="text-align: left;line-height:1em">${summary.Species }</td>
                         <td>${summary.Genus }</td>
                         <td>${summary.GI }</td>
                         <td>${summary.Coverage }</td>
                         <td>${summary.Reads_hit }</td>
                         <td>${summary.Reads_num }</td>
                         <td>${summary.Reads_Ratio }</td>
                         <td>${summary.avgCoverage }</td>
                       </tr>
                     </c:forEach>
                   </c:if>
                 </tbody>
              </table>
            </div>
        </div>
        <!--报告图示一-->
        <div class="m-box">
            <h2><i class="i-edit"></i>报告</h2>
            <div class="m-boxCon">
		      <c:if test="${not empty bsi.pngPath.top1png }">
		        <img src="<c:if test="${!bsi.pngPath.top1png.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.pngPath.top1png }" style="width:90%;">
		      </c:if>
		      <c:if test="${not empty bsi.pngPath.top2png }">
		        <img src="<c:if test="${!bsi.pngPath.top2png.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.pngPath.top2png }" style="width:90%;">
		      </c:if>
		      <c:if test="${not empty bsi.pngPath.top3png }">
		        <img src="<c:if test="${!bsi.pngPath.top3png.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.pngPath.top3png }" style="width:90%;">
		      </c:if>
		      <c:if test="${not empty bsi.pngPath.top4png }">
		        <img src="<c:if test="${!bsi.pngPath.top4png.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.pngPath.top4png }" style="width:90%;">
		      </c:if>
		      <c:if test="${not empty bsi.pngPath.top5png }">
		        <img src="<c:if test="${!bsi.pngPath.top5png.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.pngPath.top5png }" style="width:90%;">
		      </c:if>
		      <c:if test="${not empty bsi.pngPath.top6png }">
		        <img src="<c:if test="${!bsi.pngPath.top6png.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.pngPath.top6png }" style="width:90%;">
		      </c:if>
		      <c:if test="${not empty bsi.pngPath.top7png }">
		        <img src="<c:if test="${!bsi.pngPath.top7png.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.pngPath.top7png }" style="width:90%;">
		      </c:if>
		      <c:if test="${not empty bsi.pngPath.top8png }">
		        <img src="<c:if test="${!bsi.pngPath.top8png.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.pngPath.top8png }" style="width:90%;">
		      </c:if>
		      <c:if test="${not empty bsi.pngPath.top9png }">
		        <img src="<c:if test="${!bsi.pngPath.top9png.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.pngPath.top9png }" style="width:90%;">
		      </c:if>
		      <c:if test="${not empty bsi.pngPath.top10png }">
		        <img src="<c:if test="${!bsi.pngPath.top10png.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.pngPath.top10png }" style="width:90%;">
		      </c:if>
            </div>
        </div>
        <!--检测结果-->
        <div class="m-box">
            <h2><i class="i-edit"></i>序列质量分析（见QC结果）</h2>
            <div class="m-boxCon" id="_report" style="display: inline-block;width: 90%">
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
                            <td>${bsi.basicStatistics1.Filename }</td>
                            <td>${bsi.basicStatistics2.Filename }</td>
                        </tr>
                        <tr>
                            <td>File type</td>
                            <td>${bsi.basicStatistics1.FileType }</td>
                            <td>${bsi.basicStatistics2.FileType }</td>
                        </tr>
                        <tr>
                            <td>Encoding</td>
                            <td>${bsi.basicStatistics1.Encoding }</td>
                            <td>${bsi.basicStatistics2.Encoding }</td>
                        </tr>
                        <tr>
                            <td>Total Sequences</td>
                            <td>${bsi.basicStatistics1.TotalSeq }</td>
                            <td>${bsi.basicStatistics2.TotalSeq }</td>
                        </tr>
                        <tr>
                            <td>Filtered Sequences</td>
                            <td>${bsi.basicStatistics1.FilteredSeq }</td>
                            <td>${bsi.basicStatistics2.FilteredSeq }</td>
                        </tr>
                        <tr>
                            <td>Sequence length</td>
                            <td>${bsi.basicStatistics1.SeqLength }</td>
                            <td>${bsi.basicStatistics2.SeqLength }</td>
                        </tr>
                        <tr>
                            <td>%GC</td>
                            <td>${bsi.basicStatistics1.gc }</td>
                            <td>${bsi.basicStatistics2.gc }</td>
                        </tr>
                    </tbody>
                </table>
                <table style="width:100%;">
                  <tr>
                    <td style="width:50%;">
                      <c:if test="${not empty bsi.qualityPath1 }">
                        <img style="width:100%;" src="<c:if test="${!bsi.qualityPath1.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.qualityPath1 }">
                      </c:if>
                    </td>
                    <td>
                      <c:if test="${not empty bsi.qualityPath2 }">
                        <img style="width:100%;" src="<c:if test="${!bsi.qualityPath2.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }/</c:if>${bsi.qualityPath2 }">
                      </c:if>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <c:if test="${not empty bsi.seqContentPath1 }">
                        <img style="width:100%;" alt="" src="<c:if test="${!bsi.seqContentPath1.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.seqContentPath1 }">
                      </c:if>
                    </td>
                    <td>
                      <c:if test="${not empty bsi.seqContentPath2 }">
                        <img style="width:100%;" alt="" src="<c:if test="${!bsi.seqContentPath2.contains('Tools') }">${uploadPath }/${bsi.userId }/${bsi.appId }/${bsi.dataKey }</c:if>${bsi.seqContentPath2 }">
                      </c:if>
                    </td>
                  </tr>
                </table>
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
});
</script>