<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计</title>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css"> 
<link href="//cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">    
<link href="<%=request.getContextPath() %>/dist/css/celloud.css?version=1.0" rel="stylesheet" type="text/css" />
<%-- <link href="<%=request.getContextPath() %>/css/count.css?version=2015081806" rel="stylesheet"> --%>
</head>
<body>
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
	  <input type="hidden" id="sessionUserId" value="${session.userId }">
	  <input type="hidden" id="sessionUserName" value="${session.userName }">
	  <section class="content">
	    <div class="row">
	      <div class="col-xs-12">
	        <div class="box box-success color-palette-bo">
	          <div class="box-body bg-green" style="padding-left:30px;padding-bottom: 0px;">
	            <h3 style="font-family:黑体;color:#FFFFFF">
	            	资源监控
	<!--             	<a type="submit" class="text-yellow" style="font-size:12px;margin-left:10px">信息导出</a> -->
	            </h3>
	            <p style="font-family:黑体;color:#FFFFFF">本页提供所有数据报告总览。</p>
	            <button class="btn btn-success btn-flat" style="width:150px;height:70px;margin-right:0px" id="toDataCount">数据</button>
	            <button class="btn btn-success bg-green-active btn-flat" style="width:150px;height:70px;margin-right:0px">报告</button>
	          </div><!-- /.box-body -->
	        </div><!-- /.box -->
	        <div id="countDiv">
	        </div>
	      </div>
	    </div><!--/.row-->
	  </section><!-- /.content -->
	</div><!-- /.content-wrapper -->
  </div><!-- /.wrapper -->
  <script src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
  	$.get("pgsReport!toPgsCount",{"pgs.userId":$("#sessionUserId").val(),"pgs.username":$("#sessionUserName").val()},function(responseText){
  		$("#countDiv").html(responseText);
  	});
  	$("#toDataCount").click(function(){
  		
  	});
  })
  </script>
</body>
</html>