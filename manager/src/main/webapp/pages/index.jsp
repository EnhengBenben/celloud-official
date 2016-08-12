<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据,统计系统" />
<title>CelLoud统计系统</title>
<link rel="bookmark" href="<%=request.getContextPath()%>/img/favicon.ico" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/img/favicon.ico" />
<link rel="stylesheet" href="plugins/bootstrap/css/fonts/linecons/css/linecons.css">
<link rel="stylesheet" href="plugins/bootstrap/css/fonts/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet" href="plugins/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-core.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-forms.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-components.css">
<link rel="stylesheet" href="plugins/bootstrap/css/xenon-skins.css">
<link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/plugins/select2/css/select2.min.css">
<link rel="stylesheet" href="css/main.css">
<link rel="stylesheet" href="css/console.css">
<link rel="stylesheet" href="css/pay.css">
<link rel="stylesheet" href="plugins/jquery.alerts.css">
<script src="plugins/bootstrap/js/jquery-1.11.1.min.js"></script>
<script src="plugins/jquery.alerts.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="//cdn.datatables.net/plug-ins/1.10.12/sorting/file-size.js"></script>
</head>
<body class="page-body">
    <div class="page-container"><!-- add class "sidebar-collapsed" to close sidebar by default, "chat-visible" to make chat appear always -->
            
        <div class="sidebar-menu toggle-others fixed">
            <div class="sidebar-menu-inner" style="background-color: #2c2e2f;">
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
                        <a href="javascript:consoleModel.toConsole();">
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
                            <li id="data-week-menu">
                                <a href="javascript:dataFile.toWeekDataCount();">
                                    <span class="title">周统计</span>
                                </a>
                            </li>
                            <c:if test="${loginUserInSession.role=='2'}">
                                <li id="data-bigCustomer-menu">
                                    <a href="javascript:dataFile.toBigCustomerDataCount();">
                                        <span class="title">大客户统计</span>
                                    </a>
                                </li>
                            </c:if>
                            <li id="data-export-menu">
                                <a href="javascript:dataFile.toDataExport();">
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
                            
                            <li id="company-baseInfo-menu">
                                <a href="javascript:companyCount.toCompanyBaseInfo()">
                                    <span class="title">医院基本信息</span>
                                </a>
                            </li>
                            
                            <li id="company-data-menu">
                                <a href="javascript:companyCount.toCompanyDataCount();">
                                    <span class="title">医院数据统计</span>
                                </a>
                            </li>
                            <li id="company-report-menu">
                                <a href="javascript:companyCount.toCompanyReportCount();">
                                    <span class="title">医院报告统计</span>
                                </a>
                            </li>
                            <c:if test="${ loginUserInSession.role=='2'}">
                            <li id="company-bigcustomer-menu">
                                <a href="javascript:companyCount.toBigCustomerCount();">
                                    <span class="title">大客户统计</span>
                                </a>
                            </li>
                            </c:if>
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
                    <li>
                        <a href="#">
                            <i class="linecons-mail"></i>
                            <span class="title">APP管理 </span>
                        </a>
                        <ul>
<!--                             <li id="app-price-menu"> -->
<!--                                 <a data-click="to-app-price-list" href="javascript:void(0);"> -->
<!--                                     <span class="title">价格管理</span> -->
<!--                                 </a> -->
<!--                             </li> -->
                            <li id="app-grant-menu">
                                <a data-click="to-app-grant-list" href="javascript:void(0);">
                                    <span class="title">授权管理</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <!-- 费用中心 -->
                    <li>
                        <a href="#">
                            <i class="linecons-mail"></i>
                            <span class="title">费用中心 </span>
                        </a>
                        <ul>
                            <li id="to-pay-detail">
                                <a href="javascript:void(0);">
                                    <span class="title">消费明细</span>
                                </a>
                            </li>
                            <li id="to-recharge">
                                <a href="javascript:void(0);">
                                    <span class="title">账户充值</span>
                                </a>
                            </li>
                            <li id="to-recharge-record">
                                <a href="javascript:void(0);">
                                    <span class="title">充值记录</span>
                                </a>
                            </li>
                            <li id="to-invoice">
                                <a href="javascript:void(0);">
                                    <span class="title">发票管理</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div id="index-main-div" class="main-content">
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
                        <input id="user-role-hidden" type="hidden" value="${loginUserInSession.role }">
                        <ul class="dropdown-menu user-profile-menu list-unstyled">
                            <li>
                                <a href="javascript:user.showChangePwd();">
                                    <i class="fa-wrench"></i>
                                   修改密码
                                </a>
                            </li>
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
    <div class="modal fade" id="update-app-price-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title">修改APP价格</h4>
                </div>
                <div class="modal-body">
                    <form id="update-app-price-form" role="form" class="form-horizontal">
                       <input id="update-app-price-itemid" type="hidden">
                       <div class="form-group">
                           <label class="col-sm-2 control-label">APP名称</label>
                           <div class="col-sm-10">
                             <input type="text" class="form-control" id="update-app-price-name" readonly="readonly">
                           </div>
                       </div>
                       <div class="form-group">
                           <label class="col-sm-2 control-label">当前价格</label>
                           <div class="col-sm-10" >
                             <input type="text" class="form-control" id="update-app-current-price" readonly="readonly">
                           </div>
                       </div>
                       <div class="form-group">
                           <label class="col-sm-2 control-label">新价格</label>
                           <div class="col-sm-10">
		                       <input type="text" class="form-control" id="update-app-new-price" placeholder="格式： 300.00 / 300">
                           </div>
                       </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-info" id="update-app-price-button">修改</button>
                </div>
            </div>
        </div>
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
    <!-- JavaScripts initializations and stuff -->
    <script src="plugins/bootstrap/js/xenon-custom.js"></script>
    <!-- echarts -->
    <script src="plugins/echarts-2.2.7/echarts-all.js"></script>
    <script src="plugins/echarts-2.2.7/theme.js"></script>
    <script src="plugins/jquery.media.js"></script>
    <!-- validate -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugins/jQuery/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/validate.extend.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/jquery.form.js"></script>
    <!-- select2 -->
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugins/select2/js/select2.full.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath }/resources/plugins/select2/js/zh-CN.js"></script>
    <script src="js/main.js"></script>
    <script src="js/app.js"></script>
    <script type="text/javascript">
       window.CONTEXT_PATH = '<%=request.getContextPath()%>';
	</script>
    <script src="js/expense.js?version=3.2.5"></script>
</body>
</html>