<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<form role="form" class="form-horizontal" id="clientForm">
    <div class="form-group">
    	<c:forEach items="${userList }" var="user">
	        <label class="col-sm-2 control-label">${user.username }</label>
    	</c:forEach>
    </div>
    <div class="form-group-separator"></div>
    <div class="form-group">
        <div class="col-sm-10 text-center">
            <a class="btn btn-info" href="javascript:app.downEmailFile('${path }')" target="_blank">下载</a>
            <a class="btn btn-success" data-dismiss="modal">朕知道了</a>
            <a class="btn btn-white" data-dismiss="modal">取消</a>
        </div>
    </div>
</form>
