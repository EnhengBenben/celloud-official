<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据,统计系统" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico" />
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico" />
<title>CelLoud统计系统</title>

<link rel="stylesheet" href="plugins/bootstrap/css/fonts/linecons/css/linecons.css">
<link rel="stylesheet" href="plugins/bootstrap/css/fonts/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-core.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-forms.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-components.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-skins.css">
<link rel="stylesheet" href="plugins/bootstrap/js/datatables/dataTables.bootstrap.css">

<script src="plugins/bootstrap/js/jquery-1.11.1.min.js"></script>

</head>
<body class="page-body">
    <div class="page-container"><!-- add class "sidebar-collapsed" to close sidebar by default, "chat-visible" to make chat appear always -->
            
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
                    <li class="active opened active" id="console-menu">
                        <a href="javascript:console.toConsole();">
                            <i class="linecons-cog"></i>
                            <span class="title">控制台</span>
                        </a>
                    </li>
                    <li>
                        <a id="to-app-manage" href="#">
                            <i class="linecons-desktop"></i>
                            <span class="title">数据统计</span>
                        </a>
                        <ul>
                            <li id="data-count-menu">
                                <a href="javascript:dataFile.toDataCount();">
                                    <span class="title">数据总览</span>
                                </a>
                            </li>
                            <li id="data-user-menu">
                                <a href="javascript:dataFile.toUserDataCount();">
                                    <span class="title">用户数据统计</span>
                                </a>
                            </li>
                            <li id="data-company-menu">
                                <a href="javascript:dataFile.toCompanyDataCount();">
                                    <span class="title">医院数据统计</span>
                                </a>
                            </li>
                            <c:if test="${loginUserInSession.role=='2'}">
                                <li id="data-bigCustomer-menu">
                                    <a href="javascript:dataFile.toBigCustomerDataCount()">
                                        <span class="title">大客户统计</span>
                                    </a>
                                </li>
                            </c:if>
                            <li>
                                <a href="javascript:gotoOutputData()">
                                    <span class="title">数据导出</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#">
                            <i class="linecons-mail"></i>
                            <span class="title">医院统计 </span>
                        </a>
                        <ul>
                            <li id="company-guide-menu">
                                <a href="javascript:companyCount.toCompanyGuideCount();">
                                    <span class="title">医院总览</span>
                                </a>
                            </li>
                            <c:if test="${loginUserInSession.role=='2'}">
                                <li>
                                    <a href="javascript:toCompanyBaseInfo()">
                                        <span class="title">医院基本信息</span>
                                    </a>
                                </li>
                            </c:if>
                            <li>
                                <a href="#">
                                    <span class="title">医院数据统计</span>
                                </a>
                            </li>
                            <li>
                                <a href="#">
                                    <span class="title">医院报告统计</span>
                                </a>
                            </li>
                            <c:if test="${ loginUserInSession.role=='2'}">
                            <li>
                                <a href="#">
                                    <span class="title">大客户统计</span>
                                </a>
                            </li>
                            </c:if>
                        </ul>
                    </li>
                </ul>
                        
            </div>
            
        </div>
        <div class="main-content">
        <!-- User Info, Notifications and Menu Bar -->
            <nav class="navbar user-info-navbar" role="navigation">
                
                <!-- Right links for user info navbar -->
                <ul class="user-info-menu right-links list-inline list-unstyled">
                    <li class="dropdown user-profile">
                        <a href="#" data-toggle="dropdown">
                            <span>
                                ${loginUserInSession.username }
                                <i class="fa-angle-down"></i>
                            </span>
                        </a>
                        
                        <ul class="dropdown-menu user-profile-menu list-unstyled">
                            <li class="last">
                                <a  href="logout">
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
    
    <script src="js/main.js"></script>
    
     <!-- echarts -->
    <script src="plugins/echarts-2.2.7/echarts-all.js"></script>
    <script src="plugins/echarts-2.2.7/theme.js"></script>
</body>
</html>