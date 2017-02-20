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
          <p><br><br></p>
          <div class="info-btn-group">
            <button ng-click="commitSampleInfo()" class="btn -low pull-right" ng-disabled="sampleInfoList.length <= 0">提交订单</button>
            <button ng-click="toAddSample()" class="btn -low pull-right" style="margin-right: 10px;" >新建</button>
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
              <tr ng-repeat="sampleInfo in sampleInfoList" ng-if="sampleInfoList.length > 0">
                  <td>{{sampleInfo.sampleName }}</td>
                  <td>{{sampleInfo.tagName }}</td>
                  <td>{{sampleInfo.type }}</td>
                  <td>{{sampleInfo.name }}</td>
                  <td>{{sampleInfo.gender == 0 ? '女' : '男' }}</td>
                  <td>{{sampleInfo.age }}</td>
                  <td>{{sampleInfo.tel }}</td>
                  <td>{{sampleInfo.idCard }}</td>
                  <td>{{sampleInfo.updateDate | date : 'yyyy-MM-dd HH:mm:ss'}}</td>
                  <td>
                      <a href="javascript:void(0)" ng-click="toEditSampleInfo(sampleInfo.sampleId)">
                          <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                      </a>
                      <a href="javascript:void(0)" ng-click="removeSampleInfo(sampleInfo.sampleId)">
                          <i class="fa fa-times-circle" aria-hidden="true"></i>
                      </a>
                  </td>
              </tr>
              <tr ng-if="sampleInfoList.length == 0">
                  <td colspan="10" class="table-null">暂无样本信息</td>
              </tr>
          </tbody>
        </table>
	</div>
</div>
<div id="addSampleInfoModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" style="width:800px;">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
          <h4 class="modal-title">请填写样本基本信息</h4>
        </div>
        <div class="modal-body form-modal">
          <form class="form-horizontal info-form" name="addSampleInfoForm" id="addSampleInfoForm" ng-submit="saveSample()">
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10"><font></font></div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">样品编号：<span class="form-star">*</span></div>
                <div class="col-xs-5 md26">
                    <input type="text" name="sampleName" ng-model="sample.sampleName" placeholder="样品编号" maxlength="50" required>
                </div>
                <div class="control-label form-label col-xs-2 md10">姓&emsp;&emsp;名：*</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="name" ng-model="patient.name" placeholder="姓名" maxlength="50" required>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">手&emsp;&emsp;机：*</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="tel" ng-model="patient.tel" placeholder="手机" maxlength="11" ng-pattern="/^((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))\d{8}$/" required>
                </div>
                <div class="control-label form-label col-xs-2 md10">年&emsp;&emsp;龄：*</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="age" ng-model="patient.age" placeholder="年龄" maxlength="3" ng-pattern="/^\d{1,3}$/" required>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">身份证号：*</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="idCard" ng-model="patient.idCard" placeholder="身份证号" maxlength="18" ng-pattern="/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/" required>
                </div>
                <div class="control-label form-label col-xs-2 md10">性&emsp;&emsp;别：*</div>
                <div class="col-xs-5 form-group-content md26">
                   <label class="radio-lable">
                     <input class="radio" type="radio" value="1" ng-model="patient.gender" name="gender" ng-checked="true">
                     <span class="info"></span>
                   </label>
                   先生
                   <label class="radio-lable">
                     <input class="radio" type="radio" value="0" ng-model="patient.gender" name="gender">
                     <span class="info"></span>
                   </label>
                   女士
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">检测类型：*</div>
                <div class="col-xs-5 md26">
                    <select ng-model="productTag" class="form-control" id="productTag1" name="productTag" multiple="multiple" style="width: 100%;" ng-change="changeSampleTypeByTag(1)" required>
                    </select>
                </div>
                <div class="control-label form-label col-xs-2 md10">样本类型：*</div>
                <div class="col-xs-5 md26" id="sampleTypes1">
                    <select ng-model="sampleType" class="form-control" id="sampleType1" name="sampleType" multiple="multiple" style="width: 100%;" required>
                    </select>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">体&emsp;&emsp;重：</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="weight" placeholder="体重" ng-model="patient.weight" maxlength="10">
                </div>
                <div class="control-label form-label col-xs-2 md10">身&emsp;&emsp;高：</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="height" placeholder="身高" ng-model="patient.height" maxlength="10">
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">邮&emsp;&emsp;箱：</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="email" placeholder="电子邮箱" ng-model="patient.email" maxlength="50">
                </div>
                <div class="control-label form-label col-xs-2 md10">是否吸烟：</div>
                <div class="col-xs-5 form-group-content md26">
                   <label class="radio-lable">
                     <input class="radio" type="radio" value="1" ng-model="patient.smoke" name="smoke"  ng-checked="true">
                     <span class="info"></span>
                   </label>
                   是
                   <label class="radio-lable">
                     <input class="radio" type="radio" value="0" ng-model="patient.smoke" name="smoke">
                     <span class="info"></span>
                   </label>
                   否
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">个&ensp;人&ensp;史：</div>
                <div class="col-xs-11" style="max-width: 620px;">
                    <input type="text" name="personalHistory" placeholder="个人史" ng-model="patient.personalHistory" maxlength="150">
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">家&ensp;族&ensp;史：</div>
                <div class="col-xs-11" style="max-width: 620px;">
                    <input type="text" name="familyHistory" placeholder="家族史" ng-model="patient.familyHistory" maxlength="50">
                </div>
              </div>
              <div class="form-group">
                <div class="text-center">
		            <button type="reset" class="btn btn-cancel">重置</button>
		            <button type="submit" class="btn" ng-disabled="addSampleInfoForm.$invalid" >提交</button>
		        </div>
              </div>
          </form>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div id="editSampleInfoModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" style="width:800px;">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
          <h4 class="modal-title">请填写样本基本信息</h4>
        </div>
        <div class="modal-body form-modal">
          <form class="form-horizontal info-form" name="updateSampleInfoForm" id="updateSampleInfoForm" ng-submit="updateSample()">
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10"><font></font></div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">样品编号：*</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="sampleName" ng-model="sample.sampleName" placeholder="样品编号" maxlength="50" required>
                </div>
                <div class="control-label form-label col-xs-2 md10">姓&emsp;&emsp;名：*</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="name" ng-model="patient.name" placeholder="姓名" maxlength="50" required>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">手&emsp;&emsp;机：*</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="tel" ng-model="patient.tel" placeholder="手机" maxlength="11" ng-pattern="/^((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))\d{8}$/" required>
                </div>
                <div class="control-label form-label col-xs-2 md10">年&emsp;&emsp;龄：*</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="age" ng-model="patient.age" placeholder="年龄" maxlength="3" ng-pattern="/^\d{1,3}$/" required>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">身份证号：*</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="idCard" ng-model="patient.idCard" placeholder="身份证号" maxlength="18" ng-pattern="/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/" required>
                </div>
                <div class="control-label form-label col-xs-2 md10">性&emsp;&emsp;别：*</div>
                <div class="col-xs-5 form-group-content md26">
                   <label class="radio-lable">
                     <input class="radio" type="radio" value="1" ng-model="patient.gender" name="gender" ng-checked="true">
                     <span class="info"></span>
                   </label>
                   先生
                   <label class="radio-lable">
                     <input class="radio" type="radio" value="0" ng-model="patient.gender" name="gender">
                     <span class="info"></span>
                   </label>
                   女士
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">检测类型：*</div>
                <div class="col-xs-5 md26">
                    <select ng-model="productTag" class="form-control" id="productTag2" name="productTag" multiple="multiple" style="width: 100%;" ng-change="changeSampleTypeByTag(2)" required>
                    </select>
                </div>
                <div class="control-label form-label col-xs-2 md10">样本类型：*</div>
                <div class="col-xs-5 md26" id="sampleTypes2">
                    <select ng-model="sampleType" class="form-control" id="sampleType2" name="sampleType" multiple="multiple" style="width: 100%;" required>
                    </select>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">体&emsp;&emsp;重：</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="weight" placeholder="体重" ng-model="patient.weight" maxlength="10">
                </div>
                <div class="control-label form-label col-xs-2 md10">身&emsp;&emsp;高：</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="height" placeholder="身高" ng-model="patient.height" maxlength="10">
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">邮&emsp;&emsp;箱：</div>
                <div class="col-xs-5 md26">
                    <input type="text" name="email" placeholder="电子邮箱" ng-model="patient.email" maxlength="50">
                </div>
                <div class="control-label form-label col-xs-2 md10">是否吸烟：</div>
                <div class="col-xs-5 form-group-content md26">
                   <label class="radio-lable">
                     <input class="radio" type="radio" value="1" ng-model="patient.smoke" name="smoke"  ng-checked="true">
                     <span class="info"></span>
                   </label>
                   是
                   <label class="radio-lable">
                     <input class="radio" type="radio" value="0" ng-model="patient.smoke" name="smoke">
                     <span class="info"></span>
                   </label>
                   否
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">个&ensp;人&ensp;史：</div>
                <div class="col-xs-11" style="max-width: 620px;">
                    <input type="text" name="personalHistory" placeholder="个人史" ng-model="patient.personalHistory" maxlength="150">
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2 md10">家&ensp;族&ensp;史：</div>
                <div class="col-xs-11" style="max-width: 620px;">
                    <input type="text" name="familyHistory" placeholder="家族史" ng-model="patient.familyHistory" maxlength="50">
                </div>
              </div>
              <div class="form-group">
                <div class="text-center">
                    <button type="reset" class="btn btn-cancel">重置</button>
                    <button type="submit" class="btn" ng-disabled="updateSampleInfoForm.$invalid" >提交</button>
                </div>
              </div>
          </form>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
$(function(){
	
})
</script>