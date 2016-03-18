<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css" media="all" />
<title>CelLoud 用户重置密码</title>
</head>
<body onselectstart="return false;">
    <!--#S bgContainer-->
    <div class="bgContainer">
        <img src="<%=request.getContextPath()%>/images/home/login_bg.png" id="bg" />
        <!--#S wrapper-->
        <div class="wrapper">
            <div class="login">
                <div class="logo" id="logo">
                    <img src="<%=request.getContextPath()%>/images/home/logo@2x.png" />
                </div>
                <div class="main_f clearfix">
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
                            <form id="resetPasswordForm" action="<%=request.getContextPath()%>/resetPassword.html" method="post">
                                <input type="hidden" id="userNameHidden" name="username" value="${user.username}">
                                <input type="hidden" id="userCodeHidden" name="randomCode" value="${randomCode}">
                                <div class="autolog">用户名：${user.username }</div>
                                <input type="password" class="pwd" name="password" id="inputPassword" placeholder="新密码(字母数字及下划线组合)"
                                    name="password" />
                                <input type="password" class="pwd" id="inputConfirmPassword" placeholder="确认密码" />
                                <div class="autolog">
                                    <span class="error"></span>
                                </div>
                                <a href="javascript:void()" class="btn-confirm" onclick="javascript:submitPassword();"></a>
                                <a href="<%=request.getContextPath()%>/login" class="btn-cancle"></a>
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="view-tips">最佳浏览体验：Chrome / Firefox 8.0+ / IE 9.0+</div>
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/forgetRwd.js"></script>
    <script type="text/javascript">
        $.ajaxSetup ({
            cache: false //关闭AJAX相应的缓存
        });
        //根据视口和文档的宽高设置背景图片的尺寸
        utils.setDocSize();
        //表单提交验证
        function validateSubmitPassword(){
            var pwd = $("#inputPassword").val();
            var confirmPwd = $("#inputConfirmPassword").val();
            if(!pwd){
                $(".error").html("请输入新密码");
                $("#inputPassword").focus();
                return false;
            }
            var numberRegex = /^[\d+]{6,16}$/;
            if(numberRegex.test(pwd)){
                $(".error").html("密码不能全为数字");
                $("#inputPassword").focus();
                return false;
            }
            var combineRegex = /^[a-zA-Z0-9_]{6,16}$/;
            if(!combineRegex.test(pwd)){
                $(".error").html("密码为6-16位的字母数字组合，也可以包含下划线_");
                $("#inputPassword").focus();
                return false;
            }
            if(!confirmPwd){
                $(".error").html("请输入确认密码");
                $("#inputConfirmPassword").focus();
                return false;
            }
            if(confirmPwd!=pwd){
                $(".error").html("确认密码与新密码不一致");
                $("#inputConfirmPassword").focus();
                return false;
            }
            $(".error").html("");
            return true;
        }
        //修改密码
        function submitPassword(){
            $(".error").html("");
            if(validateSubmitPassword()){
                $("#resetPasswordForm").submit();
            }
        }
    </script>
    
</body>
</html>