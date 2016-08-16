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
          <tr>
            <td></td>
            <td></td>
            <td></td>
            <td>sss</td>
            <td></td>
            <td></td>
            <td></td>
            <td><a class="btn-link" href="javascript:void(0)" data-toggle="modal" data-target="#invoice-detail-modal">详情</a></td>
          </tr>
        </tbody>
      </table>
      <ng-include src="'pages/partial/_partial_pagination_common.jsp'" ></ng-include>
    </div>
  
  <div id="invoice-detail-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
          <h4 class="modal-title">查看发票信息</h4>
        </div>
        <div class="modal-body">
          <h5>快递单号：申通:227547879908</h5>
          <h5>基本信息</h5>
          <table class="table table-main info-table">
            <tbody>
	          <tr>
	            <td>发票类型</td>
	            <td>普通发票</td>
	          </tr>
	          <tr>
                <td>金&emsp;&emsp;额</td>
                <td>￥1000.00</td>
              </tr>
              <tr>
                <td>抬&emsp;&emsp;头</td>
                <td>上海华点云生物科技有限公司</td>
              </tr>
	        </tbody>
          </table>
          <h5>基本信息</h5>
          <table class="table table-main info-table">
            <tbody>
              <tr>
                <td>地&emsp;&emsp;&emsp;址</td>
                <td>上海市长宁金钟路999号C栋易贸大楼</td>
              </tr>
              <tr>
                <td>收&emsp;件&emsp;人</td>
                <td>夏天</td>
              </tr>
              <tr>
                <td>收件人电话</td>
                <td>13813838388</td>
              </tr>
              <tr>
                <td>创建&emsp;时间</td>
                <td>2016-05-04 17:44:00</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
</div>