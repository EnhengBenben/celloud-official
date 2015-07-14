<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row">
	<div class="m-file">文件名称：
		<span class="file-name"><s:property value="%{resultMap.pagePath.replace('/SVG','').substring(resultMap.pagePath.replace('/SVG','').lastIndexOf('/')+1,resultMap.pagePath.replace('/SVG','').length())}"/>(<s:property value="resultMap.fileName"/>)</span>
	</div>
	<div class="m-box">
		<h2><i class="i-edit"></i>突变类型</h2>
	    <div class="m-boxCon result">
			<s:property value="resultMap.result1" escape="false"/>
	    </div>
	</div>
	<div class="m-box m-box-yc">
		<h2>
			<i class="i-edit"></i>SNP
		</h2>
	    <div class="m-boxCon result" id="report_tb">
			<s:property value="resultMap.result2" escape="false"/>
	    </div>
	</div>
	<div class="m-box">
		<h2><i class="i-edit"></i>原始序列</h2>
	    <div class="m-boxCon result" id="_seq">
			<s:property value="resultMap.seq" escape="false"/>
	    </div>
	</div>
	<div class="m-box">
		<h2><i class="i-dna"></i>原始峰图</h2>
	    <div class="m-boxCon">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>','listAll1Img');" >
				<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>" style="width: 750px;height: 150px;" id="listAll1Img">
			</a>
	    </div>
	    <div class="m-boxCon">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>','listAll2Img');" >
				<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>" style="width: 750px;height: 150px;" id="listAll2Img">
			</a>
	    </div>
	     <div class="m-boxCon">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>','listAll3Img');" >
				<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>" style="width: 750px;height: 150px;" id="listAll3Img">
			</a>
	    </div>
	     <div class="m-boxCon">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>','listAll4Img');" >
				<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>" style="width: 750px;height: 150px;" id="listAll4Img">
			</a>
	    </div>
	     <div class="m-boxCon">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll5"/>','listAll5Img');" >
				<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll5"/>" style="width: 750px;height: 150px;" id="listAll5Img">
			</a>
	    </div>
	</div>
<!-- 	<div class="bg-analysis"> -->
<!-- 		<div class="m-box"> -->
<!-- 			<h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2> -->
<!-- 			<div class="m-boxCon" id="charDiv"> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
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
</script>