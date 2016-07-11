<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="content">
  <div class="upload-content">
    <div id="upload-step-one">
	    <div class="step">
		  <div class="item">1</div>
		  <div class="info">第一步：请输入文件标签，输入标签可以方便后续的数据检索及体验更加便捷的服务</div>
	    </div>
	    <div class="form-inline">
	      <div class="form-group">
		    <label for="batch-info">数据标签</label>
		    <input type="text" class="form-control" id="batch-info" placeholder="标签">
		  </div>
	      <button id="upload-next" class="btn -confirm">下一步</button>
	    </div>
	</div>
    <div id="upload-step-two" class="hide">
      <div class="step">
        <div class="item">2</div>
        <div class="info">第二步：拖拽文件到下面指定区域或者点击指定位置的选择文件进行选择，后即可自动上传文件</div>
      </div>
      <div id="plupload-content" class="box-body plupload-content">
        <div class="upload-text"><i class="celicon grey-upload-icon"></i>拖拽文件到此或者点击选择文件上传</div>
      </div>
      <table class="table table-main">
        <thead>
          <tr>
            <th>文件名</th>
            <th>上传进度</th>
            <th>上传速度</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody id="upload-list-tbody">
          <tr>
            <td>123456789.R1.fastq.gz</td>
            <td>60%</td>
            <td>45kb/s</td>
            <td>上传中</td>
            <td><a href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>
          </tr>
          <tr>
            <td>123456789.R2.fastq.gz</td>
            <td>60%</td>
            <td>45kb/s</td>
            <td>上传中</td>
            <td><a href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>
          </tr>
          <tr>
            <td>1ddfgghhhh.R1.fastq.gz</td>
            <td>60%</td>
            <td>45kb/s</td>
            <td>上传中</td>
            <td><a href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>
          </tr>
          <tr>
            <td>1ddfgghhhh.R2.fastq.gz</td>
            <td>100%</td>
            <td>65kb/s</td>
            <td>完成</td>
            <td><a href="javascript:void(0)"><i class="fa fa-times-circle" aria-hidden="true"></i></a></td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>