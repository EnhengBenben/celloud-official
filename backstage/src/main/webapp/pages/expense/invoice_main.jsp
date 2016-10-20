<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="panel panel-default">
<div class="panel-heading">
    <h3 class="panel-title">发票管理</h3>
    <div class="panel-options">
        <form class="form-inline">
         <div class="form-group">
          <input type="text" class="form-control" data-rule-required="true" id="keyword" name="keyword" value="${keyword }" placeholder="请输入关键字">
         </div>
         <button class="btn btn-warning" type="button" onclick="javascript:expense.invoice.toInvoiceMain('1');" style="margin-bottom:0;">检索</button>
       </form>
     </div>
</div>
<div class="panel-body">
    <table class="table table-bordered table-striped" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th width="100px">用户名</th>
                <th width="85px">金额</th>
                <th width="85px">类型</th>
                <th>抬头</th>
                <th>地址</th>
                <th width="70px">状态</th>
                <th width="190px">备注</th>
                <th width="100px">创建时间</th>
                <th width="60px">操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${invoicePageList.datas }" var="invoice">
                <tr>
                    <td>${invoice.username }</td>
	                <td>${invoice.money }</td>
	                <td>
	                   <c:if test="${invoice.invoice_type == false }">公司普票</c:if>
	                   <c:if test="${invoice.invoice_type == true }">公司专票</c:if>
	                </td>
	                <td>${invoice.invoice_header }</td>
	                <td>${invoice.address }</td>
	                <td>
	                   <c:if test="${invoice.invoice_state == false }">已申请</c:if>
	                   <c:if test="${invoice.invoice_state == true }">已寄出</c:if>
	                </td>
	                <td>
	                   <c:if test="${empty invoice.remark }">无</c:if>
	                   <c:if test="${not empty invoice.remark }">${invoice.remark }</c:if>
                    </td>
	                <td><fmt:formatDate value="${invoice.create_date }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                <td><a href="javascript:expense.invoice.toInvoiceDdetail('${invoice.id }');">详情</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <c:if test="${invoicePageList.datas.size()>0}">
        <div class="row-fluid">
            <div class="col-md-6 keepRight">
                <nav id="pageView">
                    <ul class="pagination">
                        <c:choose>
                            <c:when test="${invoicePageList.page.hasPrev}">
                                <li><a href="javascript:expense.invoice.toInvoiceMain('${invoicePageList.page.prevPage }')">&lt;</a></li>
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
                                <li><a href="javascript:expense.invoice.toInvoiceMain('1')">1</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${invoicePageList.page.currentPage>3}">
                            <li><a href="javascript:void(0)">..</a></li>
                        </c:if>
                        <c:if test="${invoicePageList.page.currentPage>2}">
                            <li><a href="javascript:expense.invoice.toInvoiceMain('${invoicePageList.page.prevPage }')">${invoicePageList.page.prevPage }</a></li>
                        </c:if>
                        <c:if test="${invoicePageList.page.currentPage>1&&invoicePageList.page.currentPage<invoicePageList.page.totalPage}">
                            <li class="active"><a href="javascript:void(0)">${invoicePageList.page.currentPage }</a></li>
                        </c:if>
                        <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>1}">
                            <li><a href="javascript:expense.invoice.toInvoiceMain('${invoicePageList.page.nextPage }')">${invoicePageList.page.nextPage }</a></li>
                        </c:if>
                        <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>2}">
                            <li><a href="javascript:void(0)">..</a></li>
                        </c:if>
                        <c:choose>
                            <c:when test="${invoicePageList.page.currentPage==invoicePageList.page.totalPage&&invoicePageList.page.totalPage>1}">
                                <li class="active"><a href="javascript:void(0)">${invoicePageList.page.totalPage }</a></li>
                            </c:when>
                            <c:when test="${invoicePageList.page.totalPage>1}">
                                <li><a href="javascript:expense.invoice.toInvoiceMain('${invoicePageList.page.totalPage }')">${invoicePageList.page.totalPage }</a></li>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${invoicePageList.page.hasNext}">
                                <li><a href="javascript:expense.invoice.toInvoiceMain('${invoicePageList.page.nextPage }')">&gt;</a></li>
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
</div>
</div>
<div class="modal fade bs-example-modal-lg" id="invoice-detailModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
	  <div class="modal-dialog modal-lg">
	    <div class="modal-content">
	    </div>
	  </div>
</div>