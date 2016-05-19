var fileUpload=(function(fileUpload){
  var self=fileUpload||{};
  self.refreshSession=function(){
      //为了防止上传过程中session超时而随便请求的一个方法
      $.get("upload/checkAdminSessionTimeOut");
  }
  $(function() {
    var uploader = new plupload.Uploader({
      runtimes : 'html5,flash,silverlight,html4',
      browse_button : ['plupload-content','upload-more'],
      url : "../upload/uploadManyFile",
      // Maximum file size
      chunk_size : '1mb',
      // Specify what files to browse for
      filters : {
        max_file_size : '3gb',
        prevent_duplicates : true, //不允许选取重复文件
        mime_types : [
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
      max_retries : 5,
      multiple_queues : true,
      // Flash settings
      flash_swf_url : '//cdn.bootcss.com/plupload/2.1.8/Moxie.swf'
    });
    uploader.init();
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
    uploader.bind("UploadProgress", function(uploader, file) {
      $("#uploading-" + file.id +" .plupload-file-status").html(file.percent+"%");
      $("#uploading-" + file.id + " .plupload-file-surplus").html(utils.formatDate((file.size-file.loaded)/uploader.total.bytesPerSec));
      waveLoading.setProgress(uploader.total.percent);
      handleStatus(uploader.total.percent);
    });
    function getSize(fileSize){
      var unit = "b";
      if(fileSize > 1000){
        fileSize= fileSize / 1000;
        unit = "kb";
      }
      if(fileSize > 1000){
        fileSize= fileSize / 1000;
        unit = "mb";
      }
      return fileSize.toFixed(2)+unit;
    }
    function getSpead(spead){
      var unit = "b/s";
      if(spead > 1000){
        spead = spead/1000;
        unit = "kb/s";
      }
      if(spead > 1000){
        spead = spead/1000;
        unit = "mb/s";
      }
      return spead.toFixed(2)+unit;
    }
    function handleStatus(file) {
      var actionClass;
      var iconClass;
      if (file.status == plupload.DONE) {
        actionClass = 'plupload_done';
        iconClass = 'fa fa-check-circle';
      }

      if (file.status == plupload.FAILED) {
        actionClass = 'plupload_failed';
        iconClass = 'fa fa-exclamation-triangle';
      }

      if (file.status == plupload.QUEUED || file.status == plupload.UPLOADING) {
        actionClass = 'plupload_delete';
        iconClass = 'fa fa-times-circle-o';
      }
      var icon = $('#uploading-' + file.id).attr('class', actionClass).find('i').attr('class', iconClass);
      if (file.hint) {
        icon.attr('title', file.hint);  
      }
    }
    uploader.bind("FilesAdded", function(uploader, files) {
      $.get("upload/checkAdminSessionTimeOut",function(response){
        if(response){//session超时则执行下两步
          
        }else{
          //销毁uploader，间接取消选择文件弹窗
          uploader.destroy();
        }
      });
      $.each(files, function(index, item) {
        var $fileDom = $('<li id="' + item.id + '">');
        $fileDom.append($('<div class="plupload-file-name"><span>' + item.name + '</span></div>'));
        $fileDom.append($('<div class="plupload-file-size">'+getSize(item.size)+'</div>'));
        $fileDom.append($('<div class="plupload-file-action"><a href="#" style="display: block;"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a></div>'));
        $fileDom.append($('<div class="plupload-clearer"></div>&nbsp;</li>'));
        $("#upload-filelist").append($fileDom);
        var $fileDom_uploading = $('<li id="uploading-' + item.id + '">');
        $fileDom_uploading.append($('<div class="plupload-file-name"><span title="' + item.name + '">' + item.name + '</span></div>'));
        $fileDom_uploading.append($('<div class="plupload-file-size">'+getSize(item.size)+'</div>'));
        $fileDom_uploading.append($('<div class="plupload-file-surplus">_</div>'));
        $fileDom_uploading.append($('<div class="plupload-file-status">_</div>'));
        $fileDom_uploading.append($('<div class="plupload-file-action"><a href="#" style="display: block;"><i class="fa fa-times-circle-o" aria-hidden="true"></i></a></div>'));
        $fileDom_uploading.append($('<div class="plupload-clearer"></div>&nbsp;</li>'));
        $("#uploading-filelist").append($fileDom_uploading);
        handleStatus(item);
        $('#' + item.id + '.plupload_delete a').click(function(e) {
          $('#' + item.id).remove();
          uploader.removeFile(item);
          e.preventDefault();
          utils.stopBubble(e);
        });
        $('#uploading-' + item.id + '.plupload_delete a').click(function(e) {
          $('#uploading-' + item.id).remove();
          uploader.removeFile(item);
          e.preventDefault();
          utils.stopBubble(e);
        });
      }); 
    });
    uploader.bind("FileUploaded", function(uploader, file, response) {
      var res = response.response;
      if(res != "1"){
        $.get("data/run",JSON.parse(res),function(result){
          $.report.find.condition();
        });
      }
      handleStatus(file);
    });
    uploader.bind("UploadComplete",function(uploader,files){
      $(".step-one-content").removeClass("hide");
      $(".step-two-content").addClass("hide");
      $(".step-three-content").addClass("hide");
      $("#one-to-two").removeClass("active");
      $("#two-to-three").removeClass("active");
      $(".step-two").removeClass("active");
      $(".step-three").removeClass("active");
      $("#batch-info").val("")
      uploader.splice();
      $("#upload-modal").modal("hide");
    });
    uploader.bind("BeforeUpload", function(uploader, file) {
       uploader.setOption("multipart_params",{'originalName': file.name,'tagId':$("#tag-info").val(),'batch': $("#batch-info").val(),'needSplit':$("#need-split:checked").val()});
    });
    uploader.bind("Error", function(uploader, error) {
       if(error.code=='-602'){
         alert("当前队列已存在文件【"+error.file.name+"】，请勿重复添加！");
       }
    });
    $("#begin-upload").on("click",function(){
      $("#upload-filelist").html("");
      uploader.start();
      $(".step-three-content").removeClass("hide");
      $(".step-one-content").addClass("hide");
      $(".step-two-content").addClass("hide");
      $("#two-to-three").addClass("active");
      $(".step-three").addClass("active");
      $("#tags-review").html($("#batch-info").val());
      waveLoading.init({
          target: document.querySelector('#upload-progress'),
          color: 'rgba(40, 230, 200, 0.6)',
          showText: false
      });
      waveLoading.draw();
      waveLoading.resetProgress(0);
      
    });
  });

})(fileUpload);

