<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<header class="header">
    <nav class="navbar navbar-default navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <div class="navbar-logo {{collapsed|logoMiniFilter}}">
            <a class="logo" href="#"></a>
          </div>
        </div>
        <ul class="nav navbar-nav pull-left">
            <shiro:hasPermission name="rocky:product">
              <li>
                <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
                  <i class="cubes-icon">&nbsp;</i>
                </a>
                <div class="dropdown-menu product-dropdown">
                  <a href="<%=request.getContextPath()%>/rocky"><img src="<%=request.getContextPath()%>/images/app/rocky.png" alt="华木兰" title="华木兰"></a>
                </div>
              </li>
            </shiro:hasPermission>
            <shiro:hasPermission name="bsi:product">
              <li>
                <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
                  <i class="cubes-icon">&nbsp;</i>
                </a>
                <div class="dropdown-menu product-dropdown">
                  <a href="<%=request.getContextPath()%>/bsi"><img src="<%=request.getContextPath()%>/images/app/bsi.png" alt="百菌探" title="百菌探"></a>
                </div>
              </li>
            </shiro:hasPermission>
          <li><a data-toggle="modal" data-target="#upload-modal" ng-click="getProTags()"><i class="upload-icon"></i></a></li>
        </ul>
        <ul class="nav navbar-nav pull-right">
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="code-icon"></i>
            </a>
            <div class="dropdown-menu code-dropdown">
              <img alt="扫码关注" src="<%=request.getContextPath()%>/images/icon/qrcode.jpg">
            </div>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="money-icon">&nbsp;</i>
            </a>
            <div class="dropdown-menu money-dropdown">
              <p>账户余额：<span class="tips">{{userInfo.balances}}</span>元</p>
              <a class="btn" href="<%=request.getContextPath()%>/index#/expense/paydetail">立即充值</a><br>
              <a class="btn-link" href="<%=request.getContextPath()%>/index#/expense/consume">查看消费记录</a>
            </div>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="bell-icon">&nbsp;</i>
              <span class="label label-danger">{{notices.num==0?'':(notices.num+'')}}</span>
            </a>
            <div class="dropdown-menu message-dropdown">
              <p> 您有<span class="tips">{{notices.num}}</span>条新消息</p>
              <a class="btn-link" href="<%=request.getContextPath()%>/index#/notices">查看所有</a>
            </div>
          </li>
           <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="bell-icon">&nbsp;</i>
              <span class="label label-danger">{{messages.num==0?'':(''+messages.num)}}</span>
            </a>
            <div class="dropdown-menu message-dropdown">
              <p> 您有<span class="tips">{{messages.num}}</span>条新消息</p>
              <a class="btn-link" href="<%=request.getContextPath()%>/index#/messages">查看所有</a>
            </div>
          </li>
          <li class="dropdown">
            <a href="javascript:void(0)" data-toggle="dropdown"  role="button" aria-haspopup="true" aria-expanded="false">
              <i class="user-icon">&nbsp;</i>
            </a>
            <div class="dropdown-menu user-dropdown">
              <a class="btn" href="<%=request.getContextPath()%>/index#/user/base">个人信息</a>
              <a class="btn btn-cancel" href="logout">退出</a>
            </div>
          </li>
        </ul>
      </div>
    </nav>
  </header>