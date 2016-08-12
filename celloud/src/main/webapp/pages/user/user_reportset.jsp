<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<aside class="pro-sidebar">
  <section class="s-bar">
    <ul class="pro-sidebar-menu">
      <li class="header">账户管理</li>
      <li>
        <a href="javascript:void(0)"><span>基本信息</span></a>
      </li>
      <li>
        <a href="javascript:void(0)"><span>修改密码</span></a>
      </li>
      <li>
        <a href="javascript:void(0)"><span>操作日志</span></a>
      </li>
      <li class="active">
        <a href="javascript:void(0)"><span>报告设置</span></a>
      </li>
    </ul>
  </section>
</aside>
<div class="pro-body">
  <ol class="breadcrumb">
    <li>CelLoud</li>
    <li>用户中心</li>
    <li>账户管理</li>
    <li>报告设置</li>
  </ol>
  <div class="content">
    <div class="content-body">
      <div class="col-lg-12 hospital-logo-content">
	      <div class="col-lg-6">
	        <h5>医院LOGO：</h5>
	        <div class="hospital-logo">
	          <img src="<%=request.getContextPath()%>/images/avatar/01.png" role="button" id="user-image"/>
	        </div>
	        <p>
	          <a href="javascript:void(0)"><i class="fa fa-picture-o" aria-hidden="true"></i><span>选择图片</span></a><span>
	          （最佳尺寸140×140，可以上传高质量图片进行裁切）</span>
	        </p>
	        <button type="button" class="btn">提交</button>
	      </div>
	      <div class="col-lg-6">
	        <h5>图片预览：</h5>
	        <div class="preview logo-140">
              <img src="<%=request.getContextPath()%>/images/avatar/01.png" role="button" id="user-image"/>
              <span>140×140</span>
            </div>
            <div class="preview logo-120">
              <img src="<%=request.getContextPath()%>/images/avatar/01.png" role="button" id="user-image"/>
              <span>120×120</span>
            </div>
            <div class="preview logo-100">
              <img src="<%=request.getContextPath()%>/images/avatar/01.png" role="button" id="user-image"/>
              <span>100×100</span>
            </div>
	      </div>
      </div>
    </div>
  </div>
</div>