<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<title>数据管理</title>
<link href="<%=request.getContextPath() %>/plugins/plupload-2.1.2/jquery.plupload.queue/css/jquery.plupload.queue.css?version=1.1" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath() %>/css/upload.css" rel="stylesheet" type="text/css">
<style type="text/css">
</style>
</head>
<body id="fileDataBody">
<input type="hidden" id="fileDataSessionUserIdHidden" value='<s:property value="#session.userId"/>'>
<div id="fileUploadDiv" class="wholeheight">
    
</div>
<div style="display: none;" id="_sessionTimeOut">
</div>
<script src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap-tab.js"></script>
<script src="<%=request.getContextPath() %>/plugins/bootstrap-modal.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/jquery.plupload.queue/jquery.plupload.queue.js?version=1.2"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/plupload.dev.js?version=1.6"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/i18n/zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/baidu.js"></script>
<!-- spin:loading效果 begin-->
<script src="<%=request.getContextPath()%>/plugins/spin.min.js" type="text/javascript"></script>
<!-- spin:loading效果 end-->
<script type="text/javascript" src="<%=request.getContextPath() %>/js/upload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/spark-md5.js"></script>
<script type="text/javascript">
// window.onload = function() {
// 	if (!window.applicationCache) {
// 		document.write("<div style='text-align: center;color: red;'>很抱歉，您的浏览器版本过低，无法上传文件。请使用最新的Google Chrome浏览器上传数据。</div>");
// 	}
// };
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
		url : '../../manyAjaxUploadFile_uploadManyFile',
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
				{title : "gz", extensions : "gz"}
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
// 	uploader.bind('BeforeUpload', function() {
// 		$("._uploadAlert").html("数据正准备上传...");
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
</body>
</html>