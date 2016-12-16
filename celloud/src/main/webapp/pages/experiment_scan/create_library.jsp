<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_experiment_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>实验管理</li>
      <li>文库构建</li>
    </ol>
    <div class="content library">
        <div class="content-header clearfix">
		  <div class="library-name">文库编码：{{infos.libraryName}}</div>
          <span class="input-alert" ng-show="sampleName.$dirty && sampleName.$error.required">请输入样本编号！</span>
	      <div class="info-btn-group">
            <input class="field" type="text" ng-trim="true" ng-model="sampleName" ng-keypress="doOnKeyPress($event)" required placeholder="扫码或者输入实验样本编号"/>
            <a class="action" ng-click="addSample()">扫码入库</a>
          </div>
          <div class="library-btns pull-right">
            <select class="form-control" ng-model="sindex" ng-options="(meta.name+':'+meta.seq) for meta in infos.metaList" style="padding-top:3px;" >
            	<option value="">文库index</option>
	        </select>
            <button ng-click="addLibrary()" class="btn -low pull-right" ng-disabled="infos.pageList.datas.length <= 0 || sindex=='' || sindex==undefined">建库</button>
            <button ng-click="addAndDownLibrary()" class="btn btn-reset -low pull-right" ng-disabled="infos.pageList.datas.length <= 0 || sindex=='' || sindex==undefined">建库并下载</button>
          </div>
        </div>
        <div class="table-opera clearfix">
           <span class="tips">提示： 每个文库最多可添加{{infos.sampleIndex.length}}个样本。</span>
        </div>
        <div ng-controller="editSampleController">
          <table class="table table-main">
            <thead>
                <tr>
                    <th>序号</th>
                    <th>实验样本编号</th>
                    <th>样本类型</th>
                    <th>采样时间</th>
                    <th>样品index</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr ng-repeat="sample in infos.pageList.datas">
                    <td ng-bind="$index + 1"></td>
                    <td>{{sample.experSampleName }}</td>
                    <td>{{sample.type }}</td>
                    <td>{{sample.updateDate | date : 'yyyy-MM-dd HH:mm:ss'}}</td>
                    <td>{{sample.sindex }}</td>
                    <td>建库</td>
                    <td>
                        <a ng-click="remove(sample.sampleLogId)">
                            <i class="fa fa-times-circle" aria-hidden="true"></i>
                        </a>
                    </td>
                </tr>
            </tbody>
          </table>
        </div>
	</div>
</div>