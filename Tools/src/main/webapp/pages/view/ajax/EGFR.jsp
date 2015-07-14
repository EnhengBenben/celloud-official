<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="row">
	<input type="hidden" value="<s:property value="resultMap.length"/>" id="seq_length"/>
	<!--文件名称-->
	<div class="m-file">文件名称：
		<span class="file-name"><s:property value="%{resultMap.pagePath.replace('/SVG','').substring(resultMap.pagePath.replace('/SVG','').lastIndexOf('/')+1,resultMap.pagePath.replace('/SVG','').length())}"/>(<s:property value="resultMap.fileName"/>)</span>
		<s:if test="%{!resultMap.pdf.equals('false')}">
			<div class="toolbar"><a href="<s:property value="resultMap.pdf"/>" class="btn btn-default"><i class="i-pdf"></i>PDF下载</a></div>
		</s:if>
		<a href='javascript:toPrintHBV("<s:property value="resultMap.pagePath"/>")' class="btn btn-info toolbar" style="float:right;margin-right:110px;"><i class="i-printback i-print"></i>打印报告</a>
	</div>
	<!--位点突变-->
	<div class="m-box m-box-yc">
		<h2>
			<i class="i-edit"></i>报告
			<span class="filter">
				<input type="text" value="5" id="_snum"><a href="javascript:void(0)" class="btn btn-orange" onclick="searchTable()"><i class="i-filter"></i>筛选</a>
			</span>
		</h2>
	    <div class="m-boxCon result" id="report_tb">
			<table id="_sr">
			</table>
	    </div>
	</div>
	<div id="egfrTable" style="display: none;">
		<s:property value="resultMap.result" escape="false"/>
	</div>
	<!--检测结果-->
	<div class="m-box">
		<h2><i class="i-edit"></i>原始序列</h2>
	    <div class="m-boxCon result" id="_seq">
			<s:property value="resultMap.seq" escape="false"/>
	    </div>
	</div>
	<!--染色体序列点图-->
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
	<div class="bg-analysis">
		<div class="m-box">
			<h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
			<div class="m-boxCon" id="charDiv">
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
 	searchTable();
});
function showBg(src,id) { 
	var width = $("#" + id).width();
	var height = $("#" + id).height();
	$(window.parent.document).find("img[id='imageFullScreen']").css("width",width*1.5);
	$(window.parent.document).find("img[id='imageFullScreen']").css("height",height*1.5);
	window.parent.showZoom(src);
}
function searchTable(){
	var search = $("#_snum").val();
	$("#_sr").html("");
	$("#egfrTable").find("td").each(function(){
		var context = $(this).html();
		if(search==""){
			$("#_sr").append("<tr><td>"+context+"</tr></td>");
		}else{
			var len = context.indexOf("-");
			var before = $.trim(context.substring(len-2,len-1));
			var after = $.trim(context.substring(len+1,len+3));
			var d = context.indexOf(",");
			var k = context.indexOf(")");
			if(before==after){
				if(d>-1&&k>-1){
					var result = context.substring(d+1,k);
					if(parseFloat(result)<parseFloat(search)){
						$("#_sr").append("<tr><td>"+context+"</tr></td>");
					}
				}else{
					$("#_sr").append("<tr><td>"+context+"</tr></td>");
				}
			}else{
				var sub = context.indexOf("|");
				if(sub>-1){
					if(d>-1&&k>-1){
						var result = context.substring(d+1,k);
						if(parseFloat(result)>parseFloat(search)){
							var last = context.substring(k+1,context.length);
							var l = last.indexOf("|");
							if(l==-1){
								l = last.length;
							}
							$("#_sr").append("<tr><td>"+context.substring(0,sub)+last.substring(0,l)+"</tr></td>");
						}else{
							$("#_sr").append("<tr><td>"+context+"</tr></td>");
						}
					}else{
						$("#_sr").append("<tr><td>"+context+"</tr></td>");
					}
				}else{
					$("#_sr").append("<tr><td>"+context+"</tr></td>");
				}
			}
		}
	});
}
</script>