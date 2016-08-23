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
	             <input class="radio" type="radio" name="pay-type" checked>
	             <span class="info"></span>
	           </label>
	           在线充值
           </div>
           <div class="pay-types-detail">
               <label class="radio-lable">
                 <input class="radio" type="radio" name="pay-type">
                 <span class="info"></span>
               </label>
	           公司转账
           </div>
        </div>
        <div class="pay-money">
           <lable class="pay-types-header">金<span class="pull-right">额：</span></lable>
           <div class="pay-money-detail">
             <input type="text" name="money" class="money-input" value="10" aria-describedby="helpBlock" />
             <button class="btn btn-money-confirm">确定</button>
           </div>
        </div>
        <div class="pay-types nav-tabs" role="tablist">
           <lable class="pay-types-header">支付方式：</lable>
           <div class="pay-types-detail">
               <label class="radio-lable">
                 <input class="radio" type="radio" name="pay-way" checked ng-click="tab = 'pay_tab_alipay'">
                 <span class="info"></span>
               </label>
               支付宝
           </div>
           <div class="pay-types-detail">
               <label class="radio-lable">
                 <input class="radio" type="radio" name="pay-way" ng-click="tab = 'pay_tab_b2b'">
                 <span class="info"></span>
               </label>
               企业网银
           </div>
           <div class="pay-types-detail">
               <label class="radio-lable">
                 <input class="radio" type="radio" name="pay-way" ng-click="tab = 'pay_tab_b2c'">
                 <span class="info"></span>
               </label>
               个人网银
           </div>
        </div>
       	<div role="tabpanel" class="bank-list" ng-if="tab == 'pay_tab_alipay'" >
          <ul class="pay-banks tab-pane">
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
        <div role="tabpanel" class="bank-list" ng-if="tab == 'pay_tab_b2b'" >
          <ul class="pay-banks">
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
      	<div role="tabpanel" class="bank-list" ng-if="tab == 'pay_tab_b2c'" >
          <ul class="pay-banks">
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
      </div>
    </div>
</div>