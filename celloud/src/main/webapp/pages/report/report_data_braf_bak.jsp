<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<div class="m-file">
		<dl class="dl-horizontal datareport-title">
			<dt>项目名称：</dt>
			<dd>${project.projectName}</dd>
			<dt>应用名称：</dt>
			<dd>${braf.appName}</dd>
			<dt>文件名称：</dt>
			<dd class="force-break">${braf.fileName}(${braf.dataKey})</dd>
		</dl>
	</div>
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>突变类型
		</h2>
		<div class="m-boxCon result">
			<c:if test="${braf.position!=null && braf.position!='' }">
	    		${braf.position }
    		</c:if>
			<c:if test="${braf.position==null || braf.position=='' }">
    			未检测到突变
    		</c:if>
		</div>
	</div>
	<div class="m-box m-box-yc">
		<h2>
			<i class="i-edit"></i>SNP <span class="filter"> <input
				type="text" value="5" id="_snum" style="padding: 0; height: 35px;"><a
				href="javascript:void(0)" class="btn btn-success"
				onclick="searchTable()"><i class="i-filter"></i>筛选</a>
			</span>
		</h2>
		<div class="m-boxCon result">
			<table>
				<tbody>
					<tr>
						<td style="width: 30%; min-width: 265px" id="report_tb"><c:if
								test="${braf.mutationPosition!=null }">
					    	${braf.mutationPosition }
				    	</c:if> <c:if test="${braf.mutationPosition==null }">
				    		数据正常，未找到其他突变。
				    	</c:if></td>
						<td style="width: 40%; text-align: center;"><img
							src="../../resources/img/report/arrow1.png"></td>
						<td style="width: 30%; padding-right: 50px">
							<div
								style="width: 58px; min-width: 58px; padding: 20px 20px; border: 4px solid #B5D329; border-radius: 4px; margin-left: 30%; text-align: center"
								id="searchResult"></div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>原始序列
		</h2>
		<div class="m-boxCon result" id="_seq">${braf.seq }</div>
	</div>
	<div class="m-box">
		<h2>
			<i class="i-dna"></i>原始峰图
		</h2>
		<c:if test="${braf.original!=null }">
			<c:forEach var="original" items="${braf.original }" varStatus="st">
				<div class="m-boxCon result">
					<a href="javascript:bigOrigin('${uploadPath}${braf.userId }/${braf.appId }/${braf.dataKey }/SVG/${original }','original${st.count }');">
						<img class="originImg" src="${uploadPath}${braf.userId }/${braf.appId }/${braf.dataKey }/SVG/${original }" id="original${st.count }">
					</a>
				</div>
			</c:forEach>
		</c:if>
		<c:if test="${braf.original==null }">
			<div class="m-boxCon result">样本异常，无法检测</div>
		</c:if>
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
	function searchTable() {
		var result = "";
		$("#report_tb").find("td").each(function() {
			var context = $(this).html();
			var len = context.indexOf("-");
			var before = $.trim(context.substring(len - 2, len - 1));
			var after = $.trim(context.substring(len + 1, len + 3));
			var d = context.indexOf(",");
			var k = context.indexOf(")");
			if (k == -1) {
				result += after;
			} else if (before != after) {
				result += after;
			} else {
				var search = $("#_snum").val();
				var r = context.substring(d + 1, k);
				if (parseFloat(r) > parseFloat(search)) {
					result += after;
				} else {
					var l = context.indexOf("|");
					var r = context.indexOf("(");
					result += context.substring(l + 1, r);
				}
			}
		});
		$("#searchResult").html(map[result]);
	}
</script>