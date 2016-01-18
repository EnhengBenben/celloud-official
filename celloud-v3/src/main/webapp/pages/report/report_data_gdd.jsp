<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<div class="m-file">
		数据编号：<span class="file-name">${cmpReport.dataKey}</span><br>
		文件名称：
		<span class="file-name">
		<c:forEach items="${cmpReport.data}" var="data">
			${data.fileName}(${data.dataKey})&nbsp;&nbsp;
		</c:forEach>
		</span>
		<div class="toolbar">
			<a href="javascript:printGDD(${cmpReport.projectId },${cmpReport.dataKey },${cmpReport.appId })" class="btn btn-default"><i class="i-print"></i>打印GDD报告</a>
		</div>
	</div>
	<div id="printCMPContext">
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-report1"></i>数据统计</h2>
			<div class="m-boxCon" id="_table">
				<p>按照测序数据质量分析报告如下：（分析日期：<span id="cmp_RunDate">${cmpReport.runDate }</span>）</p>
		    	<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th>基本信息</th>
							<th width="50%">说明</th>
						</tr>	
					</thead>
					<tbody>
						<tr>
							<td>共获得有效片段：<span id="cmp_allFragment">${cmpReport.allFragment }</span></td>
							<td>10000&nbsp;条以上序列认为合格</td>
						</tr>
						<tr>
							<td>平均质量：<span id="cmp_avgQuality">${cmpReport.avgQuality }</span></td>
							<td>质量值30以上为可用数据</td>
						</tr>
						<tr>
							<td>平均GC含量：<span id="cmp_avgGC">${cmpReport.avgGCContent }</span></td>
							<td>40%~50%&nbsp;均属正常</td>
						</tr>
						<tr>
							<td>可用片段：<span id="cmp_useFragment">${cmpReport.usableFragment }</span></td>
							<td>高质量数据，碱基质量大于30</td>
						</tr>
						<tr>
							<td>待检基因：<span id="cmp_undetectGene">${cmpReport.noDetectedGene }</span></td>
							<td>待检基因数目</td>
						</tr>
						<tr>
							<td>检测基因数：<span id="cmp_detectGene">${cmpReport.detectedGene }</span></td>
							<td>检测到的基因数目</td>
						</tr>
						<tr>
							<td>平均测序深度：<span id="cmp_avgCoverage">${cmpReport.avgCoverage }</span></td>
							<td>100&nbsp;倍以上数据</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!--检测结果-->
		<div class="m-box m-box-yc">
			<h2><i class="i-edit"></i>报告</h2>
			<div class="m-boxCon" id="_report" style="display: inline-block;width: 100%">
				<table class="table" style="width:95%;height:100px;">
			   		<tr>
			   		  <c:choose>
			   			<c:when test="cmpReport.cmpGeneResult=='' || cmpReport.cmpGeneResult==null || cmpReport.cmpGeneResult=='null'">
			   				<td>未检测到相关突变位点</td>
			   			</c:when>
			   			<c:otherwise>
			   				 <c:choose>
					   			<c:when test="${cmpReport.cmpGeneResult.size()>=2}">
					   				<td width="46%" valign="top" height="100%">
										<table class="table table-striped-green table-text-center table-padding0" id="snp_table1">
											<thead>
												<tr>
													<th class="mwidth_Gene">基因</th>
													<th>已知突变位点数</th>
													<th>测序深度</th>
												</tr>	
											</thead>
											<tbody>
												<c:forEach items="${cmpReport.cmpGeneResult}" var="gene" begin="0" end="${cmpReport.cmpGeneResult.size()/2-1 }">
													<tr>
														<td><span <c:if test="${gene.sequencingDepth<50 }">style='background-color:#feaa20'</c:if>>${gene.geneName }</span></td>
														<td>${gene.knownMSNum }</td>
														<td>${gene.sequencingDepth }</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</td>
									<td width="46%" valign="top" height="100%">
										<table class="table table-striped-green table-text-center table-padding0" id="snp_table2">
											<thead>
												<tr>
													<th class="mwidth_Gene">基因</th>
													<th>已知突变位点数</th>
													<th>测序深度</th>
												</tr>	
											</thead>
											<tbody>
												<c:forEach items="${cmpReport.cmpGeneResult}" var="gene" begin="${cmpReport.cmpGeneResult.size()/2 }" end="${cmpReport.cmpGeneResult.size() }">
													<tr>
														<td><span <c:if test="${gene.sequencingDepth<50 }">style='background-color:#feaa20'</c:if>>${gene.geneName }</span></td>
														<td>${gene.knownMSNum }</td>
														<td>${gene.sequencingDepth }</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</td>
					   			</c:when>
					   			<c:otherwise>
					   				<td width="47%" valign="top" height="100%">
										<table class="table table-striped-green table-text-center table-padding0" id="snp_table1">
											<thead>
												<tr>
													<th class="mwidth_Gene">基因</th>
													<th>已知突变位点数 </th>
													<th>测序深度</th>
												</tr>	
											</thead>
											<tbody>
												<c:forEach items="${cmpReport.cmpGeneResult}" var="gene">
													<tr>
														<td><span <c:if test="${gene.sequencingDepth<50 }">style='background-color:#feaa20'</c:if>>${gene.geneName }</span></td>
														<td>${gene.knownMSNum }</td>
														<td>${gene.sequencingDepth }</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</td>
								</c:otherwise>
							</c:choose>
			   			</c:otherwise>
			   		  </c:choose>
			   		</tr>
				</table>
			</div>
			<div class="m-tips">
				<i class="i-tips"></i>注释： 已知突变位点，在样本中发现且有文献支持的突变位点。
			</div>
		</div>
		<!--检测结果-->
		<div class="m-box m-box-yc">
			<h2><i class="i-edit"></i>序列质量分析（见QC结果）</h2>
			<div class="m-boxCon" id="_report" style="display: inline-block;width: 90%">
				<div class="h2">Basic Statistics</div>
				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th>#Measure</th>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Filename</td>
							<td>${cmpReport.basicStatistics1.Filename }</td>
						</tr>
						<tr>
							<td>File type</td>
							<td>${cmpReport.basicStatistics1.FileType }</td>
						</tr>
						<tr>
							<td>Encoding</td>
							<td>${cmpReport.basicStatistics1.Encoding }</td>
						</tr>
						<tr>
							<td>Total Sequences</td>
							<td>${cmpReport.basicStatistics1.TotalSeq }</td>
						</tr>
						<tr>
							<td>Filtered Sequences</td>
							<td>${cmpReport.basicStatistics1.FilteredSeq }</td>
						</tr>
						<tr>
							<td>Sequence length</td>
							<td>${cmpReport.basicStatistics1.SeqLength }</td>
						</tr>
						<tr>
							<td>%GC</td>
							<td>${cmpReport.basicStatistics1.gc }</td>
						</tr>
					</tbody>
				</table>
				<div class="h2">Basic Statistics</div>
				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th>#Measure</th>
							<th>Value</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Filename</td>
							<td>${cmpReport.basicStatistics2.Filename }</td>
						</tr>
						<tr>
							<td>File type</td>
							<td>${cmpReport.basicStatistics2.FileType }</td>
						</tr>
						<tr>
							<td>Encoding</td>
							<td>${cmpReport.basicStatistics2.Encoding }</td>
						</tr>
						<tr>
							<td>Total Sequences</td>
							<td>${cmpReport.basicStatistics2.TotalSeq }</td>
						</tr>
						<tr>
							<td>Filtered Sequences</td>
							<td>${cmpReport.basicStatistics2.FilteredSeq }</td>
						</tr>
						<tr>
							<td>Sequence length</td>
							<td>${cmpReport.basicStatistics2.SeqLength }</td>
						</tr>
						<tr>
							<td>%GC</td>
							<td>${cmpReport.basicStatistics2.gc }</td>
						</tr>
					</tbody>
				</table>
				<table style="width:90%;">
			      <tr>
			    	<td style="width:50%;"><img style="max-width:500px;" src="<c:if test="${!cmpReport.qualityPath1.contains('Tools') }">${uploadPath}${cmpReport.userId }/${cmpReport.appId }/${cmpReport.dataKey }</c:if>${cmpReport.qualityPath1 }"></td>
			    	<td><img style="max-width:500px;" src="<c:if test="${!cmpReport.qualityPath2.contains('Tools') }">${uploadPath}${cmpReport.userId }/${cmpReport.appId }/${cmpReport.dataKey }/</c:if>${cmpReport.qualityPath2 }"></td>
			      </tr>
			      <tr>
			    	<td><img style="max-width:500px;" alt="" src="<c:if test="${!cmpReport.seqContentPath1.contains('Tools') }">${uploadPath}${cmpReport.userId }/${cmpReport.appId }/${cmpReport.dataKey }</c:if>${cmpReport.seqContentPath1 }"></td>
			    	<td><img style="max-width:500px;" alt="" src="<c:if test="${!cmpReport.seqContentPath2.contains('Tools') }">${uploadPath}${cmpReport.userId }/${cmpReport.appId }/${cmpReport.dataKey }</c:if>${cmpReport.seqContentPath2 }"></td>
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