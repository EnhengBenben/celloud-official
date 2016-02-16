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
                <label class="col-sm-2 control-label" for="noticeTitle">公告标题<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" data-rule-maxlength="50" id="noticeTitle" name="noticeTitle" value="${notice.noticeTitle }" placeholder="公告标题">
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-2 control-label" for="noticeContext">公告内容<font color="red">*</font></label>
                <div class="col-sm-10">
                    <div class="btn-toolbar" data-role="editor-toolbar" data-target="#editor">
				      <div class="btn-group">
				        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font"><i class="icon-font"></i><b class="caret"></b></a>
				          <ul class="dropdown-menu">
				          </ul>
				        </div>
				      <div class="btn-group">
				        <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font Size"><i class="icon-text-height"></i>&nbsp;<b class="caret"></b></a>
				          <ul class="dropdown-menu">
				          <li><a data-edit="fontSize 5"><font size="5">Huge</font></a></li>
				          <li><a data-edit="fontSize 3"><font size="3">Normal</font></a></li>
				          <li><a data-edit="fontSize 1"><font size="1">Small</font></a></li>
				          </ul>
				      </div>
				      <div class="btn-group">
				        <a class="btn" data-edit="bold" title="Bold (Ctrl/Cmd+B)"><i class="icon-bold"></i></a>
				        <a class="btn" data-edit="italic" title="Italic (Ctrl/Cmd+I)"><i class="icon-italic"></i></a>
				        <a class="btn" data-edit="strikethrough" title="Strikethrough"><i class="icon-strikethrough"></i></a>
				        <a class="btn" data-edit="underline" title="Underline (Ctrl/Cmd+U)"><i class="icon-underline"></i></a>
				      </div>
				      <div class="btn-group">
				        <a class="btn" data-edit="insertunorderedlist" title="Bullet list"><i class="icon-list-ul"></i></a>
				        <a class="btn" data-edit="insertorderedlist" title="Number list"><i class="icon-list-ol"></i></a>
				        <a class="btn" data-edit="outdent" title="Reduce indent (Shift+Tab)"><i class="icon-indent-left"></i></a>
				        <a class="btn" data-edit="indent" title="Indent (Tab)"><i class="icon-indent-right"></i></a>
				      </div>
				      <div class="btn-group">
				        <a class="btn" data-edit="justifyleft" title="Align Left (Ctrl/Cmd+L)"><i class="icon-align-left"></i></a>
				        <a class="btn" data-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i class="icon-align-center"></i></a>
				        <a class="btn" data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i class="icon-align-right"></i></a>
				        <a class="btn" data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i class="icon-align-justify"></i></a>
				      </div>
				      <div class="btn-group">
				          <a class="btn dropdown-toggle" data-toggle="dropdown" title="Hyperlink"><i class="icon-link"></i></a>
				            <div class="dropdown-menu input-append">
				                <input class="span2" placeholder="URL" type="text" data-edit="createLink"/>
				                <button class="btn" type="button">Add</button>
				        </div>
				        <a class="btn" data-edit="unlink" title="Remove Hyperlink"><i class="icon-cut"></i></a>
				
				      </div>
				      
				      <div class="btn-group">
				        <a class="btn" title="Insert picture (or just drag & drop)" id="pictureBtn"><i class="icon-picture"></i></a>
				        <input type="file" data-role="magic-overlay" data-target="#pictureBtn" data-edit="insertImage" />
				      </div>
				      <div class="btn-group">
				        <a class="btn" data-edit="undo" title="Undo (Ctrl/Cmd+Z)"><i class="icon-undo"></i></a>
				        <a class="btn" data-edit="redo" title="Redo (Ctrl/Cmd+Y)"><i class="icon-repeat"></i></a>
				      </div>
				      <input type="text" data-edit="inserttext" id="voiceBtn" x-webkit-speech="">
				    </div>
                    <textarea class="form-control" rows="3" name="noticeContext" id="noticeContext"></textarea>
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
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/bootstrap-wysiwyg/bootstrap-wysiwyg.js"></script>
<script type="text/javascript">
$(function(){
	$('#noticeContext').wysiwyg();
	$("#noticeForm").validate({
		submitHandler:function(form) {      
            $(form).ajaxSubmit({
            	url:"notice/save",
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
