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
            <option value="M16s_1_1R/F:AG">M16s_1_1R/F:AG</option>
			<option value="M16s_1_2R/F:AT">M16s_1_2R/F:AT</option>
			<option value="M16s_1_3R/F:CA">M16s_1_3R/F:CA</option>
			<option value="M16s_1_4R/F:CG">M16s_1_4R/F:CG</option>
			<option value="M16s_1_5R/F:GA">M16s_1_5R/F:GA</option>
			<option value="M16s_1_6R/F:GT">M16s_1_6R/F:GT</option>
			<option value="M16s_1_7R/F:CTGG">M16s_1_7R/F:CTGG</option>
			<option value="M16s_1_8R/F:CTTG">M16s_1_8R/F:CTTG</option>
			<option value="M16s_1_9R/F:ACGT">M16s_1_9R/F:ACGT</option>
			<option value="M16s_1_10R/F:GGCT">M16s_1_10R/F:GGCT</option>
			<option value="M16s_1_11R/F:AAGC">M16s_1_11R/F:AAGC</option>
			<option value="M16s_1_12R/F:CTTA">M16s_1_12R/F:CTTA</option>
			<option value="M16s_1_13R/F:CTGT">M16s_1_13R/F:CTGT</option>
			<option value="M16s_1_14R/F:GCAG">M16s_1_14R/F:GCAG</option>
			<option value="M16s_1_15R/F:TCAC">M16s_1_15R/F:TCAC</option>
			<option value="M16s_1_16R/F:AACT">M16s_1_16R/F:AACT</option>
			<option value="M16s_1_17R/F:ACCAGAG">M16s_1_17R/F:ACCAGAG</option>
			<option value="M16s_1_18R/F:TGAGAGT">M16s_1_18R/F:TGAGAGT</option>
			<option value="M16s_1_19R/F:TGATACG">M16s_1_19R/F:TGATACG</option>
			<option value="M16s_1_20R/F:GGCAGAC">M16s_1_20R/F:GGCAGAC</option>
			<option value="M16s_1_21R/F:CTTCTAA">M16s_1_21R/F:CTTCTAA</option>
			<option value="M16s_1_22R/F:GCATCGT">M16s_1_22R/F:GCATCGT</option>
			<option value="M16s_1_23R/F:TCTACTG">M16s_1_23R/F:TCTACTG</option>
			<option value="M16s_1_24R/F:TACTTCC">M16s_1_24R/F:TACTTCC</option>
          </select>
          <span class="input-alert" ng-show="notPrevError">此样本未提取DNA</span>
          <span class="input-alert" ng-show="repeatError">此样品信息已经入库，请核查或者扫描下一管样品信息！</span>
          <span class="input-alert" ng-show="sampleName.$dirty && sampleName.$error.required">请输入样本编号！</span>
	      <div class="info-btn-group">
            <input class="field" type="text" ng-trim="true" ng-model="sampleName" required placeholder="扫描样本编号/病历号"/>
            <a class="action" ng-click="addSample()">扫码入库</a>
          </div>
          <div class="library-btns pull-right">
            <a ng-click="addLibrary()" class="btn -low pull-right">建库</a>
            <a ng-click="addAndDownLibrary()" class="btn btn-reset -low pull-right">建库并下载</a>
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