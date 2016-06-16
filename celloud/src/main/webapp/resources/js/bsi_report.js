$.dataReport = {
    navList: function(){
      $.post("report/bsi/batchReportList",{"batch":$("#data-batch").html(),"appId": $("#appid-hide").val(),"dataKey":$("#datakey-hide").val()},function(response){
        $.dataReport.loadNavList(response);
      });
    },
    paginNavList: function(currentPage){
      $.report.detail.option.batchPage = currentPage;
      $.post("report/bsi/batchReportList",{"batch":$("#data-batch").html(),"appId": $("#appid-hide").val(),"dataKey":$("#datakey-hide").val(),"page":currentPage},function(response){
        $.dataReport.loadNavList(response);
      });
    },
    loadNavList: function(response){
      $("#report-pagination").html(response);
      $("#reportbatch"+$("#datakey-hide").val()).addClass("active");
      $("#pagination-data-report").on("click","a",function(e){
        var id = $(this).attr("id");
        var currentPage = parseInt($("#batch-current-page-hide").val());
        var totalPage = parseInt($("#batch-total-page-hide").val());
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
        $.dataReport.paginNavList(page);
      });
    }
}
$(function () {
  if($.report.options.reportType == 1){
    $("#analy-tab").tab("show");
  }
  $("#patient-tab").click(function (e) {
    e.preventDefault()
    $(this).tab('show');
    $("#print-patient-a").removeClass("hide");
    $("#print-analy-a").addClass("hide");
    $.report.options.reportType = 0;
  });
  $("#analy-tab").click(function (e) {
    e.preventDefault()
    $(this).tab('show');
    $("#print-analy-a").removeClass("hide");
    $("#print-patient-a").addClass("hide");
    $.report.options.reportType = 1;
  });
  $.dataReport.paginNavList($.report.detail.option.batchPage);
});