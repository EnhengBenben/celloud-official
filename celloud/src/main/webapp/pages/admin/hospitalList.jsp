<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<span>公司详情：</span>
<!-- <a style="float:right" class="btn btn-primary" onclick="javascript:toAddDept();">新增部门</a> -->
<!-- <a class="btn btn-primary" onclick="javascript:toAddHospital();">新增公司</a> -->
<div class="row-fluid">
	<div class="col-md-7">
		<table class="table table-bordered table-striped" style="margin-bottom: 1px">
			<thead>
				<tr>
					<th width="73">公司编号</th>
					<th>公司名称</th>
					<th style="min-width: 80px;">操作&nbsp;|<a onclick="javascript:toAddHospital();">新增</a></th>
				</tr>
			</thead>
			<tbody>
				<s:if test="%{pageList.datas.size()>0}">
					<s:iterator id="li" value="pageList.datas" status="st">
						<tr>
							<td align="center"><s:property value="#li.companyId" /></td>
							<td><a href="javascript:void(0)" onclick="getDeptAll(<s:property value="#li.companyId" />)" titile="点击查看部门信息"><s:property value="#li.companyName" /></a></td>
							<td>
								<s:if test="#li.state==0">
									<a href="javascript:void(0)" onclick="updateState(<s:property value="#li.companyId" />,1)">删除</a>
									|<a href="javascript:void(0)" onclick="toEdit(<s:property value="#li.companyId" />)">编辑</a>
								</s:if>
								<s:else>
									<a href="javascript:void(0)" onclick="updateState(<s:property value="#li.companyId" />,0)">恢复</a>
								</s:else>
							</td>
						</tr>
					</s:iterator>
				</s:if>
				<s:else>
					<tr><td colspan="13">对不起，没有数据</td></tr>
				</s:else>
			</tbody>
		</table>
	</div>
	<div class="col-md-5">
		<table class="table" style="margin-bottom: 1px;display: none;">
			<thead>
				<tr>
					<th width="73">部门编号</th>
					<th>部门名称</th>
					<th style="min-width: 80px;">操作&nbsp;|<a onclick="javascript:toAddDept();">新增</a></th>
				</tr>
			</thead>
			<tbody id="deptBody">
			</tbody>
		</table>
	</div>
</div>
<s:if test="%{pageList.datas.size()>0}">
	<div class="row-fluid">
		<div class="col-md-6 keepRight">
			<nav id="pageView">
				<ul class="pagination">
					<s:if test="%{pageList.page.hasPre}">
						<li><a href="javascript:getHospital(<s:property value="pageList.page.currentPage-1" />)">&lt;</a></li>
					</s:if>
					<s:else>
						<li><a href="javascript:void(0)">&lt;</a></li>
					</s:else>
					<s:if test="%{pageList.page.currentPage==1}">
						<li class="active"><a href="javascript:void(0)">1</a></li>
					</s:if>
					<s:else>
						<li><a href="javascript:getHospital(1)">1</a></li>
					</s:else>
					<s:if test="%{pageList.page.currentPage>3}">
						<li><a href="javascript:void(0)">..</a></li>
					</s:if>
					<s:if test="%{pageList.page.currentPage>2}">
						<li><a href="javascript:getHospital(<s:property value="pageList.page.currentPage-1"/>)"><s:property value="pageList.page.currentPage-1"/></a></li>
					</s:if>
					<s:if test="%{pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
						<li class="active"><a href="javascript:void(0)"><s:property value="pageList.page.currentPage"/></a></li>
					</s:if>
					<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>1}">
						<li><a href="javascript:getHospital(<s:property value="pageList.page.currentPage+1"/>)"><s:property value="pageList.page.currentPage+1"/></a></li>
					</s:if>
					<s:if test="%{pageList.page.totalPage-pageList.page.currentPage>2}">
						<li><a href="javascript:void(0)">..</a></li>
					</s:if>
					<s:if test="%{pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}">
						<li class="active"><a href="javascript:void(0)"><s:property value="pageList.page.totalPage"/></a></li>
					</s:if>
					<s:elseif test="%{pageList.page.totalPage>1}">
						<li><a href="javascript:getHospital(<s:property value="pageList.page.totalPage"/>)"><s:property value="pageList.page.totalPage"/></a></li>
					</s:elseif>
					<s:if test="%{pageList.page.hasNext}">
						<li><a href="javascript:getHospital(<s:property value="pageList.page.currentPage+1"/>)">&gt;</a></li>
					</s:if>
					<s:else>
						<li><a href="javascript:void(0)">&gt;</a></li>
					</s:else>
					<li>
						<a>共<s:property value="pageList.page.totalPage"/>页&nbsp;|&nbsp;合计<s:property value="pageList.page.rowCount"/>条</a>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</s:if>

<div class="modal fade" id="deptModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
 <div class="modal-dialog">
  <div class="modal-content">
	<div class="modal-header well">
		<a class="close" data-dismiss="modal">×</a>
		<h4>新增部门</h4>
	</div>
	<div class="modal-body well">
		<form class="form-horizontal" id="deptForm">
			<div class="form-group">
				<label class="col-sm-3 control-label">部门名称：</label>
				<div class="col-sm-8">
					<input type="text" name="dept.deptName"/>
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">英语名称：</label>
				<div class="col-sm-8">
					<input type="text" name="dept.englishName"/>
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">电话：</label>
				<div class="col-sm-8">
					<input type="text" name="dept.tel"/>
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">部门 Logo：</label>
				<div class="col-sm-8">
					<img alt="科室 Logo" id="descriptionLogo" src="" style="width: 100px;height: 100px;">
					<input type="file" name="file" id="deptLogo" onchange="uploadImg(this,1,0)"/>
					<input type="hidden" name="dept.deptIcon" id="pictureName" class="addSoftware">
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">所属于公司：</label>
				<div class="col-sm-8">
					<select name="dept.companyId">
						<option value="0">--请选择--</option>
						<s:iterator value="list" id="st">
							<option value="<s:property value="#st.companyId"/>"><s:property value="#st.companyName"/></option>
						</s:iterator>
					</select>
				</div>
			</div>
			<div class="form-group" style="display: none" id="hospitalErrorDiv" align="center">
				<div class="alert" id="hospitalError">
					
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<a class="btn btn-primary" href="javascript:void(0)" onclick="addDept()"><i class="icon-share icon-white"></i>确定</a>
		<a class="btn" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
	</div>
  </div>
 </div>
</div>

<div class="modal fade" id="editHospitalModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header well">
		<a class="close" data-dismiss="modal">×</a>
		<h4>修改医院信息</h4>
	</div>
	<div class="modal-body well" id="editHospital">
		
	</div>
	<div class="modal-footer">
		<a class="btn btn-primary" href="javascript:void(0)" onclick="editHospital()"><i class="icon-share icon-white"></i>确定</a>
		<a class="btn" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
	</div>
  </div>
 </div>
</div>

<div class="modal fade" id="editDeptModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
   <div class="modal-content">
	<div class="modal-header well">
		<a class="close" data-dismiss="modal">×</a>
		<h4>修改部门信息</h4>
	</div>
	<div class="modal-body well" id="editDept">
		<form class="form-horizontal" id="editDeptForm">
			<input type="hidden" name="dept.deptId"/>
			<div class="form-group">
				<label class="col-sm-3 control-label">部门名称</label>
				<div class="col-sm-8">
					<input type="text" name="dept.deptName"/>
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">英语名称</label>
				<div class="col-sm-8">
					<input type="text" name="dept.englishName"/>
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">电话</label>
				<div class="col-sm-8">
					<input type="text" name="dept.tel"/>
					<span class="help-inline"></span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">部门Logo</label>
				<div class="col-sm-8">
					<div id="editDIcon"></div>
					<img alt="公司 Logo" id="editDLogo" src="" style="width: 100px;height: 100px;">
					<input type="file" name="file" id="editDeptLogo" onchange="uploadImg(this,1,1)"/>
					<input type="hidden" name="dept.deptIcon">
					<span class="help-inline"></span>
				</div>
			</div>
		</form>
	</div>
	<div class="modal-footer">
		<a class="btn btn-primary" href="javascript:void(0)" onclick="editDept()"><i class="icon-share icon-white"></i>确定</a>
		<a class="btn" href="javascript:void(0)" data-dismiss="modal"><i class="icon-ban-circle"></i> 取消</a>
	</div>
  </div>
 </div>
</div>