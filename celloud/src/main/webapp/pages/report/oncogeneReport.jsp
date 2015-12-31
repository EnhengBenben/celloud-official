<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div>
	<input type="hidden" value="${oncogene.length }" id="seq_length"/>
	<!--文件名称-->
	<div class="m-file">
		<div class="row">
			<div class="col-lg-9 force-break">
				文件名称：
				<span class="file-name">
					${oncogene.dataKey }(${oncogene.fileName })
				</span>
			</div>
			<div class="col-lg-3">
				<div class="toolbar" style="position: inherit;right: auto;">
					<c:if test="${oncogene.pdf!=null && oncogene.pdf!='' }">
						<a href="${down }${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/${oncogene.pdf }" class="btn btn-default"><i class="i-pdf"></i>PDF下载</a>
					</c:if>
<%-- 					<a href='javascript:toPrintHBV("${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }")' class="btn btn-default"><i class="i-printback i-print"></i>打印报告</a> --%>
				</div>
			</div>
		</div>
	</div>
	<!--位点突变-->
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>一、 已知突变位点（依据已发表文献，该突变位点有明确临床意义）
		</h2>
	    <div class="m-boxCon result">
	    	<div id="knowResult">
		    	<c:if test="${oncogene.wz1!=null && oncogene.wz1!='' }">
		    		${oncogene.wz1 }
		    	</c:if>
		    	<c:if test="${oncogene.wz1==null || oncogene.wz1=='' }">
	    			未检测到突变
		    	</c:if>
	    	</div>
	    	<br/>
	    	<c:if test="${oncogene.knowMutation!=null}">
		    	<c:forEach var="img" items="${oncogene.knowMutation }">
		    		<br/>
			    	<img name="know" src="${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${img}" style="width: 100%;">
		    	</c:forEach>
	    	</c:if>
	    </div>
	</div>
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>二、 未知突变位点（该突变位点目前没有已发表文献支持，无明确临床意义）
		</h2>
	    <div class="m-boxCon result">
	    	<c:if test="${oncogene.wz2!=null && oncogene.wz2!='' }">
		    	${oncogene.wz2 }
		    	<br/>
		    	<c:forEach var="img" items="${oncogene.out }">
		    		<a href="javascript:showBgTwo('${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${img.replace('png','10.png') }');" >
						<img class="imgtop" title="${img }" name="imgSrc" style="padding-left: 30px;" src="${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${img }" height="170px;" width="150px;">
					</a>
		    	</c:forEach>
	    	</c:if>
	    	<c:if test="${oncogene.wz2==null || oncogene.wz2=='' }">
	    		数据正常，未找到其他突变。
	    	</c:if>
	    </div>
	</div>
	<div class="m-box">
		<h2>
			<i class="i-edit"></i>三、 参考结论
		</h2>
	    <div class="m-boxCon result" id="_result">
	    	${oncogene.report }
	    </div>
	</div>
	<!--检测结果-->
	<div class="m-box">
		<h2><i class="i-edit"></i>四、 测序序列结果</h2>
	    <div class="m-boxCon result" id="_seq">
			${oncogene.seq }
	    </div>
	</div>
	<!--染色体序列点图-->
	<div class="m-box">
		<h2><i class="i-dna"></i>五、 测序峰图结果</h2>
	    <c:if test="${oncogene.original.containsKey('1_all_png') }">
		    <div class="m-boxCon result">
					<a href="javascript:showBg('${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${oncogene.original['1_all_png'] }','listAll1Img');" >
						<img name="imgSrc" src="${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${oncogene.original['1_all_png'] }" style="width: 85%;" id="listAll1Img">
					</a>
		    </div>
		</c:if>
	    <c:if test="${oncogene.original.containsKey('2_all_png') }">
		    <div class="m-boxCon result">
					<a href="javascript:showBg('${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${oncogene.original['2_all_png'] }','listAll2Img');" >
						<img name="imgSrc" src="${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${oncogene.original['2_all_png'] }" style="width: 85%;" id="listAll2Img">
					</a>
		    </div>
		</c:if>
		<c:if test="${oncogene.original.containsKey('3_all_png') }">
			<div class="m-boxCon result">
				<a href="javascript:showBg('${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${oncogene.original['3_all_png'] }','listAll3Img');" >
					<img name="imgSrc" src="${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${oncogene.original['3_all_png'] }" style="width: 85%;" id="listAll3Img">
				</a>
			</div>
		</c:if>
	     <c:if test="${oncogene.original.containsKey('4_all_png') }">
		     <div class="m-boxCon result">
					<a href="javascript:showBg('${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${oncogene.original['4_all_png'] }','listAll4Img');" >
						<img name="imgSrc" src="${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${oncogene.original['4_all_png'] }" style="width: 85%;" id="listAll4Img">
					</a>
		    </div>
	     </c:if>
     	<c:if test="${oncogene.original.containsKey('5_all_png') }">
		     <div class="m-boxCon result">
					<a href="javascript:showBg('${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${oncogene.original['5_all_png'] }','listAll5Img');" >
						<img name="imgSrc" src="${path }/${oncogene.userId }/${oncogene.appId }/${oncogene.dataKey }/SVG/${oncogene.original['5_all_png'] }" style="width: 85%;" id="listAll5Img">
					</a>
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
});
function showBgTwo(src){
	$("img[id='imageFullScreen']").css("width",1050);
	$("img[id='imageFullScreen']").css("height",157.5);
	showZoom(src);
}
function showBg(src,id) { 
	var width = $("#" + id).width();
	var height = $("#" + id).height();
	$("img[id='imageFullScreen']").css("width",width*1.5);
	$("img[id='imageFullScreen']").css("height",height*1.5);
	showZoom(src);
}
</script>