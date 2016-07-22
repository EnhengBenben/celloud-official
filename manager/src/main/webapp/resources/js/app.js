/**
 * APP管理
 */
$.appManager = {};
$.appManager.grant = {
  list: function(){
    $.post("app/list",function(responseText){
      menu("app-grant-menu",responseText);
    });
  },
  toGrant: function(appId){
    $("#appId").val(appId);
    $.ajax({
    	url:"app/getUsersByApp",
    	async:false,
    	data:{appId:appId},
    	type:"post",
    	success:function(data){
    		$("input[name='userIds']").each(function(){
    			$(this).prop("checked",false);
    		});
    		$("input[name='userIds']").each(function(){
    			for(var i = 0;i<data.length;i++){
    				if($(this).val()==data[i]){
    					$(this).prop("checked",true);
    					break;
    				}
    			}
    		});
    	}
    });
    $("#grantApp").one("click",function(){
      $.appManager.grant.doGrant();
    });
    $("#grant-app-modal").modal("show");
  },
  doGrant : function(){
	  $.post("app/grant",$("#grantAppForm").serialize(),function(data){
		  $("#grant-app-modal").modal("hide");
		  $("#grant-app-modal").on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
			  $.appManager.grant.list();
          });
	  });
  }
}
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
    $("#update-app-new-price").val('');
    $("#update-app-price-button").one("click",function(){
      $.appManager.price.update();
    });
    $("#update-app-price-modal").modal("show");
  },
  update: function(){
	  var price = $("#update-app-new-price").val();
	  if(!isNaN(price)){
		  $.post("app/updatePice",{"appId":$("#update-app-price-itemid").val(),"price":price},function(result){
		      if(result>0){
		          $("#update-app-price-modal").modal("hide");
		          $.appManager.price.list();
		      }else{
		          alert("保存失败");
		      }
	      });
	  }else{
		  alert("请输入正确的格式!");
	  }
  }
  
}
$(function(){
  $("body").on("click","[data-click='to-update-app-price']",function(){
    $.appManager.price.toUpdateModel($(this).data("id"),$(this).data("name"),$(this).data("price"));
  });
  $("body").on("click","[data-click='to-app-price-history']",function(){
    $.appManager.price.toHistoryList($(this).data("id"),$(this).data("name"),$(this).data("price"));
  });
  $("body").on("click","[data-click='to-grant-app-user']",function(){
	  $.appManager.grant.toGrant($(this).data("id"));
  });
});