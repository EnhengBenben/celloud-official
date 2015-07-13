<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/content/css/style.css" />
<link href="<%=request.getContextPath() %>/bootstrap/css/style.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/content/css/version.css" media="all" />
<title>系统公告</title>
<style>
._fontTitle{font-size:16px;}
._fontContent{font-size:14px; text-indent: 2em;}
._fontActive{color:#009900}
</style>
</head>
<body>
<input type="hidden" id="baseInfoUserIdHidden" value="<%=session.getAttribute("userId")%>">
<div>
    <div class="alert alert-info">
        <span style="">O(∩_∩)O~</span>
    </div>
     <div id="fileUploadDiv" align="center">
         <table style="width: 70%">
		   <tr>
		      <td>
                <h2 class="_fontTitle"> 尊敬的 <span class="_fontActive">${sessionScope.userName }</span> 您好:</h2><br>
                <p class="_fontContent">系统在 <span id="createDate" class="_fontActive"></span> <span id="noticeTitle" class="_fontActive"></span> ，具体内容如下:</p><br>
                <p id="noticeContext" class="_fontContent"></p>
		      </td>  
		   </tr>
		   <tr>
		      <td colspan="2" align="center" id="alertId">
		        
		      </td>
		   </tr>
		</table>
     </div>
</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
			$.ajaxSetup ({
				cache: false //关闭AJAX相应的缓存
			});
		  $.get("notice!getNewsNotice",{},function(notice){
			  $("#createDate").html(notice.createDate.substring(0,10));
			  $("#noticeTitle").html(notice.noticeTitle);
			  $("#noticeContext").html(notice.noticeContext);
		  });
	});
</script>
</html>