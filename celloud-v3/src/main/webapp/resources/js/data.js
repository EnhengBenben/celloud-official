$.dataManager = {};
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(function(){
  _init_data();
  $.dataManager.find.all();
  $("#data-condition-find").on("click",function(){
	  $.dataManager.options.condition = $("#data-condition-input").val();
	  $.dataManager.find.condition();
  });
  $("#data-condition-input").on("keyup",function(){
	  $.dataManager.options.condition = $(this).val();
	  $.dataManager.find.condition();
  });
  $("#data-page-size-sel").on("change",function(){
	  $.dataManager.options.pageSize = $(this).val();
	  $.dataManager.find.condition();
  });
});

function _init_data(){
  $.dataManager.options = {
	 condition: null,
	 pageSize: $("#data-page-size-sel").val(),
	 sort: 0,
	 sortDateType: "desc",
	 sortNameType: "asc"
  };
  $.dataManager.find = {
    all: function(){
    	$.get("data/dataAllList.action",function(response){
    		$.dataManager.loadlist(response);
		});
    },
    condition: function(){
    	var options = $.dataManager.options;
    	$.get("data/dataList.action",{"condition":options.condition,"size":options.pageSize,"sort":options.sort,"sortDateType":options.sortDateType,"sortNameType":options.sortNameType},function(response){
    		$.dataManager.loadlist(response);
		});
    },
    pagination: function(currentPage){
    	var options = $.dataManager.options;
    	$.get("data/dataList.action",{"page":currentPage,"condition":options.condition,"size":options.pageSize,"sort":options.sort,"sortDateType":options.sortDateType,"sortNameType":options.sortNameType},function(response){
    		$.dataManager.loadlist(response);
		});
    }
  };
  $.dataManager.loadlist = function(response){
  	$("#selfDataDiv").html(response);
  	$.dataManager.sortIcon();
    /** 翻页按钮 */
	$("#pagination-data").on("click","a",function(e){
	  var id = $(this).attr("id");
	  var currentPage = parseInt($("#data-current-page-hide").val());
	  var page = id == null ? 
			  $(this).html() : (id == "prev-page-data" ? currentPage-1 : currentPage+1);
	  $.dataManager.find.pagination(page);
    });
	/** 按文件名排序 */
	$("#data-sort-name").on("click",function(e){
	  $.dataManager.options.sort = 1;
	  $.dataManager.find.condition();
	  $.dataManager.options.sortNameType = $.dataManager.options.sortNameType=="desc"?"asc":"desc";
    });
	/** 按日期排序 */
	$("#data-sort-createdate").on("click",function(e){
	  $.dataManager.options.sort = 0;
	  $.dataManager.find.condition();
	  $.dataManager.options.sortDateType = $.dataManager.options.sortDateType=="desc"?"asc":"desc";
    });
  };
  /**排序图标*/
  $.dataManager.sortIcon = function(){
	var sort = $.dataManager.options.sort;
	var sortNameType = $.dataManager.options.sortNameType;
	var sortDateType = $.dataManager.options.sortDateType;
	if(sort==1){
		$("#data-sort-createdate").removeClass("a-green-normal").addClass("a-gray");
		$("#data-sort-name").removeClass("a-gray").addClass("a-green-normal");
	}else{
		$("#data-sort-name").removeClass("a-green-normal").addClass("a-gray");
		$("#data-sort-createdate").removeClass("a-gray").addClass("a-green-normal");
	}
	if(sortNameType=="asc"){
		$("#data-sort-name-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
	}else if(sortNameType=="desc"){
		$("#data-sort-name-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
	}
	if(sortDateType=="asc"){
		$("#data-sort-date-icon").removeClass("fa-sort-amount-desc").addClass("fa-sort-amount-asc");
	}else if(sortDateType=="desc"){
		$("#data-sort-date-icon").removeClass("fa-sort-amount-asc").addClass("fa-sort-amount-desc");
	}
  };
}
