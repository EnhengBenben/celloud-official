<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table table-main" id="hbv_count">
  <thead>
    <tr>
      <th style="min-width:90px;max-width: 150px;">文件名</th>
      <th style="min-width:60px">I169T</th>
      <th style="min-width:60px">V173L</th>
      <th style="min-width:60px">L180M</th>
      <th style="min-width:70px">A181V/T</th>
      <th style="min-width:100px">T184A/G/S/I/L/F</th>
      <th style="min-width:60px">A194T</th>
      <th style="min-width:60px">S202G/I</th>
      <th style="min-width:60px">M204V</th>
      <th style="min-width:60px">N236T</th>
      <th style="min-width:70px">M250V/L/I</th>
      <th style="min-width:60px">序列</th>
    </tr>
  </thead>
  <tbody>
  <c:choose>
  	<c:when test="${map.data.size()>0}">
  	  <c:forEach items="${map.data }" var="hbv">
	    <tr>
	      <td align="center" style="max-width: 150px;">${hbv.fileName }</td>
	      <c:if test="${not empty hbv.site  }">
			<c:forEach var="key" items="169,173,180,181,184,194,202,204,236,250">
		      	<c:set value="${key }_wild" var="w"></c:set>
		      	<c:set value="${key }_mutation" var="m"></c:set>
			      	<td align="center">
				      	<c:if test="${hbv.site[m].contains('未检测到') }">
				      	${hbv.site[w].substring(0,1) }
				      	</c:if>
				      	<c:if test="${not hbv.site[m].contains('未检测到') }">
				      	${hbv.site[m].substring(0,1) }
				      	</c:if>
			      </td>
			</c:forEach>
	      </c:if>
	      <c:if test="${empty hbv.site }">
	      	<td align="center" colspan="10">由于分析流程的升级，八月一日之前的分析结果无法提取到该信息，若需要请重新运行。</td>
	      </c:if>
	      <td align="center"><a href="javascript:showSeq('${hbv.seq }')">查看序列</a></td>
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
<c:if test="${map.data.size()>0}">
	<input type="hidden" value="count/download?fileName=${map.fileName }" id="downUrl"></input>
</c:if>
