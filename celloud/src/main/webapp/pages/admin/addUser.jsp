<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="celloud,生物信息云平台,生物大数据平台,序列数据分析,基因大数据" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="bookmark" href="<%=request.getContextPath()%>/favicon.ico"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css" media="all" />
<title>CelLoud 用户注册</title>
</head>
<body onselectstart="return false;">
<!--#S bgContainer-->
<div class="bgContainer">
	<img src="<%=request.getContextPath()%>/images/home/login_bg.png" id="bg" />
	<!--#S wrapper-->
	<div class="wrapper">
    	<div class="register">
        	<div class="register-logo" id="logo"><img src="<%=request.getContextPath()%>/images/login/login_logo.png" /></div>
        	<s:if test="%{flag}">
			<form id="userForm">
			    <div class="main_regist clearfix">
			    	<div class="register-font">用户注册</div>
			    	<input type="hidden" name="user.deptId" value="<s:property value="user.deptId"/>" />
					<input type="hidden" name="user.companyId" value="<s:property value="user.companyId"/>" />
					<input type="hidden" name="user.navigation" value="1" />
			    	<input type="text" name="user.email" readonly="readonly" value="<s:property value="user.email"/>" id="email"/>
			    	<input type="text" id="add_username" name="user.username" class="addUser" placeholder="用户名">
		    	    <input type="password" name="user.password" class="addUser pwd" placeholder="密码（6-16位数字字母组合）" id="add_password"/>
		    	    <input type="password" placeholder="确认密码" id="add_confirmPwd" name="confirmPwd" class="addUser pwd">
		    	    <input type="text" placeholder="真实姓名" name="user.truename" class="addUser">
		    	    <input type="text" placeholder="手机号码" id="add_cellphone" name="user.cellPhone" class="addUser" >
		    	    <div class="autolog">
	<%--                 	<span id="remPass"><img src="images/login/nocheck.png"/></span> --%>
	<!--                 	<input id="isAllow" name="isRem" value="0" style="display: none"> -->
	                	<input type="checkbox" id="isAllow">
	                	我已阅读并同意<a href="<%=request.getContextPath() %>/service.html" target="_blank">《Celloud用户使用协议》</a></span>
	                </div>
	               <div class="autolog">
	               	 <span class="error"></span>
	               </div>
	               <a href="javascript:void()" class="btn-email" id="save">注册</a>
	               <a href="javascript:void(0);" id="cancle" class="return">重置</a>
	               <div id="successDiv" style="display:none;font-size:12px;color:#007DB1">
						注册成功！<span style="color:#b94a48;" id="second"></span>秒后跳转到登录页面...
					</div>
	            </div>
			</form>
			</s:if>
			<s:else>
				<div class="main_f clearfix">
	             	<span style="font-size:50px">对不起，您的链接已超时，请重新申请！</span>
	            	<a href="../home.html" class="error_return">返回首页</a>
	            </div>
			</s:else>
            <div class="view-tips">最佳浏览体验：Chrome / Firefox 8.0+ / IE 9.0+</div>
        </div>
        <jsp:useBean id="_now" class="java.util.Date" />
        <div class="footer">© <fmt:formatDate value="${_now}" type="both" dateStyle="long" pattern="yyyy" /> Celloud，Inc. All Rights reserved. <a  href="#">生物信息云平台</a> · <a  href="javascript:void();">沪ICP备14035977号</a> · <a href="service.html" target="_blank">服务与支持</a> · <a href="feedBack.html" target="_blank">意见反馈</a></div>
    </div>
	<!--#E wrapper-->
</div>
<!--#E bgContainer-->
</body>
<script type="text/javascript" src="<%=request.getContextPath() %>/plugins/jQuery/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/metro.js"></script>
<script type="text/javascript">
//判断浏览器是否支持 placeholder属性
function isPlaceholder(){
	var input = document.createElement('input');
	return 'placeholder' in input;
}

if (!isPlaceholder()) {//不支持placeholder 用jquery来完成
	$(document).ready(function() {
	    if(!isPlaceholder()){
	        $("input").not("input[name='password']").each(//把input绑定事件 排除password框
	            function(){
	                if($(this).val()=="" && $(this).attr("placeholder")!=""){
	                    $(this).val($(this).attr("placeholder"));
	                    $(this).addClass("placeholder");
	                    $(this).focus(function(){
	                        if($(this).val()==$(this).attr("placeholder")) $(this).val("");
	                    });
	                    $(this).blur(function(){
	                        if($(this).val()=="") $(this).val($(this).attr("placeholder"));
	                    });
	                }
	        });
	        //对password框的特殊处理1.创建一个text框 2获取焦点和失去焦点的时候切换
	        var pwdField	= $("input[type=password]");
	        var pwdVal		= pwdField.attr('placeholder');
	        pwdField.after('<input id="pwdPlaceholder" type="text" class="pwd" value='+pwdVal+' autocomplete="off" />');
	        var pwdPlaceholder = $('#pwdPlaceholder');
	        pwdPlaceholder.show();
	        pwdField.hide();
	        
	        pwdPlaceholder.focus(function(){
	        	pwdPlaceholder.hide();
	        	pwdField.show();
	        	pwdField.focus();
	        });
	        
	        pwdField.blur(function(){
	        	if(pwdField.val() == '') {
	        		pwdPlaceholder.show();
	        		pwdField.hide();
	        	}
	        });
	    }
	});
}
	$.ajaxSetup ({
		cache: false //关闭AJAX相应的缓存
	});
	$("#isAllow").change(function(){
		if($("#isAllow").prop("checked")){
			$("#save").removeClass("disUse");
			$("#save").addClass("btn-success");
		}else{
			$("#save").removeClass("btn-success");
			$("#save").addClass("disUse");
		}
	});
	$(document).ready(function(){
		resetVal();
		$("#cancle").click(function(){
			resetVal();
		});
		
		$("#save").click(function(){
			if(!validateAddForm()){
				return;
			}else{
				//验证用户名重复问题
				var username = $.trim($("#add_username").val());
				$.get("<%=request.getContextPath() %>/checkUsername",{"username":username},function(flag){
					if(flag){
						$(".error").html("该用户名已经存在！");
					}else{
						$(".error").html("");
						var email = $("#email").val();
						$.get("<%=request.getContextPath() %>/user!checkEmailOne",{"email":email},function(flag){
							if(flag){//true 校验失败
								$(".error").html("该邮箱已经存在！");
							}else{
								$(".error").html("");
								$("#add_username").val(username);
								var params = $("#userForm").serialize();
								//服务协议
								var isAllow = $("#isAllow").prop("checked");
								if(!isAllow){
									$("#isAllowError").css("display","");
									$(".error").html("请阅读并同意《Celloud用户使用协议》");
									return;
								}else{
									$("#isAllowError").css("display","none");
								}
								$.post('<%=request.getContextPath() %>/userJson_addUser.action',params,function(addFlag){
									if(addFlag){
										$("#successDiv").css("display","");
										$("#save").unbind();
										countDown(3);
									}else{
										alert("注册失败");
									}
								});
							}
						});
					}
				});
			}
		});
	});
	
	function countDown(time){
		$("#second").text(time);//<span>中显示的内容值
		if(--time>0){
		   setTimeout("countDown("+time+")",1000);//设定超时时间
		}else{
			window.location.href="<%=request.getContextPath() %>/toLogin";//跳转页面
		}
	}
	
	//重置
	function resetVal(){
		$(".addUser").val("");
		$(".error").html("");
		$("#successDiv").css("display","none");
	}
	
	//验证addForm
	function validateAddForm(){
		var username = $.trim($("#add_username").val());
		if(username==""){
			$(".error").html("请输入用户名！");
			return false;
		}else if(!/^([a-zA-z_]{1})([\w]*)$/g.test(username)){
			$(".error").html("用户名只能由字母、数字、下划线组成，且只能以下划线或字母开头！");
			return false;
		}
		var password = $.trim($("#add_password").val());
		if(password==""){
			$(".error").html("请输入密码！");
			return false;
		}else{
			var numberRegex = /^[\d+]{6,16}$/;
			if(numberRegex.test(password)){
				$(".error").html("密码不能全为数字！");
				return false;
			}else{
				var combineRegex = /^[a-zA-Z0-9_]{6,16}$/;
				if(!combineRegex.test(password)){
					$(".error").html("密码为6-16位的字母数字组合，也可以包含下划线_！");
					return false;
				}
			}
		}
		//确认密码
		var confirmPwd = $.trim($("#add_confirmPwd").val());
		if(confirmPwd==""){
			$(".error").html("请输入确认密码！");
			return false;
		}else{
			if(confirmPwd!=password){
				$(".error").html("确认密码与密码不同！");
				return false;
			}
		}
		//手机号
		var cellPhone = $.trim($("#add_cellphone").val());
		if(cellPhone!=""){
			var mobileregex = /^[\d+]{11}$/;
		    if(!mobileregex.test(cellPhone)){
			    $(".error").html("手机号格式不正确！");
			    return false;
		    }
		}
		return true;
	}
</script>
</html>