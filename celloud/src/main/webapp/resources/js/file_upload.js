var datafileUpload=(function(datafileUpload){
	var self=datafileUpload||{};
	var session_user = $("#fileDataSessionUserIdHidden").val();
	//检验session是否超时
	if(!session_user){
		window.top.location = "login";
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
	self.refreshSession=function(){
	    //为了防止上传过程中session超时而随便请求的一个方法
	    $.get("uploadFile/checkAdminSessionTimeOut");
	}
	$(function() {
		$("#fileUploadDiv").html("<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>");
		$("#fileUploadDiv").pluploadQueue({
			url : "../uploadFile/uploadManyFile",
			chunk_size : "1mb",
			file_data_name : "file",
			filters : {
				max_file_size : "10gb",
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
			flash_swf_url : "../plugins/plupload-2.1.2/Moxie.swf",
			headers: {
	            Authorization: ""
	        },
			max_retries : 5,
	 		unique_names : true,
	 		multiple_queues : true,
		});
		var uploader = $("#fileUploadDiv").pluploadQueue();
		uploader.bind("Init", function(){
			if(intro != null){
			  uploader.disableBrowse(true);
				intro.exit();
				intro = null;
				intro = introJs();
				intro.setOption("tooltipPosition", "auto");
				intro.setOption("positionPrecedence", ["left", "right", "bottom", "top"]);
				intro.setOption("showStepNumbers", false);
				intro.setOption("showButtons", false);
				intro.start();
				intro.goToStep(4);
				$("#toaddfilediv").one("click",function(){
					intro.setOption("tooltipPosition", "bottom");
					intro.goToStep(5);
					uploader.disableBrowse(false);
				});
				$("#tobeginfilediv").one("click",function(){
          intro.goToStep(6);
        });
			}
		});
		//绑定选择文件事件
		uploader.bind("Browse", function() {
			$.get("uploadFile/checkAdminSessionTimeOut",function(response){
				if(!response){//session超时则执行下两步
					//销毁uploader，间接取消选择文件弹窗
					uploader.destroy();
				}
			});
		});
		uploader.bind("StateChanged", function() {
			//文件队列开始上传
			if (uploader.state === plupload.STARTED) {
				//防止文件上传时意外关闭了浏览器、页签或者上传页面
				window.parent.isUploading = true;
				//防止session超时
				refresh = setInterval("self.refreshSession()",600000);
			}else if(uploader.state === plupload.STOPPED){//文件队列全部上传结束
				window.parent.isUploading = false;
				clearInterval(refresh);
			}
		});
		
		uploader.bind('FilesAdded',function(uploader,files){
			for(var i in files){//文件名称过长时，tooltip显示全部
				$("#"+files[i].id+" div.plupload_file_name span").attr("data-toggle","tooltip").attr("data-placement","bottom").attr("title",files[i].name);
			}
			$("[data-toggle='tooltip']").tooltip();
	    });
		//自定义上传按钮，先进行session超时校验，再决定是执行上传还是跳转登陆页面操作
		$("._start_custom").click(function(){
			$.get("uploadFile/checkAdminSessionTimeOut",function(response){
				if(response){
					uploader.start();
				}else{
					uploader.destroy();
				}
			});
		});
		window.onbeforeunload=function(){
			var qp=uploader.total;
			var percent=qp.percent;
			if(qp.size>0&&percent<100&&percent>0){
				return "数据正在上传，您确定要关闭页面吗?"
			}
		}
	});
})(datafileUpload);