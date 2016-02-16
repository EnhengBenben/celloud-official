//根据视口和文档的宽高设置背景图片的尺寸
function setDocSize(){
	var winWidth = $(window).width();
	var docWidth = $(document).width();
	var winHeight = $(window).height();
	var docHeight = $(document).height();
	//设置宽度
	if(winWidth >= docWidth){
		$(".bgContainer").width(winWidth);
		$("#bg").width(winWidth);
		$(".wrapper").width(winWidth);
	}else{
		$(".bgContainer").width(docWidth);
		$("#bg").width(docWidth);
		$(".wrapper").width(docWidth);
	}
	//设置高度
	if(winHeight >= docHeight){
		$(".bgContainer").height(winHeight);
		$("#bg").height(winHeight);
		$(".wrapper").height(winHeight);
	}else{
		$(".bgContainer").height(docHeight);
		$("#bg").height(docHeight);
		$(".wrapper").height(docHeight);
	}
	$(".bgContainer").css("overflow","hidden");
}
setDocSize();

