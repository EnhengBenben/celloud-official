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
    <link href="<%=request.getContextPath() %>/dist/css/celloud.css?version=1.4" rel="stylesheet" type="text/css" />
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
        <!-- Main content -->
        <section class="content">
          <div class="row">
             <div class="col-xs-10">
              <div class="box box-solid">
                <div class="box-header with-border">
                  <h3 class="box-title">App使用介绍</h3>
                </div><!-- /.box-header -->
                <div class="box-body">
                  <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                      <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                      <li data-target="#carousel-example-generic" data-slide-to="1" class=""></li>
                      <li data-target="#carousel-example-generic" data-slide-to="2" class=""></li>
                    </ol>
                    <div class="carousel-inner">
                      <div class="item active">
                        <img style="height:300px;width:100%	" src="http://placehold.it/900x500/39CCCC/ffffff&text=I+Love+Bootstrap" alt="First slide">
                        <div class="carousel-caption">
                          First Slide
                        </div>
                      </div>
                      <div class="item">
                        <img style="height:300px;width:100%	" src="http://placehold.it/900x500/3c8dbc/ffffff&text=I+Love+Bootstrap" alt="Second slide">
                        <div class="carousel-caption">
                          Second Slide
                        </div>
                      </div>
                      <div class="item">
                        <img style="height:300px;width:100%	" src="http://placehold.it/900x500/f39c12/ffffff&text=I+Love+Bootstrap" alt="Third slide">
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
                </div><!-- /.box-body -->
              </div>
              <ul class="list-inline">
              	<li class="types-options big_item selected">分析功能</li>
              	<li class="types-options big_item">数据功能</li>
              </ul>
              <ul class="list-inline">
              	<li class="types-options second_item selected">全部</li>
              	<li class="types-options second_item">胚胎染色体检测</li>
              	<li class="types-options second_item">癌症肿瘤相关</li>
              	<li class="types-options second_item">新生儿遗传病</li>
              	<li class="types-options second_item">病原检测</li>
              </ul>
              <h4>App分类一<span class="text-green" style="margin-left:10px;"><a href="moreApp.html">more</a></span></h4>
              <div class="col-md-3" style="margin-left:-15px;">
                  <div class="info-box">
                    <span class="info-box-icon bg-green"><i class="fa fa-flag-o"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
              </div><!-- /.col -->
              <div class="col-md-3">
                  <div class="info-box">
                    <span class="info-box-icon bg-red"><i class="fa fa-star-o"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
               </div><!-- /.col -->
               <div class="col-md-3">
                  <div class="info-box">
                    <span class="info-box-icon bg-yellow"><i class="fa fa-calendar"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
                </div><!-- /.col -->
                <div class="col-md-3">
                  <div class="info-box">
                    <span class="info-box-icon bg-aqua"><i class="fa fa-comments-o"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
                </div><!-- /.col -->
                <h4>App分类二<span class="text-green" style="margin-left:10px;"><a href="moreApp.html">more</a></span></h4>
              <div class="col-md-3" style="margin-left:-15px;">
                  <div class="info-box">
                    <span class="info-box-icon bg-green"><i class="fa fa-flag-o"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
              </div><!-- /.col -->
              <div class="col-md-3">
                  <div class="info-box">
                    <span class="info-box-icon bg-red"><i class="fa fa-star-o"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
               </div><!-- /.col -->
               <div class="col-md-3">
                  <div class="info-box">
                    <span class="info-box-icon bg-yellow"><i class="fa fa-calendar"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
                </div><!-- /.col -->
                <div class="col-md-3">
                  <div class="info-box">
                    <span class="info-box-icon bg-aqua"><i class="fa fa-comments-o"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
                </div><!-- /.col -->
                <h4>App分类三<span class="text-green" style="margin-left:10px;"><a href="moreApp.html">more</a></span></h4>
              <div class="col-md-3" style="margin-left:-15px;">
                  <div class="info-box">
                    <span class="info-box-icon bg-green"><i class="fa fa-flag-o"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
              </div><!-- /.col -->
              <div class="col-md-3">
                  <div class="info-box">
                    <span class="info-box-icon bg-red"><i class="fa fa-star-o"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
               </div><!-- /.col -->
               <div class="col-md-3">
                  <div class="info-box">
                    <span class="info-box-icon bg-yellow"><i class="fa fa-calendar"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
                </div><!-- /.col -->
                <div class="col-md-3">
                  <div class="info-box">
                    <span class="info-box-icon bg-aqua"><i class="fa fa-comments-o"></i></span>
                    <div class="info-box-content">
                      <span class="info-box-text">序列识别</span>
                      <span class="info-box-text">人气：410</span>
                      <button class="btn btn-block btn-success btn-flat btn-xs" style="margin-top:18px;"><i class="fa fa-plus"></i>添加</button>
                    </div><!-- /.info-box-content -->
                  </div><!-- /.info-box -->
                </div><!-- /.col -->
            </div>
            <div class="col-xs-2 pull-right">
              <button class="btn bg-olive btn-flat" style="width:100%">管理我的APP</button>
              <div class="box box-success" style="margin-top:20px">
                <div class="box-header with-border">
                  <h5 class="box-title">服务商招募</h5>
                  <div class="box-tools pull-right">
                    <button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                  </div><!-- /.box-tools -->
                </div><!-- /.box-header -->
                <div class="box-body" style="font-size:12px;">
                  介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍介绍
                </div><!-- /.box-body -->
                <div class="box-footer">
                	<button class="btn btn-block btn-info btn-flat">我要入驻</button>
                </div>
              </div><!-- /.box -->
              <div class="box">
                <div class="box-header">
                  <h3 class="box-title">常见问题</h3>
                </div><!-- /.box-header -->
                <div class="box-body no-padding">
                  <table class="table table-condensed">
                    <tr>
                      <td>1.</td>
                      <td>Update software</td>
                    </tr>
                    <tr>
                      <td>2.</td>
                      <td>Clean database</td>
                    </tr>
                    <tr>
                      <td>3.</td>
                      <td>Cron job running</td>
                    </tr>
                    <tr>
                      <td>4.</td>
                      <td>Fix and squish bugs</td>
                    </tr>
                  </table>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
            </div>
          </div><!--/.row-->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
    </div><!-- ./wrapper -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="//cdn.bootcss.com/fastclick/1.0.6/fastclick.min.js"></script>
    <script src="<%=request.getContextPath() %>/dist/js/celloud.js" type="text/javascript"></script>
    <script src="<%=request.getContextPath()%>/plugins/spin.min.js" type="text/javascript"></script>
	<!-- spin:loading效果 end-->
	<script type="text/javascript">
		var session_userId = <%=session.getAttribute("userId")%>;
		var sessionUserName = "<%=session.getAttribute("userName")%>";
		$.ajaxSetup ({
			cache: false //关闭AJAX相应的缓存
		});
		$(document).ready(function(){
				
		});
	</script>
  </body>
</html>