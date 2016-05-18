<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 优先使用最新版本的IE 和 Chrome 内核 -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="baidu-site-verification" content="IsldTuHqik" />
<title>CelLoud </title>
<meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据,上海华点云生物科技有限公司网站首页,上海华点云生物科技有限公司官网,上海华点云生物科技有限公司北京分公司" />
<meta name="description" content="一站式高通量基因检测数据分析系统" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/upload.css" rel="stylesheet" type="text/css">
<link href="<%=request.getContextPath()%>/css/bsi_main.css" rel="stylesheet" type="text/css" />
</head>
<body>
  <div class="page-layout page-main-content">
    <div class="topbar-menu">
      <div class="global-topbar">
        <div class="y-topbar-row" style="position: static; opacity: 1;">
          <div class="y-topbar-span3">
            <a href="/"><i class="celicon home-icon"></i><span>首页</span></a>
            <hr>
          </div>
          <div class="y-topbar-span9 y-last">
            <a href="logout" title="点击退出"><i class="celicon user-icon"></i></a>
          </div>
        </div>
      </div>
      <div class="common-menu">
        <div class="common-menu-inner">
          <div class="common-menu-logo pull-left">
            <a href="javascript:void(0)" class="bio-logo">
              <img src="<%=request.getContextPath()%>/images/app/bactive.png">
	        </a>
	        <div class="bio-name">
	          <div>百菌探</div>
	          <div>Bactive</div>
	          <a href="javascript:void(0)">详情>>></a>
	        </div>
          </div>
          <hr>
          <div class="common-menu-btn pull-left">
            <a class="item-btn active" id="to-report-a" href="javascript:void(0)"><i class="celicon my-report-icon"></i><br>报告</a>
            <a class="item-btn" id="to-data-a" href="javascript:void(0)"><i class="celicon my-data-icon"></i><br>数据</a>
            <a class="item-btn" id="to-upload-a" href="javascript:void(0)"><i class="celicon my-upload-icon"></i><br>上传</a>
          </div>
          <hr>
          <div class="common-menu-search pull-right">
			<div class="data-search">
			  <input id="condition-input" class="input-sm" type="text" placeholder="搜索"/>
			  <div id="condition-find" class="input-group-btn"><i class="fa fa-search"></i></div>
			</div>
          </div>
        </div>
      </div>
    </div>
    <div id="container" class="container">
     
    </div>
  </div>
  <div id="upload-modal" class="modal upload-modal">
	<div class="modal-dialog">
	  <div class="modal-content">
	    <div class="modal-header">
	      <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	      <h4 class="modal-title">数据上传</h4>
	    </div>
	    <div class="modal-body row">
	      <div class="step-item">
		    <div class="step-one active">1</div><hr class="active">
		    <div class="step-two">2</div><hr>
		    <div class="step-three">3</div>
	      </div>
	      <div class="step-one-content">
	        <input id="batch-info" type="text" placeholder="请输入标签" value=""/><br>
	        <p><span>为每次上传数据按输入加入标签，提升后续报告查询、数据管理、分类汇总提供快捷服务。<span></p>
	        <p><span>示例：外科5月；ICU-9床；急诊发热<span></p>
	      </div>
	      <div class="step-two-content hide">
		      <input id="need-split" type="checkbox" value="1" checked="checked">需要数据拆分 (run: Split)
		      <input id="tag-info" type="hidden" value="1"/>
	      </div>
	      <div id="upload-content" class="upload-content upload-step-one hide">
	        <div id="upload_container" class="wholeheight">
              <div class="box-header">
                <div class="plupload_header_content">
                  <div class="plupload_file_name">
                    <div class="box-title plupload_buttons">
                      <a href="javascript:void(0)" class="celicon addfile" id="addfile_browse"></a>
                    </div>
                  </div>
                </div>
              </div>
              <div id="plupload-content" class="box-body plupload_content">
                <ul id="upload_filelist" class="plupload_filelist"></ul>
              </div>
            </div>
	      </div>
	    </div>
	  </div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<script src="//cdn.bootcss.com/plupload/2.1.8/plupload.full.min.js"></script>
<script src="//cdn.bootcss.com/plupload/2.1.8/i18n/zh_CN.js"></script>
<script src="//cdn.bootcss.com/swfobject/2.2/swfobject.min.js"></script>
<script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
<script src="<%=request.getContextPath()%>/plugins/sockjs-modified-1.0.0.js"></script>
<script src="<%=request.getContextPath()%>/js/utils.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/bsi_main.js" type="text/javascript"></script>
<script src="<%=request.getContextPath() %>/js/bsi_upload.js"></script>
<script src="<%=request.getContextPath()%>/js/charts.js"></script>
</body>
</html>