<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<link href="<%=request.getContextPath() %>/dist/css/productDetail.css" rel="stylesheet" type="text/css" />
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
      <div class="mainpage" id="appMain">
		  <div class="y-row operation-serve"  data-spm="16">
	  		<div class="info">
	    	  <p>完善基本信息，通知邮箱及手机绑定。</p>
	  		</div>
	  		<ul>
		      <li><a href="javascript:showUserInfo()" id="userinfoTab">基本信息</a></li>
		      <li><a href="javascript:showChangePwd()" id="changePwdTab">修改密码</a></li>
		      <li><a href="javascript:showOperaLog()" id="operlogTab">操作日志</a></li>
	  		</ul>
		  </div>
	      <div class="y-row" style="padding: 20px 10px;background-color: #fff;"  data-spm="17">
	        <div class="common-normal common-slide common-normals" id="userinfo">
	          <form class="form-horizontal form-cel" style="width:600px">
		          <!-- <div class="form-group" style="vertical-align:middle">
		            <label for="photo" class="col-sm-2 control-label">头像</label>
		            <div class="col-sm-7">
		              <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
		            </div>
		          </div> -->
		        <div class="form-group">
					<div class="control-label form-label col-xs-3">用户名称</div>
					<div class="col-xs-9">
						<input type="text" class="readonly" value="${sessionScope.userName }" disabled="disabled"/>
					</div>
				</div>
				<div class="form-group">
					<div class="control-label form-label col-xs-3">邮箱地址</div>
					<div class="col-xs-9">
						<input type="text" value="${data.dataTags}" id="inputEmail" placeholder="Email"/>
						<span id="emailSpanInfo" class="baseInfo"></span>
					</div>
				</div>
				<div class="form-group">
					<div class="control-label form-label col-xs-3">手机号码</div>
					<div class="col-xs-9">
						<input type="text" id="inputPhone" placeholder="phone"/>
						<span id="phoneSpanInfo" class="baseInfo"></span>
					</div>
				</div>
		          <div class="form-group">
		            <div class="col-sm-offset-3 col-sm-9">
		              <button type="button" class="btn btn-success btn-flat" style="width:150px;height:60" onclick="submitBaseInfo()">修改</button>
		              <span id="updateBaseInfoDiv" class="baseInfoSucc" style="display: none;"></span>
		            </div>
		          </div>
		      </form>
	        </div>
	        <div class="common-normal common-slide common-normals hide" id="changePwd">
	           <form class="form-horizontal form-cel" style="width:600px">
		          <!-- <div class="form-group" style="vertical-align:middle">
		            <label for="photo" class="col-sm-2 control-label">头像</label>
		            <div class="col-sm-7">
		              <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
		            </div>
		          </div> -->
		        <div class="form-group">
					<div class="control-label form-label col-xs-3">原密码</div>
					<div class="col-xs-9">
						<input type="password" id="inputOldPassword" placeholder=""/>
						<span id="oldPwdSpanInfo" class="baseInfo"></span>
					</div>
				</div>
				<div class="form-group">
					<div class="control-label form-label col-xs-3">新密码</div>
					<div class="col-xs-9">
					  <input type="password" id="inputNewPassword" placeholder="">
		              <span id="newPwdSpanInfo" class="baseInfo"></span>
					</div>
				</div>
				<div class="form-group">
					<div class="control-label form-label col-xs-3">请确认</div>
					<div class="col-xs-9">
					  <input type="password" id="inputConfirmPassword" placeholder="">
		              <span id="confirmPwdSpanInfo" class="baseInfo"></span>
					</div>
				</div>
		        <div class="form-group">
		           <div class="col-sm-offset-3 col-sm-9">
		              <button type="button" class="btn btn-success btn-flat" style="width:150px;height:60" onclick="submitUpdatePwd()">修改</button>
		              <span id="resetPwdSpanInfo" class="baseInfoSucc"></span>
		           </div>
		        </div>
		      </form>
		    </div>
			<div class="common-normal common-slide common-normals hide" id="operlog">
			</div>
	      </div>
	  </div>
    </div>
  </div>
</section>

<script type="text/javascript" src="<%=request.getContextPath() %>/js/baseInfo.js"></script>
<script type="text/javascript">
function clearActive(){
	$("#userinfo").addClass("hide");
	$("#changePwd").addClass("hide");
	$("#operlog").addClass("hide");
}
function showUserInfo(){
	clearActive();
// 	$("#userinfoTab").addClass("bg-green-active");
	$("#userinfo").removeClass("hide");
	$("#subtitle").html("基本信息");
}
function showChangePwd(){
	clearActive();
// 	$("#changePwdTab").addClass("bg-green-active");
	$("#changePwd").removeClass("hide");
	$("#subtitle").html("修改密码");
}
function showOperaLog(){
	clearActive();
// 	$("#operlogTab").addClass("bg-green-active");
	$("#operlog").removeClass("hide");
	$("#subtitle").html("操作日志");
	//获取日志信息
	$.get("user!getUserLogInfo",{},function(responseText){
		$("#operlog").html(responseText);
	});
}
</script>
