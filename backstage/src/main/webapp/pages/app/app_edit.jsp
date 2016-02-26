<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- Basic Setup -->
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">添加应用</h3>
    </div>
    <div class="panel-body">
        <form role="form" class="form-horizontal" id="appForm">
            <input type="hidden" value="${app.appId }">
            <div class="form-group">
                <label class="col-sm-2 control-label">应用名称<font color="red">*</font></label>
                
                <div class="col-sm-10">
                    <input type="text" class="form-control" data-rule-required="true" data-rule-appNameExist="true" name="appName" value="${app.appName }" placeholder="应用名称">
                </div>
            </div>
            
            <div class="form-group">
                <label class="col-sm-2 control-label">英文名称</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="englishName" value="${app.englishName }" placeholder="英文名称">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">软件地址<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="address" value="${app.address }" placeholder="软件地址" data-rule-required="true">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">所属公司<font color="red">*</font></label>
                
                <div class="col-sm-10">
                    <select class="form-control" name="companyId"  value="${app.companyId }" data-rule-required="true">
                        <option value="" selected="selected">--请选择--</option>
                        <c:forEach items="${companyList }" var="company">
                            <option value="${company.companyId }">${company.companyName }</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">推荐标识</label>
                
                <div class="col-sm-10">
                    <label class="radio-inline">
                        <input type="radio" name="flag" value="1" checked>
                        推荐
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="flag" value="0">
                        不推荐
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">APP分类<font color="red">*</font></label>
                
                <div class="col-sm-10">
                <c:forEach items="${classifyList }" var="classify">
                    <label class="checkbox-inline">
                        <input type="checkbox" name="classifyIds" value="${classify.classifyId }" checked data-rule-required="true">
                                            ${classify.classifyName }
                    </label>
                </c:forEach>
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <label class="col-sm-2 control-label">直接运行数据<font color="red">*</font></label>
                
                <div class="col-sm-10">
                    <label class="radio-inline">
                        <input type="radio" name="runData" value="0" checked data-rule-required="true">
                        可以
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="runData" value="1">
                        不可以
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">最少数据个数<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="dataNum" placeholder="最少数据个数"  data-rule-required="true" data-rule-digits="true">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" >运行命令<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="command" placeholder="perl &hellip;" data-rule-required="true">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" >报告标题</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="title" placeholder="dataKey\tfileName\t&hellip;">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">运行完成后调用方法名称<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="method" placeholder="" data-rule-required="true">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" >同时运行任务数限制<font color="red">*</font></label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="maxTask" placeholder="0：表示无限制" data-rule-required="true"  data-rule-digits="true">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">运行方式<font color="red">*</font></label>
                
                <div class="col-sm-10">
                    <select class="form-control" name="runType" data-rule-required="true">
                        <option value="0">直接运行</option>
                        <option value="1">需添加然后运行</option>
                        <option value="2">可直接运行可添加</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">应用权限<font color="red">*</font></label>
                
                <div class="col-sm-10">
                    <label class="radio-inline">
                        <input type="radio" name="attribute" value="1" checked data-rule-required="true">
                        私有
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="attribute" value="0">
                        共有
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">运行时是否有参数<font color="red">*</font></label>
                
                <div class="col-sm-10">
                    <label class="radio-inline">
                        <input type="radio" name="param" value="1" checked data-rule-required="true">
                        有
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="param" value="0">
                        无
                    </label>

                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">数据类型<font color="red">*</font></label>
                
                <div class="col-sm-10">
                <c:forEach items="${fileFormatList }" var="fileFormat">
                    <label class="checkbox-inline">
                        <input type="checkbox" name="fileFormatIds" value="${fileFormat.formatId }" checked data-rule-required="true">
                                            ${fileFormat.formatDesc }
                    </label>
                </c:forEach>
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <label class="col-sm-2 control-label" >简介</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" name="intro" value="${app.intro }" placeholder="APP简介&hellip;">
                    <p class="help-block">一句话描述这个APP的功能.</p>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" >详细描述</label>
                
                <div class="col-sm-10">
                    <textarea class="form-control" cols="5" id="editordescription" name="description"></textarea>
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <label class="col-sm-2 control-label" for="field-4">APP图标</label>
                
                <div class="col-sm-10">
                    <div class="pictureName-input-hidden"><input type="hidden" name="pictureName" value="${app.pictureName }" data-rule-required="true"></div>
                    <div class="img-thumbnail-inline"><c:if test="${not empty app.pictureName ||fn:trim(app.pictureName)!='' }"><img src="app/icon?file=${app.pictureName }" class="img-thumbnail" style="height: 60px; margin-right: 10px;"></c:if></div>
                    <img id="pictureNameUploading" class="img-thumbnail hide" style="height: 60px;margin-right: 10px;" src="images/icon/loading.jpg">
                    <a class="btn btn-default btn-lg" id="uploadPictureNameBtn">
                        <i class="fa fa-plus"></i>
                    </a>
                    <div id="pictureNameContainer"></div>
                </div>
            </div>
            <div class="form-group hide" id="screen-group">
                <label class="col-sm-2 control-label" for="field-4">APP报告截图</label>
                
                <div class="col-sm-10">
                    <img id="screenUploading" class="img-thumbnail hide" style="height: 60px; margin-right: 10px;"
                         src="images/icon/loading.jpg">
                     <a class="btn btn-default btn-lg" id="uploadScreenBtn">
                         <i class="fa fa-plus"></i>
                     </a>
                     <div id="screenContainer"></div>
                </div>
            </div>
            <div class="form-group has-error" id="warning-group" style="color: #a94442">
                <label class="col-sm-2 control-label">WARNING：</label>
                <div class="col-sm-9">
                    <p class="form-control-static">Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <label class="col-sm-2 control-label" >文档介绍</label>
                
                <div class="col-sm-10">
                    <textarea class="form-control" cols="5" id="editorappDoc" name="appDoc"></textarea>
                </div>
            </div>
            <div class="form-group-separator"></div>
            <div class="form-group">
                <div class="col-sm-12 text-center">
                    <button type="submit" class="btn btn-success">添加</button>
                    <button type="reset" class="btn btn-white">重置</button>
                </div>
            </div>
        </form>
        
    </div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/plugins/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript">
$(function(){
    CKEDITOR.replace( 'editordescription');
    CKEDITOR.replace( 'editorappDoc');
    $("#appForm").validate({
        submitHandler:function(form) {
            $("#editordescription").val(CKEDITOR.instances.editordescription.getData());
            $("#editorappDoc").val(CKEDITOR.instances.editorappDoc.getData());
            
            $(form).ajaxSubmit({
                url:"app/save",
                type:"POST",
                success:function(responseText){
                    if(responseText>0){
                        alert("成功");
                        
                    }
                }
            });   
         }
    });
});
</script>
