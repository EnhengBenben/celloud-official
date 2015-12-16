<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div>
	<div class="m-file">
		<div class="row">
			<div class="col-lg-9 force-break">
				文件名称：
				<span class="file-name"><s:property value="resultMap.datakey"/>（<s:property value="resultMap.fileName"/>）</span>
			</div>
			<div class="col-lg-3">
				<div class="toolbar" style="position: inherit;right: auto;">
					<a href='javascript:toPrintHBV("<s:property value="resultMap.pagePath"/>")' class="btn btn-default"><i class="i-print"></i>打印报告</a>
					<a href="javascript:void(0)" class="btn btn-default" onclick="change()" id="change" style="display: none;">显示更多</a>
				</div>
			</div>
		</div>
	</div>
	<div id="cfda">
		<!--检测结果-->
	    <div class="m-box">
	    	<h2><i class="i-edit"></i>检测结果</h2>
	        <div class="m-boxCon result">
				<s:property value="resultMap.table" escape="false"/>
	        </div>
	    </div>
	</div>
	<div id="nomal" style="display: none;">
		<!--检测结果-->
	    <div class="m-box">
	    	<h2><i class="i-edit"></i>检测结果</h2>
	        <div class="m-boxCon result" id="resultHcv2">
				<s:property value="resultMap.table" escape="false"/>
	        </div>
	    </div>
	</div>
	<div class="m-box">
		<h2><i class="i-edit"></i>原始序列</h2>
	    <div class="m-boxCon result" id="seq">
			<s:property value="resultMap.seq" escape="false"/>
	    </div>
	</div>
	<div class="m-box" id="printDiv3" <s:if test="%{resultMap.isAll.equals('false')}">style="display: none;"</s:if>>
		<h2><i class="i-dna"></i>原始峰图</h2>
	 <s:if test="%{resultMap.isAll.equals('true')}">
	    <div class="m-boxCon result">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll1"/>','listAll1Img');" >
				<img class="imgtop" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll1"/>" style="width: 750px;height: 150px;" id="listAll1Img">
			</a>
	    </div>
	     <div class="m-boxCon result">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll2"/>','listAll2Img');" >
				<img class="imgtop" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll2"/>" style="width: 750px;height: 150px;" id="listAll2Img">
			</a>
	    </div>
	     <div class="m-boxCon result">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll3"/>','listAll3Img');" >
				<img class="imgtop" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll3"/>" style="width: 750px;height: 150px;" id="listAll3Img">
			</a>
	    </div>
	     <div class="m-boxCon result">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll4"/>','listAll4Img');" >
				<img class="imgtop" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll4"/>" style="width: 750px;height: 150px;" id="listAll4Img">
			</a>
	    </div>
	    <div class="m-boxCon result">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll5"/>','listAll5Img');" >
				<img class="imgtop" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll5"/>" style="width: 750px;height: 150px;" id="listAll5Img">
			</a>
	    </div>
	    <div class="m-boxCon result">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll6"/>','listAll6Img');" >
				<img class="imgtop" name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/SVG/<s:property value="resultMap.listAll6"/>" style="width: 750px;height: 150px;" id="listAll6Img">
			</a>
	    </div>
	</s:if>
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
<script>
$(document).ready(function(){
	$("#cfda").find("td").each(function(i){
		if(i==1){
			var val = $(this).html();
			if(val!="1b"&&val!="2a"&&val!="3a"&&val!="3b"&&val!="6a"){
				$(this).html("其他");
				$("#change").css("display","");
			}
		}
	});
});
function change(){
	var val = $("#change").html();
	if(val=="显示更多"){
		$("#nomal").css("display","");
		$("#cfda").css("display","none");
		$("#change").html("返回");
	}else{
		$("#nomal").css("display","none");
		$("#cfda").css("display","");
		$("#change").html("显示更多");
	}
}
function showBg(src,id) { 
	var width = $("#" + id).width();
	var height = $("#" + id).height();
	$("img[id='imageFullScreen']").css("width",width*1.5);
	$("img[id='imageFullScreen']").css("height",height*1.5);
	window.parent.showZoom(src);
}
</script>