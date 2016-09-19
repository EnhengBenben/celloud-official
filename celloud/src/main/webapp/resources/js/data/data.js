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
    page : 1,
    pageSize: 20,
    sort: 0,
    dataMax: 25,
    sortDateType: "desc",
    sortNameType: "asc",
    checkedIds: [],
    validIds: 0,
    isRun: 0,
    isTag: 0,
    isBSI: 0,
    isRocky: 0,
    isPair:0
  };
  
  /**
   * 选择单个数据
   */
  $.dataManager.checkOneData = function(obj){
    var _checked = $(obj).prop("checked");//jquery1.11获取属性
    var _dataId = $(obj).val();
    var _isRun = $(obj).attr("is_run");
    var _isTag = $(obj).attr("is_tag");
    var _isBSI = $(obj).attr("is_bsi");
    var _isRocky = $(obj).attr("is_rocky");
    var _isPair = $(obj).attr("is_pair");
    if(_checked){
      $.dataManager.checkData.isCheck(_dataId);
      if(_isRun=="true"){
        $.dataManager.options.isRun = $.dataManager.options.isRun + 1;
      }else if(_isTag=="true"){
        $.dataManager.options.isTag = $.dataManager.options.isTag + 1;
      }else if(_isBSI=="true"){
        $.dataManager.options.isBSI = $.dataManager.options.isBSI + 1;
      }else if(_isRocky=="true"){
        $.dataManager.options.isRocky = $.dataManager.options.isRocky + 1;
      }else if(_isPair=="true"){
        $.dataManager.options.isPair = $.dataManager.options.isPair + 1;
        $.dataManager.options.validIds = $.dataManager.options.validIds + 1;
      }else{
        $.dataManager.options.validIds = $.dataManager.options.validIds + 1;
      }
    }else{
      $.dataManager.checkData.noCheck(_dataId);
      if(_isRun=="true"){
        $.dataManager.options.isRun = $.dataManager.options.isRun - 1;
      }else if(_isTag=="true"){
        $.dataManager.options.isTag = $.dataManager.options.isTag - 1;
      }else if(_isBSI=="true"){
        $.dataManager.options.isBSI = $.dataManager.options.isBSI - 1;
      }else if(_isRocky=="true"){
        $.dataManager.options.isRocky = $.dataManager.options.isRocky - 1;
      }else if(_isPair=="true"){
        $.dataManager.options.isPair = $.dataManager.options.isPair - 1;
        $.dataManager.options.validIds = $.dataManager.options.validIds - 1;
      }else{
        $.dataManager.options.validIds = $.dataManager.options.validIds - 1;
      }
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
      var _checkedLength = choicearr.length;
      for(var i=0;i<_checkedLength;i++) {
        if($(choicearr[i]).prop("disabled")==false){
          if($(choicearr[i]).prop("checked")==false){
            var _isRun = $(choicearr[i]).attr("is_run");
            var _isTag = $(choicearr[i]).attr("is_tag");
            var _isBSI = $(choicearr[i]).attr("is_bsi");
            var _isRocky = $(choicearr[i]).attr("is_rocky");
            var _isPair = $(choicearr[i]).attr("is_pair");
            if(_isRun=="true"){
              $.dataManager.options.isRun = $.dataManager.options.isRun + 1;
            }else if(_isTag=="true"){
              $.dataManager.options.isTag = $.dataManager.options.isTag + 1;
            }else if(_isBSI=="true"){
              $.dataManager.options.isBSI = $.dataManager.options.isBSI + 1;
            }else if(_isRocky=="true"){
              $.dataManager.options.isRocky = $.dataManager.options.isRocky + 1;
            }else if(_isPair=="true"){
              $.dataManager.options.isPair = $.dataManager.options.isPair + 1;
              $.dataManager.options.validIds = $.dataManager.options.validIds + 1;
            }else{
              $.dataManager.options.validIds = $.dataManager.options.validIds + 1;
            }
          }
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
      $.dataManager.options.isRun = 0;
      $.dataManager.options.isTag = 0;
      $.dataManager.options.isBSI = 0;
      $.dataManager.options.isRocky = 0;
      $.dataManager.options.isPair = 0;
      $.dataManager.options.validIds = 0;
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
    $.dataManager.options.isRun = 0;
    $.dataManager.options.isTag = 0;
    $.dataManager.options.isBSI = 0;
    $.dataManager.options.isRocky = 0;
    $.dataManager.options.isPair = 0;
    $.dataManager.options.validIds = 0;
    $.dataManager.editBtn.update();
    $.dataManager.cleanCheckbox("demo-checkbox1");
  }
  
  /**
   * 复选框事件
   */
  $.dataManager.checkData = {
    isCheck: function(dataId){
      $.dataManager.options.checkedIds.push(dataId);
      if($("input[name='data-checkone']").length==$.dataManager.options.checkedIds.length){
        $("#data-checkall").prop("checked",true);
      }
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
  
}
