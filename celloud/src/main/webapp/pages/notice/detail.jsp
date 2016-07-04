<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="box box-success">
	<div class="box-header with-border">
		<div class="row">
			<div class="col-xs-10">
				<h4>
					<i class="${notice.icon }"></i> ${notice.noticeTitle }
				</h4>
				<p>&nbsp;</p>
				<p>
					发布时间：
					<fmt:formatDate value="${notice.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
				</p>
			</div>
			<div class="col-xs-2">
				<button class="btn pull-right" onclick="notices.deleteNotice('${notice.noticeId}')">X</button>
			</div>
		</div>
	</div>
	<div class="box-body" style="min-height: 390px;">
		<pre>${notice.noticeContext }</pre>
	</div>
</div>
