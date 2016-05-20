$(function () {
  $("#condition-find").unbind("click");
  $("#condition-find").on("click",function(){
    $.report.options.condition = $("#condition-input").val();
    $.report.find.condition();
  });
});
