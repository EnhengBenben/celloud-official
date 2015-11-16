<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<table class="table table-tab table-bordered">
	<thead>
    	<tr>
        	<th colspan="6" class="green">
        	<label for="fileNum" style="font-weight: bold;">
        		第一步：请输入上传文件个数：&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fileNum" id="fileNumber">
        		<a onclick="javascript:submitFileNum();" class="btn btn-primary" style="margin-bottom: 9px;font-weight: normal;">提交</a>
        		<a onclick="javascript:clearPage();" class="btn btn-primary" style="margin-bottom: 9px;font-weight: normal;">清空</a>
        		<span id="fileNumWarn" style="color: red;font-weight: normal;"></span>
        	</label>
        	</th>
        </tr>
    </thead>
</table>
<table class="table table-tab table-bordered" id="dataKeyTable" style="display: none;">
	<thead>
    	<tr>
        	<th colspan="6" class="green">第二步：将文件复制到/share/data/file/目录。</th>
        </tr>
    </thead>
</table>
<table class="table table-tab table-bordered" id="fileDetailTable" style="display: none;">
	<thead>
		<tr>
        	<th colspan="6" id="filePromt" class="green">
        		第三步：文件详细信息设置
        	</th>
        </tr>
        <tr>
    		<td>原文件名<font color="red">*</font></td>
        	<td>
        		<input type="text" id="bigFileName1" maxlength="100" placeholder="最长100个字符">
        		<span id="fileNameWarn1" style="color: red;"></span>
        	</td>
        	<td>所属用户<font color="red">*</font></td>
        	<td>
        		<select id="userNameSel1">
        			
        		</select>
        		<span id="userNameWarn1" style="color: red;"></span>
        	</td>
        </tr>
	</thead>
	<tbody id="fileDetailTbody">
    	
    </tbody>
    <tfoot class="">
    	<tr>
        	<td colspan="6">
	        	<a onclick="javascript:addBigData();" class="btn btn-primary">保存</a>
        	</td>			
        </tr>
    	<tr id="addBigDataPromptTr" style="display: none;">
        	<td colspan="6">
	        	<span id="addBigDataPrompt">&nbsp;</span><br/>
	        	<span id="addBigDataPrompt1">&nbsp;</span>
        	</td>
        </tr>
    </tfoot>
</table>
<script type="text/javascript">
$.ajaxSetup ({
	cache: false //关闭AJAX相应的缓存
});
$(document).ready(function(){
	getUserList();
});
</script>