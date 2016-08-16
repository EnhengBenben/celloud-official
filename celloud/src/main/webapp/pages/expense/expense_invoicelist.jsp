<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_expense_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>费用设置</li>
      <li>发票管理</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p>您可以在账户钱包中进行充值、开发票、激活优惠券等操作，也可以查询充值记录和管理发票。</p>
        <p class="description">C币是CelLoud币，是CelLoud平台中的虚拟货币。</p>
      </div>
      <div class="table-opera">
        <span class="tips">
             发票金额可以是多次充值金额之和，建议您积累到一定金额后一并申请，若您在超过15工作日仍未收到发票，请工单与我们联系。   
        </span>
      </div>
      <table class="table table-main">
        <thead>
          <tr>
            <th>消费时间</th>
            <th>消费明细</th>
            <th>消费C币</th>
            <th>备注</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td></td>
            <td></td>
            <td></td>
            <td>sss</td>
          </tr>
        </tbody>
      </table>
      <ng-include src="'pages/partial/_partial_pagination_common.jsp'" ></ng-include>
    </div>
</div>