<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="table table-main">
  <thead>
    <tr>
      <th>批次/标签<a id="sort-batch" href="javascript:void(0);"><i id="sort-batch-icon" class="fa fa-sort-asc" aria-hidden="true"></i></a></th>
      <th>文件名<a id="sort-name" href="javascript:void(0);"><i id="sort-name-icon" class="fa fa-sort-asc" aria-hidden="true"></i></a></th>
      <th>状态<a id="sort-period" href="javascript:void(0);"><i id="sort-period-icon" class="fa fa-sort-asc" aria-hidden="true"></i></a></th>
      <th>更新时间<a id="sort-date" href="javascript:void(0);"><i id="sort-date-icon" class="fa fa-sort-amount-asc"></i></a></th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody id="data-list-tbody">
    <c:if test="${pageList.datas.size()>0 }">
      <c:forEach var="task" items="${pageList.datas }" varStatus="size">
	    <tr id="report${task.dataKey}${task.projectId}${task.appId}">
	      <td>${task.batch }</td>
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
	      <td>
	        <c:if test="${task.period==0 }">等待运行</c:if>
	        <c:if test="${task.period==1 }"><a data-toggle="modal" data-target="#running-modal">正在运行</a></c:if>
	        <c:if test="${task.period==2 }">完成</c:if>
	        <c:if test="${task.period==3 }"><a data-toggle="modal" data-target="#report-uploading-modal">数据上传中</a></c:if>
	        <c:if test="${empty task.period }"><a href="javascript:void(0)" onclick="$.report.period.error('${task.fileName }')" class="wrong">运行异常</a></c:if>
	      </td>
	      <td><fmt:formatDate value="${task.updateDate }" type="both" dateStyle="long" pattern="yyyy-MM-dd" /></td>
	      <td>
	        <c:if test="${task.period==2 }">
	            <a class="edit-icon" title="查看报告" name="to-report-a" href="javascript:<c:choose><c:when test="${task.period==2 }">$.report.detail.patient('${task.dataKey}',${task.projectId},${task.appId},${size.count},${pageList.page.currentPage })</c:when><c:otherwise>void(0)</c:otherwise></c:choose>"><i class="celicon show-icon"></i></a>
	        </c:if>
	        <c:if test="${task.period==1||task.period==2||empty task.period }">
	          <a class="edit-icon" title="重新运行" href="javascript:$.report.reRun(${task.dataKey },${task.appId },${task.projectId })"><i class="celicon rerun-icon"></i></a>
            </c:if>
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
    <ul id="pagination-task" class="pages pull-left">
<!--       <li><a id="first-page-task" href="javascript:void(0);">首页</a></li> -->
      <!-- 显示prev -->
      <li><a id="prev-page-task" class="ends" href="javascript:void(0);">&lt;&lt;</a></li>
      
      <!-- 显示第一页 -->
      <c:choose>
        <c:when test="${pageList.page.currentPage==1}"><li class="active"><a href="#">1</a></li></c:when>
        <c:otherwise><li><a name="pagination-task" href="javascript:void(0);">1</a></li></c:otherwise>
      </c:choose>
      
      <c:if test="${pageList.page.currentPage>4&&pageList.page.totalPage>10}">
          <li><a href="javascript:void(0)">…</a></li>
      </c:if>
      <c:choose>
        <c:when test="${pageList.page.totalPage-pageList.page.currentPage>=7}">
          <c:if test="${pageList.page.currentPage==3}">
              <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage==4}">
              <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage-2 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage>3}">
              <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
              <li class="active"><a href="#">${pageList.page.currentPage }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>1}">
              <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage+1 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>2}">
              <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage+2 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>3}">
              <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage+3 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>4}">
              <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage+4 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>5}">
              <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage+5 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage<4}">
              <c:if test="%{pageList.page.totalPage-pageList.page.currentPage>6}">
                  <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage+6 }</a></li>
              </c:if>
          </c:if>
          <c:choose>
            <c:when test="${pageList.page.currentPage==1}">
              <c:if test="%{pageList.page.totalPage-pageList.page.currentPage>7}">
                  <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage+7 }</a></li>
              </c:if>
              <c:if test="%{pageList.page.totalPage-pageList.page.currentPage>8}">
                  <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage+8 }</a></li>
              </c:if>
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${pageList.page.currentPage==2}">
                  <c:if test="${pageList.page.totalPage-pageList.page.currentPage>7}">
                      <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage+7 }</a></li>
                  </c:if>
                </c:when>
                <c:otherwise>
                  <c:if test="${pageList.page.currentPage>4 && (pageList.page.totalPage-pageList.page.currentPage>6)}">
                      <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.currentPage+6 }</a></li>
                  </c:if>
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:when>
        <c:otherwise>
          <c:choose>
            <c:when test="${pageList.page.totalPage-8>0}">
              <c:forEach begin="${pageList.page.totalPage-8}" step="1" end="${pageList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==pageList.page.currentPage}">   
                      <li class="active"><a href="#">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a name="pagination-task" href="javascript:void(0)">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <c:forEach begin="2" step="1" end="${pageList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==pageList.page.currentPage}">   
                      <li class="active"><a href="#">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a name="pagination-task" href="javascript:void(0)">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </c:otherwise>
      </c:choose>
      <c:if test="${pageList.page.totalPage-pageList.page.currentPage>=8&&pageList.page.totalPage>10}">
          <li><a href="javascript:void(0)">…</a></li>
      </c:if>
      <c:choose>
        <c:when test="${pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}"> 
          <li class="active"><a href="#">${pageList.page.totalPage }</a></li>
        </c:when>
        <c:otherwise>
          <c:if test="${pageList.page.totalPage>1}">   
            <li><a name="pagination-task" href="javascript:void(0)">${pageList.page.totalPage }</a></li>
          </c:if>
        </c:otherwise>
      </c:choose>
      <li><a id="next-page-task" class="ends" href="javascript:void(0)">&gt;&gt;</a></li>
    </ul>
    <ul class="pagination-data pull-right">
      <li><span>共${pageList.page.rowCount }条</span></li>
      <li>每页
        <select id="page-size-sel">
          <option value="10" <c:if test="${pageList.page.pageSize==10}">selected</c:if>>10</option>
          <option value="20" <c:if test="${pageList.page.pageSize==20}">selected</c:if>>20</option>
          <option value="30" <c:if test="${pageList.page.pageSize==30}">selected</c:if>>30</option>
          <option value="50" <c:if test="${pageList.page.pageSize==50}">selected</c:if>>50</option>
          <option value="100" <c:if test="${pageList.page.pageSize==100}">selected</c:if>>100</option>
        </select>条
      </li>
    </ul>
  </c:if>
</div>