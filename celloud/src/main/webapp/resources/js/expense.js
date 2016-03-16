/**
 * 费用中心js
 */
$.expense = {};
$.ajaxSetup ({
  cache: false //关闭AJAX相应的缓存
});
$(function(){
  init_expense();
  $.expense.pay.all();
});
function init_expense(){
  $.expense.pay = {
      all: function(){
        $.get("expense/toRunExpenseList",{},function(response){
          $.expense.pay.loadlist(response);
        });
      },
      pagination: function(currentPage){
        $.get("expense/toRunExpenseList",{"page":currentPage},function(response){
          $.expense.pay.loadlist(response);
        });
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
  }
}
