<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="pro-body">
	<input type="hidden" id="sessionCompanyId" value="${companyId }">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>统计</li>
    </ol>
    <div class="content">
    	<div class="mainpage" id="appMain">
	      <div class="content-header">
	      <!-- 这里不能写死ID，应该用是否拥有APP来判断 -->
	        <c:if test="${companyId==6 }">
			    <div class="info">
		    	  <p>本页提供所有数据报告总览。若表格中信息显示不完全，请下载Excel文件查看。</p>
		  		</div>
	     	  </c:if>
	     	  <c:if test="${companyId==3 || companyId == 966 || companyId == 965 || companyId == 961}">
	     	    <div class="info">
		    	  <p>目前只提供 HBV_SNP流程的报告统计；查看页面中不提供序列详细信息的直接展示，用户可以通过“下载Excel”获得全部序列信息。</p>
		  		</div>
	     	  </c:if>
	    	  <c:if test="${companyId==33 }">
	    	    <div class="info">
		    	  <p>查看页面中不提供序列详细信息的直接展示，用户可以通过“下载Excel”获得全部序列信息。</p>
		  		</div>
		       </c:if>
		       <c:if test="${companyId!=3 && companyId!=6 && companyId!=33 && companyId!=961 && companyId!=965 && companyId!=966 }">
	    	    <div class="info">
		    	  <p>很抱歉，暂时没有为该用户提供的统计信息，敬请期待。</p>
		  		</div>
		       </c:if>
		  		<ul>
			      <li><a href="javascript:void(0)" id="_down">下载 Excel</a></li>
		  		</ul>
	      </div>
	      <div id="countDiv">
	      
	      </div>
	     </div>
	</div>
</div>
  
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
<script src="<%=request.getContextPath()%>/js/count.js?version=3.3.7" type="text/javascript"></script>
