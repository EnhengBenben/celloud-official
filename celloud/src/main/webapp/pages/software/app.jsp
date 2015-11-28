<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="#"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>应用市场</a></li>
    <li class="active">概况</li>
  </ol>
</section>
   <section class="content">
     <div class="row">
        <div class="col-xs-12">
          <div class="box box-success color-palette-bo">
		<div class="box-body bg-green" style="padding-left:30px;padding-bottom: 0px;">
		  <h3 style="font-family:黑体;color:#FFFFFF">
		  	应用市场
		  </h3>
		  <p style="font-family:黑体;color:#FFFFFF">这里有所有您需要的APP，可以添加运行文件的，也有可以直接运行数据的小软件。
		  	<span class="text-yellow" style="font-size:14px;margin-left:10px">默认按照APP分析功能分类，可以根据您的需要选择</span>
		  </p>
		  <button class="btn btn-success btn-flat bg-green-active" style="width:150px;height:70px;margin-right:0px" id="toAllApp" onclick="getAppClassify(1)">全部APP</button>
		     <button class="btn btn-success btn-flat" style="width:150px;height:70px;margin-right:0px" id="toMyApp" onclick="toMyAppList()">已添加APP</button>
		    </div>
		</div>
         <div style="padding-bottom:20px;" id="appClassify">
         </div>
         <div id="appDetail" class="row">
         </div>
       </div>
       </div>
   </section><!-- /.content -->
<!-- All Modal -->
<div class="modal modal-green-header" id="appDetailModel">
  <div class="modal-dialog app-modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">APP详情</h4>
      </div>
      <div class="modal-body row app-modal-body" id="modelAppDetail">
      </div>
      <div class="modal-footer">
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<script src="<%=request.getContextPath()%>/js/app.js?version=1.0" type="text/javascript"></script>
<script type="text/javascript">
	var session_userId = <%=session.getAttribute("userId")%>;
	var sessionUserName = "<%=session.getAttribute("userName")%>";
	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	$(document).ready(function(){
		initApp();
	});
</script>
