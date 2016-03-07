<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table data-table">
    <thead>
        <tr>
            <th>扣费时间</th>
            <th class="file-name">文件名称</th>
            <th class="strain">应用名称</th>
            <th class="data-tags">应用单价</th>
            <th class="sample">实付价格</th>
            <th class="data-size">备注</th>
        </tr>
    </thead>
    <tbody id="data-list-tbody">
		<c:choose>
		  <c:when test="${expensePageList.datas.size()>0 }">
		    <c:forEach items="${expensePageList.datas }" var="expense" varStatus="status">
		       <tr>
                  <td><fmt:formatDate value="${expense.createDate }" type="both"/></td>
                  <td>${expense.fileInfos }</td>
                  <td>${expense.appName }</td>
                  <td>${expense.price }C</td>
                  <td>${expense.price }C</td>
                  <td>${expense.remark }</td>
              </tr>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <tr><td colspan="8">记录为空</td></tr>
          </c:otherwise>
		</c:choose>
	</tbody>
</table>
<div class="pagination text-center">
  <c:if test="${expensePageList.datas.size()>0}">
    <input id="expense-current-page-hide" value="${expensePageList.page.currentPage }" type="hidden" >
    <ul id="pagination-pay" class="pages">
      <!-- 显示prev -->
      <c:if test="${expensePageList.page.hasPrev}">
          <li><a id="prev-page-expense" href="javascript:void(0);">&lt;</a></li>
      </c:if>
      <!-- 显示第一页 -->
      <c:choose>
        <c:when test="${expensePageList.page.currentPage==1}"><li class="active"><a href="#">1</a></li></c:when>
        <c:otherwise><li><a name="pagination-pay" href="javascript:void(0);">1</a></li></c:otherwise>
      </c:choose>
      
      <c:if test="${expensePageList.page.currentPage>4&&expensePageList.page.totalPage>10}">
          <li>...</li>
      </c:if>
      <c:choose>
        <c:when test="${expensePageList.page.totalPage-expensePageList.page.currentPage>=7}">
          <c:if test="${expensePageList.page.currentPage==3}">
              <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${expensePageList.page.currentPage==4}">
              <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage-2 }</a></li>
          </c:if>
          <c:if test="${expensePageList.page.currentPage>3}">
              <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${expensePageList.page.currentPage>1&&expensePageList.page.currentPage<expensePageList.page.totalPage}">
              <li class="active"><a href="#">${expensePageList.page.currentPage }</a></li>
          </c:if>
          <c:if test="${expensePageList.page.totalPage-expensePageList.page.currentPage>1}">
              <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage+1 }</a></li>
          </c:if>
          <c:if test="${expensePageList.page.totalPage-expensePageList.page.currentPage>2}">
              <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage+2 }</a></li>
          </c:if>
          <c:if test="${expensePageList.page.totalPage-expensePageList.page.currentPage>3}">
              <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage+3 }</a></li>
          </c:if>
          <c:if test="${expensePageList.page.totalPage-expensePageList.page.currentPage>4}">
              <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage+4 }</a></li>
          </c:if>
          <c:if test="${expensePageList.page.totalPage-expensePageList.page.currentPage>5}">
              <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage+5 }</a></li>
          </c:if>
          <c:if test="${expensePageList.page.currentPage<4}">
              <c:if test="%{expensePageList.page.totalPage-expensePageList.page.currentPage>6}">
                  <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage+6 }</a></li>
              </c:if>
          </c:if>
          <c:choose>
            <c:when test="${expensePageList.page.currentPage==1}">
              <c:if test="%{expensePageList.page.totalPage-expensePageList.page.currentPage>7}">
                  <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage+7 }</a></li>
              </c:if>
              <c:if test="%{expensePageList.page.totalPage-expensePageList.page.currentPage>8}">
                  <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage+8 }</a></li>
              </c:if>
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${expensePageList.page.currentPage==2}">
                  <c:if test="${expensePageList.page.totalPage-expensePageList.page.currentPage>7}">
                      <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage+7 }</a></li>
                  </c:if>
                </c:when>
                <c:otherwise>
                  <c:if test="${expensePageList.page.currentPage>4 && (expensePageList.page.totalPage-expensePageList.page.currentPage>6)}">
                      <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.currentPage+6 }</a></li>
                  </c:if>
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:when>
        <c:otherwise>
          <c:choose>
            <c:when test="${expensePageList.page.totalPage-8>0}">
              <c:forEach begin="${expensePageList.page.totalPage-8}" step="1" end="${expensePageList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==expensePageList.page.currentPage}">   
                      <li class="active"><a href="#">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a name="pagination-pay" href="javascript:void(0)">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <c:forEach begin="2" step="1" end="${expensePageList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==expensePageList.page.currentPage}">   
                      <li class="active"><a href="#">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a name="pagination-pay" href="javascript:void(0)">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </c:otherwise>
      </c:choose>
      <c:if test="${expensePageList.page.totalPage-expensePageList.page.currentPage>=8&&expensePageList.page.totalPage>10}">
          <li>...</li>
      </c:if>
      <c:choose>
        <c:when test="${expensePageList.page.currentPage==expensePageList.page.totalPage&&expensePageList.page.totalPage>1}"> 
          <li class="active"><a href="#">${expensePageList.page.totalPage }</a></li>
        </c:when>
        <c:otherwise>
          <c:if test="${expensePageList.page.totalPage>1}">   
            <li><a name="pagination-pay" href="javascript:void(0)">${expensePageList.page.totalPage }</a></li>
          </c:if>
        </c:otherwise>
      </c:choose>
      <c:if test="${expensePageList.page.hasNext}">
          <li><a id="next-page-data" href="javascript:void(0)">&gt;</a></li>
      </c:if>
      <li>
                  共${expensePageList.page.totalPage }页&nbsp;|&nbsp;合计${expensePageList.page.rowCount }条
      </li>
    </ul>
  </c:if>
</div>