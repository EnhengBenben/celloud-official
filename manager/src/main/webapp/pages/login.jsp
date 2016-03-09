<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<link href="plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="css/fonts/font-awesome.min.css" />
<link rel="stylesheet" href="css/analy.min.css" />
<link rel="stylesheet" type="text/css" href="css/login.css" media="all" />
<title>CelLoud 用户登录</title>
</head>
<body class="bgContainer" onselectstart="return false;">
<div class="navbar">
    <div class="container">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" style="margin-top:-20px;" href="http://www.celloud.org/"><img src="images/logo.png" width="186"></a><h2 style="margin-top:5px;">统计系统</h2>
    </div>
</div>
<div class="login-content">
   	<div class="row">
        <div class="col-sm-4 col-sm-offset-4">
            <div class="position-relative">
				<div id="login-box" class="login-box visible widget-box no-border">
					<div class="widget-body">
						<div class="widget-main">
							<h4 class="header blue lighter bigger">
								<i class="icon-coffee green"></i>
								请输入您的账号信息
							</h4>

							<div class="space-6"></div>

							<form action="login" method="post" id="loginForm">
							<input type="hidden" id="modulusHidden" name="modulus"
		                        value="${publicKey.modulus }" />
		                    <input type="hidden" id="exponentHidden" name="exponent"
		                        value="${publicKey.exponent }" />
		                    <input type="hidden" name="checked" id="checked"
		                        value="${requestScope.checked }" />
		                    <input type="hidden" name="info" id="info"
		                        value="${requestScope.info }" />
								<fieldset>
									<label class="block clearfix">
										<span class="block input-icon input-icon-right">
											<input type="text" class="form-control" placeholder="用户名或邮箱" id="username" name="username" value="${requestScope.user.username }"/>
											<i class="icon-user"></i>
										</span>
									</label>

									<label class="block clearfix">
										<span class="block input-icon input-icon-right">
											<input type="password" class="form-control" placeholder="密码" id="password"
                        value="${fn:substring(requestScope.user.password,0,16)}"/>
											<input type="hidden" name="password" value="${user.password }"/>
											<i class="icon-lock"></i>
										</span>
									</label>
									<div class="block clearfix">
										<label class="col-sm-10">
											<input style="margin-left:-15px;" type="text" class="form-control" placeholder="验证码"  id="captcha"
                            name="kaptchaCode" value="${requestScope.kapcode }"/>
					                	</label>
					                	<div class="col-sm-2">
						                	<img style="margin-left:-30px;" title="看不清，换一张" src="kaptcha.jpg" id="kaptchaImage" alt="验证码" class="validateCode" style="cursor: pointer;"/>
					                	</div>
					                </div>
					                <div class="autolog">
				                        <span id="remPass">
				                            <img src="<%=request.getContextPath()%>/images/icon/nocheck.png" />
				                        </span>
				                        <input id="isRem" name="isRem" value="0" style="display: none">
				                        记住密码
				                        <span class="error"></span>
				                    </div>
					                <div style="height:25px;">
					                	<div class="text-warning bigger-110 orange hide" id="loginInfoDiv"><i class="icon-warning-sign"></i><span id="loginInfo">${info }</span></div>
					                </div>
									<div class="clearfix" style="text-align:center;">
										<a href="javascript:void(0);" class="width-35 btn btn-sm btn-primary" id="submit"><i class="icon-key"></i>登录</a>
									</div>

									<div class="space-4"></div>
								</fieldset>
							</form>
						</div><!-- /widget-main -->
						<div class="toolbar clearfix">
							<div>
								<a href="http://www.celloud.org/forgetPwd" target="_blank" class="forgot-password-link">
									<i class="icon-arrow-left"></i>
									忘记密码
								</a>
							</div>
						</div>
					</div><!-- /widget-body -->
				</div><!-- /login-box -->
			</div><!-- /position-relative -->
        </div>
     </div>
</div>
<!--#E bgContainer-->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-2.0.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/utils.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/tologin.js?version=2.0"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/md5.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/security.js"></script>
<script type="text/javascript">
//session超时iframe完全退出
if(window.top!=window.self){
    window.top.location = "<%=request.getContextPath() %>/toLogin";
}
//根据视口和文档的宽高设置背景图片的尺寸
setDocSize();
</script>
</body>
</html>