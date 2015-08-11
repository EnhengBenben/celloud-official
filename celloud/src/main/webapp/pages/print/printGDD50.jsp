<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>遗传疾病监测50基因报告打印</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print.css?version=20150804">
</head>
<body>
<a href="javascript:void(0)" onclick="preview(this)" class="btn btn-default" id="change" style="float:right;margin-top:10px;margin-right:-80px;">打印</a>
<form id="form">
<input type="hidden" name="cmpId" id="cmpId" value="${cmpReport.id }">
<input type="hidden" name="cmpReport.dataKey" value="${cmpReport.dataKey }">
<input type="hidden" name="cmpReport.userId" value="${cmpReport.userId }">
<input type="hidden" name="infos" id="infos">
<section class="section0 border1 w3cbbs">
	<div class="header">
		<img src="<%=request.getContextPath()%>/images/report/yanda_print.png">
		<h1>遗传疾病监测50基因分析报告</h1>
	</div>
	<div class="titletype">
		<div>
			<span>肿瘤类型：</span><span><input type="text" class="input200" id="tumorType" name="cmpFill.tumorType" value="${cmpReport.cmpFilling.tumorType }"></span>
		</div>
		<div>
			<span>姓</span><span style="margin-left:40px">名：</span><span><input type="text" class="input200" value="${cmpReport.cmpFilling.patientBasic.name }"></span>
		</div>
		<div>
			<span>取样日期：</span><span><input type="text" class="input200" value="${cmpReport.cmpFilling.samplingDate }" ></span>
		</div>
		<div>
			<span>报告日期：</span><span><input type="text" class="input200" name="cmpFill.reportDate" value="${cmpReport.cmpFilling.reportDate }"></span>
		</div>
	</div>
	<footer>
		检测机构：燕达国际医院临床检测二部/华点云科技
	</footer>
</section>
<section class="section1 border1 w3cbbs">
	<h3>一、基本信息</h3>
	<h4>1、送检基本信息</h4>
	<ul class="info">
        <li>姓名：<span><input type="text" id="patientName" name="cmpFill.patientBasic.name" value="${cmpReport.cmpFilling.patientBasic.name }"></span></li>
        <li>取样日期：<span><input type="text" id="samplingDate" name="cmpFill.samplingDate" value="${cmpReport.cmpFilling.samplingDate }"></span></li>
        <li>性别： <span id="_sex"><input type="radio" name="cmpFill.patientBasic.sex" value="男" <c:if test="${cmpReport.cmpFilling.patientBasic.sex eq '男' }">checked="checked"</c:if>>男<input type="radio" name="cmpFill.patientBasic.sex" value="女" <c:if test="${cmpReport.cmpFilling.patientBasic.sex eq '女' }">checked="checked"</c:if>>女</span></li>
        <li>样本来源：<span><input type="text" id="sampleSource" name="cmpFill.sampleSource" value="${cmpReport.cmpFilling.sampleSource }"></span></li>
        <li>年龄：<span><input type="text" id="patientAge" name="cmpFill.patientBasic.age" value="${cmpReport.cmpFilling.patientBasic.age }"></span>岁</li>
        <li>分析日期：<span><input type="text" id="analysisDate" name="cmpFill.analysisDate" value="${cmpReport.cmpFilling.analysisDate }"></span></li>
        <li>分子生物实验操作：<span><input type="text" class="input100" id="molecularBioExperOper" name="cmpFill.molecularBioExperOper" value="${cmpReport.cmpFilling.molecularBioExperOper }"></span></li>
        <li>基因分析：<span><input type="text" id="geneAnalysis" name="cmpFill.geneAnalysis" value="${cmpReport.cmpFilling.geneAnalysis }"></span></li>
    </ul>
    <h4>2、送检要求</h4>
    <div class="info">
    	<p>50种新生儿遗传代谢疾病相关基因的突变检测</p>
    	<table class="table table-striped-green">
    	  <thead>
			<tr>
				<th>Type</th>
				<th colspan="2">Disease</th>
				<th>Gene</th>
			</tr>	
		  </thead>
		  <tbody>
    		<tr>
    			<td rowspan="12">肝病肾脏类</td><td></td><td>进行性家族性肝内胆汁瘀积2型</td><td></td>
    		</tr>
    		<tr>
    			<td></td><td>天冬氨酰葡萄糖胺尿症</td><td></td>
    		</tr>
    		<tr>
    			<td>Bile Acid Synthesis Defect</td><td>先天性胆汁酸合成缺陷</td><td></td>
    		</tr>
    		<tr>
    			<td>hepatolenticular degeneration</td><td>肝豆状核变性</td><td></td>
    		</tr>
    		<tr>
    			<td>Homocystinuria</td><td>高胱氨酸尿，N(5，10)-甲基四氢叶酸还原酶活性缺失</td><td>MTRR</td>
    		</tr>
    		<tr>
    			<td>Glutaric aciduria 2</td><td>Ⅱ型戊二酸尿症</td><td>ETFDH</td>
    		</tr>
    		<tr>
    			<td>CDH1</td><td>FGFR2</td><td>JAK2</td><td>NRAS</td><td>SRC</td>
    		</tr>
    		<tr>
    			<td>CDKN2A</td><td>FGFR3</td><td>JAK3</td><td>PDGFRA</td><td>STK11</td>
    		</tr>
    		<tr>
    			<td>CSF1R</td><td>FLT3</td><td>KDR</td><td>PIK3CA</td><td>TP53</td>
    		</tr>
    		<tr>
    			<td>CTNNB1</td><td>GNA11</td><td>KIT</td><td>PTEN</td><td>VHL</td>
    		</tr>
    	</table>
    </div>
</section>
</form>
<section class="section2 border1 w3cbbs">
  <h3>二、基因检测结果简述</h3>
  <h4>1、检测结果</h4>
  <p class="info">按照临床及/或病理诊断，结合患者诊疗病史进行针对肿瘤基因测序分析报告如下：</p>
  <div id="secion2_info1" class="info">
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
  <div id="secion2_info2">
    <h4>2、检测结果详细描述</h4>
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
  </div>
</section>
<section class="section8 border1 w3cbbs">
	<h3>八、附件：检测方法与技术、序列质量控制及覆盖度说明</h3>
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
    	<td style="width:50%;"><img src="${cmpReport.qualityPath1 }"></td>
    	<td><img src="${cmpReport.qualityPath2 }"></td>
      </tr>
      <tr>
    	<td><img alt="" src="${cmpReport.seqContentPath1 }"></td>
    	<td><img alt="" src="${cmpReport.seqContentPath2 }"></td>
      </tr>
    </table>
</section>
<script language="javascript" src="<%=request.getContextPath()%>/plugins/calendar/WdatePicker.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/plugins/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/browser.js"></script>
<script type="text/javascript">
var num = 1;
$(document).ready(function(){
	var ntemp = 0;
	if($("#drugTbody_3_tr_length").val()>1){
		num = $("#drugTbody_3_tr_length").val();
	}else if($("#drugTbody_4_tr_length").val()>1&&$("#drugTbody_4_tr_length").val()>$("#drugTbody_3_tr_length").val()){
		num = $("#drugTbody_4_tr_length").val();
	}
	$("#section5 table").each(function(index,element){
		ntemp++;
		if(ntemp==1){
			$(this).attr("class","table table-striped-green table-text-center");
		}else if(ntemp==2){
			$(this).attr("class","table table-striped-blue table-text-center");
		}else if(ntemp==3){
			$(this).attr("class","table table-striped-orange table-text-center");
			ntemp = 0;
		}
	});
	$("#noMutation").change(function(){
		if($("#noMutation").attr("checked")=="checked"){
			$("#drugTable").css("display","none");
			$("#del_drugTbody_3").css("display","none");
			$("#drugTable").parent().find("a").css("display","none");
		}else{
			$("#drugTable").css("display","");
			$("#del_drugTbody_3").css("display","");
			$("#drugTable").parent().find("a").css("display","");
		}
	});
});
window.onload=function(){
	printSection2Table();
	$("#section5").find("div[name='geneDescriptDiv']").each(function(index,element){
		var height1 = $(this).height()+36;
		var height2 = $(this).parent().find("table").find("tr").length;
		var height_t = $(this).parent().parent().parent().height();
 		if(height_t>980 &&((height1>670 && height2>1) || (height1>570 && height2>5)|| (height1>550 && height2>6))){
 			$(this).addClass("w3cbbs");
 		}
	});
	$("#section5").find("ul[class='list']").each(function(){
		printTable($(this),980);	
	});
	printSection6Table();
};

function addTableTr(tbody){
	var v1="<tr name=\""+tbody+"_tr\" id=\""+tbody+"_tr"+num+"\"><td><input type=\"text\" class=\"form-control\"></td><td><input type=\"text\" class=\"form-control\"></td><td><input type=\"text\" class=\"form-control\"></td></tr>";
	$("#"+tbody).append(v1);
	$("#del_"+tbody).append("<tr id='"+tbody+"_deltr"+num+"'><td><a href='javascript:removeThisTr(\""+tbody+"\","+num+")' title='删除此行'/></td></tr>");
	num++;
}
function addDrugInput(div){
	$("#"+div).append("<br><div name=\"drugContent\"><div class=\"h2\"><input type=\"text\" class=\"form-control col-sm-10\"></div><div name=\"section4Text\"><textarea class=\"form-control\" rows=\"15\" cols=\"100\"></textarea></div></div>");
}
function removeTableTr(tbody){
	$("#"+tbody).children("tr:last-child").remove();
	$("#del_"+tbody).find("tbody").children("tr:last-child").remove();
}
function removeThisTr(tbody,num){
	$("#"+tbody+"_tr"+num).remove();
	$("#"+tbody+"_deltr"+num).remove();
}
function removeDrugInput(div){
	$("#"+div).children("div[name='drugContent']:last-child").remove();
	$("#"+div).children("br:last-child").remove();
}
function preview(obj){
	var inputVal;
	var textareaVal;
	var classname;
	var cmpDrug = "";
	saveFillCmp();
	$("body").find("section").each(function(){
		$(this).removeClass("border1");
	});
	$("body").find("input[type='text']").each(function(){
		inputVal = $(this).val();
		classname = $(this).attr("class");
		$(this).parent().html("<input type='hidden' value='"+classname+"'><span name='print'>"+inputVal+"</span>");
	});
	$("body").find("table[name='cmpDrugTable']").find("tbody").find("tr").each(function(){
		$(this).find("td").each(function(){
			cmpDrug += $(this).find("span").html() + "#";
		});
		cmpDrug += "@";
	});
	$.get("report!addCmpDrug",{"context":cmpDrug},function(flag){});
	$("#section4 textarea").each(function(){
		textareaVal = $(this).val();
		$(this).parent().html("<p name='section4p'>"+textareaVal+"</p>");
	});
	var sex = $("input[type='radio']:checked").val();
	$("#_sex").html(sex);
	$("#change").hide();
	if($("#noMutation").attr("checked")){
		$("#noDrug").css("display","");
	}
	$("#checkboxdiv").css("display","none");
	$("a").css("display","none");
	window.print();
	$("#change").show();
	$("body").find("section").each(function(){
		$(this).addClass("border1");
	});
	$("body").find("span[name='print']").each(function(){
		inputVal = $(this).html();
		classname = $(this).prev().val();
		$(this).parent().html("<input type='text' class='"+classname+"' value='"+inputVal+"'>");
	});
	$("body").find("p[name='section4p']").each(function(){
		inputVal = $(this).html();
		$(this).parent().html("<textarea class='form-control' rows='15' cols='100'>"+inputVal+"</textarea>");
	});
	$("#_sex").html("<input type='radio' name='sex' value='男'>男<input type='radio' name='sex' value='女'>女");
	$("input[type='radio'][value="+sex+"]").attr("checked",true); 
	$("#noDrug").css("display","none");
	$("#checkboxdiv").css("display","");
	$("a").css("display","");
}
function printTable($parentDiv_,height){
	var $table_ = $parentDiv_.find("table");
	var total_h = $parentDiv_.height();
	var table_h = $table_.height();
	var text_h = total_h-table_h;
	var tmp_h =  height- text_h;
	var totaltrnum = $table_.find("tr").length;
	var page_tr_num = Math.floor(height/37);
	var trnum1 = 0;
	var trnum2 = Math.floor((totaltrnum-trnum1-1)/page_tr_num);
	var thead = "";
	var newTables = "";
	if($parentDiv_.find("div[name='geneDescriptDiv']").hasClass("w3cbbs")){
		trnum1 = 25;
	}else{
		trnum1 = Math.floor(tmp_h/37);
	}
	if(table_h>0 && total_h>height && text_h<height && totaltrnum>trnum1){
		thead = $table_.find("thead").html();
		newTables = "<table class='table table-green table-striped-green table-text-center'><thead>"+thead+"</thead><tbody>";
		for(var i=1; i<trnum1-1; i++){
			newTables+="<tr>"+$table_.find("tbody").find("tr:eq("+i+")").html()+"</tr>";
		}
		newTables+="</tbody></table><div class='w3cbbs'></div>";
		if(trnum2>0){
			for(var i=1;i<trnum2;i++){
				newTables+="<table class='table table-green table-striped-green table-text-center'><thead>"+thead+"</thead><tbody>";
				for(var j=trnum1-1;j<trnum1+page_tr_num-1;j++){
					newTables+="<tr>"+$table_.find("tbody").find("tr:eq("+j+")").html()+"</tr>";
				}
				newTables+="</tbody></table><div class='w3cbbs'></div>";
				trnum1 = trnum1+page_tr_num-1;
			}
		}
		if((totaltrnum-trnum1-1)%page_tr_num>0){
			newTables+="<table class='table table-green table-striped-green table-text-center'><thead>"+thead+"</thead><tbody>";
			for(var i=trnum1;i<trnum1+(totaltrnum-trnum1-1)%page_tr_num;i++){
				newTables+="<tr>"+$table_.find("tbody").find("tr:eq("+i+")").html()+"</tr>";
			}
			newTables+="</tbody></table>";
		}
		$parentDiv_.find("table").remove();
		$parentDiv_.append(newTables);
	}
}
function printSection6Table(){
	var trLength = $("#section6").find("table").find("tr").length;
	if(trLength>25){
		var pageNum = Math.floor((trLength-25)/26);	
		var lastNum = (trLength-25)%26;
		var $table_ = $("#section6").find("table");
		var thead = "<table class='table table-green table-striped-green table-text-center'><thead>" +$table_.find("thead").html()+"</thead>";
		var newTables = thead+"<tbody>";
		for(var i=1; i<25; i++){
			newTables+="<tr>"+$table_.find("tbody").find("tr:eq("+i+")").html()+"</tr>";
		}
		newTables+="</tbody></table><div class='w3cbbs'></div>";
		if(pageNum>1){
			var begin = 25;
			for(var i=1;i<=pageNum;i++){
				newTables += thead+"<tbody>";
				for(var j=begin;j<begin+25+1;j++){
					newTables+="<tr>"+$table_.find("tbody").find("tr:eq("+j+")").html()+"</tr>";
				}
				newTables+="</tbody></table><div class='w3cbbs'></div>";
				begin = begin+25+1;
			}
		}
		if(lastNum>0){
			newTables += thead+"<tbody>";
			for(var j=begin;j<begin+lastNum-1;j++){
				newTables+="<tr>"+$table_.find("tbody").find("tr:eq("+j+")").html()+"</tr>";
			}
			newTables+="</tbody></table>";
		}
		$("#section6").find("table").remove();
		$("#section6").append(newTables);
	}
}
function printSection2Table(){
	var trLength = $("#snp_table1").find("tr").length;
	if(trLength>23){
		var thead = "<table class='table table-striped-green table-text-center table-padding0'><thead>"+$("#snp_table1").find("thead").html()+"</thead>";
		var newTables = "<table style='width:100%;height:100px;'><tr><td>";
		var tbody1 = thead+"<tbody>";
		var tbody2 = thead+"<tbody>";
		for(var i=1;i<22;i++){
			tbody1+="<tr>"+$("#snp_table1").find("tr:eq("+i+")").html()+"</tr>";
			tbody2+="<tr>"+$("#snp_table2").find("tr:eq("+i+")").html()+"</tr>";
		}
		tbody1+="</tbody></table>";
		tbody2+="</tbody></table>";
		newTables+=tbody1+"</td><td>"+tbody2+"</td></tr></table><div class='w3cbbs'></div>";
		tbody1 = thead+"<tbody>";
		tbody2 = thead+"<tbody>";
		for(var i=22;i<trLength;i++){
			tbody1+="<tr>"+$("#snp_table1").find("tr:eq("+i+")").html()+"</tr>";
			tbody2+="<tr>"+$("#snp_table2").find("tr:eq("+i+")").html()+"</tr>";
		}
		tbody1+="</tbody></table>";
		tbody2+="</tbody></table>";
		newTables += "<table style='width:100%;height:100px;'><tr><td>";
		newTables+=tbody1+"</td><td>"+tbody2+"</td></tr></table>";
		$("#secion2_info1").find("table").remove();
		$("#secion2_info1").prepend(newTables);
	}else{
		$("#secion2_info1").addclass("w3cbbs");
	}
}
function saveFillCmp(){
	var resistanceSiteSum = "";
	var personalizedMedicine = "";
	var recommendDrug = "";
	$("#drugTbody_3").find("tr").each(function(){
		resistanceSiteSum += getTbodyDrug($(this),resistanceSiteSum);
	});
	$("#drugTbody_4").find("tr").each(function(){
		personalizedMedicine += getTbodyDrug($(this),personalizedMedicine);
	});
	$("#drugDescipDiv").find("div[name='drugContent']").each(function(){
		recommendDrug += $(this).find("input[type='text']").val()+","+$(this).find("textarea").val()+";";
	});
	var infos = ""+resistanceSiteSum+"----"+personalizedMedicine+"----"+recommendDrug;
	$("#infos").val(infos);
	var age = parseInt($("#patientAge").val());
	$("#patientAge").val(age);
	$.get("cmpReport!updateFill",$("#form").serialize());
}
function getTbodyDrug(obj,result){
	result = "";
	var geneName = "";
	var mutationSite = "";
	var drug = "";
	obj.find("input").each(function(index){
		if(index == 0){
			geneName = $(this).val();
		}else if(index == 1){
			mutationSite = $(this).val();
		}else if(index == 2){
			drug = $(this).val();
		}
	});
	result+=geneName+","+mutationSite +","+drug+";";
	return result;
}
</script>
</body>
</html>