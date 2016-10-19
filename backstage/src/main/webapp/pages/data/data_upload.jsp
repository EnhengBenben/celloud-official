<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">数据上传</h3>
    </div>
    <div class="panel-body">
        <form class="form-inline" id="DataAmountForm">
        <label>第一步：请输入上传文件个数：</label>
         <div class="form-group">
          <input type="text" class="form-control" data-rule-required="true" name="amount" data-rule-digits="true" data-rule-max="10" data-rule-min="1">
         </div>
         <button class="btn btn-primary" type="submit" style="margin-bottom:0;">提交</button>
         <button class="btn btn-info" type="button" onclick="dataFile.empty();" style="margin-bottom:0;">清空</button>
       </form>
       <div class="panel-body" id="data-upload-set-panel">
       </div>
    </div>
    
</div>
<script type="text/javascript">
$(function(){
    $("#DataAmountForm").validate({
    	errorElement: 'label',
        errorClass: 'control-label',
        submitHandler:function(form) {
            $(form).ajaxSubmit({
                url:"dataFile/sumitAmount",
                type:"POST",
                success:function(responseText){
                	  $("#data-upload-set-panel").html(responseText);
                }
            });
         }
    });
});
</script>
