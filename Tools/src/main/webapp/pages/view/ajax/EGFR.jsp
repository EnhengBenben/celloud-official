<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<input type="hidden" value="<s:property value="resultMap.length"/>" id="seq_length"/>
	<input type="hidden" value="<s:property value="resultMap.projectId"/>" id="_projectId">
	<!--文件名称-->
	<div class="m-file">
	   <dl class="dl-horizontal datareport-title">
          <dt>项目名称：</dt>
          <dd>${resultMap['projectName'] }</dd>
          <dt>应用名称：</dt>
          <dd>${resultMap['appName'] }</dd>
          <dt>文件名称：</dt>
          <dd class="force-break">
            <s:property value="resultMap.fileName"/>(<s:property value="%{resultMap.pagePath.replace('/SVG','').substring(resultMap.pagePath.replace('/SVG','').lastIndexOf('/')+1,resultMap.pagePath.replace('/SVG','').length())}"/>)
          </dd>
        </dl>
        <div class="toolbar">
            <a class="btn btn-celloud-success btn-flat" href='javascript:toPrintHBV("<s:property value="resultMap.pagePath"/>")'><i class="fa fa-print"></i>打印报告</a>
            <s:if test="%{!resultMap.pdf.equals('false')}">
                <a class="btn btn-warning btn-flat" href="<s:property value="resultMap.pdf"/>"><i class="fa fa-file-pdf-o"></i>PDF下载</a>
            </s:if>
        </div>
	</div>
	<!--位点突变-->
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>一、 已知突变位点（依据已发表文献，该突变位点有明确临床意义）
			<span style="float:right;padding-right: 30px;" title="帮助" onclick="showModal('helpModal')"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><span class="fa fa-thumbs-up"></span></div></span>
		</h2>
	    <div class="m-boxCon result">
	    	<div id="knowResult">
		    	<s:if test="%{resultMap.wz1!=null && resultMap.wz1!=''}">
		    		<s:property value="resultMap.wz1" escape="false"/>
	    		</s:if>
	    		<s:else>
	    			未检测到突变
	    		</s:else>
	    	</div>
	    	<s:if test="%{resultMap.know!=''}">
	    		<br/>
		    	<img name="know" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.know"/>" style="width: 100%;">
	    	</s:if>
	    	<s:else>
	    		<s:if test="%{resultMap.pagePath.substring(resultMap.pagePath.indexOf('/')+1,resultMap.pagePath.indexOf('/')+3)==89}">
	    		</s:if>
		    	<s:else>
			    	<s:if test="%{resultMap.wz1!='新的突变型或者测序质量差<br />'}">
			    		<br/>
			    		数据正常，未找到已知突变。
			    	</s:if>
		    	</s:else>
	    	</s:else>
	    </div>
	</div>
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>二、 未知突变位点（该突变位点目前没有已发表文献支持，无明确临床意义）
			<div style="float:right;padding-right: 30px;" title="帮助" onclick="showModal('helpModal')"><div class="clear button button-glow button-circle button-rounded button-primary button-tiny text-center"><span class="fa fa-thumbs-up"></span></div></div>
		</h2>
	    <div class="m-boxCon result">
	    	<s:if test="%{!resultMap.wz2.equals('')}">
		    	<s:property value="resultMap.wz2" escape="false"/>
		    	<br/>
		    	<s:generator separator="," val="resultMap.unknow">
		    		<s:iterator var="var" status="st">
			    		<a href="javascript:showBgTwo('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="var"/>');" style="padding-right: 20px">
			    			<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="var"/>" style="width: 156px;height: 177px;">
			    		</a>
					</s:iterator>	    	
		    	</s:generator>
	    	</s:if>
	    	<s:else>
	    		数据正常，未找到其他突变。
	    	</s:else>
	    </div>
	</div>
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>三、 参考结论
		</h2>
	    <div class="m-boxCon result" id="_result">
	    	<s:property value="resultMap.report" escape="false"/>
	    </div>
	</div>
	<!--检测结果-->
	<div class="m-box">
		<h2><i class="i-edit"></i>四、 测序序列结果</h2>
	    <div class="m-boxCon result" id="_seq">
			<s:property value="resultMap.seq" escape="false"/>
	    </div>
	</div>
	<!--染色体序列点图-->
	<div class="m-box">
		<h2><i class="i-dna"></i>五、 测序峰图结果</h2>
	    <c:if test="${resultMap.listAll1!='' }">
		    <div class="m-boxCon result">
					<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>','listAll1Img');" >
						<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>" style="width: 85%;" id="listAll1Img">
					</a>
		    </div>
		</c:if>
	    <c:if test="${resultMap.listAll2!='' }">
		    <div class="m-boxCon result">
					<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>','listAll2Img');" >
						<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>" style="width: 85%;" id="listAll2Img">
					</a>
		    </div>
		</c:if>
		<c:if test="${resultMap.listAll3!='' }">
			<div class="m-boxCon result">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>','listAll3Img');" >
					<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>" style="width: 85%;" id="listAll3Img">
				</a>
			</div>
		</c:if>
	     <c:if test="${resultMap.listAll4!='' }">
		     <div class="m-boxCon result">
					<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>','listAll4Img');" >
						<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>" style="width: 85%;" id="listAll4Img">
					</a>
		    </div>
	     </c:if>
     	<c:if test="${resultMap.listAll5!='' }">
		     <div class="m-boxCon result">
					<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll5"/>','listAll5Img');" >
						<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll5"/>" style="width: 85%;" id="listAll5Img">
					</a>
		    </div>
     	</c:if>
     	<c:if test="${resultMap.listAll1==''&&resultMap.listAll2==''&&resultMap.listAll3==''&&resultMap.listAll4==''&&resultMap.listAll5=='' }">
		    <div class="m-boxCon result">
		    样本异常，无法检测
		    </div>
		</c:if>
	</div>
	<div class="bg-analysis">
		<div class="m-box">
			<h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
			<div class="m-boxCon">
				<div class="row" id="charDiv">
			    </div>
			</div>
		</div>
	</div>
</div>

<div class="modal modal-green-header in" id="helpModal">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header">
		<a class="close" data-dismiss="modal">×</a>
		<h4>注释</h4>
	</div>
	<div class="modal-body" style="padding-left: 50px;padding-right: 50px;">
<div class="_help">1. 如果为Homozygous纯合突变，发生突变的位置在看峰图序列时以最高峰代表的碱基为突变碱基；如果为Heterozygous杂合突变，发生突变的位置在看峰图序列时以第二高峰代表的碱基为突变碱基；</div>
<div class="_help">2. V769-D770insASV 为突变型名称。</div>
<div class="_help">3. 比例值说明：A-A|G(67|33,2.1)，2.1为67和33的比值，该比例并不代表样本中该位点A和G的真实数量比例，只是代表该位点是A的可能性为67%，是G的可能性为33%。如果没有“(67|33,2.1）”出现，认为该位点100%发生突变。如果是A-A|G，说明该位点为A的可能性大；如果为A-G|A，说明该位点为G的可能性大。当野生型的碱基（即A）的可能性大于突变碱基（即G）的可能性时，如果比值小于5我们认为该位点是突变；如果突变碱基（即G）的可能性大于野生型的碱基（即A）的可能性时，不论比值多少都认为发生了突变。</div>
<div class="_help">4. Homozygous 为纯合突变，Heterozygous 为杂合突变。</div>
<div class="_help">5. 图中的“*”表示在该位置发生突变。</div>
<div class="_help">6. “Recombination: ttaagagaagcaa- &gt;c”:表示ttaagagaagcaa变为c；<br/>
“Deletion: gaattaagagaagcaaca ：”表示gaattaagagaagcaaca缺失；<br/>
“Insertion: gccagcgtg” ：表示gccagcgtg插入。</div>
<div class="_help">7. “*Wild type: ACAACCCCCACGTGTGCCGCCTGCTGGGC ”：表示野生型序列为ACAACCCCCACGTGTGCCGCCTGCTGGGC</div>
<div class="_help">8. “M204M|V {A-A|G(67|33,2.1);G-G|T(72|28,2.5)}”中：<br/>
（1）“M”表示野生型编码氨基酸为M；“204”表示氨基酸位点为204；“M|V”表示由原来的野生型M变为V。<br/>
（2）“A-A|G(67|33,2.1) ;G-G|T(72|28,2.5)”表示碱基的变化，一个碱基位点由原来的A变为A|G杂合，比例为67比33，比值为2.1；另一个碱基位点由原来的G变为G|T杂合，比例为72比28，比值为2.5。</div>
	</div>
	<div class="modal-footer">
		<a class="btn btn-celloud-success btn-flat" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
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
function showBgTwo(src){
	$("img[id='imageFullScreen']").css("width",1050);
	$("img[id='imageFullScreen']").css("height",157.5);
	showZoom(src.replace("png","10.png"));
}
function showBg(src,id) { 
	var width = $("#" + id).width();
	var height = $("#" + id).height();
	$("img[id='imageFullScreen']").css("width",width*1.5);
	$("img[id='imageFullScreen']").css("height",height*1.5);
	showZoom(src);
}
function showModal(id){
	$("#"+id).modal("show");
	$("#"+id).find(".modal-body").scrollTop(0);
}
</script>