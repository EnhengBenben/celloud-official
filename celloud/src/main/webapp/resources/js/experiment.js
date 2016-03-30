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
	self.getDonePageList = function() {
    $("#doneExp").removeClass("hide");
    $("#doneTab").addClass("active");
    $("#doingExp").addClass("hide");
    $("#doingTab").removeClass("active");
    $("#subtitle").html("Done");
    $.get("experiment/getDonePageList",function(response){
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
	      $("#doingExp").html(response);
	    }
    })
  }
	self.validateBaseInfo = function() {
    return true;
  }
	return self;
})(experiment);

$(document).ready(function() {
  $.ajaxSetup({
    cache : false// 关闭AJAX相应的缓存
  });
  experiment.getPageList();
});
