<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">应用管理</h3>
    </div>
    <div class="panel-body">
        <table id="app-table" class="table table-bordered table-striped" cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th>APP名称</th>
                    <th>英文名称</th>
                    <th>创建时间</th>
                    <th>所属公司</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pageList.datas }" var="app">
                    <tr>
                    <td>${app.appName }</td>
                    <td>${app.englishName }</td>
                    <td><fmt:formatDate value="${app.createDate }" pattern="yyyy-MM-dd"/></td>
                    <td>${app.companyName }</td>
                    <td>
                        <a class="btn btn-secondary" href="javascript:app.toShowRight(${app.appId })">
                        	查看使用者
                        </a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <c:if test="${pageList.datas.size()>0}">
		    <div class="row-fluid">
		        <div class="col-md-6 keepRight">
		            <nav id="pageView">
		                <ul class="pagination">
		                    <c:choose>
		                        <c:when test="${pageList.page.hasPrev}">
		                            <li><a href="javascript:app.toAppRight(${pageList.page.prevPage })">&lt;</a></li>
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
		                            <li><a href="javascript:app.toAppRight(1)">1</a></li>
		                        </c:otherwise>
		                    </c:choose>
		                    <c:if test="${pageList.page.currentPage>3}">
		                        <li><a href="javascript:void(0)">..</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.currentPage>2}">
		                        <li><a href="javascript:app.toAppRight(${pageList.page.prevPage })">${pageList.page.prevPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
		                        <li class="active"><a href="javascript:void(0)">${pageList.page.currentPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.totalPage-pageList.page.currentPage>1}">
		                        <li><a href="javascript:app.toAppRight(${pageList.page.nextPage })">${pageList.page.nextPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.totalPage-pageList.page.currentPage>2}">
		                        <li><a href="javascript:void(0)">..</a></li>
		                    </c:if>
		                    <c:choose>
		                        <c:when test="${pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}">
		                            <li class="active"><a href="javascript:void(0)">${pageList.page.totalPage }</a></li>
		                        </c:when>
		                        <c:when test="${pageList.page.totalPage>1}">
		                            <li><a href="javascript:app.toAppRight(${pageList.page.totalPage })">${pageList.page.totalPage }</a></li>
		                        </c:when>
		                    </c:choose>
		                    <c:choose>
		                        <c:when test="${pageList.page.hasNext}">
		                            <li><a href="javascript:app.toAppRight(${pageList.page.nextPage })">&gt;</a></li>
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

<div class="modal fade bs-example-modal-lg" id="showRight" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
	    <div class="panel panel-default">
		    <div class="panel-heading">
		        <h3 class="panel-title">APP使用者</h3>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		    </div>
		    <div class="panel-body" id="right_body">
		    </div>
		</div>
    </div>
  </div>
</div>
