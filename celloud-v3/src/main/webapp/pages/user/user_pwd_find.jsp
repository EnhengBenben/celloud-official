<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css" media="all" />
<title>CelLoud 用户找回密码</title>
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
                <c:choose>
                    <c:when test="${requestScope.success eq 'ok' }">
                        <div class="main_f clearfix">
                            <br>
                            <br>
                            <p>您的申请已提交成功，请查看您的${requestScope.email }邮箱</p>
                            <br>
                            <br>
                            <br>
                            <br>
                            <br>
                            <br>
                            <a href="${requestScope.emailAddress }" class="btn-email" target="_blank">查看邮箱</a>
                            <a href="<%=request.getContextPath()%>/login" class="return">返回登录</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <form action="<%=request.getContextPath()%>/findPassword.html" method="post" id="findPasswordForm">
                            <div class="main_f clearfix">
                                <input type="text" class="username" placeholder="已注册的邮箱" id="inputEmail" name="email" />
                                <div class="yzm">
                                    <input type="text" class="yzm" placeholder="验证码" id="inputValidateCode" name="kaptchaCode"
                                        value="${requestScope.kapcode }" />
                                    <img title="看不清，换一张" src="<%=request.getContextPath()%>/kaptcha.jpg" id="kaptchaImage" alt="验证码"
                                        class="validateCode" style="cursor: pointer;" />
                                </div>
                                <div class="autolog">
                                    <span class="error">${requestScope.info }</span>
                                </div>
                                <a href="javascript:void()" class="btn-email" onclick="javascript:submitEmail();">发送邮件</a>
                                <a href="<%=request.getContextPath()%>/login" class="return">返回登录</a>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/utils.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/plugins/jQuery/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/user_pwd_reset.js?version=3.1"></script>
    <script type="text/javascript">
    //根据视口和文档的宽高设置背景图片的尺寸
    utils.setDocSize();
    </script>

</body>
</html>