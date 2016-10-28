<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">客户端</h3>
        <div class="panel-options">
            <button type="button" class="btn btn-primary" onclick="javascript:client.addClient()" style="margin-bottom:0">新增版本</button>
        </div>
    </div>
    <div class="panel-body">
        <table id="client-table" class="table table-bordered table-striped" cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>版本号</th>
                    <th>x86文件名称</th>
                    <th>x64文件名称</th>
                    <th>时间</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pageList.datas }" var="client" varStatus="status">
                    <tr>
                    <td>${status.index+1+(pageList.page.currentPage-1)*10 }</td>
                    <td>
                        ${client.version }
                    </td>
                    <td>
                        ${client.nameX86 }
                    </td>
                    <td>
                        ${client.nameX64 }
                    </td>
                    <td><fmt:formatDate value="${client.createDate }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
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
		                            <li><a href="javascript:client.getClientList(${pageList.page.prevPage })">&lt;</a></li>
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
		                            <li><a href="javascript:client.getClientList(1)">1</a></li>
		                        </c:otherwise>
		                    </c:choose>
		                    <c:if test="${pageList.page.currentPage>3}">
		                        <li><a href="javascript:void(0)">..</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.currentPage>2}">
		                        <li><a href="javascript:client.getClientList(${pageList.page.prevPage })">${pageList.page.prevPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
		                        <li class="active"><a href="javascript:void(0)">${pageList.page.currentPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.totalPage-pageList.page.currentPage>1}">
		                        <li><a href="javascript:client.getClientList(${pageList.page.nextPage })">${pageList.page.nextPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.totalPage-pageList.page.currentPage>2}">
		                        <li><a href="javascript:void(0)">..</a></li>
		                    </c:if>
		                    <c:choose>
		                        <c:when test="${pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}">
		                            <li class="active"><a href="javascript:void(0)">${pageList.page.totalPage }</a></li>
		                        </c:when>
		                        <c:when test="${pageList.page.totalPage>1}">
		                            <li><a href="javascript:client.getClientList(${pageList.page.totalPage })">${pageList.page.totalPage }</a></li>
		                        </c:when>
		                    </c:choose>
		                    <c:choose>
		                        <c:when test="${pageList.page.hasNext}">
		                            <li><a href="javascript:client.getClientList(${pageList.page.nextPage })">&gt;</a></li>
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

<div class="modal fade bs-example-modal-lg" id="client-editModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
    </div>
  </div>
</div>
