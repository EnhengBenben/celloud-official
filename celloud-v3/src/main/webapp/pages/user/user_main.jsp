<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<section class="content-header">
	<h1>
		<small> </small>
	</h1>
	<ol class="breadcrumb">
		<li>
			<a href="javascript:void(0)">
				<i class="fa fa-user"></i> 帐号管理
			</a>
		</li>
		<li class="active">
			<span id="subtitle">基本信息</span>
		</li>
	</ol>
</section>

<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="mainpage" id="appMain">
				<div class="y-row operation-serve box box-success" data-spm="16">
					<div class="info">
						<p>完善基本信息，通知邮箱及手机绑定。</p>
					</div>
					<ul id="userOperaUl">
						<li class="active" id="userinfoTab">
							<a href="javascript:users.showUserInfo()">基本信息</a>
						</li>
						<li id="changePwdTab">
							<a href="javascript:users.showChangePwd()">修改密码</a>
						</li>
						<li id="operlogTab">
							<a href="javascript:users.showOperaLog()">操作日志</a>
						</li>
					</ul>
				</div>
				<div class="y-row" style="padding: 20px 10px; background-color: #fff;" data-spm="17">
					<div class="common-normal common-slide common-normals" id="userinfo">
						<form class="form-horizontal form-cel" id="userBaseInfoForm"
							action="<%=request.getContextPath()%>/user/updateInfo">
							<div class="form-group" style="vertical-align: middle;">
								<label for="photo" class="col-xs-2 form-label control-label">头像</label>
								<input type="hidden" name="icon" value="${user.icon==null?'01':user.icon }">
								<div class="col-xs-10">
									<img src="${user.avatar }" role="button" class="img-circle" alt="User Image" title="头像预览，保存后生效" onclick="users.showUserImages()"
										id="userImage" />
								</div>
							</div>
							<div class="form-group">
								<div class="control-label form-label col-xs-2">用户名称</div>
								<div class="col-xs-10">
									<input type="text" class="readonly" value="${user.username }" disabled="disabled" />
								</div>
							</div>
							<div class="form-group">
								<div class="control-label form-label col-xs-2">邮箱地址</div>
								<div class="col-xs-10">
									<input name="email" type="text" value="${user.email}" id="inputEmail" placeholder="Email" />
									<span id="emailSpanInfo" class="baseInfo"></span>
								</div>
							</div>
							<div class="form-group">
								<div class="control-label form-label col-xs-2">手机号码</div>
								<div class="col-xs-10">
									<input name="cellphone" type="text" id="inputPhone" placeholder="phone" value="${user.cellphone }" />
									<span id="phoneSpanInfo" class="baseInfo"></span>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="button" class="btn btn-success btn-flat" style="width: 150px; height: 60"
										onclick="users.saveBaseInfo()">修改</button>
									<span id="updateBaseInfoDiv" class="baseInfoSucc" style="display: none;"></span>
								</div>
							</div>
						</form>
					</div>
					<div class="common-normal common-slide common-normals hide" id="changePwd">
						<form class="form-horizontal form-cel" id="updatePasswordForm"
							action="<%=request.getContextPath()%>/user/updatePassword">
							<div class="form-group">
								<div class="control-label form-label col-xs-2">原密码</div>
								<div class="col-xs-10">
									<input type="password" name="oldPassword" id="inputOldPassword" placeholder="" />
									<span id="oldPwdSpanInfo" class="baseInfo"></span>
								</div>
							</div>
							<div class="form-group">
								<div class="control-label form-label col-xs-2">新密码</div>
								<div class="col-xs-10">
									<input name="newPassword" type="password" id="inputNewPassword" placeholder="">
									<span id="newPwdSpanInfo" class="baseInfo"></span>
								</div>
							</div>
							<div class="form-group">
								<div class="control-label form-label col-xs-2">请确认</div>
								<div class="col-xs-10">
									<input type="password" id="inputConfirmPassword" placeholder="">
									<span id="confirmPwdSpanInfo" class="baseInfo"></span>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="button" class="btn btn-success btn-flat" style="width: 150px; height: 60"
										onclick="users.updatePassword()">修改</button>
									<span id="resetPwdSpanInfo" class="baseInfoSucc"></span>
								</div>
							</div>
						</form>
					</div>
					<div class="common-normal common-slide common-normals hide" id="operlog"></div>
				</div>
			</div>
		</div>
	</div>
</section>
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" id="userImages">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">给自己选一个头像吧</h4>
			</div>
			<div class="modal-body">
				<c:forEach begin="1" end="9" var="num">
					<label>
						<img src="images/avatar/0${num }.png" onclick="users.chooseUserImage('0${num }')" role="button"
							style="height: 85px;" class="img-circle" alt="User Image" />
					</label>
				</c:forEach>
				<c:if test="${num==3||num==6 }">
					<br>
				</c:if>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/user.js?version=3.0"></script>
