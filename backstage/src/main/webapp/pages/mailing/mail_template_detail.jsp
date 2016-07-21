<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">修改邮件模板</h3>
        <div class="alert alert-dismissible hide" id="mail-alert">
          <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <span id="mail-info"></span>
        </div>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="emailTemplateForm">
            <input type="hidden" name="id" value="${emailTemplate.id }">
            <div class="form-group">
                <label class="col-sm-2 control-label">模板名称</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="name" value="${emailTemplate.name }" required>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">模板方法</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="method" value="${emailTemplate.method }" required>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">模板主题</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="title" value="${emailTemplate.title }" required>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" >模板内容</label>
                <div class="col-sm-10">
                    <textarea class="form-control" cols="5" id="editor-context" name="context">${emailTemplate.context }</textarea>
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <div class="col-sm-10 text-center">
                    <a id="commit-emailtemplate" class="btn btn-success">保存</a>
                    <button type="reset" class="btn btn-white">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>