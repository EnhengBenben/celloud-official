<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="row">
<div style="float: left;">
	<form class="form-inline">
		<input type="text" class="form-control" name="username" id="search_username" placeholder="请输入用户名"/>
		<a id="search" href="javascript:searchUserByName();" class="btn btn-primary"><i class="icon-search icon-white"></i> 检索</a>
	</form>
</div>
	<a style="float:right" href="#sendEmail" onclick="clearClass()" role="button" class="btn btn-primary" data-toggle="modal">发送邮件</a>
	<a style="float:right" href="#addModal" onclick="clearClass()" role="button" class="btn btn-primary" data-toggle="modal">新增</a>
	
	<div id="sendEmail" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
				&times;
			</button>
			<h4>
				新增用户
			</h4>
		</div>
		<div class="modal-body" style="margin-left: -50px;">
			<form class="form-horizontal" id="emailForm">
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						App提供者
					</label>
					<div class="col-sm-8">
						<select id="cselect1" name="user.companyId" class="form-control">
							
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						公司
					</label>
					<div class="col-sm-8">
						<select id="cselect" onchange="cchange('cselect','dselect')" class="form-control">
							
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						部门
					</label>
					<div class="col-sm-8">
						<select id="dselect" name="deptId" class="form-control">
							
						</select>
					</div>
				</div>
				<div class="form-group" id="email0">
					<label class="col-sm-3 control-label" for="title">
						邮箱<font color="red">*</font>
					</label>
					<div class="col-sm-8">
						<input type="text" name="emailArray" class="form-control"/>
						<span class="help-inline"></span>
						<a style="cursor: pointer;" onclick="addFile('emailForm')"><i class="icon-plus"></i></a>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<a href="javascript:void(0);" id="send" class="btn btn-primary">发送邮件</a>
			<a href="javascript:void(0);" id="cancle" class="btn">取消</a>
		</div>
	  </div>
	</div>
	</div>
	
	<div id="addModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
				&times;
			</button>
			<h4>
				新增用户
			</h4>
		</div>
		<div class="modal-body" style="margin-left: -50px;">
			<form class="form-horizontal" id="userForm">
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						用户名<font color="red">*</font>
					</label>
					<div class="col-sm-8">
						<input type="text" id="add_username" name="user.username" class="addUser form-control">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						密码<font color="red">*</font>
					</label>
					<div class="col-sm-8">
						<input type="password" placeholder="6-16位数字字母组合" id="add_password" name="user.password" class="addUser form-control">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						确认密码<font color="red">*</font>
					</label>
					<div class="col-sm-8">
						<input type="password" placeholder="6-16位数字字母组合" id="add_confirmPwd" name="confirmPwd" class="addUser form-control">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						主机密码
					</label>
					<div class="col-sm-8">
						<input type="password" id="add_hostPwd" name="user.hostPwd" class="form-control">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						真实姓名
					</label>
					<div class="col-sm-8">
						<input type="text" id="" name="user.truename" class="form-control">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						邮箱
					</label>
					<div class="col-sm-8">
						<input type="text" id="add_email" name="user.email" class="form-control">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						手机号码
					</label>
					<div class="col-sm-8">
						<input type="text" id="add_cellphone" name="user.cellPhone" class="form-control">
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						App提供者
					</label>
					<div class="col-sm-8">
						<select id="cselect3" name="user.companyId" class="form-control">
							
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						公司
					</label>
					<div class="col-sm-8">
						<select id="cselect2" onchange="cchange('cselect2','dselect2')" class="form-control">
							
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						部门
					</label>
					<div class="col-sm-8">
						<select id="dselect2" name="user.deptId" class="form-control">
							
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						角色<font color="red">*</font>
					</label>
					<div class="col-sm-8">
						<select id="add_role" class="addUser form-control" name="user.role">
							<option value="">请选择用户角色</option>
							<option value="0">普通用户</option>
							<option value="3">管理员</option>
						</select>
						<span class="help-inline"></span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label" for="title">
						备注
					</label>
					<div class="col-sm-8">
						<textarea rows="5" cols="10" name="user.remark" class="form-control"></textarea>
						<span class="help-inline"></span>
					</div>
				</div>
			</form>
		</div>
		<div class="modal-footer">
			<a href="javascript:void(0);" id="save" class="btn btn-primary">保存</a>
			<a class="btn" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
		</div>
		</div>
	  </div>
	</div>
	<div id="userList">
		
	</div>
<div id="editModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
	<div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">
			&times;
		</button>
		<h4>
			编辑用户
		</h4>
	</div>
	<div class="modal-body" id="edit" style="margin-left: -50px;">
		
	</div>
	<div class="modal-footer">
		<a href="javascript:void(0);" id="editSave" class="btn btn-primary">保存</a>
		<a href="javascript:void(0);" id="editCancle" class="btn">取消</a>
	</div>
  </div>
  </div>
</div>
<div id="detailModal" class="modal fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
<div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">
			&times;
		</button>
		<h4>
			用户备注信息
		</h4>
	</div>
	<div class="modal-body" id="userDetailDiv">
		
	</div>
	<div class="modal-footer">
		<a href="javascript:closeDetailModal();" class="btn">关闭</a>
	</div>
	</div>
  </div>
</div>
</div>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
	initUser();
});
</script>