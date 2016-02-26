<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<input type="hidden" value="<s:property value="resultMap.projectId"/>" id="_projectId">
	<div class="m-file">
	   <dl class="dl-horizontal datareport-title">
          <dt>项目名称：</dt>
          <dd>${resultMap['projectName'] }</dd>
          <dt>应用名称：</dt>
          <dd>${resultMap['appName'] }</dd>
          <dt>文件名称：</dt>
          <dd class="force-break"><s:property value="resultMap.fileName"/>(<s:property value="resultMap.datakey"/>)</dd>
        </dl>
        <div class="toolbar">
            <a class="btn btn-celloud-success btn-flat" href='javascript:toPrintHBV("<s:property value="resultMap.pagePath"/>")'><i class="fa fa-print"></i>打印报告</a>
            <a class="btn btn-warning btn-flat" style="display: none;" href="javascript:void(0)" onclick="change()"><i class="fa fa-folder-open-o"></i>显示更多</a>
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
	$("#cfda").find("thead").html("<tr><th>File Name<br>(文件名)</th><th style='min-width: 70px;'>Subtype<br>(亚型)</th><th style='min-width: 100px;'>Subject Name<br>(参考序列名)</th><th style='min-width: 80px;'>Identity<br>(相似度)</th><th style='min-width: 200px;'>Overlap/total<br>(比对上的长度/比对的总长度)</th><th style='min-width: 70px;'>E_value<br>(期望值)</th><th style='min-width: 50px;'>Score<br>(比分)</th></tr>");
	$("#nomal").find("thead").html("<tr><th>File Name<br>(文件名)</th><th style='min-width: 70px;'>Subtype<br>(亚型)</th><th style='min-width: 100px;'>Subject Name<br>(参考序列名)</th><th style='min-width: 80px;'>Identity<br>(相似度)</th><th style='min-width: 200px;'>Overlap/total<br>(比对上的长度/比对的总长度)</th><th style='min-width: 70px;'>E_value<br>(期望值)</th><th style='min-width: 50px;'>Score<br>(比分)</th></tr>");
	$("#cfda").find("td").each(function(i){
		$(this).css("word-break","break-all");
		$(this).css("word-wrap","break-word");
		if(i==1){
			var val = $(this).html();
			if(val!="1b"&&val!="2a"&&val!="3a"&&val!="3b"&&val!="6a"){
				$(this).html("其他");
				$("#change").css("display","");
			}
		}
	});
	$("#nomal").find("td").each(function(i){
		$(this).css("word-break","break-all");
		$(this).css("word-wrap","break-word");
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
	showZoom(src);
}
</script>