<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="<%=request.getContextPath()%>/plugins/smartJqueryZoom/zoom-styles.css" rel="stylesheet" type="text/css"/>
<!-- 放大图片所需的div -->
<div id="fullbg"></div> 
<div id="pageContent" class="pageContent">
	<a class="zoomClose" id="closeZoom" href="javascript:closeZoom();" style="margin-right: 75px;"></a>
	<img id="imageFullScreen" src="">
</div>
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:void(0)"><i class="fa fa-files-o"></i> 报告</a></li>
    <li class="active">数据报告</li>
    <li><a href="javascript:void(0)" onclick="toProjectReport()">返回</a></li>
  </ol>
</section>
<section class="content">
  <div class="row">
    <div class="col-xs-10">
      <div class="box box-success color-palette-box report-data-content" id="reportResultDiv">
      	
      </div>
    </div>
    <div class="col-xs-2">
     <div class="btn-group-vertical width-percent-100 report-data-titlelist" id="fileListUl">
      </div>
    </div>
  </div>
</section>
<script src="<%=request.getContextPath() %>/plugins/backToTop/toTop.1.0.js"></script>
<script src="<%=request.getContextPath() %>/plugins/smartJqueryZoom/e-smart-zoom-jquery.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#imageFullScreen').smartZoom({'containerClass':'zoomableContainer'});
});
function showZoom(src) {
	var bh = $(window.parent.document).height();  
	var bw = $(window.parent.document).width();
	$("#imageFullScreen").smartZoom({'containerClass':'zoomableContainer'});
	$("#imageFullScreen").attr("src",src);
	$("#fullbg").css({  
		height:bh,  
		width:bw,  
		display:"block"  
	});
	$("#closeZoom").css({display:"block"});
	$("#pageContent").show();
}
function closeZoom(){
	$('#imageFullScreen').smartZoom('destroy');
	$("#fullbg,#pageContent,#closeZoom").hide(); 
}
</script>
