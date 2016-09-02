<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_experiment_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>实验管理</li>
      <li>文库构建</li>
    </ol>
    <div class="content sample">
        <div class="content-header clearfix">
          <img src="<%=request.getContextPath()%>/images/icon/sample_scan.jpg">
          <p>* 请持条码枪扫描样品管上的条码<br>
              无条码样品请按以下方式操作：<br> 1. 在样品管上记录样品病历号<br> 2. 将病历号输入上面窗口后回车
          </p>
          <span  class="input-alert">此样品信息已经收集过，请核查或者采集下一管样品信息！</span>
          <div class="info-btn-group">
            <input class="field" type="text" placeholder="扫描样本编号/病历号"/>
            <a class="action" ng-click="conditionList()">扫码提DNA</a>
          </div>
        </div>
        <table class="table table-main">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>样品编号</th>
                    <th>样品类型</th>
                    <th>采样时间</th>
                    <th>样品index</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody id="rocky-sample-list">
              <tr ng-if="sample.size()>0">
                  <td>${samples.size() - size.index }<input type="hidden" name="sampleIds" value="${sample.sampleId }">
                  </td>
                  <td>${sample.sampleName }</td>
                  <td>
                      <fmt:formatDate value="${sample.createDate }" type="both" dateStyle="long" pattern="yyyy-MM-dd HH:mm:ss" />
                  </td>
                  <td>
                      <a data-click="del-sample" data-id="${sample.sampleId }" href="javascript:void(0)">
                          <i class="fa fa-times-circle" aria-hidden="true"></i>
                      </a>
                  </td>
              </tr>
              <tr ng-if="sample.size()<=0">
                  <td colspan="7" class="table-null">请按上面提示进行操作</td>
              </tr>
            </tbody>
        </table>
	</div>
</div>