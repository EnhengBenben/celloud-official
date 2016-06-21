<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="backWhite">
	<div class="errorDIV"><img src="<%=request.getContextPath()%>/images/content/error.png" class="errorIcon"></div>
	<div class="view-tips">很抱歉，您的浏览器版本无法访问本平台</div>
	<div class="view-tips">为了您的浏览体验，请安装如下浏览器：</div>
	<div class="view-tips">
		<a href="http://www.chromeliulanqi.com/" target="_blank"><img src="<%=request.getContextPath()%>/images/content/chrome.png" class="browserIcon">Chrome</a>
		<a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie" target="_blank"><img src="<%=request.getContextPath()%>/images/content/ie.png" class="browserIcon">IE 9.0+</a>
		<a href="http://www.firefox.com.cn/" target="_blank"><img src="<%=request.getContextPath()%>/images/content/firefox.png" class="browserIcon">Firefox 8.0+</a>
	</div>
</div>
<style type="text/css">
.backWhite{
    border: 5px solid #dedede;
    -moz-border-radius: 15px;      /* Gecko browsers */
    -webkit-border-radius: 15px;   /* Webkit browsers */
    border-radius:15px;            /* W3C syntax */
	background-color: #FFFFFF;
	min-width: 400px;
}
.view-tips{
	margin: 10px 0px 10px 0px;
}
.browserIcon{
	width: 20px;
	height: 20px;
}
.errorIcon{
	width: 75px;
	height: 75px;
	margin: 10px;
}
.errorDIV{
	float: left;
}
.login{
	width: auto;
}
</style>
