<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="icon-hospital"></i>
            <a href="javascript:void(0)">费用中心</a>
        </li>
        <li class="active"><a href="javascript:void(0)">发票管理</a></li>
    </ul>
</div>
<table class="table data-table text-center">
    <thead>
        <tr>
            <th class="pay-money">金额</th>
            <th>类型</th>
            <th>抬头</th>
            <th width="250px;">地址</th>
            <th>状态</th>
            <th class="pay-remarks">备注</th>
            <th class="pay-date">创建时间</th>
            <th>操作</th>
        </tr>
    </thead>
    <tbody id="pay-list-tbody" class="pay-tbody">
		<c:choose>
		  <c:when test="${invoicePageList.datas.size()>0 }">
		    <c:forEach items="${invoicePageList.datas }" var="invoice" varStatus="status">
		       <tr>
                  <td>${invoice.money }</td>
                  <td>
                    <c:if test="${invoice.invoiceType == 0 }">公司普票</c:if>
                    <c:if test="${invoice.invoiceType == 1 }">公司专票</c:if>
                  </td>
                  <td>${invoice.invoiceHeader }</td>
                  <td>${invoice.address }</td>
                  <td>
                    <c:if test="${invoice.invoiceState == 0 }">已申请</c:if>
                    <c:if test="${invoice.invoiceState == 1 }">已寄出</c:if>
                  </td>
                  <td>
                    <c:if test="${empty invoice.remark }">无</c:if>
                    <c:if test="${not empty invoice.remark }">${invoice.remark }</c:if>
                  </td>
                  <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${invoice.createDate }" type="both"/></td>
                  <td><a href="javascript:showInvoiceDetail('${invoice.id }');">详情</a></td>
              </tr>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <tr><td colspan="8">记录为空</td></tr>
          </c:otherwise>
		</c:choose>
	</tbody>
</table>
<c:if test="${invoicePageList.datas.size()>0}">
  <div class="row-fluid">
      <div class="col-md-6 keepRight">
          <nav id="pageView">
              <ul class="pagination">
                  <c:choose>
                      <c:when test="${invoicePageList.page.hasPrev}">
                          <li><a href="javascript:$.expense.pay.pageInvoiceList(${invoicePageList.page.prevPage })">&lt;</a></li>
                      </c:when>
                      <c:otherwise>
                          <li><a href="javascript:void(0)">&lt;</a></li>
                      </c:otherwise>
                  </c:choose>
                  <c:choose>
                      <c:when test="${invoicePageList.page.currentPage==1}">
                          <li class="active"><a href="javascript:void(0)">1</a></li>
                      </c:when>
                      <c:otherwise>
                          <li><a href="javascript:$.expense.pay.pageInvoiceList(1)">1</a></li>
                      </c:otherwise>
                  </c:choose>
                  <c:if test="${invoicePageList.page.currentPage>3}">
                      <li><a href="javascript:void(0)">..</a></li>
                  </c:if>
                  <c:if test="${invoicePageList.page.currentPage>2}">
                      <li><a href="javascript:$.expense.pay.pageInvoiceList(${invoicePageList.page.prevPage })">${invoicePageList.page.prevPage }</a></li>
                  </c:if>
                  <c:if test="${invoicePageList.page.currentPage>1&&invoicePageList.page.currentPage<invoicePageList.page.totalPage}">
                      <li class="active"><a href="javascript:void(0)">${invoicePageList.page.currentPage }</a></li>
                  </c:if>
                  <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>1}">
                      <li><a href="javascript:$.expense.pay.pageInvoiceList(${invoicePageList.page.nextPage })">${invoicePageList.page.nextPage }</a></li>
                  </c:if>
                  <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>2}">
                      <li><a href="javascript:void(0)">..</a></li>
                  </c:if>
                  <c:choose>
                      <c:when test="${invoicePageList.page.currentPage==invoicePageList.page.totalPage&&invoicePageList.page.totalPage>1}">
                          <li class="active"><a href="javascript:void(0)">${invoicePageList.page.totalPage }</a></li>
                      </c:when>
                      <c:when test="${invoicePageList.page.totalPage>1}">
                          <li><a href="javascript:$.expense.pay.pageInvoiceList(${invoicePageList.page.totalPage })">${invoicePageList.page.totalPage }</a></li>
                      </c:when>
                  </c:choose>
                  <c:choose>
                      <c:when test="${invoicePageList.page.hasNext}">
                          <li><a href="javascript:$.expense.pay.pageInvoiceList(${invoicePageList.page.nextPage })">&gt;</a></li>
                      </c:when>
                      <c:otherwise>
                          <li><a href="javascript:void(0)">&gt;</a></li>
                      </c:otherwise>
                  </c:choose>
                  <li>
                      <a>共${invoicePageList.page.totalPage }页&nbsp;|&nbsp;合计${invoicePageList.page.rowCount }条</a>
                  </li>
              </ul>
          </nav>
      </div>
  </div>
</c:if>

<!-- 申请发票表单 -->
<div id="detailInvoice-modal" class="modal modal-green-header">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">查看发票信息 </h4>
      </div>
      <div class="modal-body row">
        <form id="applyInvoice-form" class="form-horizontal form-cel">
          <h5 class="invoice-modal-title" style="float:left;">快递单号：</h5><div style="padding-top:8px;" id="remark"></div>
          <h5 class="invoice-modal-title">基本信息</h5>
          <hr class="invoice-modal-hr"/>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">发票类型:</div>
            <div class="col-xs-9 detailInvoice-info" id="invoiceType"></div>
          </div>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">金额:</div>
            <div class="col-xs-9 detailInvoice-info" id="money"></div>
          </div>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">抬头:</div>
            <div class="col-xs-9 detailInvoice-info" id="invoiceHeader"></div>
          </div>
          <h5 class="invoice-modal-title">邮寄信息</h5>
          <hr class="invoice-modal-hr"/>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">地址:</div>
            <div class="col-xs-9 detailInvoice-info" id="address"></div>
          </div>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">收件人:</div>
            <div class="col-xs-9 detailInvoice-info" id="contacts"></div>
          </div>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">收件人电话:</div>
            <div class="col-xs-9 detailInvoice-info" id="cellphone"></div>
          </div>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">创建时间:</div>
            <div class="col-xs-9 detailInvoice-info" id="create_date"></div>
          </div>
        </form>
        <div id="applyInvoice-error" class="alert alert-warning-cel alert-dismissable hide">
           <button id="applyInvoice-error-close" type="button" class="close"><i class="fa fa-close"></i></button>
           <h5><i class="icon fa fa-warning"></i>申请失败！</h5>
        </div>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script>
function showInvoiceDetail(id){
	$("#detailInvoice-modal").modal("show");
	$.get("invoice/detail",{"id":id},function(data){
		if(data.invoiceType == 0){
			$("#invoiceType").html("公司普票");
		}else{
			$("#invoiceType").html("公司专票");
		}
		$("#money").html(data.money);
		$("#invoiceHeader").html(data.invoiceHeader);
		$("#address").html(data.address);
		$("#contacts").html(data.contacts);
		$("#cellphone").html(data.cellphone);
		$("#remark").html(!data.remark?'暂无物流信息':data.remark);
		var d = new Date(data.createDate);
		$("#create_date").html(d.getFullYear() + "-" + ((parseInt(d.getMonth())+1)<10?'0'+(parseInt(d.getMonth())+1):(parseInt(d.getMonth())+1)) + "-" + (parseInt(d.getDate())<10?'0'+d.getDate():d.getDate()) + " " + (parseInt(d.getHours())<10?'0'+d.getHours():d.getHours()) + ":" + (parseInt(d.getMinutes())<10?'0'+d.getMinutes():d.getMinutes()) + ":" + ((parseInt(d.getSeconds())<10)?'0'+d.getSeconds():d.getSeconds()));
	});
}
</script>
