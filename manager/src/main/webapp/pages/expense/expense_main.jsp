<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<section class="content-header">
	<h1>
		<small>&nbsp;</small>
	</h1>
	<ol class="breadcrumb">
		<li>
			<a href="javascript:javascript:void(0);">
				<i class="fa fa-rmb"></i>费用中心
			</a>
		</li>
		<li>
			<a href="javascript:void(0);" id="secondClassifyName">消费记录</a>
		</li>
		<li class="hide">
			<span id="appNameTitle">应用名称</span>
		</li>
	</ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="mainpage" id="appMain">
				<div class="y-row operation-serve box box-success" data-spm="16">
					<div class="info">
						<p>您可以在账户钱包中进行充值、开发票、激活优惠券等操作，也可以查询充值记录和管理发票。</p>
					</div>
					<ul id="expense-box" class="app-classify-ul" data-step="2" data-intro="" data-position="bottom"
						data-img="checkapp.png">
						<li id="to-pay-detail" class="active">
							<a href="javascript:void(0)">消费明细</a>
						</li>
						<li id="to-recharge" class="">
							<a href="javascript:void(0)">账户充值</a>
						</li>
						<li id="to-recharge-record" class="">
							<a href="javascript:void(0)">充值记录</a>
						</li>
						<li id="to-invoice" class="">
							<a href="javascript:void(0)">发票管理</a>
						</li>
					</ul>
				</div>
				<div id="expense-content" class="box"></div>
			</div>
		</div>
	</div>
</section>
<div id="tip-modal" class="modal fade modal-celloud-green">
	<div class="modal-dialog">
		<div id="tip-modal-content" class="modal-content tipModal">
			<div id="tip-modal-head" class="modal-header" id="">
				<button class="close" type="button" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">
					<i class="icon fa fa-warning"></i>等待充值
				</h4>
			</div>
			<div class="modal-body">
				<p id="tip-text">请在新窗口中完成支付</p>
			</div>
			<div class="modal-footer">
				<button id="check-true" type="button" class="btn btn-outline" onclick="$.expense.pay.recharge();">已支付</button>
				<button id="check-flase" type="button" class="btn btn-outline" onclick="$.expense.pay.recharge();">取消</button>
			</div>
		</div>
	</div>
</div>
<script src="<%=request.getContextPath()%>/js/expense.js" type="text/javascript"></script>