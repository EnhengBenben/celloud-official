/**
 * 血流用户主页事件
 */
var uploadPercent = 0;
$(function () {
  $.report.find.main(0);
  $("#to-sample-a").on("click",function(){
    $.base.itemBtnToggleActive($(this));
    $.sample.main();
  });
  $("#to-upload-a").on("click",function(){
    $.base.itemBtnToggleActive($(this));
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
    $.base.itemBtnToggleActive($(this));
  });
  $("body").on("click",'[data-click="report-list"]',function(){
    $.report.find.main(1);
    $(this).addClass("active");
    $.base.itemBtnToggleActive($("#to-report-a"));
  });
  $("#to-data-a").on("click",function(){
    $.data_.find.condition();
    $.base.itemBtnToggleActive($(this));
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
  $("body").on("click",".table>tbody .checkbox",function(){
    $(this).toggleClass("checkbox-un");
    $(this).toggleClass("checkbox-ed");
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
  },
  itemBtnToggleActive: function(obj){
    $("#common-menu .item-btn").removeClass("active");
    $(obj).addClass("active");
  }
};

$.sample = {
    main: function(){
      $("#container").load("pages/bsi/sample_main.jsp",function(){
        $.sample.sampleList();
        $("#sample-add-a").on("click",function(){
          $.sample.addSample();
        });
        $("#sample-input").on("keyup",function(e){
          e = e || window.event;
          if (e.keyCode == "13") {//keyCode=13是回车键
            $("#sample-add-a").click();
          }
        });
        $("#close-s-error").on("click",function(){
          $.sample.errorTips.hide();
        });
        $("#sample-commit-a").on("click",function(){
          $.sample.commitSamples();
        });
        $("#sample-reset").on("click",function(){
          $.sample.deleteList();
        });
        $("body").on("click","[data-click='del-sample']",function(){
          $.sample.deleteOne($(this).data("id"));
        });
      });
    },
    sampleList: function(){
      $.get("sample/bsi/sampleList",{},function(response){
        $("#sample-list-tbody").html(response);
      });
    },
    addSample: function(){
      var sampleName = $("#sample-input").val();
      if(sampleName.trim() != ""){
        $.get("sample/bsi/addSample",{"sampleName": sampleName},function(result){
          if(result == 1){
            $.sample.sampleList();
            $("#sample-input").val("");
          }else if (result == 2){
            $.sample.errorTips.show();
          }
        });
      }
    },
    commitSamples: function(){
      var param = $("#sample-form").serialize();
      if(param.length>0){
        $.post("sample/bsi/commitSamples",param,function(result){
          $.sample.sampleList();
        });
      }
    },
    deleteOne: function(id){
      $.post("sample/bsi/deleteOne",{"sampleId":id},function(result){
        if(result>0)
          $.sample.sampleList();
      });
    },
    deleteList: function(){
      var param = $("#sample-form").serialize();
      if(param.length>0)
        $.post("sample/bsi/deleteList",param,function(result){
          if(result>0)
            $.sample.sampleList();
        });
    },
    errorTips: {
      show: function(){
        $("#sample-error").removeClass("hide");
      },
      hide: function(){
        $("#sample-error").addClass("hide");
      }
    } 
}
$.upload = {
    uploadTextType : function(){
      if($("#uploading-filelist").children().length> 2){
        $(".upload-text").addClass("hide");
      }else{
        $(".upload-text").removeClass("hide");
      }
    }
}

$.report = {
  options: {
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
      distributed: null, //0:是   1： 否
      sampleName: null
  },
  find: {
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
        $("body").on("click","[data-click='report-check-all']",function(){
          if($(this).hasClass("checkbox-ed")){
            $(this).removeClass("checkbox-ed").addClass("checkbox-un");
            $(".table>tbody .checkbox,.pagination .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
          }else{
            $(this).removeClass("checkbox-un").addClass("checkbox-ed");
            $(".table>tbody .checkbox,.pagination .checkbox").addClass("checkbox-ed").removeClass("checkbox-un");
          }
        });
        $("body").on("click","[data-click='report-batch-search']",function(){
          if(!$("#batch-sl").hasClass("select-more")){
            $.report.options.batch = "'"+$(this).text()+"'";
            $.report.find.condition();
            $("#selected-batch span").html($(this).text());
            $("#selected-batch").removeClass("hide");
            $("#to-sl-batch").addClass("hide");
          }
        });
        $("body").on("click","[data-click='report-period-search']",function(){
          if(!$("#period-sl").hasClass("select-more")){
            $.report.options.period = $(this).find("input").val();
            $.report.find.condition();
            $("#selected-period span").html($(this).find("span").html());
            $("#selected-period").removeClass("hide");
            $("#to-sl-period").addClass("hide");
          }
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
            $("#batch-more span").html("更多");
            $("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
          }else{
            $("#batch-lists").addClass("show-more");
            $("#batch-more span").html("收起");
            $("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
          }
        });
        $("body").on("click","[data-click='report-select-more']",function(){
          $(".selector-line").removeClass("select-more");
          $("#report-multibatch-search").addClass("disabled");
          $("#report-multibatch-search").attr("disabled",true);
          $(".selector-line .checkbox").removeClass("checkbox-ed").addClass("checkbox-un").addClass("hide");
          $(".selector-line").find("[name='sl-confirm']").addClass("disabled");
          $(".selector-line").find("[name='sl-confirm']").attr("disabled",true);
          $(".selector-line").find(".multisl-btns").addClass("hide");
          var selectorline = $(this).parent().parent().parent();
          $(selectorline).addClass("select-more");
          $(selectorline).find(".sl-val").addClass("show-more");
          $(selectorline).find(".checkbox").removeClass("hide");
          $(selectorline).find(".multisl-btns").removeClass("hide");
        });
        $("#batch-multiselect").on("click",function(){
          $("#batch-lists").addClass("show-more");
          $("#batch-more span").html("收起");
          $("#batch-more i").removeClass("fa-chevron-down").addClass("fa-chevron-up");
          $("#batch-lists .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
          $("#report-multibatch-search").addClass("disabled");
          $("#report-multibatch-search").attr("disabled",true);
          $("#batch-more").addClass("disabled");
          $("#batch-more").attr("disabled",true);
        });
        $("#batch-lists .sl-val-content").on("click",function(){
          $(this).find(".checkbox").toggleClass("checkbox-un");
          $(this).find(".checkbox").toggleClass("checkbox-ed");
          if($("#batch-lists .checkbox-ed").size() > 0){
            $("#report-multibatch-search").removeClass("disabled");
            $("#report-multibatch-search").attr("disabled",false);
          }else{
            $("#report-multibatch-search").addClass("disabled");
            $("#report-multibatch-search").attr("disabled",true);
          }
        });
        $("#report-multibatch-search").on("click",function(){
          var show_val = [];
          $("#batch-lists .checkbox-ed").each(function(){
            $.report.options.batch == null? $.report.options.batch = "'"+$(this).next().text()+"'" : $.report.options.batch += ",'"+$(this).next().text() + "'";
            show_val.push($(this).next().text());
          });
          $.report.find.condition();
          $("#selected-batch span").html(show_val.toString());
          $("#selected-batch").removeClass("hide");
          $("#to-sl-batch").addClass("hide");
          $("#batch-sl").removeClass("select-more");
          $("#batch-lists").removeClass("show-more");
          $("#batch-lists").find(".checkbox").addClass("hide");
          $("#batch-more span").html("更多");
          $("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
        });
        $("#clear-sl-batch").on("click",function(){
          $("#selected-batch").addClass("hide");
          $("#to-sl-batch").removeClass("hide");
          $.report.options.batch = null;
          $.report.find.condition();
        });
        $("#period-lists .sl-val-content").on("click",function(){
          $(this).find(".checkbox").toggleClass("checkbox-un");
          $(this).find(".checkbox").toggleClass("checkbox-ed");
          if($("#period-lists .checkbox-ed").size() > 0){
            $("#report-multiperiod-search").removeClass("disabled");
            $("#report-multiperiod-search").attr("disabled",false);
          }else{
            $("#report-multiperiod-search").addClass("disabled");
            $("#report-multiperiod-search").attr("disabled",true);
          }
        });
        $("#report-multiperiod-search").on("click",function(){
          var show_val = [];
          $("#period-lists .checkbox-ed").each(function(){
            $.report.options.period == null? $.report.options.period = $(this).next().find("input[type='hidden']").val() : $.report.options.period += ","+$(this).next().find("input[type='hidden']").val();
            show_val.push($(this).next().find("span").html());
          });
          $.report.find.condition();
          $("#selected-period span").html(show_val.toString());
          $("#selected-period").removeClass("hide");
          $("#to-sl-period").addClass("hide");
          $("#period-sl").removeClass("select-more");
          $("#period-sl").find(".checkbox").addClass("hide");
          $("#period-sl").find(".multisl-btns").addClass("hide");
          $("#period-lists .checkbox").removeClass("checkbox-ed").addClass("checkbox-un");
        });
        $("#clear-sl-period").on("click",function(){
          $("#selected-period").addClass("hide");
          $("#to-sl-period").removeClass("hide");
          $.report.options.period = null;
          $.report.find.condition();
        });
        $("body").on("click","[data-click='reset-multiselect']",function(){
          var selectorline = $(this).parent().parent().parent().parent();
          $(selectorline).removeClass("select-more");
          $(selectorline).find(".sl-val").removeClass("show-more");
          $(selectorline).find(".checkbox").addClass("hide").addClass("checkbox-un").removeClass("checkbox-ed");
          $(selectorline).find(".multisl-btns").addClass("hide");
          if($("#batch-more").hasClass("disabled"))
            $("#batch-more").removeClass("disabled");
            $("#batch-more").attr("disabled",false);
            $("#batch-more span").html("更多");
            $("#batch-more i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
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
            distributed: null, //是否分发  0:是   1： 否 
            sampleName: null
        };
      });
    },
    condition: function(){
      var options = $.report.options;
      $.get("report/bsi/searchReportList",{"sampleName":options.sampleName,"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
        $.report.loadlist(response);
      });
    },
    pagination: function(currentPage){
      var options = $.report.options;
      $.get("report/bsi/searchReportList",{"sampleName":options.sampleName,"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
        $.report.loadlist(response);
      });
    }
  },
  loadlist: function(response){
    $("#report-list").html(response);
    $("#sample-selector").on("keyup",function(e){
      e = e || window.event;
      if (e.keyCode == "13") {//keyCode=13是回车键
        $.report.options.sampleName = $("#sample-selector").val();
        $.report.find.condition();
      }
    });
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
  },
  reRun: function(dataKey,appId,projectId){
    $.get("data/reRun",{"dataKey":dataKey,"appId":appId,"projectId":projectId},function(result){
      $.report.find.condition();
    });
  },
  detail: {
    option: {
      batchPage: 1
    },
    patient: function(dataKey,projectId,appId,reportIndex,currentPage){
      var options = $.report.options;
      $.post("report/getBSIPatientReport",{"sampleName":options.sampleName,"reportIndex":reportIndex,"dataKey":dataKey,"projectId":projectId,"appId":appId,"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName,"size":options.pageSize},function(response){
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
        $.post("report/getPrevOrNextBSIReport",{"sampleName":options.sampleName,"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"isPrev":true,"page":currentPage-1,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
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
        $.post("report/getPrevOrNextBSIReport",{"sampleName":options.sampleName,"batch":options.batch,"period":options.period,"beginDate":options.beginDate,"endDate":options.endDate,"isPrev":false,"page":currentPage+1,"totalPage":totalPage,"condition":options.condition,"sort":options.sort,"sortDate":options.sortDate,"sortPeriod":options.sortPeriod,"sortBatch":options.sortBatch,"sortName":options.sortName},function(response){
          if(response != null &&response !=""){
            $("#container").html(response);
          }
        });
      }
    }
  },
  period: {
      error: function(dataName){
        $("#run-error-data").html(dataName);
        $("#running-error-modal").modal("show");
      }
  }
};

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
    },
    loadlist: function(response){
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
};