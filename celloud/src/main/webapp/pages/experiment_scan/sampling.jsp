<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>样本采集</li>
    </ol>
    <div class="content sample">
        <div class="content-header clearfix">
          <img src="<%=request.getContextPath()%>/images/icon/sample_scan.jpg">
          <p>* 请持条码枪扫描样品管上的条码<br>
              无条码样品请按以下方式操作：<br> 1. 在样品管上记录样品病历号<br> 2. 将病历号输入上面窗口后回车
          </p>
          <form name="samplingForm" novalidate>
            <span class="input-alert" ng-show="samplingForm.$invalid">需要输入完整信息</span>
            <select class="tag-select" name="selTags" ng-model="selTags" ng-options="tag.tagName for tag in productTags" required>
            </select>
            <select class="type-select" name="type" ng-model="type" ng-options="type for type in typeList" required>
            </select>
            <div class="info-btn-group">
              <input class="field" type="text" ng-trim="true" name="sampleName" ng-model="sampleName" placeholder="扫描样本编号/病历号" required/>
              <button class="btn action" ng-click="addSample()" ng-disabled="samplingForm.$invalid">添加</button>
            </div>
          </form>
        </div>
        <div class="sample-btns clearfix">
          <button ng-click="commitSample()" class="btn -low pull-right" ng-disabled="sampleList.length <= 0">提交样本</button>
          <button id="sample-cancel" class="btn btn-reset -low pull-right" ng-disabled="sampleList.length <= 0">取消</button>
        </div>
        <form id="sample-form" method="post">
          <table class="table table-main">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>样品编号</th>
                    <th>检测项目</th>
                    <th>样品类型</th>
                    <th>更新时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr ng-repeat="sample in sampleList">
                    <td ng-bind="sampleList.length - $index">1</td>
                    <td>{{sample.sampleName }}<input type="hidden" name="sampleIds" value="{{sample.sampleId}}"></td>
                    <td>{{sample.tagName }}</td>
                    <td>{{sample.type }}</td>
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
        </form>
	</div>
</div>