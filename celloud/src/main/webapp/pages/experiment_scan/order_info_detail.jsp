<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
  <div class="print-button">
    <a ng-click="send()" class="btn -low" style="border-radius:6px;" title="发送邮件" href="javascript:void(0)"><i class="fa fa-paper-plane"></i></a>
    <a ng-click="print()" class="btn -low" style="border-radius:6px;" title="打印" href="javascript:void(0)"><i class="fa fa-print"></i></a>
  </div>
  <div class="title-content">
      <div class="title">样本寄送订单</div>
  </div>
  <div class="clearfix" style="height: 80px">
    <div class="pull-left" style="margin-top: 20px">
	  <h4>订单编号：<span>{{sampleOrderInfo.sampleOrder.orderNo}}</span></h4><br>
	  <h4>下单日期：<span>{{sampleOrderInfo.sampleOrder.createDate| date : 'yyyy-MM-dd HH:mm:ss'}}</span></h4>
    </div>
    <img class="pull-right" width="76" alt="" ng-src="<%=request.getContextPath()%>/user/icon/temp?file={{sampleOrderInfo.sampleOrder.orderNo}}.png">
  </div>
  <div class="tests-content">
	  <table class="table table-count">
	    <thead>
	        <tr>
	            <th>序号</th>
	            <th>样本编号</th>
	            <th>姓名</th>
	            <th>性别</th>
	            <th>年龄</th>
	            <th>联系电话</th>
	            <th>身份证号</th>
	            <th>更新时间</th>
	        </tr>
	    </thead>
	    <tbody>
	        <tr ng-repeat="sample in sampleOrderInfo.sampleInfos">
	            <td ng-bind="sampleOrderInfo.sampleInfos.length - $index">1</td>
	            <td>{{sample.sampleName }}</td>
	            <td>{{sample.name }}</td>
	            <td>{{sample.gender == 0 ? '女' : '男' }}</td>
	            <td>{{sample.age }}</td>
	            <td>{{sample.tel }}</td>
	            <td>{{sample.idCard }}</td>
	            <td>{{sample.updateDate | date : 'yyyy-MM-dd HH:mm:ss'}}</td>
	        </tr>
	    </tbody>
	  </table>
  </div>