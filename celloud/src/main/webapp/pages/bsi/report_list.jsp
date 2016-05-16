<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-header">
  <div class="header">
    <h4>血流感染检测 </h4>
  </div>
  <ol class="breadcrumb">
    <li>CelLoud平台</li>
    <li>我的报告</li>
  </ol>
</div>
<div class="box-tools">
  <div class="data-search">
    <input id="report-condition-input" class="input-sm" type="text"/>
    <div class="input-group-btn">
      <button id="report-condition-find" class="btn btn-sm" type="button"><i class="fa fa-search"></i>搜索</button>
    </div>
  </div>
</div>
<table class="table table-main">
  <thead>
    <tr>
      <th>批次/标签</th>
      <th>文件名</th>
      <th>状态<a id="sort-period" href="javascript:void(0);"><i id="sort-period-icon" class="fa fa-sort-asc" aria-hidden="true"></i></a></th>
      <th>生成时间<a id="sort-date" href="javascript:void(0);"><i id="sort-date-icon" class="fa fa-sort-amount-asc"></i></a></th>
      <th>操作</th>
    </tr>
  </thead>
  <tbody id="data-list-tbody">
    <c:if test="${pageList.datas.size()>0 }">
      <c:forEach var="task" items="${pageList.datas }">
	    <tr>
	      <td>${task.batch }</td>
	      <td title="${task.fileName }" name="data-name-td" >
	        <a href="javascript:<c:choose><c:when test="${task.period==2 }">$.report.detail.patient('${task.dataKey}',${task.projectId},${task.appId})</c:when><c:otherwise>void(0)</c:otherwise></c:choose>">
              <c:choose><c:when test="${fn:length(task.fileName)>60 }"><c:out value="${fn:substring(task.fileName, 0, 60) }"/>...</c:when><c:otherwise>${task.fileName }</c:otherwise></c:choose>
            </a>
          </td>
	      <td>
	        <c:if test="${task.period==0 }">等待运行</c:if>
	        <c:if test="${task.period==1 }">正在运行</c:if>
	        <c:if test="${task.period==2 }">完成</c:if>
	        <c:if test="${task.period==3 }">数据上传中</c:if>
	        <c:if test="${empty task.period }"><span class="wrong">运行异常</span></c:if>
	      </td>
	      <td><fmt:formatDate type="both" value="${task.createDate }"/></td>
	      <td>
            <a class="edit-icon" id="to-upload-a" href="javascript:<c:choose><c:when test="${task.period==2 }">$.report.detail.patient('${task.dataKey}',${task.projectId},${task.appId})</c:when><c:otherwise>void(0)</c:otherwise></c:choose>"><i class="celicon show-icon"></i></a>
	        <a class="edit-icon" id="to-rerun-a" href="javascript:$.report.run(${task.fileId },118)"><i class="celicon rerun-icon"></i></a>
<%-- 	        <a class="edit-icon" id="to-rerun-a" href="javascript:$.report.run(${task.fileId },${task.appId })"><i class="celicon rerun-icon"></i></a> --%>
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
    <ul id="pagination-task" class="pages">
      <li><a id="first-page-task" href="javascript:void(0);">首页</a></li>
      <!-- 显示prev -->
      <li><a id="prev-page-task" href="javascript:void(0);">上一页</a></li>
      
      <!-- 显示第一页 -->
      <c:choose>
        <c:when test="${pageList.page.currentPage==1}"><li class="active"><a href="#">1</a></li></c:when>
        <c:otherwise><li><a name="pagination-task" href="javascript:void(0);">1</a></li></c:otherwise>
      </c:choose>
      
      <c:if test="${pageList.page.currentPage>4&&pageList.page.totalPage>10}">
          <li>...</li>
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
          <li>……</li>
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
      <li><a id="next-page-task" href="javascript:void(0)">下一页</a></li>
      <li><a id="last-page-task" href="javascript:void(0);">尾页</a></li>
    </ul>
  </c:if>
</div>
<script src="<%=request.getContextPath()%>/js/bsi_report.js"></script>