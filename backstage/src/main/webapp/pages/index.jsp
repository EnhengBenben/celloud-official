<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CelLoud后台管理系统</title>
<meta name="keywords" content="上海华点云生物科技有限公司,celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据,上海华点云生物科技有限公司网站首页,上海华点云生物科技有限公司官网,上海华点云生物科技有限公司北京分公司" />
<meta name="description" content="一站式高通量基因检测数据分析系统" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="baidu-site-verification" content="ZvlzkAOdsB" />
<link rel="bookmark" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" />
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link href="//cdn.bootcss.com/bootstrap-switch/3.3.2/css/bootstrap3/bootstrap-switch.min.css" rel="stylesheet">
<link href="//cdn.bootcss.com/datatables/1.10.12/css/dataTables.bootstrap.min.css" rel="stylesheet">
<link href="//cdn.bootcss.com/Buttons/2.0.0/css/buttons.min.css" rel="stylesheet">
<link href="//cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-core.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-forms.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-components.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-skins.css">
<link rel="stylesheet" href="plugins/jquery_alert_dialogs/jquery.alerts.css">
<link rel="stylesheet" href="plugins/select2/css/select2.min.css"/>
<link rel="stylesheet" href="css/main.css">
</head>
<body class="page-body">
    <div class="page-container">
        <div class="sidebar-menu toggle-others fixed">
            <div class="sidebar-menu-inner">
                <header class="logo-env">
                    <!-- logo -->
                    <div class="logo">
                        <a href="#" class="logo-expanded">
                            <img src="images/index/logo.png" width="80" alt="" />
                        </a>
                        
                        <a href="#" class="logo-collapsed">
                            <img src="images/index/mini-logo.png" width="40" alt="" />
                        </a>
                    </div>          
                </header>
                <ul id="main-menu" class="main-menu">
                    <li>
                        <a id="to-app-manage" href="#">
                            <i class="fa fa-cubes" aria-hidden="true"></i>
                            <span class="title">应用管理</span>
                        </a>
                        <ul>
                            <li id="app-list-menu">
                                <a href="javascript:app.toAppMain();">
                                    <span class="title">应用列表</span>
                                </a>
                            </li>
                            <li id="app-add-menu">
                                <a href="javascript:app.toAddApp();">
                                    <span class="title">添加应用</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li id="company-menu">
                        <a href="#">
                            <i class="fa fa-hospital-o" aria-hidden="true"></i>
                            <span class="title">公司管理</span>
                        </a>
                        <ul>
                            <li id="file-clean-menu">
                                <a href="javascript:company.toCompanyMain();">
		                            <span class="title">公司列表</span>
		                        </a>
                            </li>
                            <li id="file-upload-menu">
                                <a href="javascript:company.toUploadPdf();">
                                    <span class="title">PDF上传</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li id="user-menu">
                        <a href="javascript:user.toUserMain();">
                            <i class="fa fa-users" aria-hidden="true"></i>
                            <span class="title">用户管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="fa fa-database" aria-hidden="true"></i>
                            <span class="title">数据管理</span>
                        </a>
                        <ul>
                            <li id="file-clean-menu">
                                <a href="javascript:dataFile.toDataClean();">
                                    <span class="title">数据清理</span>
                                </a>
                            </li>
                            <li id="file-upload-menu">
                                <a href="javascript:dataFile.toDataFileUpload();">
                                    <span class="title">数据上传</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li id="task-menu">
                        <a href="#">
                            <i class="fa fa-tasks" aria-hidden="true"></i>
                            <span class="title">任务统计</span>
                        </a>
                        <ul>
                            <li id="queuing-time-menu">
                                <a href="javascript:task.toQueuingTime();">
                                    <span class="title">排队时间</span>
                                </a>
                            </li>
                            <li id="running-time-menu">
                                <a href="javascript:task.toRunningTime(1);">
                                    <span class="title">运行时间</span>
                                </a>
                            </li>
                            <li id="quantity-statistics-menu">
                                <a href="javascript:task.toQuantityStatistics();">
                                    <span class="title">数量统计</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li id="permission-menu">
                        <a href="#">
                            <i class="fa fa-user" aria-hidden="true"></i>
                            <span class="title">权限管理</span>
                        </a>
                        <ul>
                            <li id="queuing-time-menu">
                                <a href="javascript:permission.resource.pageQuery('1',10);">
                                    <span class="title">资源管理</span>
                                </a>
                            </li>
                            <li id="running-time-menu">
                                <a href="javascript:permission.role.pageQuery('1',10);">
                                    <span class="title">角色管理</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li id="task-menu">
                        <a href="#">
                            <i class="fa fa-money" aria-hidden="true"></i>
                            <span class="title">充值</span>
                        </a>
                        <ul>
                            <li>
                                <a href="javascript:expense.recharge.main();">
                                    <span class="title">账户充值</span>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:void(0);">
                                    <span class="title">充值记录</span>
                                </a>
                            </li>
                            <li>
                                <a href="javascript:expense.invoice.toInvoiceMain('1','');">
                                    <span class="title">发票管理</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li id="mailing-menu">
                        <a href="#">
                           <i class="fa fa-send-o" aria-hidden="true"></i>
                            <span class="title">邮件管理</span>
                        </a>
                        <ul>
                            <li>
                                <a href="javascript:mailing.toMailingMain();">
		                            <span class="title">邮件群发</span>
		                        </a>
                            </li>
                            <li>
                                <a href="javascript:mailing.mailTemplate.main();">
                                    <span class="title">邮件模板</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li id="notice-menu">
                        <a href="javascript:notice.toNoticeMain();">
                            <i class="fa fa-bullhorn" aria-hidden="true"></i>
                            <span class="title">公告管理</span>
                        </a>
                    </li>
                    <li id="feedback-menu">
                        <a href="javascript:feedback.tofeedbackMain();">
                            <i class="fa fa-comments-o" aria-hidden="true"></i>
                            <span class="title">意见反馈</span>
                        </a>
                    </li>
                    <li id="client-menu">
                        <a href="javascript:client.toClientMain();">
                            <i class="fa fa-cloud" aria-hidden="true"></i>
                            <span class="title">客户端</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div id="index-main-div" class="main-content">
            <!-- User Info, Notifications and Menu Bar -->
            <nav class="navbar user-info-navbar" role="navigation">
                <!-- Left links for user info navbar -->
                <ul class="user-info-menu left-links list-inline list-unstyled">
                    <li class="hidden-sm hidden-xs">
                        <a href="#" data-toggle="sidebar">
                            <i class="fa-bars"></i>
                        </a>
                    </li>
                </ul>
                <!-- Right links for user info navbar -->
                <ul class="user-info-menu right-links list-inline list-unstyled">
                    <li class="dropdown user-profile">
                        <a href="#" data-toggle="dropdown">
                            <img src="images/index/user-4.png" alt="user-image" class="img-circle img-inline userpic-32" width="28" />
                            <span>
                                ${loginUserInSession.username }
                                <i class="fa-angle-down"></i>
                            </span>
                        </a>
                        <ul class="dropdown-menu user-profile-menu list-unstyled">
                            <li>
                                <a href="javascript:user.showChangePwd();">
                                    <i class="fa-wrench"></i>  修改密码
                                </a>
                            </li>
                            <li class="last">
                                <a href="logout">
                                    <i class="fa-lock"></i>   退出
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </nav>
            <div id="main-content" class="row"></div>
        </div>
    </div>
    <div class="page-loading-overlay">
        <div class="loader-2"></div>
    </div>
    <!-- Bottom Scripts -->
    <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <script src="//cdn.bootcss.com/jquery-validate/1.15.0/jquery.validate.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <script src="//cdn.bootcss.com/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
    <script src="//cdn.bootcss.com/datatables/1.10.12/js/dataTables.bootstrap.min.js"></script>
    <script src="//cdn.bootcss.com/plupload/2.1.8/plupload.full.min.js"></script>
	<script src="//cdn.bootcss.com/plupload/2.1.8/i18n/zh_CN.js"></script>
	<script src="//cdn.bootcss.com/swfobject/2.2/swfobject.min.js"></script>
    <script src="plugins/bootstrap/js/TweenMax.min.js"></script>
    <script src="plugins/bootstrap/js/resizeable.js"></script>
    <script src="plugins/bootstrap/js/joinable.js"></script>
    <script src="plugins/bootstrap/js/xenon-api.js"></script>
    <script src="plugins/bootstrap/js/xenon-toggles.js"></script>
    <!-- Imported scripts on this page -->
    <script src="plugins/bootstrap/js/xenon-widgets.js"></script>

    <!-- Imported scripts on this page -->
    <script src="plugins/bootstrap/js/xenon-custom.js"></script>
	<script src="plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
	<script src="plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
	<script src="plugins/ckeditor/ckeditor.js" type="text/javascript"></script>
    <!-- JavaScripts initializations and stuff -->
    <script src="js/validate.extend.js"></script>
	<script src="js/jquery.form.js"></script>
	<script src="js/main.js"></script>
</body>
</html>