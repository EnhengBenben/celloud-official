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
            <option value="BSL1:ATCACG">BSL1:ATCACG</option>
			<option value="BSL2:CGATGT">BSL2:CGATGT</option>
			<option value="BSL3:TTAGGC">BSL3:TTAGGC</option>
			<option value="BSL4:TGACCA">BSL4:TGACCA</option>
			<option value="BSL5:ACAGTG">BSL5:ACAGTG</option>
			<option value="BSL6:GCCAAT">BSL6:GCCAAT</option>
			<option value="BSL7:CAGATC">BSL7:CAGATC</option>
			<option value="BSL8:ACTTGA">BSL8:ACTTGA</option>
			<option value="BSL9:GATCAG">BSL9:GATCAG</option>
			<option value="BSL10:TAGCTT">BSL10:TAGCTT</option>
			<option value="BSL11:GGCTAC">BSL11:GGCTAC</option>
			<option value="BSL12:CTTGTA">BSL12:CTTGTA</option>
          </select>
          <span class="input-alert" ng-show="sampleName.$dirty && sampleName.$error.required">请输入样本编号！</span>
	      <div class="info-btn-group">
            <input class="field" type="text" ng-trim="true" ng-model="sampleName" required placeholder="扫描样本编号/病历号"/>
            <a class="action" ng-click="addSample()">扫码入库</a>
          </div>
          <div class="library-btns pull-right">
            <button ng-click="addLibrary()" class="btn -low pull-right" ng-disabled="infos.pageList.datas.length <= 0">建库</button>
            <button ng-click="addAndDownLibrary()" class="btn btn-reset -low pull-right" ng-disabled="infos.pageList.datas.length <= 0">建库并下载</button>
          </div>
        </div>
        <div class="table-opera clearfix">
           <span class="tips">提示： 每个文库最多可添加12个样本。</span>
        </div>
        <div ng-controller="editSampleController">
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
                    <td ng-bind="infos.pageList.datas.length - $index">1</td>
                    <td>{{sample.sampleName }}</td>
                    <td>{{sample.type }}</td>
                    <td>{{sample.createDate | date : 'yyyy-MM-dd HH:mm:ss'}}</td>
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