<%@page import="com.celloud.constants.Bank"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="pay-content base">
  <h2>账户余额</h2>
  <form action="" class="form-horizontal" target="_blank" id="rechargeForm" method="post">
      <div class="pay-money">
         <lable class="pay-types-header">金<span class="pull-right">额：</span></lable>
         <div class="pay-money-detail">
           <input type="hidden" name="money" value="2800">
           <h2><strong>2800元</strong></h2>
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
      </div>
      <ul class="pay-banks clearfix">
        <li class="bank-item">
          <label class="checkbox-inline">
            <label class="radio-lable">
              <input id="alipay" class="radio" type="radio" name="pay-bank" checked>
              <span class="info"></span>
            </label>
            <img alt="支付宝" src="<%=request.getContextPath()%>/images/bank/alipay.gif" />
          </label>
        </li>
      </ul>
      <ul class="pay-banks clearfix">
        <c:set var="b2cBanks" value="<%=Bank.listB2C()%>"></c:set>
        <c:forEach items="${b2cBanks}" var="bank" varStatus="st">
            <li class="bank-item">
              <label class="checkbox-inline">
                <label class="radio-lable">
                  <input class="radio" type="radio" name="pay_bank" <c:if test="${st.first }">ng-checked="tab == 'pay_tab_b2c'"</c:if> value="${bank.bankCode }">
                  <span class="info"></span>
                </label>
                <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/${bank.bankLogo }.gif">
              </label>
            </li>
        </c:forEach>
      </ul>
    <div class="pay-btns">
       <button type="submit" class="btn -low" ng-click="sumbitRecharge()" ng-disabled="checkSubmit">现在充值</button>
    </div>
  </form>
</div>