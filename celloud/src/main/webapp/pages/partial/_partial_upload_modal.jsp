<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="upload-modal" class="modal upload-modal" ng-controller="fileUpload" novalidate>
  <div class="modal-dialog modal-md">
    <div class="modal-content">
      <div class="modal-header">
        <button id="close-upload-modal" type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true"><i class="fa fa-times-circle"></i></span></button>
        <h4 class="modal-title">数据上传</h4>
      </div>
      <div class="modal-body row">
        <div class="step">
          <div class="item active">1</div><hr id="one-to-two" ng-class="{active: step!='one'}">
          <div class="item" ng-class="{active: step!='one'}">2</div><hr id="two-to-three" ng-class="{active: step=='three'}">
          <div class="item" ng-class="{active: step=='three'}">3</div>
        </div>
        <div class="upload-content" ng-class="{hide: step!='one'}">
          <div class="info">第一步：请输入数据标签，输入标签可以方便后续的数据检索及体验更加便捷的服务</div>
          <form class="info-form" name="first">
	          <div class="info-form-group">
	            <label>数据标签：</label>
	            <div>
	              <input type="text" placeholder="请输入数据标签" name="batch" ng-model="batch" required maxlength="30"/>
	            </div>
	          </div>
	          <div class="info-form-group">
	            <label>产品标签：</label>
	            <div>
	              <select ng-model="tagSelected" name="productTag" ng-options="tag.tagName for tag in tags" required>
                  </select>
	            </div>
	          </div>
	          <button type="button" class="btn" ng-click="nextStep()" ng-disabled="first.$invalid">下一步</button>
	      </form>
        </div>
        
        <div class="upload-content" ng-class="{hide: step!='two'}">
          <div class="info">第二步：拖拽文件到下面指定区域或者点击指定位置的选择文件</div>
          <div class="info text-left">数据标签：{{batch}}&nbsp;&nbsp;产品标签：{{tagSelected.tagName}}</div>
          <div id="plupload-content" class="plupload-content" style="height:120px;overflow-y: auto;">
	          <table class="table table-main" id="upload-list-table">
	            <tbody id="upload-list-tbody">
	            </tbody>
	          </table>
            <div class="upload-text" ng-show="upload.files.length<1">
                <i class="celicon -uploadgray"></i> 拖拽文件到此或者点击选择文件上传
            </div>
          </div>
          <button ng-click="beginUpload()" ng-disabled="upload.files.length<1" class="btn" href="javascript:void(0)">开始上传</button>
        </div>
        
        <div class="upload-content" ng-class="{hide: step!='three'}">
          <div class="info">第三步：上传中...</div>
          <div class="info text-left">数据标签：{{batch}}&nbsp;&nbsp;产品标签：{{tagSelected.tagName}}</div>
          <div id="plupload-content2" class="plupload-content" style="height:150px;overflow-y: auto;">
	          <table class="table table-main" id="uploading-list-table">
	            <thead>
	                <tr>
	                    <th>文件名</th>
	                    <th>上传进度</th>
	                    <th>剩余时间</th>
	                    <th>上传速度</th>
	                    <th>操作</th>
	                </tr>
	            </thead>
	            <tbody id="uploading-list-tbody">
	            </tbody>
	          </table>
          </div>
          <button id="uploadMore" ng-click="uploadMore()" class="btn" href="javascript:void(0)">上传更多</button>
        </div>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="bsi" ng-controller="bsiFileUpload">
	<div id="bsi-upload-modal" class="modal upload-modal">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button id="close-upload-modal" type="button" class="close" ng-click="closeUploadModal()" data-dismiss="modal" aria-label="Close">
	                    <span aria-hidden="true">
	                        <i class="fa fa-times-circle"></i>
	                    </span>
	                </button>
	                <h4 class="modal-title">数据上传</h4>
	            </div>
	            <div class="modal-body row">
	                <div class="step-item">
	                    <div class="step-one active">1</div>
	                    <hr id="bsi-one-to-two">
	                    <div class="step-two">2</div>
	                    <hr id="bsi-two-to-three">
	                    <div class="step-three">3</div>
	                </div>
	                <div class="step-one-content">
	                    <input id="batch-info" type="text" placeholder="请输入标签" ng-model="bsiBatch" ng-keypress="batchInfo($event)" maxlength="30" />
	                    <br>
	                    <p>
	                        <span>
	                            为每次上传数据按输入加入标签，提升后续报告查询、数据管理、分类汇总提供快捷服务。
	                            <span>
	                    </p>
	                    <p>
	                        <span>
	                            示例：外科5月；ICU-9床；急诊发热
	                            <span>
	                    </p>
	                    <a id="next-step" class="btn" href="javascript:void(0)" ng-click="stepTwo()">下一步</a>
	                </div>
	                <div class="step-two-content hide">
	                    <div class="upload-container" style="position: relative;">
	                        <div id="bsi-plupload-content" class="box-body plupload-content">
	                            <ul id="upload-filelist" class="plupload-filelist"></ul>
	                            <div class="upload-text">
	                                <i class="celicon grey-upload-icon"></i>拖拽文件到此或者点击选择文件上传
	                            </div>
	                        </div>
	                    </div>
	                    <p>
                            <span>&gt;目前支持 .tar.gz; .zip; 压缩过的fasta 格式的配对序列文件</span>
                        </p>
                        <p>
                            <span>&gt;文件命名规则为  (实验样本编号.tar.gz) 或 (文库编码_R1.tar.gz & 文库编码_R2.tar.gz )</span>
                        </p>
	                    <input id="tag-info" type="hidden" value="1" />
	                    <button ng-click="beginBsiUpload()" class="btn" ng-disabled="bsiUploader == null || bsiUploader.files.length < 1">开始上传</button>
	                </div>
	                <div class="step-three-content hide">
	                    <div class="upload-status">
	                        数据上传中...数据标签 ：
	                        <span id="tags-review"></span>
	                    </div>
	                    <div class="upload-container">
	                        <div id="plupload-content" class="box-body plupload-content">
	                            <ul id="uploading-filelist" class="plupload-filelist">
	                                <li class="plupload-filelist-header">
	                                    <div class="plupload-file-name">
	                                        <span>文件名</span>
	                                    </div>
	                                    <div class="plupload-file-size">大小</div>
	                                    <div class="plupload-file-surplus">剩余时间</div>
	                                    <div class="plupload-file-status">进度</div>
	                                    <div class="plupload-file-action">操作</div>
	                                </li>
	                            </ul>
	                        </div>
	                    </div>
	                    <p>
	                        <span>文件上传中结束会自动开始分析任务，可在报告页面查看最新报告.</span>
	                    </p>
	                    <a id="bsi-upload-more" class="btn" href="javascript:void(0)">上传更多</a>
	                </div>
	            </div>
	        </div>
	        <!-- /.modal-content -->
	    </div>
	    <!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</div>