$(function() {
	$("#printBtn").click(function() {
		$("#printBtns").hide();
		$(".main-container").css("padding", "0px");
		$(".affix").parent().removeClass("col-xs-2").hide();
		$(".affix").parent().siblings().removeClass("col-xs-10").addClass("col-xs-12");
		$("#rockyBaseInfoForm input[type='text']").each(function(index,item){
			var $this = $(this);
			var $label = $("<span>"+$this.val()+"</span>");
			$label.css({"text-align": "left", "font-weight": "normal", "white-space": "normal"});
			$label.attr("name",$this.attr("name"));
			$label.attr("class",$this.attr("class"));
			$label.css("display","normal");
			$this.hide();
			$this.after($label);
		});
		window.print();
		$("#printBtns").show();
		$(".affix").parent().addClass("col-xs-2").show();
		$(".affix").parent().siblings().removeClass("col-xs-12").addClass("col-xs-10");
		$(".main-container").css("padding", "0px 45px 0px 70px");
		$("#rockyBaseInfoForm input[type='text']").show();
		$("#rockyBaseInfoForm span[name]").remove();
	});
	$("#saveBtn").click(function(){
		var url = $("#rockyBaseInfoForm").attr("action");
		$.post(url, $("#rockyBaseInfoForm").serialize(),function(){
			alert('保存成功！');
		});
	});
	$("#resetBtn").click(function(){
		$("#rockyBaseInfoForm")[0].reset();
	});
	$("#downloadBtn").click(function(){
		var url = $("#downloadPdfUrl").val();
		var objId = $("#objId").val();
		var datakey = $("#dataKey").val();
		$.get(url,{"objId":objId,"dataKey":dataKey},function(data){
			if(data == 1){
				alert("下载失败,请联系管理员!");
			}
		});
	});
});