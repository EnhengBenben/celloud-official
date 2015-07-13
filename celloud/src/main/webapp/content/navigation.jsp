<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新手导航</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/content/css/navigation.css" />
</head>
<body>
	<div class="left">
        <div class="help_side" id="sortlist">
        	<div>
            	<h4><a href="#baseInfoH3">修改个人资料</a></h4>
            	<h4><a href="#themeH3">设置主题</a></h4>
            	<h4><a href="#deskH3">桌面app管理</a></h4>
            	<h4><a href="#findAppH3">查找app</a></h4>
            	<h4><a href="#dataUpH3">数据上传</a></h4>
            	<h4><a href="#dataRunAppH3">数据app运行</a></h4>
            	<h4><a href="#proRunAppH3">项目app运行</a></h4>
            </div>
         </div>
         <!-- 下次是否打开 -->
        <div class="help_box" style="margin-top: 50%;">
        	<input type="checkbox" id="notifyChk" onchange="javascript:notNotifyAnyMore(this);">不再提示
        </div>
    </div>
	<div class="right" id="top">
		<!-- 修改个人资料 -->
    	<a class="h-1px" id="help163" name="help163"></a>
        <h3 class="help_tit" id="baseInfoH3"><strong>如何修改个人资料</strong></h3>
        <div class="help_box">
        	<div>点击页面顶部celloud图标，打开个人信息对话框，在其中可以修改个人基本信息、修改密码、查看登录日志；</div>
        	<div><img alt="" src="/celloud/images/navigation/baseInfo.png"></div>
        	<div>注：其中邮箱为必填项，在密码找回中会用到，所以请填写真实邮箱。</div>
        </div>
        <!-- 修改主题 -->
        <a class="h-1px" id="help163" name="help163"></a>
        <h3 class="help_tit" id="themeH3"><strong>如何更换主题</strong></h3>
        <div class="help_box">
        	<div>点击页面左下角主题设置图标，打开主题设置对话框，轻轻点击选择喜欢的主题便可完成设置；</div>
        	<div><img alt="" src="/celloud/images/navigation/theme.png" style="height: 400px;"></div>
        </div>
        <!-- 桌面app图标管理 -->
        <a class="h-1px" id="help164" name="help164"></a>
        <h3 class="help_tit" id="deskH3"><strong>如何管理桌面app图标</strong></h3>
        <div class="help_box">
        	<ul class="list_Number">
        		<li>1、app桌面分为有当前桌面与全局视图两种模式，可通过页面顶部的全局视图按钮进行切换；</li>
        		<li>2、当前桌面负责app的添加、删除及查看，全局视图负责app在桌面间的移动；</li>
        	</ul>
        	<div><img alt="" src="/celloud/images/navigation/global.png"></div>
        	<div>3、在当前桌面，点击+号图标，打开应用市场，里面列出了平台所有可用的app。</div>
        	<div><img alt="" src="/celloud/images/navigation/addApp.png"></div>
        	<div>4、点击某个app下的“添加”按钮，该app便会添加到桌面上。</div>
        	<div><img alt="" src="/celloud/images/navigation/addApp_1.png"></div>
        	<div>5、右击桌面的已经添加的app，出现删除操作，点击删除应用，该app便会从桌面进行删除。</div>
        	<div><img alt="" src="/celloud/images/navigation/deleteApp.png"></div>
        	<div>6、切换到全局视图页面，显示了所有的桌面及对应桌面的app，用鼠标简单拖拽便可完成桌面间app的移动。</div>
        	<div><img alt="" src="/celloud/images/navigation/appMove.png"></div>
        </div>
        <!-- 应用市场 -->
        <a class="h-1px" id="help164" name="help164"></a>
        <h3 class="help_tit" id="findAppH3"><strong>如何查找app</strong></h3>
        <div class="help_box">
        	<div>1、点击页面左部应用市场图标，打开应用市场，左边显示了所有app的分类，右边显示某分类下的app；</div>
        	<div><img alt="" src="/celloud/images/navigation/apps.png"></div>
        	<div>2、在应用市场右上角输入app名称，输入回车或者点击搜索图标便可完成搜索；</div>
        	<div><img alt="" src="/celloud/images/navigation/appSearch.png"></div>
        	<div>3、点击app图标，可查看该app的详细信息；</div>
        	<div><img alt="" src="/celloud/images/navigation/appDetail.png"></div>
        	<div>4、已经添加的app，也可以在app详细页面对该app发表评论；</div>
        	<div><img alt="" src="/celloud/images/navigation/appComment.png"></div>
        </div>
        <!-- 上传数据 -->
        <a class="h-1px" id="help164" name="help164"></a>
        <h3 class="help_tit" id="dataUpH3"><strong>如何上传数据</strong></h3>
        <div class="help_box">
        	<div>1、点击页面左部数据管理图标，打开数据管理对话框；</div>
        	<div><img alt="" src="/celloud/images/navigation/data.png"></div>
        	<div>2、点击“上传数据”页签，打开数据上传页面，支持多数据上传；</div>
        	<div><img alt="" src="/celloud/images/navigation/dataUpload.png"></div>
        </div>
        <!-- 数据管理 -->
        <a class="h-1px" id="help164" name="help164"></a>
        <h3 class="help_tit" id="dataRunAppH3"><strong>数据如何运行app及查看报告</strong></h3>
        <div class="help_box">
        	<ul class="list_Number">
        		<li>1、数据管理显示了我的数据列表及其它用户共享给我的数据列表；</li>
        		<li>2、我的数据列表可进行的操作有app运行、导入项目、删除、共享、信息编辑，共享列表只可以查看报告；</li>
        	</ul>
        	<div><img alt="" src="/celloud/images/navigation/data.png"></div>
        	<div>3、数据批量运行，勾选需要操作的数据，点击右上角“选择App”按钮，选择app，点击选择的app便可开始运行；</div>
        	<div><img alt="" src="/celloud/images/navigation/runMultiApp.png"></div>
        	<div>4、单条数据运行，选择某条数据后面“操作”列的“查看应用报告”图标，打开查看应用对话框；</div>
        	<div><img alt="" src="/celloud/images/navigation/sdata_run.png"></div>
        	<div>5、在数据处理对话框，单击推荐的App，单击“确定添加”按钮，该app便会添加到该数据的app列表中；</div>
        	<div><img alt="" src="/celloud/images/navigation/dataAddApp.png"></div>
        	<div>6、app添加完成后，便可点击运行；</div>
        	<div><img alt="" src="/celloud/images/navigation/dataRunApp.png"></div>
        	<div>7、运行完成的app可以点击后面的“阅读”按钮查看报告；</div>
        	<div><img alt="" src="/celloud/images/navigation/dataViewReport.png"></div>
        </div>
        <!-- 项目管理 -->
        <a class="h-1px" id="help164" name="help164"></a>
        <h3 class="help_tit" id="proRunAppH3"><strong>项目如何运行app及查看报告</strong></h3>
        <div class="help_box">
        	<ul class="list_Number">
        		<li>1、项目管理显示了我的项目列表及其它用户共享给我的项目列表；</li>
        		<li>2、我的项目列表可进行的操作有新增项目、信息修改、app运行、共享、参数设置、删除；</li>
        	</ul>
        	<div><img alt="" src="/celloud/images/navigation/project.png"></div>
        	<div>3、在项目管理页面，选择某条项目后面“操作”列的“查看应用报告”图标，打开项目处理状态对话框；</div>
        	<div><img alt="" src="/celloud/images/navigation/pro_run.png"></div>
        	<div>4、在项目处理对话框，单击推荐的App，单击“确定添加”按钮，该app便会添加到该项目的app列表中；</div>
        	<div><img alt="" src="/celloud/images/navigation/proAddApp.png"></div>
        	<div>5、app添加完成后，便可点击运行；</div>
        	<div><img alt="" src="/celloud/images/navigation/proRunApp.png"></div>
        	<div>6、运行完成的app可以点击后面的“阅读”按钮查看报告；</div>
        	<div><img alt="" src="/celloud/images/navigation/proViewReport.png"></div>
        </div>
    </div>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
	function notNotifyAnyMore(obj){
		var checked = $("#notifyChk").attr("checked");
		var flag = 0;
		if(checked){
			flag=1;
		}
		$.get("userJson_updateNotify.action",{"notify":flag},function(result){
			
		});
	}
</script>
</body>
</html>