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
        <li class="active"><a data-click="to-app-price-list" href="javascript:void(0)">价格管理</a></li>
    </ul>
</div>
<div class="page-content">
    <div class="row">
        <c:if test="${appPriceList!=null && fn:length(appPriceList)>0 }">
          <div class="table-responsive table-div col-sm-12">
                <table id="app-price-list-table" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th class="min-w-50">APP名称</th>
                            <th class="min-w-50">APP单价</th>
                            <th class="min-w-50">修改时间</th>
                            <th class="min-w-50">编辑</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${appPriceList}" var="item">
                            <tr>
                                <td>${item.appName }</td>
                                <td>${item.price }</td>
                                <td><fmt:formatDate value="${item.chargeTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                                <td>
                                    <a data-click="to-update-app-price" data-id="${item.appId }" data-name="${item.appName }" data-price="${item.price }" href="javascript:void(0)">价格修改</a> | 
                                    <a data-click="to-app-price-history" data-id="${item.appId }" data-name="${item.appName }" data-price="${item.price }" href="javascript:void(0)">历史价格</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>
</div>
<script type="text/javascript">
$("#app-price-list-table").dataTable({"oLanguage": {
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