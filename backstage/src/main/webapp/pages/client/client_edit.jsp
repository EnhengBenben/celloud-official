<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">新增客户端版本</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="clientForm">
            <div class="form-group">
                <label class="col-sm-2 control-label" for="clientVersion">版本号<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" data-rule-maxlength="30" id="clientVersion" name="version" placeholder="客户端版本号">
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-2 control-label" for="nameX86">X86文件名称<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" data-rule-maxlength="100" id="nameX86" name="nameX86" placeholder="X86文件名称">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="nameX64">x64文件名称<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" data-rule-maxlength="100" id="nameX64" name="nameX64" placeholder="x64文件名称">
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <div class="col-sm-10 text-center">
                    <button type="submit" class="btn btn-success">保存</button>
                    <button type="button" class="btn btn-white" data-dismiss="modal">取消</button>
                </div>
            </div>
        </form>
        
    </div>
</div>
<script type="text/javascript">
$(function(){
	$("#clientForm").validate({
		submitHandler:function(form) {
            $(form).ajaxSubmit({
            	url:"client/save",
            	type:"POST",
            	success:function(responseText){
                    if(responseText>0){
                        $("#client-editModal").modal("hide");
                        alert("成功");
                        
                    }
                }
            });   
         }
	});
	$('#client-editModal').on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
		client.getClientList(client.currentPage);
	});
	$("#clientVersion").blur(function(){
		var version=$(this).val();
		if(version.length>0){
			var name="CelLoudClient";
	        $("#nameX64").val(name+"_x64_"+$(this).val()+".exe");
	        $("#nameX86").val(name+"_x86_"+$(this).val()+".exe");
		}
	});
});
</script>
