<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<ng-include src="'pages/partial/_partial_notice_sidebar.jsp'"></ng-include>
<div class="pro-body">
    <ol class="breadcrumb">
      <li>CelLoud</li>
      <li>消息管理</li>
      <li>消息设置</li>
    </ol>
    <div class="content">
      <div class="notice-content">
        <div class="notice-body">
	        <h5>消息提醒设置</h5>
	        <table class="table table-main notice-table">
	          <thead>
	            <tr>
	              <th></th>
	              <th>邮件</th>
	              <th>桌面提醒</th>
	              <th>微信</th>
	            </tr>
	          </thead>
	          <tbody>
	          
						<%-- <tr>
							<td>收到【${message.name }】通知</td>
							<td>
								<c:choose>
									<c:when test="${message.email=='2'}">
									    <div><input name="email" class="create-switch" type="checkbox" disabled="disabled" /></div>
									</c:when>
									<c:otherwise>
									    <div><input name="email" class="create-switch" type="checkbox" ${message.email=='0'?'':'checked' } /></div>
									</c:otherwise>
								</c:choose>
						    </td>
							<td>
								<c:choose>
									<c:when test="${message.window=='2'}">
									    <div><input name="window" class="create-switch" type="checkbox" disabled="disabled"/></div>
									</c:when>
									<c:otherwise>
									    <div><input name="window" class="create-switch" type="checkbox" ${message.window=='0'?'':'checked' } /></div>
									</c:otherwise>
								</c:choose>
							</td>
                                        <td>
								<c:choose>
									<c:when test="${message.wechat=='2'}">
									    <div><input name="wechat" class="create-switch" type="checkbox" disabled="disabled"/></div>
									</c:when>
									<c:otherwise>
                                              <div><input name="wechat" class="create-switch" type="checkbox" ${message.wechat=='0'?'':'checked' } /></div>
									</c:otherwise>
								</c:choose>
                                        </td>
                                        <td><input type="hidden" name="messageCategoryId" value="${message.mcId }" /></td>											
                                        <td><input type="hidden" name="flag" value="${message.flag }" /></td>
						</tr> --%>
	            <tr ng-repeat="message in userMessageCategoryList">
	              <td>收到[{{message.name}}]通知</td>
	              <td>
	                <div class="switch-btn-group">
	                  <a><span class="s-btn no">拒绝</span></a>
	                  <a><span class="s-btn active">接受</span></a>
	                </div>
	              </td>
	              <td>
	                <div class="switch-btn-group">
	                  <a><span class="s-btn no">拒绝</span></a>
	                  <a><span class="s-btn active">接受</span></a>
	                </div>
	              </td>
	              <td>
	                <div class="switch-btn-group">
	                  <a><span class="s-btn no">拒绝</span></a>
	                  <a><span class="s-btn active">接受</span></a>
	                </div>
	              </td>
	            </tr>
	            
	          </tbody>
	        </table>
        </div>
      </div>
    </div>
</div>
