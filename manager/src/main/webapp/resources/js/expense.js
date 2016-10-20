/**
 * 费用中心js
 */
$.expense = {};
$.ajaxSetup ({
  cache: false //关闭AJAX相应的缓存
});
$(function(){
  init_expense();
  $("#to-recharge-record").click(function(){
    $.expense.pay.pageRechargeList();
  });
  $("#to-pay-detail").click(function(){
    $.expense.pay.expenseList();
  });
  $("#to-recharge").click(function(){
    $.expense.pay.recharge();
  });
  $("#to-invoice").click(function(){
    $.expense.pay.pageInvoiceList();
  });
  
});
function init_expense(){
  $.expense.pay = {
      page:{
    	  recharge:{
    		  currentPage:1,
    		  pageSize:10
    	  }
      },
      showTipModal:function(text){
    	  $("#tip-text").html(text);
    	  $("#invoice-tip-modal").modal("show");
      },
      expenseList: function(currentPage,pageSize){
        currentPage=currentPage|| 1;
        pageSize=pageSize|| $.expense.pay.page.recharge.pageSize;
        $.expense.pay.page.recharge.currentPage = currentPage;
        $.expense.pay.page.recharge.pageSize=pageSize;
        $.post("expense/toRunExpenseList",{page:currentPage,size:pageSize},function(responseText){
          menu("to-pay-detail",responseText);
        });
      },
      pageRechargeList:function(currentPage,pageSize){
    	  currentPage=currentPage|| 1;
    	  pageSize=pageSize|| $.expense.pay.page.recharge.pageSize;
    	  $.expense.pay.page.recharge.currentPage = currentPage;
    	  $.expense.pay.page.recharge.pageSize=pageSize;
    	  $.post(CONTEXT_PATH+"/pay/recharge/list",{currentPage:currentPage,pageSize:pageSize},function(responseText){
          menu("to-recharge-record",responseText);
        });
      },
      pageInvoiceList:function(currentPage,pageSize){
    	  currentPage=currentPage||1;
    	  pageSize=pageSize|| $.expense.pay.page.recharge.pageSize;
        $.expense.pay.page.recharge.currentPage = currentPage;
        $.expense.pay.page.recharge.pageSize=pageSize;
        $.post(CONTEXT_PATH+"/invoice/list",{currentPage:currentPage,pageSize:pageSize},function(responseText){
          menu("to-invoice",responseText);
        });
      },
      recharge:function(){
        $(".modal-backdrop").remove();
    	  $.post(CONTEXT_PATH+"/pay/recharge",function(responseText){
          menu("to-recharge",responseText);
        });
      },
  };
  $("body").on("click","#onlineRechargeRadio",function(){
	  $("#onlineRecharge").removeClass("hide");
	  $("#companyTransfer").addClass("hide");
  });
  $("body").on("click","#companyTransferRadio",function(){
	  $("#companyTransfer").removeClass("hide");
	  $("#onlineRecharge").addClass("hide");
  });
  $("body").on("submit","#rechargeForm",function(){
	 var $self = $("#rechargeForm");
	 var $group =  $self.find("#moneyGroup");
	 var payType = $self.find('#pay_type_alipay').is(":checked");
	 $group.removeClass("has-error");
	 $group.find(".text-danger").hide();
	 var money = $self.find("input[name='money']").val();
	 if($.isNumeric(money) && money*1 == 0.01){//测试用的
	   $self.attr('action',$self.attr("action")+(payType?'alipay':'jdpay'));
		 $("#tip-modal").modal("show");
		 return true;
	 }
	 if(!$.isNumeric(money)){
		 $("#money-number").show();
		 $group.addClass("has-error");
		 return false;
	 }
	 if(money*1 < 10){
		 $("#money-min").show();
		 $group.addClass("has-error");
		 return false;
	 }
	 if( (money*1)%1 != 0){
		 $("#money-int").show();
		 $group.addClass("has-error");
		 return false;
	 }
	 if( money*1 >10000){
		 $("#money-max").show();
		 $group.addClass("has-error");
		 return false;
	 }
	 $("#tip-modal").modal('show');
	 $self.find("input[name='money']").val(parseInt(money));
	 $self.attr('action',$self.attr("action")+(payType?'alipay':'jdpay'));
	 return true;
  });
}
