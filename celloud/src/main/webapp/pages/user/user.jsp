<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<section class="content-header">
  <h1>
    <small>	</small>
  </h1>
  <ol class="breadcrumb">
    <li><a href="javascript:void(0)"><i class="fa fa-user"></i> 帐号管理</a></li>
    <li class="active"><span id="subtitle">基本信息</span></li>
  </ol>
</section>

<section class="content">
  <div class="row">
    <div class="col-xs-12">
      <div class="box box-success color-palette-bo">
        <div class="box-body bg-green" style="padding-left:30px;padding-bottom: 0px;">
          <h3 style="font-family:黑体;color:#FFFFFF">基本信息</h3>
          <p style="font-family:黑体;color:#FFFFFF">完善基本信息，通知邮箱及手机绑定</p>
          <button onclick="showUserInfo()" id="userinfoTab" class="btn btn-success bg-green-active btn-flat" style="width:150px;height:70px;margin-right:0px">基本信息</button>
          <button onclick="showChangePwd()" id="changePwdTab" class="btn btn-success btn-flat" style="width:150px;height:70px;margin-right:0px">修改密码</button>
          <button onclick="showOperaLog()" id="operlogTab" class="btn btn-success btn-flat" style="width:150px;height:70px;margin-right:0px">操作日志</button>
        </div><!-- /.box-body -->
      </div><!-- /.box -->
      <div id="userinfo">
      	<form class="form-horizontal">
          <!-- <div class="form-group" style="vertical-align:middle">
            <label for="photo" class="col-sm-2 control-label">头像</label>
            <div class="col-sm-7">
              <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
            </div>
          </div> -->
          <div class="form-group">
            <label class="col-sm-2 control-label">用户名称:</label>
            <div class="col-sm-7" style="padding-top: 7px;">
              ${sessionScope.userName }
            </div>
          </div>
          <div class="form-group">
            <label for="inputEmail" class="col-sm-2 control-label">邮箱地址:</label>
            <div class="col-sm-7">
              <input type="email" class="form-control" id="inputEmail" placeholder="Email">
              <span id="emailSpanInfo" class="baseInfo"></span>
            </div>
          </div>
          <div class="form-group">
            <label for="inputPhone" class="col-sm-2 control-label">手机号码：</label>
            <div class="col-sm-7">
              <input type="text" class="form-control" id="inputPhone" placeholder="phone">
              <span id="phoneSpanInfo" class="baseInfo"></span>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="button" class="btn btn-success btn-flat" style="width:150px;height:60" onclick="submitBaseInfo()">修改</button>
              <span id="updateBaseInfoDiv" class="baseInfoSucc" style="display: none;"></span>
            </div>
          </div>
        </form>
      </div>
      <div id="changePwd" style="display:none;">
      	<form class="form-horizontal">
          <div class="form-group">
            <label for="inputOldPassword" class="col-sm-2 control-label">原密码:</label>
            <div class="col-sm-7">
              <input type="password" class="form-control" id="inputOldPassword" placeholder="">
              <span id="oldPwdSpanInfo" class="baseInfo"></span>
            </div>
          </div>
          <div class="form-group">
            <label for="inputNewPassword" class="col-sm-2 control-label">新密码:</label>
            <div class="col-sm-7">
              <input type="password" class="form-control" id="inputNewPassword" placeholder="">
              <span id="newPwdSpanInfo" class="baseInfo"></span>
            </div>
          </div>
          <div class="form-group">
            <label for="inputConfirmPassword" class="col-sm-2 control-label">请确认：</label>
            <div class="col-sm-7">
             <input type="password" class="form-control" id="inputConfirmPassword" placeholder="">
             <span id="confirmPwdSpanInfo" class="baseInfo"></span>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="button" class="btn btn-success btn-flat" style="width:150px;height:60" onclick="submitUpdatePwd()">修改</button>
              <span id="resetPwdSpanInfo" class="baseInfoSucc"></span>
            </div>
          </div>
        </form>
      </div>
		<div id="operlog" style="display:none;margin-left:20px;" class="col-sm-10">
		</div>
    </div>
  </div>
</section>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/baseInfo.js"></script>
<script type="text/javascript">
function clearActive(){
	$("#userinfoTab").removeClass("bg-green-active");
	$("#changePwdTab").removeClass("bg-green-active");
	$("#operlogTab").removeClass("bg-green-active");
	$("#userinfo").css("display","none");
	$("#changePwd").css("display","none");
	$("#operlog").css("display","none");
}
function showUserInfo(){
	clearActive();
	$("#userinfoTab").addClass("bg-green-active");
	$("#userinfo").css("display","");
	$("#subtitle").html("基本信息");
}
function showChangePwd(){
	clearActive();
	$("#changePwdTab").addClass("bg-green-active");
	$("#changePwd").css("display","");
	$("#subtitle").html("修改密码");
}
function showOperaLog(){
	clearActive();
	$("#operlogTab").addClass("bg-green-active");
	$("#operlog").css("display","");
	$("#subtitle").html("操作日志");
	//获取日志信息
	$.get("user!getUserLogInfo",{},function(responseText){
		$("#operlog").html(responseText);
	});
}
</script>
