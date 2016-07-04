<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	import="com.celloud.constants.RechargeType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<table class="table data-table">
	<thead>
		<tr>
			<th width="30px">
				<input type="checkbox" id="checkAll" />
			</th>
			<th class="text-center">发票</th>
			<th>充值方式</th>
			<th class="text-center">充值时间</th>
			<th class="text-right">充值金额</th>
			<th class="text-right">充值后余额</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${recharges.datas }" var="recharge">
			<tr>
				<td>
					<c:choose>
						<c:when test="${recharge.invoiceState!=0 }">
							<input type="checkbox" disabled="disabled" />
						</c:when>
						<c:otherwise>
							<input type="checkbox" name="rechargeIds" value="${recharge.id }" />
						</c:otherwise>
					</c:choose>
				</td>
				<td class="text-center">
				    ${recharge.invoiceState==0?"未开":(recharge.invoiceState==1?"已开":"不开" )}
				</td>
				<td>${recharge.rechargeTypeName }</td>
				<td class="text-center">
					<fmt:formatDate value="${recharge.createDate }" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="text-right">${recharge.amount }</td>
				<td class="text-right">${recharge.balances }</td>
			</tr>
		</c:forEach>
		<c:if test="${ recharges.datas.size() == 0  }">
			<tr height="250px">
				<td colspan="6" class="text-center" style="vertical-align: middle; font-size: 18px;">
					<i class="glyphicon glyphicon-exclamation-sign text-yellow"></i> 您好，还没有充值记录哦！
				</td>
			</tr>
		</c:if>
	</tbody>
</table>
<div class="row">
	<div class="col-xs-2">
		<c:if test="${ recharges.datas.size() != 0  }">
		&nbsp;
		<button class="btn btn-default" id="applyInvoice">申请发票</button>
		</c:if>
	</div>
	<div class="col-xs-10">
		<div class="pagination text-right">
			<c:if test="${recharges.datas.size()>0}">
				<input id="expense-current-page-hide" value="${recharges.page.currentPage }" type="hidden">
				<ul id="pagination-pay" class="pages">
					<!-- 显示prev -->
					<c:if test="${recharges.page.hasPrev}">
						<li>
							<a
								href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage-1>0?recharges.page.currentPage-1:1 }');">&lt;</a>
						</li>
					</c:if>
					<!-- 显示第一页 -->
					<c:choose>
						<c:when test="${recharges.page.currentPage==1}">
							<li class="active">
								<a href="javascript:void(0);">1</a>
							</li>
						</c:when>
						<c:otherwise>
							<li>
								<a href="javascript:$.expense.pay.pageRechargeList('1');">1</a>
							</li>
						</c:otherwise>
					</c:choose>

					<c:if test="${recharges.page.currentPage>4&&recharges.page.totalPage>10}">
						<li>...</li>
					</c:if>
					<c:choose>
						<c:when test="${recharges.page.totalPage-recharges.page.currentPage>=7}">
							<c:if test="${recharges.page.currentPage==3}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage-1 }');">${recharges.page.currentPage-1 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.currentPage==4}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage-2 }');">${recharges.page.currentPage-2 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.currentPage>3}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage-1 }');">${recharges.page.currentPage-1 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.currentPage>1&&recharges.page.currentPage<recharges.page.totalPage}">
								<li class="active">
									<a href="javascript:void(0);">${recharges.page.currentPage }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.totalPage-recharges.page.currentPage>1}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+1 }')">${recharges.page.currentPage+1 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.totalPage-recharges.page.currentPage>2}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+2 }')">${recharges.page.currentPage+2 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.totalPage-recharges.page.currentPage>3}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+3 }')">${recharges.page.currentPage+3 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.totalPage-recharges.page.currentPage>4}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+4 }')">${recharges.page.currentPage+4 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.totalPage-recharges.page.currentPage>5}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+5 }')">${recharges.page.currentPage+5 }</a>
								</li>
							</c:if>
							<c:if test="${recharges.page.currentPage<4}">
								<c:if test="%{recharges.page.totalPage-recharges.page.currentPage>6}">
									<li>
										<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+6 }')">${recharges.page.currentPage+6 }</a>
									</li>
								</c:if>
							</c:if>
							<c:choose>
								<c:when test="${recharges.page.currentPage==1}">
									<c:if test="%{recharges.page.totalPage-recharges.page.currentPage>7}">
										<li>
											<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+7 }')">${recharges.page.currentPage+7 }</a>
										</li>
									</c:if>
									<c:if test="%{recharges.page.totalPage-recharges.page.currentPage>8}">
										<li>
											<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+8 }')">${recharges.page.currentPage+8 }</a>
										</li>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${recharges.page.currentPage==2}">
											<c:if test="${recharges.page.totalPage-recharges.page.currentPage>7}">
												<li>
													<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+7 }')">${recharges.page.currentPage+7 }</a>
												</li>
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${recharges.page.currentPage>4 && (recharges.page.totalPage-recharges.page.currentPage>6)}">
												<li>
													<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+6 }')">${recharges.page.currentPage+6 }</a>
												</li>
											</c:if>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${recharges.page.totalPage-8>0}">
									<c:forEach begin="${recharges.page.totalPage-8}" step="1" end="${recharges.page.totalPage-1}" var="step">
										<c:choose>
											<c:when test="${step==recharges.page.currentPage}">
												<li class="active">
													<a href="javascript:void(0)">${step }</a>
												</li>
											</c:when>
											<c:otherwise>
												<li>
													<a href="javascript:$.expense.pay.pageRechargeList('${step}')">${step }</a>
												</li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach begin="2" step="1" end="${recharges.page.totalPage-1}" var="step">
										<c:choose>
											<c:when test="${step==recharges.page.currentPage}">
												<li class="active">
													<a href="javascript:void(0)">${step }</a>
												</li>
											</c:when>
											<c:otherwise>
												<li>
													<a href="javascript:$.expense.pay.pageRechargeList('${step}')">${step }</a>
												</li>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<c:if test="${recharges.page.totalPage-recharges.page.currentPage>=8&&recharges.page.totalPage>10}">
						<li>...</li>
					</c:if>
					<c:choose>
						<c:when test="${recharges.page.currentPage==recharges.page.totalPage&&recharges.page.totalPage>1}">
							<li class="active">
								<a href="javascript:void(0)">${recharges.page.totalPage }</a>
							</li>
						</c:when>
						<c:otherwise>
							<c:if test="${recharges.page.totalPage>1}">
								<li>
									<a href="javascript:$.expense.pay.pageRechargeList('${recharges.page.totalPage }')">${recharges.page.totalPage }</a>
								</li>
							</c:if>
						</c:otherwise>
					</c:choose>
					<c:if test="${recharges.page.hasNext}">
						<li>
							<a
								href="javascript:$.expense.pay.pageRechargeList('${recharges.page.currentPage+1>recharges.page.totalPage?recharges.page.totalPage:recharges.page.currentPage+1 }')">&gt;</a>
						</li>
					</c:if>
					<li>
					   <select onchange="$.expense.pay.pageRechargeList('1',this.value)">
					       <option ${ recharges.page.pageSize==10?'selected':''}>10</option>
					       <option ${ recharges.page.pageSize==20?'selected':''}>20</option>
					       <option ${ recharges.page.pageSize==50?'selected':''}>50</option>
					       <option ${ recharges.page.pageSize==100?'selected':''}>100</option>
					   </select>
					</li>
					<li>共${recharges.page.totalPage }页&nbsp;|&nbsp;合计${recharges.page.rowCount }条</li>
				</ul>
			</c:if>
		</div>
	</div>
</div>
<!-- 申请发票表单 -->
<div id="applyInvoice-modal" class="modal modal-green-header">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">申请发票 </h4>
      </div>
      <div class="modal-body row">
        <form id="applyInvoice-form" class="form-horizontal form-cel">
          <h5 class="invoice-modal-title">发票信息</h5>
          <hr class="invoice-modal-hr"/>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">申请金额</div>
            <div class="col-xs-9">
                <input type="text" name="money" id="money" readonly="readonly"/>
            </div>
          </div>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">发票类型</div>
            <div class="col-xs-9">
                <div class="radio-div"><input type="radio" name="invoiceType" value="0" checked="checked"/>公司普票</div>
            </div>
          </div>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">发票抬头</div>
            <div class="col-xs-9">
                <input type="text" id="invoiceHeader" name="invoiceHeader" maxlength="45"/><span class="invoice-modal-error"></span>
            </div>
          </div>
          <h5 class="invoice-modal-title">邮寄信息</h5>
          <hr class="invoice-modal-hr"/>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">公司地址</div>
            <div class="col-xs-9">
                <input type="text" id="address" name="address" maxlength="45"/><span class="invoice-modal-error"></span>
            </div>
          </div>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">联系人</div>
            <div class="col-xs-9">
                <input type="text" id="contacts" name="contacts" maxlength="45"/><span class="invoice-modal-error"></span>
            </div>
          </div>
          <div class="form-group">
            <div class="control-label form-label col-xs-3">联系方式</div>
            <div class="col-xs-9">
                <input type="text" id="cellphone" name="cellphone" maxlength="45"/><span class="invoice-modal-error"></span>
            </div>
          </div>
          <!-- 隐藏域 -->
          <input type="hidden" id="rechargeIds" name="rechargeIds">
          <input type="hidden" id="invoiceState" name="invoiceState" value="0">
        </form>
        <div id="applyInvoice-error" class="alert alert-warning-cel alert-dismissable hide">
           <button id="applyInvoice-error-close" type="button" class="close"><i class="fa fa-close"></i></button>
           <h5><i class="icon fa fa-warning"></i>申请失败！</h5>
        </div>
        <div id="applyInvoice-success" class="alert alert-warning-cel alert-dismissable hide">
           <h5><i class="icon fa fa-warning"></i>申请成功！</h5>
        </div>
      </div>
      <div class="modal-footer">
        <button id="reset-applyInvoice" type="button" class="btn btn-celloud-close btn-flat pull-left">重置</button>
        <button id="save-applyInvoice" type="button" class="btn btn-celloud-success btn-flat">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- 提示modal -->
<div id="invoice-tip-modal" class="modal modal-celloud-green">
  <div class="modal-dialog">
    <div id="tip-modal-content" class="modal-content tipModal">
      <div id="tip-modal-head" class="modal-header" id="">
        <button class="close" type="button" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="icon fa fa-warning"></i>提示</h4>
      </div>
      <div class="modal-body">
        <p id="tip-text">&hellip;</p>
      </div>
      <div class="modal-footer">
        <button id="check-flase" type="button" class="btn btn-outline pull-left" data-dismiss="modal">取消</button>
        <button id="check-true" type="button" class="btn btn-outline" data-dismiss="modal">确定</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
$(function(){
	$("input[name='rechargeIds']").click(function(){
		if($("input[name='rechargeIds']").length == $("input[name='rechargeIds']:checked").length){
			$("#checkAll").prop("checked",true);
		}else{
			$("#checkAll").prop("checked",false);
		}
	});
	$("#checkAll").click(function(){
        if($(this).prop("checked")){
            $("input[name='rechargeIds']").prop('checked',true);
        }else{
            $("input[name='rechargeIds']").prop('checked',false);
        }
    });
	$("#applyInvoice").click(function(){
		var rechargeIds = $("input[name='rechargeIds']:checked");
		var money = 0;
		if(rechargeIds.length < 1){
			$.expense.pay.showTipModal("请选择一条记录");
			return;
		}
		rechargeIds.each(function(){
			money += parseFloat($(this).parent().siblings().eq(3).html());
		});
		if(money < 0.02){
			$.expense.pay.showTipModal("最少申请100元");
			return;
		}
		$("#applyInvoice-modal").modal("show");
		$("#money").attr("value",money);
		var ids = new Array();
		rechargeIds.each(function(){
            ids.push($(this).val());
        });
		$("#rechargeIds").val(ids.join(","));
	});
	$("#save-applyInvoice").click(function(){
		$(this).prop("disabled",true);
		if(validateApplyForm()){
			// 提交表单
			$.post("invoice/apply",$("#applyInvoice-form").serialize(),function(data){
				if(data > 0){
					$("#applyInvoice-modal").modal("hide");
					$.expense.pay.tab['to-recharge-record']();
				}else{
					$("#applyInvoice-error").removeClass("hide");
				}
			});
		}
		$(this).prop("disabled",false);
	});
	$("#reset-applyInvoice").click(function(){
		$("#applyInvoice-form").get(0).reset();
	});
	$("#applyInvoice-error-close").click(function(){
		$("#applyInvoice-error").addClass("hide");
	});
})

function validateApplyForm(){
	var flag = true;
	$(".invoice-modal-error").html("");
	// 公司名称(抬头)
    var invoiceHeader = $.trim($("#invoiceHeader").val());
    if(invoiceHeader==""){
    	$("#invoiceHeader").next().html("请输入公司名称！");
        flag = false;
    }
    
    // 公司地址
    var address = $.trim($("#address").val());
    if(address==""){
        $("#address").next().html("请输入公司地址！");
        flag = false;
    }
    
    // 联系人
    var contacts = $.trim($("#contacts").val());
    if(contacts==""){
        $("#contacts").next().html("请输入联系人！");
        flag = false;
    }
    
    //手机号
    var cellPhone = $.trim($("#cellphone").val());
    if(cellPhone!=""){
        var mobileregex = /^[\d+]{11}$/;
        if(!mobileregex.test(cellPhone)){
        	$("#cellphone").next().html("手机号格式不正确！");
        	flag = false;
        }
    }else{
    	$("#cellphone").next().html("联系方式不能为空！");
    	flag = false;
    }
    return flag;
}

</script>