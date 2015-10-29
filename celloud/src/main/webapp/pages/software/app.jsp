<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>应用市场</title>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css"> 
    <link href="//cdn.bootcss.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">    
  	<link href="<%=request.getContextPath() %>/plugins/select/select2.css" rel="stylesheet"/>
    <link href="<%=request.getContextPath() %>/dist/css/celloud.css?version=1.0" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <div class="wrapper">
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            <small>&nbsp;</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>应用市场</a></li>
            <li class="active">概况</li>
          </ol>
        </section>
        <!-- Main content -->
        <section class="content">
          <div class="row">
             <div class="col-xs-12">
              <div class="app-box box-success color-palette-bo">
                <div class="app-box-body bg-green">
                  <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                      <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                      <li data-target="#carousel-example-generic" data-slide-to="1" class=""></li>
                      <li data-target="#carousel-example-generic" data-slide-to="2" class=""></li>
                    </ol>
                    <div class="carousel-inner">
                      <div class="item active">
                        <img style="height:200px;width:100%	" src="http://placehold.it/900x500/00a65a/text=I+Love+Bootstrap" alt="First slide">
                        <div class="carousel-caption">
                          First Slide
                        </div>
                      </div>
                      <div class="item">
                        <img style="height:200px;width:100%	" src="http://placehold.it/900x500/00a65a/ffffff&text=I+Love+Bootstrap" alt="Second slide">
                        <div class="carousel-caption">
                          Second Slide
                        </div>
                      </div>
                      <div class="item">
                        <img style="height:200px;width:100%	" src="http://placehold.it/900x500/00a65a/ffffff&text=I+Love+Bootstrap" alt="Third slide">
                        <div class="carousel-caption">
                          Third Slide
                        </div>
                      </div>
                    </div>
                    <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                      <span class="fa fa-angle-left"></span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                      <span class="fa fa-angle-right"></span>
                    </a>
                  </div>
                  <button class="btn btn-flat" id="toAllApp" onclick="getAppClassify(1)">全部APP</button>
	              <button class="btn bg-green-active btn-flat" id="toMyApp" onclick="toMyAppList()">已添加APP</button>
                </div><!-- /.box-body -->
              </div>
              <div style="padding-bottom:20px;" id="appClassify">
              </div>
              <div id="appDetail" class="row">
              </div>
            </div>
            </div>
          </div><!--/.row-->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
    </div><!-- ./wrapper -->
    <!-- All Modal -->
    <div class="modal modal-green-header" id="appDetailModel">
      <div class="modal-dialog app-modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">APP详情</h4>
          </div>
          <div class="modal-body row app-modal-body" id="modelAppDetail">
            <div class="col-xs-12">
	            <h5>已选数据(点击文件名称取消选择)</h5>
	            <ul class="list-inline" id="addedDataUl"></ul>
            </div>
            <div class="col-xs-12">
	            <h5>可选App(点击选中)</h5>
	            <ul class="list-inline" id="appsForDataUl"></ul>
            </div>
            <div class="col-xs-12">
	            <div class="alert alert-warning-cel alert-dismissable hide" id="runErrorDiv">
	               <button type="button" class="close" onclick="okToRun(1)"><i class="fa fa-check"></i></button>
	               <button type="button" class="close" onclick="okToRun(0)"><i class="fa fa-close"></i></button>
	               <h5><i class="icon fa fa-warning"></i> <span id="runErrorTitle">Alert!</span></h5>
	               <span id="runError"></span>
	             </div>
             </div>
          </div>
          <div class="modal-footer">
<!--             <button type="button" class="btn btn-celloud-close btn-flat pull-left" data-dismiss="modal">关闭</button> -->
<!--             <button type="button" class="btn btn-celloud-success btn-flat" onclick="toRunApp()" id="toRunApp" disabled="disabled">运行</button> -->
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="//cdn.bootcss.com/fastclick/1.0.6/fastclick.min.js"></script>
    <script src="<%=request.getContextPath() %>/dist/js/celloud.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/plugins/spin.min.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/js/app.js" type="text/javascript"></script>
	<!-- spin:loading效果 end-->
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
  </body>
</html>