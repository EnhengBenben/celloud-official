<%@page import="com.celloud.constants.Bank"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ng-include src="'pages/partial/_partial_expense_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>费用设置</li>
      <li>账户充值</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p>您可以在账户钱包中进行充值、开发票、激活优惠券等操作，也可以查询充值记录和管理发票。</p>
      </div>
      <!-- <div class="table-opera">
        <span class="tips">
             充值优惠：凡单笔充值达到&nbsp;5000&nbsp;返现&nbsp;5%，达到&nbsp;1万&nbsp;返现&nbsp;10%，达到&nbsp;10万&nbsp;返现&nbsp;15%，达到&nbsp;100万&nbsp;返现&nbsp;20%。  
        </span>
      </div> -->
      <div class="content-body pay-content">
        <h2>账户余额： <span class="tips">￥{{balance}}</span></h2>
        <div class="pay-types">
           <lable class="pay-types-header">充值方式：</lable>
           <div class="pay-types-detail">
	           <label class="radio-lable">
	             <input class="radio" type="radio" name="pay_type" ng-click="pay_type = 'online'" checked>
	             <span class="info"></span>
	           </label>
	           在线充值
           </div>
           <div class="pay-types-detail">
               <label class="radio-lable">
                 <input class="radio" type="radio" name="pay_type" ng-click="pay_type = 'transfer'">
                 <span class="info"></span>
               </label>
	           公司转账
           </div>
        </div>
        <div class="pay-online" ng-if="pay_type == 'online'">
	        <form action="${pageContext.request.contextPath }/pay/recharge/" class="form-horizontal" target="_blank" id="rechargeForm" method="post">
	        	<div class="pay-money">
		           <lable class="pay-types-header">金<span class="pull-right">额：</span></lable>
		           <div class="pay-money-detail">
		             <input type="text" id="money" name="money" class="money-input" ng-model="rechargeForm.money" ng-change="checkMoney()" aria-describedby="helpBlock" />
		             <span class="input-alert break-line" ng-show="checkFlag">{{moneyError}}</span>
		           </div>
		        </div>
		        <div class="pay-types nav-tabs">
		           <lable class="pay-types-header">支付方式：</lable>
		           <div class="pay-types-detail">
		               <label class="radio-lable">
		                 <input class="radio" type="radio" name="pay-way" value="alipay" checked ng-click="tab = 'pay_tab_alipay'">
		                 <span class="info"></span>
		               </label>
		               支付宝
		           </div>
		           <div class="pay-types-detail">
		               <label class="radio-lable">
		                 <input class="radio" type="radio" name="pay-way" value="jdpay" ng-click="tab = 'pay_tab_b2b'">
		                 <span class="info"></span>
		               </label>
		               企业网银
		           </div>
		           <div class="pay-types-detail">
		               <label class="radio-lable">
		                 <input class="radio" type="radio" name="pay-way" value="jdpay" ng-click="tab = 'pay_tab_b2c'">
		                 <span class="info"></span>
		               </label>
		               个人网银
		           </div>
		        </div>
		        <div class="bank-list" ng-if="tab == 'pay_tab_alipay'">
		          <ul class="pay-banks clearfix">
		            <li class="bank-item">
		              <label class="checkbox-inline">
		                <label class="radio-lable">
		                  <input class="radio" type="radio" name="pay-bank" checked="checked" value="alipay" checked>
		                  <span class="info"></span>
		                </label>
						<img alt="支付宝" src="<%=request.getContextPath()%>/images/bank/alipay.gif" />
		              </label>
		            </li>
		          </ul>
		        </div>
		        <div class="bank-list" ng-if="tab == 'pay_tab_b2b'" >
		          <ul class="pay-banks clearfix">
		          	<c:set var="b2bBanks" value="<%=Bank.listB2B()%>"></c:set>
					<c:forEach items="${b2bBanks}" var="bank">
						<li class="bank-item">
			              <label class="checkbox-inline">
			                <label class="radio-lable">
			                  <input class="radio" type="radio" name="pay-bank" value="${bank.bankCode }">
			                  <span class="info"></span>
			                </label>
			                <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/${bank.bankLogo }.gif">
			              </label>
			            </li>
					</c:forEach>
		          </ul>
		        </div>
		      	<div class="bank-list" ng-if="tab == 'pay_tab_b2c'" >
		          <ul class="pay-banks clearfix">
		          	<c:set var="b2cBanks" value="<%=Bank.listB2C()%>"></c:set>
					<c:forEach items="${b2cBanks}" var="bank">
						<li class="bank-item">
			              <label class="checkbox-inline">
			                <label class="radio-lable">
			                  <input class="radio" type="radio" name="pay-bank" value="${bank.bankCode }">
			                  <span class="info"></span>
			                </label>
			                <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/${bank.bankLogo }.gif">
			              </label>
			            </li>
					</c:forEach>
		          </ul>
		      </div>
		      <div class="pay-btns">
		         <button type="submit" class="btn -low" ng-disabled="checkSubmit">现在充值</button>
		      </div>
	        </form>
        </div>
        <div class="pay-transfer" ng-if="pay_type == 'transfer'">
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