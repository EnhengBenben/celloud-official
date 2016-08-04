<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="panel panel-default">
<div class="panel-heading">
    <h3 class="panel-title">资源管理</h3>
    <div class="panel-options">
        <form class="form-inline">
         <button class="btn btn-danger" type="button" onclick="permission.resource.toAdd();" style="margin-bottom:0;">新增</button>
       </form>
     </div>
</div>
<div class="panel-body">
    <table class="table table-bordered table-striped tree" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th style="text-align: center;">选择</th>
                <th>资源名称</th>
                <th>资源类型</th>
                <th>表达式</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${resourceList }" var="resource" varStatus="status">
                <tr class="treegrid-${resource.id } <c:if test="${resource.parentId != 0 }">treegrid-parent-${resource.parentId }</c:if>">
                    <td style="text-align: center;"><input class="resourceCheck" type="checkbox" value="${resource.id }"></td>
                    <td>${resource.name }</td>
	                <td>${resource.type }</td>
	                <td>${resource.permission }</td>
	                <td>
	                   <c:if test="${resource.disabled==0 }">上线</c:if>
	                   <c:if test="${resource.disabled==1 }">下线</c:if>
	                </td>
	                <td><fmt:formatDate value="${resource.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                <td>
	                   <a href="javascript:permission.resource.toEdit('${resource.id }');">编辑</a>
	                   <!-- 上下线 -->
	                   <c:if test="${resource.disabled==1 }">
                            <a href="javascript:permission.resource.onOrOff('${resource.id }','0');">上线</a>
                       </c:if>
	                   <c:if test="${resource.disabled==0 }">
                            <a href="javascript:permission.resource.onOrOff('${resource.id }','1');">下线</a>
                       </c:if>
                       <!-- 上下移 -->
                       <a href="javascript:permission.resource.moveUp('${resource.id }','${resource.parentId }','${resource.priority }');">上移</a>
                       <a href="javascript:permission.resource.moveDown('${resource.id }','${resource.parentId }','${resource.priority }');">下移</a>
	                </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</div>
<div class="modal fade bs-example-modal-lg" id="resource-addModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
		    <div class="panel panel-default">
	            <div class="panel-heading">
	                <h3 class="panel-title"></h3>
	                <div class="alert alert-warning hide" id="resource-alert">
	                    <strong>Warning!</strong> <span id="resource-info"></span>
	                </div>
	                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	            </div>
	            <div class="panel-body">
	                <form class="form-horizontal" id="resourceForm" >
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label">资源名称<font color="red">*</font></label>
	                        <div class="col-sm-10">
	                           <input type="text" id="name" class="form-control" name="name" required="required" >
	                           <span class="help-inline text-danger"></span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label">表达式<font color="red">*</font></label>
	                        <div class="col-sm-10">
	                           <input type="text" id="permission" class="form-control" name="permission" required="required">
	                           <span class="help-inline text-danger"></span>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label">资源类型<font color="red">*</font></label>
	                        <div class="col-sm-10">
	                           <select id="type" name="type" class="form-control col-sm-5" data-rule-required="true" data-msg-required="该项为必选项，请选择。" >
	                               <option value="">请选择</option>
	                               <option value="menu">菜单</option>
	                               <option value="button">按钮</option>
	                           </select>
	                           <div class="help-inline text-danger">
	                           </div>
	                        </div>
	                    </div>
	                    <div class="form-group">
	                        <label class="col-sm-2 control-label">资源状态<font color="red">*</font></label>
	                        <div class="col-sm-10">
	                           <select id="disabled" name="disabled" class="form-control col-sm-5" data-rule-required="true" data-msg-required="该项为必选项，请选择。" >
	                               <option value="">请选择</option>
                                   <option value="0">上线</option>
                                   <option value="1">下线</option>
                               </select>
                               <div class="help-inline text-danger">
                               </div>
	                        </div>
	                    </div>
	                    <div class="form-group-separator"></div>
	                    <input type="hidden" id="resourceId" name="id">
	                    <input type="hidden" id="parentId" name="parentId">
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
    <div id="confirm-modal-content" class="modal-content confirmModal">
      <div id="confirm-modal-head" class="modal-header" id="">
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
<!-- 提示moddal -->
<div id="tip-modal" class="modal modal-celloud-green">
  <div class="modal-dialog">
    <div id="tip-modal-content" class="modal-content tipModal">
      <div id="tip-modal-head" class="modal-header" id="">
        <h4 class="modal-title"><i class="icon fa fa-warning"></i>提示</h4>
      </div>
      <div class="modal-body">
        <p id="tip-text">&hellip;</p>
      </div>
      <div class="modal-footer">
        <button id="tip-true" type="button" class="btn btn-outline ">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->