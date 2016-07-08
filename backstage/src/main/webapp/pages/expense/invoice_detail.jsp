<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">查看发票信息</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="invoiceForm">
            <input type="hidden" name="id" value="${invoice.id }">
            <input type="hidden" name="email" value="${invoice.email }">
            <div class="form-group">
                <label class="col-sm-2 control-label" for="addressDetail">发票类型</label>
                <div class="col-sm-10">
                    <c:if test="${invoice.invoice_type == false }">
                        <input type="text" class="form-control" id="invoiceType" value="公司普票" readonly="readonly" >
                    </c:if>
                    <c:if test="${invoice.invoice_type == true }">
                        <input type="text" class="form-control" id="invoiceType" value="公司专票" readonly="readonly" >
                    </c:if>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="money">金额</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="money" name="money" value="${invoice.money }" readonly="readonly" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="invoiceHeader">抬头</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="invoiceHeader" name="invoiceHeader" value="${invoice.invoice_header }" readonly="readonly" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="address">地址</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="address" name="address" value="${invoice.address }" readonly="readonly" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="contacts">收件人</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="contacts" name="contacts" value="${invoice.contacts }" readonly="readonly" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="cellphone">收件人电话</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="cellphone" name="cellphone" value="${invoice.cellphone }" readonly="readonly" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="createDate">创建时间</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="createDate" name="createDate" value="<fmt:formatDate value="${invoice.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="postCompany">快递公司</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="postCompany" name="postCompany" value="${postCompany }" data-rule-required="true">
                </div>
                <label class="col-sm-2 control-label" for="postNumber">快递单号</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="postNumber" name="postNumber" value="${postNumber }" data-rule-required="true" >
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <div class="col-sm-10 text-center">
                    <button type="submit" class="btn btn-success">保存</button>
                    <button type="reset" class="btn btn-white">重置</button>
                </div>
            </div>
        </form>
        
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#invoiceForm").validate({
		submitHandler:function(form) {      
            $(form).ajaxSubmit({
            	url:"invoice/edit",
            	success:function(data){
                    if(data>0){
                        $("#invoice-detailModal").modal("hide");
                        alert("成功");
                    }
                }
            });     
         }
	});
	$('#invoice-detailModal').on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
		expense.invoice.toInvoiceMain('1','');
    });
});
</script>
