<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="baidu-site-verification" content="IsldTuHqik" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<title>CelLoud 客户端下载</title>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico"/>
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
            <p class="fs16">提供高速稳定的基因传输通道<br />数据上传支持断点续传，支持多文件同时传输<br />根据客户端机器的配置自动判别同时传输的文件数量<br />支持xp、win7、win8、win10操作系统，提供x86和x64位版本</p>
            <a href="http://client.celloud.org/${client.nameX86 }.exe" class="downloadApp"><img src="images/home/downloadApp.png" />下载客户端</a>
            <h3>客户端安装使用过程中可能出现的问题及解答</h3>
            <p>A.杀毒软件不信任问题<br />
            软件下载后直接解压缩即可使用，使用请把该软件加入杀毒软件可信列表中或者暂时关闭杀毒软件。</p>
            <p>B.软件x86和x64位版本<br />
            软件默认提供x86版本下载，如果您的操作系统是x64位操作系统，也可以<a target="_blank" href="http://client.celloud.org/${client.nameX64 }.exe">【下载x64】</a>位软件。</p>
            <p>C.目前软件处于测试版本中，如果遇到问题，可以直接发送邮件到：service@celloud.cn 给我们反馈</p>
            <p>D.软件安装目录推荐解压缩英文目录中</p>
        </div>
    </div>
    <jsp:useBean id="_now" class="java.util.Date" />
    <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> CelLoud，Inc. All Rights reserved. <a  href="#">生物信息云平台</a> · <a  href="javascript:void();">沪ICP备14035977号</a> · <a href="<%=request.getContextPath() %>/service.html" target="_blank">服务与支持</a> · <a href="<%=request.getContextPath() %>/feedback.html" target="_blank">意见反馈</a> · <a id="toshgsA" href='https://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=1atr5hendjiu232trv8vb6qred7d9ymqx1ly1i9na5fa0e04ya' target="_blank"><img src='images/home/shgs.png' style="width: 20px; margin-bottom: -5px;" border=0></a><a href='http://net.china.com.cn/' target="_blank"><img src='images/home/jbzx.png' style="width: 20px; margin-bottom: -5px;" border=0></a></div>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
	var url = window.location.href;
	if(url.indexOf(".cn")!=-1){
		$("#toshgsA").attr("href","https://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=1atr5hendjiu232trv8vb6qred7d9ymqx1ly1i9na5fa0e04ya");
	} else if(url.indexOf(".net")!=-1){
		$("#toshgsA").attr("href","https://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=1atr5hendjiu232trv8vb6qred7d9ymqx1ly1i9na5fa0e04yd");
	}else if(url.indexOf(".org")!=-1){
		$("#toshgsA").attr("href","https://www.sgs.gov.cn/lz/licenseLink.do?method=licenceView&entyId=1atr5hendjiu232trv8vb6qred7d9ymqx1ly1i9na5fa0e04yc");
	}
</script>
</body>
</html>