<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<!--文件名称-->
	<div class="m-file">
	    <dl class="dl-horizontal datareport-title">
          <dt>项目名称：</dt>
          <dd>${project.projectName}</dd>
          <dt>应用名称：</dt>
          <dd>${hcv.appName}</dd>
          <dt>文件名称：</dt>
          <dd class="force-break">${hcv.fileName}(${hcv.dataKey})</dd>
        </dl>
        <div class="toolbar">
            <a class="btn btn-celloud-success btn-flat" target="_blank" href="report/printHCV?appId=${hcv.appId }&dataKey=${hcv.dataKey }&projectId=${hcv.projectId }"><i class="fa fa-print"></i>打印报告</a>
            <a class="btn btn-warning btn-flat" style="display: none;" href="javascript:void(0)" onclick="change()"><i class="fa fa-folder-open-o"></i><span id="_change">显示更多</span></a>
        </div>
	</div>
	<div id="cfda">
		<!--检测结果-->
	    <div class="m-box">
	    	
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
							<th>File Name<br>(文件名)</th>
							<th style="min-width: 70px;">Subtype<br>(亚型)</th>
							<th style="min-width: 100px;">Subject Name<br>(参考序列名)</th>
							<th style="min-width: 80px;">Identity<br>(相似度)</th>
							<th style="min-width: 200px;">Overlap/total<br>(比对上的长度/比对的总长度)</th>
							<th style="min-width: 70px;">E_value<br>(期望值)</th>
							<th style="min-width: 50px;">Score<br>(比分)</th>
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
		<c:if test="${hcv.original.containsKey('1_all_png') }">
		    <div class="m-boxCon result">
				<a href="javascript:bigOrigin('${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['1_all_png'] }','listAll1Img');" >
					<img class="imgtop originImg" name="imgSrc" src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['1_all_png'] }" id="listAll1Img">
				</a>
		    </div>
	    </c:if>
	    <c:if test="${hcv.original.containsKey('2_all_png') }">
		     <div class="m-boxCon result">
				<a href="javascript:bigOrigin('${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['2_all_png'] }','listAll2Img');" >
					<img class="imgtop originImg" name="imgSrc" src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['2_all_png'] }" id="listAll2Img">
				</a>
		    </div>
		</c:if>
	    <c:if test="${hcv.original.containsKey('3_all_png') }">
	     <div class="m-boxCon result">
			<a href="javascript:bigOrigin('${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['3_all_png'] }','listAll3Img');" >
				<img class="imgtop originImg" name="imgSrc" src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['3_all_png'] }" id="listAll3Img">
			</a>
	     </div>
	    </c:if>
	    <c:if test="${hcv.original.containsKey('4_all_png') }">
	     <div class="m-boxCon result">
			<a href="javascript:bigOrigin('${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['4_all_png'] }','listAll4Img');" >
				<img class="imgtop originImg" name="imgSrc" src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['4_all_png'] }" id="listAll4Img">
			</a>
	     </div>
	    </c:if>
	    <c:if test="${hcv.original.containsKey('5_all_png') }">
		    <div class="m-boxCon result">
				<a href="javascript:bigOrigin('${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['5_all_png'] }','listAll5Img');" >
					<img class="imgtop originImg" name="imgSrc" src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['5_all_png'] }" id="listAll5Img">
				</a>
		    </div>
		</c:if>
	    <c:if test="${hcv.original.containsKey('6_all_png') }">
		    <div class="m-boxCon result">
				<a href="javascript:bigOrigin('${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['6_all_png'] }','listAll6Img');" >
					<img class="imgtop originImg" name="imgSrc" src="${uploadPath}${hcv.userId }/${hcv.appId }/${hcv.dataKey }/SVG/${hcv.original['6_all_png'] }" id="listAll6Img">
				</a>
		    </div>
		</c:if>
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
				$("#_change").parent().css("display","");
			}
		}
	});
});
function change(){
	var val = $("#_change").html();
	if(val=="显示更多"){
		$("#nomal").css("display","");
		$("#cfda").css("display","none");
		$("#_change").html("返回");
	}else{
		$("#nomal").css("display","none");
		$("#cfda").css("display","");
		$("#_change").html("显示更多");
	}
}
</script>
