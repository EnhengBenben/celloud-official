<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="table_">
  <thead>
    <tr>
      <th style="min-width:40px">序号</th>
      <th style="min-width:120px">数据编号</th>
      <th style="min-width:70px">Barcode</th>
      <th style="min-width:70px">数据别名</th>
      <th style="min-width:70px">上传日期</th>
      <th style="min-width:70px">APP</th>
      <th style="min-width:90px">Total_Reads</th>
      <th style="min-width:90px">Map_Reads</th>
      <th style="min-width:100px">Map_Ratio(%)</th>
      <th style="min-width:80px">Duplicate</th>
      <th style="min-width:100px">GC_Count(%)</th>
      <th style="min-width:60px">*SD</th>
    </tr>
  </thead>
  <tbody>
  <c:choose>
  	<c:when test="${list.size()>0}">
  	  <c:forEach items="${list }" var="pgs" varStatus="pgsSta">
	    <tr>
	      <td align="center"><c:out value="${pgsSta.index}"/></td>
	      <td align="center">${pgs.dataKey }</td>
	      <td align="center"><c:set var="fileName" value="${fn:split(pgs.fileName, '_')}" />
	      	<c:choose>
		      	<c:when test="${fn:length(fileName)>2}">
					<c:forEach items="${fileName }" var="fname" begin="0" end="1" varStatus="status">
						${fname }<c:if test="${status.count == 1}">_</c:if>
					</c:forEach>
		      	</c:when>
		      	<c:otherwise>
		      		<c:out value='${fn:substringBefore(pgs.fileName, ".")}'></c:out>
		      	</c:otherwise>
	      	</c:choose>
	      </td>
	      <td align="center">${pgs.anotherName }</td>
	      <td align="center"><fmt:formatDate value="${pgs.uploadDate }" type="date"/> </td>
	      <td align="center">${pgs.appName }</td>
	      <td align="center">${pgs.totalReads }</td>
	      <td align="center">${pgs.mapReads }</td>
	      <td align="center">${pgs.mapRatio }</td>
	      <td align="center">${pgs.duplicate }</td>
	      <td align="center">${pgs.gcCount }</td>
	      <td align="center">${pgs.sd }</td>
	    </tr>
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
