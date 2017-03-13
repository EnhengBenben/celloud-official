<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<ng-include src="'pages/partial/_partial_company_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>用户中心</li>
      <li>医院管理</li>
      <li>医院账号管理</li>
    </ol>
    <div class="content">
      <div class="table-opera">
        <div class="table-opera-content">
          <div class="opera-info">
            <button class="btn data-operate" data-toggle="modal" data-target="#company-addUser-modal" ng-click="showAddUserForm()">邮箱注册</button>
            <button class="btn data-operate" data-toggle="modal" data-target="#company-cellphone-addUser-modal" ng-click="showAddUserForm()">手机注册</button>
          </div>
        </div>
      </div>
      <div class="alert alert-dismissible message-alert fade in" role="alert" ng-show="state">
      	<button type="button" class="close" ng-click="state=false"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
      	<span>{{message}}</span>
      </div>
      <table class="table table-main" ng-init="pageType='data'" id="_data_table">
        <thead>
          <tr>
            <th>账户名</th>
            <th>真实姓名</th>
            <th>邮箱</th>
            <th>手机号码</th>
            <th>创建时间</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="user in userList.datas">
            <td title="{{user.username}}"><i class="fa fa-truck" aria-hidden="true" ng-show="file.isRunning==1"></i>{{user.username}}</td>
            <td title="{{user.truename}}">{{user.truename}}</td>
            <td>{{user.email}}</td>
            <td>{{user.cellphone}}</td>
            <td>{{user.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td>
            	<a class="btn-link" data-toggle="modal" data-target="#company-addApp-modal" ng-click="toAddApp(user.userId)">追加APP</a>
            	<a class="btn-link" data-toggle="modal" data-target="#company-removeApp-modal" ng-click="toRemoveApp(user.userId)">删除APP</a>
            	<a class="btn-link" data-toggle="modal" data-target="#company-addRole-modal" ng-click="toAddRole(user.userId)">追加权限</a>
            	<a class="btn-link" data-toggle="modal" data-target="#company-removeRole-modal" ng-click="toRemoveRole(user.userId)">删除权限</a>
            	<a class="btn-link" data-toggle="modal" ng-click="updateUserState(user.userId,user.state)"><span ng-if="user.state == 1">启用</span><span ng-if="user.state == 0">禁用</span></a>
            </td>
          </tr>
          <tr ng-show="user.datas.length == 0">
          	<td colspan="9">暂无数据</td>
          </tr>
        </tbody>
      </table>
      <pagination page="userList.page" change="pageQuery(page,pageSize)"></pagination>
    </div>
  <div id="company-cellphone-addUser-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
          <h4 class="modal-title">增加账号--发送短信</h4>
        </div>
        <div class="modal-body form-modal">
          <form class="form-horizontal info-form" name="userCellphoneAddForm" id="userCellphoneAddForm">
              <div class="form-group">
                <div class="control-label form-label col-xs-2">APP：</div>
                <div class="col-xs-10">
                    <div class="form-group">
                        <div ng-repeat="app in appList" class="col-xs-6 role">
                            <label class="checkbox-lable">
                                <input type="checkbox" name="cellphone-app" class="checkbox"  value="{{app.appId}}" checked="checked">
                                <span class="info"></span>
                            </label>
                            {{app.appName}}
                        </div>
                    </div>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2">角色：</div>
                <div class="col-xs-10">
                    <div class="form-group">
                        <div ng-repeat="role in roleList" class="col-xs-6 role">
                            <label class="checkbox-lable">
                                <input type="checkbox" name="cellphone-role" class="checkbox"  value="{{role.id}}">
                                <span class="info"></span>
                            </label>
                            {{role.name}}
                        </div>
                    </div>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2">电话：</div>
                <div class="col-xs-7">
                    <input type="text" ng-change="clearState()" name="cellphone-email" placeholder="手机号码" ng-model="email" required="true" ng-pattern="/^1\d{10}$/">
                    <span class="input-alert" ng-show="userAddForm.email.$dirty && userAddForm.email.$invalid">手机号码格式不正确!</span>
                    <span class="input-alert" ng-show="cellphoneError != null">{{cellphoneError}}</span>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2">姓名：</div>
                <div class="col-xs-7">
                    <input type="text" ng-change="clearState()" name="truename" placeholder="真实姓名" ng-model="truename" required="true">
                    <span class="input-alert" ng-show="userAddForm.truename.$dirty && userAddForm.truename.$invalid">真实姓名不能为空!</span>
                </div>
              </div>
              <div class="form-group">
                <div class="control-label form-label col-xs-2">验证码：</div>
                <div class="col-xs-7">
                    <input type="text" ng-change="clearState()" name="cellphone-captcha" placeholder="验证码" ng-model="captcha" required="true" />
                    <span class="input-alert" ng-show="kaptchaError != null">{{kaptchaError}}</span>
                </div>
                <div class="col-xs-2">
                   <div style="position: absolute;top: 0px;right: 60px;">
                       <img title="看不清，换一张" src="<%=request.getContextPath()%>/kaptcha" name="kaptchaImage" alt="验证码"
                            class="validateCode" style="cursor: pointer;" />
                    </div>
                </div>
              </div>
          </form>
        </div>
        <div class="modal-footer">
          <div class="text-center">
              <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
              <button type="submit" class="btn" ng-disabled="userCellphoneAddForm.$invalid" ng-click="sendCellphoneCaptcha()" id="submit">发送</button>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div id="company-addUser-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog modal-lg">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">增加账号--发送邮件</h4>
	    </div>
	    <div class="modal-body form-modal">
	      <form class="form-horizontal info-form" name="userAddForm" id="userAddForm">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2">APP：</div>
	            <div class="col-xs-10">
		            <div class="form-group">
		             	<div ng-repeat="app in appList" class="col-xs-6 role">
		             		<label class="checkbox-lable">
				            	<input type="checkbox" name="app" class="checkbox" value="{{app.appId}}" checked="checked">
				            	<span class="info"></span>
				            </label>
		            		{{app.appName}}
		            	</div>
		            </div>
	           	</div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2">角色：</div>
	            <div class="col-xs-10">
	            	<div class="form-group">
			            <div ng-repeat="role in roleList" class="col-xs-6 role">
			           		<label class="checkbox-lable">
				            	<input type="checkbox" name="role" class="checkbox"  value="{{role.id}}">
				            	<span class="info"></span>
				            </label>
			            	{{role.name}}
			            </div>
	            	</div>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2">邮箱：</div>
	            <div class="col-xs-7">
	                <input type="text" ng-change="clearState()" name="email" placeholder="邮箱地址" ng-model="email" required="true" ng-pattern="/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/">
	                <span class="input-alert" ng-show="userAddForm.email.$dirty && userAddForm.email.$invalid">邮箱格式不正确!</span>
	                <span class="input-alert" ng-show="emailError != null">{{emailError}}</span>
	            </div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2">验证码：</div>
	            <div class="col-xs-7">
                    <input type="text" ng-change="clearState()" name="kaptcha" placeholder="验证码" ng-model="kaptcha" required="true" />
	                <span class="input-alert" ng-show="kaptchaError != null">{{kaptchaError}}</span>
	            </div>
	            <div class="col-xs-2">
	               <div style="position: absolute;top: 0px;right: 60px;">
                       <img title="看不清，换一张" src="<%=request.getContextPath()%>/kaptcha" name="kaptchaImage" alt="验证码"
                            class="validateCode" style="cursor: pointer;" />
                    </div>
	            </div>
	          </div>
	      </form>
	    </div>
	    <div class="modal-footer">
	      <div class="text-center">
              <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
              <button type="submit" class="btn" ng-disabled="userAddForm.$invalid" ng-click="sendEmail()" id="submit">提交</button>
          </div>
	    </div>
	  </div>
	</div>
  </div>
  <div id="company-addApp-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">追加APP</h4>
	    </div>
	    <div class="modal-body form-modal">
	      <form class="form-horizontal info-form" name="appAddForm" id="appAddForm">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2">APP：</div>
	            <div class="col-xs-10">
		            <div class="form-group">
		             	<div ng-repeat="app in appList" class="col-xs-6 role">
		             		<label class="checkbox-lable">
				            	<input type="checkbox" name="app" class="checkbox"  value="{{app.appId}}">
				            	<span class="info"></span>
				            </label>
		            		{{app.appName}}
		            	</div>
		            </div>
	           	</div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2"></div>
	            <div class="col-xs-10">
		        	<span class="input-alert" ng-show="addApp.isShow">请选择APP!</span>
	           	</div>
	          </div>
	      </form>
	    </div>
	    <div class="modal-footer">
	      <div class="text-center">
              <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
              <button type="submit" class="btn" ng-disabled="appAddForm.$invalid" ng-click="addApp()">提交</button>
          </div>
	    </div>
	  </div>
	</div>
  </div>
  <div id="company-removeApp-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">删除APP</h4>
	    </div>
	    <div class="modal-body form-modal">
	      <form class="form-horizontal info-form" name="removeAppForm" id="removeAppForm">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2">APP：</div>
	            <div class="col-xs-10">
		            <div class="form-group">
		             	<div ng-repeat="app in appList" class="col-xs-6 role">
		             		<label class="checkbox-lable">
				            	<input type="checkbox" name="app" class="checkbox"  value="{{app.appId}}">
				            	<span class="info"></span>
				            </label>
		            		{{app.appName}}
		            	</div>
		            </div>
	           	</div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2"></div>
	            <div class="col-xs-10">
		        	<span class="input-alert" ng-show="removeApp.isShow">请选择APP!</span>
	           	</div>
	          </div>
	      </form>
	    </div>
	    <div class="modal-footer">
	      <div class="text-center">
              <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
              <button type="submit" class="btn" ng-disabled="removeAppForm.$invalid" ng-click="removeApp()" id="submit">提交</button>
          </div>
	    </div>
	  </div>
	</div>
  </div>
  <div id="company-addRole-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">追加角色</h4>
	    </div>
	    <div class="modal-body form-modal">
	      <form class="form-horizontal info-form" name="roleAddForm" id="roleAddForm">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2">角色：</div>
	            <div class="col-xs-10">
		            <div class="form-group">
		             	<div ng-repeat="role in roleList" class="col-xs-6 role">
		             		<label class="checkbox-lable">
				            	<input type="checkbox" name="role" class="checkbox"  value="{{role.id}}">
				            	<span class="info"></span>
				            </label>
		            		{{role.name}}
		            	</div>
		            </div>
	           	</div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2"></div>
	            <div class="col-xs-10">
		        	<span class="input-alert" ng-show="addRole.isShow">请选择角色!</span>
	           	</div>
	          </div>
	      </form>
	    </div>
	    <div class="modal-footer">
	      <div class="text-center">
              <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
              <button type="submit" class="btn" ng-disabled="roleAddForm.$invalid" ng-click="addRole()">提交</button>
          </div>
	    </div>
	  </div>
	</div>
  </div>
  <div id="company-removeRole-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">删除角色</h4>
	    </div>
	    <div class="modal-body form-modal">
	      <form class="form-horizontal info-form" name="roleRemoveForm" id="roleRemoveForm">
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2">角色：</div>
	            <div class="col-xs-10">
		            <div class="form-group">
		             	<div ng-repeat="role in roleList" class="col-xs-6 role">
		             		<label class="checkbox-lable">
				            	<input type="checkbox" name="role" class="checkbox"  value="{{role.id}}">
				            	<span class="info"></span>
				            </label>
		            		{{role.name}}
		            	</div>
		            </div>
	           	</div>
	          </div>
	          <div class="form-group">
	            <div class="control-label form-label col-xs-2"></div>
	            <div class="col-xs-10">
		        	<span class="input-alert" ng-show="removeRole.isShow">请选择角色!</span>
	           	</div>
	          </div>
	      </form>
	    </div>
	    <div class="modal-footer">
	      <div class="text-center">
              <button type="reset" class="btn btn-cancel" data-dismiss="modal">取消</button>
              <button type="submit" class="btn" ng-disabled="roleRemoveForm.$invalid" ng-click="removeRole()">提交</button>
          </div>
	    </div>
	  </div>
	</div>
  </div>
</div>
