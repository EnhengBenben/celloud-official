<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="pagination text-center">
  <c:if test="${pageList.datas.size()>0}">
    <input id="current-page-hide" value="${pageList.page.currentPage }" type="hidden" >
    <input id="total-page-hide" value="${pageList.page.totalPage }" type="hidden" >
    <ul id="pagination-ul" class="pages pull-right">
      <!-- 显示prev -->
      <li><a id="prev-page" class="ends" href="javascript:void(0);">&lt;&lt;</a></li>
      <c:choose>
        <c:when test="${pageList.page.totalPage<=7}">   
          <c:forEach begin="1" step="1" end="${pageList.page.totalPage}" var="step">
            <c:choose>
              <c:when test="${step==pageList.page.currentPage}">   
                <li class="active"><a href="#">${step }</a></li>
              </c:when>
              <c:otherwise>
                <li><a name="pagination-info" href="javascript:void(0)">${step }</a></li>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <c:if test="${pageList.page.currentPage<=4}">   
              <c:forEach begin="1" step="1" end="5" var="step">
                <c:choose>
                  <c:when test="${step==pageList.page.currentPage}">   
                    <li class="active"><a href="#">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                    <li><a name="pagination-info" href="javascript:void(0)">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
              <li><a href="javascript:void(0)">…</a></li>
              <li><a name="pagination-info" href="javascript:void(0)">${pageList.page.totalPage }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage>4 && pageList.page.currentPage<pageList.page.totalPage-3}">
              <li><a name="pagination-info" href="javascript:void(0)">1</a></li>
              <li><a href="javascript:void(0)">…</a></li>
              <li><a name="pagination-info" href="javascript:void(0)">${pageList.page.currentPage-1 }</a></li>
              <li class="active"><a href="#">${pageList.page.currentPage }</a></li>
              <li><a name="pagination-info" href="javascript:void(0)">${pageList.page.currentPage+1 }</a></li>
              <li><a href="javascript:void(0)">…</a></li>
              <li><a name="pagination-info" href="javascript:void(0)">${pageList.page.totalPage }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage>=pageList.page.totalPage-3}">   
              <li><a name="pagination-info" href="javascript:void(0)">1</a></li>
              <li><a href="javascript:void(0)">…</a></li>
              <c:forEach begin="${pageList.page.totalPage-4}" step="1" end="${pageList.page.totalPage}" var="step">
                <c:choose>
                  <c:when test="${step==pageList.page.currentPage}">   
                    <li class="active"><a href="#">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                    <li><a name="pagination-info" href="javascript:void(0)">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
          </c:if>
        </c:otherwise>
      </c:choose>
      <li><a id="next-page" class="ends" href="javascript:void(0)">&gt;&gt;</a></li>
    </ul>
  </c:if>
</div>