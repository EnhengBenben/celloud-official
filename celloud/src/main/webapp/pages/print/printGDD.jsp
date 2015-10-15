<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>遗传疾病检测报告打印</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print_gdd.css?version=1.3">
</head>
<body>
<a href="javascript:void(0)" onclick="preview(this)" class="btn btn-default" id="change" style="float:right;margin-top:10px;margin-right:-80px;">打印</a>
<a href="javascript:void(0)" onclick="save()" class="btn btn-default" id="change" style="float:right;margin-top:40px;margin-right:-80px;">保存</a>
<form id="form">
<input type="hidden" name="cmpId" id="cmpId" value="${cmpReport.id }">
<input type="hidden" name="cmpReport.dataKey" value="${cmpReport.dataKey }">
<input type="hidden" name="cmpReport.userId" value="${cmpReport.userId }">
  <section class="section0 border1 w3cbbs">
	<div class="header">
		<img src="<%=request.getContextPath()%>/images/report/yanda_print.png">
		<h1>新生儿遗传代谢疾病检测分析报告</h1>
	</div>
	<div class="titletype">
		<div class="titleinfo">
			<span>姓</span><span style="margin-left:40px">名：</span><span><input type="text" class="input200" value="${cmpReport.cmpFilling.patientName }"></span>
		</div>
		<div class="titleinfo">
			<span>取样日期：</span><span><input type="text" class="input200" value="${cmpReport.cmpFilling.samplingDate }" ></span>
		</div>
		<div class="titleinfo">
			<span>报告日期：</span><span><input type="text" class="input200" value="${cmpReport.cmpFilling.reportDate }"></span>
		</div>
	</div>
	<footer>
		检测机构：燕达国际医院临床检测二部/华点云科技
	</footer>
</section>
<section class="section1 border1 w3cbbs">
	<h3>受检者信息</h3>
    <ul>
    	<li>母亲姓名：<span><input type="text" name="cmpFill.motherName" value="${cmpReport.cmpFilling.motherName }"></span></li>
    	<li>病历号：<span><input type="text" name="cmpFill.medicalRecord" value="${cmpReport.cmpFilling.medicalRecord }"></span></li>
    	<li style="width:90%">项目编号：<span><input type="text" name="cmpFill.projectNo" value="${cmpReport.cmpFilling.projectNo }"></span></li>
        <li>新生儿姓名：<span><input type="text" name="cmpFill.patientName" value="${cmpReport.cmpFilling.patientName }"></span></li>
        <li>出生日期：<span><input type="text" name="cmpFill.birthday" value="${cmpReport.cmpFilling.birthday }"></span></li>
        <li>受检者性别： <span id="_sex"><input type="radio" name="cmpFill.patientSex" value="男" <c:if test="${cmpReport.cmpFilling.patientSex eq '男' }">checked="checked"</c:if>>男<input type="radio" name="cmpFill.patientSex" value="女" <c:if test="${cmpReport.cmpFilling.patientSex eq '女' }">checked="checked"</c:if>>女</span></li>
        <li>指导医生：<span><input type="text" name="cmpFill.doctorName" value="${cmpReport.cmpFilling.doctorName }"></span></li>
        <li>送检单位：<span><input type="text" name="cmpFill.inspectionUnit" value="${cmpReport.cmpFilling.inspectionUnit }"></span></li>
        <li>样本状态：<span><input type="text" name="cmpFill.sampleStatus" value="${cmpReport.cmpFilling.sampleStatus }"></span></li>
        <li style="width:90%">检测项目：<span>新生儿遗传代谢病相关基因检测</span></li>
        <li style="width:90%">检测方法：<span>外显子捕获，illumina高通量测序平台，SNPs/微插入/微缺失检测</span></li>
        <li>分子生物实验操作：<span><input type="text" class="input100" name="cmpFill.molecularBioExperOper" value="${cmpReport.cmpFilling.molecularBioExperOper }"></span></li>
        <li>基因分析：<span><input type="text" name="cmpFill.geneAnalysis" value="${cmpReport.cmpFilling.geneAnalysis }"></span></li>
        <li>送检日期：<span><input type="text" name="cmpFill.samplingDate" value="${cmpReport.cmpFilling.samplingDate }"></span></li>
        <li>分析日期：<span><input type="text" name="cmpFill.analysisDate" value="${cmpReport.cmpFilling.analysisDate }"></span></li>
        <li>报告日期：<span><input type="text" name="cmpFill.reportDate" value="${cmpReport.cmpFilling.reportDate }"></span></li>
    </ul>
	<h3>检测结果</h3>
	<h4>一. 致病性明确的基因检测结果</h4>
	<p class="font10">
	（注：本栏结果有明确研究和文献支持；或者是明确致病基因会引起相关蛋白功能改变）
	</p>
	<table class="table table-text-center">
      <thead>
        <tr>
            <th style="text-align:center">疾病名称</th>
            <th style="text-align:center">检测基因</th>
            <th style="text-align:center">突变位点数(个)</th>
            <th style="text-align:center">判定结果</th>
        </tr>	
      </thead>
      <tbody>
          <c:forEach items="${gsrList}" var="accountConfig" varStatus="status" >  
             <tr>  
	        	 <td>${accountConfig.diseaseName }</td>
	        	 <td>${accountConfig.gene }</td>
	        	 <td>${accountConfig.mutNum }</td>
	        	 <td><input type="text" name="cmpFill.decisionResult.${accountConfig.diseaseEngName }" value="${cmpReport.cmpFilling.decisionResult[accountConfig.diseaseEngName] }"></td>  
             </tr>
          </c:forEach>
      </tbody>
    </table>
    <p class="font10" style="width:100%;display:block">
    	注：检测结果判定为疾病携带者的受检者，需追查其父亲和母亲的相关遗传病基因是否突变，综合分析才能够最后确定检测者是否会患病。我们免费提供携带者父亲和母亲的基因检测和分析服务。
   	</p>
  </section>
  <section class="section2 border1 w3cbbs" id="section2">
    <h4>二. 检测结果详述</h4>
    <c:forEach items="${cmpReport.geneDetectionDetail }" var="geneDetection" varStatus="size">
      <h5>${size.count}.&nbsp;&nbsp;${geneDetection.key }基因：该基因突变与${geneDetection.value.result[0].diseaseName }相关</h5>
      <div class="info">
	   	<div name="geneDescriptDiv">
	   	  <h6>检测结果</h6>
	   	  <table class="table table-text-center">
	   	    <thead>
	   		  <tr>
	   			<th>基因</th>
	   			<th>参考碱基</th>
	   			<th>突变率</th>
	   			<th>覆盖深度</th>
	   			<th>突变位置</th>
	   			<th>氨基酸变化</th>
	   			<th>纯合/杂合</th>
	   			<th>遗传方式</th>
	   			<th>疾病名称</th>
	   		  </tr>
	   		</thead>
	   		<tbody>
	   			<c:forEach items="${geneDetection.value.result}" var="r" varStatus="size">
	    			<c:choose>
	    				<c:when test="${fn:contains(r.gene, '没有发现突变位点')}">
	    					<tr><td colspan="6">没有发现突变位点</td></tr>
	    				</c:when>
	    				<c:otherwise>
	   						<tr>
	   							<td>${r.gene }</td>
	   							<td>${r.refBase }</td>
	   							<td>${r.mutBase }</td>
	   							<td>${r.depth }</td>
	   							<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
	   							<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
	   							<td>杂合突变</td>
	   							<td>${r.geneticMethod }</td>
	   							<td>${r.diseaseName }</td>
	   						</tr>
	    				</c:otherwise>
	    			</c:choose>
	   			</c:forEach>
	    	</tbody>
	      </table>
	      <h6>疾病简介</h6>
	      <div><textarea class="form-control" rows="15" cols="100" name="cmpFill.diseaseProfile.${geneDetection.value.result[0].diseaseEngName }">${cmpReport.cmpFilling.diseaseProfile[geneDetection.value.result[0].diseaseEngName] }</textarea></div>
	    </div>
	  </div>
    </c:forEach>
  </section>
</form>
  <section class="section2 border1 w3cbbs" id="section3">
    <h4>三. 其他检测结果</h4>
    <table class="table table-text-center">
   	    <thead>
   		  <tr>
   		  	<th>疾病名称</th>
   			<th>检测基因</th>
   			<th>检测结果</th>
   			<th>判定结果</th>
   		  </tr>
   		</thead>
   		<tbody>
   			<c:choose>
   				<c:when test="${gddDiseaseList.size()==0}">
   					<tr><td colspan="4">没有发现其他检测结果</td></tr>
   				</c:when>
   				<c:otherwise>
		   			<c:forEach items="${gddDiseaseList}" var="gddDisease">
   						<tr>
   							<td>${gddDisease.name }</td>
   							<td>${gddDisease.gene }</td>
   							<td>未发现异常</td>
   							<td>正常</td>
   						</tr>
		   			</c:forEach>
   				</c:otherwise>
   			</c:choose>
    	</tbody>
      </table>
  </section>
  <section class="section2 border1 w3cbbs" id="section4">
    <h4>四. 结果说明</h4>
    <div class="info">
	    <h6>1.&nbsp;&nbsp;关于遗传方式的解释：</h6>
	    <p><span>AR</span>，常染色体隐性遗传：理论上存在2个突变位点才可能致病，在只发现一个杂合突变点的时候，虽然为高危致病基因，但定义为致病基因携带者，并不发病。</p>
	    <p><span>AD</span>，常染色体显性遗传：理论上存在1个杂合突变点即导致致病，通常显性遗传临床症状较轻。</p>
	    <p><span>XR</span>，X染色体隐性遗传：女孩有两条X染色体，男孩只有一条X染色体，通常女孩为致病基因携带者，男孩则发病。</p>
	    <p><span>XD</span>，X染色体显性遗传：理论上1个杂合或纯合的突变位点均可能致病。</p>
    </div>
    <div class="info">
	    <h6>2.&nbsp;&nbsp;对经检测发现遗传代谢病致病基因突变的解释：</h6>
	    <p>这些突变位点在人类基因组突变专业数据库（HGMD）内，有明确文献报道位点与该疾病相关，这样的位点一般经过蛋白功能或家系内的严谨验证，确认为高危致病基因，但不排除少数位点局限于以前的科学水平，属于误报道的高危致病位点。</p>
  	</div>
    <div class="info">
	  	<h6>3.&nbsp;&nbsp;对经检验未发现遗传代谢病致病相关基因突变的解释：</h6>
	    <p>结果未发现遗传代谢病相关的致病基因突变位点，表示在基因检测的列表当中没有发现异常，即列表中基因引发的遗传病发病风险很低。随着人类基因组的深入研究，新的致病基因和变异仍在不断被报道出来，不排除有新基因、新发变异导致病变发生的可能。</p>
	  	<p>某些遗传性疾病和非遗传性疾病很容易混淆，疾病非遗传原因，一旦发病，在无家族史情况下，散发的患者较为常见。</p>
  	</div>
  </section>
  <section class="section2 border1 w3cbbs" id="fj1">
    <h3>附件一：详细检测结果</h3>
	<table class="table table-text-center">
	    <thead>
		  <tr>
			<th>基因</th>
			<th>突变率</th>
			<th>覆盖深度</th>
			<th>突变位置</th>
			<th>氨基酸变化</th>
			<th>纯合/杂合</th>
			<th>疾病名称</th>
		  </tr>
		</thead>
		<tbody>
			<c:forEach items="${allGsr}" var="r" varStatus="size">
 			<c:choose>
 				<c:when test="${fn:contains(r.gene, '没有发现突变位点')}">
 					<tr><td colspan="6">没有发现突变位点</td></tr>
 				</c:when>
 				<c:otherwise>
					<tr>
						<td>${r.gene }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
						<td>
							<c:choose>
				 				<c:when test="${r.hetOrHom=='het'}">
				 					杂合
				 				</c:when>
				 				<c:otherwise>
				 					纯合
				 				</c:otherwise>
 							</c:choose>
						</td>
						<td>${r.diseaseName }</td>
					</tr>
 				</c:otherwise>
 			</c:choose>
			</c:forEach>
 	    </tbody>
    </table>
  </section>
  <section class="fj border1 w3cbbs" id="fj1">
	<h3>附件二：检测方法与技术、序列质量控制及覆盖度说明</h3>
    <div class="info">
	  <h6>1.&nbsp;&nbsp;检测方法与技术：</h6>
	  <p>设计118个基因外显子的探针序列进行捕获，采用MISEQ测序仪进行高通量测序，得到原始数据。</p>
	  <p>通过FASTQC对原始数据进行质控，去除末端质量低于30的碱基。去除adaptor接头序列以及测序引物序列。</p>
	</div>
    <div class="info">
	  <h6>2.&nbsp;&nbsp;基因突变：</h6>
	  <p>采用bowtie比对软件，将过滤得到的高质量序列比对到50个基因序列上。通过bcftools得到mpileup文件并解析。判断碱基覆盖度及比对质量找到突变位点。</p>
	</div>
	<h6>3.&nbsp;&nbsp;序列质量分析（见QC结果）</h6>
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
  <script language="javascript" src="<%=request.getContextPath()%>/plugins/jquery-1.8.3.min.js"></script>
  <script>
  	function preview(obj){
  		var inputVal;
  		var textareaVal;
  		var classname;
  		$("body").find("section").each(function(){
  			$(this).removeClass("border1");
  		});
  		$("body").find("input[type='text']").each(function(){
  			inputVal = $(this).val();
  			classname = $(this).attr("class");
  			$(this).parent().html("<input type='hidden' value='"+classname+"'><span name='print'>"+inputVal+"</span>");
  		});
  		$("#section2 textarea").each(function(){
  			textareaVal = $(this).val();
  			$(this).parent().html("<p name='section4p'>"+textareaVal+"</p>");
  		});
  		var sex = $("input[type='radio']:checked").val();
  		$("#_sex").html(sex);
  		$("#change").hide();
  		if($("#noMutation").attr("checked")){
  			$("#noDrug").css("display","");
  		}
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
  		$("a").css("display","");
  	}
  	function save(){
  		$.get("cmpReport!saveGddFile",$("#form").serialize());
  	}
  </script>
</body>
</html>
