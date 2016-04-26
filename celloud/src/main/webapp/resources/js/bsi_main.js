/**
 * 血流用户主页事件
 */
$(function () {
  $("#to-upload-a").on("click",function(){
    $("#upload-modal").modal("show");
  });
  $("#to-report-a").on("click",function(){
    $.get("data/taskAllList",{},function(responseText){
      $("#container").html(responseText);
    })
  });
});
$.report = {};
$.report.options = {
    condition: null,
    sort: 0,
    sortDate: "desc",
    sortPeriod: "asc",
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
  $.base.sortIcon();
  
  /** 
   *翻页
   */
  $("#pagination-task").on("click","a",function(e){
    var id = $(this).attr("id");
    var currentPage = parseInt($("#data-current-page-hide").val());
    var page = id == null ? 
        $(this).html() : (id == "prev-page-data" ? currentPage-1 : currentPage+1);
    $.get("data/dataList.action",{"page":currentPage,"condition":options.condition,"size":options.pageSize,"sort":options.sort,"sortDateType":options.sortDateType,"sortNameType":options.sortNameType},function(response){
      $.dataManager.loadlist(response);
    });
  });
  
  /** 
   * 按文件名排序
   */
  $("#data-sort-name").on("click",function(e){
    $.dataManager.options.sort = 1;
    $.dataManager.options.sortNameType = $.dataManager.options.sortNameType=="desc"?"asc":"desc";
    $.dataManager.find.condition();
  });
  
  /** 
   * 按日期排序
   */
  $("#data-sort-createdate").on("click",function(e){
    $.dataManager.options.sort = 0;
    $.dataManager.options.sortDateType = $.dataManager.options.sortDateType=="desc"?"asc":"desc";
    $.dataManager.find.condition();
  });
}
/**
 * 排序图标
 */
$.base = {
  /**
   * 排序图标切换
   */
  sortIcon : function(sortType1,sortType2){
    if(sortType1=="asc"){
      $("#"+sortType1+"icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
    }else if(sortType1=="desc"){
      $("#"+sortType1+"icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
    }
    if(sortType2=="asc"){
      $("#"+sortType2+"icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
    }else if(sortType2=="desc"){
      $("#"+sortType2+"icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
    }
  },
  pagination: function(param,optionType){
    var options = $.dataManager.options;
    $.get("data/dataList.action",param,function(response){
      $.dataManager.loadlist(response);
    });
  },
  loadlistBase : function(response,optionType){
    $("#container").html(response);
    $("#data-list-tbody").find("td[name='data-name-td']").each(function(){
      var _data = $(this).attr("title");
      if(_data.length>40){
        var newData = utils.splitDataByInfo(_data, "\r\n" ,40);
        $(this).attr("title",newData);
      }
    });
    $.base.sortIcon();
    
    /** 
     *翻页
     */
    $(".pagination").on("click","a",function(e){
      var id = $(this).attr("id");
      var currentPage = parseInt($("#current-page-hide").val());
      var page = id == null ? 
          $(this).html() : (id.indexof("prev")>0 ? currentPage-1 : currentPage+1);
      var options = $.dataManager.options;
      $.get("data/dataList.action",{"page":currentPage,"condition":options.condition,"size":options.pageSize,"sort":options.sort,"sortDateType":options.sortDateType,"sortNameType":options.sortNameType},function(response){
        $.dataManager.loadlist(response);
      });
    });
    
    /** 
     * 按文件名排序
     */
    $("#sort-period").on("click",function(e){
      $.dataManager.options.sort = 1;
      $.dataManager.options.sortNameType = $.dataManager.options.sortNameType=="desc"?"asc":"desc";
      $.dataManager.find.condition();
    });
    
    /** 
     * 按日期排序
     */
    $("#end-date").on("click",function(e){
      $.dataManager.options.sort = 0;
      $.dataManager.options.sortDateType = $.dataManager.options.sortDateType=="desc"?"asc":"desc";
      $.dataManager.find.condition();
    });
  }
};