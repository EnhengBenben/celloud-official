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
    noValidIds: 0
  };
  
  /**
   * 选择单个数据
   */
  $.dataManager.checkOneData = function(obj){
    var _checked = $(obj).prop("checked");//jquery1.11获取属性
    var _dataId = $(obj).val();
    var _valid = $(obj).prev().val();
    if(_checked){
      $.dataManager.checkData.isCheck(_dataId);
      if(_valid=="true"){
        $.dataManager.options.noValidIds = $.dataManager.options.noValidIds + 1;
      }else{
        $.dataManager.options.validIds = $.dataManager.options.validIds + 1;
      }
    }else{
      $.dataManager.checkData.noCheck(_dataId);
      if(_valid=="true"){
        $.dataManager.options.noValidIds = $.dataManager.options.noValidIds - 1;
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
            var _valid = $(choicearr[i]).prev().val();
            if(_valid=="true"){
              $.dataManager.options.noValidIds = $.dataManager.options.noValidIds + 1;
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
      $.dataManager.options.noValidIds = 0;
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
    $.dataManager.options.noValidIds = 0;
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
