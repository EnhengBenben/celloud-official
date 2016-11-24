$(document).ready(function(){
  $.ajaxSetup ({
    cache: false //关闭AJAX相应的缓存
  });
  var time = 0;
  //变换随机验证码
  $("#getcaptcha").click(function(){
    var cellphone = $.trim($("#cellphone").val());
    if(cellphone==""||cellphone==$.trim($("#cellphone").attr("placeholder"))){
      $(".error").html("请输入手机号！");
      $("#cellphone").val('').focus();
      return false;
    }else{
      var mobileregex = /^[\d+]{11}$/;
      if(!mobileregex.test(cellphone)){
        $(".error").html("手机号格式不正确！");
        return false;
      }
    }
    if(time > 0){
      return false;
    }
    $("#getcaptcha").attr("readonly","true");
    $.get("customer/sendCaptcha.html",{"cellphone":$("#cellphone").val()},function(result){
      if(result == "succuss"){
        time = 60;
        $("#getcaptcha").addClass("disabled");
        $("#getcaptcha").html("重新发送(<span id='times'>60</span>)");
        var setinterval = setInterval(function(){
            time--;    
            if(time==0){
                $("#getcaptcha").removeClass("disabled");
                clearInterval(setinterval);
                $("#getcaptcha").attr("readonly","false");
                $("#getcaptcha").html("重新发送");
            } else {
                $("#times").html(time);
            }
        }, 1000);
      }else{
        setTimeout(function(){
          $(".error").html("请勿频繁获取验证码！")
        }, 5000);
      }
    });
  });
  error = $("#info").val();
  if(error!=""){
    $(".error").html(error);
  }else{
    $(".error").html("");
  }

  var tmpRem=0;
  $("#submit").click(function(){
    if(checkForm()){
      $("#clientLoginForm").submit();
    }
  });
  $('#submit').keydown(function(e){
    if(e.keyCode==13){
      $("#submit").click();
    }
  });
  document.onkeydown = function(e){ 
      var ev = document.all ? window.event : e;
      if(ev.keyCode==13) {
        $("#submit").click();
       }
  };
  function checkForm(){
    $(".error").html("");
    //校验用户名是否为空
    var cellphone = $.trim($("#cellphone").val());
    if(cellphone==""||cellphone==$.trim($("#cellphone").attr("placeholder"))){
      $(".error").html("请输入手机号！");
      $("#cellphone").val('').focus();
      return false;
    }else{
      var mobileregex = /^[\d+]{11}$/;
      if(!mobileregex.test(cellphone)){
        $(".error").html("手机号格式不正确！");
        return false;
      }
    }
    
    //校验密码是否为空
    var captcha = $.trim($("#captcha").val());
    if(captcha==""){
      $(".error").html("请输入验证码！");
      $("#captcha").focus();
      return false;
    }
    return true;
  }
});
function forwardProgress(time){
  var setinterval = setInterval(function(){
      time--;    
      if(time==0){
          clearInterval(setinterval);
          window.location.href="<%=request.getContextPath()%>/login";
      } else {
          $("#time").html(time);
      }
  }, 1000);
}