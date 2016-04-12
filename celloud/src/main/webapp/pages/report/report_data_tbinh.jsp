<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<div class="m-file">
		<dl class="dl-horizontal datareport-title">
			<dt>项目名称：</dt>
			<dd>${project.projectName}</dd>
			<dt>应用名称：</dt>
			<dd>${tbinh.appName}</dd>
			<dt>文件名称：</dt>
			<dd class="force-break">${tbinh.fileName}(${tbinh.dataKey})</dd>
		</dl>
	</div>
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>Gene Name
		</h2>
		<div class="m-boxCon result">${tbinh.geneName }</div>
		<input type="hidden" id="_hidName" value="${tbinh.simpleGeneName }">
        <input type="hidden" id="_hidMutant" value="${mutant }">
        <input type="hidden" id="_hidWild" value="${wild }">
        <input type="hidden" id="_hidNeither" value="${neither }">
	</div>
	<!--位点突变-->
	<div class="m-box m-box-yc">
		<h2>
			<i class="i-edit"></i>Known mutation <span class="filter"> <input
				type="text" value="5" id="_snum1" style="padding: 0; height: 35px;"><a
				href="javascript:void(0)" class="btn btn-success"
				onclick="searchTable('_snum1','r1','_sr1')"><i class="i-filter"></i>筛选</a>
			</span>
		</h2>
		<div class="m-boxCon result">
			<table id="_sr1">
			</table>
		</div>
	</div>
	<!--位点突变-->
	<div class="m-box m-box-yc">
		<h2>
			<i class="i-edit"></i>Unknown mutation <span class="filter"> <input
				type="text" value="5" id="_snum2" style="padding: 0; height: 35px;"><a
				href="javascript:void(0)" class="btn btn-success"
				onclick="searchTable('_snum2','r2','_sr2')"><i class="i-filter"></i>筛选</a>
			</span>
		</h2>
		<div class="m-boxCon result">
			<table id="_sr2">
			</table>
		</div>
	</div>
	<div id="r1" style="display: none;">${tbinh.position }</div>
	<div id="r2" style="display: none;">${tbinh.mutationPosition }</div>
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>Samples Statistic
		</h2>
		<div class="m-boxCon" id="_showPie"></div>
	</div>
	<!--检测结果-->
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>原始序列
		</h2>
		<div class="m-boxCon result" id="_seq">${tbinh.seq }</div>
	</div>
	<!--染色体序列点图-->
	<div class="m-box">
		<h2>
			<i class="i-dna"></i>原始峰图
		</h2>
		<c:if test="${empty tbinh.original }">
			<div class="m-boxCon">样本数据异常,无法检测</div>
		</c:if>
		<c:if test="${not empty tbinh.original }">
			<c:forEach items="${tbinh.original }" var="original" varStatus="st">
				<div class="m-boxCon">
					<a
						href="javascript:showBg('${uploadPath}${tbinh.userId }/${tbinh.appId }/${tbinh.dataKey }/SVG/${original }','original${st.count }');">
						<img name="imgSrc"
						src="${uploadPath}${tbinh.userId }/${tbinh.appId }/${tbinh.dataKey }/SVG/${original }"
						style="width: 85%;" id="original${st.count }">
					</a>
				</div>
			</c:forEach>
		</c:if>
	</div>
</div>
<script>
	$(function() {
		$(window).manhuatoTop({
			showHeight : 100,
			speed : 1000
		});
		searchTable("_snum1", "r1", "_sr1");
		searchTable("_snum2", "r2", "_sr2");
	});
	function showBg(src, id) {
		var width = $("#" + id).width();
		var height = $("#" + id).height();
		$("img[id='imageFullScreen']").css("width", width * 1.5);
		$("img[id='imageFullScreen']").css("height", height * 1.5);
		showZoom(src);
	}
	function searchTable(numId, sourceId, resultId) {
		var search = $("#" + numId).val();
		$("#" + resultId).html("");
		var i = 0;
		$("#" + sourceId).find("td").each(
		function() {
			var context = $(this).html();
			if (search == "") {
				i++;
				$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
			} else {
				var len = context.lastIndexOf("-");
				var before = $.trim(context.substring(len - 2, len - 1));
				var after = $.trim(context.substring(len + 1, len + 3));
				var d = context.indexOf(",");
				var k = context.indexOf(")");
				if (before == after) {
					if (d > -1 && k > -1) {
						var result = context.substring(d + 1, k);
						if (parseFloat(result) < parseFloat(search)) {
							i++;
							$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
						}
					} else {
						i++;
						$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
					}
				} else {
					var sub = context.indexOf("|");
					if (sub > -1) {
						if (d > -1 && k > -1) {
							var result = context.substring(d + 1, k);
							if (parseFloat(result) > parseFloat(search)) {
								var last = context.substring(k + 1, context.length);
								var l = last.indexOf("|");
								if (l == -1) {
									l = last.length;
								}
								i++;
								$("#" + resultId).append("<tr><td>" + context.substring(0, sub) + last.substring(0, l) + "</tr></td>");
							} else {
								i++;
								$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
							}
						} else {
							i++;
							$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
						}
					} else {
						i++;
						$("#" + resultId).append("<tr><td>" + context + "</tr></td>");
					}
				}
			}
		});
		if (i == 0) {
			$("#" + resultId).append("<tr><td style='color:red'>没有符合筛选条件的结果</tr></td>");
		}
	}
</script>