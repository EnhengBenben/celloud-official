<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="<%=request.getContextPath() %>/plugins/plupload-2.1.2/jquery.plupload.queue/css/jquery.plupload.queue.css?version=1.4" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath() %>/css/upload.css?v=3.0" rel="stylesheet" type="text/css">
<input type="hidden" id="fileDataSessionUserIdHidden" value='<s:property value="#session.userId"/>'>
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:void(0)"><i class="fa fa-sellsy"></i> 数据上传</a></li>
    <li class="active">全部</li>
  </ol>
</section>
<section class="content">
  <div class="row">
    <div class="col-xs-12">
      <div class="box box-success" id="fileUploadDiv">
      </div><!-- /.box -->
    </div>
  </div><!--/.row-->
</section><!-- /.content -->  
<div style="display: none;" id="_sessionTimeOut">
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/jquery.plupload.queue/jquery.plupload.queue.js?version=1.3"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/plupload.dev.js?version=1.7"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/i18n/zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/upload.js"></script>
<script type="text/javascript">
var session_userId = <%=session.getAttribute("userId")%>;
var sessionUserName = "<%=session.getAttribute("userName")%>";
//检验session是否超时
if(!session_userId){
	window.top.location = "<%=request.getContextPath() %>/toLogin";
}
Date.prototype.format = function(format){
	var o = {
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond
	};
	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 
	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
		} 
	} 
	return format;
};
$(function() {
	$("#fileUploadDiv").html("<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>");
	$("#fileUploadDiv").pluploadQueue({
		url : 'manyAjaxUploadFile_uploadManyFile',
		chunk_size : '1mb',
		file_data_name : 'file',
		filters : {
			max_file_size : '3gb',
			mime_types: [
				{title : "bam", extensions : "bam"},
				{title : "ab1", extensions : "ab1"},
				{title : "abi", extensions : "abi"},
				{title : "fasta", extensions : "fasta"},
				{title : "fastq", extensions : "fastq"},
				{title : "tsv", extensions : "tsv"},
				{title : "gz", extensions : "gz"},
				{title : "lis", extensions : "lis"},
				{title : "txt", extensions : "txt"}
			]
		},
		// Flash settings
		flash_swf_url : '../../plugins/plupload-2.1.2/Moxie.swf',
		headers: {
            Authorization: ""
        },
		max_retries : 5,
 		unique_names : true,
 		multiple_queues : true,
	});
	var uploader = $('#fileUploadDiv').pluploadQueue();
	uploader.bind('Init', function(){
		if(intro != null){
			$(".addfile").attr("disabled",true);
			$("._start_custom").attr("disabled",true);
			intro.exit();
			intro = null;
			intro = introJs();
			intro.setOption('tooltipPosition', 'auto');
			intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
			intro.setOption('showStepNumbers', false);
			intro.setOption('showButtons', false);
			intro.start();
			intro.goToStep(4);
			$("#toaddfilediv").bind('click',function(){
				if(intro != null){
					intro.exit();
					intro = null;
					intro = introJs();
					intro.setOption('tooltipPosition', 'bottom');
					intro.setOption('showStepNumbers', false);
					intro.setOption('showButtons', false);
					intro.start();
					intro.goToStep(5);
					$("#tobeginfilediv").bind('click',function(){
						if(intro != null){
							intro.exit();
							intro = null;
							intro = introJs();
							intro.setOption('tooltipPosition', 'bottom');
							intro.setOption('showStepNumbers', false);
							intro.setOption('showButtons', false);
							intro.start();
							intro.goToStep(6);
							$("._start_custom").removeAttr("disabled");
						}
						$("#tobeginfilediv").unbind('click');
					});
					$(".addfile").removeAttr("disabled");
				}
				$("#toaddfilediv").unbind('click');
			});
		}
	});
// 	$("#toaddfilediv").bind('click',function(){
// 		if(intro != null){
// 			intro.exit();
// 			intro = null;
// 			intro = introJs();
// 			intro.setOption('tooltipPosition', 'bottom');
// 			intro.setOption('showStepNumbers', false);
// 			intro.setOption('showButtons', false);
// 			intro.start();
// 			intro.goToStep(6);
// 			$("#toaddfilediv").unbind('click');
// 		}
// 	});
// 	$(".toaddfilediv").bind("click",function(){
// 		if(intro != null){
// 			intro.exit();
// 			intro = null;
// 			intro = introJs();
// 			intro.setOption('tooltipPosition', 'auto');
// 			intro.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);
// 			intro.setOption('showStepNumbers', false);
// 			intro.setOption('showButtons', false);
// 			intro.start();
// 			intro.goToStep(6);
// 		}
// 	});
	//绑定选择文件事件
	uploader.bind('Browse', function() {
		$.get("checkAdminSessionTimeOut",function(response){
			if(sessionUserName!=response){//session超时则执行下两步
				//销毁uploader，间接取消选择文件弹窗
				uploader.destroy();
				//跳转登陆页面
				$("#_sessionTimeOut").html(response);
			}
			if(intro != null){
				intro.goToStep(5);
			}
		});
	});
	uploader.bind('StateChanged', function() {
		//文件队列开始上传
		if (uploader.state === plupload.STARTED) {
			//防止文件上传时意外关闭了浏览器、页签或者上传页面
			window.parent.isUploading = true;
			//防止session超时
			refresh = setInterval("refreshSession()",600000);
		}else if(uploader.state === plupload.STOPPED){//文件队列全部上传结束
			window.parent.isUploading = false;
			clearInterval(refresh);
		}
	});
	//自定义上传按钮，先进行session超时校验，再决定是执行上传还是跳转登陆页面操作
	$("._start_custom").click(function(){
		$.get("checkAdminSessionTimeOut",function(response){
			if(sessionUserName==response){
				uploader.start();
			}else{
				uploader.destroy();
				$("#_sessionTimeOut").html(response);
			}
		});
	});
});
function refreshSession(){
    //为了防止上传过程中session超时而随便请求的一个方法
    $.get("getAllDataType");
}
</script>
