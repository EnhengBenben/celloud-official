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
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/print_gdd.css?version=150823">
</head>
<body>
<a href="javascript:void(0)" onclick="preview(this)" class="btn btn-default" id="change" style="float:right;margin-top:10px;margin-right:-80px;">打印</a>
<form id="form">
<input type="hidden" name="cmpId" id="cmpId" value="${cmpReport.id }">
<input type="hidden" name="cmpReport.dataKey" value="${cmpReport.dataKey }">
<input type="hidden" name="cmpReport.userId" value="${cmpReport.userId }">
<input type="hidden" name="cmpReport.projectId" value="${cmpReport.projectId" }">
<input type="hidden" name="infos" id="infos">
  <section class="section0 border1 w3cbbs">
	<div class="header">
		<img src="<%=request.getContextPath()%>/images/report/yanda_print.png">
		<h1>新生儿遗传代谢疾病检测分析报告</h1>
	</div>
	<div class="titletype">
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
        <li>分析日期：<span><input type="text" id="analysisDate" name="cmpFill.analysisDate" value="${cmpReport.cmpFilling.analysisDate }"></span></li>
        <li>分子生物实验操作：<span><input type="text" class="input100" id="molecularBioExperOper" name="cmpFill.molecularBioExperOper" value="${cmpReport.cmpFilling.molecularBioExperOper }"></span></li>
        <li>基因分析：<span><input type="text" id="geneAnalysis" name="cmpFill.geneAnalysis" value="${cmpReport.cmpFilling.geneAnalysis }"></span></li>
    </ul>
    <h4>2、送检要求</h4>
    <div class="info">
    	<p>对420种新生儿遗传代谢疾病相关的118个基因，共10998个突变位点进行检测。</p>
    </div>
  </section>
</form>
  <section class="section2 border1 w3cbbs">
	<h3>二、基因检测结果简述</h3>
	<h4>1、检测结果</h4>
	<table class="table table-striped-green w3cbbs">
      <thead>
        <tr>
            <th style="text-align:center">疾病类型</th>
            <th colspan="2" style="text-align:center">疾病名称</th>
            <th style="text-align:center">基因</th>
            <th style="text-align:center">突变位点数</th>
        </tr>	
      </thead>
      <tbody>
          <c:set var="tempCount" value="0"></c:set><%--临时变量 --%>  
          <c:set var="rowspanCount" value="0"></c:set><%--记录合并列数 --%>  
          <c:set var="tempFrist" value="0"></c:set><%--记录合并开始位置 --%>  
          <c:set var="tempEnd" value="-1"></c:set><%--记录合并结束位置 --%>  
          <c:forEach items="${gsrList}" var="accountConfig" varStatus="status" >  
             <tr>  
                 <%--利用一个结果集List<Bean>来生成，数据过多会加重客户断负担 --%>  
                 <c:if test="${status.index>=tempEnd}">  
                     <c:set var="rowspanCount" value="0"></c:set><%--清楚历史数据 --%>  
                     <c:forEach var="item2" items="${gsrList}" varStatus="status2">  
                             <%-- tablename指要合并的属性 --%>  
                         <c:if test="${accountConfig.diseaseType==item2.diseaseType}">  
                             <c:set var="tempFrist" value="${status.index }"></c:set>  
                             <c:set var="rowspanCount" value="${rowspanCount+1 }"></c:set>  
                             <c:set var="tempEnd" value="${tempFrist+rowspanCount }"></c:set>  
                         </c:if>  
                     </c:forEach>  
                 </c:if>  
                 <c:if test="${status.index==tempFrist}">  
                 	<td rowspan="${rowspanCount}" style="text-align:center;vertical-align: middle;">  
                         <%-- tablename指要合并的属性 --%>  
                         ${accountConfig.diseaseType}   
                 	</td>
                 </c:if>
                 <td>${accountConfig.diseaseEngName }</td>
	        	 <td>${accountConfig.diseaseName }</td>
	        	 <td>${accountConfig.gene }</td>
	        	 <td>${accountConfig.mutNum }</td>    
             </tr>
          </c:forEach>
      </tbody>
    </table>
  </section>
  <section class="section3 border1 w3cbbs">
    <h4>2、测序数据质量分析报告</h4>
	<div class="h2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Basic Statistics</div>
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
	<div class="h2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Basic Statistics</div>
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
  </section>
  <section class="section3 border1 w3cbbs">
  	<h3>三、附录1——检测方法与技术、序列质量控制及覆盖度说明</h3>
<!--     <div class="h1">附录1、检测方法与技术、序列质量控制及覆盖度说明</div> -->
    <h4>1.&nbsp;&nbsp;检测方法与技术：</h4>
	<p>
		设计118个基因外显子的探针序列进行捕获，采用MISEQ测序仪进行高通量测序，得到原始数据。
	</p>
	<p>
		通过FASTQC对原始数据进行质控，去除末端质量低于30的碱基。去除adaptor接头序列以及测序引物序列。
	</p>
	<h4>2.&nbsp;&nbsp;基因突变：</h4>
	<p>
		采用bowtie比对软件，将过滤得到的高质量序列比对到50个基因序列上。通过bcftools得到mpileup文件并解析。判断碱基覆盖度及比对质量找到突变位点。
	</p>
  </section>
  <section class="section3 border1 w3cbbs">
    <h3>四、附录2——50种新生儿可治愈遗传代谢疾病</h3>
<!--     <div class="h1">附录2、50种新生儿可治愈遗传代谢疾病</div> -->
    <table class="table table-striped-green w3cbbs">
      <thead>
        <tr>
            <th style="text-align:center">疾病类型</th>
            <th colspan="2" style="text-align:center">疾病名称</th>
            <th style="text-align:center">基因</th>
        </tr>	
      </thead>
      <tbody>
        <tr>
            <td rowspan="12" style="vertical-align: middle;">肝病肾脏类</td><td></td><td>进行性家族性肝内胆汁瘀积2型</td><td></td>
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
            <td>Alkaptonuria</td><td>黑尿病</td><td>HGD</td>
        </tr>
        <tr>
            <td>Methylmalonic aciduria</td><td>甲基丙二酸尿症</td><td>MUT</td>
        </tr>
        <tr>
            <td>Malonic & methylmalonic aciduria, combined</td><td>甲基丙二酸尿症</td><td>ACSF3</td>
        </tr>
        <tr>
            <td>phenylketonurics</td><td>苯丙酮尿症</td><td></td>
        </tr>
        <tr>
            <td>Hypoparathyroidism, infanitle-onset & peripheral polyneuropathy</td><td>甲状旁腺功能减退-生长迟缓-畸形综合征</td><td>HADHB</td>
        </tr>
        <tr>
            <td>Argininosuccinic aciduria</td><td>瓜氨酸血症1型</td><td>ASL</td>
        </tr>
        <tr>
            <td style="vertical-align: middle;">骨骼类</td><td></td><td>抗维生素D佝偻病</td><td></td>
        </tr>
        <tr>
            <td style="vertical-align: middle;">呼吸系统类</td><td></td><td>早产儿呼吸窘迫综合症</td><td></td>
        </tr>
        <tr>
            <td rowspan="10" style="vertical-align: middle;">免疫缺陷类</td><td>Glucose-6-PhosphateDehydrogenase(G6PD)deficiency</td><td>葡萄糖-6-磷酸脱氢酶缺乏症</td><td></td>
        </tr>
        <tr>
            <td>Medium chain acyl CoA dehydrogenase deficiency</td><td>中链酰基辅酶A脱氢酶缺乏症</td><td>ACADM</td>
        </tr>
        <tr>
            <td>Biotinidase deficiency</td><td>生物素酶缺乏症</td><td>BTD</td>
        </tr>
        <tr>
            <td>HMG-CoA lyase deficiency</td><td>HMG-CoA裂解酶缺乏</td><td>HMGCL</td>
        </tr>
        <tr>
            <td>congenitaldisordersofglycosylation</td><td>先天性糖蛋白糖基化缺陷Ia型</td><td></td>
        </tr>
        <tr>
            <td></td><td>磷酸丝氨酸转氨酶缺陷</td><td></td>
        </tr>
        <tr>
            <td>Hyperammonaemia</td><td>鸟氨酸氨甲酰基转移酶缺乏症（高氨血症）</td><td></td>
        </tr>
        <tr>
            <td></td><td>酪氨酸羟化酶缺乏症</td><td></td>
        </tr>
        <tr>
            <td>Congenital adrenal hyperplasia </td><td>类固醇21-β-羟化酶B1缺乏性先天性肾上腺皮质增生症</td><td></td>
        </tr>
        <tr>
            <td>Fabry disease</td><td>法布瑞氏症</td><td>GLA</td>
        </tr>
        <tr>
            <td rowspan="7" style="vertical-align: middle;">内分泌类</td><td>Glycogen storage disease 2</td><td>糖原累积病II型</td><td>GAA</td>
        </tr>
        <tr>
            <td>Galactokinase deficiency</td><td>半乳糖血症</td><td>GALK1</td>
        </tr>
        <tr>
            <td>Maple syrup urine disease</td><td>枫糖尿病（支链酮酸尿症）</td><td>DBT</td>
        </tr>
        <tr>
            <td>Hypoglycaemia, hyperinsulinaemic</td><td>遗传性高胰岛素血症性低血糖症4型</td><td>HADH</td>
        </tr>
        <tr>
            <td></td><td>先天性耳聋伴甲状腺肿大</td><td></td>
        </tr>
        <tr>
            <td></td><td>先天性甲状腺机能低下症4型</td><td></td>
        </tr>
        <tr>
            <td></td><td>先天吸收不良性腹泻  </td><td></td>
        </tr>
        <tr>
            <td style="vertical-align: middle;">皮肤类</td><td>Xeroderma pigmentosum</td><td>X连锁自闭症易感3型</td><td></td>
        </tr>
        <tr>
            <td rowspan="6" style="vertical-align: middle;">神经类</td><td>Neural tube defect, increased risk, association with</td><td>神经管缺陷</td><td>MTHFR</td>
        </tr>
        <tr>
            <td></td><td>预激综合症</td><td></td>
        </tr>
        <tr>
            <td></td><td>脊椎性肌萎缩症Ⅱ(小儿)</td><td></td>
        </tr>
        <tr>
            <td></td><td>雷特氏症</td><td></td>
        </tr>
        <tr>
            <td></td><td>β地中海贫血，显性包涵体型</td><td></td>
        </tr>
        <tr>
            <td></td><td>家族性肾视网膜营养不良1型</td><td></td>
        </tr>
        <tr>
            <td style="vertical-align: middle;">胃肠病类</td><td>diarrhea diseases</td><td>杜氏肌营养不良</td><td></td>
        </tr>
        <tr>
            <td rowspan="11" style="vertical-align: middle;">心血管类</td><td>Hereditary fructosein tolerance</td><td>遗传性果糖不耐症</td><td></td>
        </tr>
        <tr>
            <td>Tyrosinaemia 1</td><td>遗传性酪氨酸血症1型</td><td>FAH</td>
        </tr>
        <tr>
            <td>Gaucher disease 1</td><td>戈谢病Ⅰ型</td><td>GBA</td>
        </tr>
        <tr>
            <td>Isovaleric acidaemia</td><td>异戊酸血症</td><td>IVD</td>
        </tr>
        <tr>
            <td>Glutaricacidaemia 1</td><td>戊二酸血症I型</td><td></td>
        </tr>
        <tr>
            <td>Cardiomyopathy, hypertrophic</td><td>肥厚型心肌病</td><td>GLA</td>
        </tr>
        <tr>
            <td></td><td>着色性干皮病</td><td></td>
        </tr>
        <tr>
            <td>Thalassemia</td><td>血色病3型</td><td></td>
        </tr>
        <tr>
            <td>Beta-thalassemia </td><td>地中海贫血</td><td></td>
        </tr>
        <tr>
            <td>dilatedcardiomyopathy</td><td>X连锁自闭症易感3型</td><td></td>
        </tr>
        <tr>
            <td>Cardiomyopathy, dilated</td><td>扩张型心肌病</td><td>LAMP2</td>
        </tr>
        <tr>
            <td></td><td></td><td>精氨酸琥珀酸尿症</td><td></td>
        </tr>
      </tbody>
    </table>
  </section>
  <section class="section3 border1 w3cbbs">
    <h3>五、附录3——美国医保必查的新生儿遗传代谢疾病</h3>
<!--     <div class="h1">附录3、美国医保必查的新生儿遗传代谢疾病</div> -->
    <table class="table table-green table-striped-green table-padding0">
      <thead>
      	<tr>
      		<th colspan="2">Disease</th><th>Abbr.</th><th>Gene</th>
      	</tr>
      </thead>
      <tbody>
    	<tr>
			<td>Carnitine uptake defect </td><td>肉毒碱摄取缺陷</td><td>CUD </td><td>&nbsp;</td>
		</tr>	
		<tr>
			<td>Long-chain 3-hydroxyacyl-CoA dehydrogenase deficiency</td><td>长链3-羟基酰基辅酶A缺乏</td><td>LCHAD </td><td>HADHA</td>
		</tr>	
		<tr>
			<td>Medium chain acyl CoA dehydrogenase deficiency</td><td>中链脂酰辅酶A脱氢酶缺乏</td><td>MCAD </td><td>ACADM</td>
		</tr>	
		<tr>
			<td>Mitochondrial trifunctional protein deficiency</td><td>线粒体三功能蛋白缺乏症</td><td>TFP </td><td>HADHB</td>
		</tr>	
		<tr>
			<td>Very long chain acyl-CoA dehydrogenase deficiency</td><td>极长链酰基辅酶A脱氢酶缺乏</td><td>VLCAD </td><td>ACADVL</td>
		</tr>	
		<tr>
			<td>Glutaricacidaemia I</td><td>Ⅰ型戊二酸尿症</td><td>GA1 </td><td>GCDH</td>
		</tr>	
		<tr>
			<td>3-hydroxy-3-methylglutaric aciduria</td><td>3-羟基-3-甲基戊二酸尿症</td><td>HMG </td><td>HMGCL</td>
		</tr>	
		<tr>
			<td>Isovaleric acidaemia</td><td>异戊酸血症</td><td>IVA </td><td>IVD</td>
		</tr>	
		<tr>
			<td>3-methylcrotonyl-CoA carboxylase deficiency</td><td>3-甲基巴豆酰辅酶A羧化酶缺乏</td><td>3-MCC </td><td>MCCC1</td>
		</tr>	
		<tr>
			<td>Methylmalonic acidaemia</td><td>甲基丙酸血症</td><td>Cbl A,B </td><td>MMAA</td>
		</tr>	
		<tr>
			<td>3-ketothiolase deficiency</td><td>3-酮硫解酶缺乏症</td><td>BKT </td><td>ACAT1</td>
		</tr>	
		<tr>
			<td>Methylmalonic acidaemia</td><td>甲基丙酸血症</td><td>MUT </td><td>MMAA</td>
		</tr>	
		<tr>
			<td>Propionic acidemia </td><td>丙酸血症</td><td>PROP </td><td></td>
		</tr>	
		<tr>
			<td>Holocarboxylase synthetase deficiency</td><td>全羟化酶合成酶缺乏症</td><td>MCD </td><td>HLCS</td>
		</tr>	
		<tr>
			<td>Argininosuccinic aciduria</td><td>精氨基琥珀酸尿症</td><td>ASA </td><td>ASL</td>
		</tr>	
		<tr>
			<td>Citrullinaemia</td><td>瓜氨酸血症</td><td>CIT </td><td>ASS1</td>
		</tr>	
		<tr>
			<td>Homocystinuria</td><td>高胱氨酸尿症</td><td>HCY </td><td>MTRR</td>
		</tr>	
		<tr>
			<td>Maple syrup urine disease</td><td>枫糖尿病</td><td>MSUD </td><td>DBT</td>
		</tr>	
		<tr>
			<td>Classic phenylketonuria </td><td></td><td>PKU </td><td></td>
		</tr>	
		<tr>
			<td>Tyrosinaemia 1</td><td>高酪氨酸血症1型</td><td>TYR I </td><td>FAH</td>
		</tr>	
		<tr>
			<td>Primary congenital hypothyroidism </td><td></td><td>CH </td><td></td>
		</tr>	
		<tr>
			<td>Congenital adrenal hyperplasia </td><td></td><td>CAH </td><td></td>
		</tr>	
		<tr>
			<td>Sickle cell anemia </td><td></td><td>Hb SS </td><td></td>
		</tr>	
		<tr>
			<td>S, Beta-thalassemia </td><td></td><td>Hb S/ßTh </td><td></td>
		</tr>	
		<tr>
			<td>S, C disease </td><td></td><td>Hb S/C </td><td></td>
		</tr>	
		<tr>
			<td>Biotinidase deficiency</td><td>生物素酶缺乏症</td><td>BIOT </td><td>BTD</td>
		</tr>	
		<tr>
			<td>Classic galactosemia </td><td></td><td>GALT </td><td></td>
		</tr>	
		<tr>
			<td>Cystic fibrosis </td><td></td><td>CF </td><td></td>
		</tr>	
		<tr>
			<td>Critical congenital heart disease</td><td></td><td>CCHD</td><td></td>
		</tr>	
		<tr>
			<td>Severe combined immunodeficiency</td><td></td><td>SCID</td><td></td>
		</tr>	
		<tr>
			<td>Hearing loss </td><td></td><td>HEAR </td><td></td>
		</tr>	
		<tr>
			<td>Carnitine acylcarnitine translocase deficiency </td><td></td><td>CACT </td><td></td>
		</tr>	
		<tr>
			<td>Carnitine palmitoyltransferase 1 deficiency</td><td>肉碱棕榈酰转移酶1缺乏</td><td>CPT-IA</td><td>CPT1A</td>
		</tr>	
		<tr>
			<td>Carnitine palmitoyltransferase 2 deficiency</td><td>肉碱棕榈酰转移酶Ⅱ缺乏症</td><td>CPT-II </td><td>CPT2</td>
		</tr>	
		<tr>
			<td>2,4 Dienoyl-CoA reductase deficiency </td><td></td><td>DE RED </td><td></td>
		</tr>	
		<tr>
			<td>Glutaricacidaemia 2</td><td>Ⅱ型戊二酸血症</td><td>GA-2 </td><td>ETFDH</td>
		</tr>	
		<tr>
			<td>Medium-chain ketoacyl-CoA thiolase deficiency </td><td></td><td>MCKAT </td><td></td>
		</tr>	
		<tr>
			<td>Medium and short-chain 3-hydroxyacyl-CoA dehydrogenase deficiency</td><td>中链，短链-3羟烷基辅酶A脱氢酶缺乏</td><td>M/SCHAD </td><td>HADH</td>
		</tr>	
		<tr>
			<td>Short-chain 3-hydroxyacyl-CoA dehydrogenase deficiency</td><td>短链3-羟基乙酰辅酶A脱氢酶缺乏</td><td>SCAD </td><td>HADH</td>
		</tr>	
		<tr>
			<td>2-Methyl-3-hydroxybutyric acidemia </td><td></td><td>2M3HBA </td><td></td>
		</tr>	
		<tr>
			<td>2-Methylbutyrylglycinuria </td><td></td><td>2MBG </td><td></td>
		</tr>	
		<tr>
			<td>3-Methylglutaconic aciduria </td><td>3-甲基戊烯二酸尿症1型</td><td>3MGA </td><td>AUH</td>
		</tr>	
		<tr>
			<td>Methylmalonic aciduria & homocystinuria, cblC type</td><td>甲基丙酸血症，高胱氨酸尿</td><td>Cbl-C, D</td><td>MMACHC</td>
		</tr>	
		<tr>
			<td>Isobutyrylglycinuria </td><td></td><td>IBG </td><td></td>
		</tr>	
		<tr>
			<td>Malonic acidemia </td><td></td><td>MAL </td><td></td>
		</tr>	
		<tr>
			<td>Argininaemia</td><td>先天性精氨酸代谢失常</td><td>ARG </td><td>ARG1</td>
		</tr>	
		<tr>
			<td>Biopterin defect in cofactor biosynthesis </td><td></td><td>BIOPT-BS </td><td></td>
		</tr>	
		<tr>
			<td>Biopterin defect in cofactor regeneration </td><td></td><td>BIOPT-REG </td><td></td>
		</tr>	
		<tr>
			<td>Citrullinaemia</td><td>瓜氨酸血症</td><td>CIT II </td><td>ASS1</td>
		</tr>	
		<tr>
			<td>Benign hyperphenylalaninemia </td><td></td><td>H-PHE </td><td></td>
		</tr>	
		<tr>
			<td>Hypermethioninaemia</td><td>高甲硫氨酸尿症</td><td>MET </td><td>ADK</td>
		</tr>	
		<tr>
			<td>Tyrosinemia, type II </td><td></td><td>TYR II </td><td></td>
		</tr>	
		<tr>
			<td>Tyrosinaemia 3</td><td>高酪氨酸血症3型</td><td>TYR III </td><td>HPD</td>
		</tr>	
		<tr>
			<td>Galactoepimerase deficiency </td><td></td><td>GALE </td><td></td>
		</tr>	
		<tr>
			<td>Galactokinase deficiency</td><td>半乳糖血症，半乳糖酶缺乏症</td><td>GALK </td><td>GALK1</td>
		</tr>	
		<tr>
			<td>Hemoglobinopathies </td><td></td><td>Var Hb </td><td></td>
		</tr>
	   </tbody>
    </table>
  </section>
  <section class="section3 border1 w3cbbs">
    <h3>六、附录4——常见700种新生儿遗传疾病</h3>
<!--     <h4 class="h1">附录4、常见700种新生儿遗传疾病</h4> -->
    <table class="table table-green table-striped-green table-padding0">
		<thead style="display:table-header-group;">
		  <tr>
			<th colspan="4" style="text-align: left">内分泌类</th>
		  </tr>
		</thead>
		<tbody>
			<tr>
				<td>视隔发育不全</td><td>联合性垂体激素缺乏症2型</td><td>矮妖精貌综合征</td><td>胰岛素样和生长因子Ⅰ型缺乏症</td>
			</tr>
			<tr>
				<td>Allgroe综合症</td><td>先天性耳聋伴甲状腺肿大</td><td>肯尼迪病（脊髓延髓肌肉萎缩症）</td><td>状旁腺功能减退-生长迟缓-畸形综合征</td>
			</tr>
			<tr>
				<td>部分雄激素不敏感</td><td>先天性类脂性肾上腺增生</td><td>联合性垂体激素缺乏症3型</td><td>遗传性高胰岛素血症性低血糖症5型</td>
			</tr>
			<tr>
				<td>Kenny-Caffey综合症</td><td>先天性甲状腺机能低下症4型</td><td>X-连锁尿道下裂</td><td>类固醇21-β-羟化酶B1缺乏性先天性肾上腺皮质增生症</td>
			</tr>
			<tr>
				<td>前庭导水管扩大症</td><td>雄激素不敏感综合症</td><td>二型糖尿病合并黑棘皮症</td><td>营养性维生素D缺乏性佝偻病1A型</td>
			</tr>
			<tr>
				<td>Dent病</td><td>Rabson-Medenhall综合症</td><td>先天性肾上腺增生</td><td>联合性垂体激素缺乏症1型</td>
			</tr>
			<tr>
				<td>冷诱导出汗综合症</td><td>X连锁无脑回畸形</td><td>再生障碍性贫血伴血小板减少症</td><td>Meckel综合症（脑膨出-多指-多囊肾综合征）</td>
			</tr>
			<tr>
				<td>骨发育不全2型</td><td>先天性点状软骨发育不全症1型</td><td>重症肌无力综合症，慢通道综合症</td><td>deSanctis-Cacchione综合征：</td>
			</tr>
			<tr>
				<td>Meckel综合征5型</td><td>先天性肾上腺发育不全</td><td>X连锁隐形家族性渗出性玻璃体视网膜病变</td><td>肢根点状软骨发育不良1型</td>
			</tr>
			<tr>
				<td>骨畸形发育不良</td><td>轴后多指畸形A1型</td><td>Donnai-Barrow综合症</td><td>颅骨下颅骨皮肤发育不全B型</td>
			</tr>
			<tr>
				<td>Mohr-Tranebjaerg综合症</td><td>脑积水，无脑回和视网膜发育不全综合征</td><td>X连锁智力低下伴或不伴癫痫发作，ARX相关</td><td>致命性多发翼状胬肉综合征</td>
			</tr>
			<tr>
				<td>Fraser综合征</td><td>先天致死性挛缩综合征1型</td><td>致命性多发翼状胬肉综合征</td><td>轴前多指畸形 Ⅳ型</td>
			</tr>
			<tr>
				<td>脑-眼-面-骨综合征</td><td>爱唐综合症6型；皮肤弹力过度症6型</td><td>&nbsp;</td><td>&nbsp;</td>
			</tr>
		</tbody>
	</table>
	<table class="table table-green table-striped-green table-padding0">
		<thead style="display:table-header-group;">
		  <tr>
			<th colspan="4" style="text-align: left">肝病肾脏类</th>
		  </tr>
		</thead>
		<tbody>
			<tr>
				<td>Meacham综合症</td><td>假性醛固酮减少症</td><td>先天性肾病综合征1型（芬兰型）</td><td>蛋白酶抑制剂引发的肺气肿，肺气肿性肝硬化</td>
			</tr>
			<tr>
				<td>肾病综合征3型</td><td>肾结核3型</td><td>家族性青少年高尿酸血症肾病2型</td><td>张力减退-胱酸尿症综合征</td>
			</tr>
			<tr>
				<td>家族性肝硬化病</td><td>利德尔综合征</td><td>Pierson综合征</td><td>新生儿型巴特综合征1型</td>
			</tr>
			<tr>
				<td>Alport综合征</td><td>肾结核4型</td><td>支气管扩张</td><td>家族性肾视网膜营养不良1型</td>
			</tr>
			<tr>
				<td>Wilms瘤1型</td><td>新生儿巴特综合征2型</td><td>肾肝胰发育不良综合征</td><td>胱氨酸储积症 失明性变种肾病</td>
			</tr>
			<tr>
				<td>Denys-Drash综合征</td><td>肾小管发育不良</td><td>支气管扩张3型</td><td>家族性肾视网膜营养不良4型</td>
			</tr>
			<tr>
				<td>肾结核1型</td><td>低镁血症5型</td><td>胱氨酸贮积症，迟发型青少年肾病</td><td>原发性肾病综合征伴弥漫性系膜硬化</td>
			</tr>
			<tr>
				<td>Frasier综合征</td><td>肾小管发育不良</td><td>家族性肾视网膜营养不良5型</td><td>支气管扩张症伴或不伴汗氯化物升高</td>
			</tr>
			<tr>
				<td>肾结核2型</td><td>胱氨酸尿症</td><td>常染色体隐性多囊性肾病</td><td>张力减退-胱酸尿症综合征</td>
			</tr>
			<tr>
				<td>Joubert氏综合征4型</td><td>糖尿病微血管并发症，易感3型</td><td>&nbsp;</td><td>&nbsp;</td>
			</tr>	
		</tbody>
	</table>
	<table class="table table-green table-striped-green table-text-center table-padding0">
		<thead style="display:table-header-group;">
		  <tr>
			<th colspan="4" style="text-align: left">骨骼类</th>
		  </tr>
		</thead>
		<tbody>
			<tr>
				<td>软骨生成不全2型</td><td>Antley-Bixler综合症</td><td>多发性内生软骨瘤病</td><td>Weissenbacher-Zweymuller综合征</td>
			</tr>
			<tr>
				<td>Kniest发育不良</td><td>骺端发育不良（干骺端骨软骨发育不良）</td><td>成骨不全1型</td><td>婴儿型全身性玻璃样变性</td>
			</tr>
			<tr>
				<td>不发育型发育不良</td><td>儿童低磷酸酯酶症</td><td>渗出性玻璃体视网膜病变4型</td><td>Apert综合征（箭头并指综合征1型）</td>
			</tr>
			<tr>
				<td>Pfeiffer综合征</td><td>成骨不全2b型</td><td>积液、异位钙化和虫蛀性骨骼发育不良</td><td>婴幼儿低磷酸酯酶症</td>
			</tr>
			<tr>
				<td>耳脊椎骨骺发育不良</td><td>常染色体显性骨硬化病1型</td><td>史蒂克勒氏综合征1型</td><td>Beare-Stevenson皮肤旋纹综合征</td>
			</tr>
			<tr>
				<td>成骨不全7型</td><td>脊椎外围发育不良</td><td>幼年透明蛋白纤维瘤病</td><td>Schwartz-Jampel综合征（软骨障碍性肌强直）</td>
			</tr>
			<tr>
				<td>耳聋53型</td><td>常染色体显性遗传耳聋13型</td><td>史蒂克勒氏综合征3型</td><td>Blomstrand型软骨发育异常</td>
			</tr>
			<tr>
				<td>Seckel综合征</td><td>良性全身性骨皮质增厚症</td><td>成骨不全8型</td><td>原发性股骨头缺血性坏死</td>
			</tr>
			<tr>
				<td>Eiken骨骼发育不良</td><td>非典型史蒂克勒氏综合征</td><td>常染色体隐形低磷酸盐血症性佝偻病</td><td>维生素D依赖性佝偻病2A型</td>
			</tr>
			<tr>
				<td>青少年Paget病</td><td>Silveman-Handmaker综合征</td><td>尺骨和腓骨缺乏伴严重肢体缺失</td><td>致死性骨硬化发育不良</td>
			</tr>
			<tr>
				<td>FUHRMANN综合症</td><td>股骨头骨骺软骨病</td><td>常染色体隐形骨硬化病1型</td><td>先天性脊椎发育不良</td>
			</tr>
			<tr>
				<td>骨发育不全2型</td><td>轻度软骨异常型骨关节炎</td><td>多发性骨骺发育不良并早发性糖尿病</td><td>中性粒细胞核分叶减少症</td>
			</tr>
			<tr>
				<td>Geleophysic发育不良</td><td>常染色体隐形骨硬化病3型</td><td>先天性牙萌出障碍</td><td>Strudwick型脊柱骨骺发育不良</td>
			</tr>
			<tr>
				<td>软骨发育不全1b型</td><td>多发性骨骺发育异常</td><td>Stuve-Wiedeman综合征</td><td>舟形头畸形合并上颌骺移伴智力低下</td>
			</tr>
			<tr>
				<td>骨畸形发育不良</td><td>Jackson-Weiss颅缝早闭综合征</td><td>常染色体隐形骨硬化病5型</td><td>新生儿致命的软骨发育不良</td>
			</tr>
			<tr>
				<td>软骨毛发发育不全</td><td>vanBuchem病（泛发性骨皮质增厚症）</td><td>多发性骨骺发育异常伴近视及传导性耳聋</td><td>椎骨体扁平致死性骨骺发育异常</td>
			</tr>
			<tr>
				<td>Jansen型干骺端软骨发育不全</td><td>骨质疏松-假性神经胶质瘤综合征</td><td>常染色体隐形脊椎肋骨发育不全</td><td>遗传性家族性颅面骨发育不全</td>
			</tr>
			<tr>
				<td>成骨不全1型</td><td>成骨不全2a型</td><td>成骨不全3型</td><td>成骨不全4型</td>
			</tr>
		</tbody>
	</table>
	<table class="table table-green table-striped-green table-text-center table-padding0">
		<thead style="display:table-header-group;">
		  <tr>
			<th colspan="4" style="text-align: left">呼吸系统类</th>
		  </tr>
		</thead>
		<tbody>
			<tr>
				<td>肺静脉闭塞性疾病</td><td>氨甲酰磷酸合酶1缺乏性高氨血症</td><td>睾丸发育不良伴婴儿猝死综合征</td><td>先天性双侧输精管缺如</td>
			</tr>
			<tr>
				<td>囊性纤维化</td><td>肺泡蛋白沉着症2型</td><td>原发性肺动脉高压</td><td>肺表面活性剂代谢功能障碍</td>
			</tr>
			<tr>
				<td>肺微泡石症</td><td>早产儿呼吸窘迫综合症</td><td>&nbsp;</td><td>&nbsp;</td>
			</tr>
		</tbody>
	</table>
	<table class="table table-green table-striped-green table-text-center table-padding0">
		<thead style="display:table-header-group;">
		  <tr>
			<th colspan="4" style="text-align: left">免疫缺陷类</th>
		  </tr>
		</thead>
		<tbody>
			<tr>
				<td>色素失调症</td><td>常染色体显性家族性地中海热</td><td>先天性白细胞颗粒异常综合征</td><td>免疫机能丧失，着丝粒的不稳定和面部畸形综合症</td>
			</tr>
			<tr>
				<td>X-连锁联合免疫缺陷</td><td>家族性地中海热</td><td>Hoyeraal-Hreidarsson综合征</td><td>重度联合免疫缺陷症，T细胞NK细胞抑制型</td>
			</tr>
			<tr>
				<td>Omenn综合征</td><td>单一性生长素缺乏症3型</td><td>先天性严重中性粒细胞减少症</td><td>无汗型外胚层发育不良伴免疫缺陷</td>
			</tr>
			<tr>
				<td>免疫缺陷性高IgM1型</td><td>X连锁先天性角化不全症</td><td>家族性非典型分支杆菌病</td><td>重度联合免疫缺陷症伴对电离辐射敏感</td>
			</tr>
			<tr>
				<td>Omenn综合征</td><td>X连锁性淋巴细胞增生综合征1型</td><td>肝静脉闭塞病伴免疫缺陷</td><td>腺甙脱氨酶缺乏性重度联合免疫缺陷症</td>
			</tr>
			<tr>
				<td>奈梅亨破损综合症</td><td>无汗性外胚层发育不良无免疫缺陷</td><td>联合细胞免疫和体液免疫缺陷</td><td>侏儒-面部毛细血管扩张综合征（布卢姆综合征）</td>
			</tr>
			<tr>
				<td>血小板增多症1型</td><td>T细胞免疫缺陷、先天性脱发和指甲营养不良综合征</td><td>侵袭性肺炎链球菌病1型</td><td>格里塞利综合征2型</td>
			</tr>
			<tr>
				<td>软骨毛发发育不全</td><td>无外胚层发育不良无汗性伴免疫缺陷</td><td>免疫功能失调伴多发性内分泌病、肠病</td><td>自身免疫性多内分泌腺病综合征1型</td>
			</tr>
			<tr>
				<td>侵袭性肺炎球菌病</td><td>Wiskott-Aldrich综合征</td><td>共济失调性毛细血管扩张症</td><td>严重巨细胞病毒感染伴自身免疫性贫血</td>
			</tr>
			<tr>
				<td>不发育型发育不良</td><td>先天X连锁性无丙种球蛋白血症</td><td>骺端发育不良（干骺端骨软骨发育不良）</td><td>重度联合免疫缺陷症，T细胞B细胞抑制型</td>
			</tr>
			<tr>
				<td>X连锁备解素缺乏</td><td>X连锁重症联合免疫缺陷</td><td>&nbsp;</td><td>&nbsp;</td>
			</tr>
		</tbody>
	</table>
	<table class="table table-green table-striped-green table-text-center table-padding0">
		<thead style="display:table-header-group;">
		  <tr>
			<th colspan="4" style="text-align: left">皮肤病类</th>
		  </tr>
		</thead>
		<tbody>
			<tr>
				<td>扩张型心肌病1A型</td><td>Imna相关性先天性肌肉萎缩症</td><td>常染色体隐性皮肤松弛2a型</td><td>痒疹样大疱性表皮松解症</td>
			</tr>
			<tr>
				<td>着色性干皮病</td><td>X连锁选择性牙齿发育不全1型</td><td>斯洛文尼亚型心手综合征</td><td>非大疱性鱼鳞病样红皮病</td>
			</tr>
			<tr>
				<td>致死性限制性真皮病</td><td>扩张型心肌病1A型</td><td>常染色体隐性营养不良性大疱性表皮松解</td><td>遗传性A群着色性干皮病</td>
			</tr>
			<tr>
				<td>板层性鱼鳞病</td><td>脱髓鞘周围神经病合并中心髓鞘形成障碍伴Waardenburg综合征及hischsprung</td><td>非赫利茨型交界型大疱性表皮松解症</td><td>着色性干皮病-恶性肿瘤综合征，着色性干皮病白痴综合征</td>
			</tr>
			<tr>
				<td>ABCD综合征</td><td>扩张性心肌病伴羊毛状发及皮肤角化病</td><td>早年衰老综合症</td><td>遗传性D群着色性干皮病</td>
			</tr>
			<tr>
				<td>板层状鱼鳞病1型</td><td>瓦登伯格综合征2e型</td><td>非赫利茨型交界型大疱性表皮松解症</td><td>着色性干皮病互补D组</td>
			</tr>
			<tr>
				<td>Cockayne综合征B型</td><td>老年性黄斑变性3型</td><td>常染色体隐性皮肤松弛症1型</td><td>遗传性E群着色性干皮病</td>
			</tr>
			<tr>
				<td>瓦登伯格综合征4b型</td><td>表皮松解症伴先天性皮肤局部缺失和指甲畸形</td><td>非赫利茨型交界型大疱性表皮松解症</td><td>肢带型肌营养不良症</td>
			</tr>
			<tr>
				<td>deSanctis-Cacchione综合征：</td><td>颅骨下颌骨皮肤发育不全A型</td><td>咽喉皮质增生综合征</td><td>遗传性F群着色性干皮病</td>
			</tr>
			<tr>
				<td>常染色体显性皮肤弹力过度症2型</td><td>瓦登伯格综合征4c型</td><td>光敏性毛硫营养失调</td><td>致命皮肤棘层松解的大疱性表皮松解症</td>
			</tr>
			<tr>
				<td>Emery-Dreifuss型肌营养不良</td><td>颅骨下颌骨皮肤发育不全B型</td><td>胫前营养不良性大疱性表皮松解</td><td>遗传性G群着色性干皮病</td>
			</tr>
			<tr>
				<td>常染色体显性皮肤松弛症</td><td>先天性红细胞生成卟啉症</td><td>家族部分性脂肪营养不良2型</td><td>致死性限制性真皮病</td>
			</tr>
			<tr>
				<td>毛发硫营养障碍症</td><td>Schopf-Schulz-Passarge综合征</td><td>丑胎（丑角样鱼鳞病）</td><td>易感型先天性巨结肠病</td>
			</tr>
			<tr>
				<td>脆性角膜综合症</td><td>新生儿鳞癣硬化胆管炎综合征</td><td>家族性致心律失常性右心室发育不良8型</td><td>轴突型腓骨肌萎缩症2B1型</td>
			</tr>
			<tr>
				<td>Waardenburg综合征</td><td>脑颅骨综合症2型</td><td>婴儿型全身性玻璃样变性</td><td>常染色体显性营养不良性大疱性表皮松解</td>
			</tr>
			<tr>
				<td>XFE早衰样综合征</td><td>新生儿暂时性大疱性皮肤松解症</td><td>交界型大疱性表皮松解症，赫利茨型</td><td>轴突型腓骨肌萎缩症2B1型</td>
			</tr>
			<tr>
				<td>脑-眼-面-骨综合征</td><td>大疱性表皮松解与幽门闭锁</td><td>幼年透明蛋白纤维瘤病</td><td>常染色体隐性爱唐综合症心血管型</td>
			</tr>
			<tr>
				<td>皱皮综合征</td><td>常染色体隐性皮肤松弛症1型</td><td>牙齿、指甲、皮肤发育不良综合征</td><td>角化症掌跖病纹状体2型</td>
			</tr>
			<tr>
				<td>X连锁无汗型外胚层发育不良</td><td>皮脆性卷曲毛发综合征</td><td>对紫外线敏感综合征</td><td>&nbsp;</td>
			</tr>
		</tbody>
	</table>
	<table class="table table-green table-striped-green table-text-center table-padding0">
		<thead style="display:table-header-group;">
		  <tr>
			<th colspan="4" style="text-align: left">神经性</th>
		  </tr>
		</thead>
		<tbody>
			<tr>
				<td>Alstrom综合征</td><td>脊髓性肌萎缩3型</td><td>X-连锁肌酸缺乏综合征</td><td>视网膜血管病变伴脑脑白质营养不良</td>
			</tr>
			<tr>
				<td>纳瓦霍人神经肝病</td><td>deSanctis-Cacchione综合征：</td><td>腓骨肌萎缩症，显性中间D型</td><td>婴幼儿发病升序遗传性痉挛性瘫痪</td>
			</tr>
			<tr>
				<td>辅酶Q10缺乏</td><td>脊髓性萎缩4型</td><td>X连锁脊髓肌肉萎缩症</td><td>沃克-沃尔伯格综合征</td>
			</tr>
			<tr>
				<td>Griscelli综合征3型</td><td>脑白质营养不良伴髓鞘发育不良2型（佩梅样病）</td><td>腓骨肌萎缩症1A</td><td>婴幼儿纹状体黑质变性</td>
			</tr>
			<tr>
				<td>辅酶Q10缺乏</td><td>家族性格林-巴利综合征</td><td>X连锁痉挛性截瘫</td><td>先天肌纤维不对称性肌病</td>
			</tr>
			<tr>
				<td>腓骨肌萎缩症1B</td><td>Hallervorden-Spatz病2a型</td><td>脑白质营养不良伴髓鞘发育不良5型</td><td>由于先天性脑导水管狭窄导致的脑积水</td>
			</tr>
			<tr>
				<td>辅酶Q11缺乏</td><td>家族性甲状腺髓样癌</td><td>X连锁无脑回畸形</td><td>先天性黑蒙症10型</td>
			</tr>
			<tr>
				<td>腓骨肌萎缩症1D</td><td>Hallervorden-Spatz病2b型</td><td>脑-眼-面-骨综合征</td><td>幼婴癫痫性脑病1型</td>
			</tr>
			<tr>
				<td>先天性肌肉萎缩症</td><td>脊髓性及萎缩症Ⅰ（小儿）</td><td>家族性肾视网膜营养不良1型</td><td>X连锁隐性遗传性耳聋</td>
			</tr>
			<tr>
				<td>HPRT相关性痛风</td><td>年龄依赖性癫痫性脑病</td><td>腓骨肌萎缩症4E型</td><td>幼婴癫痫性脑病2型</td>
			</tr>
			<tr>
				<td>脊髓性肌萎缩症Ⅱ（小儿）</td><td>家族性肾视网膜营养不良6型</td><td>X连锁智力低下，Lubs型</td><td>先天性肌肉萎缩症1C</td>
			</tr>
			<tr>
				<td>Ⅱ型杆状体肌病</td><td>胼胝体发育不全伴生殖器异常</td><td>腓骨肌萎缩症4E型</td><td>远端脊髓肌萎缩1型</td>
			</tr>
			<tr>
				<td>脊髓性肌萎缩症Ⅲ（少年期）</td><td>进行性肌阵挛性癫痫</td><td>X连锁智力低下，综合征10型</td><td>先天性肌肉萎缩症1d型</td>
			</tr>
			<tr>
				<td>Joubert氏综合症2型</td><td>胼胝体发育不全及周围神经病变</td><td>腓骨肌萎缩症4H型</td><td>早发型共济失调伴动眼不能和低蛋白血症</td>
			</tr>
			<tr>
				<td>线粒体DNA耗竭综合征3</td><td>进行性肌阵挛性癫痫3型</td><td>X连锁智力低下，综合征13型</td><td>先天性瞳孔异位</td>
			</tr>
			<tr>
				<td>Joubert氏综合症4型</td><td>桥新小脑发育不全</td><td>腓骨肌萎缩症X连锁隐性5型；</td><td>早期婴儿型癫痫性脑病</td>
			</tr>
			<tr>
				<td>鱼鳞癣样红皮症-痉挛性两侧瘫痪-智力发育不全综合征</td><td>进行性眼外肌麻痹伴线粒体DNA缺失</td><td>X连锁智力低下，综合征Christianson型</td><td>先天性痛觉不敏感合并无汗症</td>
			</tr>
			<tr>
				<td>Joubert氏综合症5型</td><td>桥新小脑发育不全2A型</td><td>腓骨肌萎缩症和耳聋</td><td>张力失常-帕金森病综合征成人早发型</td>
			</tr>
			<tr>
				<td>先天性无虹膜</td><td>X连锁无脑回畸形</td><td>进行性眼外肌麻痹并线粒体DNA缺失</td><td>X连锁智力低下17型</td>
			</tr>
			<tr>
				<td>枕骨角综合症</td><td>Joubert氏综合症6型</td><td>青少年原发性侧索硬化</td><td>福山型先天性肌营养不良</td>
			</tr>
			<tr>
				<td>涎尿</td><td>沙勒沃伊-萨格奈常染色体隐性遗传痉挛性共济失调</td><td>进行性眼外肌麻痹并线粒体核酸缺失3型</td><td>X连锁智力低下伴或不伴癫痫发作，ARX相关</td>
			</tr>
			<tr>
				<td>Kenny-Caffey综合症</td><td>山德霍夫氏病</td><td>辅酶Q10缺乏症</td><td>肢带型进行性肌肉萎缩症2d型</td>
			</tr>
			<tr>
				<td>幼婴癫痫性脑病3型</td><td>巨颅伴皮层下海绵样囊肿性脑白质病</td><td>X连锁自闭症易感3型</td><td>小儿异染性脑白质营养不良</td>
			</tr>
			<tr>
				<td>Leigh综合征</td><td>神经元蜡样脂褐质沉积症10型</td><td>杆状体肌病5</td><td>肢带型进行性肌肉萎缩症2i型</td>
			</tr>
			<tr>
				<td>科恩综合症</td><td>17β-羟类固醇脱氢酶缺乏症</td><td>埃勒雅尔德综合征</td><td>小脑性共济失调，智力低下和平衡失调综合征1型</td>
			</tr>
			<tr>
				<td>拉福拉病</td><td>神经元蜡样脂褐质沉积症3型</td><td>感觉性共济失调性周围神经病伴构音障碍及眼肌麻痹（SANDO)</td><td>肢带型进行性肌肉萎缩症2k型</td>
			</tr>
			<tr>
				<td>艾迪瞳孔</td><td>Aicardi-Goutieres综合症</td><td>扩张型心肌病1X型</td><td>新生儿重症脑病</td>
			</tr>
			<tr>
				<td>Masa综合征</td><td>神经元蜡样脂褐质沉积症6型</td><td>感觉性共济失调性周围神经病伴构音障碍及眼肌麻痹（SANDO)</td><td>肢带型进行性肌肉萎缩症2m型</td>
			</tr>
			<tr>
				<td>扩张型心肌病3B型</td><td>Amish婴儿癫痫综合症</td><td>白内障、智力低下和性腺功能减退综合征</td><td>遗传性共济失调-白内障-侏儒-智力缺陷综合征</td>
			</tr>
			<tr>
				<td>Meckel综合征3型</td><td>神经元蜡样脂褐质沉积症7型</td><td>格里塞利综合征1型</td><td>智力低下、共济失调和癫痫综合征</td>
			</tr>
			<tr>
				<td>Arts综合征</td><td>Lesch-Nyhan综合症（自毁容貌症）</td><td>丙酮酸脱羧酶缺乏症</td><td>遗传性共济失调伴肌萎缩综合征</td>
			</tr>
			<tr>
				<td>Meckel综合征4型</td><td>神经元蜡样脂褐质沉积症1型</td><td>共济失调伴选择性维生素E缺乏症</td><td>智力低下及肌萎缩综合症</td>
			</tr>
			<tr>
				<td>雷特氏病</td><td>Becker型肌营养不良症</td><td>常染色体隐性包涵体肌病</td><td>遗传性共济失调伴肌萎缩综合征</td>
			</tr>
			<tr>
				<td>Morquio综合征B型</td><td>神经元蜡样脂褐质沉积症2型</td><td>虹膜，脉络膜及视网膜缺损</td><td>中央凹发育不良伴早老性白内障综合征</td>
			</tr>
			<tr>
				<td>CEDNIK综合征</td><td>联合鞘脂激活蛋白缺陷</td><td>常染色体隐性脊髓小脑型共济失调9型</td><td>遗传性角膜炎</td>
			</tr>
			<tr>
				<td>Nonaka肌病</td><td>神经元蜡样质脂褐质沉积症5型</td><td>肌管性肌病</td><td>轴突型腓骨肌萎缩症2I型</td>
			</tr>
			<tr>
				<td>两侧视神经发育不全</td><td>CM1神经节苷脂贮积症1型</td><td>常染色体隐性皮肤弹力过度症7型</td><td>遗传性痉挛性截瘫44型</td>
			</tr>
			<tr>
				<td>Pelizaeus-Merzbacher病</td><td>神经元蜡样质脂褐质沉积症5型</td><td>肌萎缩性侧索硬化，幼年2型</td><td>轴突型腓骨肌萎缩症2J型</td>
			</tr>
			<tr>
				<td>冻疮样狼疮</td><td>CM1神经节苷脂贮积症2型</td><td>磷酸核糖焦磷酸合成酶超活性痛风</td><td>遗传性压迫易感性神经病</td>
			</tr>
			<tr>
				<td>肌-眼-脑病</td><td>Peters异常（角膜基质中角膜细胞分化及细胞外基质异常）</td><td>神经元蜡样脂褐质沉积症8型，北部癫痫变异型</td><td>状旁腺功能减退-生长迟缓-畸形综合征</td>
			</tr>
			<tr>
				<td>肌-眼-脑病</td><td>CM1神经节苷脂贮积症3型</td><td>弥漫性进行性脑灰质变性综合症</td><td>杜肌氏营养不良；进行性假肥大性肌营养不良症</td>
			</tr>
			<tr>
				<td>肾结核1型</td><td>SaposinC缺乏性戈谢病</td><td>德雅兰-索塔斯病</td><td>遗传性自主神经病3型（Riley-Day综合征）</td>
			</tr>
			<tr>
				<td>Cockayne综合征A型</td><td>缅克斯症候群</td><td>对紫外线敏感综合征</td><td>婴儿型脊髓小脑型共济失调</td>
			</tr>
			<tr>
				<td>吉莱斯皮综合征</td><td>Walker-Warburg综合征</td><td>肾上腺脑白质营养不良</td><td>夏科-马里-图思病4F型</td>
			</tr>
			<tr>
				<td>Cockayne综合征B型</td><td>钼辅酶缺乏症</td><td>非典型克拉伯病</td><td>婴儿型家族性黑曚性痴呆</td>
			</tr>
			<tr>
				<td>视神经缺损</td><td>X连锁部分胼胝体发育不全</td><td>脊肌萎缩症1型</td><td>&nbsp;</td>
			</tr>
		</tbody>
	</table>
	<table class="table table-green table-striped-green table-text-center table-padding0">
		<thead style="display:table-header-group;">
		  <tr>
			<th colspan="4" style="text-align: left">胃肠病类</th>
		  </tr>
		</thead>
		<tbody>
			<tr>
				<td>慢性假性肠梗阻</td><td>常染色体隐性结直肠腺瘤性息肉</td><td>脑室周围异位</td><td>先天吸收不良性腹泻</td>
			</tr>
			<tr>
				<td>Melnick-Needles综合征</td><td>脑室周围结节状异位</td><td>纤维蛋白原综合症</td><td>&nbsp;</td>
			</tr>
		</tbody>
	</table>
	<table class="table table-green table-striped-green table-text-center table-padding0">
		<thead style="display:table-header-group;">
		  <tr>
			<th colspan="4" style="text-align: left">心血管类</th>
		  </tr>
		</thead>
		<tbody>
			<tr>
				<td>血色病2b型</td><td>扩张型心肌炎</td><td>蛋白C缺乏引起的遗传性常染色体隐性性易栓症</td><td>血色素沉着症2A型</td>
			</tr>
			<tr>
				<td>β-地中海贫血，显性包涵体型</td><td>先天性肌病，早发，伴心肌病</td><td>谷胱甘肽合成酶缺乏症</td><td>遗传性血栓性血小板减少性紫癜</td>
			</tr>
			<tr>
				<td>镰状细胞性贫血</td><td>Becker型肌营养不良症</td><td>低血磷性佝偻病2型</td><td>眼齿指发育不良</td>
			</tr>
			<tr>
				<td>巴特综合征</td><td>先天性无/低纤维蛋白原血症</td><td>活化蛋白C抵抗引起的易栓症</td><td>婴儿期广泛性动脉钙化</td>
			</tr>
			<tr>
				<td>地中海贫血</td><td>凝血因子V莱顿易栓症</td><td>耶维尔-朗厄.尼尔逊综合症1型</td><td>伴α地中海贫血X连锁智力低下综合征（ATR-X综合征）</td>
			</tr>
			<tr>
				<td>Carney综合征</td><td>纤维蛋白原缺乏血症</td><td>脊椎后纵韧带骨化</td><td>原发性色素性结节状肾上腺皮质病1型</td>
			</tr>
			<tr>
				<td>地中海贫血</td><td>Hallemann-Streiff综合征</td><td>舒-戴二氏综合症</td><td>遗传性红细胞丙酮酸激酶缺陷症</td>
			</tr>
			<tr>
				<td>家族性房颤3型</td><td>丙酮酸激酶活性增高症（红细胞ATP增多症）</td><td>纤维蛋白原缺乏血症</td><td>原发性血小板增多症</td>
			</tr>
			<tr>
				<td>QT间期延长综合征</td><td>先天性Heinz小体贫血（变性珠蛋白小体贫血）</td><td>杜肌氏营养不良；进行性假肥大性肌营养不良症</td><td>遗传性肌病并早期呼吸衰竭</td>
			</tr>
			<tr>
				<td>并指畸形Ⅲ型</td><td>家族性肾脏淀粉样变性</td><td>肢带型进行性肌肉萎缩症2j型</td><td>α-地中海贫血伴骨髓增生异常综合征（获得性血红蛋白H病）</td>
			</tr>
			<tr>
				<td>Smith-Fineman-Myers综合征</td><td>先天性Heinz小体贫血（变性珠蛋白小体贫血）</td><td>短QT综合征2型</td><td>遗传性凝血因子V缺乏症</td>
			</tr>
			<tr>
				<td>迟发性胫骨肌营养不良症</td><td>血浆酶原缺乏症1型</td><td>扩张型心肌病1G型</td><td>左心发育不良综合征</td>
			</tr>
			<tr>
				<td>心脏粘液瘤</td><td>先天性纯巨核细胞再障血小板减少症</td><td>谷胱甘肽合成酶缺乏症</td><td>遗传性凝血因子XI缺乏症</td>
			</tr>
			<tr>
				<td>血色病3型</td><td>蛋白C缺乏引起的遗传性常染色体显性易栓症</td><td>扩张型心肌病3B型</td><td>左心室肌致密化不全，X连锁</td>
			</tr>
		</tbody>
	</table>
	<table class="table table-green table-striped-green table-text-center table-padding0">
		<thead style="display:table-header-group;">
		  <tr>
			<th colspan="4" style="text-align: left">眼科类</th>
		  </tr>
		</thead>
		<tbody>
			<tr>
				<td>无脉络膜症</td><td>Fuchs角膜内皮营养不良4型</td><td>家族性热性惊厥4</td><td>尤赛氏综合症Ⅱc型</td>
			</tr>
			<tr>
				<td>全色盲3型</td><td>常染色体隐性非综合征性耳聋</td><td>尤赛氏综合症Ⅰc型</td><td>常染色体显性遗传耳聋11型</td>
			</tr>
			<tr>
				<td>先天性视网膜劈裂症</td><td>角膜内皮营养不良2型</td><td>尤赛氏综合症Ⅲ型</td><td>常染色体隐性遗传耳聋18型</td>
			</tr>
			<tr>
				<td>斯特格病变1型</td><td>尤赛氏综合症Ⅰd型</td><td>常染色体隐性非综合征性耳聋</td><td>尤赛氏综合征1F型</td>
			</tr>
			<tr>
				<td>耳聋23型</td><td>角膜营养不良和感知性耳聋</td><td>尤赛氏综合症Ⅱ型</td><td>尤赛氏综合症Ⅰg型</td>
			</tr>
			<tr>
				<td>尤赛氏综合症Ⅰ型</td><td>杜安眼球后退综合征1型</td><td>杜安眼球后退综合征2型</td><td>&nbsp;</td>
			</tr>
		</tbody>
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
  		var sex = $("input[type='radio']:checked").val();
  		$("#_sex").html(sex);
  		$("#change").hide();
  		if($("#noMutation").attr("checked")){
  			$("#noDrug").css("display","");
  		}
  		$("a").css("display","none");
  		saveFillCmp();
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
  		$("#_sex").html("<input type='radio' name='sex' value='男'>男<input type='radio' name='sex' value='女'>女");
  		$("input[type='radio'][value="+sex+"]").attr("checked",true); 
  		$("a").css("display","");
  	}
  	function saveFillCmp(){
  		var resistanceSiteSum = "";
  		var personalizedMedicine = "";
  		var recommendDrug = "";
  		var age = parseInt($("#patientAge").val());
  		$("#patientAge").val(age);
  		$.get("cmpReport!updateFill",$("#form").serialize());
  	}
  </script>
</body>
</html>
