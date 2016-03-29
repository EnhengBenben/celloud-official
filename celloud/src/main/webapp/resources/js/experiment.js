var experiment = (function(experiment) {
	var self = experiment || {};
	self.getPageList = function() {
		$("#doneExp").addClass("hide");
		$("#doneTab").removeClass("active");
    $("#doingExp").removeClass("hide");
    $("#doingTab").addClass("active");
		$("#subtitle").html("Doing");
		$.get("experiment/getPageList",function(response){
		  $("#doingExp").html(response);
		})
	}
	self.getDonePageList = function() {
    $("#doneExp").removeClass("hide");
    $("#doneTab").addClass("active");
    $("#doingExp").addClass("hide");
    $("#doingTab").removeClass("active");
    $("#subtitle").html("Done");
    $.get("experiment/getPageList",function(response){
      $("#doingExp").html(response);
    })
  }
	self.toAddExp = function(){
	  //TODO 请求后台初始化动态菜单
	  $("#addExp").modal('show');
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
	  alert("xx");
	  $.get("experiment/addExperiment",$("#exp-form").serialize(),function(response){
	    alert(response);
      $("#doingExp").html(response);
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
