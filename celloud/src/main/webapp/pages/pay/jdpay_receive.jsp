<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.celloud.pay.alipay.AlipayConfig"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<title>CelLoud平台账户充值--网银支付</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 优先使用最新版本的IE 和 Chrome 内核 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="baidu-site-verification" content="IsldTuHqik" />
<title>CelLoud 控制台</title>
<meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据,上海华点云生物科技有限公司网站首页,上海华点云生物科技有限公司官网,上海华点云生物科技有限公司北京分公司" />
<meta name="description" content="一站式高通量基因检测数据分析系统" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- <meta name="baidu-site-verification" content="1fI6fRe9op" /> -->
<meta name="baidu-site-verification" content="ZvlzkAOdsB" />
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=request.getContextPath()%>/css/main.css?version=3.1.9" rel="stylesheet" type="text/css" />
</head>
<body class="skin-green sidebar-mini">
	<div class="fixed">
		<header class="main-header">
			<a href="javascript:void(0)" class="logo">
				<span class="logo-lg">
					<img src="<%=request.getContextPath()%>/images/icon/logo.png">
				</span>
			</a>
			<!-- Header Navbar: style can be found in header.less -->
			<nav class="navbar navbar-static-top">
				<!-- Sidebar toggle button-->
				<div class="navbar-custom-menu"></div>
			</nav>
		</header>
		<div class="content-wrapper" style="background-color: #fff; margin-right: 180px;">
			<c:if test="${verify_result }">
				<div class="page-header">
					<h1>
						<span class="fa fa-check-square text-success"></span>
						充值成功
					</h1>
				</div>
				<p>
					您成功为账号【
					<span class="text-info">
						<strong> ${username } </strong>
					</span>
					】充值
					<span class="text-success">
						<strong>${alipay.amount } </strong>
					</span>
					元。账户当前余额为
					<span class="text-success">
						<strong> ${balance } </strong>
					</span>
					元
				</p>
				<div class="row">
					<div class="col-xs-6">
						<table class="table">
							<thead>
								<tr>
									<th></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>名称</td>
									<td>${pay.subject }</td>
								</tr>
								<tr>
									<td>订单号</td>
									<td>${pay.tradeNo }</td>
								</tr>
								<tr>
									<td>支付方式</td>
									<td>${pay.bankName }</td>
								</tr>
								<tr>
									<td>支付宝流水号</td>
									<td>${pay.aliTradeNo }</td>
								</tr>
								<tr>
									<td>充值时间</td>
									<td>
										<fmt:formatDate value="${pay.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
								</tr>
								<tr>
									<td>描述</td>
									<td>${pay.description }</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</c:if>
			<c:if test="${!verify_result}">
				<div class="page-header">
					<h1>
						<span class="fa fa-warning text-danger"></span>
						充值失败
					</h1>
				</div>
				<p>
					您为账号【 <strong> <span class="text-info">${demo }</span>
					</strong> 】充值操作失败，系统未能成功验证支付宝返回结果。
				</p>
				<p>
					<span class="fa fa-hand-o-right"></span>
					您看到此结果不代表您本次充值操作不成功，也可能是其他原因导致。
				</p>
				<p>
					<span class="fa fa-hand-o-right"></span>
					如果您的支付宝已经支付成功并已扣款，您的充值结果会在稍后到账，切勿重复支付。
				</p>
				<p>
					<span class="fa fa-hand-o-right"></span>
					如有疑问或长时间未收到充值结果，您可以工单与我们联系，或联系我们的市场或客服。
				</p>
			</c:if>
		</div>
	</div>
</body>
</html>