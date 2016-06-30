<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.celloud.pay.alipay.AlipayConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<title>CelLoud平台账户充值--支付宝</title>
</head>
<body>
	<form action="<%=AlipayConfig.ALIPAY_GATEWAY_NEW%>" id="alipaysubmit" name="alipaysubmit" method="get"
		_input_charset="<%=AlipayConfig.input_charset%>">
		<c:forEach items="${params }" var="item">
			<input type="hidden" name="${item.key }" value="${item.value }">
		</c:forEach>
		<input type="submit" value="确定" style="display: none;">
	</form>
	<script type="text/javascript">
		document.forms['alipaysubmit'].submit();
	</script>
</body>
</html>