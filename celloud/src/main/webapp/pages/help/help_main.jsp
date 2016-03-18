<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="<%=request.getContextPath()%>/js/help.js?version=1.0" type="text/javascript"></script>
<link href="<%=request.getContextPath()%>/css/help.css" rel="stylesheet" type="text/css" />
<section class="content-header">
  <h1>
    <small></small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="#"><i class="fa fa-dashboard"></i>帮助</a></li>
    <li class="active">用户指南</li>
  </ol>
</section>
<!-- Main content -->
<section class="content">
  <div class="col-sm-2">
      <div class="btn-group-vertical" style="width:100%;padding-right:0px;padding-left:0px;">
         <!-- <a href="javascript:userHelp.openPage('pages/help/help_main.jsp')" class="btn btn-success bg-green-active">
            用户指南
        </a>
        <a href="javascript:userHelp.openPage('pages/help/help_usefast.jsp')" class="btn btn-success">
            快速上手
        </a> -->
        <a href="javascript:userHelp.openPage('pages/help/help_question.jsp')" class="btn btn-success">
            常见问题
        </a>
      </div>
      <!-- <div class="input-group" style="margin-top:5px;">
        <input type="text" class="form-control">
        <span class="input-group-btn">
          <button class="btn btn-success btn-flat" type="button">搜索</button>
        </span>
      </div> -->
  </div>
  <div class="col-xs-10 pull-right" style="padding-left:0px;font-size:12px; font-color:#8A8A8A;">
    <div class="box box-success">
        <div class="box-header with-border" style="font-size:12px; font-color:#8A8A8A;">
          
        </div><!-- /.box-header -->
        <div class="box-body">
          <ul class="help-list">
            <li>CelLoud简介</li>
            <li>如何使用CelLoud
              <ul>
                <li>上传数据</li>
                <li>添加任务</li>
                <li>使用APP</li>
                <li>查看报告</li>
              </ul>
            </li>
            <li>充值和计费</li>
            <li>服务限制</li>
            <li>CelLoud</li>
            <li>CelLoud</li>
          </ul>
        </div>
      </div>
  </div>
</section><!-- /.content -->