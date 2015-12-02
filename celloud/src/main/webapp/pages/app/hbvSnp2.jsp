<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>HBV_SNP2</title>
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
          <a class="btn btn-navbar openReport" data-toggle="collapse" data-target=".nav-collapse" href="javascript:void(0)">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="javascript:void(0)">HBV_SNP2</a>
          <div class="">
            <ul class="nav pull-right">
              <li><a href="javascript:void(0)" class="openReport">查看报告</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
    <div class="container-fluid">
    	<div class="row-fluid">
			<div class="alert alert-info">
		    	<strong>介绍：</strong> 乙肝病毒（HBV）抗药性检测，通过检测乙肝病毒DNA特定序列片段中的SNP，判断其对各种药物的敏感性，包括“拉米夫定LAM”、“替比夫定LDT”、“恩替卡韦ETV”、“阿德福韦ADV”、“替诺福韦酯TDF”和“恩曲他滨FTC”等6种耐药的检测。
		    	<br />
		    </div>
		</div>
		<div class="row-fluid" id="checkDataDiv">
			<div class="span12">
				<p class="text-info">请选择运行数据:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="openUpload" title="上传数据"><img src="<%=request.getContextPath()%>/images/publicIcon/icon-update.png"/></a></p>
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
<script type="text/javascript" src="<%=request.getContextPath() %>/js/run.js?v=0.2"></script>
</body>
</html>