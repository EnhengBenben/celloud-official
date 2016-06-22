<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="backWhite">
	<div class="errorDIV"><img src="<%=request.getContextPath()%>/images/content/error.png" class="errorIcon"></div>
	<div class="view-tips first-tip">很抱歉，您的浏览器版本无法访问本平台</div>
	<div class="view-tips">为了您的浏览体验，请安装如下浏览器：</div>
	<div class="browserDiv">
		<a href="http://www.chromeliulanqi.com/" target="_blank"><img src="<%=request.getContextPath()%>/images/content/chrome.png" class="browserIcon"><span class="prompt">Chrome</span></a>
		<a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie" target="_blank"><img src="<%=request.getContextPath()%>/images/content/ie.png" class="browserIcon img-left"><span class="prompt">IE 9.0+</span></a>
		<a href="http://www.firefox.com.cn/" target="_blank"><img src="<%=request.getContextPath()%>/images/content/firefox.png" class="browserIcon img-left"><span class="prompt">Firefox 8.0+</span></a>
	</div>
</div>
<style type="text/css">
.login{
	width: auto;
}
.backWhite{
    border: 1px solid #aacd06;
    -moz-border-radius: 15px;      /* Gecko browsers */
    -webkit-border-radius: 15px;   /* Webkit browsers */
    border-radius:15px;            /* W3C syntax */
	background-color: #FFFFFF;
	width: 400px;
	height: 140px;
}
.errorDIV{
	float: left;
}
.errorIcon{
	width: 84px;
	height: 84px;
	margin: 28px 15px 28px 20px;
}
.view-tips{
	text-align:justify;
	font-family:微软雅黑;
	font-size:14px;
	font-weight:bold;
	color:#a0a0a0;
	margin: 10px 0px 10px 0px;
}
.first-tip{
	margin: 28px 20px 0 0;
}
.browserDiv{
	margin-top: 20px;
	text-align:justify;
}
.browserIcon{
	width: 25px;
	height: 25px;
}
.img-left{
	margin-left: 10px;
}
.prompt{
	font-family:微软雅黑;
	font-size:9px;
	color:#a0a0a0;
}
</style>
