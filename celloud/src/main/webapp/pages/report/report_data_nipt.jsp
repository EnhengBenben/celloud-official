<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<div class="m-file">
	   <dl class="dl-horizontal datareport-title">
          <dt>项目名称：</dt>
          <dd>${project.projectName}</dd>
          <dt>应用名称：</dt>
          <dd>${nipt.appName}</dd>
          <dt>文件名称：</dt>
          <dd class="force-break">
            <c:choose>
              <c:when test="${empty nipt.anotherName }">${nipt.fileName }</c:when>
              <c:otherwise>${nipt.anotherName }</c:otherwise>
            </c:choose>
            (${nipt.dataKey})
          </dd>
        </dl>
        <div class="toolbar">
			<a id="_url" class="btn btn-celloud-success btn-flat" target="_blank" href="../../printNIPT/${nipt.userId }/${nipt.appId }/${nipt.dataKey }/${nipt.minipng }/"><i class="fa fa-print"></i>打印报告</a>
        </div>
	</div>
	<!--报告图示一-->
	<div class="m-box">
		<h2><i class="i-report1"></i>数据统计</h2>
		<div class="m-boxCon" id="_table">
			<table class="table table-bordered table-condensed">
				  <thead>
					<tr>
							<th>Total_Reads</th>
							<td>Reads_Length>50bp</td>
							<th>%</th>
							<th>Mapping_Reads</th>
							<th>%</th>
					</tr>
				  </thead>
				  <tbody>
					<tr>
							<td>${nipt.totalReads }</td>
							<td>${nipt.readsLength }</td>
							<td>${nipt.readsPercent }</td>
							<td>${nipt.mappingReads }</td>
							<td>${nipt.mappingPercent }</td>
					</tr>
				  </tbody>
				</table>
		</div>
	</div>
	<!--检测结果-->
	<div class="m-box m-box-yc">
		<h2><i class="i-edit"></i>报告</h2>
		<div class="m-boxCon result" id="_report">
			<c:if test="${nipt.detail!=null && nipt.detail.size()>0 }">
					<table class='table table-bordered table-condensed'>
						<c:forEach items="${nipt.detail }" var="info">
							<tr>
								<c:forEach var="ss" items="${info}" varStatus="st">  
								    <td>${ss }</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</table>
				</c:if>
		</div>
	</div>
	<!--染色体图示一-->
    <div class="m-box">
    	<h2><i class="i-dna"></i>染色体</h2>
        <div class="m-boxCon">
			<a href="javascript:showBg('${uploadPath}${nipt.userId }/${nipt.appId }/${nipt.dataKey }/Result/${nipt.minipng }','finalPngImg');" >
				<img src="${uploadPath}${nipt.userId }/${nipt.appId }/${nipt.dataKey }/Result/${nipt.minipng }" style="width: 750px;height: 150px;" id="finalPngImg">
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
	$("img[id='imageFullScreen']").css("width",width*1.5);
	$("img[id='imageFullScreen']").css("height",height*1.5);
	showZoom(src);
} 
</script>