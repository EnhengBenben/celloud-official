<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table_" style="text-align: center;">
	<c:set var="datas" value="${pageList.datas }"></c:set>
	<c:set var="page" value="${pageList.page }"></c:set>
	<thead>
		<tr>
			<th>编号</th>
			<th>日期</th>
			<th>样本类型</th>
			<th>扩增方法</th>
			<th>浓度</th>
			<th>质控</th>
			<th>Index</th>
			<th>库浓度</th>
			<th>测序仪</th>
			<th>阶段</th>
			<th>其他</th>
			<th>报告</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${datas }" var="exp">
			<tr>
				<td>${exp.number }</td>
				<td>
					<fmt:formatDate value="${exp.createDate }" pattern="yyyyMMdd" />
				</td>
				<td>${exp.sampleType }</td>
				<td>${exp.amplificationMethod }</td>
				<td>${exp.concentration }</td>
				<td>${exp.quality }</td>
				<td>${exp.seqIndex }</td>
				<td>${exp.libraryConcentration }</td>
				<td>${exp.sequenator }</td>
				<td>${exp.step }</td>
				<td>${exp.other }</td>
				<td>${exp.index }</td>
			</tr>
		</c:forEach>
		<c:if test="${datas.size()==0 }">
			<tr>
				<td colspan="13">实验流程为空</td>
			</tr>
		</c:if>
	</tbody>
</table>
<!-- users.searchLogInfo -->
<div class="pagination text-center">
  <c:if test="${pageList.datas.size()>0}">
    <input id="data-current-page-hide" value="${pageList.page.currentPage }" type="hidden" >
    <ul class="pages" id="pagination-data">
      <!-- 显示prev -->
      <c:if test="${pageList.page.hasPrev}">
          <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage-1 })">&lt;</a></li>
      </c:if>
      <!-- 显示第一页 -->
      <c:choose>
        <c:when test="${pageList.page.currentPage==1}"><li class="active"><a href="javascript:users.searchLogInfo(1)">1</a></li></c:when>
        <c:otherwise><li><a href="javascript:users.searchLogInfo(1)">1</a></li></c:otherwise>
      </c:choose>
      
      <c:if test="${pageList.page.currentPage>4&&pageList.page.totalPage>10}">
          <li>...</li>
      </c:if>
      <c:choose>
        <c:when test="${pageList.page.totalPage-pageList.page.currentPage>=7}">
          <c:if test="${pageList.page.currentPage==3}">
              <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage-1 })">${pageList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage==4}">
              <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage-2 })">${pageList.page.currentPage-2 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage>3}">
              <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage-1 })">${pageList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
              <li class="active"><a href="javascript:users.searchLogInfo(${pageList.page.currentPage })">${pageList.page.currentPage }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>1}">
              <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+1 })">${pageList.page.currentPage+1 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>2}">
              <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+2 })">${pageList.page.currentPage+2 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>3}">
              <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+3 })">${pageList.page.currentPage+3 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>4}">
              <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+4 })">${pageList.page.currentPage+4 }</a></li>
          </c:if>
          <c:if test="${pageList.page.totalPage-pageList.page.currentPage>5}">
              <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+5 })">${pageList.page.currentPage+5 }</a></li>
          </c:if>
          <c:if test="${pageList.page.currentPage<4}">
              <c:if test="%{pageList.page.totalPage-pageList.page.currentPage>6}">
                  <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+6 })">${pageList.page.currentPage+6 }</a></li>
              </c:if>
          </c:if>
          <c:choose>
            <c:when test="${pageList.page.currentPage==1}">
              <c:if test="%{pageList.page.totalPage-pageList.page.currentPage>7}">
                  <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+7 })">${pageList.page.currentPage+7 }</a></li>
              </c:if>
              <c:if test="%{pageList.page.totalPage-pageList.page.currentPage>8}">
                  <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+8 })">${pageList.page.currentPage+8 }</a></li>
              </c:if>
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${pageList.page.currentPage==2}">
                  <c:if test="${pageList.page.totalPage-pageList.page.currentPage>7}">
                      <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+7 })">${pageList.page.currentPage+7 }</a></li>
                  </c:if>
                </c:when>
                <c:otherwise>
                  <c:if test="${pageList.page.currentPage>4 && (pageList.page.totalPage-pageList.page.currentPage>6)}">
                      <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+6 })">${pageList.page.currentPage+6 }</a></li>
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
                      <li class="active"><a href="javascript:users.searchLogInfo(${step })">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a href="javascript:users.searchLogInfo(${step })">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <c:forEach begin="2" step="1" end="${pageList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==pageList.page.currentPage}">   
                      <li class="active"><a href="javascript:users.searchLogInfo(${step })">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a href="javascript:users.searchLogInfo(${step })">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </c:otherwise>
      </c:choose>
      <c:if test="${pageList.page.totalPage-pageList.page.currentPage>=8&&pageList.page.totalPage>10}">
          <li>...</li>
      </c:if>
      <c:choose>
        <c:when test="${pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}"> 
          <li class="active"><a href="javascript:users.searchLogInfo(${pageList.page.totalPage })">${pageList.page.totalPage }</a></li>
        </c:when>
        <c:otherwise>
          <c:if test="${pageList.page.totalPage>1}">   
            <li><a href="javascript:users.searchLogInfo(${pageList.page.totalPage })">${pageList.page.totalPage }</a></li>
          </c:if>
        </c:otherwise>
      </c:choose>
      <c:if test="${pageList.page.hasNext}">
          <li><a href="javascript:users.searchLogInfo(${pageList.page.currentPage+1 })">&gt;</a></li>
      </c:if>
      <li>
                  共${pageList.page.totalPage }页&nbsp;|&nbsp;合计${pageList.page.rowCount }条
      </li>
    </ul>
  </c:if>
</div>
