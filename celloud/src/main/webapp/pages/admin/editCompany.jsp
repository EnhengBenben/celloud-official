<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form class="form-horizontal" id="editHospitalForm">
	<input type="hidden" name="company.companyId" value="${company.companyId}" />
	<div class="form-group">
		<label class="col-sm-3 control-label">公司名称</label>
		<div class="col-sm-8">
			<input type="text" name="company.companyName" value="${company.companyName}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label">英语名称</label>
		<div class="col-sm-8">
			<input type="text" name="company.englishName" value="${company.englishName}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label">公司Logo</label>
		<div class="col-sm-8">
			<div id="editHIcon">${company.companyIcon}</div>
			<img alt="公司 Logo" id="editHLogo" src="<%=request.getContextPath()%>/images/hospitalIcon/${company.companyIcon}" style="width: 100px;height: 100px;">
			<input type="file" name="file" id="editHospitalLogo" onchange="uploadImg(this,0,1)"/>
			<input type="hidden" name="company.companyIcon">
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label">地址</label>
		<div class="col-sm-8">
			<input type="text" name="company.address" value="${company.address}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label">英语地址</label>
		<div class="col-sm-8">
			<input type="text" name="company.addressEn" value="${company.addressEn}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label">邮编</label>
		<div class="col-sm-8">
			<input type="text" name="company.zipCode" value="${company.zipCode}"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-3 control-label">电话</label>
		<div class="col-sm-8">
			<input type="text" name="company.tel" value="${company.tel}"/>
			<span class="help-inline"></span>
		</div>
	</div>
</form>