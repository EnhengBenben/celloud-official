<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<ng-include src="'pages/partial/_partial_sample_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>样本管理</li>
      <li>样本信息采集</li>
    </ol>
    <div class="content sampleInfo">
        <div class="content-header clearfix">
          <div class="info-btn-group">
            <button ng-click="commitSampleInfo()" class="btn -low pull-right" ng-disabled="sampleInfoList.length <= 0">提交订单</button>
            <button ng-click="toAddSample()" class="btn -low pull-right sampleInfo" >新建</button>
          </div>
        </div>
        <table class="table table-main">
          <thead>
              <tr>
                  <th class="sampleInfo">样本编号</th>
                  <th class="sampleInfo">检测项目</th>
                  <th class="sampleInfo">样本类型</th>
                  <th class="sampleInfo">姓名</th>
                  <th class="sampleInfo">性别</th>
                  <th class="sampleInfo">年龄</th>
                  <th class="sampleInfo">联系电话</th>
                  <th class="sampleInfo">身份证号</th>
                  <th class="sampleInfo">更新时间</th>
                  <th class="sampleInfo">操作</th>
              </tr>
          </thead>
          <tbody>
              <tr ng-repeat="sampleInfo in sampleInfoList" ng-if="sampleInfoList.length > 0">
                  <td class="sampleInfo">{{sampleInfo.sampleName }}</td>
                  <td class="sampleInfo">{{sampleInfo.tagName }}</td>
                  <td class="sampleInfo">{{sampleInfo.type }}</td>
                  <td class="sampleInfo">{{sampleInfo.name }}</td>
                  <td class="sampleInfo">{{sampleInfo.gender == 0 ? '女' : '男' }}</td>
                  <td class="sampleInfo">{{sampleInfo.age }}</td>
                  <td class="sampleInfo">{{sampleInfo.tel }}</td>
                  <td class="sampleInfo">{{sampleInfo.idCard }}</td>
                  <td class="sampleInfo">{{sampleInfo.updateDate | date : 'yyyy-MM-dd HH:mm:ss'}}</td>
                  <td class="sampleInfo">
                      <a href="javascript:void(0)" ng-click="toEditSampleInfo(sampleInfo.sampleId)">
                          <i class="fa fa-pencil-square-o" aria-hidden="true"></i>
                      </a>
                      <a href="javascript:void(0)" ng-click="removeSampleInfo(sampleInfo.sampleId)">
                          <i class="fa fa-times-circle" aria-hidden="true"></i>
                      </a>
                  </td>
              </tr>
              <tr ng-if="sampleInfoList.length == 0">
                  <td colspan="10" class="table-null sampleInfo"><font color="#1dd2af">暂无数据!</font></td>
              </tr>
          </tbody>
        </table>
	</div>
</div>
<div id="addSampleInfoModal" class="modal fade sampleInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
          <h4 class="modal-title">请填写样本基本信息</h4>
        </div>
        <div class="modal-body form-modal">
          <form class="form-horizontal info-form sampleInfo" name="addSampleInfoForm" id="addSampleInfoForm" ng-submit="saveSample()">
              <div class="form-group">
                <div class="control-label form-label tips sample-star" ng-show="(addSampleInfoForm.sampleName.$dirty && addSampleInfoForm.sampleName.$invalid)||(addSampleInfoForm.tel.$dirty && addSampleInfoForm.tel.$invalid)||(addSampleInfoForm.idCard.$dirty && addSampleInfoForm.idCard.$invalid)||(addSampleInfoForm.productTag.$dirty && addSampleInfoForm.productTag.$invalid)||
                (addSampleInfoForm.name.$dirty && addSampleInfoForm.name.$invalid)||(addSampleInfoForm.age.$dirty && addSampleInfoForm.age.$invalid)||(addSampleInfoForm.sampleType.$dirty && addSampleInfoForm.sampleType.$invalid)">您输入的信息有误,请检查后再输入...</div>
              </div>
              <div class="form-group">
                <div class="control-label form-label">样品编号：<span class="sample-star">*</span></div>
                <div>
                    <input type="text" name="sampleName" ng-model="sample.sampleName" placeholder="样品编号" maxlength="50" required>
                </div>
                <div class="control-label form-label second">姓&emsp;&emsp;名：<span class="sample-star">*</span></div>
                <div>
                    <input type="text" name="name" ng-model="patient.name" placeholder="姓名" maxlength="50" required>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label">手&emsp;&emsp;机：<span class="sample-star">*</span></div>
                <div>
                    <input type="text" name="tel" ng-model="patient.tel" placeholder="手机" maxlength="11" ng-pattern="/^((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))\d{8}$/" required>
                </div>
                <div class="control-label form-label second">年&emsp;&emsp;龄：<span class="sample-star">*</span></div>
                <div>
                    <input type="text" name="age" ng-model="patient.age" placeholder="年龄" maxlength="3" ng-pattern="/^\d{1,3}$/" required>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label">身份证号：<span class="sample-star">*</span></div>
                <div>
                    <input type="text" name="idCard" ng-model="patient.idCard" placeholder="身份证号" maxlength="18" ng-pattern="/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/" required>
                </div>
                <div class="control-label form-label mr10 second">性&emsp;&emsp;别：<span class="sample-star">*</span></div>
                <div class="form-group-content">
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
                <div class="control-label form-label mr10">检测类型：<span class="sample-star">*</span></div>
                <div>
                    <select ng-model="productTag" name="productTag" class="form-control" id="productTag1" multiple="multiple" ng-change="changeSampleTypeByTag(1)" required>
                    </select>
                </div>
                <div class="control-label form-label mr10 second">样本类型：<span class="sample-star">*</span></div>
                <div id="sampleTypes1">
                    <select ng-model="sampleType" name="sampleType" class="form-control" id="sampleType1" multiple="multiple" required>
                    </select>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label mw76">体&emsp;&emsp;重：</div>
                <div>
                    <input type="text" name="weight" placeholder="体重" ng-model="patient.weight" maxlength="10">
                </div>
                <div class="control-label form-label second mw76">身&emsp;&emsp;高：</div>
                <div>
                    <input type="text" name="height" placeholder="身高" ng-model="patient.height" maxlength="10">
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label mw76">邮&emsp;&emsp;箱：</div>
                <div>
                    <input type="text" name="email" placeholder="电子邮箱" ng-model="patient.email" maxlength="50">
                </div>
                <div class="control-label form-label mw76 mr10 second">是否吸烟：</div>
                <div class="form-group-content">
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
                <div class="control-label form-label mw76">个&ensp;人&ensp;史：</div>
                <div>
                    <input class="mw552" type="text" name="personalHistory" placeholder="个人史" ng-model="patient.personalHistory" maxlength="150">
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label mw76">家&ensp;族&ensp;史：</div>
                <div>
                    <input class="mw552" type="text" name="familyHistory" placeholder="家族史" ng-model="patient.familyHistory" maxlength="50">
                </div>
              </div>
              <div class="form-group">
                <div class="text-center">
		            <button ng-click="addReset()" type="button" class="btn btn-cancel">重置</button>
		            <button type="submit" class="btn" ng-disabled="addSampleInfoForm.$invalid" >提交</button>
		        </div>
              </div>
          </form>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<div id="editSampleInfoModal" class="modal fade sampleInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
          <h4 class="modal-title">请填写样本基本信息</h4>
        </div>
        <div class="modal-body form-modal">
          <form class="form-horizontal info-form" name="updateSampleInfoForm" id="updateSampleInfoForm" ng-submit="updateSample()">
              <div class="form-group">
                <div class="control-label form-label tips sample-star" ng-show="(addSampleInfoForm.sampleName.$dirty && addSampleInfoForm.sampleName.$invalid)||(addSampleInfoForm.tel.$dirty && addSampleInfoForm.tel.$invalid)||(addSampleInfoForm.idCard.$dirty && addSampleInfoForm.idCard.$invalid)||(addSampleInfoForm.productTag.$dirty && addSampleInfoForm.productTag.$invalid)||
                (addSampleInfoForm.name.$dirty && addSampleInfoForm.name.$invalid)||(addSampleInfoForm.age.$dirty && addSampleInfoForm.age.$invalid)||(addSampleInfoForm.sampleType.$dirty && addSampleInfoForm.sampleType.$invalid)">您输入的信息有误,请检查后再输入...</div>
              </div>
              <div class="form-group">
                <div class="control-label form-label">样品编号：<span class="sample-star">*</span></div>
                <div>
                    <input type="text" name="sampleName" ng-model="sample.sampleName" placeholder="样品编号" maxlength="50" required>
                </div>
                <div class="control-label form-label mw76 second">姓&emsp;&emsp;名：<span class="sample-star">*</span></div>
                <div>
                    <input type="text" name="name" ng-model="patient.name" placeholder="姓名" maxlength="50" required>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label mw76">手&emsp;&emsp;机：<span class="sample-star">*</span></div>
                <div>
                    <input type="text" name="tel" ng-model="patient.tel" placeholder="手机" maxlength="11" ng-pattern="/^((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))\d{8}$/" required>
                </div>
                <div class="control-label form-label mw76 second">年&emsp;&emsp;龄：<span class="sample-star">*</span></div>
                <div>
                    <input type="text" name="age" ng-model="patient.age" placeholder="年龄" maxlength="3" ng-pattern="/^\d{1,3}$/" required>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label">身份证号：<span class="sample-star">*</span></div>
                <div>
                    <input type="text" name="idCard" ng-model="patient.idCard" placeholder="身份证号" maxlength="18" ng-pattern="/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/" required>
                </div>
                <div class="control-label form-label second mr10 mw76">性&emsp;&emsp;别：<span class="sample-star">*</span></div>
                <div class="form-group-content">
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
                <div class="control-label form-label mr10">检测类型：<span class="sample-star">*</span></div>
                <div>
                    <select ng-model="productTag" class="form-control" id="productTag2" name="productTag" multiple="multiple" ng-change="changeSampleTypeByTag(2)" required>
                    </select>
                </div>
                <div class="control-label form-label second mr10">样本类型：<span class="sample-star">*</span></div>
                <div id="sampleTypes2">
                    <select ng-model="sampleType" class="form-control" id="sampleType2" name="sampleType" multiple="multiple" required>
                    </select>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label mw76">体&emsp;&emsp;重：</div>
                <div>
                    <input type="text" name="weight" placeholder="体重" ng-model="patient.weight" maxlength="10">
                </div>
                <div class="control-label form-label mw76 second">身&emsp;&emsp;高：</div>
                <div>
                    <input type="text" name="height" placeholder="身高" ng-model="patient.height" maxlength="10">
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label mw76">邮&emsp;&emsp;箱：</div>
                <div>
                    <input type="text" name="email" placeholder="电子邮箱" ng-model="patient.email" maxlength="50">
                </div>
                <div class="control-label form-label mw76 mr10 second">是否吸烟：</div>
                <div class="form-group-content">
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
                <div class="control-label form-label mw76">个&ensp;人&ensp;史：</div>
                <div>
                    <input class="mw552" type="text" name="personalHistory" placeholder="个人史" ng-model="patient.personalHistory" maxlength="150">
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label mw76">家&ensp;族&ensp;史：</div>
                <div>
                    <input class="mw552" type="text" name="familyHistory" placeholder="家族史" ng-model="patient.familyHistory" maxlength="50">
                </div>
              </div>
              <div class="form-group">
                <div class="text-center">
                    <button ng-click="editReset();" type="button" class="btn btn-cancel">重置</button>
                    <button type="submit" class="btn" ng-disabled="updateSampleInfoForm.$invalid" >提交</button>
                </div>
              </div>
          </form>
        </div>
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->