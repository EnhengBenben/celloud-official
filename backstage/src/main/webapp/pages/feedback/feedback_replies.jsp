<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:forEach items="${replies }" var="reply">
	<div class="box-footer feedback-reply">
		<h5>回复(${reply.userName })：</h5>
		<pre>${reply.content }</pre>
	</div>
</c:forEach>
