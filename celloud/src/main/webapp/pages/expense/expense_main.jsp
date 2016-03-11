<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:javascript:void(0);"><i class="fa fa-rmb"></i>费用中心</a></li>
    <li class="hide"><a href="javascript:void(0);" id="secondClassifyName">消费记录</a></li>
    <li class="hide"><span id="appNameTitle">应用名称</span></li>
  </ol>
</section>
<section class="content">
<div class="row">
  <div class="col-xs-12">
    <div class="mainpage" id="appMain">
      <div class="y-row operation-serve box box-success"  data-spm="16">
        <div class="info">
          <p>您可以在账户钱包中进行充值、开发票、激活优惠券等操作，也可以查询充值记录和管理发票。C币是CelLoud币，是CelLoud平台中的虚拟货币。</p>
        </div>
        <ul id="" class="app-classify-ul" data-step="2" data-intro="" data-position="bottom" data-img="checkapp.png">
          <li id="to-pay-detail" class="active"><a href="javascript:void(0)">消费明细</a></li>
        </ul>
      </div>
      <div id="expense-content" class="box">
        
      </div>
    </div>
  </div>
</div>
</section>
<script src="<%=request.getContextPath()%>/js/expense.js" type="text/javascript"></script>