<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<table style="width: 100%">
   <tr>
       <td id="_linTable" align="center">
           <div id="fileUploadDiv">
               <input type="file" name="fileUpload" id="fileUpload"/>
           </div>
       </td>
       <td id="_linAlert" style="display: none;">
           <span style="font-size: 15px;color: #C30">您浏览器的flash版本过低，请点击</span> 
           <span style="font-size: 13px;color: #69C"><a target='blank' href='http://get.adobe.com/cn/flashplayer/'>http://get.adobe.com/cn/flashplayer/</a></span> 
           <span style="font-size: 15px;color: #C30">下载安装最新的flash插件。</span>
           <div style="font-size: 15px;color: #C30">安装成功后请刷新此页面。</div>
       </td>
   </tr>
   <tr>
      <td id="progressTd" style="display:none;" colspan="2" align="center">
          <input type="button" style="width:100px" class="btn" value="全部上传" onclick="javascript:checkTotalSize()">
          <input type="button" style="width:100px" class="btn" id="delUploadRecord" value="删除记录" >
          <div id="progress">
              
          </div>
      </td>  
   </tr>
   <tr>
      <td colspan="2" align="center" id="alertId">
        
      </td>
   </tr>
</table>
<!-- 修改文件别名 -->
<div class="modal fade hide" id="anotherNameModal">
    <div class="modal-header">
        <a class="close" data-dismiss="modal" id="closeModal">×</a>
        <h3><img src="<%=request.getContextPath()%>/images/publicIcon/icon-modal-03.png"/>修改文件别名</h3>
    </div>
    <div class="modal-body" align="center">
        <table class="table table-tab table-bordered table-collapse" style="width:80%;margin-top:10px">
            <thead>
                <tr>
                    <th>文件名称 </th>
                    <th>文件别名</th>
                </tr>
            </thead>
            <tbody id="anotherNameBody">
            </tbody>
        </table>
        <div id="saveAnotherNameDiv" style="margin-left: 70px;margin-top: 20px;"></div>
    </div>
    <div class="modal-footer">
        <a href="javascript:void();" class="btn btn-green btn-small" id="saveAnotherName">确 定</a>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	initUploadDetail();
});
</script>