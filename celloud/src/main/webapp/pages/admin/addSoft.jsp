<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="alert" id="addSoftDivInfo"></div>
<form class="form-horizontal" id="softAddForm">
	<input type="hidden" name="softId" id="softwareIdHidden">
	<input type="hidden" name="editType" id="editTypeHidden" value="0">
	<div class="form-group">
		<label for="softwareName" class="col-sm-2 control-label"><h3 id="appManageInfo">新增软件</h3></label>
		<div class="col-sm-8">
			<a style="float:right" class="btn btn-primary" onclick="javascript:toSoftList();">返回</a>
		</div>
	</div>
	<div class="form-group">
		<label for="softwareName" class="col-sm-2 control-label">软件名称<font color="red">*</font></label>
		<div class="col-sm-8">
			<input type="text" name="soft.softwareName" id="softwareName" class="form-control">
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">英文名称</label>
		<div class="col-sm-8">
			<input type="text" name="soft.englishName" id="englishName" class="form-control">
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">软件入口</label>
		<div class="col-sm-8">
			<input type="text" name="soft.softwareEntry" id="softwareEntry" class="form-control"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">进程名</label>
		<div class="col-sm-8">
			<input type="text" name="soft.processName" id="processName" class="form-control"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">软件部署地址</label>
		<div class="col-sm-8">
			<input type="text" name="soft.host" id="host" class="form-control"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">软件图片<font color="red">*</font></label>
		<div class="col-sm-8">
			<div></div>
			<img alt="软件图片" id="pictureImg" src="" style="width: 48px;height: 48px;">
			<input type="file" name="file" id="uploadSoftImg" onchange="upload(this,0)"/>
			<input type="hidden" name="soft.pictureName" id="pictureName" class="form-control">
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">软件分类<font color="red">*</font></label>
		<div class="col-sm-8" id="appClassifyDiv">
			<input type="hidden" name="soft.classify" id="appClassifyHidden">
			<select id="classifySel1" onchange="javascript:changeClassify();" class="form-control">
			</select>
			<a style="cursor: pointer;" onclick="addClassify();changeClassify();"><img src="<%=request.getContextPath()%>/images/admin/add.png"></a></a>
			<span class="help-inline"></span>
			<br/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">关键词描述<font color="red">*</font></label>
		<div class="col-sm-8">
			<input type="text" name="soft.description" id="description" class="form-control"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">简介<font color="red">*</font></label>
		<div class="col-sm-8">
			<textarea rows="5" cols="20" name="soft.intro" id="intro" class="form-control"></textarea>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">管理员邮箱</label>
		<div class="col-sm-8">
			<input type="text" name="soft.email" id="email" class="form-control"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">应用类型标志位<font color="red">*</font></label>
		<div class="col-sm-3">
			<select name="soft.type" id="typeSel" class="form-control">
				<option value="1">C/S软件</option>
				<option value="2">数据库</option>
				<option value="3" selected="selected">B/S软件</option>
				<option value="4">JS软件</option>
			</select>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">直接运行数据<font color="red">*</font></label>
		<div class="col-sm-8">
			<input type="radio" name="soft.runData" value="0" checked="checked">可以&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="soft.runData" value="1">不可以
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">至少运行数据条数<font color="red">*</font></label>
		<div class="col-sm-8">
			<input type="text" name="soft.dataNum" id="dataNum" class="form-control" value="1"/>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">参数设置<font color="red">*</font></label>
		<div class="col-sm-8">
			<input type="radio" name="soft.param" value="0" checked="checked">不需要&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="radio" name="soft.param" value="1">需要
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">软件截图<font color="red">*</font></label>
		<div class="col-sm-8" id="divScreenShot">
			<input type="hidden" name="soft.screenShot" id="screenShotAllHidden" class="form-control">
			<img alt="软件截图" id="screenImg1" src="" style="width: 100px;height: 100px;">
			<input type="file" name="file" id="uploadScreenshot1" onchange="upload(this,1)" style="width:150px"/>
			<span id="screenNameInfo1" class="screenSpan" style="display:none;"></span>
			<a style="cursor: pointer;"  onclick="addAppFile();changeScreen();"><img src="<%=request.getContextPath()%>/images/admin/add.png"></a></a>
			<input type="hidden" id="screenHidden1"/>
			<span class="help-inline"></span>
			<br/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">运行文件格式<font color="red">*</font></label>
		<div class="col-sm-4" id="formatDiv">
			<input type="hidden" name="soft.formatDesc" id="fileFormatHidden" class="form-control">
			<select id="fileFormat1" onchange="javascript:changeFormat();" class="form-control">
				
			</select>
			<a style="cursor: pointer;"  onclick="javascript:addFileFormat();"><img src="<%=request.getContextPath()%>/images/admin/add.png"></a></a>
			<span class="help-inline"></span>
			<br/>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">数据类型</label>
		<div class="col-sm-4">
			<input type="hidden" name="soft.proDataType" id="proDataTypeHidden">
			<select id="proDataTypeSel" onchange="javascript:changeProDataType();" class="form-control">
				
			</select>
			<span class="help-inline"></span>
			<br/>
		</div>
	</div>
	<div class="form-group">
			<label class="col-sm-2 control-label">所属公司</label>
			<div class="col-sm-8">
				<select name="soft.companyId" class="form-control">
					<option value="0">--请选择--</option>
					<s:iterator value="companyList" id="st">
						<option value="<s:property value="#st.companyId"/>"><s:property value="#st.companyName"/></option>
					</s:iterator>
				</select>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">文档介绍</label>
		<div class="col-sm-8">
			<textarea name="soft.appDoc" id="myEditor"></textarea>
			<span class="help-inline"></span>
		</div>
	</div>
	<div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
		    <a href="javascript:void(0);" class="btn btn-primary" onclick="javascript:addSoft();">保存</a>
			<a href="javascript:void(0);" class="btn" onclick="javascript:toSoftList();">取消</a>
	    </div>
	</div>
</form>
<!-- <div style="margin-left: 180px;"> -->
	
<!-- </div> -->
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
	initAddSoft();
});
editor = new UE.ui.Editor({initialFrameWidth:900,initialFrameHeight:300});
editor.render("myEditor");
</script>