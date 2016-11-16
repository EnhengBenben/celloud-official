<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="panel panel-default">
<div class="panel-heading">
    <h3 class="panel-title">角色管理</h3>
    <div class="panel-options">
        <form class="form-inline">
        <%--  <div class="form-group">
          <input type="text" class="form-control" data-rule-required="true" id="keyword" name="keyword" value="${keyword }" placeholder="请输入关键字">
         </div> --%>
         <!-- <button class="btn btn-warning" type="button" onclick="javascript:permission.role.pageQuery('1');" style="margin-bottom:0;">检索</button> -->
         <button class="btn btn-danger" type="button" onclick="permission.role.toAdd();" style="margin-bottom:0;">新增</button>
       </form>
     </div>
</div>
<div class="panel-body">
    <table class="table table-bordered table-striped tree" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>选择</th>
                <th>角色名称</th>
                <th>角色编码</th>
                <th>状态</th>
                <th>描述</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${list }" var="role" varStatus="status">
                <tr class="treegrid-${role.id } <c:if test="${role.parentId != 0 }">treegrid-parent-${role.parentId }</c:if>">
                	<td style="text-align: center;"><input class="resourceCheck" type="checkbox" value="${role.id }"></td>
                    <td>${role.name }</td>
	                <td>${role.code }</td>
	                <td>
                       <c:if test="${role.disabled==0 }">启用</c:if>
                       <c:if test="${role.disabled==1 }">未启用</c:if>
                    </td>
	                <td>${role.description }</td>
	                <td><fmt:formatDate value="${role.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                <td>
	                   <a href="javascript:permission.role.toEdit('${role.id }');">编辑</a>
	                   <!-- 上下线 -->
	                   <c:if test="${role.disabled==1 }">
                            <a href="javascript:permission.role.onOrOff('${role.id }','0',1);">启用</a>
                       </c:if>
	                   <c:if test="${role.disabled==0 }">
                            <a href="javascript:permission.role.onOrOff('${role.id }','1',1);">禁用</a>
                       </c:if>
                       <a href="javascript:permission.role.toDistribution('${role.id }');">分配</a>
                       <a href="javascript:permission.role.toGrant('${role.id }');">授权</a>
	                </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</div>
<div class="modal fade bs-example-modal-lg" id="role-addModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
		    <div class="panel panel-default">
	            <div class="panel-heading">
	                <h3 class="panel-title"></h3>
	                <div class="alert alert-warning hide" id="role-alert">
	                    <strong>Warning!</strong> <span id="role-info"></span>
	                </div>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	            </div>
	            <div class="panel-body">
	                <form class="form-horizontal" id="roleForm" >
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label">角色名称<font color="red">*</font></label>
	                        <div class="col-sm-10">
	                           <input type="text" id="name" class="form-control" name="name" required="required" >
	                           <span class="help-inline text-danger"></span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label">角色编码<font color="red">*</font></label>
	                        <div class="col-sm-10">
	                           <input type="text" id="code" class="form-control" name="code" required="required">
	                           <span class="help-inline text-danger"></span>
	                        </div>
	                    </div>
	                    <div class="form-group">
                            <label class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10">
                               <input type="text" id="description" class="form-control" name="description" required="required">
                               <span class="help-inline text-danger"></span>
                            </div>
                        </div>
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label">角色状态<font color="red">*</font></label>
	                        <div class="col-sm-10">
	                           <select id="disabled" name="disabled" class="form-control col-sm-5" data-rule-required="true" data-msg-required="该项为必选项，请选择。" >
	                               <option value="">请选择</option>
                                   <option value="0">启用</option>
                                   <option value="1">禁用</option>
                               </select>
                               <div class="help-inline text-danger">
                               </div>
	                        </div>
	                    </div>
	                    <div class="form-group-separator"></div>
	                    <input type="hidden" id="roleId" name="id">
	                    <input type="hidden" id="saveUpdateFlag">
	                    <div class="form-group">
	                        <div class="col-sm-10 text-center">
	                            <a id="saveOrUpdate" class="btn btn-success">确定</a>
	                            <button type="reset" class="btn btn-white" data-dismiss="modal" aria-label="Close">取消</button>
	                        </div>
	                    </div>
	                </form>
	            </div>
	        </div>
	    </div>
	  </div>
</div>
<div id="confirm-modal" class="modal modal-celloud-green">
  <div class="modal-dialog" style="width: 450px;">
    <div id="tip-modal-content" class="modal-content tipModal">
      <div id="tip-modal-head" class="modal-header" id="">
        <h4 class="modal-title"><i class="icon fa fa-warning"></i>提示</h4>
      </div>
      <div class="modal-body">
        <p id="confirm-text">&hellip;</p>
      </div>
      <div class="modal-footer">
        <button id="confirm-flase" type="button" class="btn btn-outline pull-left" data-dismiss="modal">取消</button>
        <button id="confirm-true" type="button" class="btn btn-outline" data-dismiss="modal">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade bs-example-modal-lg" id="role-distribution" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="panel panel-default">
                 <div class="panel-heading">
                    <h3 class="panel-title">分配角色</h3>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" id="roleDistributionForm" >
						<div class="form-group">
						    <div class="col-sm-12" id="bigCustomerIds">
						          <c:forEach items="${bigCustomers }" var="bigCustomer">
						              <div class="col-sm-3">
							              <label class="checkbox-inline"><input name="bigCustomerIds" type="checkbox" value="${bigCustomer.userId }">${bigCustomer.username }</label>
						              </div>
						          </c:forEach>
						    </div>
	                        <input type="hidden" id="roleIdDis" name="roleId">
						</div>
                        <div class="form-group-separator"></div>
                        <div class="form-group">
                            <div class="col-sm-10 text-center">
                                <a id="distribute" class="btn btn-success">确定</a>
                                <button type="reset" class="btn btn-white" data-dismiss="modal" aria-label="Close">取消</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
      </div>
</div>

<div class="modal fade bs-example-modal-lg" id="role-grant" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="panel panel-default">
                 <div class="panel-heading">
                    <h3 class="panel-title">授权资源</h3>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" id="roleGrantForm" >
                        <div class="form-group">
	                        <table class="table table-bordered table-striped tree" cellspacing="0" width="100%">
	                            <thead>
	                                <tr>
	                                    <th style="text-align: center;width: 80px;">选择</th>
	                                    <th>资源名称</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                                <c:forEach items="${resourceList }" var="resource" varStatus="status">
	                                    <tr class="treegrid-${resource.id } <c:if test="${resource.parentId != 0 }">treegrid-parent-${resource.parentId }</c:if>">
	                                        <td style="text-align: center;"><input name="resourceIds" class="resourceCheck" type="checkbox" value="${resource.id }"></td>
	                                        <td>${resource.name }</td>
	                                    </tr>
	                                </c:forEach>
	                            </tbody>
	                        </table>
                        </div>
                        <input type="hidden" id="roleIdGrant" name="roleId">
                        <div class="form-group-separator"></div>
                        <div class="form-group">
                            <div class="col-sm-10 text-center">
                                <a id="grant" class="btn btn-success">确定</a>
                                <button type="reset" class="btn btn-white" data-dismiss="modal" aria-label="Close">取消</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
      </div>
</div>

