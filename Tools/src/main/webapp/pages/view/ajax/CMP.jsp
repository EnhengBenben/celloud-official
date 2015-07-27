<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
	<div class="m-file">
		文件名称：
		<span class="file-name"><input type="hidden" id="cmp_fileName" value="${ cmpReport.dataKey}(${ resultMap.fileName})">
			${ cmpReport.dataKey}(${ resultMap.fileName})
		</span>
		<div class="toolbar">
			<a href="javascript:printSimpCMP()" class="btn btn-default"><i class="i-print"></i>打印临床报告</a>
			<a href="javascript:printCMP(${cmpReport.userId },${resultMap.appId },${cmpReport.dataKey })" class="btn btn-default"><i class="i-print"></i>打印科研报告</a>
		</div>
	</div>
	<div id="printCMPContext">
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-report1"></i>数据统计</h2>
			<div class="m-boxCon" id="_table">
				<p>按照测序数据质量分析报告如下：（分析日期：${cmpReport.runDate }）</p>
		    	<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th>基本信息</th>
							<th width="50%">说明</th>
						</tr>	
					</thead>
					<tbody>
						<tr>
							<td>共获得有效片段：${cmpReport.allFragment }</td>
							<td>10000&nbsp;条以上序列认为合格</td>
						</tr>
						<tr>
							<td>平均质量：${cmpReport.avgQuality }</td>
							<td>质量值30以上为可用数据</td>
						</tr>
						<tr>
							<td>平均GC含量：${cmpReport.avgGCContent }</td>
							<td>40%~50%&nbsp;均属正常</td>
						</tr>
						<tr>
							<td>可用片段：${cmpReport.usableFragment }</td>
							<td>高质量数据，碱基质量大于30</td>
						</tr>
						<tr>
							<td>待检基因：${cmpReport.noDetectedGene }</td>
							<td>待检基因数目</td>
						</tr>
						<tr>
							<td>检测基因数：${cmpReport.detectedGene }</td>
							<td>检测到的基因数目</td>
						</tr>
						<tr>
							<td>平均测序深度：${cmpReport.avgCoverage }</td>
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
				<input type="hidden" id="cmp_snp_tbody1" value="${resultMap.snp_tbody1 }">
				<input type="hidden" id="cmp_snp_tbody2" value="${resultMap.snp_tbody2 }">
				<table style="width:100%;height:100px;">
			   		<tr>
			   		  <c:choose>
			   			<c:when test="cmpReport.cmpGeneResult=='' || cmpReport.cmpGeneResult==null || cmpReport.cmpGeneResult=='null'">
			   				<td>未检测到相关突变位点</td>
			   			</c:when>
			   			<c:otherwise>
			   				 <c:choose>
					   			<c:when test="%{fn:length(cmpReport.cmpGeneResult)<2}">
					   				<td width="49%" valign="top" height="100%">
										<table class="table table-striped-green table-text-center table-padding0" id="snp_table1">
											<thead>
												<tr>
													<th class="mwidth_Gene">基因</th>
													<th>已知突变位点数<c:out value="%{fn:length(cmpReport.cmpGeneResult)}"></c:out> </th>
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
					   			</c:when>
					   			<c:otherwise>
					   				<td width="49%" valign="top" height="100%">
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
									<td width="49%" valign="top" height="100%">
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
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-report1"></i>序列质量分析（见QC结果）</h2>
			<div class="h2">Basic Statistics</div>
				<table class="table table-green table-striped-blue table-text-center">
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
				<table class="table table-green table-striped-orange table-text-center">
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
				<table style="width:100%;">
			      <tr>
			    	<td style="width:50%;"><img src="${cmpReport.basicStatistics2.qualityPath1 }"></td>
			    	<td><img src="${cmpReport.basicStatistics2.qualityPath2 }"></td>
			      </tr>
			      <tr>
			    	<td><img alt="" src="${cmpReport.basicStatistics2.seqContentPath1 }"></td>
			    	<td><img alt="" src="${cmpReport.basicStatistics2.seqContentPath2 }"></td>
			      </tr>
			    </table>
		</div>
	</div>
</div>
<script>
$(function() {
	$(window).manhuatoTop({
		showHeight : 100,
		speed : 1000
	});
	var pa = "";
	$("#_report").find("td").each(function(){
		pa += $(this).html()+"@";
	});
	var reg = new RegExp("&lt;","g");
	pa = pa.replace(reg,"%3C");
	var url = $("#_url").attr("href");
	$("#_url").attr("href",url+pa);
});
function showBg(src,id) { 
	var width = $("#" + id).width();
	var height = $("#" + id).height();
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",width*1.5);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",height*1.5);
	window.parent.showZoom(src);
} 
function showGeneDetail(gname){
	$.get("report!getCmpGeneInfo",{"gname":gname},function(cmpGene){
		$("#cmpGeneName").html(cmpGene.cmpName);
		$("#cmpGeneDesc").html(cmpGene.cmpDescription);
		if(cmpGene.cmpImage != null &&cmpGene.cmpImage!=''){
			$("#cmpImg").css("display","");
			var imgPath = '<%=request.getContextPath()%>/resource/img/cmp/'+cmpGene.cmpImage;
			$("#cmpImg").attr("src",imgPath);
		}
		if(cmpGene.cmpTreatment != null &&cmpGene.cmpTreatment!=''){
			$("#cmpImg").css("display","");
			$("#cmpGeneTreat").html(cmpGene.cmpTreatment);
		}
		if(cmpGene.cmpTreatment != null &&cmpGene.cmpTreatment!=''){
			$("#cmpGeneTreatDesc").css("display","");
			$("#cmpGeneTreatDesc").html(cmpGene.cmpTreatmentDesc);
		}
		$("#cmpGeneInfo").css("display","");
	})
}
</script>