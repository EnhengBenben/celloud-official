$(function () {
  $("#data-condition-find").on("click",function(){
    $.data_.options.condition = $("#data-condition-input").val();
    $.data_.find.condition();
  });
  $("#data-condition-input").on("keyup",function(e){
    e = e || window.event;
    if (e.keyCode == "13") {//keyCode=13是回车键
      $("#data-condition-find").click();
    }
  });
});
