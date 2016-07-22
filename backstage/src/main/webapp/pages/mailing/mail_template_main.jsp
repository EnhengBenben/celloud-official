<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">邮件模板列表</h3>
        <div class="panel-options">
	       <button data-click="to-edit-mailtemplate" class="btn btn-warning" type="button" style="margin-bottom:0;">新增</button>
	    </div>
    </div>
    <div class="panel-body">
        <table id="app-table" class="table table-bordered table-striped" cellspacing="0" width="100%">
            <thead>
                <tr>
                    <th>模板名称</th>
                    <th>模板方法</th>
                    <th>模板主题</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pageList.datas }" var="template">
                    <tr>
                    <td>${template.name }</td>
                    <td>${template.method }</td>
                    <td>${template.title }</td>
                    <td><fmt:formatDate value="${template.createDate }" pattern="yyyy-MM-dd"/></td>
                    <td>
                        <a class="btn btn-secondary" href="javascript:void(0);" data-click="to-edit-mailtemplate" data-id="${template.id }">编辑</a>
                        <a class="btn btn-secondary" href="javascript:void(0);" data-click="to-del-mailtemplate" data-id="${template.id }">删除</a>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
        <%@ include file="../pagination.jsp" %>
    </div>
</div>
