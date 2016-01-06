<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link href="<%=request.getContextPath() %>/plugins/plupload-2.1.2/jquery.plupload.queue/css/jquery.plupload.queue.css?version=1.4" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath() %>/css/upload.css?v=3.0" rel="stylesheet" type="text/css">
<input type="hidden" id="fileDataSessionUserIdHidden" value="${loginUserInSession.userId }">
<input type="hidden" id="fileDataSessionUserIdHidden" value="${loginUserInSession.navigation }">
<section class="content-header">
  <h1>
    <small>&nbsp;</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:void(0)"><i class="fa fa-sellsy"></i> 数据上传</a></li>
    <li class="active">全部</li>
  </ol>
</section>
<section class="content">
  <div class="row">
    <div class="col-xs-12">
      <div class="box box-success" id="fileUploadDiv">
      </div><!-- /.box -->
    </div>
  </div><!--/.row-->
</section><!-- /.content -->  
<div style="display: none;" id="_sessionTimeOut">
</div>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/plupload.full.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/jquery.plupload.queue/jquery.plupload.queue.js?version=1.3"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/plupload.dev.js?version=1.7"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/plupload-2.1.2/i18n/zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/upload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/file_upload.js"></script>
