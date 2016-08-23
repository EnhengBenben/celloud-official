<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="upload-modal" class="modal upload-modal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button id="close-upload-modal" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
        <h4 class="modal-title">数据上传</h4>
      </div>
      <div class="modal-body row">
        <div class="step">
          <div class="item active">1</div><hr id="one-to-two">
          <div class="item">2</div><hr id="two-to-three">
          <div class="item">3</div>
        </div>
        <div class="upload-content">
          <div class="info">第一步：请输入文件标签，输入标签可以方便后续的数据检索及体验更加便捷的服务</div>
          <form class="info-form">
	          <div class="info-form-group">
	            <label>数据标签：</label>
	            <div>
	              <input type="text"/>
	            </div>
	          </div>
	          <div class="info-form-group">
	            <label>产品标签:</label>
	            <div>
	              <select>
                    <option>华木兰</option>
                    <option>百菌探</option>
                  </select>
	            </div>
	          </div>
	      </form>
          <button type="button" class="btn">下一步</button>
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
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->