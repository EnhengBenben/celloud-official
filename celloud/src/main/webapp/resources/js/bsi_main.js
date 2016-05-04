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
});
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

$.report = {};
$.report.options = {
    condition: null,
    sort: 0,
    sortDate: "desc",
    sortPeriod: "asc",
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
    var currentPage = parseInt($("#current-page-hide").val());
    var page;
    if(id == null){
      page = $(this).html();
    }else if(id.indexof("prev")>0){
      page = currentPage-1;
    }else if(id.indexof("next")>0){
      page = currentPage+1;
    }else if(id.indexof("first")>0){
      page = 1;
    }else if(id.indexof("last")>0){
      page = parseInt($("#total-page-hide").val());
    }
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
    }
}

$.data_ = {
    options: {
        condition: null,
        sort: 0,
        sortDate: "desc"
    },
    find : {
        all: function(){
          $.get("data/bsiDataAllList",function(response){
            $.data_.loadlist(response);
          });
        },
        condition: function(){
          var options = $.data_.options;
          $.get("data/bsiDataList",{"condition":options.condition,"sort":options.sort,"sortDateType":options.sortDate},function(response){
            $.data_.loadlist(response);
          });
        },
        pagination: function(currentPage){
          var options = $.data_.options;
          $.get("data/bsiDataList",{"page":currentPage,"condition":options.condition,"sort":options.sort,"sortDateType":options.sortDate},function(response){
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
  $.base.sortIcon($.data_.options.sortPeriod,$.data_.options.sortDate);
  $("#pagination-data").on("click","a",function(e){
    var id = $(this).attr("id");
    var currentPage = parseInt($("#current-page-hide").val());
    var page;
    if(id == null){
      page = $(this).html();
    }else if(id.indexof("prev")>0){
      page = currentPage-1;
    }else if(id.indexof("next")>0){
      page = currentPage+1;
    }else if(id.indexof("first")>0){
      page = 1;
    }else if(id.indexof("last")>0){
      page = parseInt($("#total-page-hide").val());
    }
    $.data_.find.pagination(page);
  });
  $("#sort-period").on("click",function(e){
    $.data_.options.sort = 1;
    $.data_.options.sortPeriod = $.data_.options.sortPeriod=="desc"?"asc":"desc";
    $.data_.find.condition();
  });
  $("#data-sort-date").on("click",function(e){
    $.data_.options.sort = 0;
    $.data_.options.sortDate = $.data_.options.sortDate=="desc"?"asc":"desc";
    $.data_.find.condition();
  });
}
