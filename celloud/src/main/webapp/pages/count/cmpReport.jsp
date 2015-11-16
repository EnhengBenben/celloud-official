<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="table_">
  <thead>
    <tr>
      <th style="min-width:90px;">数据编号</th>
      <th style="min-width:60px">原始文件名</th>
      <th style="min-width:60px">共获得有效片段</th>
      <th style="min-width:70px">可用片段</th>
      <th style="min-width:100px">平均测序深度</th>
      <th style="min-width:60px">基因检测结果</th>
    </tr>
  </thead>
  <tbody>
  <c:choose>
  	<c:when test="${cmpList.size()>0}">
  	  <c:forEach items="${cmpList }" var="cmp" varStatus="cmpSta">
	    <tr>
	      <td align="center">${cmp.dataKey }</td>
	      <td align="center">${cmp.data[0].fileName }<br>${cmp.data[1].fileName }</td>
<!-- 	      <td align="center"></td> -->
	      <td align="center">${cmp.allFragment }</td>
	      <td align="center">${cmp.usableFragment }</td>
	      <td align="center">${cmp.avgCoverage }</td>
	      <td align="center">
	        <c:forEach items="${cmp.cmpGeneResult}" var="gene">
	          <c:if test="${gene.knownMSNum>0}">
	          	${gene.geneName }:${gene.knownMSNum };
	          </c:if>
			</c:forEach>
	      </td>
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
<input type="hidden" value="report3!download?fileName=${infos }" id="downUrl"></input>