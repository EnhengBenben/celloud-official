<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="col-sm-2 panel-title">报告打印模板PDF</h3>
        <div id="alert-tips" class="col-sm-9 alert alert-info alert-dismissible pull-right hide" role="alert">
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		  <span id="alert-info"></span>
		</div>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="companyForm">
            <div class="form-group">
                <label class="col-sm-2 control-label">公司名称<font color="red">*</font></label>
                <div class="col-sm-10">
                    <select id="company-id-select" class="form-control col-sm-5" data-rule-required="true" data-msg-required="该项为必选项，请选择。" multiple >
                        <c:forEach items="${cList }" var="company">
                            <option value="${company.companyId }">${company.companyName }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="englishName">文件<font color="red">*</font></label>
                <div class="col-sm-10">
                    <div id="plupload-content" class="box-body plupload-content">
		                <div class="upload-text">
		                    <i class="celicon -uploadgray"></i>拖拽文件到此或者点击选择文件上传
		                </div>
		            </div>
		            <table class="table table-bordered hide" id="upload-list-table">
		                <thead>
		                    <tr>
		                        <th>文件名</th>
		                        <th>上传进度</th>
		                        <th>操作</th>
		                    </tr>
		                </thead>
		                <tbody id="upload-list-tbody">
		                </tbody>
		            </table>
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <div class="col-sm-10 text-center">
                    <a id="upload-pdf-a" class="btn btn-success">上传</a>
                    <button type="reset" class="btn btn-white">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>
