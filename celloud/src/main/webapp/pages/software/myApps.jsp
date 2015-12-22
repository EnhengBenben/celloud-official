<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="nominate" style="display: block;">
  <h5>已添加的APP</h5>
  <ul>
    <c:choose>
		<c:when test="${empty appList}">
		  <div class="col-md-12">结果为空 </div>
		</c:when>
		<c:otherwise>
			<c:forEach items="${appList}" var="app">
			  <li>
			      <a href="#">
			        <img src="<%=request.getContextPath()%>/images/app/${app.pictureName}" alt="">
			      </a>
			      <div class="intro">
			        <h6 style="overflow: hidden;" title="${app.softwareName }">
			          <a class="reco-hd-link" href="javascript:toAppDetail(${app.softwareId })">
			          	${app.softwareName }
			          </a>
			        </h6>
			       	<p><a href="javascript:void()" onclick="removeApp(${app.softwareId })" >取消添加</a></p>
			      </div>
			   </li>
			</c:forEach>
		</c:otherwise>
	</c:choose>
  </ul>
</div>
