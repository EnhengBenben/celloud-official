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
      </div>
      <div class="table-opera">
        <button type="button" class="btn" data-toggle="modal" ng-click="applyInvoice()">申请发票</button>
      </div>
      <table class="table table-main" ng-init="pageType='expense/paylist'">
        <thead>
          <tr>
            <th>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" name="checkAll" id="checkAll" >
                <span class="info"></span>
              </label>
            </th>
            <th>总额</th>
            <th>类型</th>
            <th>充值时间</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="recharge in dataList.datas">
            <td>
              <label class="checkbox-lable">
                <input class="checkbox" type="checkbox" value="{{recharge.id}}" ng-click="rechargeIds()" name="rechargeIds" ng-disabled="recharge.invoiceState!=0">
                <span class="info"></span>
              </label>
            </td>
            <td>{{recharge.amount}}</td>
            <td>{{recharge.rechargeTypeName}}</td>
            <td>{{recharge.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
          </tr>
        </tbody>
      </table>
      <pagination page="dataList.page" change="pageQueryRecharge(page,pageSize)"></pagination>
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
          <form class="form-horizontal info-form" id="invoiceForms" name="invoiceForms" ng-submit="apply()">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">申请金额：</div>
	            <div class="col-xs-9">
	                <input type="text" ng-model="invoiceForm.money" id="money" readonly="readonly"/>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">发票类型：</div>
	            <div class="col-xs-9 form-group-content">
	               <label class="radio-lable">
                     <input class="radio" type="radio" value="0" id="generalTicket" name="invoiceType" checked>
                     <span class="info"></span>
                   </label>
                   公司普票
                   <label class="radio-lable">
                     <input class="radio" type="radio" value="1" id="specialTicket" name="invoiceType">
                     <span class="info"></span>
                   </label>
                   公司专票
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">公司抬头：</div>
	            <div class="col-xs-9">
	                <input type="text" id="invoiceHeader" name="invoiceHeader" ng-model="invoiceForm.invoiceHeader" maxlength="45" ng-model="invoiceHeader" required/>
	                <span class="invoice-modal-error"></span>
	                <span class="input-alert break-line" ng-show="invoiceForms.invoiceHeader.$invalid">公司抬头不能为空!</span>
	            </div>
	          </div>
	          <h5>邮寄信息</h5>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">公司地址：</div>
	            <div class="col-xs-9">
	                <input type="text" id="address" name="address" ng-model="invoiceForm.address" maxlength="45" ng-model="address" required/>
	                <span class="invoice-modal-error"></span>
	                <span class="input-alert break-line" ng-show="invoiceForms.address.$invalid">公司地址不能为空!</span>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">联系人：</div>
	            <div class="col-xs-9">
	                <input type="text" id="contacts" name="contacts" ng-model="invoiceForm.contacts" maxlength="45" ng-model="contacts" required/>
	                <span class="invoice-modal-error"></span>
	                <span class="input-alert break-line" ng-show="invoiceForms.contacts.$invalid">联系人不能为空!</span>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-3">联系方式：</div>
	            <div class="col-xs-9">
	                <input type="text" id="cellphone" name="cellphone" ng-model="invoiceForm.cellphone" maxlength="45" ng-pattern="/^((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))\d{8}$/" required />
	                <span class="invoice-modal-error"></span>
	                <span class="input-alert break-line" ng-show="invoiceForms.cellphone.$invalid">请输入正确的手机号码!</span>
	            </div>
	          </div>
	          <input type="hidden" id="rechargeIds" />
	          <div class="form-group">
	            <div class="text-center">
	                <button type="button" class="btn btn-cancel" ng-click="reset()">重置</button>
	                <button type="submit" class="btn" ng-disabled="invoiceForms.$invalid" id="invoiceSubmit" >提交</button>
	            </div>
	          </div>
	      </form>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
  <div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="state">
    <button type="button" class="close" ng-click="state=false"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
    <span>{{message}}</span>
  </div>
</div>

