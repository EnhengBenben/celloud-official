<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table_">
  <thead>
    <tr>
      <th style="min-width:100px;">数据编号</th>
      <th style="min-width:160px">原始文件名1</th>
      <th style="min-width:160px">原始文件名2</th>
      <th style="min-width:70px">应用名称</th>
      <th style="min-width:90px">共获得有效片段</th>
      <th style="min-width:60px">可用片段</th>
      <th style="min-width:80px">平均测序深度</th>
      <th style="min-width:60px">基因检测</th>
    </tr>
  </thead>
  <tbody>
  <c:choose>
  	<c:when test="${map.data.size()>0}">
  	  <c:forEach items="${map.data }" var="cmp" varStatus="cmpSta">
  	    <c:if test="${not empty cmp.data}">
  	      <tr>
	          <td align="center">${cmp.dataKey }</td>
	          <td align="center">${cmp.data[0].fileName }(${cmp.data[0].dataKey })</td>
	          <td align="center">${cmp.data[1].fileName }(${cmp.data[1].dataKey })</td>
	          <td align="center">${cmp.appName }</td>
	          <td align="center">${cmp.allFragment }</td>
	          <td align="center">${cmp.usableFragment }</td>
	          <td align="center">${cmp.avgCoverage }</td>
	          <td align="center">
	            <a href="javascript:showGeneResult('<c:forEach items="${cmp.cmpGeneResult}" var="gene">
	              <c:if test="${gene.knownMSNum>0}">${gene.geneName }:${gene.knownMSNum }&nbsp;&nbsp;&nbsp;</c:if>
	            </c:forEach>')">查看结果</a>
	          </td>
	        </tr>
  	    </c:if>
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
<input type="hidden" value="count/download?fileName=${map.fileName }" id="downUrl"></input>
<div class="modal modal-green-header" id="geneResultModal">
 <div class="modal-dialog">
  <div class="modal-content">
  <div class="modal-header">
		<a class="close" data-dismiss="modal">&times;</a>
		<h4 class="modal-title">基因检测结果</h4>
	</div>
	<div class="modal-body row">
		<div class="col-sm-12" id="showGeneResult" style="word-break:break-all;">
			
		</div>
	</div>
	<div class="modal-footer">
         <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button>
	</div>
  </div>
 </div>
</div>