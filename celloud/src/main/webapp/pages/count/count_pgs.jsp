<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="table table-main no-padding-left count-table">
  <thead>
    <tr style="padding: 0px;">
      <th>序号</th>
      <th>数据编号</th>
      <th>Barcode</th>
      <th>数据别名</th>
      <th>上传日期</th>
      <th>APP</th>
      <th>Total_Reads</th>
      <th>Map_Reads</th>
      <th>Map_Ratio(%)</th>
      <th>Duplicate</th>
      <th>GC_Count(%)</th>
      <th>*SD</th>
    </tr>
  </thead>
  <tbody>
  <c:choose>
  	<c:when test="${map.data.size()>0}">
  	  <c:forEach items="${map.data }" var="pgs" varStatus="pgsSta">
  	  <!-- tr与td如果换行的话ie9会有bug -->
	    <tr style="padding: 0px;"><td align="center"><c:out value="${pgsSta.index}"/></td><td align="center">${pgs.dataKey }</td><td align="center"><c:set var="fileName" value="${fn:split(pgs.fileName, '_')}" /><c:set var="name" value=""></c:set><c:choose><c:when test="${fn:length(fileName)>2}"><c:forEach items="${fileName }" var="fname" begin="0" end="1" varStatus="status"><c:set var="name" value="${name.concat(fname) }"></c:set><c:if test="${status.count == 1}"><c:set var="name" value="${name.concat('_') }"></c:set></c:if></c:forEach></c:when><c:otherwise><c:set var="name" value="${fn:substringBefore(pgs.fileName, '.') }"></c:set></c:otherwise></c:choose>${name.length() > 16 ? name.substring(0,11).concat('...') : name }</td><td align="center">${pgs.anotherName.length() > 14 ? (pgs.anotherName.substring(0,11).concat('...')) : pgs.anotherName }</td><td align="center"><fmt:formatDate value="${pgs.uploadDate }" pattern="yyyy-MM-dd"/></td><td align="center">${pgs.appName }</td><td align="center">${pgs.totalReads }</td><td align="center">${pgs.mapReads }</td><td align="center">${pgs.mapRatio }</td><td align="center">${pgs.duplicate }</td><td align="center">${pgs.gcCount }</td><td align="center">${pgs.sd }</td></tr>
  	  </c:forEach>
  	</c:when>
  	<c:otherwise>
  	 <tr>
	   <td colspan="12">还没有数据报告结果哦~</td>
	 </tr>
  	</c:otherwise>
  </c:choose>
  </tbody>
</table>
<c:if test="${map.data.size()>0}">
	<input type="hidden" value="count/download?fileName=${map.fileName }" id="downUrl"></input>
</c:if>
