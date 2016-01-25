$.dataManager = {};
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(function(){
  _init_data();
  $.dataManager.find.all();
  //给各按钮绑定事件
  $("#data-condition-find").on("click",function(){
	  $.dataManager.options.condition = $("#data-condition-input").val();
	  $.dataManager.find.condition();
  });
  $("#data-condition-input").on("keyup",function(e){
    e = e || window.event;
    if (e.keyCode == "13") {//keyCode=13是回车键
        $("#data-condition-find").click();
    }
  });
  $("#run-app-btn").on("click",function(){
    $.dataManager.run.showModal();
  });
  $("#del-data-btn").on("click",function(){
    $.dataManager.deleteData();
  });
  $("#manage-data-btn").on("click",function(){
    $.dataManager.updateData.showBatchEditModal();
  });
  $("#to-batch-editdata-modal").on("click",function(){
    $.dataManager.updateData.showBatchEditModal();
  });
  $("#to-each-editdata-modal").on("click",function(){
    $.dataManager.updateData.showEachEditModal();
  });
  $("#confirm-run").on("click",function(){
    $.dataManager.run.confirmRunApp();
  });
  $("#run-btn").on("click",function(){
    $.dataManager.run.beginRun();
  });
  
  //提示框添加可移动功能
  $("#tip-modal-head").mouseover(function() {
    $("#tip-modal-content").draggable();
  });
  $("#tip-modal-head").mouseout(function() {
    $("#tip-modal-content").draggable("destroy");
  });
  $("#run-error-head").mouseover(function() {
    $("#run-error-content").draggable();
  });
  $("#run-error-head").mouseover(function() {
    $("#run-error-content").draggable("destroy");
  });
  
  //运行modal按钮绑定事件
  $("#added-data-ul").on("click","li",function(e){
    $.dataManager.run.removeToRunData($(this));
  });
  $("#apps-data-ul").on("click","li",function(e){
    $.dataManager.run.updateRunApp($(this));
  });
});

/**
 * 数据模块使用方法定义
 */
function _init_data(){
  $.dataManager.options = {
    condition: null,
    pageSize: $("#data-page-size-sel").val(),
    sort: 0,
    sortDateType: "desc",
    sortNameType: "asc",
    checkedIds: [],
    checkedNames: [],
    runAppIds: []
  };
  
  /**
   * ==========================
   * 主方法  ---begin
   */
  
  /**
   * 数据检索
   */
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
  
  /**
   * 数据运行
   */
  $.dataManager.run = {
    showModal: function(){
      $("#run-btn").attr("disabled","true");
      var o = $.dataManager.options;
      var checkedIds = o.checkedIds;
      var checkedNames = o.checkedNames;
      var dataIds = "";
      if(checkedIds.length==0){
        $.dataManager.showTipModal("请选择至少一条数据");
        return;
      }
      var dataLi = "";
      var hasConfigure = false;
      for (var i=0;i<checkedIds.length;i++){
        var fileName = checkedNames[i];
        var fileId = checkedIds[i];
        if(!utils.isConfigure(fileName)){
          dataIds += fileId + ",";
        }else{
          hasConfigure = true;
        }
        dataLi += "<li class=\"types-options data-select\" title=\"点击删除\" name=\"to-run-data\" data-dataid=\""+checkedIds[i]+"\">"+fileName+"</li>";
      }
      dataIds = dataIds.substring(0, dataIds.length-1);
      var dataLength = checkedIds.length;
      if(dataIds=="" && hasConfigure){
        $.dataManager.showTipModal("请选择有效数据");
        return;
      }
      $.get("data/getFormatByDataIds.action",{"dataIds":dataIds},function(result){
        if(result == -1){
          $.dataManager.showTipModal("所选数据格式不统一");
        }else{
          $.get("data/getRunApp.action",{"formatId":result},function(appList){
            var appLi = "";
            var noAPP = 0;
            if(appList.length==0 ){
              appLi += "<li class='types-options'>没有可运行的APP</li>";
            }else{
              var _checkConfigure = true;
              for(var i=0;i<appList.length;i++){
                var appId = appList[i].appId;
                var appName = appList[i].appName;
                var dataNum = appList[i].dataNum;
                if(hasConfigure && appId!=113){
                  break;
                }else{
                  if(dataNum <= dataLength){
                    if((appId==109||appId==110||appId==111||appId==112||appId==113) && dataNum<dataLength){
                    }else{
                      appLi += "<li class=\"types-options\" title=\"点击选中\" name=\"to-run-app\" data-appid=\""+appId+"\" >"+appName+"</li>";
                      noAPP++;
                    }
                  }
                }
                if(appId == 113){
                  _checkConfigure = false;
                }
              }
              if(hasConfigure && _checkConfigure){
                $.dataManager.showTipModal("所选数据格式不统一");
                return;
              }
            }
            $("#apps-data-ul").html(appLi);
            $("#added-data-ul").html(dataLi);
            $("#run-modal").modal("show");
          });
        }
      });
    },
    removeToRunData: function(Obj){
      var _dataId = $(Obj).data("dataid");
      var checkedIds = $.dataManager.options.checkedIds;
      $.dataManager.checkData.noCheck(_dataId);
      $("#chk"+_dataId).prop("checked",false);
      $(Obj).remove();
      if(checkedIds.length==0){
        $("#run-btn").attr("disabled",true);
        $("#run-modal").modal("hide");
        $.dataManager.editBtn.disable();
      }
    },
    updateRunApp: function(Obj){
      var appId = $(Obj).data("appid");
      var appName = $(Obj).html();
      var o = $.dataManager.options;
      if($(Obj).hasClass("selected")){
        var runAppIds = o.runAppIds;
        o.runAppIds.splice($.inArray(appId,runAppIds),1);
        $(Obj).removeClass("selected");
        if(runAppIds.length==0){
          $("#run-btn").attr("disabled","true");
        }
      }else{
        var checkedIds = o.checkedIds;
        var dataIds = new Array();
        for (var i=0;i<checkedIds.length;i++){
          dataIds.push(checkedIds[i]);
        }
        $.get("data/checkDataRunningApp.action",{"dataIds":dataIds.toString(),"appId":appId},function(dataIdList){
          if(dataIdList.length>0){
            var dataName = new Array();
            for(var i=0;i<dataIdList.length;i++){
              dataName.push($("#filename-"+dataIdList[i]).val() + "<br>");
            }
            $.dataManager.showTipModal("以下数据正在运行APP："+appName+"<br>"+dataName+"<br>请选择其他APP或删除选中数据");
          }else{
            //判断为包含CMP/CMP_199/GDD则提示检查所选数据
            if(appId==110 ||appId==111|| appId==112){
              $("#run-error-text").html("运行"+appName+"需确定所选数据为配对数据！<input type='hidden' id='appId-hide' value='"+appId+"'><br>(配对格式:aaa<span class='text-red'>1</span>.fastq&nbsp;&nbsp;&nbsp;aaa<span class='text-red'>2</span>.fastq)");
              $("#run-error-modal").modal("show");
            }else if(appId==113){
              $("#run-error-text").html("运行"+appName+"需注意以下文件格式:<input type='hidden' id='appId-hide' value='"+appId+"'><br>配对数据格式：<br><div style='padding-left:83px'>aaa<span class='text-red'>1</span>.fastq</div><div style='padding-left:83px'>aaa<span class='text-red'>2</span>.fastq</div><br>参数文件格式：index.<span class='text-red'>txt</span>");
              $("#run-error-modal").modal("show");
            }else{
              $(Obj).addClass("selected");
              $("#run-btn").removeAttr("disabled");
              $.dataManager.options.runAppIds.push(appId);
            }
          }
        });
      }
    },
    confirmRunApp: function(){
      var appId = $("#appId-hide").val();
      $("#apps-data-ul li[data-appid="+appId+"]").addClass("selected");
      $("#run-btn").removeAttr("disabled");
      $.dataManager.options.runAppIds.push(appId);
    },
    beginRun: function(){
      var o = $.dataManager.options;
      var checkedIds = o.checkedIds;
      var addedApps = o.runAppIds;
      var dataIds = "";
      var appIds = "";
      for (var i=0;i<checkedIds.length;i++){
        dataIds += checkedIds[i] + ",";
      }
      dataIds = dataIds.substring(0, dataIds.length-1);
      $("#run-error-text").html("");
      for (var i=0;i<addedApps.length;i++){
        appIds += addedApps[i] + ",";
      }
      appIds = appIds.substring(0, appIds.length-1);
      $.get("data/run.action",{"dataIds":dataIds,"appIds":appIds},function(result){
        if(result != ""){
          $.dataManager.showTipModal("以下APP运行失败：<br>"+result);
        }else{
          $.dataManager.find.condition();
          $("#run-modal").modal("hide");
        }
      });
    }
  };
  
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
   * 数据删除
   */
  $.dataManager.deleteData = function(){
    $.dataManager.showTipModal("确定要删除选中数据吗？");
    $("#check-true").one("click",function(){
      var checkedIds = $.dataManager.options.checkedIds;
      var dataIds = "";
      for (var i=0;i<checkedIds.length;i++){
        dataIds += checkedIds[i] + ",";
      }
      dataIds = dataIds.substring(0, dataIds.length-1);
      $.get("data/delete.action",{"dataIds":dataIds},function(data){
        if(data.code == "104"){
          $.dataManager.find.condition();
        }else{
          $.dataManager.showTipModal(data.message);
        }
      });
    });
    $("#check-flase").one("click",function(){
      $("#check-true").unbind("click");
    })
  }; 
  /**
   * 主方法  ---end
   * ==========================
   */
  
  
  
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
  	  if(_data.length>50){
  	    var newData = $.dataManager.splitDataByInfo(_data, "\r\n" ,50);
  	    $(this).attr("title",newData);
  	  }
  	});
  	$.dataManager.sortIcon();
  	var o = $.dataManager.options;
    o.checkedIds = [];
    o.checkedNames = [];
    $.dataManager.editBtn.disable();
    
    /**
     * 复选框事件
     */
    $("input[name='data-checkone']").on("click",function(){
      $.dataManager.checkOneData($(this),25);
    });
    $("#data-checkall").on("click",function(){
      $.dataManager.checkAll($(this), "data-checkone", 25);
    });
    
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
   * 选择单个数据
   */
  $.dataManager.checkOneData = function(obj, num){
  	var _checked = $(obj).prop("checked");//jquery1.11获取属性
  	var _dataId = $(obj).val();
    if(_checked){
  	  if($.dataManager.options.checkedIds.length>=num){
  	    $.dataManager.showTipModal("最多允许同时操作"+num+"条数据！");
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
  $.dataManager.checkAll = function(obj, name, num){
    var _checked = $(obj).prop("checked");
    $.dataManager.options.checkedIds = new Array();
    $.dataManager.options.checkedNames = new Array();
    if(_checked){
      var choicearr = document.getElementsByName(name);
      var _checkedLength = num;
      if(choicearr.length<=num){
        _checkedLength = choicearr.length;
      }else{
        $.dataManager.showTipModal("最多允许同时操作"+num+"条数据！");
      }
      for(var i=0;i<_checkedLength;i++) {
        $(choicearr[i]).prop("checked",true);
        var dataId = $(choicearr[i]).val();
        $.dataManager.checkData.isCheck(dataId);
      }
      $.dataManager.editBtn.show();
    }else{
      $("[name='"+name+"']").prop("checked",false);
      $.dataManager.editBtn.disable();
    }
  }
  
  /**
   * 复选框事件
   */
  $.dataManager.checkData = {
    isCheck: function(dataId){
      $.dataManager.options.checkedIds.push(dataId);
      $.dataManager.options.checkedNames.push($("#filename-"+dataId).val());
    },
    noCheck: function(dataId){
      $("#data-checkall").prop("checked",false);
      var index = $.inArray(dataId, $.dataManager.options.checkedIds);
      $.dataManager.options.checkedIds.splice(index, 1);
      $.dataManager.options.checkedNames.splice(index, 1);
    }
  };
  
  /**
   * 弹出提示框
   */
  $.dataManager.showTipModal = function(text){
    $("#tip-text").html(text);
    $("#tip-modal").modal("show");
  };
  
  /**
   * 编辑按钮显示状态
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
      $("#del-data-btn").attr("disabled",false);
      $("#manage-data-btn").attr("disabled",false);
      $("#del-data-btn").removeClass("disabled");
      $("#manage-data-btn").removeClass("disabled");
    },
    disable: function(){
      $("#del-data-btn").attr("disabled",true);
      $("#manage-data-btn").attr("disabled",true);
      $("#del-data-btn").addClass("disabled");
      $("#manage-data-btn").addClass("disabled");
    }
  };
  
  /**
   * title每隔n个字符插入指定字符
   */
  /**
    @param data 原始字符串
    @param splitStr 要插入的字符串
    @param number 间隔的字符长度
    @example $.dataManager.splitDataByInfo("abcdefghijklmno","\r\n",10) 
       即表示在字符串"abcdefghijklmno"中每隔10个字符串插入"\r\n字符串"
  */
  $.dataManager.splitDataByInfo = function(data,splitStr,number){
    var reStr = "(.{"+number+"}|.*)"; 
    var reg = new RegExp(reStr,"g"); 
    var dataArray = data.match(reg) ;
    dataArray.pop();
    var arrLength = dataArray.length+1; 
    for(var i=0;i<dataArray.length;i+=2){ 
      dataArray.splice(i+1,0,splitStr);
    } 
    dataArray.pop();
    var str = dataArray.join('');
    return str; 
  }
  
  /**
   * Tools ----end
   * ========================
   */
}
