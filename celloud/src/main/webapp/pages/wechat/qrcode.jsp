<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<img alt="" src="<%=request.getContextPath()%>/api/wechat/qrcode" width="150px;" height="150px;" id="qrcodeImg">
<p id="qrcodeInfo" style="display: none;text-align: center;font-size: 12px;">二维码已失效<br>请刷新后再试</p>
<script type="text/javascript">
var time = 300000;//5min有效期
function show(){
   $("#qrcodeInfo").css("display","");
   $("#qrcodeImg").css("opacity",0.2);
}
setTimeout(show,time);
</script>