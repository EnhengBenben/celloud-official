<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.celloud.manager.constants.Bank"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="icon-hospital"></i>
            <a href="javascript:void(0)">费用中心</a>
        </li>
        <li class="active"><a href="javascript:void(0)">账户充值</a></li>
    </ul>
</div>
<div class="row" style="margin: 5px;">
	<div class="col-xs-12">
		<h3>
			<strong>账户余额:</strong>
			<label class="text-success">￥${balance }</label>
		</h3>
		<br>
		<form action="" class="form-horizontal">
			<div class="form-group">
				<label class="col-sm-1 control-label">充值方式</label>
				<div class="col-sm-11">
					<label class="checkbox-inline">
						<input id="onlineRechargeRadio" type="radio" checked="checked" name="rechargeType" value="1">
						在线充值
					</label>
					<label class="checkbox-inline">
						<input id="companyTransferRadio" type="radio" name="rechargeType" value="2">
						公司转账
					</label>
				</div>
			</div>
		</form>
		<div id="onlineRecharge">
			<form action="<%=request.getContextPath() + "/pay/recharge/"%>" class="form-horizontal" target="_blank" id="rechargeForm" method="post">
				<div class="form-group" id="moneyGroup">
					<label class="col-sm-1 control-label">金额(￥)</label>
					<div class="col-sm-11">
						<label class="checkbox-inline">
							<input type="text" name="money" class="form-control" value="10" aria-describedby="helpBlock" />
						</label>
						<span id="money-int" class="text-danger" style="display: none;">请不要充入1元以下的“零钱”哦！</span>
						<span id="money-min" class="text-danger" style="display: none;">充值金额要大于10元哦！</span>
						<span id="money-max" class="text-danger" style="display: none;">大于10000元的充值金额，请使用公司转账方式充值！</span>
						<span id="money-number" class="text-danger" style="display: none;">请正确输入充值金额！</span>
						<span id="pay_type" class="text-danger" style="display: none;">请选择您需要的付款方式！</span>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-11">
						<div>
							<ul class="nav nav-tabs" role="tablist">
								<li role="presentation" class="active">
									<a href="#pay_tab_alipay" aria-controls="pay_tab_alipay" role="tab" data-toggle="tab">支付宝</a>
								</li>
								<li role="presentation">
									<a href="#pay_tab_b2b" aria-controls="pay_tab_b2b" role="tab" data-toggle="tab">企业网银</a>
								</li>
								<li role="presentation">
									<a href="#pay_tab_b2c" aria-controls="pay_tab_b2c" role="tab" data-toggle="tab">个人网银</a>
								</li>
							</ul>
							<div class="tab-content">
								<div role="tabpanel" class="tab-pane active" id="pay_tab_alipay">
									<ul class="pay-banks">
										<li class="pay-item alone">
											<label class="checkbox-inline">
												<input type="radio" name="pay_type" value="alipay" checked="checked" id="pay_type_alipay">
												<img alt="支付宝" src="<%=request.getContextPath()%>/images/bank/alipay.gif" />
											</label>
										</li>
									</ul>
								</div>
								<div role="tabpanel" class="tab-pane" id="pay_tab_b2b">
									<ul class="pay-banks">
										<c:set var="b2bBanks" value="<%=Bank.listB2B()%>"></c:set>
										<c:forEach items="${b2bBanks}" var="bank">
											<li class="pay-item">
												<label class="checkbox-inline">
													<input type="radio" name="pay_type" value="${bank.bankCode }">
													<img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/${bank.bankLogo }.gif">
												</label>
											</li>
										</c:forEach>
									</ul>
								</div>
								<div role="tabpanel" class="tab-pane" id="pay_tab_b2c">
									<ul class="pay-banks">
										<c:set var="b2cBanks" value="<%=Bank.listB2C()%>"></c:set>
										<c:forEach items="${b2cBanks}" var="bank">
											<li class="pay-item">
												<label class="checkbox-inline">
													<input type="radio" name="pay_type" value="${bank.bankCode }">
													<img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/${bank.bankLogo }.gif">
												</label>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-offset-1 col-xs-2">
						<label class="checkbox-inline">
							<button class="btn btn-success">现在充值</button>
						</label>
					</div>
				</div>
			</form>
		</div>
		<div id="companyTransfer" class="hide">
			<br>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>开户银行</th>
						<th>账户名</th>
						<th>账户</th>
						<!-- th>银行电汇代码</th -->
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>中国工商银行股份有限公司上海市临空支行</td>
						<td>上海华点云生物科技有限公司</td>
						<td>1001 2883 0930 0297 407</td>
						<!-- td>XXXXX</td -->
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
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
