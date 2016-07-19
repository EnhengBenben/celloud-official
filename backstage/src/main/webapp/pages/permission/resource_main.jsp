<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="panel panel-default">
<div class="panel-heading">
    <h3 class="panel-title">资源管理管理</h3>
    <div class="panel-options">
        <form class="form-inline">
        <%--  <div class="form-group">
          <input type="text" class="form-control" data-rule-required="true" id="keyword" name="keyword" value="${keyword }" placeholder="请输入关键字">
         </div> --%>
         <!-- <button class="btn btn-warning" type="button" onclick="javascript:permission.resource.pageQuery('1');" style="margin-bottom:0;">检索</button> -->
         <button class="btn btn-danger" type="button" onclick="permission.resource.toAdd();" style="margin-bottom:0;">新增</button>
       </form>
     </div>
</div>
<div class="panel-body">
    <table class="table table-bordered table-striped" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>资源名称</th>
                <th>资源类型</th>
                <th>表达式</th>
                <th>顺序</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${pageList.datas }" var="resource" varStatus="status">
                <tr>
                    <td>${resource.name }</td>
	                <td>${resource.type }</td>
	                <td>${resource.permission }</td>
	                <td>${resource.priority }</td>
	                <td>
	                   <c:if test="${resource.disabled==0 }">正常</c:if>
	                   <c:if test="${resource.disabled==1 }">下线</c:if>
	                </td>
	                <td><fmt:formatDate value="${resource.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                <td>
	                   <a href="javascript:permission.resource.toEdit('${resource.id }');">编辑</a>
	                   <!-- 上下线 -->
	                   <c:if test="${resource.disabled==1 }">
                            <a href="javascript:permission.resource.onOrOff('${resource.id }','0','${pageList.page.currentPage }');">上线</a>
                       </c:if>
	                   <c:if test="${resource.disabled==0 }">
                            <a href="javascript:permission.resource.onOrOff('${resource.id }','1','${pageList.page.currentPage }');">下线</a>
                       </c:if>
                       <!-- 上下移 -->
                       <c:if test="${status.first and !status.last and !pageList.page.hasPrev }">
                                                    上移
                          <a href="javascript:permission.resource.moveDown('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">下移</a>
                       </c:if>
                       <c:if test="${status.first and !status.last and pageList.page.hasPrev }">
                          <a href="javascript:permission.resource.moveUp('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">上移</a>
                          <a href="javascript:permission.resource.moveDown('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">下移</a>
                       </c:if>
                       <c:if test="${!status.first and !status.last }">
                          <a href="javascript:permission.resource.moveUp('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">上移</a>
                          <a href="javascript:permission.resource.moveDown('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">下移</a>
                       </c:if>
                       <c:if test="${status.last and !status.first and pageList.page.hasNext }">
                          <a href="javascript:permission.resource.moveUp('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">上移</a>
                          <a href="javascript:permission.resource.moveDown('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">下移</a>
                       </c:if>
                       <c:if test="${status.last and !status.first and !pageList.page.hasNext }">
                          <a href="javascript:permission.resource.moveUp('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">上移</a>
                          下移
                       </c:if>
                       <c:if test="${status.first and status.last and !pageList.page.hasPrev and !pageList.page.hasNext }">
                          上移
                          下移
                       </c:if>
                       <c:if test="${status.first and status.last and pageList.page.hasPrev and pageList.page.hasNext }">
                          <a href="javascript:permission.resource.moveUp('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">上移</a>
                          <a href="javascript:permission.resource.moveDown('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">下移</a>
                       </c:if>
                       <c:if test="${status.first and status.last and pageList.page.hasPrev and !pageList.page.hasNext }">
                          <a href="javascript:permission.resource.moveUp('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">上移</a>
                          下移
                       </c:if>
                       <c:if test="${status.first and status.last and !pageList.page.hasPrev and pageList.page.hasNext }">
                          上移
                          <a href="javascript:permission.resource.moveDown('${resource.id }','${resource.parentId }','${resource.priority }','${pageList.page.currentPage }');">下移</a>
                       </c:if>
	                </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <c:if test="${pageList.datas.size()>0}">
        <div class="row-fluid">
            <div class="col-md-6 keepRight" style="width: 100%;">
                <nav id="pageView" style="float: right;">
                    <ul class="pagination">
                        <c:choose>
                            <c:when test="${pageList.page.hasPrev}">
                                <li><a href="javascript:permission.resource.pageQuery('${pageList.page.prevPage }')">&lt;</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0)">&lt;</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${pageList.page.currentPage==1}">
                                <li class="active"><a href="javascript:void(0)">1</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:permission.resource.pageQuery('1')">1</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${pageList.page.currentPage>3}">
                            <li><a href="javascript:void(0)">..</a></li>
                        </c:if>
                        <c:if test="${pageList.page.currentPage>2}">
                            <li><a href="javascript:permission.resource.pageQuery('${pageList.page.prevPage }')">${pageList.page.prevPage }</a></li>
                        </c:if>
                        <c:if test="${pageList.page.currentPage>1&&pageList.page.currentPage<pageList.page.totalPage}">
                            <li class="active"><a href="javascript:void(0)">${pageList.page.currentPage }</a></li>
                        </c:if>
                        <c:if test="${pageList.page.totalPage-pageList.page.currentPage>1}">
                            <li><a href="javascript:permission.resource.pageQuery('${pageList.page.nextPage }')">${pageList.page.nextPage }</a></li>
                        </c:if>
                        <c:if test="${pageList.page.totalPage-pageList.page.currentPage>2}">
                            <li><a href="javascript:void(0)">..</a></li>
                        </c:if>
                        <c:choose>
                            <c:when test="${pageList.page.currentPage==pageList.page.totalPage&&pageList.page.totalPage>1}">
                                <li class="active"><a href="javascript:void(0)">${pageList.page.totalPage }</a></li>
                            </c:when>
                            <c:when test="${pageList.page.totalPage>1}">
                                <li><a href="javascript:permission.resource.pageQuery('${pageList.page.totalPage }')">${pageList.page.totalPage }</a></li>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${pageList.page.hasNext}">
                                <li><a href="javascript:permission.resource.pageQuery('${pageList.page.nextPage }')">&gt;</a></li>
                            </c:when>
                            <c:otherwise>
                                <li><a href="javascript:void(0)">&gt;</a></li>
                            </c:otherwise>
                        </c:choose>
                        <li>
	                       <select onchange="permission.resource.pageQuery('1',this.value)" id="pageSize" style="height: 33px;">
	                           <option ${ pageList.page.pageSize==10?'selected':''}>10</option>
	                           <option ${ pageList.page.pageSize==20?'selected':''}>20</option>
	                           <option ${ pageList.page.pageSize==50?'selected':''}>50</option>
	                           <option ${ pageList.page.pageSize==100?'selected':''}>100</option>
	                       </select>
	                    </li>
                        <li>
                            <a>共${pageList.page.totalPage }页&nbsp;|&nbsp;合计${pageList.page.rowCount }条</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </c:if>
</div>
</div>
<div class="modal fade bs-example-modal-lg" id="resource-addModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
		    <div class="panel panel-default">
	            <div class="panel-heading">
	                <h3 class="panel-title">新增资源</h3>
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
                            <label class="col-sm-2 control-label">顺序<font color="red">*</font></label>
                            <div class="col-sm-10">
                               <input type="text" id="priority" class="form-control" name="priority" required="required">
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
	                    <div class="form-group">
                            <label class="col-sm-2 control-label">父资源</label>
                            <div class="col-sm-10">
                               <select id="parentId" name="parentId" class="form-control col-sm-5" >
                                   <option value="0">请选择</option>
                               </select>
                            </div>
                        </div>
	                    <div class="form-group-separator"></div>
	                    <input type="hidden" id="resourceId" name="id">
	                    <input type="hidden" id="saveUpdateFlag">
	                    <div class="form-group">
	                        <div class="col-sm-10 text-center">
	                            <a id="saveOrUpdate" class="btn btn-success">确定</a>
	                            <button class="btn btn-white">重置</button>
	                        </div>
	                    </div>
	                </form>
	            </div>
	        </div>
	    </div>
	  </div>
</div>
<div id="confirm-modal" class="modal modal-celloud-green">
  <div class="modal-dialog">
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
<script>
$(function(){
    $("#keyword").keydown(function(e){
        if(e.keyCode==13){
        	permission.resource.pageQuery('1');
	        return false;
        }
    });
})
</script>