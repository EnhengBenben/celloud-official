<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Basic Setup -->
<div>第二步：将文件复制到/share/data/file/目录。</div>
<div class="panel-body">
    <div style="margin-bottom:10px;">第三步：文件详细信息设置  </div>
    <form class="form-inline" id="dataFileUploadForm">
    <c:forEach begin="1" end="${amount }" varStatus="status">
	    <div class="form-group">
	      <label class="control-label">源文件名</label>
	      <input type="text" class="form-control" data-rule-required="true" name="fileName${status.index }" >
	     </div>
	     <div class="form-group">
	      <label class="control-label">所属用户</label>
	      <select class="form-control" data-rule-required="true" name="userId${status.index }">
			  <option value="">请选择所属用户</option>
			  <c:forEach items="${userList }" var="user">
			     <option value="${user.userId }">${user.username }</option>
			  </c:forEach>
			</select>
	     </div>
	     <div class="form-group-separator" style="margin-top:10px;"></div>
    </c:forEach>
     <div class="form-group has-error"><label class="control-label"  id="dataFilePrompt"></label></div>
     <button class="btn btn-primary" type="submit">保存</button>
   </form>
</div>
<script type="text/javascript">
$(function(){
	$("#dataFileUploadForm").validate({
		errorElement: 'label',
        errorClass: 'control-label',
		submitHandler:function(form) {
			var fileName="";
			var userId="";
			$("input[name^='fileName']").each(function(i,dom){
				fileName+=$(dom).val()+",";
				userId+=$("select[name='userId"+(i+1)+"']").val()+",";
			});
			if(fileName.length>0){
				fileName=fileName.substring(0,fileName.length-1);
				userId=userId.substring(0,userId.length-1);
			}
            $(form).ajaxSubmit({
            	url:"dataFile/save",
            	type:"POST",
            	data:{fileName:fileName,userId:userId},
            	success:function(responseText){
                   /*  if(responseText.length>0){
                    	var notExistFile=responseText.split(",");
                    	for(var i=0;i<notExistFile.length;i++){
                    		alert("input[value='"+notExistFile[i]+"']");
                    		alert($("input[value='"+notExistFile[i]+"']").length());
                    		$("input[value='"+notExistFile[i]+"']").parent().addClass("has-error").append('<label class="control-label">文件不存在</label>');
                    	}
                    }  */
                    if(responseText.length>0){
                        $("#dataFilePrompt").html(responseText+"文件不存在，其余文件上传成功！").show();
                    }else{
                        $("#dataFilePrompt").html("保存成功!").show();
                    }
                }
            });
         }
	});
});
</script>
