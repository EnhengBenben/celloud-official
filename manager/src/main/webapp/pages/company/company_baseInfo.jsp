<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="breadcrumbs" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="icon-hospital"></i>
			<a href="#">医院统计</a>
		</li>
		<li class="active" onclick="toCompanyReportList()">医院基本信息</li>
	</ul>
</div>
<div class="page-content">
	<div class="row">
		<c:if test="${companyList!=null && fn:length(companyList)>0 }">
		  <div class="table-responsive table-div col-sm-12">
                <table id="allUserDataList" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th class="min-w-50">医院编码</th>
                            <th class="min-w-200 ">医院名称</th>
                            <th class="min-w-100 ">入驻时间</th>
                            <c:if test="${loginUserInSession.role=='2'}">
                              <th class="min-w-200 ">所属大客户</th>
                            </c:if>
                            <th class="min-w-200 ">医院地址</th>
                            <th class="min-w-50 ">账号数量</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${companyList}" var="item">
                            <tr>
                                <td>${item.companyId }</td>
                                <td>${item.companyName }</td>
                                <td><fmt:formatDate value="${item.createDate }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
                                <c:if test="${loginUserInSession.role=='2'}">
	                              <td>${item.bigCustomerName }</td>
	                            </c:if>
                                <td>${item.address }</td>
                                <td>${item.userNum }</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
		</c:if>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$('#allUserDataList').dataTable({"oLanguage": {
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
        "aoColumns" : $("#user-role-hidden").val()==2?[ null, null,null,null,null,null ]:[ null, null,null,null,null ],
        iDisplayLength : 10,
        "aaSorting" : [ [ 2, "desc" ] ],
    });
});
</script>