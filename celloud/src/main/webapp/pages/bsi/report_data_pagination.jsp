<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<table class="table table-main">
  <thead>
    <tr>
      <th>相同标签报告</th>
    </tr>
  </thead>
  <tbody id="data-list-tbody">
    <c:if test="${pageList.datas.size()>0 }">
      <c:forEach var="task" items="${pageList.datas }" varStatus="size">
        <tr id="report${task.dataKey}${task.projectId}${task.appId}">
          <td title="${task.fileName }" name="data-name-td" >
            <c:choose>
              <c:when test="${task.period==2 }">
                 <a href="javascript:<c:choose><c:when test="${task.period==2 }">$.report.detail.patient('${task.dataKey}',${task.projectId},${task.appId},${size.count},${pageList.page.currentPage })</c:when><c:otherwise>void(0)</c:otherwise></c:choose>">
                   <c:choose><c:when test="${fn:length(task.fileName)>60 }"><c:out value="${fn:substring(task.fileName, 0, 60) }"/>...</c:when><c:otherwise>${task.fileName }</c:otherwise></c:choose>
                   <c:if test="${not empty task.anotherName }">(${task.anotherName })</c:if>
                 </a>
              </c:when>
              <c:otherwise>
                <c:choose><c:when test="${fn:length(task.fileName)>60 }"><c:out value="${fn:substring(task.fileName, 0, 60) }"/>...</c:when><c:otherwise>${task.fileName }</c:otherwise></c:choose>
                <c:if test="${not empty task.anotherName }">(${task.anotherName })</c:if>
              </c:otherwise>
            </c:choose>
          </td>
        </tr>
      </c:forEach>
    </c:if>
  </tbody>
</table>
<div class="pagination text-center">
  <c:if test="${pageList.datas.size()>0}">
    <input id="current-page-hide" value="${pageList.page.currentPage }" type="hidden" >
    <input id="total-page-hide" value="${pageList.page.totalPage }" type="hidden" >
    <ul id="pagination-data-report" class="pages">
      <li><a id="prev-page-task" class="ends pull-left" href="javascript:void(0);">&lt;&lt;</a></li>
      <!-- 显示第一页 -->
      <c:choose>
        <c:when test="${pageList.page.totalPage<=3}">
          <c:forEach begin="1" end="${pageList.page.totalPage}" step="1" var="step">
            <li <c:if test="${step==pageList.page.currentPage }">class="active"</c:if>><a name="pagination-task" href="javascript:void(0);">${step }</a></li>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <c:if test="${pageList.page.currentPage==1}">
            <li class="active"><a name="pagination-task" href="javascript:void(0);">1</a></li>
            <li><a name="pagination-task" href="javascript:void(0);">2</a></li>
            <li><a name="pagination-task" href="javascript:void(0);">3</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage==pageList.page.totalPage}">
            <li><a name="pagination-task" href="javascript:void(0);">${pageList.page.currentPage-2}</a></li>
            <li><a name="pagination-task" href="javascript:void(0);">${pageList.page.currentPage-1}</a></li>
            <li class="active"><a name="pagination-task" href="javascript:void(0);">${pageList.page.currentPage}</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage>1 && pageList.page.currentPage<pageList.page.totalPage}">
	        <li><a name="pagination-task" href="javascript:void(0);">${pageList.page.currentPage-1}</a></li>
	        <li class="active"><a name="pagination-task" href="javascript:void(0);">${pageList.page.currentPage}</a></li>
	        <li><a name="pagination-task" href="javascript:void(0);">${pageList.page.currentPage+1}</a></li>
          </c:if>
        </c:otherwise>
      </c:choose>
      <li><a id="next-page-task" class="ends pull-right" href="javascript:void(0)">&gt;&gt;</a></li>
    </ul>
  </c:if>
</div>