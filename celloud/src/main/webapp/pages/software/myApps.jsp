<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<c:choose>
	<c:when test="${empty appList}">
	  <div class="col-md-12">结果为空 </div>
	</c:when>
	<c:otherwise>
		<c:forEach items="${appList}" var="app">
		  <div class="col-md-12">
		    <div class="info-box">
			  <div class="app-info-icon">
				<img src="<%=request.getContextPath()%>/images/app/${app.pictureName}" class="Absolute-Center" style="width:90px;margin:0px">
			  </div>
			  <div class="info-box-content app-info-content">
			      <span class="info-box-text">${app.softwareName }</span>
			      <div>
				      <span class="info-box-text">软件分类：${app.classifyNames } &nbsp;&nbsp;&nbsp; &nbsp;上线时间：${app.createDate }</span>
			      </div>
			      <p class="info-box-artic">${app.intro }</p>
			  	  <p class="info-box-artic">发布机构：${app.companyName }</p>
			  	  <a class="a-green-normal" style="position:absolute;bottom:20px;" href="javascript:void()" onclick="toAppDetail(${app.softwareId})">查看APP详细</a>
			      <button class="btn btn-success btn-flat btn-xs info-btn"><i class="fa fa-minus"></i>取消</button>
			  </div><!-- /.info-box-content -->
		    </div><!-- /.info-box -->
		  </div><!-- /.col -->
		</c:forEach>
	</c:otherwise>
</c:choose>