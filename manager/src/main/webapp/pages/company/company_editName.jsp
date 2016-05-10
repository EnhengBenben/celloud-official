<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">修改公司信息</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="companyForm">
            <input type="hidden" value="${currentName }" name="currentName" id="currentName">
            <div class="form-group">
                <label class="col-sm-2 control-label" for="oldName">原公司名称<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" id="oldName" name="oldName" value="${currentName }" disabled="disabled">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="newName">新名称<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" id="newName" name="newName" placeholder="新名称">
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-2 control-label" for="reason">原因<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" id="reason" name="reason" placeholder="原因">
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <div class="col-sm-10 text-center">
                    <button type="submit" class="btn btn-success">保存</button>
                    <button type="reset" class="btn btn-white">重置</button>
                </div>
            </div>
        </form>
        
    </div>
</div>
<script type="text/javascript">
$(function(){
	
	$("#companyForm").validate({
        submitHandler:function(form) {      
            $(form).ajaxSubmit({
                url:"company/sendName",
                success:function(responseText){
                    if(responseText>0){
                        $("#company-editModal").modal("hide");
                        alert("成功");
                    }
                }
            });     
         }
    });
    $('#company-editModal').on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
        company.getCompany(company.currentPage);
    });
});
</script>
