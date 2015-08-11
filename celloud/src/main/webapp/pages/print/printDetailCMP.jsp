<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>肿瘤报告打印</title>
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
		<h1>肿瘤样本基因检测与个体化治疗分析报告</h1>
	</div>
	<div class="titletype">
		<div>
			<span>肿瘤类型：</span><span><input type="text" class="input200" id="tumorType" name="cmpFill.tumorType" value="${cmpReport.cmpFilling.tumorType }"></span>
		</div>
		<div>
			<span>姓</span><span style="margin-left:40px">名：</span><span><input type="text" class="input200" value="${cmpReport.cmpFilling.patientName }"></span>
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
        <li>姓名：<span><input type="text" id="patientName" name="cmpFill.patientName" value="${cmpReport.cmpFilling.patientName }"></span></li>
        <li>取样日期：<span><input type="text" id="samplingDate" name="cmpFill.samplingDate" value="${cmpReport.cmpFilling.samplingDate }"></span></li>
        <li>性别： <span id="_sex"><input type="radio" name="cmpFill.patientSex" value="男" <c:if test="${cmpReport.cmpFilling.patientSex eq '男' }">checked="checked"</c:if>>男<input type="radio" name="cmpFill.patientSex" value="女" <c:if test="${cmpReport.cmpFilling.patientSex eq '女' }">checked="checked"</c:if>>女</span></li>
        <li>样本来源：<span><input type="text" id="sampleSource" name="cmpFill.sampleSource" value="${cmpReport.cmpFilling.sampleSource }"></span></li>
        <li>年龄：<span><input type="text" id="patientAge" name="cmpFill.patientAge" value="${cmpReport.cmpFilling.patientAge }"></span>岁</li>
        <li>临床诊断：<span><input type="text" id="clinicalDiagnosis" name="cmpFill.clinicalDiagnosis" value="${cmpReport.cmpFilling.clinicalDiagnosis }"></span></li>
        <li>病理诊断：<span><input type="text" id="pathologicDiagnosis" name="cmpFill.pathologicDiagnosis" value="${cmpReport.cmpFilling.pathologicDiagnosis }"></span></li>
        <li>分析日期：<span><input type="text" id="analysisDate" name="cmpFill.analysisDate" value="${cmpReport.cmpFilling.analysisDate }"></span></li>
        <li>病理位置：<span><input type="text" id="pathologicalPosition" name="cmpFill.pathologicalPosition" value="${cmpReport.cmpFilling.pathologicalPosition }"></span></li>
        <li>分子生物实验操作：<span><input type="text" class="input100" id="molecularBioExperOper" name="cmpFill.molecularBioExperOper" value="${cmpReport.cmpFilling.molecularBioExperOper }"></span></li>
        <li>病理类型：<span><input type="text" id="pathologicalType" name="cmpFill.pathologicalType" value="${cmpReport.cmpFilling.pathologicalType }"></span></li>
        <li>基因分析：<span><input type="text" id="geneAnalysis" name="cmpFill.geneAnalysis" value="${cmpReport.cmpFilling.geneAnalysis }"></span></li>
    </ul>
    <h4>2、送检要求</h4>
    <div class="info">
    	<p>50个肿瘤相关基因的突变检测及用药指导分析</p>
    	<table class="table table-striped-green">
    		<tr>
    			<td>ABL1</td><td>EGFR</td><td>GNAQ</td><td>KRAS</td><td>PTPN11</td>
    		</tr>
    		<tr>
    			<td>AKT1</td><td>ERBB2</td><td>GNAS</td><td>MET</td><td>RB1</td>
    		</tr>
    		<tr>
    			<td>ALK</td><td>ERBB4</td><td>HNF1A</td><td>MLH1</td><td>RET</td>
    		</tr>
    		<tr>
    			<td>APC</td><td>EZH2</td><td>HRAS</td><td>MPL</td><td>SMAD4</td>
    		</tr>
    		<tr>
    			<td>ATM</td><td>FBXW7</td><td>IDH1</td><td>NOTCH1</td><td>SMARCB1</td>
    		</tr>
    		<tr>
    			<td>BRAF</td><td>FGFR1</td><td>IDH2</td><td>NPM1</td><td>SMO</td>
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
<section class="section3 border1 w3cbbs">
    <h3>三、耐药位点汇总</h3>
   	<div id="checkboxdiv"><input type="checkbox" id="noMutation">未见明确耐药位点突变</div>
   	<ul class="list" style="display:none;" id="noDrug">
   		<li>未见明确耐药位点突变</li>
   	</ul>
   	<table class="table table-striped-blue table-text-center" id="drugTable" name="cmpDrugTable">
		<thead>
			<tr>
				<th style="width:110px;">基因</th>
				<th style="min-width:105px">突变位点</th>
				<th style="min-width:105px">药物</th>
			</tr>	
		</thead>
		<tbody id="drugTbody_3">
			<c:choose>
				<c:when test="${cmpReport.cmpFilling.resistanceSiteSum.size()>0}">
					<c:forEach items="${cmpReport.cmpFilling.resistanceSiteSum}" var="li" varStatus="size">
						<tr id="drugTbody_3_tr<c:out value="${size.index}"/>" name="drugTbody_3_tr">
							<td><input type="text" class="form-control" value="${li.geneName }"></td>
							<td><input type="text" class="form-control" value="${li.mutationSite }"></td>
							<td><input type="text" class="form-control" value="${li.drug }"></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr id="drugTbody_3_tr0" name="drugTbody_3_tr">
						<td><input type="text" class="form-control"></td>
						<td><input type="text" class="form-control"></td>
						<td><input type="text" class="form-control"></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<table class="table-del" id="del_drugTbody_3">
		<tr><th>&nbsp;</th></tr>
		<c:choose>
			<c:when test="${cmpReport.cmpFilling.resistanceSiteSum.size()>0}">
				<c:forEach items="${cmpReport.cmpFilling.resistanceSiteSum}" var="li" varStatus="size">
					<tr id="drugTbody_3_deltr<c:out value="${size.index}"/>"><td><a href="javascript:removeThisTr('drugTbody_3',<c:out value="${size.index}"/>)" title="删除所有"/></td></tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr id="drugTbody_3_deltr0"><td><a href="javascript:removeThisTr('drugTbody_3',0)" title="删除所有"/></td></tr>
			</c:otherwise>
		</c:choose>
	</table>
	<a href="javascript:void(0)" onclick="removeTableTr('drugTbody_3')">删除行</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" onclick="addTableTr('drugTbody_3')">添加行</a>
</section>
<input type="hidden" id="drugTbody_3_tr_length" value="${cmpReport.cmpFilling.resistanceSiteSum.size()}">
<input type="hidden" id="drugTbody_4_tr_length" value="${cmpReport.cmpFilling.personalizedMedicine.size()}">
<section class="section4 border1 w3cbbs" id="section4">
    <h3>四、个体化用药提示</h3>
   	<table class="table table-striped-orange table-text-center" name="cmpDrugTable">
		<thead>
			<tr>
				<th width="120">基因</th>
				<th style="min-width:105px">突变位点</th>
				<th style="min-width:105px">药物</th>
			</tr>
		</thead>
		<tbody id="drugTbody_4">
			<c:choose>
				<c:when test="${cmpReport.cmpFilling.personalizedMedicine.size()>0}">
					<c:forEach items="${cmpReport.cmpFilling.personalizedMedicine}" var="li" varStatus="size">
						<tr id="drugTbody_4_tr<c:out value="${size.index}"/>" name="drugTbody_4_tr">
							<td><input type="text" class="form-control" value="${li.geneName }"></td>
							<td><input type="text" class="form-control" value="${li.mutationSite }"></td>
							<td><input type="text" class="form-control" value="${li.drug }"></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr id="drugTbody_4_tr0" name="drugTbody_4_tr">
						<td><input type="text" class="form-control"></td>
						<td><input type="text" class="form-control"></td>
						<td><input type="text" class="form-control"></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<table class="table-del" id="del_drugTbody_4">
		<c:choose>
			<c:when test="${cmpReport.cmpFilling.personalizedMedicine.size()>0}">
				<c:forEach items="${cmpReport.cmpFilling.personalizedMedicine}" var="li" varStatus="size">
					<tr id="drugTbody_4_deltr<c:out value="${size.index}"/>"><td><a href="javascript:removeThisTr('drugTbody_4',<c:out value="${size.index}"/>)" title="删除所有"/></td></tr>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr id="drugTbody_4_deltr0"><td><a href="javascript:removeThisTr('drugTbody_4',0)" title="删除所有"/></td></tr>
			</c:otherwise>
		</c:choose>
	</table>
	<a href="javascript:void(0)" onclick="removeTableTr('drugTbody_4')">删除行</a>&nbsp;&nbsp;
	<a href="javascript:void(0)" onclick="addTableTr('drugTbody_4')">添加行</a>
	<h4>推荐药物：</h4>
	<div id="drugDescipDiv">
		<c:choose>
			<c:when test="${cmpReport.cmpFilling.recommendDrug.size()>0}">
				<c:forEach items="${cmpReport.cmpFilling.recommendDrug}" var="li" varStatus="size">
				  <div name="drugContent">
					<div class="h2">
				    	<input type="text" class="form-control col-sm-10" value="${li.drugName }">
				    </div>
				    <div name="section4Text">
				    	<textarea class="form-control" rows="15" cols="100">${li.drugDescrip }</textarea>
			    	</div>
					<c:if test="size.index>0"><br></c:if>
				  </div>
				</c:forEach>
			</c:when>
			<c:otherwise>
			  <div name="drugContent">
				<div class="h2">
			    	<input type="text" class="form-control col-sm-10">
			    </div>
			    <div name="section4Text">
			    	<textarea class="form-control" rows="15" cols="100"></textarea>
		    	</div>
		      </div>
			</c:otherwise>
		</c:choose>
    </div>
    <a href="javascript:void();" onclick="removeDrugInput('drugDescipDiv')">删除药物</a>&nbsp;&nbsp;
    <a href="javascript:void();" onclick="addDrugInput('drugDescipDiv')">添加药物</a>
</section>
<section class="section5 border1 w3cbbs" id="section5">
    <h3>五、检测基因的详细信息</h3>
    <s:set name="a" value="1"/>
<%--     <s:if test="resultMap.ABL1Coverage!=''&&resultMap.ABL1Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;ABL1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    	  <div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名： Abelson murine leukemia viral oncogene homolog 1。ABL1基因位于第9号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;临床作用：当BRA基因与ABL1基因型成BRA-ABL1融合基因的时候，新的BRA-ABL1融合蛋白就会使细胞的增生脱离细胞因子的控制，细胞癌化，BRA-ABL1融合基因是慢性粒细胞性白血病的一个重要病因。
	    		</p>
	    		<li>治疗药物：KU-55933</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;依马替尼（Imatinib mesylate）是针对BRA-ABL1融合基因的抑制剂，也是治疗此种癌症的靶向治疗药物。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/ABL1.png?date=20150629">
	    	  </div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;ABL1基因平均测序深度${cmpReport.geneDetectionDetail.ABL1.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
		    			<c:forEach items="${cmpReport.geneDetectionDetail.ABL1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
		    			</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	  </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.AKT1Coverage!=''&&resultMap.AKT1Coverage!=null"> --%>
      <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;AKT1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：RAC-alpha serine/threonine-protein kinase，又名：AKT1。位于第14号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：AKT1是细胞分裂、生长的正调控元件。AKT1可以抑制细胞的凋亡，促进细胞生长。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：在横纹肌实体瘤中有发现AKT1过表达。
	    		</p>
	    		<li>治疗药物</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;Selleck公司出品的“MK-2206 2HCl”是一种高度选择性的Akt1/2/3抑制剂。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/AKT1.png?date=20150629" style="width:450px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;AKT1基因平均测序深度${cmpReport.geneDetectionDetail.AKT1.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.AKT1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	  </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.ALKCoverage!=''&&resultMap.ALKCoverage!=null"> --%>
      <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;ALK基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Anaplastic lymphoma kinase，又名：ALK。位于第2号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：ALK在神经发育过程中起重要作用，是正调控元件。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：EML4-ALK融合基因占非小细胞肺癌中的3~5%，EML4有一个强启动子，与ALK融合之后，EML4-ALK基因的表达水平大幅升高，ALK酶活性也大幅升高。
	    		</p>
	    		<li>治疗药物</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;克唑替尼（crizotinib）是针对ALK的抑制剂，对ALK融合基因导致的肺癌有良好疗效
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/ALK.png?date=20150629" style="width:400px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;ALK基因平均测序深度${cmpReport.geneDetectionDetail.ALK.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.ALK.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	  </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.APCCoverage!=''&&resultMap.APCCoverage!=null"> --%>
      <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;APC基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Adenomatous polyposis coli，位于5号染色体长臂。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：是一个肿瘤抑制基因，主要通过调节其下游的β-catenin发挥作用，调节细胞增殖、迁移、粘着及染色体稳定等。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;肿瘤综合征：家族性腺瘤性息肉（FAP，OMIM#175100）。
	    		</p>
	    		<p class="context">
	    			4.&nbsp;&nbsp;APC的失活突变（缺失、截断、无义点突变）会导致细胞分裂的失控，APC 蛋白与 β连环蛋白 （ beta-catenin ） ( 一种转录transcription 因子 ) 形成复合物，导致β连环蛋白降解。如果缺乏 APC 蛋白，过多的β连环蛋白就会在 细胞核 （nucleus） 内的聚集。β连环蛋白与细胞核内的另一种蛋白结合， 形成一种复合物；这种复合物又与 DNA结合，启动了几种 基因 （genes） 的转录。这种复合物中的一个靶基因叫做 c-myc 一种已知的癌基因。 C-myc 本身就是几种基因的转录因子 （transcription factor）， 它控制着细胞的生长和分裂。因此，APC 基因的突变导致了一系列的连锁反应，最终导致细胞分裂的加速。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/APC.png?date=20150629">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;APC基因平均测序深度${cmpReport.geneDetectionDetail.APC.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.APC.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	  </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.ATMCoverage!=''&&resultMap.ATMCoverage!=null"> --%>
      <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;ATM基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Ataxia telangiectasia mutated (共济失调毛细血管扩张突变基因)，位于11号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：ATM基因是一个肿瘤抑制基因，主要在DNA双链受到损伤的时候，阻止细胞进入分裂周期而起作用，ATM失活与多种白血病、淋巴瘤相关。
	    		</p>
	    		<p class="context">
	    		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ATM是与DNA损伤检验有关的一个重要基因。最早发现于毛细血管扩张性共济失调症患者，人群中约有1%的人含ATM缺失的杂合子，表现对电离辐射敏感和易患癌症。正常细胞经放射处理后，DNA损伤会激活修复机制，如DNA不能修复则诱导细胞凋亡。
	    		</p>
	    		<li>治疗药物：KU-55933</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;依马替尼（Imatinib mesylate）是针对BRA-ABL1融合基因的抑制剂，也是治疗此种癌症的靶向治疗药物。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/ATM.png?date=20150629" style="width:400px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;ATM基因平均测序深度${cmpReport.geneDetectionDetail.ATM.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.ATM.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	  </div>
<%--      </s:if> --%>
<%--      <s:if test="resultMap.BRAFCoverage!=''&&resultMap.BRAFCoverage!=null"> --%>
   	   <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;BRAF基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：V-raf murine sarcoma viral oncogene homologB1（鼠类肉瘤滤过性毒素致癌同源体B1），又名：BRAF。位于第7号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：BRAF基因编码B-raf蛋白，是一个激酶，该酶将信号从Ras转导至MEK1/2，从而参与调控细胞内多种生物学事件。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：BRAF蛋白激活后导致MEK/ERK的激活，使细胞不进入凋亡程序。BRAF蛋白的完全活化需要T598和S601两个位点的磷酸化。但是如果BRAF发生V600E突变，那么无需T598和S601两个位点的磷酸化，BRAF就已获得持续的活性，并刺激细胞进入分裂周期。
	    		</p>
	    		<li>治疗药物</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;维罗非尼（Vemurafenib）、和索拉菲尼（Sorafenib）是获FDA批准的BRAF V600E突变肿瘤的靶向药物。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/BRAF.png?date=20150629">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;BRAF基因平均测序深度${cmpReport.geneDetectionDetail.BRAF.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.BRAF.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	  </div>
<%--      </s:if> --%>
<%--      <s:if test="resultMap.CDH1Coverage!=''&&resultMap.CDH1Coverage!=null"> --%>
     <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;CDH1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Cadherin-1，位于16号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：CDH1是一种依赖于钙离子的细胞粘合蛋白，是一种抑癌基因。CDH1与癌细胞的浸润、癌细胞转移相关，当CDH1基因发生突变失活时，细胞更容易突破基底膜、侵入到周围的组织中去。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/CDH1.png?date=20150629" style="width:400px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;CDH1基因平均测序深度${cmpReport.geneDetectionDetail.CDH1.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.CDH1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	</div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.CDKN2ACoverage!=''&&resultMap.CDKN2ACoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;CDKN2A基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：cyclin-dependent kinase inhibitor 2A，又名P16基因，位于9号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：CDKN2A是一种抑癌基因，其蛋白可以抑制或延缓细胞从G1期转到S期，DKN2A的突变与缺失与多种癌症的发生相关。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/CDKN2A.png?date=20150629">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;CDKN2A基因平均测序深度${cmpReport.geneDetectionDetail.CDKN2A.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.CDKN2A.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.CSF1RCoverage!=''&&resultMap.CSF1RCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;CSF1R基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Colony stimulating factor 1 receptor，又名：CSF1R。位于第5号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：CSF1R编码一个跨膜蛋白，这个蛋白是colony stimulating factor 1（CSF1）的受体。CSF1能够促进巨噬细胞的分裂、增殖。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：CSF1R和慢性骨髓单核细胞性白血病、以及M4急性成髓细胞白血病相关。CSF1R和CSF1都和乳腺癌相关。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/CSF1R.png?date=20150629" style="width:385px;height:137px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;CSF1R基因平均测序深度${cmpReport.geneDetectionDetail.CSF1R.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.CSF1R.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	  </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.CTNNB1Coverage!=''&&resultMap.CTNNB1Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;CTNNB1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    	  <div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：catenin (cadherin-associated protein) beta 1，位于3号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：该基因编码的蛋白是粘附连接体（AJS）蛋白复合体的一个组分。AJS通过调节细胞生长及细胞间的粘附对上皮细胞层的形成和维持发挥必需的功能。CTNNB1编码的蛋白质也会通过锚定肌动蛋白细胞骨架，当上皮细胞层完整形成后，通过发送接触抑制信号使得细胞停止继续分裂。β-连环蛋白是Wnt信号通路的一个组成部分。在正常的结肠上皮细胞中，Wnt信号失活，腺瘤息肉病基因（APC）复合物磷酸化β-catenin，导致β-catennin降解。这便阻止了β-catenin的核内沉积，最终导致细胞进入分化并保持结肠上皮细胞处于动态平衡状态。而当Wnt信号通路激活，永久性失活APC复合物，导致β-catennin降解被阻断。胞内丰度大量增加的β连环蛋白会转移进入核内，作为转录因子亚单位诱导大量基因的表达，这些靶基因的表达产物中有很多都是能够诱导EMT转换过程的转录因子，从而促进肿瘤的发展。
	    		</p>
	    		<li>与肿瘤的关系</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;与APC基因编码的蛋白相结合，APC突变常见于结肠腺瘤性息肉病。已在结直肠癌（CRC）、毛母质瘤（PTR）、髓母细胞瘤（MDB）、卵巢癌病变组织中中发现CTNNB1基因突变。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/CTNNB1.png?date=20150629" style="width:450px;">
	    	  </div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;CTNNB1基因平均测序深度${cmpReport.geneDetectionDetail.CTNNB1.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
	   					<c:forEach items="${cmpReport.geneDetectionDetail.CTNNB1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
	   					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.EGFRCoverage!=''&&resultMap.EGFRCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;EGFR基因(表皮生长因子受体基因)突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：epidermal growth factor receptor，又名HER1。位于7号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：是重要的细胞生长的细胞膜表面受体。EGFR是表皮细胞生长的重要调控蛋白，EGFR下游的信号转导通路主要有两条：一条是Ras/ Raf/ MEK/ERK-MAPK 通路，而另一条是PI3K/Akt/mTOR通路。多种癌症都与EGFR的过量表达有关系。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：EGFR的mRNA表达水平高低，对EGFR靶向治疗效果，会有显著差异。mRNA表达水平高的非小细胞肺癌患者，往往经针对EGFR的靶向治疗后有良好效果。而mRNA表达水平低的非小细胞肺癌患者，经EGFR的靶向治疗往往没有太好的效果。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;针对EGFR的小分子药物有吉非替尼（gefitinib、iressa）、厄洛替尼（erlotinib、tarceva）和拉帕非尼（lapatinib、 tykerb）等。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;针对EGFR的单抗药物有西妥昔单抗（cetuximab、erbitux）、帕尼单抗（panitumumab、vectibix）、尼妥珠单抗（nimotuzumab、泰欣生）。
	    		</p>
	    		<p class="context">
	    		<br>经临床实践证明，上述药物都对多种肿瘤有明显的抑制作用。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/EGFR.png?date=20150629" style="width:450px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;EGFR基因平均测序深度${cmpReport.geneDetectionDetail.EGFR.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.EGFR.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.ERBB2Coverage!=''&&resultMap.ERBB2Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;ERBB2（也称HER2）基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Receptor tyrosine-protein kinase erbB-2，又称HER2，human epidermal growth factor receptor 2。位于第17号染色体的长臂。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：ERBB2参与MAPK、PI3K/Akt、PKC、STAT等一系列的信号通路。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：乳腺癌中30%有ERBB2的突变。
	    		</p>
	    		<li>治疗药物:</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;赫赛汀（Herceptin，曲妥珠单抗）是对针对ERBB2的靶向药物，赫赛汀十分昂贵，而且有心脏毒性，所以用赫赛汀之前应做ERBB2的基因检测，ERBB2基因表达升高的、拷贝数增加的病人，赫赛汀的治疗效果会更好。另外帕妥珠单抗（Pertuzumab）也是针对ERBB2的药物。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/ERBB2.png?date=20150629" style="width:500px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;ERBB2基因平均测序深度${cmpReport.geneDetectionDetail.ERBB2.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.ERBB2.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.ERBB4Coverage!=''&&resultMap.ERBB4Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;ERBB4基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Receptor tyrosine-protein kinase erbB-4，又名：HER4。位于2号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：和其它的erbB基因一样，erbB4也是一个膜受体蛋白，也同样是酪氨酸激酶。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：erbB4与多种肿瘤相关，尤其与乳腺癌相关。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/ERBB4.png?date=20150629" style="width:400px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;ERBB4基因平均测序深度${cmpReport.geneDetectionDetail.ERBB4.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.ERBB4.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.EZH2Coverage!=''&&resultMap.EZH2Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;EZH2基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Histone-lysine N-methyltransferase，又名：EZH2。位于7号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：把3个甲基化基团加到组蛋白3（Histone 3）的第27号赖氨酸基团上，这会导致染色体的收紧。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：EZH2的过量表达会导致多种原发的癌症，原因是它过量表达后，会抑制多种抑癌基因的表达。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/EZH2.png?date=20150629">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;EZH2基因平均测序深度${cmpReport.geneDetectionDetail.EZH2.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.EZH2.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.FBXW7Coverage!=''&&resultMap.FBXW7Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;FBXW7基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：F-box/WD repeat-containing protein 7，又名：FBXW7。位于4号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：FBXW7直接与细胞周期蛋白E（cyclin E）作用，并很可以导向细胞周期蛋白E参与泛素介导的降解作用。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：在卵巢癌、乳腺癌和结直肠癌发现有FBXW7的突变。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/FBXW7.png?date=20150629" style="width:400px">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;FBXW7基因平均测序深度${cmpReport.geneDetectionDetail.FBXW7.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.FBXW7.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.FGFR1Coverage!=''&&resultMap.FGFR1Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;FGFR1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Fibroblast growth factor receptor 1，又名：FGFR1。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：FGFR1是成纤维细胞生长因子家族的一员，这是一个膜受体蛋白，也是一个酪氨酸激酶。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：与肺鳞癌相关。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/FGFR1.png?date=20150629" style="width:500px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;FGFR1基因平均测序深度${cmpReport.geneDetectionDetail.FGFR1.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.FGFR1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.FGFR2Coverage!=''&&resultMap.FGFR2Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;FGFR2基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Fibroblast growth factor receptor 2，又名：FGFR2。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：FGFR2是成纤维细胞生长因子家族的一员，也是一个酪氨酸激酶。	
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：一个第2内含子的点突变与一种高乳腺癌风险相关。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/FGFR2.png?date=20150629" style="width:417px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;FGFR2基因平均测序深度${cmpReport.geneDetectionDetail.FGFR2.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.FGFR2.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.FGFR3Coverage!=''&&resultMap.FGFR3Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;FGFR3基因（纤维芽细胞生长因子受体-3）突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Fibroblast growth factor receptor 3，4号染色体4p16.3位点。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;与肿瘤的关系：缺少FGFR3与膀胱癌相关，EGFR的mRNA表达水平高低，对EGFR靶向治疗效果，会有显著差异。mRNA表达水平高的非小细胞肺癌患者，往往经针对EGFR的靶向治疗后有良好效果。而mRNA表达水平低的非小细胞肺癌患者，经EGFR的靶向治疗往往没有太好的效果。
	    		</p>
	    		<li>治疗药物：DOVTINIB</li>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/FGFR3.png?date=20150629" style="width:417px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;FGFR3基因平均测序深度${cmpReport.geneDetectionDetail.FGFR3.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.FGFR3.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	</div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.FLT3Coverage!=''&&resultMap.FLT3Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;FLT3基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Fms-like tyrosine kinase 3，又名：Cluster of differentiation antigen 135 (CD135)。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：它是一个三类受体酪氨酸激酶。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：FLT3的串联持拷贝数增加是急性髓性白血病中最常见的突变，而且有这种突变也意味着预后不好。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    		&nbsp;&nbsp;&nbsp;&nbsp;有发现索拉非尼对FLT3阳性的急性髓性白血病有良好的疗效。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/FLT3.png?date=20150629" style="width:454px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;FLT3基因平均测序深度${cmpReport.geneDetectionDetail.FLT3.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
	   					<c:forEach items="${cmpReport.geneDetectionDetail.FLT3.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
	   					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--      <s:if test="resultMap.GNA11Coverage!=''&&resultMap.GNA11Coverage!=null"> --%>
     <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;GNA11基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Guanine nucleotide-binding protein subunit alpha-11。又名：Gqα亚基。位于第9号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：GNA11是一种G蛋白其功能是激活磷酯酶C（phospholipase C），进尔参与一系列的信号通路。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：编码G蛋白，后者通常用作分子的通断开关，调节外部信息到细胞内部的通道。G蛋白成为永久“开”或激活状态，从而导致Yes-associated protein (YAP)过度激活。YAP蛋白的活化诱导细胞生长失控，抑制细胞死亡，引起恶性肿瘤。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/GNA11.png?date=20150629" style="width:400px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;GNA11基因平均测序深度${cmpReport.geneDetectionDetail.GNA11.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.GNA11.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.GNAQCoverage!=''&&resultMap.GNAQCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;GNAQ基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Guanine nucleotide-binding protein G(q) subunit alpha。位于第9号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：可激活分裂原活化蛋白激酶通路(MARK通路)。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：在葡萄膜黑色素瘤中的突变率接近50%，最长见的突变位点是第5号外显子的209位氨基酸。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    		&nbsp;&nbsp;&nbsp;&nbsp;抑制剂司美替尼可抑制丝裂原活化蛋白激酶(MEK)通路。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/GNAQ.png?date=20150629" style="width:179px;height:158px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;GNAQ基因平均测序深度${cmpReport.geneDetectionDetail.GNAQ.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.GNAQ.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.GNASCoverage!=''&&resultMap.GNASCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;GNAS基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：G-protein alpha subunit。位于20号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：作为重要的信号转导蛋白，在G蛋白偶联的信号转导途径中激活腺苷酸环化酶，催化ATP转化为cAMP。cAMP对于调节细胞分裂和细胞增长有着重要的作用。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：与脑垂体瘤相关。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/GNAS.png?date=20150629" style="width:285px;height:162px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;GNAS基因平均测序深度${cmpReport.geneDetectionDetail.GNAS.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.GNAS.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.HNF1ACoverage!=''&&resultMap.HNF1ACoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;HNF1A基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：hepatocyte nuclear factor 1 homeobox A。位于第12号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：这是一个转录因子，与几种肝脏特异的基因表达调控有关。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：HNF1A 基因突变是青年发病的成年型糖尿病3（MODY3）的病因，糖尿病人群中此基因突变的几率是非糖尿病对照组人群的5倍之多。HNF1a双等位基因失活突变在不同个体的肝细胞腺瘤中被检测到。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/HNF1A.png?date=20150629" style="width:500px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;HNF1A基因平均测序深度${cmpReport.geneDetectionDetail.HNF1A.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.HNF1A.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--      </s:if> --%>
<%--      <s:if test="resultMap.HRASCoverage!=''&&resultMap.HRASCoverage!=null"> --%>
     <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;HRAS基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：GTPase HRas，又名：transforming protein p21。位于第11号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：这是一个小的G蛋白，也是small GTPases的一员。一旦结合到GTP上，HRaf就会激活c-Raf之类的Raf kinase，并进一步激活MAPK/ERK通路。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：HRAS与膀胱癌有密切的关系；HRAS若发生G12V、G12S突变，则其活性就永久激活；HRAS的突变与甲状腺癌、肾癌也有关系。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/HRAS.png?date=20150629" style="width:500px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;HRAS基因平均测序深度${cmpReport.geneDetectionDetail.HRAS.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.HRAS.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.IDH1Coverage!=''&&resultMap.IDH1Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;IDH1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Isocitrate dehydrogenase 1 (NADP+，异柠檬酸脱氢酶)。该基因位于2号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：异柠檬酸脱氢酶催化异柠檬酸转变成戊邻酮二酸。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：胶质母细胞瘤与IDH1有关系。 IDH1突变往往是在低级别胶质瘤发展的第一步，IDH1基因突变在这些脑肿瘤形成的关键事件。IDH1突变与生存期延长相关。胶质母细胞瘤与野生型IDH1基因有只有1年的中位总生存期，而IDH1突变的胶质母细胞瘤患者有2年以上的中位总生存期。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/IDH1.png?date=20150629" style="width:321px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;IDH1基因平均测序深度${cmpReport.geneDetectionDetail.IDH1.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.IDH1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	  </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.IDH2Coverage!=''&&resultMap.IDH2Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;IDH2基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Isocitrate dehydrogenase，异柠檬酸脱氢酶2。位于第15号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：是三羧酸循环中的一个重要的酶，把异柠檬酸脱氢，变成α-酮戊二酸。	
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：IDH酶通常将异柠檬酸转化为α-酮戊二酸盐（αKG）。然而，与癌症(cancer)相关的IDH突变产生了一种酶，能够将αKG转化为2-羟戊二酸（2HG）。2HG能够竞争性地抑制αKG的靶点，后者具有DNA和组蛋白脱甲基酶活性。因此，2HG产物导致了基因表达中的变化，这些变化之前被报告能够导致受损的分化。IDH2突变导致了分化抑制，并在不同的肿瘤背景下改变了DNA甲基化和过度增殖。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    		   &nbsp;&nbsp;&nbsp;&nbsp;当用一种小分子化合物AGI-6780治疗IDH2突变的小鼠白血病AML细胞时，AML细胞停止了增生和分化。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/IDH2.png?date=20150629" style="width:350px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;IDH2基因平均测序深度${cmpReport.geneDetectionDetail.IDH2.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.IDH2.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.JAK2Coverage!=''&&resultMap.JAK2Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;JAK2基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Janus kinase 2。该基因位于9号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：这是一种不依赖于受体的酪氨酸激酶。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/JAK2.png?date=20150629" style="width:450px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;JAK2基因平均测序深度${cmpReport.geneDetectionDetail.JAK2.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.JAK2.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.JAK3Coverage!=''&&resultMap.JAK3Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;JAK3基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Ataxia telangiectasia mutated (共济失调毛细血管扩张突变基因)，位于11号染色体Janus kinase 3。该基因位于第19号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：JAK3是一个酪氨酸激酶，主要在造血细胞中表达，如NK细胞、T细胞和B细胞，这一点与同基因家族的JAK1、JAK2在各种细胞中的广泛表达不一样。JAK3主要在免疫细胞中表达，主要是在被白介素激活后发挥其酪氨酸激酶的功能而起作用。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：JAK1、JAK2、JAK3的活化突变，都已被确认是血液性的癌症的病因。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;有一种编号为CP-690550（Tofacitinib，星熠艾克）的JAK3抑制剂，已经在临床验证中显示出很明确的疗效。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/JAK3.png?date=20150629">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;JAK3基因平均测序深度${cmpReport.geneDetectionDetail.JAK3.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.JAK3.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.KDRCoverage!=''&&resultMap.KDRCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;KDR基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Kinase insert domain receptor，也叫VEGFR-2（vascular endothelial growth factor receptor 2 ，血管内皮生长因子2），位于4号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：KDR则主要介导内皮细胞的增殖，导致血管通透性的增高，并阻止内皮细胞凋亡，维持内皮细胞的存活，与胚胎期内皮细胞的分化有关。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与癌症的关系：肿瘤的生长和扩散依赖于血管生成。肿瘤血管生成对于肿瘤的发生、发展、转移及预后具有重要作用，肿瘤血管生成是一系列复杂的调控过程,其中VEGF及其受体发挥着重要作用。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;Regorafenib是KDR的特异性抑制剂，并已得到FDA的批准，用于结肠癌。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;Foretinib是KDR的抑制剂，同时也是c-Met的抑制剂。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;lenvatinib， motesanib， Pazopanib等也是KDR的抑制剂。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/KDR.png?date=20150629" style="width:400px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;KDR基因平均测序深度${cmpReport.geneDetectionDetail.KDR.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.KDR.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.KITCoverage!=''&&resultMap.KITCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;KIT基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名： Mast/stem cell growth factor receptor，又名：CD117。属于原癌基因，其产物是Ⅲ型酪氨酸激酶。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：CD117是在造血干细胞表面表达的一种细胞因子受体。这个受与与干细胞因子结合后形成一个二聚体，这个二聚体激活其内在的酪氨酸激酶的活性，然后在细胞内激活下游的信号途径
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：精细胞肿瘤中经常会有CD117的第17个外显子的突变，而且这种肿瘤中还常常有CD117的过表达。CD117与白血病、黑色素瘤、胃肠道间质肿瘤相关
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;Gleevec（格列卫，Imatinib），如果病人有CD117在17号外显子的突变，Gleevec就会有效。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;dasatinib和nilotinib也有应用
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/KIT.png?date=20150629" style="width:500px">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;KIT基因平均测序深度${cmpReport.geneDetectionDetail.KIT.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.KIT.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.KRASCoverage!=''&&resultMap.KRASCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;KRAS基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：V-Ki-ras2 Kirsten rat sarcoma viral oncogene homolog。位于12号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：KRAS是一个分子开关，一旦它打开，它就会活化多种分裂、增殖因子，例如：c-Raf和PI 3-kinase。KRAS会和GTP结合，把GTP的最末一个磷酸基切掉，让它变成GDP。在把GTP变成GDP后，KRAS就关闭了。正常情况下，KRAS活化后，会马上失活。但是KRAS基因突变后，KRAS蛋白持续保持活化状态，导致细胞持续增生。	
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：与结直肠癌的关系密切。20%的非小细胞肺癌中有KRAS突变。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;KRAS是EGFR信号通路的下游结点。如果KRAS发生持续活化突变，则针对EGFR的抑制剂药物，如：易瑞沙（Iressa,gefitinb）和特罗凯（erlotinib,Tarceva），往往无效。所以，用针对EGFR的靶向药物治疗前，建议检测KRAS的突变状态。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/KRAS.png?date=20150629" style="width:465px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;KRAS基因平均测序深度${cmpReport.geneDetectionDetail.KRAS.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.KRAS.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	</div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.METCoverage!=''&&resultMap.METCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;MET基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：MNNG HOS Transforming gene，又名HGFR、c-MET。位于7号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：Hepatocyte growth factor（HGF）是MET的配体，它是可激活MET。被激活的MET启动一系列的生物反应，并会引起入侵性生长。MET参与的信号通路： RAS，PI3K，STAT，beta-catenin，Notch。异常的MET活性会导致血管的生长，并以此支持肿瘤的营养。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：激活关键的生长通路，促进血管生长，促进细胞的分散，这也导致肿瘤的转移，MET基因倍培是透明细胞瘤的一个潜在生物标记物。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;有MET基因倍增的结直肠癌肿瘤，对抑EGFR的治疗有抗性，一系列的MET抑制剂被研发出来：
	    		</p>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;K252a，SU11274，PHA-665752，ARQ197，Foretinib，SGX523，MP470。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;HGF的抑制剂：HGF是已知唯一的MET配体，所以能够阻断HGF作用的物质，也可能会是有效的抑制MET活性的物质。
	    		</p>
	    		<p class="context">
	    		  3.&nbsp;&nbsp;MET的假底物：CGEN241是MET酶的借底物，对动物模型中，对阻止肿瘤的生长和转移有很有效。
	    		</p>
	    		<p class="context">
	    			4.&nbsp;&nbsp;主动免疫治疗：白介素2（IL-2）被FDA批准用于肾细胞癌、和转移的黑色素瘤，这两种瘤都常常有MET活性失控的情况。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/MET.png?date=20150629" style="width:450px">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;MET基因平均测序深度${cmpReport.geneDetectionDetail.MET.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.MET.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	</div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.MLH1Coverage!=''&&resultMap.MLH1Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;MLH1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：MutL homolog 1。位于3号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：MLH1是人类基因中与大肠杆菌MutL同源的一个基因，MutL在大肠杆菌中负责对DNA错配的修复。	
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：MLH1基因的突变与遗传性非息肉结直肠癌有密切关系。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/MLH1.png?date=20150629" style="width:450px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;MLH1基因平均测序深度${cmpReport.geneDetectionDetail.MLH1.avgCoverage }。</p>
    			<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.MLH1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	   </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.MPLCoverage!=''&&resultMap.MPLCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;MPL（CD110）基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Myeloproliferative leukemia virus oncogene，又名Thrombopoietin Receptor（促血小板生成素受体）、CD110。位于1号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：MPL是一个细胞因子受体，与配体结合后，能把下游的信号蛋白（STAT、MAPK等）的酪氨酸残基磷酸化。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：MPL与骨髓增生白血病有关系。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/MPL.png?date=20150629" style="width:350px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;MPL基因平均测序深度${cmpReport.geneDetectionDetail.MPL.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.MPL.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.NOTCH1Coverage!=''&&resultMap.NOTCH1Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;NOTCH1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Notch homolog 1。位于3号染色体。	
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：Notch1基因在发育过程中控制细胞的分化命运。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：Notch1与白血病相关。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/NOTCH1.png?date=20150629" style="width:603px;height:312px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;NOTCH1基因平均测序深度${cmpReport.geneDetectionDetail.NOTCH1.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.NOTCH1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="esultMap.NPM1Coverage!=''&&esultMap.NPM1Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;NPM1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Nucleophosmin，又名：nucleolar phosphoprotein B23、numatrin。位于5号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：NPM1与核仁核糖体蛋白相关，能与单链或双链的核酸相结合。	
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：在多种肿瘤中都发现NPM1的上调、突变、或者染色体易位。当NPM1高表达时，会抑制P53/ARF通路。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/NPM1.png?date=20150629" style="width:400px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;NPM1基因平均测序深度${cmpReport.geneDetectionDetail.NPM1.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.NPM1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div></div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.NRASCoverage!=''&&resultMap.NRASCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;NRAS基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Neuroblastoma RAS viral oncogene homolog。位于1号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：与KRAS基因相近，对细胞的生长起调控作用。也是GTP酶，可以将GTP分解成GDP。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：与多种肿瘤相关。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/NRAS.png?date=20150629" style="width:500px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;NRAS基因平均测序深度${cmpReport.geneDetectionDetail.NRAS.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.NRAS.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	</div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.PDGFRACoverage!=''&&resultMap.PDGFRACoverage!=null"> --%>
   	<div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;PDGFRA基因（血小板源性生长因子受体α多肽）突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Alpha-type platelet-derived growth factor receptor，是PDGFR的a亚基。位于4号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：是血小板衍生生长因子家族的受体，也是一个细胞表面的酪氨酸激酶。激活MAPK通路，激活PI3K通路。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：与胃肠道间质瘤有关系。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;Crenolanib (CP-868596)是一种有效的，选择性的PDGFRα/β抑制剂，Kd为2.1 nM/3.2 nM，也有效抑制FLT3，对D842V突变型而不是V561D突变型敏感，作用于PDGFR比作用于c-Kit， VEGFR-2， TIE-2， FGFR-2， EGFR， erbB2，和Src选择性高100倍以上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;酪氨酸激酶抑制剂甲磺酸伊马替尼（格列卫）可选择性抑制ablAbl-Berc-kit及PDGFR的激酶活性。近年来，格列卫成为治疗GIST有效的分子靶向药物（尤其是手术不能切除或转移、复发病例）。PDGFRA基因突变位点对预测药物疗效有积极作用。研究结果表明，对于KIT基因无突变的GIST患者，还需要检测有无PDGFRA基因是否存在激活性突变，因为PDGFR的突变主要发生在没有KIT突变的肿瘤中，目前检测有三种突变类型外显子12（3%），外显子14（<1%），外显子18（9.7%），其中PDGFRA第18外显子D842V发生突变的GIST病例对格列卫、舒尼替尼治疗原发耐药。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/PDGFRA.png?date=20150629" style="width:500px">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;PDGFRA基因平均测序深度${cmpReport.geneDetectionDetail.PDGFRA.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.PDGFRA.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	</div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.PIK3CACoverage!=''&&resultMap.PIK3CACoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;PIK3CA基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：phosphatidylinositol-4，5-bisphosphate 3-kinase， catalytic subunit alpha，是PIK3的α亚基。又名：P110α。位于第3号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：PIK3激活PI3K/AKT/mTOR通路中的蛋白激酶B的活性。PIK3是一个重要的信号通路的关键酶。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：在许多肿瘤中，都存在PIK3的突变。这些突变大多数是导致激酶的活性更高。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;Wortmannin是一种PI3K抑制剂
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;BAY80-6946
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;GDC-0032
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/PIK3CA.png?date=20150629" style="width:400px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;PIK3CA基因平均测序深度${cmpReport.geneDetectionDetail.PIK3CA.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.PIK3CA.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	  </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.PTENCoverage!=''&&resultMap.PTENCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;PTEN基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名： Phosphatase and tensin homolog。位于10号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：是一个磷酸酯酶，专门使PIP3脱掉磷酸基团。这个活性的作用是阻断AKT信号通路。是一个抑癌基因。可以抑制细胞的分裂生长，并可以导致细胞的凋亡。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：在人类肿瘤中，PTEN是最常见的被丢失的基因。在前列腺肿瘤中，70%都有丢失至少一个拷贝。在胶质母细胞瘤、子宫内膜癌、和前列腺癌中经常发现PTEN的失活突变。PTEN的突变也会导致各种癌变倾向。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;VO-OHPIC
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;TRIHYDRATE
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/PTEN.png?date=20150629" style="width:550px">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;PTEN基因平均测序深度${cmpReport.geneDetectionDetail.PTEN.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.PTEN.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.PTPN11Coverage!=''&&resultMap.PTPN11Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;PTPN11基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名： Tyrosine-protein phosphatase non-receptor type 11，又名Shp2。位于12号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：PTPN11是一个酪氨酸磷酸酯酶。它可以把结合在酪氨酸残基上的磷酸基团水解下来。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：带有PTPN11突变的患者，很容易得少年单核细胞白血病。在神经母细胞瘤，黑色素瘤，急性髓细胞性白血病，乳腺癌，肺癌，结肠直肠癌中有发现PTPN11的活化突变。有报道：PTPN11既有促癌的作用，也有抑癌的作用。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/PTPN11.png?date=20150629">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;PTPN11基因平均测序深度${cmpReport.geneDetectionDetail.PTPN11.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.PTPN11.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.RB1Coverage!=''&&resultMap.RB1Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;RB1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：retinoblastoma protein。位于第13号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：这是一种抑癌基因，可以抑制E2F因子的作用，而E2F-DF是一种把细胞推进到S期的重要因子。这也是一个染色体重新组织的因子，它可以召集甲基化酶和乙酰化酶。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：如果两个RB1等位基因都突变了，这人就会视网膜母细胞瘤，并因此失明。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/RB1.png?date=20150629" style="width:450px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;RB1基因平均测序深度${cmpReport.geneDetectionDetail.RB1.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.RB1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.RETCoverage!=''&&resultMap.RETCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;RET基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：RET proto-oncogene。位于10号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：RET是一个受体酪氨酸激酶，参与神经的形成。缺少这个基因的人会得先天性巨结肠症（因为缺少神经控制，肠就不会蠕动，粪便会把肠壁撑大）。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：RET功能增强性的突变，会导致：甲状腺髓样癌，多发性内分泌瘤形成（2A型，和2B型），嗜铬细胞瘤和甲状旁腺增生。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;凡德他尼(vandetanib)是EGFR、VEGFR和RET酪氨酸激酶的抑制剂。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;卡博替尼（Cabozantinib）是VEGFR2, Met, FLT3, Tie2, Kit和 Ret的强效抑制剂。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/RET.png?date=20150629" style="width:380px">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;RET基因平均测序深度${cmpReport.geneDetectionDetail.RET.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.RET.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	  </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.SMAD4Coverage!=''&&resultMap.SMAD4Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;SMAD4基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Mothers against decapentaplegic homolog 4。位于第18号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：SMAD4和SMAD1、SMAD2等因子相结合，型成复合物，结合到DNA上以转录因子的形式起作用。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：在结直肠癌和胰腺癌中经常发现有SMAD4的突变。它也在常染色体显性遗传病幼年性息肉综合症中被发现有突变，这些息肉很可以发展成结肠癌。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/SMAD4.png?date=20150629" style="width:500px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;SMAD4基因平均测序深度${cmpReport.geneDetectionDetail.SMAD4.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.SMAD4.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    </div>
	    </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.SMARCB1Coverage!=''&&resultMap.SMARCB1Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;SMARCB1基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：SWI/SNF-related matrix-associated actin-dependent regulator of chromatin subfamily B member 1。位于第22号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：这个基因所编码的蛋白，是解开染色体压缩结构的复合蛋白的一个组成部分。它能使转录复合体能够更高效地结合到目标基因区域上。将编码核蛋白质也可结合并增强HIV-1整合酶对DNA的结合活性。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：这是一个肿瘤抑制基因，与恶性横纹肌样瘤有关。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/SMARCB1.png?date=20150629" style="width:550px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;SMARCB1基因平均测序深度${cmpReport.geneDetectionDetail.SMARCB1.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.SMARCB1.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.SMOCoverage!=''&&resultMap.SMOCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;SMO基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Smoothened。位于第7号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;与肿瘤的关系：SMO是一个原癌基因，如果发生活化突变，会导致hedgehog pathway信号通路的活化，并致癌。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;是致畸剂环杷明（环巴胺）的靶分子。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;LY2940680
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/SMO.png?date=20150629" style="width:500px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;SMO基因平均测序深度${cmpReport.geneDetectionDetail.SMO.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.SMO.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	   </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.SRCCoverage!=''&&resultMap.SRCCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;SRC基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Proto-oncogene tyrosine-protein kinase Src。位于第20号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：Src是一个非受体的酪氨酸激酶（这点与大多数的酪氨酸激酶要依赖于的受体是不一样的），Src可以被多种跨膜蛋白所激活（adhesion receptors, receptor tyrosine kinases, G-protein coupled receptors and cytokine receptors）。当它被激活后，它会启动细胞的生存、血管新生、细胞增殖和细胞侵袭等信号通路。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：50%的结肠癌、肝癌、肺癌、乳腺癌和胰腺癌与Src通路的激活有关。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;达沙替尼（dasatinib）是Src的针对性抑制剂。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/SRC.png?date=20150629" style="width:500px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;SRC基因平均测序深度${cmpReport.geneDetectionDetail.SRC.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.SRC.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	 </div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.STK11Coverage!=''&&resultMap.STK11Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;STK11基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名： Serine/threonine kinase 11，又名：LKB1。位于第19号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：STK11是AMPK信号通路的上个上游激酶。STK11激活AMPK，可在能量和营养缺乏的时侯，起到抑制细胞生长的作用。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：最近的研究发现在宫颈、乳腺、肠、睾丸、胰腺和皮肤癌中大量存在STK11的体细胞突变。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/STK11.png?date=20150629" style="width:347px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;STK11基因平均测序深度${cmpReport.geneDetectionDetail.STK11.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.STK11.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	</div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.TP53Coverage!=''&&resultMap.TP53Coverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;TP53基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Tumor protein p53。位于第17号染色体上。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：修复DNA损伤，稳定基因组。在DNA所受的损伤无法修复时，启动细胞进入凋亡程序。如果发现在有DNA损伤，可以在细胞进入G1/S期时让细胞停止在这一时期阻止血管新生。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：50%以上的肿瘤带有TP53的突变。TP53是最著名的抑癌基因。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/TP53.png?date=20150629" style="width:450px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;TP53基因平均测序深度${cmpReport.geneDetectionDetail.TP53.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.TP53.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	</div>
<%--     </s:if> --%>
<%--     <s:if test="resultMap.VHLCoverage!=''&&resultMap.VHLCoverage!=null"> --%>
    <div class="w3cbbs">
	    <h4><s:property value="#a"/><s:set name="a" value="#a+1"/>.&nbsp;&nbsp;VHL基因突变检测</h4>
	    <div class="info">
	    	<ul class="list">
	    		<div name="geneDescriptDiv">
	    		<li>指标说明</li>
	    		<p class="context">
	    			1.&nbsp;&nbsp;全名：Von Hippel–Lindau tumor suppressor。位于第3号染色体。
	    		</p>
	    		<p class="context">
	    			2.&nbsp;&nbsp;功能：此基因表达的蛋白参与HIF（Hypoxia-inducible factors，缺氧可诱导因子）的降解有关。而HIF在血管新生中起重要作用。
	    		</p>
	    		<p class="context">
	    			3.&nbsp;&nbsp;与肿瘤的关系：VHL突变是一种显性遗传突变，会得Von Hippel–Lindau综合症，会诱发眼、脑、脊髓、肾脏、胰脏、和肾上腺多种恶性和良性肿瘤。当第2个拷贝再发生突变时，就会生成肿瘤。
	    		</p>
	    		<li>治疗药物：</li>
	    		<p class="context">
	    			&nbsp;&nbsp;&nbsp;&nbsp;针对VEGF（血管内皮生长因子）的靶向药物可以治疗VHL突变引起的肿瘤。血管内皮生长因子受体的抑制剂：索拉非尼、舒尼替尼、帕唑帕尼、阿西替尼已被美国FDA批准。mTOR抑制剂雷帕霉素类似物依维莫司和西罗莫司脂化或VEGF单克隆抗体贝伐单抗也可能是一种选择。
	    		</p>
	    		<img src="<%=request.getContextPath()%>/images/report/cmp/VHL.png?date=20150629" style="width:450px;">
	    		</div>
	    		<li>检测结果</li>
	    		<p class="context">&nbsp;&nbsp;&nbsp;&nbsp;VHL基因平均测序深度${cmpReport.geneDetectionDetail.VHL.avgCoverage }。</p>
	    		<table class="table table-green table-striped-green table-text-center">
		    		<thead>
		    		  <tr>
		    			<th class="mwidth_Gene">Gene</th>
		    			<th class="mwidth_Ref_Base">Ref_Base</th>
		    			<th class="mwidth_Mut_base">Mut_base%</th>
		    			<th class="mwidth_Depth">Depth</th>
		    			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
		    			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
		    		  </tr>
		    		</thead>
		    		<tbody>
    					<c:forEach items="${cmpReport.geneDetectionDetail.VHL.result}" var="r" varStatus="size">
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
		    						</tr>
			    				</c:otherwise>
			    			</c:choose>
    					</c:forEach>
			    	</tbody>
			    </table>
	    	</ul>
	    </div>
	   </div>
<%--     </s:if> --%>
</section>
<section class="section6 border1 w3cbbs" id="section6">
    <h3>六、附件：检测结果详细信息</h3>
    <table class="table table-green table-striped-green table-text-center table-padding0">
   		<thead style="display:table-header-group;">
   		  <tr>
   			<th class="mwidth_Gene">Gene</th>
   			<th class="mwidth_Ref_Base">Ref_Bawe</th>
   			<th class="mwidth_Mut_base">Mut_base%</th>
   			<th class="mwidth_Depth">Depth</th>
   			<th class="mwidth_CDS_mut_syntax">CDS_mut_syntax</th>
   			<th class="mwidth_AA_mut_syntax">AA_mut_syntax</th>
   		  </tr>
   		</thead>
   		<tbody>
			<c:forEach items="${cmpReport.geneDetectionDetail.ABL1.result}" var="r" varStatus="size">
   				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.AKT1.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.ALK.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.APC.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.ATM.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.BRAF.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.CDH1.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.CDKN2A.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.CSF1R.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.CTNNB1.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.EGFR.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.ERBB2.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.ERBB4.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.EZH2.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.FBXW7.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.FGFR1.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.FGFR2.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.FGFR3.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.FLT3.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.GNA11.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.GNAQ.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.GNAS.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.HNF1A.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.HRAS.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.IDH1.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.IDH2.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.JAK2.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.JAK3.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.KDR.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.KIT.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.KRAS.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.MET.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.MLH1.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.MPL.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.NOTCH1.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.NPM1.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.NRAS.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.PDGFRA.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.PIK3CA.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.PTEN.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.PTPN11.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.RB1.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.RET.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.SMAD4.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.SMARCB1.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.SMO.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.SRC.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.STK11.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.TP53.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
			<c:forEach items="${cmpReport.geneDetectionDetail.VHL.result}" var="r" varStatus="size">
				<c:if test="${!fn:contains(r.gene, '没有发现突变位点')}">
   					<tr>
						<td>${r.gene }</td>
						<td>${r.refBase }</td>
						<td>${r.mutBase }</td>
						<td>${r.depth }</td>
						<td><c:choose><c:when test="${fn:length(r.cdsMutSyntax)>14 }"><c:out value="${fn:substring(r.cdsMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.cdsMutSyntax }</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${fn:length(r.aaMutSyntax)>14 }"><c:out value="${fn:substring(r.aaMutSyntax, 0, 14) }"/></c:when><c:otherwise>${r.aaMutSyntax }</c:otherwise></c:choose></td>
					</tr>
   				</c:if>
			</c:forEach>
    	</tbody>
    </table>
</section>
<section class="border1 w3cbbs section7">
    <h3>七、附件：靶向用药参考信息</h3>
    <div class="h1">Bcr-Abl：</div>
    <p>Bcr-Abl tyrosine-kinase inhibitors (TKI) are the first-line therapy for most patients with chronic myelogenous leukemia (CML). More than 90% of CML cases are caused by a chromosomal abnormality that results in the formation of a so-called Philadelphia chromosome. This abnormality is a consequence of fusion between the Abelson (Abl) tyrosine kinase gene at chromosome 9 and the break point cluster (Bcr) gene at chromosome 22, resulting in a chimeric oncogene (Bcr-Abl) and a constitutively active Bcr-Abl tyrosine kinase that has been implicated in the pathogenesis of CML. Compounds have been developed to selectively inhibit the tyrosine kinase.</p>
    <div class="h2">1.&nbsp;&nbsp;Dasatinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Dasatinib(BMS-354825) is a potent and dual Abl/ Src inhibitor IC50 of ＜1 nM/0.8 nM respectively; also inhibit c-Kit (WT)/c-Kit (D816V) with IC50 of 79 nM/37 nM.</li>
		<li><span class="font-bold">IC50 value: </span>＜1 nM/0.8 nM(Abl/ Src); 79 nM/37 nM (c-Kit WT/c-Kit D816V) [1] [2]</li>
		<li><span class="font-bold">Target:</span>Abl; Src; c-Kit</li>
		<li><span class="font-bold">in vitro:</span> Dasatinib is more effective than imatinib in inhibiting the proliferation of Ba/F3 cells expressing wild-type Bcr-Abl and Bcr-Abl mutants, with the exception of T315I. Dasatinib has a two-log (～325-fold) increased potency relative to imatinib. Dasatinib potently inhibits wild-type Abl kinase and all mutants except T315I over a narrow range. Dasatinib directly targets wild-type and mutant Abl kinase domains and inhibits autophosphorylation and substrate phosphorylation in a concentration-dependent manner. Dasatinib displays 325-fold greater potency compared with imatinib against cells expressing wild-type Bcr-Abl [1]. Dasatinib treatment inhibits Src signaling, decreases growth, and induces cell cycle arrest and apoptosis in a subset of thyroid cancer cells. Treatment with increasing doses of Dasatinib (0.019 μM to 1.25 μM) for 3 days inhibits the growth of the C643, TPC1, BCPAP, and SW1736 cell lines by about 50% at low nanomolar concentrations, while higher concentrations are required to inhibit the growth of the K1 cell line. Treatment with 10 nM or 50 nM Dasatinib results in a 9-22% increase of cells in the G1 population among BCPAP and SW1736 and K1 cells, and a corresponding 7-18% decrease in the percentage of cells in the S phase [3].</li>
		<li><span class="font-bold">in vivo:</span>Dasatinib reverses splenomegaly in LMP2A/MYC double transgenic mice. Dasatinib specifically prevents colony formation by LMP2A expressing bone marrow B cells and decreased spleen size in the TgE mice. Spleen mass is significantly decreased among Dasatinib treated Tg6/λ-MYC mice when compared to the control group. Dasatinib inhibits lymphadenopathy in LMP2A/MYC double transgenic mice. Dasatinib reverses splenomegaly in Rag1KO mice engrafted with tumor cells from LMP2A/MYC double transgenic mice. Dasatinib therapy inhibits Lyn phosphorylation in B lymphocyte tumors expressing LMP2A [4].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> O'Hare T, et al. In vitro activity of Bcr-Abl inhibitors AMN107 and BMS-354825 against clinically relevant imatinib-resistant Abl kinase domain mutants. Cancer Res. 2005 Jun 1;65(11):4500-5.
		</p>
		<p>
			<span class="ml-20">[2].</span> Shah NP, et al. Dasatinib (BMS-354825) inhibits KITD816V, an imatinib-resistant activating mutation that triggers neoplastic growth in most patients with systemic mastocytosis. Blood. 2006 Jul 1;108(1):286-91.
		</p>
		<p>
			<span class="ml-20">[3].</span> Chan CM, et al. Targeted inhibition of Src kinase with dasatinib blocks thyroid cancer growth and metastasis. Clin Cancer Res. 2012 Jul 1;18(13):3580-91.
		</p>
		<p>
			<span class="ml-20">[4].</span> Dargart JL, et al. Dasatinib therapy results in decreased B cell proliferation, splenomegaly, and tumor growth in a murine model of lymphoma expressing Myc and Epstein-Barr virus LMP2A. Antiviral Res. 2012 Jul;95(1):49-56.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp;Nilotinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Nilotinib (AMN-107) is a Bcr-Abl inhibitor with IC50 less than 30 nM.</li>
		<li><span class="font-bold">IC50 value:</span> < 30 nM [1]</li>
		<li><span class="font-bold">Target:</span> Bcr-Abl</li>
		<li><span class="font-bold">in vitro:</span> Nilotinib (AMN107) is significantly more potent against BCR-ABL than imatinib, and is active against many imatinib-resistant BCR-ABL mutants [1]. Nilotinib inhibits proliferation, migration, and actin filament formation, as well as the expression of α-SMA and collagen in activated HSCs. Nilotinib induces apoptosis of HSCs, which is correlated with reduced bcl-2 expression, increases p53 expression, cleavage of PARP, as well as increases expression of PPARγ and TRAIL-R. Nilotinib also induces cell cycle arrest, accompanied by increased expression of p27 and downregulation of cyclin D1. Interestingly, Nilotinib not only inhibits activation of PDGFR, but also TGFRII through Src. Nilotinib significantly inhibits PDGF and TGFβ-simulated phosphorylation of ERK and Akt. Furthermore, PDGF- and TGFβ-activated phosphorylated form(s) of Abl in human HSCs are inhibited by Nilotinib [2].</li>
		<li><span class="font-bold">in vivo:</span>exposure of a variety of BCR-ABL+ cell lines to imatinib and nilotinib results in additive or synergistic cytotoxicity, including testing of a large panel of cells expressing BCR-ABL point mutations causing resistance to imatinib in patients [1]. Nilotinib reduces collagen deposition and α-SMA expression in CCl4 and BDL-induced fibrosis. Nilotinib could induce HSC undergoing apoptosis, which is correlated with downregulation of bcl-2 [2]. AMN107 prolongs survival of mice injected with Bcr-Abl-transformed hematopoietic cell lines or primary marrow cells, and prolongs survival in imatinib-resistant CML mouse models [3].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Weisberg E, et al. Beneficial effects of combining nilotinib and imatinib in preclinical models of BCR-ABL+ leukemias. Blood. 2007 Mar 1;109(5):2112-20.
		</p>
		<p>
			<span class="ml-20">[2].</span> Liu Y, et al. Inhibition of PDGF, TGF-β, and Abl signaling and reduction of liver fibrosis by the small molecule Bcr-Abl tyrosine kinase antagonist Nilotinib. J Hepatol. 2011 Sep;55(3):612-25.
		</p>
		<p>
			<span class="ml-20">[3].</span>  Weisberg E, et al. Characterization of AMN107, a selective inhibitor of native and mutant Bcr-Abl. Cancer Cell. 2005 Feb;7(2):129-41.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp;Ponatinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Ponatinib (AP24534) is a novel, potent multi-target inhibitor of Abl, PDGFRα, VEGFR2, FGFR1 and Src with IC50 of 0.37 nM, 1.1 nM, 1.5 nM, 2.2 nM and 5.4 nM, respectively.</li>
		<li><span class="font-bold">IC50 value:</span> 0.37 nM/1.1 nM/1.5 nM/2.2 nM/5.4 nM (Abl/PDGFRα/VEGFR2/FGFR1/Src) [1]</li>
		<li><span class="font-bold">Target:</span>Abl; multikinase</li>
		<li><span class="font-bold">in vitro:</span>AP24534 potently inhibits native Abl, AblT315I, and other clinically important Abl kinase domain mutants with IC50 of 0.30 nM–2 nM. AP24534 doesn't inhibit Aurora kinase family members, nor does it inhibit insulin receptor or CDK2/cyclin E. AP24534 inhibits proliferation of Ba/F3 cells expressing Bcr-Abl with IC50 of 0.5 nM, as well as Ba/F3 cells expressing a range of Bcr-Abl mutants with IC50 of 0.5 nM–36 nM. The inhibition of proliferation by AP24534 is correlated with induction of apoptosis [2]. In leukemic cell lines containing activated forms of FLT3, KIT, FGFR1, and PDGFRα receptors, AP24534 potently inhibits receptor phosphorylation and cellular proliferation with IC50 of 0.3 nM to 20 nM. In MV4-11 (FLT3-ITD(+/+)) but not RS4;11 (FLT3-ITD(–/–)) AML cells, AP24534 inhibits FLT3 signaling and induced apoptosis at less than 10 nM. AP24534 inhibits viability of primary leukemic blasts from a FLT3-ITD positive AML patient with IC50 of 4 nM but not those from patients with AML expressing native FLT3 [3].</li>
		<li><span class="font-bold">in vivo:</span> In a mouse xenograft model of Ba/F3 cells expressing native Bcr-Abl, AP24534 (2.5 mg/kg and 5 mg/kg) prolongs mice median survival. In the xenograft model of Ba/F3 Bcr-AblT315I, AP24534 (10 mg/kg–50 mg/kg) significantly suppresses tumor growth. AP24534 (30 mg/kg) decreases the phosphorylated Bcr-Abl and phosphorylated CrkL levels in the tumors [2].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> O'Hare T, et al. AP24534, a pan-BCR-ABL inhibitor for chronic myeloid leukemia, potently inhibits the T315I mutant and overcomes mutation-based resistance. Cancer Cell, 2009, 16(5), 401-412.
		</p>
		<p>
			<span class="ml-20">[2].</span>Huang WS, et al. Discovery of 3-[2-(imidazo[1,2-b]pyridazin-3-yl)ethynyl]-4-methyl-N-{4-[(4-methylpiperazin-1-yl)methyl]-3-(trifluoromethyl)phenyl}benzamide (AP24534), a potent, orally active pan-inhibitor of breakpoint cluster region-abelson (BCR-ABL) kinase including the T315I gatekeeper mutant. J Med Chem, 2010, 53(12), 4701-4719.
		</p>
		<p>
			<span class="ml-20">[3].</span>  Gozgit JM, et al. Potent activity of ponatinib (AP24534) in models of FLT3-driven acute myeloid leukemia and other hematologic malignancies. Mol Cancer Ther, 2011, 10(6), 1028-1035.
   		</p>
	</div>
	<div class="h1">AKT1：</div>
	<p>Akt (protein kinase B, PKB) is a serine/threonine-specific protein kinase that plays a key role in multiple cellular processes such as glucose metabolism, apoptosis, cell proliferation, transcription and cell migration. Akt1 is involved in cellular survival pathways, by inhibiting apoptotic processes. Akt2 is an important signaling molecule in the Insulin signaling pathway. The role of Akt3 is less clear, though it appears to be predominantly expressed in the brain. Akt regulates cellular survival and metabolism by binding and regulating many downstream effectors, e.g. Nuclear Factor-κB, Bcl-2 family proteins and MDM2. Akt inhibitors may treat cancers such as neuroblastoma and infection such as HIV and HSV.</p>
	<div class="h2">1.&nbsp;&nbsp;Deguelin</div>
	<ul>
		<li><span class="font-bold">Description:</span>Deguelin, a naturally occurring rotenoid, is known to be an Akt inhibitor and to have an anti-tumor effect on several cancers; decrease levels of phosphorylated Akt.</li>
		<li><span class="font-bold">IC50 value:</span></li>
		<li><span class="font-bold">Target:</span> Akt; Anti-cancer</li>
		<li><span class="font-bold">in vitro:</span> Deguelin in a dose and time dependent manner inhibited the growth of MDA-MB-231, MDA-MB-468, BT-549 and BT-20 cells [1]. Deguelin administration causes a significant HNSCC cell death. Deguelin induces both cell apoptosis and autophagy by modulating multiple signaling pathways in cultured HNSCC cells. Deguelin inhibits Akt signaling, and down-regulates survivin and cyclin-dependent kinase 4 (Cdk4) expressions, by disrupting their association with heat shock protein-90 (Hsp-90). Deguelin induces ceramide production through de novo synthase pathway to promote HNSCC cell death. Importantly, increased ceramide level activates AMP-activated protein kinase (AMPK), which then directly phosphorylates Ulk1 and eventually leads to cell autophagy [2]. Deguelin inhibited the proliferation of MPC-11 cells in a concentration- and time-dependent manner and caused the apoptotic death of MPC-11 cells. Following exposure to deguelin, the phosphorylation of Akt was decreased. The inhibition of cell growth may be associated with decreased levels of phosphorylated Akt. Deguelin-induced apoptosis was characterized by the upregulation of Bax, downregulation of Bcl-2 and activation of caspase-3 [3].</li>
		<li><span class="font-bold">in vivo:</span>Deguelin (2 or 4 mg/kg body weight), when injected intraperitoneally, reduced the in vivo tumor growth of MDA-MB-231 cells transplanted subcutaneously in athymic mice. Moreover it was nontoxic as evident from daily observations on mobility, food and water consumption and comparison of bodyweight and other visceral organ weights with those in control animals at the termination of the study [1]. In the colon cancer xenograft model, the volume of the tumor treated withdeguelin was significantly lower than that of the control, and the apoptotic index for deguelin-treated mice was much higher [4].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Mehta R, et al. Deguelin action involves c-Met and EGFR signaling pathways in triple negative breast cancer cells. PLoS One. 2013 Jun 10;8(6):e65113.
		</p>
		<p>
			<span class="ml-20">[2].</span>Yang YL, et al. Deguelin induces both apoptosis and autophagy in cultured head and neck squamous cell carcinoma cells. PLoS One. 2013;8(1):e54736.
		</p>
		<p>
			<span class="ml-20">[3].</span> Li Z, et al. Deguelin, a natural rotenoid, inhibits mouse myeloma cell growth in vitro via induction of apoptosis. Oncol Lett. 2012 Oct;4(4):677-681.
		</p>
		<p>
			<span class="ml-20">[4].</span> Kang HW, et al. Deguelin, an Akt inhibitor, down-regulates NF-κB signaling and induces apoptosis in colon cancer cells and inhibits tumor growth in mice. Dig Dis Sci. 2012 Nov;57(11):2873-82.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp;3CAI</div>
	<ul>
		<li><span class="font-bold">Description:</span>3CAI is a potent allosteric and specific AKT inhibitor, which exerts efficacy in vitro and in vivo.</li>
		<li><span class="font-bold">IC50 value:</span></li>
		<li><span class="font-bold">Target:</span>Akt</li>
		<li><span class="font-bold">in vitro:</span> 3CAI (1 μM) suppressed only AKT1 kinase activity and the other kinases tested were not affected by 3CAI. 3CAI is a much more potent AKT1 inhibitor than PI3K (60% inhibition at 1 vs 10 μM, respectively). 3CAI, but not I3C, substantially suppressed AKT1 activity as well as AKT2 activity in a dose dependent manner [1]. The AKT-mediated phosphorlyation site of mTOR (Ser2448) and GSK3β (Ser9) were substantially decreased by 3CAI in a time-dependent manner. However, phosphorylation of AKT (Thr308) was not changed. Furthermore, pro-apoptotic marker proteins p53 and p21 were also upregulated by 3CAI after 12 or 24 h of treatment. Additionally, the anti-apoptotic marker protein Bcl2 and AKT-mediated phosphorylation of ASK1 (Ser83) were significantly decreased [1].</li>
		<li><span class="font-bold">in vivo:</span> Mice were orally administered 3CAI at 20 or 30 mg/kg, I3C at 100 mg/kg, or vehicle 5 times a week for 21 days. Treatment of mice with 30 mg/kg of 3CAI significantly suppressed HCT116 tumor growth by 50% relative to the vehicle-treated group. Remarkably, mice seemed to tolerate treatment with these doses of 3CAI without overt signs of toxicity or significant loss of body weight compared with vehicle-treated group. Expression of these AKT-target proteins was strongly suppressed by 30mg/kg of 3CAI in tumor tissues [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Kim DJ, et al. (3-Chloroacetyl)-indole, a novel allosteric AKT inhibitor, suppresses colon cancer growth in vitro and in vivo. Cancer Prev Res (Phila). 2011 Nov;4(11):1842-51.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp;GDC-0068</div>
	<ul>
		<li><span class="font-bold">Description:</span>GDC-0068(RG 7440) is a highly selective pan-Akt inhibitor targeting Akt1/2/3 with IC50 of 5 nM/18 nM/8 nM, 620-fold selectivity over PKA.</li>
		<li><span class="font-bold">IC50 value:</span>5 nM/18 nM/8 nM(Akt1/2/3) [1]</li>
		<li><span class="font-bold">Target:</span>Akt1/2/3</li>
		<li><span class="font-bold">in vitro:</span>  Testing against a broad panel of 230 kinases, GDC-0068 only inhibits 3 kinases by >70% at 1 μM concentration (PRKG1α, PRKG1β, and p70S6K, with IC50 of 98 nM, 69 nM, and 860 nM, respectively). GDC-0068 displays >100-fold selectivity for Akt over PKA with IC50 of 3.1 μM. In LNCaP, PC3 and BT474M1 cells, GDC-0068 treatment inhibits the phosphorylation of the Akt substrate, PRAS40 with IC50 of 157 nM, 197 nM, and 208 nM, respectively. Furthermore, GDC-0068 selectively inhibits cell cycle progression and viability of cancer cell lines driven by Akt signaling, including those with defects in the tumor suppressor PTEN, oncogenic mutations in PIK3CA, and amplification of HER2, with strongest effects in HER2+ and Luminal subtypes.</li>
		<li><span class="font-bold">in vivo:</span> Oral administration of GDC-0068 in PC3 prostate tumor xenografts model induces down-regulation of p-PRAS40. In BT474-Tr xenografts, GDC-0068 treatment reduces pS6 and peIF4G levels, re-localizes FOXO3a to nucleus, and induces feedback upregulation of HER3 and pERK. Administration of GDC-0068 exhibits potent antitumor efficacy in multiple xenograft tumor models, including the PTEN-deficient prostate cancer models LNCaP and PC3, the PIK3CA H1047R mutant breast cancer model KPL-4, and MCF7-neo/HER2 tumor model.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>  Lin K. Abstract DDT02-01: GDC-0068: A novel, selective, ATP-competitive inhibitor of Akt. Cancer Res, 2011, 71(8 Supplement), abstract DDT02-01.
		</p>
		<p>
			<span class="ml-20">[2].</span>Tabernero J, et al. Ann Oncol, 2011, 22(suppl 3), abstract IL33.
		</p>
		<p>
			<span class="ml-20">[3].</span> Heidi M. Savage, et al. Abstract 966: Predictive biomarkers of the AKT inhibitor, GDC-0068, in single agent and combination studies. Cancer Res, 2012, 72(8 Supplement), 966.
		</p>
	</div>
	<div class="h1">ALK：</div>
	<p>ALK (Anaplastic lymphoma kinase) is an enzyme that in humans is encoded by the ALK gene. ALK is a membrane associated tyrosine kinase receptor of the insulin receptor superfamily. The function of the full-length ALK receptor is poorly understood. It has a probable role in the central and peripheral nervous system development and maintenance. ALK is a dependence receptor, which may exert antagonist functions, proapoptotic or antiapoptotic, depending on the absence or presence of a ligand. Dependence receptors have a potential role in cancer and development. Ligands available for this demonstration were agonist anti-ALK antibodies. ALK is still an orphan receptor, given the high level of controversy about pleiotrophin and midkine, which have been proposed as ligands by Stoica et al.</p>
	<div class="h2">1.&nbsp;&nbsp;LDK378 dihydrochloride</div>
	<ul>
		<li><span class="font-bold">Description:</span>LDK378 2Hcl (Ceritinib) is potent inhibitor against ALK with IC50 of 0.2 nM, shows 40- and 35-fold selectivity against IGF-1R and InsR, respectively.</li>
		<li><span class="font-bold">IC50 value:</span> 0.2 nM [1]</li>
		<li><span class="font-bold">Target:</span>ALK</li>
		<li><span class="font-bold">in vitro:</span> LDK378 shows great anti-proliferative activity in Ba/F3-NPM-ALK and Karpas290 cells with IC50 of 26.0 nM and 22.8 nM, compared with IC50 of 319.5 nM and 2477 nM in Ba/F3-Tel-InsR and Ba/F3-WT cells [1].</li>
		<li><span class="font-bold">in vivo:</span>  LDK378 is designed to reduce the possibility of forming reactive metabolites and shows undetectable levels of glutathione (GSH) adducts (<1%) in liver microsomes. LDK378 has relatively good metabolic stability, with moderate CYP3A4 (Midazolam substrate) inhibition and hERG inhibition. LDK378 exhibits low plasma clearance in animals (mouse, rat, dog and monkey) compared to liver blood flow, with the oral bioavailability of above 55% in mouse, rat, dog and monkey. LDK378 induces a dose-dependent growth inhibition and tumor regression in the Karpas299 and H2228 rat xenograft models, with no body-weight loss. LDK378 shows no impact on insulin levels or plasma glucose utilization in the mouse upon chronic dosing up to 100 mg/kg [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>  Marsilje TH, et al. Synthesis, structure-activity relationships, and in vivo efficacy of the novel potent and selective anaplastic lymphoma kinase (ALK) inhibitor5-chloro-N2-(2-isopropoxy-5-methyl-4-(piperidin-4-yl)phenyl)-N4-(2-(isopropylsulfonyl)phenyl)pyrimidine-2,4-diamine (LDK378) currently in phase 1 and phase 2 clinical trials. J Med Chem. 2013, Jun 6.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp;Crizotinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Crizotinib is inhibitor of the c-Met kinase and the NPM-ALK. Crizotinib inhibited cell proliferation in ALK-positive ALCL cells (IC50s=30 nM). Crizotinib is useful in treatment of anaplastic large-cell lymphoma.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Camidge DR, Bang YJ, Kwak EL, Iafrate AJ, Varella-Garcia M, Fox SB, Riely GJ, Solomon B, Ou SH, Kim DW, Salgia R, Fidias P, Engelman JA, Gandhi L, J?nne PA, Costa DB, Shapiro GI, Lorusso P, Ruffner K, Stephenson P, Tang Y, Wilner K, Clark JW, Shaw AT.Activity and safety of crizotinib in patients with ALK-positive non-small-cell lung cancer: updated results from a phase 1 study.Lancet Oncol. 2012 Sep 3.
			<br><br>Abstract<br>
			BACKGROUND: ALK fusion genes occur in a subset of non-small-cell lung cancers (NSCLCs). We assessed the tolerability and activity of crizotinib in patients with NSCLC who were prospectively identified to have an ALK fusion within the first-in-man phase 1 crizotinib study. METHODS: In this phase 1 study, patients with ALK-positive stage III or IV NSCLC received oral crizotinib 250 mg twice daily in 28-day cycles. Endpoints included tumour responses, duration of response, time to tumour response, progression-free survival (PFS), overall survival at 6 and 12 months, and determination of the safety and tolerability and characterisation of the plasma pharmacokinetic profile of crizotinib after oral administration. Responses were analysed in evaluable patients and PFS and safety were analysed in all patients. This study is registered with ClinicalTrials.gov, number NCT00585195...
		</p>
		<p>
			<span class="ml-20">[2].</span>Sparidans RW, Tang SC, Nguyen LN, Schinkel AH, Schellens JH, Beijnen JH.Liquid chromatography-tandem mass spectrometric assay for the ALK inhibitor crizotinib in mouse plasma.J Chromatogr B Analyt Technol Biomed Life Sci. 2012 Sep 15;905:150-4. Epub 2012 Aug 21.
			<br><br>Abstract<br>
			A quantitative bioanalytical liquid chromatography-tandem mass spectrometric (LC-MS/MS) assay for the ALK inhibitor crizotinib was developed and validated. Plasma samples were pre-treated using protein precipitation with acetonitrile containing crizotinib-(13)C(2)-(2)H(5) as internal standard. The extract was directly injected into the chromatographic system after dilution with water. This system consisted of a sub-2μm particle, trifunctional bonded octadecyl silica column with a gradient using 0.1% (v/v) of ammonium hydroxide in water and methanol. The eluate was transferred into the electrospray interface with positive ionization and the analyte was detected in the selected reaction monitoring mode of a triple quadrupole mass spectrometer. The assay was validated in a 10-10,000ng/ml calibration range with r(2)=0.99980±0.00014 for double logarithmic linear regression (n=5). Within day precisions (n=6) were 3.4-4.8%, between day (3 days; n=18) precisions 3.6-4.9%. Accuracies were between 107% and 112% for the whole calibration range. The drug was sufficiently stable under all relevant analytical conditions. Oxidative metabolites of crizotinib were monitored semi-quantitatively. Finally, the assay was successfully used to assess drug pharmacokinetics in mice.
		</p>
		<p>
			<span class="ml-20">[3].</span> Crystal AS, Shaw AT.Variants on a Theme: A Biomarker of Crizotinib Response in ALK-Positive Non-Small Cell Lung Cancer?Clin Cancer Res. 2012 Sep 1;18(17):4479-81. Epub 2012 Aug 21.
			<br><br>Abstract<br>
			Anaplastic lymphoma kinase (ALK) gene rearrangements are found in approximately 5% of non-small cell lung carcinoma patients and confer sensitivity to ALK inhibitors such as crizotinib. The particular ALK fusion expressed may have an impact on protein stability and sensitivity to crizotinib, and this may underlie the heterogeneity in responses observed in the clinic. Clin Cancer Res; 18(17); 4479-81. ?2012 AACR.
		</p>
		<p>
			<span class="ml-20">[4].</span> Okamoto W, Okamoto I, Arao T, Kuwata K, Hatashita E, Yamaguchi H, Sakai K, Yanagihara K, Nishio K, Nakagawa K.Antitumor action of the MET tyrosine kinase inhibitor crizotinib (PF-02341066) in gastric cancer positive for MET amplification.Mol Cancer Ther. 2012 Jul;11(7):1557-64. Epub 2012 Jun 22.
			<br><br>Abstract<br>
			Therapeutic strategies that target the tyrosine kinase MET hold promise for gastric cancer, but the mechanism underlying the antitumor activity of such strategies remains unclear. We examined the antitumor action of the MET tyrosine kinase inhibitor crizotinib (PF-02341066) in gastric cancer cells positive or negative for MET amplification. Inhibition of MET signaling by crizotinib or RNA interference-mediated MET depletion resulted in induction of apoptosis accompanied by inhibition of AKT and extracellular signal-regulated kinase phosphorylation in gastric cancer cells with MET amplification but not in those without it, suggesting that MET signaling is essential for the survival of MET amplification-positive cells. Crizotinib upregulated the expression of BIM, a proapoptotic member of the Bcl-2 family, as well as downregulated that of survivin, X-linked inhibitor of apoptosis protein (XIAP), and c-IAP1, members of the inhibitor of apoptosis protein family, in cells with MET amplification. Forced depletion of BIM inhibited crizotinib-induced apoptosis, suggesting that upregulation of BIM contributes to the proapoptotic effect of crizotinib. Crizotinib also exhibited a marked antitumor effect in gastric cancer xenografts positive for MET amplification, whereas it had little effect on those negative for this genetic change. Crizotinib thus shows a marked antitumor action both in vitro and in vivo specifically in gastric cancer cells positive for MET amplification.
		</p>
		<p>
			<span class="ml-20">[5].</span> Fallet V, Toper C, Antoine M, Cadranel J, Wislez M.[Management of crizotinib, a new individualized treatment].Bull Cancer. 2012 Jul-Aug;99(7-8):787-91.
			<br><br>Abstract<br>
			Crizotinib, an inhibitor of the receptor tyrosine kinase anaplastic lymphoma kinase (ALK), achieves response rates of 57 % at eight weeks in patients with stage IV non-small cell lung cancer with ALK rearrangements. With such results, the crizotinib followed an accelerated procedure in the United States and obtained the Food and Drug Administration (FDA) approval based on the results of phase I studies. The results should be confirmed with one phase II study and two phase III studies in patients with ALK rearrangements. In France, the Commission of Authorization for Marketing has granted an Authorization of Temporary Use (ATU) for cohort on the 15 December 2011 to allow its administration in patients before marketing authorization.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp; CH5424802</div>
	<ul>
		<li><span class="font-bold">Description:</span>CH5424802(AF 802; Alectinib) is a potent ALK inhibitor with IC50 of 1.9 nM, sensitive to L1196M mutation.</li>
		<li><span class="font-bold">IC50 value:</span>  1.9 nM [1]</li>
		<li><span class="font-bold">Target:</span> ALK</li>
		<li><span class="font-bold">in vitro:</span> The dissociation constant (KD) value of CH5424802 for ALK in an ATP-competitive manner is 2.4 nM. CH5424802 has substantial inhibitory potency against both native ALK and L1196M with Ki of 0.83 nM and 1.56 nM, respectively. CH5424802 prevents autophosphorylation of ALK in NCI-H2228 NSCLC cells expressing EML4-ALK. CH5424802 also suppresses the phosphorylation of STAT3 and AKT, but not of ERK1/2. CH5424802 completely inhibits the phosphorylation of STAT3 at Tyr705. CH5424802 is preferentially efficacious against NCI-H2228 cells expressing EML4-ALK, but not ALK fusion-negative NSCLC cell lines, including HCC827 cells (EGFR exon 19 deletion), A549 cells (KRAS mutant), or NCI-H522 cells (EGFR wild-type, KRAS wild-type, and ALK wild-type) in monolayer culture. CH5424802 elicits an apoptotic marker—caspase-3/7-like activation—in NCI-H2228 spheroid cells. CH5424802 blocks the growth of two lymphoma lines, KARPAS-299 and SR, with NPM-ALK fusion protein but does not influence the growth of an HDLM-2 lymphoma line without ALK fusion [1]. CH5424802 displays high target selectivity and the stronger anti-proliferative activity against KARPAS-299. CH5424802 inhibits KAPRAS-299 with an IC50 of 3 nM, and KDR with IC50 of 1.4 μM. The metabolic stability of CH5424802 is very high [2].</li>
		<li><span class="font-bold">in vivo:</span> Oral administration of CH5424802 dose-dependently inhibits tumor growth with an ED50 of 0.46 mg/kg and tumor regression. Treatment of 20 mg/kg CH5424802 reveals rapid tumor regression by 168%, the tumor volume in any mouse is <30 mm3 after 11 days of treatment (at day 28), a potent antitumor effect is maintained, and tumor regrowth does not occur throughout the 4-week drug-free period. The half-life and the oral bioavailability of CH5424802 in mice are 8.6 hours and 70.8%, respectively. At a repeated dose of 6 mg/kg, the mean plasma levels reached 1.7, 1.5, and 0.3 nM at 2, 7, and 24 hours post-dose, respectively. Administration of CH5424802 leads to tumor growth prevention and tumor regression. Tumor growth inhibition at 20 mg/kg is 119% for KARPAS-299 and 104% for NB-1 on day 20. CH5424802 inhibits the phosphorylation of STAT3 in a dose-dependent manner (2–20 mg/kg). A partial decrease in AKT phosphorylation is also observed in CH5424802-treated xenograft tumors [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Sakamoto H, et al. CH5424802, a selective ALK inhibitor capable of blocking the resistant gatekeeper mutant. Cancer Cell. 2011, 19(5), 679-690.
		</p>
		<p>
			<span class="ml-20">[2].</span>Kinoshita K, et al. Design and synthesis of a highly selective, orally active and potent anaplastic lymphoma kinase inhibitor (CH5424802). Bioorg Med Chem. 2012, 20(3), 1271-1280.
		</p>
	</div>
	<div class="h1">APC：</div>
	<p>APC(Anaphase-Promoting Complex) is an E3 ubiquitin ligase that marks target cell cycle proteins for degradation by the 26S proteasome. The APC/C is a large complex of 11–13 subunit proteins, including a cullin (Apc2) and RING (Apc11) subunit much like SCF. The APC/C's main function is to trigger the transition from metaphase to anaphase by tagging specific proteins for degradation. The two proteins of most importance that get degraded in this process as substrates of the APC/C are securin and S and M cyclins. Securin releases separase, a protease, after being degraded which in turn triggers the cleavage of cohesin, the protein complex that binds sister chromatids together. During metaphase, sister chromatids are linked by intact cohesin complexes. When securin undergoes ubiquitination by the APC/C and releases separase, which degrades cohesin, sister chromatids become free to move to opposite poles for anaphase. The APC/C also targets the mitotic cyclins for degradation, resulting in the inactivation of M-CdK (mitotic cyclin-dependent kinase) complexes, promoting exit from mitosis and cytokinesis.</p>
	<div class="h2">1.&nbsp;&nbsp;TAME</div>
	<ul>
		<li><span class="font-bold">Description:</span>TAME is a small molecule anaphase-promoting complex/cyclosome (APC) inhibitor.</li>
		<li><span class="font-bold">IC50 value:</span> 12 μM ( inhibits cyclin proteolysis in mitotic Xenopus egg extract)</li>
		<li><span class="font-bold">Target:</span> APC</li>
		<li><span class="font-bold">in vitro:</span> TAME at concentration of 1-200 μM arrests interphase extract treated with recombinant cyclin B1/Cdc2 complex in mitosis, with stable cyclin B1 and phosphorylated Cdc27. TAME at concentration of 200 μM dramaticly inhibits the ubiquitin ligase activity of the Anaphase-Promoting Complex (APC), accompanied by reduced binding of Cdh1 to APC. TAME addition to interphase extract reduces Cdc20 association with the APC in a dose-dependent manner partly by binding directly to APC, and the contribution motif is the C-terminal isoleucine-arginine (IR) tail on APC. TAME is hydrolysed by trypsin with Km of 0.328 mM. TAME accelerates the ATP hydrolysis process about 12-fold. TAME interacts with β and γ phosphate and the adenine ring of ATP by the guanidinium group and the aromatic ring. TAME at concentration of 50 mM inhibits nutrient-induced germination and pressure-induced germination at 600 MPa in Bacillus subtilis. TAME induces a concentration dependent contractile response on ileal strips with EC50 of 4.3 x 103 M.</li>
		<li><span class="font-bold">in vivo:</span> N/A</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Zeng X et al. Pharmacologic inhibition of the anaphase-promoting complex induces a spindle checkpoint-dependent mitotic arrest in the absence of spindle damage. Cancer Cell. 2010 Oct 19;18(4):382-95.
		</p>
		<p>
			<span class="ml-20">[2].</span>Paul VD, Rajagopalan SS, Sundarrajan S, George SE, Asrani JY, Pillai R, Chikkamadaiah R, Durgaiah M, Sriram B, Padmanabhan S.A novel bacteriophage Tail-Associated Muralytic Enzyme (TAME) from Phage K and its development into a potent antistaphylococcal protein.BMC Microbiol. 2011 Oct 11;11:226. 
		</p>
	</div>
	<div class="h1">ATM：</div>
	<p>ATM/ATR are members of the PI-3 family of serine-threonine kinases and function as essential links between the sensors and effectors of the DNA damage response. The roles of ATM and ATR partially overlap and are cooperative; however they are also known to play distinct roles in protecting the cell from DNA damage. ATM is mostly responsible for sending signals from DSBs (double-strand breaks) induced by ionizing radiation while the closely related ATR responds to UV damage or stalled replication forks. ATM and ATR are known to phosphorylate common as well as specific substrates to activate checkpoint signaling. The G1, S, and G2 cell cycle checkpoints are primarily regulated by the ATM (ataxia telangiectasia, mutated) and ATR (ATM and Rad3-related) protein kinases.</p>
	<div class="h2">1.&nbsp;&nbsp;CP-466722</div>
	<ul>
		<li><span class="font-bold">Description:</span>CP-466722 is rapidly reversible potential ATM kinase inhibitor.</li>
		<li><span class="font-bold">IC50 value:</span></li>
		<li><span class="font-bold">Target:</span> ATM</li>
		<li>CP-466722 is non-toxic and does not inhibit PI3K or PI3K-like protein kinase family members in cells. CP-466722 inhibited cellular ATM-dependent phosphorylation events and disruption of ATM function resulted in characteristic cell cycle checkpoint defects. Inhibition of cellular ATM kinase activity was rapidly and completely reversed by removing CP-466722. Interestingly, clonogenic survival assays demonstrated that transient inhibition of ATM is sufficient to sensitize cells to ionizing radiation and suggests that therapeutic radiosensitization may only require ATM inhibition for short periods of time. The ability of CP-466722 to rapidly and reversibly regulate ATM activity provides a new tool to ask questions about ATM function that could not easily be addressed using genetic models or RNA interference technologies.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Rainey MD et al (2008) Transient inhibition of ATM kinase is sufficient to enhance cellular sensitivity to ionizing radiation. Cancer Res. 2008 Sep 15;68(18):7466-74.
		</p>
		<p>
			<span class="ml-20">[2].</span>Kuroda S, Urata Y, Fujiwara T.Ataxia-telangiectasia mutated and the Mre11-Rad50-NBS1 complex: promising targets for radiosensitization.Acta Med Okayama. 2012;66(2):83-92.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp;KU-55933</div>
	<ul>
		<li><span class="font-bold">Description:</span>KU-55933 is a novel, specific, and potent inhibitor of the ATM kinase withIC50 12.9 nM and a Ki of 2.2 nM[1].KU-55933 is highly selective for ATM as compared to DNA-PK, PI3K/PI4K, ATR and mTOR. KU-55933 inhibits cancer cell proliferation by inducing G1 cell cycle arrest. Furthermore, KU-55933 completely abrogates rapamycin-induced feedback activation of Akt[2].Suppression of ATM-dependent STAT3 activation by KU-55933 enhances TRAIL-mediated apoptosis through up-regulation of surface DR5 expression, whereas suppression of both STAT3 and NF-κBappeares to be involved in down-regulation of cFLIP accompanied by an additional increase in apoptotic levels[3</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Hickson I, etal.Identification and characterization of a novel and specific inhibitor of the ataxia-telangiectasia mutated kinase ATM.Cancer Res.64(24):9152-9(2004).
		</p>
		<p>
			<span class="ml-20">[2].</span>Yan Li, et al.The ATM Inhibitor KU-55933 Suppresses Cell Proliferation and Induces Apoptosis by Blocking Akt In Cancer Cells with OveractivatedAkt. Cancer Research.9(1):113-25(2010).
		</p>
		<p>
			<span class="ml-20">[3].</span>Ivanov VN, et al.Inhibition of ataxia telangiectasia mutated kinase activity enhances TRAIL-mediated apoptosis in human melanoma cells.69(8):3510-9(2009).
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp;KU-60019</div>
	<ul>
		<li><span class="font-bold">Description:</span>KU-60019 is an improved analogue of KU-55933, with IC50 of 6.3 nM for ATM.</li>
		<li><span class="font-bold">IC50 value:</span> 6.3 nM</li>
		<li><span class="font-bold">Target:</span> ATM</li>
		<li><span class="font-bold">in vitro:</span> KU-60019 effectively radiosensitizes human glioma cells with dose-enhancement ratio of 1.7 and 4.4 at 1 μM and 10 μM, respectively, and also radiosensitizes the normal fibroblasts but not the A-T fibroblasts. KU-60019 treatment (3 μM) blocks basal and insulin-induced AKT S473 phosphorylation by 70% and ~50%, respectively, and completely reduces radiation-induced AKT phosphorylation below the level of control. The effect of KU-60019 on AKT S473 phosphorylation can be seen in glioma cell lines and normal fibroblasts but not in A-T (h-TERT) cells, and can be significantly blocked by phosphatase inhibitor okadaic acid, suggesting a critical role of ATM kinase in regulating AKT phosphorylation via unknown phosphatase. Consistent with the inhibition of prosurvival AKT signaling, KU-60019 at 3 μM significantly inhibits migration and invasion of human glioma U87 cells by >70% and ~60%, respectively, as well as U1242 cells by >50% and ~60% respectively.</li>
		<li><span class="font-bold">in vivo:</span> In orthotopic glioma U1242/luc-GFP xenograft models, the combination of KU-60019 and radiation significantly increases survival of mice than KU-60019 alone, radiation alone, or no treatment. In addition, p53-mutant gliomas is much more sensitive to KU-60019 radiosensitization than wild-type glioma.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Golding SE et al. Improved ATM kinase inhibitor KU-60019 radiosensitizes glioma cells, compromises insulin, AKT and ERK prosurvival signaling, and inhibits migration and invasion. Mol Cancer Ther. 2009 Oct;8(10):2894-902.
		</p>
		<p>
			<span class="ml-20">[2].</span>Golding SE, Rosenberg E, Adams BR, Wignarajah S, Beckta JM, O'Connor MJ, Valerie K.Dynamic inhibition of ATM kinase provides a strategy for glioblastoma multiforme radiosensitization and growth control.Cell Cycle. 2012 Mar 15;11(6):1167-73. Epub 2012 Mar 15.
		</p>
		<p>
			<span class="ml-20">[3].</span> Raso A, Vecchio D, Cappelli E, Ropolo M, Poggi A, Nozza P, Biassoni R, Mascelli S, Capra V, Kalfas F, Severi P, Frosina G.Characterization of Glioma Stem Cells Through Multiple Stem Cell Markers and Their Specific Sensitization to Double-Strand Break-Inducing Agents by Pharmacological Inhibition of Ataxia Telangiectasia Mutated Protein.Brain Pathol. 2012 Sep;22(5):677-688.
		</p>
	</div>
	<div class="h1">BRAF</div>
	<div class="h2">1.&nbsp;&nbsp;Dabrafenib Mesylate</div>
	<ul>
		<li><span class="font-bold">Description:</span>Dabrafenib Mesylate(GSK-2118436 Mesylate) is a novel, potent, and selective Raf kinase inhibitor that is capableof inhibiting the kinase activity of wild-type B-Raf, B-RafV600Eand c-Raf with IC50 values of 3.2, 0.8, and 5.0 nM, respectively.</li>
		<li><span class="font-bold">IC50 value:</span> 3.2/0.8/5.0 nM (B-Raf/B-RafV600E/ c-Raf) [1]</li>
		<li><span class="font-bold">Target:</span> B-RafV600E</li>
		<li><span class="font-bold">in vitro:</span> Dabrafenib is a novel, potent, and selective Raf kinase inhibitor that is capableof inhibiting the kinase activity of wild-type B-Raf, B-RafV600Eand c-Raf with IC50 values of 3.2, 0.8, and 5.0 nM, respectively.Kinase panel screening for over 270 kinases has indicated that this inhibitor is selective for Raf kinase, with  400 fold selectivitytowards B-Raf over 91% of the other kinases tested. Specificcellular inhibition of B-RafV600E kinase by this inhibitor leadsto decreased ERK phosphorylation and inhibition of cell proliferationby an initial arrest in the G1 phase of the cell cycle, followedby cell death. This inhibition is selective for cancer cellsthat specifically encode the mutation for B-RafV600E [1]. The combination of GSK2118436 and GSK1120212 effectively inhibited cell growth, decreased ERK phosphorylation, decreased cyclin D1 protein, and increased p27(kip1) protein in the resistant clones. Moreover, the combination of GSK2118436 or GSK1120212 with the phosphoinositide 3-kinase/mTOR inhibitor GSK2126458 enhanced cell growth inhibition and decreased S6 ribosomal protein phosphorylation in these clones [2].</li>
		<li><span class="font-bold">in vivo:</span> Oral compoundadministration inhibits the growth of B-RafV600E mutant melanoma(A375P) and colon cancer (Colo205) human tumor xenografts, growingsubcutaneously in immuno-compromised mice [1]. We used an accelerated dose titration method, with the first dose cohort receiving 12 mg dabrafenib daily in a 21-day cycle. Once doses had been established, we expanded the cohorts to include up to 20 patients. On the basis of initial data, we chose a recommended phase 2 dose [3].</li>
		<li><span class="font-bold">Toxicity:</span> Treatment-related adverse events of grade 3 or worse occurred in 38 (22%) patients. Eleven (6%) patients developed squamous-cell carcinoma (five [6%] patients in cohort A, of whom one also had keratoacanthoma; six [7%] in cohort B). Four grade 4 treatment-related adverse events occurred in cohort A: one blood amylase increase, one convulsion, one lipase increase, and one neutropenia. Two grade 4 events occurred in cohort B: one agranulocytosis and one intracranial haemorrhage. 51 (30%) patients had a serious adverse event. The three most frequent serious adverse events were pyrexia (ten [6%] patients), intracranial haemorrhage (ten [6%]; one treatment-related), and squamous-cell carcinoma (11 [6%]) [4].</li>
		<li><span class="font-bold">Clinical trial:</span> BREAK-2: A Study of GSK-2118436 in Previously Treated BRAF Mutant Metastatic Melanoma. Phase 2</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Sylvie Laquerre, et al. Abstract B88: A selective Raf kinase inhibitor induces cell death and tumor regression of human cancer cell lines encoding B-RafV600E mutation. Molecular Cancer Therapeutics: December 2009; Volume 8, Issue 12, Supplement 1
		</p>
		<p>
			<span class="ml-20">[2].</span> Greger JG, et al. Combinations of BRAF, MEK, and PI3K/mTOR inhibitors overcome acquired resistance to the BRAF inhibitor GSK2118436 dabrafenib, mediated by NRAS or MEK mutations. Mol Cancer Ther. 2012 Apr;11(4):909-20.
		</p>
		<p>
			<span class="ml-20">[3].</span>Long GV, et al. Dabrafenib in patients with Val600Glu or Val600Lys BRAF-mutant melanoma metastatic to the brain (BREAK-MB): a multicentre, open-label, phase 2 trial. Lancet Oncol. 2012 Nov;13(11):1087-95.
		</p>
		<p>
			<span class="ml-20">[4].</span> Falchook GS, et al. Dabrafenib in patients with melanoma, untreated brain metastases, and other solid tumours: a phase 1 dose-escalation trial. Lancet. 2012 May 19;379(9829):1893-901.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp; BRAF inhibitor</div>
	<ul>
		<li><span class="font-bold">Description:</span>BRAF inhibitor is a potent BRAF inhibitor.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Spevak, Wayne et al. Preparation of pyrrolo[2,3-b]pyridine derivatives as kinase modulators PCT Int. Appl. (2008), WO 2008079906 A1 20080703.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp; Dabrafenib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Dabrafenib (GSK2118436) is a mutant BRAFV600 specific inhibitor with IC50 of 0.8 nM, with 4- and 6-fold less potency against B-Raf(wt) and c-Raf, respectively.</li>
		<li><span class="font-bold">IC50 value:</span> 0.8 nM (BRAFV600) [1]</li>
		<li><span class="font-bold">Target:</span> BRAFV600</li>
		<li><span class="font-bold">in vitro:</span> Dabrafenib is selective for Raf kinase, with 400 fold selectivity towards B-Raf over 91% of the other kinases tested. Dabrafenib inhibits B-RafV600E kinase, leading to decreased ERK phosphorylation and inhibition of cell proliferationby an initial arrest in the G1 phase of the cell cycle in cancer cells that specifically encode the mutation for B-RafV600E.</li>
		<li><span class="font-bold">in vivo:</span> Dabrafenib (orally administrated) inhibits the growth of B-RafV600E mutant melanoma (A375P) and colon cancer (Colo205) human tumor xenografts, growing subcutaneously in immuno-compromised mice.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Sylvie Laquerre, et al. 2009, EORTC International Conference. Abst B88.
		</p>
		<p>
			<span class="ml-20">[2].</span>  Greger JG, et al. Combinations of BRAF, MEK, and PI3K/mTOR inhibitors overcome acquired resistance to the BRAF inhibitor GSK2118436 dabrafenib, mediated by NRAS or MEK mutations. Mol Cancer Ther, 2012, 11(4), 909-920.
		</p>
	</div>
	<div class="h1">CSF1R:</div>
	<p>c-FMS (CSF1R) is located at the cell plasma membrane. c-FMS (CSF1R) is the receptor for the ligand colony stimulating factor-1 (CSF1). CSF1R is an integral transmembrane glycoprotein that exhibits ligand-induced tyrosine-specific protein kinase activity, which triggers a signaling cascade eventually affecting transcription of CSF1-responsive genes. CSF1R tyrosine phosphorylation is induced upon binding of CSF1, leading to activation of Ras / Erk and class I-A phosphatidylinositol 3-kinase signaling pathways, which in turn activate the signal transducers and activators of transcription (STATs) pathways, specifically STAT1, STAT3, and STAT5. CSF1R activation by CSF1 results in increased growth, proliferation and differentiation</p>
	<div class="h2">1.&nbsp;&nbsp;Ki20227</div>
	<ul>
		<li><span class="font-bold">Description:</span>Ki-20227 is a highly selective c-Fms tyrosine kinase(CSF1R) inhibitor with IC50 value of 2 nM; 6 fold and > 100 fold selectivity over VEGFR2(IC50=12 nM) and c-Kit/PDGFRβ(IC50=451/217 nM), respectively.</li>
		<li><span class="font-bold">IC50 value:</span> </li>
		<li><span class="font-bold">Target:</span> CSF1R</li>
		<li><span class="font-bold">in vitro:</span> Ki20227 did not inhibit other kinases tested, such as fms-like tyrosine kinase-3, epidermal growth factor receptor, or c-Src (c-src proto-oncogene product). Ki20227 was also found to inhibit the M-CSF-dependent growth of M-NFS-60 cells but not the M-CSF-independent growth of A375 human melanoma cells in vitro [1]. Ki20227 inhibited M-CSF-dependent reactions, such as lipopolysaccharide-induced tumor necrosis factor-alpha production, which were enhanced by M-CSF in vitro [2].</li>
		<li><span class="font-bold">in vivo:</span> Ki20227 decreased the number of tartrate-resistant acid phosphatase-positive osteoclast-like cells on bone surfaces in ovariectomized (ovx) rats [1]. In addition, the number of CD11b(+), Gr-1(+), and Ly-6G(+) cells in the spleen decreased in the Ki20227-treated mice, and the CII-induced cytokine production in splenocytes isolated from the Ki20227-treated arthritic mice was also reduced [2]. Ki20227 treatments inhibited the turn-over/expansion of myeloid cells provoked by the immunization and subsequent MOG-specific T cell responses in our EAE animal model [3].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Ohno H, et al. A c-fms tyrosine kinase inhibitor, Ki20227, suppresses osteoclast differentiation and osteolytic bone destruction in a bone metastasis model. Mol Cancer Ther. 2006 Nov;5(11):2634-43.
		</p>
		<p>
			<span class="ml-20">[2].</span>   Ohno H, et al. The orally-active and selective c-Fms tyrosine kinase inhibitor Ki20227 inhibits disease progression in a collagen-induced arthritis mouse model. Eur J Immunol. 2008 Jan;38(1):283-91.
		</p>
		<p>
			<span class="ml-20">[3].</span>   Uemura Y, et al. The selective M-CSF receptor tyrosine kinase inhibitor Ki20227 suppresses experimental autoimmune encephalomyelitis. J Neuroimmunol. 2008 Mar;195(1-2):73-80.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp; OSI-930</div>
	<ul>
		<li><span class="font-bold">Description:</span>：OSI-930 is a potent inhibitor of Kit, KDR and CSF-1R with IC50 of 80 nM, 9 nM and 15 nM, respectively; also potent to Flt-1, c-Raf and Lck and low activity against PDGFRα/β, Flt-3 and Abl.</li>
		<li><span class="font-bold">IC50 value:</span>9 nM(VEGFR2); 15 nM(CSF1R); 80 nM (Kit activated) [1]</li>
		<li><span class="font-bold">Target:</span> VEGFR2/Kit/CSF1R</li>
		<li><span class="font-bold">in vitro:</span> OSI-930 inhibits the cell proliferation in the HMC-1 cell line with IC50 of 14 nM without significant effect on growth of the COLO-205 cell line that does not express a constitutively active mutant receptor tyrosine kinase. Moreover, OSI-930 also induces apoptosis in HMC-1 cell line with EC50 of 34 nM [1]. A recent study shows that OSI-930 inactivates purified, recombinant cytochrome P450 (P450) 3A4 with a Ki of 24 μM in a time- and concentration-dependent mode [2].</li>
		<li><span class="font-bold">in vivo:</span> OSI-930, administrated at the maximally efficacious dose of 200 mg/kg by oral gavage, exhibits potent antitumor activity in a broad range of preclinical xenograft models including HMC-1, NCI-SNU-5, COLO-205 and U251 xenograft models [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Garton AJ, et al. OSI-930: a novel selective inhibitor of Kit and kinase insert domain receptor tyrosine kinases with antitumor activity in mouse xenograft models. Cancer Res. 2006, 66(2):1015-1024.
		</p>
		<p>
			<span class="ml-20">[2].</span>  Lin HL, et al. Inactivation of cytochrome P450 (P450) 3A4 but not P450 3A5 by OSI-930, a thiophene-containing anticancer drug. Drug Metab Dispos. 2011, 39(2), 345-350.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp;BLZ945</div>
	<ul>
		<li><span class="font-bold">Description:</span> BLZ945 is a highly selective and brain-penetrant inhibitor of CSF1R with IC50 of 1 nM; >3200-fold higher than its affinity for other kinases.</li>
		<li><span class="font-bold">IC50 value:</span> 1 nM [1]</li>
		<li><span class="font-bold">Target:</span> CSF-1R inhibitor</li>
		<li><span class="font-bold">in vitro:</span> Treatment of bone marrow-derived macrophages (BMDMs) with BLZ945 inhibited CSF-1-dependent proliferation (EC50=67nM), and decreased CSF-1R phosphorylation, similar to CSF-1R antibody blockade. BLZ945 also reduced viability of CRL-2467 microglia, Ink4a/Arf/BMDMs (PDG genetic background), and NOD/SCID BMDMs. Importantly, BLZ945 treatment in culture did not affect proliferation of any PDG-derived tumor[1].</li>
		<li><span class="font-bold">in vivo:</span> BLZ945-treated TAMs retained CSF-1R expression in vivo, and there were no significant differences compared to vehicle-treated PDG gliomas. four human lines responded to BLZ945 in vivo, showing a clear histological response, and significantly reduced tumor growth and invasion [1]. BLZ945 decreases primary mammary tumor growth but does not affect lung metastatic tumor burden in MMTV-PyMT mice [2].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Pyonteck SM, et al. CSF-1R inhibition alters macrophage polarization and blocks glioma progression. Nat Med. 2013 Oct;19(10):1264-72.
		</p>
		<p>
			<span class="ml-20">[2].</span>  Strachan DC, et al. CSF1R inhibition delays cervical and mammary tumor growth in murine models by attenuating the turnover of tumor-associated macrophages and enhancing infiltration by CD8+ T cells. Oncoimmunology. 2013 Dec 1;2(12):e26968.
		</p>
	</div>
	<div class="h1">EGFR</div>
	<p>EGFR (The epidermal growth factor receptor; ErbB-1; HER1 in humans) is the cell-surface receptor for members of the epidermal growth factorfamily (EGF-family) of extracellular protein ligands. The epidermal growth factor receptor is a member of the ErbB family of receptors, a subfamily of four closely related receptor tyrosine kinases: EGFR (ErbB-1), HER2/c-neu (ErbB-2), Her 3 (ErbB-3) and Her 4 (ErbB-4). Mutations affecting EGFR expression or activity could result in cancer. EGFR exists on the cell surface and is activated by binding of its specificligands, including epidermal growth factor and transforming growth factor α (TGFα). EGFR dimerization stimulates its intrinsic intracellular protein-tyrosine kinase activity. As a result,autophosphorylation of several tyrosine (Y) residues in the C-terminal domain of EGFR occurs.</p>
	<div class="h2">1.&nbsp;&nbsp;Afatinib</div>
	<ul>
		<li><span class="font-bold">Description:</span> Afatinib (BIBW2992) irreversibly inhibits EGFR/HER2 including EGFR(wt), EGFR(L858R), EGFR(L858R/T790M) and HER2 with IC50 of 0.5 nM, 0.4 nM, 10 nM and 14 nM, respectively; 100-fold more active against Gefitinib-resistant L858R-T790M EGFR mutant.</li>
		<li><span class="font-bold">IC50 value:</span> 0.5 nM/0.4 nM/10 nM/14 nM (EGFR(wt)/EGFR(L858R)/EGFR(L858R/T790M) /HER2) [1]</li>
		<li><span class="font-bold">Target:</span> EGFR;EGFR mutant forms; HER2</li>
		<li><span class="font-bold">in vitro:</span> BIBW2992 shows potent activity against both wild-type and mutant forms of EGFR and HER2. It is similar to Gefitinib in potency against L858R EGFR, but about 100-fold more active against the Gefitinib resistant L858R-T790M EGFR double mutant. BIBW2992 exhibits potent effects on both EGFR and HER2 phosphorylation in vivo. It compares favorably to reference compounds (such as Lapatinib et al.) in all cell types tested, such as human epidermoid carcinoma cell line A431 expressing wt EGFR, murine NIH-3T3 cells transfected with wt HER2, as well as breast cancer cell line BT-474 and gastric cancer cell line NCI-N87, which express endogenous HER2 [1]. All pancreatic cancer cell lines were found to be IGF-IR positive and NVP-AEW541 treatment inhibited the growth of the pancreatic cancer cell lines with IC50 values ranging from 342 nM (FA6) to 2.73 μM (PT45). Interestingly, of the various combinations examined, treatment with a combination of NVP-AEW541 and afatinib was superior in inducing synergistic growth inhibition of the majority of pancreatic cancer cells [2].</li>
		<li><span class="font-bold">in vivo:</span> Daily oral administration of BIBW2992 at 20 mg/kg for 25 days results in dramatic tumor regression with a cumulative treated/control tumor volume ratio (T/C ratio) of 2%. Reduced phosphorylation of EGFR and AKT is confirmed by immunohistochemical staining of tissue sections. Therefore, like lapatinib and neratinib, BIBW2992 is a next generation tyrosine kinase inhibitor (TKI) that inhibits human epidermal growth factor receptor 2 (Her2) and epidermal growth factor receptor (EGFR) kinases irreversibly. BIBW2992 is not only active against EGFR mutations targeted by first generation TKIs like Erlotinib or Gefitinib, but also against those insensitive to these standard therapies [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>D Li et al BIBW2992, an irreversible EGFR/HER2 inhibitor highly effective in preclinical lung cancer models Oncogene (2008) 27, 4702-4711
		</p>
		<p>
			<span class="ml-20">[2].</span> Ioannou N, et al. Treatment with a combination of the ErbB (HER) family blocker afatinib and the IGF-IR inhibitor, NVP-AEW541 induces synergistic growth inhibition of human pancreatic cancer cells. BMC Cancer. 2013 Jan 31;13:41.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp;AZD-9291</div>
	<ul>
		<li><span class="font-bold">Description:</span>AZD-9291 is a potent and selective mutated forms EGFR inhibitor(Exon 19 deletion EGFR IC50=12.92 nM, L858R/T790M EGFR IC50= 11.44 nM, wild type EGFR IC50= 493.8 nM).</li>
		<li><span class="font-bold">IC50 value:</span>12.92 nM(Exon 19 deletion EGFR); 11.44 nM(L858R/T790M EGFR) [1]</li>
		<li><span class="font-bold">Target:</span> L858R/T790M EGFR; Exon 19 deletion EGFR</li>
		<li>AZD-9291 is a third-generation EGFR inhibitor, showed promise in preclinical studies and provides hope for patients with advanced lung cancers that have become resistant to existing EGFR inhibitors. AZD9291 is highly active in preclinical models and is well tolerated in animal models. It inhibits both activating and resistant EGFR mutations while sparing the normal form of EGFR that is present in normal skin and gut cells, thereby reducing the side effects encountered with currently available medicines.</li>
		<li>Patent: WO 2013014448</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Patent WO 2013014448 A1: 2 - (2, 4, 5 - substituted -anilino) pyrimidine derivatives as egfr modulators useful for treating cancer 
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp;Erlotinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Erlotinib (OSI-744; NSC 718781; R1415) is an EGFR inhibitor with IC50 of 2 nM, >1000-fold more sensitive for EGFR than human c-Src or v-Abl.</li>
		<li><span class="font-bold">IC50 value:</span>2 nM [1]</li>
		<li><span class="font-bold">Target:</span> EGFR</li>
		<li><span class="font-bold">in vitro:</span> Erlotinib HCl potently inhibits EGFR activation in intact cells including HNS human head and neck tumor cells (IC50 20nM), DiFi humancolon cancer cells andMDA MB-468 human breast cancer cells. Erlotinib HCl (1 μM) induces apoptosis in DiFi humancolon cancer cells [1]. Erlotinib inhibits growth of a panel of NSCLC cell lines including A549, H322, H3255, H358 H661, H1650, H1975, H1299, H596 with IC50 ranging from 29 nM to >20 μM. [2] Erlotinib HCl(2 μM) significantly inhibits growth of AsPC-1 and BxPC-3 pancreatic cells [2].Combination with Erlotinib HCl could down-modulate rapamycin-stimulated Akt activity and produces a synergistic effect on cell growth inhibition [3].</li>
		<li><span class="font-bold">in vivo:</span> At doses of 100 mg/kg, Erlotinib HCl completely prevents EGF-induced autophosphorylation of EGFR in human HN5 tumors growing as xenografts in athymic mice and of the hepatic EGFR of the treated mice [1]. Erlotinib HCl (100 mg/Kg) inhibits H460a and A549 tumor models with 71 and 93% inhibition rate [3].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Moyer JD, et al. Induction of apoptosis and cell cycle arrest by CP-358,774, an inhibitor of epidermal growth factor receptor tyrosine kinase. Cancer Res. 1997, 57(21), 4838-4848.
		</p>
		<p>
			<span class="ml-20">[2].</span>  Ali S, et al. Apoptosis-inducing effect of erlotinib is potentiated by 3,3'-diindolylmethane in vitro and in vivo using an orthotopic model of pancreatic cancer. Mol Cancer Ther, 2008, 7(6), 1708-1719.
		</p>
		<p>
			<span class="ml-20">[3].</span>  Higgins B, et al. Antitumor activity of erlotinib (OSI-774, Tarceva) alone or in combination in human non-small cell lung cancer tumor xenograft models. Anticancer Drugs. 2004, (5), 503-512.
		</p>
	</div>
	<div class="h1">ERBB2</div>
	<div class="h2">1.&nbsp;&nbsp; Lapatinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Lapatinib(GW-572016) is a potent EGFR and ErbB2 inhibitor with IC50 of 10.8 and 9.2 nM, respectively.</li>
		<li><span class="font-bold">IC50 value:</span> 10.8 nM (EGFR); 9.2 nM (ErbB2)</li>
		<li><span class="font-bold">Target:</span> EGFR/ErbB2</li>
		<li>Lapatinib ditosylate is a selective and effective inhibitor of ErbB-2 and EGFR dual tyrosine kinases. ErbB-2 and EGFR dual tyrosine kinases are growth promoting factors that are over expressed in some breast cancer cell lines. Research studies have reported that Lapatinib Ditosylate can inhibit breast cancer cell proliferation.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Gottfried E. Konecny et al Activity of the Dual Kinase Inhibitor Lapatinib (GW572016) against HER-2-Overexpressing and Trastuzumab-Treated Breast Cancer Cells Cancer Res February 1, 2006 66; 163
		</p>
		<p>
			<span class="ml-20">[2].</span> Montemurro F, Valabrega G, Aglietta M. Lapatinib: a dual inhibitor of EGFR and HER2 tyrosine kinase activity. Expert Opin Biol Ther. 2007 Feb;7(2):257-68.
		</p>
		<p>
			<span class="ml-20">[3].</span>  O'Neill F, Madden SF, Aherne ST et al. Gene expression changes as markers of early lapatinib response in a panel of breast cancer cell lines. Mol Cancer. 2012 Jun 18;11(1):41.
		</p>
		<p>
			<span class="ml-20">[4].</span>  Karajannis MA, Legault G, Hagiwara M et al. Phase II trial of lapatinib in adult and pediatric patients with neurofibromatosis type 2 and progressive vestibular schwannomas. Neuro Oncol. 2012 Sep;14(9):1163-70.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp;  ARRY-380</div>
	<ul>
		<li><span class="font-bold">Description:</span> ARRY-380 is a potent and selective HER2 inhibitor with IC50 of 8 nM, equipotent against truncated p95-HER2, 500-fold more selective for HER2 versus EGFR.</li>
		<li><span class="font-bold">IC50 value:</span> 8 nM</li>
		<li>ErbB-2 inhibitor ARRY-380 selectively binds to and inhibits the phosphorylation of ErbB-2, which may prevent the activation of ErbB-2 signal transduction pathways, resulting in growth inhibition and death of ErbB-2-expressing tumor cells. ErbB-2 is overexpressed in a variety of cancers and plays an important role in cellular proliferation and differentiation.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>ErbB-2 inhibitor ARRY-380 
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp;   AEE788</div>
	<ul>
		<li><span class="font-bold">Description:</span>AEE788(NVP-AEE 788) is a potent inhibitor of EGFR and HER2/ErbB2 with IC50 of 2 nM and 6 nM, less potent to VEGFR2/KDR, c-Abl, c-Src, and Flt-1, does not inhibit Ins-R, IGF-1R, PKCα and CDK1.</li>
		<li><span class="font-bold">IC50 value:</span> 2 nM( EGFR); 6 nM (ErbB2) [1]</li>
		<li><span class="font-bold">Target:</span> EGFR/HER2</li>
		<li><span class="font-bold">in vitro:</span> At the enzyme level, AEE788 inhibited EGFR and VEGF receptor tyrosine kinases in the nm range (IC(50)s: EGFR 2 nm, ErbB2 6 nm, KDR 77 nm, and Flt-1 59 nm). In cells, growth factor-induced EGFR and ErbB2 phosphorylation was also efficiently inhibited (IC(50)s: 11 and 220 nm, respectively)[1]. Treatment of cutaneous SCC cells with AEE788 led to dose-dependent inhibition of EGFR and VEGFR-2 phosphorylation, growth inhibition, and induction of apoptosis [2].</li>
		<li><span class="font-bold">in vivo:</span> Oral administration of AEE788 to tumor-bearing mice resulted in high and persistent compound levels in tumor tissue. Moreover, AEE788 efficiently inhibited growth factor-induced EGFR and ErbB2 phosphorylation in tumors for >72 h, a phenomenon correlating with the antitumor efficacy of intermittent treatment schedules [1]. Liver regeneration and liver architecture were not impaired by AEE788 treatment after 90%PH [3]. Treatment with AEE788 alone or in combination with IR strongly improved tumor oxygenation in both tumor models as determined by the detection of endogenous and exogenous markers of tumor hypoxia [4]. In mice treated with AEE788, tumor growth was inhibited by 54% at 21 days after the start of treatment compared with control mice (P < 0.01) [2].</li>
		<li><span class="font-bold">Clinical trial:</span> Study of Oral AEE-788 in Adults With Advanced Cancer. Phase1/ Phase2 </li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Traxler P, Allegrini PR, Brandt R, AEE788: a dual family epidermal growth factor receptor/ErbB2 and vascular endothelial growth factor receptor tyrosine kinase inhibitor with antitumor and antiangiogenic activity. Cancer Res. 2004 Jul 15;64(14):4931-41.
		</p>
		<p>
			<span class="ml-20">[2].</span>Park YW, Younes MN, Jasser SA, AEE788, a dual tyrosine kinase receptor inhibitor, induces endothelial cell apoptosis in human cutaneous squamous cell carcinoma xenografts in nude mice. Clin Cancer Res. 2005 Mar 1;11(5):1963-73.
		</p>
		<p>
			<span class="ml-20">[3].</span> Deng M, Huang H, Jin H, The anti-proliferative side effects of AEE788, a tyrosine kinase inhibitor blocking both EGF- and VEGF-receptor, are liver-size-dependent after partial hepatectomy in rats. Invest New Drugs. 2011 Aug;29(4):593-606.
		</p>
		<p>
			<span class="ml-20">[4].</span>Oehler-J?nne C, Jochum W, Riesterer O, Hypoxia modulation and radiosensitization by the novel dual EGFR and VEGFR inhibitor AEE788 in spontaneous and related allograft tumor models. Mol Cancer Ther. 2007 Sep;6(9):2496-504.
		</p>
	</div>
	<div class="h1">EZH2</div>
	<p>EZH2 is an enzyme that in humans is encoded by the EZH2 gene. The EZH2 gene provides instructions for making a type of enzyme called a histone methyltransferase. EZH2 acts mainly as a gene silencer. EZH2 protein is the catalytic subunit of Polycomb Repressive Complex 2, one of the two-multimeric repressive complexes in the organization of the PcG. PcG proteins act as an important epigenetic mediator that can repress gene expression by forming multiple complexes leading to trimethylation at lysine 27 of histone H3. EZH2 is a histone methyltransferase, which plays an important role in tumor development. Moreover, EZH2 might also play essential roles in the control of central nervous systems by regulating the dopamine receptor D4.</p>
	<div class="h2">1.&nbsp;&nbsp;CPI-169</div>
	<ul>
		<li><span class="font-bold">Description:</span> CPI-169 R-enantiomer is the R enantiomer of CPI-169, which is a novel and potent EZH2 inhibitor with IC50 <1 nM(inhibition of the catalytic activity of PRC2); decreases cellular levels of H3K27me3 with an EC50 of 70 nM, and triggers cell cycle arrest and apoptosis in a variety of cell lines.</li>
		<li><span class="font-bold">IC50 value:</span> < 1 nM (catalytic activity of PRC2); 70 nM (Cellular EC50) [1]</li>
		<li><span class="font-bold">Target:</span> EZH2 inhibitor</li>
		<li><span class="font-bold">in vitro:</span> CPI-169, a representative compound from that effort, inhibits the catalytic activity of PRC2 with an IC50 of < 1nM, decreases cellular levels of H3K27me3 with an EC50 of 70 nM, and triggers cell cycle arrest and apoptosis in a variety of cell lines. Importantly, compound treatment triggers a sequence of downstream functional consequences of EZH2 inhibition whereby apoptosis is not induced before ten days of continuous target engagement [1].</li>
		<li><span class="font-bold">in vivo:</span> Administered subcutaneously at 200 mpk twice daily (BID), CPI-169 is well tolerated in mice with no observed toxic effect or body weight loss. In the present study we show that CPI-169 treatment led to tumor growth inhibition (TGI) of an EZH2 mutant KARPAS-422 DLBCL xenograft. The TGI is proportional to the dose administered and to the reduction of the pharmacodynamic marker H3K27me3. The highest dose, 200 mpk, BID led to complete tumor regression. Since CHOP (cyclophosphamide, doxorubicin, vincristine and prednisone) is the standard treatment of advanced DLBCL, we were interested in combining a suboptimal dose of CPI-169 (100 mpk, BID) with a single dose of CHOP in the KARPAS-422 model. After a week of combinatorial treatment the tumors rapidly regressed and became unpalpable. Four weeks after the last dose only a single mouse presented a palpable tumor [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Hall A-E, et al. CPI-169, a novel and potent EZH2 inhibitor, synergizes with CHOP in vivo and achieves complete regression in lymphoma xenograft models
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp; EI1</div>
	<ul>
		<li><span class="font-bold">Description:</span>EI1 is a potent and selective small molecule inhibitor of EZH2 with IC50 values of 15 nM and 13 nM for wild type EZH2 and EZH2 Y641F mutant, respectively.</li>
		<li><span class="font-bold">IC50 value:</span> 15±2 nM (EZH2 wild type); 13±3 nM (EZH2 Y641F mutant type) [1]</li>
		<li><span class="font-bold">Target:</span> EZH2</li>
		<li><span class="font-bold">in vitro:</span> EI1 demonstrated potent, concentration-dependent inhibition of the enzymatic activity against both Ezh2 wild-type and Y641F mutant enzymes with IC50 of 15 ± 2 nM and 13 ± 3 nM, respectively. Although SAM is the common cofactor for all HMTs, EI1 showed remarkable selectivity against Ezh2 over other HMTs. EI1 dramatically inhibited the H3K27me3 and H3K27me2 levels in these cells in a dose-dependent manner, but H3K27me1 was largely unchanged. The effect was similar in these cell lines, although they have different basal H3K27me3 and H3K27me2 levels. For example, in WSU-DLCL2, SU-DHL6, and Karpas422 with Ezh2 mutations, the H3K27me3 level was much higher than that in DLBCL cells with wild-type Ezh2 [1].</li>
		<li><span class="font-bold">in vivo:</span></li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Qi W, et al. Selective inhibition of Ezh2 by a small molecule inhibitor blocks tumor cells proliferation. Proc Natl Acad Sci U S A. 2012 Dec 26;109(52):21360-5.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp;  EPZ-6438</div>
	<ul>
		<li><span class="font-bold">Description:</span>EPZ-6438 is a potent, selective, and orally bioavailable small-molecule inhibitor of EZH2 enzymatic activity with Ki value of 2.5±0.5 nM.</li>
		<li><span class="font-bold">IC50 value:</span> 2.5±0.5 nM (PRC2-containing wild-type EZH2) [1]</li>
		<li><span class="font-bold">Target:</span> EZH2; HMTase</li>
		<li><span class="font-bold">in vitro:</span> EPZ-6438 inhibited the activity of human PRC2-containing wild-type EZH2 with an inhibition constant (Ki) value of 2.5 ± 0.5 nM, and similar potency was observed for EZH2 proteins bearing all known lymphoma change-of-function mutations. EPZ-6438 displayed a 35-fold selectivity versus EZH1 and >4,500-fold selectivity relative to 14 other HMTs tested. In vitro treatment of SMARCB1-deleted MRT cell lines with EPZ-6438 induced strong antiproliferative effects with IC50 values in the nanomolar range, whereas the control (wild-type) cell lines were minimally affected. EPZ-6438 was well tolerated at all doses with minimal effect on body weight (Fig. S4A). Oral EPZ-6438 treatment of G401 cells for up to 7 d strongly induced expression of CD133, DOCK4, and PTPRK and up-regulated cell cycle inhibitors CDKN1A and CDKN2A and tumor suppressor BIN1, all in a time-dependent manner [1].</li>
		<li><span class="font-bold">in vivo:</span> EPZ-6438 was well tolerated at all doses with minimal effect on body weight. Oral dosing at 250 or 500 mg/kg twice daily (BID) for 21-28 d practically eliminated the fast-growing G401 tumors. Regrowth was not observed for 32 d after dose cessation. EPZ-6438 dosed at 125 mg/kg induced tumor stasis during the administration period and produced a significant tumor growth delay compared with vehicle after the dosing period. Measuring EPZ-6438 plasma levels either 5 min before or 3 h after dosing on day 21 revealed a clear dose-dependent increase in systemic exposure [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Knutson SK, et al. Durable tumor regression in genetically altered malignant rhabdoid tumors by inhibition of methyltransferaseEZH2. Proc Natl Acad Sci U S A. 2013 May 7;110(19):7922-7.
			<br><br>Abstract<br>
			Inactivation of the switch/sucrose nonfermentable complex component SMARCB1 is extremely prevalent in pediatric malignant rhabdoid tumors (MRTs) or atypical teratoid rhabdoid tumors. This alteration is hypothesized to confer oncogenic dependency on EZH2 in these cancers. We report the discovery of a potent, selective, and orally bioavailable small-molecule inhibitor of EZH2 enzymatic activity, (N-((4,6-dimethyl-2-oxo-1,2-dihydropyridin-3-yl)methyl)-5-(ethyl(tetrahydro-2H-pyran-4-yl)amino)-4-methyl-4'-(morpholinomethyl)-[1,1'-biphenyl]-3-carboxamide). The compound induces apoptosis and differentiation specifically in SMARCB1-deleted MRT cells. Treatment of xenograft-bearing mice with (N-((4,6-dimethyl-2-oxo-1,2-dihydropyridin-3-yl)methyl)-5-(ethyl(tetrahydro-2H-pyran-4-yl)amino)-4-methyl-4'-(morpholinomethyl)-[1,1'-biphenyl]-3-carboxamide) leads to dose-dependent regression of MRTs with correlative diminution of intratumoral trimethylation levels of lysine 27 on histone H3, and prevention of tumor regrowth after dosing cessation. These data demonstrate the dependency of SMARCB1 mutant MRTs on EZH2 enzymatic activity and portend the utility of EZH2-targeted drugs for the treatment of these genetically defined cancers
		</p>
	</div>
	<div class="h1">FGFR</div>
	<p>FGFR (Fibroblast growth factor receptors) are the receptors that bind to members of the fibroblast growth factor family of proteins. Some of these receptors are involved in pathological conditions. A point mutation in FGFR3 can lead to achondroplasia. Five distinct membrane FGFR have been identified in vertebrates and all of them belong to the tyrosine kinase superfamily (FGFR1, FGFR2, FGFR3, FGFR4, FGFR6). The fibroblast growth factor family constitutes one of the most important groups of paracrine factors that act during development. They are responsible for determining certain cells to become mesoderm, for the production of blood vessels, for limb outgrowth, and for the growth and differentiation of numerous cell types. As we study developmental biology, we will see fibroblast growth factors popping up all over the embryo.</p>
	<div class="h2">1.&nbsp;&nbsp; AZD4547</div>
	<ul>
		<li><span class="font-bold">Description:</span>AZD4547 is a novel selective FGFR inhibitor targeting FGFR1/2/3 with IC50 of 0.2 nM/2.5 nM/1.8 nM, weaker activity against FGFR4, VEGFR2(KDR), and little activity observed against IGFR, CDK2, and p38.</li>
		<li><span class="font-bold">IC50 value:</span>0.2 nM/2.5 nM/1.8 nM(FGFR1/2/3) [1]</li>
		<li><span class="font-bold">Target:</span> FGFR1/2/3</li>
		<li><span class="font-bold">in vitro:</span> Compared to FGFR1-3, AZD4547 displays weaker activity against FGFR4 with IC50 of 165 nM. AZD4547 only inhibits recombinant VEGFR2 (KDR) kinase activity with IC50 of 24 nM, in the in vitro selectivity test against a diverse panel of representative human kinases. AZD4547 at 0.1 μM exhibits no activity against a range of recombinant kinases including ALK, CHK1, EGFR, MAPK1, MEK1, p70S6K, PDGFR, PKB, Src, Tie2, and PI3-kinase. Consistently, the potent selectivity of AZD4547 for FGFR1-3 over FGFR4, IGFR, and KDR is also observed in cellular phosphorylation assays. AZD4547 has potent in vitro antiproliferative activity only against tumor cell lines expressing deregulated FGFRs such as KG1a, Sum52-PE, and KMS11 with IC50 of 18-281 nM, and is inactive against MCF7 as well as more than 100 additional tumor cell lines. AZD4547 treatment potently inhibits FGFR and MAPK phosphorylation in human tumor cell lines in a dose-dependent manner. AZD4547 also potently inhibits the phosphorylation of FRS2 and PLCγ, downstream markers of FGFR signaling. Notably, AZD4547 affects the AKT phosphorylation in the breast cell lines, MCF7 and Sum52-PE but not in KG1a and KMS11 lines. AZD4547 treatment significantly induces apoptosis in Sum52-PE and KMS11 cells, dramatically increases G1 arrest but not apoptosis in KG1a cells, and has no effect on cell cycle distribution or apoptosis in MCF7 cells [1].</li>
		<li><span class="font-bold">in vivo:</span> Oral administration of AZD4547 at 3 mg/kg twice daily in mice bearing KMS11 tumors results in significant tumor growth inhibition of 53% when compared with vehicle-treated controls, and AZD4547 at 12.5 mg/kg once daily or 6.25 mg/kg twice daily leads to complete tumor stasis, which is associated with dose proportional pharmacodynamic modulation of phospho-FGFR3 and reduced KMS11 tumor cell proliferation. Moreover, oral administration of AZD4547 at 12.5 mg/kg once daily results in 65% tumor growth inhibition in the FGFR1-fusion KG1a xenograft model. At efficacious dose levels, AZD4547 does not exhibit antiangiogenic effects. AZD4547 has no significant effect on blood pressure and therefore lacks in vivo anti-KDR activity. Consistently, dosing of 6.25 mg/kg orally twice daily AZD4547 is inactive in the cediranib-sensitive xenograft models including Calu-6, HCT-15 and LoVo [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Gavine PR, et al. AZD4547: an orally bioavailable, potent, and selective inhibitor of the fibroblast growth factor receptor tyrosine kinase family. Cancer Res, 2012, 72(8), 2045-2056.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp; Ponatinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Ponatinib (AP24534) is a novel, potent multi-target inhibitor of Abl, PDGFRα, VEGFR2, FGFR1 and Src with IC50 of 0.37 nM, 1.1 nM, 1.5 nM, 2.2 nM and 5.4 nM, respectively.</li>
		<li><span class="font-bold">IC50 value:</span>0.37 nM/1.1 nM/1.5 nM/2.2 nM/5.4 nM (Abl/PDGFRα/VEGFR2/FGFR1/Src) [1]</li>
		<li><span class="font-bold">Target:</span> Abl; multikinase</li>
		<li><span class="font-bold">in vitro:</span> AP24534 potently inhibits native Abl, AblT315I, and other clinically important Abl kinase domain mutants with IC50 of 0.30 nM–2 nM. AP24534 doesn't inhibit Aurora kinase family members, nor does it inhibit insulin receptor or CDK2/cyclin E. AP24534 inhibits proliferation of Ba/F3 cells expressing Bcr-Abl with IC50 of 0.5 nM, as well as Ba/F3 cells expressing a range of Bcr-Abl mutants with IC50 of 0.5 nM–36 nM. The inhibition of proliferation by AP24534 is correlated with induction of apoptosis [2]. In leukemic cell lines containing activated forms of FLT3, KIT, FGFR1, and PDGFRα receptors, AP24534 potently inhibits receptor phosphorylation and cellular proliferation with IC50 of 0.3 nM to 20 nM. In MV4-11 (FLT3-ITD(+/+)) but not RS4;11 (FLT3-ITD(–/–)) AML cells, AP24534 inhibits FLT3 signaling and induced apoptosis at less than 10 nM. AP24534 inhibits viability of primary leukemic blasts from a FLT3-ITD positive AML patient with IC50 of 4 nM but not those from patients with AML expressing native FLT3 [3].</li>
		<li><span class="font-bold">in vivo:</span> In a mouse xenograft model of Ba/F3 cells expressing native Bcr-Abl, AP24534 (2.5 mg/kg and 5 mg/kg) prolongs mice median survival. In the xenograft model of Ba/F3 Bcr-AblT315I, AP24534 (10 mg/kg–50 mg/kg) significantly suppresses tumor growth. AP24534 (30 mg/kg) decreases the phosphorylated Bcr-Abl and phosphorylated CrkL levels in the tumors [2].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>O'Hare T, et al. AP24534, a pan-BCR-ABL inhibitor for chronic myeloid leukemia, potently inhibits the T315I mutant and overcomes mutation-based resistance. Cancer Cell, 2009, 16(5), 401-412.
		</p>
		<p>
			<span class="ml-20">[2].</span>Huang WS, et al. Discovery of 3-[2-(imidazo[1,2-b]pyridazin-3-yl)ethynyl]-4-methyl-N-{4-[(4-methylpiperazin-1-yl)methyl]-3-(trifluoromethyl)phenyl}benzamide (AP24534), a potent, orally active pan-inhibitor of breakpoint cluster region-abelson (BCR-ABL) kinase including the T315I gatekeeper mutant. J Med Chem, 2010, 53(12), 4701-4719.
		</p>
		<p>
			<span class="ml-20">[2].</span>Gozgit JM, et al. Potent activity of ponatinib (AP24534) in models of FLT3-driven acute myeloid leukemia and other hematologic malignancies. Mol Cancer Ther, 2011, 10(6), 1028-1035.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp; PD173074</div>
	<ul>
		<li><span class="font-bold">Description:</span>PD173074 is a potent FGFR1 inhibitor with IC50 of ~25 nM and also inhibits VEGFR2 with IC50 of 100-200 nM, ~1000-fold selective for FGFR1 than PDGFR and c-Src.</li>
		<li><span class="font-bold">IC50 value:</span>~25 nM (FGFR1); 100-200 nM (VEGFR2) [1]</li>
		<li><span class="font-bold">Target:</span> FGFR1; VEGFR</li>
		<li><span class="font-bold">in vitro:</span> PD173074 is an ATP-competitive inhibitor of FGFR1 with Ki of ~40 nM. PD173074 is also an effective inhibitor of VEGFR2. Compared to FGFR1, PD173074 weakly inhibits the activities of Src, InsR, EGFR, PDGFR, MEK, and PKC with 1000-fold or greater IC50 values. PD173074 inhibits autophosphorylation of FGFR1 and VEGFR2 in a dose-dependent manner with IC50 of 1-5 nM and 100-200 nM, respectively[1]. PD173074 inhibits FGF-2 promotion of granule neuron survival in a dose-dependent manner with IC50 of 12 nM, exhibiting 1,000-fold greater potency than that of SU 5402 [2]. PD173074 specifically inhibits FGF-2-mediated effects on proliferation, differentiation, and MAPK activation in oligodendrocyte (OL) lineage cells [3].</li>
		<li><span class="font-bold">in vivo:</span> Administration of PD173074 at 1 mg/kg/day or 2 mg/ka/day in mice can effectively block angiogenesis induced by either FGF or VEGF in a dose-dependent manner with no apparent toxicity [1]. PD173074 inhibits in vivo growth of mutant FGFR3-transfected NIH 3T3 cells in nude mice. Inhibition of FGFR3 by PD173074 delays tumor growth and increases survival of mice in a KMS11 xenograft myeloma model [4].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Mohammadi M, et al. Crystal structure of an angiogenesis inhibitor bound to the FGF receptor tyrosine kinase domain. EMBO J. 1998 Oct 15;17(20):5896-904.
		</p>
		<p>
			<span class="ml-20">[2].</span> Skaper SD, et al. The FGFR1 inhibitor PD 173074 selectively and potently antagonizes FGF-2 neurotrophic and neurotropic effects. J Neurochem. 2000 Oct;75(4):1520-7.
		</p>
		<p>
			<span class="ml-20">[3].</span>Bansal R, et al. Specific inhibitor of FGF receptor signaling: FGF-2-mediated effects on proliferation, differentiation, and MAPK activation are inhibited by PD173074 in oligodendrocyte-lineage cells. J Neurosci Res. 2003 Nov 15;74(4):486-93.
		</p>
		<p>
			<span class="ml-20">[4].</span>Trudel S, et al. Inhibition of fibroblast growth factor receptor 3 induces differentiation and apoptosis in t(4;14) myeloma. Blood. 2004 May 1;103(9):3521-8.
		</p>
	</div>
	<div class="h1">FLT3</div>
	<p>FLT3 (Fms-like tyrosine kinase 3, CD135) is a protein that in humans is encoded by the FLT3 gene. FLT3 is a cytokine receptor which belongs to the receptor tyrosine kinase class III. FLT3 is the receptor for the cytokine Flt3 ligand (FLT3L). FLT-3 is expressed on the surface of many hematopoietic progenitor cells. Signalling of FLT3 is important for the normal development of haematopoietic stem cells and progenitor cells. The FLT3 gene is one of the most frequently mutated genes in acute myeloid leukemia (AML). Besides, high levels of wild-type FLT3 have been reported for blast cells of some AML patients without FLT3 mutations. These high levels may be associated with worse prognosis. Signaling through FLT3 plays a role in cell survival, proliferation, and differentiation. FLT3 is important for lymphocyte (B cell and T cell) development, but not for the development of other blood cells. Two cytokines that down modulate FLT3 activity are TNF-Alpha and TGF-Beta.</p>
	<div class="h2">1.&nbsp;&nbsp;Amuvatinib</div>
	<ul>
		<li><span class="font-bold">Description:</span> Amuvatinib (MP-470) is a potent and multi-targeted inhibitor of c-Kit, PDGFRα and Flt3 with IC50 of 10 nM, 40 nM and 81 nM, respectively.</li>
		<li><span class="font-bold">IC50 value:</span> 10 nM(c-KitD816H); 40 nM(PDGFRαV561D); 81 nM(Flt3D835Y) [1]</li>
		<li><span class="font-bold">Target:</span> c-Kit; PDGFRα; FLT3</li>
		<li><span class="font-bold">in vitro:</span> The hydrochloride salt of MP-470 also inhibits several mutants of c-Kit, including c-KitD816V, c-KitD816H, c-KitV560G, and c-KitV654A, as well as a Flt3 mutant (Flt3D835Y) and two PDGFRα mutants (PDGFRαV561D and PDGFRαD842V), with IC50 of 10 nM to 8.4 μM. MP-470 potently inhibits the proliferation of OVCAR-3, A549, NCI-H647, DMS-153, and DMS-114 cells, with IC50 of 0.9 μM–7.86 μM [1]. MP-470 also inhibits c-Kit and PDGFRα, with IC50 values of 31 μM and 27 μM, respectively. MP-470 demonstrates potent cytotoxicity against MiaPaCa-2, PANC-1, and GIST882 cells, with IC50 of 1.6 μM to 3.0 μM. MP-470 also binds to and inhibits several c-Kit mutants, including c-KitK642E, c-KitD816V, and c-KitK642E/D816V [2]. In MDA-MB-231 cells, MP-470 (1 μM) inhibits tyrosine phosphorylation of AXL [3]. In LNCaP and PC-3, but not DU145 cells, MP-470 exhibits cytotoxicity with IC50 of 4 μM and 8 μM, respectively, and induces apoptosis at 10 μM. In LNCaP cells, MP-470 (10 μM) elicits G1 arrest and decreases phosphorylation of Akt and ERK1/2 [4].</li>
		<li><span class="font-bold">in vivo:</span> In mice xenograft models of HT-29, A549, and SB-CL2 cells, MP-470 (10 mg/kg–75 mg/kg via i.p. or 50 mg/kg–200 mg/kg via p.o.) inhibits tumor growth [1]. In mice bearing LNCaP xenograft, MP-470 (20 mg/kg) combined with Erlotinib significantly induces tumor growth inhibition (TGI) [4].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Bearss DJ, et al. US Patent, US/2008/0226747.
		</p>
		<p>
			<span class="ml-20">[2].</span> Hurley LH, et al. World Patent, WO/2005/037825.
		</p>
		<p>
			<span class="ml-20">[3].</span> Mahadevan D, et al. A novel tyrosine kinase switch is a mechanism of imatinib resistance in gastrointestinal stromal tumors. Oncogene, 2007, 26(27), 3909-3919.
		</p>
		<p>
			<span class="ml-20">[4].</span>Qi W, et al. MP470, a novel receptor tyrosine kinase inhibitor, in combination with Erlotinib inhibits the HER family/PI3K/Akt pathway and tumor growth in prostate cancer. BMC Cancer, 2009, 9, 142.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp;AST 487</div>
	<ul>
		<li><span class="font-bold">Description:</span>AST487(NVP-AST487) is a Ret kinase inhibitor/FLT3 inhibitor with IC50 of 0.88 uM for Ret.</li>
		<li><span class="font-bold">IC50 value:</span> 0.88 uM</li>
		<li><span class="font-bold">Target:</span> Ret/FLT3</li>
		<li>AST 487 displays high selectivity and potency toward FLT3 as a molecular target, and which could potentially be used to override drug resistance in AML. NVP-AST487 has an IC(50) of 0.88 mumol/L on RET kinase, inhibits RET autophosphorylation and activation of downstream effectors, and potently inhibited the growth of human thyroid cancer cell lines with activating mutations of RET but not of lines without RET mutations. NVP-AST487 induced a dose-dependent growth inhibition of xenografts of NIH3T3 cells expressing oncogenic RET, and of the MTC cell line TT in nude mice. NVP-AST487 inhibited calcitonin gene expression in vitro in TT cells, in part, through decreased gene transcription.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Weisberg, Ellen; Roesel, Johannes; Furet, Pascal et al. Antileukemic effects of novel first-and second-generation FLT3 inhibitors: structure-affinity comparison. Genes & Cancer (2010), 1(10), 1021-1032.
		</p>
		<p>
			<span class="ml-20">[2].</span>Akeno-Stuart, Nagako; Croyle, Michelle; Knauf, Jeffrey A. et al. The RET Kinase Inhibitor NVP-AST487 Blocks Growth and Calcitonin Gene Expression through Distinct Mechanisms in Medullary Thyroid Cancer Cells. Cancer Research (2007), 67(14), 6956-6964.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp;Quizartinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Quizartinib (AC220) is a second-generation FLT3 inhibitor for Flt3(ITD/WT) with IC50 of 1.1 nM/4.2 nM, 10-fold more selective for Flt3 than KIT, PDGFRα, PDGFRβ, RET, and CSF-1R.</li>
		<li><span class="font-bold">IC50 value:</span> 1.1 nM/4.2 nM( Flt3 ITD/WT) [1]</li>
		<li><span class="font-bold">Target:</span> FLT3</li>
		<li><span class="font-bold">in vitro:</span> AC220, a unique, potent and selective inhibitor of FLT3, has high affinity for FLT3 with a Kd value of 1.6 nM. AC220 inhibits the autophosphorylation of FLT3 in the human leukemia cell lines MV4-11 which harbor a homozygous FLT3-ITD mutation and is FLT3 dependent, and RS4;11 which expresses wild-type FLT3 with IC50 values of 1.1 nM and 4.2 nM, respectively. AC220 is the most potent cellular FLT3-ITD inhibitor, leading to the most significant inhibition of MV4-11 cell proliferation with IC50 of 0.56 nM compared to all other FLT3 inhibitors whose IC50 values range from 0.87 nM to 64 nM. AC220 has no inhibitory activity against the proliferation of A375 cells which harbor an activating mutation in BRAF and are not FLT3 dependent, indicating a large window between FLT3 inhibition and general cytotoxic effects.</li>
		<li><span class="font-bold">in vivo:</span> Oral administration of AC220 (10 mg/kg) induces time-dependent inhibition of FLT3 autophosphorylation in the FLT3-ITD–dependent MV4-11 tumor xenograft mouse model; the inhibition being 90% at 2 hours and 40% at 24 hours. AC220 significantly extends survival in a mouse model of FLT3-ITD AML with doses as low as 1 mg/kg given orally once a day. Treatment with AC220 at 10 mg/kg for 28 days results in rapid and complete regression of tumors in all mice with no tumor regrowth during the 60-day posttreatment period. AC220 displays more significant efficacy compared to sunitinib treatment which causes tumors to shrink slowly and resume growth immediately upon discontinuation of treatment in all but one of the mice.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Zarrinkar PP, et al. AC220 is a uniquely potent and selective inhibitor of FLT3 for the treatment of acute myeloid leukemia (AML). Blood, 2009, 114(14), 2984-2992.
		</p>
	</div>
	<div class="h1">IDH1</div>
	<p>Isocitrate dehydrogenase (IDH) is an enzyme that catalyzes the oxidative decarboxylation of isocitrate, producingalpha-ketoglutarate (α-ketoglutarate) and CO2. This is a two-step process, which involves oxidation of isocitrate (a secondary alcohol) to oxalosuccinate(a ketone), followed by the decarboxylation of the carboxyl group beta to the ketone, forming alpha-ketoglutarate. In humans, IDH exists in three isoforms: IDH3 catalyzes the third step of the citric acid cycle while converting NAD+ to NADH in the mitochondria. The isoforms IDH1 and IDH2 catalyze the same reaction outside the context of the citric acid cycle and use NADP+ as a cofactor instead of NAD+. They localize to the cytosol as well as themitochondrion and peroxisome.</p>
	<div class="h2">1.&nbsp;&nbsp; AGI-5198 </div>
	<ul>
		<li><span class="font-bold">Description:</span> AGI-5198(IDH C35) is a novel R132H-IDH1 inhibitor, identified through a high-throughput screen blocked R132H-IDH1 activity in a dose-dependent manner.</li>
		<li><span class="font-bold">IC50 value:</span></li>
		<li>The ability of the mutant enzyme (mIDH1) to produce R-2-hydroxyglutarate (R-2HG). The recent discovery of mutations in metabolic enzymes has rekindled interest in harnessing the altered metabolism of cancer cells for cancer therapy. Isocitrate dehydrogenase 1 (IDH1) is one potential drug target, which is mutated in multiple human cancers [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Rohle D, Popovici-Muller J, Palaskas N, An inhibitor of mutant IDH1 delays growth and promotes differentiation of glioma cells. Science. 2013 May 3;340(6132):626-30.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp; AGI-6780</div>
	<ul>
		<li><span class="font-bold">Description:</span> AGI-6780 is the first highly potent and selective small molecule inhibitor of isocitrate dehydrogenases, which binds in an allosteric manner at the dimer interface of mutant IDH2-R140Q (IC50=23 nM).</li>
		<li><span class="font-bold">IC50 value:</span> 23 nM (IDH2-R140Q) [1]</li>
		<li><span class="font-bold">Target:</span>  mutant IDH2-R140Q</li>
		<li><span class="font-bold">in vitro:</span> AGI-6780 inhibits IDH2-R140Q in vitro with an IC50 ~23 nM and inhibits 2-HG formation in human glioblastoma U87 and TF-1 cells expressing IDH2-R140Q with IC50 < 20 nM. AGI-6780 can reverse the differentiation blockade in TF-1 cells conferred by IDH2-R140Q, and induce blast differentiation in primary human IDH2-R140Q AML patient samples. It provides proof-of-concept that inhibitors targeting mutant IDH2-R140Q could have potential applications as a differentiation therapy for cancer [1].</li>
		<li><span class="font-bold">in vivo:</span> N/A</li>
		<li><span class="font-bold">Toxicity:</span> N/A</li>
		<li><span class="font-bold">Clinical trial:</span> Phase I Study of AG-221 in Subjects With Advanced Hematological Malignancies With an IDH2 Mutation. Phase 1</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Wang F, et al. Targeted inhibition of mutant IDH2 in leukemia cells induces cellular differentiation. Science. 2013 May 3;340(6132):622-6.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp; Mutant IDH1 inhibitor</div>
	<ul>
		<li><span class="font-bold">Description:</span> Mutant IDH1 Inhibitor is a potent mutant IDH1 R132H inhibitor with IC50 of < 72 nM.</li>
		<li><span class="font-bold">IC50 value:</span>  < 72 nM  [1]</li>
		<li><span class="font-bold">Target:</span>  mutant IDH1 R132H</li>
		<li>More information can be found in Patent WO 2013046136 A1 20130404 (Example 556)</li>
		<li>3-Pyrimidin-4-yl-oxazolidin-2-ones as inhibitors of mutant IDH and their preparation</li>
		<li>By Cho, Young Shin; Levell, Julian Roy; Toure, Bakary-Barry; Yang, Fan; Caferro, Thomas;</li>
		<li>Lei, Huangshu; Lenoir, Francois; Liu, Gang; Palermo, Mark G.; Shultz, Michael David; et al</li>
		<li>From PCT Int. Appl. (2013), WO 2013046136 A1 20130404.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>3-Pyrimidin-4-yl-oxazolidin-2-ones as inhibitors of mutant IDH and their preparation. Patent WO 2013046136 A1 20130404
		</p>
	</div>
	<div class="h1">JAK</div>
	<p>Janus kinase (JAK) is a family of intracellular, nonreceptor tyrosine kinases that transduce cytokine-mediated signals via the JAK-STAT pathway. Since members of the type I and type II cytokine receptor families possess no catalytic kinase activity, they rely on the JAK family of tyrosine kinases to phosphorylate and activate downstream proteins involved in their signal transduction pathways. The receptors exist as paired polypeptides, thus exhibiting two intracellular signal-transducing domains. JAKs associate with a proline-rich region in each intracellular domain, which is adjacent to the cell membrane and called a box1/box2 region. After the receptor associates with its respective cytokine/ligand, it goes through a conformational change, bringing the two JAKs close enough to phosphorylate each other. The JAK autophosphorylation induces a conformational change within itself, enabling it to transduce the intracellular signal by further phosphorylating and activating transcription factors called STATs. The activated STATs dissociate from the receptor and form dimers before translocating to the cell nucleus, where they regulate transcription of selected genes.</p>
	<div class="h2">1.&nbsp;&nbsp; AT9283</div>
	<ul>
		<li><span class="font-bold">Description:</span>AT9283 is a small molecule a multi-targeted inhibitor with IC50s of 4, 1.2, 1.1 and approximate 3 nM for Bcr-Abl(T3151), JAK2 and JAK3, Aurora A and Aurora B, respectively.</li>
		<li><span class="font-bold">IC50 value:</span> 1.2 nM(Jak2); 1.1 nM(Jak3)</li>
		<li><span class="font-bold">Target:</span> Aurora A/B; JAK2/3</li>
		<li><span class="font-bold">in vitro:</span> AT9283 leads to a clear polyploid phenotype by inhibiting the activity of Aurora B kinase in HCT116 cells with IC50 of 30 nM. Furthermore, AT9283 also produces the potent inhibition on HCT116 colony formation.</li>
		<li><span class="font-bold">in vivo:</span> In HCT116 human colon carcinoma xenograft bearing mice, AT9283 treatment (15 mg/kg and 20 mg/kg) for 16 days results in a significant tumor growth inhibition of 67% and 76%, respectively. In addition, AT9283 also exhibits a significantly longer half-life in tumors(2.5 hours) compared with plasma (0.5 hour) and modest oral bioavailability in mice (Fp.o. = 24%).</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Howard, Steven; Berdini, Valerio; Fragment-Based Discovery of the Pyrazol-4-yl Urea (AT9283), a Multitargeted Kinase Inhibitor with Potent Aurora Kinase Activity.Journal of Medicinal Chemistry (2009), 52(2), 379-388.
		</p>
		<p>
			<span class="ml-20">[2].</span> Curry, Jayne; Angove, Hayley; Fazal, Lynsey; Aurora B kinase inhibition in mitosis: strategies for optimizing the use of aurora kinase inhibitors such as AT9283. Cell Cycle (2009), 8(12), 1921-1929.
		</p>
		<p>
			<span class="ml-20">[3].</span>Arkenau HT, Plummer R, Molife LR, Olmos D, Yap TA, Squires M, Lewis S, Lock V, Yule M, Lyons J, Calvert H, Judson I.A phase I dose escalation study of AT9283, a small molecule inhibitor of aurora kinases, in patients with advanced solid malignancies.Ann Oncol. 2012 May;23(5):1307-13. Epub 2011 Oct 19.
		</p>
		<p>
			<span class="ml-20">[4].</span> Qi W, Liu X, Cooke LS, Persky DO, Miller TP, Squires M, Mahadevan D.AT9283, a novel aurora kinase inhibitor, suppresses tumor growth in aggressive B-cell lymphomas.Int J Cancer. 2012 Jun 15;130(12):2997-3005.
		</p>
		<p>
			<span class="ml-20">[5].</span> Podesta JE, Sugar R, Squires M, Linardopoulos S, Pearson AD, Moore AS.Adaptation of the plasma inhibitory activity assay to detect Aurora, ABL and FLT3 kinase inhibition by AT9283 in pediatric leukemia.Leuk Res. 2011 Sep;35(9):1273-5. Epub 2011 Jun 12.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp; AZD-1480</div>
	<ul>
		<li><span class="font-bold">Description:</span> AZD1480 is a novel ATP-competitive JAK2 inhibitor with IC50 of 0.26 nM, selectivity against JAK3 and Tyk2, and to a smaller extent against JAK1.
		<li><span class="font-bold">IC50 value:</span> 0.26 nM [1]
		<li><span class="font-bold">Target:</span> JAK2
		<li><span class="font-bold">in vitro:</span> AZD1480 demonstrated significant immunoregulatory effects by downregulating T-helper 2 cytokines and chemokines, including IL-13 and thymus- and activation-regulated chemokine, and the surface expression of the immunosuppressive programmed death ligands 1 and 2. Higher concentrations of AZD1480 (5μ) induced G2/M arrest and cell death by inhibiting Aurora kinases [1]. These biological responses to AZD1480 are associated with concomitant inhibition of phosphorylation of JAK2, STAT3 and MAPK signaling proteins. In addition, there is inhibition of expression of STAT3 target genes, particularly Cyclin D2. Examination of a wider variety of myeloma cells (RPMI 8226, OPM-2, NCI-H929, Kms.18, MM1.S and IM-9), as well as primary myeloma cells, showed that AZD1480 has broad efficacy [2].  AZD1480 treatment effectively blocks constitutive and stimulus-induced JAK1, JAK2, and STAT-3 phosphorylation in both human and murine glioma cells, and leads to a decrease in cell proliferation and induction of apoptosis [3]. AZD1480 inhibits STAT3 in tumor-associated myeloid cells, reducing their number and inhibiting tumor metastasis. Myeloid cell-mediated angiogenesis was also diminished by AZD1480, with additional direct inhibition of endothelial cell function in vitro and in vivo [4].
		<li><span class="font-bold">in vivo:</span> AZD1480 inhibits the STAT3 phosphorylation in an xenograft model of human solid tumors and multiple myeloma [1]. n vivo, AZD1480 inhibits the growth of subcutaneous tumors and increases survival of mice bearing intracranial glioblastoma (GBM) tumors by inhibiting STAT-3 activity, indicating that pharmacologic inhibition of the JAK/STAT-3 pathway by AZD1480 should be considered for study in the treatment of patients with GBM tumors [3]. AZD1480 blocks lung infiltration of myeloid cells and formation of pulmonary metastases in both mouse syngeneic experimental and spontaneous metastatic models. Furthermore, AZD1480 reduces angiogenesis and metastasis in a human xenograft tumor model [4].
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Derenzini E, et al. The JAK inhibitor AZD1480 regulates proliferation and immunity in Hodgkin lymphoma. Blood Cancer J. 2011 Dec;1(12):e46.
		</p>
		<p>
			<span class="ml-20">[2].</span>Scuto A, et al. The novel JAK inhibitor AZD1480 blocks STAT3 and FGFR3 signaling, resulting in suppression of human myeloma cell growth and survival. Leukemia. 2011 Mar;25(3):538-50.
		</p>
		<p>
			<span class="ml-20">[3].</span>McFarland BC, et al. Therapeutic potential of AZD1480 for the treatment of human glioblastoma. Mol Cancer Ther. 2011 Dec;10(12):2384-93.
		</p>
		<p>
			<span class="ml-20">[4].</span> Xin H, et al. Antiangiogenic and antimetastatic activity of JAK inhibitor AZD1480. Cancer Res. 2011 Nov 1;71(21):6601-10.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp; Tofacitinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Tofacitinib(CP-690550) is a novel inhibitor of JAK3 with IC50 of 1 nM, 20- to 100-fold less potent against JAK2 and JAK1.</li>
		<li><span class="font-bold">IC50 value:</span> 1 nM [1]</li>
		<li><span class="font-bold">Target:</span> JAK3</li>
		<li><span class="font-bold">in vitro:</span> Tofacitinib citrate inhibits IL-2-mediated human T cell blast proliferation and IL-15-induced CD69 expression with IC50 of 11 nM and 48 nM, respectively. Tofacitinib citrate prevents mixed lymphocyte reaction with IC50 of 87 nM. Tofacitinib citrate treatment of murine factor-dependent cell Patersen–erythropoietin receptor (FDCP-EpoR) cells harboring human wild-type or V617F JAK2 leads to prevention of cell proliferation with IC50 of 2.1 μM and 0.25 μM, respectively. Tofacitinib citrate inhibits interleukin-6-induced phosphorylation of STAT1 and STAT3 with IC50 of 23 nM and 77 nM, respectively. Moreover, Tofacitinib citrate generates a significant pro-apoptotic effect on murine FDCP-EpoR cells carrying JAK2VV617F, whereas a lesser effect is observed for cells carrying wild-type JAK2. This activity is coupled with the inhibition of phosphorylation of the key JAK2V617F-dependent downstream signaling effectors signal transducer and activator of transcription (STAT)3, STAT5, and v-akt murine thymoma viral oncogene homolog (AKT) [2]. Additionally, Tofacitinib citrate prevents IL-15-induced CD69 expression in human and cynomolgus monkey NK and CD8+ T cells in vitro [3].</li>
		<li><span class="font-bold">in vivo:</span> Tofacitinib citrate decrease a delayed-type hyper-sensitivity response and extended cardiac allograft survival in murine models. Furthermore, Tofacitinib citrate treatment of ex-vivo-expanded erythroid progenitors from JAK2V617F-positive PV patients results in specific, antiproliferative (IC50 = 0.2 μM) and pro-apoptotic activity. In contrast, expanded progenitors from healthy controls are less sensitive to Tofacitinib citrate in proliferation (IC50 > 1.0 μM), and apoptosis assays [2]. During 2 weeks of Tofacitinib citrate dosing at 10 and 30 mg/kg/d, a significant, time-dependent decrease in NK cell numbers relative to vehicle treatment is observed. Effector memory CD8+ cell numbers in the Tofacitinib citrate-treated group are 55% less than those observed in animals treated with vehicle [3].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Flanagan ME, et al. Discovery of CP-690,550: a potent and selective Janus kinase (JAK) inhibitor for the treatment of autoimmune diseases and organ transplant rejection. J Med Chem, 2010, 53(24), 8468-8484.
		</p>
		<p>
			<span class="ml-20">[2].</span>Manshouri T, et al. The JAK kinase inhibitor CP-690,550 suppresses the growth of human polycythemia vera cells carrying the JAK2V617F mutation. Cancer Sci, 2008, 99(6), 1265-1273.
		</p>
		<p>
			<span class="ml-20">[3].</span>Conklyn M, et al. The JAK3 inhibitor CP-690550 selectively reduces NK and CD8+ cell numbers in cynomolgus monkey blood following chronic oral dosing. J Leukoc Biol, 2004, 76(6), 1248-1255.
		</p>
	</div>
	<div class="h1">KIT</div>
	<p>c-Kit (Mast/stem cell growth factor receptor, SCFR or CD117) is a proteinthat in humans is encoded by the KIT gene. c-Kit (CD117) is an important cell surface marker used to identify certain types of hematopoietic(blood) progenitors in the bone marrow. c-Kit is a cytokine receptor expressed on the surface of hematopoietic stem cells as well as other cell types. Altered forms of this receptor may be associated with some types of cancer. c-Kit is a receptor tyrosine kinase type III, which binds to stem cell factor. When c-Kit binds to stem cell factor (SCF) it forms adimer that activates its intrinsic tyrosine kinase activity, that in turn phosphorylates and activates signal transduction molecules that propagate the signal in the cell. Signalling through c-Kit plays a role in cell survival, proliferation, and differentiation.</p>
	<div class="h2">1.&nbsp;&nbsp;Imatinib</div>
	<ul>
		<li><span class="font-bold">Description:</span></li>
		<li><span class="font-bold">IC50 value:</span> 100 nM (PDGFR) [1]; 100 nM (c-Kit) [2]</li>
		<li>Imatinib is a multi-target inhibitor of v-Abl, c-Kit and PDGFR with IC50 of 0.6 μM, 0.1 μM and 0.1 μM, respectively. Imatinib is used to treat chronic myelogenous leukemia (CML), gastrointestinal stromal tumors (GISTs) and a number of other malignancies.</li>
		<li><span class="font-bold">in vitro:</span> In vitro assays for inhibition of a panel of tyrosine and serine/threonine protein kinases show that Imatinib inhibits the v-Abl tyrosine kinase and PDGFR potently with an IC50 of 0.6 and 0.1 μM, respectively [1]. Imatinib inhibits the SLF-dependent activation of wild-type c-kit kinase activity with a IC50 for these effects of approximately 0.1 μM, which is similar to the concentration required for inhibition of PDGFR [2]. Imatinib exhibits growth-inhibitory activity on the human bronchial carcinoid cell line NCI-H727 and the human pancreatic carcinoid cell line BON-1 with an IC50 of 32.4 and 32.8 μM, respectively [3].</li>
		<li><span class="font-bold">in vivo:</span> In the PS-ASODN group, tumor growth was inhibited by 59.437%, which was markedly higher than in the imatinib group (11.071%) and liposome negative control group [4]. Cohorts of mice were maintained on chow formulated with imatinib 0.5 mg/g or control chow for the duration of the experiment [5].</li>
		<li><span class="font-bold">Toxicity:</span>Imatinib is mainly indicated for chronic myeloid leukemia and gastrointestinal stromal tumors but is also prescribed by dermatologists for dermatofibrosarcoma protuberans, systemic sclerosis, and systemic mastocytosis, among other conditions. Most adverse effects are mild or moderate and therapy is generally well tolerated [6].</li>
		<li><span class="font-bold">Clinical trial:</span> Imatinib Mesylate And Mycophenolate Mofetil For Steroid-Refractory Sclerotic/Fibrotic cGVHD In Children. Phase 2</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Buchdunger E, et al. Selective inhibition of the platelet-derived growth factor signal transduction pathway by a protein-tyrosine kinaseinhibitor of the 2-phenylaminopyrimidine class. Proc Natl Acad Sci U S A. 1995 Mar 28;92(7):2558-62.
		</p>
		<p>
			<span class="ml-20">[2].</span> Heinrich MC, et al. Inhibition of c-kit receptor tyrosine kinase activity by STI 571, a selective tyrosine kinase inhibitor. Blood. 2000 Aug 1;96(3):925-32.
		</p>
		<p>
			<span class="ml-20">[3].</span>Yao JC, et al. Clinical and in vitro studies of imatinib in advanced carcinoid tumors. Clin Cancer Res. 2007 Jan 1;13(1):234-40.
		</p>
		<p>
			<span class="ml-20">[4].</span>Sun XC, et al. Depletion of telomerase RNA inhibits growth of gastrointestinal tumors transplanted in mice. World J Gastroenterol. 2013 Apr 21;19(15):2340-7.
		</p>
		<p>
			<span class="ml-20">[5].</span> Horton JA, et al. Inhibition of radiation-induced skin fibrosis with imatinib. Int J Radiat Biol. 2013 Mar;89(3):162-70.
		</p>
		<p>
			<span class="ml-20">[6].</span> Pretel-Irazabal M, et al. Adverse Skin Effects of Imatinib, a Tyrosine Kinase Inhibitor. Actas Dermosifiliogr. 2013 Apr 30. pii: S0001-7310(13)00108-7.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp; Masitinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Masitinib(AB-1010) is a novel inhibitor for Kit and PDGFRα/β with IC50 of 200 nM and 540 nM/800 nM, weak inhibition to ABL and c-Fms.</li>
		<li><span class="font-bold">IC50 value:</span> 200 nM (Kit); 540 nM/800 nM(PDGFRα/β) [1]</li>
		<li><span class="font-bold">Target:</span> c-Kit; PDGFRα/β</li>
		<li><span class="font-bold">in vitro:</span> In Ba/F3 cells expressing human wild-type Kit, Masitinib inhibits SCF (stem cell factor)-induced cell proliferation with an IC50 of 150 nM, while the IC50 for inhibition of IL-3-stimulated proliferation is at approximately >10 μM. In Ba/F3 cells expressing PDGFRα, Masitinib inhibits PDGF-BB-stimulated proliferation and PDGFRα tyrosine phosphorylation with IC50 of 300 nM. Masitinib also causes inhibition of SCF-stimulated tyrosine phosphorylation of human Kit in mastocytoma cell-lines and BMMC. Masitinib inhibits Kit gain-of-function mutants, including V559D mutant and Δ27 mouse mutant with IC50 of 3 and 5 nM in Ba/F3 cells. Masitinib inhibits the cell proliferation of mastocytoma cell lines including HMC-1α155 and FMA3 with IC50 of 10 and 30 nM, respectively [1]. Masitinib inhibits cell growth and PDGFR phosphorylation in two novel ISS cell lines, which suggest that Masitinib displays activity against both primary and metastatic ISS cell line and may aid in the clinical management of ISS [2].</li>
		<li><span class="font-bold">in vivo:</span> Masitinib inhibits tumour growth and increases the median survival time in Δ27-expressing Ba/F3 tumor models at 30 mg/kg, without cardiotoxicity or genotoxicity [1]. Masitinib (12.5 mg/kg/d PO) increases overall TTP (time-to-tumor progression) compared with placebo in dogs [3].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Dubreuil P, et al. Masitinib (AB1010), a Potent and Selective Tyrosine Kinase Inhibitor Targeting KIT. PLoS One, 2009, 4(9), e7258.
		</p>
		<p>
			<span class="ml-20">[2].</span>Lawrence J, et al. Masitinib demonstrates anti-proliferative and pro-apoptotic activity in primary and metastatic feline injection-site sarcoma cells. Vet Comp Oncol, 2011, doi: 10.1111/j.1476-5829.2011.00291.x.
		</p>
		<p>
			<span class="ml-20">[3].</span>Hahn KA, et al. Masitinib is safe and effective for the treatment of canine mast cell tumors. J Vet Intern Med, 2008, 22(6), 1301-1309.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp; Dovitinib</div>
	<ul>
		<li><span class="font-bold">Description:</span> Dovitinib(CHIR-258; TKI258) is a multitargeted RTK inhibitor, mostly for class III (FLT3/c-Kit) with IC50 of 1 nM/2 nM, also potent to class IV (FGFR1/3) and class V (VEGFR1-4) RTKs with IC50 of 8-13 nM, less potent to InsR, EGFR, c-Met, EphA2, Tie2, IGFR1 and HER2.</li>
		<li><span class="font-bold">IC50 value: </span></li>
		<li><span class="font-bold">Target:</span> multitargeted RTK</li>
		<li><span class="font-bold">in vitro:</span> Dovitinib potently inhibits the FGF-stimulated growth of WT and F384L-FGFR3-expressing B9 cells with IC50 of 25 nM. In addition, Dovitinib inhibits proliferation of B9 cells expressing each of the various activated mutants of FGFR3. Interestingly, there are minimal observed differences in the sensitivity of the different FGFR3 mutations to Dovitinib, with the IC50 ranging from 70 to 90 nM for each of the various mutations. IL-6-dependent B9 cells containing vector only (B9-MINV cells are resistant to the inhibitory activity of Dovitinib at concentrations up to 1 μM. Dovitinib inhibits cell proliferation of KMS11 (FGFR3-Y373C), OPM2 (FGFR3-K650E), and KMS18 (FGFR3-G384D) cells with IC50 of 90 nM (KMS11 and OPM2) and 550 nM, respectively. Dovitinib inhibits FGF-mediated ERK1/2 phosphorylation and induces cytotoxicity in FGFR3-expressing primary MM cells. BMSCs does confer a modest degree of resistance with 44.6% growth inhibition for cells treated with 500 nM Dovitinib and cultured on stroma compared with 71.6% growth inhibition for cells grown without BMSCs. Dovitinib inhibits proliferation of M-NFS-60, an M-CSF growth-driven mouse myeloblastic cell line with a median effective concentration (EC50) of 220 nM [1]. Treatment of SK-HEP1 cells with Dovitinib results in a dose-dependent reduction in cell number and G2/M phase arrest with reduction in the G0/G1 and S phases, inhibition of anchorage-independent growth and blockage of bFGF-induced cell motility. The IC50 for Dovitinib in SK-HEP1 cells is approximately 1.7 μM. Dovitinib also significantly reduces the basal phosphorylation levels of FGFR-1, FGFR substrate 2α (FRS2-α) and ERK1/2 but not Akt in both SK-HEP1 and 21-0208 cells. In 21-0208 HCC cells, Dovitinib significantly inhibits bFGF-induced phosphorylation of FGFR-1, FRS2-α, ERK1/2 but not Akt [2].</li>
		<li><span class="font-bold">in vivo:</span> Dovitinib induces both cytostatic and cytotoxic responses in vivo resulting in regression of FGFR3-expressing tumors [1]. Dovitinib shows a dose- and exposure-dependent inhibition of target receptor tyrosine kinases (RTKs) expressed in tumor xenografts. Dovitinib potently inhibits tumor growth of six HCC lines. Inhibition of angiogenesis correlated with inactivation of FGFR/PDGFRβ/VEGFR2 signaling pathways. In an orthotopic model, Dovitinib potently inhibits primary tumor growth and lung metastasis and significantly prolonged mouse survival [2].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Trudel S, et al. CHIR-258, a novel, multitargeted tyrosine kinase inhibitor for the potential treatment of t(4;14) multiple myeloma. Blood. 2005, 105(7), 2941-2948.
		</p>
		<p>
			<span class="ml-20">[2].</span>Huynh H, et al. Dovitinib demonstrates antitumor and antimetastatic activities in xenograft models of hepatocellular carcinoma. J Hepatol. 2012, 56(3), 595-601.
		</p>
	</div>
	<div class="h1">KRAS</div>
	<div class="h2">1.&nbsp;&nbsp; 6H05</div>
	<ul>
		<li><span class="font-bold">Description:</span>6H05 is a selective, and allosteric inhibitor of oncogenic mutant K-Ras(G12C).</li>
		<li><span class="font-bold">IC50 value: </span></li>
		<li><span class="font-bold">Target:</span> K-Ras G12C</li>
		<li>6H05 gives the greatest degree of modification, which allosterically modifies the oncogenic G12C mutant of highly homologous protein H-Ras without affecting wild-type K-Ras [1]. 6H05 can be used as an intermediate for the synthesis of other oncogenic K-Ras(G12C) inhibitors [2].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Ostrem JM, et al. K-Ras(G12C) inhibitors allosterically control GTP affinity and effector interactions. Nature. 2013 Nov 28;503(7477):548-51.
		</p>
		<p>
			<span class="ml-20">[2].</span>Lu S, et al. Harnessing allostery: a novel approach to drug discovery. Med Res Rev. 2014 Nov;34(6):1242-85.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp; Oncrasin-1</div>
	<ul>
		<li><span class="font-bold">Description:</span>Oncrasin-1 is a potent and effective anticancer inhibitor that kills various human lung cancer cells with K-Ras mutations at low or submicromolar concentrations; also led to abnormal aggregation of PKCι in nucleus of sensitive cells but not in resistant cells.</li>
		<li><span class="font-bold">IC50 value:</span> 1.0 μM(A549, K-ras 12H and p53 Wt) [1]</li>
		<li><span class="font-bold">Target:</span> human lung cancer cells with K-Ras mutation; K-Ras/PKCiota pathway inhibitor</li>
		<li><span class="font-bold">in vitro:</span> effectively kills various human lung cancer cells with K-Ras mutations at low or submicromolar concentrations. The cytotoxic effects correlated with apoptosis inductionas was evidenced by increase of apoptotic cells and activation of caspase-3 and caspase-8 upon the treatment of oncrasin-1 in sensitive cells.</li>
		<li>Treatment with oncrasin-1 also led to abnormal aggregation of PKCι in nucleus of sensitive cells but not in resistant cells. Furthermore, oncrasin-1 induced apoptosis was blocked by siRNA of K-Ras or PKCι suggesting that oncrasin-1 is targeted to a novel K-Ras/PKCι pathway [1]. oncrasin-1 treatment led to coaggregation of PKCiota and splicing factors into megaspliceosomes but had no obvious effects on the DNA repair molecule Rad51. Moreover, oncrasin-1 treatment suppressed the phosphorylation of the largest subunit of RNA polymerase II and the expression of intronless reporter genes in sensitive cells but not in resistant cells [2].</li>
		<li><span class="font-bold">in vivo:</span> The in vivo administration of oncrasin-1 suppressed the growth of K-ras mutant human lung tumor xenografts by >70% and prolonged the survival of nude mice bearing these tumors, without causing detectable toxicity [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Guo W, et al. Identification of a small molecule with synthetic lethality for K-ras and protein kinase C iota. Cancer Res. 2008 Sep 15;68(18):7403-8.
		</p>
		<p>
			<span class="ml-20">[2].</span>Guo W, et al. Interruption of RNA processing machinery by a small compound, 1-[(4-chlorophenyl)methyl]-1H-indole-3-carboxaldehyde (oncrasin-1). Mol Cancer Ther. 2009 Feb;8(2):441-8.
		</p>
	</div>
	<div class="h1">MET</div>
	<p>c-Met ( hepatocyte growth factor receptor or HGFR) is a protein possesses tyrosine kinase activity. The primary single chain precursor protein is post-translationally cleaved to produce the alpha and beta subunits, which are disulfide linked to form the mature receptor. c-Met is a membrane receptor that is essential for embryonic development and wound healing. Hepatocyte growth factor (HGF) is the only known ligand of the c-Met receptor. c-Met is normally expressed by cells of epithelial origin, while expression of HGF is restricted to cells of mesenchymalorigin. Upon HGF stimulation, c-Met induces several biological responses that collectively give rise to a program known as invasive growth.</p>
	<div class="h2">1.&nbsp;&nbsp;Foretinib</div>
	<ul>
		<li><span class="font-bold">Description:</span> Foretinib (GSK1363089; XL880; EXEL-2880; GSK089) is an ATP-competitive inhibitor of HGFR and VEGFR, mostly for Met and KDR with IC50 of 0.4 nM and 0.9 nM. Less potent against Ron, Flt-1/3/4, Kit, PDGFRα/β and Tie-2, and little activity to FGFR1 and EGFR.</li>
		<li><span class="font-bold">IC50 value:</span> 0.4 nM/0.9 nM (Met/KDR) [1]</li>
		<li><span class="font-bold">Target:</span></li>
		<li><span class="font-bold">in vitro:</span> XL880 inhibits HGF receptor family tyrosine kinases with IC50 values of 0.4 nM for Met and 3 nM for Ron. XL880 also inhibits KDR, Flt-1, and Flt-4 with IC50 values of 0.9 nM, 6.8 nM and 2.8 nM, respectively. XL880 inhibits colony growth of B16F10, A549 and HT29 cells with IC50 of 40 nM, 29 nM and 165 nM, respectively [1]. A recent study indicates XL880 affects cell growth differently in gastric cancer cell lines MKN-45 and KATO-III. XL880 inhibits phosphorylation of MET and downstream signaling molecules in MKN-45 cells, while targets GFGR2 in KATO-III cells [2].</li>
		<li><span class="font-bold">in vivo:</span> A single 100 mg/kg oral gavage dose of XL880 results in substantial inhibition of phosphorylation of B16F10 tumor Met and ligand (e.g., HGFor VEGF)-induced receptor phosphorylation of Met in liver and Flk-1/KDR in lung, which both persisted through 24 hours. Treatment with XL880 (30-100 mg/kg, once daily, oral gavage) results in reduction in tumor burden. The lung surface tumor burden is reduced by 50% and 58% following treatment with 30 and 100 mg/kg XL880, respectively. XL880 treatment of mice bearing B16F10 solid tumors also results in dose-dependent tumor growth inhibition of 64% and 87% at 30 and 100 mg/kg, respectively. For both studies, administration of XL880 is well tolerated with no significant body weight loss [1]. XL880 is developed to target abnormal signaling of HGF through Met and simultaneously target several receptors tyrosine kinase involved in tumor angiogenesis. XL880 caused tumor hemorrhage and necrosis in human xenografts within 2 to 4 hours, and maximal tumornecrosis is observed at 96 hours (after five daily doses), resulting in complete regression [3].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Qian F, et al. Inhibition of tumor cell growth, invasion, and metastasis by EXEL-2880 (XL880, GSK1363089), a novel inhibitor of HGF and VEGF receptor tyrosine kinases. Cancer Res, 2009, 69(20), 8009-8016.
		</p>
		<p>
			<span class="ml-20">[2].</span> Kataoka Y, et al. Foretinib (GSK1363089), a multi-kinase inhibitor of MET and VEGFRs, inhibits growth of gastric cancer cell lines by blocking inter-receptor tyrosine kinase networks. Invest New Drugs, 2011.
		</p>
		<p>
			<span class="ml-20">[3].</span> Eder JP, et al. A phase I study of foretinib, a multi-targeted inhibitor of c-Met and vascular endothelial growth factor receptor 2. Clin Cancer Res, 2010, 16(13), 3507-3516.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp; Tivantinib</div>
	<ul>
		<li><span class="font-bold">Description:</span> Tivantinib (ARQ 197) is the first non-ATP-competitive c-Met inhibitor with Ki of 0.355 μM, little activity to Ron, and no inhibition to EGFR, InsR, PDGFRα or FGFR1/4.</li>
		<li><span class="font-bold">IC50 value:</span> 0.355 μM(Ki)</li>
		<li><span class="font-bold">Target:</span> c-Met</li>
		<li><span class="font-bold">in vitro:</span> ARQ-197 has been shown to prevent HGF/c-met induced cellular responses in vitro. ARQ-197 possesses antitumor activity; inhibiting proliferation of A549, DBTRG and NCI-H441 cells with IC50 of 0.38, 0.45, 0.29 μM. Treatment with ARQ-197 results in a decrease in phosphorylation of the MAPK signaling cascade and prevention of invasion and migration. In addition, ectopic expression of c-Met in NCI-H661, a cell line having no endogenous expression of c-Met, causes it to acquire an invasive phenotype that is also suppressed by ARQ-197. Although the addition of increasing concentrations of ARQ-197 does not significantly affect the Km of ATP, exposure of c-Met to 0.5 μM ARQ-197 decreased the Vmax of c-Met by approximately 3-fold. The ability of ARQ-197 to decrease the Vmax without affecting the Km of ATP confirmed that ARQ-197 inhibits c-Met through a non–ATP-competitive mechanism and may therefore account for its high degree of kinase selectivity. ARQ-197 prevents human recombinant c-Met with a calculated inhibitory constant Ki of approximately 355 nM. Although the highest concentration of ATP used is 200 μM, the potency of ARQ-197 against c-Met is not reduced by using concentrations of ATP up to 1 mM. ARQ-197 blocks c-Met phosphorylation and downstream c-Met signaling pathways. ARQ-197 suppresses constitutive and ligand-mediated c-Met autophosphorylation and, by extension, c-Met activity, in turn leading to the inhibition of downstream c-Met effectors. ARQ-197 induction of caspase-dependent apoptosis is increased in c-Met–expressing human cancer cells including HT29, MKN-45, and MDA-MB-231 cells [1][2].</li>
		<li><span class="font-bold">in vivo:</span> All three xenograft models treated with ARQ-197 display reductions in tumor growth: 66% in the HT29 model, 45% in the MKN-45 model, and 79% in the MDA-MB-231 model. In these xenograft studies, no significant body weight changes following oral administration of ARQ-197 at 200 mg/kg are observed. Pharmacodynamically, the phosphorylation of c-Met in human colon xenograft tumors (HT29) is strongly inhibited by ARQ-197, as assessed by a dramatic reduction of c-Met autophosphorylation 24 hours after a single oral dose of 200 mg/kg of ARQ-197. This same dosage in mice exhibits that tumor xenografts are exposed to sustained plasma levels of ARQ-197, consistent with the observed pharmacodynamic inhibition of c-Met phosphorylation and inhibition of proliferation of c-Met harboring cancer cell lines. Plasma levels of ARQ-197 10 hours after dosing are determined to be 1.3 μM, more than 3-fold above the biochemical inhibitory constant of ARQ-197 for c-Met. Therefore, ARQ-197 is able to suppress its target in vivo in the xenografted human tumor tissue. In conclusion, ARQ-197 inhibits the growth of c-Met-dependent xenografted human tumors [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Munshi N, et al. ARQ 197, a novel and selective inhibitor of the human c-Met receptor tyrosine kinase with antitumor activity. Mol Cancer Ther. 2010 Jun;9(6):1544-53.
		</p>
		<p>
			<span class="ml-20">[2].</span> Comoglio PM, et al. Drug development of MET inhibitors: targeting oncogene addiction and expedience. Nat Rev Drug Discov, 2008, 7(6), 504-516.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp;  AMG-208</div>
	<ul>
		<li><span class="font-bold">Description:</span> AMG-208 is a potent small molecular c-Met inhibitor with an IC50 of 9.3 nM.</li>
		<li><span class="font-bold">IC50 value: </span>9.3 nM</li>
		<li><span class="font-bold">Target:</span> c-Met</li>
		<li><span class="font-bold">in vitro:</span> AMG-208 shows the potent inhibition of kinase c-Met activity with IC50 of 9 nM in a cell-free assay. Besides, AMG-208 treatment also leads to the inhibition of HGF-mediated c-Met phosphorylation in PC3 cells with IC50 of 46 nM [1]. Pre-incubation of AMG-208 with human liver microsomes for 30 minutes shows a potent time-dependent inhibition for CYP3A4 metabolic activity with IC50 of 4.1 μM, which is an eightfold decrease relative to the IC50 (32 μM) without preincubation [2]. AMG-208 is identified to be a c-MET and RON dual selective inhibitor [3].</li>
		<li><span class="font-bold">in vivo:</span> In male Sprague?Dawley rats, AMG-208 (0.5 mg/kg i.v.) shows a high bioavailability with Cl of 0.37 L/h/kg, Vss of 0.38 L/kg and T1/2 of 1 hour, while AMG-208 (2 mg/kg i.v.) shows a bioavailability with AUC0→∞ of 2517 ng·h/mL and F of 43%, respectively [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Albrecht BK, et al. Discovery and optimization of triazolopyridazines as potent and selective inhibitors of the c-Met kinase. J Med Chem. 2008, 51(10), 2879-2882.
		</p>
		<p>
			<span class="ml-20">[2].</span>Boezio AA, et al. Discovery and optimization of potent and selective triazolopyridazine series of c-Met inhibitors. Bioorg Med Chem Lett. 2009, 19(22), 6307-6312.
		</p>
		<p>
			<span class="ml-20">[3].</span> Liu X, et al. Developing c-MET pathway inhibitors for cancer therapy: progress and challenges. Trends Mol Med. 2010,16(1), 37-45.
		</p>
	</div>
	<div class="h1">NOTCH</div>
	<p>The notch signaling pathway is a highly conserved cell signaling system present in most multicellular organisms. Notch is present in all metazoans, and mammals possess four different notch receptors, referred to as NOTCH1, NOTCH2, NOTCH3, and NOTCH4. The notch receptor is a single-pass transmembrane receptor protein. It is a hetero-oligomer composed of a large extracellular portion, which associates in a calcium-dependent, non-covalent interaction with a smaller piece of the notch protein composed of a short extracellular region, a single transmembrane-pass, and a small intracellular region. Notch signaling promotes proliferative signaling during neurogenesis, and its activity is inhibited by Numb to promote neural differentiation. The notch signaling pathway is important for cell-cell communication, which involves gene regulation mechanisms that control multiple cell differentiation processes during embryonic and adult life.</p>
	<div class="h2">1.&nbsp;&nbsp;BMS-983970</div>
	<ul>
		<li><span class="font-bold">Description:</span> BMS-983970 is an oral pan-Notch inhibitor for the treatment of cancer.
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Ashvinikumar V. Gavai, et al. Abstract 1643: BMS-983970, an oral pan-Notch inhibitor for the treatment of cancer . Cancer Res October 1, 2014 74; 1643 
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp;FLI-06</div>
	<ul>
		<li><span class="font-bold">Description:</span>FLI-06 is a novel potent and selective small molecule intercepting Notch signaling and the early secretory pathway (EC50 ~2.3 μM), identified by using automated microscopy to monitor the trafficking and processing of a ligand-independent Notch-GFP fusion reporter.</li>
		<li><span class="font-bold">IC50 value: </span>2.3 uM (EC50) [1]</li>
		<li><span class="font-bold">Target:</span> Notch signaling</li>
		<li>FLI-06 can induce accumulation of the reporter at the plasma membrane by interfering with transport in the secretory pathway. It can also disrupt the Golgi apparatus in a manner distinct from that of brefeldin A and golgicide A. FLI-06 inhibited general secretion at a step before exit from the endoplasmic reticulum (ER), which was accompanied by a tubule-to-sheet morphological transition of the ER, rendering FLI-06 the first small molecule acting at such an early stage in secretory traffic. FLI-06 is a very useful chemical probe to study the inhibition of membrane traffic at pre- ER-exit site (ERES) stages without fusion of ER-Golgi.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Kramer A, et al. Small molecules intercept Notch signaling and the early secretory pathway. Nat Chem Biol. 2013 Nov;9(11):731-8.
			<br><br>Abstract<br>
			Notch signaling has a pivotal role in numerous cell-fate decisions, and its aberrant activity leads to developmental disorders and cancer. To identify molecules that influence Notch signaling, we screened nearly 17,000 compounds using automated microscopy to monitor the trafficking and processing of a ligand-independent Notch-enhanced GFP (eGFP) reporter. Characterization of hits in vitro by biochemical and cellular assays and in vivo using zebrafish led to five validated compounds, four of which induced accumulation of the reporter at the plasma membrane by inhibiting γ-secretase. One compound, the dihydropyridine FLI-06, disrupted the Golgi apparatus in a manner distinct from that of brefeldin A and golgicide A. FLI-06 inhibited general secretion at a step before exit from the endoplasmic reticulum (ER), which was accompanied by a tubule-to-sheet morphological transition of the ER, rendering FLI-06 the first small molecule acting at such an early stage in secretory traffic. These data highlight the power of phenotypic screening to enable investigations of central cellular signaling pathways.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp; Z-Ile-Leu-aldehyde</div>
	<ul>
		<li><span class="font-bold">Description:</span> Z-Ile-Leu-aldehyde(Z-IL-CHO; GSI-XII) is a potent gamma-Secretase inhibitor; Notch signaling inhibitor.</li>
		<li><span class="font-bold">IC50 value:</span></li>
		<li><span class="font-bold">Target:</span> gamma-Secretase inhibitor</li>
		<li><span class="font-bold">in vitro:</span> GSI-XII induces apoptosis of murine MOPC315.BM myeloma cells with high Notch activity. GSI XII impairs murine osteoclast differentiation of receptor activator of NF-κB ligand (RANKL)-stimulated RAW264.7 cells in vitro [1]. Notch-signaling inhibition in HRS cells by the γ-secretase inhibitor (GSI) XII results in decreased alternative p52/RelB NF-κB signaling, interfering with processing of the NF-κB2 gene product p100 into its active form p52 [2]. GSI treatment induced morphologic erythroid differentiation and promoted hemoglobin production. GSI treatment suppressed short-term growth and colony formation, while treatment with GSI-XXI promoted the growth of AA cells [3].</li>
		<li><span class="font-bold">in vivo:</span> In the murine MOPC315.BM myeloma model GSI XII has potent anti-MM activity and reduces osteolytic lesions as evidenced by diminished myeloma-specific monoclonal immunoglobulin (Ig)-A serum levels and quantitative assessment of bone structure changes via high-resolution microcomputed tomography scans [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Schwarzer R, et al. Notch pathway inhibition controls myeloma bone disease in the murine MOPC315.BM model. Blood Cancer J. 2014 Jun 13;4:e217.
		</p>
		<p>
			<span class="ml-20">[2].</span> Schwarzer R, et al. Notch is an essential upstream regulator of NF-κB and is relevant for survival of Hodgkin and Reed-Sternberg cells. Leukemia. 2012 Apr;26(4):806-13.
		</p>
		<p>
			<span class="ml-20">[3].</span> Okuhashi Y, et al. Gamma-secretase inhibitors induce erythroid differentiation in erythroid leukemia cell lines. Anticancer Res. 2010 Oct;30(10):4071-4.
		</p>
	</div>
	<div class="h1">PDGFR</div>
	<p>PDGFR (Platelet-derived growth factor receptors) are cell surface tyrosine kinase receptors for members of the platelet-derived growth factor(PDGF) family. PDGF subunits -A and -B are important factors regulating cell proliferation, cellular differentiation, cell growth, development and many diseases including cancer. There are two forms of the PDGFR: PDGFR alpha and PDGFR beta. PDGFRA has been shown to interact with PDGFRB, PLCG1, Sodium-hydrogen antiporter 3 regulator 1, Cbl gene, CRK, Caveolin 1 andPDGFC. PDGFRB has been shown to interact with PTPN11, NCK1, Grb2, Caveolin 1, PDGFRA, Sodium-hydrogen antiporter 3 regulator 1, RAS p21 protein activator 1, CRK, SHC1 and NCK2.</p>
	<div class="h2">1.&nbsp;&nbsp; Crenolanib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Crenolanib (CP-868596) is a potent and selective inhibitor of PDGFRα/β with Kd of 2.1 nM/3.2 nM, also potently inhibits FLT3, sensitive to D842V mutation not V561D mutation, >100-fold more selective for PDGFR than c-Kit, VEGFR-2, TIE-2, FGFR-2, EGFR, erbB2, and Src.</li>
		<li><span class="font-bold">IC50 value: 2.1 nM/3.2 nM(PDGFRα/β) [1]</span>
		<li><span class="font-bold">Target:</span> PDGFRα/β</li>
		<li><span class="font-bold">in vitro:</span> Crenolanib is significantly more potent than imatinib in inhibiting the kinase activity of imatinib-resistant PDGFRα kinases (D842I, D842V, D842Y, D1842-843IM, and deletion I843). Crenolanib is 135-fold more otent than imatinib against D842V in the isogenic model system, with an IC50 of approximately 10 nM. Crenolanib inhibits the kinase activity of the fusion oncogene in EOL-1 cell line, which is derived from a patient with chronic eosinophilic leukemia and expresses the constitutively activated FIP1L1- PDGFRα fusion kinase, with IC50 = 21 nM. Crenolanib also inhibits the proliferation of EOL-1 cells with IC50 = 0.2 pM. Crenolanib inhibits the activation of V561D or D842V-mutant kinases expressed in BaF3 cells with IC50 with 85 nM or 272 nM, respectively. Crenolanib inhibits PDGFRα activation in H1703 non-small cell lung cancer cell line which has 24-fold amplification of the 4q12 region that contains the PDGFRα locus, with IC50 with 26 nM [1]. Crenolanib is an orally bioavailable, highly potent and selective PDGFR TKI. Crenolanib is a benzimidazole compound that has IC50s of 0.9 nM and 1.8 nM for PDGFRA and PDGFRB, respectively [2].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Heinrich MC, et al.Crenolanib inhibits the drug-resistant PDGFRA D842V mutation associated with imatinib-resistant gastrointestinal stromal tumors. Clin Cancer Res, 2012, Jun 27.
		</p>
		<p>
			<span class="ml-20">[2].</span>Heinrich M, et al. CP-868,596, a highly potent PDGFR inhibitor, inhibits phosphorylation of the imatinib-resistant PDGFRA D842V activating mutation associated with advanced GIST. AACR, 2011, Abstract 3586.
		</p>
	</div>
	<div class="h2">2.&nbsp;&nbsp;Imatinib</div>
	<ul>
		<li><span class="font-bold">Description:</span>	
		<li><span class="font-bold">IC50 value:</span> 100 nM (PDGFR) [1]; 100 nM (c-Kit) [2]
		<li>Imatinib is a multi-target inhibitor of v-Abl, c-Kit and PDGFR with IC50 of 0.6 μM, 0.1 μM and 0.1 μM, respectively. Imatinib is used to treat chronic myelogenous leukemia (CML), gastrointestinal stromal tumors (GISTs) and a number of other malignancies.</li>
		<li><span class="font-bold">in vitro:</span> In vitro assays for inhibition of a panel of tyrosine and serine/threonine protein kinases show that Imatinib inhibits the v-Abl tyrosine kinase and PDGFR potently with an IC50 of 0.6 and 0.1 μM, respectively [1]. Imatinib inhibits the SLF-dependent activation of wild-type c-kit kinase activity with a IC50 for these effects of approximately 0.1 μM, which is similar to the concentration required for inhibition of PDGFR [2]. Imatinib exhibits growth-inhibitory activity on the human bronchial carcinoid cell line NCI-H727 and the human pancreatic carcinoid cell line BON-1 with an IC50 of 32.4 and 32.8 μM, respectively [3].
		<li><span class="font-bold">in vivo:</span> In the PS-ASODN group, tumor growth was inhibited by 59.437%, which was markedly higher than in the imatinib group (11.071%) and liposome negative control group [4]. Cohorts of mice were maintained on chow formulated with imatinib 0.5 mg/g or control chow for the duration of the experiment [5].
		<li><span class="font-bold">Toxicity: </span>Imatinib is mainly indicated for chronic myeloid leukemia and gastrointestinal stromal tumors but is also prescribed by dermatologists for dermatofibrosarcoma protuberans, systemic sclerosis, and systemic mastocytosis, among other conditions. Most adverse effects are mild or moderate and therapy is generally well tolerated [6].
		<li><span class="font-bold">Clinical trial:</span> Imatinib Mesylate And Mycophenolate Mofetil For Steroid-Refractory Sclerotic/Fibrotic cGVHD In Children. Phase 2
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span> Buchdunger E, et al. Selective inhibition of the platelet-derived growth factor signal transduction pathway by a protein-tyrosine kinaseinhibitor of the 2-phenylaminopyrimidine class. Proc Natl Acad Sci U S A. 1995 Mar 28;92(7):2558-62.
		</p>
		<p>
			<span class="ml-20">[2].</span>Heinrich MC, et al. Inhibition of c-kit receptor tyrosine kinase activity by STI 571, a selective tyrosine kinase inhibitor. Blood. 2000 Aug 1;96(3):925-32.
		</p>
		<p>
			<span class="ml-20">[3].</span> Yao JC, et al. Clinical and in vitro studies of imatinib in advanced carcinoid tumors. Clin Cancer Res. 2007 Jan 1;13(1):234-40.
		</p>
		<p>
			<span class="ml-20">[4].</span> Sun XC, et al. Depletion of telomerase RNA inhibits growth of gastrointestinal tumors transplanted in mice. World J Gastroenterol. 2013 Apr 21;19(15):2340-7.
		</p>
		<p>
			<span class="ml-20">[5].</span> Horton JA, et al. Inhibition of radiation-induced skin fibrosis with imatinib. Int J Radiat Biol. 2013 Mar;89(3):162-70.
		</p>
		<p>
			<span class="ml-20">[6].</span>Pretel-Irazabal M, et al. Adverse Skin Effects of Imatinib, a Tyrosine Kinase Inhibitor. Actas Dermosifiliogr. 2013 Apr 30. pii: S0001-7310(13)00108-7.
		</p>
	</div>
	<div class="h2">3.&nbsp;&nbsp; Linifanib</div>
	<ul>
		<li><span class="font-bold">Description:</span>Linifanib (ABT-869; AL-39324) is a novel, potent ATP-competitive VEGFR/PDGFR inhibitor for KDR, CSF-1R, Flt-1/3 and PDGFRβ with IC50 of 4 nM, 3 nM, 3 nM/4 nM and 66 nM respectively.</li>
		<li><span class="font-bold">IC50 value:</span>  4 nM/3 nM/3 nM/4 nM /66 nM(KDR/CSF-1R/Flt-1/Flt-3PDGFRβ) [1]</li>
		<li><span class="font-bold">Target:</span> VEGFR/PDGFR</li>
		<li><span class="font-bold">in vitro:</span> Linifanib shows inhibitory to Kit, PDGFRβ and Flt4 with IC50 of 14 nM, 66 nM and 190 nM in kinases assay. Linifanib also inhibits ligand-induced KDR, PDGFRβ, Kit, and CSF-1R phosphorylation with IC50 of 2 nM, 2 nM, 31 nM and 10 nM at cellular level and this cellular potency could be affected by serum protein. Linifanib suppresses VEGF-stimulated HUAEC proliferation with IC50 of 0.2 nM. While Linifanib has weak activity against tumor cells which are not induced by VEGF or PDGF, except for MV4-11 leukemia cells (with constitutively active form of Flt3) with IC50 of 4 nM. Linifanib could cause a decrease in S and G2-M phases with a corresponding increase in the sub-G0-G1 apoptotic population in MV4-11 cells [1]. Linifanib binds to the ATP-binding site of CSF-1R with Ki of 3 nM [2]. Linifanib (10 nM) exhibits a reduced phosphorylation of Akt at Ser473 and decreased phosphorylation of GSK3βat Ser9 in Ba/F3 FLT3 ITD cell lines [3].</li>
		<li><span class="font-bold">in vivo:</span> Linifanib (0.3 mg/kg) results in complete inhibition of KDR phosphorylation in lung tissue. Linifanib also inhibits the edema response with ED50 of 0.5 mg/kg. Linifanib (7.5 and 15 mg/kg, bid) significantly inhibits both bFGF- and VEGF-induced angiogenesis in the cornea. Linifanib inhibits tumor growth in flank xenograft models including HT1080, H526, MX-1 and DLD-1 with ED75 from 4.5-12 mg/kg. Linifanib also shows efficacy in A431 and MV4-11 xenografts at low dose levels. Linifanib (12.5 mg/kg bid) reveals a decrease of microvasculure density in MDA-231 xenograft. Linifanib shows a Cmax and AUC24 hours with 0.4 μg/mL and 2.7 μg?hour/mL in HT1080 fibrosarcoma model [1].</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Albert DH, et al. Preclinical activity of ABT-869, a multitargeted receptor tyrosine kinase inhibitor. Mol Cancer Ther, 2006, 5(4), 995-1006.
		</p>
		<p>
			<span class="ml-20">[2].</span> Guo J, et al. Inhibition of phosphorylation of the colony-stimulating factor-1 receptor (c-Fms) tyrosine kinase in transfected cells by ABT-869 and other tyrosine kinase inhibitors. Mol Cancer Ther, 2006, 5(4), 1007-1013.
		</p>
		<p>
			<span class="ml-20">[3].</span> Hernandez-Davies JE, et al. The multitargeted receptor tyrosine kinase inhibitor linifanib (ABT-869) induces apoptosis through an Akt and glycogen synthase kinase 3β-dependent pathway. Mol Cancer Ther, 2011, 10(6), 949-959.
		</p>
	</div>
	<div class="h1">PTEN</div>
	<p>PTEN (Phosphatase and tensin homologue deleted on chromosome 10), a phosphoinositide 3-phosphatase, is an important regulator of insulin-dependent signaling. The loss or impairment of PTEN results in an antidiabetic impact, which led to the suggestion that PTEN could be an important target for drugs against type II diabetes. PTEN has a much wider active site cleft enabling it to bind the PtdIns(3,4,5)P3 substrate. a highly potent and specific inhibitor of PTEN that increases cellular PtdIns(3,4,5)P3 levels, phosphorylation of Akt, and glucose uptake in adipocytes at nanomolar concentrations.</p>
	<div class="h2">1.&nbsp;&nbsp; VO-Ohpic trihydrate</div>
	<ul>
		<li><span class="font-bold">Description:</span>VO-Ohpic trihydrate is an inhibitor of PTEN (phosphatase and tensin homologue deleted on chromosome 10).</li>
		<li><span class="font-bold">IC50 value:</span></li>
		<li><span class="font-bold">Target:</span> PTEN</li>
		<li>VO-OHpic is an extremely potent inhibitor of PTEN with nanomolar affinity in vitro and in vivo. Given the importance of this inhibitor for future drug design and development, its mode of action needed to be elucidated. It was discovered that inhibition of recombinant PTEN by VO-OHpic is fully reversible. Both K(m) and V(max) are affected by VO-OHpic, demonstrating a noncompetitive inhibition of PTEN. The inhibition constants K(ic) and K(iu) were determined to be 27?±?6 and 45?±?11 nM, respectively. Using the artificial phosphatase substrate 3-O-methylfluorescein phosphate (OMFP) or the physiological substrate phosphatidylinositol 3,4,5-triphosphate (PIP(3)) comparable parameters were obtained suggesting that OMFP is a suitable substrate for PTEN inhibition studies and PTEN drug screening.</li>
	</ul>
	<div class="reference">
		<div class="title">Reference: </div>
		<p>
			<span class="ml-20">[1].</span>Rosivatz, E., et al., A small molecule inhibitor for phosphatase and tensin homologue deleted on chromosome 10 (PTEN). ACS Chem. Biol. 1, 780-790, (2006)
		</p>
		<p>
			<span class="ml-20">[2].</span> Mak LH, Vilar R, Woscholski R. Characterisation of the PTEN inhibitor VO-OHpic. J Chem Biol. 2010 Oct;3(4):157-63.
		</p>
		<p>
			<span class="ml-20">[3].</span> Zu L, Shen Z, Wesley J, Cai ZP. PTEN inhibitors cause a negative inotropic and chronotropic effect in mice. Eur J Pharmacol. 2011 Jan 10;650(1):298-302.
		</p>
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