/**
 * 血流用户主页事件
 */
$(function () {
  $.report.find.all();
  $("#to-upload-a").on("click",function(){
    if($(".plupload_filelist li").hasClass("plupload_droptext")){
      $("#batch-info").val("");
      $("#batch-div").removeClass("hide");
      $("#upload-content").addClass("upload-step-one");
    }
    
    $("#upload-modal").modal("show");
    if(waveLoading.getProgress()>=100)
      waveLoading.resetProgress();
  });
  
  $("#to-report-a").on("click",function(){
    $.report.find.all();
    $(this).addClass("active");
    $("#to-upload-a").removeClass("active");
    $("#to-data-a").removeClass("active");
  });
  $("#to-data-a").on("click",function(){
    $.data_.find.condition();
    $(this).addClass("active");
    $("#to-upload-a").removeClass("active");
    $("#to-report-a").removeClass("active");
  });
  $("#next-step").on("click",function(){
    $(".step-one-content").addClass("hide");
    $(".step-two-content").removeClass("hide");
    $("#one-to-two").addClass("active");
    $(".step-two").addClass("active");
  });
  
});
$.base = {
  sortIcon : function(sortDate,sortBatch,sortName,sortPeriod){
    if(sortDate=="asc"){
      $("#sort-date-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
    }else if(sortDate=="desc"){
      $("#sort-date-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
    }
    if(sortBatch=="asc"){
      $("#sort-batch-icon").removeClass("fa-sort-desc").addClass("fa-sort-asc");
    }else if(sortBatch=="desc"){
      $("#sort-batch-icon").removeClass("fa-sort-asc").addClass("fa-sort-desc");
    }
    if(sortName=="asc"){
      $("#sort-name-icon").removeClass("fa-sort-desc").addClass("fa-sort-asc");
    }else if(sortName=="desc"){
      $("#sort-name-icon").removeClass("fa-sort-asc").addClass("fa-sort-desc");
    }
    if(sortPeriod=="asc"){
      $("#sort-period-icon").removeClass("fa-sort-desc").addClass("fa-sort-asc");
    }else if(sortPeriod=="desc"){
      $("#sort-period-icon").removeClass("fa-sort-asc").addClass("fa-sort-desc");
    }
  }
};

$.report = {};
$.report.options = {
    condition: null,
    sort: 0,
    sortBatch: "asc",
    sortName: "asc",
    sortPeriod: "asc",
    sortDate: "desc",
    pageSize: $("#page-size-sel").val()
};
$.report.run = function(dataIds,appIds){
  $.get("data/run",{"dataIds":dataIds,"appIds":appIds},function(result){
    $.report.find.condition();
  });
};
$.report.find = {
  all: function(){
    $.get("data/taskAllList",function(response){
      $.report.loadlist(response);
    });
  },
  condition: function(){
    var options = $.report.options;
    $.get("data/taskList",{"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
      $.report.loadlist(response);
    });
  },
  pagination: function(currentPage){
    var options = $.report.options;
    $.get("data/taskList",{"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
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
  $.base.sortIcon($.report.options.sortDate,$.report.options.sortBatch,$.report.options.sortName,$.report.options.sortPeriod);
  $("#pagination-task").on("click","a",function(e){
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
    $.report.find.pagination(page);
  });
  $("#sort-date").on("click",function(e){
    $.report.options.sort = 0;
    $.report.options.sortDate = $.report.options.sortDate=="desc"?"asc":"desc";
    $.report.find.condition();
  });
  $("#sort-batch").on("click",function(e){
    $.report.options.sort = 1;
    $.report.options.sortBatch = $.report.options.sortBatch=="desc"?"asc":"desc";
    $.report.find.condition();
  });
  $("#sort-name").on("click",function(e){
    $.report.options.sort = 2;
    $.report.options.sortName = $.report.options.sortName=="desc"?"asc":"desc";
    $.report.find.condition();
  });
  $("#sort-period").on("click",function(e){
    $.report.options.sort = 3;
    $.report.options.sortPeriod = $.report.options.sortPeriod=="desc"?"asc":"desc";
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
    }
}
$.report.period = {
    error: function(dataName){
      $("#run-error-data").html(dataName);
      $("#running-error-modal").modal("show");
    }
}

$.data_ = {
    options: {
        condition: null,
        sort: 0,
        sortBatch: "asc",
        sortName: "asc",
        sortDate: "desc",
        pageSize: $("#page-size-sel").val()
    },
    find : {
        all: function(){
          $.get("data/bsiDataAllList",function(response){
            $.data_.loadlist(response);
          });
        },
        condition: function(){
          var options = $.data_.options;
          $.get("data/bsiDataList",{"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
            $.data_.loadlist(response);
          });
        },
        pagination: function(currentPage){
          var options = $.data_.options;
          $.get("data/bsiDataList",{"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
            $.data_.loadlist(response);
          });
        }
      }
};
$.data_.loadlist = function(response){
  $("#container").html(response);
  $("#data-list-tbody").find("td[name='data-name-td']").each(function(){
    var _data = $(this).attr("title");
    if(_data.length>40){
      var newData = utils.splitDataByInfo(_data, "\r\n" ,40);
      $(this).attr("title",newData);
    }
  });
  var options = $.data_.options;
  $.base.sortIcon(options.sortDate,options.sortBatch,options.sortName,null);
  $("#pagination-data").on("click","a",function(e){
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
    $.data_.find.pagination(page);
  });
  $("#sort-date").on("click",function(e){
    $.data_.options.sort = 0;
    $.data_.options.sortDate = $.data_.options.sortDate=="desc"?"asc":"desc";
    $.data_.find.condition();
  });
  $("#sort-batch").on("click",function(e){
    $.data_.options.sort = 1;
    $.data_.options.sortBatch = $.data_.options.sortBatch=="desc"?"asc":"desc";
    $.data_.find.condition();
  });
  $("#sort-name").on("click",function(e){
    $.data_.options.sort = 2;
    $.data_.options.sortName = $.data_.options.sortName=="desc"?"asc":"desc";
    $.data_.find.condition();
  });
}
