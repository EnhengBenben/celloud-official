<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
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
  <link href="<%=request.getContextPath()%>/less/celloud.less" rel="stylesheet/less" type="text/css" />
  <script src="//cdn.bootcss.com/less.js/2.7.1/less.min.js"></script>
  <script src="//cdn.bootcss.com/modernizr/2.8.3/modernizr.min.js"></script>
  <link href="<%=request.getContextPath() %>/css/upload.css" rel="stylesheet" type="text/css">
  <link href="<%=request.getContextPath()%>/css/bsi_main.css?version=3.1.15" rel="stylesheet" type="text/css" />
  <!-- [if It IE 9]>
  <script src="//cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
  <![endif]  -->
</head>
<body class="container" ng-app="celloudApp" ng-controller="sidebarController">
  <header class="header">
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <div class="navbar-logo {{collapsed|logoMiniFilter}}">
            <a class="logo" href="#"></a>
          </div>
        </div>
        <ul class="nav navbar-nav pull-left">
            <shiro:hasPermission name="rocky:product">
              <li>
                <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
                  <i class="cubes-icon">&nbsp;</i>
                </a>
                <div class="dropdown-menu product-dropdown">
                  <a href=""><img src="<%=request.getContextPath()%>/images/app/rocky.png" alt="华木兰" title="华木兰"></a>
                </div>
              </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="bsi:product">
              <li>
                <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
                  <i class="cubes-icon">&nbsp;</i>
                </a>
                <div class="dropdown-menu product-dropdown">
                  <a href=""><img src="<%=request.getContextPath()%>/images/app/rocky.png" alt="百菌探" title="百菌探"></a>
                  <a href=""><img src="<%=request.getContextPath()%>/images/app/rocky.png" alt="华木兰" title="华木兰"></a>
                </div>
              </li>
            </shiro:hasPermission>
          <li><a data-toggle="modal" data-target="#upload-modal"><i class="upload-icon"></i></a></li>
        </ul>
        <ul class="nav navbar-nav pull-right">
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="code-icon"></i>
            </a>
            <div class="dropdown-menu code-dropdown">
              <img alt="扫码关注" src="<%=request.getContextPath()%>/images/icon/qrcode.jpg">
            </div>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="money-icon">&nbsp;</i>
            </a>
            <div class="dropdown-menu money-dropdown">
              <p>账户余额：<span class="tips">{{userInfo.balances}}</span>元</p>
              <a class="btn" href="#/expense/paydetail">立即充值</a><br>
              <a class="btn-link" href="#/expense/consume">查看消费记录</a>
            </div>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="bell-icon">&nbsp;</i>
              <span class="label label-danger">{{notices.num==0?'':(notices.num+'')}}</span>
            </a>
            <div class="dropdown-menu message-dropdown">
              <p> 您有<span class="tips">{{notices.num}}</span>条新消息</p>
              <a class="btn-link" href="#/notice/list">查看所有</a>
            </div>
          </li>
           <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="bell-icon">&nbsp;</i>
              <span class="label label-danger">{{messages.num==0?'':(''+messages.num)}}</span>
            </a>
            <div class="dropdown-menu message-dropdown">
              <p> 您有<span class="tips">{{messages.num}}</span>条新消息</p>
              <a class="btn-link" href="#/notice/list">查看所有</a>
            </div>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="user-icon">&nbsp;</i>
            </a>
            <div class="dropdown-menu user-dropdown">
              <a class="btn" href="#/user/base">个人信息</a>
              <a class="btn btn-cancel" href="logout">退出</a>
            </div>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  <aside class="sidebar {{collapsed|collapsedFilter}}" id="common-sidebar">
    <section class="s-bar">
      <div class="sidebar-collapse">
        <a href="javascript:void(0)" ng-click="toggleCollapse()"><i class="{{collapsed|collapsedIconFilter}}"></i></a>
      </div>
      <ul class="sidebar-menu">
        <li class="header">产品与服务</li>
        <li ng-class="{active: isActive('/')}">
          <a href="#/"><i class="overview-icon"></i><span>我的工作台</span></a>
        </li>
        <li>
          <a href="javascript:void(0)"><i class="sample-icon"></i><span>样本采集</span></a>
        </li>
        <li>
          <a href="javascript:void(0)"><i class="experiment-icon"></i><span>实验管理</span></a>
        </li>
        <li ng-class="{active: isActive('/data')}">
          <a href="#/data"><i class="data-icon"></i><span>数据管理</span></a>
        </li>
        <li ng-class="{active: isActive('/report')}">
<%--           <shiro:lacksPermission name="bsi:product"> --%>
<!--             <a href="#/reportpro"><i class="report-icon"></i><span>报告管理</span></a> -->
<%--           </shiro:lacksPermission> --%>
<%--           <shiro:hasPermission name="bsi:product"> --%>
            <a href="#/reportdata"><i class="report-icon"></i><span>报告管理</span></a>
<%--           </shiro:hasPermission> --%>
        </li>
        <li ng-class="{active: isActive('/app')}">
          <a href="#/app"><i class="app-icon"></i><span>应用市场</span></a>
        </li>
      </ul>
      <ul class="sidebar-menu">
        <li class="header">用户中心</li>
        <li ng-class="{active: isActive('/user')}">
          <a href="#/user/base"><i class="account-icon"></i><span>账号管理</span></a>
        </li>
        <li ng-class="{active: isActive('/qa')}">
          <a href="#/qa"><i class="qa-icon"></i><span>问题反馈</span></a>
        </li>
        <li ng-class="{active: isActive('/expense')}">
          <a href="#/expense/consume"><i class="cost-icon"></i><span>费用中心</span></a>
        </li>
      </ul>
    </section>
  </aside>
  <div class="view-container">
    <div class="pro-body">
      <ol class="breadcrumb">
	    <li>CelLoud</li>
	    <li>数据管理</li>
      </ol>
	  <div class="content">
	      <div class="page-layout page-main-content">
		    <div class="topbar-menu">
		      <header class="common-menu">
                <div class="logo">
                    <img alt="华木兰" src="<%=request.getContextPath()%>/images/app/breast_mulations_scan.png">
                </div>
                <hr class="-left">
                <div id="common-menu-center" class="info"></div>
                <div id="common-menu-right" class="searchs"></div>
                <hr class="-right">
              </header>
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
		          <div id="common-menu" class="common-menu-btn pull-left">
		            <a class="item-btn" id="to-sample-a" href="javascript:void(0)"><i class="celicon my-sample-icon"></i><br>收样</a>
		            <a class="item-btn upload-btn" id="to-upload-a" href="javascript:void(0)">
		              <i class="celicon my-upload-icon"></i><br>上传
		              <canvas id="upload-progress" class="upload-progress" width="64" height="64"></canvas>
		            </a>
		            <a class="item-btn" id="to-data-a" href="javascript:void(0)"><i class="celicon my-data-icon"></i><br>数据</a>
		            <a class="item-btn active" id="to-report-a" href="javascript:void(0)"><i class="celicon my-report-icon"></i><br>报告</a>
		          </div>
		          <hr>
		          <div class="common-menu-search pull-right">
		            <div class="data-search">
		              <input id="condition-input" class="input-sm" type="text" placeholder="搜索"/>
		              <a id="condition-find" class="input-group-btn"><i class="fa fa-search"></i></a>
		            </div>
		          </div>
		        </div>
		      </div>
		    </div>
		    <div id="container" class="container-fluid">
		     
		    </div>
		  </div>
      </div>
	  <div id="upload-modal" class="modal upload-modal">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button id="close-upload-modal" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	          <h4 class="modal-title">数据上传</h4>
	        </div>
	        <div class="modal-body row">
	          <div class="step-item">
	            <div class="step-one active">1</div><hr id="one-to-two">
	            <div class="step-two">2</div><hr id="two-to-three">
	            <div class="step-three">3</div>
	          </div>
	          <div class="step-one-content">
	            <input id="batch-info" type="text" placeholder="请输入标签" value=""/><br>
	            <select>
	              <option>百菌探-血液</option>
	              <option>百菌探-腹水</option>
	            </select>
	            <p><span>为每次上传数据按输入加入标签，提升后续报告查询、数据管理、分类汇总提供快捷服务。<span></p>
	            <p><span>示例：外科5月；ICU-9床；急诊发热<span></p>
	            <a id="next-step" class="btn" href="javascript:void(0)">下一步</a>
	          </div>
	          <div class="step-two-content hide">
	            <div class="upload-container">
	              <div id="plupload-content" class="box-body plupload-content">
	                <ul id="upload-filelist" class="plupload-filelist"></ul>
	                <div class="upload-text"><i class="celicon grey-upload-icon"></i>拖拽文件到此或者点击选择文件上传</div>
	              </div>
	            </div>
	            <p><span><input id="need-split" type="checkbox" value="1" checked="checked">需要数据拆分 (run: Split)</span></p>
	            <p><span>&gt;目前支持 .tar.gz; .zip; 压缩过的fasta 格式的序列文件及index 文件</span></p>
	            <p><span>&gt;文件命名规则请点击查看 "百菌探_下机文件命名规则"</span></p>
	            <p><span>&gt;数据拆分(Split) 选中时系统先按index文件对数据拆分并重命名.</span></p>
	            <input id="tag-info" type="hidden" value="1"/>
	            <a id="begin-upload" class="btn" href="javascript:void(0)">开始上传</a>
	          </div>
	          <div class="step-three-content hide">
	            <div class="upload-status">数据上传中...数据标签 ：<span id="tags-review"></span></div>
	            <div class="upload-container">
	              <div id="plupload-content" class="box-body plupload-content">
	                <ul id="uploading-filelist" class="plupload-filelist">
	                  <li class="plupload-filelist-header">
	                    <div class="plupload-file-name"><span>文件名</span></div>
	                    <div class="plupload-file-size">大小</div>
	                    <div class="plupload-file-surplus">剩余时间</div>
	                    <div class="plupload-file-status">进度</div>
	                    <div class="plupload-file-action">操作</div>
	                  </li>
	                </ul>
	              </div>
	            </div>
	            <p><span>文件上传中结束会自动开始分析任务，可在报告页面查看最新报告.</span></p>
	            <a id="upload-more" class="btn" href="javascript:void(0)">上传更多</a>
	          </div>
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
	  <div id="report-uploading-modal" class="modal running-modal">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	          <h4 class="modal-title">数据上传中</h4>
	        </div>
	        <div class="modal-body row">
	          <h4>数据不完整，来检查您缺哪个数据吧！</h4>
	          <div>数据运行所需文件如下：<br>
	            <span class="highlight-text">public_name_</span>R1.fastq<br>
	            <span class="highlight-text">public_name_</span>R2.fastq<br>
	            <span class="highlight-text">public_name_</span>.txt
	          </div>
	          <a class="btn" href="javascript:void(0)" data-dismiss="modal" aria-label="Close">知道了</a>
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
	  <div id="running-modal" class="modal running-modal">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	          <h4 class="modal-title">数据分析中</h4>
	        </div>
	        <div class="modal-body row">
	          <img alt="" src="<%=request.getContextPath()%>/images/icon/in-analysis.gif">
	          <h4>报告生成中</h4>
	          <div>请<span class="highlight-text">稍后</span>刷新查看</div>
	          <a class="btn" href="javascript:void(0)" data-dismiss="modal" aria-label="Close">OK,再等等</a>
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
	  <div id="running-error-modal" class="modal running-error-modal">
	    <div class="modal-dialog">
	      <div class="modal-content">
	        <div class="modal-header">
	          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
	          <h4 class="modal-title">异常终止</h4>
	        </div>
	        <div class="modal-body row">
	          <i class="celicon run-error-icon"></i>
	          <h4>我们抱歉的通知，数据： <span id="run-error-data" class="highlight-text"></span></h4>
	          <h4>上传（分析）过程中出错</h4>
	          <div>请重传（联系service@celloud.cn获取帮助）</div>
	          <a class="btn" href="javascript:void(0)" data-dismiss="modal" aria-label="Close">知道了</a>
	        </div>
	      </div><!-- /.modal-content -->
	    </div><!-- /.modal-dialog -->
	  </div><!-- /.modal -->
	</div>
  </div>
  <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="//cdn.bootcss.com/spin.js/2.3.2/spin.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/3.2.2/echarts.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-route.min.js"></script>
  <script src="//cdn.bootcss.com/angular.js/1.5.8/angular-resource.min.js"></script>
  <script src="//cdn.bootcss.com/echarts/2.2.7/echarts.js"></script>
  
  <script src="<%=request.getContextPath()%>/js/message.js"></script>
  <script src="<%=request.getContextPath()%>/js/application.js"></script>
  <script src="<%=request.getContextPath()%>/js/app/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/app/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/expense/filter.js"></script>
  <script src="<%=request.getContextPath()%>/js/expense/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/expense/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/user/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/user/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/data/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/data/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/data/data.js"></script>
  <script src="<%=request.getContextPath()%>/js/config/routeProvider.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/common/filter.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/service.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/controller.js"></script>
  <script src="<%=request.getContextPath()%>/js/overview/userCount.js"></script>
  <script src="<%=request.getContextPath()%>/plugins/waveLoading.min.js?version=1.1"></script>
  <script src="<%=request.getContextPath()%>/plugins/calendar/WdatePicker.js"></script>
  <script src="<%=request.getContextPath()%>/js/utils.js?version=1.1" type="text/javascript"></script>
  <script src="<%=request.getContextPath()%>/js/bsi_main.js?version=3.1.15" type="text/javascript"></script>
  <script src="<%=request.getContextPath() %>/js/bsi_upload.js?version=3.1.12"></script>
  <script src="<%=request.getContextPath()%>/js/charts.js"></script>
</body>
</html>