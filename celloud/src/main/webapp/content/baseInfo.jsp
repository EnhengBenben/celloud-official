<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=request.getContextPath() %>/content/css/style.css" />
<title>用户个人信息设置</title>
</head>
<body>
<input type="hidden" id="baseInfoUserIdHidden" value="<%=session.getAttribute("userId")%>">
<div class="themeWrapper clearfix" id="baseInfoParentDiv" style="background-color: #e5f5fc;">
	<div class="tabbable user-info"> <!-- tabs -->
        <ul class="nav nav-tabs">
            <li class="tab1 active" onclick="javascript:showTab(1);"><a href="#tab1" data-toggle="tab" style="text-decoration: none;">基本信息</a></li>
            <li class="tab2" onclick="javascript:showTab(2);"><a href="#tab2" data-toggle="tab" style="text-decoration: none;">修改密码</a></li>
            <li class="tab3" onclick="javascript:showTab(3);"><a href="#tab3" data-toggle="tab" style="text-decoration: none;">登录历史</a></li>
        </ul>
        <div class="tab-content"> 
            <!-- 基本信息维护 -->
            <div class="tab-pane active" id="tab1">  
                <div class="control-group">
                    <label class="control-label" for="textarea">用户名</label>
                    <div class="controls">
                      ${sessionScope.userName }
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="textarea">邮箱</label>
                    <div class="controls">
                      <input type="text" class="input-normal" id="inputEmail" name="inputEmail"/>
                      <span id="emailSpanInfo" class="baseInfo"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="textarea">手机号</label>
                    <div class="controls">
                      <input type="text" class="input-normal" id="inputPhone" name="inputPhone"/>
                      <span id="phoneSpanInfo" class="baseInfo"></span>
                    </div>
                </div>
                <div class=" center" style="margin-left: 225px;">
                	<a href="javascript:submitBaseInfo();" class="btn btn-submit" style="text-decoration: none;">提交</a>
                	<span id="updateBaseInfoDiv" class="baseInfoSucc" style="display: none;"></span>
                </div>
            </div>
            <!-- 修改密码 -->
            <div class="tab-pane" id="tab2" style="width: 600px">  
                <div class="control-group">
                    <label class="control-label" for="textarea">原密码</label>
                    <div class="controls">
                      <input type="password" class="input-normal" id="inputOldPassword" name="oldPassword"/>
                      <span id="oldPwdSpanInfo" class="baseInfo"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="textarea">新密码</label>
                    <div class="controls">
                      <input type="password" class="input-normal" id="inputNewPassword" name="newPassword"/>
                      <br/>
                      <span id="newPwdSpanInfo" class="baseInfo"></span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="textarea">确认密码</label>
                    <div class="controls">
                      <input type="password" class="input-normal" id="inputConfirmPassword" name="confirmPassword"/>
                      <br/>
                      <span id="confirmPwdSpanInfo" class="baseInfo"></span>
                    </div>
                </div>
                <div class=" center" style="margin-left: 225px;">
                	<a href="javascript:submitUpdatePwd();" class="btn btn-submit" style="text-decoration: none;">提交</a>
                	<span id="resetPwdSpanInfo" class="baseInfoSucc"></span>
                </div>
            </div>
            <!-- 登录日志 -->
            <div class="tab-pane" id="tab3">
            	
            </div>
        </div>
    </div>
</div>
<!-- 个人信息维护 -->
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/bootstrap-tab.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/baseInfo.js"></script>
</body>
</html>