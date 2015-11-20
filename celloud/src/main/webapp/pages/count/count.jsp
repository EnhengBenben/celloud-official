<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统计</title>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css"> 
<link href="//cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">    
<link href="<%=request.getContextPath() %>/dist/css/celloud.css?version=1.2" rel="stylesheet" type="text/css" />
<%-- <link href="<%=request.getContextPath() %>/css/count.css?version=2015081806" rel="stylesheet"> --%>
</head>
<body>
  <div class="wrapper">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
	  <input type="hidden" id="sessionUserId" value="${session.userId }">
	  <input type="hidden" id="sessionUserName" value="${session.userName }">
	  <input type="hidden" id="sessionCompanyId" value="${session.companyId }">
	  <section class="content">
	    <div class="row">
	      <div class="col-xs-12">
		      <c:if test="${session.companyId==6 }">
		        <div class="box box-success color-palette-bo">
		          <div class="box-body bg-green" style="padding-left:30px;padding-bottom: 0px;">
		            <h3 style="font-family:黑体;color:#FFFFFF">
		            	资源监控
		<!--             	<a type="submit" class="text-yellow" style="font-size:12px;margin-left:10px">信息导出</a> -->
		            </h3>
		            <p style="font-family:黑体;color:#FFFFFF">本页提供所有数据报告总览。</p>
<!-- 		            <button class="btn btn-success btn-flat" style="width:150px;height:70px;margin-right:0px" id="toDataCount">数据</button> -->
<!-- 		            <button class="btn btn-success bg-green-active btn-flat" style="width:150px;height:70px;margin-right:0px">报告</button> -->
		          </div>
		        </div>
		      </c:if>
		      <c:if test="${session.companyId==3 }">
		        <div class="box box-success color-palette-bo">
		          <div class="box-body bg-green" style="padding-left:30px;padding-bottom: 0px;">
		            <h3 style="font-family:黑体;color:#FFFFFF">
		            	运行统计
		            	<a type="button" class="text-yellow" style="font-size:14px;margin-left:10px">目前只提供 HBV_SNP 流程的统计</a>
		            </h3>
		            <p style="font-family:黑体;color:#FFFFFF">查看页面中不提供序列详细信息的直接展示，用户可以通过“下载Excel”获得全部序列信息。</p>
		            <a class="btn btn-success btn-flat" style="width:100px;height:35px;margin-right:0px" href="javascript:void(0)" id="_down">下载 Excel</a>
		          </div>
		        </div>
		      </c:if>
		      <c:if test="${session.companyId==33 }">
		        <div class="box box-success color-palette-bo">
		          <div class="box-body bg-green" style="padding-left:30px;padding-bottom: 0px;">
		            <h3 style="font-family:黑体;color:#FFFFFF">
		            	运行统计
		            	<a type="button" class="text-yellow" style="font-size:14px;margin-left:10px">目前只提供 CMP 流程的统计</a>
		            </h3>
		            <p style="font-family:黑体;color:#FFFFFF">查看页面中不提供序列详细信息的直接展示，用户可以通过“下载Excel”获得全部序列信息。</p>
		            <a class="btn btn-success btn-flat" style="width:100px;height:35px;margin-right:0px" href="javascript:void(0)" id="_down">下载 Excel</a>
		          </div>
		        </div>
		      </c:if>
	        <div id="countDiv">
	        </div>
	      </div>
	    </div><!--/.row-->
	  </section><!-- /.content -->
	</div><!-- /.content-wrapper -->
  </div><!-- /.wrapper -->
  
<div class="modal modal-green-header" id="seqModal">
 <div class="modal-dialog">
  <div class="modal-content">
  <div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h4 class="modal-title">查看序列</h4>
	</div>
	<div class="modal-body row">
		<div class="col-sm-12" id="showSeq" style="word-break:break-all;">
			
		</div>
	</div>
	<div class="modal-footer">
         <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
	</div>
  </div>
 </div>
</div>

  <script src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
  <script src="<%=request.getContextPath() %>/plugins/bootstrap-modal.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){
	  var companyId = $("#sessionCompanyId").val();
	  if(companyId==6){
	  	$.get("report3!toPgsCount",{"pgs.userId":$("#sessionUserId").val(),"pgs.username":$("#sessionUserName").val()},function(responseText){
	  		$("#countDiv").html(responseText);
	  	});
	  }else if (companyId==3){
		$.get("report3!toHBVCount",function(responseText){
			$("#countDiv").html(responseText);
			var url = $("#downUrl").val();
			$("#_down").attr("href",url);
		});
	  }else if(companyId==33){
		$.get("cmpReport!toCmpCount",function(responseText){
			$("#countDiv").html(responseText);
			var url = $("#downUrl").val();
			$("#_down").attr("href",url);
		});
	  }
  	$("#toDataCount").click(function(){
  		
  	});
  })
  function showSeq(seq){
	  $("#showSeq").html(seq);
	  $("#seqModal").modal("show");
  }
  function showGeneResult(result){
	  $("#showGeneResult").html(result);
	  $("#geneResultModal").modal("show");
  }
  </script>
</body>
</html>