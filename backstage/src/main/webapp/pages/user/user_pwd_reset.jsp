<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel panel-default">
<div class="panel-heading">
    <h3 class="panel-title">重置密码</h3>
</div>
<div class="panel-body">
    <div role="alert" id="resetPwdInfo"></div>
    <form role="form" class="form-horizontal" id="pwdResetForm">
       <div class="form-group">
           <label class="col-sm-2 control-label">原密码<font color="red">*</font></label>
           <div class="col-sm-5">
               <input type="password" autocomplete="off" id="inputOldPassword" name="oldPassword" class="form-control" data-rule-required="true" data-msg-required="请输入原密码">
           </div>
       </div>
       
       <div class="form-group">
           <label class="col-sm-2 control-label" >新密码<font color="red">*</font></label>
           <div class="col-sm-5">
               <input type="password" class="form-control" data-rule-required="true" data-msg-required="请输入新密码" data-rule-pwdRE1="true" data-rule-pwdRE2="true" id="inputNewPassword" name="newPassword" placeholder="">
           </div>
       </div>
       <div class="form-group">
           <label class="col-sm-2 control-label">请确认</label>
           <div class="col-sm-5">
               <input type="password" class="form-control" data-rule-required="true" data-msg-required="请输入确认密码"  data-rule-equalTo="#inputNewPassword" data-msg-equalTo="确认密码与新密码不一致"  id="inputConfirmPassword" placeholder="">
           </div>
       </div>
       <div class="form-group-separator"></div>
       <div class="form-group">
           <div class="col-sm-10 text-center">
               <button type="submit" class="btn btn-success">保存</button>
           </div>
       </div>
   </form>
</div>
</div>
<script type="text/javascript">
$(function(){
	$("#inputOldPassword").focus();
    $("#pwdResetForm").validate({
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
       },
        submitHandler:function(form) {      
            $(form).ajaxSubmit({
                url:"<%=request.getContextPath()%>/user/updatePassword",
                success:function(data){
                	if (data == 203) {
                		$("#inputOldPassword").focus();
                        $("#inputOldPassword").parent().after("<div id='inputOldPassword-error' class='help-block' ><font color='#cc3f44'>原始密码错误</font></div>");
                    } else {
                        if(data>0){
                            $("#pwdResetForm")[0].reset();
                            $("#resetPwdInfo").attr("class",'alert alert-success').html("保存成功");
                        }else{
                        	 $("#resetPwdInfo").attr("class",'alert alert-danger').html("用户修改原始密码失败");
                        }
                    }
                }
            });     
         }
    });
});
</script>