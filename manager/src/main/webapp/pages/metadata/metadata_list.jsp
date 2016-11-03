<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="breadcrumbs" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="icon-hospital"></i>
            <a href="#">实验元数据管理</a>
        </li>
        <li class="active"><a data-click="to-app-price-list" href="javascript:void(0)">百菌探</a></li>
    </ul>
</div>
<div class="page-content">
	<div class="panel panel-default">
	    <div style="float: left;">
	    	种类
	    	<select onchange="$.metadata.change()" id="metadataFlag">
	    		<option value="1">样本index</option>
	    		<option value="2">文库index</option>
	    		<option value="3">样本类型</option>
	    	</select>
	     </div>
		 <div style="float: right;">
	         <button class="btn btn-danger" type="button" onclick="$.metadata.toEdit(null);" style="margin-bottom:0;">新增</button>
	     </div>
	</div>
    <div class="row">
          <div class="table-responsive table-div col-sm-12">
                <table id="app-price-list-table" class="table table-striped table-bordered table-hover">
                    <thead>
                        <tr>
                            <th class="min-w-50">种类</th>
                            <th class="min-w-50">名称</th>
                            <th class="min-w-50">序列</th>
                            <th class="min-w-50">序号</th>
                            <th class="min-w-50">操作</th>
                        </tr>
                    </thead>
                    <tbody>
				        <c:if test="${metadataList!=null && metadataList.size()>0 }">
	                        <c:forEach items="${metadataList}" var="data" varStatus="st">
	                            <tr>
	                                <td>${data.flag==1?'样本index':data.flag==2?'文库index':data.flag==3?'样本类型':'错误的种类' }</td>
	                                <td>${data.name }</td>
	                                <td>${data.seq }</td>
	                                <td>${data.priority }</td>
	                                <td>
	                                	<input type="hidden" name="id" value="${data.id }">
	                                	<input type="hidden" name="priority" value="${data.priority }">
	                                    <a href="javascript:void(0)" onclick="$.metadata.toEdit(${data.id },'${data.name }','${data.seq }');">编辑</a> | 
	                                    <a href="javascript:void(0)" onclick="$.metadata.toDelete(${data.id });">删除</a> |
	                                    <c:if test="${not st.first }">
		                                    <a href="javascript:void(0)" onclick="$.metadata.moveUp(${data.id})">上移</a><c:if test="${not st.last }"> |</c:if>
	                                    </c:if>
	                                    <c:if test="${not st.last }">
		                                    <a href="javascript:void(0)" onclick="$.metadata.moveDown(${data.id})">下移</a>
	                                    </c:if>
	                                </td>
	                            </tr>
	                        </c:forEach>
				        </c:if>
				        <c:if test="${metadataList==null||metadataList.size()==0 }">
				        	<tr><td colspan="5">暂无信息</td></tr>
				        </c:if>
                    </tbody>
                </table>
            </div>
    </div>
</div>

<div id="metadata-modal" class="modal modal-celloud-green">
  <div class="modal-dialog">
    <div class="modal-content tipModal">
      <div class="modal-header">
        <button class="close" type="button" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="meta_title">新增</h4>
      </div>
      <div class="modal-body">
      	<form role="form" class="form-horizontal" id="metadataForm">
           <div class="form-group">
               <label class="col-sm-2 control-label" for="name">名称</label>
               <div class="col-sm-10">
		          <input name="name" type="text" id="name">
		          <span style="color: red;"></span>
		          <input name="id" type="hidden" id="id">
		          <input name="appId" type="hidden" id="appId">
		          <input name="flag" type="hidden" id="flag">
               </div>
           </div>
           <div class="form-group">
               <label class="col-sm-2 control-label" for="seq">序列</label>
               <div class="col-sm-10" id="email-roleIds">
		          <input name="seq" type="text" id="seq">
		          <span style="color: red;"></span>
               </div>
           </div>
           <div class="form-group">
               <label class="col-sm-2 control-label"></label>
               <div class="col-sm-10">
		          <span style="color: red;" id="totalError"></span>
               </div>
           </div>
       </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-outline" onclick="$.metadata.save()">确定</button>
      </div>
    </div>
  </div>
</div>

