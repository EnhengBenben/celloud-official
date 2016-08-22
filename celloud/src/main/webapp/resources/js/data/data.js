$.dataManager = {};
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(function(){
  _init_data();
});

/**
 * 数据模块使用方法定义
 */
function _init_data(){
  $.dataManager.options = {
    condition: null,
    pageSize: $("#data-page-size-sel").val(),
    sort: 0,
    dataMax: 25,
    sortDateType: "desc",
    sortNameType: "asc",
    checkedIds: []
  };
  
  /**
   * ======================= new start ======================
   */
  
  /**
   * 选择单个数据
   */
  $.dataManager.checkOneData = function(obj){
    var _checked = $(obj).prop("checked");//jquery1.11获取属性
    var _dataId = $(obj).val();
    if(_checked){
      if($.dataManager.options.checkedIds.length>=$.dataManager.options.dataMax){
        alert("最多允许同时操作"+$.dataManager.options.dataMax+"条数据！");
        $(obj).prop("checked",false);
      }else{
        $.dataManager.checkData.isCheck(_dataId);
      }
    }else{
      $.dataManager.checkData.noCheck(_dataId);
    }
    $.dataManager.editBtn.update();
  };
  
  /**
   * 数据全选
   */
  $.dataManager.checkAll = function(obj, name){
    var _checked = $(obj).prop("checked");
    $.dataManager.options.checkedIds = new Array();
    if(_checked){
      var choicearr = document.getElementsByName(name);
      var _checkedLength = $.dataManager.options.dataMax;
      if(choicearr.length<=_checkedLength){
        _checkedLength = choicearr.length;
      }else{
        $.dataManager.showTipModal("最多允许同时操作"+$.dataManager.options.dataMax+"条数据！");
      }
      for(var i=0;i<_checkedLength;i++) {
        if($(choicearr[i]).prop("disabled")==false){
          $(choicearr[i]).prop("checked",true);
          var dataId = $(choicearr[i]).val();
          $.dataManager.checkData.isCheck(dataId);
        }
      }
      if($.dataManager.options.checkedIds.length>0){
        $.dataManager.editBtn.show();
      }
    }else{
      $("[name='"+name+"']").prop("checked",false);
      $.dataManager.editBtn.disable();
    }
  }
  
  /**
   * 将checkbox设置为取消选择状态
   */
  $.dataManager.cleanCheckbox = function(name){
    $("input[type='checkbox'][name='"+name+"']").prop("checked",false);
  }

  /**
   * 刷新数据列表的后续操作（清空数据选择列表、按钮重置、全选复选框重置）
   */
  $.dataManager.refreshDataList = function(name){
    $.dataManager.options.checkedIds = new Array();
    $.dataManager.editBtn.update();
    $.dataManager.cleanCheckbox("demo-checkbox1");
  }
  
  /**
   * 复选框事件
   */
  $.dataManager.checkData = {
    isCheck: function(dataId){
      $.dataManager.options.checkedIds.push(dataId);
    },
    noCheck: function(dataId){
      $("#data-checkall").prop("checked",false);
      var index = $.inArray(dataId.toString(), $.dataManager.options.checkedIds);
      $.dataManager.options.checkedIds.splice(index, 1);
    }
  };
  
  /**
   * 数据操作按钮显示状态（运行和归档）
   */ 
  $.dataManager.editBtn = {
    update: function(){
      var _checkedIds = $.dataManager.options.checkedIds;
      if(_checkedIds.length>0){
        $.dataManager.editBtn.show();
      }else{
        $.dataManager.editBtn.disable();
      };
    },
    show: function(){
      $(".data-operate").attr("disabled",false);
      $(".data-operate").removeClass("btn-cancel");
    },
    disable: function(){
      $(".data-operate").attr("disabled",true);
      $(".data-operate").addClass("btn-cancel");
    }
  };
  
  /**
   * ======================= new end ======================
   */
  
  
  
  
  
  
  
  
  
  /**
   * 数据修改
   */
  $.dataManager.updateData = {
    showBatchEditModal: function(){
      $.dataManager.updateData.strainList("#batch-editdatas-strain");
      $("#each-editdata-modal").modal("hide");
      $("#batch-editdata-form")[0].reset();
      $("#batch-editdata-error").addClass("hide");
      $("#batch-editdatas-strain").val("");
      $("#batch-editdata-modal").modal("show");
      $("#save-batch-data").on("click",function(){
        $.dataManager.updateData.saveBatchEdit();
      });
    },
    saveBatchEdit: function(){
      var checkedIds = $.dataManager.options.checkedIds;
      var dataIds = "";
      for (var i=0;i<checkedIds.length;i++){
           dataIds += checkedIds[i] + ",";
      }
      dataIds = dataIds.substring(0, dataIds.length-1);
      $("#batch-editdataIds-hide").val(dataIds);
      $.post("data/batchEditDataByIds.action",$("#batch-editdata-form").serialize(),function(flag){
        if(flag>0){
          $.dataManager.find.condition();
          $("#batch-editdata-modal").modal("hide");
        }else {
          $("#batch-editdata-error").removeClass("hide");;
        }
      });
    },
    showEachEditModal: function(){
      var checkedIds = $.dataManager.options.checkedIds;
      var dataIds = "";
      for (var i=0;i<checkedIds.length;i++){
           dataIds += checkedIds[i] + ",";
      }
      dataIds = dataIds.substring(0, dataIds.length-1);
      $.get("data/toEachEditDatas.action",{"dataIds":dataIds},function(response){
        $("#each-editdatas-div").html(response);
        $.dataManager.updateData.strainList("input[class='strain']");
        $("#batch-editdata-modal").modal("hide");
        $("#each-editdata-modal").modal("show");
        $("#save-each-data").on("click",function(){
          $.dataManager.updateData.saveEachEdit();
        });
      });
    },
    saveEachEdit: function(){
      $.post("data/eachEditDataByIds.action",$("#each-editdata-form").serialize(),function(result){
        if(result>0){
          $.dataManager.find.condition();
          $("#each-editdata-modal").modal("hide");
        }else{
          $("#each-updatedata-error").removeClass("hide");
        }
      });
    },
    strainList: function(Obj){
      $.get("data/getStrainList.action",{},function(result){
        $.dataManager.setSelect2Info(Obj,result);
      });
    }
  };
  
  /**
   * ============================
   * Tools  ---start
   */

  /**
   * 加载select2
   */
  $.dataManager.setSelect2Info = function(objId,data){
    $(objId).select2({
      placeholder: "请选择样本类型/物种",
      allowClear: true, //必须与placeholder同时出现才有效
      createSearchChoice:function(term, data) {
      if ($(data).filter(function() {
        return this.text.localeCompare(term)===0; 
        }).length===0) {
        return {id:term, text:term};
        }
      },
      data:data
    });
  };

  /**
   * 加载数据列表
   */
  $.dataManager.loadlist = function(response){
  	$("#data-list-div").html(response);
  	$("#data-list-tbody").find("td[name='data-name-td']").each(function(){
  	  var _data = $(this).attr("title");
  	  if(_data.length>40){
  	    var newData = utils.splitDataByInfo(_data, "\r\n" ,40);
  	    $(this).attr("title",newData);
  	  }
  	});
  	$.dataManager.sortIcon();
  	var o = $.dataManager.options;
    o.checkedIds = [];
    $.dataManager.editBtn.disable();
    
    /** 
     *翻页
     */
  	$("#pagination-data").on("click","a",function(e){
  	  var id = $(this).attr("id");
  	  var currentPage = parseInt($("#data-current-page-hide").val());
  	  var page = id == null ? 
  			  $(this).html() : (id == "prev-page-data" ? currentPage-1 : currentPage+1);
  	  $.dataManager.find.pagination(page);
    });
  	/**
  	 * 选择每页n条数据
  	 */
    $("#data-page-size-sel").on("change",function(){
      $.dataManager.options.pageSize = $(this).val();
      $.dataManager.find.condition();
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
  	
  	/**
  	 * 添加导航
  	 */
  	if(intro != null){
      intro.exit();
      intro = null;
      intro = introJs();
      intro.setOption('tooltipPosition', 'bottom');
      intro.setOption('showStepNumbers', false);
      intro.setOption('showButtons', false);
      intro.start();
      intro.goToStep(2);
      $("#manage-data-btns a").attr("disabled",true);
      $("#manage-data-btns a").unbind("click");
      $("#manage-data-btns").on("click",function(){
        if(intro != null){
          intro.exit();
          intro = null;
          intro = introJs();
          intro.setOption('tooltipPosition', 'bottom');
          intro.setOption('showStepNumbers', false);
          intro.setOption('showButtons', false);
          intro.start();
          intro.goToStep(8);
          $("#manage-data-btns").unbind("click");
        }
      });
    }
  };
  
  /**
   * 排序图标
   */
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
  
  /**
   * 弹出提示框
   */
  $.dataManager.showTipModal = function(text){
    $("#tip-text").html(text);
    $("#tip-modal").modal("show");
  };
  
}
