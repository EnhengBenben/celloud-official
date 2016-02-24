<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印CMP简报</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/font.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print_cmp.css?version=1.0">
</head>
<body>
<a href="javascript:void(0)" onclick="preview(this)" class="btn btn-default" id="change" style="float:right;margin-top:10px;margin-right:-100px;"><i class="i-print"></i>打印</a>
<section class="section2 border1 w3cbbs">
    <h3>基因检测结果简述</h3>
    <h4>1.&nbsp;&nbsp; 检测文件</h4>
    <div class="info">
		<c:forEach items="${cmpReport.data}" var="data">
			${data.fileName}&nbsp;&nbsp;
		</c:forEach>
	</div>
    <h4>2.&nbsp;&nbsp;检测结果</h4>
    <div class="info">
    	<p>按照临床及/或病理诊断，结合患者诊疗病史进行针对肿瘤基因测序分析报告如下：</p>
	   	<table style="width:100%;height:100px;">
	   		<tr>
	   		  <c:choose>
	   			<c:when test="cmpReport.cmpGeneResult=='' || cmpReport.cmpGeneResult==null || cmpReport.cmpGeneResult=='null'">
	   				<td>未检测到相关突变位点</td>
	   			</c:when>
	   			<c:otherwise>
	   				 <c:choose>
			   			<c:when test="${fn:length(cmpReport.cmpGeneResult)<2}">
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
	   	<p class="font10" style="width:100%;display:block">
	    	注：<br>
			1.&nbsp;&nbsp;该检测结果仅对本次送检样本负责，由于存在异质性的现象，不能反映全部病变的性质。<br>
			2.&nbsp;&nbsp;由于检测样本不能长期保存，对检测结果有任何异议，需要检测复核请与24小时内提出。<br>
			3.&nbsp;&nbsp;该检测结果仅供科研参考。<br>
			4.&nbsp;&nbsp;已知突变位点:在样本中发现且有文献支持的突变位点。<br>
			5.&nbsp;&nbsp;橘色阴影标记的基因：测序深度低于50，分析结果的可信度比较低。
	   	</p>
    </div>
    <h4>3.&nbsp;&nbsp;检测结果详细描述</h4>
    <div class="info">
    	<p>按照测序数据质量分析报告如下：（分析日期：${cmpReport.runDate }）</p>
    	<table class="table table-striped-green">
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
</section>
<section class="section8 border1 w3cbbs">
	<h3>检测方法与技术、序列质量控制及覆盖度说明</h3>
	<h4>1.&nbsp;&nbsp;检测方法与技术：</h4>
	<p>
		设计50个基因的引物序列进行扩增，并构建300PE的片段文库，采用MISEQ测序仪进行高通量测序，得到原始数据。
	</p>
	<p>
		通过FASTQC对原始数据进行质控，去除末端质量低于30的碱基。去除adaptor接头序列以及测序引物序列。
	</p>
	<h4>2.&nbsp;&nbsp;基因突变：</h4>
	<p>
		采用bowtie比对软件，将过滤得到的高质量序列比对到50个基因序列上。通过bcftools得到mpileup文件并解析。判断碱基覆盖度及比对质量找到突变位点。
	</p>
	<h4>3.&nbsp;&nbsp;序列质量分析（见QC结果）</h4>
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
    	<td style="width:50%;"><img src="<c:if test="${!cmpReport.qualityPath1.contains('Tools') }">${uploadPath }/${cmpReport.userId }/${cmpReport.appId }/${cmpReport.dataKey }</c:if>${cmpReport.qualityPath1 }"></td>
    	<td><img src="<c:if test="${!cmpReport.qualityPath2.contains('Tools') }">${uploadPath }/${cmpReport.userId }/${cmpReport.appId }/${cmpReport.dataKey }/</c:if>${cmpReport.qualityPath2 }"></td>
      </tr>
      <tr>
    	<td><img alt="" src="<c:if test="${!cmpReport.seqContentPath1.contains('Tools') }">${uploadPath }/${cmpReport.userId }/${cmpReport.appId }/${cmpReport.dataKey }</c:if>${cmpReport.seqContentPath1 }"></td>
    	<td><img alt="" src="<c:if test="${!cmpReport.seqContentPath2.contains('Tools') }">${uploadPath }/${cmpReport.userId }/${cmpReport.appId }/${cmpReport.dataKey }</c:if>${cmpReport.seqContentPath2 }"></td>
      </tr>
    </table>
</section>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript">
function preview(obj){
	var inputVal;
	var textareaVal;
	var classname;
	var cmpDrug = "";
	$("body").find("section").each(function(){
		$(this).removeClass("border1");
	});
	window.print();
	$("#change").show();
	$("body").find("section").each(function(){
		$(this).addClass("border1");
	});
}
</script>
</body>
</html>