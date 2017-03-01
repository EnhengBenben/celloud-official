<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
  <div class="print-button">
    <a ng-click="print()" class="btn -low" style="border-radius:6px;" title="打印" href="javascript:void(0)"><i class="fa fa-print"></i></a>
  </div>
  <div class="title-content">
      <div class="title">样本寄送订单</div>
  </div>
  <div class="clearfix" style="position: relative;">
    <div style="margin-top: 20px">
      <h4>订单编号：<span>{{sampleOrderInfo.sampleOrder.orderNo}}</span></h4><br>
      <h4>下单日期：<span>{{sampleOrderInfo.sampleOrder.createDate| date : 'yyyy-MM-dd HH:mm:ss'}}</span></h4><br/>
      <h4 ng-if="userProduct.app123==123">寄送信息：<span>艾吉泰康-张三-13800138000-北京市朝阳区京顺东街8号北京地坛医院</span></h4><br/>
    </div>
    <img style="position: absolute;right: 0px; top: 0px;" width="76" alt="" ng-src="<%=request.getContextPath()%>/user/icon/temp?file={{sampleOrderInfo.sampleOrder.orderNo}}.png">
  </div>
  <div class="tests-content">
	  <table class="table table-count">
	    <thead>
	        <tr>
	            <th>序号</th>
	            <th>样本编号</th>
	            <th>实验样本编号</th>
	            <th>检测项目</th>
	            <th>样本类型</th>
	            <th>更新时间</th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr ng-repeat="sample in sampleOrderInfo.samples">
	            <td ng-bind="sampleOrderInfo.samples.length - $index">1</td>
	            <td>{{sample.sampleName }}</td>
	            <td>{{sample.experSampleName }}</td>
	            <td>{{sample.tagName }}</td>
	            <td>{{sample.type }}</td>
	            <td>{{sample.createDate | date : 'yyyy-MM-dd HH:mm:ss'}}</td>
	        </tr>
	    </tbody>
	  </table>
	  <p>
	    本次样品为 {{sampleOrderInfo.samples.length}} 管血液样品 , 寄样人：${loginUserInSession.username } 联系电话：${loginUserInSession.cellphone }<br/>
	    备注：在送样进入实验室，需要把该订单打印并交付到样本接受人员手中，否则无法进行样本入库及影响后续的实验安排。
	  </p>
  </div>