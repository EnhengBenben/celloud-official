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
        <li><a data-click="to-app-price-list" href="javascript:void(0)">价格管理</a></li>
        <li data-click="to-app-price-history" class="active">${appName }历史价格</li>
    </ul>
</div>
<div class="row">
  <div class="col-sm-12">
    <div class="panel panel-default">
      <div class="panel-body">
                            当前价格  ： ${currentPrice }   <button class="btn btn-success" data-click="to-update-app-price" data-id="${appId }" data-name="${appName }" data-price="${currentPrice }">修改价格</button>
      </div>
    </div>
    <c:if test="${appPriceList!=null && fn:length(appPriceList)>0 }">
	    <div class="panel panel-default">
	      <div class="panel-heading">${appName }历史价格</div>
	      <div class="panel-body">
		      <div class="table-responsive table-div col-sm-12">
		            <table id="app-price-history-table" class="table table-striped table-bordered table-hover">
		                <thead>
		                    <tr>
		                        <th class="min-w-50">历史单价</th>
		                        <th class="min-w-50">创建时间</th>
		                        <th class="min-w-50">失效时间</th>
		                    </tr>
		                </thead>
		                <tbody>
		                    <c:forEach items="${appPriceList}" var="item">
		                        <tr>
		                            <td>${item.price }</td>
		                            <td><fmt:formatDate value="${item.createDate }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
		                            <td><fmt:formatDate value="${item.expireDate }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
		                        </tr>
		                    </c:forEach>
		                </tbody>
		            </table>
		        </div>
		  </div>
	    </div>
    </c:if>
  </div>
</div>
<script type="text/javascript">
$("#app-price-history-table").dataTable({"oLanguage": {
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
     "aoColumns" : [ null, null,null ],
     iDisplayLength : 10,
     "aaSorting" : [ [ 2, "desc" ] ],
 });
</script>