<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/content/css/style.css" />
<link rel="stylesheet" href="<%=request.getContextPath() %>/content/css/zoom-styles.css" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<title>Celloud</title>
</head>
<body
	style="background: url(images/desktopBg/big/${requestScope.user.theme}) repeat center center;">
	<div id="fullbg"></div> 
	<div id="pageContent" class="pageContent">
		<a class="zoomClose" id="closeZoom" href="javascript:closeZoom();"></a>
		<img id="imageFullScreen" src=""/>
	</div>
	<!-- /#desktop -->
	<input type="hidden" name="userId" id="userId" value="${sessionScope.userId }" />
	<input type="hidden" name="softwareId" id="softwareId" value="" />
	<input type="hidden" name="softwareName" id="softwareName" value="" />
	<div id="desktop" style="overflow: auto;">
		<!-- /.topBar -->
		<div class="topBar">
			<div class="portrait appBtn addAppBtn1" _appName="个人信息" _appId="0" _appImg="images/publicIcon/portrait.png" _iframeSrc="content/baseInfo.jsp">
				<a href="javascript:void(0);"><img src="images/publicIcon/portrait.png"
					alt="个人信息" title="个人信息" /></a>
			</div>
			<div class="screenOrder">
				<a href="javascript:;" class="one"></a> 
				<a href="javascript:;" class="two"></a> 
				<a href="javascript:;" class="three"></a> 
				<a href="javascript:;" class="four"></a> 
				<a href="javascript:;" class="five"></a>
			</div>
			<a href="javascript:;" class="globalView"></a> 
			<a classca="search"></a>
		</div>
		<!-- /.leftBar -->
		<div class="leftBar">
			<div class="leftApp" _appId="sjsc" _appName="数据上传"
				_appImg="images/content/appmarket.png" _iframeSrc="pages/data/fileUpload.jsp">
				<img src="images/content/appmarket.png" alt="数据上传" title="数据上传"
					width="48" height="48" />
			</div>
			<div class="leftApp" _appId="sjgl" _appName="数据管理"
                _appImg="images/content/datamanag.png" _iframeSrc="pages/data/data.jsp">
                <img src="images/content/datamanag.png" alt="数据管理" title="数据管理"
                    width="48" height="48" />
            </div>
            <div class="leftApp" _appId="bg" _appName="报告"
				_appImg="images/content/report.png" _iframeSrc="pages/report/report.jsp">
				<img src="images/content/report.png" alt="报告" title="报告"
					width="48" height="48" /><em class="new"></em>
			</div>
			<div class="leftApp" _appId="yysc" _appName="应用市场"
				_appImg="images/content/diskexplorer.png" _iframeSrc="pages/software/software.jsp">
				<img src="images/content/diskexplorer.png" alt="应用市场" title="应用市场"
					width="48" height="48" />
			</div> 
			<div class="leftApp" _appId="sjk" _appName="数据库"
				_appImg="images/content/qq.png" _iframeSrc="pages/database/database.jsp">
				<img src="images/content/qq.png" alt="数据库" title="数据库"
					width="48" height="48" />
			</div>
			<c:if test="${session.companyId==6}">
				<div class="leftApp" _appId="tj" _appName="统计"
					_appImg="images/content/count_.png" _iframeSrc="pages/count/count.jsp">
					<img src="images/content/count_.png" alt="统计" title="统计"
						width="48" height="48" />
				</div>
			</c:if>
			<div class="leftToolWrapper">
				<div class="leftTool">
		          <a href="content/feedBack.html" target="_blank" title="意见反馈" class="toolIcon toolIcon-theme"></a>
<!-- 		          <a title="意见反馈" class="toolIcon toolIcon-setting"></a> -->
		          <a href="javascript:;" title="主题设置" class="toolIcon toolIcon-setting"></a>
		        </div>
		        <div class="leftTool">
		          <a href="logout" title="点击这里退出" class="toolIcon toolIcon-start"></a>
		        </div> 
			</div>
		</div>
		<!-- /.desktopWrapper -->
		<div class="desktopWrapper">
			<div class="desktopContainer desktop-disappear-animation1" _order="0">
				
			</div>
			<div class="desktopContainer desktop-disappear-animation1" _order="1">
				
			</div>
			<div class="desktopContainer desktop-show-animation" _order="2">
				
			</div>
			<div class="desktopContainer desktop-disappear-animation2" _order="3">
						
			</div>
			<div class="desktopContainer desktop-disappear-animation2" _order="4">
				
			</div>
		</div>
		<div class="taskBox">
			
		</div>
		<!--  /.themeWindow -->
		<div class="popWindow themeWindow " _appId="ztsz" style="display: none;">
			<div class="popWindow-titleBar">
				<div class="popWindow-title">主题设置</div>
				<div class="popWindow-Btn">
					<a href="javascript:;" class="popWindow-Btn-close"></a>
				</div>
			</div>
			<div class="popWindow-content">
				<div class="themeWrapper clearfix">
					  <div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/ysbj.jpg">
			            <img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_ysbj.jpg" alt="原生背景" width="150" height="110" />
			            <span>原生背景</span>
			          </div>
			          <div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/zrfj.jpg">
			            <img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_zrfj.jpg" alt="自然风景" width="150" height="110" />
			            <span>自然风景</span>
			          </div>
			          <div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/lqmx.jpg">
			            <img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_lqmx.jpg" alt="两歧麦秀" width="150" height="110" />
			            <span>两歧麦秀</span>
			          </div>
			          <div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/bcxg.jpg">
			            <img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_bcxg.jpg" alt="北辰星拱" width="150" height="110" />
			            <span>北辰星拱</span>
			          </div>
			          <div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/swxx.jpg">
			            <img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_swxx.jpg" alt="生物信息" width="150" height="110" />
			            <span>生物信息</span>
			          </div>
					<div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/blue.jpg">
						<img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_blue.jpg" alt="梦幻光影"
							width="150" height="110" /> <span>梦幻光影</span>
					</div>
					<div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/pinky_night.jpg">
						<img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_pinky_night.jpg"
							alt="粉红之夜" width="150" height="110" /> <span>粉红之夜</span>
					</div>
					<div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/green.jpg">
						<img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_green.jpg" alt="青青世界"
							width="150" height="110" /> <span>青青世界</span>
					</div>
					<div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/wood1.jpg">
						<img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_wood1.jpg" alt="温馨木纹"
							width="150" height="110" /> <span>温馨木纹</span>
					</div>
					<div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/wood2.jpg">
						<img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_wood2.jpg" alt="黑色木纹"
							width="150" height="110" /> <span>黑色木纹</span>
					</div>
					<div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/universe.jpg">
						<img src="<%=request.getContextPath() %>/images/desktopBg/small/theme_universe.jpg"
							alt="神秘星际" width="150" height="110" /> <span>神秘星际</span>
					</div>
					<div class="theme" _src="<%=request.getContextPath() %>/images/desktopBg/big/blue_sky.jpg">
						<img src="<%=request.getContextPath() %>/images/desktopBg/big/blue_sky.jpg" alt="默认背景"
							width="150" height="110" /> <span>默认背景</span>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- /#appManagerPanel -->
	<div id="appManagerPanel" style="display: none;">
		<div class="aMgDockContainer clearfix">
			<a href="javascript:;" class="quit">返回</a>
			<div class="leftApp" _appId="sjsc" _appName="数据上传"
				_appImg="images/content/appmarket.png" _iframeSrc="pages/data/fileUpload.jsp">
				<img src="images/content/appmarket.png" alt="数据上传" title="数据上传"
					width="48" height="48" />
			</div>
			<div id="toFileData" class="leftApp" _appId="sjgl" _appName="数据管理"
				_appImg="images/content/datamanag.png" _iframeSrc="pages/data/fileData.jsp">
				<img src="images/content/datamanag.png" alt="数据管理" title="数据管理"
					width="48" height="48" />
			</div>
			<div class="leftApp" _appId="bg" _appName="报告"
				_appImg="images/content/report.png" _iframeSrc="pages/report/report.jsp">
				<img src="images/content/report.png" alt="报告" title="报告"
					width="48" height="48" />
			</div>
			<div class="leftApp" _appId="yysc" _appName="应用市场"
				_appImg="images/content/diskexplorer.png" _iframeSrc="pages/software/software.jsp">
				<img src="images/content/diskexplorer.png" alt="应用市场" title="应用市场"
					width="48" height="48" />
			</div>
			<div class="leftApp" _appId="sjk" _appName="数据库"
				_appImg="images/content/qq.png" _iframeSrc="pages/database/database.jsp">
				<img src="images/content/qq.png" alt="数据库" title="数据库"
					width="48" height="48" />
			</div>
		</div>
		<div class="aMgFolderContainer clearfix">
			<div class="folderItem folderItem01">
				<div class="folderItemInner" _order="0">
					
				</div>
			</div>
			<div class="folderItem folderItem02">
				<div class="folderItemInner" _order="1">
					
				</div>
			</div>
			<div class="folderItem folderItem03">
				<div class="folderItemInner" _order="2">
					
				</div>
			</div>
			<div class="folderItem folderItem04">
				<div class="folderItemInner" _order="3">
					
				</div>
			</div>
			<div class="folderItem folderItem05">
				<div class="folderItemInner" _order="4">
					
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" value="0" id="_hidAppId">
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/external.js"></script>
<script type="text/javascript">
var userName = "<%=session.getAttribute("userName") %>";
var password = <%=session.getAttribute("userPwd") %>;
var sessionUserNav = "<%=session.getAttribute("userNav") %>";
<%-- var sessionNoticeTitleFlag = "<%=session.getAttribute("noticeTitleFlag")%>"; --%>
</script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/celloud.js"></script>
<script type="text/javascript">
	//记录当前数据模块打开的哪个数据标签页，默认是数据管理标签
	var dataWindow = 1;
	window.apps = [];
	//定义全局变量，当前桌面的编号，从0开始
	var deskNo = 2;
	var userId = $.trim($("#userId").val());
	//是否有文件正在上传
	var isUploading = false;
	var globalDataIds = new Array();
	(function() {
		initCelloud();
	})();
</script>
</body>
</html>