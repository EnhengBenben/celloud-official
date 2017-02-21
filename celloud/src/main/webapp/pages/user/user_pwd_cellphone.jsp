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
<title>CelLoud 用户重置密码</title>
</head>
<body onselectstart="return false;">
    <!--#S bgContainer-->
    <div class="bgContainer">
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
                  <form id="resetPasswordForm" action="<%=request.getContextPath()%>/resetCellphonePwd.html" method="post">
                      <input type="hidden" id="userNameHidden" name="username" value="${cellphone}">
                      <input type="hidden" id="userCodeHidden" name="randomCode" value="${randomCode}">
                      <input type="hidden" name="info" id="info" value="${requestScope.info }" />
                      <div class="error">&nbsp;</div>
                      <input type="text" class="input-top" value="用户名：${cellphone }" readonly="readonly"/>
                      <input type="password" class="pwd" name="password" id="inputPassword" placeholder="新密码(字母数字及下划线组合)"
                          name="password" />
                      <input type="password" class="pwd input-bottom" id="inputConfirmPassword" placeholder="确认密码" />
                      <a href="javascript:void()" class="btn-login btn-update-pwd" onclick="javascript:submitPassword();"></a>
                      <a href="<%=request.getContextPath()%>/login" class="btn-login btn-return"></a>
                  </form>
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/forgetRwd.js"></script>
    <script type="text/javascript">
        $.ajaxSetup ({
            cache: false //关闭AJAX相应的缓存
        });
        //根据视口和文档的宽高设置背景图片的尺寸
        utils.setDocSize();
        error = $("#info").val();
        if(error!=""){
          $(".error").html(error);
	    }else{
          $(".error").html("");
	    }
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