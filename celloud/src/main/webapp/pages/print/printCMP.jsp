<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>打印CMP简报</title>
<link href="<%=request.getContextPath() %>/css/print.css?version=20150526" rel="stylesheet">
</head>
<body>
<a href="javascript:void(0)" onclick="preview(this)" class="btn btn-default" id="change" style="float:right;margin-top:10px;margin-right:-80px;"><i class="i-print"></i>打印</a>
<section class="section2 border1 w3cbbs">
    <h3>基因检测结果简述</h3>
    <h4>1、检测文件</h4>
    <div class="info">${resultMap.fileName }</div>
    <h4>2、检测结果</h4>
    <div class="info w3cbbs">
    	<p>按照临床及/或病理诊断，结合患者诊疗病史进行针对肿瘤基因测序分析报告如下：</p>
    	<table style="width:100%;height:100px;">
    		<tr>
    		<s:if test="resultMap.snp_tbody1=='' || resultMap.snp_tbody1==null || resultMap.snp_tbody1=='null'">
				<td>未检测到相关突变位点</td>
			</s:if>
			<s:else>
				<td width="49%" valign="top" height="100%">
					<table class="table table-striped-green table-text-center">
						<thead>
							<tr>
								<th>基因</th>
								<th>已知突变位点数</th>
								<th>测序深度</th>
							</tr>	
						</thead>
						${resultMap.snp_tbody1 }
					</table>
				</td>
				<s:if test="resultMap.snp_tbody2!=null && resultMap.snp_tbody2!='null'">
					<td width="49%" valign="top" height="100%">
						<table class="table table-striped-green table-text-center">
							<thead>
								<tr>
									<th>基因</th>
									<th>已知突变位点数</th>
									<th>测序深度</th>
								</tr>	
							</thead>
							${resultMap.snp_tbody2 }
						</table>
					</td>
				</s:if>
			</s:else>
    		</tr>
		</table>
    	<p class="font10" style="width:100%;display:block">
	    	注：<br>
			1.&nbsp;&nbsp;已知突变位点:在样本中发现且有文献支持的突变位点。<br>
			2.&nbsp;&nbsp;橘色阴影标记的基因：测序深度低于50，分析结果的可信度比较低。
    	</p>
    </div>
    <h4>3、检测结果详细描述</h4>
    <div class="info">
    	<table class="table table-striped-green">
			<thead>
				<tr>
					<th colspan="2">分析日期：${resultMap.runDate }</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>基本信息</td>
					<td width="50%">说明</td>
				</tr>	
				<tr>
					<td>共获得有效片段：${resultMap.allFragment }</td>
					<td>10000&nbsp;条以上序列认为合格</td>
				</tr>
				<tr>
					<td>平均质量：${resultMap.avgQuality }</td>
					<td>质量值30以上为可用数据</td>
				</tr>
				<tr>
					<td>平均GC含量：${resultMap.avgGC }</td>
					<td>40%~50%&nbsp;均属正常</td>
				</tr>
				<tr>
					<td>可用片段：${resultMap.useFragment }</td>
					<td>高质量数据，碱基质量大于30</td>
				</tr>
				<tr>
					<td>待检基因：${resultMap.undetectGene }</td>
					<td>待检基因数目</td>
				</tr>
				<tr>
					<td>检测基因数：${resultMap.detectGene }</td>
					<td>检测到的基因数目</td>
				</tr>
				<tr>
					<td>平均测序深度：${resultMap.avgCoverage }</td>
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
		${resultMap.fastqc_data }
	</table>
	<div class="h2">Basic Statistics</div>
	<table class="table table-green table-striped-orange table-text-center">
		${resultMap.f2 }
	</table>
	<table style="width:100%;">
      <tr>
    	<td style="width:50%;"><img src="${resultMap.per_base_quality }"></td>
    	<td><img src="${resultMap.q2 }"></td>
      </tr>
      <tr>
    	<td><img alt="" src="${resultMap.per_base_seq_content }"></td>
    	<td><img alt="" src="${resultMap.s2 }"></td>
      </tr>
    </table>
</section>
<script language="javascript" src="/celloud/plugins/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/celloud/js/browser.js"></script>
<script type="text/javascript">
function preview(obj){
	var inputVal;
	$("body").find("section").each(function(){
		$(this).removeClass("border1");
	});
	$("body").find("input[type='text']").each(function(){
		inputVal = $(this).val();
		$(this).parent().html("<span name='print'>"+inputVal+"</span>");
	});
	var sex = $("input[type='radio']:checked").val();
	$("#_sex").html(sex);
	$("#change").hide();
	window.print();
	$("#change").show();
	$("body").find("section").each(function(){
		$(this).addClass("breport");
	});
	$("body").find("span[name='print']").each(function(){
		inputVal = $(this).html();
		$(this).parent().html("<input type='text' value='"+inputVal+"'>");
	});
	$("#_sex").html("<input type='radio' name='sex' value='男'>男<input type='radio' name='sex' value='女'>女");
	$("input[type='radio'][value="+sex+"]").attr("checked",true); 
}
</script>
</body>
</html>