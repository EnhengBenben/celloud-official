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
          <select ng-model="sindex">
              <option value="文库index">文库index</option>
              <option value="index1:ATCACG">index1:ATCACG</option>
              <option value="index2:CGATGT">index2:CGATGT</option>
              <option value="index3:TTAGGC">index3:TTAGGC</option>
              <option value="index4:TGACCA">index4:TGACCA</option>
              <option value="index5:ACAGTG">index5:ACAGTG</option>
              <option value="index6:GCCAAT">index6:GCCAAT</option>
              <option value="index7:CAGATC">index7:CAGATC</option>
              <option value="index8:ACTTGA">index8:ACTTGA</option>
              <option value="index9:GATCAG">index9:GATCAG</option>
              <option value="index10:TAGCTT">index10:TAGCTT</option>
              <option value="index11:GGCTAC">index11:GGCTAC</option>
              <option value="index12:CTTGTA">index12:CTTGTA</option>
          </select>
          <span class="input-alert" ng-show="notPrevError">此样本未提取DNA</span>
          <span class="input-alert" ng-show="repeatError">此样品信息已经入库，请核查或者扫描下一管样品信息！</span>
          <span class="input-alert" ng-show="sampleName.$dirty && sampleName.$error.required">请输入样本编号！</span>
	      <div class="info-btn-group pull-right">
            <input class="field" type="text" ng-trim="true" ng-model="sampleName" required placeholder="扫描样本编号/病历号"/>
            <a class="action" ng-click="addSample()">扫码入库</a>
          </div>
        </div>
        <div class="table-opera clearfix">
            <a ng-click="addLibrary()" class="btn -low pull-right">建库</a>
            <a ng-click="addAndDownLibrary()" class="btn btn-cancel -low pull-right">建库并下载</a>
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
            <tbody>
                <tr ng-repeat="sample in infos.pageList.datas">
                    <td>1</td>
                    <td>{{sample.sampleName }}</td>
                    <td>{{sample.type }}</td>
                    <td>{{sample.createDate }}</td>
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