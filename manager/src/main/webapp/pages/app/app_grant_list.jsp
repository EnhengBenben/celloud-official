<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="icon-hospital"></i>
            <a href="#">APP管理</a>
        </li>
        <li class="active"><a data-click="to-app-grant-list" href="javascript:void(0)">授权管理</a></li>
    </ul>
</div>
<div class="page-content">
    <div class="row">
          <div class="table-responsive table-div col-sm-12">
               <table id="app-price-grant-table" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th class="min-w-50">APP名称</th>
                            <th class="min-w-50">修改时间</th>
                            <th class="min-w-50">操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${appList}" var="app">
                            <tr>
                                <td>${app.appName }</td>
                                <td><fmt:formatDate value="${app.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>
                                    <a data-click="to-grant-app-user" data-id="${app.appId }" data-name="${app.appName }" href="javascript:void(0)">授权用户</a> 
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
              </table>
          </div>
    </div>
</div>
<div class="modal fade" id="grant-app-modal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">授权用户</h4>
            </div>
            <div class="modal-body">
                <form id="grantAppForm" role="form" class="form-horizontal">
                   <div class="form-group">
                       <div class="col-sm-12 " id="userIds">
                             <c:forEach items="${userList }" var="user">
	                             <div class="col-sm-4">
	                                 <label class="checkbox-inline"><input name="userIds" type="checkbox" value="${user.userId }">${user.username }</label>
                                 </div>
                             </c:forEach>
                       </div>
                       <input type="hidden" id="appId" name="appId">
                   </div>
                   
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-info" id="grantApp">确定</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
$("#app-price-grant-table").dataTable({"oLanguage": {
  "sSearch": "搜索",
  "sLengthMenu": "每页显示 _MENU_ 条记录",
  "sZeroRecords": "Nothing found - 没有记录",
  "sInfo": "显示第  _START_ 条到第  _END_ 条记录,共  _TOTAL_ 条记录",
  "sInfoEmpty": "没有数据",   
  "sProcessing": "正在加载数据...",
  "oPaginate": {
     "sFirst": "首页",
     "sPrevious": "上一页",
     "sNext": "下一页",
     "sLast": "尾页"
     },
  },
     "aoColumns" : [ null, null,null, {"bSortable" : false} ],
     iDisplayLength : 10,
     "aaSorting" : [ [ 2, "desc", ] ],
 });
</script>