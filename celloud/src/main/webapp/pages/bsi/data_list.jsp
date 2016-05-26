<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content-header">
  <ol class="breadcrumb">
    <li>主页</li>
    <li>应用</li>
    <li>百菌探</li>
    <li>数据</li>
    <li id="to-my-data">数据列表</li>
  </ol>
</div>
<table class="table table-main">
  <thead>
    <tr>
      <th>批次/标签</th>
      <th>文件名</th>
      <th>文件编号</th>
      <th>文件大小</th>
      <th>上传时间<a id="data-sort-date" href="javascript:void(0);"><i id="sort-date-icon" class="fa fa-sort-amount-asc"></i></a></th>
<!--       <th>操作</th> -->
    </tr>
  </thead>
  <tbody id="data-list-tbody">
    <c:if test="${pageList.datas.size()>0 }">
      <c:forEach var="data" items="${pageList.datas }">
	    <tr>
	      <td>${data.batch }</td>
	      <td title="${data.fileName }" name="data-name-td" >
              <c:choose><c:when test="${fn:length(data.fileName)>60 }"><c:out value="${fn:substring(data.fileName, 0, 60) }"/>...</c:when><c:otherwise>${data.fileName }</c:otherwise></c:choose>
            <c:if test="${not empty data.anotherName }">(${data.anotherName })</c:if>
          </td>
          <td>${data.dataKey }</td>
          <td>
            <c:choose><c:when test="${data.size>1048576 }"><fmt:formatNumber pattern="0.00" value="${data.size/1048576 }"/>MB</c:when><c:otherwise><fmt:formatNumber pattern="0.00" value="${data.size/1024 }"/>KB</c:otherwise></c:choose>
          </td>
	      <td><fmt:formatDate type="both" value="${data.createDate }"/></td>
	    </tr>
      </c:forEach>
    </c:if>
  </tbody>
</table>
<div class="pagination text-center">
  <c:if test="${pageList.datas.size()>0}">
    <input id="current-page-hide" value="${pageList.page.currentPage }" type="hidden" >
    <input id="total-page-hide" value="${pageList.page.totalPage }" type="hidden" >
    <ul id="pagination-data" class="pages pull-left">
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
<!--       <li><a id="last-page-task" href="javascript:void(0);">尾页</a></li> -->
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
<script src="<%=request.getContextPath()%>/js/bsi_data.js?version=1.1"></script>