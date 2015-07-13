<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<input type="hidden" id="loginFlag" value="<s:property value="#session.userRole"/>"/>
<nav class="navbar navbar-new">
<!-- 	<div class="navbar-new"> -->
		<div class="container-fluid">
			<div class="navbar-header">
			  <span class="logo"></span>
			  <a class="navbar-brand logofont" href="javascript:void(0);">后台管理系统</a>
		    </div>
			<div class="nav-collapse" id="menuDiv">
				<ul class="nav navbar-nav navbar-right" id="nav1">
				<s:if test="#session.userRole==2">
					<li class="subNavBtn">
						<a href="javascript:void(0);" id="software">软件管理</a>
					</li>
					<li class="subNavBtn">
						<a href="javascript:void(0);" id="company">公司管理</a>
					</li>
					<li class="subNavBtn">
						<a href="javascript:void(0);" id="user">用户管理</a>
					</li>
					<li class="subNavBtn">
						<a href="javascript:void(0);" id="notice">公告管理</a>
					</li>
					<li class="subNavBtn">
						<a href="javascript:void(0);" id="client">客户端</a>
					</li>
					<li class="subNavBtn">
						<a href="javascript:void(0);" id="dataMenu">数据管理</a>
					</li>
					<li class="subNavBtn">
						<a href="javascript:void(0);" id="email">邮件群发</a>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle subNavBtn" data-toggle="dropdown">系统统计
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu">
							<li>
								<a href="javascript:void(0);" id="logo">登录统计</a>
							</li>
							<li>
								<a href="javascript:void(0);" id="softCount">软件运行统计</a>
							</li>
						</ul>
					</li>
		    	</s:if>
		    	<s:elseif test="#session.userRole==3">
		    		<li>
						<a href="javascript:void(0);" id="count">信息统计</a>
					</li>
		    	</s:elseif>
					<ul class="nav navbar-nav pull-right">
					<s:if test="#session.userRole==2||#session.userRole==3">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle usermenu" data-toggle="dropdown"><s:property value="#session.userName"/>
							<b class="caret"></b>
							</a>
							<ul class="dropdown-menu">
								<li>
									<a href="javascript:void(0);" id="resetPwd">修改密码</a>
								</li>
								<li class="divider"></li>
								<li>
									<a href="javascript:void(0);" id="logout">退出</a>
								</li>
							</ul>
						</li>
					</s:if>
					<s:else>
			    		<li><a href="javascript:void(0);" id="login">请登录</a></li>
				    </s:else>
					</ul>
				</ul>
			</div>
		</div>
<!-- 	</div> -->
</nav>

<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header well">
				<a class="close" data-dismiss="modal">×</a>
				<h4>登录</h4>
			</div>
			<div class="modal-body well">
				<form class="form-horizontal" id="loginForm">
					<input type="hidden" name="user.password" id="userPwdHidden">
					<input type="hidden" id="time"/>
					<div class="form-group">
						<label for="login_username" class="col-sm-3 control-label">登录名称：</label>
						<div class="col-sm-8">
							<input type="text" name="user.username" id="login_username" class="form-control"/>
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="form-group">
						<label for="login_password" class="col-sm-3 control-label">登录密码：</label>
						<div class="col-sm-8">
							<input type="password" id="login_password" class="form-control"/>
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="form-group" style="display: none" id="loginErrorDiv" align="center">
						<div class="alert" id="loginError">
							
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" href="javascript:void(0)" onclick="userLogin()"><i class="icon-share icon-white"></i>登录</a>
				<a class="btn btn-primary" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle icon-white"></i> 取消</a>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="editPwdModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header well">
				<a class="close" data-dismiss="modal">×</a>
				<h4 class="titleBlue">修改密码</h4>
			</div>
			<div class="modal-body well">
				<form class="form-horizontal" id="resetPwdForm">
					<fieldset>
						<div class="form-group">
							<label for="pwdOld" class="col-sm-3 control-label">原密码：</label>
							<div class="col-sm-8">
								<input type="password" id="pwdOld" name="user.password" class="form-control" onblur="checkPwd()"/>
								<span class="help-inline"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="pwdNew" class="col-sm-3 control-label">新密码：</label>
							<div class="col-sm-8">
								<input type="password" id="pwdNew" name="pwdNew" class="form-control"/>
								<span class="help-inline"></span>
							</div>
						</div>
						<div class="form-group">
							<label for="confirmPwd" class="col-sm-3 control-label">确认新密码：</label>
							<div class="col-sm-8">
								<input type="password" id="confirmPwd" class="form-control" name="confirmPwd"/>
								<span class="help-inline"></span>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" href="javascript:void(0)" id="saveNewPwd"><i class="icon-share icon-white"></i> 保存</a>
				<a class="btn btn-primary" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle icon-white"></i> 取消</a>
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="hospitalModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header well">
				<a class="close" data-dismiss="modal">×</a>
				<h4>新增医院信息</h4>
			</div>
			<div class="modal-body well">
				<form class="form-horizontal" id="hospitalForm">
					<div class="form-group">
						<label class="col-sm-3 control-label">医院名称：</label>
						<div class="col-sm-8">
							<input type="text" name="company.companyName" class="form-control"/>
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">英语名称：</label>
						<div class="col-sm-8">
							<input type="text" name="company.englishName" class="form-control"/>
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">医院 Logo：</label>
						<div class="col-sm-8">
							<img alt="医院 Logo" id="hospitalLogo" src="" style="width: 100px;height: 100px;">
							<input type="file" name="file" id="hosLogo" onchange="uploadImg(this,0,0)"/>
							<input type="hidden" name="company.companyIcon" class="addSoftware">
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">地址：</label>
						<div class="col-sm-8">
							<input type="text" name="company.address" class="form-control"/>
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">英语地址：</label>
						<div class="col-sm-8">
							<input type="text" name="company.addressEn" class="form-control"/>
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">邮编：</label>
						<div class="col-sm-8">
							<input type="text" name="company.zipCode" class="form-control"/>
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-3 control-label">电话：</label>
						<div class="col-sm-8">
							<input type="text" name="company.tel" class="form-control"/>
							<span class="help-inline"></span>
						</div>
					</div>
					<div class="form-group" style="display: none" id="hospitalErrorDiv" align="center">
						<div class="alert" id="hospitalError">
							
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<a class="btn btn-primary" href="javascript:void(0)" onclick="addHospital()"><i class="icon-share icon-white"></i>确定</a>
				<a class="btn" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$.ajaxSetup ({cache:false});
$(document).ready(function() {
	initMenu();
});
</script>