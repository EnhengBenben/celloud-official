<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table data-table text-center">
    <thead>
        <tr>
            <th class="pay-money">金额</th>
            <th>类型</th>
            <th>抬头</th>
            <th>地址</th>
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
                  <td><fmt:formatDate value="${invoice.createDate }" type="both"/></td>
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
<div class="pagination text-center">
<c:if test="${invoicePageList.datas.size()>0}">
    <input id="expense-current-page-hide" value="${invoicePageList.page.currentPage }" type="hidden" >
    <ul id="pagination-pay" class="pages">
      <!-- 显示prev -->
      <c:if test="${invoicePageList.page.hasPrev}">
          <li><a id="prev-page-expense" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage-1 }');">&lt;</a></li>
      </c:if>
      <!-- 显示第一页 -->
      <c:choose>
        <c:when test="${invoicePageList.page.currentPage==1}"><li class="active"><a href="#">1</a></li></c:when>
        <c:otherwise><li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('1');">1</a></li></c:otherwise>
      </c:choose>
      
      <c:if test="${invoicePageList.page.currentPage>4&&invoicePageList.page.totalPage>10}">
          <li>...</li>
      </c:if>
      <c:choose>
        <c:when test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>=7}">
          <c:if test="${invoicePageList.page.currentPage==3}">
              <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage-1 }')">${invoicePageList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${invoicePageList.page.currentPage==4}">
              <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage-2 }')">${invoicePageList.page.currentPage-2 }</a></li>
          </c:if>
          <c:if test="${invoicePageList.page.currentPage>3}">
              <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage-1 }')">${invoicePageList.page.currentPage-1 }</a></li>
          </c:if>
          <c:if test="${invoicePageList.page.currentPage>1&&invoicePageList.page.currentPage<invoicePageList.page.totalPage}">
              <li class="active"><a href="#">${invoicePageList.page.currentPage }</a></li>
          </c:if>
          <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>1}">
              <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+1 }')">${invoicePageList.page.currentPage+1 }</a></li>
          </c:if>
          <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>2}">
              <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+2 }')">${invoicePageList.page.currentPage+2 }</a></li>
          </c:if>
          <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>3}">
              <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+3 }')">${invoicePageList.page.currentPage+3 }</a></li>
          </c:if>
          <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>4}">
              <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+4 }')">${invoicePageList.page.currentPage+4 }</a></li>
          </c:if>
          <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>5}">
              <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+5 }')">${invoicePageList.page.currentPage+5 }</a></li>
          </c:if>
          <c:if test="${invoicePageList.page.currentPage<4}">
              <c:if test="%{invoicePageList.page.totalPage-invoicePageList.page.currentPage>6}">
                  <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+6 }')">${invoicePageList.page.currentPage+6 }</a></li>
              </c:if>
          </c:if>
          <c:choose>
            <c:when test="${invoicePageList.page.currentPage==1}">
              <c:if test="%{invoicePageList.page.totalPage-invoicePageList.page.currentPage>7}">
                  <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+7 }')">${invoicePageList.page.currentPage+7 }</a></li>
              </c:if>
              <c:if test="%{invoicePageList.page.totalPage-invoicePageList.page.currentPage>8}">
                  <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+8 }')">${invoicePageList.page.currentPage+8 }</a></li>
              </c:if>
            </c:when>
            <c:otherwise>
              <c:choose>
                <c:when test="${invoicePageList.page.currentPage==2}">
                  <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>7}">
                      <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+7 }')">${invoicePageList.page.currentPage+7 }</a></li>
                  </c:if>
                </c:when>
                <c:otherwise>
                  <c:if test="${invoicePageList.page.currentPage>4 && (invoicePageList.page.totalPage-invoicePageList.page.currentPage>6)}">
                      <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+6 }')">${invoicePageList.page.currentPage+6 }</a></li>
                  </c:if>
                </c:otherwise>
              </c:choose>
            </c:otherwise>
          </c:choose>
        </c:when>
        <c:otherwise>
          <c:choose>
            <c:when test="${invoicePageList.page.totalPage-8>0}">
              <c:forEach begin="${invoicePageList.page.totalPage-8}" step="1" end="${invoicePageList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==invoicePageList.page.currentPage}">   
                      <li class="active"><a href="#">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${step }')">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <c:forEach begin="2" step="1" end="${invoicePageList.page.totalPage-1}" var="step">
                <c:choose>
                  <c:when test="${step==invoicePageList.page.currentPage}">   
                      <li class="active"><a href="#">${step }</a></li>
                  </c:when>
                  <c:otherwise>
                      <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${step }')">${step }</a></li>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </c:otherwise>
      </c:choose>
      <c:if test="${invoicePageList.page.totalPage-invoicePageList.page.currentPage>=8&&invoicePageList.page.totalPage>10}">
          <li>...</li>
      </c:if>
      <c:choose>
        <c:when test="${invoicePageList.page.currentPage==invoicePageList.page.totalPage&&invoicePageList.page.totalPage>1}"> 
          <li class="active"><a href="#">${invoicePageList.page.totalPage }</a></li>
        </c:when>
        <c:otherwise>
          <c:if test="${invoicePageList.page.totalPage>1}">   
            <li><a name="pagination-pay" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.totalPage }')">${invoicePageList.page.totalPage }</a></li>
          </c:if>
        </c:otherwise>
      </c:choose>
      <c:if test="${invoicePageList.page.hasNext}">
          <li><a id="next-page-data" href="javascript:$.expense.pay.pageInvoiceList('${invoicePageList.page.currentPage+1 }')">&gt;</a></li>
      </c:if>
      <li>
                  共${invoicePageList.page.totalPage }页&nbsp;|&nbsp;合计${invoicePageList.page.rowCount }条
      </li>
    </ul>
  </c:if>
</div>

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
		var d = new Date(data.createDate);
		$("#create_date").html(d.getFullYear() + "-" + (parseInt(d.getMonth())+1) + "-" + d.getDate() + " " + d.getHours() + ":" + d.getMinutes() + ":" + ((parseInt(d.getSeconds())<10)?'0'+d.getSeconds():d.getSeconds()));
	});
}
</script>
