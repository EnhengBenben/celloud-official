/**
 * HBV峰图放大
 * @param src
 */
function bigFigure(src){
  $("img[id='imageFullScreen']").css("width",1260);
  $("img[id='imageFullScreen']").css("height",156);
  showZoom(src);
}
/**
 * 原始峰图放大
 * @param src
 * @param id
 */
function bigOrigin(src,id) { 
  var width = $("#" + id).width();
  var height = $("#" + id).height();
  $("img[id='imageFullScreen']").css("width",width*1.5);
  $("img[id='imageFullScreen']").css("height",height*1.5);
  showZoom(src);
}
/**
 * 有路径替换的
 * @param src
 */
function bigReplace(src){
  $("img[id='imageFullScreen']").css("width",1050);
  $("img[id='imageFullScreen']").css("height",157.5);
  showZoom(src);
}