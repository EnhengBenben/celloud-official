<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_sample_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>样本管理</li>
      <li>样本信息采集</li>
    </ol>
    <div class="content sample">
        <div class="content-header clearfix">
          <img src="<%=request.getContextPath()%>/images/icon/sample_scan.jpg">
          <p>* 请持条码枪扫描样品管上的条码<br>
              无条码样本请按以下方式操作：<br> 1. 在样本管上记录样品病历号<br> 2. 将病历号填入右侧输入框后回车
          </p>
          <div class="info-btn-group">
            <button ng-click="commitSample()" class="btn -low pull-right" ng-disabled="sampleList.length <= 0">提交订单</button>
            <button data-toggle="modal" data-target="#sampleInfoModal" class="btn -low pull-right" style="margin-right: 10px;" >新建</button>
          </div>
        </div>
        <table class="table table-main">
          <thead>
              <tr>
                  <th>样本编号</th>
                  <th>检测项目</th>
                  <th>样本类型</th>
                  <th>姓名</th>
                  <th>性别</th>
                  <th>年龄</th>
                  <th>联系电话</th>
                  <th>身份证号</th>
                  <th>更新时间</th>
                  <th>操作</th>
              </tr>
          </thead>
          <tbody>
              <tr ng-repeat="sample in sampleList">
                  <td>{{sample.sampleName }}</td>
                  <td>{{sample.tagName }}</td>
                  <td>{{sample.type }}</td>
                  
                  <td>{{sample.sampleName }}</td>
                  <td>{{sample.sampleName }}</td>
                  <td>{{sample.sampleName }}</td>
                  <td>{{sample.sampleName }}</td>
                  <td>{{sample.sampleName }}</td>
                  
                  <td>{{sample.createDate | date : 'yyyy-MM-dd HH:mm:ss'}}</td>
                  <td>
                      <a href="javascript:void(0)" ng-click="deleteSample(sample.sampleId)">
                          <i class="fa fa-times-circle" aria-hidden="true"></i>
                      </a>
                  </td>
              </tr>
              <tr ng-if="sampleList.size()==0">
                  <td colspan="6" class="table-null">请按左侧提示进行操作</td>
              </tr>
          </tbody>
        </table>
	</div>
</div>
<div id="sampleInfoModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
          <h4 class="modal-title">查看发票信息</h4>
        </div>
        <div class="modal-body">
          <h5>快递单号：</h5>
          <h5>基本信息</h5>
          <table class="table table-main info-table">
            <tbody>
              <tr>
                <td>发票类型</td>
                <td></td>
              </tr>
              <tr>
                <td>金&emsp;&emsp;额</td>
                <td></td>
              </tr>
              <tr>
                <td>抬&emsp;&emsp;头</td>
                <td></td>
              </tr>
            </tbody>
          </table>
          <h5>邮寄信息</h5>
          <table class="table table-main info-table">
            <tbody>
              <tr>
                <td>地&emsp;&emsp;&emsp;址</td>
                <td></td>
              </tr>
              <tr>
                <td>收&emsp;件&emsp;人</td>
                <td></td>
              </tr>
              <tr>
                <td>收件人电话</td>
                <td></td>
              </tr>
              <tr>
                <td>创建&emsp;时间</td>
                <td></td>
              </tr>
            </tbody>
          </table>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->