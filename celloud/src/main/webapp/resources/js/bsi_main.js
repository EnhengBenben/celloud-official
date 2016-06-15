/**
 * 血流用户主页事件
 */
var uploadPercent = 0;
$(function () {
  $.report.find.main(0);
  $("#to-upload-a").on("click",function(){
    if($(".plupload_filelist li").hasClass("plupload_droptext")){
      $("#batch-info").val("");
      $("#batch-div").removeClass("hide");
      $("#upload-content").addClass("upload-step-one");
    }
    $("#upload-modal").modal("show");
    if(uploadPercent >= 100){
      waveLoading.setProgress(0);
      document.querySelector("#upload-progress").height = document.querySelector("#upload-progress").height;
    }
  });
  
  $("#to-report-a").on("click",function(){
    $.report.find.main(0);
    $(this).addClass("active");
    $("#to-upload-a").removeClass("active");
    $("#to-data-a").removeClass("active");
  });
  $("body").on("click",'[data-click="report-list"]',function(){
    $.report.find.main(1);
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
    $.upload.uploadTextType();
  });
  $("#batch-info").on("keyup",function(e){
    e = e || window.event;
    if (e.keyCode == "13") {//keyCode=13是回车键
      $("#next-step").click();
    }
  });
  $("#condition-input").on("keyup",function(e){
    e = e || window.event;
    if (e.keyCode == "13") {//keyCode=13是回车键
      $("#condition-find").click();
    }
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
$.upload = {
    uploadTextType : function(){
      if($("#uploading-filelist").children().length> 2){
        $(".upload-text").addClass("hide");
      }else{
        $(".upload-text").removeClass("hide");
      }
    }
}
$.report = {};
$.report.options = {
    condition: null,
    sort: 0,
    sortBatch: "asc",
    sortName: "asc",
    sortPeriod: "asc",
    sortDate: "desc",
    pageSize: $("#page-size-sel").val(),
    reportType: 0,  //0:患者报告  1：分析报告
    batch: null,
    period: null,
    beginDate: null,
    endDate: null,
    distributed: null //0:是   1： 否
};
$.report.reRun = function(dataKey,appId,projectId){
  $.get("data/reRun",{"dataKey":dataKey,"appId":appId,"projectId":projectId},function(result){
    $.report.find.condition();
  });
};
$.report.find = {
  main: function(type){
    $.get("report/bsi/reportMain",function(response){
      $("#container").html(response);
      if(type==0){
        $.report.find.all();
      }else{
        $.report.find.pagination();
      }
      $("#condition-find").unbind("click");
      $("#condition-find").on("click",function(){
        $.report.options.condition = $("#condition-input").val();
        $.report.find.condition();
      });
      $("body").on("click","[data-click='report-batch-search']",function(){
        if(!$("#batch-lists").hasClass("show-more"))
          $.report.options.batch = $(this).text();
          $.report.find.condition();
      });
      $("body").on("click","[data-click='report-period-search']",function(){
        $.report.options.period = $(this).find("input").val();
        $.report.find.condition();
      });
      $("body").on("click","[data-click='report-date-search']",function(){
        $.report.options.beginDate = $("#report-begindate-search").val();
        $.report.options.endDate = $("#report-enddate-search").val();
        $.report.find.condition();
      });
      $("body").on("click","[data-click='report-distributed-search']",function(){
        if($(this).find(".sl-judge-no").hasClass("hide")){
          $(this).find(".sl-judge-no").removeClass("hide");
          $(this).find(".sl-judge-yes").addClass("hide");
          $.report.options.distributed = 1;
        }else{
          $(this).find(".sl-judge-yes").removeClass("hide");
          $(this).find(".sl-judge-no").addClass("hide");
          $.report.options.distributed = 0;
        }
        $.report.find.condition();
      });
      $("#batch-more").on("click",function(){
        if($("#batch-lists").hasClass("show-more")){
          $("#batch-lists").removeClass("show-more");
          $("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
        }else{
          $("#batch-lists").addClass("show-more");
          $("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
        }
      });
      $("body").on("click","[data-click='report-select-more']",function(){
        $(".selector-line").removeClass("select-more");
        $(".selector-line").find(".checkbox").addClass("hide");
        $(".selector-line").find(".multisl-btns").addClass("hide");
        var selectorline = $(this).parent().parent();
        $(selectorline).addClass("select-more");
        $(selectorline).find(".sl-val").addClass("show-more");
        $(selectorline).find(".checkbox").removeClass("hide");
        $(selectorline).find(".multisl-btns").removeClass("hide");
      });
      $("#batch-multiselect").on("click",function(){
        $("#batch-lists").addClass("show-more");
        $("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
      });
      $("#batch-lists .sl-val-content").on("click",function(){
        $(this).find(".checkbox").toggleClass("checkbox-un");
        $(this).find(".checkbox").toggleClass("checkbox-ed");
        if($(this).find(".checkbox").hasClass("checkbox-ed"))
          $.report.options.batch == null? $.report.options.batch = "'"+$(this).find("a").text()+"'" : $.report.options.batch += ",'"+$(this).find("a").text() + "'";
        if($.report.options.batch != null)
          $("#report-multibatch-search").removeClass("disabled");
          $("#report-multibatch-search").attr("disabled",false);
      });
      $("#report-multibatch-search").on("click",function(){
        $.report.find.condition();
      });
      $("#period-lists .sl-val-content").on("click",function(){
        $(this).find(".checkbox").toggleClass("checkbox-un");
        $(this).find(".checkbox").toggleClass("checkbox-ed");
        if($(this).find(".checkbox").hasClass("checkbox-ed"))
          $.report.options.period == null? $.report.options.period = $(this).find("input[type='hidden']").val() : $.report.options.period += ","+$(this).find("input[type='hidden']").val();
        if($.report.options.period != null)
          $("#report-multiperiod-search").removeClass("disabled");
          $("#report-multiperiod-search").attr("disabled",false);
      });
      $("#report-multiperiod-search").on("click",function(){
        var period = $.report.options.period;
        $.report.find.condition();
      });
      $("body").on("click","[data-click='reset-multiselect']",function(){
        var selectorline = $(this).parent().parent().parent();
        $(selectorline).removeClass("select-more");
        $(selectorline).find(".sl-val").removeClass("show-more");
        $(selectorline).find(".checkbox").addClass("hide").addClass("checkbox-un").removeClass("checkbox-ed");
        $(selectorline).find(".multisl-btns").addClass("hide");
        $(".multisl-btns").first().addClass("disabled");
        $(".multisl-btns").first().attr("disabled",true);
        $.report.options.batch = null;
        $.report.options.period = null;
      });
    });
  },
  all: function(){
    $.get("report/bsi/reportList",function(response){
      $.report.loadlist(response);
      $.report.options = {
          condition: null,
          sort: 0,
          sortBatch: "asc",
          sortName: "asc",
          sortPeriod: "asc",
          sortDate: "desc",
          pageSize: $("#page-size-sel").val(),
          reportType: 0,  //0:患者报告  1：分析报告
          batch: null,
          period: null,
          beginDate: null,
          endDate: null,
          distributed: null //0:是   1： 否
      };
    });
  },
  condition: function(){
    var options = $.report.options;
    $.get("report/bsi/searchReportList",{"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
      $.report.loadlist(response);
    });
  },
  pagination: function(currentPage){
    var options = $.report.options;
    $.get("report/bsi/searchReportList",{"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
      $.report.loadlist(response);
    });
  }
};
$.report.loadlist = function(response){
  $("#report-list").html(response);
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
  $("#page-size-sel").on("change",function(e){
    $.report.options.pageSize = $("#page-size-sel").val()
    $.report.find.condition();
  });
}
$.report.detail = {
    patient: function(dataKey,projectId,appId,reportIndex,currentPage){
      var options = $.report.options;
      $.post("report/getBSIPatientReport",{"reportIndex":reportIndex,"dataKey":dataKey,"projectId":projectId,"appId":appId,"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
        $("#container").html(response);
      });
    },
    analy: function(dataKey,projectId,appId){
      $.get("report/getBSIAnalyReport",{"dataKey":dataKey,"projectId":projectId,"appId":appId},function(response){
        $("#myTabContent").html(response);
      });
    },
    prev: function(currentPage){
      if(currentPage > 1){
        var options = $.report.options;
        $.post("report/getPrevOrNextBSIReport",{"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"isPrev":true,"page":currentPage-1,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
          if(response != null &&response !=""){
            $("#container").html(response);
          }
        });
      }
    },
    next: function(currentPage){
      var totalPage = $("#total-page-hide").val();
      currentPage = parseInt(currentPage);
      if(currentPage < totalPage){
        var options = $.report.options;
        $.post("report/getPrevOrNextBSIReport",{"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"isPrev":false,"page":currentPage+1,"totalPage":totalPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
          if(response != null &&response !=""){
            $("#container").html(response);
          }
        });
      }
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
          $.get("data/bsiDataList",{"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
            $.data_.loadlist(response);
          });
        },
        pagination: function(currentPage){
          var options = $.data_.options;
          $.get("data/bsiDataList",{"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
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
  $("#page-size-sel").on("change",function(e){
    $.data_.options.pageSize = $("#page-size-sel").val()
    $.data_.find.condition();
  });
}