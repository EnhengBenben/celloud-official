<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row">
	<div class="m-file">
		文件名称：
		<span class="file-name"><input type="hidden" id="cmp_fileName" value="${ resultMap.dataKey}(${ resultMap.fileName})">
			${ resultMap.dataKey}(${ resultMap.fileName})
		</span>
		<div class="toolbar">
			<a href="javascript:printSimpCMP()" class="btn btn-default"><i class="i-print"></i>打印临床报告</a>
			<a href="javascript:printCMP(${resultMap.userId },${resultMap.appId },${resultMap.dataKey })" class="btn btn-default"><i class="i-print"></i>打印科研报告</a>
		</div>
	</div>
	<div id="printCMPContext">
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-report1"></i>数据统计</h2>
			<div class="m-boxCon" id="_table">
				<table class="table table-bordered table-condensed">
					<thead>
						<tr>
							<th>基本信息</th>
							<th width="50%">说明</th>
						</tr>	
					</thead>
					<tbody>
						<tr>
							<td>分析日期：<span id="cmp_RunDate">${resultMap.runDate }</span></td>
							<td></td>
						</tr>
						<tr>
							<td>共获得有效片段：<span id="cmp_allFragment">${resultMap.allFragment }</span></td>
							<td>10000条以上序列认为合格</td>
						</tr>
						<tr>
							<td>平均质量：<span id="cmp_avgQuality">${resultMap.avgQuality }</span></td>
							<td>质量值30以上为可用数据</td>
						</tr>
						<tr>
							<td>平均GC含量：<span id="cmp_avgGC">${resultMap.avgGC }</span></td>
							<td>40%~50%均属正常</td>
						</tr>
						<tr>
							<td>可用片段：<span id="cmp_useFragment">${resultMap.useFragment }</span></td>
							<td>高质量数据，碱基质量大于30</td>
						</tr>
						<tr>
							<td>待检基因：<span id="cmp_undetectGene">${resultMap.undetectGene }</span></td>
							<td>待检基因数目</td>
						</tr>
						<tr>
							<td>检测基因数：<span id="cmp_detectGene">${resultMap.detectGene }</span></td>
							<td>检测到的基因数目</td>
						</tr>
						<tr>
							<td>平均测序深度：<span id="cmp_avgCoverage">${resultMap.avgCoverage }</span></td>
							<td>100倍以上数据</td>
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
				<s:if test="%{resultMap.snp_tbody1==''||resultMap.snp_tbody1==null||resultMap.snp_tbody1=='null' }">
					未检测到相关突变位点
				</s:if>
				<s:else>
					<table class="table table-bordered table-condensed" style="width:45%;float:left;margin-right:10px;">
						<thead>
							<tr>
								<th>基因</th>
								<th>已知突变位点数</th>
								<th>测序深度</th>
							</tr>	
						</thead>
						${resultMap.snp_tbody1 }
					</table>
					<s:if test="resultMap.snp_tbody2!=null && resultMap.snp_tbody2!='null'">
						<table class="table table-bordered table-condensed" style="width:45%;float:left;">
							<thead>
								<tr>
									<th>基因</th>
									<th>已知突变位点数</th>
									<th>测序深度</th>
								</tr>	
							</thead>
							${resultMap.snp_tbody2 }
						</table>
					</s:if>
				</s:else>
			</div>
			<div id="cmpGeneInfo" style="display:none;padding:10px 20px;">
				<h4><span id="cmpGeneName"></span>突变检测</h4>
				<ul class="list">
	    		<li>指标说明</li>
	    		<p class="context" id="cmpGeneDesc">
	    		</p>
	    		<li style="displey:none;">治疗药物：<span id="cmpGeneTreat"></span></li>
	    		<p class="context" id="cmpGeneTreatDesc" style="displey:none;">
	    		</p>
	    		</ul>
	    		<img style="displey:none;" id="cmpImg" src="">
			</div>
			<div class="m-tips">
				<i class="i-tips"></i>注释： 已知突变位点，在样本中发现且有文献支持的突变位点。
			</div>
		</div>
		<!--报告图示一-->
		<div class="m-box">
			<h2><i class="i-report1"></i>序列质量分析（见QC结果）</h2>
			<input type="hidden" id="cmp_fastqc_data" value="${resultMap.fastqc_data }">
			<input type="hidden" id="cmp_per_base_quality" value="${resultMap.per_base_quality }">
			<input type="hidden" id="cmp_per_base_seq_content" value="${resultMap.per_base_seq_content }">
			<input type="hidden" id="cmp_f2" value="${resultMap.f2 }">
			<input type="hidden" id="cmp_q2" value="${resultMap.q2 }">
			<input type="hidden" id="cmp_s2" value="${resultMap.s2 }">
			<div class="m-boxCon" id="_table" style="overflow:auto;zoom:1;">
				<div style="width:48%;float:left;left:10px;">
					<div class="title">Basic Statistics</div>
					<table class="table table-bordered table-condensed">
						${resultMap.fastqc_data }
					</table>
					<img style="width:100%;" src="${resultMap.per_base_quality }">
					<img style="width:100%;" src="${resultMap.per_base_seq_content }">
				</div>
				<div style="width:48%;float:left;margin-right:10px;">
					<div class="title">Basic Statistics</div>
					<table class="table table-bordered table-condensed">
						${resultMap.f2 }
					</table>
					<img style="width:100%;" src="${resultMap.q2 }">
					<img style="width:100%;" src="${resultMap.s2 }">
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