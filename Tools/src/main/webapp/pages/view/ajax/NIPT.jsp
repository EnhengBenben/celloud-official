<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row">
	<div class="m-file">
		文件名称：
		<span class="file-name">
			<s:property value="%{resultMap.pagePath.substring(resultMap.pagePath.lastIndexOf('/')+1,resultMap.pagePath.length())}"/>(<s:property value="resultMap.fileName"/>)
		</span>
		<div class="toolbar">
			<a target="_blank" id="_url" href='../../printNIPT/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.png"/>/' class="btn btn-default"><i class="i-print"></i>打印报告</a>
		</div>
	</div>
	<!--报告图示一-->
	<div class="m-box">
		<h2><i class="i-report1"></i>数据统计</h2>
		<div class="m-boxCon" id="_table">
			<s:property value="resultMap.r1" escape="false"/>
		</div>
	</div>
	<!--检测结果-->
	<div class="m-box m-box-yc">
		<h2><i class="i-edit"></i>报告</h2>
		<div class="m-boxCon result" id="_report">
			<s:property value="resultMap.r2" escape="false"/>
		</div>
	</div>
	<!--染色体图示一-->
    <div class="m-box">
    	<h2><i class="i-dna"></i>染色体</h2>
        <div class="m-boxCon">
			<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/Result/<s:property value="resultMap.png"/>','finalPngImg');" >
				<img src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/Result/<s:property value="resultMap.png"/>" style="width: 750px;height: 150px;" id="finalPngImg">
			</a>
        </div>
    </div>
</div>
<script>
$(function() {
	$(window).manhuatoTop({
		showHeight : 100,
		speed : 1000
	});
	var pa = "";
	$("#_report").find("td").each(function(){
		pa += $(this).html()+"@";
	});
	var reg = new RegExp("&lt;","g");
	pa = pa.replace(reg,"%3C");
	var url = $("#_url").attr("href");
	$("#_url").attr("href",url+pa);
});
function showBg(src,id) { 
	var width = $("#" + id).width();
	var height = $("#" + id).height();
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",width*1.5);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",height*1.5);
	window.parent.showZoom(src);
} 
</script>