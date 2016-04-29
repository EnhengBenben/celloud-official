$(function () {
  $("#report-condition-find").on("click",function(){
    $.report.options.condition = $("#report-condition-input").val();
    $.report.find.condition();
  });
  $("#report-condition-input").on("keyup",function(e){
    e = e || window.event;
    if (e.keyCode == "13") {//keyCode=13是回车键
      $("#report-condition-find").click();
    }
  });
});
