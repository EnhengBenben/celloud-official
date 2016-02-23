<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">数据清理</h3>
    </div>
    <div class="panel-body">
        <div role="alert" id="data-clean-info"></div>
        <form class="form-inline" id="dataCleanForm">
        <label>请选择需要清数据的用户：</label>
         <div class="form-group">
            <select class="form-control" data-rule-required="true" data-msg-required="请选择用户" name="userId">
              <option value="">请选择用户</option>
              <c:forEach items="${userList }" var="user">
                 <option value="${user.userId }">${user.username }</option>
              </c:forEach>
            </select>
         </div>
         <button class="btn btn-primary" type="submit" style="margin-bottom:0;">确定</button>
       </form>
       <div class="panel-body" id="data-upload-set-panel">
       </div>
    </div>
    
</div>
<script type="text/javascript">
$(function(){
    $("#dataCleanForm").validate({
    	errorElement: 'label',
        errorClass: 'control-label',
        submitHandler:function(form) {
            $(form).ajaxSubmit({
                url:"dataFile/cleanData",
                type:"POST",
                success:function(data){
                	  if(data){
                		  $("#data-clean-info").attr("class",'alert alert-success').html("<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>数据清理成功");
                	  }else{
                		  $("#data-clean-info").attr("class",'alert alert-danger').html("<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>&times;</span></button>数据清理失败");
                	  }
                }
            });
         }
    });
});
</script>
