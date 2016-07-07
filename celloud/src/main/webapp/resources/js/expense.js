/**
 * 费用中心js
 */
$.expense = {};
$.ajaxSetup ({
  cache: false //关闭AJAX相应的缓存
});
$(function(){
  init_expense();
  $.expense.pay.expenseList();
});
function init_expense(){
  $.expense.pay = {
      page:{
    	  recharge:{
    		  currentPage:1,
    		  pageSize:20
    	  }
      },
      showTipModal:function(text){
    	  $("#tip-text").html(text);
    	  $("#invoice-tip-modal").modal("show");
      },
      expenseList: function(){
        $.get("expense/toRunExpenseList",{},function(response){
          $.expense.pay.loadlist(response);
        });
      },
      pagination: function(currentPage){
        $.get("expense/toRunExpenseList",{"page":currentPage},function(response){
          $.expense.pay.loadlist(response);
        });
      },
      pageRechargeList:function(currentPage,pageSize){
    	  currentPage=currentPage|| 1;
    	  pageSize=pageSize|| $.expense.pay.page.recharge.pageSize;
    	  $.expense.pay.page.recharge.currentPage = currentPage;
    	  $.expense.pay.page.recharge.pageSize=pageSize;
    	  $("#expense-content").load(CONTEXT_PATH+"/pay/recharge/list",{currentPage:currentPage,pageSize:pageSize});
      },
      pageInvoiceList:function(currentPage){
    	  currentPage=currentPage||1;
    	  $("#expense-content").load(CONTEXT_PATH+"/invoice/list",{"currentPage":currentPage,"pageSize":20})
      },
      recharge:function(){
    	  $("#tip-modal").modal("hide");
    	  $("#expense-content").load(CONTEXT_PATH+"/pay/recharge");
      },
      tab:{
    	  "to-pay-detail":function(){
    		  $.expense.pay.expenseList();
    	  },
    	  "to-recharge":function(){
    		  $.expense.pay.recharge();
    	  },
    	  "to-recharge-record":function(){
    		  $.expense.pay.pageRechargeList();
    	  },
    	  "to-invoice":function(){
    		  $.expense.pay.pageInvoiceList();
    	  }
      },
      loadlist: function(response){
        $("#expense-content").html(response);
        $("#pay-list-tbody").find("span").each(function(){
          var _data = $(this).attr("title");
          if(_data.length>40){
            var newData = utils.splitDataByInfo(_data, "\r\n" ,40);
            $(this).attr("title",newData);
          }
        });
        /** 
         *翻页
         */
        $("#pagination-pay").on("click","a",function(e){
          var id = $(this).attr("id");
          var currentPage = parseInt($("#expense-current-page-hide").val());
          var page = id == null ? 
              $(this).html() : (id == "prev-page-expense" ? currentPage-1 : currentPage+1);
              $.expense.pay.pagination(page);
        });
      }
  };
  $("#expense-box li").click(function(){
	  var $self = $(this);
	  $self.siblings().removeClass("active");
	  $self.addClass("active");
	  $("#secondClassifyName").html($self.text());
	  var id = $self.attr("id");
	  $.expense.pay.tab[id]();
  });
  $("#expense-content").on("click","#onlineRechargeRadio",function(){
	  $("#onlineRecharge").removeClass("hide");
	  $("#companyTransfer").addClass("hide");
  });
  $("#expense-content").on("click","#companyTransferRadio",function(){
	  $("#companyTransfer").removeClass("hide");
	  $("#onlineRecharge").addClass("hide");
  });
  $("#expense-content").on("submit","#rechargeForm",function(){
	 var $self = $("#rechargeForm");
	 var $group =  $self.find("#moneyGroup");
	 $group.removeClass("has-error");
	 $group.find(".text-danger").hide();
	 var money = $self.find("input[name='money']").val();
	 if($.isNumeric(money) && money*1 == 0.01){//测试用的
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
	 $self.find("input[name='money']").val(parseInt(money));
	 return true;
  });
}
