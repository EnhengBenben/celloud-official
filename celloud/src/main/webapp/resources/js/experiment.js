var experiment = (function(experiment) {
	var self = experiment || {};
	self.getPageList = function() {
		$("#doneExp").addClass("hide");
		$("#doneTab").removeClass("active");
    $("#doingExp").removeClass("hide");
    $("#doingTab").addClass("active");
		$("#subtitle").html("Doing");
		$.get("experiment/getPageList",function(response){
		  $("#doingLoadDiv").html(response);
		})
	}
	var SAMPLE = 0;
	self.changeSample = function(id,obj){
	  $(".samples").removeClass("_datered");
	  $(obj).addClass("_datered");
	  SAMPLE = id;
	  self.getDonePageList();
	}
	var METHOD = 0;
	self.changeMethod = function(id,obj){
	  $(".methods").removeClass("_datered");
	  $(obj).addClass("_datered");
	  METHOD = id;
	  self.getDonePageList();
	}
	var STEP = 0;
	self.changeStep = function(id,obj){
	  $(".steps").removeClass("_datered");
	  $(obj).addClass("_datered");
	  STEP = id;
	  self.getDonePageList();
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
    }
    if(START != null && START != ""){
      START = START+" 00:00:00";
    }
    if(END != null && END != ""){
      END = END+" 23:59:59";
    }
    self.getDonePageList();
	}
	self.clearDoneCondition = function(){
	  $(".samples").removeClass("_datered");
	  $(".methods").removeClass("_datered");
	  $(".steps").removeClass("_datered");
	  SAMPLE = 0;
	  METHOD = 0;
	  STEP = 0;
	  START = null;
	  END = null;
	  $("#_startDate").val("");
	  $("#_endDate").val("");
	  self.getDonePageList();
	}
	self.getDonePageList = function() {
    $("#doneExp").removeClass("hide");
    $("#doneTab").addClass("active");
    $("#doingExp").addClass("hide");
    $("#doingTab").removeClass("active");
    $("#subtitle").html("Done");
    $.get("experiment/getDonePageList",{"sampleId":SAMPLE,"methodId":METHOD,"stepId":STEP,"start":START,"end":END},function(response){
      $("#doneLoadDiv").html(response);
    })
  }
	self.toAddExp = function(){
	  $.get("experiment/toAddExp",function(response){
	    $("#addExp").html(response);
	    $("#addExp").modal('show');
	  });
	}
	self.toEditExp = function(id){
    $.get("experiment/toEditExp",{"expId":id},function(response){
      $("#addExp").html(response);
      $("#addExp").modal('show');
    });
  }
	self.closeExperiment = function(){
	  $("#expState").val(1);
	  self.updateExperiment();
	}
	self.updateExperiment = function(){
	  $.get("experiment/updateExperiment",$("#exp-add-form").serialize(),function(flag){
      if(flag == 1){
        $("#addExp").modal('hide');
        self.getPageList();
      }else{
        //TODO error info
        $("#doingExp").html(flag);
      }
    })
  }
	self.validateBaseInfo = function() {
	  //TODO 加校验
    return false;
  }
	self.addExperiment = function() {
	  if(!self.validateBaseInfo()){
	    alert("error");
	    return;
	  }
	  $.get("experiment/addExperiment",$("#exp-add-form").serialize(),function(flag){
	    if(flag == 1){
	      $("#addExp").modal('hide');
	      self.getPageList();
	    }else{
	      //TODO error info
	      $("#doingExp").html(flag);
	    }
    })
  }
	return self;
})(experiment);

$(document).ready(function() {
  $.ajaxSetup({
    cache : false// 关闭AJAX相应的缓存
  });
  experiment.getPageList();
});
