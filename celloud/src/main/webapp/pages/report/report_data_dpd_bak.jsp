<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div>
	<div class="m-file">
	    <dl class="dl-horizontal datareport-title">
          <dt>项目名称：</dt>
          <dd>${project.projectName}</dd>
          <dt>应用名称：</dt>
          <dd>${dpd.appName}</dd>
          <dt>文件名称：</dt>
          <dd class="force-break">${dpd.fileName}(${dpd.dataKey})</dd>
        </dl>
	</div>
	<div class="m-box">
		<h2><i class="i-edit"></i>突变类型</h2>
	    <div class="m-boxCon result">
			<c:if test="${dpd.position!=null && dpd.position!='' }">
	    		${dpd.position }
    		</c:if>
    		<c:if test="${dpd.position==null || dpd.position=='' }">
    			未检测到突变
    		</c:if>
	    </div>
	</div>
	<div class="m-box m-box-yc">
		<h2>
			<i class="i-edit"></i>SNP
			<span class="filter">
				<input type="text" value="5" id="_snum" style="padding: 0;height: 36px;"><a href="javascript:void(0)" class="btn btn-success" onclick="searchTable()"><i class="i-filter"></i>筛选</a>
			</span>
		</h2>
	    <div class="m-boxCon result" id="report_tb">
			<table id="_sr">
			</table>
	    </div>
	</div>
	<div id="egfrTable" style="display: none;">
		<c:if test="${dpd.mutationPosition!=null }">
	    	${dpd.mutationPosition }
    	</c:if>
    	<c:if test="${dpd.mutationPosition==null }">
    		数据正常，未找到其他突变。
    	</c:if>
	</div>
	<div class="m-box">
		<h2><i class="i-edit"></i>原始序列</h2>
	    <div class="m-boxCon result" id="_seq">
			${dpd.seq }
	    </div>
	</div>
	<div class="m-box">
		<h2><i class="i-dna"></i>原始峰图</h2>
		<c:if test="${dpd.original!=null }">
	    	<c:forEach var="original" items="${dpd.original }" varStatus="st">
				<div class="m-boxCon result">
						<a href="javascript:bigOrigin('${uploadPath}${dpd.userId }/${dpd.appId }/${dpd.dataKey }/SVG/${original }','original${st.count }');" >
							<img name="imgSrc" class="originImg" src="${uploadPath}${dpd.userId }/${dpd.appId }/${dpd.dataKey }/SVG/${original }" id="original${st.count }">
						</a>
			    </div>
	    	</c:forEach>
		</c:if>
     	<c:if test="${dpd.original==null }">
		    <div class="m-boxCon result">
		    样本异常，无法检测
		    </div>
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