<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="statistic">
	<jsp:useBean id="now" class="java.util.Date" />
	数据汇总：
	<span>已出报告[${periodMap.done }份] </span>
	<span>待出报告[${periodMap.wait }份] </span>
	<span>已传数据[${periodMap.uploaded }批] </span>
	<span>正在上传[${periodMap.uploading }批] </span>
	<span>状态异常[${periodMap.error }份] </span>
	<span class="pull-right">
		统计时间：
		<fmt:formatDate value="${now }" pattern="yyyy-MM-dd" />
	</span>
</div>