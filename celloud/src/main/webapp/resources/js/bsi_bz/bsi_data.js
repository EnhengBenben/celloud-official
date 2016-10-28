$(function () {
  $("#condition-find").unbind("click");
  $("#condition-find").on("click",function(){
    $.data_.options.condition = $("#condition-input").val();
    $.data_.find.condition();
  });
});
