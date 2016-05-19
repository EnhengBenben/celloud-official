$(function () {
  $("#condition-find").on("click",function(){
    $.report.options.condition = $("#condition-input").val();
    $.report.find.condition();
  });
  $("#condition-input").on("keyup",function(e){
    e = e || window.event;
    if (e.keyCode == "13") {//keyCode=13是回车键
      $.report.options.condition = $("#condition-input").val();
      $.report.find.condition();
    }
  });
});
