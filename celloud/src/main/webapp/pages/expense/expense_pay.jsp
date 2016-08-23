<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <h2>账户余额： <span class="tips">￥2900</span></h2>
        <div class="pay-types">
           <lable class="pay-types-header">充值方式：</lable>
           <div class="pay-types-detail">
	           <label class="checkbox-lable">
	             <input class="checkbox" type="checkbox" name="demo-checkbox1" checked>
	             <span class="info"></span>
	           </label>
	           在线充值
           </div>
           <div class="pay-types-detail">
               <label class="checkbox-lable">
	             <input class="checkbox" type="checkbox" name="demo-checkbox1">
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
        <div class="pay-types">
           <lable class="pay-types-header">支付方式：</lable>
           <div class="pay-types-detail">
               <label class="checkbox-lable">
                 <input class="checkbox" type="checkbox" name="demo-checkbox1" checked>
                 <span class="info"></span>
               </label>
               支付宝
           </div>
           <div class="pay-types-detail">
               <label class="checkbox-lable">
                 <input class="checkbox" type="checkbox" name="demo-checkbox1">
                 <span class="info"></span>
               </label>
               企业网银
           </div>
           <div class="pay-types-detail">
               <label class="checkbox-lable">
                 <input class="checkbox" type="checkbox" name="demo-checkbox1">
                 <span class="info"></span>
               </label>
               个人网银
           </div>
        </div>
        <div class="bank-list">
          <ul class="pay-banks">
            <li class="bank-item">
              <label class="checkbox-inline">
                <label class="checkbox-lable">
                  <input class="checkbox" type="checkbox" name="demo-checkbox1" checked>
                  <span class="info"></span>
                </label>
                <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/404.gif">
              </label>
            </li>
            <li class="bank-item">
              <label class="checkbox-inline">
                  <label class="checkbox-lable">
                     <input class="checkbox" type="checkbox" name="demo-checkbox1">
                     <span class="info"></span>
                   </label>
                  <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/404.gif">
              </label>
            </li>
            <li class="bank-item">
              <label class="checkbox-inline">
                  <label class="checkbox-lable">
                     <input class="checkbox" type="checkbox" name="demo-checkbox1">
                     <span class="info"></span>
                   </label>
                  <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/404.gif">
              </label>
            </li>
            <li class="bank-item">
              <label class="checkbox-inline">
                  <label class="checkbox-lable">
                     <input class="checkbox" type="checkbox" name="demo-checkbox1">
                     <span class="info"></span>
                   </label>
                  <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/404.gif">
              </label>
            </li>
            <li class="bank-item">
              <label class="checkbox-inline">
                  <label class="checkbox-lable">
                     <input class="checkbox" type="checkbox" name="demo-checkbox1">
                     <span class="info"></span>
                   </label>
                  <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/404.gif">
              </label>
            </li>
            <li class="bank-item">
              <label class="checkbox-inline">
                  <label class="checkbox-lable">
                     <input class="checkbox" type="checkbox" name="demo-checkbox1">
                     <span class="info"></span>
                   </label>
                  <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/404.gif">
              </label>
            </li>
            <li class="bank-item">
              <label class="checkbox-inline">
                  <label class="checkbox-lable">
                     <input class="checkbox" type="checkbox" name="demo-checkbox1">
                     <span class="info"></span>
                   </label>
                  <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/404.gif">
              </label>
            </li>
            <li class="bank-item">
              <label class="checkbox-inline">
                  <label class="checkbox-lable">
                     <input class="checkbox" type="checkbox" name="demo-checkbox1">
                     <span class="info"></span>
                   </label>
                  <img alt="${bank.bankName }" title="${bank.bankName }" src="<%=request.getContextPath()%>/images/bank/404.gif">
              </label>
            </li>
          </ul>
        </div>
      </div>
    </div>
</div>