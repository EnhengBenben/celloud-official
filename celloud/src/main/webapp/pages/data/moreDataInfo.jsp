<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="hide" id="strainListHide">
${data.strainList }
</div>
<input type="hidden" name="dataIds" value="${data.fileId}" />
<div class="form-group">
	<div class="control-label form-label col-xs-3">文件别名</div>
	<div class="col-xs-9">
		<input type="text" name="data.anotherName" value="${data.anotherName}" onkeyup="value=value.replace(/[^\u4E00-\u9FA5\w]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\u4E00-\u9FA5\w]/g,''))" placeholder="请输入字母\数字\下划线\汉字"/>
	</div>
</div>
<div class="form-group">
	<div class="control-label form-label col-xs-3">数据标签</div>
	<div class="col-xs-9">
		<input type="text" name="data.dataTags" value="${data.dataTags}" />
	</div>
</div>
<div class="form-group">
	<div class="control-label form-label col-xs-3">样本</div>
	<div class="col-xs-9">
		<input type="text" name="data.sample" value="${data.sample}" maxlength="45"/>
	</div>
</div>
<div class="form-group">
	<div class="control-label form-label col-xs-3">样本类型/物种</div>
	<div class="col-xs-9" id="dataTag">
		<span id="dataMoreInfoStrainSpan">
			<input type="hidden" id="dataStrainHide" name="data.strain" value="${data.strain}" style="width: 270px;"/>
		</span>
 	</div>
</div>
<div class="alert alert-warning-cel alert-dismissable hide" id="updateDataErrorDiv">
   <button type="button" class="close"><i class="fa fa-close"></i></button>
   <h5><i class="icon fa fa-warning"></i>保存失败！</h5>
</div>