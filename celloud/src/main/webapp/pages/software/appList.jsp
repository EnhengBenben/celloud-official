<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul class="appitem">
  <c:forEach items="${classifyMap.pclassify}" var="classify">
 	 <li class="types-options big_item" id="pclassify${classify.classifyId }">${classify.classifyName }</li>
  </c:forEach>
</ul>
<ul class="appitem">
  <li class="types-options second_item selected" id="sclassifyAll">全部</li>
  <c:forEach items="${classifyMap.sclassify}" var="classify">
 	 <li class="types-options second_item" id="sclassify${classify.classifyId }">${classify.classifyName }</li>
  </c:forEach>
</ul>
<%-- <c:choose> --%>
<%--   <c:when test="${fn.length(appList)>0}"> --%>
	<c:forEach items="${appList}" var="app">
	  <div class="col-md-3" style="margin-left:-15px;">
	    <div class="info-box">
		  <div class="info-box-icon bg-green">
		    <img src="<%=request.getContextPath()%>/images/app/${app.pictureName}">
		  </div>
		  <div class="info-box-content">
		      <span class="info-box-text">${app.softwareName }</span>
		      <span class="info-box-text">${app.description }</span>
		      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
		  </div><!-- /.info-box-content -->
	    </div><!-- /.info-box -->
	  </div><!-- /.col -->
	</c:forEach>
<%--   </c:when> --%>
<%--   <c:otherwise> --%>
<%--   </c:otherwise> --%>
<%-- </c:choose> --%>