<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_experiment_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>实验管理</li>
      <li>提取DNA</li>
    </ol>
    <div class="content sample">
        <div class="content-header clearfix">
          <img src="<%=request.getContextPath()%>/images/icon/sample_scan.jpg">
          <p>* 请持条码枪扫描样品管上的条码<br>
              无条码样品请按以下方式操作：<br> 1. 在样品管上记录样品病历号<br> 2. 将病历号输入上面窗口后回车
          </p>
          <span class="input-alert" ng-show="sampleName.$dirty && sampleName.$error.required">请输入样本编号！</span>
          <div class="info-btn-group">
            <input class="field" type="text" ng-trim="true" ng-model="sampleName" required placeholder="扫描样本编号/病历号"/>
            <a class="action" ng-click="tokenDNA()">扫码提DNA</a>
          </div>
        </div>
        <div ng-controller="editSampleController">
          <table class="table table-main">
            <thead>
                <tr>
                    <th>样品编号</th>
                    <th>样品类型</th>
                    <th>采样时间</th>
                    <th>状态</th>
                    <th>备注</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <tr ng-repeat="sample in sampleList.datas">
                    <td>{{sample.sampleName }}</td>
                    <td>{{sample.type }}</td>
                    <td>{{sample.createDate | date : 'yyyy-MM-dd HH:mm:ss' }}</td>
                    <td>提DNA</td>
                    <td>{{sample.remark }}</td>
                    <td>
                        <a data-toggle="modal" data-target="#sample-remark-modal" ng-click="toEditRemark(sample.sampleId,sample.remark)">
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                        </a>
                        <a ng-click="remove(sample.sampleLogId)">
                            <i class="fa fa-times-circle" aria-hidden="true"></i>
                        </a>
                    </td>
                </tr>
            </tbody>
          </table>
          <pagination page="sampleList.page" change="pageQuery(page,pageSize)"></pagination>
          <div id="sample-remark-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		      <div class="modal-dialog">
		        <div class="modal-content">
		          <div class="modal-header">
		            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
		            <h4 class="modal-title" ng-init="exper_state=2">编辑备注</h4>
		          </div>
		          <div class="modal-body form-modal">
		            <form class="form-horizontal info-form" name="sampleForm" ng-submit="editRemark(sampleForm.$invalid)">
		               <div class="form-group">
		                   <div class="control-label form-label col-xs-2">备注：</div>
		                   <!-- 长度10-100 -->
		                   <div class="col-xs-10 form-group-content">
		                       <textarea rows="4" ng-trim="true" ng-model="remark" name="remark" required ng-maxlength="100">{{remark}}</textarea>
		                       <span class="input-alert break-line" ng-show="sampleForm.remark.$dirty && sampleForm.remark.$error.required">请输入问题的描述</span>
		                       <span class="input-alert break-line" ng-show="sampleForm.remark.$dirty && (sampleForm.remark.$error.maxlength)">请输入小于1000字的描述！</span>
		                   </div>
		               </div>
		               <div class="form-group form-btns">
		                   <div class="text-center">
		                       <button type="reset" class="btn btn-cancel" ng-click="resetSampleRemark()">重置</button>
		                       <button type="submit" class="btn" ng-disabled="sampleForm.$invalid">提交</button>
		                   </div>
		               </div>
		            </form>
		          </div>
		        </div>
		      </div>
		  </div>
		</div>
    </div>
</div>