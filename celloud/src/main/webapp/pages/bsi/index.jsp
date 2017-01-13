<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ page import="com.celloud.model.mysql.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>CelLoud 控制台</title>
  <meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据,上海华点云生物科技有限公司网站首页,上海华点云生物科技有限公司官网,上海华点云生物科技有限公司北京分公司" />
  <meta name="description" content="一站式高通量基因检测数据分析系统" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link href="<%=request.getContextPath()%>/images/favicon.ico" rel="bookmark">
  <link href="<%=request.getContextPath()%>/images/favicon.ico" rel="shortcut icon">
  <link href="//cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
  <link href="//cdn.bootcss.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet">
  <link href="<%=request.getContextPath()%>/css/celloud.min.css?v=3.3.3" rel="stylesheet">
  <link href="<%=request.getContextPath() %>/css/upload.css" rel="stylesheet" type="text/css">
  <link href="<%=request.getContextPath()%>/css/bsi_main.css?v=0.1" rel="stylesheet" type="text/css" />
  <link href="<%=request.getContextPath()%>/plugins/smartJqueryZoom/zoom-styles.css" rel="stylesheet" type="text/css"/>
  <script src="//cdn.bootcss.com/modernizr/2.8.3/modernizr.min.js"></script>
  <!-- [if It IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]  -->
</head>
<body class="container" ng-app="celloudApp" ng-controller="sidebarController">
	<jsp:include page="../partial/_partial_index_header.jsp"></jsp:include>
	<jsp:include page="../partial/_partial_index_sidebar.jsp"></jsp:include>
	<div class="view-container">
		<div class="pro-body">
			<ol class="breadcrumb">
				<li>CelLoud</li>
				<li>我的产品</li>
				<li>百菌探</li>
			</ol>
			<div class="content">
				<div class="page-layout page-main-content">
					<div class="topbar-menu">
						<header class="common-menu">
							<div class="common-menu-logo">
								<img alt="百菌探" src="<%=request.getContextPath()%>/images/app/bsi.png">
							</div>
							<hr class="-left">
							<div id="common-menu-center" class="info">
								<div id="common-menu" class="common-menu-btn pull-left">
									<!--                     <a class="item-btn" id="to-sample-a" href="javascript:void(0)"><i class="celicon my-sample-icon"></i><br>收样</a> -->
									<a class="item-btn upload-btn" id="to-upload-a" href="javascript:void(0)">
										<i class="celicon my-upload-icon"></i><br>上传
										<canvas id="upload-progress" class="upload-progress" width="64" height="64"></canvas>
									</a>
									<a class="item-btn" id="to-data-a" href="javascript:void(0)">
										<i class="celicon my-data-icon"></i><br>数据
									</a>
									<a class="item-btn active" id="to-report-a" href="javascript:void(0)">
										<i class="celicon my-report-icon"></i><br>报告
									</a>
								</div>
							</div>
							<div id="common-menu-right" class="searchs common-menu-btn">
								<div class="data-search">
									<input id="condition-input" class="input-sm" type="text" placeholder="搜索" />
									<a id="condition-find" class="input-group-btn">
										<i class="fa fa-search"></i>
									</a>
								</div>
							</div>
							<hr class="-right">
						</header>
					</div>
					<div id="container" class="container-fluid common-container"></div>
				</div>
			</div>
			<div id="upload-modal" class="modal upload-modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button id="close-upload-modal" type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">
									<i class="fa fa-times-circle"></i>
								</span>
							</button>
							<h4 class="modal-title">数据上传</h4>
						</div>
						<div class="modal-body row">
							<div class="step-item">
								<div class="step-one active">1</div>
								<hr id="one-to-two">
								<div class="step-two">2</div>
								<hr id="two-to-three">
								<div class="step-three">3</div>
							</div>
							<div class="step-one-content">
								<input id="batch-info" type="text" placeholder="请输入标签" value="" />
								<br>
								<p>
									<span>
										添加数据标签，便于使用报告查询、数据管理、分类汇总等平台功能。
										<span>
								</p>
								<p>
									<span>
										示例：外科5月；ICU-9床；急诊发热
										<span>
								</p>
								<a id="next-step" class="btn" href="javascript:void(0)">下一步</a>
							</div>
							<div class="step-two-content hide">
								<div class="upload-container">
									<div id="plupload-content" class="box-body plupload-content">
										<ul id="upload-filelist" class="plupload-filelist"></ul>
										<div class="upload-text">
											<i class="celicon grey-upload-icon"></i>拖拽文件到此或者点击选择文件上传
										</div>
									</div>
								</div>
<!-- 								<p> -->
<!-- 									<span> -->
<!-- 										<input id="need-split" type="checkbox" value="1" checked="checked"> -->
<!-- 										需要数据拆分 (run: Split) -->
<!-- 									</span> -->
<!-- 								</p> -->
								<p>
									<span>&gt;目前支持 .tar.gz; .zip; 压缩过的fasta 格式的配对序列文件</span>
								</p>
								<p>
									<span>&gt;文件命名规则为  (实验样本编号.tar.gz) 或 (文库编码_R1.tar.gz & 文库编码_R2.tar.gz )</span>
								</p>
<!-- 								<p> -->
<!-- 									<span>&gt;数据拆分(Split) 选中时系统先按index文件对数据拆分并重命名.</span> -->
<!-- 								</p> -->
								<input id="tag-info" type="hidden" value="1" />
								<a id="begin-upload" class="btn" href="javascript:void(0)">开始上传</a>
							</div>
							<div class="step-three-content hide">
								<div class="upload-status">
									数据上传中...数据标签 ：
									<span id="tags-review"></span>
								</div>
								<div class="upload-container">
									<div id="plupload-content" class="box-body plupload-content">
										<ul id="uploading-filelist" class="plupload-filelist">
											<li class="plupload-filelist-header">
												<div class="plupload-file-name">
													<span>文件名</span>
												</div>
												<div class="plupload-file-size">大小</div>
												<div class="plupload-file-surplus">剩余时间</div>
												<div class="plupload-file-status">进度</div>
												<div class="plupload-file-action">操作</div>
											</li>
										</ul>
									</div>
								</div>
								<p>
									<span>文件上传中结束会自动开始分析任务，可在报告页面查看最新报告.</span>
								</p>
								<a id="upload-more" class="btn" href="javascript:void(0)">上传更多</a>
							</div>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<div id="report-uploading-modal" class="modal running-modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">
									<i class="fa fa-times-circle"></i>
								</span>
							</button>
							<h4 class="modal-title">数据上传中</h4>
						</div>
						<div class="modal-body row">
							<h4>数据不完整，来检查您缺哪个数据吧！</h4>
							<div>
								数据运行所需文件如下：<br>
								<span class="highlight-text">public_name_</span>
								R1.fastq<br>
								<span class="highlight-text">public_name_</span>
								R2.fastq<br>
								<span class="highlight-text">public_name_</span>
								.txt
							</div>
							<a class="btn" href="javascript:void(0)" data-dismiss="modal" aria-label="Close">知道了</a>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<div id="running-modal" class="modal running-modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">
									<i class="fa fa-times-circle"></i>
								</span>
							</button>
							<h4 class="modal-title">数据分析中</h4>
						</div>
						<div class="modal-body row">
							<img alt="" src="<%=request.getContextPath()%>/images/icon/in-analysis.gif">
							<h4>报告生成中</h4>
							<div>
								请
								<span class="highlight-text">稍后</span>
								刷新查看
							</div>
							<a class="btn" href="javascript:void(0)" data-dismiss="modal" aria-label="Close">OK,再等等</a>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<div id="running-error-modal" class="modal running-error-modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">
									<i class="fa fa-times-circle"></i>
								</span>
							</button>
							<h4 class="modal-title">异常终止</h4>
						</div>
						<div class="modal-body row">
							<i class="celicon run-error-icon"></i>
							<h4>
								我们抱歉的通知，数据：
								<span id="run-error-data" class="highlight-text"></span>
							</h4>
							<h4>上传（分析）过程中出错</h4>
							<div>请重传（联系service@celloud.cn获取帮助）</div>
							<a class="btn" href="javascript:void(0)" data-dismiss="modal" aria-label="Close">知道了</a>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
		</div>
	</div>
  </div>
  <div id="fullbg"></div> 
  <div id="pageContent" class="pageContent hide">
    <a class="zoomClose" id="closeZoom" ng-click="closeZoom();" style="margin-right: 75px;"></a>
    <img id="imageFullScreen" src="">
  </div>
  <script type="text/javascript">
     window.CONTEXT_PATH = '<%=request.getContextPath()%>';
     window.userId = '<%=((User)request.getSession().getAttribute("loginUserInSession")).getUserId() %>';
  </script>
  <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/sockjs-modified-1.0.0.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/waveLoading.min.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="//cdn.bootcss.com/spin.js/2.3.2/spin.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/3.2.2/echarts.min.js"></script>
  <script src="//cdn.bootcss.com/select2/4.0.3/js/select2.full.min.js" type="text/javascript"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-route.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-resource.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-sanitize.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
  <script src="//cdn.bootcss.com/plupload/2.1.8/plupload.full.min.js"></script>
  <script src="<%=request.getContextPath() %>/plugins/jquery.ba-resize.min.js"></script>
  <script src="<%=request.getContextPath() %>/plugins/smartJqueryZoom/e-smart-zoom-jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/calendar/WdatePicker.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/highcharts/char.js"></script>
  <script src="<%=request.getContextPath()%>/js/main.min.js?version=3.3.10.1"></script>
  <script src="<%=request.getContextPath()%>/js/bsi_main.js?version=3.1.15" type="text/javascript"></script>
  <script src="<%=request.getContextPath() %>/js/bsi_upload.js?version=3.3.3"></script>
  <script src="<%=request.getContextPath()%>/js/charts.js"></script>
</body>
</html>