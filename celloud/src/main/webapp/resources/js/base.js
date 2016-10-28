var $base = {
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
    $("#common-menu .treeview").removeClass("-active");
    $(obj).parent().addClass("-active");
    $("#menu-name").html($(obj).data("menu"));
  }
};