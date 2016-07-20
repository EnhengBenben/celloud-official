<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<title>CelLoud平台账户充值--网银支付</title>
</head>
<body>
	<form action="https://tmapi.jdpay.com/PayGate" method="POST" name="jdpaysubmit" id="jdpaysubmit">
		<c:forEach items="${params }" var="item">
			<!-- input style="" type="text" name="${item.key }" value="${item.value }" -->
		</c:forEach>
		<input type="text" name="v_md5info" value="${params.v_md5info }" size="100"><br>
		<input type="text" name="v_mid" value="${params.v_mid }"  size="100"><br>
		<input type="text" name="v_oid" value="${params.v_oid }"  size="100"><br>
		<input type="text" name="v_amount" value="${params.v_amount }" size="100"><br>
		<input type="text" name="v_moneytype" value="${params.v_moneytype }" size="100"><br>
		<input type="text" name="v_url" value="${params.v_url }" size="100"><br>

		<!--以下几项项为网上支付完成后，随支付反馈信息一同传给信息接收页 -->

		<input type="text" name="remark1" value="${params.remark1 }" size="100"><br>
		<input type="text" name="remark2" value="${params.remark2 }" size="100"><br>

		<input type="text" name="pmode_id" value="${params.pmode_id }" size="100"><br>
		<input type="submit" value="确定" >
	</form>
	<script type="text/javascript">
		//document.forms['jdpaysubmit'].submit();
	</script>
</body>
</html>