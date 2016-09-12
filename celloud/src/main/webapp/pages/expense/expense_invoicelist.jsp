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
      </div>
      <div class="table-opera">
        <span class="tips">
             发票金额可以是多次充值金额之和，建议您积累到一定金额后一并申请，若您在超过15个工作日仍未收到发票，请提交问题反馈与我们联系。   
        </span>
      </div>
      <table class="table table-main" ng-init="pageType='expense/invoice'">
        <thead>
          <tr>
            <th>金额</th>
            <th>类型</th>
            <th>抬头</th>
            <th>地址</th>
            <th>状态</th>
            <th>备注</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="invoice in dataList.datas">
            <td>{{invoice.money}}</td>
            <td>{{invoice.invoiceType==0?'普票':'专票'}}</td>
            <td>{{invoice.invoiceHeader}}</td>
            <td>{{invoice.address}}</td>
            <td>{{invoice.invoiceState==0?'已申请':'已发出'}}</td>
            <td>{{invoice.remark==null?"暂无物流信息":invoice.remark}}</td>
            <td>{{invoice.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td><a class="btn-link" href="javascript:void(0)" data-toggle="modal" data-target="#invoice-detail-modal" ng-click="showInvoiceDetail(invoice.id)">详情</a></td>
          </tr>
        </tbody>
      </table>
      <pagination page="dataList.page" change="pageQueryInvoice(page,pageSize)"></pagination>
    </div>
  
  <div id="invoice-detail-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
          <h4 class="modal-title">查看发票信息</h4>
        </div>
        <div class="modal-body">
          <h5>快递单号：{{invoiceDetail.remark == null?'暂无物流信息':invoiceDetail.remark}}</h5>
          <h5>基本信息</h5>
          <table class="table table-main info-table">
            <tbody>
	          <tr>
	            <td>发票类型</td>
	            <td>{{invoiceDetail.invoiceType==0?'公司普票':'公司专票'}}</td>
	          </tr>
	          <tr>
                <td>金&emsp;&emsp;额</td>
                <td>￥{{invoiceDetail.money}}</td>
              </tr>
              <tr>
                <td>抬&emsp;&emsp;头</td>
                <td>{{invoiceDetail.invoiceHeader}}</td>
              </tr>
	        </tbody>
          </table>
          <h5>邮寄信息</h5>
          <table class="table table-main info-table">
            <tbody>
              <tr>
                <td>地&emsp;&emsp;&emsp;址</td>
                <td>{{invoiceDetail.address}}</td>
              </tr>
              <tr>
                <td>收&emsp;件&emsp;人</td>
                <td>{{invoiceDetail.contacts}}</td>
              </tr>
              <tr>
                <td>收件人电话</td>
                <td>{{invoiceDetail.cellphone}}</td>
              </tr>
              <tr>
                <td>创建&emsp;时间</td>
                <td>{{invoiceDetail.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
</div>