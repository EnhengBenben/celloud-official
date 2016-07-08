$(function () {
  $(".common-sidebar").on("click","a",function(){
    $base.itemBtnToggleActive($(this));
  });
  $("#to-sample-a").on("click",function(){
    $("#container").load("pages/rocky/sample_main.jsp",function(){
      $("#sample-list-tbody").load("pages/rocky/sample_list.jsp");
    });
  });
  $("#to-upload-a").on("click",function(){
    $("#container").load("pages/rocky/upload.jsp",function(){
      $("#upload-next").on("click",function(){
        $upload.next();
      })
    });
  });
  $("#to-report-a").on("click",function(){
    $("#container").load("pages/rocky/sample_main.jsp");
  });
  $("#to-data-a").on("click",function(){
    $("#container").load("pages/rocky/sample_main.jsp");
  });
  $("#to-sample-a").trigger("click");
});

