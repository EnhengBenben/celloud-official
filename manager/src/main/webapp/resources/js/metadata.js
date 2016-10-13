/**
 * 元数据js
 */
$.metadata = {};
$.ajaxSetup ({
  cache: false //关闭AJAX相应的缓存
});
$(function(){
  init_metadata();
  $("#to-bsi").click(function(){
    $.metadata.options.appId = 118;
    $.metadata.options.flag = 1;
    $.metadata.options.icon = 'to-bsi';
    $.metadata.metadataList();
  });
  $("#to-rocky").click(function(){
    $.metadata.options.appId = 123;
    $.metadata.options.flag = 1;
    $.metadata.options.icon = 'to-rocky';
    $.metadata.metadataList();
  });
});
function init_metadata(){
  $.metadata = {
      options:{
        appId:0,
        flag:1,
        icon:'to-bsi'
      },
      toDelete:function(id){
        if(confirm("确定要删除此项么？")){
          $.get("metadata/deleteMetadata",{id:id},function(responseText){
            $.metadata.metadataList();
          });
        }
      },
      toEdit:function(id,name,seq){
        if($.metadata.options.flag==0){
          alert("请选择种类");
          return;
        }
        if(id==null){
          $("#meta_title").html("新增");
        }else{
          $("#meta_title").html("编辑");
        }
        $("#id").val(id);
        $("#name").val(name);
        $("#seq").val(seq);
        $("#appId").val($.metadata.options.appId);
        $("#flag").val($.metadata.options.flag);
    	  $("#metadata-modal").modal("show");
      },
      save:function(){
        $.get("metadata/updateMetadata",$("#metadataForm").serialize(),function(responseText){
          $.metadata.metadataList();
        });
      },
      change:function(){
        $.metadata.options.flag = $("#metadataFlag").val();
        $.metadata.metadataList();
      },
      moveUp:function(id){
        $.get("metadata/moveUp",{id:id},function(responseText){
          if(responseText!=2){
            alert("上移失败！");
          }else{
            $.metadata.metadataList();
          }
        });
      },
      moveDown:function(id){
        $.get("metadata/moveDown",{id:id},function(responseText){
          if(responseText!=2){
            alert("下移失败！");
          }else{
            $.metadata.metadataList();
          }
        });
      },
      metadataList: function(){
        var appId = $.metadata.options.appId;
        var flag = $.metadata.options.flag;
        var icon = $.metadata.options.icon;
        $.get("metadata/toShowMetadata",{appId:appId,flag:flag},function(responseText){
          menu(icon,responseText);
          $("#metadataFlag").val($.metadata.options.flag)
        });
      }
  };
}
