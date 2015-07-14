<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row">
	<s:if test="%{resultMap.info==null||resultMap.info.equals('')||resultMap.info.equals('null')}">
		<div class="m-file">
			文件名称：
			<span class="file-name">
				<s:property value="%{resultMap.pagePath.substring(resultMap.pagePath.lastIndexOf('/')+1,resultMap.pagePath.length())}"/>(<s:property value="resultMap.fileName"/>)
			</span>
			<div class="toolbar">
				<a href="<s:property value="resultMap.down"/>" class="btn btn-default"><i class="i-download"></i>报告下载</a>
				<s:if test="%{!resultMap.pdf.equals('false')}">
					<a href="<s:property value="resultMap.pdf"/>" class="btn btn-default"><i class="i-pdf"></i>PDF下载</a>
				</s:if>
				<a target="_blank" href='../../printPGS/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.miniPng"/>/<s:property value="resultMap.txt.replace('+','@')" escape="false"/>' class="btn btn-default"><i class="i-print"></i>打印报告</a>
				<s:if test="%{!resultMap.split.equals('')}">
					<a target="_blank" href='../../printPGS/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.split"/>/<s:property value="resultMap.txt.replace('+','@')" escape="false"/>' class="btn btn-default"><i class="i-print"></i>点图报告</a>
				</s:if>
			</div>
		</div>
		<!--报告图示一-->
		<div class="m-box">
			<h2>
				<i class="i-report1"></i>数据统计
				<s:if test="%{resultMap.appId!=85}">
					<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('countModal')"><i class="i-tips"></i></div>
				</s:if>
			</h2>
			<div class="m-boxCon" id="_table">
				<s:property value="resultMap.table" escape="false"/>
			</div>
			<s:if test="%{resultMap.notes!=null&&!resultMap.notes.equals('')&&resultMap.appId==85}">
				<div class="m-tips">
					<i class="i-tips"></i><s:property value="resultMap.notes"/>
				</div>
			</s:if>
		</div>
		<!--检测结果-->
		<div class="m-box m-box-yc">
			<h2><i class="i-edit"></i>报告
				<div style="float:right;padding-right: 30px" title="帮助" onclick="showModal('reportModal')"><i class="i-tips"></i></div>
			</h2>
			<div class="m-boxCon result" id="reportDiv">
				<s:if test="%{resultMap.appId==85}">
					<s:property value="resultMap.txt" escape="false"/>
				</s:if>
				<s:else>
					<s:if test="%{resultMap.xls.equals('')}">
						<s:property value="resultMap.txt" escape="false"/>
					</s:if>
					<s:else>
						<s:property value="resultMap.xls" escape="false"/>
					</s:else>
				</s:else>
			</div>
		</div>
        <!--染色体图示一-->
        <div class="m-box">
        	<h2><i class="i-dna"></i>染色体</h2>
            <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.miniPng"/>','miniPngImg');" >
					<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.miniPng"/>" style="width: 700px;" id="miniPngImg">
				</a>
            </div>
        </div>
        <!--染色体图示一-->
        <div class="m-box">
        	<h2><i class="i-dna"></i>染色体点图</h2>
            <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.testPng"/>','testPngImg');" >
					<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.testPng"/>" style="width: 700px;height: 220px" id="testPngImg">
				</a>
            </div>
        </div>
		<!--染色体图示一-->
        <div class="m-box">
        	<h2><i class="i-dna"></i>染色体位置图</h2>
            <div class="m-boxCon">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.finalPng"/>','finalPngImg');" >
					<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.finalPng"/>" style="height: 1000px;" id="finalPngImg">
				</a>
            </div>
        </div>
		<!--序列GC校正图-->
<!-- 		<div class="m-box"> -->
<!-- 			<h2><i class="i-dna"></i>序列GC校正图</h2> -->
<!-- 		    <div class="m-boxCon"> -->
<%-- 				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.rawPng"/>','rawPngImg');" > --%>
<%-- 					<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.rawPng"/>" style="width: 340px;" id="rawPngImg"> --%>
<!-- 				</a> -->
<%-- 				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.normalPng"/>','normalPngImg');" > --%>
<%-- 					<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.normalPng"/>" style="width: 340px;" id="normalPngImg"> --%>
<!-- 				</a> -->
<!-- 		    </div> -->
<!-- 		</div> -->
		<!--Celloud数据参数同比分析-->
		<div class="bg-analysis">
		    <div class="m-box">
		        <h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
		        <div class="m-boxCon" id="charDiv">
		        </div>
		        <div class="m-tips">
		        	<i class="i-tips"></i>
		        	<span id="charResult"></span>
		        </div>
		    </div>
		</div>
	</s:if>
	<s:else>
		<h3><s:property value="resultMap.name"/></h3>
		<p><s:property value="resultMap.info"/></p>
	</s:else>
</div>

<div class="modal fade" id="countModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header well">
		<a class="close" data-dismiss="modal">×</a>
		<h4>数据统计说明</h4>
	</div>
	<div class="modal-body well">
		<div class="lineheight">Total_Reads：样本测序序列总数据量，分析要求数据量大于15万。</div>
		<div class="lineheight">Map_Reads：样本序列比对到人类基因组的数据量。</div>
		<div class="lineheight">Map_Ratio：样本序列比对到人类基因组的数据量比例。</div>
		<div class="lineheight">Duplicate：样本测序过程中冗余序列比例，冗余序列产生于同一序列片段，为消除冗余的对分析染色体拷贝数的影响，分析过程中去除冗余序列。</div>
		<div class="lineheight">GC_Count：样本测序序列的GC含量，围绕人类基因组GC平均含量41%波动。</div>
		<div class="lineheight">SD：样本拷贝数分析中染色体的平均偏差，SD越小假阳率越低，SD小于3.5可检测4Mb以上染色体异常。</div>
		<s:if test="%{resultMap.appId==81||resultMap.appId==88||resultMap.appId==91||resultMap.appId==93}">
			<div class="lineheight">MT_Ratio：样本测序序列中线粒体序列百分比。数据统计表明染色体拷贝数异常胚胎线粒体比例高。</div>
			<div>
				<img alt="" src="<s:property value="resultMap.outPath.replace('upload','')"/>/resource/img/pgs.png" width="100%">
			</div>
		</s:if>
	</div>
	<div class="modal-footer">
		<a class="btn close" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
	</div>
  </div>
 </div>
</div>
<div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header well">
		<a class="close" data-dismiss="modal">×</a>
		<h4>报告说明</h4>
	</div>
	<div class="modal-body well">
		<div class="lineheight">Aneuploidy：染色体异倍性及异常区域。</div>
		<div class="lineheight">Position：染色体异常位置。</div>
		<div class="lineheight">Average：染色体拷贝数分析中每个染色体的平均值。该值为染色体拷贝数据的参考值。数据统计表明大部分正常染色体平均值为15-23，染色体三体的平均值大于25，染色体单体的平均值小于13。</div>
	</div>
	<div class="modal-footer">
		<a class="btn close" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
	</div>
  </div>
 </div>
</div>
<style>
.lineheight{
	line-height: 30px;
}
</style>
<script>
$(function() {
	$(window).manhuatoTop({
		showHeight : 100,
		speed : 1000
	});
	$("#reportDiv").find("th").each(function(){
		$(this).css("padding-left","20px");
		$(this).css("text-align","left");
	});
	$("#reportDiv").find("td").each(function(){
		var result = $(this).text();
		if(result.length>50){
			$(this).html(result.substring(0,50)+"...");
			$(this).attr("title",result);
		}
		$(this).css("padding-left","20px");
		$(this).css("text-align","left");
	});
});
function showBg(src,id) { 
	var width = $("#" + id).width();
	var height = $("#" + id).height();
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",width*1.5);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",height*1.5);
	window.parent.showZoom(src);
}
function showModal(id){
	$("#"+id).modal("show");
}
</script>