$.dataReport = {
    navList: function(){
      $.post("data/taskBatchList",{"batch":$("#data-batch").html(),"appId": $("#appid-hide").val(),"dataKey":$("#datakey-hide").val()},function(response){
        $.dataReport.loadNavList(response);
      });
    },
    paginNavList: function(){
      $.get("data/taskBatchList",{"batch":$("#data-batch").html(),"appId": $("#appid-hide").val(),"dataKey":$("#datakey-hide").val(),"page":currentPage},function(response){
        $.dataReport.loadNavList(response);
      });
    },
    loadNavList: function(response){
      $("#report-pagination").html(response);
      $("#pagination-data-report").on("click","a",function(e){
        var id = $(this).attr("id");
        var currentPage = parseInt($("#current-page-hide").val());
        var totalPage = parseInt($("#total-page-hide").val());
        var page;
        if(id == undefined){
          page = $(this).html();
        }else if(id.indexOf("prev")>=0){
          if(currentPage == 1){
            page = 1;
          }else{
            page = currentPage-1;
          }
        }else if(id.indexOf("next")>=0){
          page = currentPage+1;
          if(currentPage == totalPage){
            page = currentPage;
          }else{
            page = currentPage+1;
          }
        }else if(id.indexOf("first")>=0){
          page = 1;
        }else if(id.indexOf("last")>=0){
          page = totalPage;
        }
        $.dataReport.paginNavList();
      });
    }
}
$(function () {
  $("#patient-tab").click(function (e) {
    e.preventDefault()
    $(this).tab('show');
  });
  $("#analy-tab").click(function (e) {
    e.preventDefault()
    $(this).tab('show');
  });
  $.dataReport.navList();
});