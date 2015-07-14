<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="hcvdiv" class="row">
	<!--文件名称-->
	<div class="m-file">文件名称：
		<span class="file-name"><s:property value="resultMap.datakey"/>（<s:property value="resultMap.fileName"/>）</span>
		<div class="toolbar">
			<a href='javascript:toPrintHBV("<s:property value="resultMap.pagePath"/>")' class="btn btn-default"><i class="i-print"></i>打印报告</a>
			<a href="javascript:void(0)" class="btn btn-default" onclick="change()" id="change" style="display: none;">显示更多</a>
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
	<div class="bg-analysis">
		<div class="m-box">
			<h2><i class="i-celloud"></i>Celloud数据参数同比分析</h2>
			<div class="m-boxCon" id="charDiv">
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
</script>