<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form class="form-horizontal" id="editForm">
	<input type="hidden" name="user.userId" id="editUserIdHidden" value="<s:property value='user.userId' />" />
	<div class="form-group">
		<label class="col-sm-3 control-label" for="title">
			用户名<font color="red">*</font>
		</label>
		<div class="col-sm-8">
			<input type="text" id="edit_username" name="user.username" class="form-control" value="<s:property value='user.username'/>">
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="title">
			真实姓名
		</label>
		<div class="col-sm-8">
			<input type="text" id="" name="user.truename" class="form-control" value="<s:property value='user.truename'/>">
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="title">
			邮箱
		</label>
		<div class="col-sm-8">
			<input type="text" id="edit_email" name="user.email" class="form-control" value="<s:property value='user.email'/>">
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="title">
			手机号码
		</label>
		<div class="col-sm-8">
			<input type="text" id="edit_cellphone" name="user.cellPhone" class="form-control" value="<s:property value='user.cellPhone'/>">
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="title">
			App提供者
		</label>
		<div class="col-sm-8">
			<input type="hidden" value="<s:property value="user.companyId"/>" id="CAIdHidden">
			<select id="editCAselect" name="user.companyId" class="form-control">
				<option value="0">--请选择--</option>
				<s:iterator value="comList" id="st">
					<option value="<s:property value="#st.companyId"/>"><s:property value="#st.companyName"/></option>
				</s:iterator>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="title">
			公司
		</label>
		<div class="col-sm-8">
			<select id="editCselect" onchange="cchange('editCselect','editDselect')" class="form-control">
				<option value="0">--请选择--</option>
				<s:iterator value="comList" id="st">
					<option value="<s:property value="#st.companyId"/>"><s:property value="#st.companyName"/></option>
				</s:iterator>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="title">
			部门
		</label>
		<div class="col-sm-8">
			<input type="hidden" value="<s:property value="user.deptId"/>" id="deptIdHidden">
			<s:iterator value="deptList" id="st" status="dept">
				<s:if test="#dept.First">
					<input type="hidden" value="<s:property value="#st.companyId"/>" id="companyIdHidden">
				</s:if>
			</s:iterator>
			<select id="editDselect" name="user.deptId" class="form-control">
				<option value="0">--请选择--</option>
				<s:iterator value="deptList" id="st" status="dept">
					<option value="<s:property value="#st.deptId"/>"><s:property value="#st.deptName"/></option>
				</s:iterator>
			</select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="title">
			角色<font color="red">*</font>
		</label>
		<div class="col-sm-8">
			<select id="edit_role" class="form-control" name="user.role">
				<option value="0" <s:if test="user.role==0||user.role==1">selected="selected"</s:if>>普通用户</option>
				<option value="2" <s:if test="user.role==2">selected="selected"</s:if>>超级管理员</option>
				<option value="3" <s:if test="user.role==3">selected="selected"</s:if>>管理员</option>
			</select>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label" for="title">
			备注
		</label>
		<div class="col-sm-8">
			<textarea rows="5" cols="10" id="edit_remark" name="user.remark" class="form-control"><s:property value='user.remark'/></textarea>
			<span class="help-inline"></span>
		</div>
	</div>
</form>