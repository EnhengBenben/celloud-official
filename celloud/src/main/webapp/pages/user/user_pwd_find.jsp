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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css?version=3.1.14" media="all" />
<title>CelLoud 用户找回密码</title>
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
                <c:choose>
                    <c:when test="${requestScope.success eq 'ok' }">
                        <div class="login-main clearfix">
                            <br>
                            <br>
                            <p>您的申请已提交成功，请查看您的${requestScope.email }邮箱</p>
                            <br>
                            <br>
                            <a href="${requestScope.emailAddress }" class="btn-login find-email" target="_blank">查看邮箱</a>
                            <a href="<%=request.getContextPath()%>/login" class="btn-login btn-return">返回登录</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <form action="<%=request.getContextPath()%>/findPassword.html" method="post" id="findPasswordForm">
                            <div class="login-main clearfix">
                                <div class="error">${requestScope.info }&nbsp;</div>
                                <input type="text" class="username input-top" placeholder="注册邮箱" id="inputEmail" name="email" />
                                <div class="yzm">
                                    <input type="text" class="yzm input-bottom" placeholder="验证码" id="inputValidateCode" name="kaptchaCode"
                                        value="${requestScope.kapcode }" />
                                    <img title="看不清，换一张" src="<%=request.getContextPath()%>/kaptcha" id="kaptchaImage" alt="验证码"
                                        class="validateCode" style="cursor: pointer;" />
                                </div>
                                <a href="javascript:void()" class="btn-login btn-email" onclick="javascript:submitEmail();">发送邮件</a>
                                <a href="<%=request.getContextPath()%>/login" class="btn-login btn-return">返回登录</a>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose>
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/user_pwd_reset.js?version=3.3.6"></script>
    <script type="text/javascript">
    //根据视口和文档的宽高设置背景图片的尺寸
    utils.setDocSize();
    </script>

</body>
</html> 