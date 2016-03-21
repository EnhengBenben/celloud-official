/**
 * APP管理
 */
$.appManager = {};
$.appManager.price = {
  list: function(){
    $.post("app/priceList",function(responseText){
      menu("app-price-menu",responseText);
    });
  },
  toHistoryList: function(id,name,price){
    $.post("app/priceHistory",{"appId":id,"appName":name,"price":price},function(responseText){
      menu("app-price-menu",responseText);
    });
  },
  toUpdateModel: function(id,name,price){
    $("#update-app-price-itemid").val(id);
    $("#update-app-price-name").val(name);
    $("#update-app-current-price").val(price);
    $("#update-app-price-button").on("click",function(){
      $.appManager.price.update();
    });
    $("#update-app-price-modal").modal("show");
  },
  update: function(){
    $.post("app/updatePice",{"appId":$("#update-app-price-itemid").val(),"price":$("#update-app-new-price").val()},function(result){
      if(result>0){
        $("#update-app-price-modal").modal("hide");
        $.appManager.price.list();
      }else{
        alert("保存失败");
      }
    });
  }
  
}
$(function(){
  $("body").on("click","[data-click='to-update-app-price']",function(){
    $.appManager.price.toUpdateModel($(this).data("id"),$(this).data("name"),$(this).data("price"));
  });
  $("body").on("click","[data-click='to-app-price-history']",function(){
    $.appManager.price.toHistoryList($(this).data("id"),$(this).data("name"),$(this).data("price"));
  });
});