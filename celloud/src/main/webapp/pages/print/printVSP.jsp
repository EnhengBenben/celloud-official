<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/celloud/css/style_print.css?version=1.0">
<style type="text/css">
.w3cbbs { page-break-after:always;}
</style>
</head>
<body>
<div>
	<a href="javascript:void(0)" onclick="preview(this)" class="button btn-info" id="change" style="float:right;margin-top:20px;margin-right:30px;"><i class=""></i>打印</a>
    <div class="wrapper">
        ${context}
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" src="/celloud/js/browser.js"></script>
<script type="text/javascript">
function preview(obj){
	var inputVal;
	$("body").find("input[type='text']").each(function(){
		inputVal = $(this).val();
		$(this).parent().html("<span name='print'>"+inputVal+"</span>");
	});
	var sex = $("input[type='radio']:checked").val();
	$("#_sex").html(sex);
	$("#change").hide();
	window.print();
	$("#change").show();
	$("body").find("span[name='print']").each(function(){
		inputVal = $(this).html();
		$(this).parent().html("<input type='text' value='"+inputVal+"'>");
	});
	$("#_sex").html("<input type='radio' name='sex' value='男'>男<input type='radio' name='sex' value='女'>女");
	$("input[type='radio'][value="+sex+"]").attr("checked",true); 
}
$(document).ready(function(){
	var browser = $.NV('name');
	var height;
	if(browser=='firefox'){
		height = 1480;
	}else if(browser=='chrome'){
		height = 2500;
	}
	if($("#mainDIv").height()>1000){
		$("#mainDIv").css("min-height",height+"px");
	}
});
</script>
</body>
</html>