<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<aside class="pro-sidebar">
  <section class="s-bar">
    <ul class="pro-sidebar-menu">
      <li class="header">账户管理</li>
      <li>
        <a href="javascript:void(0)"><span>基本信息</span></a>
      </li>
      <li class="active">
        <a href="javascript:void(0)"><span>修改密码</span></a>
      </li>
      <li>
        <a href="javascript:void(0)"><span>操作日志</span></a>
      </li>
      <li>
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
      <li>修改密码</li>
    </ol>
    <div class="content">
      <div class="content-header">
        <p>完善基本信息，统计邮箱及手机绑定。</p>
      </div>
      <div class="content-body">
        <form class="info-form">
          <div class="info-form-group">
            <label>原密码:</label>
            <div>
                <input type="text" class="readonly" value="" />
            </div>
          </div>
          <div class="info-form-group">
            <label>新密码:</label>
            <div>
                <input type="text" class="readonly" value="" />
            </div>
          </div>
          <div class="info-form-group">
            <label>确认密码:</label>
            <div>
                <input name="cellphone" type="text" id="input-phone" value="" />
            </div>
          </div>
          <div class="info-form-group">
            <label></label>
            <div>
                <button type="reset" class="btn btn-cancel">重置</button>
                <button type="submit" class="btn">提交</button>
            </div>
          </div>
        </form>
      </div>
    </div>
</div>