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
    		  $("#expense-content").html("充值记录");
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
}
