<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>translate</title>
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/bootstrap/css/bootstrap.css">
<link rel="stylesheet" type="text/css"	href="<%=request.getContextPath() %>/bootstrap/css/bootstrap-responsive.css">
<link href="<%=request.getContextPath()%>/pages/app/css/style.css" rel="stylesheet" type="text/css" media="screen" />
<script type="text/javascript">
var session_userId = <%=session.getAttribute("userId")%>;
var sessionUserName = "<%=session.getAttribute("userName")%>";
//检验session是否超时
if(!session_userId){
	window.top.location = "<%=request.getContextPath() %>/toLogin";
}
</script>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container-fluid">
          <a class="brand" href="javascript:void(0)">Translate Simplified</a>
          <ul class="nav pull-right">
            <li><a href="javascript:void(0)" class="openReport">查看报告</a></li>
          </ul>
        </div>
      </div>
    </div>
    <div class="container-fluid">
    	<div class="row-fluid">
			<div class="span12">
				<p class="text-info font-big">直接运行:&nbsp;&nbsp;</p>
				<p class="text-info">粘贴原始序列或一个或多个FASTA序列到下面的文本区域。输入限制为5000个字符，最大可接受的引物长度50个碱基。</p>
				<form action="" name="main_form" id="main_form">
                   	<textarea name="sequence" id="sequence" class="span9" rows="6"></textarea><br />
                    <input type="button" value="运行" id="submit" class="btn btn-info"/>
				    <input type="button" value="清空" id="reset" class="btn"/>
				    <input type="button" value="随机" id="random" class="btn"/>
				    <span class="run_success" id="runAlertInput"></span><br />
               	</form>
			</div>
		</div><hr class="hr0">
    	<div class="row-fluid" id="checkDataDiv">
			<div class="span12">
				<p class="text-info font-big">选择运行:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="openUpload" title="上传数据"><img src="<%=request.getContextPath()%>/images/publicIcon/icon-update.png"/></a></p>
				<form action="" name="data_form" id="sel_data_form">
				  <div id="dataDiv" class="dataForm"></div> 
				  <div class="pull-right pull_day" id="pageDiv"></div>
				  <div class="sel_data_btn">
					<input type="button" value="运行" id="runCheckBox" class="btn btn-info"/>
					<input type="button" value="取消" id="resetCheckbox" class="btn"/>
					<span class="run_success" id="runAlert"></span>
				  </div>
               	</form>
			</div>
		</div>
	</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/run.js?v=0.1"></script>
</body>
</html>