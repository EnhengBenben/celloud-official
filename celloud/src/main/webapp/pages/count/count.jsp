<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<%=request.getContextPath() %>/dist/css/productDetail.css" rel="stylesheet" type="text/css" />
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:void(0)"><i class="fa fa-heartbeat"></i> 统计</a></li>
    <li class="active">全部</li>
  </ol>
</section>
<input type="hidden" id="sessionUserId" value="${session.userId }">
<input type="hidden" id="sessionUserName" value="${session.userName }">
<input type="hidden" id="sessionCompanyId" value="${session.companyId }">
<section class="content">
  <div class="row">
    <div class="col-xs-12">
     <div class="mainpage" id="appMain">
       <div class="y-row operation-serve box box-success"  data-spm="16">
		  <c:if test="${session.companyId==6 }">
		    <div class="info">
	    	  <p>本页提供所有数据报告总览。</p>
	  		</div>
     	  </c:if>
     	  <c:if test="${session.companyId==3 }">
     	    <div class="info">
	    	  <p>目前只提供 HBV_SNP流程的报告统计；查看页面中不提供序列详细信息的直接展示，用户可以通过“下载Excel”获得全部序列信息。</p>
	  		</div>
	  		<ul>
		      <li><a href="javascript:void(0)" id="_down">下载 Excel</a></li>
	  		</ul>
     	  </c:if>
    	  <c:if test="${session.companyId==33 }">
    	    <div class="info">
	    	  <p>目前只提供 CMP流程的统计；查看页面中不提供序列详细信息的直接展示，用户可以通过“下载Excel”获得全部序列信息。</p>
	  		</div>
	  		<ul>
		      <li><a href="javascript:void(0)" id="_down">下载 Excel</a></li>
	  		</ul>
	       </c:if>
	  	</div>
	    <div class="y-row" style="padding: 20px 10px;background-color: #fff;"  data-spm="17">
	       <div class="common-normal common-slide common-normals" id="countDiv">
	       </div>
	     </div>
	  </div>
    </div>
  </div><!--/.row-->
</section><!-- /.content -->
  
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
