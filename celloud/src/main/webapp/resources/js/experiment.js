var experiment = (function(experiment) {
	var self = experiment || {};
	var PAGE = 1;
	self.getDoingPageList = function(page){
	  PAGE = page;
	  self.getPageList(PAGE);
	}
	self.getPageList = function(page) {
		$("#doneExp").addClass("hide");
		$("#doneTab").removeClass("active");
    $("#doingExp").removeClass("hide");
    $("#doingTab").addClass("active");
		$("#subtitle").html("Doing");
		$.get("experiment/getPageList",{"page":PAGE},function(response){
		  $("#doingLoadDiv").html(response);
		})
	}
	var DONEPAGE = 1;
	var SAMPLE = 0;
	self.changeSample = function(id,obj){
	  $(".samples").removeClass("_datered");
	  $(obj).addClass("_datered");
	  SAMPLE = id;
	  self.changeDonePage(1);
	}
	var METHOD = 0;
	self.changeMethod = function(id,obj){
	  $(".methods").removeClass("_datered");
	  $(obj).addClass("_datered");
	  METHOD = id;
	  self.changeDonePage(1);
	}
	var STEP = 0;
	self.changeStep = function(id,obj){
	  $(".steps").removeClass("_datered");
	  $(obj).addClass("_datered");
	  STEP = id;
	  self.changeDonePage(1);
	}
	var START = null;
	var END = null;
	self.changeDate = function(){
	  START =$("#_startDate").val();
    END = $("#_endDate").val();
    if(START && END && START>END){
      $("#_alertSpan").css("display","");
      $("#_alertSpan").html("起始日期不能大于结束日期");
      return ;
    }else{
      $("#_alertSpan").css("display","none");
      $("#_alertSpan").html("");
    }
    if(START != null && START != ""){
      START = START+" 00:00:00";
    }
    if(END != null && END != ""){
      END = END+" 23:59:59";
    }
    self.changeDonePage(1);
	}
	self.clearDoneCondition = function(){
	  $(".samples").removeClass("_datered");
	  $(".methods").removeClass("_datered");
	  $(".steps").removeClass("_datered");
	  $("#_alertSpan").css("display","none");
    $("#_alertSpan").html("");
	  SAMPLE = 0;
	  METHOD = 0;
	  STEP = 0;
	  START = null;
	  END = null;
	  $("#_startDate").val("");
	  $("#_endDate").val("");
	  self.changeDonePage(1);
	}
	self.changeDonePage = function(page){
	  DONEPAGE = page;
	  self.getDonePageList(DONEPAGE);
	}
	self.getDonePageList = function(page) {
    $("#doneExp").removeClass("hide");
    $("#doneTab").addClass("active");
    $("#doingExp").addClass("hide");
    $("#doingTab").removeClass("active");
    $("#subtitle").html("Done");
    $.get("experiment/getDonePageList",{"page":DONEPAGE,"sampleId":SAMPLE,"methodId":METHOD,"stepId":STEP,"start":START,"end":END},function(response){
      $("#doneLoadDiv").html(response);
    })
  }
	self.toAddExp = function(){
	  $.get("experiment/toAddExp",function(response){
	    $("#addExp").html(response);
	    $("#addExp").modal('show');
	  });
	}
	var isChangeName = false;
  self.changeName = function(){
    var originNum = $("#expOriginNum").val();
    var expnumber = $("#expnumber").val();
    if(expnumber == originNum){
      isChangeName = false;
    }else{
      isChangeName = true;
    }
  }
	self.toEditExp = function(id){
	  isChangeName = false;
    $.get("experiment/toEditExp",{"expId":id},function(response){
      $("#addExp").html(response);
      $("#addExp").modal('show');
    });
  }
	self.closeExperiment = function(){
	  jConfirm('确认要关闭该实验吗?', '关闭实验', function(result) {
      if(!result){
        return;
      }
      $("#expState").val(1);
      $.get("experiment/updateExperiment",$("#exp-add-form").serialize(),function(flag){
        if(flag == 1){
          $("#addExp").modal('hide');
          self.getDoingPageList(1);
        }else{
          self.showError("关闭失败！");
        }
      })
    });
	}
	self.hideEditError = function(){
    $("#expErrorInfo").html("");
    $("#exp-add-error").addClass("hide");
  }
	self.updateExperiment = function(){
	  if(!self.validateBaseInfo()){
	    return;
	  }
	  var number = $("#expnumber").val().trim();
	  $.get("experiment/checkNumber",{"num":number},function(flag){
	    if((isChangeName&&flag==0)||(!isChangeName&&flag==1)){
	      $.get("experiment/updateExperiment",$("#exp-add-form").serialize(),function(flag){
	        if(flag == 1){
	          var fileId = $("#expFileId").val();
	          if(fileId==0){//是否已经绑定数据
	            if($("#dataStep").val()==$("#stepSelect").val()){
	              self.showError("保存成功！正在检索与本实验相匹配的数据，请稍等！");
	              $.get("experiment/getDataByAnotherName",{"anotherName":number},function(response){
	                $("#showData").html(response);
	                $("#showData").removeClass("hide");
	                $("#expEditSubmit").addClass("hide");
	                $("#expBindDataSubmit").removeClass("hide");
	                self.showError("保存成功！请选择数据后再点击一次保存！");
	              });
	            }else{
	              $("#addExp").modal('hide');
	              self.getDoingPageList(PAGE);
	            }
	          }else{
	            $("#addExp").modal('hide');
	            self.getDoingPageList(PAGE);
	          }
	        }else{
	          self.showError("修改失败！");
	        }
	      })
	    }else{
	      self.showError("编号不能和已有的doing状态的编号相同！");
	    }
	  });
	}
	self.expBindData = function(){
	  if(!self.validateBaseInfo()){
	    return;
	  }
	  var number = $("#expnumber").val().trim();
	  $.get("experiment/checkNumber",{"num":number},function(flag){
	    if(flag<=1){
	      var file_id = $("input[type='radio']:checked").val();
	      var dataKey = $("input[type='radio']:checked").next().val();
	      $("#expFileId").val(file_id);
	      $("#expDataKey").val(dataKey);
	      $.get("experiment/updateExperiment",$("#exp-add-form").serialize(),function(flag){
	        if(flag == 1){
	          $("#addExp").modal('hide');
	          self.getDoingPageList(PAGE);
	        }else{
	          self.showError("修改失败！");
	        }
	      })
	    }else{
	      self.showError("编号不能和已有的doing状态的编号相同！");
	    }
	  });
	}
	self.validateBaseInfo = function() {
	  var number = $("#expnumber").val().trim();
	  if(!number){
	    self.showError("编号不能为空！");
	    return;
	  }
	  if(number.length>45){
	    self.showError("编号长度不能大于45个字符！");
	    return;
	  }
	  var expCon = $("#expCon").val();
	  if(isNaN(expCon)){
	    self.showError("浓度必须为数字！");
      return;
	  }
	  var expLibCon = $("#expLibCon").val();
	  if(isNaN(expLibCon)){
      self.showError("库浓度必须为数字！");
      return;
    }
	  var expSeqIndex = $("#expSeqIndex").val().trim();
	  if(expSeqIndex.length>45){
      self.showError("Index长度不能大于45个字符！");
      return;
    }
	  var other = $("#expOther").val().trim();
    if(other.length>255){
      self.showError("其他长度不能大于255个字符！");
      return;
    }
	  $("#exp-add-error").addClass("hide");
    return true;
  }
	self.showError = function(info){
	  $("#expErrorInfo").html(info);
	  $("#exp-add-error").removeClass("hide");
	}
	self.addExperiment = function() {
	  if(!self.validateBaseInfo()){
	    return;
	  }
	  var number = $("#expnumber").val().trim();
	  $.get("experiment/checkNumber",{"num":number},function(flag){
	    if(flag==0){
	      $.get("experiment/addExperiment",$("#exp-add-form").serialize(),function(flag){
	        if(flag == 1){
	          $("#addExp").modal('hide');
	          self.getDoingPageList(PAGE);
	        }else{
	          self.showError("保存失败！");
	        }
	      })
	    }else{
	      self.showError("编号不能和已有的doing状态的编号相同！");
	    }
	  });
  }
	return self;
})(experiment);

$(document).ready(function() {
  $.ajaxSetup({
    cache : false// 关闭AJAX相应的缓存
  });
  experiment.getDoingPageList(1);
});
