<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 优先使用最新版本的IE 和 Chrome 内核 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="baidu-site-verification" content="IsldTuHqik" />
<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<title>CelLoud 服务与支持</title>
<meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<meta name="description" content="一站式高通量基因检测数据分析系统">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico"/>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico"/>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/feedback.css" />
<link href="<%=request.getContextPath() %>/css/download.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container">
    <div class="header phone_padding" >
        <div class="clearfix">
            <h1 class="fl"><a href="<%=request.getContextPath() %>/">celloud</a></h1>
            <div class="nav fr">
                <a href="#" onclick="javascript:clearForm();" id="toMe">投诉建议</a> 
            </div>
        </div>
    </div>
    <div class="main phone_padding">
    	<div class="longmargin">
            <div id="selfSay">  
                <p class="fs16">欢迎使用CelLoud生物信息云平台！您在使用平台的过程中遇到的问题，或者您对平台的改进有什么好的想法和建议，都可以向我们提出。我们会认真阅读您的建议，不断改进我们的工作。
				<p><span class="txt-span">姓名：</span><input type="text" class="feedback-small" id="userName">
                   	<input type="checkbox" id="chkUsername">&nbsp;匿名
                   	<span id="userNameSpanInfo" class="error"></span>
                </p>
                <p><span class="txt-span">邮箱：</span><input type="text" class="input-normal" id="email">
                   <span id="emailSpanInfo" class="error"></span>
                </p>
                <p><span class="txt-span">标题：</span><input type="text" class="input-normal" id="title" placeholder="请输入4~30个字符">
                   <span id="titleSpanInfo" class="error"></span>
                </p>
                <p><span class="txt-span">内容：</span><textarea rows="9" id="content" placeholder="请输入10~10000个字符"></textarea>
                   <span id="contentSpanInfo" class="error"></span>
                </p>
                <p class="submitFoot">
               		<span id="submitSpanInfo" class="success">&nbsp;</span>
               		<a href="javascript:submitFeedBack();" class="submitBtn">提交</a>
                </p>
            </div>
        </div>
    </div>
    <jsp:useBean id="_now" class="java.util.Date" />
    <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> CelLoud，Inc. All Rights reserved.<br/><a  href="<%=request.getContextPath() %>/">生物信息云平台</a> · <a  href="javascript:void();">沪ICP备14035977号</a></div>
</div>
<script type="text/javascript">
	var context = '<%=request.getContextPath() %>';
	var userName = '<%=session.getAttribute("userName")%>';
</script>
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/utils.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/feedback_phone.js"></script>
<script type="text/javascript">
$.ajaxSetup ({
    cache: false //关闭AJAX相应的缓存
});
var editor;
$(document).ready(function(){
	$("#otherSay").html("");
	if(userName!=null&&userName!=""&&userName!="null"){
		$("#userName").val(userName);
	}else{
		$("#userName").val("");
	}
});
</script>
<style type="text/css">
.footer{
	line-height: 30px;
}
.phone_padding{
	padding:  0 10px 0 10px;
}
.submitBtn,.header{
	width: auto;
}
#content{
	width: 280px;
}
.txt-span{
	vertical-align: top;
}
.feedback-small{
    height: 25px;
    width: 230px;
}
</style>
</body>
</html>