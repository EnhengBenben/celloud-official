<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div>
	<!--文件名称-->
	<div class="m-file">文件名称：
		<span class="file-name">${hcv.dataKey }(${hcv.fileName })</span>
		<div class="toolbar">
			<a href='javascript:toPrintHBV("${hcv.userId }/${hcv.appId }/${hcv.dataKey }")' class="btn btn-default"><i class="i-print"></i>打印报告</a>
			<a href="javascript:void(0)" class="btn btn-default" onclick="change()" id="change" style="display: none;">显示更多</a>
		</div>
	</div>
	<div id="cfda">
		<!--检测结果-->
	    <div class="m-box">
	    	<h2><i class="i-edit"></i>检测结果</h2>
	        <div class="m-boxCon result">
				<table class="table table-bordered table-condensed" id="hcvTable">
					<thead>
						<tr>
							<th>File Name</th>
							<th>Subtype</th>
							<th>Subject Name</th>
							<th>Identity</th>
							<th>Overlap/total</th>
							<th>E_value</th>
							<th>Score</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${hcv.fileName }</td>
							<td>${hcv.subtype }</td>
							<td>${hcv.subjectName }</td>
							<td>${hcv.identity }</td>
							<td>${hcv.total }</td>
							<td>${hcv.evalue }</td>
							<td>${hcv.score }</td>
						</tr>
					</tbody>
				</table>
	        </div>
	    </div>
	</div>
	<div id="nomal" style="display: none;">
		<!--检测结果-->
	    <div class="m-box">
	    	<h2><i class="i-edit"></i>检测结果</h2>
	        <div class="m-boxCon result" id="resultHcv2">
				<table class="table table-bordered table-condensed" id="hcvTable">
					<thead>
						<tr>
							<th>File Name</th>
							<th>Subtype</th>
							<th>Subject Name</th>
							<th>Identity</th>
							<th>Overlap/total</th>
							<th>E_value</th>
							<th>Score</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${hcv.fileName }</td>
							<td>${hcv.subtype }</td>
							<td>${hcv.subjectName }</td>
							<td>${hcv.identity }</td>
							<td>${hcv.total }</td>
							<td>${hcv.evalue }</td>
							<td>${hcv.score }</td>
						</tr>
					</tbody>
				</table>
	        </div>
	    </div>
	</div>
	<div class="m-box">
		<h2><i class="i-edit"></i>原始序列</h2>
	    <div class="m-boxCon result" id="seq">
			${hcv.seq }
	    </div>
	</div>
	<div class="m-box" id="printDiv3">
		<h2><i class="i-dna"></i>原始峰图</h2>
	    <div class="m-boxCon result">
			<a href="javascript:showBg('${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['1_all_png'] }','listAll1Img');" >
				<img class="imgtop" name="imgSrc" src="${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['1_all_png'] }" style="width: 750px;height: 150px;" id="listAll1Img">
			</a>
	    </div>
	     <div class="m-boxCon result">
			<a href="javascript:showBg('${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['2_all_png'] }','listAll2Img');" >
				<img class="imgtop" name="imgSrc" src="${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['2_all_png'] }" style="width: 750px;height: 150px;" id="listAll2Img">
			</a>
	    </div>
	     <div class="m-boxCon result">
			<a href="javascript:showBg('${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['3_all_png'] }','listAll3Img');" >
				<img class="imgtop" name="imgSrc" src="${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['3_all_png'] }" style="width: 750px;height: 150px;" id="listAll3Img">
			</a>
	    </div>
	     <div class="m-boxCon result">
			<a href="javascript:showBg('${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['4_all_png'] }','listAll4Img');" >
				<img class="imgtop" name="imgSrc" src="${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['4_all_png'] }" style="width: 750px;height: 150px;" id="listAll4Img">
			</a>
	    </div>
	    <div class="m-boxCon result">
			<a href="javascript:showBg('${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['5_all_png'] }','listAll5Img');" >
				<img class="imgtop" name="imgSrc" src="${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['5_all_png'] }" style="width: 750px;height: 150px;" id="listAll5Img">
			</a>
	    </div>
	    <div class="m-boxCon result">
			<a href="javascript:showBg('${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['6_all_png'] }','listAll6Img');" >
				<img class="imgtop" name="imgSrc" src="${path }/${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['6_all_png'] }" style="width: 750px;height: 150px;" id="listAll6Img">
			</a>
	    </div>
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