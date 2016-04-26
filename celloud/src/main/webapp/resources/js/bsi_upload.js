var fileUpload=(function(fileUpload){
  var self=fileUpload||{};
//  var session_user = $("#fileDataSessionUserIdHidden").val();
//  //检验session是否超时
//  if(!session_user){
//    window.top.location = "login";
//  }
  self.refreshSession=function(){
      //为了防止上传过程中session超时而随便请求的一个方法
      $.get("upload/checkAdminSessionTimeOut");
  }
  $(function() {
    $("#upload-content").html("<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>");
    $("#upload-content").pluploadQueue({
      url : "../upload/uploadManyFile",
      chunk_size : "1mb",
      file_data_name : "file",
      filters : {
        max_file_size : "3gb",
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
      multiple_queues : true
    });
    var uploader = $("#upload-content").pluploadQueue();
    //绑定选择文件事件
    uploader.bind("Browse", function() {
      $.get("upload/checkAdminSessionTimeOut",function(response){
        if(response){//session超时则执行下两步
          $("#plupload-content").removeClass("hide");
        }else{
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
        uploader.start();
      }
      $("[data-toggle='tooltip']").tooltip();
    });
    uploader.bind("UploadComplete",function(uploader,files){
      $("#plupload-content").addClass("hide");
      $("#batch-info").val("");
      uploader.splice();
      $("#upload-modal").modal("hide");
    });
    window.onbeforeunload=function(){
      var qp=uploader.total;
      var percent=qp.percent;
      if(qp.size>0&&percent<100&&percent>0){
        return "数据正在上传，您确定要关闭页面吗?"
      }
    }
  });

})(fileUpload);

