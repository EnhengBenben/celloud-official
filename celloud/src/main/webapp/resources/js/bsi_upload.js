var fileUpload=(function(fileUpload){
  var self=fileUpload||{};
  self.refreshSession=function(){
      //为了防止上传过程中session超时而随便请求的一个方法
      $.get("upload/checkAdminSessionTimeOut");
  }
  $(function() {
    var uploader = new plupload.Uploader({
      runtimes : 'html5,flash,silverlight,html4',
      browse_button : 'addfile_browse',
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
      $("#" + file.id +" .plupload_file_status").html(file.percent+"%");
      $("#" + file.id + " .plupload_file_surplus").html(utils.formatDate((file.size-file.loaded)/uploader.total.bytesPerSec));
      handleStatus(file);
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
        iconClass = 'fa fa-times';
      }
      var icon = $('#' + file.id).attr('class', actionClass).find('i').attr('class', iconClass);
      if (file.hint) {
        icon.attr('title', file.hint);  
      }
    }
    uploader.bind("FilesAdded", function(uploader, files) {
      $.get("upload/checkAdminSessionTimeOut",function(response){
        if(response){//session超时则执行下两步
          $("#batch-div").addClass("hide");
          $("#upload-content").removeClass("upload-step-one");
        }else{
          //销毁uploader，间接取消选择文件弹窗
          uploader.destroy();
        }
      });
      $.each(files, function(index, item) {
        var $fileDom = $('<li id="' + item.id + '">');
        $fileDom.append($('<div class="plupload_file_name"><span>' + item.name + '</span></div>'));
        $fileDom.append($('<div class="plupload_file_status">' + item.percent + '%</div>'));
        $fileDom.append($('<div class="plupload_file_size">'+getSize(item.size)+'</div>'));
        $fileDom.append($('<div class="plupload_file_surplus">-</div>'));
        $fileDom.append($('<div class="plupload_file_action"><a href="#" style="display: block;"><i class="fa fa-times" aria-hidden="true"></i></a></div>'));
        $fileDom.append($('<div class="plupload_clearer"></div>&nbsp;</li>'));
        $("#upload_filelist").append($fileDom);
        handleStatus(item);
        $('#' + item.id + '.plupload_delete a').click(function(e) {
          $('#' + item.id).remove();
          uploader.removeFile(item);
          e.preventDefault();
        });
        uploader.start();
      }); 
    });
    uploader.bind("FileUploaded", function(uploader, file, response) {
      var res = response.response;
      alert(response.response);
      if(res != "1"){
        $.get("data/run",JSON.parse(res),function(result){
          $.report.find.condition();
        });
      }
      handleStatus(file);
    });
    uploader.bind("BeforeUpload", function(uploader, file) {
       uploader.setOption("multipart_params",{'originalName': file.name,'tagId':$("#tag-info").val(),'batch': $("#batch-info").val(),'needSplit':$("#need-split:checked").val()});
    });
    uploader.bind("Error", function(uploader, error) {
       if(error.code=='-602'){
         alert("当前队列已存在文件【"+error.file.name+"】，请勿重复添加！");
       }
    });
  });

})(fileUpload);

