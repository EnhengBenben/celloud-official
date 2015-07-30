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
			<a href='javascript:toPrintHBV("<s:property value="resultMap.pagePath"/>",0)' class="btn btn-default"><i class="i-print"></i>详细报告</a>
			<a href='javascript:toPrintHBV("<s:property value="resultMap.pagePath"/>",1)' class="btn btn-default"><i class="i-print"></i>简要报告</a>
			<a href="<s:property value="resultMap.down"/><s:property value="resultMap.zip"/>" class="btn btn-default"><i class="i-download"></i>报告下载</a>
		</div>
	</div>
	<div class="m-file" id="cfda">
		<div class="m-box w500">
			<h2>位点突变</h2>
			<h5 id="snpType"><s:property value="resultMap.snpType.replace('Type','基因型')" escape="false"/></h5>
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
			<div id="printDiv1">
				<div class="m-box">
				 	<h2>
				 		1.替诺福韦酯TDF突变检测
				 		<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal')"><i class="i-tips"></i></div>
				 	</h2>
				    <div class="m-boxCon">
					    <s:if test="%{!''.equals(resultMap.svg194)}">
					    	<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10194"/>');" >
								<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg194"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到194位点。
							</div>
						</s:else>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>2.替比夫定 LDT 突变检测
				 		<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal')"><i class="i-tips"></i></div>
				 	</h2>
				    <div class="m-boxCon">
					    <s:if test="%{!''.equals(resultMap.svg204)}">
					    	<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10204"/>');" >
								<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到204位点。
							</div>
						</s:else>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>3.阿德福韦 ADV 突变检测
				 		<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal')"><i class="i-tips"></i></div>
				 	</h2>
				    <div class="m-boxCon">
					    <s:if test="%{!''.equals(resultMap.svg181)}">
					    	<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10181"/>');" >
								<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg181"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到181位点。
							</div>
						</s:else>
						<s:if test="%{!''.equals(resultMap.svg236)}">
							<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10236"/>');" >
								<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg236"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到236位点。
							</div>
						</s:else>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>4.拉米夫定 LAM 突变检测
				 		<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal')"><i class="i-tips"></i></div>
				 	</h2>
				    <div class="m-boxCon">
					    <s:if test="%{!''.equals(resultMap.svg173)}">
					    	<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10173"/>');" >
								<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg173"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到173位点。
							</div>
						</s:else>
						<s:if test="%{!''.equals(resultMap.svg180)}">
							<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10180"/>');" >
								<img style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg180"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到180位点。
							</div>
						</s:else>
						<s:if test="%{!''.equals(resultMap.svg204)}">
							<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10204"/>');" >
								<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到204位点。
							</div>
						</s:else>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>5.恩曲他滨 FTC 突变检测
				 		<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal')"><i class="i-tips"></i></div>
				 	</h2>
				    <div class="m-boxCon">
					    <s:if test="%{!''.equals(resultMap.svg173)}">
					    	<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10173"/>');" >
								<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg173"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到173位点。
							</div>
						</s:else>
						<s:if test="%{!''.equals(resultMap.svg180)}">
							<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10180"/>');" >
								<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg180"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到180位点。
							</div>
						</s:else>
						<s:if test="%{!''.equals(resultMap.svg204)}">
							<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10204"/>');" >
								<img style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到204位点。
							</div>
						</s:else>
				    </div>
				</div>
				<!--位点突变-->
				<div class="m-box">
				 	<h2>6.恩替卡韦 ETV 突变检测
				 		<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal')"><i class="i-tips"></i></div>
				 	</h2>
				    <div class="m-boxCon">
					    <s:if test="%{!''.equals(resultMap.svg169)}">
						    <a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10169"/>');" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg169"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到169位点。
							</div>
						</s:else>
						<s:if test="%{!''.equals(resultMap.svg180)}">
							<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10180"/>');" >
								<img class="imgtop" style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg180"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到180位点。
							</div>
						</s:else>
						<s:if test="%{!''.equals(resultMap.svg184)}">
							<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10184"/>');" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg184"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到184位点。
							</div>
						</s:else>
						<s:if test="%{!''.equals(resultMap.svg202)}">
							<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10202"/>');" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg202"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到202位点。
							</div>
							</div>
						</s:else>
						<s:if test="%{!''.equals(resultMap.svg204)}">
							<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10204"/>');" >
								<img class="imgtop" style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg204"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到204位点。
							</div>
						</s:else>
						<s:if test="%{!''.equals(resultMap.svg250)}">
							<a href="javascript:showBgOne('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg10250"/>');" >
								<img class="imgtop" style="padding-left: 30px;" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.svg250"/>" height="170px;" width="150px;">
							</a>
						</s:if>
						<s:else>
							<div class="imgmiss">
								可能因为测序质量差 , <br/>样本中未找到250位点。
							</div>
						</s:else>
				    </div>
				</div>
				<div class="w3cbbs" style="display: none;"></div>
				<div class="container" style="display: none;"></div>
				<!--位点突变-->
				<div class="m-box">
				 	  <h2>7.其他突变位点（该位点目前没有已发表文献支持，无明确临床意义）
				 	  	<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal')"><i class="i-tips"></i></div>
				 	  </h2>
				    <div class="m-boxCon">
						<s:generator separator="," val="resultMap.other">
							<s:iterator status="st">
								<a href="javascript:showBgTwo('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property/>');" >
									<img class="imgtop" title="<s:property/>" name="imgSrc" style="padding-left: 30px;" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property/>" height="170px;" width="150px;">
								</a>
							</s:iterator>
						</s:generator>
				    </div>
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
		    <div class="m-boxCon result" id="printDiv2">
				<s:property value="resultMap.seq" escape="false"/>
		    </div>
		</div>
	    <div id="printDiv4" style="display: none;">
			<s:property value="resultMap.clinical" escape="false"/>
	    </div>
		<!--染色体序列点图-->
		<div class="m-box" id="printDiv3">
			<h2><i class="i-dna"></i>原始峰图
				<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('helpModal',1)"><i class="i-tips"></i></div>
			</h2>
		    <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>','listAll1Img');" >
					<img class="imgtop" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>" style="width: 750px;height: 150px;" id="listAll1Img">
				</a>
		    </div>
		     <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>','listAll2Img');" >
					<img class="imgtop" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>" style="width: 750px;height: 150px;" id="listAll2Img">
				</a>
		    </div>
		     <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>','listAll3Img');" >
					<img class="imgtop" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>" style="width: 750px;height: 150px;" id="listAll3Img">
				</a>
		    </div>
		     <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>','listAll4Img');" >
					<img class="imgtop" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>" style="width: 750px;height: 150px;" id="listAll4Img">
				</a>
		    </div>
		    <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll5"/>','listAll5Img');" >
					<img class="imgtop" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll5"/>" style="width: 750px;height: 150px;" id="listAll5Img">
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

<div class="modal fade" id="helpModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header well">
		<a class="close" data-dismiss="modal">×</a>
		<h4>注释</h4>
	</div>
	<div class="modal-body well">
		<div id="_showMore">
			<div class="lineheight">
			1. 突变结果举例解释：“M204M|V {A-A|G(67|33,2.1);G-G|T(72|28,2.5)}” ：
			<div class="_leftShort">（1）“M”表示野生型编码氨基酸为M；“204”表示氨基酸位点为204；“M|V”表示由原来的野生型M变为V；</div>
			<div class="_leftShort">（2）“{A-A|G(67|33,2.1);G-G|T(72|28,2.5)} ” 表示碱基的变化，其中一个位点由原来的A变为A|G杂合，比例为67比33；另一个碱基由原来的G变为G|T杂合，比例为72比28。</div>
    		<div class="_leftShort">（3）比例值说明：A-A|G(67|33,2.1)，2.1为67和33的比值，该比例并不代表样本中该位点A和G的真实数量比例，只是代表该位点是A的可能性为67%，是G的可能性 为33%。如果没有“(67|33,2.1）”出现，认为该位点100%发生突变。如果是A-A|G，说明该位点为A的可能性大；如果为A-G|A，说明该位点为G的 可能性大。当野生型的碱基（即A）的可能性大于突变碱基（即G）的可能性时，如果比值小于5我们认为该位点是突变；如果突变碱基（即G）的可能性大 于野生型的碱基（即A）的可能性时，不论比值多少都认为发生了突变。</div>
        </div>
			<div class="lineheight">2. “ *Wild Type: GCT ” 表示该位点的野生型为GCT</div>
			<div class="lineheight">3. 峰图中的 “ * ” 表示该位置的碱基发生了突变</div>
			<div class="lineheight">4. ” *Low quality " 表示该区域的碱基测序质量值太低</div>
		</div>
		<div id="_showOne">
			<div class="lineheight">峰图中的 “ * ” 表示该位置的碱基发生了突变</div>
		</div>
	</div>
	<div class="modal-footer">
		<a class="btn close" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
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
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",1260);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",156);
	window.parent.showZoom(src.replace("png","10.png"));
}
function showBgOne(src){
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",1260);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",156);
	window.parent.showZoom(src);
}
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
function showModal(id,flag){
	if(flag == 1){
		$("#_showOne").css("display","");
		$("#_showMore").css("display","none");
	}else{
		$("#_showMore").css("display","");
		$("#_showOne").css("display","none");
	}
	$("#"+id).modal("show");
}
</script>
<style>
.lineheight{
	padding-top: 10px;
}
._leftShort{
	line-height:2;
	padding-left: 14px;
}
.imgmiss{
	font-size: 12px;
    width: 130px;
    border: 1px solid;
    padding: 30px 10px 10px 10px;
    margin-top: 19px;
    margin-left: 30px;
    height: 90px;
    float: left;
    border-color: black;
    color: #CC0000;
}
</style>