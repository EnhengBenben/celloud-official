<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">公司管理</h3>
        <div class="panel-options">
        <form class="form-inline">
		  <div class="form-group">
		   <input type="text" class="form-control" data-rule-required="true" id="keyword" name="keyword" value="${keyword }" placeholder="医院中文名称">
		  </div>
		  <a class="btn btn-warning" href="javascript:company.search()" style="margin-bottom:0">检索</a>
		</form>
        </div>
    </div>
    <div class="panel-body">
        <table id="example-2" class="table table-bordered table-striped" cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th class="no-sorting">
                        <input type="checkbox" class="cbr">
                    </th>
                    <th>公司名称</th>
                    <th>操作|
                        <a href="javascript:company.addCompany();" class="text-success">
                                                    新增
                        </a>
                    </th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pageList.datas }" var="company">
                    <tr>
                    <td>
                        <input type="checkbox" class="cbr">
                    </td>
                    <td><a href="javascript:dept.getDeptByCompany(${company.companyId },1)" class="text-success">${company.companyName }</a></td>
                    <td>
                        <a class="btn btn-secondary" href="javascript:company.editCompany(${company.companyId })">
                                                    编辑
                        </a>
                        <a class="btn btn-danger" href="javascript:company.deleteCompany(${company.companyId })">
                                                    删除
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
		                            <li><a href="javascript:company.getCompany(${pageList.page.prevPage })">&lt;</a></li>
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
		                            <li><a href="javascript:company.getCompany(1)">1</a></li>
		                        </c:otherwise>
		                    </c:choose>
		                    <c:if test="${pageList.page.currentPage>3}">
		                        <li><a href="javascript:void(0)">..</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.currentPage>2}">
		                        <li><a href="javascript:company.getCompany(${pageList.page.prevPage })">${pageList.page.prevPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
		                        <li class="active"><a href="javascript:void(0)">${pageList.page.currentPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.totalPage-pageList.page.currentPage>1}">
		                        <li><a href="javascript:company.getCompany(${pageList.page.nextPage })">${pageList.page.nextPage }</a></li>
		                    </c:if>
		                    <c:if test="${pageList.page.totalPage-pageList.page.currentPage>2}">
		                        <li><a href="javascript:void(0)">..</a></li>
		                    </c:if>
		                    <c:choose>
		                        <c:when test="${pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}">
		                            <li class="active"><a href="javascript:void(0)">${pageList.page.totalPage }</a></li>
		                        </c:when>
		                        <c:when test="${pageList.page.totalPage>1}">
		                            <li><a href="javascript:company.getCompany(${pageList.page.totalPage })">${pageList.page.totalPage }</a></li>
		                        </c:when>
		                    </c:choose>
		                    <c:choose>
		                        <c:when test="${pageList.page.hasNext}">
		                            <li><a href="javascript:company.getCompany(${pageList.page.nextPage })">&gt;</a></li>
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

<div class="panel panel-default" id="dept-manager">
    
</div>


<div class="modal fade bs-example-modal-lg" id="company-editModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
    </div>
  </div>
</div>

