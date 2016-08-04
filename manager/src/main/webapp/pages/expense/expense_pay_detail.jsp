<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="icon-hospital"></i>
            <a href="javascript:void(0)">费用中心</a>
        </li>
        <li class="active"><a href="javascript:void(0)">消费明细</a></li>
    </ul>
</div>
<table class="table data-table">
    <thead>
        <tr>
            <th class="pay-date text-center">消费时间</th>
            <th class="pay-detail text-center">消费明细</th>
<!--             <th class="data-tags text-center">应用单价</th> -->
            <th class="pay-money text-center">消费</th>
            <th class="pay-remarks text-center">备注</th>
        </tr>
    </thead>
    <tbody id="pay-list-tbody" class="pay-tbody text-left">
		<c:choose>
		  <c:when test="${expensePageList.datas.size()>0 }">
		    <c:forEach items="${expensePageList.datas }" var="expense" varStatus="status">
		       <tr>
                  <td class="text-center"><fmt:formatDate value="${expense.createDate }" type="both"/></td>
                  <td class="text-left">
                  	<c:if test="${expense.itemId==0 }">
                  		金钱赠予
                  	</c:if>
                  	<c:if test="${expense.itemId>0 }">
                  		数据
                  		<span title="${expense.fileInfos }">
	                  		<c:choose>
	                  			<c:when test="${fn:length(expense.fileInfos)>50 }">
	                  				<c:out value="${fn:substring(expense.fileInfos, 0, 50) }"/>...</c:when>
	                  			<c:otherwise>${expense.fileInfos }</c:otherwise>
	                  		</c:choose>
                  		</span>
                  		运行${expense.appName }
                  	</c:if>
                  </td>
<%--                   <td>${expense.price }C</td> --%>
                  <td class="text-center">${expense.price }</td>
                  <td class="text-center">
                     <c:if test="${empty expense.remark }">无</c:if>
                     ${expense.remark }
                  </td>
              </tr>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <tr><td colspan="8">记录为空</td></tr>
          </c:otherwise>
		</c:choose>
	</tbody>
</table>
<c:if test="${expensePageList.datas.size()>0}">
  <div class="row-fluid">
      <div class="col-md-6 keepRight">
          <nav id="pageView">
              <ul class="pagination">
                  <c:choose>
                      <c:when test="${expensePageList.page.hasPrev}">
                          <li><a href="javascript:$.expense.pay.expenseList(${expensePageList.page.prevPage })">&lt;</a></li>
                      </c:when>
                      <c:otherwise>
                          <li><a href="javascript:void(0)">&lt;</a></li>
                      </c:otherwise>
                  </c:choose>
                  <c:choose>
                      <c:when test="${expensePageList.page.currentPage==1}">
                          <li class="active"><a href="javascript:void(0)">1</a></li>
                      </c:when>
                      <c:otherwise>
                          <li><a href="javascript:$.expense.pay.expenseList(1)">1</a></li>
                      </c:otherwise>
                  </c:choose>
                  <c:if test="${expensePageList.page.currentPage>3}">
                      <li><a href="javascript:void(0)">..</a></li>
                  </c:if>
                  <c:if test="${expensePageList.page.currentPage>2}">
                      <li><a href="javascript:$.expense.pay.expenseList(${expensePageList.page.prevPage })">${expensePageList.page.prevPage }</a></li>
                  </c:if>
                  <c:if test="${expensePageList.page.currentPage>1&&expensePageList.page.currentPage<expensePageList.page.totalPage}">
                      <li class="active"><a href="javascript:void(0)">${expensePageList.page.currentPage }</a></li>
                  </c:if>
                  <c:if test="${expensePageList.page.totalPage-expensePageList.page.currentPage>1}">
                      <li><a href="javascript:$.expense.pay.expenseList(${expensePageList.page.nextPage })">${expensePageList.page.nextPage }</a></li>
                  </c:if>
                  <c:if test="${expensePageList.page.totalPage-expensePageList.page.currentPage>2}">
                      <li><a href="javascript:void(0)">..</a></li>
                  </c:if>
                  <c:choose>
                      <c:when test="${expensePageList.page.currentPage==expensePageList.page.totalPage&&expensePageList.page.totalPage>1}">
                          <li class="active"><a href="javascript:void(0)">${expensePageList.page.totalPage }</a></li>
                      </c:when>
                      <c:when test="${expensePageList.page.totalPage>1}">
                          <li><a href="javascript:$.expense.pay.expenseList(${expensePageList.page.totalPage })">${expensePageList.page.totalPage }</a></li>
                      </c:when>
                  </c:choose>
                  <c:choose>
                      <c:when test="${expensePageList.page.hasNext}">
                          <li><a href="javascript:$.expense.pay.expenseList(${expensePageList.page.nextPage })">&gt;</a></li>
                      </c:when>
                      <c:otherwise>
                          <li><a href="javascript:void(0)">&gt;</a></li>
                      </c:otherwise>
                  </c:choose>
                  <li>
                      <a>共${expensePageList.page.totalPage }页&nbsp;|&nbsp;合计${expensePageList.page.rowCount }条</a>
                  </li>
              </ul>
          </nav>
      </div>
  </div>
</c:if>
