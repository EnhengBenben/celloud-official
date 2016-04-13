<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<div class="m-file">
		<dl class="dl-horizontal datareport-title">
			<dt>项目名称：</dt>
			<dd>${project.projectName}</dd>
			<dt>应用名称：</dt>
			<dd>${tbrifampicin.appName}</dd>
			<dt>文件名称：</dt>
			<dd class="force-break">${tbrifampicin.fileName }(${tbrifampicin.dataKey })</dd>
		</dl>
		<div class="toolbar">
			<a class="btn btn-celloud-success btn-flat" target="_blank" href='report/printTBRifampicin?appId=${tbrifampicin.appId }&dataKey=${tbrifampicin.dataKey }&projectId=${tbrifampicin.projectId }'><i class="fa fa-print"></i>打印报告</a>
			<c:if test="${tbrifampicin.pdf!=null && tbrifampicin.pdf!='' }">
				<a class="btn btn-warning btn-flat" href="${toolsPath }Procedure!miRNADownload?userId=${tbrifampicin.userId }/${tbrifampicin.appId }/${tbrifampicin.dataKey }/${tbrifampicin.pdf }"><i class="fa fa-file-pdf-o"></i>PDF下载</a>
			</c:if>
		</div>
	</div>
	<!--位点突变-->
	<div class="m-box m-box-yc">
		<h2>
			<i class="i-edit"></i>报告 
			<span class="filter"> 
			     <input type="text" value="5" id="_snum" style="padding: 0; height: 35px;">
			     <a href="javascript:void(0)" class="btn btn-success" onclick="searchTable()"><i class="i-filter"></i>筛选</a>
			</span>
		</h2>
		<div class="m-boxCon result" id="report_tb">
			<table id="_sr">
			</table>
		</div>
	</div>
	<div id="egfrTable" style="display: none;">${tbrifampicin.report }</div>
	<!--检测结果-->
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>原始序列
		</h2>
		<div class="m-boxCon result">
			<c:if test="${empty tbrifampicin.seq }">
				<div style="color: red;">程序分析异常，没有得到任何结果</div>
			</c:if>
			<c:if test="${not empty tbrifampicin.seq }">
			     ${tbrifampicin.seq }
			</c:if>
		</div>
	</div>
	<!--染色体序列点图-->
	<div class="m-box">
		<h2>
			<i class="i-dna"></i>原始峰图
		</h2>
		<c:if test="${empty tbrifampicin.original }">
			<div class="m-boxCon">
				<div style="color: red;">程序分析异常，没有得到任何结果</div>
			</div>
		</c:if>
		<c:if test="${not empty tbrifampicin.original }">
			<c:forEach items="${tbrifampicin.original }" var="original" varStatus="st">
				<div class="m-boxCon">
					<a href="javascript:showBg('${uploadPath}${tbrifampicin.userId }/${tbrifampicin.appId }/${tbrifampicin.dataKey }/SVG/${original }','original${st.count }');">
						<img name="imgSrc" src="${uploadPath}${tbrifampicin.userId }/${tbrifampicin.appId }/${tbrifampicin.dataKey }/SVG/${original }" style="width: 85%;" id="original${st.count }">
					</a>
				</div>
			</c:forEach>
		</c:if>
	</div>
	<!--Celloud数据参数同比分析-->
	<div class="bg-analysis">
		<div class="m-box">
			<h2>
				<i class="i-celloud"></i>Celloud数据参数同比分析
			</h2>
			<div class="m-boxCon">
				<div class="row" id="charDiv"></div>
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
	function showBg(src, id) {
		var width = $("#" + id).width();
		var height = $("#" + id).height();
		$("img[id='imageFullScreen']").css("width", width * 1.5);
		$("img[id='imageFullScreen']").css("height", height * 1.5);
		showZoom(src);
	}
	function searchTable() {
		var search = $("#_snum").val();
		$("#_sr").html("");
		$("#egfrTable").find("td").each(function() {
			var context = $(this).html();
			if (!search) {
				$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
			} else {
				var len = context.indexOf("-");
				var d = context.indexOf(",");
				var k = context.indexOf(")");
				if (len == -1) {
					if (d > -1 && k > -1) {
						var result = context.substring(d + 1, k);
						if (parseFloat(result) < parseFloat(search)) {
							$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
						} else {
							var kl = context.indexOf("(");
							context = context.substring(0, kl)+ context.substring(k + 1,context.length);
							$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;") + "</tr></td>");
						}
					} else {
						$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
					}
				} else {
					var before = $.trim(context.substring(len - 2, len - 1));
					var after = $.trim(context.substring(len + 1, len + 3));
					if (before == after) {
						if (d > -1 && k > -1) {
							var result = context.substring(d + 1, k);
							if (parseFloat(result) < parseFloat(search)) {
								$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
							}
						} else {
							$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
						}
					} else {
						if (d > -1 && k > -1) {
							var result = context.substring(d + 1, k);
							if (parseFloat(result) > parseFloat(search)) {
								var c = context.split("\t");
								var c3 = c[3].substring(0, c[3].indexOf("|"));
								var c4 = (c[4].substring(0,c[3].lastIndexOf("|") - 1) + c[4].substring(c[4].lastIndexOf(")") + 1,c[4].length)).replace("(", "");
								var c5;
								if (c[5].indexOf("|") == -1) {
									c5 = c[5];
								} else {
									c5 = c[5].substring(0, c[5].indexOf("|"));
								}
								var last = c[0] + "\t" + c[1] + "\t" + c[2] + "\t" + c3 + "\t" + c4 + "\t" + c5;
								$("#_sr").append("<tr><td>"+ last.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
							} else {
								$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
							}
						} else {
							$("#_sr").append("<tr><td>"+ context.replace(new RegExp("\t","g"),"&nbsp;&nbsp;&nbsp;&nbsp;")+ "</tr></td>");
						}
					}
				}
			}
		});
	}
</script>