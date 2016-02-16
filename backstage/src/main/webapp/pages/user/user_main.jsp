<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel panel-default">
<div class="panel-heading">
    <h3 class="panel-title">用户管理</h3>
    <div class="panel-options">
        <form class="form-inline">
         <div class="form-group">
          <input type="text" class="form-control" data-rule-required="true" id="keyword" name="keyword" value="${keyword }" placeholder="请输入关键字">
         </div>
         <div class="form-group">
            <select class="form-control" id="searchFiled">
			  <option value="username" >用户名</option>
			  <option value="email" <c:if test="${searchFiled=='email' }">selected</c:if>>邮箱</option>
			</select>
		 </div>
         <button class="btn btn-warning" type="button" onclick="javascript:user.search()" style="margin-bottom:0;">检索</button>
         <button class="btn btn-danger" type="button" onclick="user.toSendEmail();" style="margin-bottom:0;">发送邮件</button>
       </form>
     </div>
</div>
<div class="panel-body">
    <table class="table table-bordered table-striped" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th class="no-sorting">
                    <input type="checkbox" class="cbr">
                </th>
                <th>用户编号</th>
                <th>用户名 </th>
                <th>真实姓名 </th>
                <th>邮箱 </th>
                <th>手机号码 </th>
                <th>角色 </th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${pageList.datas }" var="user">
                <tr>
                <td>
                    <input type="checkbox" class="cbr">
                </td>
                <td>${user.userId }</td>
                <td>${user.username }</td>
                <td>${user.truename }</td>
                <td>${user.email }</td>
                <td>${user.cellphone }</td>
                <td>
                    <c:if test="${user.role==0 }">普通用户</c:if>
                </td>
                <td>
                   
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
                                <li><a href="javascript:user.getUserList(${pageList.page.prevPage })">&lt;</a></li>
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
                                <li><a href="javascript:user.getUserList(1)">1</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${pageList.page.currentPage>3}">
                            <li><a href="javascript:void(0)">..</a></li>
                        </c:if>
                        <c:if test="${pageList.page.currentPage>2}">
                            <li><a href="javascript:user.getUserList(${pageList.page.prevPage })">${pageList.page.prevPage }</a></li>
                        </c:if>
                        <c:if test="${pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
                            <li class="active"><a href="javascript:void(0)">${pageList.page.currentPage }</a></li>
                        </c:if>
                        <c:if test="${pageList.page.totalPage-pageList.page.currentPage>1}">
                            <li><a href="javascript:user.getUserList(${pageList.page.nextPage })">${pageList.page.nextPage }</a></li>
                        </c:if>
                        <c:if test="${pageList.page.totalPage-pageList.page.currentPage>2}">
                            <li><a href="javascript:void(0)">..</a></li>
                        </c:if>
                        <c:choose>
                            <c:when test="${pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}">
                                <li class="active"><a href="javascript:void(0)">${pageList.page.totalPage }</a></li>
                            </c:when>
                            <c:when test="${pageList.page.totalPage>1}">
                                <li><a href="javascript:user.getUserList(${pageList.page.totalPage })">${pageList.page.totalPage }</a></li>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${pageList.page.hasNext}">
                                <li><a href="javascript:user.getUserList(${pageList.page.nextPage })">&gt;</a></li>
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