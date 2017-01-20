<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css?version=3.1.14" media="all" />
<title>CelLoud 用户重置邮箱</title>
</head>
<body onselectstart="return false;">
    <!--#S bgContainer-->
    <div class="bgContainer">
<%--         <img src="<%=request.getContextPath()%>/images/home/login_bg.png" id="bg" /> --%>
        <!--#S wrapper-->
        <div class="wrapper">
            <div class="login">
                <div class="login-icon" id="logo">
                    <a href="<%=request.getContextPath()%>/" style="color: transparent;">
                        <img src="<%=request.getContextPath()%>/images/icon/login_logo_187.png" />
                    </a>
                </div>
                <div class="logo-name">生物信息云平台</div>
                <div class="login-main clearfix">
                    <c:choose>
                        <c:when test="${! empty forbidden || empty user }">
                            <br>
                            <br>
                            <br>
                            <br>
                            <div class="autolog">
                                <span class="error" style="padding-left: 0; font-size: 16px;">${info }，<span id="time">5</span>
                                    秒后将跳转到登录页面
                                </span>
                            </div>
                            <script type="text/javascript">
                                //跳转倒计时
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
                                forwardProgress(5);
                            </script>
                        </c:when>
                        <c:otherwise>
                            <form id="resetEmailForm">
                                <input type="hidden" id="userNameHidden" name="username" value="${user.username}">
                                <input type="hidden" id="userIdHidden" name="userId" value="${user.userId}">
                                <input type="hidden" id="userCodeHidden" name="randomCode" value="${randomCode}">
                                <input type="hidden" id="userEmailHidden" name="oldEmail" value="${user.email}">
                                <div class="error">&nbsp;</div>
                                <input type="text" class="input-top" value="用户名：${user.username }" readonly="readonly"/>
                                <input type="text" class="" value="旧邮箱：${user.email }" readonly="readonly"/>
                                <input type="text" class="email input-bottom" name="email" id="inputEmail" placeholder="请输入新邮箱" />
                                <a href="javascript:void()" class="btn-login btn-email" onclick="javascript:sendToNewEmail();"></a>
                                <a href="<%=request.getContextPath()%>/login" class="btn-login btn-return"></a>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <jsp:useBean id="_now" class="java.util.Date" />
            <div class="footer">
                ©
                <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" />
                Celloud，Inc. All Rights reserved.
                <a href="#">生物信息云平台</a>
                ·
                <a href="javascript:void();">沪ICP备14035977号</a>
                ·
                <a href="<%=request.getContextPath()%>/service.html" target="_blank">服务与支持</a>
                ·
                <a href="<%=request.getContextPath()%>/feedback.html" target="_blank">意见反馈</a>
            </div>
        </div>
        <!--#E wrapper-->
    </div>
    <!--#E bgContainer-->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/utils.js"></script>
    <script type="text/javascript">
        $.ajaxSetup ({
            cache: false //关闭AJAX相应的缓存
        });
        //根据视口和文档的宽高设置背景图片的尺寸
        utils.setDocSize();
        //表单提交验证
        function validateSubmitEmail(){
            var email = $("#inputEmail").val().trim();
            if(!email){
                $(".error").html("请输入新邮箱");
                $("#inputEmail").focus();
                return false;
            }
            var emailregex = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        	if (!emailregex.test(email)) {
        	  $(".error").html("邮箱格式不正确");
        	  $("#inputEmail").focus();
        	  return false;
        	}
            if(email==$("#userEmailHidden").val().trim()){
              $(".error").html("新旧邮箱不能相同");
              $("#inputEmail").focus();
              return false;
            }
            $(".error").html("");
            return true;
        }
        //修改密码
        function sendToNewEmail(){
            $(".error").html("");
            if(validateSubmitEmail()){
              $.get("<%=request.getContextPath()%>/user/email/checkEmail",{"email":$("#inputEmail").val().trim(),"userId":$("#userIdHidden").val().trim()},function(flag){
                if(flag==0){
                  $(".error").html("新邮箱已经被使用");
                }else{
	              $.get("<%=request.getContextPath()%>/user/email/sendNewEmail",$("#resetEmailForm").serialize(),function(flag){
	                if(flag == 1){
	                  $(".error").html("邮件发送失败，请重新点击发送！");
	                }else{
	                  $(".error").html("邮件发送成功，请到新邮箱激活验证链接！");
	                }
	              });
                }
              });
            }
        }
    </script>
    
</body>
</html>
