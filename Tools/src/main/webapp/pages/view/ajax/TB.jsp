<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
	<input type="hidden" value="<s:property value="resultMap.projectId"/>" id="_projectId">
	<!--文件名称-->
	<div class="m-file">
		<div class="row">
			<div class="col-lg-9 force-break">
				项目名称：
				<span class="file-name">
					${resultMap['projectName'] }
				</span>
				<br/>
				App 名称：
				<span class="file-name">
					${resultMap['appName'] }
				</span>
				<br/>
				文件名称：
				<span class="file-name">
					<s:property value="%{resultMap.pagePath.replace('/SVG','').substring(resultMap.pagePath.replace('/SVG','').lastIndexOf('/')+1,resultMap.pagePath.replace('/SVG','').length())}"/>(<s:property value="resultMap.fileName"/>)
				</span>
			</div>
			<div class="col-lg-3">
				<div class="toolbar" style="position: inherit;right: auto;">
					<s:if test="%{!resultMap.pdf.equals('false')}">
						<a href="<s:property value="resultMap.pdf"/>" class="btn btn-default"><i class="i-pdf"></i>PDF下载</a>
					</s:if>
					<a href='javascript:toPrintHBV("<s:property value="resultMap.pagePath"/>")' class="btn btn-default"><i class="i-printback i-print"></i>打印报告</a>
				</div>
			</div>
		</div>
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
	    <div class="m-boxCon result">
			<s:if test="%{''.equals(resultMap.seq.trim())}">
				<div style="color: red;" >程序分析异常，没有得到任何结果</div>
			</s:if>
			<s:else>
				<s:property value="resultMap.seq" escape="false"/>
			</s:else>
	    </div>
	</div>
	<!--染色体序列点图-->
	<div class="m-box">
		<h2><i class="i-dna"></i>原始峰图</h2>
		<div class="m-boxCon">
			<s:if test="%{''.equals(resultMap.listAll1)&&''.equals(resultMap.listAll2)&&''.equals(resultMap.listAll3)&&''.equals(resultMap.listAll4)&&''.equals(resultMap.listAll5)}">
				<div style="color: red;" >程序分析异常，没有得到任何结果</div>
			</s:if>
	    </div>
	    <div class="m-boxCon">
			<s:if test="%{!''.equals(resultMap.listAll1)}">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>','listAll1Img');" >
					<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll1"/>" style="width: 750px;height: 150px;" id="listAll1Img">
				</a>
			</s:if>
	    </div>
	    <div class="m-boxCon">
			<s:if test="%{!''.equals(resultMap.listAll2)}">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>','listAll2Img');" >
					<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll2"/>" style="width: 750px;height: 150px;" id="listAll2Img">
				</a>
			</s:if>
	    </div>
	     <div class="m-boxCon">
			<s:if test="%{!''.equals(resultMap.listAll3)}">	
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>','listAll3Img');" >
					<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll3"/>" style="width: 750px;height: 150px;" id="listAll3Img">
				</a>
			</s:if>
	    </div>
	     <div class="m-boxCon">
			<s:if test="%{!''.equals(resultMap.listAll4)}">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>','listAll4Img');" >
					<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll4"/>" style="width: 750px;height: 150px;" id="listAll4Img">
				</a>
			</s:if>
	    </div>
	     <div class="m-boxCon">
			<s:if test="%{!''.equals(resultMap.listAll5)}">
				<a href="javascript:showBg('<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll5"/>','listAll5Img');" >
					<img name="imgSrc" src="<s:property value="resultMap.outPath"/>/<s:property value="resultMap.pagePath"/>/<s:property value="resultMap.listAll5"/>" style="width: 750px;height: 150px;" id="listAll5Img">
				</a>
			</s:if>
	    </div>
	</div>	
	<!--Celloud数据参数同比分析-->
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
	$("img[id='imageFullScreen']").css("width",width*1.5);
	$("img[id='imageFullScreen']").css("height",height*1.5);
	showZoom(src);
}
function searchTable(){
	var search = $("#_snum").val();
	$("#_sr").html("");
	$("#egfrTable").find("td").each(function(){
		var context = $(this).html();
		if(!search){
			$("#_sr").append("<tr><td>"+context.replace(new RegExp("\t", "g"), "&nbsp;&nbsp;&nbsp;&nbsp;")+"</tr></td>");
		}else{
			var len = context.indexOf("-");
			var d = context.indexOf(",");
			var k = context.indexOf(")");
			if(len==-1){
				if(d>-1&&k>-1){
					var result = context.substring(d+1,k);
					if(parseFloat(result)<parseFloat(search)){
						$("#_sr").append("<tr><td>"+context.replace(new RegExp("\t", "g"), "&nbsp;&nbsp;&nbsp;&nbsp;")+"</tr></td>");
					}else{
						var kl = context.indexOf("(");
						context = context.substring(0,kl)+context.substring(k+1,context.length);
						$("#_sr").append("<tr><td>"+context.replace(new RegExp("\t", "g"), "&nbsp;&nbsp;&nbsp;&nbsp;")+"</tr></td>");
					}
				}else{
					$("#_sr").append("<tr><td>"+context.replace(new RegExp("\t", "g"), "&nbsp;&nbsp;&nbsp;&nbsp;")+"</tr></td>");
				}
			}else{
				var before = $.trim(context.substring(len-2,len-1));
				var after = $.trim(context.substring(len+1,len+3));
				if(before==after){
					if(d>-1&&k>-1){
						var result = context.substring(d+1,k);
						if(parseFloat(result)<parseFloat(search)){
							$("#_sr").append("<tr><td>"+context.replace(new RegExp("\t", "g"), "&nbsp;&nbsp;&nbsp;&nbsp;")+"</tr></td>");
						}
					}else{
						$("#_sr").append("<tr><td>"+context.replace(new RegExp("\t", "g"), "&nbsp;&nbsp;&nbsp;&nbsp;")+"</tr></td>");
					}
				}else{
					if(d>-1&&k>-1){
						var result = context.substring(d+1,k);
						if(parseFloat(result)>parseFloat(search)){
							var c = context.split("\t");
							var c3 = c[3].substring(0,c[3].indexOf("|"));
							var c4 = (c[4].substring(0,c[3].lastIndexOf("|")-1)+c[4].substring(c[4].lastIndexOf(")")+1,c[4].length)).replace("(","");
							var c5;
							if(c[5].indexOf("|")==-1){
								c5 = c[5];
							}else{
								c5 = c[5].substring(0,c[5].indexOf("|"));
							}
							var last = c[0]+"\t"+c[1]+"\t"+c[2]+"\t"+c3+"\t"+c4+"\t"+c5;
							$("#_sr").append("<tr><td>"+last.replace(new RegExp("\t", "g"), "&nbsp;&nbsp;&nbsp;&nbsp;")+"</tr></td>");
						}else{
							$("#_sr").append("<tr><td>"+context.replace(new RegExp("\t", "g"), "&nbsp;&nbsp;&nbsp;&nbsp;")+"</tr></td>");
						}
					}else{
						$("#_sr").append("<tr><td>"+context.replace(new RegExp("\t", "g"), "&nbsp;&nbsp;&nbsp;&nbsp;")+"</tr></td>");
					}
				}
			}
		}
	});
}
</script>