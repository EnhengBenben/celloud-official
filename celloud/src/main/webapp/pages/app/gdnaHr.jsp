<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>gDNA_HR</title>
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
          <a class="brand" href="javascript:void(0)">gDNA_HR</a>
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
		    	<strong>介绍：</strong>gDNA-HR流产组织染色体异倍性分析系统主要功能是针对人类基因组二代测序数据，检测染色体数目畸变。
软件主要基于均值漂移算法对二代测序数据进行分析，软件读取序列比对格式（bam）文件，
通过基因组各染色体不同位置比对序列数量，计算得出染色体不同片段的拷贝数。为临床诊断提供一个参考依据，大大提高临床检验的效率。
软件可以分析得到1M以上的染色体微重复和微缺失。
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
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/run.js"></script>
</body>
</html>