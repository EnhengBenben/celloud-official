<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel panel-default">
<div class="panel-heading">
    <h3 class="panel-title">运行时间</h3>
    <div class="panel-options">
        <form class="form-inline">
         <div class="form-group">
          <input type="text" class="form-control" data-rule-required="true" id="keyword" name="keyword" value="${keyword }" placeholder="请输入关键字">
         </div>
         <button class="btn btn-warning" type="button" onclick="javascript:task.toRunningTime(1);" style="margin-bottom:0;">检索</button>
       </form>
     </div>
</div>
<div class="panel-body">
    <table class="table table-bordered table-striped" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>app名称</th>
                <th>平均运行时间</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${empty pageList.datas }">
                <tr>
                    <td colspan="2">暂无数据</td>
                </tr>
            </c:if>
            <c:if test="${not empty pageList.datas }">
	            <c:forEach items="${pageList.datas }" var="map">
	            <tr>
	                <td>${map.app_name }</td>
	                <td>${map.avgSecond }</td>
	            </tr>
	            </c:forEach>
            </c:if>
        </tbody>
    </table>
    <c:if test="${pageList.datas.size()>0}">
        <div class="row-fluid">
            <div class="col-md-6 keepRight">
                <nav id="pageView">
                    <ul class="pagination">
                        <c:choose>
                            <c:when test="${pageList.page.hasPrev}">
                                <li><a href="javascript:task.toRunningTime(${pageList.page.prevPage })">&lt;</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0)">&lt;</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${pageList.page.currentPage==1}">
                                <li class="active"><a href="javascript:void(0)">1</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:task.toRunningTime(1)">1</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${pageList.page.currentPage>3}">
                            <li><a href="javascript:void(0)">..</a></li>
                        </c:if>
                        <c:if test="${pageList.page.currentPage>2}">
                            <li><a href="javascript:task.toRunningTime(${pageList.page.prevPage })">${pageList.page.prevPage }</a></li>
                        </c:if>
                        <c:if test="${pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
                            <li class="active"><a href="javascript:void(0)">${pageList.page.currentPage }</a></li>
                        </c:if>
                        <c:if test="${pageList.page.totalPage-pageList.page.currentPage>1}">
                            <li><a href="javascript:task.toRunningTime(${pageList.page.nextPage })">${pageList.page.nextPage }</a></li>
                        </c:if>
                        <c:if test="${pageList.page.totalPage-pageList.page.currentPage>2}">
                            <li><a href="javascript:void(0)">..</a></li>
                        </c:if>
                        <c:choose>
                            <c:when test="${pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}">
                                <li class="active"><a href="javascript:void(0)">${pageList.page.totalPage }</a></li>
                            </c:when>
                            <c:when test="${pageList.page.totalPage>1}">
                                <li><a href="javascript:task.toRunningTime(${pageList.page.totalPage })">${pageList.page.totalPage }</a></li>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${pageList.page.hasNext}">
                                <li><a href="javascript:task.toRunningTime(${pageList.page.nextPage })">&gt;</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0)">&gt;</a></li>
                            </c:otherwise>
                        </c:choose>
                        <li>
                            <a>共${pageList.page.totalPage }页&nbsp;|&nbsp;合计${pageList.page.rowCount }条</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </c:if>
</div>
</div>
<div class="modal fade bs-example-modal-lg" id="user-sendEmailModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
        </div>
      </div>
</div>