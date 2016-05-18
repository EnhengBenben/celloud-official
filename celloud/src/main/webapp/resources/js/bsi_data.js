$(function () {
  $("#condition-find").on("click",function(){
    $.data_.options.condition = $("#condition-input").val();
    $.data_.find.condition();
  });
  $("#condition-input").on("keyup",function(e){
    e = e || window.event;
    if (e.keyCode == "13") {//keyCode=13是回车键
      $.data_.options.condition = $("#condition-input").val();
      $.data_.find.condition();
    }
  });
});
