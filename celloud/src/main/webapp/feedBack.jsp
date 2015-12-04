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
<title>CelLoud 服务与支持</title>
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<%-- <link rel="stylesheet" href="<%=request.getContextPath() %>/content/css/style.css" /> --%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/feedback.css" />
<link href="<%=request.getContextPath() %>/css/download.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="container longbackground">
    <div class="header" >
        <div class="wrapper clearfix">
            <h1 class="fl"><a href="<%=request.getContextPath() %>/">celloud</a></h1>
            <div class="nav fr">
                <a href="#" onclick="javascript:clearForm();" id="toMe">投诉建议</a> 
<!--                 <a href="#" onclick="javascript:showFeedBackList();" id="toOther">大家说</a>  -->
            </div>
        </div>
    </div>
    <div class="main">
    	<div class="wrapper longmargin">
<!--             <div id="otherSay" style="height: 1000px;"></div> -->
            <div id="selfSay">  
                <p class="fs16">欢迎使用CelLoud生物信息云平台！您在使用平台的过程中遇到的问题，或者您对平台的改进有什么好的想法和建议，都可以向我们提出。我们会认真阅读您的建议，不断改进我们的工作。
				<p><span class="txt-span">姓名：</span><input type="text" class="input-normal" id="userName">
                   	<input type="checkbox" id="chkUsername">&nbsp;匿名
                   	<span id="userNameSpanInfo" class="error"></span>
                </p>
                <p><span class="txt-span">邮箱：</span><input type="text" class="input-normal" id="email">
                   <span id="emailSpanInfo" class="error"></span>
                </p>
                <p><span class="txt-span">标题：</span><input type="text" class="input-normal" id="title" placeholder="请输入4~30个字符">
                   <span id="titleSpanInfo" class="error"></span>
                </p>
                <p><span class="txt-span">内容：</span><textarea rows="12" cols="100" id="content" placeholder="请输入10~10000个字符"></textarea>
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
    <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> CelLoud，Inc. All Rights reserved. <a  href="<%=request.getContextPath() %>/">生物信息云平台</a> · <a  href="javascript:void();">沪ICP备14035977号</a> · <a href="<%=request.getContextPath() %>/service.html" target="_blank">服务与支持</a> · <a href="<%=request.getContextPath() %>/feedBack.html" target="_blank">意见反馈</a></div>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/ueditor/ueditor.all.js"></script>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
var userName = '<%=session.getAttribute("userName")%>';
var editor;
</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/feedBack.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#otherSay").html("");
	if(userName!=null&&userName!=""&&userName!="null"){
		$("#userName").val(userName);
	}else{
		$("#userName").val("");
	}
	editor = new UE.ui.Editor({initialFrameWidth:800,initialFrameHeight:200,toolbars:[["bold","italic","undo","redo","fullscreen",'underline', '|', 'forecolor', 'insertorderedlist', 'insertunorderedlist', '|','justifyleft', 'justifycenter', 'justifyright', 'justifyjustify','emotion']]});
    editor.render("content");
    clearForm();
});
</script>
</body>
</html>