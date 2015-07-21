<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row">
	<!--文件名称-->
	<div class="m-file">
		文件名称：
		<span class="file-name">
			<s:property value="%{resultMap.pagePath.replace('/SVG','').substring(resultMap.pagePath.replace('/SVG','').lastIndexOf('/')+1,resultMap.pagePath.replace('/SVG','').length())}"/>(<s:property value="resultMap.fileName"/>)
		</span>
		<div class="toolbar">
			<a href="<s:property value="resultMap.down"/><s:property value="resultMap.pdf"/>" class="btn btn-default"><i class="i-pdf"></i>PDF下载</a>
			<a href='javascript:toPrintHBV("<s:property value="resultMap.pagePath"/>")' class="btn btn-default"><i class="i-print"></i>打印报告</a>
			<a href="<s:property value="resultMap.down"/><s:property value="resultMap.zip"/>" class="btn btn-default"><i class="i-download"></i>报告下载</a>
		</div>
	</div>
	<div class="m-file" id="cfda">
		<div class="m-box w500">
			<h2>位点突变</h2>
			<h5 id="snpType"><s:property value="resultMap.snpType.replace('Type','类型')" escape="false"/></h5>
			<div class="m-boxCon">
				<s:if test="%{resultMap.snpType.equals('no match<br />')}">
					可能不是HBV样本或者是样本质量太低
				</s:if>
				<s:else>
					<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="420px" width="420px"/>
				</s:else>
			</div>
			<div class="m-tips">
				<i class="i-tips"></i>
					<s:if test="%{'true'.equals(resultMap.LDT)||'true'.equals(resultMap.LAM)||'true'.equals(resultMap.FTC)||'true'.equals(resultMap.ETV)}">
						检测到
						<s:if test="%{'true'.equals(resultMap.isLDT)}">
							替比夫定 LDT
						</s:if>
						<s:if test="%{'true'.equals(resultMap.isLAM)}">
							拉米夫定 LAM
						</s:if>
						<s:if test="%{'true'.equals(resultMap.isFTC)}">
							恩曲他滨 FTC
						</s:if>
						<s:if test="%{'true'.equals(resultMap.isETV)}">
							恩替卡韦 ETV
						</s:if>
						突变
					</s:if>
					<s:else>
						未检测到突变
					<div style="display: none;">
						<s:if test="%{'true'.equals(resultMap.isLDT)}">
						   敏感<input type="hidden" id="hasLDT" value="替比夫定 LDT">
						</s:if>
						<s:if test="%{'true'.equals(resultMap.isLAM)}">
						   敏感<input type="hidden" id="hasLAM" value="拉米夫定 LAM">
						</s:if>
						<s:if test="%{'true'.equals(resultMap.isFTC)}">
						   敏感<input type="hidden" id="hasFTC" value="恩曲他滨 FTC">
						</s:if>
						<s:if test="%{'true'.equals(resultMap.isETV)}">
						   敏感<input type="hidden" id="hasETV" value="恩替卡韦 ETV">
						</s:if>
						<s:if test="%{'true'.equals(resultMap.isADV)}">
						   敏感<input type="hidden" id="hasADV" value="阿德福韦ADV">
						</s:if>
						<s:if test="%{'true'.equals(resultMap.isTDF)}">
						   敏感<input type="hidden" id="hasTDF" value="替诺福韦酯TDF">
						</s:if>
					</div>
					</s:else>
			</div>
		</div>
		<div class="center mb10 w500">
			<a href="javascript:void(0)" onclick="change1()" class="btn btn-blue"><i class="i-view"></i>查看详情</a>
		</div>
	</div>
	<div id="nomal" style="display: none;">
		<s:if test="%{resultMap.snpType.equals('no match<br />')}">
			<div class="m-box">
			 	<h2>数据异常</h2>
			    <div class="m-boxCon">
					没有比对结果
			    </div>
			</div>
		</s:if>
		<s:else>
			<!--位点突变-->
			<div class="m-box">
			 	<h2>1.替诺福韦酯TDF突变检测</h2>
			    <div class="m-boxCon">
				    <s:if test="%{!''.equals(resultMap.svg194)}">
						<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg194"/>" height="150px;" width="150px;">
					</s:if>
			    </div>
			</div>
			<!--位点突变-->
			<div class="m-box">
			 	<h2>2.替比夫定 LDT 突变检测</h2>
			    <div class="m-boxCon">
				    <s:if test="%{!''.equals(resultMap.svg204)}">
						<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="150px;" width="150px;">
					</s:if>
			    </div>
			</div>
			<!--位点突变-->
			<div class="m-box">
			 	<h2>3.阿德福韦 ADV 突变检测</h2>
			    <div class="m-boxCon">
				    <s:if test="%{!''.equals(resultMap.svg181)}">
						<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg181"/>" height="150px;" width="150px;">
					</s:if>
					<s:if test="%{!''.equals(resultMap.svg236)}">
							<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg236"/>" height="150px;" width="150px;">
					</s:if>
			    </div>
			</div>
			<!--位点突变-->
			<div class="m-box">
			 	<h2>4.拉米夫定 LAM 突变检测</h2>
			    <div class="m-boxCon">
				    <s:if test="%{!''.equals(resultMap.svg173)}">
						<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg173"/>" height="150px;" width="150px;">
					</s:if>
					<s:if test="%{!''.equals(resultMap.svg180)}">
							<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg180"/>" height="150px;" width="150px;">
					</s:if>
					<s:if test="%{!''.equals(resultMap.svg204)}">
							<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="150px;" width="150px;">
					</s:if>
			    </div>
			</div>
			<!--位点突变-->
			<div class="m-box">
			 	<h2>5.恩曲他滨 FTC 突变检测</h2>
			    <div class="m-boxCon">
				    <s:if test="%{!''.equals(resultMap.svg173)}">
						<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg173"/>" height="150px;" width="150px;">
					</s:if>
					<s:if test="%{!''.equals(resultMap.svg180)}">
							<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg180"/>" height="150px;" width="150px;">
					</s:if>
					<s:if test="%{!''.equals(resultMap.svg204)}">
							<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="150px;" width="150px;">
					</s:if>
			    </div>
			</div>
			<!--位点突变-->
			<div class="m-box">
			 	<h2>6.恩替卡韦 ETV 突变检测</h2>
			    <div class="m-boxCon">
				    <s:if test="%{!''.equals(resultMap.svg169)}">
						<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg169"/>" height="150px;" width="150px;">
					</s:if>
					<s:if test="%{!''.equals(resultMap.svg180)}">
							<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg180"/>" height="150px;" width="150px;">
					</s:if>
					<s:if test="%{!''.equals(resultMap.svg184)}">
							<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg184"/>" height="150px;" width="150px;">
					</s:if>
					<s:if test="%{!''.equals(resultMap.svg202)}">
							<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg202"/>" height="150px;" width="150px;">
					</s:if>
					<s:if test="%{!''.equals(resultMap.svg204)}">
							<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="150px;" width="150px;">
					</s:if>
					<s:if test="%{!''.equals(resultMap.svg250)}">
							<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg250"/>" height="150px;" width="150px;">
					</s:if>
			    </div>
			</div>
			
			<!--位点突变-->
			<div class="m-box">
			 	  <h2>7.其他突变位点（该位点目前没有已发表文献支持，无明确临床意义）</h2>
			    <div class="m-boxCon">
					<s:generator separator="," val="resultMap.other">
						<s:iterator status="st">
							<img title="<s:property/>" name="imgSrc" style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property/>" height="150px;" width="150px;">
						</s:iterator>
					</s:generator>
			    </div>
			</div>
		</s:else>
		<!--结论-->
		<div class="m-box">
		 	  <h2>参考结论（根据已发表文献得出以下参考结论）</h2>
		    <div class="m-boxCon" id="resultDiv">
		    	<s:property value="resultMap.result" escape="false"/>
		    </div>
		</div>
		<!--检测结果-->
		<div class="m-box">
			<h2><i class="i-edit"></i>原始序列</h2>
		    <div class="m-boxCon result">
				<s:property value="resultMap.seq" escape="false"/>
		    </div>
		</div>
		<!--染色体序列点图-->
		<div class="m-box">
			<h2><i class="i-dna"></i>原始峰图</h2>
		    <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>','listAll1Img');" >
					<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>" style="width: 750px;height: 150px;" id="listAll1Img">
				</a>
		    </div>
		     <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>','listAll2Img');" >
					<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>" style="width: 750px;height: 150px;" id="listAll2Img">
				</a>
		    </div>
		     <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>','listAll3Img');" >
					<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>" style="width: 750px;height: 150px;" id="listAll3Img">
				</a>
		    </div>
		     <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>','listAll4Img');" >
					<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>" style="width: 750px;height: 150px;" id="listAll4Img">
				</a>
		    </div>
		</div>
		<!--Celloud数据参数同比分析-->
		<div class="bg-analysis">
		    <div class="m-box">
		        <h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
		        <div class="m-boxCon" id="charDiv">
		        </div>
		    </div>
		</div>
		<div class="center mt10 mb10">
			<a href="javascript:void(0)" onclick="change2()" class="btn btn-blue"><i class="i-view"></i>返回</a>
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
function showBg(src,id) { 
	var width = $("#" + id).width();
	var height = $("#" + id).height();
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",width*1.5);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",height*1.5);
	window.parent.showZoom(src);
}
function change1(){
	$("#reportBody").scrollTop(0);
	$("#nomal").css("display","");
	$("#cfda").css("display","none");
}
function change2(){
	$("#reportBody").scrollTop(0);
	$("#nomal").css("display","none");
	$("#cfda").css("display","");
}
</script>