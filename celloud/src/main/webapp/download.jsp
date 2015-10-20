<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="baidu-site-verification" content="IsldTuHqik" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<title>CelLoud 客户端下载</title>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<link href="css/download.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <div class="header" >
        <div class="wrapper clearfix">
            <h1 class="fl"><a href="<%=request.getContextPath() %>/">celloud</a></h1>
            <div class="nav fr">
                <a href="#">下载客户端</a> 
<!--                 <a href="#">服务与支持</a>  -->
<!--                 <a href="#">意见反馈</a>  -->
            </div>
        </div>
    </div>
    <div class="main download">
    	<div class="wrapper">
        	<h2>生物信息云平台客户端上传工具</h2>
            <p class="fs16">提供高速、稳定的基因传输通道<br />针对基因序列数据的传输而打造<br />数据上传从哪里跌倒从哪里开始<br />多个数据同时传输，可以暂停、取消<br />目前仅支持xp、win7、win8操作系统</p>
            <a href="http://client.celloud.org/CelLoudClient_x86_0.0.3.rar" class="downloadApp"><img src="images/home/downloadApp.png" />下载客户端</a>
            <h3>客户端安装使用过程中可能出现的问题及解答</h3>
            <p>A.安装时提示：尚未安装.net framework 4.0<br />
            请到官网<a target="_blank" href="http://www.microsoft.com/zh-cn/download/details.aspx?id=17718">.net4.0</a>下载安装。如果网络速度不好也可以在我们提供地址进行下载，下载地址为：http://client.celloud.org/Microsoft.NET.exe</p>
            <p>B.杀毒软件不信任问题<br />
            软件在安装过程可能会被杀毒软件删除某些关机文件，如果安装不成功，请关闭杀毒软件后再次尝试安装。</p>
            <p>C.安装成功后双击桌面图标不能启动客户端<br />
            安装完成后，点击桌面的快捷方式不能正常启动，可以使用管理员权限进行启动。</p>
        </div>
    </div>
    <jsp:useBean id="_now" class="java.util.Date" />
    <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> CelLoud，Inc. All Rights reserved. <a  href="home.html">生物信息云平台</a> · <a  href="javascript:void();">沪ICP备14035977号</a> · <a href="service.html" target="_blank">服务与支持</a> · <a href="content/feedBack.html" target="_blank">意见反馈</a></div>
</div>
</body>
</html>