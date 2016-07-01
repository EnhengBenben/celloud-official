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
    		  $("#expense-content").html("发票管理");
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
	 var money = $self.find("input[name='money']").val();
	 var result = $.isNumeric(money);
	 if(result){
		 $("#tip-modal").modal("show");
	 }else{
		 $self.find("input[name='money']").parent().popover({
			 content:"请正确输入充值金额！",
		 }).popover('show');
		 $self.find("input[name='money']").select();
	 }
	 return result;
  });
}
