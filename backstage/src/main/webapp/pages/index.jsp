<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CelLoud后台管理系统</title>
<link rel="stylesheet" href="plugins/bootstrap/css/fonts/linecons/css/linecons.css">
<link rel="stylesheet" href="plugins/bootstrap/css/fonts/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-core.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-forms.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-components.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-skins.css">
<link rel="stylesheet" href="plugins/bootstrap/js/datatables/dataTables.bootstrap.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="plugins/jquery_alert_dialogs/jquery.alerts.css">

<script src="plugins/bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="plugins/jQuery/jquery.validate.min.js"></script>
<script src="plugins/jQuery/messages_zh.min.js"></script>
<script src="js/validate.extend.js"></script>
<script src="plugins/jquery_alert_dialogs/jquery.alerts.js" type="text/javascript"></script>
<script src="js/jquery.form.js"></script>
<script src="js/main.js"></script>
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
                            <i class="linecons-desktop"></i>
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
                        <a href="javascript:company.toCompanyMain();">
                            <i class="linecons-note"></i>
                            <span class="title">公司管理</span>
                        </a>
                    </li>
                    <li id="user-menu">
                        <a href="javascript:user.toUserMain();">
                            <i class="linecons-note"></i>
                            <span class="title">用户管理</span>
                        </a>
                    </li>
                    <li id="feedback-menu">
                        <a href="javascript:feedback.tofeedbackMain();">
                            <i class="linecons-database"></i>
                            <span class="title">意见反馈</span>
                        </a>
                    </li>
                    <li id="notice-menu">
                        <a href="javascript:notice.toNoticeMain();">
                            <i class="linecons-params"></i>
                            <span class="title">公告管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            <i class="linecons-beaker"></i>
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
                    <li id="mailing-menu">
                        <a href="javascript:mailing.toMailingMain();">
                            <i class="linecons-globe"></i>
                            <span class="title">邮件群发</span>
                        </a>
                    </li>
                    <li id="client-menu">
                        <a href="javascript:client.toClientMain();">
                            <i class="linecons-cloud"></i>
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
                                    <i class="fa-wrench"></i>
                                   修改密码
                                </a>
                            </li>
                            <li class="last">
                                <a href="logout">
                                    <i class="fa-lock"></i>
                                    退出
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
                
            </nav>
            
            
            <div id="main-content" class="row">
                
            </div>
        </div>
    </div>
    <div class="page-loading-overlay">
        <div class="loader-2"></div>
    </div>
    <!-- Bottom Scripts -->
    <script src="plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="plugins/bootstrap/js/TweenMax.min.js"></script>
    <script src="plugins/bootstrap/js/resizeable.js"></script>
    <script src="plugins/bootstrap/js/joinable.js"></script>
    <script src="plugins/bootstrap/js/xenon-api.js"></script>
    <script src="plugins/bootstrap/js/xenon-toggles.js"></script>
    <!-- Imported scripts on this page -->
    <script src="plugins/bootstrap/js/xenon-widgets.js"></script>
    <script src="plugins/bootstrap/js/datatables/js/jquery.dataTables.min.js"></script>


    <!-- Imported scripts on this page -->
    <script src="plugins/bootstrap/js/datatables/dataTables.bootstrap.js"></script>
    <script src="plugins/bootstrap/js/datatables/yadcf/jquery.dataTables.yadcf.js"></script>
    <script src="plugins/bootstrap/js/datatables/tabletools/dataTables.tableTools.min.js"></script>

    <!-- JavaScripts initializations and stuff -->
    <script src="plugins/bootstrap/js/xenon-custom.js"></script>
</body>
</html>