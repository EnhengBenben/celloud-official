<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_expense_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>费用设置</li>
      <li>充值记录</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p>您可以在账户钱包中进行充值、开发票、激活优惠券等操作，也可以查询充值记录和管理发票。</p>
        <p class="description">C币是CelLoud币，是CelLoud平台中的虚拟货币。</p>
      </div>
      <div class="table-opera">
        <button type="button" class="btn" data-toggle="modal" data-target="#apply-invoice-modal">申请发票</button>
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
    
  <div id="apply-invoice-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
          <h4 class="modal-title">申请发票</h4>
        </div>
        <div class="modal-body form-modal">
          <h5>发票信息</h5>
          <form class="form-horizontal info-form">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">申请金额：</div>
	            <div class="col-xs-9">
	                <input type="text" name="money" id="money" readonly="readonly"/>
	                <span class="input-alert break-line">金额不能小于100</span>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">发票类型：</div>
	            <div class="col-xs-9 form-group-content">
	               <label class="checkbox-lable">
                     <input class="checkbox" type="checkbox" name="demo-checkbox1" checked>
                     <span class="info"></span>
                   </label>
                   公司普票
                   <label class="checkbox-lable">
                     <input class="checkbox" type="checkbox" name="demo-checkbox1">
                     <span class="info"></span>
                   </label>
                   公司专票
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">公司名称：</div>
	            <div class="col-xs-9">
	                <input type="text" id="invoiceHeader" name="invoiceHeader" maxlength="45"/><span class="invoice-modal-error"></span>
	            </div>
	          </div>
	          <h5>邮寄信息</h5>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">公司地址：</div>
	            <div class="col-xs-9">
	                <input type="text" id="address" name="address" maxlength="45"/><span class="invoice-modal-error"></span>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">联系人：</div>
	            <div class="col-xs-9">
	                <input type="text" id="contacts" name="contacts" maxlength="45"/><span class="invoice-modal-error"></span>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">联系方式：</div>
	            <div class="col-xs-9">
	                <input type="text" id="cellphone" name="cellphone" maxlength="45"/><span class="invoice-modal-error"></span>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="text-center">
	                <button type="reset" class="btn btn-cancel">重置</button>
	                <button type="submit" class="btn" >提交</button>
	            </div>
	            <div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="state">
	              <button type="button" class="close" ng-click="state=false"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	              <span>{{message}}</span>
	            </div>
	          </div>
	      </form>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
</div>