<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="row" style="margin: 5px;">
	<div class="col-xs-12">
		<div class="alert alert-success" style="color: #fff">
			<strong>充值优惠：</strong>凡单笔充值达到 5000 返现 5%，达到 1万 返现 10%，达到 10万 返现 15%，达到 100万 返现 20%。
		</div>
		<br>
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
			<form action="<%=request.getContextPath() + "/pay/recharge/alipay"%>" class="form-horizontal" target="_blank" id="rechargeForm"
				method="post">
				<div class="form-group">
					<label class="col-sm-1 control-label">金额(￥)</label>
					<div class="col-sm-11">
						<label class="checkbox-inline">
							<input type="text" name="money" class="form-control" value="10"  />
						</label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-1 control-label">支付方式</label>
					<div class="col-sm-11">
						<div class="row">
							<div class="col-xs-12">
								<label class="checkbox-inline">
									<input type="radio" checked="checked" name="pay_type" value="alipay">
									<span class="pay-logo alipay" title="支付宝"></span>
								</label>
							</div>
						</div>
						<ul class="pay-banks ">
							<li class="pay-item">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="BOCB2C">
									<span class="pay-logo disabled BOCB2C" title="暂不支持中国银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="ICBCB2C">
									<span class="pay-logo disabled ICBCB2C" title="暂不支持中国工商银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="CMB">
									<span class="pay-logo disabled CMB" title="暂不支持招商银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="CCB">
									<span class="pay-logo disabled CCB" title="暂不支持中国建设银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="ABC">
									<span class="pay-logo disabled ABC" title="暂不支持中国农业银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="SPDB">
									<span class="pay-logo disabled SPDB" title="暂不支持上海浦东发展银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="CIB">
									<span class="pay-logo disabled CIB" title="暂不支持兴业银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="GDB">
									<span class="pay-logo disabled GDB" title="暂不支持广东发展银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="SDB">
									<span class="pay-logo disabled SDB" title="暂不支持深圳发展银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="CMBC">
									<span class="pay-logo disabled CMBC" title="暂不支持中国民生银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="COMM">
									<span class="pay-logo disabled COMM" title="暂不支持中国交通银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="CITIC">
									<span class="pay-logo disabled CITIC" title="暂不支持中信银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="HZCBB2C">
									<span class="pay-logo disabled HZCBB2C" title="暂不支持杭州银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="CEB-DEBIT">
									<span class="pay-logo disabled CEB-DEBIT" title="暂不支持中国光大银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="SHBANK">
									<span class="pay-logo disabled SHBANK" title="暂不支持上海银行"></span>
								</label>
							</li>
							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="NBBANK">
									<span class="pay-logo disabled NBBANK" title="暂不支持宁波银行"></span>
								</label>
							</li>

							<li class="pay-item disabled">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="SPABANK">
									<span class="pay-logo disabled SPABANK" title="暂不支持平安银行"></span>
								</label>
							</li>
							<li class="pay-item">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="BJRCB">
									<span class="pay-logo disabled BJRCB" title="暂不支持北京农村商业银行"></span>
								</label>
							</li>
							<li class="pay-item">
								<label class="checkbox-inline disabled">
									<input disabled="disabled" type="radio" name="pay_type" value="FDB">
									<span class="pay-logo disabled FDB" title="暂不支持富滇银行"></span>
								</label>
							</li>
						</ul>
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
						<th>银行电汇代码
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>中国工商银行上海市临空支行</td>
						<td>上海华点云生物科技有限公司</td>
						<td>1000 1213 1314 5210</td>
						<td>XXXXX
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
