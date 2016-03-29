<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">邮件群发</h3>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="mailingForm">
            <div class="form-group">
               <a class="btn col-md-offset-3 text-green" href="javascript:void(0)" onclick="mailing.checkedAll(1)">全选</a>
               <a class="btn text-green" href="javascript:void(0)" onclick="mailing.checkedAll(0)">全不选</a>
               <a class="btn text-green" href="javascript:void(0)" onclick="mailing.checkedAll(2)">反选</a>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">用户列表<font color="red">*</font></label>
                
                <div class="col-sm-10">
                <c:forEach items="${userList }" var="user">
                    <c:if test="${not empty user.email && user.email!='' }">
                    <div class="checkbox col-sm-2">
                        <label>
                            <input type="checkbox" name="emails" value="${user.email }" data-rule-required="true" data-msg-required="请选择收件人">
                                            ${user.username }
                        </label>
                    </div>
                    </c:if>
                </c:forEach>
                <div class="clearfix"></div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" >文档介绍<font color="red">*</font></label>
                
                <div class="col-sm-10">
                    <textarea class="form-control" cols="5" id="emailContent" name="emailContent" data-rule-required="true"></textarea>
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <div class="col-sm-12 text-center">
                    <button type="submit" class="btn btn-success">发送</button>
                </div>
            </div>
        </form>
        
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
$(function(){
    CKEDITOR.replace( 'emailContent');
    $("#mailingForm").validate({
    	errorPlacement: function (error, element) {
            error.appendTo($(element).closest("div.col-sm-10"));
       },
        submitHandler:function(form) {
          alert(CKEDITOR.instances.emailContent.getData());
          return;
            $("#emailContent").val(CKEDITOR.instances.emailContent.getData());
            $(form).ajaxSubmit({
                url:"mailing/send",
                type:"POST",
                success:function(responseText){
                    if(responseText>0){
                        alert("发送成功");
                        
                    }
                }
            });   
         }
    });
});
</script>
