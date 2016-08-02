<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">修改公告信息</h3>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="noticeForm">
            <input type="hidden" name="noticeId" value="${notice.noticeId }">
            <div class="form-group">
                <label class="col-sm-2 control-label"><font color="red">注意</font></label>
                <div class="col-sm-10">
                	<span class="alert alert-danger">如果是测试，请只选择测试账号!!!</span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="noticeTitle">操作</label>
                <div class="col-sm-10">
                	<button type="button" class="btn btn-success" onclick="mailing.checkAll(1)">全选</button>
                	<button type="button" class="btn btn-success" onclick="mailing.checkAll(0)">全不选</button>
                	<button type="button" class="btn btn-success" onclick="mailing.checkAll(2)">反选</button>
                	<span class="alert alert-danger hide" id="userInfo"></span>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="noticeTitle">接收者<font color="red">*</font></label>
                <div class="col-sm-10">
                	<input type="hidden" name="usernames" id="hideUserNames">
               		<select id="userList" style="width: 100%" multiple="multiple">
					</select>
                </div>
            </div>
           	<br>
           	<br>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="noticeTitle">公告标题<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" data-rule-maxlength="50" id="noticeTitle" name="noticeTitle" value="${notice.noticeTitle }" placeholder="公告标题">
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-2 control-label" for="noticeContext">公告内容</label>
                <div class="col-sm-10">
                    <textarea class="form-control" rows="3" name="noticeContext" id="noticeContext">${notice.noticeContext }</textarea>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
$(function(){
  	$("#userInfo").addClass("hide");
	//请求用户数据, 添加到select2标签中
	var url = "${pageContext.request.contextPath}/mailing/getUser";
	$.ajax({
		url : url,
		type : 'post',
		dataType : 'text',
		success : function(data) {
			// 从后台获取json数据
			var json = eval('(' + data + ')');
			$("#userList").select2({
				data : json,
				placeholder : '请选择用户',
				allowClear : false,
				minimumResultsForSearch:-1
			})
		},
		error : function(data) {

		},
	});
	CKEDITOR.replace( 'noticeContext');
	$("#noticeForm").validate({
		submitHandler:function(form) {
		  	var users = '';
		  	$('#userList').find("option:selected").each(function(){
		  	  users += $(this).text()+",";
		  	});
		  	if(users.length==0){
		  	  $("#userInfo").removeClass("hide");
		  	  $("#userInfo").html("请选择用户");
		  	  return ;
		  	}
		  	$("#userInfo").addClass("hide");
	  	  	$("#userInfo").html("");
		  	$("#hideUserNames").val(users.substring(0,users.length-1));
			$("#noticeContext").val(CKEDITOR.instances.noticeContext.getData());
            $(form).ajaxSubmit({
            	url:"notice/save",
            	type:"POST",
            	success:function(responseText){
                    if(responseText>0){
                        $("#notice-editModal").modal("hide");
                        alert("成功");
                        
                    }
                }
            });   
         }
	});
	$('#notice-editModal').on('hidden.bs.modal', function (e) {//此事件在模态框被隐藏（并且同时在 CSS 过渡效果完成）之后被触发。
		notice.getNoticeList(notice.currentPage);
	});
});
</script>
