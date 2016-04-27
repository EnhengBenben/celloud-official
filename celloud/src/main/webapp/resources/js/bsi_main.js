/**
 * 血流用户主页事件
 */
$(function () {
  $("#to-upload-a").on("click",function(){
    $("#upload-modal").modal("show");
  });
  $("#to-report-a").on("click",function(){
    $.report.find.all();
  });
});


$.report = {};
$.report.options = {
    condition: null,
    sort: 0,
    sortDate: "desc",
    sortPeriod: "asc",
};
$.report.find = {
  all: function(){
    $.get("data/taskAllList",function(response){
      $.report.loadlist(response);
    });
  },
  condition: function(){
    var options = $.report.options;
    $.get("data/taskList",{"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod},function(response){
      $.report.loadlist(response);
    });
  },
  pagination: function(currentPage){
    var options = $.report.options;
    $.get("data/taskList",{"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod},function(response){
      $.report.loadlist(response);
    });
  }
};
$.report.loadlist = function(response){
  $("#container").html(response);
  $("#data-list-tbody").find("td[name='data-name-td']").each(function(){
    var _data = $(this).attr("title");
    if(_data.length>40){
      var newData = utils.splitDataByInfo(_data, "\r\n" ,40);
      $(this).attr("title",newData);
    }
  });
  $.base.sortIcon($.report.options.sortPeriod,$.report.options.sortDate);
  $("#pagination-task").on("click","a",function(e){
    var id = $(this).attr("id");
    var currentPage = parseInt($("#data-current-page-hide").val());
    var page = id == null ? 
        $(this).html() : (id == "prev-page-data" ? currentPage-1 : currentPage+1);
    $.report.find.pagination(page);
  });
  $("#sort-period").on("click",function(e){
    $.report.options.sort = 1;
    $.report.options.sortPeriod = $.report.options.sortPeriod=="desc"?"asc":"desc";
    $.report.find.condition();
  });
  $("#sort-date").on("click",function(e){
    $.report.options.sort = 0;
    $.report.options.sortDate = $.report.options.sortDate=="desc"?"asc":"desc";
    $.report.find.condition();
  });
}
$.report.detail = {
    patient: function(dataKey,projectId,appId){
      $.get("report/getBSIPatientReport",{"dataKey":dataKey,"projectId":projectId,"appId":appId},function(response){
        $("#container").html(response);
      });
    },
    analy: function(dataKey,projectId,appId){
      $.get("report/getBSIAnalyReport",{"dataKey":dataKey,"projectId":projectId,"appId":appId},function(response){
        $("#myTabContent").html(response);
      });
    },
}

$.base = {
  sortIcon : function(sortType1,sortType2){
    if(sortType1=="asc"){
      $("#sort-period-icon").removeClass("fa-sort-desc").addClass("fa-sort-asc");
    }else if(sortType1=="desc"){
      $("#sort-period-icon").removeClass("fa-sort-asc").addClass("fa-sort-desc");
    }
    if(sortType2=="asc"){
      $("#sort-date-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
    }else if(sortType2=="desc"){
      $("#sort-date-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
    }
  }
};