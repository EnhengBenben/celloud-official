<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content row">

	<div class="step-item">
	  <div class="step-one active">1</div><hr id="one-to-two">
	  <div class="step-two">2</div><hr id="two-to-three">
	  <div class="step-three">3</div>
	</div>
	<div class="step-one-content">
	  <input id="batch-info" type="text" placeholder="请输入标签" value=""/><br>
	  <p><span>为每次上传数据按输入加入标签，提升后续报告查询、数据管理、分类汇总提供快捷服务。<span></p>
	  <p><span>示例：外科5月；ICU-9床；急诊发热<span></p>
	  <a id="next-step" class="btn" href="javascript:void(0)">下一步</a>
	</div>
	<div class="step-two-content hide">
	  <div class="upload-container">
	    <div id="plupload-content" class="box-body plupload-content">
	      <ul id="upload-filelist" class="plupload-filelist"></ul>
	      <div class="upload-text"><i class="celicon grey-upload-icon"></i>拖拽文件到此或者点击选择文件上传</div>
	    </div>
	  </div>
	  <p><span><input id="need-split" type="checkbox" value="1" checked="checked">需要数据拆分 (run: Split)</span></p>
	  <p><span>&gt;目前支持 .tar.gz; .zip; 压缩过的fasta 格式的序列文件及index 文件</span></p>
	  <p><span>&gt;文件命名规则请点击查看 "百菌探_下机文件命名规则"</span></p>
	  <p><span>&gt;数据拆分(Split) 选中时系统先按index文件对数据拆分并重命名.</span></p>
	  <input id="tag-info" type="hidden" value="1"/>
	  <a id="begin-upload" class="btn" href="javascript:void(0)">开始上传</a>
	</div>
	<div class="step-three-content hide">
	  <div class="upload-status">数据上传中...数据标签 ：<span id="tags-review"></span></div>
	  <div class="upload-container">
	    <div id="plupload-content" class="box-body plupload-content">
	      <ul id="uploading-filelist" class="plupload-filelist">
	        <li class="plupload-filelist-header">
	          <div class="plupload-file-name"><span>文件名</span></div>
	          <div class="plupload-file-size">大小</div>
	          <div class="plupload-file-surplus">剩余时间</div>
	          <div class="plupload-file-status">进度</div>
	          <div class="plupload-file-action">操作</div>
	        </li>
	      </ul>
	    </div>
	  </div>
	  <p><span>文件上传中结束会自动开始分析任务，可在报告页面查看最新报告.</span></p>
	  <a id="upload-more" class="btn" href="javascript:void(0)">上传更多</a>
	</div>
</div>